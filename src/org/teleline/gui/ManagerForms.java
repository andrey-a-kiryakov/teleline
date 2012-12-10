package org.teleline.gui;

import java.util.HashSet;
import java.util.Iterator;
import javax.swing.JDialog;
import javax.swing.JFrame;

public class ManagerForms {
	
	private HashSet</*JDialog*/JFrame> elements;
	
	public ManagerForms() {
		elements = new HashSet<JFrame/*JDialog*/>();
	}
	
	public void add(/*JDialog*/JFrame dialog) {
		elements.add(dialog);
	}
	
	public void remove(/*JDialog*/JFrame dialog) {
		elements.remove(dialog);
	}
	
	public void close(/*JDialog*/JFrame dialog) {
		elements.remove(dialog);
		dialog.dispose();
	}
	
	public void closeAll() {
		Iterator<JFrame/*JDialog*/> i = elements.iterator();
		while(i.hasNext()) {
			i.next().dispose();
		}
		elements.clear();
	}
	
	public int getSize() {
		return elements.size();
	}
}