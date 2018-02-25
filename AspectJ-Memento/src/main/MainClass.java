package main;

import gamma.DiagramApp;

public class MainClass {
	
	private static void gamma() {
		new DiagramApp();
	}
	
	private static void original() {
		new NotepadApp();
	}

	public static void main(String[] args) {
		gamma();
		original();
	}
} 	
