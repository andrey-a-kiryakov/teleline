package org.teleline.model;


import java.util.Iterator;

import org.teleline.gui.gui;
import org.teleline.io.RW;
import org.teleline.io.Validator;

public class Sys {
	
	public Sys(/*NetCollection nc, DFrameCollection dfc, CabinetCollection cbc, DBoxCollection dbc, ManholeCollection mc, DuctCollection duc, BuildingCollection buc, TubeCollection tuc, FrameCollection fc, BoxCollection bc, CableCollection cc, PairCollection pc, PathCollection phc, SubscriberCollection sc, DamageCollection dmc */) {
		
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
/*		
		this.nc = nc;
		this.dfc = dfc;
		this.cbc = cbc;
		this.dbc = dbc;
		this.mc = mc;
		this.duc = duc;
		this.buc = buc;
		this.tuc = tuc;
		this.fc = fc;
		this.bc = bc;
		this.cc = cc;
		this.pc = pc;
		this.phc = phc;
		this.sc = sc;
		this.dmc = dmc;
*/		
		this.v = new Validator();
		rw = new RW(ig,nc,dfc,cbc,dbc,mc,duc,buc,tuc,fc,bc,cc,pc,phc,sc,dmc);
	}
	
	public IdGenerator ig;
	public gui GUI;
	public RW rw;
	public Validator v;
	
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
					rw.addLogMessage("Пара "+ pair.toString()+" удалена из включения: "+ path.toString()+ " у абонента: " + sc.getElement(path.getSubscriber()).toString());
				}
			}
			if (pc.removeElement(pair))
				rw.addLogMessage("Удалена: Пара " + pair.toString());
		}
		if(bc.removeElement(box))
			rw.addLogMessage("Удален: Бокс " + box.toString());
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
					rw.addLogMessage("Пара "+ pair.toString()+" удалена из включения: "+ path.toString()+ " у абонента: " + sc.getElement(path.getSubscriber()).toString());
				}
			}
			if (pc.removeElement(pair))
				rw.addLogMessage("Удалена: Пара " + pair.toString());
		}
		if (dbc.removeElement(dbox))
			rw.addLogMessage("Удалена: КРТ " + dbox.toString());
	}
	/**
	 * Удаляет участок канализации. Удаляются все каналы в канализации. Кабели проходящии по данному участку не удаляются.
	 * @param duct - Канализация
	 */
	public void removeDuct(Duct duct) {
		
		Iterator<Tube> i = tuc.getDuctsTubes(duct).iterator();
		while (i.hasNext()) removeTube(i.next());
		if (duc.removeElement(duct))
			rw.addLogMessage("Удален: Участок кабельной канализации " + duct.toString());
	}
	/**
	 * Удаляет канал в канализации. Кабели, проходящие по каналу не удалаются
	 * @param tube - канал
	 */
	public void removeTube(Tube tube) {
		
		if (tuc.removeElement(tube))
			rw.addLogMessage("Удален: Канал " + tube.toString() + " в канализации "+ duc.getElement(tube.getDuct()));
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
		
		if (cbc.removeElement(element))
			rw.addLogMessage("Удален: Шкаф " + element.toString());
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
		
		if (dfc.removeElement(element))
			rw.addLogMessage("Удален: Кросс " + element.toString());
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
					rw.addLogMessage("Пара "+ pair.toString()+" удалена из включения: "+ path.toString()+ " у абонента: " + sc.getElement(path.getSubscriber()).toString());
				}
			}
			
			if (pc.removeElement(pair))
				rw.addLogMessage("Удалена: Пара " + pair.toString());
		}
		if (fc.removeElement(frame))
			rw.addLogMessage("Удален: Громполоса " + frame.toString());
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
					rw.addLogMessage("Пара "+ pair.toString()+" удалена из включения: "+ path.toString()+ " у абонента: " + sc.getElement(path.getSubscriber()).toString());
				}
			}
			if (pc.removeElement(pair))
				rw.addLogMessage("Удалена: Пара " + pair.toString());
		}
		
		Iterator <Tube> t = tuc.getTubesByCable(cable).iterator();
		while (t.hasNext()) {
			t.next().removeCable(cable); 
		}
		
		if (cc.removeElement(cable))
			rw.addLogMessage("Удален: Кабель " + cable.toString());
	}
	/**
	 * Удаляет колодец. Все участки кабельной канализации проходящие через колодец - удаляются
	 * @param man - колодец
	 */
	public void removeManhole(Manhole man){
		
		Iterator<Duct> i = duc.getDucts(man).iterator();
		while (i.hasNext()) removeDuct(i.next());
		if (mc.removeElement(man)) rw.addLogMessage("Удален: Колодец " + man.toString());
	}
	/**
	 * Удаляет здание. Удаляется также участок канализации, подходящий к зданию.
	 * @param building - здание
	 */
	public void removeBuilding(Building building) {
		
		Iterator<Duct> i = duc.getDucts(building).iterator();
		while (i.hasNext()) removeDuct(i.next());
		
		if (buc.removeElement(building)) rw.addLogMessage("Удален: Здание " + building.toString());
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
		
		if (nc.removeElement(net))
			rw.addLogMessage("Удалена: Сеть " + net.toString());
	}
	/**
	 * Удаляет абонента. Удаляются также все включения данного абонента
	 * @param sub - Абонент
	 */
	public void removeSubscriber(Subscriber sub) {
		
		Iterator <Path> p = phc.getSubscriberPaths(sub).iterator();
		while (p.hasNext()) removePath(p.next());
		if (sc.removeElement(sub)) rw.addLogMessage("Удален: Абонент " + sub.toString());
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
			rw.addLogMessage("Пара "+ pair.toString()+" удалена из включения: "+ path.toString()+ " у абонента: " + sc.getElement(path.getSubscriber()).toString());
			
			if (phc.isPairUsed(pair) == null)  {
				pair.setStatus(0);
				rw.addLogMessage("Пара "+ pair.toString()+" освобождена ");
			}	
		}
		if (phc.removeElement(path))
			rw.addLogMessage("Удален: Включение " + path.toString() + " у абонента: "+ sc.getElement(path.getSubscriber()).toString());
	}
	
	
}