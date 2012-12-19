package org.teleline.model;

/**
*Класс элементов "Вох" (бокс в шкафу)
*
*@author Кирьяков Андрей
*/
public class Box extends ConnectedPointElement {
	/**
	 * Тип бокса: 0 - магистральный, 1 - межшкафной, 2 - распределительный, 3 - комбинированный
	 */
	private Integer type = 0;
	/**
	 * Конструктор
	 */
	public Box() {
	
	}
	/**
	 * Устанавливает тип бокса
	 */
	public void setType(Integer newType){
		this.type = newType;
	}
	/**
	 * Возвращает тип бокса
	 */
	public Integer getType() {
		return this.type;
	}
	/**
	 * Возвращает 0 - если элемент громполоса, 1 - бокс
	 * Перегружается в громполосе и боксе  и коробке
	 */
	public Integer whoIsIt () {
		return 1;
	}
	
	
	public String toString() {
		String s = "МБ";
		
		switch (this.type.intValue()) {
		case 1: s = "РБпер"; break;
		case 2: s = "РБ"; break;
		case 3: s = "К"; break;
		
		}
		
		return s+"-"+ this.getNumber().toString();
	}
	
}