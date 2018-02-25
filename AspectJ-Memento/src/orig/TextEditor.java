package orig;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentEvent.EventType;

import ch.jacopoc.memento.History;
import ch.jacopoc.memento.Memento;
import ch.jacopoc.memento.Originator;

class TextEditor extends JTextArea implements Originator<TextEditor.TextEvent> {

	private static final long serialVersionUID = 1L;
	private NotepadApp app;
	private String prevState;
	
	/**
	 * 
	 * @param rows
	 * @param cols
	 * @param listener
	 */
	public TextEditor() {
		super();
		app = null;
		prevState = "";
	}
	
	void setApp(NotepadApp app) {
		this.app = app;
		getDocument().addDocumentListener(app);		
	}
	
	/**
	 * 
	 *
	 */
	public class TextEvent extends Memento<TextEvent> {

		private final EventType type;
		private String delta;
		private int offset;
		private StringBuilder str;
		
		private TextEvent(DocumentEvent e) {
			if(e == null) {
				type = null;
				str = new StringBuilder();
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
			str = new StringBuilder(delta);
		}
		
		@Override
		protected void onAddToHistory(History<TextEvent> history) {
			onEnter();
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
		protected void onEnter() {
			System.out.println(app.getHistory());
			app.displayTime();
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
		public String toString() {
			if(str.length() == 0) {
				return "0";
			}
			return (type == EventType.INSERT ? "+" : "-") + delta.replaceAll("\n", "\\\\n") + (this == app.getHistory().current() ? "<>" : ""); 
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
		getDocument().removeDocumentListener(app);
		setText(memento.str.toString());
		getDocument().addDocumentListener(app);
	}
}