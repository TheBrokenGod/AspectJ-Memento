package ch.jacopoc.memento;

@SuppressWarnings("rawtypes")
public aspect OriginatorAspect perthis(originator()) {

	pointcut originator() : this(Originator+);
	private History history = null;
	
	/**
	 * Initialize history for the new originator
	 */
	after(Originator self) : originator() && this(self) && execution(Originator+.new(..)) {
		history = new History(self.createMemento(thisJoinPoint.getArgs()));
	}
	
	/**
	 * Store a returned memento into history
	 */
	after(Originator self) returning(Memento m) : originator() && this(self) && execution(public Memento+ Originator+.*(..)) && !cflow(within(OriginatorAspect)) {
		history.saveState(m);
	}
	
	/**
	 * Return originator's history
	 */
	History around() : originator() && execution(History Originator+.history()) && cflow(within(CaretakerAspect)) {
		return history;
	}
}