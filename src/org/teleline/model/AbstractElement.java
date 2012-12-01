package org.teleline.model;

/**
*Абстрактный класс элементов ЛКХ
*
*@author Кирьяков Андрей
*/
public abstract class AbstractElement {
	private Integer id = 0;
	
	/**
	 * Конструктор
	 */
	public AbstractElement(){
		
	}
	
	public void setId (Integer Id) {
		this.id = Id;
	}
	
	public Integer getId () {
		return this.id;
	}
	
	public Integer whoIsIt () {
		return -1;
	}
	
	public String toString() {
		return "";
	}
	
}