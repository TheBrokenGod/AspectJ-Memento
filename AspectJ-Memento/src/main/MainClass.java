package main;

import gamma.DiagramApp;
import orig.NotepadApp;

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
