package org.teleline.gui;


import javax.swing.JButton;

import org.teleline.model.AbstractElement;

@SuppressWarnings("serial")
public class ElementView extends JButton {
	
	
	private AbstractElement element;
	
	public ElementView setElement (AbstractElement element) {
		this.element = element;
		return this;
	}
	
	public AbstractElement getElement () {
		return this.element;
	}
	
}