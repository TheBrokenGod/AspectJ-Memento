package gamma;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JPanel;

import ch.jacopoc.memento.History;
import ch.jacopoc.memento.Memento;
import ch.jacopoc.memento.Originator;
import gamma.ConstraintSolver.Constraint;

public class DiagramEditor extends JPanel implements Originator {
	
	static private final long serialVersionUID = 1L;
	
	private final Map<String, Graphic> graphics;
	private final ConstraintSolver solver;
	
	public DiagramEditor() {
		graphics = new ConcurrentHashMap<>();
		solver = new ConstraintSolver();
		setOpaque(true);
		setBackground(Color.WHITE);
	}
	
	abstract class Command extends Memento {
		
		@Override
		protected void onAddToHistory(History history) {
			onEnterFromPrevious();
		}
		
		@Override
		protected void onExitToPrevious() {
			unexecute();
			solver.solve();
			repaint();
		}
		
		@Override
		protected void onEnterFromPrevious() {
			execute();
			solver.solve();
			repaint();
		}
		
		abstract protected void execute();
		abstract protected void unexecute();
		
		@Override
		public String toString() {
			return getClass().getSimpleName();
		}
	}
	
	private class EmptyCommand extends Command {
		@Override
		protected void execute() {
		}
		@Override
		protected void unexecute() {	
		}
	}
	
	public Command emptyCommand() {
		return new EmptyCommand();
	}
	
	@Override
	public Command createMemento(Object... args) {
		return emptyCommand();
	}

	private class AddGraphic extends Command {
		
		final Graphic graphic;
		
		AddGraphic(Graphic graphic) {
			this.graphic = graphic;
		}
		
		@Override
		protected void execute() {
			graphics.put(graphic.name, graphic);
		}

		@Override
		protected void unexecute() {
			graphics.remove(graphic.name);
		}
	}
	
	public Command addGraphic(String name, int posX, int posY, int radius) {
		return new AddGraphic(new Graphic(name, new Point(posX, posY), radius));
	}
	
	public Graphic getGraphic(String name) {
		return graphics.get(name);
	}
	
	private class MoveGraphic extends Command {
		
		final Graphic graphic;
		final Point delta;

		public MoveGraphic(Graphic graphic, Point delta) {
			this.graphic = graphic;
			this.delta = delta;
		}

		@Override
		protected void execute() {
			graphic.move(delta);
			solver.recomputeConstraints(graphic);
		}

		@Override
		protected void unexecute() {
			graphic.move(new Point(-delta.x, -delta.y));
			solver.recomputeConstraints(graphic);
		}	
	}
	
	public Command moveGraphic(String name, int dx, int dy) {
		return new MoveGraphic(graphics.get(name), new Point(dx, dy));
	}
	
	private class AddConstraint extends Command {
		
		final Graphic startConnection;
		final Graphic endConnection;
		
		AddConstraint(Graphic startConnection, Graphic endConnection) {
			this.startConnection = startConnection;
			this.endConnection = endConnection;
		}
		
		@Override
		protected void execute() {
			solver.addConstraint(startConnection, endConnection);
		}

		@Override
		protected void unexecute() {
			solver.removeConstraint(startConnection, endConnection);
		}
	}
	
	public Command addConstraint(String startConnection, String endConnection) {
		return new AddConstraint(getGraphic(startConnection), getGraphic(endConnection));
	}
		
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(new Color(0, 100, 0));
		g2d.setStroke(new BasicStroke(2));
		// Draw blocks
		for(Graphic graphic : graphics.values()) {
			g2d.drawOval(graphic.position.x, graphic.position.y, graphic.d(), graphic.d());
			g2d.drawString(graphic.name, graphic.position.x, graphic.position.y);	
		}
		// Draw connections
		for(Constraint con : solver.constraints) {
			g2d.drawLine(con.solution[0].x, con.solution[0].y, con.solution[1].x, con.solution[1].y);
		}
	}
}