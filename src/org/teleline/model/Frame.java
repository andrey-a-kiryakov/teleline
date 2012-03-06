package org.teleline.model;

/**
*Класс элементов "Frame" (громполоса).
*
*@author Кирьяков Андрей
*/
public class Frame extends ConnectedPointElement {
	
	/**
	 * Конструктор
	 */
	public Frame() {
	
	}
	/**
	 * Возвращает 0 - если элемент громполоса, 1 - бокс
	 * Перегружается в громполосе и боксе
	 */
	public Integer whoIsIt () {
		return 0;
	}
	/**
	 * Строковое представление элемента
	 */
	public String toString() {
		return "ГП-"+this.getNumber().toString();
	}
	
}