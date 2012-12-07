package org.teleline.gui;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.teleline.model.AbstractElement;
import org.teleline.model.DBox;
import org.teleline.model.Pair;
import org.teleline.system.Sys;

public class FormDBoxes extends FormAbstractElements {
	
	public FormDBoxes(final Sys iSys, Collection<AbstractElement> collection) {
		super(iSys, collection);
		
		iFrame.setTitle("Редактировать КРТ");
		tableLabel.setText("Список коробок");
		tableModel.setColumnIdentifiers(new String[]{"Коробка", "Кабель", "Адрес"});
		table.getColumnModel().getColumn(0).setMaxWidth(100);
		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setMaxWidth(100);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		enableSort();
		errMsg = "Коробка не выбрана!";
		passportCheck = false;
		Iterator<AbstractElement> i = collection.iterator();
		while(i.hasNext()) {addDBoxToTable((DBox)i.next());}
		table.setRowSorter(sorter);
		iFrame.setVisible(true);
	}		
			
	public void passport() {
		
		Vector<DBox> db = new Vector<DBox>();
		for (int i = 0; i < tableModel.getRowCount(); i++) {
				db.add((DBox) tableModel.getValueAt(sorter.convertRowIndexToModel(i), 0));	
			}				
		util_viewPassport(iSys.rw.createDBoxPassport(db));
	}
		
		
	public void edit() {
		new FormDBox(iSys,(DBox) tableModel.getValueAt(selectedIndex, 0));
	}
		
	public void view() {
		new FormViewDBox(iSys, (DBox)tableModel.getValueAt( selectedIndex, 0));
	}

	public void create() {
		new FormDBox(iSys, null);
	}
		
	public void delete() {
		DBox dbox = (DBox)tableModel.getValueAt( selectedIndex, 0);
		String dboxName = dbox.toString();
		if (util_newDialog("Удалить коробку: " + dboxName+" и все содержащиеся в ней пары?") == JOptionPane.YES_OPTION) {
			iSys.removeDBox(dbox);
				util_newInfo("Коробка " + dboxName+" и все содержащиеся в ней пары удалены");
				((DefaultTableModel) table.getModel()).removeRow(selectedIndex);	
			}	
	}
	
	
	
	private void addDBoxToTable(DBox dbox) {
		
		Vector<Object> v = new Vector<Object>();
		v.add(dbox);
		
		Pair p = iSys.pc.getInPlace(dbox, 0);
		if (p != null) {
			v.add(iSys.cc.getElement(p.getCable()));
		}
		else {v.add("");}
		
		v.add(iSys.buc.getElement(dbox.getBuilding()));
		tableModel.addRow(v);
	}
}
