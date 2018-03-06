package ch.jacopoc.memento;

/**
 * Holds the states produced by a specific Originator instance. 
 * 
 * It is possible to orderly traverse it in both directions and branching is supported too.
 */
public class History {

	private Memento currentState;
	
	History(Memento initialState) {
		initialState.previous = null;
		currentState = initialState;
	}
	
	/**
	 * Returns the state at current cursor position.
	 * 
	 * @return The current state.
	 */
	public Memento current() {
		return currentState;
	}
	
	/**
	 * Checks if there is a previous state.
	 * 
	 * @return True if there is a previous state, false otherwise.
	 */
	public boolean hasPrevious() {
		return currentState.previous != null;
	}
	
	/**
	 * Moves to the previous state, executing the relevant triggers on the current and previous Memento.
	 */
	public void undo() {
		currentState.onExit();
		currentState.onExitToPrevious();
		currentState = currentState.previous;
		currentState.onEnter();
		currentState.onEnterFromNext();
	}
	
	/**
	 * Checks if there is at least one next state (branch).
	 * 
	 * @return True if there is a next state, false otherwise.
	 */
	public boolean hasNext() {
		return currentState.next.size() > 0;
	}
	
	/**
	 * Checks if the given outgoing branch exists. 
	 * 
	 * @param branch Index of the branch
	 * 
	 * @return True if the given branch exists, false otherwise.
	 */
	public boolean hasNext(int branch) {
		return false;
	}
	
	/**
	 * Moves to the next state, executing the relevant triggers on the current and next Memento.
	 */
	public void redo() {
		redo(currentState.next.size() - 1);
	}

	/**
	 * Moves to the next state on the given branch, executing the relevant triggers on the current and next Memento.
	 * 
	 * @param branch Index of the branch
	 */
	public void redo(int branch) {
		currentState.onExit();
		currentState.onExitToNext();
		currentState = currentState.next.get(branch);
		currentState.onEnter();
		currentState.onEnterFromPrevious();
	}
	
	void saveState(Memento state) {
		currentState.next.add(state);
		state.previous = currentState;
		currentState = state;
		currentState.onAddToHistory();
	}

	/**
	 * Returns a printable representation of this History object.
	 */
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder("[");
		// Previous state
		Memento it = currentState;
		while((it = it.previous) != null) {
			str.insert(1, stateToString(it));
		}
		// Current cursor position
		str.append("{").append(currentState).append("}, ");
		// Next (most recent branches)	
		it = currentState;	
		while((it = it.next.size() > 0 ? it.next.get(it.next.size()-1) : null) != null) {
			str.append(stateToString(it));
		}
		return str.replace(str.length()-2, str.length(), "").append(']').toString();
	}
	
	private String stateToString(Memento state) {
		return state + (state.next.size() > 1 ? "<" + state.next.size() : "") + ", ";
	}
}