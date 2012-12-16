package org.teleline.gui;


import javax.swing.JButton;

import org.teleline.model.AbstractElement;

@SuppressWarnings("serial")
public class ElementView extends JButton {
	
	
	private AbstractElement element;
	private Integer placeNumber;
	
	public ElementView() {
		element = null;
		placeNumber = -1;
	}
	
	public ElementView setElement (AbstractElement element) {
		this.element = element;
		return this;
	}
	
	public AbstractElement getElement () {
		return this.element;
	}
	
	public void setPlaceNumber(Integer number) {
		this.placeNumber = number;
	}
	
	public Integer getPlaceNumber() {
		return this.placeNumber;
	}
	
	
}