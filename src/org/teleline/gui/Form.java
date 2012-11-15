package org.teleline.gui;

import java.util.Collection;
import java.util.Iterator;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.teleline.model.AbstractElement;
import org.teleline.model.Sys;

public abstract class Form {
	
	public JDialog  iFrame;
	public Sys iSys;
	
	public Form() {
		
		
	}
	
	protected JDialog createDialog (String title, int width, int height) {
		
		iFrame = new JDialog();
		iFrame.setSize(width, height);
		iFrame.setLocationRelativeTo(iFrame);
		iFrame.setTitle(title);
		iFrame.setResizable(false);
	//	frame.setModal(true);
		iFrame.getContentPane().setLayout(null);
		return iFrame;
	}
	public void setSys(Sys iSys) {
		this.iSys = iSys;
	}
	/**
	 * Добавляет список к форме
	 * @return список
	 */
	public JList addList( int x, int y, int w, int h) {
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(x, y, w, h);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		JList list = new JList();
		list.setModel(new DefaultListModel());
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(list);
		iFrame.getContentPane().add(scrollPane);
				
		return list;
	}
	/**
	 * Добавляет кнопку к форме
	 * @return кнопка
	 */
	public JButton addButton(String Text, int x, int y, int w, int h) {
		
		JButton button = new JButton(Text);
		button.setBounds(x, y, w, h);
		iFrame.getContentPane().add(button);
		
		return button;
	}
	/**
	 * Добавляет надпись к форме
	 * @return надпись
	 */
	public JLabel addLabel(String Text, int x, int y, int w, int h) {
		
		JLabel newLabel = new JLabel(Text);
		newLabel.setBounds(x, y, w, h);
		iFrame.getContentPane().add(newLabel);
		
		return newLabel;
	}
	/**
	 * Добавляет выпадающий список к форме
	 * @return выпадающий список
	 */
	public JComboBox addComboBox(int x, int y, int w, int h) {
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(x, y, w, h);
		iFrame.getContentPane().add(comboBox);
		
		return comboBox;
	}
	/**
	 * Добавляет текстовое поле к форме
	 * @return текстовое поле
	 */
	public JTextField addTextField(int x, int y, int w, int h) {
		
		JTextField textField = new JTextField();
		textField.setBounds(x, y, w, h);
		iFrame.getContentPane().add(textField);
		
		return textField;
	}
	
	@SuppressWarnings("serial")
	public JTable addTable( int x, int y, int w, int h) {
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(x, y, w, h);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		JTable table = new JTable(new DefaultTableModel()){
			public boolean isCellEditable(int arg0, int arg1) {return false; }
		};
		table.setRowHeight(18);
		table.getSelectionModel().setSelectionMode(0);
		scrollPane.setViewportView(table);
		table.setRowSorter(new TableRowSorter<TableModel>(table.getModel()));
		iFrame.getContentPane().add(scrollPane);
		return table;
	}
	
	protected void util_setListItems (final JList List, Collection<?> Collection) {
		
		((DefaultListModel)List.getModel()).clear();
			
		Iterator<?> listItem = Collection.iterator();
		while (listItem.hasNext()) ((DefaultListModel)List.getModel()).addElement(listItem.next());

	}
	
	public void util_clearTable (JTable table) {
		for (int i = ((DefaultTableModel) table.getModel()).getRowCount() - 1; i >=0;  i--) {
			((DefaultTableModel) table.getModel()).removeRow(i);
		}
		
	}
	/**
	 * Устанавливает (добавляет) элементы выпадающего списка
	 * @param ComboBox - выпадающий список
	 * @param Collection - коллекция элементов
	 */
	public void util_setComboBoxItems(final JComboBox ComboBox, Collection<?> Collection) {
		
		Iterator<?> i = Collection.iterator();
		while (i.hasNext())  {ComboBox.addItem((AbstractElement)i.next());}
		
	}
	
	public void util_newInfo (String mes) {JOptionPane.showMessageDialog(iFrame, mes, "Операция выполнена успешно", JOptionPane.INFORMATION_MESSAGE);}
	
	public void util_newError (String mes) {JOptionPane.showMessageDialog(iFrame, mes, "Ошибка", JOptionPane.ERROR_MESSAGE);}

}