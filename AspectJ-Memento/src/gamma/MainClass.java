package gamma;

public class MainClass {

	public static void main(String[] args) {
		Graphic g = new Graphic(20, 20);
		System.out.println(g);
		MoveCommand c1 = new MoveCommand(g, new Graphic.Point(5, -5));
		c1.execute();
		System.out.println(g);
		MoveCommand c2 = new MoveCommand(g, new Graphic.Point(10, 10));
		c2.execute();
		System.out.println(g);
		c2.unexecute();
		System.out.println(g);
		c1.unexecute();
		System.out.println(g);
	}
}
