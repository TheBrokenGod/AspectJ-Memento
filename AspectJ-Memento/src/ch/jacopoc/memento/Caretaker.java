package ch.jacopoc.memento;

public interface Caretaker {
	
	default void activate(Originator orig) {
	}
	default History history() { return null; 
	}
	default void moveBack() {	
	}
	default void moveForward() {
	}
}
