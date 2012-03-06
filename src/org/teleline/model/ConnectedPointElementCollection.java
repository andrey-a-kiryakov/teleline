package org.teleline.model;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Абстрактный класс коллекции связующих элементов
 *@author Кирьяков Андрей
 */
public abstract class ConnectedPointElementCollection extends AbstractCollection {
	
	public ConnectedPointElementCollection(IdGenerator gen) {
		super(gen);
		// TODO Auto-generated constructor stub
	}
	/**
	 * Проверяет есть ли элемент с данным номеров у своего "родителя". 
	 * Если есть, возвращает его, иначе null.
	 * @param номер вязующего элемента
	 * @param id родителя
	 */
	public ConnectedPointElement isInOwner (Integer elementNumber, Integer OwnerId){
		
		Iterator<?> i = this.elements().iterator();
		
		while (i.hasNext()) {
			ConnectedPointElement element = (ConnectedPointElement)i.next();
			if (element.getOwnerId().equals(OwnerId)  && element.getNumber().equals(elementNumber)) return element;
		}
		return null;	
	}
	/**
	 * Возвращает элемент, занимающий определенное место 
	 * @param placeNumber - номер места
	 * @param OwnerId - id родителя
	 * @return
	 */
	public ConnectedPointElement getInPlace (Integer placeNumber, Integer OwnerId) {
		
		Iterator<?> i = this.elements().iterator();
		
		while (i.hasNext()) {
			ConnectedPointElement element = (ConnectedPointElement)i.next();
			if (element.getOwnerId().equals(OwnerId)  && element.getPlaceNumber().equals(placeNumber) ) return element;
		}
		return null;
	}
	/**
	 * Возвращает коллекцию элементов принадлежащих данному "родителю"
	 * @param id родителя
	 */
	public HashSet<ConnectedPointElement> getInOwner(Integer OwnerId){
		
		HashSet<ConnectedPointElement> elementSet = new HashSet<ConnectedPointElement>();
		
		Iterator<?> i = this.elements().iterator();
		
		while (i.hasNext()) {
			ConnectedPointElement element = (ConnectedPointElement)i.next();
			if ( element.getOwnerId().equals(OwnerId) ) {elementSet.add(element);}
		}
		return elementSet;
	}
}