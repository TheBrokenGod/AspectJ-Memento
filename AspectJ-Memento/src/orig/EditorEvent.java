package orig;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentEvent.EventType;

import ch.jacopoc.memento.Memento;

public class EditorEvent implements Memento {

	private final EventType type;
	private final String delta;
	private final int offset;
	private StringBuilder str;
	
	public EditorEvent(SwingNotepad notepad, DocumentEvent e) {
		if(e == null) {
			type = null;
			delta = null;
			offset = -1;
			return;
		}
		else {
			offset = e.getOffset();
			if(e.getType() == EventType.INSERT) {
				type = EventType.INSERT;
				delta = notepad.editor.getText().substring(e.getOffset(), e.getOffset() + e.getLength());
			}
			else if(e.getType() == EventType.REMOVE) {
				type = EventType.REMOVE;
				delta = notepad.prevState.substring(e.getOffset(), e.getOffset() + e.getLength());
			}
			else {
				type = EventType.CHANGE;
				delta = null;
			}
		}
		System.out.println(delta + " at " + offset);
	}
	
	public void undo(SwingNotepad notepad) {
		if(type == null) {
			return;
		}
		else if(type == EventType.INSERT) {
			str = new StringBuilder(notepad.editor.getText());
			str.delete(offset, delta.length());
		}
		else if(type == EventType.REMOVE) {
		}
		System.out.println(this);
	}

	public void redo(SwingNotepad notepad) {
		if(type == null) {
			return;
		}
		else if(type == EventType.INSERT) {
			str = new StringBuilder(notepad.editor.getText());
			str.insert(offset, delta);
			// Insert characters at offset
		}
		else if(type == EventType.REMOVE) {
			// Store from SwingNotepad.PrevState
		}
	}
	
	@Override
	public String toString() {
		return str.toString();
	}
}
