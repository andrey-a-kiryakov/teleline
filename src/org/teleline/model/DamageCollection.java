package org.teleline.model;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Класс коллекции элементов "Damage"
 * 
 *@author Кирьяков Андрей
 */
public  class DamageCollection extends AbstractCollection {

	public DamageCollection(IdGenerator gen) {
		super(gen);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Возвращает коллекцию повреждений элемента
	 * @param owner - элемент
	 */
	public HashSet<Damage> getDamages(AbstractElement owner) {
		
		HashSet<Damage> elementSet = new HashSet<Damage>();
		
		if (owner != null) {
			Integer ownerId = owner.getId();
		
			Iterator<?> i = this.elements().iterator();
			while (i.hasNext()) {
				Damage element = (Damage)i.next();	
				if (element.getOwnerId().equals(ownerId)) {elementSet.add(element);}
			}
		}
		return elementSet;
	}
}