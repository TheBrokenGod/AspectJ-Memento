package ch.jacopoc.memento;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

abstract public class MementoApp<C extends JComponent & Originator<M>, M extends Memento<M>> extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private static final int BORDER = 5;
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss:S");
	
	private final JPanel toolbar;
	private final JButton undo;
	private final JButton redo;
	private final JLabel time;
	
	protected final C editor;
	protected final History<M> history;
	
	protected MementoApp(String title, C editor) {
		this.editor = editor;
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
		setTitle(title);
		setSize(800, 600);
		setVisible(true);
		// Memento
		history = new History<>(((Originator<M>)editor).createMemento());
		undo.addActionListener((e) -> {history.moveBack(); updateButtons();});
		redo.addActionListener((e) -> {history.moveForward(); updateButtons();});
		displayTime();
		updateButtons();
	}
	
	public History<M> getHistory() {
		return history;
	}
	
	public void displayTime() {
		time.setText(FORMATTER.format(history.current().created));
	}
	
	protected void updateButtons() {
		undo.setEnabled(history.hasPrevious());
		redo.setEnabled(history.hasNext());
	}
}