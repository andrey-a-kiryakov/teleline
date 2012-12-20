package org.teleline.gui;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SortOrder;
import javax.swing.RowSorter.SortKey;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.teleline.model.Subscriber;
import org.teleline.model.Wrapper;
import org.teleline.system.Sys;

/**
*Класс элементов Форма для формы поиска абонента по номеру и телефону
*
*/
public class FormSubscriberSearch extends FormJDialog{
	
	public JTextField phoneTextField;
	public JTextField nameTextField;
	public JTable subscriberList;

	public JButton findByPhoneButton;
	public JButton okButton;
	
	
	public FormSubscriberSearch(Window owner, final Sys iSys, final Wrapper wrapper) {
		super(owner, iSys);
		createDialog("Найти абонента", 585, 600);
		
		addLabel("Телефонный номер или имя:", 10, 10, 320, 14);
		phoneTextField = addTextField(10, 30, 420, 25);
		
		addLabel("Результаты поиска:", 10, 65, 320, 14);
		subscriberList = addTable(10, 85, 420, 480);
		final DefaultTableModel tableModel = (DefaultTableModel) subscriberList.getModel();
		tableModel.setColumnIdentifiers(new String[]{"Имя", "Телефон"});
		final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(subscriberList.getModel());
		subscriberList.setRowSorter(sorter);
		
		ArrayList<SortKey> keys=new ArrayList<SortKey>();
		keys.add(new SortKey(0, SortOrder.ASCENDING));
		sorter.setSortKeys(keys);
		sorter.setSortsOnUpdates(true);

		findByPhoneButton = addButton("Найти", 440, 30, 125, 26);
		okButton = addButton("Выбрать", 440, 85, 125, 26);
		
		/*
		 * Событие кнопки поиска абонента по телефону
		 */
		findByPhoneButton.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				util_clearTable(subscriberList);
				Iterator<Subscriber> i = iSys.sc.search(phoneTextField.getText()).iterator();
				while (i.hasNext()) addSubscriberToTable(i.next());
			}
		});
		
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (subscriberList.getSelectionModel().isSelectionEmpty()){util_newError("Абонент не выбран!"); return; }
				//Integer selectedIndex = subscriberList.getRowSorter().convertRowIndexToModel(subscriberList.getSelectionModel().getMinSelectionIndex());
				Integer selectedIndex = subscriberList.getSelectionModel().getMinSelectionIndex();
				Subscriber sub = (Subscriber) subscriberList.getValueAt(selectedIndex, 0);
				wrapper.setElement(sub);
				/*
				System.out.println(subscriberList.getSelectionModel().getMinSelectionIndex());
				System.out.println(selectedIndex);
				
				System.out.println((Subscriber) subscriberList.getValueAt(subscriberList.getSelectionModel().getMinSelectionIndex(), 0));
				System.out.println((Subscriber) subscriberList.getValueAt(selectedIndex, 0));
				*/
				
				iDialog.dispose();
			}
		});
		iDialog.setVisible(true);
	}
	/**
	 * Добавляет абонента в таблицу
	 * @param table - таблица
	 * @param subscriber - абонент
	 */
	private void addSubscriberToTable(Subscriber subscriber){
		
		Vector<Object> v = new Vector<Object>();
		v.add(subscriber);
		v.add(subscriber.getPhoneNumber());
		((DefaultTableModel) subscriberList.getModel()).addRow(v);
	}
}