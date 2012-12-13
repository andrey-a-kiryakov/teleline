package org.teleline.io;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.teleline.gui.FormProgressBar;
import org.teleline.model.AbstractElement;
import org.teleline.model.Box;
import org.teleline.model.Building;
import org.teleline.model.Cabinet;
import org.teleline.model.Cable;
import org.teleline.model.DBox;
import org.teleline.model.Damage;
import org.teleline.model.Duct;
import org.teleline.model.Frame;
import org.teleline.model.Manhole;
import org.teleline.model.Net;
import org.teleline.model.Pair;
import org.teleline.model.Path;
import org.teleline.model.StructuredElement;
import org.teleline.model.Subscriber;
import org.teleline.model.Tube;
import org.teleline.system.Sys;


public class Writer extends RW implements Runnable{

	private volatile Thread t;
	
	private static final Logger log = LoggerFactory.getLogger("Writer");
	
	public Writer(Sys sys) {
		super(sys);
		t = new Thread(this);			
	}

	@SuppressWarnings("static-access")
	@Override
	public void run() {
		FormProgressBar form = new FormProgressBar(sys);
		form.iFrame.setTitle(form.iFrame.getTitle() + " - сохранение файла");
		try {	 
			Element system = new Element("system");
			system.setAttribute(new Attribute ("idIndex", sys.ig.getId().toString()));
			system.setAttribute(new Attribute ("size", sys.getSize().toString()));
			
			Document document = new Document(system);
			Integer size = sys.getSize() + 1;
			Integer saveSize = 1;
			/*
			 * Записываем элементы "Сеть"
			 */	
			Iterator<AbstractElement> i = sys.nc.getIterator(); 
			
			while (i.hasNext())  {
			
				StructuredElement n = (StructuredElement)i.next();

				Element netXML = new Element ("net");
				netXML.setAttribute(new Attribute ("i", n.getId().toString()));

				netXML.addContent(new Element ("name").addContent(n.getName()));
				
				system.addContent(netXML); 
			}
			saveSize += sys.nc.getSize();
			form.progressBar.setValue(saveSize * 100 / size);
			t.sleep(10);
			
			/*
			 * Записываем элементы "Кросс"
			 */
			i = sys.dfc.getIterator();
			
			while(i.hasNext()) {
				
				StructuredElement f = (StructuredElement)i.next();
				
				Element frameXML = new Element ("dframe");
				frameXML.setAttribute(new Attribute ("i", f.getId().toString()));
				//frameXML.setAttribute(new Attribute ("ni", f.getNet().toString()));
				
				frameXML.addContent(new Element ("name").addContent(f.getName()));
				frameXML.addContent(new Element ("places").addContent(f.getPlacesCount().toString()));
				
				system.addContent(frameXML);
			}
			saveSize += sys.dfc.getSize();
			form.progressBar.setValue(saveSize * 100 / size);
			t.sleep(10);
			
			/*
			 * Записываем элементы "Шкаф"
			 */
			i = sys.cbc.getIterator();
			
			while(i.hasNext()) {
				
				Cabinet f = (Cabinet)i.next();
				
				Element cabinetXML = new Element ("cab");
				cabinetXML.setAttribute(new Attribute ("i", f.getId().toString()));
				//cabinetXML.setAttribute(new Attribute ("ni", f.getNet().toString()));
				
				cabinetXML.addContent(new Element ("c").addContent(f.getCabinetClass().toString()));
				cabinetXML.addContent(new Element ("n").addContent(f.getSNumber()));
				cabinetXML.addContent(new Element ("p").addContent(f.getPlacesCount().toString()));
				
				cabinetXML.addContent(new Element ("a").addContent(f.getAdress()));
				cabinetXML.addContent(new Element ("l").addContent(f.getPlace()));
				cabinetXML.addContent(new Element ("m").addContent(f.getMaterial()));
				cabinetXML.addContent(new Element ("d").addContent(f.getDate()));
				cabinetXML.addContent(new Element ("s").addContent(f.getSetup().toString()));
				cabinetXML.addContent(new Element ("r").addContent(f.getArea().toString()));
				
				system.addContent(cabinetXML);
			}
			saveSize += sys.cbc.getSize();
			form.progressBar.setValue(saveSize * 100 / size);
			t.sleep(10);
			/*
			 * Записываем элементы "Коробка"
			 */
			i = sys.dbc.getIterator();
			
			while(i.hasNext()) {
				
				Element dboxXML = new Element ("dbox");
				DBox f = (DBox)i.next();
				dboxXML.setAttribute(new Attribute ("i", f.getId().toString()));
				//dboxXML.setAttribute(new Attribute ("ni", f.getNet().toString()));
			
				dboxXML.addContent(new Element ("c").addContent(f.getCapacity().toString()));
				dboxXML.addContent(new Element ("b").addContent(f.getBuilding().toString()));
				dboxXML.addContent(new Element ("p").addContent(f.getPlase()));
				
				system.addContent(dboxXML);
			}
			saveSize += sys.dbc.getSize();
			form.progressBar.setValue(saveSize * 100 / size);
			t.sleep(10);
			/*
			 * Записываем элементы "Колодец"
			 */
			i = sys.mc.getIterator();
			
			while(i.hasNext()) {
				
				Manhole f = (Manhole)i.next();
				
				Element manholeXML = new Element ("mnh");
				manholeXML.setAttribute(new Attribute ("i", f.getId().toString()));
				//manholeXML.setAttribute(new Attribute ("ni", f.getNet().toString()));
				
				manholeXML.addContent(new Element ("n").addContent(f.getSNumber()));
				manholeXML.addContent(new Element ("d").addContent(f.getDate()));
				manholeXML.addContent(new Element ("a").addContent(f.getAdress()));
				manholeXML.addContent(new Element ("s").addContent(f.getSize()));
				
				manholeXML.addContent(new Element ("c").addContent(f.getConstruction().toString()));
				manholeXML.addContent(new Element ("f").addContent(f.getForm().toString()));
				
				system.addContent(manholeXML);
			}
			saveSize += sys.mc.getSize();
			form.progressBar.setValue(saveSize * 100 / size);
			t.sleep(10);
			/*
			 * Записываем элементы "Кабельная канализация"
			 */
			i = sys.duc.getIterator();
			
			while(i.hasNext()) {
				
				Duct f = (Duct)i.next();
				
				Element ductXML = new Element ("dct");
				ductXML.setAttribute(new Attribute ("i", f.getId().toString()));
				ductXML.setAttribute(new Attribute ("f", f.getFrom().toString()));
				ductXML.setAttribute(new Attribute ("t", f.getTo().toString()));
				
				//ductXML.setAttribute(new Attribute ("ni", f.getNet().toString()));
				
			//	ductXML.addContent(new Element("from").addContent(f.getFrom().toString()));
			//	ductXML.addContent(new Element("to").addContent(f.getTo().toString()));
				
				ductXML.addContent(new Element("fs").addContent(f.getFromSide().toString()));
				ductXML.addContent(new Element("ts").addContent(f.getToSide().toString()));
				
				ductXML.addContent(new Element("l").addContent(f.getLenght().toString()));
				ductXML.addContent(new Element("td").addContent(f.getTubeDiametr().toString()));
				ductXML.addContent(new Element("tm").addContent(f.getTubeMaterial()));
				ductXML.addContent(new Element("d").addContent(f.getDate()));
				ductXML.addContent(new Element("mm").addContent(f.getМanufacturingМethod()));

				system.addContent(ductXML);	
			}
			saveSize += sys.duc.getSize();
			form.progressBar.setValue(saveSize * 100 / size);
			t.sleep(10);
			/*
			 * Записываем элементы канал в канализации
			 */
			i = sys.tuc.getIterator();
			
			while(i.hasNext()) {
				
				Tube f = (Tube)i.next();
				
				Element tubeXML = new Element ("tub");
				tubeXML.setAttribute(new Attribute ("i", f.getId().toString()));
				tubeXML.setAttribute(new Attribute ("o", f.getDuct().toString()));
				
				tubeXML.addContent(new Element ("n").addContent(f.getNumber().toString()));
				
				if (f.cablesCount() > 0) {
					
					Element elementXML = new Element ("c");
					Iterator<Integer> c = f.getCables().iterator();
					while (c.hasNext()) elementXML.addContent(new Element ("i").addContent(c.next().toString()));
					tubeXML.addContent(elementXML);
				}
				system.addContent(tubeXML);
			}
			saveSize += sys.tuc.getSize();
			form.progressBar.setValue(saveSize * 100 / size);
			t.sleep(10);
			/*
			 * Записываем элементы здание
			 */
			i = sys.buc.getIterator();
			
			while(i.hasNext()) {
				
				Building f = (Building)i.next();
				
				Element buildingXML = new Element ("bld");
				buildingXML.setAttribute(new Attribute ("i", f.getId().toString()));
				//buildingXML.setAttribute(new Attribute ("ni", f.getNet().toString()));
				
				buildingXML.addContent(new Element ("s").addContent(f.getStreet()));
				buildingXML.addContent(new Element ("n").addContent(f.getSNumber()));
				buildingXML.addContent(new Element ("m").addContent(f.getName()));
				
				system.addContent(buildingXML);
			}
			saveSize += sys.buc.getSize();
			form.progressBar.setValue(saveSize * 100 / size);
			t.sleep(10);
			/*
			 * Записываем элементы "Громполоса"
			 */
			i = sys.fc.getIterator();
			
			while(i.hasNext()) {
				
				Frame f = (Frame)i.next();
				
				Element frameXML = new Element ("frm");
				frameXML.setAttribute(new Attribute ("i", f.getId().toString()));
				frameXML.setAttribute(new Attribute ("o", f.getOwnerId().toString()));
								
				frameXML.addContent(new Element ("n").addContent(f.getNumber().toString()));
				frameXML.addContent(new Element ("c").addContent(f.getCapacity().toString()));
				frameXML.addContent(new Element("p").addContent(f.getPlaceNumber().toString()));
			
				system.addContent(frameXML);
			}
			saveSize += sys.fc.getSize();
			form.progressBar.setValue(saveSize * 100 / size);
			t.sleep(10);
			/*
			 * Записываем элементы "Бокс"
			 */
			i = sys.bc.getIterator();
			
			while(i.hasNext()) {
				
				Box f = (Box)i.next();
				
				Element boxXML = new Element ("box");
				boxXML.setAttribute(new Attribute ("i", f.getId().toString()));
				boxXML.setAttribute(new Attribute ("o", f.getOwnerId().toString()));
				
				boxXML.addContent(new Element ("n").addContent(f.getNumber().toString()));
				boxXML.addContent(new Element ("c").addContent(f.getCapacity().toString()));
				boxXML.addContent(new Element("t").addContent(f.getType().toString()));
				boxXML.addContent(new Element("p").addContent(f.getPlaceNumber().toString()));
				
				system.addContent(boxXML);
			}
			saveSize += sys.bc.getSize();
			form.progressBar.setValue(saveSize * 100 / size);
			t.sleep(10);
			/*
			 * Записываем элементы "Кабель"
			 */
			i = sys.cc.getIterator();
			
			while(i.hasNext()) {
				
				Cable f = (Cable)i.next();
				
				Element cableXML = new Element ("cbl");
				cableXML.setAttribute(new Attribute ("i", f.getId().toString()));
				cableXML.setAttribute(new Attribute ("f", f.getFrom().toString()));
				cableXML.setAttribute(new Attribute ("t", f.getTo().toString()));
			
				cableXML.addContent(new Element ("c").addContent(f.getCapacity().toString()));
				cableXML.addContent(new Element("t").addContent(f.getType().toString()));
				cableXML.addContent(new Element("m").addContent(f.getLabel()));
				cableXML.addContent(new Element("n").addContent(f.getNumber().toString()));
			//	cableXML.addContent(new Element("from").addContent(f.getFrom().toString()));
			//	cableXML.addContent(new Element("to").addContent(f.getTo().toString()));
				cableXML.addContent(new Element("l").addContent(f.getLenght().toString()));
				cableXML.addContent(new Element("w").addContent(f.getWireDiametr()));
				cableXML.addContent(new Element("y").addContent(f.getYear()));
				cableXML.addContent(new Element("s").addContent(f.getStatus().toString()));
				
				system.addContent(cableXML);
			}
			saveSize += sys.cc.getSize();
			form.progressBar.setValue(saveSize * 100 / size);
			t.sleep(10);
			/*
			 * Записываем элементы "Пара"
			 */
			i = sys.pc.getIterator();
				
			while(i.hasNext()) {
				
				Pair f = (Pair)i.next();
				
				Element pairXML = new Element ("p");
				pairXML.setAttribute(new Attribute ("i", f.getId().toString()));
				pairXML.setAttribute(new Attribute ("c", f.getCable().toString()));
				pairXML.setAttribute(new Attribute ("f", f.getElementFrom().toString()));
				pairXML.setAttribute(new Attribute ("t", f.getElementTo().toString()));
								
				pairXML.addContent(new Element ("n").addContent(f.getNumberInCable().toString()));
				pairXML.addContent(new Element ("f").addContent(f.getFromNumber().toString()));
				pairXML.addContent(new Element ("t").addContent(f.getToNumber().toString()));
				pairXML.addContent(new Element ("s").addContent(f.getStatus().toString()));
								
				system.addContent(pairXML);
			}
			saveSize += sys.pc.getSize();
			form.progressBar.setValue(saveSize * 100 / size);
			t.sleep(10);
			/*
			 * Записываем элементы "Включение"
			 */
			i = sys.phc.getIterator();
					
			while(i.hasNext()) {
				
				Path f = (Path)i.next();
				
				Element pathXML = new Element ("pth");
				pathXML.setAttribute(new Attribute ("i", f.getId().toString()));
				
				pathXML.addContent(new Element ("s").addContent(f.getSubscriber().toString()));
				pathXML.addContent(new Element ("n").addContent(f.getName()));
				pathXML.addContent(new Element ("t").addContent(f.getTransit()));
				pathXML.addContent(new Element ("m").addContent(f.getmPair().toString()));
				
				pathXML.addContent(new Element ("r").addContent(f.getdrPair().toString()));	
				pathXML.addContent(new Element ("b").addContent(f.getdbPair().toString()));
		
				if (f.isicPair()) {
					
					Element elementXML = new Element ("c");
					for (int k = 0; k < f.geticPair().size(); k++)
						elementXML.addContent(new Element ("i").addContent(f.geticPair().get(k).toString()));
					
					pathXML.addContent(elementXML);
				}
				
				system.addContent(pathXML);
			}
			saveSize += sys.phc.getSize();
			form.progressBar.setValue(saveSize * 100 / size);
			t.sleep(10);
			/*
			 * Записываем элементы "Абонент"
			 */
			i = sys.sc.getIterator();
				
			while(i.hasNext()) {
					
				Element subscriberXML = new Element ("sub");
				Subscriber f = (Subscriber)i.next();
				subscriberXML.setAttribute(new Attribute ("i", f.getId().toString()));
				//subscriberXML.setAttribute(new Attribute ("ni", f.getNet().toString()));
					
				subscriberXML.addContent(new Element ("n").addContent(f.getName()));
				subscriberXML.addContent(new Element ("p").addContent(f.getPhoneNumber()));
				subscriberXML.addContent(new Element ("d").addContent(f.getDate()));
				subscriberXML.addContent(new Element ("a").addContent(f.getAdress()));
				subscriberXML.addContent(new Element ("e").addContent(f.getEquipment()));
					
				system.addContent(subscriberXML);
			}
			saveSize += sys.sc.getSize();
			form.progressBar.setValue(saveSize * 100 / size);
			t.sleep(10);
			/*
			 * Записываем элементы "Повреждения"
			 */
			i = sys.dmc.getIterator();
				
			while (i.hasNext()) {
					
				Element damageXML = new Element ("dmg");
				Damage d = (Damage)i.next();
				damageXML.setAttribute(new Attribute ("i", d.getId().toString()));
				damageXML.setAttribute(new Attribute ("oi", d.getOwnerId().toString()));
				
				damageXML.addContent(new Element ("n").addContent(d.getName()));
				damageXML.addContent(new Element ("d").addContent(d.getDescription()));
				damageXML.addContent(new Element ("o").addContent(d.getOpenDate()));
				damageXML.addContent(new Element ("c").addContent(d.getCloseDate()));
				
				system.addContent(damageXML);
			}
			saveSize += sys.dmc.getSize();
			
			XMLOutputter xmlOutput = new XMLOutputter();
	 		//xmlOutput.setFormat(Format.getPrettyFormat());
	 		
	 		xmlOutput.setFormat(Format.getCompactFormat());
	 		
			SimpleDateFormat DF = new SimpleDateFormat("yyyy-MM-dd_HHmmssS");
			String fileName = fSave +((Net)sys.nc.getOnlyElement()).getName()+"_"+ DF.format(Calendar.getInstance().getTime()) + ".xml";
			
			log.debug("Количество элементов перед сохранением: " + sys.getSize()+"; "+ sys.nc.getSize()+"; dfc:"+sys.dfc.getSize()+"; cbc:"+sys.cbc.getSize()+"; dbc:"+sys.dbc.getSize()+"; mc:"+sys.mc.getSize()+"; duc:"+sys.duc.getSize()+"; buc:"+sys.buc.getSize()+"; tuc:"+sys.tuc.getSize()+"; fc:"+sys.fc.getSize()+"; bc:"+sys.bc.getSize()+"; cc:"+sys.cc.getSize()+"; pc:"+sys.pc.getSize()+"; phc:"+sys.phc.getSize()+"; sc:"+sys.sc.getSize()+"; dmc:"+sys.dmc.getSize());
			
			xmlOutput.output(document, new FileOutputStream(fileName));
			
			form.progressBar.setValue(saveSize * 100 / size);
			t.sleep(10);
			
			File file = new File(fileName);
			
			log.info("Файл сохранен: {}({} байт)",fileName,file.length());
			form.label.setText("Файл " + fileName + " сохранен");
		  } 
			catch(Exception e) {
				log.error("Ошибка сохранения файла: ", e);
				form.label.setText("Ошибка при сохранении файла");
			}
		stop();
		form.okButton.setEnabled(true);
	}
	
	public void start(){
		t.start();
	}
	public void stop(){
		t = null;
	}
}