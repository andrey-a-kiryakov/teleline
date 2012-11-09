package org.teleline.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;

/**
 * Абстрактный суперкласс классов коллекции
 * 
 *@author Кирьяков Андрей
 */
public abstract class AbstractCollection {
	private HashSet<AbstractElement> elements = new HashSet<AbstractElement>();
	private IdGenerator generator;
	
	/**
	 * Конструктор
	 */
	public AbstractCollection(IdGenerator gen) {
		
		this.generator = gen;
	}
	/**
	 * Добавляет новый элемент в коллекцию, индекс назначается автоматически
	 */
	public void addElement (AbstractElement element) {
		element.setId(this.generator.getNewId());
		this.elements.add(element);		
	}
	/**
	 * Добавляет новый элемент в коллекцию 
	 */
	public void putElement(AbstractElement element){
		this.elements.add(element);
	}
	/**
	 * Возвращает элемент коллекции, null - если элемент ненайден
	 * @param id - индекс элемента
	 */
	public AbstractElement getElement (Integer id) {
		
		Iterator<AbstractElement> i = this.elements.iterator();
		AbstractElement element = null;
		
		while (i.hasNext()){
			element = i.next();
			if (element.getId().equals(id)) return element;
		}
		
		return null;			
	}
	/**
	 * Удаляет елемент из коллекции
	 * @param элемент
	 */
	public boolean removeElement(AbstractElement element) {
		return this.elements.remove(element);
	}
	/**
	 * Удаляет елемент из коллекции
	 * @param индекс элемента
	 */
	public boolean removeElement(Integer id) {
		
		Iterator<AbstractElement> i = this.elements.iterator();
		
		while (i.hasNext()){
			AbstractElement element = i.next();
			if (element.getId().equals(id)) { 
				this.elements.remove(element);
				return true;
			}
		}
		
		return false;
				
	}
	/**
	 * Удаляет все элементы коллекции
	 */
	public void removeAllElements() {
		this.elements.clear();
	}
	/**
	 * Удаляет из коллекции все элементы, перечисленные в коллекции
	 * @param c - коллекция элементов для удаления
	 */
	public void removeAllElements(Collection<AbstractElement> c) {
		this.elements.removeAll(c);
	}
	/**
	 * Возвращает копию всех элементы коллекции
	 */
	@SuppressWarnings("unchecked")
	public HashSet<AbstractElement> elements() {
		return (HashSet<AbstractElement>) this.elements.clone();
	}
	/**
	 * Возвращает итератор по всем элементам коллекции
	 */
	public Iterator<AbstractElement> getIterator() {
		return this.elements.iterator();
	}
	/**
	 * Возвращает размер коллекции
	 */
	public int getSize() {
		return elements.size();
	}
	/**
	 * Возвращает новую коллекцию, элементы которой отсортированя по id по возрастанию
	 *@param h - неотсортированный набор элементов
	 */
	public Vector<AbstractElement> sortByIdUp(HashSet<?> h){
	
		Integer min;
		Vector<AbstractElement> v = new Vector<AbstractElement>();
		
		while (h.size() > 0) {
			
			min = Integer.MAX_VALUE;
			AbstractElement minElement = null;
			Iterator<?> i = h.iterator();
			
			while (i.hasNext()) {
				AbstractElement element = (AbstractElement)i.next();
				 
				if (element.getId() < min) {
					min = element.getId();
					minElement = element;
				}	
			}
			
			v.add(minElement);
			h.remove(minElement);
		}
		
		return v;
	}
}
