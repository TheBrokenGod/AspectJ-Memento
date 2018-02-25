package orig;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import ch.jacopoc.memento.MementoApp;

public class NotepadApp extends MementoApp<TextEditor, TextEditor.TextEvent> implements DocumentListener {
	
	private static final long serialVersionUID = 1L;
	
	public NotepadApp() {
		super("Java Swing Text Editor", new TextEditor());
		editor.setApp(this);
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		changedUpdate(e);
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		changedUpdate(e);
	}
	
	@Override
	public void changedUpdate(DocumentEvent e) {
		history.saveState(editor.createMemento(e));
		updateButtons();
	}
}