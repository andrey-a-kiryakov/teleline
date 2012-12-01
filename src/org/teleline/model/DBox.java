package org.teleline.model;


/**
*Класс элементов "DВох" (распределительная коробка)
*
*@author Кирьяков Андрей
*/
public class DBox extends StructuredElement{ 
	private Integer capacity = 10;
	private Integer building = 0;
	private String plase = "";
	DFrameCollection dfc;
	CabinetCollection cbc;
	FrameCollection fc;
	BoxCollection bc;
	PairCollection pc;
	CableCollection cc;
	BuildingCollection buc;
	
	/**
	 * Конструктор
	 */
	public DBox( DFrameCollection dfc, CabinetCollection cbc, FrameCollection fc, BoxCollection bc, PairCollection pc, CableCollection cc, BuildingCollection buc  ) {
		this.dfc = dfc;
		this.cbc = cbc;
		this.fc = fc;
		this.bc = bc;
		this.pc = pc;
		this.cc = cc;
		this.buc = buc;
	}
	/**
	 * Устанавливает емкость "Коробки"
	 * @param ёмкость
	 * @return сам объект "DBox"
	 */
	public DBox setCapacity (Integer Capacity) {
		this.capacity = Capacity;
		return this;
	}
	/**
	 * Возвращает емкость коробки
	 */
	public Integer getCapacity () {
		return this.capacity;
	}
	/**
	 * Устанавливает здание коробки
	 * @param building - здание
	 * @return сам объект "DBox"
	 */
	public DBox setBuilding (Building building){
		this.building = building.getId();
		return this;
	}
	
	public DBox setBuilding (Integer buildingId){
		this.building = buildingId;
		return this;
	}
	
	/**
	 * Возвращает идентификатор здания коробки
	 */
	public Integer getBuilding () {
		return this.building;
	}
	/**
	 * Устанавливает место установки коробки
	 * @return сам объект "DBox"
	 */
	public DBox setPlase(String plase) {
		this.plase = plase;
		return this;
	}
	/**
	 * Возвращает место установки коробки
	 */
	public String getPlase() {
		return this.plase;
	}
	
	public Integer whoIsIt () {
		return 2;
	}
	
	/**
	 * Строковое представление элемента
	 */
	public String toString() {
		
		Pair p = pc.getInPlace(this, 0);
	//	Building bl = (Building)buc.getElement(this.building);
	//	String build = "";
	//	if (bl != null) build = bl.toString();
		
		if (p == null) { 
			return "КРТ (id=" + this.getId().toString() + ", без пар, "/*+ build +*/ +")"; }
		
		else {
			StructuredElement s;
			ConnectedPointElement b = (ConnectedPointElement)bc.getElement(p.getElementFrom());
			
			if (b == null ) {
				b = (ConnectedPointElement)fc.getElement(p.getElementFrom());
				s = (StructuredElement)dfc.getElement(b.getOwnerId());
				return  "КРТ"+b.getNumber().toString()+ "-" + ((Integer)(p.getFromNumber() / 10)).toString()/* +" ("+build+")"*/;
			}
			else {
				s = (StructuredElement)cbc.getElement(b.getOwnerId());}
				return s.getSNumber() + "КРТ"+b.getNumber().toString()+ "-" + ((Integer)(p.getFromNumber() / 10)).toString()/*+" ("+build+")"*/;
			
		}
	}
	
	
}