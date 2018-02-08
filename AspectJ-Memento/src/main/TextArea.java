package main;

import javax.swing.JTextArea;
import javax.swing.event.DocumentListener;

import ch.jacopoc.memento.Memento;
import ch.jacopoc.memento.Originator;

class TextArea extends JTextArea implements Originator {

	private static final long serialVersionUID = 1L;
	private final DocumentListener listener;

	/**
	 * 
	 * @param rows
	 * @param cols
	 * @param listener
	 */
	public TextArea(int rows, int cols, DocumentListener listener) {
		super(rows, cols);
		this.listener = listener;
		getDocument().addDocumentListener(listener);
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
		getDocument().removeDocumentListener(listener);
		setText(memento.state.toString());
		getDocument().addDocumentListener(listener);
	}
}
