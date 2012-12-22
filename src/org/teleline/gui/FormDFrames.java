package org.teleline.gui;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import org.teleline.model.AbstractElement;
import org.teleline.model.DFramе;
import org.teleline.model.Wrapper;
import org.teleline.system.Sys;


public class FormDFrames extends FormAbstractElements {

	public FormDFrames(final Sys iSys, Collection<AbstractElement> collection) {
		super(iSys, collection);
		
		iFrame.setTitle("Редактировать кросс");
		tableModel.setColumnIdentifiers(new String[]{"Кросс", "Громполос"});
		enableSort();
		Iterator<AbstractElement> i = collection.iterator();
		errMsg = "Кросс не выбран!";
		while (i.hasNext()) {addDFrameToTable((DFramе)i.next());}	
		iFrame.setVisible(true);
		}
	
	public void refresh() {
		
		util_clearTable(table);
		Iterator<AbstractElement> i = iSys.dfc.getIterator();
		while (i.hasNext()) {addDFrameToTable((DFramе)i.next());}	
	}
	
	public void create() {
		
		
		Wrapper wrapper = new Wrapper();
		new FormDFrame(iFrame, iSys, null, wrapper).iDialog.setVisible(true);
		
		if (wrapper.getElement() != null) {
			addDFrameToTable((DFramе)wrapper.getElement());
			util_scrollTable(table,wrapper.getElement());
		}
	}
	
	public void delete() {
		DFramе dframe = (DFramе)tableModel.getValueAt(selectedIndex, 0);
		String dframeName = dframe.toString();
		int n = util_newDialog("Удалить кросс: " + dframeName+" и все его содержимое?");
		if (n == JOptionPane.YES_OPTION) {
			iSys.removeDFrame(dframe);
			util_newInfo("Кросс " + dframeName+" и все его содержимое удалены");
			((DefaultTableModel) table.getModel()).removeRow(selectedIndex);	
		}	
	}
	
	private void addDFrameToTable(DFramе dframe) {
		
		Vector<Object> v = new Vector<Object>();
		v.add(dframe);
		v.add(iSys.fc.getInOwner(dframe.getId()).size());
		tableModel.addRow(v);
		
	}
	
	public void updateRow(AbstractElement element, Integer row) {	
		
		tableModel.setValueAt(element, row, 0);
		tableModel.setValueAt(iSys.fc.getInOwner(element.getId()).size(), row, 1);
	}
}