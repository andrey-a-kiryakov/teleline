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
	
	/*public ConnectedPointElement setUsedCapacityTop (Integer usedCapacity) {
		this.usedCapacityTop = usedCapacity;
		return this;
	}*/
	/*public Integer getUsedCapacityTop () {
		return this.usedCapacityTop;
	}
	*/
	
	/*public ConnectedPointElement setUsedCapacityBottom (Integer usedCapacity) {
		this.usedCapacityBottom = usedCapacity;
		return this;
	}*/
	/*public Integer getUsedCapacityBottom () {
		return this.usedCapacityBottom;
	}*/
	/*public Integer getUsedCapacity () {
		return this.usedCapacityBottom + this.usedCapacityTop;
	}*/
	
	/**
	 * Проверяет, можно ли присоединить к элементу данное количество "Пар",
	 * @param количество присоединяемых "Пар"
	 */
/*	public boolean isConnect(Integer pairCount) {
		if ((this.capacity - this.usedCapacityTop - this.usedCapacityBottom) >= pairCount) return true;
		return false;
	}*/
	/**
	 * Присоединяеть к элементу (заполнение сверху) данное количество "Пар"
	 * @param количество присоединяемых "Пар"
	 * @return номер первой присоединеной пары
	 */
	/*public Integer connectTop(Integer pairCount) { 
			this.usedCapacityTop += pairCount; 
			return this.usedCapacityTop - pairCount + 1;
	}*/
	
	/**
	 * Присоединяет к элементу (заполнение снизу) данное количество "Пар",
	 * @param количество присоединяемых "Пар"
	 * @return номер первой присоединеной пары
	 */
	/*public Integer connectBottom(Integer pairCount) { 
			this.usedCapacityBottom += pairCount; 
			return this.capacity - this.usedCapacityBottom + 1;	
	}*/
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