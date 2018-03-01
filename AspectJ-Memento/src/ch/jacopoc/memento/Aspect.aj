package ch.jacopoc.memento;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;

public aspect Aspect perthis(fromCaretaker()) {
	
	// TODO Memento -> Originator ???
	final Map<Originator, History> history = new WeakHashMap<>();
	WeakReference<Originator> active = new WeakReference<>(null);
	
	// Only when called from within the Caretaker itself
	pointcut fromCaretaker() : this(Caretaker+) && !within(Aspect);

	/**
	 * Caretaker created an Originator object
	 * Initialize corresponding history
	 */
	pointcut originatorCreated() : fromCaretaker() && call(Originator+.new(..));
	after() returning (Originator originator) : originatorCreated() {
		history.put(originator, new History(originator.createMemento()));
		activate(originator);
	}
	
	/**
	 * Originator returned a Memento object
	 * Put into history
	 */
	pointcut mementoReturnedToCaretaker(Originator originator) : fromCaretaker() && call(Memento+ Originator+.*(..)) && target(originator);
	after(Originator originator) returning(Memento m) : mementoReturnedToCaretaker(originator) {
		history.get(originator).saveState(m);
		activate(originator);
	}

	// Activate a specific originator if more than one
	void around(Originator originator) : fromCaretaker() && call(void Caretaker.activate(Originator)) && args(originator) {
		activate(originator);
	}
	
	private void activate(Originator originator) {
		active = new WeakReference<>(originator);
	}
	
	History around() : fromCaretaker() && call(History Caretaker.history()) {
		return history.get(active.get());
	}
}