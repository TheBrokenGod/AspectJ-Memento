package gamma;

import ch.jacopoc.memento.AbstractMemento;

public class ConstraintSolver {

	/**
	 * Nesting class as a replacement of C++ friend statement
	 */
	static public class Memento extends AbstractMemento {
		
		private final long state;
		
		private Memento(long state) {
			this.state = state;
		}
	}
	
	static private final ConstraintSolver instance = new ConstraintSolver();
	
	/**
	 * An index to identify the internal state
	 */
	private long internalStatePlaceholder;
	
	private ConstraintSolver() {
		internalStatePlaceholder = 0;
	}
	
	public static ConstraintSolver getInstance() {
		return instance;
	}
	
	public void solve() {
		System.out.println("Solving " + internalStatePlaceholder);
	}
	
	public void addConstraint(Graphic startConnection, Graphic endConnection) {
	}
	
	public void removeConstraint(Graphic startConnection, Graphic endConnection) {
	}
	
	public Memento createMemento() {
		return new Memento(internalStatePlaceholder++);
	}
	
	void setMemento(Memento memento) {
		internalStatePlaceholder = memento.state;
		System.out.println("Restoring " + memento.state);
	}
}
