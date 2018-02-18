package ch.jacopoc.memento;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * @author Jacopo Carravieri
 *
 */
public class Caretaker<T extends IMemento> {

	private List<T> savedStates;
	private int currentState;
	
	/**
	 * 
	 * @param emptyState
	 */
	public Caretaker(T emptyState) {
		savedStates = new ArrayList<>();
		currentState = -1;
		saveState(emptyState);
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
	public boolean isEmpty() {
		return savedStates.isEmpty();
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean hasPrevious() {
		// If not at first state
		return currentState > 0;
	}
	
	/**
	 * 
	 * @return
	 */
	public T previous() {
		if(!hasPrevious()) {
			return null;
		}
		// Rollback 
		return savedStates.get(--currentState);
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean hasNext() {
		// If not at most recent state
		return currentState < savedStates.size() - 1;
	}
	
	/**
	 * 
	 * @return
	 */
	public T next() {
		if(!hasNext()) {
			return null;
		}
		// Restore
		return savedStates.get(++currentState);
	}
	
	/**
	 * 
	 * @return
	 */
	public T current() {
		if(isEmpty()) {
			return null;
		}
		// Current cursor position
		return savedStates.get(currentState);
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
