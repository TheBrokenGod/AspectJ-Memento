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
		editors.addAll(Arrays.asList(new TextEditor(this), new TextEditor(this), new TextEditor(this)));
		tabs.add("script.py", new JScrollPane(editors.get(0)));
		tabs.add("Main.java", new JScrollPane(editors.get(1)));
		tabs.add("log_0.txt", new JScrollPane(editors.get(2)));
		add(tabs, BorderLayout.CENTER);
		tabs.setSelectedIndex(0);
		tabChanged();
		tabs.addChangeListener((e) -> tabChanged());
	}
	
	private void tabChanged() {
		activate(editors.get(tabs.getSelectedIndex()));
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
		// Last tab closed
		if(editors.size() == 1) {
			super.windowClosing(e);
			return;
		}
		// Remove tab and history
		int selected = tabs.getSelectedIndex();
		tabs.removeTabAt(selected);
		dispose(editors.remove(selected));
	}
}