package gamma;

import ch.jacopoc.memento.Memento;
import ch.jacopoc.memento.Originator;

abstract public class Command implements Memento, Originator<Command> {

	private ConstraintSolver.InternalState state;
	
	private Command() {
		state = null;
	}
	
	protected Command execute() {
		ConstraintSolver solver = ConstraintSolver.getInstance();
		state = solver.createMemento();
		doExecute();
		solver.solve();
		return this;
	}
	
	abstract protected void doExecute();
	
	protected void unexecute() {
		System.out.print("undo: ");
		ConstraintSolver solver = ConstraintSolver.getInstance();
		doUnexecute();
		solver.restore(state);
		solver.solve();
	}
	
	abstract protected void doUnexecute();

	@Override
	public Command createMemento(Object... args) {
		return this;
	}

	@Override
	public void restore(Command memento) {
		unexecute();
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName();
	}
	
	static public Command nop() {
		return new Nop();
	}
	
	static public Command move(Graphic target, Graphic.Point delta) {
		return new Move(target, delta).execute();
	}
	
	static public Command addConstraint(Graphic startConnection, Graphic endConnection) {
		return new AddConstraint(startConnection, endConnection).execute();
	}

	static public Command removeConstraint(Graphic startConnection, Graphic endConnection) {
		return new RemoveConstraint(startConnection, endConnection).execute();
	}
	
	static private class Nop extends Command {

		@Override
		protected Command execute() {
			return this;
		}
		
		@Override
		protected void doExecute() {	
		}
		
		@Override
		protected void unexecute() {
		}

		@Override
		protected void doUnexecute() {
		}		
	}
	
	static private class Move extends Command {
		
		private final Graphic target;
		private final Graphic.Point delta;
		
		private Move(Graphic target, Graphic.Point delta) {
			this.target = target;
			this.delta = delta;
		}
		
		@Override
		protected void doExecute() {
			target.move(delta);
		}
		
		@Override
		protected void doUnexecute() {
			target.move(delta.minus());
		}
	}
	
	static abstract private class ConstraintCommand extends Command {
		
		protected final Graphic startConnection;
		protected final Graphic endConnection;
		
		protected ConstraintCommand(Graphic startConnection, Graphic endConnection) {
			this.startConnection = startConnection;
			this.endConnection = endConnection;
		}		
	}
	
	static private class AddConstraint extends ConstraintCommand {

		private AddConstraint(Graphic startConnection, Graphic endConnection) {
			super(startConnection, endConnection);
		}

		@Override
		protected void doExecute() {
			ConstraintSolver.getInstance().addConstraint(startConnection, endConnection);
		}

		@Override
		protected void doUnexecute() {
			ConstraintSolver.getInstance().removeConstraint(startConnection, endConnection);
		}
	}
	
	static private class RemoveConstraint extends ConstraintCommand {
		
		private RemoveConstraint(Graphic startConnection, Graphic endConnection) {
			super(startConnection, endConnection);
		}

		@Override
		protected void doExecute() {
			ConstraintSolver.getInstance().removeConstraint(startConnection, endConnection);
		}

		@Override
		protected void doUnexecute() {
			ConstraintSolver.getInstance().addConstraint(startConnection, endConnection);
		}
	}
}