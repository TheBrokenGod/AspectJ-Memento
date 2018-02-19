package orig;

import java.util.ArrayList;
import java.util.List;

import ch.jacopoc.memento.IMemento;

public class EditorEvent implements IMemento {

	public static final int INSERTION = 1, REMOVAL = 0;
	
	private final int eventType;
	private final TextEditor editor;
//	private final int offset;
	
	
	public EditorEvent(TextEditor editor, int eventType, int offset, int length) {
		this.eventType = eventType;
		this.editor = editor;
//		this.offset = offset;
	}
	
	public void redo() {
		if(eventType == INSERTION) {
			List<Character> document = stringToList();
			//
		}
		else if(eventType == REMOVAL) {
			
		}
	}
	
	public void undo() {
		if(eventType == INSERTION) {
			
		}
		else if(eventType == REMOVAL) {
			
		}
	}
	
	private List<Character> stringToList() {
		List<Character> list = new ArrayList<>(editor.getText().length());
		for(char c : editor.getText().toCharArray()) {
			list.add(c);
		}
		return list;
	}
}
