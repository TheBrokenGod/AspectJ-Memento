package gamma;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ch.jacopoc.memento.History;

public class DiagramApp extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final int BORDER = 5;
	private static final String TITLE = "Diagram Editor (Memento Gamma example)";

	private final JPanel toolbar;
	private final JButton undo;
	private final JButton redo;
	private final JLabel time;
	private final DiagramEditor editor;
	final History<DiagramEditor.Command> history;
	
	public DiagramApp() {
		setLayout(new BorderLayout());
		toolbar = new JPanel(new FlowLayout());
		undo = new JButton("Undo (CTRL+Z)");
		redo = new JButton("Redo (CTRL+Y)");
		time = new JLabel();
		toolbar.add(undo);
		toolbar.add(redo);
		toolbar.add(time);
		editor = new DiagramEditor();
		editor.setBorder(BorderFactory.createEmptyBorder(BORDER, BORDER, BORDER, BORDER));
		add(toolbar, BorderLayout.NORTH);
		add(editor, BorderLayout.CENTER);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle(TITLE);
		setSize(800, 600);
		history = new History<>(editor.emptyCommand());
		undo.addActionListener(e -> history.moveBack());
		redo.addActionListener(e -> history.moveForward());
		setVisible(true);
		doSomeActions();
	}

	private void doSomeActions() {
		history.saveState(editor.addGraphic("Block1", 100, 100, 75));
		history.saveState(editor.addGraphic("Block2", 500, 75, 75));
		history.saveState(editor.addConstraint("Block1", "Block2"));
		history.saveState(editor.addGraphic("Block3", 200, 300, 75));
		history.saveState(editor.addConstraint("Block1", "Block3"));
		history.saveState(editor.moveGraphic("Block3", 200, 0));
	}
}