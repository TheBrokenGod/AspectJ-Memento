package orig;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import ch.jacopoc.memento.History;

public class NotepadApp extends JFrame implements DocumentListener {
	
	private static final long serialVersionUID = 1L;
	private static final int BORDER = 5;
	private static final String TITLE = "Memento Notepad";
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss:SSS");
	
	private final JPanel toolbar;
	private final JButton undo;
	private final JButton redo;
	private final JLabel time;
	final History<TextEditor.TextEvent> history;
	final TextEditor editor;
	
	public NotepadApp() {
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
		// Init memento logic
		history = new History<>(editor.createMemento());
		undo.addActionListener(e -> history.moveBack());
		redo.addActionListener(e -> history.moveForward());
		displayTime();
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
	}
	
	void displayTime() {
		time.setText(FORMATTER.format(history.current().created));
	}
}