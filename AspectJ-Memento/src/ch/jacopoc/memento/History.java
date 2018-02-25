package ch.jacopoc.memento;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * @author Jacopo Carravieri
 *
 */
public class History<T extends Memento<T>> {

	private List<T> savedStates;
	private int currentState;
	
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
		if(!hasPrevious()) {
			return false;
		}
		current().onExit();
		current().onExitToPrevious();
		currentState -= 1;
		current().onEnter();
		current().onEnterFromNext();
		return true;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean hasNext() {
		return currentState <= savedStates.size() - 2;
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
		if(!hasNext()) {
			return false;
		}
		current().onExit();
		current().onExitToNext();
		currentState += 1;
		current().onEnter();
		current().onEnterFromPrevious();
		return true;
	}
	
	/**
	 * 
	 * @param memento
	 */
	public void saveState(T memento) {
		// Drop the previous diverging timeline
		if(hasNext()) {
			savedStates = savedStates.subList(0, currentState + 1);
		}
		savedStates.add(memento);
		currentState++;
		// Trigger newly added memento
		current().onAddToHistory(this);
	}
	
	@Override
	public String toString() {
		return "{History} " + Arrays.deepToString(savedStates.toArray());
	}
}
