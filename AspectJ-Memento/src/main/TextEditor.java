package main;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentEvent.EventType;
import javax.swing.event.DocumentListener;

import ch.jacopoc.memento.Memento;
import ch.jacopoc.memento.Originator;

class TextEditor extends JTextArea implements Originator {

	private static final long serialVersionUID = 1L;
	private final DocumentListener listener;
	private String prevState;
	
	/**
	 * 
	 * @param rows
	 * @param cols
	 * @param listener
	 */
	public TextEditor(DocumentListener listener) {
		this.listener = listener;	
		getDocument().addDocumentListener(listener);
		prevState = "";
	}
	
	/**
	 * 
	 *
	 */
	public class TextEvent extends Memento {

		private final EventType type;
		private String delta;
		private int offset;
		private StringBuilder str;
		
		private TextEvent(DocumentEvent e) {
			if(e == null) {
				type = null;
				return;
			}
			type = e.getType();
			offset = e.getOffset();
			if(type == EventType.INSERT) {
				delta = getText().substring(e.getOffset(), e.getOffset() + e.getLength());
			}
			else if(type == EventType.REMOVE) {
				delta = prevState.substring(e.getOffset(), e.getOffset() + e.getLength());
			}
		}
		
		@Override
		protected void onExitToPrevious() {
			// Remove inserted character
			if(type == EventType.INSERT) {
				str = new StringBuilder(getText());
				str.delete(offset, offset + delta.length());
			}
			// Undo character removal
			else if(type == EventType.REMOVE) {
			}
			else {
				return;
			}
			restore(this);
		}
		
		@Override
		protected void onEnterFromPrevious() {
			// Re-insert character
			if(type == EventType.INSERT) {
				str = new StringBuilder(getText());
				str.insert(offset, delta);
			}
			// Perform remove again
			else if(type == EventType.REMOVE) {
			}
			else {
				return;
			}
			restore(this);
		}
		
		@Override
		public String repr() {
			return type != null ? ((type == EventType.INSERT ? "+" : "-") + delta.replaceAll("\n", "\\\\n")) : "0";
		}
		
		@Override
		public String toString() {
			return type != null ? str.toString() : "";
		}
	}

	@Override
	public TextEvent createMemento(Object... args) {
		try {
			return new TextEvent((DocumentEvent)args[0]);
		}
		catch(ArrayIndexOutOfBoundsException e) {
			return new TextEvent(null);
		}
		finally {
			prevState = getText();
		}
	}

	@Override
	public void restore(Memento memento) {
		getDocument().removeDocumentListener(listener);
		setText(memento.toString());
		getDocument().addDocumentListener(listener);
	}
}