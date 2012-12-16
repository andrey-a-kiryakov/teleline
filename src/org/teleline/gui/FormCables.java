package org.teleline.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.teleline.model.AbstractElement;
import org.teleline.model.Cable;
import org.teleline.system.Sys;


public class FormCables extends FormAbstractElements {
	
	public JComboBox cableTypeComboBox;
	
	public FormCables(Sys iSys, final Collection<AbstractElement> collection) {
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
		
		
		buttonPanel.add(new JLabel("Тип кабеля"), "cell 0 6");
		cableTypeComboBox = new JComboBox();
		buttonPanel.add(cableTypeComboBox, "cell 0 7");
		cableTypeComboBox.addItem("Все");
		cableTypeComboBox.addItem("Магистр.");
		cableTypeComboBox.addItem("Межшкаф.");
		cableTypeComboBox.addItem("Распред.");
		cableTypeComboBox.addItem("ПП.");
		cableTypeComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				util_clearTable(table);
				
				if (cableTypeComboBox.getSelectedIndex() == 0) {
					Iterator<AbstractElement> i = collection.iterator();
					while (i.hasNext()) {addCableToTable((Cable)i.next());}
				}
				
				if (cableTypeComboBox.getSelectedIndex() > 0) {
					Iterator<AbstractElement> i = collection.iterator();
					while (i.hasNext()) {
						Cable cable = (Cable)i.next();
						if (cable.getType().equals(cableTypeComboBox.getSelectedIndex() - 1))
						addCableToTable(cable);}
				}
			}
		});
		
		enableSort();
		errMsg = "Кабель не выбран!";
		Iterator<AbstractElement> i = collection.iterator();
		while (i.hasNext()) {addCableToTable((Cable)i.next());}
		iFrame.setVisible(true);
	}
	
	
	public void refresh() {
//		Iterator<AbstractElement> i = iSys.cc.getIterator();
//		while (i.hasNext()) {addCableToTable((Cable)i.next());}
	}
	
	public void create() {
		new FormCable(iSys, null);
	}
	
	public void edit() {
		new FormCable(iSys, (Cable)tableModel.getValueAt(selectedIndex, 0));
	}
	public void passport() {
		Cable cable = (Cable)tableModel.getValueAt(selectedIndex, 0);
		if (cable.getType() > 0) {util_viewPassport(iSys.rw.createСableLoadList(cable)); return;}
		if (cable.getType() == 0) {util_viewPassport(iSys.rw.createМCablePassport(cable)); return;}
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
	
	void addCableToTable(Cable cable) {
		
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