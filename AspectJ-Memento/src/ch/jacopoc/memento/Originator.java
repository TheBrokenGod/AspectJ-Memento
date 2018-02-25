package ch.jacopoc.memento;

/**
 * 
 * @author Jacopo
 *
 */
public interface Originator<T extends Memento<T>> {	
	/**
	 * 
	 * @return
	 */
	public T createMemento(Object... args);
	
	/**
	 * 
	 * @param memento
	 */
	default public void restore(T memento) {
		throw new UnsupportedOperationException();
	}
}
