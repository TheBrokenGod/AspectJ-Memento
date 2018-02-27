package ch.jacopoc.memento;

/**
 * 
 * @author Jacopo
 *
 */
public interface Originator {	
	/**
	 * 
	 * @return
	 */
	public Memento createMemento(Object... args);
	
	/**
	 * 
	 * @param memento
	 */
	default public void restore(Memento memento) {
	}
}
