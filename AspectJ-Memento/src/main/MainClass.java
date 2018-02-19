package main;

import ch.jacopoc.memento.History;
import gamma.Command;
import gamma.Graphic;
import orig.SwingNotepad;

public class MainClass {
	
	private static void gamma() {
		History<Command> history = new History<>(Command.nop());
		System.out.println(history);
		Graphic g1 = new Graphic(20, 20);
		Graphic g2 = new Graphic(0, 0);
		history.saveState(Command.move(g1, new Graphic.Point(3, 7)));
		history.saveState(Command.move(g1, new Graphic.Point(4, 5)));
		history.saveState(Command.addConstraint(g1, g2));
		System.out.println(history);
		Command cmd = history.current();
		while(cmd != null) {
			cmd.restore(null);
			cmd = history.hasPrevious() ? history.previous() : null;
		}
		System.out.println(history);
		history.saveState(Command.addConstraint(g1, g2));
		System.out.println(history);
	}
	
	private static void original() {
		new SwingNotepad();
	}

	public static void main(String[] args) {
		gamma();
		System.out.println();
		original();
	}
}
