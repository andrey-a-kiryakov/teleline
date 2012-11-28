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
import org.teleline.model.Manhole;
import org.teleline.model.Sys;

public class FormManholes extends Form {
	
	final public JTable table;
	public JButton refreshButton;
	
	public FormManholes(final Sys iSys, Collection<AbstractElement> collection) {
		super(iSys);
		
		createDialog("Шкафы", 785, 600);
		
		addLabel("Список колодцев:", 10, 10, 520, 14);
		table = addTable(10, 30, 620, 525);
		final DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		tableModel.setColumnIdentifiers(new String[]{"Колодец","Адрес"});
		table.getColumnModel().getColumn(0).setMaxWidth(100);
		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		//table.getColumnModel().getColumn(1).setMaxWidth(60);
		//table.getColumnModel().getColumn(1).setPreferredWidth(60);
		//table.getColumnModel().getColumn(2).setMaxWidth(60);
		//table.getColumnModel().getColumn(2).setPreferredWidth(60);
		final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
		table.setRowSorter(sorter);
		
		refreshButton = addButton("Обновить", 640, 30, 125, 26);
		JButton editButton = addButton("Редактировать", 640, 105, 125, 26);
		JButton viewButton = addButton("Смотреть", 640, 145, 125, 26);
		JButton passportButton = addButton("Паспорт", 640, 185, 125, 26);
		JButton createButton = addButton("Добавить", 640, 255, 125, 26);
		JButton deleteButton = addButton("Удалить", 640, 295, 125, 26);
		
		/*
		 * Событие кнопки обновления списка
		 */
		refreshButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				util_clearTable(table);
				
				Iterator<AbstractElement> i = iSys.mc.getIterator();
				while(i.hasNext()) {
					Manhole man = (Manhole)i.next();
					Vector<Object> v = new Vector<Object>();
					v.add(man);
					v.add(man.getAdress());
					tableModel.addRow(v);
				}
			}
		});
		/*
		 * ---------------------------------------------------------
		 */
		/*
		 * Событие кнопки паспорта
		 */
		passportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (table.getSelectionModel().isSelectionEmpty()){ util_newError("Шкаф не выбран!"); return; }
				int selectedIndex = table.getRowSorter().convertRowIndexToModel(table.getSelectionModel().getMinSelectionIndex());
				util_viewPassport(iSys.rw.createManholePassport((Manhole) tableModel.getValueAt(selectedIndex, 0)));
			}
		});
		
		/*
		 * ---------------------------------------------------------
		 */
		/*
		 * Событие кнопки редактирования
		 */
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (table.getSelectionModel().isSelectionEmpty()){ util_newError("Колодец не выбран!"); return; }
				int selectedIndex = table.getRowSorter().convertRowIndexToModel(table.getSelectionModel().getMinSelectionIndex());
				new FormManhole(iSys,(Manhole) tableModel.getValueAt(selectedIndex, 0));
			}
		});
		
		/*
		 * ---------------------------------------------------------
		 */
		/*
		 * Событие кнопки просмотра
		 */
		viewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (table.getSelectionModel().isSelectionEmpty()){ util_newError("Колодец не выбран!"); return; }
				int selectedIndex = table.getRowSorter().convertRowIndexToModel(table.getSelectionModel().getMinSelectionIndex());
				new FormViewManhole(iSys,(Manhole)tableModel.getValueAt(selectedIndex, 0));
			}
		});
		/*
		 * ---------------------------------------------------------
		 */
		/*
		 * Событие кнопки создания
		 */
		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new FormManhole(iSys, null);
			}
		});
		/*
		 * ---------------------------------------------------------
		 */
		/*
		 * Событие кнопки удаления
		 */
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if (table.getSelectionModel().isSelectionEmpty()){ util_newError("Колодец не выбран!"); return; }
				int selectedIndex = table.getRowSorter().convertRowIndexToModel(table.getSelectionModel().getMinSelectionIndex());
				Manhole man = (Manhole)tableModel.getValueAt( selectedIndex, 0);
				String manholeName = man.toString();
				int n = util_newDialog("Удалить колодец: " + manholeName +" и все участки канализации проходящие через него?");
				if (n == JOptionPane.YES_OPTION) {
					iSys.removeManhole(man);
					util_newInfo("Колодец" + manholeName +"и участки канализации, проходящие через него, удалены");
					((DefaultTableModel) table.getModel()).removeRow(selectedIndex);	
				}	
			}
		});
		/*
		 * ---------------------------------------------------------
		 */
		
		Iterator<AbstractElement> i = collection.iterator();
		while(i.hasNext()) {
			Manhole man = (Manhole)i.next();
			
			Vector<Object> v = new Vector<Object>();
			v.add(man);
			v.add(man.getAdress());
			
			tableModel.addRow(v);
		}
		
		 	ArrayList<SortKey> keys=new ArrayList<SortKey>();
	        keys.add(new SortKey(0, SortOrder.ASCENDING));                                             
	        sorter.setSortKeys(keys);
	        sorter.setSortsOnUpdates(true);
	        
	       
	        iFrame.setVisible(true);
	}
	
}