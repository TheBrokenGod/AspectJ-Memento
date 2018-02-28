package main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

abstract public class MementoApp extends JFrame {
	
	private static final long serialVersionUID = 1L;
	protected static final int BORDER = 5;
	protected static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss:S");
	private final JPanel toolbar;
	protected final JButton undo;
	protected final JButton redo;
	protected final JLabel time;
	
	protected MementoApp(String title) {
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
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle(title);
		setSize(800, 600);
		setVisible(true);
	}
}