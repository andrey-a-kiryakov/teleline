package org.teleline.model;

/**
*Класс элементов "Cabinet" (шкаф).
*
*@author Кирьяков Андрей
*/
public class Cabinet extends StructuredElement {
	
	private Integer cabinetClass = 1; //класс шкафа 1 или 2
	private String adress = "";
	private String place = "";
	private String material = "";
	private String date = "";
	private Integer setup = 0; //0 - без шкафной коробки, 1 - со шкафной коробкой
	private Integer area = 0; //0 - уличный, 1 - в помещении
	
	/**
	 * Конструктор
	 */
	public Cabinet() {
	
	}
	/**
	 * Устанавливает класс шкафа
	 * @param newClass - новый класс
	 * @return шкаф
	 */
	public Cabinet setCabinetClass(Integer newClass) {
		this.cabinetClass = newClass;
		return this;
	}
	/**
	 * Возвращает класс шкафа
	 */
	public Integer getCabinetClass(){
		return this.cabinetClass;
	}
	/**
	 * Устанавливает место установки шкафа
	 * @param newPlace - место установки
	 * @return шкаф
	 */
	public Cabinet setPlace(String newPlace){
		this.place = newPlace;
		return this;
	}
	/**
	 * Возвращает место установки шкафа
	 */
	public String getPlace() {
		return this.place;
	}
	/**
	 * Устанавливает адресс шкафа
	 * @param newAdress - адресс
	 * @return шкаф
	 */
	public Cabinet setAdress(String newAdress){
		this.adress = newAdress;
		return this;
	}
	/**
	 * Возвращает адресс шкафа
	 */
	public String getAdress() {
		return this.adress;
	}
	/**
	 * Устанавливает материал шкафа
	 * @param newMaterial - материал
	 * @return шкаф
	 */
	public Cabinet setMaterial(String newMaterial){
		this.material = newMaterial;
		return this;
	}
	/**
	 * Возвращает материал шкафа
	 */
	public String getMaterial() {
		return this.material;
	}
	/**
	 * Устанавливает дату установки шкафа
	 * @param newDate - дата
	 * @return шкаф
	 */
	public Cabinet setDate(String newDate){
		this.date = newDate;
		return this;
	}
	/**
	 * Возвращает дату установки шкафа
	 */
	public String getDate() {
		return this.date;
	}
	/**
	 * Устанавливает способ установки шкафа
	 * @param newSetup - способ установки (0 или 1)
	 * @return шкаф
	 */
	public Cabinet setSetup(Integer newSetup){
		this.setup = newSetup;
		return this;
	}
	/**
	 * Возвращает способ установки шкафа в виде целого числа
	 */
	public Integer getSetup() {
		return this.setup;
	}
	/**
	 * Возвращает способ установки шкафа в виде описательной строки
	 */
	public String getSetupText() {
		
		if (this.setup.equals(0))  { return "Без шкафной коробки";}
		else {return "Со шкафной коробкой"; }	
	}
	/**
	 * Устанавливает уличный или в помещении шкаф
	 * @param newArea - уличный или в помещении (0 или 1)
	 * @return шкаф
	 */
	public Cabinet setArea(Integer newArea){
		this.area = newArea;
		return this;
	}
	/**
	 * Возвращает уличный или в помещении шкаф в виде целого числа
	 */
	public Integer getArea() {
		return this.area;
	}
	/**
	 * Возвращает уличный или в помещении шкаф в виде описательной строки
	 */
	public String getAreaText() {
		
		if (this.area.equals(0))  { return "Уличный";}
		else {return "В помещении"; }	
	}
	/**
	 * Строковое представление элемента
	 */
	public String toString() {
		return "Шк. "+ this.getSNumber();
	}
	
}