package org.teleline.model;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Класс коллекции элементов "Абонент"
 * 
 *@author Кирьяков Андрей
 */

public class SubscriberCollection extends StructuredElementCollection {

	public SubscriberCollection(IdGenerator gen) {
		super(gen);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Проверяет есть ли абонент с таким телефонным номером, если находит возвращает элемент, в противном случае null
	 * @param PhoneNumber - номер телефона
	 * @param NetId - id сети
	 */
	public StructuredElement findByPhoneNumber (String PhoneNumber, Integer NetId){
		
		Iterator<?> i = this.elements().iterator();
		
		while (i.hasNext()) {
			Subscriber element = (Subscriber)i.next();
			if (element.getNet().equals(NetId) && (element.getPhoneNumber().intern() == PhoneNumber.intern())) return element;
		}
		
		return null;
		
	}
	/**
	 * Ищет абонентов, телефонные номера которых начинаются с заданной последовательности символов
	 * @param phone - последовательность символов для поиска
	 */
	public HashSet<Subscriber> searchByPhone (String phone){
		
		HashSet<Subscriber> h = new HashSet<Subscriber>();
		
		Iterator<?> i = this.elements().iterator();
		
		while (i.hasNext()) {
			Subscriber element = (Subscriber)i.next();
			if (element.getPhoneNumber().indexOf(phone) == 0) h.add(element);
		}
		return h;
		
	}
	/**
	 * Ищет абонентов, мена которых начинаются с заданной последовательности символов.
	 * Регистр символов не учитывается.
	 * @param name - последовательность символов для поиска
	 */
	public HashSet<Subscriber> searchByName (String name){
		
		HashSet<Subscriber> h = new HashSet<Subscriber>();
		
		Iterator<?> i = this.elements().iterator();
		
		while (i.hasNext()) {
			Subscriber element = (Subscriber)i.next();
			if (element.getName().toLowerCase().indexOf(name.toLowerCase()) == 0) h.add(element);
		}
		return h;
		
	}
	
}