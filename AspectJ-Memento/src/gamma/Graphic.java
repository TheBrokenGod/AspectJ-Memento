package gamma;

import java.awt.Point;

public class Graphic {

	final String name;
	Point position;
	int radius;
	
	public Graphic(String name, Point position, int radius) {
		this.name = name;
		this.position = position;
		this.radius = radius;
	}

	void move(Point delta) {
		position = new Point(position.x + delta.x, position.y + delta.y);
	}
	
	int d() {
		return 2 * radius;
	}
	
	@Override
	public String toString() {
		return "{Graphic:" + name + "}" + " at " + position.toString(); 
	}
}