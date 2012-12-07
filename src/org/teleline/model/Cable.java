package org.teleline.model;

import java.util.Iterator;
import java.util.Vector;

/**
*Класс элементов "Cable" (Кабель)
*
*@author Кирьяков Андрей
*/
public class Cable extends LinkedElement {

	private Integer lenght = 1; //длина кабеля в (м)
	private String wireDiametr = "0,5"; // диаметр жилы (мм)
	private String year = "2011"; //год протяжки
	private Integer status = 0; //0 - новый, 1 - бывший в эксплуатации
	private Integer type = 0; //0 - магистральный, 1 - межшкафной, 2 - распределительный, 3 - прямого питания
	private String label = "ТПП"; //ТГ и ТПП
	
	private DFrameCollection dfc;
	private CabinetCollection cbc;
	private DBoxCollection dbc;
//	private FrameCollection fc;
//	private BoxCollection bc;
	private PairCollection pc;
	
	/**
	 * Конструктор
	 */
	public Cable(DFrameCollection dfc, CabinetCollection cbc, DBoxCollection dbc, /*FrameCollection fc, BoxCollection bc,*/ PairCollection pc) {
	//	this.capacity = 100; //ёмкость по умолчанию
		this.dfc = dfc;
		this.cbc = cbc;
		this.dbc = dbc;
	//	this.fc = fc;
	//	this.bc = bc;
		this.pc = pc;
	}
	
	/**
	 * Возвращает используемую емкость елемента в "Парах"
	 */
	public Integer getUsedCapacity () {
		return this.pc.getInCable(this).size();
	}
	/**
	 * Устанавливает длину кабеля
	 * @param lenght - длина
	 * @return сам кабель
	 */
	public Cable setLenght(Integer lenght) {
		this.lenght = lenght;
		return this;
	}
	/**
	 * Возвращает длину кабеля
	 */
	public Integer getLenght() {
		return this.lenght;
	}
	/**
	 * Устанавливает диаметр жилы кабеля
	 * @param d - диаметр (мм)
	 * @return сам кабель
	 */
	public Cable setWireDiametr (String d) {
		this.wireDiametr = d;
		return this;
	}
	/**
	 * Возвращает диаметр жилы кабеля
	 */
	public String getWireDiametr() {
		return this.wireDiametr;
	}
	/**
	 * Устанавливает год протяжки кабеля
	 * @param year - год
	 */
	public Cable setYear(String year) {
		this.year = year;
		return this;
	}
	/**
	 * Возвращает год протяжки кабеля
	 */
	public String getYear(){
		return this.year;
	}
	/**
	 * Устанавливает статус кабеля
	 * @param status - статус (0 или 1)
	 */
	public Cable setStatus(Integer status) {
		this.status = status;
		return this;
	}
	/**
	 * Возвращает статус кабеля
	 */
	public Integer getStatus (){
		return this.status;
	}
	/**
	 * Возвращает текстовое представление статуса кабеля
	 */
	public String getStatusMnemonic () {
		if (this.status.equals(0)) return "Новый";
		return "Бывший в эксплуатации";
	}
	/**
	 * Проверяет, можно ли добавить в кабель данное количество "Пар"
	 * * @param количество добавляемых "Пар"
	 */
/*	public boolean isConnect (Integer pairCount) {
		if ((this.capacity - this.pc.getInCable(this).size()) >= pairCount) return true;
		return false;
	}*/
	/**
	 * Устанавливает марку кабеля
	 */
	public Cable setLabel (String newLabel) {
		this.label = newLabel;
		return this;
	}
	/**
	 * Возвращает марку кабеля
	 */
	public String getLabel () {
		return this.label;
	}
	/**
	 * Устанавливает тип кабеля
	 */
	public Cable setType(Integer newType){
		this.type = newType;
		return this;
	}
	/**
	 * Возвращает тип кабеля
	 */
	public Integer getType () {
		return this.type;
	}
	/**
	 * Возвращает элемент, из которого выходит кабель
	 */
	public AbstractElement getFromElement() {
		
		switch (this.type.intValue()) {
			case 0: return this.dfc.getElement(this.getFrom()); 
			case 1: return this.cbc.getElement(this.getFrom()); 
			case 2: return this.cbc.getElement(this.getFrom()); 
			case 3: return this.dfc.getElement(this.getFrom());
		}
		return null;
	}
	/**
	 * Возвращает элемент, в который приходит кабель
	 */
	public AbstractElement getToElement() {
		
		switch (this.type.intValue()) {
			case 0: return this.cbc.getElement(this.getTo());
			case 1: return this.cbc.getElement(this.getTo());
			case 2: return null;
			case 3: return null;
		}
		
		return null;
	}
	
	/**
	 * Возвращает вектор КРТ, в которые приходит кабель, либо null, если не приходит ни в одну коробку 
	 * @return
	 */
	public Vector<DBox> getToDBoxes() {
		
		if (this.type < 2) return null;
		
		Vector<DBox> v = new Vector<DBox>();
		Iterator<Pair> i = pc.getInCable(this).iterator();
		while(i.hasNext()) {
			DBox dbox = (DBox)dbc.getElement(i.next().getElementTo());
			if (!v.contains(dbox)) v.add(dbox);
		}
		
		if (v.size() > 0) return v;
		
		return null;
	}
	
	/**
	 * Стандартное строковое представление элемента
	 */
	public String toString() {
		
		String s = "";
		
		switch (this.type.intValue()) {
			case 0: s = "M"; break;
			case 1: s = "Рпер"; break;
			case 2: s = "Р"; break;
			case 3: s = "ПП"; break;
		}
		return s + "-" + this.getNumber();
	}
	
	/**
	 * Сокращенное строковое представление элемента
	 */
	public String toShortString() {
		
		String s = "";
		
		switch (this.type.intValue()) {
			case 0: s = "M"; break;
			case 1: s = "Рпер";  break;
			case 2: s = "Р";  break;
			case 3: s = "ПП";  break;
		}
		return s + "-" + this.getNumber();
	}
	/**
	 * Полное строковое представление элемента
	 */
	public String toLongString() {
		
		String s = "";
		String s1 = "";//, s2 = "";
		
	//	Pair p = this.pc.getInPlace(this, 0);
		
		/*if (p != null) {
			s2 = ", " + p.toString();
		}*/
		
		switch (this.type.intValue()) {
			case 0: s = "M"; s1 = this.dfc.getElement(this.getFrom()).toString()+" - "+this.cbc.getElement(this.getTo()).toString(); break;
			case 1: s = "Рпер"; s1 = this.cbc.getElement(this.getFrom()).toString()+" - "+this.cbc.getElement(this.getTo()).toString(); break;
			case 2: s = "Р"; s1  = this.cbc.getElement(this.getFrom()).toString(); break;
			case 3: s = "ПП"; s1  = this.dfc.getElement(this.getFrom()).toString(); break;
		}
		return s + "-" + this.getNumber() + " ("+s1+")";
	}
}