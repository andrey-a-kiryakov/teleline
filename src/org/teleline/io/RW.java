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
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import org.teleline.model.*;
import org.teleline.system.Sys;


public class RW {
	private static String dTmpFolder = "tmp";
	private static String dLogsFolder = "logs";
	private static String dSavesFolder = "saves";
	private static String dPassportsFolder = "saves";
	
	protected static String fSave = dSavesFolder + "/tlsave_";
	private static String fLog = dLogsFolder + "/teleline.log";
	private static String fNonSavedLog = dTmpFolder + "/teleline.$$$";
	private static String fErrorsLog = dLogsFolder +"/errors.log";
	private static String fRawPassport = dTmpFolder + "/rawpass.html";
	
	protected Sys sys;	
	public RW(Sys sys) {
		this.sys = sys;
	}
	
	public void writeLog () {
		
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
	
	public Integer valueOf(Attribute attribute) {
		
		if (attribute == null) {
			writeError("Отсутствует аттрибут");
			return 0;
		}
		else {
			return valueOf(attribute.getValue());
		}
	}
	
	public String textOf(Element element) {
		
		if (element == null) {
			writeError("Отсутствует элемент");
			return "";
		}
		else {
			if (element.getValue().length() > 150)
				return element.getValue().substring(0, 149);
			
			return element.getValue();
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
		
		SimpleDateFormat DF = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss:SSS");
		
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
			Vector<AbstractElement> mcable = sys.cc.sortByIdUp(sys.cc.getMCable(cab));
			Vector<AbstractElement> icablein = sys.cc.sortByIdUp(sys.cc.getICableIn(cab));
			Vector<AbstractElement> icableout = sys.cc.sortByIdUp(sys.cc.getICableOut(cab));
			Vector<AbstractElement> dcableout = sys.cc.sortByIdUp(sys.cc.getDCableOut(cab));
			
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
				
				Box box = (Box)sys.bc.getInPlace(i, cab.getId());
				
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
							Pair p = sys.pc.getInPlace(box, n * 10 + k);
						
							if (p != null) {
								Integer cableId = p.getCable();
								Cable c = (Cable)sys.cc.getElement(cableId);
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
				
				Iterator<AbstractElement> pairIterator = sys.pc.sortByIdUp(sys.pc.getInCable(cable, 2)).iterator();
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
			meta = new Element("meta").setAttribute("title","Паспорт кросса: " + dframe.getName());
			head.addContent(meta);
			Element body = new Element("body");
			Element style = new Element("style").setAttribute("type","text/css")
					.addContent(
							".empty{border: 0px solid black; margin:5px;} " +
							"table{border-collapse: collapse}  " +
							".big-table-tr, .big-table-td {border-style:none}  " +
							".boxes-table{margin:5px;} " +
							"td, th{vertical-align: top; border: 1px solid black; background-color:#eeeeee; font-size:10pt; padding:2px} " +
							"td{background-color:#ffffff} ");
			head.addContent(style);
			
			html.addContent(head); html.addContent(body);
			body.addContent(new Element("h2").addContent("Паспорт кросса: " + dframe.getName()));
	
			Element bigtable = new Element("table").setAttribute("class","big-table").setAttribute("cellpadding", "0").setAttribute("cellspacing", "0"); 
			
			body.addContent(bigtable);
			int x = 0; Element newtr = new Element("tr").setAttribute("class","big-table-tr");
			
			for (int i = 0; i < dframe.getPlacesCount(); i++) {
				
				Frame frame = (Frame)sys.fc.getInPlace(i, dframe.getId());
				
				if (x > 5) x = 0;
				
				if (x == 0)  { newtr =  new Element("tr"); bigtable.addContent(newtr); }
					
				Element newtd = new Element("td").setAttribute("class","big-table-td"); newtr.addContent(newtd);
				
				if (frame == null) {
					Element div = new Element("div").setAttribute("class", "empty").addContent("Незанятое место №"+((Integer)i).toString());
					newtd.addContent(div);
				//	Element boxtable = new Element("table").setAttribute("class", "boxes-table").setAttribute("width", "100%").setAttribute("cellpadding", "2").setAttribute("cellspacing", "1"); 
				//	newtd.addContent(boxtable);
				//	Element tr = new Element("tr"); boxtable.addContent(tr);
				//	tr.addContent(new Element("th").setAttribute("class", "empty").addContent("пусто"));
				//	tr.addContent(new Element("th").setAttribute("class", "empty").addContent("пусто"));
				//	tr.addContent(new Element("th").setAttribute("class", "empty").addContent("пусто"));
					
				//	for (int n = 0; n < 15; n++) {
						
				//		Element tr11 = new Element("tr").setAttribute("class", "empty");
				//		boxtable.addContent(tr11);
				//		tr11.addContent(new Element("td").setAttribute("class", "empty").addContent("пусто"));
				//		tr11.addContent(new Element("td").setAttribute("class", "empty").addContent(((Integer)(n*10)).toString()+"-"+((Integer)(n*10+9)).toString()));
				//		tr11.addContent(new Element("td").setAttribute("class", "empty").addContent("пусто"));
				//	}
				}
				else {
				
					Element boxtable = new Element("table").setAttribute("class", "boxes-table").setAttribute("cellpadding", "0").setAttribute("cellspacing", "0"); 
					newtd.addContent(boxtable);
					Element tr = new Element("tr"); boxtable.addContent(tr);
					tr.addContent(new Element("th").addContent("Шк"));
					tr.addContent(new Element("th").addContent(frame.toString()));
					tr.addContent(new Element("th").addContent("М"));
					
				
					for (int n = 0; n < frame.getCapacity()/10; n++) {
					
						Element tr11 = new Element("tr"); boxtable.addContent(tr11);
						
						
						HashSet<Cable> h = new HashSet<Cable>();
						HashSet<StructuredElement> ch = new HashSet<StructuredElement>();
						
						for (int k = 0; k < 10; k++) {
							Pair p = sys.pc.getInPlace(frame, n * 10 + k);
						
							if (p != null) {
								Integer cableId = p.getCable();
								Cable c = (Cable)sys.cc.getElement(cableId);
								StructuredElement se;
								if (c.getType().equals(0)) {
									 se = (StructuredElement) sys.cbc.getElement(c.getTo());
									 if (!ch.contains(se)) ch.add(se);
								}
								if (c.getType().equals(3)) {
								//	 se = (StructuredElement) sys.dbc.getElement(c.getTo());
									 se = (StructuredElement) sys.dbc.getElement(p.getElementTo());
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
			
			body.addContent(new Element("br")); body.addContent(new Element("br"));
			body.addContent(new Element("h4").addContent("Cоставил: инженер _________________________   \"____\"___________201___г. "));
			body.addContent(new Element("h4").addContent("Ответственный за технический учет линейных сооружений: __________________________ "));
			body.addContent(new Element("h4").addContent(" _________________________________________________________________________________"));
			body.addContent(new Element("h4").addContent("\"____\"___________201___г."));
			body.addContent(new Element("br")); body.addContent(new Element("br"));
			body.addContent(new Element("br")); body.addContent(new Element("br"));
			
			/*
			 * Создаем таблицу пар прямого питания
			 */
			body.addContent(new Element("h2").addContent("Журнал пар прямого питания"));
			Element cablesTable = new Element("table").setAttribute("cellpadding", "0").setAttribute("cellspacing", "0"); 
			body.addContent(cablesTable);
			
			Element cablesTableTr1 = new Element("tr"); cablesTable.addContent(cablesTableTr1);
			
			cablesTableTr1.addContent(new Element("th").addContent("Кабель"));
			cablesTableTr1.addContent(new Element("th").addContent("Марка и емкость"));
			cablesTableTr1.addContent(new Element("th").addContent("№ ГП"));
			cablesTableTr1.addContent(new Element("th").addContent("Пара ГП"));
			cablesTableTr1.addContent(new Element("th").addContent("КРТ"));
			cablesTableTr1.addContent(new Element("th").addContent("Пара КРТ"));
			cablesTableTr1.addContent(new Element("th").addContent("Адрес КРТ"));
			cablesTableTr1.addContent(new Element("th").addContent("Абонент"));
			cablesTableTr1.addContent(new Element("th").addContent("Дата включения"));
			
			Vector<AbstractElement> dcableout = sys.cc.sortByIdUp(sys.cc.getDCableOut(dframe));
			
			Iterator <AbstractElement> c = dcableout.iterator();
			while (c.hasNext()) {

				Cable cable = (Cable)c.next();
				
				for (int i = 0; i < cable.getCapacity(); i++) {
					
					Pair p = sys.pc.getInPlace(cable, i);
					if (p != null) {
					Element tr = new Element("tr"); cablesTable.addContent(tr);
					tr.addContent(new Element("td").addContent(cable.toString()));
					tr.addContent(new Element("td").addContent(cable.getLabel() + "x" + cable.getCapacity()));
					
					Frame frame = (Frame)sys.fc.getElement(p.getElementFrom());
					tr.addContent(new Element("td").addContent(frame.toString()));
					tr.addContent(new Element("td").addContent(p.getFromNumber().toString()));
					
					DBox dbox = (DBox)sys.dbc.getElement(p.getElementTo());
					if (dbox!= null) {
						tr.addContent(new Element("td").addContent(dbox.toString()));
						tr.addContent(new Element("td").addContent(p.getToNumber().toString()));
						Building build = (Building)sys.buc.getElement(dbox.getBuilding());
						tr.addContent(new Element("td").addContent(build.toString()));
						
					}
					else {
						tr.addContent(new Element("td").addContent(""));
						tr.addContent(new Element("td").addContent(""));
						tr.addContent(new Element("td").addContent(""));
					}
					
					
					Vector <Subscriber> vSub = new Vector<Subscriber>();
					Vector <String> vDate = new Vector<String>();
					
					Iterator<Path> pi = sys.phc.getPairsPath(p).iterator();
					while (pi.hasNext()) {
						Subscriber sub = (Subscriber)sys.sc.getElement(pi.next().getSubscriber());
						if (!vSub.contains(sub)) { 
							vSub.add(sub);
							vDate.add(sub.getDate());
						}
					}
					String s = vSub.toString(), a = vDate.toString();
					tr.addContent(new Element("td").addContent(s.substring(1, s.length()-1)));
					tr.addContent(new Element("td").addContent(a.substring(1, a.length()-1)));
				}
				}
				//Пустая строка, отделяющая кабели в таблице
				Element tr = new Element("tr"); cablesTable.addContent(tr);
				for (int i = 0; i < 9; i++) {
					tr.addContent(new Element("td").addContent(new Element("br")));
				}
			}
			
			body.addContent(new Element("br")); body.addContent(new Element("br"));
			body.addContent(new Element("h4").addContent("Cоставил: инженер _________________________   \"____\"___________201___г. "));
			body.addContent(new Element("h4").addContent("Ответственный за технический учет линейных сооружений: __________________________ "));
			body.addContent(new Element("h4").addContent(" _________________________________________________________________________________"));
			body.addContent(new Element("h4").addContent("\"____\"___________201___г."));
			body.addContent(new Element("br")); body.addContent(new Element("br"));
			body.addContent(new Element("br")); body.addContent(new Element("br"));
			
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
			frameTd.addContent(new Element("h2").addContent("Карточка абонента"));
			
			Element table = new Element("table").setAttribute("cellpadding", "0").setAttribute("cellspacing", "0"); frameTd.addContent(table);
			Element tr1 = new Element("tr"); table.addContent(tr1);
			tr1.addContent(new Element("th").setAttribute("colspan","2").addContent("Номер: " + sub.getPhoneNumber()));
			tr1.addContent(new Element("th").setAttribute("colspan","6").addContent("Абонент: " + sub.getName()).addContent(new Element("br")).addContent("Адрес установки: " + sub.getAdress()));
			tr1.addContent(new Element("th").addContent("Дата установки:").addContent(new Element("br")).addContent(sub.getDate()));
			
			Element tr2 = new Element("tr"); table.addContent(tr2);
			tr2.addContent(new Element("td").addContent("Громполоса"));
			tr2.addContent(new Element("td").addContent("Пара").addContent(new Element("br")).addContent("громполосы"));
			tr2.addContent(new Element("td").addContent("Шкаф"));
			tr2.addContent(new Element("td").addContent("№").addContent(new Element("br")).addContent("МБ/РБ"));
			tr2.addContent(new Element("td").addContent("Магистраль/").addContent(new Element("br")).addContent("Распределение"));
			tr2.addContent(new Element("td").addContent("№ РБ"));
			tr2.addContent(new Element("td").addContent("Распред."));
			tr2.addContent(new Element("td").addContent("Рамка").addContent(new Element("br")).addContent("перехода"));
			tr2.addContent(new Element("td").addContent("Примечание"));
			
			Element newTr = new Element("tr");;
			
			Iterator<Path> pt = sys.phc.getSubscriberPaths(sub).iterator();
			while (pt.hasNext()) {
				
				Path path = pt.next();
				newTr = new Element("tr");
			
				if (path.isdrPair()) {
				
					
						Pair p = (Pair)sys.pc.getElement(path.getdrPair());
						Frame f = (Frame)sys.fc.getElement(p.getElementFrom());
				
						newTr.addContent(new Element("td").addContent(f.toString()));
						newTr.addContent(new Element("td").addContent(p.getFromNumber().toString()));
						newTr.addContent(new Element("td").addContent(" "));
						newTr.addContent(new Element("td").addContent(" "));
						newTr.addContent(new Element("td").addContent(" "));
					
	
				}
				if (path.ismPair()) {	
				
					Pair p = (Pair)sys.pc.getElement(path.getmPair());
					Frame f = (Frame)sys.fc.getElement(p.getElementFrom());
					Box b = (Box)sys.bc.getElement(p.getElementTo());
					Cabinet cb = (Cabinet)sys.cbc.getElement(b.getOwnerId());
					Cable c = (Cable)sys.cc.getElement(p.getCable());
						
					newTr.addContent(new Element("td").addContent(f.toString()));
					newTr.addContent(new Element("td").addContent(p.getFromNumber().toString()));
					newTr.addContent(new Element("td").addContent(cb.toString()));
					newTr.addContent(new Element("td").addContent(b.toString()+"-"+p.getToNumber()));
					newTr.addContent(new Element("td").addContent(c.toShortString()+"-"+p.getNumberInCable().toString()));
					
				}
				if (!path.ismPair() && path.isicPair()) {
					newTr.addContent(new Element("td").addContent(" "));
					newTr.addContent(new Element("td").addContent(" "));
					newTr.addContent(new Element("td").addContent(" "));
					newTr.addContent(new Element("td").addContent(" "));
					newTr.addContent(new Element("td").addContent(" "));
				}
			
		
			for (int i = 0; i < path.geticPair().size(); i++) {
				Pair p = (Pair)sys.pc.getElement(path.geticPair().get(i));
				Cable c = (Cable)sys.cc.getElement(p.getCable());
				
				Box bto = (Box)sys.bc.getElement(p.getElementTo());
				Box bfrom = (Box)sys.bc.getElement(p.getElementFrom());
				
				//Cabinet cbfrom = (Cabinet)cbc.getElement(bfrom.getOwnerId());
				Cabinet cbto = (Cabinet)sys.cbc.getElement(bto.getOwnerId());
				
				newTr.addContent(new Element("td").addContent(bfrom.toString()+"-"+p.getFromNumber()));
				newTr.addContent(new Element("td").addContent(c.toShortString()+"-"+p.getNumberInCable().toString()));
				newTr.addContent(new Element("td").addContent(" ")); //рамка перехода
				newTr.addContent(new Element("td").addContent(path.getName())); //примечание
				table.addContent(newTr);
				
				newTr = new Element("tr");
				newTr.addContent(new Element("td").addContent(" ")); //громполоса
				newTr.addContent(new Element("td").addContent(" ")); //пара громполосы
				
				newTr.addContent(new Element("td").addContent(cbto.toString()));
				newTr.addContent(new Element("td").addContent(bto.toString()+"-"+p.getToNumber()));
				newTr.addContent(new Element("td").addContent(c.toShortString()+"-"+p.getNumberInCable().toString()));
			}
			
			
			if ( path.ismPair() && ((path.isicPair() && path.isdbPair()) || (!path.isicPair() && path.isdbPair()))) { //есть/нет передача, есть распределение
				
					Pair p = (Pair)sys.pc.getElement(path.getdbPair());
					Box b = (Box)sys.bc.getElement(p.getElementFrom());
						
					Cable c = (Cable)sys.cc.getElement(p.getCable());
					newTr.addContent(new Element("td").addContent(b.toString()+"-"+p.getFromNumber()));
					newTr.addContent(new Element("td").addContent(c.toShortString()+"-"+p.getNumberInCable().toString()));
						
					newTr.addContent(new Element("td").addContent(" "));
					newTr.addContent(new Element("td").addContent(path.getName())); //примечание
					table.addContent(newTr);
				
			}
			
			if ( path.ismPair() && ((path.isicPair() && !path.isdbPair()) || (!path.isicPair() && !path.isdbPair()))) { //есть/нет передачи, нет распределения
				
				newTr.addContent(new Element("td").addContent(" "));
				newTr.addContent(new Element("td").addContent(" "));
				newTr.addContent(new Element("td").addContent(" "));
				newTr.addContent(new Element("td").addContent(path.getName())); //примечание
				table.addContent(newTr);
			}
			
			
			if (!path.ismPair() && !path.isicPair() && path.isdbPair()) { //только распределение
				
				
					Pair p = (Pair)sys.pc.getElement(path.getdbPair());
					Box b = (Box)sys.bc.getElement(p.getElementFrom());
					Cabinet cb = (Cabinet)sys.cbc.getElement(b.getOwnerId());	
					Cable c = (Cable)sys.cc.getElement(p.getCable());
				
					newTr = new Element("tr");
					newTr.addContent(new Element("td").addContent(" "));
					newTr.addContent(new Element("td").addContent(" "));
					newTr.addContent(new Element("td").addContent(cb.toString()));
					newTr.addContent(new Element("td").addContent(" "));
					newTr.addContent(new Element("td").addContent(" "));
					newTr.addContent(new Element("td").addContent(b.toString()+"-"+p.getFromNumber()));
					newTr.addContent(new Element("td").addContent(c.toShortString()+"-"+p.getNumberInCable().toString()));
					newTr.addContent(new Element("td").addContent(" "));
					newTr.addContent(new Element("td").addContent(path.getName())); //примечание
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
		//	tr1.addContent(new Element("td").addContent("-"));
		//	tr1.addContent(new Element("td").addContent("-"));
			
			for (int i = 0; i < 3; i++) {
				tr2 = new Element("tr"); table.addContent(tr2);
				tr2.addContent(new Element("td").addContent(new Element("br")));
				tr2.addContent(new Element("td").addContent(" "));
				tr2.addContent(new Element("td").addContent(" "));
			//	tr2.addContent(new Element("td").addContent("-"));
			//	tr2.addContent(new Element("td").addContent("-"));
			}
			
			table = new Element("table").setAttribute("cellpadding", "0").setAttribute("cellspacing", "0"); frameTd.addContent(table);
			tr1 = new Element("tr"); table.addContent(tr1);
			tr1.addContent(new Element("th").addContent("Основные услуги"));
			tr1.addContent(new Element("td").addContent("Дата включения"));
			tr1.addContent(new Element("td").addContent("Дата выключения"));
			tr1.addContent(new Element("td").addContent("Причина"));
			//tr1.addContent(new Element("td").addContent("-"));
			
			for (int i = 0; i < 3; i++) {
				tr2 = new Element("tr"); table.addContent(tr2);
				tr2.addContent(new Element("td").addContent(new Element("br")));
				tr2.addContent(new Element("td").addContent(" "));
				tr2.addContent(new Element("td").addContent(" "));
				tr2.addContent(new Element("td").addContent(" "));
				//tr2.addContent(new Element("td").addContent("-"));
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
		
		Cabinet cab = (Cabinet)sys.cbc.getElement(cable.getTo());
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
			cablesTableTr3.addContent(new Element("td").addContent(cable.getStatusMnemonic()));
			cablesTableTr3.addContent(new Element("td").addContent(cable.getYear()));
			cablesTableTr3.addContent(new Element("td").addContent("-"));
			cablesTableTr3.addContent(new Element("td").addContent("-"));
			cablesTableTr3.addContent(new Element("td").addContent(cable.getLenght().toString()));
			cablesTableTr3.addContent(new Element("td").addContent(cab.toString()));
			
			
			//генерируем ячейку с поврежденными парами
			Iterator<AbstractElement> pairIterator = sys.pc.sortByIdUp(sys.pc.getInCable(cable, 2)).iterator();
			String s = "";
			while (pairIterator.hasNext()) {
				s = s  + ((Pair)pairIterator.next()).getNumberInCable() + ",";
			}
			cablesTableTr3.addContent(new Element("td").addContent(s));
			
			//генерируем строки с учасками канализации
			Iterator<?> i = sys.tuc.sortByIdUp(sys.tuc.getTubesByCable(cable)).iterator();
			while (i.hasNext()){
				
				Duct d = (Duct)sys.duc.getElement(((Tube)i.next()).getDuct());
				Manhole m1 = (Manhole)sys.mc.getElement(d.getFrom());
				Manhole m2 = (Manhole)sys.mc.getElement(d.getTo());
				
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
				
				Pair p = sys.pc.getInPlace(cable, n);
				if (p != null) {
					
					Iterator<Path> pi = sys.phc.getPairsPath(p).iterator();
					
					while (pi.hasNext()) {
						Path path = pi.next();
						td1.addContent(((Subscriber)sys.sc.getElement(path.getSubscriber())).toString() +", " + path.getName()).addContent(new Element("br"));
												
						if (path.isicPair()) {
							Iterator <Integer> m = path.geticPair().iterator();
							
							while(m.hasNext()) {
								Pair icPair = (Pair)sys.pc.getElement(m.next());
								Cable dcable = (Cable)sys.cc.getElement(icPair.getCable());
								if (dcable.getFrom().equals(cab.getId()) || dcable.getTo().equals(cab.getId()))
									td3.addContent(icPair.toString()).addContent(new Element("br"));
								
							}
						}
						
						if (path.isdbPair()) {
							Pair dbPair = (Pair)sys.pc.getElement(path.getdbPair());
							Cable dcable = (Cable)sys.cc.getElement(dbPair.getCable());
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
		
		Cabinet cab = (Cabinet)sys.cbc.getElement(cable.getTo());
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
			
				Pair p = sys.pc.getInPlace(cable, n);
				if (p != null) {
				
					Iterator<Path> pi = sys.phc.getPairsPath(p).iterator();
				
					while (pi.hasNext()) {
						Path path = pi.next();
						td1.addContent(((Subscriber)sys.sc.getElement(path.getSubscriber())).toString() +", " + path.getName()).addContent(new Element("br"));
											
						if (path.isicPair()) {
							Iterator <Integer> m = path.geticPair().iterator();
						
							while(m.hasNext()) {
								Pair icPair = (Pair)sys.pc.getElement(m.next());
								Cable dcable = (Cable)sys.cc.getElement(icPair.getCable());
								if (!dcable.getId().equals(cable.getId()) &&  (dcable.getFrom().equals(cab.getId()) || dcable.getTo().equals(cab.getId())))
									td3.addContent(icPair.toString()).addContent(new Element("br"));
							}
						}
					
						if (path.isdbPair()) {
							Pair dbPair = (Pair)sys.pc.getElement(path.getdbPair());
							Cable dcable = (Cable)sys.cc.getElement(dbPair.getCable());
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
			
			/*
			 * Заголовок таблицы для паспорта
			 */
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
			/*
			 *-----------------------------------------------------------
			 */
			
			frameTd.addContent(new Element("br")); frameTd.addContent(new Element("br"));
			
			/*
			 * Заголовок таблицы технического состояния
			 */
			frameTd.addContent(new Element("h3").addContent("Отметки о техническом состоянии"));
			Element techTable = new Element("table").setAttribute("cellpadding", "0").setAttribute("cellspacing", "0"); 
			Element techTableTr1 = new Element("tr"); techTable.addContent(techTableTr1);
			techTableTr1.addContent(new Element("th").addContent("№ п/п"));
			techTableTr1.addContent(new Element("th").addContent("Участок"));
			techTableTr1.addContent(new Element("th").addContent("Дата обнаружения повреждения"));
			techTableTr1.addContent(new Element("th").addContent("Характер повреждения"));
			techTableTr1.addContent(new Element("th").addContent("Описание работ по устранению"));
			techTableTr1.addContent(new Element("th").addContent("Дата устранения"));
			
			/*
			 *-----------------------------------------------------------
			 */
			
			Integer n = 1;
			Iterator<Duct> i = ductSet.iterator();
			while (i.hasNext()) {
				Duct duct = i.next();
				/*
				 * Заполняем таблицу повреждений
				 */
				Integer z = 1;
				Iterator <Damage> d = sys.dmc.getDamages(duct).iterator();
				while (d.hasNext()) {
					Damage damage = d.next();
					Element techTableTr = new Element("tr"); techTable.addContent(techTableTr);
					techTableTr.addContent(new Element("td").addContent(z.toString()));
					techTableTr.addContent(new Element("td").addContent(duct.toString()));
					techTableTr.addContent(new Element("td").addContent(damage.getOpenDate()));
					techTableTr.addContent(new Element("td").addContent(damage.getName()));
					techTableTr.addContent(new Element("td").addContent(damage.getDescription()));
					techTableTr.addContent(new Element("td").addContent(damage.getCloseDate()));
					z++;
				}
				/*
				 * -----------------------------
				 */
				HashSet<Tube> h = sys.tuc.getDuctsTubes(duct);
				AbstractElement from = sys.mc.getElement(duct.getFrom());
				AbstractElement to = sys.mc.getElement(duct.getTo());
				AbstractElement tob = sys.buc.getElement(duct.getTo());
				
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
			
			frameTd.addContent(techTable);
			
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
			cablesTableTr1.addContent(new Element("th").addContent("Марка"));
			cablesTableTr1.addContent(new Element("th").addContent("№ канала приходящего"));
			cablesTableTr1.addContent(new Element("th").addContent("№ канала уходящего"));
			cablesTableTr1.addContent(new Element("th").addContent("Расстояние до следующего колодца, м"));
			
			HashSet<Duct> ductInSet = sys.duc.getInDuct(man);
			HashSet<Duct> ductOutSet = sys.duc.getOutDuct(man);
			
			Iterator <Duct> d = ductInSet.iterator();
			while (d.hasNext()) {
				Duct duct = d.next();
				HashSet<Tube> tubeSet = sys.tuc.getDuctsTubes(duct);
				Iterator<Tube> t = tubeSet.iterator();
				while (t.hasNext()) {
					Tube tube = t.next();
					Vector<Cable> cableSet = sys.cc.getTubesCables(tube);
					Iterator<Cable> c = cableSet.iterator();
					while (c.hasNext()) {
						Cable cable = c.next();
						Element cablesTableTr = new Element("tr"); cablesTable.addContent(cablesTableTr);
						cablesTableTr.addContent(new Element("td").addContent(cable.toLongString()));
						cablesTableTr.addContent(new Element("td").addContent(cable.getLabel()+"х"+cable.getCapacity()+"*"+cable.getWireDiametr()));
						cablesTableTr.addContent(new Element("td").addContent(duct.toString()+", "+tube.toString()));
						
						Tube outTube = null;
						Duct outDuct = null;
						Iterator<Duct> o = ductOutSet.iterator();
						while (o.hasNext()) {
							Duct od = o.next();
							outTube = sys.tuc.getTubeBuCableDuct(cable, od);
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
			Building building = (Building)sys.buc.getElement(duct.getTo());
			frameTd.addContent(new Element("h4").addContent("Адрес: " + building.toString()));
			frameTd.addContent(new Element("h4").addContent("Способ изготовления ввода: " + duct.getМanufacturingМethod()));
			
			frameTd.addContent(new Element("br")); frameTd.addContent(new Element("br"));
			
			Element cablesTable = new Element("table").setAttribute("cellpadding", "0").setAttribute("cellspacing", "0"); frameTd.addContent(cablesTable);
			Element cablesTableTr1 = new Element("tr"); cablesTable.addContent(cablesTableTr1);
			cablesTableTr1.addContent(new Element("th").addContent("№ распределительной коробки"));
			cablesTableTr1.addContent(new Element("th").addContent("Длина кабеля, м"));
			cablesTableTr1.addContent(new Element("th").addContent("Диаметр жил"));
			cablesTableTr1.addContent(new Element("th").addContent("№ шкафа"));
			
			HashSet<DBox> hdbox= sys.dbc.getInBuilding(building);
			
			Iterator <AbstractElement> i = sys.tuc.sortByIdUp(sys.tuc.getDuctsTubes(duct)).iterator();
			while (i.hasNext()) {
				
				Tube tube = (Tube)i.next();
				
				Iterator <Integer> k = tube.getCables().iterator();
				while (k.hasNext()) {
					Cable cable = (Cable)sys.cc.getElement(k.next());
					
					if (cable != null) {
						
						Element cablesTableTr = new Element("tr"); cablesTable.addContent(cablesTableTr);
						
						DBox dbox  = null;
						
						Iterator <DBox> d = hdbox.iterator();
						while (d.hasNext()) {
							DBox db = (DBox)d.next();
							Pair p = sys.pc.getInPlace(db, 0);
							
							if (cable.getId().equals(p.getCable())) dbox = db;
						}
						
						if (dbox != null) { cablesTableTr.addContent(new Element("td").addContent(dbox.toString()));}
						else { cablesTableTr.addContent(new Element("td").addContent("-")); }
						
						
						cablesTableTr.addContent(new Element("td").addContent(cable.getLenght().toString()));
						cablesTableTr.addContent(new Element("td").addContent(cable.getWireDiametr().toString()));
						
						Cabinet cab = (Cabinet)sys.cbc.getElement(cable.getFrom());
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
	public String createDBoxPassport(Vector<DBox> dbv) {
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
			
			Iterator <DBox> i = dbv.iterator();
			
			while (i.hasNext()) {
				DBox dbox = i.next();
				Building build = (Building)sys.buc.getElement(dbox.getBuilding());
				Pair p = sys.pc.getInPlace(dbox, 0);
				
				Cable cable = null;
				if (p != null)  cable = (Cable)sys.cc.getElement(p.getCable());
				
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