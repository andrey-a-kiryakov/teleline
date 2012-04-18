package org.teleline.model;

/**
*Класс элементов "Damage" (повреждение)
*
*@author Кирьяков Андрей
*/
public class Damage extends AbstractElement{
	private Integer ownerId;
	private String openDate;
	private String closeDate;
	private String name;
	private String description;
	/**
	 * Конструктор
	 */
	public Damage(){
		this.ownerId = 0;
		this.description = "";
		this.name = "";
		this.openDate="";
		this.closeDate = "";
	}
	
	public Damage attachTo(Integer OwnerId) {
		this.ownerId = OwnerId;
		return this;
	}
	
	public Damage attachTo(AbstractElement Owner) {
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
	 * Устанавливает название/характер повреждения
	 * @param name - название/характер повреждения
	 * @return сам объект "Damage"
	 */
	public Damage setName (String name) {
		this.name = name;
		return this;
	}
	/**
	 * Возвращает строку с названием/характером повреждения
	 */
	public String getName() {
		return this.name;
	}
	/**
	 * Устанавливает описание неисправности
	 * @param description - описание неисправности
	 * @return сам объект "Damage"
	 */
	public Damage setDescription(String description) {
		this.description = description;
		return this;
	}
	/**
	 * Возвращает строку с описанием повреждения
	 */
	public String getDescription() {
		return this.description;
	}
	/**
	 * Устанавливает дату обнаружения повреждения
	 * @param date - дата
	 * @return сам объект "Damage"
	 */
	public Damage setOpenDate(String date) {
		this.openDate = date;
		return this;
	}
	/**
	 * Возвращает дату обнаружения повреждения
	 */
	public String getOpenDate() {
		return this.openDate;
	}
	/**
	 * Устанавливает дату устранения повреждения
	 * @param date - дата
	 * @return сам объект "Damage"
	 */
	public Damage setCloseDate(String date) {
		this.closeDate = date;
		return this;
	}
	
	/**
	 * Возвращает дату устранения повреждения
	 */
	public String getCloseDate() {
		return this.closeDate;
	}
	/**
	 * Строковое представление элемента
	 */
	public String toString() {
		return this.name;
	}
}