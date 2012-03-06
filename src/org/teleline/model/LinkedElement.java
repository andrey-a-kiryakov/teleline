package org.teleline.model;

/**
*Суперкласс связующих элементов (кабель, участок кабельной канализации между колодцами)
*
*@author Кирьяков Андрей
*/
public abstract class LinkedElement extends StructuredElement{
	private Integer from = 0;
	private Integer to = 0;
	protected Integer capacity = 0;
	/**
	 * Конструктор
	 */
	public LinkedElement(){
		
	}
	
	/**
	 * Устанавливает элемент, в который приходит связующий элемент
	 */
	public LinkedElement setTo(Integer ToId){
		this.to = ToId;
		return this;
	}
	/**
	 * Возвращает элемент, в который приходит связующий элемент
	 */
	public Integer getTo () {
		return this.to;
	}
	/**
	 * Устанавливает элемент, из которого выходит связующий элемент 
	 */
	public LinkedElement setFrom(Integer FromId){
		this.from = FromId;
		return this;
	}
	/**
	 * Возвращает элемент, из которого выходит связующий элемент
	 */
	public Integer getFrom () {
		return this.from;
	}
	
	/**
	 * Устанавливает емкость связующего элемента
	 * @param емкость элемента
	 * @return элемент
	 */
	public LinkedElement setCapacity (Integer capacity) {
		this.capacity = capacity;
		return this;
	}
	/**
	 * Возвращает емкость связующего элемента
	 */
	public Integer getCapacity () {
		return this.capacity;
	}
	
	
}