package ch.jacopoc.memento;

/**
 * 
 * @author Jacopo
 *
 */
public interface Originator<T extends Memento> {	
	/**
	 * 
	 * @return
	 */
	public T createMemento(Object... args);
	
	/**
	 * 
	 * @param memento
	 */
	public void restore(T memento);
}
