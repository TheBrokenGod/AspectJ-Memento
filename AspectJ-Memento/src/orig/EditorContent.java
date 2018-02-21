package orig;

import ch.jacopoc.memento.Memento;

class EditorContent implements Memento {

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
