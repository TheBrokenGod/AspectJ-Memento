package main;

import diagrams.DiagramApp;
import notepad.NotepadApp;
import treetest.TreeHistoryTest;

public class MainClass {
	
	public static void main(String[] args) {
		new TreeHistoryTest().run();
		new DiagramApp();
		new NotepadApp();
	}
} 	
