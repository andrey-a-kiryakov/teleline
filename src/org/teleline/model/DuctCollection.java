package org.teleline.model;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Класс коллекции элементов "Duct"
 * 
 *@author Кирьяков Андрей
 */

public class DuctCollection extends StructuredElementCollection {
	
    
	public DuctCollection(IdGenerator gen) {
		super(gen);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Возвращает коллекцию участков канализации, примыкающих к данному элементу c данной стороны
	 * @param man - колодец, шкаф, здание
	 * @param side - сторона
	 */
	public HashSet<Duct> getDuctsBySide(StructuredElement man, Integer side) {
		
		Integer manId = man.getId();
		HashSet<Duct> h = new HashSet<Duct>();
		Iterator<?> i = this.elements().iterator();
		Duct element = null;
		
		while (i.hasNext()) {
			element = (Duct)i.next();
			if ((element.getFrom().equals(manId) && element.getFromSide().equals(side)) || (element.getTo().equals(manId) && element.getToSide().equals(side))) {h.add(element);}
		}
		return h; 
	}
	/**
	 * Возвращает коллекцию участков канализации, примыкающих к данному элементу
	 * @param man - колодец, шкаф, здание
	 */
	public HashSet<Duct> getDucts(StructuredElement man) {
		
		Integer manId = man.getId();
		HashSet<Duct> h = new HashSet<Duct>();
		Iterator<?> i = this.elements().iterator();
		
		while (i.hasNext()) {
			Duct element = (Duct)i.next();
			if (element.getFrom().equals(manId) || element.getTo().equals(manId) ) {h.add(element);}
		}
		
		return h;
	}
	/**
	 * Возвращает коллекцию участков канализации, приходящих в данный элемент
	 * @param man - колодец, шкаф, здание
	 */
	public HashSet <Duct> getInDuct(StructuredElement man) {
		
		Integer manId = man.getId();
		HashSet<Duct> h = new HashSet<Duct>();
		Iterator<?> i = this.elements().iterator();
		
		while (i.hasNext()) {
			Duct element = (Duct)i.next();
			if (element.getTo().equals(manId) ) {h.add(element);}
		}
		
		return h;
	}
	/**
	 * Возвращает коллекцию участков канализации, исходящих из данного элемента
	 * @param man - колодец, шкаф, здание
	 */
	public HashSet <Duct> getOutDuct(StructuredElement man) {
		
		Integer manId = man.getId();
		HashSet<Duct> h = new HashSet<Duct>();
		Iterator<?> i = this.elements().iterator();
		
		while (i.hasNext()) {
			Duct element = (Duct)i.next();
			if (element.getFrom().equals(manId) ) {h.add(element);}
		}
		
		return h;
	}
	
}