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
import org.teleline.model.Cabinet;
import org.teleline.model.DBox;
import org.teleline.model.DFramе;
import org.teleline.model.StructuredElement;
import org.teleline.system.Sys;


public class FormAbstractElements extends FormJFrame /*implements InterfaceFormAbstractElements*/{
	
	protected Collection<AbstractElement> collection;
	
	final public JTable table;
	protected DefaultTableModel tableModel;
	protected TableRowSorter<TableModel> sorter;
	
	//public JButton refreshButton;
	public JButton editButton;
	public JButton viewButton;
	public JButton passportButton;
	public JButton createButton;
	public JButton deleteButton;
	
	protected Integer selectedIndex;
	protected String errMsg = "";
	protected boolean passportCheck = true;
	protected MigLayout buttonPanelLayout;
	protected JPanel buttonPanel;
	
	public FormAbstractElements(Sys iSys, Collection<AbstractElement> collection) {
		super(iSys);
		this.collection = collection;
		createFrame("", 785, 600);
		iFrame.setResizable(true);
		iFrame.getContentPane().setLayout(new BorderLayout(0, 0));
		JPanel panel_1 = new JPanel();
		iFrame.getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new BorderLayout(0, 0));
		buttonPanel = new JPanel();
		panel_1.add(buttonPanel, BorderLayout.EAST);
		table = addTable(panel_1,BorderLayout.CENTER);
		tableModel = (DefaultTableModel) table.getModel();
		sorter = new TableRowSorter<TableModel>(table.getModel());

	//	refreshButton = new JButton("Обновить");
		editButton = new JButton("Редактировать");
		viewButton = new JButton("Смотреть");
		passportButton = new JButton("Паспорт");
		createButton = new JButton("Добавить");
		deleteButton = new JButton("Удалить");
		
		buttonPanelLayout = new MigLayout("", "[110px,fill]", "[30px][30px][70px,top][30px][70px,top][]"); 
		buttonPanel.setLayout(buttonPanelLayout);
	//	buttonPanel.add(refreshButton, "cell 0 0");
		buttonPanel.add(editButton, "cell 0 0");
		buttonPanel.add(viewButton, "cell 0 1");
		buttonPanel.add(passportButton, "cell 0 2,");
		buttonPanel.add(createButton, "cell 0 3");
		buttonPanel.add(deleteButton, "cell 0 4");
		sorter.setSortsOnUpdates(true);
		
		/*
		 * Событие кнопки обновления
		 */
/*		refreshButton.addActionListener(new ActionListener() {
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
				selectedIndex = sorter.convertRowIndexToModel(table.getSelectionModel().getMinSelectionIndex());
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
	
	public void setCollection(Collection<AbstractElement> collection) {
		this.collection = collection;
	}	
	
	public void refresh() {
		// TODO Auto-generated method stub
	}
	public void passport() {
		AbstractElement element = (AbstractElement) tableModel.getValueAt(selectedIndex, 0);
		
		if (element instanceof Cabinet) {
			util_viewPassport(iSys.rw.createCabinetPassport((Cabinet)element));
			return;
		}
		if (element instanceof DFramе) {
			util_viewPassport(iSys.rw.createDFramePassport((DFramе)element));
			return;
		}
	}
	
	public void edit() {
		AbstractElement element = (AbstractElement) tableModel.getValueAt(selectedIndex, 0);
		
		if (element instanceof Cabinet)
			new FormCabinet(iFrame, iSys, (Cabinet)element, null).iDialog.setVisible(true);
			
		if (element instanceof DFramе)
			new FormDFrame(iFrame, iSys, (DFramе)element, null).iDialog.setVisible(true);
		
		if (element instanceof DBox)
			new FormDBox(iFrame, iSys,(DBox)element, null).iDialog.setVisible(true);
		
		updateRow(element, selectedIndex);
		
	}
	
	public void view() {
		AbstractElement element = (AbstractElement) tableModel.getValueAt(selectedIndex, 0);
		
		if (element instanceof Cabinet)
			new FormViewStructuredElement(iSys, (StructuredElement)element);
		
		if (element instanceof DFramе)
			new FormViewStructuredElement(iSys, (StructuredElement)element);
		
		if (element instanceof DBox)
			new FormViewDBox(iSys, (DBox)element);
	}
	public void create() {
		
	}
	public void delete() {
		
	}
	public void updateRow(AbstractElement element, Integer row) {
		
	}
}