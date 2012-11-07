package org.teleline.model;


/**
 * Класс  элементов "Абонент"
 * 
 *@author Кирьяков Андрей
 */
public class Subscriber extends StructuredElement{
	
	private String phoneNumber = "";
	private String adress = "";
	private String date = "";
	private String equipment = "";
	
	
	/**
	 * Конструктор
	 */
	public Subscriber(){
		
	}
	/**
	 * Устанавливает телефонный номер "Абонента"
	 */
	public Subscriber setPhoneNumber (String newPhoneNumber) {
		this.phoneNumber = newPhoneNumber;
		return this;
	}
	/**
	 * Возвращает телефонный номер "Абонента"
	 */
	public String getPhoneNumber(){
		return this.phoneNumber;
	}
	/**
	 * Устанавливает дату установки абонента
	 * @param newDate - дата
	 * @return абонент
	 */
	public Subscriber setDate(String newDate){
		this.date = newDate;
		return this;
	}
	/**
	 * Возвращает дату установки абонента
	 */
	public String getDate() {
		return this.date;
	}
	/**
	 * Устанавливает адресс абонента
	 * @param newAdress - адресс
	 * @return абонент
	 */
	public Subscriber setAdress(String newAdress){
		this.adress = newAdress;
		return this;
	}
	/**
	 * Возвращает адресс абонента
	 */
	public String getAdress() {
		return this.adress;
	}
	/**
	 * Устанавливает оборудование абонента
	 * @param newAdress - адресс
	 * @return абонент
	 */
	public Subscriber setEquipment(String newEquipment){
		this.equipment = newEquipment;
		return this;
	}
	/**
	 * Возвращает оборудование абонента
	 */
	public String getEquipment() {
		return this.equipment;
	}

	/**
	 * Строкое представление элемента
	 */
	public String toString() {
		return this.getName();/* +" (№="+this.getPhoneNumber().toString()+")";*/
	}
	
	
}