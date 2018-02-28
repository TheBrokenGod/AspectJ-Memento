package gamma;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;

import ch.jacopoc.memento.Caretaker;
import main.MementoApp;

public class DiagramApp extends MementoApp implements Caretaker {

	private static final long serialVersionUID = 1L;
	
	private final DiagramEditor editor;
	
	public DiagramApp() {
		super("Diagrams Editor");
		editor = new DiagramEditor();
		editor.setBorder(BorderFactory.createEmptyBorder(BORDER, BORDER, BORDER, BORDER));
		add(editor, BorderLayout.CENTER);
		undo.addActionListener((e) -> {moveBack(); updateButtons();});
		redo.addActionListener((e) -> {moveForward(); updateButtons();});
		// Example commands
		editor.addGraphic("Block1", 100, 100, 75);
		editor.addGraphic("Block2", 500, 75, 75);
		editor.addConstraint("Block1", "Block2");
		editor.addGraphic("Block3", 200, 300, 75);
		editor.addConstraint("Block1", "Block3");
		editor.moveGraphic("Block3", 200, 0);
		displayTime();
		updateButtons();
	}
	
	public void displayTime() {
		time.setText(FORMATTER.format(history().current().created));
	}
	
	protected void updateButtons() {
		undo.setEnabled(history().hasPrevious());
		redo.setEnabled(history().hasNext());
	}
}