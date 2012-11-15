package org.teleline.model;

/**
*Класс элементов "Duct" (кабельная канализация)
*
*@author Кирьяков Андрей
*/
public class Duct extends LinkedElement {
	private Integer fromSide = 0;
	private Integer toSide = 0;
	private Integer lenght = 0;
	private Integer tubeDiametr = 0;
	private String tubeMaterial = "";
	private String date = "";
	private String method = "";
	
	private ManholeCollection mc;
	private CabinetCollection cbc;
	private DFrameCollection dfc;
	private BuildingCollection buc;
	
	/**
	 * Конструктор
	 */
	public Duct(DFrameCollection dfc, CabinetCollection cbc, ManholeCollection mc, BuildingCollection buc) {
		this.mc = mc;
		this.cbc = cbc;
		this.dfc = dfc;
		this.buc = buc;
	}
	/**
	 * Устанавливает, с какой стороны канализация примыкает к первому колодцу
	 */
	public Duct setFromSide(Integer fromSide) {
		this.fromSide = fromSide;
		return this;
	}
	/**
	 * Возвращает, с какой стороны канализация примыкает к первому колодцу
	 */
	public Integer getFromSide() {
		return this.fromSide;
	}
	/**
	 * Устанавливает, с какой стороны канализация примыкает ко второму колодцу
	 */
	public Duct setToSide(Integer toSide) {
		this.toSide = toSide;
		return this;
	}
	/**
	 * Возвращает, с какой стороны канализация примыкает ко второму колодцу
	 */
	public Integer getToSide() {
		return this.toSide;
	}
	/**
	 * Устанавливает дату прокладки канализации
	 * @param newDate - дата
	 * @return канализация
	 */
	public Duct setDate(String newDate){
		this.date = newDate;
		return this;
	}
	/**
	 * Возвращает дату прокладки канализации
	 */
	public String getDate() {
		return this.date;
	}
	/**
	 * Устанавливает диаметр канала канализации
	 * @param diametr - диаметр, мм
	 * @return канализацию
	 */
	public Duct setTubeDiametr(Integer diametr){
		this.tubeDiametr = diametr;
		return this;
	}
	/**
	 * Возвращает диаметр канала канализации
	 */
	public Integer getTubeDiametr() {
		return this.tubeDiametr;
	}
	/**
	 * Устанавливает длину канализации
	 * @param lenght - длина
	 * @return канализацию
	 */
	public Duct setLenght(Integer lenght) {
		this.lenght = lenght;
		return this;
	}
	/**
	 * Возвращает длину канализации
	 */
	public Integer getLenght() {
		return this.lenght;
	}
	/**
	 * Устанавливает материал каналов
	 * @param material - материал
	 * @return канализация
	 */
	public Duct setTubeMaterial (String material) {
		this.tubeMaterial = material; 
		return this;
	}
	/**
	 * Возвращает материал каналов
	 */
	public String getTubeMaterial () {
		return this.tubeMaterial;
	}
	/**
	 * Устанавливает способ изготовления
	 * @param method - способ изготовления
	 * @return канализация
	 */
	public Duct setМanufacturingМethod (String method) {
		this.method = method;
		return this;
	}
	/**
	 * Возвращает способ изготовления
	 */
	public String getМanufacturingМethod () {
		return this.method;
	}
	/**
	 * Возвращает мнемоническое представление стороны
	 * @param side - сторона
	 */
	private String getSideMnemonic(Integer side) {
		
		if (side.equals(0)) return "п";
		if (side.equals(1)) return "пр";
		if (side.equals(2)) return "з";
		return "лв";
	}
	
	/**
	 * Строковое представление элемента
	 */
	public String toString() {
		
		AbstractElement from = this.mc.getElement(this.getFrom());
		if (from == null) from = this.dfc.getElement(this.getFrom());
		
		AbstractElement to = this.mc.getElement(this.getTo());
		if (to == null) to = this.cbc.getElement(this.getTo());
		if (to == null) to = this.buc.getElement(this.getTo());
		
		return from.toString() +" ("+ this.getSideMnemonic(this.getFromSide())+") - "+ to.toString()+" ("+ this.getSideMnemonic(this.getToSide()).toString()+")";
	}
	
}