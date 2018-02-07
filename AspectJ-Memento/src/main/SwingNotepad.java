package main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class SwingNotepad extends JFrame implements DocumentListener {

	private static final long serialVersionUID = 1L;
	private static final int BORDER = 5;
	private static final String TITLE = "Memento Notepad";

	private final JPanel toolbar;
	private final JButton undo;
	private final JButton redo;
	private final TextArea editor;
	
	public SwingNotepad() {
		// Init editor's Swing GUI
		setLayout(new BorderLayout());
		toolbar = new JPanel(new FlowLayout());
		undo = new JButton("Undo (CTRL+Z)");
		redo = new JButton("Redo (CTRL+Y)");
		toolbar.add(undo);
		toolbar.add(redo);
		editor = new TextArea(25, 60);
		editor.setBorder(BorderFactory.createEmptyBorder(BORDER, BORDER, BORDER, BORDER));
		add(toolbar, BorderLayout.NORTH);
		add(new JScrollPane(editor), BorderLayout.CENTER);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle(TITLE);
		pack();
		// Init editor's memento logic
		editor.getDocument().addDocumentListener(this);
		undo.addActionListener(e -> System.out.println("perform undo"));
		redo.addActionListener(e -> System.out.println("perform redo"));
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
		System.out.println("now '" + editor.createMemento().toString().replaceAll("\n", "\\\\n ") + "'");
	}	

	public static void main(String[] args) {
		new SwingNotepad().setVisible(true);
	}
}
