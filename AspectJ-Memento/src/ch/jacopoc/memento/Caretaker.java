package ch.jacopoc.memento;

public interface Caretaker {
	
	/**
	 * 
	 * @param orig
	 */
	default void activate(Originator orig) {
	}
	
	/**
	 * 
	 * @return
	 */
	default History history() { 
		return null; 
	}
	
	/**
	 * 
	 */
	default void moveBack() {	
	}
	
	/**
	 * 
	 */
	default void moveForward() {
	}
}
