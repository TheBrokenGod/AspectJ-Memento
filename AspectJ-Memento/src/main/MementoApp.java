package main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ch.jacopoc.memento.Caretaker;
import ch.jacopoc.memento.Originator;

abstract public class MementoApp<T extends JComponent & Originator> extends JFrame implements Caretaker {
	
	private static final long serialVersionUID = 1L;
	private final JPanel toolbar;
	private final JButton undo;
	private final JButton redo;
	private final JLabel time;
	protected T editor = null;
	
	protected MementoApp() {
		// Base GUI
		setLayout(new BorderLayout());
		toolbar = new JPanel(new FlowLayout());
		undo = new JButton("Undo (CTRL+Z)");
		redo = new JButton("Redo (CTRL+Y)");
		time = new JLabel();
		toolbar.add(undo);
		toolbar.add(redo);
		toolbar.add(time);
		add(toolbar, BorderLayout.NORTH);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(800, 600);
		setVisible(true);
		// Memento
		undo.addActionListener((e) -> undo());
		redo.addActionListener((e) -> redo());
	}
	
	protected void initGUI(String title, T editor) {
		this.editor = editor;
		editor.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		add((JComponent)editor, BorderLayout.CENTER);
		setTitle(title);
		updateGUI();
	}
	
	private void undo() {
		activate(editor); history().moveBack(); updateGUI();
	}
	
	private void redo() {
		activate(editor); history().moveForward(); updateGUI();
	}
	
	protected void updateGUI() {
		time.setText(DateTimeFormatter.ofPattern("HH:mm:ss:S").format(history().current().created));
		undo.setEnabled(history().hasPrevious());
		redo.setEnabled(history().hasNext());
		System.out.println(history());
	}
}