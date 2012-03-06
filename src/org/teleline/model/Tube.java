package org.teleline.model;

import java.util.Iterator;
import java.util.Vector;

/**
*Класс элементов "Tube" (канал в канализации)
*
*@author Кирьяков Андрей
*/
public class Tube extends AbstractElement {
	private Integer duct  = 0;
	private Integer number  = 0;
	private Vector<Integer> cables = new Vector<Integer>();
	
	/**
	 * Устанавливает принадлежненость канала к канализации
	 * @param duct - канализация
	 * @return канал
	 */
	public Tube setDuct(Duct duct) {
		this.duct = duct.getId();
		return this;
	}
	/**
	 * Устанавливает принадлежненость канала к канализации (при чтении из файла)
	 * @param ductId - id канализации
	 */
	public void setDuct (Integer ductId) {
		this.duct = ductId;
	}
	/**
	 * Возвращает канализацию, которой принадлежит канал
	 */
	public Integer getDuct() {
		return this.duct;
	}
	/**
	 * Устанавливает номер канала
	 * @param number - номер канала
	 * @return канал
	 */
	public Tube setNumber(Integer number) {
		this.number = number;
		return this;
	}
	/**
	 * Возвращает номер канала
	 */
	public Integer getNumber() {
		return this.number;
	}
	/**
	 * Добавляет кабель в список каблей проходящих через данный канал
	 * @param cable - кабель
	 */
	public void addCable(Cable cable) {
		this.cables.add(cable.getId());
	}
	/**
	 * Добавляет кабель в список каблей проходящих через данный канал (при чтении из файла)
	 * @param cableId - id кабеля
	 */
	public void addCable(Integer cableId) {
		this.cables.add(cableId);
	}
	/**
	 * Проверяет, есть ли кабель в списке проходящих кабелей
	 * @param cable - кабель
	 * @return true - если кабель присутствует в списке, false - если не присутствует
	 */
	public boolean containsCable(Cable cable){
		
		Integer cableId = cable.getId();

		Iterator<Integer> i = this.cables.iterator();
		while (i.hasNext()) if (i.next().equals(cableId)) return true;
		
		return false;
	}
	/**
	 * Возвращает количесвто проходящих кабелей
	 */
	public Integer cablesCount() {
		
		return this.cables.size();
	}
	
	/**
	 * Удаляет кабель из списка кабелей проходящих через данный канал
	 * @param cable кабель
	 * @return true - если кабель удален, false - если кабеля нет в списке
	 */
	public boolean removeCable(Cable cable){
		
		Integer element, cableId = cable.getId();
		
		Iterator<Integer> i = this.cables.iterator();
		while (i.hasNext()){ 
			element = i.next();
			if (element.equals(cableId)) {
				this.cables.remove(element);
				return true;
			}
		}
		return false;
	}
	/**
	 * Возвращает копию коллекции идентификаторов кабелей, проходящих через данный канал
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Vector<Integer> getCables() {
		return (Vector<Integer>)this.cables.clone();
	}
	
	public String toString() {
		return this.number.toString();
	}
}