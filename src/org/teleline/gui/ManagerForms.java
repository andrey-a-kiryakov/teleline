package org.teleline.gui;

import java.util.HashSet;
import java.util.Iterator;
import javax.swing.JFrame;

public class ManagerForms {
	
	private HashSet<JFrame> elements;
	
	public ManagerForms() {
		elements = new HashSet<JFrame>();
	}
	
	public void add(JFrame dialog) {
		elements.add(dialog);
	}
	
	public void remove(JFrame dialog) {
		elements.remove(dialog);
	}
	
	public void close(JFrame dialog) {
		elements.remove(dialog);
		dialog.dispose();
	}
	
	public void closeAll() {
		Iterator<JFrame> i = elements.iterator();
		while(i.hasNext()) {
			i.next().dispose();
		}
		elements.clear();
	}
	
	public int getSize() {
		return elements.size();
	}
}