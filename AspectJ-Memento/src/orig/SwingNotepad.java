package orig;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import ch.jacopoc.memento.History;

public class SwingNotepad extends JFrame implements DocumentListener {

	private static final long serialVersionUID = 1L;
	private static final int BORDER = 5;
	private static final String TITLE = "Memento Notepad";

	private final JPanel toolbar;
	private final JButton undo;
	private final JButton redo;
	private final JLabel time;
	private final TextEditor editor;
	private final History<EditorContent> history;
	
	public SwingNotepad() {
		// Init editor's Swing GUI
		setLayout(new BorderLayout());
		toolbar = new JPanel(new FlowLayout());
		undo = new JButton("Undo (CTRL+Z)");
		redo = new JButton("Redo (CTRL+Y)");
		time = new JLabel();
		toolbar.add(undo);
		toolbar.add(redo);
		toolbar.add(time);
		editor = new TextEditor(25, 60, this);
		editor.setBorder(BorderFactory.createEmptyBorder(BORDER, BORDER, BORDER, BORDER));
		add(toolbar, BorderLayout.NORTH);
		add(new JScrollPane(editor), BorderLayout.CENTER);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle(TITLE);
		pack();
		setVisible(true);
		// Init editor's memento logic
		undo.addActionListener(e -> undo());
		redo.addActionListener(e -> redo());
		history = new History<>(editor.createMemento());
		history.onEnter = editor::restore;
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		System.err.println(e.getOffset());
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		saveMemento(new EditorEvent(editor, EditorEvent.INSERTION, e.getOffset(), e.getLength()));
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		saveMemento(new EditorEvent(editor, EditorEvent.REMOVAL, e.getOffset(), e.getLength()));
	}
	
	private void saveMemento(EditorEvent editorEvent) {
		history.saveState(editor.createMemento());
		displayTime();
		System.out.println(history);
	}
	
	public void undo() {
		history.moveBack();
		displayTime();
	}
	
	public void redo() {
		history.moveForward();
		displayTime();
	}
	
	public void displayTime() {
		time.setText(new Date(history.current().created).toString());
	}
}
