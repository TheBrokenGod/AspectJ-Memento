package main;

import javax.swing.BorderFactory;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentEvent.EventType;
import javax.swing.event.DocumentListener;

import ch.jacopoc.memento.Memento;
import ch.jacopoc.memento.Originator;

class TextEditor extends JTextArea implements Originator<TextEditor.TextEvent> {

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
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
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
			str = new StringBuilder(getText());
			// Remove inserted character
			if(type == EventType.INSERT) {
				str.delete(offset, offset + delta.length());
			}
			// Undo character removal
			else if(type == EventType.REMOVE) {
				str.insert(offset, delta);
			}
			restore(this);
		}
		
		@Override
		protected void onEnterFromPrevious() {
			str = new StringBuilder(getText());
			if(type == EventType.INSERT) {
				// Re-insert character
				str.insert(offset, delta);
			}
			else if(type == EventType.REMOVE) {
				// Perform remove again
				str.delete(offset, offset + delta.length());
			}
			restore(this);
		}
		
		@Override
		public String toString() {
			return type != null ? ((type == EventType.INSERT ? "+" : "-") + delta.replaceAll("\n", "\\\\n")).replaceAll(" ", "\\\\s") : "0";
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
	public void restore(TextEvent memento) {
		getDocument().removeDocumentListener(listener);
		setText(memento.str.toString());
		memento.str = null;
		getDocument().addDocumentListener(listener);
	}
}