package ch.jacopoc.memento;

public interface Caretaker {
	
	static final String ERROR = "Type of 'this' is not Caretaker";
	
	/**
	 * 
	 * @param orig
	 */
	default void activate(Originator<?> orig) {
		throw new IllegalAccessError(ERROR);
	}
	
	/**
	 * 
	 * @return
	 */
	default History history() {
		throw new IllegalAccessError(ERROR);
	}
	
	/**
	 * 
	 * @param orig
	 */
	default void dispose(Originator<?> orig) {
		throw new IllegalAccessError(ERROR);		
	}
}