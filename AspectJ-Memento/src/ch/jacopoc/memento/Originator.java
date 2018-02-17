package ch.jacopoc.memento;

/**
 * 
 * @author Jacopo
 *
 */
public interface Originator<T> {
	/**
	 * 
	 * @param state
	 */
	public void setState(T state);
	
	/**
	 * 
	 * @return
	 */
	public AbstractMemento createMemento();
	
	/**
	 * 
	 * @param memento
	 */
	public void restore(AbstractMemento memento);
}
