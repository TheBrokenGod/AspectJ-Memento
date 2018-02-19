package ch.jacopoc.memento;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * 
 * @author Jacopo Carravieri
 *
 */
public class History<T extends IMemento> {

	private List<T> savedStates;
	private int currentState;
	
	public Consumer<T> onEnter = null;
	public Consumer<T> onExit = null;
	public Consumer<T> onEnterFromPrevious = null;
	public Consumer<T> onEnterFromNext = null;
	public Consumer<T> onExitToPrevious = null;
	public Consumer<T> onExitToNext = null;
	
	/**
	 * 
	 * @param emptyState
	 */
	public History(T emptyState) {
		savedStates = new ArrayList<>(Arrays.asList(emptyState));
		currentState = 0;
	}
	
	/**
	 * 
	 * @return
	 */
	public int size() {
		return savedStates.size();
	}
	
	/**
	 * 
	 * @return
	 */
	public T current() {
		return savedStates.get(currentState);
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean hasPrevious() {
		return currentState > 0;
	}
	
	/**
	 * 
	 * @return
	 */
	public T previous() {
		return savedStates.get(--currentState);
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean moveBack() {
		if(hasPrevious()) {
			trigger(onExit);
			trigger(onExitToPrevious);
			currentState -= 1;
			trigger(onEnter);
			trigger(onEnterFromNext);
			return true;
		}
		return false;
	}
	
	private void trigger(Consumer<T> callback) {
		if(callback != null)
			callback.accept(current());
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean hasNext() {
		return currentState <= savedStates.size()-2;
	}
	
	/**
	 * 
	 * @return
	 */
	public T next() {
		return savedStates.get(++currentState);
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean moveForward() {
		if(hasNext()) {
			trigger(onExit);
			trigger(onExitToNext);
			currentState += 1;
			trigger(onEnter);
			trigger(onEnterFromPrevious);
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param memento
	 */
	public void saveState(T memento) {
		// Drop the previous, diverging timeline
		if(hasNext()) {
			savedStates = savedStates.subList(0, currentState + 1);
		}
		// New memento becomes the most recent state
		savedStates.add(memento);
		currentState++;
	}
	
	@Override
	public String toString() {
		return "History: " + Arrays.deepToString(savedStates.toArray());
	}
}
