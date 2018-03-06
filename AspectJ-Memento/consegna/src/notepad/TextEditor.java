package notepad;

import java.awt.Font;

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
	 * @param listener
	 * @param initialContent
	 */
	public TextEditor(DocumentListener listener, String initialContent) {
		super(initialContent);
		this.listener = listener;
		getDocument().addDocumentListener(listener);
		prevState = "";
		setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
		setTabSize(4);
	}
	
	// Holds the text delta between states
	public class TextEvent extends Memento {

		private final EventType type;
		private final int offset;
		private final String delta;
		private StringBuilder str;

		TextEvent(String initialContent) {
			type = EventType.INSERT;
			offset = 0;
			delta = initialContent;
		}
		
		TextEvent(DocumentEvent e) {
			type = e.getType();
			offset = e.getOffset();
			delta = (type == EventType.REMOVE ? prevState : getText()).substring(offset, offset + e.getLength());
		}

		@Override
		protected void onExitToPrevious() {
			str = new StringBuilder(getText());
			// Undo text insertion
			if(type == EventType.INSERT) {
				str.delete(offset, offset + delta.length());
			}
			// Undo text removal
			else if(type == EventType.REMOVE) {
				str.insert(offset, delta);
			}
			restore(this);
		}
		
		@Override
		protected void onEnterFromPrevious() {
			str = new StringBuilder(getText());
			// Redo text insertion
			if(type == EventType.INSERT) {
				str.insert(offset, delta);
			}
			// Redo text removal
			else if(type == EventType.REMOVE) {
				str.delete(offset, offset + delta.length());
			}
			restore(this);
		}
		
		private String getString() {
			try {
				return str.toString();
			}
			finally {
				str = null;
			}
		}
		
		@Override
		public String toString() {
			return (type == EventType.INSERT ? "+" : "-") + "'" + delta.replaceAll("\n", "<br/>").replaceAll(" ", "&nbsp").replaceAll("\t", "&#9;") + "'";
		}
	}

	@Override
	public TextEvent createMemento(Object... args) {
		try {
			return args[0] instanceof DocumentEvent ? new TextEvent((DocumentEvent)args[0]) : new TextEvent(args.length > 1 ? (String)args[1] : "");
		}
		finally {
			prevState = getText();
		}
	}

	@Override
	public void restore(TextEvent memento) {
		getDocument().removeDocumentListener(listener);
		setText(memento.getString());
		getDocument().addDocumentListener(listener);
	}
}