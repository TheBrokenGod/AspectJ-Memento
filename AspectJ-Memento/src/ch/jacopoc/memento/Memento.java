package ch.jacopoc.memento;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the state of an Originator at a given time.
 */
abstract public class Memento {
	
	Memento previous;
	List<Memento> next;
	
	/**
	 * Constructor.
	 */
	protected Memento() {
		previous = null;
		next = new ArrayList<>();
	}
	
	/**
	 * Creation time.
	 */
	public final LocalDateTime created = LocalDateTime.now();
	
	/**
	 * Called when the new object is added to History.
	 */
	protected void onAddToHistory() {
	}
	
	/**
	 * Called when the cursor enters the state, except when first added to history.
	 */
	protected void onEnter() {
	}
	
	/**
	 * Called when the cursor enters the state and comes from a less recent one.
	 */
	protected void onEnterFromPrevious() {
	}
	
	/**
	 * Called when the cursor enters the state and comes from a more recent one.
	 */
	protected void onEnterFromNext() {
	}
	
	/**
	 * Called when the cursor leaves the state.
	 */
	protected void onExit() {
	}
	
	/**
	 * Called when the cursor leaves the state and is moving back to a previous one.
	 */
	protected void onExitToPrevious() {
	}
	
	/**
	 * Called when the cursor leaves the state and is moving forward to a more recent one.
	 */
	protected void onExitToNext() {
	}
	
	
}
