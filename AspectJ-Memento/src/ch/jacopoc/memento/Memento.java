package ch.jacopoc.memento;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the state of an Originator at a given time and allows the undo/redo operations.
 * 
 * NOTE: it is often useful to declare the extending class nested within its originator.
 */
abstract public class Memento {
	
//	Memento previous = null;
//	List<Memento> next = new ArrayList<>();
	
	/**
	 * Creation time.
	 */
	public final LocalDateTime created = LocalDateTime.now();
	
	/**
	 * 
	 */
	protected void onAddToHistory() {
	}
	
	/**
	 * 
	 */
	protected void onEnter() {
	}
	
	/**
	 * 
	 */
	protected void onEnterFromPrevious() {
	}
	
	/**
	 * 
	 */
	protected void onEnterFromNext() {
	}
	
	/**
	 * 
	 */
	protected void onExit() {
	}
	
	/**
	 * 
	 */
	protected void onExitToPrevious() {
	}
	
	/**
	 * 
	 */
	protected void onExitToNext() {
	}
}
