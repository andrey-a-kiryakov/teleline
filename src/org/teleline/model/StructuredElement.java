package org.teleline.model;

/**
*Класс структурных элементов ЛКХ
*
*@author Кирьяков Андрей
*/
public class StructuredElement extends AbstractElement{
	private Integer netId = 0;
	private String name = "";
	private String snumber = "";
	private Integer number = 0;
	private Integer placesCount = 0;
	/**
	 * Конструктор
	 */
	public StructuredElement(){
		
	}
	
	/**
	 * Возвращает "название" элемента
	 */
	public String getName() {
		return this.name;
	}
	/**
	 * Устанавливает "название" элемента
	 * @param name - название элемента
	 * @return сам элемент
	 */
	public StructuredElement setName(String name){
		this.name = name;
		return this;
	}
	/**
	 *Присоединяет элемент к сети 
	 * @param id элемента Net или сам объект Net
	 */
	public void attachToNet (Integer netId){
		this.netId = netId;
	}
	public StructuredElement attachToNet (Net Net){
		this.netId = Net.getId();
		return this;
	}
	/**
	 * Устанавливает номер элемента
	 * @param номер элемента
	 */
	public void setNumber(Integer NewNumber) {
		this.number = NewNumber;
	}
	/**
	 * Возвращает номер элемента
	 */
	public Integer getNumber() {
		return this.number;
	}
	/**
	 * Устанавливает строковый номер элемента
	 * @param NewNumber - номер элемента
	 */
	public void setSNumber(String NewNumber) {
		this.snumber = NewNumber;
	}
	/**
	 * Возвращает строковый номер элемента
	 */
	public String getSNumber() {
		return this.snumber;
	}
	/**
	 * Возвращает id сети, которой принадлежит элемент
	 */
	public Integer getNet() {
		return this.netId;
	}
	/**
	 * Устанавливает количество мест
	 * @param количество мест
	 */
	public StructuredElement setPlacesCount(Integer count) {
		this.placesCount = count;
		return this;
	}
	/**
	 * Возвращает количество мест
	 * @param количество мест
	 */
	public Integer getPlacesCount() {
		return this.placesCount;
	}
	
	
}