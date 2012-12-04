package org.teleline.model;

import java.util.Collection;

public class Wrapper {
	
	private AbstractElement element;
	private Collection<AbstractElement> collection;
	
	
	public Wrapper () {
		this.element = null;
		this.collection = null;
	}
	
	public void setElement(AbstractElement element) {
		this.element = element;
	}
	
	public AbstractElement getElement() {
		return this.element;
	}
	
	public void SetCollection(Collection<AbstractElement> collection) {
		this.collection = collection; 
	}
	public Collection<AbstractElement> getCollection() {
		return this.collection;
	}
	
	
}