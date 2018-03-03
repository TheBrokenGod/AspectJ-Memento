package ch.jacopoc.memento;

@SuppressWarnings("rawtypes")
public aspect OriginatorAspect perthis(originator()) {

	pointcut originator() : this(Originator+);
	pointcut newInstance(Originator self) : originator() && this(self) && execution(Originator+.new(..));
	pointcut mementoReturned(Originator self) : originator() && this(self) && execution(Memento+ Originator+.*(..)) && !cflow(within(OriginatorAspect));
	
	private History history = null;
	
	/**
	 * Initialize history for the new originator
	 */
	after(Originator self) : newInstance(self) {
		history = new History(self.createMemento(thisJoinPoint.getArgs()));
	}
	
	/**
	 * Store the returned memento into history
	 */
	after(Originator self) returning(Memento m) : mementoReturned(self) {
		history.saveState(m);
	}
	
	/**
	 * Return history
	 */
	History around() : originator() && execution(History Originator+.history()) {
		return history;
	}
}