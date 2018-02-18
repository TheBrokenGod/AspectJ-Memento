package gamma;

import ch.jacopoc.memento.IMemento;
import ch.jacopoc.memento.Originator;

class ConstraintSolver implements Originator<ConstraintSolver.InternalState> {

	/**
	 * Nesting class as a replacement of C++ friend statement
	 */
	static class InternalState implements IMemento {
		
		private final long state;
		
		private InternalState(long state) {
			this.state = state;
		}
	}
	
	static private final ConstraintSolver instance = new ConstraintSolver();
	
	private long internalStatePlaceholder;
	
	private ConstraintSolver() {
		internalStatePlaceholder = 0;
	}
	
	public static ConstraintSolver getInstance() {
		return instance;
	}
	
	void solve() {
		System.out.println("Solving " + internalStatePlaceholder);
	}
	
	void addConstraint(Graphic startConnection, Graphic endConnection) {
		System.out.print("Connecting " + startConnection.getName() + " and " + endConnection.getName() + " => ");
	}
	
	void removeConstraint(Graphic startConnection, Graphic endConnection) {
		System.out.print("Disconnecting " + startConnection.getName() + " and " + endConnection.getName() + " => ");
	}
	
	@Override
	public InternalState createMemento() {
		System.out.print("Saving " + internalStatePlaceholder + " => ");
		return new InternalState(internalStatePlaceholder++);
	}
	
	@Override
	public void restore(InternalState memento) {
		internalStatePlaceholder = memento.state;
		System.out.print("Restoring " + internalStatePlaceholder + " => ");
	}
}
