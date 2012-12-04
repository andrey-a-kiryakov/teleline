package org.teleline.gui;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import org.teleline.model.AbstractElement;
import org.teleline.model.Duct;
import org.teleline.model.Sys;

public class FormDucts extends FormAbstractElements {

	public FormDucts(final Sys iSys, Collection<AbstractElement> collection) {
		super(iSys, collection);
		iFrame.setTitle("Редактировать канализацию");
		tableLabel.setText("Список участков канализации");
		tableModel.setColumnIdentifiers(new String[]{"Участок", "Каналов"});
		enableSort();
		Iterator<AbstractElement> i = collection.iterator();
		errMsg = "Участок канализации не выбран!";
		while (i.hasNext()) {addDuctToTable((Duct)i.next());}	
		iFrame.setVisible(true);
	}
	
	private void addDuctToTable(Duct duct) {
		
		Vector<Object> v = new Vector<Object>();
		v.add(duct);
		v.add(iSys.tuc.getDuctsTubes(duct).size());
		v.add("");
		v.add("");
		tableModel.addRow(v);
	}	
}