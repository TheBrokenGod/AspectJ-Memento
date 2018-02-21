package orig;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import ch.jacopoc.memento.Originator;

class TextEditor extends JTextArea implements Originator<EditorContent> {

	private static final long serialVersionUID = 1L;
	private final DocumentListener listener;

	/**
	 * 
	 * @param rows
	 * @param cols
	 * @param listener
	 */
	public TextEditor(int rows, int cols, DocumentListener listener) {
		super(rows, cols);
		this.listener = listener;
		getDocument().addDocumentListener(listener);
	}

	@Override
	public EditorContent createMemento(Object... args) {
		return new EditorContent(getText());
	}

	public EditorEvent createMemento2(Object... args) {
		return new EditorEvent((SwingNotepad)args[0], (DocumentEvent)args[1]);
	}

	@Override
	public void restore(EditorContent memento) {
		getDocument().removeDocumentListener(listener);
		setText(memento.state);
		getDocument().addDocumentListener(listener);
	}
}
