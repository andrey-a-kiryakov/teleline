package org.teleline.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;

/**
 * Класс коллекции элементов "Cable".
 * 
 *@author Кирьяков Андрей
 */

public class CableCollection extends StructuredElementCollection {
	
    
	public CableCollection(IdGenerator gen) {
		super(gen);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Проверяет есть ли кабель с данным номером и типом в структурном элементе
	 * Если находит, возвращает кабель, null - в противном случае
	 * @param ownerId - id сети
	 * @param number - номер кабеля
	 * @param type - тип кабеля
	 */
	
	public Cable getInOwner(Integer ownerId, Integer number, Integer type){
		
		Iterator<?> i = this.elements().iterator();
		
		while (i.hasNext()) {
			Cable element = (Cable)i.next();
			if ((element.getFrom().equals(ownerId) || element.getTo().equals(ownerId) ) && element.getNumber().equals(number) && element.getType().equals(type) ) return element;
		}
		return null;	
	}
	/**
	 * Возвращает коллекцию элементов "Кабель" определнного типа и принадлежащих данной сети и данным родителям
	 * @param NetId - id сети
	 * @param Type - тип кабеля
	 * @param From - id элемента, из которого выходит кабель
	 * @param To - id элемента, в который приходит кабель
	 */
	public HashSet<Cable> getInOwners(Integer Type, Integer FromId, Integer ToId){
		
		HashSet<Cable> elementSet = new HashSet<Cable>();
		
		Iterator<?> i = this.elements().iterator();
		
		while (i.hasNext()) {
			Cable element = (Cable)i.next();
			if ( element.getType().equals(Type) && element.getFrom().equals(FromId) && element.getTo().equals(ToId) ) {elementSet.add(element);}
		}
		
		return elementSet;
	}
	
	/**
	 * Возвращает коллекцию магистральных кабелей, приходящих в данный шкаф
	 * @param Шкаф
	 */
	public HashSet<Cable> getMCable(Cabinet cab){
		
		HashSet<Cable> elementSet = new HashSet<Cable>();
		Integer cabId = cab.getId();
		
		Iterator<?> i = this.elements().iterator();
		
		while (i.hasNext()) {
			Cable element = (Cable)i.next();
			if ( (element.getTo().equals(cabId)) && (element.getType().equals(0)) ) {elementSet.add(element);}
		}
		
		return elementSet;
	}
	
	/**
	 * Возвращает коллекцию межшкафных кабелей, приходящих в данный шкаф
	 * @param Шкаф
	 */
	public HashSet<Cable> getICableIn(Cabinet cab){
		
		HashSet<Cable> elementSet = new HashSet<Cable>();
		Integer cabId = cab.getId();
		
		Iterator<?> i = this.elements().iterator();
		
		while (i.hasNext()) {
			Cable element = (Cable)i.next();
			if ( (element.getTo().equals(cabId)) && (element.getType().equals(1)) ) {elementSet.add(element);}
		}
		
		return elementSet;
	}
	
	/**
	 * Возвращает коллекцию межшкафных кабелей, выходящих из данного шкафа
	 * @param Шкаф
	 */
	public HashSet<Cable> getICableOut(Cabinet cab){
		
		HashSet<Cable> elementSet = new HashSet<Cable>();
		Integer cabId = cab.getId();
		
		Iterator<?> i = this.elements().iterator();
		
		while (i.hasNext()) {
			Cable element = (Cable)i.next();
			if ( (element.getFrom().equals(cabId)) && (element.getType().equals(1)) ) {elementSet.add(element);}
		}
		
		return elementSet;
	}
	
	/**
	 * Возвращает кабель приходящий в данную коробку
	 */
	/*public Cable getDCableIn(DBox dbox){
		
		//HashSet<Cable> elementSet = new HashSet<Cable>();
		Integer ownerToId = dbox.getId();
		Pair p = pc.getInPlace(this, 0);
		Iterator<?> i = this.elements().iterator();
		
		while (i.hasNext()) {
			Cable element = (Cable)i.next();
			if ( element.getTo().equals(ownerToId) ) {return element;}
		}
		
		return null;
	}*/
	
	/**
	 * Возвращает коллекцию распределительных/прямого питания кабелей, выходящих из данного элемента
	 */
	public HashSet<Cable> getDCableOut(StructuredElement owner){
		
		HashSet<Cable> elementSet = new HashSet<Cable>();
		Integer ownerId = owner.getId();
		
		Iterator<?> i = this.elements().iterator();
		
		while (i.hasNext()) {
			Cable element = (Cable)i.next();
			if ( (element.getFrom().equals(ownerId)) &&  ( (element.getType().equals(2)) ||  (element.getType().equals(3)) ) ) {elementSet.add(element);}
		}
		
		return elementSet;
	}
	
	/**
	 * Ищет кабели, строковые представления которых начинаются с заданной последовательности символов
	 * Регистр символов не учитывается
	 * @param snumber - последовательность симаолов
	 * @param NetId - id сети
	 * @return коллекция элементов
	 */
	public HashSet<StructuredElement> searchBySNumber (String snumber, Integer NetId){
		
		HashSet<StructuredElement> h = new HashSet<StructuredElement>();
		
		Iterator<?> i = this.elements().iterator();
		
		while (i.hasNext()) {
			StructuredElement element = (StructuredElement)i.next();
			if (element.getNet().equals(NetId) && element.toString().toLowerCase().indexOf(snumber) == 0) h.add(element);
		}
		
		return h;
		
	}
	/**
	 * Возвращает коллекцию кабелей, проходящих черезе данный канал в канализации
	 * @param duct - участок кабельной канализации
	 * @return вектор элементов Кабель
	 */
	public Vector<Cable> getTubesCables(Tube tube) {
		
		Vector<Cable> v = new Vector<Cable>();
		Iterator<Integer> i = tube.getCables().iterator();
		
		while (i.hasNext()) {v.add((Cable)this.getElement(i.next()));}
		
		return v;
	}
	
	/**
	 * Возвращает коллекцию кабелей в данном элементе
	 * @param owner - структурный элемент
	 * @return HashSet элементов Кабель
	 */
	public HashSet<Cable> getCables(StructuredElement owner) {
		
		HashSet<Cable> elementSet = new HashSet<Cable>();
		Integer ownerId = owner.getId();
		
		Iterator<?> i = this.elements().iterator();
		
		while (i.hasNext()) {
			Cable element = (Cable)i.next();
			if ( element.getFrom().equals(ownerId) || element.getTo().equals(ownerId)) {elementSet.add(element);}
		}
		
		return elementSet;
	}
	
}