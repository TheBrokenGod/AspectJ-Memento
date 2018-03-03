package diagrams;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

class ConstraintSolver  {
	
	class Constraint {
		
		final Graphic startConnection;
		final Graphic endConnection;
		Point[] solution;
		
		Constraint(Graphic startConnection, Graphic endConnection) {
			this.startConnection = startConnection;
			this.endConnection = endConnection;
			this.solution = null;
		}
		
		@Override
		public boolean equals(Object obj) {
			return startConnection == ((Constraint)obj).startConnection && endConnection == ((Constraint)obj).endConnection;
		}
	}
	
	final List<Constraint> constraints = new ArrayList<>();
	
	void solve() {
		constraints.stream().filter(c -> c.solution == null).forEach(c -> {
			c.solution = new Point[] {
				new Point(c.startConnection.position.x + c.startConnection.radius, c.startConnection.position.y + c.startConnection.radius),
				new Point(c.endConnection.position.x + c.endConnection.radius, c.endConnection.position.y + c.endConnection.radius)
			};
		});
	}
	
	void addConstraint(Graphic startConnection, Graphic endConnection) {
		constraints.add(new Constraint(startConnection, endConnection));
	}
	
	void removeConstraint(Graphic startConnection, Graphic endConnection) {
		constraints.remove(new Constraint(startConnection, endConnection));
	}
	
	void recomputeConstraints(Graphic movedGraphic) {
		constraints.stream().filter(c -> c.startConnection == movedGraphic || c.endConnection == movedGraphic).forEach(c -> c.solution = null);
	}
}