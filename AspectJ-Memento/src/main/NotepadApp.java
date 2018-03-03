package main;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class NotepadApp extends MementoApp implements DocumentListener {
	
	private static final long serialVersionUID = 1L;
	private JTabbedPane tabs;
	private List<TextEditor> editors;

	@Override
	protected void initGUI() {
		setTitle("Swing Notepad");
		tabs = new JTabbedPane();
		editors = new ArrayList<>();
		editors.addAll(Arrays.asList(
			new TextEditor(this, "print('OK')"), 
			new TextEditor(this, ""), 
			new TextEditor(this, "saved log entry")
		));
		tabs.add("script.py", new JScrollPane(editors.get(0)));
		tabs.add("Main.java", new JScrollPane(editors.get(1)));
		tabs.add("log_0.txt", new JScrollPane(editors.get(2)));
		add(tabs, BorderLayout.CENTER);
		tabs.setSelectedIndex(1);
		activate(editors.get(1));
		tabs.addChangeListener((e) -> tabChanged());
	}
	
	private void tabChanged() {
		int temp = tabs.getSelectedIndex();
		activate(editors.get(temp));
		updateGUI();
	}
	
	@Override
	public void insertUpdate(DocumentEvent e) {
		changedUpdate(e);
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		changedUpdate(e);
	}
	
	@Override
	public void changedUpdate(DocumentEvent e) {
		editors.get(tabs.getSelectedIndex()).createMemento(e);
		updateGUI();
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		// Last closed
		if(editors.size() == 1) {
			super.windowClosing(e);
			return;
		}
		// Close active tab
		int selected = tabs.getSelectedIndex();
		editors.remove(selected);
		tabs.removeTabAt(selected);
	}
}