package main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ch.jacopoc.memento.Caretaker;

abstract public class UndoRedoApp extends JFrame implements Caretaker, WindowListener {
	
	private static final long serialVersionUID = 1L;
	private static final DateTimeFormatter DTFORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
	private final JPanel toolbar;
	private final JButton undo;
	private final JButton redo;
	private final JLabel time;
	
	protected UndoRedoApp() {
		// Base GUI
		setLayout(new BorderLayout());
		toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT));
		undo = new JButton("Undo (CTRL+Z)");
		redo = new JButton("Redo (CTRL+Y)");
		time = new JLabel();
		toolbar.add(undo);
		toolbar.add(redo);
		toolbar.add(time);
		add(toolbar, BorderLayout.NORTH);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(this);
		setSize(800, 600);
		setVisible(true);
		// Memento
		undo.addActionListener((e) -> {history().undo(); updateGUI();});
		redo.addActionListener((e) -> {history().redo(); updateGUI();});
		initGUI();
		updateGUI();
	}
	
	abstract protected void initGUI();
	
	protected void updateGUI() {
		time.setText("History time: " + DTFORMATTER.format(history().current().created));
		undo.setEnabled(history().hasPrevious());
		redo.setEnabled(history().hasNext());
		System.out.println(history());
	}

	@Override
	public void windowClosing(WindowEvent e) {
		dispose();
	}
	
	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowOpened(WindowEvent e) {	
	}
}