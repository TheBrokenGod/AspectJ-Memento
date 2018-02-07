package ch.jacopoc.memento;

public interface Originator {

	public void setState(Object state);
	public Memento createMemento();
	public void restore(Memento memento);
}
