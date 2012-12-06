package org.teleline.gui;

import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.teleline.model.Sys;


public class FormStatisticCommon extends Form {
	
	private JTable table;
	private DefaultTableModel tableModel;
	
	public FormStatisticCommon(Sys iSys) {
		super(iSys);
		
		createDialog("Общая статистика", 500, 600);
		table = addTable(10,10,480,560);
		tableModel = (DefaultTableModel) table.getModel();
		tableModel.setColumnIdentifiers(new String[]{"Параметр","Значение"});
		
		getStatistic();
		iFrame.setVisible(true);
	}
	
	private void getStatistic() {
		
		Vector<Object> v = new Vector<Object>();
		v.add("Сеть"); v.add(iSys.nc.getOnlyElement().toString());
		tableModel.addRow(v);
		
		v = new Vector<Object>();
		v.add("Кроссов"); v.add(iSys.dfc.getSize());
		tableModel.addRow(v);
		
		v = new Vector<Object>();
		v.add("Шкафов"); v.add(iSys.cbc.getSize());
		tableModel.addRow(v);
		
		v = new Vector<Object>();
		v.add("КРТ"); v.add(iSys.dbc.getSize());
		tableModel.addRow(v);
		
		v = new Vector<Object>();
		v.add("Зданий"); v.add(iSys.buc.getSize());
		tableModel.addRow(v);
		
		v = new Vector<Object>();
		v.add("Колодцев"); v.add(iSys.mc.getSize());
		tableModel.addRow(v);
		
		v = new Vector<Object>();
		v.add("Участков канализации"); v.add(iSys.duc.getSize());
		tableModel.addRow(v);
		
		v = new Vector<Object>();
		v.add("Каналов канализации"); v.add(iSys.tuc.getSize());
		tableModel.addRow(v);
		
		v = new Vector<Object>();
		v.add("Кабелей"); v.add(iSys.cc.getSize());
		tableModel.addRow(v);
		
		v = new Vector<Object>();
		v.add("Громполоc"); v.add(iSys.fc.getSize());
		tableModel.addRow(v);
		
		v = new Vector<Object>();
		v.add("Боксов"); v.add(iSys.bc.getSize());
		tableModel.addRow(v);
		
		v = new Vector<Object>();
		v.add("Включений"); v.add(iSys.phc.getSize());
		tableModel.addRow(v);
		
		v = new Vector<Object>();
		v.add("Пар"); v.add(iSys.pc.getSize());
		tableModel.addRow(v);
		
		v = new Vector<Object>();
		v.add("Абонентов"); v.add(iSys.sc.getSize());
		tableModel.addRow(v);
		
		v = new Vector<Object>();
		v.add("Повреждений"); v.add(iSys.dmc.getSize());
		tableModel.addRow(v);	
	}
}