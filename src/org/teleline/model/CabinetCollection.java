package org.teleline.model;

import java.util.HashSet;
import java.util.Iterator;


/**
 * Класс коллекции элементов "Cabinet" (шкаф)
 * 
 *@author Кирьяков Андрей
 */

public class CabinetCollection extends StructuredElementCollection {

	/**
	 *  Конструктор
	 */
	public CabinetCollection(IdGenerator gen) {
		super(gen);
	}
	/**
	 * Проверяет есть ли шкаф с данным строковым номеров в данной сети
	 * @param elementNumber - строковый номер шкафа
	 * @param NetId - id сети
	 * @return шкаф или null
	 */
	public Cabinet elementInNet (String elementNumber, Integer NetId){
		
		Iterator<?> i = this.elements().iterator();
		
		while (i.hasNext()) {
			Cabinet element = (Cabinet)i.next();
			if (element.getNet().equals(NetId) && element.getSNumber().intern() == elementNumber.intern()) return element;
		}
		
		return null;
		
	}
	/**
	 * Возвращает коллекцию шкафов данного класса
	 * @param cabinetClass - класс шкафа
	 */
	public HashSet<Cabinet> getByClass(Integer cabinetClass){
		
		HashSet<Cabinet> elementSet = new HashSet<Cabinet>();
		
		Iterator<?> i = this.elements().iterator();
		
		while (i.hasNext()) {
			Cabinet element = (Cabinet)i.next();
			if (element.getCabinetClass().equals(cabinetClass) ) {elementSet.add(element);}
		}
		
		return elementSet;	
	}
	
}