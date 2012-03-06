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
     * Конструктор
     */
	public CabinetCollection(IdGenerator gen) {
		super(gen);
		// TODO Auto-generated constructor stub
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
	 * Возвращает коллекцию элементов шкаф принадлежащих данной сети и данного класса
	 * @param NetId - id сети
	 * @param cabinetClass - класс шкафа, если 0, то возвращаются все шкафы в сети
	 */
	public HashSet<Cabinet> getInNetByClass(Integer NetId, Integer cabinetClass){
		
		HashSet<Cabinet> elementSet = new HashSet<Cabinet>();
		
		Iterator<?> i = this.elements().iterator();
		
		if (cabinetClass > 0)
		while (i.hasNext()) {
			Cabinet element = (Cabinet)i.next();
			if (element.getNet().equals(NetId) && element.getCabinetClass().equals(cabinetClass) ) {elementSet.add(element);}
		}
		
		if (cabinetClass.equals(0))
			while (i.hasNext()) {
				Cabinet element = (Cabinet)i.next();
				if (element.getNet().equals(NetId)) {elementSet.add(element);}
			}
			
		return elementSet;	
	}
	
}