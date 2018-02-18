package orig;

import ch.jacopoc.memento.IMemento;

class EditorContent implements IMemento {

	final long created;
	final String state;
	
	EditorContent(String state) {
		created = System.currentTimeMillis();
		this.state = state;
	}
	
	@Override
	public String toString() {
		return "'" + state + "'";
	}
}
