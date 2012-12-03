package org.teleline.model;

public class Wrapper {
	
	private AbstractElement element;
	
	public Wrapper () {
		this.element = null;
	}
	
	public void setElement(AbstractElement element) {
		this.element = element;
	}
	
	public AbstractElement getElement() {
		return this.element;
	}
	
}