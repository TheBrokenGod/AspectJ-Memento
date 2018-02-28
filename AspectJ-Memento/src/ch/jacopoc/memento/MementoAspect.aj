package ch.jacopoc.memento;

import java.util.HashMap;
import java.util.Map;

abstract public aspect MementoAspect issingleton() {
	
	protected final Map<Originator, History> history = new HashMap<>();
	protected Originator current = null;

	// Originator created, initialize own history
	public abstract pointcut createHistoryForOriginator(Originator originator);
	after(Originator originator) : createHistoryForOriginator(originator) {
		current = originator;
		history.put(current, new History(current.createMemento()));
		System.err.println(history.get(originator).current());
	}
	
	// Store the memento object returned by the originator
	public abstract pointcut mementoReturnedToCaretaker(Originator originator);
	after(Originator originator) returning(Memento m) : mementoReturnedToCaretaker(originator) {
		history.get(originator).saveState(m);
	}

//	// Provide code for the Caretaker interface without enforcing inheritance
//	void around(Originator originator) : call(void Caretaker.activate(Originator)) && args(originator) {
//		current = originator;
//	}
//	History around() : call(History Caretaker.history()) {
//		return history.get(current);
//	}
//	void around() : call(void Caretaker.moveBack()) {
//		history.get(current).moveBack();
//	}
//	void around() : call(void Caretaker.moveForward()) {
//		history.get(current).moveForward();
//	}
}
