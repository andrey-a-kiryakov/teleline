package org.teleline.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SortOrder;
import javax.swing.RowSorter.SortKey;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.teleline.model.AbstractElement;

import system.Sys;

public class FormAbstractElements extends Form implements InterfaceFormAbstractElements{
	
	final public JTable table;
	protected DefaultTableModel tableModel;
	protected TableRowSorter<TableModel> sorter;
	
	public JLabel tableLabel;
	public JButton refreshButton;
	public JButton editButton;
	public JButton viewButton;
	public JButton passportButton;
	public JButton createButton;
	public JButton deleteButton;
	
	protected Integer selectedIndex;
	protected String errMsg = "";
	protected boolean passportCheck = true;
	
	public FormAbstractElements(Sys iSys, Collection<AbstractElement> collection) {
		super(iSys);
		createDialog("", 785, 600);
		
		tableLabel = addLabel("Список шкафов:", 10, 10, 520, 14);
		table = addTable(10, 30, 620, 525);
		tableModel = (DefaultTableModel) table.getModel();

		refreshButton = addButton("Обновить", 640, 30, 125, 26);
		editButton = addButton("Редактировать", 640, 105, 125, 26);
		viewButton = addButton("Смотреть", 640, 145, 125, 26);
		passportButton = addButton("Паспорт", 640, 185, 125, 26);
		createButton = addButton("Добавить", 640, 255, 125, 26);
		deleteButton = addButton("Удалить", 640, 295, 125, 26);
		sorter = new TableRowSorter<TableModel>(table.getModel());
		
        sorter.setSortsOnUpdates(true);
		
		/*
		 * Событие кнопки обновления
		 */
		refreshButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				util_clearTable(table);
				refresh();
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
				
				if (passportCheck) {
					if (table.getSelectionModel().isSelectionEmpty()){ util_newError(errMsg); return; }
					selectedIndex = table.getRowSorter().convertRowIndexToModel(table.getSelectionModel().getMinSelectionIndex());
				}
				passport();
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
				if (table.getSelectionModel().isSelectionEmpty()){ util_newError(errMsg); return; }
				selectedIndex = table.getRowSorter().convertRowIndexToModel(table.getSelectionModel().getMinSelectionIndex());
				edit();
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
				if (table.getSelectionModel().isSelectionEmpty()){ util_newError(errMsg); return; }
				selectedIndex = table.getRowSorter().convertRowIndexToModel(table.getSelectionModel().getMinSelectionIndex());
				view();
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
				create();
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
				if (table.getSelectionModel().isSelectionEmpty()){ util_newError(errMsg); return; }
				selectedIndex = table.getRowSorter().convertRowIndexToModel(table.getSelectionModel().getMinSelectionIndex());
				delete();
			}
		});
		/*
		 * ---------------------------------------------------------
		 */
	}
	public void enableSort (){
		
		ArrayList<SortKey> keys = new ArrayList<SortKey>();
        keys.add(new SortKey(0, SortOrder.ASCENDING));                                             
        sorter.setSortKeys(keys);
        table.setRowSorter(sorter);
	}
	
	@Override
	public void refresh() {
		// TODO Auto-generated method stub
	}
	@Override
	public void passport() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void edit() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void view() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void create() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}
}