package ch.jacopoc.memento;

abstract public class AbstractMemento {

	/**
	 * 
	 */
	public final long created;	
	/**
	 * 
	 */
	protected AbstractMemento() {
		this.created = System.currentTimeMillis();
	}
}
