package main;

import java.util.HashMap;
import java.util.Map;

import ch.jacopoc.memento.History;
import ch.jacopoc.memento.Memento;
import ch.jacopoc.memento.Originator;

public aspect CaretakerAspect {

	private final Map<Originator, History> history = new HashMap<>();
	private Originator current = null;
	
	pointcut createHistory(TextEditor originator) : execution(TextEditor.new(..)) && this(originator);
	after(TextEditor target) : createHistory(target) {
		current = target;
		history.put(current, new History(current.createMemento()));
	}
	
	pointcut saveReturnedMemento(TextEditor originator) : call(TextEditor.TextEvent TextEditor.*(..)) && target(originator) && !withincode(TextEditor.new(..));
	after(TextEditor target) returning(Memento m) : saveReturnedMemento(target) {
		history.get(target).saveState(m);
	}

//	void around(Originator originator) : call(void ch.jacopoc.memento.Caretaker.activate(Originator)) && args(originator) {
//		current = originator;
//	}
	
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
