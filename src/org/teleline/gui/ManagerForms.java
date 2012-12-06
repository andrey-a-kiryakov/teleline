package org.teleline.gui;

import java.util.HashSet;
import java.util.Iterator;
import javax.swing.JDialog;

public class ManagerForms {
	
	private HashSet<JDialog> elements;
	
	public ManagerForms() {
		elements = new HashSet<JDialog>();
	}
	
	public void add(JDialog dialog) {
		elements.add(dialog);
	}
	
	public void remove(JDialog dialog) {
		elements.remove(dialog);
	}
	
	public void close(JDialog dialog) {
		elements.remove(dialog);
		dialog.dispose();
	}
	
	public void closeAll() {
		Iterator<JDialog> i = elements.iterator();
		while(i.hasNext()) {
			i.next().dispose();
		}
		elements.clear();
	}
	
	public int getSize() {
		return elements.size();
	}
}