package org.teleline.gui;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import org.teleline.model.AbstractElement;
import org.teleline.model.DFramе;
import org.teleline.model.Sys;

public class FormDFrames extends FormAbstractElements {

	public FormDFrames(final Sys iSys, Collection<AbstractElement> collection) {
		super(iSys, collection);
		
		iFrame.setTitle("Редактировать кросс");
		tableLabel.setText("Список кроссов");
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
	public void passport() {
		
		util_viewPassport(iSys.rw.createDFramePassport((DFramе)tableModel.getValueAt(selectedIndex, 0)));	
	}
	public void create() {
		new FormDFrame(iSys, null);
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
	public void edit() {
		new FormDFrame(iSys, (DFramе)tableModel.getValueAt(selectedIndex, 0));
	}
	
	public void view() {
		new FormViewDFrame(iSys,(DFramе)tableModel.getValueAt(selectedIndex, 0));
	}
	
	
	private void addDFrameToTable(DFramе dframe) {
		
		Vector<Object> v = new Vector<Object>();
		v.add(dframe);
		v.add(iSys.fc.getInOwner(dframe.getId()).size());
		tableModel.addRow(v);
		
	}
}