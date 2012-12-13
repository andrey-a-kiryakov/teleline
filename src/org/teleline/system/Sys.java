package org.teleline.system;


import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.teleline.gui.ManagerForms;
import org.teleline.io.RW;
import org.teleline.io.Validator;
import org.teleline.model.AbstractElement;
import org.teleline.model.Box;
import org.teleline.model.BoxCollection;
import org.teleline.model.Building;
import org.teleline.model.BuildingCollection;
import org.teleline.model.Cabinet;
import org.teleline.model.CabinetCollection;
import org.teleline.model.Cable;
import org.teleline.model.CableCollection;
import org.teleline.model.ConnectedPointElement;
import org.teleline.model.DBox;
import org.teleline.model.DBoxCollection;
import org.teleline.model.DFrameCollection;
import org.teleline.model.DFramе;
import org.teleline.model.DamageCollection;
import org.teleline.model.Duct;
import org.teleline.model.DuctCollection;
import org.teleline.model.Frame;
import org.teleline.model.FrameCollection;
import org.teleline.model.IdGenerator;
import org.teleline.model.Manhole;
import org.teleline.model.ManholeCollection;
import org.teleline.model.Net;
import org.teleline.model.NetCollection;
import org.teleline.model.Pair;
import org.teleline.model.PairCollection;
import org.teleline.model.Path;
import org.teleline.model.PathCollection;
import org.teleline.model.StructuredElement;
import org.teleline.model.Subscriber;
import org.teleline.model.SubscriberCollection;
import org.teleline.model.Tube;
import org.teleline.model.TubeCollection;

public class Sys {
	
	public static final Logger log = LoggerFactory.getLogger("sys");
	public static final Logger log_app = LoggerFactory.getLogger("app");
	
	public boolean changes = false;
	
	public Sys() {
		
		ig = new IdGenerator();
		nc = new NetCollection(ig);
		dfc = new DFrameCollection(ig);
		cbc = new CabinetCollection(ig);
		dbc = new DBoxCollection(ig);
		mc = new ManholeCollection(ig);
		duc = new DuctCollection(ig);
		buc = new BuildingCollection(ig);
		tuc = new TubeCollection(ig);
		fc = new FrameCollection(ig);
		bc = new BoxCollection(ig);
		cc = new CableCollection(ig);
		pc = new PairCollection(ig);
		phc = new PathCollection(ig);
		sc = new SubscriberCollection(ig);
		dmc = new DamageCollection(ig);
		
		v = new Validator();
		rw = new RW(this);
		mng = new ManagerForms();
		
		Net net = new Net();
		net.setName("Новая сеть");
		nc.addElement(net);
	}
	
	public IdGenerator ig;
	//public gui GUI;
	public RW rw;
	public Validator v;
	public ManagerForms mng;
	
	public NetCollection nc;
	public DFrameCollection dfc;
	public CabinetCollection cbc; 
	public DBoxCollection dbc;
	public ManholeCollection mc;
	public DuctCollection duc;
	public BuildingCollection buc;
	public TubeCollection tuc;
	public FrameCollection fc;
	public BoxCollection bc;
	public CableCollection cc;
	public PairCollection pc;
	public PathCollection phc;
	public SubscriberCollection sc;
	public DamageCollection dmc;
	
	/**
	 * Удаляет бокс и все пары в нем. Также пары удаляются из занятых пар у абонентов
	 * @param box - бокс
	 */
	public void removeBox (Box box) {
		
		Pair pair = null;
		Path path = null;
		Iterator <Pair> p = pc.getInOwner(box).iterator();
		
		while (p.hasNext()){
			pair = p.next();
			Iterator<AbstractElement> ph = phc.elements().iterator();
			while (ph.hasNext()) {
				path = (Path) ph.next();
				if (path.isPairUsed(pair)) {
					path.removePair(pair);
					log.info("Пара "+ pair.toString()+" удалена из включения: "+ path.toString()+ " у абонента: " + sc.getElement(path.getSubscriber()).toString());
					changes = true;
				}
			}
			if (pc.removeElement(pair)) {
				log.info("Удалена: Пара {}", pair);
				changes = true;
			}
		}
		if(bc.removeElement(box)) {
			log.info("Удален: Бокс {}", box);
			changes = true;
		}
	}
	/**
	 * Удаляет КРТ и все пары в ней. Также пары удаляются из всех включений
	 * @param dbox - КРТ
	 */
	public void removeDBox (DBox dbox) {
		
		Pair pair = null;
		Path path = null;
		Iterator <Pair> p = pc.getInOwner(dbox).iterator();
		
		while (p.hasNext()){
			pair = p.next();
			Iterator<AbstractElement> ph = phc.elements().iterator();
			while (ph.hasNext()) {
				path = (Path) ph.next();
				if (path.isPairUsed(pair)) {
					path.removePair(pair);
					log.info("Пара "+ pair.toString()+" удалена из включения: "+ path.toString()+ " у абонента: " + sc.getElement(path.getSubscriber()).toString());
					changes = true;
				}
			}
			if (pc.removeElement(pair)){
				log.info("Удалена: Пара {}", pair);
				changes = true;
			}
		}
		if (dbc.removeElement(dbox)) {
			log.info("Удалена: КРТ {}", dbox);
			changes = true;
		}
	}
	/**
	 * Удаляет участок канализации. Удаляются все каналы в канализации. Кабели проходящии по данному участку не удаляются.
	 * @param duct - Канализация
	 */
	public void removeDuct(Duct duct) {
		
		Iterator<Tube> i = tuc.getDuctsTubes(duct).iterator();
		while (i.hasNext()) removeTube(i.next());
		if (duc.removeElement(duct)) {
			log.info("Удален: Участок кабельной канализации " + duct.toString());
			changes = true;
		}
	}
	/**
	 * Удаляет канал в канализации. Кабели, проходящие по каналу не удалаются
	 * @param tube - канал
	 */
	public void removeTube(Tube tube) {
		
		if (tuc.removeElement(tube)){
			log.info("Удален: Канал " + tube.toString() + " в канализации "+ duc.getElement(tube.getDuct()));
			changes = true;
		}
	}
	/**
	 * Удаляет шкаф и все боксы в нем, подходяшие участки канализаци и кабели
	 * @param element - шкаф
	 */
	public void removeCabinet (Cabinet element) {
		
		Iterator<ConnectedPointElement> k = bc.getInOwner(element.getId()).iterator();
		while (k.hasNext()) removeBox((Box)k.next());
		
		Iterator<Duct> i = duc.getDucts(element).iterator();
		while (i.hasNext()) removeDuct(i.next());
		
		Iterator<Cable> c = cc.getCables(element).iterator();
		while (c.hasNext()) removeCable(c.next());
		
		if (cbc.removeElement(element)){
			log.info("Удален: Шкаф {}", element);
			changes = true;
		}
	}
	
	/**
	 * Удаляет кросс и все громполосы в нем, подходяшие участки канализаци и кабели
	 * @param element - кросс
	 */
	public void removeDFrame (DFramе element) {
		
		Iterator<ConnectedPointElement> k = fc.getInOwner(element.getId()).iterator();
		while (k.hasNext()) removeFrame((Frame)k.next());
		
		Iterator<Duct> i = duc.getDucts(element).iterator();
		while (i.hasNext()) removeDuct(i.next());
		
		Iterator<Cable> c = cc.getCables(element).iterator();
		while (c.hasNext()) removeCable(c.next());
		
		if (dfc.removeElement(element)) {
			log.info("Удален: Кросс {}", element);
			changes = true;
		}
	}
	/**
	 * Удаляет громполосу и все пары в ней. Также пары удаляются из занятых пар у абонентов
	 * @param frame - громполоса
	 */
	public void removeFrame (Frame frame) {
		
		Pair pair = null;
		Path path = null;
		Iterator <Pair> p = pc.getInOwner(frame).iterator();
		
		while (p.hasNext()){
			pair = p.next();
			Iterator<AbstractElement> ph = phc.elements().iterator();
			while (ph.hasNext()) {
				path = (Path) ph.next();
				if (path.isPairUsed(pair)) {
					path.removePair(pair);
					log.info("Пара "+ pair.toString()+" удалена из включения: "+ path.toString()+ " у абонента: " + sc.getElement(path.getSubscriber()).toString());
					changes = true;
				}
			}
			
			if (pc.removeElement(pair)) {
				log.info("Удалена: Пара {}", pair);
				changes = true;
			}
		}
		if (fc.removeElement(frame)) {
			log.info("Удален: Громполоса {}", frame);
			changes = true;
		}
	}	
	/**
	 * Удаляет кабель и все пары в нем. Кабель удаляется из всех каналов канализации. Также пары удаляются из всех включений.
	 * @param cable - Кабель
	 */
	public void removeCable(Cable cable) {
		
		Pair pair = null;
		Path path = null;
		
		Iterator <Pair> p = pc.getInCable(cable).iterator();
		while (p.hasNext()){
			pair = p.next();
			Iterator<AbstractElement> ph = phc.elements().iterator();
			while (ph.hasNext()) {
				path = (Path) ph.next();
				if (path.isPairUsed(pair)) {
					path.removePair(pair);
					log.info("Пара "+ pair.toString()+" удалена из включения: "+ path.toString()+ " у абонента: " + sc.getElement(path.getSubscriber()).toString());
					changes = true;
				}
			}
			if (pc.removeElement(pair)) {
				log.info("Удалена: Пара {}", pair);
				changes = true;
			}
		}
		
		Iterator <Tube> t = tuc.getTubesByCable(cable).iterator();
		while (t.hasNext()) {
			t.next().removeCable(cable); 
		}
		
		if (cc.removeElement(cable)) {
			log.info("Удален: Кабель {}", cable);
			changes = true;
		}
	}
	/**
	 * Удаляет колодец. Все участки кабельной канализации проходящие через колодец - удаляются
	 * @param man - колодец
	 */
	public void removeManhole(Manhole man){
		
		Iterator<Duct> i = duc.getDucts(man).iterator();
		while (i.hasNext()) removeDuct(i.next());
		if (mc.removeElement(man)){ 
			log.info("Удален: Колодец {}", man);
			changes = true;
		}
	}
	/**
	 * Удаляет здание. Удаляется также участок канализации, подходящий к зданию.
	 * @param building - здание
	 */
	public void removeBuilding(Building building) {
		
		Iterator<Duct> i = duc.getDucts(building).iterator();
		while (i.hasNext()) removeDuct(i.next());
		
		if (buc.removeElement(building)){ 
			log.info("Удален: Здание {}", building);
			changes = true;
		}
	}
	/**
	 * Удаляет сеть и все элементы в ней
	 * @param net - сеть
	 */
	public void removeNet (Net net) {
		
		Iterator<StructuredElement> i = cbc.getInNet(net).iterator();
		while (i.hasNext()) removeCabinet((Cabinet)i.next());
		
		i = dfc.getInNet(net).iterator();
		while (i.hasNext()) removeDFrame((DFramе)i.next());
		
		i = dbc.getInNet(net).iterator();
		while (i.hasNext()) removeDBox((DBox)i.next());
		
		i = mc.getInNet(net).iterator();
		while (i.hasNext()) removeManhole((Manhole)i.next());
		
		i = buc.getInNet(net).iterator();
		while (i.hasNext()) removeBuilding((Building)i.next());
		
		i = cc.getInNet(net).iterator();
		while (i.hasNext()) removeCable((Cable)i.next());
		
		i = sc.getInNet(net).iterator();
		while (i.hasNext()) removeSubscriber((Subscriber)i.next());
		
		if (nc.removeElement(net)){
			log.info("Удалена: Сеть {}", net);
			changes = true;
		}
	}
	/**
	 * Удаляет абонента. Удаляются также все включения данного абонента
	 * @param sub - Абонент
	 */
	public void removeSubscriber(Subscriber sub) {
		
		Iterator <Path> p = phc.getSubscriberPaths(sub).iterator();
		while (p.hasNext()) removePath(p.next());
		if (sc.removeElement(sub)) {
			log.info("Удален: Абонент {}",sub);
			changes = true;
		}
	}
	/**
	 * Удаляет включение. Пара освобождается, если больше не задействована ни в одном включении
	 * @param path - Абонент
	 */
	public void removePath(Path path) {
		
		Iterator <Pair> p = path.getUsedPairs().iterator();
		path.removeAllPairs();
		
		while (p.hasNext()){
			Pair pair = p.next();
			log.info("Пара {} удалена из включения: {} у абонента: {}", pair, path, sc.getElement(path.getSubscriber()) );
			changes = true;
			
			if (phc.isPairUsed(pair) == null)  {
				pair.setStatus(0);
				log.info("Пара {} освобождена",pair );
				changes = true;
			}	
		}
		if (phc.removeElement(path)) {
			log.info("Удален: Включение {} у абонента: {}", path, sc.getElement(path.getSubscriber()) );
			changes = true;
		}
	}
	
	public void clear(){
		changes = false;
	//	rw.deleteNotSavedLog();
		nc.removeAllElements(); 
		dfc.removeAllElements();
		cbc.removeAllElements(); 
		dbc.removeAllElements();
		mc.removeAllElements(); 
		fc.removeAllElements();
		bc.removeAllElements(); 
		cc.removeAllElements();
		pc.removeAllElements(); 
		sc.removeAllElements();
		phc.removeAllElements(); 
		duc.removeAllElements();
		tuc.removeAllElements(); 
		buc.removeAllElements();
		dmc.removeAllElements();
		
		ig.setIdIndex(0);
		
	}
	
	public Integer getSize(){
		return 
				nc.getSize() 
				+ dfc.getSize() 
				+ cbc.getSize() 
				+ dbc.getSize() 
				+ mc.getSize() 
				+ duc.getSize() 
				+ buc.getSize() 
				+ tuc.getSize() 
				+ fc.getSize() 
				+ bc.getSize() 
				+ cc.getSize() 
				+ pc.getSize() 
				+ phc.getSize() 
				+ sc.getSize() 
				+ dmc.getSize(); 
	}
	
	
}