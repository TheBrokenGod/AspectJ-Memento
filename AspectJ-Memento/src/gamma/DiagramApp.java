package gamma;

import main.MementoApp;

public class DiagramApp extends MementoApp<DiagramEditor> {

	private static final long serialVersionUID = 1L;
	
	public DiagramApp() {
		initGUI("Diagrams", new DiagramEditor());
		// Example commands
		editor.addGraphic("Block1", 100, 100, 75);
		editor.addGraphic("Block2", 500, 75, 75);
		editor.addConstraint("Block1", "Block2");
		editor.addGraphic("Block3", 200, 300, 75);
		editor.addConstraint("Block1", "Block3");
		editor.moveGraphic("Block3", 200, 0);
		updateGUI();
	}
}