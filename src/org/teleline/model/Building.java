package org.teleline.model;

/**
*Класс элементов "Building" (здание)
*
*@author Кирьяков Андрей
*/
public class Building extends StructuredElement {
	
	// name - название здания
	private String street = ""; //улица
	//snumber - номер дома
	
	/**
	 * Конструктор
	 */
	public Building() {
	
	}
	/**
	 * Устанавливает улицу здания
	 * @param street - улица
	 * @return сам объект здания
	 */
	public Building setStreet(String street) {
		this.street = street;
		return this;
	}
	/**
	 * Возвращает улицу здания
	 */
	public String getStreet() {
		return this.street;
	}
	
	public String toString() {
		
		return this.street +", "+this.getSNumber() + " " +" ("+this.getName()+")";
	}
}