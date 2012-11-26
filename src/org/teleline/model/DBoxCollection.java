package org.teleline.model;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Класс коллекции элементов DBox
 * 
 *@author Кирьяков Андрей
 */

public class DBoxCollection extends StructuredElementCollection {

	public DBoxCollection(IdGenerator gen) {
		super(gen);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Возвращает коллекцию коробок в данном здании
	 * @param building - здание
	 * @return
	 */
	public HashSet<DBox> getInBuilding(Building building) {
		
		HashSet<DBox> h = new HashSet<DBox>();
		Integer buildingId = building.getId();
		
		Iterator<?> i = this.elements().iterator();
		
		while (i.hasNext()) {
			DBox element = (DBox)i.next();
			if (element.getBuilding().equals(buildingId)) {h.add(element);}
		}
		
		return h;
	}
}