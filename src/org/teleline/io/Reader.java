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
import org.teleline.model.Sys;
import org.teleline.model.Tube;

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
			sys.ig.setIdIndex(system.getAttribute("idIndex").getIntValue());
			Integer size = system.getAttribute("size").getIntValue();
			
			Integer netId = 1;
			Element n = null;
			Iterator<?> i = null;
			
			/**
			 * Считываем элементы "Сеть"
			 */
			i = system.getChildren("net").iterator();
			
			while(i.hasNext()) { n = (Element)i.next();
				
				Net net = new Net();
				netId = n.getAttribute("i").getIntValue();
				net.setId(netId); 
				net.setName(n.getChildText("name"));
				
				sys.nc.putElement(net);
			}
			
			form.progressBar.setValue(sys.getSize() * 100 / size);
			t.sleep(10);
			/**
			 * Считываем элементы "Кросс"
			 */
			i = system.getChildren("dframe").iterator();
			
			while(i.hasNext()) { n = (Element)i.next();
				
				DFramе dframe = new DFramе();
				
				dframe.setId(n.getAttribute("i").getIntValue()); 
				//dframe.attachToNet(n.getAttribute("ni").getIntValue());
				dframe.attachToNet(netId);
				dframe.setName(n.getChildText("name"));
				dframe.setPlacesCount(this.valueOf(n.getChildText("places")));
				
				sys.dfc.putElement(dframe);
			}
			form.progressBar.setValue(sys.getSize() * 100 / size);
			t.sleep(10);
			/**
			 * Считываем элементы "Шкаф"
			 */
			i = system.getChildren("cab").iterator();
			
			while(i.hasNext()) { n = (Element)i.next();
				
				Cabinet cabinet = new Cabinet();
				
				cabinet.setId(n.getAttribute("i").getIntValue()); 
				//cabinet.attachToNet(n.getAttribute("ni").getIntValue());
				cabinet.attachToNet(netId);
							
				cabinet.setSNumber(n.getChildText("n"));
				cabinet.setCabinetClass(this.valueOf(n.getChildText("c")));
				cabinet.setPlacesCount(this.valueOf(n.getChildText("p")));
				
				cabinet.setAdress(n.getChildText("a"));
				cabinet.setPlace(n.getChildText("l"));
				cabinet.setMaterial(n.getChildText("m"));
				cabinet.setDate(n.getChildText("d"));
				cabinet.setSetup(this.valueOf(n.getChildText("s")));
				cabinet.setArea(this.valueOf(n.getChildText("r")));
				
				sys.cbc.putElement(cabinet);
			}
			form.progressBar.setValue(sys.getSize() * 100 / size);
			t.sleep(10);
			/**
			 * Считываем элементы "Коробка"
			 */
			
			i = system.getChildren("dbox").iterator();
			
			while(i.hasNext()) { n = (Element)i.next();
				
				DBox dbox = new DBox(sys.dfc,sys.cbc,sys.fc,sys.bc,sys.pc,sys.cc,sys.buc);
				
				dbox.setId(n.getAttribute("i").getIntValue()); 
				//dbox.attachToNet(n.getAttribute("ni").getIntValue());
				dbox.attachToNet(netId);
				
				dbox.setCapacity(this.valueOf(n.getChildText("c")));
				dbox.setBuilding(this.valueOf(n.getChildText("b")));
				dbox.setPlase(n.getChildText("p"));
				
				sys.dbc.putElement(dbox);
			}
			form.progressBar.setValue(sys.getSize() * 100 / size);
			t.sleep(10);
			/**
			 * Считываем элементы "Колодец"
			 */
			
			i = system.getChildren("mnh").iterator();
			
			while(i.hasNext()) { n = (Element)i.next();
				
				Manhole manhole = new Manhole();
				
				manhole.setId(n.getAttribute("i").getIntValue()); 
				//manhole.attachToNet(n.getAttribute("ni").getIntValue());
				manhole.attachToNet(netId);
				
				manhole.setSNumber(n.getChildText("n"));
				
				manhole.setDate(n.getChildText("d"));
				manhole.setAdress(n.getChildText("a"));
				manhole.setSize(n.getChildText("s"));
				
				manhole.setConstruction(this.valueOf(n.getChildText("c")));
				manhole.setForm(this.valueOf(n.getChildText("f")));
				
				sys.mc.putElement(manhole);
			}
			form.progressBar.setValue(sys.getSize() * 100 / size);
			t.sleep(10);
			/*
			 * Считываем элементы "Кабельная канализация"
			 */
			i = system.getChildren("dct").iterator();
			
			while(i.hasNext()) { n = (Element) i.next();
			
				Duct duct = new Duct(sys.dfc, sys.cbc, sys.mc, sys.buc);
				duct.setId(n.getAttribute("i").getIntValue());
				duct.setFrom(n.getAttribute("f").getIntValue());
				duct.setTo(n.getAttribute("t").getIntValue());
				//duct.attachToNet(n.getAttribute("ni").getIntValue());
				duct.attachToNet(netId);
				
				duct.setFromSide(this.valueOf(n.getChildText("fs")));
				duct.setToSide(this.valueOf(n.getChildText("ts")));
				
				duct.setLenght(this.valueOf(n.getChildText("l")));
				duct.setTubeDiametr(this.valueOf(n.getChildText("td")));
				duct.setTubeMaterial(n.getChildText("tm"));
				duct.setDate(n.getChildText("d"));
				duct.setМanufacturingМethod(n.getChildText("mm"));
				
				sys.duc.putElement(duct);	
			}
			form.progressBar.setValue(sys.getSize() * 100 / size);
			t.sleep(10);
			/*
			 * Считываем элементы "Канал в канализации"
			 */
			i = system.getChildren("tub").iterator();
			
			while(i.hasNext()) { n = (Element)i.next();
			
				Tube tube = new Tube();
				
				tube.setId(n.getAttribute("i").getIntValue()); 
				tube.setDuct(n.getAttribute("o").getIntValue());
				
				tube.setNumber(this.valueOf(n.getChildText("n")));
				
				Iterator<?> k =  n.getChildren("c").iterator();
				while(k.hasNext()) {
					
					Element mp = (Element)k.next();
					
					Iterator<?> m = mp.getChildren("i").iterator();
					
					while(m.hasNext()) {
						Element p = (Element)m.next();
						tube.addCable(this.valueOf(p.getValue()));
					}
				}
				sys.tuc.putElement(tube);
			}
			form.progressBar.setValue(sys.getSize() * 100 / size);
			t.sleep(10);
			/*
			 * Считываем элементы "Здание"
			 */
			i = system.getChildren("bld").iterator();
			
			while(i.hasNext()) { n = (Element)i.next();
				
				Building building = new Building();
				
				building.setId(n.getAttribute("i").getIntValue()); 
				//building.attachToNet(n.getAttribute("ni").getIntValue());
				building.attachToNet(netId);
				
				building.setSNumber(n.getChildText("n"));
				building.setName(n.getChildText("m"));
				building.setStreet(n.getChildText("s"));
								
				sys.buc.putElement(building);
				
			}
			form.progressBar.setValue(sys.getSize() * 100 / size);
			t.sleep(10);
			/*
			 * Считываем элементы "Громполоса"
			 */
			i = system.getChildren("frm").iterator();
			
			while(i.hasNext()) { n = (Element)i.next();
				
				Frame frame = new Frame();
				
				frame.setId(n.getAttribute("i").getIntValue()); 
				frame.attachTo(n.getAttribute("o").getIntValue());
				
				frame.setNumber(this.valueOf(n.getChildText("n")));
				frame.setCapacity(this.valueOf(n.getChildText("c")));
				frame.setPlaceNumber(this.valueOf(n.getChildText("p")));
								
				sys.fc.putElement(frame);
			}
			form.progressBar.setValue(sys.getSize() * 100 / size);
			t.sleep(10);
			
			/*
			 * Считываем элементы "Бокс"
			 */
			
			i = system.getChildren("box").iterator();
			
			while(i.hasNext()) { n = (Element)i.next();
				
				Box box = new Box();
				
				box.setId(n.getAttribute("i").getIntValue()); 
				box.attachTo(n.getAttribute("o").getIntValue());
				
				box.setNumber(this.valueOf(n.getChildText("n")));
				box.setCapacity(this.valueOf(n.getChildText("c")));
			
				box.setType(this.valueOf(n.getChildText("t")));
				box.setPlaceNumber(this.valueOf(n.getChildText("p")));
								
				sys.bc.putElement(box);
			}
			form.progressBar.setValue(sys.getSize() * 100 / size);
			t.sleep(10);
			
			/*
			 * Считываем элементы "Кабель"
			 */
			
			i = system.getChildren("cbl").iterator();
			
			while(i.hasNext()) { n = (Element)i.next();
				
				Cable cable = new Cable(sys.dfc,sys.cbc,/*dbc,fc,bc,*/sys.pc);
				
				cable.setId(n.getAttribute("i").getIntValue()); 
				cable.setFrom(n.getAttribute("f").getIntValue());
				cable.setTo(n.getAttribute("t").getIntValue());
				
				//cable.attachToNet(n.getAttribute("ni").getIntValue());
				cable.attachToNet(netId);
								
				cable.setCapacity(this.valueOf(n.getChildText("c")));
				cable.setType(this.valueOf(n.getChildText("t")));
				cable.setLabel(n.getChildText("m"));
				cable.setNumber(this.valueOf(n.getChildText("n")));
			//	cable.setFrom(this.valueOf(n.getChildText("from")));
			//	cable.setTo(this.valueOf(n.getChildText("to")));
				cable.setLenght(this.valueOf(n.getChildText("l")));
				cable.setWireDiametr(n.getChildText("w"));
				cable.setYear(n.getChildText("y"));
				cable.setStatus(this.valueOf(n.getChildText("s")));
				
				sys.cc.putElement(cable);
			}
			form.progressBar.setValue(sys.getSize() * 100 / size);
			t.sleep(10);
			
			/*
			 * Считываем элементы "Пара"
			 */
			
			i = system.getChildren("p").iterator();
			
			while(i.hasNext()) { n = (Element)i.next();
				
				Pair pair = new Pair(sys.fc,sys.bc,sys.dbc,sys.cc);
				
				pair.setId(n.getAttribute("i").getIntValue()); 
				pair.attachToCable(n.getAttribute("c").getIntValue()); 
				pair.attachToElementFrom(n.getAttribute("f").getIntValue());
				pair.attachToElementTo(n.getAttribute("t").getIntValue());
								
				pair.setNumberInCable(this.valueOf(n.getChildText("n")));
				pair.setFromNumber(this.valueOf(n.getChildText("f")));
				pair.setToNumber(this.valueOf(n.getChildText("t")));
				pair.setStatus(this.valueOf(n.getChildText("s")));
				
				sys.pc.putElement(pair);
			}
			form.progressBar.setValue(sys.getSize() * 100 / size);
			t.sleep(10);
			
			/*
			 * Считываем элементы "Включение"
			 */
			i = system.getChildren("pth").iterator();
			
			while(i.hasNext()) { n = (Element)i.next();
				
				Path path = new Path(sys.sc,sys.pc);
				
				path.setId(n.getAttribute("i").getIntValue());
				
				path.setSubscriber(this.valueOf(n.getChildText("s")));
				path.setName(n.getChildText("n"));
				path.setTransit(n.getChildText("t"));
				path.addmPair(this.valueOf(n.getChildText("m")));
				
				path.adddrPair(this.valueOf(n.getChildText("r")));
				path.adddbPair(this.valueOf(n.getChildText("b")));
				
				
				Iterator<?> k =  n.getChildren("c").iterator();
				while(k.hasNext()) {
					
					Element icp = (Element)k.next();
					
					Iterator<?> m = icp.getChildren("i").iterator();
					
					while(m.hasNext()) {
						Element p = (Element)m.next();
						path.addicPair(this.valueOf(p.getValue()));
					}
				}
				
				sys.phc.putElement(path);
			}
			form.progressBar.setValue(sys.getSize() * 100 / size);
			t.sleep(10);
			/*
			 * Считываем элементы "Абонент"
			 */
			
			i = system.getChildren("sub").iterator();
			
			while(i.hasNext()) { n = (Element)i.next();
				
				Subscriber subscriber = new Subscriber();
				
				subscriber.setId(n.getAttribute("i").getIntValue()); 
				//subscriber.attachToNet(n.getAttribute("ni").getIntValue()); 
				subscriber.attachToNet(netId);
				subscriber.setName(n.getChildText("n")); 
				subscriber.setPhoneNumber(n.getChildText("p")); 
				subscriber.setDate(n.getChildText("d"));
				subscriber.setAdress(n.getChildText("a"));
				subscriber.setEquipment(n.getChildText("e")); 
				
				sys.sc.putElement(subscriber);
				
				
			}
			form.progressBar.setValue(sys.getSize() * 100 / size);
			t.sleep(10);
			/*
			 * Считываем элементы "Повреждение"
			 */
			
			i = system.getChildren("dmg").iterator();
			
			while(i.hasNext()) { n = (Element)i.next();
				
				Damage damage = new Damage();
				
				damage.setId(n.getAttribute("i").getIntValue());
				damage.attachTo(n.getAttribute("oi").getIntValue());
				damage.setName(n.getChildText("n"));
				damage.setDescription(n.getChildText("d"));
				damage.setOpenDate(n.getChildText("o"));
				damage.setCloseDate(n.getChildText("c"));
				
				sys.dmc.putElement(damage);
				
			}
			form.progressBar.setValue(sys.getSize() * 100 / size);
			t.sleep(10);
			addLogMessage("Файл открыт: "+ xmlFile.getName() + "("+ xmlFile.length() + " байт)");
			addLogMessage("Количество элементов после открытия файла: " + sys.getSize()+"; "+ sys.nc.getSize()+"; dfc:"+sys.dfc.getSize()+"; cbc:"+sys.cbc.getSize()+"; dbc:"+sys.dbc.getSize()+"; mc:"+sys.mc.getSize()+"; duc:"+sys.duc.getSize()+"; buc:"+sys.buc.getSize()+"; tuc:"+sys.tuc.getSize()+"; fc:"+sys.fc.getSize()+"; bc:"+sys.bc.getSize()+"; cc:"+sys.cc.getSize()+"; pc:"+sys.pc.getSize()+"; phc:"+sys.phc.getSize()+"; sc:"+sys.sc.getSize()+"; dmc:"+sys.dmc.getSize());
			writeLog();
			
			form.label.setText("Файл \"" + xmlFile.getName() + "\" прочитан");
			//return true;
			
		  } catch (IOException io) {
			writeError("Ошибка чтения файла: " + io.toString()); 
			form.label.setText("Ошибка при чтении файла");
			//return false;
		  } 
			catch (JDOMException jdomex) {
			writeError("Ошибка парсинга XML файла:" + jdomex.toString());
			form.label.setText("Ошибка при чтении файла");
			//return false;
		  } catch (InterruptedException e) {
			  writeError("Ошибка потока чтения:" + e.toString());
			  form.label.setText("Ошибка при чтении файла");
			}
		stop();
		
	}
	public void start(){
		t.start();
	}
	public void stop(){
		t = null;
	}
	
}