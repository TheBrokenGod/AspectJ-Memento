package main;

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

import ch.jacopoc.memento.Caretaker;

public class SwingNotepad extends JFrame implements DocumentListener {

	private static final long serialVersionUID = 1L;
	private static final int BORDER = 5;
	private static final String TITLE = "Memento Notepad";

	private final JPanel toolbar;
	private final JButton undo;
	private final JButton redo;
	private final JLabel time;
	private final TextArea editor;
	private final Caretaker caretaker;
	
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
		editor = new TextArea(25, 60, this);
		editor.setBorder(BorderFactory.createEmptyBorder(BORDER, BORDER, BORDER, BORDER));
		add(toolbar, BorderLayout.NORTH);
		add(new JScrollPane(editor), BorderLayout.CENTER);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle(TITLE);
		pack();
		// Init editor's memento logic
		undo.addActionListener(e -> undo());
		redo.addActionListener(e -> redo());
		caretaker = new Caretaker();
		saveMemento();
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		saveMemento();
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		saveMemento();
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		saveMemento();
	}
	
	private void saveMemento() {
		caretaker.saveState(editor.createMemento());
		System.out.println(caretaker);
		displayTime();
	}
	
	public void undo() {
		if(caretaker.hasPrevious()) {
			editor.restore(caretaker.previous());
			displayTime();
		}
	}
	
	public void redo() {
		if(caretaker.hasNext()) {
			editor.restore(caretaker.next());
			displayTime();
		}
	}
	
	public void displayTime() {
		time.setText(new Date(caretaker.current().created).toString());
	}
	
	public static void main(String[] args) {
		new SwingNotepad().setVisible(true);
	}
}
