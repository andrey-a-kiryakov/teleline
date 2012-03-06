package org.teleline.model;

public class IdGenerator {
	private Integer idIndex = 0;
	
	/**
	 * Устанавливает текущее значение индекса
	 * @param index
	 */
	public void setIdIndex (Integer index) {
		this.idIndex = index;
	}
	/**
	 * Возвращает текущее значение индекса
	 */
	public Integer getId() {
		return this.idIndex;
	}
	/**
	 * Возвращает индекс для нового элемента
	 */
	public Integer getNewId() {
		this.idIndex++;
		return this.idIndex;
	}
}