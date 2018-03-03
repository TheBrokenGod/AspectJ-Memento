package ch.jacopoc.memento;

import java.lang.ref.WeakReference;

public aspect CaretakerAspect perthis(caretaker()) {
	
	pointcut caretaker() : this(Caretaker+);
	pointcut originatorCreated() : caretaker() && call(Originator+.new(..));
	
	private WeakReference<Originator<?>> active = new WeakReference<>(null);
	
	/**
	 * Activate the latest built originator
	 */
	after() returning (Originator<?> originator) : originatorCreated() {
		active = new WeakReference<>(originator);
	}
	
	/**
	 * Activate a particular object in a multi-originator environment
	 */
	void around(Originator<?> originator) : caretaker() && execution(void Caretaker.activate(Originator)) && args(originator) {
		active = new WeakReference<>(originator);
	}
	
	/**
	 * Fetch active originator's history
	 */
	History around() : caretaker() && execution(History Caretaker.history()) {
		return active.get().history();
	}
}