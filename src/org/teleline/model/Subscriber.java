package org.teleline.model;


/**
 * Класс  элементов "Абонент"
 * 
 *@author Кирьяков Андрей
 */
public class Subscriber extends StructuredElement{
	private String phoneNumber = "";
/*	private Vector<Integer> mPair = new Vector<Integer>(); //массив занимаемых магистральных пара
	private Vector<Integer> drPair = new Vector<Integer>(); //массив занимаемых пар прямого питания
	private Vector<Integer> dbPair = new Vector<Integer>(); //массив занимаемых распределительных пар
	private Vector<Integer> icPair = new Vector<Integer>(); //массив занимаемых межшкафных пар	
*/	
	//private PairCollection pc;
	
	private String adress = "";
	private String date = "";
	private String equipment = "";
	
	
	/**
	 * Конструктор
	 */
	public Subscriber(/*PairCollection pc*/){
		
	//	this.pc = pc;
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
	 * Проверяет, занимает ли абонент магистральную пару
	 */
/*	public boolean ismPair() {
		if (this.mPair.size() > 0) return true;
		return false;
	}*/
	/**
	 * Врзвращает вектор занимамых  магистральных пар
	 */
/*	public Vector<Integer> getmPair() {
		return this.mPair;
	}*/
	/**
	 * Добавляет пару в список занимаемых магистральных пар
	 */
/*	public Subscriber addmPair(Pair pair) {
		this.mPair.add(pair.getId());
		return this;
	}
	public void addmPair(Integer pairId) {
		this.mPair.add(pairId);
	}*/
	/**
	 * Проверяет, занимает ли абонент пару прямого питания
	 */
/*	public boolean isdrPair() {
		if (this.drPair.size() > 0) return true;
		return false;
	}*/
	/**
	 * Возвращает вектор занимаемых пар прямого питания
	 */
/*	public Vector<Integer> getdrPair() {
		return this.drPair;
	}*/
	/**
	 * Добавляет пару в список занятых пар прямого питания
	 */
/*	public Subscriber adddrPair(Pair pair) {
		this.drPair.add(pair.getId());
		return this;
	}
	public void adddrPair(Integer pairId) {
		this.drPair.add(pairId);
	}*/
	/**
	 * Проверяет, занимает ли абонент распределительную пару
	 */
/*	public boolean isdbPair() {
		if (this.dbPair.size() > 0) return true;
		return false;
	}*/
	/**
	 * Врзвращает вектор занимаемых распределительных пар
	 */
/*	public Vector<Integer> getdbPair() {
		return this.dbPair;
	}*/
	/**
	 * Добавляет пару в список занятых распределительных пар
	 */
/*	public Subscriber adddbPair(Pair pair) {
		this.dbPair.add(pair.getId());
		return this;
	}
	public void adddbPair(Integer pairId) {
		this.dbPair.add(pairId);
	}*/
	
	/**
	 * Проверяет, занимает ли абонент межшкафную пару
	 */
/*	public boolean isicPair() {
		if (this.icPair.size() > 0) return true;
		return false;
	}*/
	/**
	 * Врзвращает коллекцию занимаемых межшкафных пар
	 */
/*	public Vector<Integer> geticPair() {
		return this.icPair;
	}*/
	/**
	 * Добавляет пару в список занятых межшкафных пар
	 */
/*	public Subscriber addicPair(Pair pair) {
		this.icPair.add(pair.getId());
		return this;
	}
	public void addicPair(Integer pairId) {
		this.icPair.add(pairId);
	}*/
	/**
	 * Возвращает коллецию элементов "Пара", занимаемых абонентом
	 * @return
	 */
/*	public Vector<Pair> getUsedPairs (){
		
		Vector<Pair> v = new Vector<Pair>();
		
		for (int i = 0; i < this.mPair.size(); i++) v.add((Pair)pc.getElement(this.mPair.get(i)));
		for (int i = 0; i < this.drPair.size(); i++) v.add((Pair)pc.getElement(this.drPair.get(i)));
		for (int i = 0; i < this.icPair.size(); i++) v.add((Pair)pc.getElement(this.icPair.get(i)));
		for (int i = 0; i < this.dbPair.size(); i++) v.add((Pair)pc.getElement(this.dbPair.get(i)));
	
		return v;
	}*/
	/**
	 * Удаляет "Пару" из занимаемых пар, статус "Пары" устанавливается - свободно
	 */
	
/*	public boolean removePair(Pair pair) {
		
		Integer pi = pair.getId();
		
		//if (this.mPair.equals(pi)) { this.mPair = 0; return true;  }
		//if (this.drPair.equals(pi)) { this.drPair = 0; return true; }
		//if (this.dbPair.equals(pi)) { this.dbPair = 0; return true; }
		for (int i = 0; i < this.mPair.size(); i++)
			if (this.mPair.get(i).equals(pi)) {
				this.mPair.remove(i);
				return true;
			}
		
		for (int i = 0; i < this.drPair.size(); i++)
			if (this.drPair.get(i).equals(pi)) {
				this.drPair.remove(i);
				return true;
			}
		
		for (int i = 0; i < this.dbPair.size(); i++)
			if (this.dbPair.get(i).equals(pi)) {
				this.dbPair.remove(i);
				return true;
			}
		
		for (int i = 0; i < this.icPair.size(); i++)
			if (this.icPair.get(i).equals(pi)) {
				this.icPair.remove(i);
				return true;
			}
				
		return false;
		
	}*/
	/**
	 * Строкое представление элемента
	 */
	public String toString() {
		return this.getName() +" (№="+this.getPhoneNumber().toString()+")";
	}
	
	
}