package ch.jacopoc.memento;

import java.time.LocalDateTime;

public class Memento<T extends Memento<T>> {
	
	public final LocalDateTime created = LocalDateTime.now();
	
	protected void onAddToHistory(History<T> history) {
	}
	protected void onEnter() {
	}
	protected void onEnterFromPrevious() {
	}
	protected void onEnterFromNext() {
	}
	protected void onExit() {
	}
	protected void onExitToPrevious() {
	}
	protected void onExitToNext() {
	}
}
