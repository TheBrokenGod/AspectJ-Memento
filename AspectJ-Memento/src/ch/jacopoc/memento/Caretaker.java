package ch.jacopoc.memento;

import java.util.*;

public class Caretaker {

	private final List<Memento> savedStates = new ArrayList<>();
	
	@Override
	public String toString() {
		StringBuilder states = new StringBuilder();
		for(Memento state : savedStates) {
			states.append(state.toString() + '\n');
		}
		return states.toString();
	}
}
