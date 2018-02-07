package main;

import javax.swing.JTextArea;

import ch.jacopoc.memento.Memento;
import ch.jacopoc.memento.Originator;

class TextArea extends JTextArea implements Originator {

	private static final long serialVersionUID = 1L;

	public TextArea(int rows, int cols) {
		super(rows, cols);
	}
	
	@Override
	public void setState(Object state) {
		setText(state.toString());
	}

	@Override
	public Memento createMemento() {
		return new Memento(getText());
	}

	@Override
	public void restore(Memento memento) {
		setText(memento.getSavedState().toString());
	}
}
