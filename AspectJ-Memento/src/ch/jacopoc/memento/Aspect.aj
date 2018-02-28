package ch.jacopoc.memento;

import java.util.HashMap;
import java.util.Map;

public aspect Aspect {
	
	private final Map<Originator, History> history = new HashMap<>();
	private Originator current = null;

	// Originator object created, create corresponding history
	pointcut createHistoryForOriginator(Originator originator) : execution(Originator+.new(..)) && this(originator);
	after(Originator originator) : createHistoryForOriginator(originator) {
		history.put(activate(originator), new History(originator.createMemento()));
	}
	
	// Store the memento object returned to the caretaker
	pointcut mementoReturnedToCaretaker(Originator originator) : call(Memento+ Originator+.*(..)) && target(originator) && within(Caretaker+);
	after(Originator originator) returning(Memento m) : mementoReturnedToCaretaker(originator) {
		history.get(activate(originator)).saveState(m);
	}

	// Activate a specific originator if more than one
	void around(Originator originator) : call(void Caretaker.activate(Originator)) && args(originator) {
		activate(originator);
	}
	
	private Originator activate(Originator originator) {
		current = originator;
		return current;
	}
	
	History around() : call(History Caretaker.history()) {
		return history.get(current);
	}
	
	void around() : call(void Caretaker+.moveBack()) {
		history.get(current).moveBack();
	}
	
	void around() : call(void Caretaker+.moveForward()) {
		history.get(current).moveForward();
	}
}