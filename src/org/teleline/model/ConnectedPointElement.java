package org.teleline.model;

/**
*Абстрактный класс элементов (громполосы, боксы), через которые связываются стурктурные элементы 
*
*@author Кирьяков Андрей
*/
public abstract class ConnectedPointElement extends AbstractElement{
	private Integer ownerId = 0;
	private Integer capacity = 100;
	private Integer number = 0;
	//private Integer usedCapacityTop = 0;
	//private Integer usedCapacityBottom = 0;
	private Integer placeNumber = 0;
	
	/**
	 * Конструктор
	 */
	public ConnectedPointElement(){
		
	}
	public ConnectedPointElement attachTo(Integer OwnerId) {
		this.ownerId = OwnerId;
		return this;
	}
	public ConnectedPointElement attachTo(StructuredElement Owner) {
		this.ownerId = Owner.getId();
		return this;
	}
	/**
	 * Возвращает id родителя
	 */
	public Integer getOwnerId() {
		return this.ownerId;
	}
	/**
	 * Устанавливает номер элемента
	 * @param номер элемента
	 */
	public ConnectedPointElement setNumber(Integer NewNumber){
		this.number = NewNumber;
		return this;
	}
	/**
	 * Возвращает номер элемента
	 * @return номер элемента
	 */
	public Integer getNumber() {
		return this.number;
	}
	/**
	 * Устанавливает емкость элемента в "Парах"
	 * @param емкость элемента
	 * @return элемент
	 */
	public ConnectedPointElement setCapacity (Integer capacity) {
		this.capacity = capacity;
		return this;
	}
	/**
	 * Возвращает емкость елемента в "Парах"
	 */
	public Integer getCapacity () {
		return this.capacity;
	}
	/**
	 * Устанавливает номер места в родителе
	 * @param номер места
	 */
	public ConnectedPointElement setPlaceNumber(Integer number) {
		this.placeNumber = number;
		return this;
	}
	/**
	 * Возвращает номер места в родителе
	 * @param номер места
	 */
	public Integer getPlaceNumber() {
		return this.placeNumber;
	}
	/**
	 * Возвращает тип элемента. Перегружается в боксе
	 * @return
	 */
	public Integer getType() {
		return 0;
	}
	/**
	 * Возвращает 0 - если элемент громполоса, 1 - бокс
	 * Перегружается в громполосе и боксе
	 */
	public Integer whoIsIt () {
		return -1;
	}
	/**
	 * Строковое представление элемента
	 */
	public String toString() {
		return "Номер " + this.number.toString() + " (id="+this.getId().toString()+", Емкость="+this.capacity.toString()+")";
	}
	
}