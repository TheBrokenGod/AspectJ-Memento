package treetest;

import ch.jacopoc.memento.Caretaker;
import ch.jacopoc.memento.Memento;
import ch.jacopoc.memento.Originator;

public class TreeHistoryTest implements Caretaker {

	// Word inserted -> new state
	static class WordMemento extends Memento {	
		final String word;
		
		WordMemento(String word) {
			this.word = word;
		}
		
		@Override
		public String toString() {
			return word;
		}
	}
	
	static class OriginatorImpl implements Originator<WordMemento> {	
		@Override
		public WordMemento createMemento(Object... args) {
			return new WordMemento(args.length > 0 ? (String)args[0] : "This");
		}
	}
	
	public TreeHistoryTest() {
		Originator<?> originator = new OriginatorImpl();
		// Generate some branches
		originator.createMemento("is");
		originator.createMemento("the");
		originator.createMemento("first");
		originator.createMemento("branch.");
		undo(2);
		printHistory();
		originator.createMemento("second");
		originator.createMemento("branch.");
		undo(1);
		printHistory();
		originator.createMemento("and");
		originator.createMemento("last");
		originator.createMemento("branch.");
		printHistory();
		// Test correctness by exploring them
		assertHistory("This is the second and last branch.");
		undo(4);
		redo(2, 0);
		assertHistory("This is the first branch.");
		undo(2);
		redo(1, 1);
		redo(1, 0);
		assertHistory("This is the second branch.");
		undo(1);
		redo(1, 1);
		redo(1, 0);
		assertHistory("This is the second and last");
		printHistory();
	}
	
	void undo(int count) {
		for (int i = 0; i < count; i++) history().undo();
	}
	
	void redo(int count, int branch) {
		for (int i = 0; i < count; i++) history().redo(branch);
	}
	
	void printHistory() {
		System.err.println(history());
	}
	
	void assertHistory(String assertion) {
		// Join history up to current position and test it
		String sentence = history().toString().split("\\[")[1].split("<>")[0].split("}")[0];
		sentence = sentence.replaceAll(",", "").replaceAll("\\<2", "").replaceAll("\\]", "").replaceAll("\\{", "");
		sentence = sentence.trim();
		if(!sentence.equals(assertion)) {
			throw new AssertionError(sentence);
		}
		System.err.println(sentence);
	}
}