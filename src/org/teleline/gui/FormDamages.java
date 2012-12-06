package org.teleline.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SortOrder;
import javax.swing.RowSorter.SortKey;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.teleline.model.AbstractElement;
import org.teleline.model.Damage;

import system.Sys;

public class FormDamages extends Form {
	
	public JTable damageTable;
	
	public FormDamages(final Sys iSys, Collection<Damage> damageCollection, final AbstractElement owner) {
		super(iSys);
		createDialog("Список повреждений " + owner.toString(), 685, 600);
		iFrame.setResizable(true);
		
		damageTable = addTable(10, 10, 520, 560);
		final DefaultTableModel tableModel = (DefaultTableModel) damageTable.getModel();
		tableModel.setColumnIdentifiers(new String[]{"Дата обнаружения","Дата устраннеия","Характер повреждения"});
		damageTable.getColumnModel().getColumn(0).setMaxWidth(130);
		damageTable.getColumnModel().getColumn(0).setPreferredWidth(130);
		damageTable.getColumnModel().getColumn(1).setMaxWidth(130);
		damageTable.getColumnModel().getColumn(1).setPreferredWidth(130);
		final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(damageTable.getModel());
		damageTable.setRowSorter(sorter);
		
		JButton refreshButton = addButton("Обновить", 540, 10, 125, 26);
		JButton editButton = addButton("Редактировать", 540, 60, 125, 26);
		JButton createButton = addButton("Добавить", 540, 110, 125, 26);
		JButton deleteButton = addButton("Удалить", 540, 150, 125, 26);
		
		Iterator<Damage> i = damageCollection.iterator();
		while (i.hasNext()) {addDamageToTable(i.next());}
		
		refreshButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				util_clearTable(damageTable);
				Iterator<Damage> i = iSys.dmc.getDamages(owner).iterator();
				while (i.hasNext()) {addDamageToTable(i.next());}
			}
		});
		/*
		 * Событие кнопки редактирования повреждения
		 */
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (damageTable.getSelectionModel().isSelectionEmpty()){ util_newError("Повреждение не выбрано!"); return; }
				int selectedIndex = damageTable.getRowSorter().convertRowIndexToModel(damageTable.getSelectionModel().getMinSelectionIndex());
				//Damage damage = (Damage)tableModel.getValueAt(selectedIndex, 0);
				new FormDamage(iSys,(Damage)tableModel.getValueAt(selectedIndex, 2),owner);
				//updateDamageInTable(damage, selectedIndex);
			}
		});
		/*
		 * ---------------------------------------------------------
		 */
		/*
		 * Событие кнопки создания повреждения
		 */
		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new FormDamage(iSys, null, owner);
			/*	if (damage != null) {
					damage.attachTo(owner);
					addDamageToTable(damage);
				}*/
			}
		});
		/*
		 * ---------------------------------------------------------
		 */
		/*
		 * Событие кнопки удаления повреждения
		 */
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if (damageTable.getSelectionModel().isSelectionEmpty()){ util_newError("Повреждение не выбрано!"); return; }
				int selectedIndex = damageTable.getRowSorter().convertRowIndexToModel(damageTable.getSelectionModel().getMinSelectionIndex());
				Damage damage = (Damage)tableModel.getValueAt( selectedIndex, 0);
				
				int n = util_newDialog("Удалить повреждение: " + damage.toString()+" ?");
				if (n == JOptionPane.YES_OPTION) {
					iSys.dmc.removeElement(damage);
					String mes = "Повреждение " + damage.toString() + " удалено";
					iSys.rw.addLogMessage(mes);
					((DefaultTableModel) damageTable.getModel()).removeRow(selectedIndex);
					util_newInfo(mes);
				}	
			}
		});
		/*
		 * ---------------------------------------------------------
		 */
		ArrayList<SortKey> keys=new ArrayList<SortKey>();
        keys.add(new SortKey(0, SortOrder.ASCENDING));                                             
        sorter.setSortKeys(keys);
        sorter.setSortsOnUpdates(true);
        
		iFrame.setVisible(true);

	}
	
	/**
	 * Добавляет повреждение в таблицу
	 * @param damage - повреждение
	 */
	public void addDamageToTable(Damage damage){
		Vector<Object> v = new Vector<Object>();
		v.add(damage.getOpenDate());
		v.add(damage.getCloseDate());
		v.add(damage);
		((DefaultTableModel) damageTable.getModel()).addRow(v);
	}
	/**
	 * Обновляет строчку с повреждением в таблице
	 * @param damage - повреждение
	 * @param index - позиция обновляемой строки в таблице
	 */
	public void updateDamageInTable(Damage damage, Integer index) {
		DefaultTableModel tableModel = (DefaultTableModel) damageTable.getModel();
		tableModel.setValueAt(damage.getOpenDate(), index, 0);
		tableModel.setValueAt(damage.getCloseDate(), index, 1);
		tableModel.setValueAt(damage, index, 2);
	}
}