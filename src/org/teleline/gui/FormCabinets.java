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
import javax.swing.RowSorter.SortKey;
import javax.swing.SortOrder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.teleline.model.AbstractElement;
import org.teleline.model.Cabinet;

import org.teleline.model.Sys;

public class FormCabinets extends Form {
	
	final public JTable table;
	public JButton refreshButton;
	
	public FormCabinets(final Sys iSys, Collection<AbstractElement> collection) {
		super(iSys);
		// TODO Auto-generated constructor stub
		
		createDialog("Шкафы", 785, 600);
		
		addLabel("Список шкафов:", 10, 10, 520, 14);
		table = addTable(10, 30, 620, 525);
		final DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		tableModel.setColumnIdentifiers(new String[]{"Шкаф", "Класс", "Мест", "Адрес"});
		table.getColumnModel().getColumn(0).setMaxWidth(100);
		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setMaxWidth(60);
		table.getColumnModel().getColumn(1).setPreferredWidth(60);
		table.getColumnModel().getColumn(2).setMaxWidth(60);
		table.getColumnModel().getColumn(2).setPreferredWidth(60);

		refreshButton = addButton("Обновить", 640, 30, 125, 26);
		JButton editButton = addButton("Редактировать", 640, 105, 125, 26);
		JButton viewButton = addButton("Смотреть", 640, 145, 125, 26);
		JButton passportButton = addButton("Паспорт", 640, 185, 125, 26);
		JButton createButton = addButton("Добавить", 640, 255, 125, 26);
		JButton deleteButton = addButton("Удалить", 640, 295, 125, 26);
		final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
		table.setRowSorter(sorter);
		
		/*
		 * Событие кнопки обновления списка
		 */
		refreshButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				util_clearTable(table);
				
				Iterator<AbstractElement> i = iSys.cbc.getIterator();
				while(i.hasNext()) {
					Cabinet cab = (Cabinet)i.next();
					Vector<Object> v = new Vector<Object>();
					v.add(cab);
					v.add(cab.getCabinetClass());
					v.add(cab.getPlacesCount());
					v.add(cab.getAdress());
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
				util_viewPassport(iSys.rw.createCabinetPassport((Cabinet) tableModel.getValueAt(selectedIndex, 0)));
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
				if (table.getSelectionModel().isSelectionEmpty()){ util_newError("Шкаф не выбран!"); return; }
				int selectedIndex = table.getRowSorter().convertRowIndexToModel(table.getSelectionModel().getMinSelectionIndex());
				new FormCabinet(iSys,(Cabinet) tableModel.getValueAt(selectedIndex, 0));
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
				if (table.getSelectionModel().isSelectionEmpty()){ util_newError("Шкаф не выбран!"); return; }
				int selectedIndex = table.getRowSorter().convertRowIndexToModel(table.getSelectionModel().getMinSelectionIndex());
			//	new FormViewDBox(iSys, (DBox)tableModel.getValueAt( selectedIndex, 0));
				new FormViewCabinet(iSys,(Cabinet)tableModel.getValueAt(selectedIndex, 0));
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
				new FormCabinet(iSys, null);
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
				
				if (table.getSelectionModel().isSelectionEmpty()){ util_newError("Шкаф не выбран!"); return; }
				int selectedIndex = table.getRowSorter().convertRowIndexToModel(table.getSelectionModel().getMinSelectionIndex());
				Cabinet cab = (Cabinet)tableModel.getValueAt( selectedIndex, 0);
				String cabinetName = cab.toString();
				int n = util_newDialog("Удалить шкаф: " + cabinetName+" и все его содержимое?");
				if (n == JOptionPane.YES_OPTION) {
					iSys.removeCabinet(cab);
					util_newInfo("Шкаф" + cabinetName+" и все его содержимое удалены");
					((DefaultTableModel) table.getModel()).removeRow(selectedIndex);	
				}	
			}
		});
		/*
		 * ---------------------------------------------------------
		 */
		
		Iterator<AbstractElement> i = collection.iterator();
		while(i.hasNext()) {
			Cabinet cab = (Cabinet)i.next();
			
			Vector<Object> v = new Vector<Object>();
			v.add(cab);
			v.add(cab.getCabinetClass());
			v.add(cab.getPlacesCount());
			v.add(cab.getAdress());
			tableModel.addRow(v);
		}
		
		 	ArrayList<SortKey> keys=new ArrayList<SortKey>();
	        keys.add(new SortKey(0, SortOrder.ASCENDING));                                             
	        sorter.setSortKeys(keys);
	        sorter.setSortsOnUpdates(true);
	        
	       
	        iFrame.setVisible(true);
	        
	}
}
