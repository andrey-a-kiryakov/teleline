package org.teleline.gui;

import java.awt.Container;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.teleline.model.AbstractElement;
import org.teleline.system.Sys;

public class Form {
	
	protected static final Logger log = LoggerFactory.getLogger("sys");
	
	public JFrame iFrame;
	public JDialog iDialog;
	protected Container contentPane;
	
	public Sys iSys;
	
	public Form(Sys iSys) {
		this.iSys = iSys;
	}
	
	/**
	 * Добавляет кнопку к форме
	 * @return кнопка
	 */
	public JButton addButton(String Text, int x, int y, int w, int h) {
		
		JButton button = new JButton(Text);
		button.setBounds(x, y, w, h);
		contentPane.add(button);
		
		return button;
	}
	/**
	 * Добавляет надпись к форме
	 * @return надпись
	 */
	public JLabel addLabel(String Text, int x, int y, int w, int h) {
		
		JLabel newLabel = new JLabel(Text);
		newLabel.setBounds(x, y, w, h);
		contentPane.add(newLabel);
		
		return newLabel;
	}
	/**
	 * Добавляет выпадающий список к форме
	 * @return выпадающий список
	 */
	public JComboBox addComboBox(int x, int y, int w, int h) {
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(x, y, w, h);
		contentPane.add(comboBox);
		
		return comboBox;
	}
	/**
	 * Добавляет текстовое поле к форме
	 * @return текстовое поле
	 */
	public JTextField addTextField(int x, int y, int w, int h) {
		
		JTextField textField = new JTextField();
		textField.setBounds(x, y, w, h);
		contentPane.add(textField);
		
		return textField;
	}
	
	public JTextArea addTextArea(int x, int y, int w, int h) {
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(x, y, w, h);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		JTextArea textArea = new JTextArea();
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		contentPane.add(scrollPane);
		
		return textArea;
	}
	@SuppressWarnings("serial")
	public JTable addTable(int x, int y, int w, int h) {
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(x, y, w, h);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		JTable table = new JTable(new DefaultTableModel()){
			public boolean isCellEditable(int arg0, int arg1) {return false; }
		};
		table.setRowHeight(20);
		table.getSelectionModel().setSelectionMode(0);
		scrollPane.setViewportView(table);
		table.setRowSorter(new TableRowSorter<TableModel>(table.getModel()));
		contentPane.add(scrollPane);
		return table;
	}
	
	@SuppressWarnings("serial")
	public JTable addTable(JPanel panel, String position) {
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		JTable table = new JTable(new DefaultTableModel()){
			public boolean isCellEditable(int arg0, int arg1) {return false; }
		};
		table.setRowHeight(18);
		table.getSelectionModel().setSelectionMode(0);
		scrollPane.setViewportView(table);
		table.setRowSorter(new TableRowSorter<TableModel>(table.getModel()));
		panel.add(scrollPane,position);
		return table;
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
	public void util_clearTable (JTable table) {
		for (int i = ((DefaultTableModel) table.getModel()).getRowCount() - 1; i >=0;  i--) {
			((DefaultTableModel) table.getModel()).removeRow(i);
		}
		
	}
	
	
}