package org.teleline.model;

/**
*Класс элементов "Manhole" (колодец).
*
*@author Кирьяков Андрей
*/
public class Manhole extends StructuredElement {
	private String adress = "";
	private Integer construction = 0;
	private Integer form = 0;
	private String date = "";
	private String size = "";
	
	
	/**
	 * Конструктор
	 */
	public Manhole() {
	
	}
	public Manhole setAdress(String adress) {
		this.adress = adress;
		return this;
	}
	public String getAdress() {
		return this.adress;
	}
	public Manhole setConstruction(Integer constr) {
		this.construction = constr;
		return this;
	}
	public Integer getConstruction() {
		return this.construction;
	}
	public String getConstructionMnemonic() {
		if (this.construction ==0 ) return "Железобетонный";
		return "Кирпичный";
	}
	public Manhole setForm(Integer form) {
		this.form = form;
		return this;
	}
	public Integer getForm() {
		return this.form;
	}
	public String getFormMnemonic() {
		if (this.form == 0) return "Овальный";
		return "Прямоугольный";
	}
	public Manhole setDate(String date) {
		this.date = date;
		return this;
	}
	public String getDate() {
		return this.date;
	}
	public Manhole setSize(String size) {
		this.size = size;
		return this;
	}
	public String getSize() {
		return this.size;
	}
	/**
	 * Строковое представление элемента
	 */
	public String toString() {
		return "К.к. "+ this.getSNumber();
	}
	
}