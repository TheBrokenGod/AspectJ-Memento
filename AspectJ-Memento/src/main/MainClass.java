package main;

import ch.jacopoc.memento.Caretaker;
import gamma.Command;
import gamma.Graphic;
import orig.SwingNotepad;

public class MainClass {
	
	private static void gamma() {
		Caretaker<Command> caretaker = new Caretaker<>(Command.nop());
		System.out.println(caretaker);
		Graphic g1 = new Graphic(20, 20);
		Graphic g2 = new Graphic(0, 0);
		caretaker.saveState(Command.move(g1, new Graphic.Point(3, 7)));
		caretaker.saveState(Command.move(g1, new Graphic.Point(4, 5)));
		caretaker.saveState(Command.addConstraint(g1, g2));
		System.out.println(caretaker);
		Command cmd = caretaker.current();
		while(cmd != null) {
			cmd.restore(null);
			cmd = caretaker.previous();
		}
		System.out.println(caretaker);
		caretaker.saveState(Command.addConstraint(g1, g2));
		System.out.println(caretaker);
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
