package gamma;

import ch.jacopoc.memento.MementoApp;

public class DiagramApp extends MementoApp<DiagramEditor, DiagramEditor.Command> {

	private static final long serialVersionUID = 1L;		
	
	public DiagramApp() {
		super("Diagrams Editor", new DiagramEditor());
		history.saveState(editor.addGraphic("Block1", 100, 100, 75));
		history.saveState(editor.addGraphic("Block2", 500, 75, 75));
		history.saveState(editor.addConstraint("Block1", "Block2"));
		history.saveState(editor.addGraphic("Block3", 200, 300, 75));
		history.saveState(editor.addConstraint("Block1", "Block3"));
		history.saveState(editor.moveGraphic("Block3", 200, 0));
		updateButtons();
	}
}