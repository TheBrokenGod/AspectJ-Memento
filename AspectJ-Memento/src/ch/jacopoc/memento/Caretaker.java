package ch.jacopoc.memento;

/**
 * This interface represents a Caretaker which creates, handles and destroys Originator objects.
 * 
 * It should simply be implemented without overriding any method, because they are already backed by CaretakerAspect.
 */
public interface Caretaker {
	
	/**
	 * In a multi-originator environment (e.g. a multitab text editor) activate the specified originator object and its history.
	 * 
	 * An originator must be the active one when a memento is created, otherwise the behaviour is undefined. 
	 * 
	 * There is no need to call this method if there is a single originator: the last built originator object is automatically activated.
	 * 
	 * @param orig The originator to activate
	 */
	default void activate(Originator<?> orig) {
	}
	
	/**
	 * This is a private method, use Caretaker.history() instead.
	 * 
	 * @return
	 */
	default History history() {
		return null;
	}
}