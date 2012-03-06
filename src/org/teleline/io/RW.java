package org.teleline.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import org.teleline.model.*;

public class RW {
	private static String dTmpFolder = "tmp";
	private static String dLogsFolder = "logs";
	private static String dSavesFolder = "saves";
	private static String dPassportsFolder = "saves";
	
	private static String fSave = dSavesFolder + "/tlsave_";
	private static String fLog = dLogsFolder + "/teleline.log";
	private static String fNonSavedLog = dTmpFolder + "/teleline.$$$";
	private static String fErrorsLog = dLogsFolder +"/errors.log";
	private static String fRawPassport = dTmpFolder + "/rawpass.html";
	
	public IdGenerator ig;
	private NetCollection nc;
	private DFrameCollection dfc;
	private CabinetCollection cbc; 
	private DBoxCollection dbc;
	private ManholeCollection mc;
	private DuctCollection duc;
	private BuildingCollection buc;
	private TubeCollection tuc;
	private FrameCollection fc;
	private BoxCollection bc;
	private CableCollection cc;
	private PairCollection pc;
	private PathCollection phc;
	private SubscriberCollection sc;
	
	public RW(IdGenerator ig, NetCollection nc, DFrameCollection dfc, CabinetCollection cbc, DBoxCollection dbc, ManholeCollection mc, DuctCollection duc, BuildingCollection buc, TubeCollection tuc, FrameCollection fc, BoxCollection bc, CableCollection cc, PairCollection pc, PathCollection phc, SubscriberCollection sc ) {
		this.ig = ig;
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
	}
	
	private void writeLog () {
		
		try {
			PrintWriter pw = new PrintWriter(new FileOutputStream(fLog, true));
			File readFile = new File (fNonSavedLog);
			BufferedReader reader = new BufferedReader(new FileReader(readFile));
		
			String line;
			
			while ((line = reader.readLine()) != null) pw.println(line);
				
			pw.close();
			reader.close();
			readFile.delete();
		}
		catch (IOException e) {
			writeError(e.toString());
		}				
	}

	public Integer valueOf(String string) {
		
		try {
			return Integer.valueOf(string);
		} 
		catch (NumberFormatException io) {
			writeError("Ошибка преобразования строки в число: " + io.toString());
			return (Integer)0;
		}
	}
	public void checkFolders () {
		
		File file = new File(dTmpFolder);
		if (file.exists() && !file.isDirectory()) file.delete();
		file = new File(dTmpFolder); if (!file.exists()) file.mkdir();
		
		file = new File(dLogsFolder);
		if (file.exists() && !file.isDirectory()) file.delete();
		file = new File(dLogsFolder); if (!file.exists()) file.mkdir();
		
		file = new File(dSavesFolder);
		if (file.exists() && !file.isDirectory()) file.delete();
		file = new File(dSavesFolder); if (!file.exists()) file.mkdir();
		
		file = new File(dPassportsFolder);
		if (file.exists() && !file.isDirectory()) file.delete();
		file = new File(dPassportsFolder); if (!file.exists()) file.mkdir();
		
			
	}
	/**
	 * Сохраняет данные системы в файл
	 */
	public boolean save() {
		
		try {	 
			Element system = new Element("system");
			system.setAttribute(new Attribute ("idIndex", ig.getId().toString()));
			Document document = new Document(system);
			/**
			 * Записываем элементы "Сеть"
			 */	
			Iterator<AbstractElement> i = nc.elements().iterator(); 
			
			while (i.hasNext())  {
			
				StructuredElement n = (StructuredElement)i.next();

				Element netXML = new Element ("net");
				netXML.setAttribute(new Attribute ("i", n.getId().toString()));

				netXML.addContent(new Element ("name").addContent(n.getName()));
				
				system.addContent(netXML); 
			}
			/**
			 * Записываем элементы "Кросс"
			 */
			i = dfc.elements().iterator();
			
			while(i.hasNext()) {
				
				StructuredElement f = (StructuredElement)i.next();
				
				Element frameXML = new Element ("dframe");
				frameXML.setAttribute(new Attribute ("i", f.getId().toString()));
				frameXML.setAttribute(new Attribute ("ni", f.getNet().toString()));
				
				frameXML.addContent(new Element ("name").addContent(f.getName()));
				frameXML.addContent(new Element ("places").addContent(f.getPlacesCount().toString()));
				
				system.addContent(frameXML);
			}
			/**
			 * Записываем элементы "Шкаф"
			 */
			i = cbc.elements().iterator();
			
			while(i.hasNext()) {
				
				Cabinet f = (Cabinet)i.next();
				
				Element cabinetXML = new Element ("cab");
				cabinetXML.setAttribute(new Attribute ("i", f.getId().toString()));
				cabinetXML.setAttribute(new Attribute ("ni", f.getNet().toString()));
				
				cabinetXML.addContent(new Element ("class").addContent(f.getCabinetClass().toString()));
				cabinetXML.addContent(new Element ("number").addContent(f.getSNumber()));
				cabinetXML.addContent(new Element ("places").addContent(f.getPlacesCount().toString()));
				
				cabinetXML.addContent(new Element ("adress").addContent(f.getAdress()));
				cabinetXML.addContent(new Element ("place").addContent(f.getPlace()));
				cabinetXML.addContent(new Element ("material").addContent(f.getMaterial()));
				cabinetXML.addContent(new Element ("date").addContent(f.getDate()));
				cabinetXML.addContent(new Element ("setup").addContent(f.getSetup().toString()));
				cabinetXML.addContent(new Element ("area").addContent(f.getArea().toString()));
				
				system.addContent(cabinetXML);
			}			
			/**
			 * Записываем элементы "Коробка"
			 */
			i = dbc.elements().iterator();
			
			while(i.hasNext()) {
				
				Element dboxXML = new Element ("dbox");
				DBox f = (DBox)i.next();
				dboxXML.setAttribute(new Attribute ("i", f.getId().toString()));
				dboxXML.setAttribute(new Attribute ("ni", f.getNet().toString()));
			
				dboxXML.addContent(new Element ("capacity").addContent(f.getCapacity().toString()));
				dboxXML.addContent(new Element ("b").addContent(f.getBuilding().toString()));
				dboxXML.addContent(new Element ("p").addContent(f.getPlase()));
				
				system.addContent(dboxXML);
			}
			
			/**
			 * Записываем элементы "Колодец"
			 */
			i = mc.elements().iterator();
			
			while(i.hasNext()) {
				
				Manhole f = (Manhole)i.next();
				
				Element manholeXML = new Element ("manhole");
				manholeXML.setAttribute(new Attribute ("i", f.getId().toString()));
				manholeXML.setAttribute(new Attribute ("ni", f.getNet().toString()));
				
				manholeXML.addContent(new Element ("number").addContent(f.getSNumber()));
				manholeXML.addContent(new Element ("d").addContent(f.getDate()));
				manholeXML.addContent(new Element ("a").addContent(f.getAdress()));
				manholeXML.addContent(new Element ("s").addContent(f.getSize()));
				
				manholeXML.addContent(new Element ("c").addContent(f.getConstruction().toString()));
				manholeXML.addContent(new Element ("f").addContent(f.getForm().toString()));
				
				
				
				system.addContent(manholeXML);
			}
			
			/**
			 * Записываем элементы "Кабельная канализация"
			 */
			i = duc.elements().iterator();
			
			while(i.hasNext()) {
				
				Duct f = (Duct)i.next();
				
				Element ductXML = new Element ("duct");
				ductXML.setAttribute(new Attribute ("i", f.getId().toString()));
				ductXML.setAttribute(new Attribute ("ni", f.getNet().toString()));
				
				ductXML.addContent(new Element("from").addContent(f.getFrom().toString()));
				ductXML.addContent(new Element("to").addContent(f.getTo().toString()));
				
				ductXML.addContent(new Element("fromSide").addContent(f.getFromSide().toString()));
				ductXML.addContent(new Element("toSide").addContent(f.getToSide().toString()));
				
				ductXML.addContent(new Element("l").addContent(f.getLenght().toString()));
				ductXML.addContent(new Element("td").addContent(f.getTubeDiametr().toString()));
				ductXML.addContent(new Element("tm").addContent(f.getTubeMaterial()));
				ductXML.addContent(new Element("d").addContent(f.getDate()));
				ductXML.addContent(new Element("mm").addContent(f.getМanufacturingМethod()));

				system.addContent(ductXML);
			}
			/**
			 * Записываем элементы канал в канализации
			 */
			i = tuc.elements().iterator();
			while(i.hasNext()) {
				
				Tube f = (Tube)i.next();
				
				Element tubeXML = new Element ("tube");
				tubeXML.setAttribute(new Attribute ("i", f.getId().toString()));
				tubeXML.setAttribute(new Attribute ("oi", f.getDuct().toString()));
				
				tubeXML.addContent(new Element ("number").addContent(f.getNumber().toString()));
				
				if (f.cablesCount() > 0) {
					
					Element elementXML = new Element ("cb");
					Iterator<Integer> c = f.getCables().iterator();
					while (c.hasNext()) elementXML.addContent(new Element ("i").addContent(c.next().toString()));
					tubeXML.addContent(elementXML);
				}
				system.addContent(tubeXML);
			}
			/**
			 * Записываем элементы здание
			 */
			i = buc.elements().iterator();
			while(i.hasNext()) {
				
				Building f = (Building)i.next();
				
				Element buildingXML = new Element ("buil");
				buildingXML.setAttribute(new Attribute ("i", f.getId().toString()));
				buildingXML.setAttribute(new Attribute ("ni", f.getNet().toString()));
				
				buildingXML.addContent(new Element ("street").addContent(f.getStreet()));
				buildingXML.addContent(new Element ("number").addContent(f.getSNumber()));
				buildingXML.addContent(new Element ("name").addContent(f.getName()));
				
				system.addContent(buildingXML);
			}
			
			/**
			 * Записываем элементы "Громполоса"
			 */
			i = fc.elements().iterator();
			
			while(i.hasNext()) {
				
				Frame f = (Frame)i.next();
				
				Element frameXML = new Element ("frame");
				frameXML.setAttribute(new Attribute ("i", f.getId().toString()));
				frameXML.setAttribute(new Attribute ("ownerId", f.getOwnerId().toString()));
								
				frameXML.addContent(new Element ("number").addContent(f.getNumber().toString()));
				frameXML.addContent(new Element ("capacity").addContent(f.getCapacity().toString()));
				frameXML.addContent(new Element("placeNumber").addContent(f.getPlaceNumber().toString()));
			
				system.addContent(frameXML);
			}
			
			/**
			 * Записываем элементы "Бокс"
			 */
			i = bc.elements().iterator();
			
			while(i.hasNext()) {
				
				Box f = (Box)i.next();
				
				Element boxXML = new Element ("box");
				boxXML.setAttribute(new Attribute ("i", f.getId().toString()));
				boxXML.setAttribute(new Attribute ("ownerId", f.getOwnerId().toString()));
				
				boxXML.addContent(new Element ("number").addContent(f.getNumber().toString()));
				boxXML.addContent(new Element ("capacity").addContent(f.getCapacity().toString()));
				boxXML.addContent(new Element("type").addContent(f.getType().toString()));
				boxXML.addContent(new Element("placeNumber").addContent(f.getPlaceNumber().toString()));
				
				system.addContent(boxXML);
			}
			/**
			 * Записываем элементы "Кабель"
			 */
			i = cc.elements().iterator();
			
			while(i.hasNext()) {
				
				Cable f = (Cable)i.next();
				
				Element cableXML = new Element ("cable");
				cableXML.setAttribute(new Attribute ("i", f.getId().toString()));
				cableXML.setAttribute(new Attribute ("ni", f.getNet().toString()));
			
				cableXML.addContent(new Element ("capacity").addContent(f.getCapacity().toString()));
				cableXML.addContent(new Element ("usedCapacity").addContent(f.getUsedCapacity().toString()));
				cableXML.addContent(new Element("type").addContent(f.getType().toString()));
				cableXML.addContent(new Element("label").addContent(f.getLabel()));
				cableXML.addContent(new Element("number").addContent(f.getNumber().toString()));
				cableXML.addContent(new Element("from").addContent(f.getFrom().toString()));
				cableXML.addContent(new Element("to").addContent(f.getTo().toString()));
				cableXML.addContent(new Element("l").addContent(f.getLenght().toString()));
				cableXML.addContent(new Element("wd").addContent(f.getWireDiametr()));
				cableXML.addContent(new Element("y").addContent(f.getYear()));
				cableXML.addContent(new Element("s").addContent(f.getStatus().toString()));
				
				system.addContent(cableXML);
			}
			/**
			 * Записываем элементы "Пара"
			 */
			i = pc.elements().iterator();
				
			while(i.hasNext()) {
				
				Pair f = (Pair)i.next();
				
				Element pairXML = new Element ("p");
				pairXML.setAttribute(new Attribute ("i", f.getId().toString()));
				pairXML.setAttribute(new Attribute ("c", f.getCable().toString()));
				pairXML.setAttribute(new Attribute ("f", f.getElementFrom().toString()));
				pairXML.setAttribute(new Attribute ("t", f.getElementTo().toString()));
								
				pairXML.addContent(new Element ("n").addContent(f.getNumberInCable().toString()));
				pairXML.addContent(new Element ("fn").addContent(f.getFromNumber().toString()));
				pairXML.addContent(new Element ("tn").addContent(f.getToNumber().toString()));
				pairXML.addContent(new Element ("s").addContent(f.getStatus().toString()));
			//	pairXML.addContent(new Element("ty").addContent(f.getType().toString()));
								
				system.addContent(pairXML);
			}	
			/**
			 * Записываем элементы "Включение"
			 */
			i = phc.elements().iterator();
					
			while(i.hasNext()) {
				
				Path f = (Path)i.next();
				
				Element pathXML = new Element ("path");
				pathXML.setAttribute(new Attribute ("i", f.getId().toString()));
				
				pathXML.addContent(new Element ("s").addContent(f.getSubscriber().toString()));
				pathXML.addContent(new Element ("n").addContent(f.getName()));
				pathXML.addContent(new Element ("t").addContent(f.getTransit()));
				pathXML.addContent(new Element ("mp").addContent(f.getmPair().toString()));
			/*	
				if (f.ismPair()) {
					
					Element elementXML = new Element ("mp");
					for (int k = 0; k < f.getmPair().size(); k++)
						elementXML.addContent(new Element ("i").addContent(f.getmPair().get(k).toString()));
					
					pathXML.addContent(elementXML);
				}
			*/	
				pathXML.addContent(new Element ("drp").addContent(f.getdrPair().toString()));
			/*	if (f.isdrPair()) {
					
					Element elementXML = new Element ("drp");
					for (int k = 0; k < f.getdrPair().size(); k++)
						elementXML.addContent(new Element ("i").addContent(f.getdrPair().get(k).toString()));
					
					pathXML.addContent(elementXML);
				}
			*/	
				pathXML.addContent(new Element ("dbp").addContent(f.getdbPair().toString()));
		/*		if (f.isdbPair()) {
					
					Element elementXML = new Element ("dbp");
					for (int k = 0; k < f.getdbPair().size(); k++)
						elementXML.addContent(new Element ("i").addContent(f.getdbPair().get(k).toString()));
					
					pathXML.addContent(elementXML);
				}
		*/		
				if (f.isicPair()) {
					
					Element elementXML = new Element ("icp");
					for (int k = 0; k < f.geticPair().size(); k++)
						elementXML.addContent(new Element ("i").addContent(f.geticPair().get(k).toString()));
					
					pathXML.addContent(elementXML);
				}
				
				system.addContent(pathXML);
			}	
			/**
			 * Записываем элементы "Абонент"
			 */
				i = sc.elements().iterator();
				
				while(i.hasNext()) {
					
					Element subscriberXML = new Element ("sub");
					Subscriber f = (Subscriber)i.next();
					subscriberXML.setAttribute(new Attribute ("i", f.getId().toString()));
					subscriberXML.setAttribute(new Attribute ("ni", f.getNet().toString()));
					
					subscriberXML.addContent(new Element ("nm").addContent(f.getName()));
					subscriberXML.addContent(new Element ("ph").addContent(f.getPhoneNumber()));
					subscriberXML.addContent(new Element ("dt").addContent(f.getDate()));
					subscriberXML.addContent(new Element ("adr").addContent(f.getAdress()));
					subscriberXML.addContent(new Element ("eq").addContent(f.getEquipment()));
					
					system.addContent(subscriberXML);
				}
		
			XMLOutputter xmlOutput = new XMLOutputter();
	 		//xmlOutput.setFormat(Format.getPrettyFormat());
	 		
	 		xmlOutput.setFormat(Format.getCompactFormat());
	 		
			SimpleDateFormat DF = new SimpleDateFormat("yyyy-MM-dd_HHmmssS");
			String fileName = fSave + DF.format(Calendar.getInstance().getTime()) + ".xml";
			
			xmlOutput.output(document, new FileOutputStream(fileName));
			
			addLogMessage("Файл сохранен: "+ fileName);
			writeLog();
			//System.out.println("File Saved!");
			return true;
		  } 
		catch (IOException io) {
			//System.out.println(io.getMessage());
			writeError("Ошибка сохранения файла системы: " + io.toString());
			return false;
		  }
		
	}

	public boolean read(File xmlFile) {
			
		try {
			
			SAXBuilder builder = new SAXBuilder();
			//File xmlFile = new File(file);
			
			Document document = (Document) builder.build(xmlFile);
			Element system = document.getRootElement();
			ig.setIdIndex(system.getAttribute("idIndex").getIntValue());
			
			Element n = null;
			Iterator<?> i = null;
			
			/**
			 * Считываем элементы "Сеть"
			 */
			i = system.getChildren("net").iterator();
			
			while(i.hasNext()) { n = (Element)i.next();
				
				Net net = new Net();
				
				net.setId(n.getAttribute("i").getIntValue()); 
				net.setName(n.getChildText("name"));
				
				nc.putElement(net);
			}
			
			/**
			 * Считываем элементы "Кросс"
			 */
			i = system.getChildren("dframe").iterator();
			
			while(i.hasNext()) { n = (Element)i.next();
				
				DFramе dframe = new DFramе();
				
				dframe.setId(n.getAttribute("i").getIntValue()); 
				dframe.attachToNet(n.getAttribute("ni").getIntValue());
				dframe.setName(n.getChildText("name"));
				dframe.setPlacesCount(this.valueOf(n.getChildText("places")));
				
				dfc.putElement(dframe);	
			}
			
			/**
			 * Считываем элементы "Шкаф"
			 */
			i = system.getChildren("cab").iterator();
			
			while(i.hasNext()) { n = (Element)i.next();
				
				Cabinet cabinet = new Cabinet();
				
				cabinet.setId(n.getAttribute("i").getIntValue()); 
				cabinet.attachToNet(n.getAttribute("ni").getIntValue());
							
				cabinet.setSNumber(n.getChildText("number"));
				cabinet.setCabinetClass(this.valueOf(n.getChildText("class")));
				cabinet.setPlacesCount(this.valueOf(n.getChildText("places")));
				
				cabinet.setAdress(n.getChildText("adress"));
				cabinet.setPlace(n.getChildText("place"));
				cabinet.setMaterial(n.getChildText("material"));
				cabinet.setDate(n.getChildText("date"));
				cabinet.setSetup(this.valueOf(n.getChildText("setup")));
				cabinet.setArea(this.valueOf(n.getChildText("area")));
				
				cbc.putElement(cabinet);
			}
			
			/**
			 * Считываем элементы "Коробка"
			 */
			
			i = system.getChildren("dbox").iterator();
			
			while(i.hasNext()) { n = (Element)i.next();
				
				DBox dbox = new DBox(dfc,cbc,fc,bc,pc,cc,buc);
				
				dbox.setId(n.getAttribute("i").getIntValue()); 
				dbox.attachToNet(n.getAttribute("ni").getIntValue());
			
				dbox.setCapacity(this.valueOf(n.getChildText("capacity")));
				dbox.setBuilding(this.valueOf(n.getChildText("b")));
				dbox.setPlase(n.getChildText("p"));
				
				dbc.putElement(dbox);	
			}
			
			/**
			 * Считываем элементы "Колодец"
			 */
			
			i = system.getChildren("manhole").iterator();
			
			while(i.hasNext()) { n = (Element)i.next();
				
				Manhole manhole = new Manhole();
				
				manhole.setId(n.getAttribute("i").getIntValue()); 
				manhole.attachToNet(n.getAttribute("ni").getIntValue());
				manhole.setSNumber(n.getChildText("number"));
				
				manhole.setDate(n.getChildText("d"));
				manhole.setAdress(n.getChildText("a"));
				manhole.setSize(n.getChildText("s"));
				
				manhole.setConstruction(this.valueOf(n.getChildText("c")));
				manhole.setForm(this.valueOf(n.getChildText("f")));
				
				mc.putElement(manhole);
				
			}
			/**
			 * Считываем элементы "Кабельная канализация"
			 */
			i = system.getChildren("duct").iterator();
			
			while(i.hasNext()) { n = (Element) i.next();
			
				Duct duct = new Duct(dfc, cbc, mc, buc);
				duct.setId(n.getAttribute("i").getIntValue()); 
				duct.attachToNet(n.getAttribute("ni").getIntValue());
				
				duct.setFrom(this.valueOf(n.getChildText("from")));
				duct.setTo(this.valueOf(n.getChildText("to")));
				
				duct.setFromSide(this.valueOf(n.getChildText("fromSide")));
				duct.setToSide(this.valueOf(n.getChildText("toSide")));
				
				duct.setLenght(this.valueOf(n.getChildText("l")));
				duct.setTubeDiametr(this.valueOf(n.getChildText("td")));
				duct.setTubeMaterial(n.getChildText("tm"));
				duct.setDate(n.getChildText("d"));
				duct.setМanufacturingМethod(n.getChildText("mm"));
				
				duc.putElement(duct);
				
			}
			/**
			 * Считываем элементы "Канал в канализации"
			 */
			i = system.getChildren("tube").iterator();
			
			while(i.hasNext()) { n = (Element)i.next();
			
				Tube tube = new Tube();
				
				tube.setId(n.getAttribute("i").getIntValue()); 
				tube.setDuct(n.getAttribute("oi").getIntValue());
				
				tube.setNumber(this.valueOf(n.getChildText("number")));
				
				Iterator<?> k =  n.getChildren("cb").iterator();
				while(k.hasNext()) {
					
					Element mp = (Element)k.next();
					
					Iterator<?> m = mp.getChildren("i").iterator();
					
					while(m.hasNext()) {
						Element p = (Element)m.next();
						tube.addCable(this.valueOf(p.getValue()));
					}
				}
				tuc.putElement(tube);
			}
			/**
			 * Считываем элементы "Здание"
			 */
			i = system.getChildren("buil").iterator();
			
			while(i.hasNext()) { n = (Element)i.next();
				
				Building building = new Building();
				
				building.setId(n.getAttribute("i").getIntValue()); 
				building.attachToNet(n.getAttribute("ni").getIntValue());
				
				building.setSNumber(n.getChildText("number"));
				building.setName(n.getChildText("name"));
				building.setStreet(n.getChildText("street"));
								
				buc.putElement(building);
				
			}
			/**
			 * Считываем элементы "Громполоса"
			 */
			i = system.getChildren("frame").iterator();
			
			while(i.hasNext()) { n = (Element)i.next();
				
				Frame frame = new Frame();
				
				frame.setId(n.getAttribute("i").getIntValue()); 
				frame.attachTo(n.getAttribute("ownerId").getIntValue());
				
				frame.setNumber(this.valueOf(n.getChildText("number")));
				frame.setCapacity(this.valueOf(n.getChildText("capacity")));
				frame.setPlaceNumber(this.valueOf(n.getChildText("placeNumber")));
								
				fc.putElement(frame);
				
			}			
			/**
			 * Считываем элементы "Бокс"
			 */
			
			i = system.getChildren("box").iterator();
			
			while(i.hasNext()) { n = (Element)i.next();
				
				Box box = new Box();
				
				box.setId(n.getAttribute("i").getIntValue()); 
				box.attachTo(n.getAttribute("ownerId").getIntValue());
				
				box.setNumber(this.valueOf(n.getChildText("number")));
				box.setCapacity(this.valueOf(n.getChildText("capacity")));
			
				box.setType(this.valueOf(n.getChildText("type")));
				box.setPlaceNumber(this.valueOf(n.getChildText("placeNumber")));
								
				bc.putElement(box);
				
			}	
			/**
			 * Считываем элементы "Кабель"
			 */
			
			i = system.getChildren("cable").iterator();
			
			while(i.hasNext()) { n = (Element)i.next();
				
				Cable cable = new Cable(dfc,cbc,dbc);
				
				cable.setId(n.getAttribute("i").getIntValue()); 
				cable.attachToNet(n.getAttribute("ni").getIntValue());
								
				cable.setCapacity(this.valueOf(n.getChildText("capacity")));
				cable.setUsedCapacity(this.valueOf(n.getChildText("usedCapacity")));
				cable.setType(this.valueOf(n.getChildText("type")));
				cable.setLabel(n.getChildText("label"));
				cable.setNumber(this.valueOf(n.getChildText("number")));
				cable.setFrom(this.valueOf(n.getChildText("from")));
				cable.setTo(this.valueOf(n.getChildText("to")));
				cable.setLenght(this.valueOf(n.getChildText("l")));
				cable.setWireDiametr(n.getChildText("wd"));
				cable.setYear(n.getChildText("y"));
				cable.setStatus(this.valueOf(n.getChildText("s")));
				
				cc.putElement(cable);
			}
			/**
			 * Считываем элементы "Пара"
			 */
			
			i = system.getChildren("p").iterator();
			
			while(i.hasNext()) { n = (Element)i.next();
				
				Pair pair = new Pair(fc,bc,dbc,cc);
				
				pair.setId(n.getAttribute("i").getIntValue()); 
				pair.attachToCable(n.getAttribute("c").getIntValue()); 
				pair.attachToElementFrom(n.getAttribute("f").getIntValue());
				pair.attachToElementTo(n.getAttribute("t").getIntValue());
								
				pair.setNumberInCable(this.valueOf(n.getChildText("n")));
				pair.setFromNumber(this.valueOf(n.getChildText("fn")));
				pair.setToNumber(this.valueOf(n.getChildText("tn")));
				pair.setStatus(this.valueOf(n.getChildText("s")));
			//	pair.setType(this.valueOf(n.getChildText("ty")));			
				
				pc.putElement(pair);	
			}
			/**
			 * Считываем элементы "Включение"
			 */
			i = system.getChildren("path").iterator();
			
			while(i.hasNext()) { n = (Element)i.next();
				
				Path path = new Path(sc,pc);
				
				path.setId(n.getAttribute("i").getIntValue());
				
				path.setSubscriber(this.valueOf(n.getChildText("s")));
				path.setName(n.getChildText("n"));
				path.setTransit(n.getChildText("t"));
				path.addmPair(this.valueOf(n.getChildText("mp")));
				
				/*Iterator<?> k =  n.getChildren("mp").iterator();
				while(k.hasNext()) {
					
					Element mp = (Element)k.next();
					
					Iterator<?> m = mp.getChildren("i").iterator();
					
					while(m.hasNext()) {
						Element p = (Element)m.next();
						path.addmPair(this.valueOf(p.getValue()));
					}
				}*/
				path.adddrPair(this.valueOf(n.getChildText("drp")));
			/*	k = n.getChildren("drp").iterator();
				while(k.hasNext()) {
					
					Element drp = (Element)k.next();
					
					Iterator<?> m = drp.getChildren("i").iterator();
					
					while(m.hasNext()) {
						Element p = (Element)m.next();
						path.adddrPair(this.valueOf(p.getValue()));
					}
				}*/
				path.adddbPair(this.valueOf(n.getChildText("dbp")));
				
			/*	k = n.getChildren("dbp").iterator();
				while(k.hasNext()) {
					
					Element dbp = (Element)k.next();
					
					Iterator<?> m = dbp.getChildren("i").iterator();
					
					while(m.hasNext()) {
						Element p = (Element)m.next();
						path.adddbPair(this.valueOf(p.getValue()));
					}
				}
			*/	
				Iterator<?> k =  n.getChildren("icp").iterator();
				while(k.hasNext()) {
					
					Element icp = (Element)k.next();
					
					Iterator<?> m = icp.getChildren("i").iterator();
					
					while(m.hasNext()) {
						Element p = (Element)m.next();
						path.addicPair(this.valueOf(p.getValue()));
					}
				}
				
				phc.putElement(path);
				
			}
			/**
			 * Считываем элементы "Абонент"
			 */
			
			i = system.getChildren("sub").iterator();
			
			while(i.hasNext()) { n = (Element)i.next();
				
				Subscriber subscriber = new Subscriber();
				
				subscriber.setId(n.getAttribute("i").getIntValue()); 
				subscriber.attachToNet(n.getAttribute("ni").getIntValue()); 
				
				subscriber.setName(n.getChildText("nm")); 
				subscriber.setPhoneNumber(n.getChildText("ph")); 
				subscriber.setDate(n.getChildText("dt"));
				subscriber.setAdress(n.getChildText("adr"));
				subscriber.setEquipment(n.getChildText("eq")); 
				
		
				sc.putElement(subscriber);
				
			}
//			sc.setIdIndex(sc.getMaxId());
		
			//System.out.println("File Read!");
			addLogMessage("Файл открыт: "+ xmlFile.getName());
			writeLog();
			return true;
			
		  } catch (IOException io) {
			//System.out.println(io.getMessage());
			writeError("Ошибка чтения файла системы: " + io.toString()); 
			return false;
		  } 
			catch (JDOMException jdomex) {
			//System.out.println(jdomex.getMessage());
			writeError("Ошибка парсинга XML файла:" + jdomex.toString());
			return false;
		  }
		
	}

	public void writeError(String message) {
		
		SimpleDateFormat DF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		
		try {
			PrintWriter pw = new PrintWriter(new FileOutputStream(fErrorsLog, true));	
			pw.println(DF.format(Calendar.getInstance().getTime()) +" - " + message);
			pw.close();
		} 
		catch (IOException e) {
			//System.out.println(e.getMessage());
			writeError(e.toString());
		}
	}
	
	public void addLogMessage(String message) {
		
		SimpleDateFormat DF = new SimpleDateFormat("yyyy.MM.dd HH:mm");
		
		try {
			PrintWriter pw = new PrintWriter(new FileOutputStream(fNonSavedLog, true));	
			pw.println(DF.format(Calendar.getInstance().getTime()) +" - " + message);
			pw.close();
		} 
		catch (IOException e) {
			//System.out.println(e.getMessage());
			writeError(e.toString());
		}
	}
	
	public boolean isSaved() {
		
		 File f = new File(fNonSavedLog);
		 return !f.exists();
		 
	}
	
	public void deleteNotSavedLog (){
		
		try {
			File f = new File(fNonSavedLog);
			if (f.exists()) f.delete();
		}
		catch (SecurityException e) {
			writeError(e.toString());
		}
	}
	
	public void deleteFile (String file){
		
		try {
			File f = new File(file);
			if (f.exists()) f.delete();
			//System.out.println(f.toString());
		}
		catch (SecurityException e) {
			writeError(e.toString());
		}
	}
	/**
	 * Создает и сохраняет в файл пасспорт шкафа
	 * @return имя файла
	 */
	public String createCabinetPassport(Cabinet cab) {
		
		try {	 
			
			Element html = new Element("html");
			Document document = new Document(html);
			
			Element head = new Element("head");
			Element meta = new Element("meta").setAttribute("http-equiv","content-type").setAttribute("content", "text/html;charset=utf-8");
			head.addContent(meta);
			meta = new Element("meta").setAttribute("title","Паспорт распределительного шкафа №" + cab.getSNumber());
			head.addContent(meta);
			Element body = new Element("body");
			Element style = new Element("style").setAttribute("type","text/css")
					.addContent(".empty{color: #cccccc} table{border-collapse: collapse}  .frame-table{border-style:none; width:600px} .frame-tr, .frame-td, .big-table-tr, .big-table-td {border-style:none}  .boxes-table{margin:0 10px 10px 0} td, th{border: 1px solid black; background-color:#eeeeee; font-size:10pt; padding:2px} td{background-color:#ffffff} ");
			head.addContent(style);
			
			html.addContent(head); html.addContent(body);
			
			Element frameTable = new Element("table").setAttribute("class","frame-table").setAttribute("cellpadding", "0").setAttribute("cellspacing", "0"); 
			body.addContent(frameTable);
			Element frameTr = new Element("tr").setAttribute("class","frame-tr"); frameTable.addContent(frameTr);
			Element frameTd = new Element("td").setAttribute("class","frame-td"); frameTr.addContent(frameTd);
			
			frameTd.addContent(new Element("h2").addContent("Паспорт распределительного шкафа №" + cab.getSNumber()));
			
			frameTd.addContent(new Element("h4").addContent("Адрес: " + cab.getAdress()));
			frameTd.addContent(new Element("h4").addContent("Магистральные кабели и связь между шкафами"));
			
			
			Element table = new Element("table").setAttribute("cellpadding", "0").setAttribute("cellspacing", "0"); frameTd.addContent(table);
			Element tr1 = new Element("tr"); table.addContent(tr1);
			tr1.addContent(new Element("th").setAttribute("colspan","5").addContent("Входящие в шкаф"));
			tr1.addContent(new Element("th").setAttribute("colspan","2").addContent("Исходящие из шкафа"));
			
			Element tr2 = new Element("tr"); table.addContent(tr2);
			tr2.addContent(new Element("th").addContent("№ сотни магистрального кабеля по кроссу"));
			tr2.addContent(new Element("th").addContent("№ шкафа 1 класса / № распределительного бокса"));
			tr2.addContent(new Element("th").addContent("Длина до данного шкафа"));
			tr2.addContent(new Element("th").addContent("Дата включения / дата выключения"));
			tr2.addContent(new Element("th").addContent("№ инвент. справки"));
			tr2.addContent(new Element("th").addContent("№ распред. бокса / № шкафа 2 класа"));
			tr2.addContent(new Element("th").addContent("№ инвент. справки"));
			
			frameTd.addContent(new Element("h4").addContent("Предельная емкость шкафа: " + ((Integer)(cab.getPlacesCount()*100)).toString()+ " пар"));
			frameTd.addContent(new Element("h4").addContent("Уличный или в помещении: " + cab.getAreaText()));
			frameTd.addContent(new Element("h4").addContent("Конструкция, материал (шкафа, боксов): " + cab.getMaterial()));
			frameTd.addContent(new Element("h4").addContent("Способ установки (со шкафной коробкой или без коробки): " + cab.getSetupText()));;
			frameTd.addContent(new Element("h4").addContent("Место расположения: " + cab.getPlace()));
			frameTd.addContent(new Element("h4").addContent("Дата установки шкафа: " + cab.getDate()));
			frameTd.addContent(new Element("h4").addContent("ОСОБЫЕ ОТМЕТКИ: __________________________________________________________________"));
			frameTd.addContent(new Element("h4").addContent(" _________________________________________________________________________________"));
			
			frameTd.addContent(new Element("h4").addContent("Паспорт составил: инженер _________________________   \"____\"___________201___г. "));
			frameTd.addContent(new Element("h4").addContent("Ответственный за технический учет линейных сооружений: __________________________ "));
			frameTd.addContent(new Element("h4").addContent(" _________________________________________________________________________________"));
			frameTd.addContent(new Element("h4").addContent("\"____\"___________201___г."));
			/*
			 * Заполняем таблицу с кабелями
			 */
			Vector<AbstractElement> mcable = cc.sortByIdUp(cc.getMCable(cab));
			Vector<AbstractElement> icablein = cc.sortByIdUp(cc.getICableIn(cab));
			Vector<AbstractElement> icableout = cc.sortByIdUp(cc.getICableOut(cab));
			Vector<AbstractElement> dcableout = cc.sortByIdUp(cc.getDCableOut(cab));
			
			int max = mcable.size();
			if (max < icablein.size()) max = icablein.size();
			if (max < icableout.size()) max = icableout.size();
			
			for (int i = 0; i < max; i++) {
				Element tr = new Element("tr"); table.addContent(tr);
				
				if (i < mcable.size()) {tr.addContent(new Element("td").addContent(((Cable)mcable.get(i)).toString()));}
				else {tr.addContent(new Element("td").addContent(""));}
				
				if (i < icablein.size()) {tr.addContent(new Element("td").addContent(((Cable)icablein.get(i)).toString()));}
				else {tr.addContent(new Element("td").addContent(""));}
				tr.addContent(new Element("td").addContent(""));
				tr.addContent(new Element("td").addContent(""));
				tr.addContent(new Element("td").addContent(""));
				
				if (i < icableout.size()) {tr.addContent(new Element("td").addContent(((Cable)icableout.get(i)).toString()));}
				else {tr.addContent(new Element("td").addContent(""));}
				
				tr.addContent(new Element("td").addContent(""));
			}	
			/*
			 * --------------------------------------------------------------------
			 */
			/*
			 * Создаем больщую таблицу по боксам и заполняем ее таблицами боксов 
			 */
			Element bigtable = new Element("table").setAttribute("class","big-table").setAttribute("cellpadding", "0").setAttribute("cellspacing", "0"); frameTd.addContent(bigtable);
			
			int x = 0; Element newtr = new Element("tr").setAttribute("class","big-table-tr");
			
			for (int i = 0; i < cab.getPlacesCount(); i++) {
				
				Box box = (Box)bc.getInPlace(i, cab.getId());
				
				if (x > 2) x = 0;
				
				if (x == 0)  { newtr =  new Element("tr"); bigtable.addContent(newtr); }
					
				Element newtd = new Element("td").setAttribute("class","big-table-td");; newtr.addContent(newtd);
				if (box == null) {
					Element boxtable = new Element("table").setAttribute("class", "boxes-table").setAttribute("width", "120").setAttribute("cellpadding", "2").setAttribute("cellspacing", "1"); newtd.addContent(boxtable);
					Element tr = new Element("tr"); boxtable.addContent(tr);
					tr.addContent(new Element("th").setAttribute("class", "empty").setAttribute("colspan","2").addContent("пусто"));
					tr = new Element("tr"); boxtable.addContent(tr);
					tr.addContent(new Element("th").setAttribute("colspan","2").addContent(((Integer)i).toString()));
					
					for (int n = 0; n < 10; n++) {
						
						Element tr11 = new Element("tr"); boxtable.addContent(tr11);
						tr11.addContent(new Element("td").addContent(((Integer)n).toString()));
						tr11.addContent(new Element("td").setAttribute("class", "empty").addContent("пусто"));
					}
				}
				else {
				
					Element boxtable = new Element("table").setAttribute("class", "boxes-table").setAttribute("width", "120").setAttribute("cellpadding", "2").setAttribute("cellspacing", "1"); newtd.addContent(boxtable);
					Element tr = new Element("tr"); boxtable.addContent(tr);
					tr.addContent(new Element("th").setAttribute("colspan","2").addContent(box.toString()));
					tr = new Element("tr"); boxtable.addContent(tr);
					tr.addContent(new Element("th").setAttribute("colspan","2").addContent(((Integer)i).toString()));
				
					for (int n = 0; n < box.getCapacity() / 10; n++) {
					
						Element tr11 = new Element("tr"); boxtable.addContent(tr11);
						tr11.addContent(new Element("td").addContent(((Integer)n).toString()));
					
						HashSet<Cable> h = new HashSet<Cable>();
						for (int k = 0; k < 10; k++) {
							Pair p = pc.getInPlace(box, n * 10 + k);
						
							if (p != null) {
								Integer cableId = p.getCable();
								Cable c = (Cable)cc.getElement(cableId);
								if (!h.contains(c)) h.add(c);
							}
						}
						tr11.addContent(new Element("td").addContent(h.toString()));
					}
				}
				x++;
			}
			/*
			 * ---------------------------------------------------------------
			 */
			
			frameTd.addContent(new Element("h2").addContent("Паспорт распределительных подземных кабелей"));
			frameTd.addContent(new Element("h4").addContent("По шкафу: №" + cab.getSNumber().toString()));
			frameTd.addContent(new Element("br")); frameTd.addContent(new Element("br"));
			
			/*
			 * Создаем таблицу распределительных кабелей
			 */
			Element cablesTable = new Element("table").setAttribute("cellpadding", "0").setAttribute("cellspacing", "0"); frameTd.addContent(cablesTable);
			Element cablesTableTr1 = new Element("tr"); cablesTable.addContent(cablesTableTr1);
			cablesTableTr1.addContent(new Element("th").addContent("Марка кабеля"));
			cablesTableTr1.addContent(new Element("th").addContent("Емкость кабеля"));
			cablesTableTr1.addContent(new Element("th").addContent("Диаметр жил"));
			cablesTableTr1.addContent(new Element("th").addContent("Длина кабеля"));
			cablesTableTr1.addContent(new Element("th").addContent("Поврежденные пары"));
			
			for (int i = 0; i < dcableout.size(); i++) {
				
				Cable cable = (Cable)dcableout.get(i);
				Element tr = new Element("tr"); cablesTable.addContent(tr);
				tr.addContent(new Element("td").addContent(cable.getLabel()));
				tr.addContent(new Element("td").addContent(cable.getCapacity().toString()));
				tr.addContent(new Element("td").addContent(cable.getWireDiametr().toString()));
				tr.addContent(new Element("td").addContent(cable.getLenght().toString()));
				
				Iterator<AbstractElement> pairIterator = pc.sortByIdUp(pc.getInCable(cable, 2)).iterator();
				String s = "";
				while (pairIterator.hasNext()) {
					s = s  + ((Pair)pairIterator.next()).getNumberInCable() + ",";
				}
				tr.addContent(new Element("td").addContent(s));	
			}
			
			/*
			 * ---------------------------------------------------------------
			 */
			frameTd.addContent(new Element("br")); frameTd.addContent(new Element("br"));
			
			frameTd.addContent(new Element("h4").addContent("Паспорт составил: инженер _________________________   \"____\"___________201___г. "));
			frameTd.addContent(new Element("h4").addContent("Ответственный за технический учет линейных сооружений: __________________________ "));
			frameTd.addContent(new Element("h4").addContent(" _________________________________________________________________________________"));
			frameTd.addContent(new Element("h4").addContent("\"____\"___________201___г."));
			

			XMLOutputter xmlOutput = new XMLOutputter();
			xmlOutput.setFormat(Format.getCompactFormat().setEncoding("UTF-8").setOmitDeclaration(true).setIndent("  "));
	 		
	 		FileOutputStream f = new FileOutputStream(fRawPassport); 
	 		xmlOutput.output(document, f);
	 		f.close();
						
			return fRawPassport;
		  } 
		
		catch (IOException io) {
			writeError(io.toString());
			return null;
		  }
				
	}
	/**
	 * Создает и сохраняет в файл пасспорт Кросса
	 * @return имя файла
	 */
	public String createDFramePassport(DFramе dframe) {
		try {	 
			
			Element html = new Element("html");
			Document document = new Document(html);
			
			Element head = new Element("head");
			Element meta = new Element("meta").setAttribute("http-equiv","content-type").setAttribute("content", "text/html;charset=utf-8");
			head.addContent(meta);
			meta = new Element("meta").setAttribute("title","Паспорт кросса " + dframe.getName());
			head.addContent(meta);
			Element body = new Element("body");
			Element style = new Element("style").setAttribute("type","text/css")
					.addContent(".empty{color: #cccccc} table{border-collapse: collapse}  .frame-table{border-style:none; width:1200px} .frame-tr, .frame-td, .big-table-tr, .big-table-td {border-style:none}  .boxes-table{margin:0 10px 10px 0} td, th{border: 1px solid black; background-color:#eeeeee; font-size:10pt; padding:1px} td{background-color:#ffffff} ");
			head.addContent(style);
			
			html.addContent(head); html.addContent(body);
			
			Element frameTable = new Element("table").setAttribute("class","frame-table").setAttribute("cellpadding", "0").setAttribute("cellspacing", "0"); body.addContent(frameTable);
			Element frameTr = new Element("tr").setAttribute("class","frame-tr"); frameTable.addContent(frameTr);
			Element frameTd = new Element("td").setAttribute("class","frame-td"); frameTr.addContent(frameTd);
			
			Element element = new Element("h2"); element.addContent("Паспорт кросса " + dframe.getName());
			frameTd.addContent(element);

			Element bigtable = new Element("table").setAttribute("class","big-table").setAttribute("cellpadding", "0").setAttribute("cellspacing", "0"); frameTd.addContent(bigtable);
			
			int x = 0; Element newtr = new Element("tr").setAttribute("class","big-table-tr");
			
			for (int i = 0; i < dframe.getPlacesCount(); i++) {
				
				Frame frame = (Frame)fc.getInPlace(i, dframe.getId());
				
				if (x > 6) x = 0;
				
				if (x == 0)  { newtr =  new Element("tr"); bigtable.addContent(newtr); }
					
				Element newtd = new Element("td").setAttribute("class","big-table-td"); newtr.addContent(newtd);
				if (frame == null) {
					Element boxtable = new Element("table").setAttribute("class", "boxes-table").setAttribute("width", "120").setAttribute("cellpadding", "2").setAttribute("cellspacing", "1"); newtd.addContent(boxtable);
					Element tr = new Element("tr"); boxtable.addContent(tr);
					tr.addContent(new Element("th").setAttribute("class", "empty").addContent("пусто"));
					tr.addContent(new Element("th").setAttribute("class", "empty").addContent("пусто"));
					tr.addContent(new Element("th").setAttribute("class", "empty").addContent("пусто"));
					
					for (int n = 0; n < 15; n++) {
						
						Element tr11 = new Element("tr"); boxtable.addContent(tr11);
						tr11.addContent(new Element("td").setAttribute("class", "empty").addContent("пусто"));
						tr11.addContent(new Element("td").setAttribute("class", "empty").addContent(((Integer)(n*10)).toString()+"-"+((Integer)(n*10+9)).toString()));
						tr11.addContent(new Element("td").setAttribute("class", "empty").addContent("пусто"));
					}
				}
				else {
				
					Element boxtable = new Element("table").setAttribute("class", "boxes-table").setAttribute("width", "120").setAttribute("cellpadding", "2").setAttribute("cellspacing", "1"); newtd.addContent(boxtable);
					Element tr = new Element("tr"); boxtable.addContent(tr);
					tr.addContent(new Element("th").addContent("Шк"));
					tr.addContent(new Element("th").addContent(frame.toString()));
					tr.addContent(new Element("th").addContent("М"));
					
				
					for (int n = 0; n < 15; n++) {
					
						Element tr11 = new Element("tr"); boxtable.addContent(tr11);
						
						
						HashSet<Cable> h = new HashSet<Cable>();
						HashSet<StructuredElement> ch = new HashSet<StructuredElement>();
						
						for (int k = 0; k < 10; k++) {
							Pair p = pc.getInPlace(frame, n * 10 + k);
						
							if (p != null) {
								Integer cableId = p.getCable();
								Cable c = (Cable)cc.getElement(cableId);
								StructuredElement se;
								if (c.getType().equals(0)) {
									 se = (StructuredElement) cbc.getElement(c.getTo());
									 if (!ch.contains(se)) ch.add(se);
								}
								if (c.getType().equals(3)) {
									 se = (StructuredElement) dbc.getElement(c.getTo());
									 if (!ch.contains(se)) ch.add(se);
								}
								
								if (!h.contains(c)) h.add(c);
							}
						}
						Element structuredElementsTd = new Element("td"); tr11.addContent(structuredElementsTd);
						Iterator<StructuredElement> StructuredElementIterator = ch.iterator();
						while (StructuredElementIterator.hasNext()) {
							structuredElementsTd.addContent(StructuredElementIterator.next().toString()).addContent(new Element("br"));
						}
						
						tr11.addContent(new Element("td").addContent(((Integer)(n*10)).toString()+"-"+((Integer)(n*10+9)).toString()));
						
						Element cablesTd = new Element("td"); tr11.addContent(cablesTd);
						Iterator<Cable> cableIterator = h.iterator();
						while (cableIterator.hasNext()) {
							cablesTd.addContent(cableIterator.next().toShortString()).addContent(new Element("br"));
						}
					}
				}
				x++;
			}

			XMLOutputter xmlOutput = new XMLOutputter();
			xmlOutput.setFormat(Format.getCompactFormat().setEncoding("UTF-8").setOmitDeclaration(true).setIndent("  "));
	 		
	 		FileOutputStream f = new FileOutputStream(fRawPassport); 
	 		xmlOutput.output(document, f);
	 		f.close();
						
			return fRawPassport;
		  } 
		
		catch (IOException io) {
			//System.out.println(io.getMessage());
			writeError(io.toString());
			return null;
		  }
		
				
	}
	/**
	 * Создает и сохраняет в файл карточку Абонента
	 * @return имя файла
	 */
	public String createSubscriberPassport(Subscriber sub) {
		
		try {	 
			
			Element html = new Element("html");
			Document document = new Document(html);
			
			Element head = new Element("head");
			Element meta = new Element("meta").setAttribute("http-equiv","content-type").setAttribute("content", "text/html;charset=utf-8");
			head.addContent(meta);
			meta = new Element("meta").setAttribute("title","Карточка абонента " + sub.toString());
			head.addContent(meta);
			Element body = new Element("body");
			Element style = new Element("style").setAttribute("type","text/css")
					.addContent(".empty{color: #cccccc} table{ width:100%; border-collapse: collapse; margin:0 0 20px 0}  .frame-table{border-style:none; width:600px} .frame-tr, .frame-td, .big-table-tr, .big-table-td {border-style:none}  .boxes-table{margin:0 10px 10px 0} td, th{border: 1px solid black; background-color:#eeeeee; font-size:10pt; padding:5px} td{background-color:#ffffff} ");
			head.addContent(style);
			
			html.addContent(head); html.addContent(body);
			
			Element frameTable = new Element("table").setAttribute("class","frame-table").setAttribute("cellpadding", "0").setAttribute("cellspacing", "0"); body.addContent(frameTable);
			Element frameTr = new Element("tr").setAttribute("class","frame-tr"); frameTable.addContent(frameTr);
			Element frameTd = new Element("td").setAttribute("class","frame-td"); frameTr.addContent(frameTd);
			
			Element table = new Element("table").setAttribute("cellpadding", "0").setAttribute("cellspacing", "0"); frameTd.addContent(table);
			Element tr1 = new Element("tr"); table.addContent(tr1);
			tr1.addContent(new Element("th").setAttribute("colspan","2").addContent("Номер: " + sub.getPhoneNumber()));
			tr1.addContent(new Element("th").setAttribute("colspan","6").addContent("Абонент: " + sub.getName()).addContent(new Element("br")).addContent("Адрес установки: " + sub.getAdress()));
			tr1.addContent(new Element("th").addContent("Дата установки").addContent(new Element("br")).addContent(sub.getDate()));
			
			Element tr2 = new Element("tr"); table.addContent(tr2);
			tr2.addContent(new Element("td").addContent("Громполоса"));
			tr2.addContent(new Element("td").addContent("Пара").addContent(new Element("br")).addContent("громполосы"));
			tr2.addContent(new Element("td").addContent("Шкаф"));
			tr2.addContent(new Element("td").addContent("№").addContent(new Element("br")).addContent("МБ/РБ"));
			tr2.addContent(new Element("td").addContent("Магистраль").addContent(new Element("br")).addContent("Распределение"));
			tr2.addContent(new Element("td").addContent("№ РБ"));
			tr2.addContent(new Element("td").addContent("Распред."));
			tr2.addContent(new Element("td").addContent("Рамка").addContent(new Element("br")).addContent("перехода"));
			tr2.addContent(new Element("td").addContent("Примечание"));
			
			Element newTr = new Element("tr");;
			//System.out.println(sub.geticPair().size());
			
			Iterator<Path> pt = phc.getSubscriberPaths(sub).iterator();
			while (pt.hasNext()) {
				
				Path path = pt.next();
				newTr = new Element("tr");
			
				if (path.isdrPair()) {
				
					
						Pair p = (Pair)pc.getElement(path.getdrPair());
						Frame f = (Frame)fc.getElement(p.getElementFrom());
				
						newTr.addContent(new Element("td").addContent(f.toString()));
						newTr.addContent(new Element("td").addContent(p.getFromNumber().toString()));
						newTr.addContent(new Element("td").addContent(" "));
						newTr.addContent(new Element("td").addContent(" "));
						newTr.addContent(new Element("td").addContent(" "));
					
	
				}
			if (path.ismPair()) {	
				
					Pair p = (Pair)pc.getElement(path.getmPair());
					Frame f = (Frame)fc.getElement(p.getElementFrom());
					Box b = (Box)bc.getElement(p.getElementTo());
					Cabinet cb = (Cabinet)cbc.getElement(b.getOwnerId());
					Cable c = (Cable)cc.getElement(p.getCable());
						
					newTr.addContent(new Element("td").addContent(f.toString()));
					newTr.addContent(new Element("td").addContent(p.getFromNumber().toString()));
					newTr.addContent(new Element("td").addContent(cb.toString()));
					newTr.addContent(new Element("td").addContent(b.toString()));
					newTr.addContent(new Element("td").addContent(c.toString()+"-"+p.getNumberInCable().toString()));
					
			}
			else {
				newTr.addContent(new Element("td").addContent(" "));
				newTr.addContent(new Element("td").addContent(" "));
				newTr.addContent(new Element("td").addContent(" "));
				newTr.addContent(new Element("td").addContent(" "));
				newTr.addContent(new Element("td").addContent(" "));
			}
			
		
			for (int i = 0; i < path.geticPair().size(); i++) {
				Pair p = (Pair)pc.getElement(path.geticPair().get(0));
				Cable c = (Cable)cc.getElement(p.getCable());
				
				Box bto = (Box)bc.getElement(p.getElementTo());
				Box bfrom = (Box)bc.getElement(p.getElementFrom());
				
				//Cabinet cbfrom = (Cabinet)cbc.getElement(bfrom.getOwnerId());
				Cabinet cbto = (Cabinet)cbc.getElement(bto.getOwnerId());
				
				newTr.addContent(new Element("td").addContent(bfrom.toString()));
				newTr.addContent(new Element("td").addContent(c.toString()+"-"+p.getNumberInCable().toString()));
				newTr.addContent(new Element("td").addContent(" "));
				newTr.addContent(new Element("td").addContent(" "));
				table.addContent(newTr);
				
				newTr = new Element("tr");
				newTr.addContent(new Element("td").addContent(" "));
				newTr.addContent(new Element("td").addContent(" "));
				
				newTr.addContent(new Element("td").addContent(cbto.toString()));
				newTr.addContent(new Element("td").addContent(bto.toString()));
				newTr.addContent(new Element("td").addContent(c.toString()+"-"+p.getNumberInCable().toString()));
			}
			
			if (!path.isicPair() || !path.isdbPair()) {
				
				newTr.addContent(new Element("td").addContent(" "));
				newTr.addContent(new Element("td").addContent(" "));
				newTr.addContent(new Element("td").addContent(" "));
				newTr.addContent(new Element("td").addContent(" "));
				table.addContent(newTr);
			}
			
			if (path.isicPair() && path.isdbPair()) {
				
					Pair p = (Pair)pc.getElement(path.getdbPair());
					Box b = (Box)bc.getElement(p.getElementFrom());
						
					Cable c = (Cable)cc.getElement(p.getCable());
					newTr.addContent(new Element("td").addContent(b.toString()));
					newTr.addContent(new Element("td").addContent(c.toString()+"-"+p.getNumberInCable().toString()));
						
					newTr.addContent(new Element("td").addContent(" "));
					newTr.addContent(new Element("td").addContent(" "));
					table.addContent(newTr);
				
			}
			
			if (!path.isicPair() && path.isdbPair()) {
				
				
					Pair p = (Pair)pc.getElement(path.getdbPair());
					Box b = (Box)bc.getElement(p.getElementFrom());
					Cabinet cb = (Cabinet)cbc.getElement(b.getOwnerId());	
					Cable c = (Cable)cc.getElement(p.getCable());
				
					newTr = new Element("tr");
					newTr.addContent(new Element("td").addContent(" "));
					newTr.addContent(new Element("td").addContent(" "));
					newTr.addContent(new Element("td").addContent(cb.toString()));
					newTr.addContent(new Element("td").addContent(" "));
					newTr.addContent(new Element("td").addContent(" "));
					newTr.addContent(new Element("td").addContent(b.toString()));
					newTr.addContent(new Element("td").addContent(c.toString()+"-"+p.getNumberInCable().toString()));
					newTr.addContent(new Element("td").addContent(" "));
					newTr.addContent(new Element("td").addContent(" "));
					table.addContent(newTr);
				
			}
		
			}
			table = new Element("table").setAttribute("cellpadding", "0").setAttribute("cellspacing", "0"); frameTd.addContent(table);
			tr1 = new Element("tr"); table.addContent(tr1);
			tr1.addContent(new Element("th").setAttribute("rowspan","2").addContent("Тип оконечного").addContent(new Element("br")).addContent("орудования"));
			tr1.addContent(new Element("td").addContent("Основное ОУ"));
			tr1.addContent(new Element("td").addContent("Параллельное ОУ"));
			tr2 = new Element("tr"); table.addContent(tr2);
			tr2.addContent(new Element("td").addContent(sub.getEquipment()));
			tr2.addContent(new Element("td").addContent(new Element("br")).addContent(new Element("br")).addContent(new Element("br")));
			
			table = new Element("table").setAttribute("cellpadding", "0").setAttribute("cellspacing", "0"); frameTd.addContent(table);
			tr1 = new Element("tr"); table.addContent(tr1);
			tr1.addContent(new Element("th").addContent("ДВО"));
			tr1.addContent(new Element("td").addContent("Дата включения"));
			tr1.addContent(new Element("td").addContent("Дата выключения"));
			tr1.addContent(new Element("td").addContent("-"));
			tr1.addContent(new Element("td").addContent("-"));
			
			for (int i = 0; i < 3; i++) {
				tr2 = new Element("tr"); table.addContent(tr2);
				tr2.addContent(new Element("td").addContent(" "));
				tr2.addContent(new Element("td").addContent(" "));
				tr2.addContent(new Element("td").addContent(" "));
				tr2.addContent(new Element("td").addContent("-"));
				tr2.addContent(new Element("td").addContent("-"));
			}
			
			table = new Element("table").setAttribute("cellpadding", "0").setAttribute("cellspacing", "0"); frameTd.addContent(table);
			tr1 = new Element("tr"); table.addContent(tr1);
			tr1.addContent(new Element("th").addContent("Основные услуги"));
			tr1.addContent(new Element("td").addContent("Дата включения"));
			tr1.addContent(new Element("td").addContent("Дата выключения"));
			tr1.addContent(new Element("td").addContent("Причина"));
			tr1.addContent(new Element("td").addContent("-"));
			
			for (int i = 0; i < 3; i++) {
				tr2 = new Element("tr"); table.addContent(tr2);
				tr2.addContent(new Element("td").addContent(" "));
				tr2.addContent(new Element("td").addContent(" "));
				tr2.addContent(new Element("td").addContent(" "));
				tr2.addContent(new Element("td").addContent(" "));
				tr2.addContent(new Element("td").addContent("-"));
			}
					
			XMLOutputter xmlOutput = new XMLOutputter();
			xmlOutput.setFormat(Format.getCompactFormat().setEncoding("UTF-8").setOmitDeclaration(true).setIndent("  "));
	 		
	 		FileOutputStream f = new FileOutputStream(fRawPassport); 
	 		xmlOutput.output(document, f);
	 		f.close();
						
			return fRawPassport;
		  } 
		
		catch (IOException io) {
			writeError(io.toString());
			return null;
		  }
	}
	
	/**
	 * Создает и сохраняет в файл паспорт магистрального кабеля
	 * @return имя файла
	 */
	public String createМCablePassport(Cable cable) {
		
		Cabinet cab = (Cabinet)cbc.getElement(cable.getTo());
		try {	 
			
			Element html = new Element("html");
			Document document = new Document(html);
			
			Element head = new Element("head");
			Element meta = new Element("meta").setAttribute("http-equiv","content-type").setAttribute("content", "text/html;charset=utf-8");
			head.addContent(meta);
			meta = new Element("meta").setAttribute("title","Пасспорт кабеля " + cable.toString());
			head.addContent(meta);
			Element body = new Element("body");
			Element style = new Element("style").setAttribute("type","text/css")
					.addContent(".empty{color: #cccccc} table{ width:100%; border-collapse: collapse; margin:0 0 20px 0}  .frame-table{border-style:none; width:600px} .frame-tr, .frame-td, .big-table-tr, .big-table-td {border-style:none}  .boxes-table{margin:0 10px 10px 0} td, th{border: 1px solid black; background-color:#eeeeee; font-size:10pt; padding:5px} td{background-color:#ffffff} ");
			head.addContent(style);
			
			html.addContent(head); html.addContent(body);
			
			Element frameTable = new Element("table").setAttribute("class","frame-table").setAttribute("cellpadding", "0").setAttribute("cellspacing", "0"); body.addContent(frameTable);
			Element frameTr = new Element("tr").setAttribute("class","frame-tr"); frameTable.addContent(frameTr);
			Element frameTd = new Element("td").setAttribute("class","frame-td"); frameTr.addContent(frameTd);
			
			frameTd.addContent(new Element("h2").addContent("ПАСПОРТ"));
			frameTd.addContent(new Element("h2").addContent("магистрального кабеля № " + cable.getNumber()));
			frameTd.addContent(new Element("br")); frameTd.addContent(new Element("br"));
			/*
			 * Создаем таблицу паспорта кросса 
			 */
			Element cablesTable = new Element("table").setAttribute("cellpadding", "0").setAttribute("cellspacing", "0"); frameTd.addContent(cablesTable);
			Element cablesTableTr1 = new Element("tr"); cablesTable.addContent(cablesTableTr1);
			cablesTableTr1.addContent(new Element("th").setAttribute("rowspan","2").addContent("Марка кабеля"));
			cablesTableTr1.addContent(new Element("th").setAttribute("rowspan","2").addContent("Емкость кабеля, пар"));
			cablesTableTr1.addContent(new Element("th").setAttribute("rowspan","2").addContent("Диаметр жил, мм"));
			cablesTableTr1.addContent(new Element("th").setAttribute("colspan","2").addContent("Кабель"));
			cablesTableTr1.addContent(new Element("th").setAttribute("colspan","2").addContent("Пролет кабеля между колодцами"));
			cablesTableTr1.addContent(new Element("th").setAttribute("rowspan","2").addContent("Длина кабеля, м"));
			cablesTableTr1.addContent(new Element("th").setAttribute("rowspan","2").addContent("Номер шкафа"));
			cablesTableTr1.addContent(new Element("th").setAttribute("rowspan","2").addContent("Поврежденные пары"));
			
			Element cablesTableTr2 = new Element("tr"); cablesTable.addContent(cablesTableTr2);
			cablesTableTr2.addContent(new Element("th").addContent("Состояние"));
			cablesTableTr2.addContent(new Element("th").addContent("Год протяжки"));
			cablesTableTr2.addContent(new Element("th").addContent("от №"));
			cablesTableTr2.addContent(new Element("th").addContent("до №"));
				
			Element cablesTableTr3 = new Element("tr"); cablesTable.addContent(cablesTableTr3);
			cablesTableTr3.addContent(new Element("td").addContent(cable.getLabel()));
			cablesTableTr3.addContent(new Element("td").addContent(cable.getCapacity().toString()));
			cablesTableTr3.addContent(new Element("td").addContent(cable.getWireDiametr().toString()));
			cablesTableTr3.addContent(new Element("td").addContent(cable.getStatus().toString()));
			cablesTableTr3.addContent(new Element("td").addContent(cable.getYear()));
			cablesTableTr3.addContent(new Element("td").addContent("-"));
			cablesTableTr3.addContent(new Element("td").addContent("-"));
			cablesTableTr3.addContent(new Element("td").addContent(cable.getLenght().toString()));
			cablesTableTr3.addContent(new Element("td").addContent(cab.toString()));
			
			
			//генерируем ячейку с поврежденными парами
			Iterator<AbstractElement> pairIterator = pc.sortByIdUp(pc.getInCable(cable, 2)).iterator();
			String s = "";
			while (pairIterator.hasNext()) {
				s = s  + ((Pair)pairIterator.next()).getNumberInCable() + ",";
			}
			cablesTableTr3.addContent(new Element("td").addContent(s));
			
			//генерируем строки с учасками канализации
			Iterator<?> i = tuc.sortByIdUp(tuc.getTubesByCable(cable)).iterator();
			while (i.hasNext()){
				
				Duct d = (Duct)duc.getElement(((Tube)i.next()).getDuct());
				Manhole m1 = (Manhole)mc.getElement(d.getFrom());
				Manhole m2 = (Manhole)mc.getElement(d.getTo());
				
				if (m1 != null && m2 != null) {
					Element tr = new Element("tr"); cablesTable.addContent(tr);
					tr.addContent(new Element("td"));
					tr.addContent(new Element("td"));
					tr.addContent(new Element("td"));
					tr.addContent(new Element("td"));
					tr.addContent(new Element("td"));
					tr.addContent(new Element("td").addContent(m1.getSNumber()));
					tr.addContent(new Element("td").addContent(m2.getSNumber()));
					tr.addContent(new Element("td"));
					tr.addContent(new Element("td"));
					tr.addContent(new Element("td"));
					
				}
				
			}
			
			/*
			 * ---------------------------------------------------------------
			 */
			frameTd.addContent(new Element("br")); frameTd.addContent(new Element("br"));
			

			frameTd.addContent(new Element("h4").addContent("Паспорт составил: инженер _________________________   \"____\"___________201___г. "));
			frameTd.addContent(new Element("h4").addContent("Ответственный за технический учет линейных сооружений: __________________________ "));
			frameTd.addContent(new Element("h4").addContent(" _________________________________________________________________________________"));
			frameTd.addContent(new Element("h4").addContent("\"____\"___________201___г."));
			
			frameTd.addContent(new Element("br")); frameTd.addContent(new Element("br"));
			
			frameTd.addContent(new Element("h2").addContent("ЛИСТ НАГРУЗКИ МАГИСТРАЛЬНОГО КАБЕЛЯ"));
			frameTd.addContent(new Element("h4").addContent("Шкаф № " + cab.toString()));
			frameTd.addContent(new Element("h4").addContent("Кабель № " + cable.getNumber().toString()));
			
			frameTd.addContent(new Element("br")); frameTd.addContent(new Element("br"));
			/*
			 * Создаем таблицу нагрузки магистрального кабеля
			 */
			Element loadTable = new Element("table").setAttribute("cellpadding", "0").setAttribute("cellspacing", "0"); frameTd.addContent(loadTable);
			Element loadTableTr1 = new Element("tr"); loadTable.addContent(loadTableTr1);
			loadTableTr1.addContent(new Element("th").addContent("Абонент"));
			loadTableTr1.addContent(new Element("th").addContent("Магистраль"));
			loadTableTr1.addContent(new Element("th").addContent("Распределение"));
			
			for (Integer n = 0; n < cable.getCapacity(); n++) {
				
				Element tr = new Element("tr"); loadTable.addContent(tr);
				Element td1 = new Element("td"); tr.addContent(td1);
				tr.addContent(new Element("td").addContent(n.toString()));
				Element td3 = new Element("td"); tr.addContent(td3);
				
				Pair p = pc.getInPlace(cable, n);
				if (p != null) {
					
					Iterator<Path> pi = phc.getPairsPath(p).iterator();
					
					while (pi.hasNext()) {
						Path path = pi.next();
						td1.addContent(((Subscriber)sc.getElement(path.getSubscriber())).toString() +", " + path.getName()).addContent(new Element("br"));
												
						if (path.isicPair()) {
							Iterator <Integer> m = path.geticPair().iterator();
							
							while(m.hasNext()) {
								Pair icPair = (Pair)pc.getElement(m.next());
								Cable dcable = (Cable)cc.getElement(icPair.getCable());
								if (dcable.getFrom().equals(cab.getId()) || dcable.getTo().equals(cab.getId()))
									td3.addContent(icPair.toString()).addContent(new Element("br"));
								
							}
						}
						
						if (path.isdbPair()) {
							Pair dbPair = (Pair)pc.getElement(path.getdbPair());
							Cable dcable = (Cable)cc.getElement(dbPair.getCable());
							if (dcable.getFrom().equals(cab.getId()))
								td3.addContent(dbPair.toString()).addContent(new Element("br"));
						}
						
					}
				
				}
				
			}
			/*
			 * ---------------------------------------------------------------
			 */
			frameTd.addContent(new Element("br")); frameTd.addContent(new Element("br"));
			
			frameTd.addContent(new Element("h4").addContent("Cоставил: инженер _________________________   \"____\"___________201___г. "));
			frameTd.addContent(new Element("h4").addContent("Ответственный за технический учет линейных сооружений: __________________________ "));
			frameTd.addContent(new Element("h4").addContent(" _________________________________________________________________________________"));
			frameTd.addContent(new Element("h4").addContent("\"____\"___________201___г."));
			XMLOutputter xmlOutput = new XMLOutputter();
			xmlOutput.setFormat(Format.getCompactFormat().setEncoding("UTF-8").setOmitDeclaration(true).setIndent("  "));
	 		
	 		FileOutputStream f = new FileOutputStream(fRawPassport); 
	 		xmlOutput.output(document, f);
	 		f.close();
						
			return fRawPassport;
		  } 
		
		catch (IOException io) {
			writeError(io.toString());
			return null;
		  }
	}
	/**
	 * Создает и сохраняет в файл паспорт межшкафного кабеля
	 * @return имя файла
	 */
	public String createIcCablePassport(Cable cable) {
		
		Cabinet cab = (Cabinet)cbc.getElement(cable.getTo());
		try {	 
			
			Element html = new Element("html");
			Document document = new Document(html);
			
			Element head = new Element("head");
			Element meta = new Element("meta").setAttribute("http-equiv","content-type").setAttribute("content", "text/html;charset=utf-8");
			head.addContent(meta);
			meta = new Element("meta").setAttribute("title","Лист нагрузки межшкафного кабеля " + cable.toString());
			head.addContent(meta);
			Element body = new Element("body");
			Element style = new Element("style").setAttribute("type","text/css")
					.addContent(".empty{color: #cccccc} table{ width:100%; border-collapse: collapse; margin:0 0 20px 0}  .frame-table{border-style:none; width:600px} .frame-tr, .frame-td, .big-table-tr, .big-table-td {border-style:none}  .boxes-table{margin:0 10px 10px 0} td, th{border: 1px solid black; background-color:#eeeeee; font-size:10pt; padding:5px} td{background-color:#ffffff} ");
			head.addContent(style);
			
			html.addContent(head); html.addContent(body);
			
			Element frameTable = new Element("table").setAttribute("class","frame-table").setAttribute("cellpadding", "0").setAttribute("cellspacing", "0"); body.addContent(frameTable);
			Element frameTr = new Element("tr").setAttribute("class","frame-tr"); frameTable.addContent(frameTr);
			Element frameTd = new Element("td").setAttribute("class","frame-td"); frameTr.addContent(frameTd);
			
			frameTd.addContent(new Element("h2").addContent("ЛИСТ НАГРУЗКИ МЕЖШКАФНОГО КАБЕЛЯ"));
			frameTd.addContent(new Element("h4").addContent("Шкаф № " + cab.toString()));
			frameTd.addContent(new Element("h4").addContent("Кабель № " + cable.getNumber().toString()));
			frameTd.addContent(new Element("br")); frameTd.addContent(new Element("br"));
			
			/*
			 * Создаем таблицу нагрузки межшкафного кабеля
			 */
			Element loadTable = new Element("table").setAttribute("cellpadding", "0").setAttribute("cellspacing", "0"); frameTd.addContent(loadTable);
			Element loadTableTr1 = new Element("tr"); loadTable.addContent(loadTableTr1);
			loadTableTr1.addContent(new Element("th").addContent("Абонент"));
			loadTableTr1.addContent(new Element("th").addContent("Распределение"));
			loadTableTr1.addContent(new Element("th").addContent("Распределение"));
		
			for (Integer n = 0; n < cable.getCapacity(); n++) {
			
				Element tr = new Element("tr"); loadTable.addContent(tr);
				Element td1 = new Element("td"); tr.addContent(td1);
				tr.addContent(new Element("td").addContent(n.toString()));
				Element td3 = new Element("td"); tr.addContent(td3);
			
				Pair p = pc.getInPlace(cable, n);
				if (p != null) {
				
					Iterator<Path> pi = phc.getPairsPath(p).iterator();
				
					while (pi.hasNext()) {
						Path path = pi.next();
						td1.addContent(((Subscriber)sc.getElement(path.getSubscriber())).toString() +", " + path.getName()).addContent(new Element("br"));
											
						if (path.isicPair()) {
							Iterator <Integer> m = path.geticPair().iterator();
						
							while(m.hasNext()) {
								Pair icPair = (Pair)pc.getElement(m.next());
								Cable dcable = (Cable)cc.getElement(icPair.getCable());
								if (!dcable.getId().equals(cable.getId()) &&  (dcable.getFrom().equals(cab.getId()) || dcable.getTo().equals(cab.getId())))
									td3.addContent(icPair.toString()).addContent(new Element("br"));
							}
						}
					
						if (path.isdbPair()) {
							Pair dbPair = (Pair)pc.getElement(path.getdbPair());
							Cable dcable = (Cable)cc.getElement(dbPair.getCable());
							if (dcable.getFrom().equals(cab.getId()))
								td3.addContent(dbPair.toString()).addContent(new Element("br"));
						}
					
					}
			
				}
			
			}
		/*
		 * ---------------------------------------------------------------
		 */
		
		frameTd.addContent(new Element("br")); frameTd.addContent(new Element("br"));
		
		frameTd.addContent(new Element("h4").addContent("Cоставил: инженер _________________________   \"____\"___________201___г. "));
		frameTd.addContent(new Element("h4").addContent("Ответственный за технический учет линейных сооружений: __________________________ "));
		frameTd.addContent(new Element("h4").addContent(" _________________________________________________________________________________"));
		frameTd.addContent(new Element("h4").addContent("\"____\"___________201___г."));
		
		XMLOutputter xmlOutput = new XMLOutputter();
		xmlOutput.setFormat(Format.getCompactFormat().setEncoding("UTF-8").setOmitDeclaration(true).setIndent("  "));
 		
 		FileOutputStream f = new FileOutputStream(fRawPassport); 
 		xmlOutput.output(document, f);
 		f.close();
					
		return fRawPassport;
	  } 
	
	catch (IOException io) {
		writeError(io.toString());
		return null;
	  }
		
	}
	/**
	 * Создает и сохраняет в файл паспорт кабельной канализации
	 * @return имя файла
	 */
	public String createDuctPassport(Vector<Duct> ductSet) {
		try {
			
			Element html = new Element("html");
			Document document = new Document(html);
			
			Element head = new Element("head");
			head.addContent(new Element("meta").setAttribute("http-equiv","content-type").setAttribute("content", "text/html;charset=utf-8"));
			
			Element body = new Element("body");
			Element style = new Element("style").setAttribute("type","text/css")
					.addContent(".empty{color: #cccccc} table{ width:100%; border-collapse: collapse; margin:0 0 20px 0}  .frame-table{border-style:none; width:600px} .frame-tr, .frame-td, .big-table-tr, .big-table-td {border-style:none}  .boxes-table{margin:0 10px 10px 0} td, th{border: 1px solid black; background-color:#eeeeee; font-size:10pt; padding:5px} td{background-color:#ffffff} ");
			head.addContent(style);
			
			html.addContent(head); html.addContent(body);
			
			Element frameTable = new Element("table").setAttribute("class","frame-table").setAttribute("cellpadding", "0").setAttribute("cellspacing", "0"); body.addContent(frameTable);
			Element frameTr = new Element("tr").setAttribute("class","frame-tr"); frameTable.addContent(frameTr);
			Element frameTd = new Element("td").setAttribute("class","frame-td"); frameTr.addContent(frameTd);
			
			frameTd.addContent(new Element("h2").addContent("ПАСПОРТ КАБЕЛЬНОЙ КАНАЛИЗАЦИИ"));
			Element fromto = new Element("h4");
			frameTd.addContent(fromto); 
			
			Element ductsTable = new Element("table").setAttribute("cellpadding", "0").setAttribute("cellspacing", "0"); frameTd.addContent(ductsTable);
			Element ductsTableTr1 = new Element("tr"); ductsTable.addContent(ductsTableTr1);
			ductsTableTr1.addContent(new Element("th").addContent("№ п/п").setAttribute("rowspan","2"));
			ductsTableTr1.addContent(new Element("th").addContent("Емкость канализации").setAttribute("rowspan","2"));
			ductsTableTr1.addContent(new Element("th").addContent("Материал трубопровода").setAttribute("rowspan","2"));
			ductsTableTr1.addContent(new Element("th").addContent("Диаметр канала, мм").setAttribute("rowspan","2"));
			ductsTableTr1.addContent(new Element("th").addContent("Канализация проложена").setAttribute("colspan","6"));
			
			Element ductsTableTr2 = new Element("tr"); ductsTable.addContent(ductsTableTr2);
			ductsTableTr2.addContent(new Element("th").addContent("от колодца №"));
			ductsTableTr2.addContent(new Element("th").addContent("до колодца №"));
			ductsTableTr2.addContent(new Element("th").addContent("до дома №"));
			ductsTableTr2.addContent(new Element("th").addContent("длина линии, м"));
			ductsTableTr2.addContent(new Element("th").addContent("длина каналов, м"));
			ductsTableTr2.addContent(new Element("th").addContent("дата"));
			
			Integer n = 1;
			Iterator<Duct> i = ductSet.iterator();
			while (i.hasNext()) {
				Duct duct = i.next();
				HashSet<Tube> h = tuc.getDuctsTubes(duct);
				AbstractElement from = mc.getElement(duct.getFrom());
				AbstractElement to = mc.getElement(duct.getTo());
				AbstractElement tob = buc.getElement(duct.getTo());
				
				if (n.equals(1)) if (from != null) fromto.addContent("От: " + ((Manhole)from).getAdress()).addContent(new Element("br"));
		
				if (n.equals(ductSet.size())) {
					if (to != null)  { fromto.addContent("До: " + ((Manhole)to).getAdress());}
					else {
						if (tob != null) fromto.addContent("До: " + ((Building)tob).toString());
					}
					
				}
				Element ductTableTr = new Element("tr"); ductsTable.addContent(ductTableTr);
				ductTableTr.addContent(new Element("td").addContent(n.toString()));
				ductTableTr.addContent(new Element("td").addContent(((Integer)h.size()).toString()));
				ductTableTr.addContent(new Element("td").addContent(duct.getTubeMaterial()));
				ductTableTr.addContent(new Element("td").addContent(duct.getTubeDiametr().toString()));
				
				if (from != null) {
					ductTableTr.addContent(new Element("td").addContent(from.toString()));
				}
				else {
					ductTableTr.addContent(new Element("td").addContent("-"));
				}
				
				if (to != null) {
					ductTableTr.addContent(new Element("td").addContent(to.toString()));
					ductTableTr.addContent(new Element("td").addContent("-"));
				}
				else {
					ductTableTr.addContent(new Element("td").addContent("-"));
					if (tob != null) {
						ductTableTr.addContent(new Element("td").addContent(tob.toString()));
					}
					else {
						ductTableTr.addContent(new Element("td").addContent("-"));
					}
				}
				
				ductTableTr.addContent(new Element("td").addContent(duct.getLenght().toString()));
				ductTableTr.addContent(new Element("td").addContent( ((Integer)(duct.getLenght() * h.size())).toString()  ));
				ductTableTr.addContent(new Element("td").addContent(duct.getDate()));
				
				n++;	
			}
			
			frameTd.addContent(new Element("br")); frameTd.addContent(new Element("br"));
			frameTd.addContent(new Element("h3").addContent("Отметки о техническом состоянии"));
			Element techTable = new Element("table").setAttribute("cellpadding", "0").setAttribute("cellspacing", "0"); frameTd.addContent(techTable);
			Element techTableTr1 = new Element("tr"); techTable.addContent(techTableTr1);
			techTableTr1.addContent(new Element("th").addContent("Дата обнаружения повреждения"));
			techTableTr1.addContent(new Element("th").addContent("Характер повреждения"));
			techTableTr1.addContent(new Element("th").addContent("Описание работ по устранению"));
			techTableTr1.addContent(new Element("th").addContent("Дата устранения"));
			
			for (int x = 0; x < 3; x++) {
				Element techTableTr = new Element("tr"); techTable.addContent(techTableTr);
				techTableTr.addContent(new Element("td").addContent(new Element("br")).addContent(new Element("br")).addContent(new Element("br")));
				techTableTr.addContent(new Element("td")).addContent(new Element("td")).addContent(new Element("td"));	
			}
			
			frameTd.addContent(new Element("br")); frameTd.addContent(new Element("br"));
			frameTd.addContent(new Element("h4").addContent("Cоставил: инженер _________________________   \"____\"___________201___г. "));
			frameTd.addContent(new Element("h4").addContent("Ответственный за технический учет линейных сооружений: __________________________ "));
			frameTd.addContent(new Element("h4").addContent(" _________________________________________________________________________________"));
			frameTd.addContent(new Element("h4").addContent("\"____\"___________201___г."));
			
			XMLOutputter xmlOutput = new XMLOutputter();
			xmlOutput.setFormat(Format.getCompactFormat().setEncoding("UTF-8").setOmitDeclaration(true).setIndent("  "));
	 		
	 		FileOutputStream f = new FileOutputStream(fRawPassport); 
	 		xmlOutput.output(document, f);
	 		f.close();
			
	 		return fRawPassport;
		}
		catch(IOException io) {
			writeError(io.toString());
			return null;
		}
	}
	/**
	 * Создает и сохраняет в файл паспорт колодца
	 * @return имя файла
	 */
	public String createManholePassport(Manhole man) {
		try {
			
			Element html = new Element("html");
			Document document = new Document(html);
			
			Element head = new Element("head");
			head.addContent(new Element("meta").setAttribute("http-equiv","content-type").setAttribute("content", "text/html;charset=utf-8"));
			
			Element body = new Element("body");
			Element style = new Element("style").setAttribute("type","text/css")
					.addContent(".empty{color: #cccccc} table{ width:100%; border-collapse: collapse; margin:0 0 20px 0}  .frame-table{border-style:none; width:600px} .frame-tr, .frame-td, .big-table-tr, .big-table-td {border-style:none}  .boxes-table{margin:0 10px 10px 0} td, th{border: 1px solid black; background-color:#eeeeee; font-size:10pt; padding:5px} td{background-color:#ffffff} ");
			head.addContent(style);
			
			html.addContent(head); html.addContent(body);
			
			Element frameTable = new Element("table").setAttribute("class","frame-table").setAttribute("cellpadding", "0").setAttribute("cellspacing", "0"); body.addContent(frameTable);
			Element frameTr = new Element("tr").setAttribute("class","frame-tr"); frameTable.addContent(frameTr);
			Element frameTd = new Element("td").setAttribute("class","frame-td"); frameTr.addContent(frameTd);
			
			frameTd.addContent(new Element("h2").addContent("ПАСПОРТ КОЛОДЦА № "+ man.getSNumber()));
			frameTd.addContent(new Element("h4").addContent("Адрес колодца: " + man.getAdress()));
			frameTd.addContent(new Element("h4").addContent("Конструкция колодца: " + man.getConstructionMnemonic()));
			frameTd.addContent(new Element("h4").addContent("Форма стенок: " + man.getFormMnemonic()));
			frameTd.addContent(new Element("h4").addContent("Дата постройки: " + man.getDate()));
			frameTd.addContent(new Element("h4").addContent("Размеры колодца (для нетиповых): " + man.getSize()));
			frameTd.addContent(new Element("br")); frameTd.addContent(new Element("br"));
			
			Element cablesTable = new Element("table").setAttribute("cellpadding", "0").setAttribute("cellspacing", "0"); frameTd.addContent(cablesTable);
			Element cablesTableTr1 = new Element("tr"); cablesTable.addContent(cablesTableTr1);
			cablesTableTr1.addContent(new Element("th").addContent("Кабель"));
			cablesTableTr1.addContent(new Element("th").addContent("№ канала приходящего"));
			cablesTableTr1.addContent(new Element("th").addContent("№ канала уходящего"));
			cablesTableTr1.addContent(new Element("th").addContent("Расстояние до следующего колодца, м"));
			
			HashSet<Duct> ductInSet = duc.getInDuct(man);
			HashSet<Duct> ductOutSet = duc.getOutDuct(man);
			
			Iterator <Duct> d = ductInSet.iterator();
			while (d.hasNext()) {
				Duct duct = d.next();
				HashSet<Tube> tubeSet = tuc.getDuctsTubes(duct);
				Iterator<Tube> t = tubeSet.iterator();
				while (t.hasNext()) {
					Tube tube = t.next();
					Vector<Cable> cableSet = cc.getTubesCables(tube);
					Iterator<Cable> c = cableSet.iterator();
					while (c.hasNext()) {
						Cable cable = c.next();
						Element cablesTableTr = new Element("tr"); cablesTable.addContent(cablesTableTr);
						cablesTableTr.addContent(new Element("td").addContent(cable.toString()));
						cablesTableTr.addContent(new Element("td").addContent(duct.toString()+", "+tube.toString()));
						
						Tube outTube = null;
						Duct outDuct = null;
						Iterator<Duct> o = ductOutSet.iterator();
						while (o.hasNext()) {
							Duct od = o.next();
							outTube = tuc.getTubeBuCableDuct(cable, od);
							if (outTube != null) {
								outDuct = od;
								break;
							}
						}
						
						if (outTube != null) {
							cablesTableTr.addContent(new Element("td").addContent(outDuct.toString()+", "+outTube.toString()));
							cablesTableTr.addContent(new Element("td").addContent(outDuct.getLenght().toString()));
						}
						else {
							cablesTableTr.addContent(new Element("td").addContent("-"));
							cablesTableTr.addContent(new Element("td").addContent("-"));
						
						}
						
						
					}
				}
			}
			
			frameTd.addContent(new Element("br")); frameTd.addContent(new Element("br"));
			frameTd.addContent(new Element("h4").addContent("Cоставил: инженер _________________________   \"____\"___________201___г. "));
			frameTd.addContent(new Element("h4").addContent("Ответственный за технический учет линейных сооружений: __________________________ "));
			frameTd.addContent(new Element("h4").addContent(" _________________________________________________________________________________"));
			frameTd.addContent(new Element("h4").addContent("\"____\"___________201___г."));
			
			XMLOutputter xmlOutput = new XMLOutputter();
			xmlOutput.setFormat(Format.getCompactFormat().setEncoding("UTF-8").setOmitDeclaration(true).setIndent("  "));
	 		
	 		FileOutputStream f = new FileOutputStream(fRawPassport); 
	 		xmlOutput.output(document, f);
	 		f.close();
			
	 		return fRawPassport;
		}
		catch(IOException io) {
			writeError(io.toString());
			return null;
		}
	}
	/**
	 * Создает и сохраняет в файл паспорт кабельного ввода
	 * @return имя файла
	 */
	public String createCableglandPassport(Duct duct) {
		try {
			
			Element html = new Element("html");
			Document document = new Document(html);
			
			Element head = new Element("head");
			head.addContent(new Element("meta").setAttribute("http-equiv","content-type").setAttribute("content", "text/html;charset=utf-8"));
			
			Element body = new Element("body");
			Element style = new Element("style").setAttribute("type","text/css")
					.addContent(".empty{color: #cccccc} table{ width:100%; border-collapse: collapse; margin:0 0 20px 0}  .frame-table{border-style:none; width:600px} .frame-tr, .frame-td, .big-table-tr, .big-table-td {border-style:none}  .boxes-table{margin:0 10px 10px 0} td, th{border: 1px solid black; background-color:#eeeeee; font-size:10pt; padding:5px} td{background-color:#ffffff} ");
			head.addContent(style);
			
			html.addContent(head); html.addContent(body);
			
			Element frameTable = new Element("table").setAttribute("class","frame-table").setAttribute("cellpadding", "0").setAttribute("cellspacing", "0"); body.addContent(frameTable);
			Element frameTr = new Element("tr").setAttribute("class","frame-tr"); frameTable.addContent(frameTr);
			Element frameTd = new Element("td").setAttribute("class","frame-td"); frameTr.addContent(frameTd);
			
			frameTd.addContent(new Element("h2").addContent("ПАСПОРТ КАБЕЛЬНОГО ВВОДА"));
			Building building = (Building)buc.getElement(duct.getTo());
			frameTd.addContent(new Element("h4").addContent("Адрес: " + building.toString()));
			frameTd.addContent(new Element("h4").addContent("Способ изготовления ввода: " + duct.getМanufacturingМethod()));
			
			frameTd.addContent(new Element("br")); frameTd.addContent(new Element("br"));
			
			Element cablesTable = new Element("table").setAttribute("cellpadding", "0").setAttribute("cellspacing", "0"); frameTd.addContent(cablesTable);
			Element cablesTableTr1 = new Element("tr"); cablesTable.addContent(cablesTableTr1);
			cablesTableTr1.addContent(new Element("th").addContent("№ распределительной коробки"));
			cablesTableTr1.addContent(new Element("th").addContent("Длина кабеля, м"));
			cablesTableTr1.addContent(new Element("th").addContent("Диаметр жил"));
			cablesTableTr1.addContent(new Element("th").addContent("№ шкафа"));
			
			HashSet<DBox> hdbox= dbc.getInBuilding(building);
			
			Iterator <AbstractElement> i = tuc.sortByIdUp(tuc.getDuctsTubes(duct)).iterator();
			while (i.hasNext()) {
				
				Tube tube = (Tube)i.next();
				
				Iterator <Integer> k = tube.getCables().iterator();
				while (k.hasNext()) {
					Cable cable = (Cable)cc.getElement(k.next());
					
					if (cable != null) {
						
						Element cablesTableTr = new Element("tr"); cablesTable.addContent(cablesTableTr);
						
						DBox dbox  = null;
						
						Iterator <DBox> d = hdbox.iterator();
						while (d.hasNext()) {
							DBox db = (DBox)d.next();
							Pair p = pc.getInPlace(db, 0);
							
							if (cable.getId().equals(p.getCable())) dbox = db;
						}
						
						if (dbox != null) { cablesTableTr.addContent(new Element("td").addContent(dbox.toString()));}
						else { cablesTableTr.addContent(new Element("td").addContent("-")); }
						
						
						cablesTableTr.addContent(new Element("td").addContent(cable.getLenght().toString()));
						cablesTableTr.addContent(new Element("td").addContent(cable.getWireDiametr().toString()));
						
						Cabinet cab = (Cabinet)cbc.getElement(cable.getFrom());
						if (cab != null) { cablesTableTr.addContent(new Element("td").addContent(cab.toString()));}
						else { cablesTableTr.addContent(new Element("td").addContent("-")); }
					}
				}
				
			}
			frameTd.addContent(new Element("br")); frameTd.addContent(new Element("br"));
			frameTd.addContent(new Element("h4").addContent("Cоставил: инженер _________________________   \"____\"___________201___г. "));
			frameTd.addContent(new Element("h4").addContent("Ответственный за технический учет линейных сооружений: __________________________ "));
			frameTd.addContent(new Element("h4").addContent(" _________________________________________________________________________________"));
			frameTd.addContent(new Element("h4").addContent("\"____\"___________201___г."));
			
			XMLOutputter xmlOutput = new XMLOutputter();
			xmlOutput.setFormat(Format.getCompactFormat().setEncoding("UTF-8").setOmitDeclaration(true).setIndent("  "));
	 		
	 		FileOutputStream f = new FileOutputStream(fRawPassport); 
	 		xmlOutput.output(document, f);
	 		f.close();
			
	 		return fRawPassport;
		}
		catch(IOException io) {
			writeError(io.toString());
			return null;
		}
	}
	/**
	 * Создает и сохраняет в файл адресный лист коробок
	 * @return имя файла
	 */
	public String createDBoxPassport(Net net) {
		try {
			
			Element html = new Element("html");
			Document document = new Document(html);
			
			Element head = new Element("head");
			head.addContent(new Element("meta").setAttribute("http-equiv","content-type").setAttribute("content", "text/html;charset=utf-8"));
			
			Element body = new Element("body");
			Element style = new Element("style").setAttribute("type","text/css")
					.addContent(".empty{color: #cccccc} table{ width:100%; border-collapse: collapse; margin:0 0 20px 0}  .frame-table{border-style:none; width:600px} .frame-tr, .frame-td, .big-table-tr, .big-table-td {border-style:none}  .boxes-table{margin:0 10px 10px 0} td, th{border: 1px solid black; background-color:#eeeeee; font-size:10pt; padding:5px} td{background-color:#ffffff} ");
			head.addContent(style);
			
			html.addContent(head); html.addContent(body);
			
			Element frameTable = new Element("table").setAttribute("class","frame-table").setAttribute("cellpadding", "0").setAttribute("cellspacing", "0"); body.addContent(frameTable);
			Element frameTr = new Element("tr").setAttribute("class","frame-tr"); frameTable.addContent(frameTr);
			Element frameTd = new Element("td").setAttribute("class","frame-td"); frameTr.addContent(frameTd);
			
			frameTd.addContent(new Element("h2").addContent("АДРЕСНЫЙ ЛИСТ КОРОБОК "));
			
			frameTd.addContent(new Element("br")); frameTd.addContent(new Element("br"));
			
			Element cablesTable = new Element("table").setAttribute("cellpadding", "0").setAttribute("cellspacing", "0"); frameTd.addContent(cablesTable);
			Element cablesTableTr1 = new Element("tr"); cablesTable.addContent(cablesTableTr1);
			cablesTableTr1.addContent(new Element("th").addContent("№ коробки"));
			cablesTableTr1.addContent(new Element("th").addContent("Адрес установки"));
			cablesTableTr1.addContent(new Element("th").addContent("Место установки"));
			cablesTableTr1.addContent(new Element("th").addContent("Расстояние до шкафа, м"));
			
			Iterator <AbstractElement> i = dbc.sortByIdUp(dbc.getInNet(net)).iterator();
			
			while (i.hasNext()) {
				DBox dbox = (DBox)i.next();
				Building build = (Building)buc.getElement(dbox.getBuilding());
				Pair p = pc.getInPlace(dbox, 0);
				
				Cable cable = null;
				if (p != null)  cable = (Cable)cc.getElement(p.getCable());
				
				Element cablesTableTr = new Element("tr"); cablesTable.addContent(cablesTableTr);
				cablesTableTr.addContent(new Element("td").addContent(dbox.toString()));
				
				if (build != null) { cablesTableTr.addContent(new Element("td").addContent(build.toString())); }
				else { cablesTableTr.addContent(new Element("td").addContent("-")); }
				
				cablesTableTr.addContent(new Element("td").addContent(dbox.getPlase()));
				
				if (cable != null) { cablesTableTr.addContent(new Element("td").addContent(cable.getLenght().toString()));}
				else { cablesTableTr.addContent(new Element("td").addContent("-")); }
				
			}
			
			frameTd.addContent(new Element("br")); frameTd.addContent(new Element("br"));
			frameTd.addContent(new Element("h4").addContent("Cоставил: инженер _________________________   \"____\"___________201___г. "));
			frameTd.addContent(new Element("h4").addContent("Ответственный за технический учет линейных сооружений: __________________________ "));
			frameTd.addContent(new Element("h4").addContent(" _________________________________________________________________________________"));
			frameTd.addContent(new Element("h4").addContent("\"____\"___________201___г."));
			
			XMLOutputter xmlOutput = new XMLOutputter();
			xmlOutput.setFormat(Format.getCompactFormat().setEncoding("UTF-8").setOmitDeclaration(true).setIndent("  "));
	 		
	 		FileOutputStream f = new FileOutputStream(fRawPassport); 
	 		xmlOutput.output(document, f);
	 		f.close();
			
	 		return fRawPassport;
		}
		catch(IOException io) {
			writeError(io.toString());
			return null;
		}
	}
	
}