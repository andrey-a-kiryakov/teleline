package org.teleline.model;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Класс коллекции элементов "Бокс"
 * 
 *@author Кирьяков Андрей
 */

public class BoxCollection extends ConnectedPointElementCollection {
	
 	
	public BoxCollection(IdGenerator gen) {
		super(gen);
	}

	/**
	 * Возвращает коллекцию элементов "Бокс" определенного типа и принадлежащих данному шкафу
	 * @param id сети
	 * @param тип бокса
	 */
	public HashSet<Box> getInOwnerByType(Integer OwnerId, Integer Type){
		
		HashSet<Box> elementSet = new HashSet<Box>();
		
		Iterator<?> i = this.elements().iterator();
		
		while (i.hasNext()) {
			Box element = (Box)i.next();
			if ( element.getOwnerId().equals(OwnerId)  && element.getType().equals(Type) ) {elementSet.add(element);}
		}
		
		return elementSet;
		
	}
	
	/**
	 * Возвращает коллекцию элементов "Бокс" определенного типа + универсальные, принадлежащих данному шкафу
	 * @param id сети
	 * @param тип бокса
	 */
	public HashSet<Box> getInOwnerByTypeUniversal (Integer OwnerId, Integer Type){
		
		HashSet<Box> elementSet = new HashSet<Box>();
		
		Iterator<?> i = this.elements().iterator();
		
		while (i.hasNext()) {
			Box element = (Box)i.next();
			if ( element.getOwnerId().equals(OwnerId)  && (element.getType().equals(Type) || element.getType().equals(3)) ) {elementSet.add(element);}
		}
		
		return elementSet;
	}
	/**
	 * Проверяет есть ли элемент с данным номером и типом у своего "родителя"
	 * @param номер вязующего элемента
	 * @param id родителя
	 */
	public Box inOwner (Integer elementNumber, Integer OwnerId, Integer Type){
		
		Iterator<?> i = this.elements().iterator();
		
		while (i.hasNext()) {
			Box element = (Box)i.next();
			if (element.getOwnerId().equals(OwnerId)  && element.getNumber().equals(elementNumber) && element.getType().equals(Type) ) return element;
		}
		return null;	
	}
	
}