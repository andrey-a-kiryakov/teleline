package org.teleline.gui;

import java.util.Collection;

import java.util.Iterator;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


import org.teleline.model.AbstractElement;
import org.teleline.model.Cabinet;
import org.teleline.model.Wrapper;
import org.teleline.system.Sys;


public class FormCabinets extends FormAbstractElements {
	
	public FormCabinets(final Sys iSys, Collection<AbstractElement> collection) {
		super(iSys, collection);
		
		iFrame.setTitle("Редактировать шкаф");
		tableModel.setColumnIdentifiers(new String[]{"Шкаф", "Класс", "Мест", "Боксов", "Адрес"});
		table.getColumnModel().getColumn(0).setMaxWidth(90);
		table.getColumnModel().getColumn(0).setPreferredWidth(90);
		table.getColumnModel().getColumn(1).setMaxWidth(50);
		table.getColumnModel().getColumn(1).setPreferredWidth(50);
		table.getColumnModel().getColumn(2).setMaxWidth(50);
		table.getColumnModel().getColumn(2).setPreferredWidth(50);
		table.getColumnModel().getColumn(3).setMaxWidth(50);
		table.getColumnModel().getColumn(3).setPreferredWidth(50);
		enableSort();
		errMsg = "Шкаф не выбран!";
		Iterator<AbstractElement> i = collection.iterator();
		while(i.hasNext()) {addCabinetToTable((Cabinet)i.next());}
		table.setRowSorter(sorter);
		iFrame.setVisible(true);
	}
	
	public void refresh() {
		Iterator<AbstractElement> i = iSys.cbc.getIterator();
		while(i.hasNext()) {addCabinetToTable((Cabinet)i.next());}
	}
	
	
	public void create() {
		Wrapper wrapper = new Wrapper();
		new FormCabinet(iFrame, iSys, null, wrapper).iDialog.setVisible(true);
		
		if (wrapper.getElement() != null) {
			addCabinetToTable((Cabinet)wrapper.getElement());
			util_scrollTable(table,wrapper.getElement());
		}
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
		v.add(iSys.bc.getInOwner(cab.getId()).size());
		v.add(cab.getAdress());
		tableModel.addRow(v);
	}
	
	public void updateRow(AbstractElement element, Integer row) {	
		
		tableModel.setValueAt(element, row, 0);
		tableModel.setValueAt(((Cabinet)element).getCabinetClass(), row, 1);
		tableModel.setValueAt(((Cabinet)element).getPlacesCount(), row, 2);
		tableModel.setValueAt(iSys.bc.getInOwner(element.getId()).size(), row, 3);
		tableModel.setValueAt(((Cabinet)element).getAdress(), row, 4);
	}
	
}