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
import org.teleline.model.DBox;
import org.teleline.model.Pair;
import org.teleline.model.Sys;
public class FormDBoxes extends Form {
	
	final public JTable table;
	public JButton refreshButton;
	
	public FormDBoxes(final Sys iSys, Collection<AbstractElement> collection/*, Cabinet cab*/) {
		super(iSys);
		
		createDialog("Коробки", 785, 600);
		
		addLabel("Список коробок:", 10, 10, 520, 14);
		table = addTable(10, 30, 620, 525);
		final DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		tableModel.setColumnIdentifiers(new String[]{"Коробка", "Кабель", "Адрес"});
		table.getColumnModel().getColumn(0).setMaxWidth(100);
		table.getColumnModel().getColumn(0).setPreferredWidth(100);
		table.getColumnModel().getColumn(1).setMaxWidth(100);
		table.getColumnModel().getColumn(1).setPreferredWidth(100);
		
		
		refreshButton = addButton("Обновить", 640, 30, 125, 26);
		JButton editButton = addButton("Редактировать", 640, 105, 125, 26);
		JButton viewButton = addButton("Смотреть", 640, 145, 125, 26);
		JButton passportButton = addButton("Адр. лист", 640, 185, 125, 26);
		JButton createButton = addButton("Добавить", 640, 255, 125, 26);
		JButton deleteButton = addButton("Удалить", 640, 295, 125, 26);
		final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
		table.setRowSorter(sorter);
		
		/*
		 * Событие кнопки обновления списка
		 */
	/*	refreshButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				GUI.clearTable(cableTable);
				Iterator<StructuredElement> i = sys.cc.getInNet(sys.nc.getOnlyElement().getId()).iterator();
	    		while (i.hasNext()) {
	    			GUI.addCableToTable(cableTable, (Cable)i.next());
	    		}
			}
		});
		/*
		 * ---------------------------------------------------------
		 */
		/*
		 * Событие кнопки создания адресного листа
		 */
		passportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Vector<DBox> db = new Vector<DBox>();
				
				for (int i = 0; i < tableModel.getRowCount(); i++) {
					db.add((DBox) tableModel.getValueAt(sorter.convertRowIndexToModel(i), 0));	
				}
				
				util_viewPassport(iSys.rw.createDBoxPassport(db));
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
				if (table.getSelectionModel().isSelectionEmpty()){ util_newError("Коробка не выбрана!"); return; }
				
				int selectedIndex = table.getRowSorter().convertRowIndexToModel(table.getSelectionModel().getMinSelectionIndex());
				new FormDBox(iSys,(DBox) tableModel.getValueAt(selectedIndex, 0));
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
				if (table.getSelectionModel().isSelectionEmpty()){ util_newError("Коробка не выбрана!"); return; }
				int selectedIndex = table.getRowSorter().convertRowIndexToModel(table.getSelectionModel().getMinSelectionIndex());
				new FormViewDBox(iSys, (DBox)tableModel.getValueAt( selectedIndex, 0));
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
				new FormDBox(iSys, null);
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
				
				if (table.getSelectionModel().isSelectionEmpty()){ util_newError("Коробка не выбрана!"); return; }
				int selectedIndex = table.getRowSorter().convertRowIndexToModel(table.getSelectionModel().getMinSelectionIndex());
				DBox dbox = (DBox)tableModel.getValueAt( selectedIndex, 0);
				String dboxName = dbox.toString();
				int n = util_newDialog("Удалить коробку: " + dboxName+" и все содержащиеся в ней пары?");
				if (n == JOptionPane.YES_OPTION) {
					iSys.removeDBox(dbox);
					util_newInfo("Коробка " + dboxName+" и все содержащиеся в ней пары удалены");
					((DefaultTableModel) table.getModel()).removeRow(selectedIndex);	
				}	
			}
		});
		/*
		 * ---------------------------------------------------------
		 */
		
		Iterator<AbstractElement> i = collection.iterator();
		while(i.hasNext()) {
			DBox dbox = (DBox)i.next();
			
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
		
		
	
		 	ArrayList<SortKey> keys=new ArrayList<SortKey>();
	        keys.add(new SortKey(0, SortOrder.ASCENDING));                                             
	        sorter.setSortKeys(keys);
	        sorter.setSortsOnUpdates(true);
	        
	       
	        iFrame.setVisible(true);
	        
	}
}
