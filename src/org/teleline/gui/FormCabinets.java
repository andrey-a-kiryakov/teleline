package org.teleline.gui;

import java.util.Collection;

import java.util.Iterator;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


import org.teleline.model.AbstractElement;
import org.teleline.model.Cabinet;

import org.teleline.model.Sys;

public class FormCabinets extends FormAbstractElements {
	
	public FormCabinets(final Sys iSys, Collection<AbstractElement> collection) {
		super(iSys, collection);
		
		iFrame.setTitle("Редактировать шкаф");
		tableLabel.setText("Список шкафов");
		tableModel.setColumnIdentifiers(new String[]{"Шкаф", "Класс", "Мест", "Адрес"});
		table.getColumnModel().getColumn(0).setMaxWidth(100);
		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setMaxWidth(60);
		table.getColumnModel().getColumn(1).setPreferredWidth(60);
		table.getColumnModel().getColumn(2).setMaxWidth(60);
		table.getColumnModel().getColumn(2).setPreferredWidth(60);
		enableSort();
		errMsg = "Шкаф не выбран!";
		Iterator<AbstractElement> i = collection.iterator();
		while(i.hasNext()) {addCabinetToTable((Cabinet)i.next());}
		table.setRowSorter(sorter);
		iFrame.setVisible(true);
	}
		
	public void passport() {
		util_viewPassport(iSys.rw.createCabinetPassport((Cabinet) tableModel.getValueAt(selectedIndex, 0)));
	}
		
	public void refresh() {
		Iterator<AbstractElement> i = iSys.cbc.getIterator();
		while(i.hasNext()) {addCabinetToTable((Cabinet)i.next());}
	}
		
	public void edit() {
		new FormCabinet(iSys,(Cabinet) tableModel.getValueAt(selectedIndex, 0));
	}
	
	public void view() {
		new FormViewCabinet(iSys,(Cabinet)tableModel.getValueAt(selectedIndex, 0));
	}
		
	public void create() {
		new FormCabinet(iSys, null);
	}
		
	public void delete() {
				
		Cabinet cab = (Cabinet)tableModel.getValueAt( selectedIndex, 0);
		String cabinetName = cab.toString();
		if (util_newDialog("Удалить шкаф: " + cabinetName+" и все его содержимое?") == JOptionPane.YES_OPTION) {
			iSys.removeCabinet(cab);
			util_newInfo("Шкаф" + cabinetName+" и все его содержимое удалены");
			((DefaultTableModel) table.getModel()).removeRow(selectedIndex);	
			}	
		}
	
	private void addCabinetToTable(Cabinet cab) {	
		Vector<Object> v = new Vector<Object>();
		v.add(cab);
		v.add(cab.getCabinetClass());
		v.add(cab.getPlacesCount());
		v.add(cab.getAdress());
		tableModel.addRow(v);
	}
}