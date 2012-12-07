package org.teleline.io;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.teleline.gui.FormProgressBar;
import org.teleline.model.Box;
import org.teleline.model.Building;
import org.teleline.model.Cabinet;
import org.teleline.model.Cable;
import org.teleline.model.DBox;
import org.teleline.model.DFramе;
import org.teleline.model.Damage;
import org.teleline.model.Duct;
import org.teleline.model.Frame;
import org.teleline.model.Manhole;
import org.teleline.model.Net;
import org.teleline.model.Pair;
import org.teleline.model.Path;
import org.teleline.model.Subscriber;
import org.teleline.model.Tube;
import org.teleline.system.Sys;


public class Reader extends RW implements Runnable{
	
	private volatile Thread t;
	private File xmlFile;
	
	public Reader(Sys sys, File xmlFile) {
		super(sys);
		t = new Thread(this);
		this.xmlFile = xmlFile;
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void run() {
		FormProgressBar form = new FormProgressBar(sys);
		form.iFrame.setTitle(form.iFrame.getTitle() + " - чтение файла");
		try {
			SAXBuilder builder = new SAXBuilder();
			//File xmlFile = new File(file);
			
			Document document = (Document) builder.build(xmlFile);
			Element system = document.getRootElement();
			sys.ig.setIdIndex(valueOf(system.getAttribute("idIndex")));
			Integer size = valueOf(system.getAttribute("size")) + 1;
			
			Integer netId = 1;
			Element n = null;
			Iterator<?> i = null;
			
			/**
			 * Считываем элементы "Сеть"
			 */
			i = system.getChildren("net").iterator();
			
			while(i.hasNext()) { n = (Element)i.next();
				
				Net net = new Net();
				netId = valueOf(n.getAttribute("i"));
				net.setId(netId); 
				net.setName(textOf(n.getChild("name")));
				
				sys.nc.putElement(net);
			}
			
			form.progressBar.setValue((sys.getSize() +1) * 100 / size);
			t.sleep(10);
			/**
			 * Считываем элементы "Кросс"
			 */
			i = system.getChildren("dframe").iterator();
			
			while(i.hasNext()) { n = (Element)i.next();
				
				DFramе dframe = new DFramе();
				
				dframe.setId(valueOf(n.getAttribute("i"))); 
				//dframe.attachToNet(n.getAttribute("ni").getIntValue());
				dframe.attachToNet(netId);
				dframe.setName(textOf(n.getChild("name")));
				dframe.setPlacesCount(valueOf(n.getChildText("places")));
				
				sys.dfc.putElement(dframe);
			}
			form.progressBar.setValue((sys.getSize() + 1) * 100 / size);
			t.sleep(10);
			/**
			 * Считываем элементы "Шкаф"
			 */
			i = system.getChildren("cab").iterator();
			
			while(i.hasNext()) { n = (Element)i.next();
				
				Cabinet cabinet = new Cabinet();
				
				cabinet.setId(valueOf(n.getAttribute("i"))); 
				//cabinet.attachToNet(n.getAttribute("ni").getIntValue());
				cabinet.attachToNet(netId);
							
				cabinet.setSNumber(textOf(n.getChild("n")));
				cabinet.setCabinetClass(valueOf(n.getChildText("c")));
				cabinet.setPlacesCount(valueOf(n.getChildText("p")));
				
				cabinet.setAdress(textOf(n.getChild("a")));
				cabinet.setPlace(textOf(n.getChild("l")));
				cabinet.setMaterial(textOf(n.getChild("m")));
				cabinet.setDate(textOf(n.getChild("d")));
				cabinet.setSetup(valueOf(n.getChildText("s")));
				cabinet.setArea(valueOf(n.getChildText("r")));
				
				sys.cbc.putElement(cabinet);
			}
			form.progressBar.setValue((sys.getSize() + 1) * 100 / size);
			t.sleep(10);
			/**
			 * Считываем элементы "Коробка"
			 */
			
			i = system.getChildren("dbox").iterator();
			
			while(i.hasNext()) { n = (Element)i.next();
				
				DBox dbox = new DBox(sys.dfc,sys.cbc,sys.fc,sys.bc,sys.pc,sys.cc,sys.buc);
				
				dbox.setId(valueOf(n.getAttribute("i"))); 
				//dbox.attachToNet(n.getAttribute("ni").getIntValue());
				dbox.attachToNet(netId);
				
				dbox.setCapacity(valueOf(n.getChildText("c")));
				dbox.setBuilding(valueOf(n.getChildText("b")));
				dbox.setPlase(textOf(n.getChild("p")));
				
				sys.dbc.putElement(dbox);
			}
			form.progressBar.setValue((sys.getSize() +1) * 100 / size);
			t.sleep(10);
			/**
			 * Считываем элементы "Колодец"
			 */
			
			i = system.getChildren("mnh").iterator();
			
			while(i.hasNext()) { n = (Element)i.next();
				
				Manhole manhole = new Manhole();
				
				manhole.setId(valueOf(n.getAttribute("i"))); 
				//manhole.attachToNet(n.getAttribute("ni").getIntValue());
				manhole.attachToNet(netId);
				
				manhole.setSNumber(textOf(n.getChild("n")));
				
				manhole.setDate(textOf(n.getChild("d")));
				manhole.setAdress(textOf(n.getChild("a")));
				manhole.setSize(textOf(n.getChild("s")));
				
				manhole.setConstruction(valueOf(n.getChildText("c")));
				manhole.setForm(valueOf(n.getChildText("f")));
				
				sys.mc.putElement(manhole);
			}
			form.progressBar.setValue((sys.getSize() +1) * 100 / size);
			t.sleep(10);
			/*
			 * Считываем элементы "Кабельная канализация"
			 */
			i = system.getChildren("dct").iterator();
			
			while(i.hasNext()) { n = (Element) i.next();
			
				Duct duct = new Duct(sys.dfc, sys.cbc, sys.mc, sys.buc);
				duct.setId(valueOf(n.getAttribute("i")));
				duct.setFrom(valueOf(n.getAttribute("f")));
				duct.setTo(valueOf(n.getAttribute("t")));
				//duct.attachToNet(n.getAttribute("ni").getIntValue());
				duct.attachToNet(netId);
				
				duct.setFromSide(valueOf(n.getChildText("fs")));
				duct.setToSide(valueOf(n.getChildText("ts")));
				
				duct.setLenght(valueOf(n.getChildText("l")));
				duct.setTubeDiametr(valueOf(n.getChildText("td")));
				duct.setTubeMaterial(textOf(n.getChild("tm")));
				duct.setDate(textOf(n.getChild("d")));
				duct.setМanufacturingМethod(n.getChildText("mm"));
				
				sys.duc.putElement(duct);	
			}
			form.progressBar.setValue((sys.getSize() +1) * 100 / size);
			t.sleep(10);
			/*
			 * Считываем элементы "Канал в канализации"
			 */
			i = system.getChildren("tub").iterator();
			
			while(i.hasNext()) { n = (Element)i.next();
			
				Tube tube = new Tube();
				
				tube.setId(valueOf(n.getAttribute("i"))); 
				tube.setDuct(valueOf(n.getAttribute("o")));
				
				tube.setNumber(valueOf(n.getChildText("n")));
				
				Iterator<?> k =  n.getChildren("c").iterator();
				while(k.hasNext()) {
					
					Element mp = (Element)k.next();
					
					Iterator<?> m = mp.getChildren("i").iterator();
					
					while(m.hasNext()) {
						Element p = (Element)m.next();
						tube.addCable(valueOf(p.getValue()));
					}
				}
				sys.tuc.putElement(tube);
			}
			form.progressBar.setValue((sys.getSize() +1) * 100 / size);
			t.sleep(10);
			/*
			 * Считываем элементы "Здание"
			 */
			i = system.getChildren("bld").iterator();
			
			while(i.hasNext()) { n = (Element)i.next();
				
				Building building = new Building();
				
				building.setId(valueOf(n.getAttribute("i"))); 
				//building.attachToNet(n.getAttribute("ni").getIntValue());
				building.attachToNet(netId);
				
				building.setSNumber(textOf(n.getChild("n")));
				building.setName(textOf(n.getChild("m")));
				building.setStreet(textOf(n.getChild("s")));
								
				sys.buc.putElement(building);
				
			}
			form.progressBar.setValue((sys.getSize() +1) * 100 / size);
			t.sleep(10);
			/*
			 * Считываем элементы "Громполоса"
			 */
			i = system.getChildren("frm").iterator();
			
			while(i.hasNext()) { n = (Element)i.next();
				
				Frame frame = new Frame();
				
				frame.setId(valueOf(n.getAttribute("i"))); 
				frame.attachTo(valueOf(n.getAttribute("o")));
				
				frame.setNumber(valueOf(n.getChildText("n")));
				frame.setCapacity(valueOf(n.getChildText("c")));
				frame.setPlaceNumber(valueOf(n.getChildText("p")));
								
				sys.fc.putElement(frame);
			}
			form.progressBar.setValue((sys.getSize() +1) * 100 / size);
			t.sleep(10);
			
			/*
			 * Считываем элементы "Бокс"
			 */
			
			i = system.getChildren("box").iterator();
			
			while(i.hasNext()) { n = (Element)i.next();
				
				Box box = new Box();
				
				box.setId(valueOf(n.getAttribute("i"))); 
				box.attachTo(valueOf(n.getAttribute("o")));
				
				box.setNumber(valueOf(n.getChildText("n")));
				box.setCapacity(valueOf(n.getChildText("c")));
			
				box.setType(valueOf(n.getChildText("t")));
				box.setPlaceNumber(valueOf(n.getChildText("p")));
								
				sys.bc.putElement(box);
			}
			form.progressBar.setValue((sys.getSize() +1) * 100 / size);
			t.sleep(10);
			
			/*
			 * Считываем элементы "Кабель"
			 */
			
			i = system.getChildren("cbl").iterator();
			
			while(i.hasNext()) { n = (Element)i.next();
				
				Cable cable = new Cable(sys.dfc,sys.cbc,/*dbc,fc,bc,*/sys.pc);
				
				cable.setId(valueOf(n.getAttribute("i"))); 
				cable.setFrom(valueOf(n.getAttribute("f")));
				cable.setTo(valueOf(n.getAttribute("t")));
				
				//cable.attachToNet(n.getAttribute("ni").getIntValue());
				cable.attachToNet(netId);
								
				cable.setCapacity(valueOf(n.getChildText("c")));
				cable.setType(valueOf(n.getChildText("t")));
				cable.setLabel(textOf(n.getChild("m")));
				cable.setNumber(valueOf(n.getChildText("n")));
			//	cable.setFrom(this.valueOf(n.getChildText("from")));
			//	cable.setTo(this.valueOf(n.getChildText("to")));
				cable.setLenght(valueOf(n.getChildText("l")));
				cable.setWireDiametr(textOf(n.getChild("w")));
				cable.setYear(textOf(n.getChild("y")));
				cable.setStatus(valueOf(n.getChildText("s")));
				
				sys.cc.putElement(cable);
			}
			form.progressBar.setValue((sys.getSize() +1) * 100 / size);
			t.sleep(10);
			
			/*
			 * Считываем элементы "Пара"
			 */
			
			i = system.getChildren("p").iterator();
			
			while(i.hasNext()) { n = (Element)i.next();
				
				Pair pair = new Pair(sys.fc,sys.bc,sys.dbc,sys.cc);
				
				pair.setId(valueOf(n.getAttribute("i"))); 
				pair.attachToCable(valueOf(n.getAttribute("c"))); 
				pair.attachToElementFrom(valueOf(n.getAttribute("f")));
				pair.attachToElementTo(valueOf(n.getAttribute("t")));
								
				pair.setNumberInCable(valueOf(n.getChildText("n")));
				pair.setFromNumber(valueOf(n.getChildText("f")));
				pair.setToNumber(valueOf(n.getChildText("t")));
				pair.setStatus(valueOf(n.getChildText("s")));
				
				sys.pc.putElement(pair);
			}
			form.progressBar.setValue((sys.getSize() +1) * 100 / size);
			t.sleep(10);
			
			/*
			 * Считываем элементы "Включение"
			 */
			i = system.getChildren("pth").iterator();
			
			while(i.hasNext()) { n = (Element)i.next();
				
				Path path = new Path(sys.sc,sys.pc);
				
				path.setId(valueOf(n.getAttribute("i")));
				
				path.setSubscriber(valueOf(n.getChildText("s")));
				path.setName(textOf(n.getChild("n")));
				path.setTransit(textOf(n.getChild("t")));
				path.addmPair(valueOf(n.getChildText("m")));
				
				path.adddrPair(valueOf(n.getChildText("r")));
				path.adddbPair(valueOf(n.getChildText("b")));
				
				
				Iterator<?> k =  n.getChildren("c").iterator();
				while(k.hasNext()) {
					
					Element icp = (Element)k.next();
					
					Iterator<?> m = icp.getChildren("i").iterator();
					
					while(m.hasNext()) {
						Element p = (Element)m.next();
						path.addicPair(valueOf(p.getValue()));
					}
				}
				
				sys.phc.putElement(path);
			}
			form.progressBar.setValue((sys.getSize() +1) * 100 / size);
			t.sleep(10);
			/*
			 * Считываем элементы "Абонент"
			 */
			
			i = system.getChildren("sub").iterator();
			
			while(i.hasNext()) { n = (Element)i.next();
				
				Subscriber subscriber = new Subscriber();
				
				subscriber.setId(valueOf(n.getAttribute("i"))); 
				//subscriber.attachToNet(n.getAttribute("ni").getIntValue()); 
				subscriber.attachToNet(netId);
				subscriber.setName(textOf(n.getChild("n"))); 
				subscriber.setPhoneNumber(textOf(n.getChild("p"))); 
				subscriber.setDate(textOf(n.getChild("d")));
				subscriber.setAdress(textOf(n.getChild("a")));
				subscriber.setEquipment(textOf(n.getChild("e"))); 
				
				sys.sc.putElement(subscriber);
				
				
			}
			form.progressBar.setValue((sys.getSize() +1) * 100 / size);
			t.sleep(10);
			/*
			 * Считываем элементы "Повреждение"
			 */
			
			i = system.getChildren("dmg").iterator();
			
			while(i.hasNext()) { n = (Element)i.next();
				
				Damage damage = new Damage();
				
				damage.setId(valueOf(n.getAttribute("i")));
				damage.attachTo(valueOf(n.getAttribute("oi")));
				damage.setName(textOf(n.getChild("n")));
				damage.setDescription(textOf(n.getChild("d")));
				damage.setOpenDate(textOf(n.getChild("o")));
				damage.setCloseDate(textOf(n.getChild("c")));
				
				sys.dmc.putElement(damage);
				
			}
			form.progressBar.setValue((sys.getSize() +1) * 100 / size);
			t.sleep(10);
			addLogMessage("Файл открыт: "+ xmlFile.getName() + "("+ xmlFile.length() + " байт)");
			addLogMessage("Количество элементов после открытия файла: " + sys.getSize()+"; "+ sys.nc.getSize()+"; dfc:"+sys.dfc.getSize()+"; cbc:"+sys.cbc.getSize()+"; dbc:"+sys.dbc.getSize()+"; mc:"+sys.mc.getSize()+"; duc:"+sys.duc.getSize()+"; buc:"+sys.buc.getSize()+"; tuc:"+sys.tuc.getSize()+"; fc:"+sys.fc.getSize()+"; bc:"+sys.bc.getSize()+"; cc:"+sys.cc.getSize()+"; pc:"+sys.pc.getSize()+"; phc:"+sys.phc.getSize()+"; sc:"+sys.sc.getSize()+"; dmc:"+sys.dmc.getSize());
			writeLog();
			form.label.setText("Файл \"" + xmlFile.getName() + "\" прочитан");
		  } 
			catch (IOException io) {
				writeError("Ошибка чтения файла: " + io.toString()); 
				form.label.setText("Ошибка при чтении файла");
		  } 
			catch (JDOMException jdomex) {
				writeError("Ошибка парсинга XML файла:" + jdomex.toString());
				form.label.setText("Ошибка при чтении файла");
		  } 
			catch (InterruptedException e) {
				writeError("Ошибка потока чтения:" + e.toString());
				form.label.setText("Ошибка при чтении файла");
		  } 
			catch(Exception e) {
				writeError("Ошибка чтения (без уточнения):" + e.toString());
				form.label.setText("Ошибка при чтении файла");
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