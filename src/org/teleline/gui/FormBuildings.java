package org.teleline.gui;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import org.teleline.model.AbstractElement;
import org.teleline.model.Building;
import org.teleline.model.Sys;

public class FormBuildings extends FormAbstractElements {

	public FormBuildings(Sys iSys, Collection<AbstractElement> collection) {
		super(iSys, collection);
		iFrame.setTitle("Редактировать здание");
		tableLabel.setText("Список зданий");
		tableModel.setColumnIdentifiers(new String[]{"Здание"});
		enableSort();
		Iterator<AbstractElement> i = collection.iterator();
		errMsg = "Здание не выбрано!";
		while (i.hasNext()) {addBuildingToTable((Building)i.next());}
		passportButton.setEnabled(false);
		viewButton.setEnabled(false);
		iFrame.setVisible(true);
	}
	
	public void refresh() {
		Iterator<AbstractElement> i = iSys.buc.getIterator();
		while (i.hasNext()) {addBuildingToTable((Building)i.next());}	
	}
	
	public void create() {
		new FormBuilding(iSys, null);
	}
	public void delete() {
		Building building = (Building)tableModel.getValueAt(selectedIndex, 0);
		String buildingName = building.toString();
		int n = util_newDialog("Удалить здание: " + buildingName +"?");
		if (n == JOptionPane.YES_OPTION) {
			iSys.removeBuilding(building);
			util_newInfo("Здание удалено");
			((DefaultTableModel) table.getModel()).removeRow(selectedIndex);	
		}	
	}
	public void edit() {
		new FormBuilding(iSys, (Building)tableModel.getValueAt(selectedIndex, 0));
	}
	
	
	private void addBuildingToTable(Building building) {
		
		Vector<Object> v = new Vector<Object>();
		v.add(building);
		tableModel.addRow(v);
		
	}
	
}