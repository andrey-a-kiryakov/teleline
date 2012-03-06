package org.teleline.model;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Класс коллекции элементов "Tube"
 * 
 *@author Кирьяков Андрей
 */

public class TubeCollection extends AbstractCollection {
	
    
	public TubeCollection(IdGenerator gen) {
		super(gen);
		// TODO Auto-generated constructor stub
	}
	/**
	 * Возвращает коллекцию каналов, принадлежащих данной канализации
	 * @param duct
	 * @return
	 */
	public HashSet<Tube> getDuctsTubes(Duct duct){
		
		HashSet<Tube> elementSet = new HashSet<Tube>();
		Integer ductId = duct.getId();
		
		Iterator<?> i = this.elements().iterator();
		
		while (i.hasNext()) {
			Tube element = (Tube)i.next();
			if (element.getDuct().equals(ductId)) {elementSet.add(element);}
		}
		
		return elementSet;
	}
	
	/**
	 * Возвращает канал определенного номера, принадлежащий канализации
	 * @param h - коллекция каналов, принадлежих канализации
	 * @param number - номер канала
	 */
	public Tube getDuctByNumber(HashSet<Tube> h, Integer number){
		
		Iterator<Tube> i = h.iterator();
		
		while (i.hasNext()) {
			Tube element = (Tube)i.next();
			if (element.getNumber().equals(number)) return element;
		}
		return null;
	}
	/**
	 * Возвращает коллекцию каналов, по которым проходит кабель
	 * @param cable - кабель
	 * @return коллекция каналов
	 */
	public HashSet<Tube> getTubesByCable(Cable cable) {
		
		HashSet<Tube> h = new HashSet<Tube>();
		Iterator<?> i = this.elements().iterator();
		
		while (i.hasNext()) {
			Tube element = (Tube)i.next();
			if (element.containsCable(cable)) {h.add(element);}
		}
		return h;
	}
	/**
	 * Возвращает канал в данной канализации, по которому проходит кабель
	 * @param cable - кабель
	 * @param duct - канализация
	 */
	public Tube getTubeBuCableDuct(Cable cable, Duct duct) {
		
		Integer ductId = duct.getId();
		Iterator<?> i = this.elements().iterator();
		
		while (i.hasNext()) {
			Tube element = (Tube)i.next();
			if (element.getDuct().equals(ductId) && element.containsCable(cable)) {return element;}
		}
		return null;
	}
}
