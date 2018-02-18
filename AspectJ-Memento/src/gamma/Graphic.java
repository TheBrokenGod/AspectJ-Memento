package gamma;

public class Graphic {
	
	static public class Point {
		
		public final int x;
		public final int y;
		
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		public Point add(Point p) {
			return new Point(x + p.x, y + p.y);
		}
		
		public Point minus() {
			return new Point(-x, -y);
		}
		
		@Override
		public String toString() {
			return "[x=" + x + ",y=" + y + "]";
		}
	}

	private static int IdFactory = 0;
	private final int id;
	private Point position;
	
	public Graphic(Point position) {
		this.id = IdFactory++;
		this.position = position;
	}
	
	public Graphic(int posX, int posY) {
		this(new Point(posX, posY));
	}
	
	void move(Point delta) {
		position = position.add(delta);
		System.out.print("Moving " + getName() + " of " + delta + " => ");
	}
	
	public Point getPosition() {
		return position;
	}
	
	public String getName() {
		return "{Graphic:" + id + "}";
	}
	
	@Override
	public String toString() {
		return getName() + " is at " + position.toString(); 
	}
}