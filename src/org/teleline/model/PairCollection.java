package org.teleline.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;

/**
 * Класс коллекции элементов "Paire" (пара)
 * 
 *@author Кирьяков Андрей
 */

public class PairCollection extends AbstractCollection {
	
    
	public PairCollection(IdGenerator gen) {
		super(gen);
		// TODO Auto-generated constructor stub
	}
	/**
	 * Возвращает коллекцию элементов "Пара" исходящих или приходящих в данный элемент
	 * @param Owner - элемент, в котором присутвуют пары
	 */
	public HashSet<Pair> getInOwner(ConnectedPointElement Owner){
		HashSet<Pair> elementSet = new HashSet<Pair>();
		Integer OwnerId = Owner.getId();
		
		Iterator<?> i = this.elements().iterator();
		
		while (i.hasNext()) {
			Pair element = (Pair)i.next();
			if (  element.getElementFrom().equals(OwnerId) || element.getElementTo().equals(OwnerId)   ) {elementSet.add(element);}
		}
		
		return elementSet;
	}
	
	public HashSet<Pair> getInOwner(DBox Owner){
		HashSet<Pair> elementSet = new HashSet<Pair>();
		Integer OwnerId = Owner.getId();
		
		Iterator<?> i = this.elements().iterator();
		
		while (i.hasNext()) {
			Pair element = (Pair)i.next();
			if (  element.getElementFrom().equals(OwnerId) || element.getElementTo().equals(OwnerId)   ) {elementSet.add(element);}
		}
		
		return elementSet;
	}
	/**
	 * Возвращает коллекцию элементов "Пара" находящихся в данном кабеле
	 * @param cable - кабель
	 */
	public HashSet<Pair> getInCable(Cable cable) {
		HashSet<Pair> elementSet = new HashSet<Pair>();
		
		Integer CableId = cable.getId();
		Iterator<?> i = this.elements().iterator();
		
		while (i.hasNext()) {
			Pair element = (Pair)i.next();
			if ( element.getCable().equals(CableId)) {elementSet.add(element);}
		}
		
		return elementSet;
	}
	
	/**
	 * Возвращает коллекцию элементов "Пара" определенного состояния находящихся в данном кабеле
	 * @param cable - кабель
	 */
	public HashSet<Pair> getInCable(Cable cable, Integer status) {
		HashSet<Pair> elementSet = new HashSet<Pair>();
		
		Integer CableId = cable.getId();
		Iterator<?> i = this.elements().iterator();
		
		while (i.hasNext()) {
			Pair element = (Pair)i.next();
			if ( element.getCable().equals(CableId) && element.getStatus().equals(status)) {elementSet.add(element);}
		}
		return elementSet;
	}
	
	/**
	 * Возвращает пару под определенном номеров в кабеле
	 * @param cable - кабель
	 * @param placeNumber - номер в кабеле
	 * @return
	 */
	public Pair getInPlace(Cable cable, Integer placeNumber) {
		
		Iterator<?> i = this.elements().iterator();
		Integer cableId = cable.getId();
		
		while (i.hasNext()) {
			Pair pair = (Pair)i.next();
			if ( pair.getCable().equals(cableId) && pair.getNumberInCable().equals(placeNumber)) return pair;
		}
		return null;
	}
	/**
	 * Возвращает пару, занимающую определенное место в родителе
	 * Если место не занято, возвращает null. 
	 * @param Owner - элемент-родитель
	 * @param placeNumber - номер места
	 */
	public Pair getInPlace (AbstractElement Owner, Integer placeNumber) {
		
		Iterator<?> i = this.elements().iterator();
		Integer ownerId = Owner.getId();
		
		while (i.hasNext()) {
			Pair pairt = (Pair)i.next();
			if ( (pairt.getFromNumber().equals(placeNumber) && pairt.getElementFrom().equals(ownerId)) || (pairt.getToNumber().equals(placeNumber) && pairt.getElementTo().equals(ownerId))) return pairt;
		}
		return null;
	}
	/**
	 * Возвращает коллекцию элементов "Пара" данного состояния, исходящих или приходящих в данный элемент
	 * @param id элемента, в котором присутвуют пары
	 * @param Status - статус пары
	 * @return
	 */
/*	public HashSet<Pair> getInOwnerByStatus(ConnectedPointElement Owner, Integer Status){
		HashSet<Pair> elementSet = new HashSet<Pair>();
		Integer OwnerId = Owner.getId();
		
		Iterator<?> i = this.elements().iterator();
		
		while (i.hasNext()) {
			Pair element = (Pair)i.next();
			if ( (element.getStatus().equals(Status)) && (element.getElementFrom().equals(OwnerId) || (element.getElementTo().equals(OwnerId)) )   ) {elementSet.add(element);}
		}
		
		return elementSet;
	}*/
		
	/**
	 * Сортирует коллекцию элементов "Пара" по исходящему номеру
	 * @param набор неотсортированных элементов
	 * @return набор отсортированных элементов
	 */
	public Vector<Pair> sortByFrom(HashSet<Pair> h) {
		
		Integer min;
		Vector<Pair> v = new Vector<Pair>();

		while (h.size() > 0) {
			
			min = Integer.MAX_VALUE;
			Pair minElement = null;
			Iterator<?> i = h.iterator();
			
			while (i.hasNext()) {
				Pair element = (Pair)i.next();
				 
				if (element.getFromNumber() < min) {
					min = element.getFromNumber();
					minElement = element;
				}	
			}
			
			v.add(minElement);
			h.remove(minElement);
		}
		
		return v;
		
	}
	
}