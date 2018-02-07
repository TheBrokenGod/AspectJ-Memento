package ch.jacopoc.memento;

public class Memento implements Comparable<Memento> {

	private final Object state;
	
	public Memento(Object state) {
		this.state = state;
	}
	
	public Object getSavedState() {
		return state;
	}

	@Override
	public int compareTo(Memento o) {
		return state.toString().compareTo(o.toString());
	}
	
	@Override
	public String toString() {
		return state.toString();
	}
}
