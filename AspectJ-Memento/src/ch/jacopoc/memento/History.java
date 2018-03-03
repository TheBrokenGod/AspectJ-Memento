package ch.jacopoc.memento;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Jacopo Carravieri
 *
 */
public class History {

	private List<Memento> savedStates;
	private int currentState;
	
	/**
	 * 
	 * @param emptyState
	 */
	History(Memento initialState) {
		savedStates = new ArrayList<>();
		savedStates.add(initialState);
		currentState = 0;
	}
	
	/**
	 * 
	 * @return
	 */
	public int size() {
		return savedStates.size();
	}
	
	/**
	 * 
	 * @return
	 */
	public Memento current() {
		return savedStates.get(currentState);
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean hasPrevious() {
		return currentState > 0;
	}
	
	/**
	 * 
	 * @return
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
	 * 
	 * @return
	 */
	public boolean hasNext() {
		return currentState <= savedStates.size() - 2;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean redo() {
		return redo(0);
	}

	/**
	 * 
	 * @param branch
	 * @return
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
	
	/**
	 * 
	 * @param memento
	 */
	public void saveState(Memento memento) {
		// Drop the previous diverging timeline
		if(hasNext()) {
			savedStates = savedStates.subList(0, currentState + 1);
		}
		savedStates.add(memento);
		currentState++;
		current().onAddToHistory(this);
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