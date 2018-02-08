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

	private List<Memento> savedStates;
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
	public Caretaker(Memento initialState) {
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
	public Memento previous() {
		if(hasPrevious()) {
			// Rollback to the previous
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
	public Memento next() {
		if(hasNext()) {
			// Return to the next
			return savedStates.get(++currentState);
		}
		throw new NoSuchElementException();
	}
	
	/**
	 * 
	 * @return
	 */
	public Memento current() {
		if(size() > 0) {
			return savedStates.get(currentState);
		}
		throw new NoSuchElementException();
	}
	
	/**
	 * 
	 * @param memento
	 */
	public void saveState(Memento memento) {
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
