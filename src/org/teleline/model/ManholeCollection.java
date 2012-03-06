package org.teleline.model;

import java.util.Iterator;

/**
 * Класс коллекции элементов DFrame.
 * 
 *@author Кирьяков Андрей
 */

public class ManholeCollection extends StructuredElementCollection {

	public ManholeCollection(IdGenerator gen) {
		super(gen);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Проверяет есть ли колодец с данным строковым номеров в данной сети
	 * @param elementNumber - строковый номер колодца
	 * @param NetId - id сети
	 * @return колодец или null
	 */
	public Manhole elementInNet (String elementNumber, Integer NetId){
		
		Iterator<?> i = this.elements().iterator();
		
		while (i.hasNext()) {
			Manhole element = (Manhole)i.next();
			if (element.getNet().equals(NetId) && element.getSNumber().intern() == elementNumber.intern()) return element;
		}
		
		return null;
		
	}
   
	
}