package org.teleline.gui;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JOptionPane;

import org.teleline.model.AbstractElement;
import org.teleline.model.Cable;
import org.teleline.system.Sys;


public class FormCables extends FormAbstractElements {

	public FormCables(Sys iSys, Collection<AbstractElement> collection) {
		super(iSys, collection);
		iFrame.setTitle("Редактировать кабель");
		tableModel.setColumnIdentifiers(new String[]{"Кабель","От","До","Емкость","Исп.емкость"});
		table.getColumnModel().getColumn(0).setMaxWidth(70);
		table.getColumnModel().getColumn(0).setPreferredWidth(70);
		table.getColumnModel().getColumn(1).setMaxWidth(70);
		table.getColumnModel().getColumn(1).setPreferredWidth(70);
		table.getColumnModel().getColumn(3).setMaxWidth(75);
		table.getColumnModel().getColumn(3).setPreferredWidth(75);
		table.getColumnModel().getColumn(4).setMaxWidth(90);
		table.getColumnModel().getColumn(4).setPreferredWidth(90);
		enableSort();
		errMsg = "Кабель не выбран!";
		Iterator<AbstractElement> i = collection.iterator();
		while (i.hasNext()) {addCableToTable((Cable)i.next());}
		iFrame.setVisible(true);
	}
	
	public void refresh() {
		Iterator<AbstractElement> i = iSys.cc.getIterator();
		while (i.hasNext()) {addCableToTable((Cable)i.next());}
	}
	
	public void create() {
		new FormCable(iSys, null);
	}
	
	public void edit() {
		new FormCable(iSys, (Cable)tableModel.getValueAt(selectedIndex, 0));
	}
	public void passport() {
		
		Cable cable = (Cable)tableModel.getValueAt(selectedIndex, 0);
		if (cable.getType() == 2) {util_newError("Паспорт для распределительного кабеля создается в составе паспорта шкафа."); return;}
		if (cable.getType() == 0) {util_viewPassport(iSys.rw.createМCablePassport(cable)); return;}
		if (cable.getType() == 1) {util_viewPassport(iSys.rw.createIcCablePassport(cable)); return;}
		if (cable.getType() == 3) {return;}
		
	}
	public void view() {
		new FormViewCable(iSys,(Cable)tableModel.getValueAt(selectedIndex, 0),null);
		
	}
	public void delete() {
		
		Cable cable = (Cable)tableModel.getValueAt(selectedIndex, 0);
		
		if (util_newDialog("Удалить кабель: " + cable.toString()+" и все содержащиеся в нем пары?") == JOptionPane.YES_OPTION) {
			iSys.removeCable(cable);
			util_newInfo("Кабель "+cable.toString()+" и все содержащиеся в нем пары удалены");
			tableModel.removeRow(selectedIndex);	
		}
	}
	
	private void addCableToTable(Cable cable) {
		
		Vector<Object> v = new Vector<Object>();
		v.add(cable);
		v.add(cable.getFromElement());
		if (cable.getType() < 2)  {
			v.add(cable.getToElement());
		}
		else {
			v.add(cable.getToDBoxes());
		}
		v.add(cable.getCapacity());
		v.add(cable.getUsedCapacity());
	//	v.add(cable.getLenght());
		tableModel.addRow(v);
	}
	
}