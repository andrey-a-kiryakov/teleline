package org.teleline.model;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Класс коллекции элементов "Path"
 * 
 *@author Кирьяков Андрей
 */

public class PathCollection extends AbstractCollection {
	
    
	public PathCollection(IdGenerator gen) {
		super(gen);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 *Возвращает коллекцию включений данного абонента 
	 * @param sub - абонент
	 * @return коллекция включений
	 */
	public HashSet<Path> getSubscriberPaths(Subscriber sub){
		
		HashSet<Path> elementSet = new HashSet<Path>();
		Integer subId = sub.getId();
		
		Iterator<?> i = this.elements().iterator();
		
		while (i.hasNext()) {
			Path element = (Path)i.next();
			if ( element.getSubscriber().equals(subId) ) {elementSet.add(element);}
		}
		
		return elementSet;	
	}
	/**
	 * Возвращает коллекцию включений, задействующих данную пару
	 * @param pair - пара
	 * @return коллекция включений
	 */
	public HashSet<Path> getPairsPath(Pair pair) {
		
		HashSet<Path> elementSet = new HashSet<Path>();
		
		Iterator<?> i = this.elements().iterator();
		
		while (i.hasNext()) {
			Path element = (Path)i.next();
			if (element.isPairUsed(pair) == true) {elementSet.add(element);}
		}
		return elementSet; 
	}
	/**
	 * Проверяет используется ли пара в каком-либо включении
	 * @param pair - пара
	 * @return
	 */
	public Path isPairUsed(Pair pair) {
		
		Iterator<?> i = this.elements().iterator();
		while (i.hasNext()) {
			Path element = (Path)i.next();
			if (element.isPairUsed(pair)) return element;	
		}
		
		return null;
	}
}