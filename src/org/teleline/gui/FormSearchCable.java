package org.teleline.gui;

import java.util.Iterator;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.teleline.model.Cable;
import org.teleline.model.CableCollection;
import org.teleline.model.StructuredElement;
import org.teleline.model.Sys;

public class FormSearchCable extends Form {
	
	public JTable cableTable;
	public JButton okButton;
	
	public FormSearchCable(Sys iSys, Integer netId) {
		super(iSys);
		
		createDialog("Выбрать кабель", 685, 600);
		
		cableTable = addTable(10, 10, 520, 540);
		final DefaultTableModel tableModel = (DefaultTableModel) cableTable.getModel();
		tableModel.setColumnIdentifiers(new String[]{"Кабель","От","До","Емкость","Исп.емкость","Длина"});
		util_clearTable(cableTable);
		Iterator<StructuredElement> i = iSys.cc.getInNet(netId).iterator();
		while (i.hasNext()) { addCableToTable((Cable)i.next()); }		
		okButton = addButton("Выбрать", 540, 10, 125, 26);
		iFrame.setVisible(true);
	
	}
	
	private void addCableToTable(Cable cable){
		
		Vector<Object> v = new Vector<Object>();
		v.add(cable);
		v.add(cable.getFromElement());
		v.add(cable.getToElement());
		v.add(cable.getCapacity());
		v.add(cable.getUsedCapacity());
		
		v.add(cable.getLenght());
		((DefaultTableModel) cableTable.getModel()).addRow(v);
	}
		
}