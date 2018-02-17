package ch.jacopoc.memento;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * 
 * @author Jacopo Carravieri
 *
 */
public class Caretaker {

	private List<AbstractMemento> savedStates;
	private int currentState;
	
	/**
	 * 
	 */
	public Caretaker() {
		savedStates = new ArrayList<>();
		currentState = -1;
	}
	
	/**
	 * 
	 * @param initialState
	 */
	public Caretaker(AbstractMemento initialState) {
		this();
		saveState(initialState);
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
	public AbstractMemento previous() {
		if(hasPrevious()) {
			// Rollback 
			return savedStates.get(--currentState);
		}
		throw new NoSuchElementException();
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
	public AbstractMemento next() {
		if(hasNext()) {
			// Restore
			return savedStates.get(++currentState);
		}
		throw new NoSuchElementException();
	}
	
	/**
	 * 
	 * @return
	 */
	public AbstractMemento current() {
		if(size() > 0) {
			// Current cursor position
			return savedStates.get(currentState);
		}
		throw new NoSuchElementException();
	}
	
	/**
	 * 
	 * @param memento
	 */
	public void saveState(AbstractMemento memento) {
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
		return Arrays.deepToString(savedStates.toArray());
	}
}
