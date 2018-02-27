package main;

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

import ch.jacopoc.memento.Caretaker;

public class NotepadApp extends JFrame implements DocumentListener, Caretaker {
	
	private static final long serialVersionUID = 1L;
	private static final int BORDER = 5;
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss:S");
	
	private final JPanel toolbar;
	private final JButton undo;
	private final JButton redo;
	private final JLabel time;
	protected final TextEditor editor;
	
	protected NotepadApp() {
		this.editor = new TextEditor(this);
//		activate(editor);
		// GUI
		setLayout(new BorderLayout());
		toolbar = new JPanel(new FlowLayout());
		undo = new JButton("Undo (CTRL+Z)");
		redo = new JButton("Redo (CTRL+Y)");
		time = new JLabel();
		toolbar.add(undo);
		toolbar.add(redo);
		toolbar.add(time);
		add(toolbar, BorderLayout.NORTH);
		editor.setBorder(BorderFactory.createEmptyBorder(BORDER, BORDER, BORDER, BORDER));
		add(new JScrollPane(editor), BorderLayout.CENTER);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(800, 600);
		setVisible(true);
		// Memento
		undo.addActionListener((e) -> {moveBack(); updateButtons();});
		redo.addActionListener((e) -> {moveForward(); updateButtons();});
		displayTime();
		updateButtons();
		setTitle("Debug");
	}
	
	public void displayTime() {
		time.setText(FORMATTER.format(history().current().created));
	}
	
	protected void updateButtons() {
		undo.setEnabled(history().hasPrevious());
		redo.setEnabled(history().hasNext());
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
		updateButtons();
	}
}