package org.teleline.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SortOrder;
import javax.swing.RowSorter.SortKey;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import net.miginfocom.swing.MigLayout;

import org.teleline.model.AbstractElement;
import org.teleline.system.Sys;


public class FormAbstractElements extends Form implements InterfaceFormAbstractElements{
	
	final public JTable table;
	protected DefaultTableModel tableModel;
	protected TableRowSorter<TableModel> sorter;
	
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
		iFrame.setResizable(true);
		iFrame.getContentPane().setLayout(new BorderLayout(0, 0));
		JPanel panel_1 = new JPanel();
		iFrame.getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		JPanel panel = new JPanel();
		panel_1.add(panel, BorderLayout.EAST);
		table = addTable(panel_1);
		tableModel = (DefaultTableModel) table.getModel();
		sorter = new TableRowSorter<TableModel>(table.getModel());

		refreshButton = new JButton("Обновить");
		editButton = new JButton("Редактировать");
		viewButton = new JButton("Смотреть");
		passportButton = new JButton("Паспорт");
		createButton = new JButton("Добавить");
		deleteButton = new JButton("Удалить");
		
		panel.setLayout(new MigLayout("", "[110px,fill]", "[70px,top][30px][30px][70px,top][30px][30px]"));
		panel.add(refreshButton, "cell 0 0");
		panel.add(editButton, "cell 0 1");
		panel.add(viewButton, "cell 0 2");
		panel.add(passportButton, "cell 0 3,");
		panel.add(createButton, "cell 0 4");
		panel.add(deleteButton, "cell 0 5");
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