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
			return "[" + x + ";" + y + "]";
		}
	}

	private Point position;
	
	public Graphic(Point position) {
		this.position = position;
	}
	
	public Graphic(int posX, int posY) {
		this.position = new Point(posX, posY);
	}
	
	void move(Point delta) {
		position = position.add(delta);
	}
	
	public Point getPosition() {
		return position;
	}
	
	@Override
	public String toString() {
		return "{Graphic:" + hashCode() + "} at:" + position.toString(); 
	}
}
