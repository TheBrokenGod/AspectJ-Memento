package ch.jacopoc.memento;

/**
 * The Originator saves and restores its state from Memento objects.
 */
public interface Originator<T extends Memento> {	
	/**
	 * Creates a Memento object.
	 * 
	 * This method is invoked at least once when the Originator instance is constructed, and gets passed the same arguments in the same order. It is then important that its implementation is valid.
	 * 
	 * @param args The arguments for memento creation. When invoked internally, the arguments used in construction get passed here.
	 * 
	 * @return The memento object. 
	 */
	public T createMemento(Object... args);
	
	/**
	 * Restores the given state. 
	 * 
	 * Depending on the application, it is not always possible to restore a state by simply calling this method. This method has then a default, empty implementation.
	 * 
	 * @param memento The given state
	 */
	default public void restore(T memento) {
	}
	
	/**
	 * Do not call nor override. use Caretaker.history() instead.
	 * 
	 * @return Private data
	 */
	default public History history() {
		return null;
	}
}