package ch.jacopoc.memento;

/**
 * 
 * @author Jacopo
 *
 */
public interface Originator {
	/**
	 * 
	 * @param state
	 */
	public void setState(Object state);
	
	/**
	 * 
	 * @return
	 */
	public Memento createMemento();
	
	/**
	 * 
	 * @param memento
	 */
	public void restore(Memento memento);
}
