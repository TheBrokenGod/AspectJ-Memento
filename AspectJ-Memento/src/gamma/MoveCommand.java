package gamma;

public class MoveCommand {
	
	private final Graphic target;
	private final Graphic.Point delta;
	private ConstraintSolver.Memento state;
	
	public MoveCommand(Graphic target, Graphic.Point delta) {
		this.target = target;
		this.delta = delta;
	}
	
	public void execute() {
		ConstraintSolver solver = ConstraintSolver.getInstance();
		state = solver.createMemento();
		target.move(delta);
		solver.solve();
	}
	
	public void unexecute() {
		ConstraintSolver solver = ConstraintSolver.getInstance();
		target.move(delta.minus());
		solver.setMemento(state);
		solver.solve();
	}
}
