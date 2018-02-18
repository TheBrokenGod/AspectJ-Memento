package ch.jacopoc.memento;

/**
 * 
 * @author Jacopo
 *
 */
public interface Originator<T extends IMemento> {	
	/**
	 * 
	 * @return
	 */
	public T createMemento();
	
	/**
	 * 
	 * @param memento
	 */
	public void restore(T memento);
}
