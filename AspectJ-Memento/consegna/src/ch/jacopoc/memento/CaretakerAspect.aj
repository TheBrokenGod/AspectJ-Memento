package ch.jacopoc.memento;

import java.lang.ref.WeakReference;

public aspect CaretakerAspect perthis(caretaker()) {
	
	pointcut caretaker() : this(Caretaker+);
	private WeakReference<Originator<?>> active = new WeakReference<>(null);
	
	/**
	 * Activate the built originator
	 */
	after() returning (Originator<?> originator) : caretaker() && call(Originator+.new(..)) {
		active = new WeakReference<>(originator);
	}
	
	/**
	 * Activate a particular originator
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