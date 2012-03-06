package org.teleline.model;

import java.util.Iterator;
import java.util.Vector;



/**
*Класс элементов "Path" (путь, прохождение задействованого включения через пары)
*
*@author Кирьяков Андрей
*/
public class Path extends AbstractElement {
	
	private Integer subscriber = 0;
	private String name = "Основное";
	private String transit = ""; //данные о паре перехода (формат Ц-ЦЦ)
	
	private Integer mPair = 0; // магистральная пара
	private Integer drPair = 0; //пара прямого питания
	private Integer dbPair = 0; // распределительная пара
	private Vector<Integer> icPair = new Vector<Integer>(); //массив занимаемых межшкафных пар
	
	private PairCollection pc;
	private SubscriberCollection sc;
	
	/**
	 * Конструктор
	 */
	public Path(SubscriberCollection sc, PairCollection pc) {
		this.pc = pc;
		this.sc = sc;
	}
	/**
	 * Устанавливает ссылку на абонента
	 */
	public Path setSubscriber(Subscriber sub) {
		this.subscriber = sub.getId();
		return this;
	}
	public Path setSubscriber(Integer subId) {
		this.subscriber = subId;
		return this;
	}
	/**
	 * Возвращает id связанного абонента
	 */
	public Integer getSubscriber() {
		return this.subscriber;
	}
	/**
	 * Устанавливает имя включения
	 */
	public Path setName(String newName){
		this.name = newName;
		return this;
	}
	/**
	 * Возвращает имя включения
	 */
	public String getName() {
		return this.name;
	}
	/**
	 * Устанавливает данные о паре перехода
	 * @param transit - пара перехода
	 */
	public Path setTransit(String transit){
		this.transit = transit;
		return this;
	}
	/**
	 * Возвращает данные о паре перехода
	 * @return
	 */
	public String getTransit(){
		return this.transit;
	}
	/**
	 * Проверяет, занимает ли включение магистральную пару
	 */
	public boolean ismPair() {
		if (this.mPair > 0) return true;
		return false;
	}
	/**
	 * Врзвращает id магистральнуый пары
	 */
	public Integer getmPair() {
		return this.mPair;
	}
	/**
	 * Добавляет магистральную пару
	 */
	public Path addmPair(Pair pair) {
		this.mPair = pair.getId();
		return this;
	}
	public void addmPair(Integer pairId) {
		this.mPair = pairId;
	}
	/**
	 * Проверяет, занимает ли включение пару прямого питания
	 */
	public boolean isdrPair() {
		if (this.drPair > 0) return true;
		return false;
	}
	/**
	 * Возвращает id пары прямого питания
	 */
	public Integer getdrPair() {
		return this.drPair;
	}
	/**
	 * Добавляет пару  прямого питания
	 */
	public Path adddrPair(Pair pair) {
		this.drPair = pair.getId();
		return this;
	}
	public void adddrPair(Integer pairId) {
		this.drPair = pairId;
	}
	/**
	 * Проверяет, занимает ли включение распределительную пару
	 */
	public boolean isdbPair() {
		if (this.dbPair > 0) return true;
		return false;
	}
	/**
	 * Врзвращает id занимаемой распределительной пары
	 */
	public Integer getdbPair() {
		return this.dbPair;
	}
	/**
	 * Добавляет распределительную пару
	 */
	public Path adddbPair(Pair pair) {
		this.dbPair = pair.getId();
		return this;
	}
	public void adddbPair(Integer pairId) {
		this.dbPair = pairId;
	}
	
	/**
	 * Проверяет, занимает ли включение межшкафную пару
	 */
	public boolean isicPair() {
		if (this.icPair.size() > 0) return true;
		return false;
	}
	/**
	 * Врзвращает коллекцию занимаемых межшкафных пар
	 */
	public Vector<Integer> geticPair() {
		return this.icPair;
	}
	/**
	 * Добавляет пару в список занятых межшкафных пар
	 */
	public Path addicPair(Pair pair) {
		this.icPair.add(pair.getId());
		return this;
	}
	public void addicPair(Integer pairId) {
		this.icPair.add(pairId);
	}
	/**
	 * Возвращает коллецию элементов "Пара", занимаемых абонентом
	 * @return
	 */
	public Vector<Pair> getUsedPairs (){
		
		Vector<Pair> v = new Vector<Pair>();
		
		if (this.mPair > 0) v.add((Pair)pc.getElement(this.mPair));
		if (this.drPair > 0) v.add((Pair)pc.getElement(this.drPair));
		for (int i = 0; i < this.icPair.size(); i++) v.add((Pair)pc.getElement(this.icPair.get(i)));
		if (this.dbPair > 0) v.add((Pair)pc.getElement(this.dbPair));
	
		return v;
	}
	/**
	 * Проверяет использует ли включение данную пару
	 * @param pair - пара
	 */
	public boolean isPairUsed(Pair pair) {
		
		Integer pairId = pair.getId();

		if (this.mPair.equals(pairId) || this.drPair.equals(pairId) || this.dbPair.equals(pairId) ) return true;
			
		Iterator <Integer> i = this.icPair.iterator();
		while (i.hasNext()) if (i.next().equals(pairId)) return true;
		
		return false;
	}
	/**
	 * Удаляет "Пару" из занимаемых пар
	 */
	public boolean removePair(Pair pair) {
		
		Integer pi = pair.getId();
		
		if (this.mPair.equals(pi)) {this.mPair = 0; return true;}
		if (this.drPair.equals(pi)) {this.drPair = 0; return true;}
		if (this.dbPair.equals(pi)) {this.dbPair = 0; return true;}
		
		for (int i = 0; i < this.icPair.size(); i++)
			if (this.icPair.get(i).equals(pi)) {
				this.icPair.remove(i);
				return true;
			}
				
		return false;
		
	}
	/**
	 * Удаляет все пары из включения
	 */
	public void removeAllPairs(){
		
		this.mPair = 0;
		this.dbPair = 0;
		this.drPair = 0;	
		this.icPair.clear();
	}
	
	public String toString() {
		return this.name + " (" + ((Subscriber)sc.getElement(this.subscriber)).getName() + ")";
	}
}