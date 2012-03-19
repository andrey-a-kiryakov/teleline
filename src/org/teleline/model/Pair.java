package org.teleline.model;

/**
*Класс элементов "Pair" (Кабельная пара)
*
*@author Кирьяков Андрей
*/
public class Pair extends AbstractElement {
	private Integer cableId = 0;
	private Integer numberInCable = 0;
	private Integer fromId = 0, toId = 0;
	private Integer from = 0;
	private Integer to = 0;
	private Integer status = 0; //0 - свободная, 1 - занятая, 2 - поврежденная
//	private Integer type = 0; //0 - магистральный, 1 - межшкафная, 2 - распределительная, 3 - прямого питания
	
	private FrameCollection fc;
	private BoxCollection bc;
	private DBoxCollection dbc;
	private CableCollection cc;
	
	
	/**
	 * Конструктор
	 */
	public Pair(FrameCollection fc, BoxCollection bc, DBoxCollection dbc, CableCollection cc) {
		
		this.fc = fc;
		this.bc = bc;
		this.dbc = dbc;
		this.cc = cc;
	}
	/**
	 * Добавляет элемент "Пару" к "Кабелю"
	 * @param id кабеля
	 */
	public void attachToCable(Integer CableId){
		this.cableId = CableId;
	}
	/**
	 * Добавляет элемент "пара" к "кабелю"
	 * @param элемент "Кабель"
	 * @return элемент "Пара"
	 */
	public Pair attachToCable(StructuredElement Cable){
		this.cableId = Cable.getId();
		return this;
	}
	
	public Integer getCable () {
		return this.cableId;
	}
	
	public Pair attachToElementFrom(ConnectedPointElement Element){
		this.fromId = Element.getId();
		return this;
	}
	public Pair attachToElementFrom(Integer ElementId){
		this.fromId = ElementId;
		return this;
	}
	/**
	 * Возвращает id элемента, из которого выходит "Пара"
	 * @return
	 */
	public Integer getElementFrom () {
		return this.fromId;
	}
	
	public Pair attachToElementTo(ConnectedPointElement Element){
		this.toId = Element.getId();
		return this;
	}
	public Pair attachToElementTo(Integer ElementId){
		this.toId = ElementId;
		return this;
	}
	/**
	 * Возвращает id элемента, в который приходит "Пара"
	 * @return
	 */
	public Integer getElementTo () {
		return this.toId;
	}
	/**
	 * Устанавливает номер "Пары" в "Кабеле"
	 * @param номер в кабеле
	 */
	public Pair setNumberInCable(Integer Number) {
		this.numberInCable = Number;
		return this;
	}
	/**
	 * Возвращает номер "Пары" в кабеле
	 */
	public Integer getNumberInCable(){
		return this.numberInCable;
	}
	/**
	 * Устанавливает номер "Пары" внутри элемента, из которого выходит "Пара"
	 * @param From
	 */
	public Pair setFromNumber(Integer From){
		this.from = From;
		return this;
	}
	/**
	 * Возвращает номер "Пары" внутри элемента, из которого выходит "Пара"
	 * @return
	 */
	public Integer getFromNumber() {
		return this.from;
	}
	/**
	 * Устанавливает номер "Пары" внутри элемента, в который приходит "Пара"
	 */
	public Pair setToNumber(Integer To){
		this.to = To;
		return this;
	}
	/**
	 * Возвращает номер "Пары" внутри элемента, в который приходит "Пара"
	 */
	public Integer getToNumber() {
		return this.to;
	}
	/**
	 * Устанавливает статус "Пары"
	 * @param Статус
	 * @return
	 */
	public Pair setStatus(Integer Status) {
		this.status = Status;
		return this;
	}
	public Integer getStatus() {
		return this.status;
	}
	/**
	 * Устанавливает тип "Пары"
	 */
//	public void setType(Integer Type) {
//		this.type = Type;
//	}
	/**
	 * Возвращает тип "Пары"
	 */
//	public Integer getType() {
//		return this.type;
//	}
		
	/**
	 * Строковое представление "Пары"
	 */
	public String toString() {
		String t = "";	
		Integer type = ((Cable)cc.getElement(this.getCable())).getType();
		switch (type) {
		case 0: 
			t = fc.getElement(this.getElementFrom()).toString() + " п."+ this.getFromNumber().toString() +" - " + bc.getElement(this.getElementTo()).toString()+ " п."+this.getToNumber().toString();
			break;
		case 1: 
			t = bc.getElement(this.getElementFrom()).toString() + " п."+ this.getFromNumber().toString() +" - " + bc.getElement(this.getElementTo()).toString()+ " п."+this.getToNumber().toString();; 
			break;
		case 2: 
			t = bc.getElement(this.getElementFrom()).toString() + " п."+ this.getFromNumber().toString() +" - " + dbc.getElement(this.getElementTo()).toString()+ " п."+this.getToNumber().toString(); 
			break;
		case 3: 
			t = fc.getElement(this.getElementFrom()).toString() + " п."+ this.getFromNumber().toString() +" - " + dbc.getElement(this.getElementTo()).toString()+ " п."+this.getToNumber().toString(); 
			break;
		}
			
		return t; /*+ " (к№="+this.getNumberInCable().toString()+")";*/
	}

}