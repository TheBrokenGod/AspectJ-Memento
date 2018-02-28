package main;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class NotepadApp extends MementoApp<TextEditor> implements DocumentListener {
	
	private static final long serialVersionUID = 1L;
	
	protected NotepadApp() {
		initGUI("Notepad", new TextEditor(this));
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
		editor.createMemento(e);
	}
}