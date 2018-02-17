package main;

import ch.jacopoc.memento.AbstractMemento;

class Memento extends AbstractMemento {

	final String state;
	
	Memento(String state) {
		this.state = state;
	}
}
