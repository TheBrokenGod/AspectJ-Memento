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
	default public T createMemento(Object... args) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * 
	 * @param memento
	 */
	default public void restore(T memento) {
		throw new UnsupportedOperationException();
	}
}
