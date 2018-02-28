package gamma;

import ch.jacopoc.memento.History;
import ch.jacopoc.memento.MementoAspect;
import ch.jacopoc.memento.Originator;

public aspect MainAspect extends MementoAspect {

	public pointcut createHistoryForOriginator(Originator originator) : 
		execution(DiagramEditor.new(..)) && 
		this(originator);
	
	public pointcut mementoReturnedToCaretaker(Originator originator) : 
		call(DiagramEditor.Command DiagramEditor.*(..)) && 
		target(originator) &&
		!cflow(execution(DiagramEditor.new(..)));
	
	// TODO move to super
	// Provide code for the Caretaker interface without enforcing inheritance
	void around(Originator originator) : call(void ch.jacopoc.memento.Caretaker.activate(Originator)) && args(originator) {
		current = originator;
	}
	History around() : call(History ch.jacopoc.memento.Caretaker.history()) {
		return history.get(current);
	}
	void around() : call(void ch.jacopoc.memento.Caretaker.moveBack()) {
		history.get(current).moveBack();
	}
	void around() : call(void ch.jacopoc.memento.Caretaker.moveForward()) {
		history.get(current).moveForward();
	}
}
