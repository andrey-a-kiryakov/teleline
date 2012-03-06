package org.teleline.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;

/**
 * Абстрактный класс коллекции структурных элементов
 * 
 *@author Кирьяков Андрей
 */
public abstract class StructuredElementCollection extends AbstractCollection {
	
	
	public StructuredElementCollection(IdGenerator gen) {
		super(gen);
		// TODO Auto-generated constructor stub
	}
	/**
	 * Возвращает коллекцию элементов принадлежащих данной сети
	 * @param NetId - id сети
	 */
	public HashSet<StructuredElement> getInNet(Integer NetId){
		
		HashSet<StructuredElement> elementSet = new HashSet<StructuredElement>();
		
		Iterator<?> i = this.elements().iterator();
		
		while (i.hasNext()) {
			StructuredElement element = (StructuredElement)i.next();
			if ( element.getNet().equals(NetId.intValue()) ) {elementSet.add(element);}
		}
		
		return elementSet;	
	}
	/**
	 * Возвращает коллекцию элементов принадлежащих данной сети
	 * @param net - сеть
	 */
	public HashSet<StructuredElement> getInNet(Net net){
		return this.getInNet(net.getId());
	}
		
	/**
	 * Сортирует коллекцию элементов по возрастанию номера
	 * @param h - неотсортированная коллекция элементов
	 * @return отсортированный вектор элементов
	 */
	public Vector<StructuredElement> sortByNumberUp(HashSet<?> h) {
		
		Integer min;
		Vector<StructuredElement> v = new Vector<StructuredElement>();
		while (h.size() > 0) {
			
			min = Integer.MAX_VALUE;
			StructuredElement minElement = null;
			Iterator<?> i = h.iterator();
			
			while (i.hasNext()) {
				StructuredElement element = (StructuredElement)i.next();
				 
				if (element.getNumber() < min) {
					min = element.getNumber();
					minElement = element;
				}	
			}
			
			v.add(minElement);
			h.remove(minElement);
		}
		
		return v;
	}
	
}