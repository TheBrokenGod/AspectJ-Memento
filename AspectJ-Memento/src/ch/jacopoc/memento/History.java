package ch.jacopoc.memento;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents an ordered list of consecutive states which is possible to traverse in both directions.
 */
public class History {

	private List<Memento> savedStates;
	private int currentState;
	
	History(Memento initialState) {
		savedStates = new ArrayList<>();
		savedStates.add(initialState);
		currentState = 0;
	}
	
	/**
	 * Return the total number of saved states.
	 * 
	 * @return The number of saved states.
	 */
	public int size() {
		return savedStates.size();
	}
	
	/**
	 * Return the state at current cursor position.
	 * 
	 * @return The current state.
	 */
	public Memento current() {
		return savedStates.get(currentState);
	}
	
	/**
	 * Check if there is a previous state.
	 * 
	 * @return true if there is a previous state, false otherwise.
	 */
	public boolean hasPrevious() {
		return currentState > 0;
	}
	
	/**
	 * Move to the previous state, activating the proper triggers on the current and the previous memento object.
	 * 
	 * @return true if there is a previous state, false otherwise.
	 */
	public boolean undo() {
		if(!hasPrevious()) {
			return false;
		}
		current().onExit();
		current().onExitToPrevious();
		currentState -= 1;
		current().onEnter();
		current().onEnterFromNext();
		return true;
	}
	
	/**
	 * Check if there is a next state.
	 * 
	 * @return true if there is a next state, false otherwise.
	 */
	public boolean hasNext() {
		return currentState <= savedStates.size() - 2;
	}
	
	/**
	 * Move to the next state, activating the proper triggers on the current and the next memento object.
	 * 
	 * @return true if there is a next state, false otherwise.
	 */
	public boolean redo() {
		return redo(0);
	}

	/**
	 * Move to the next state on the given branch, activating the proper triggers on the current and the next memento object.
	 * 
	 * @return true if there is a next state on the given branch, false otherwise.
	 */
	public boolean redo(int branch) {
		if(!hasNext()) {
			return false;
		}
		current().onExit();
		current().onExitToNext();
		currentState += 1;
		current().onEnter();
		current().onEnterFromPrevious();
		return true;		
	}
	
	void saveState(Memento memento) {
		// Drop the previous diverging timeline
		if(hasNext()) {
			savedStates = savedStates.subList(0, currentState + 1);
		}
		savedStates.add(memento);
		currentState++;
		current().onAddToHistory();
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder("{History:" + hashCode() + "} [");
		for(Memento state : savedStates) {
			str.append(state).append(state == current() ? "<>" : "").append(", ");
		}
		return str.replace(str.length()-2, str.length(), "").append(']').toString();
	}
}