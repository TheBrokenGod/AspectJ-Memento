package diagrams;

import java.awt.BorderLayout;

import main.UndoRedoApp;

public class DiagramApp extends UndoRedoApp {

	private static final long serialVersionUID = 1L;
	private DiagramEditor editor;

	protected void initGUI() {
		setTitle("Diagrams (Gamma)");
		setBounds(100, 100, getWidth(), getHeight());
		editor = new DiagramEditor();
		add(editor, BorderLayout.CENTER);
		// Perform some commands
		editor.addGraphic("Block1", 100, 100, 75);
		editor.addGraphic("Block2", 500, 75, 75);
		editor.addConstraint("Block1", "Block2");
		editor.addGraphic("Block3", 200, 300, 75);
		editor.addConstraint("Block1", "Block3");
		editor.moveGraphic("Block3", 200, 0);
	}
}