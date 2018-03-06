package ch.jacopoc.memento;

/**
 * The Caretaker creates and manipulates Originator object(s).
 */
public interface Caretaker {
	
	/**
	 * In a multi-originator environment activates the specified object and the associated history.
	 * 
	 * There is no need to call this method if there is a single originator: the last built originator automatically becomes active.
	 * 
	 * An originator must be active when a Memento is created.
	 * 
	 * @param orig The originator to activate
	 */
	default void activate(Originator<?> orig) {
	}
	
	/**
	 * Returns the history associated to the active Originator.
	 * 
	 * @return Active history
	 */
	default History history() {
		return null;
	}
}