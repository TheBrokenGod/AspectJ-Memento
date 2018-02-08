package ch.jacopoc.memento;

public class Memento {

	/**
	 * 
	 */
	public final long created;
	/**
	 * 
	 */
	public final Object state;
	
	/**
	 * 
	 * @param state
	 */
	public Memento(Object state) {
		this.created = System.currentTimeMillis();
		this.state = state;
	}
	
	@Override
	public String toString() {
		return state.toString();
	}
}
