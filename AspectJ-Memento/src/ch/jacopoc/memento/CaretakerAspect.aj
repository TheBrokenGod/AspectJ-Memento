package ch.jacopoc.memento;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("rawtypes") 
public aspect CaretakerAspect perthis(caretaker()) {
	
	private final Map<Originator, History> histories = new HashMap<>();
	private Originator active = null;
	
	/**
	 *  Every Caretaker will have its instance of CaretakerAspect
	 */
	pointcut caretaker() : this(Caretaker+) && !within(CaretakerAspect);

	/**
	 * Caretaker created an Originator object -> Initialize corresponding history
	 */
	pointcut originatorCreated() : caretaker() && call(Originator+.new(..));
	after() returning (Originator originator) : originatorCreated() {
		histories.put(originator, new History(originator.createMemento()));
		activate(originator);
	}
	
	/**
	 * Originator returned a Memento object to the Caretaker -> Put into history
	 */
	pointcut mementoReturnedToCaretaker(Originator originator) : caretaker() && call(Memento+ Originator+.*(..)) && target(originator);
	after(Originator originator) returning(Memento m) : mementoReturnedToCaretaker(originator) {
		histories.get(originator).saveState(m);
		activate(originator);
	}

	/**
	 * If more than one Originator set the specified one as active
	 */
	void around(Originator originator) : caretaker() && call(void Caretaker.activate(Originator)) && args(originator) {
		activate(originator);
	}
	
	private void activate(Originator originator) {
		active = originator;
	}
	
	/**
	 * Return the History associated to the currently active Originator
	 */
	History around() : caretaker() && call(History Caretaker.history()) {
		return histories.get(active);
	}
	
	/**
	 * The given Originator has been disposed -> Delete the associated History
	 */
	void around(Originator originator) : caretaker() && call(void Caretaker.dispose(Originator)) && args(originator) {
		histories.remove(originator);
		if(active == originator)
			active = null;
	}
}