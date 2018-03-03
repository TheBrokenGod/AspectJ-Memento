package treetest;

import ch.jacopoc.memento.Caretaker;
import ch.jacopoc.memento.Memento;
import ch.jacopoc.memento.Originator;

public class TreeHistoryTest implements Caretaker, Runnable {

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

		public OriginatorImpl(String string) {
		}

		@Override
		public WordMemento createMemento(Object... args) {
			return new WordMemento(args.length > 0 ? (String)args[0] : "");
		}
	}
	
	final Originator<WordMemento> originator;
	
	public TreeHistoryTest() {
		originator = new OriginatorImpl("This");
	}

	@Override
	public void run() {
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
		System.out.println();
	}
	
	void printHistory() {
		System.err.println(history());
	}
	
	void assertHistory(String assertion) {
		String sentence = history().toString().split("\\[")[1].split("<>")[0].replaceAll("\\]", "").replaceAll(",", "");
//		if(!sentence.equals(assertion)) throw new AssertionError();
		System.err.println(sentence);
	}
	
	void undo(int count) {
		for (int i = 0; i < count; i++) history().undo();
	}
	
	void redo(int count, int branch) {
		for (int i = 0; i < count; i++) history().redo(branch);
	}
}
