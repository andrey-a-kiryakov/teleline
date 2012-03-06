package org.teleline.model;

/**
*Класс элементов "Distribution Frame (DFrame)".
*
*@author Кирьяков Андрей
*/
public class DFramе extends StructuredElement {
	
	/**
	 * Конструктор
	 */
	public DFramе() {
	
	}
	
	public String toString() {
		return this.getName()/* + " (id="+this.getId().toString()+")"*/;
	}
	
	
}