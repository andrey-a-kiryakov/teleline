package org.teleline.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JOptionPane;
import org.teleline.model.AbstractElement;
import org.teleline.model.Building;
import org.teleline.model.Duct;
import org.teleline.model.Sys;


public class FormDucts extends FormAbstractElements {

	public FormDucts(final Sys iSys, Collection<AbstractElement> collection) {
		super(iSys, collection);
		iFrame.setTitle("Редактировать канализацию");
		tableLabel.setText("Список участков канализации");
		tableModel.setColumnIdentifiers(new String[]{"Участок", "Каналов","Длина"});
		enableSort();
		Iterator<AbstractElement> i = collection.iterator();
		errMsg = "Участок канализации не выбран!";
		while (i.hasNext()) {addDuctToTable((Duct)i.next());}	
		iFrame.setVisible(true);
	}
	
	public void refresh() {
		Iterator<AbstractElement> i = iSys.duc.getIterator();
		while (i.hasNext()) {addDuctToTable((Duct)i.next());}	
	}
	
	public void passport() {
		
		Duct duct = (Duct)tableModel.getValueAt(selectedIndex, 0);
		Building building = (Building)iSys.buc.getElement(duct.getTo());
		if (building != null)  { util_viewPassport(iSys.rw.createCableglandPassport(duct)); return;}
		
		final FormDuctsSet form = new FormDuctsSet(iSys);
		form.okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Vector<Duct> d = new Vector<Duct>();
				if (form.setListModel.getSize() > 0) { 
				for (int i = 0; i < form.setListModel.getSize(); i++) 
					d.add((Duct)form.setListModel.getElementAt(i));
				}
				//form.iFrame.dispose();
				if (d.size() > 0) {
				util_viewPassport(iSys.rw.createDuctPassport(d));
				}	
			}
		});
		//Vector<Duct> v = GUI.formCreateDuctsSet((Net)iSys.nc.getOnlyElement());
		
	}
	
	public void create() {
		new FormDuct(iSys, null);
	}
	
	public void delete() {
		Duct duct = (Duct)tableModel.getValueAt(selectedIndex, 0);
		String ductName = duct.toString();
		int n = util_newDialog("Удалить участок канализации: " + ductName + "?");
		if (n == JOptionPane.YES_OPTION) {
			iSys.removeDuct(duct);
			util_newInfo("Участок канализации:" + ductName + " удален");
			tableModel.removeRow(selectedIndex);	
		}	
	}
	
	public void edit() {
		new FormDuct(iSys, (Duct)tableModel.getValueAt(selectedIndex, 0));
	}
	
	public void view() {
		new FormViewDuct(iSys,(Duct)tableModel.getValueAt(selectedIndex, 0));
	}
	
	private void addDuctToTable(Duct duct) {
		
		Vector<Object> v = new Vector<Object>();
		v.add(duct);
		v.add(iSys.tuc.getDuctsTubes(duct).size());
		v.add(duct.getLenght());
		v.add("");
		tableModel.addRow(v);
	}	
}