package org.teleline.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.teleline.model.*;

public class gui {
	public Sys sys;

	private JFrame frame;
	
	public gui(Sys iSys,/*NetCollection nc, DFrameCollection dfc, CabinetCollection cbc, DBoxCollection dbc, ManholeCollection mc, DuctCollection duc, BuildingCollection buc, TubeCollection tuc, FrameCollection fc, BoxCollection bc, CableCollection cc, PairCollection pc, PathCollection phc, SubscriberCollection sc, DamageCollection dmc, RW rw, Validator V,*/ JFrame frame ) {
		this.frame = frame;
		this.sys = iSys;
	}
	
	/**
	 * Добавояет кабель в таблицу
	 * @param table - таблица
	 * @param cable - кабель
	 */
	public void addCableToTable(JTable table, Cable cable){
		
		Vector<Object> v = new Vector<Object>();
		v.add(cable);
		v.add(cable.getFromElement());
		v.add(cable.getToElement());
		v.add(cable.getCapacity());
		v.add(cable.getUsedCapacity());
		
		v.add(cable.getLenght());
		((DefaultTableModel) table.getModel()).addRow(v);
	}
	
	/**
	 * Обновляет строчку с кабелем в таблице
	 * @param table - таблица
	 * @param cable - кабель
	 * @param index - позиция обновляемой строки в таблице
	 */
	public void updateCableInTable(JTable table, Cable cable, Integer index) {
		
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		tableModel.setValueAt(cable, index, 0);
		tableModel.setValueAt(cable.getFromElement(), index, 1);
		tableModel.setValueAt(cable.getToElement(), index, 2);
		tableModel.setValueAt(cable.getCapacity(), index, 3);
		tableModel.setValueAt(cable.getUsedCapacity(), index, 4);
		tableModel.setValueAt(cable.getLenght(), index, 5);
	}
	/**
	 * Добавляет абонента в таблицу
	 * @param table - таблица
	 * @param subscriber - абонент
	 */
	public void addSubscriberToTable(JTable table, Subscriber subscriber){
		
		Vector<Object> v = new Vector<Object>();
		v.add(subscriber);
		v.add(subscriber.getPhoneNumber());
		v.add(subscriber.getAdress());
		((DefaultTableModel) table.getModel()).addRow(v);
	}
	/**
	 * Обновляет строчку с абонентом в таблице
	 * @param table - таблица
	 * @param subscriber - абонент
	 * @param index - позиция обновляемой строки в таблице
	 */
	public void updateSubscriberInTable(JTable table, Subscriber subscriber, Integer index) {
		
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		tableModel.setValueAt(subscriber, index, 0);
		tableModel.setValueAt(subscriber.getPhoneNumber(), index, 1);
		tableModel.setValueAt(subscriber.getAdress(), index, 2);
	}
	/**
	 * Добавляет повреждение в таблицу
	 * @param table - таблица
	 * @param damage - повреждение
	 */
	public void addDamageToTable(JTable table, Damage damage){
		
		Vector<Object> v = new Vector<Object>();
		v.add(damage);
		v.add(damage.getOpenDate());
		v.add(damage.getCloseDate());
		((DefaultTableModel) table.getModel()).addRow(v);
	}
	/**
	 * Обновляет строчку с повреждением в таблице
	 * @param table - таблица
	 * @param damage - повреждение
	 * @param index - позиция обновляемой строки в таблице
	 */
	public void updateDamageInTable(JTable table, Damage damage, Integer index) {
		
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		tableModel.setValueAt(damage, index, 0);
		tableModel.setValueAt(damage.getOpenDate(), index, 1);
		tableModel.setValueAt(damage.getCloseDate(), index, 2);
	}
	
	/**
	 * Очищает таблицу
	 * @param table - таблица
	 */
	public void clearTable (JTable table) {
		for (int i = ((DefaultTableModel) table.getModel()).getRowCount() - 1; i >=0;  i--) {
			((DefaultTableModel) table.getModel()).removeRow(i);
		}
		
	}
	
	public JDialog newDialog (String Title, int width, int height) {
		
		JDialog frame = new JDialog();
		frame.setSize(width, height);
		frame.setLocationRelativeTo(this.frame);
		frame.setTitle(Title);
		
		frame.setResizable(false);
	//	frame.setModal(true);
		
		frame.getContentPane().setLayout(null);
		
		return frame;
	}
	/**
	 * Создает выпадающий список кроссов
	 * @param NetsComboBox - выпадающий список сетей, из него берется сеть
	 * @return выпадающий список
	 */
	public JComboBox dframeComboBox(Integer netId,/* JComboBox NetsComboBox,*/ JDialog iFrame, int x, int y, int w, int h){
		
		JComboBox comboBox = new JComboBox();
	/*	if (NetsComboBox.getSelectedIndex() > -1) {
			setComboBoxItems(comboBox, sys.dfc.sortByIdUp(sys.dfc.getInNet(((Net)NetsComboBox.getSelectedItem()).getId())));
		}*/
		
		if (netId > -1) {
			setComboBoxItems(comboBox, sys.dfc.sortByIdUp(sys.dfc.getInNet(netId)));
	}
		comboBox.setBounds(x, y, w, h);
		iFrame.getContentPane().add(comboBox);
		
		return comboBox;
	}
	
	public JComboBox cabinetComboBox(Integer netId/*JComboBox NetsComboBox*/, Integer cabinetClass, JDialog iFrame, int x, int y, int w, int h){
		
		JComboBox comboBox = new JComboBox();
		/*if (NetsComboBox.getSelectedIndex() > -1) {
			setComboBoxItems(comboBox, sys.cbc.sortByNumberUp(sys.cbc.getInNetByClass(((Net)NetsComboBox.getSelectedItem()).getId(), cabinetClass)));
		}*/
		if (netId > -1) {
			setComboBoxItems(comboBox, sys.cbc.sortByNumberUp(sys.cbc.getInNetByClass(netId, cabinetClass)));
		}
		comboBox.setBounds(x, y, w, h);
		iFrame.getContentPane().add(comboBox);
		
		return comboBox;
	}
	/**
	 * СОздает и добавляет в форму выпадающий список зданий
	 * @param NetsComboBox- выпадающий список сетей
	 * @return выпадающий список
	 */
	public JComboBox buildingComboBox(Integer netId,/*JComboBox NetsComboBox,*/ JDialog iFrame, int x, int y, int w, int h){
		
		JComboBox comboBox = new JComboBox();
	/*	if (NetsComboBox.getSelectedIndex() > -1) {
			setComboBoxItems(comboBox, sys.buc.sortByIdUp(sys.buc.getInNet(((Net)NetsComboBox.getSelectedItem()).getId())));
		}*/
		
		if (netId > -1) {
		setComboBoxItems(comboBox, sys.buc.sortByIdUp(sys.buc.getInNet(netId)));
	}
		comboBox.setBounds(x, y, w, h);
		iFrame.getContentPane().add(comboBox);
		
		return comboBox;
	}
	/**
	 * Создает и добавляет в форму выпадающий список колодцев
	 * @param NetsComboBox - выпадающий список сетей
	 * @return выпадающий список
	 */
	public JComboBox manholeComboBox(JComboBox NetsComboBox, JDialog iFrame, int x, int y, int w, int h){
		
		JComboBox comboBox = new JComboBox();
		if (NetsComboBox.getSelectedIndex() > -1) {
			setComboBoxItems(comboBox, sys.mc.sortByIdUp(sys.mc.getInNet(((Net)NetsComboBox.getSelectedItem()).getId())));
		}
		comboBox.setBounds(x, y, w, h);
		iFrame.getContentPane().add(comboBox);
		
		return comboBox;
	}
	
	/**
	 * Создает и добавляет в форму выпадающий список колодцев и кроссов
	 * @param NetsComboBox - выпадающий список сетей
	 * @return выпадающий список
	 */
	public JComboBox manholeDFrameComboBox(Integer netId/*JComboBox NetsComboBox*/, JDialog iFrame, int x, int y, int w, int h){
		
		JComboBox comboBox = new JComboBox();
	/*	if (NetsComboBox.getSelectedIndex() > -1) {
			setComboBoxItems(comboBox, sys.mc.sortByIdUp(sys.mc.getInNet(((Net)NetsComboBox.getSelectedItem()).getId())));
			setComboBoxItems(comboBox, sys.dfc.sortByIdUp(sys.dfc.getInNet(((Net)NetsComboBox.getSelectedItem()).getId())));
		}*/
		if (netId > -1) {
			setComboBoxItems(comboBox, sys.mc.sortByIdUp(sys.mc.getInNet(netId)));
			setComboBoxItems(comboBox, sys.dfc.sortByIdUp(sys.dfc.getInNet(netId)));
		}
		comboBox.setBounds(x, y, w, h);
		iFrame.getContentPane().add(comboBox);
		
		return comboBox;
	}
	/**
	 * Создает и добавляет в форму выпадающий список колодцев, шкафов и зданий
	 * @param NetsComboBox - выпадающий список сетей
	 * @return выпадающий список
	 */
	public JComboBox manholeCabinetBuildingComboBox(Integer netId/*JComboBox NetsComboBox*/, JDialog iFrame, int x, int y, int w, int h){
		
		JComboBox comboBox = new JComboBox();
	/*	if (NetsComboBox.getSelectedIndex() > -1) {
			setComboBoxItems(comboBox, sys.mc.sortByIdUp(sys.mc.getInNet(((Net)NetsComboBox.getSelectedItem()).getId())));
			setComboBoxItems(comboBox, sys.cbc.sortByIdUp(sys.cbc.getInNet(((Net)NetsComboBox.getSelectedItem()).getId())));
			setComboBoxItems(comboBox, sys.buc.sortByIdUp(sys.buc.getInNet(((Net)NetsComboBox.getSelectedItem()).getId())));
			
		}*/
		if (netId > -1) {
			setComboBoxItems(comboBox, sys.mc.sortByIdUp(sys.mc.getInNet(netId)));
			setComboBoxItems(comboBox, sys.cbc.sortByIdUp(sys.cbc.getInNet(netId)));
			setComboBoxItems(comboBox, sys.buc.sortByIdUp(sys.buc.getInNet(netId)));
		}
		comboBox.setBounds(x, y, w, h);
		iFrame.getContentPane().add(comboBox);
		
		return comboBox;
	}
	
	public JComboBox dboxComboBox(Integer netId /*JComboBox NetsComboBox*/, JDialog iFrame, int x, int y, int w, int h){
		
		JComboBox comboBox = new JComboBox();
	/*	if (NetsComboBox.getSelectedIndex() > -1) {
			setComboBoxItems(comboBox, sys.dbc.sortByIdUp(sys.dbc.getInNet(((Net)NetsComboBox.getSelectedItem()).getId())));
		}*/
		if (netId > -1) {
		setComboBoxItems(comboBox, sys.dbc.sortByIdUp(sys.dbc.getInNet(netId)));
	}
	
		comboBox.setBounds(x, y, w, h);
		iFrame.getContentPane().add(comboBox);
		
		return comboBox;
	}
	
	public JComboBox cableComboBox(Integer netId/*JComboBox NetsComboBox*/, JComboBox FromComboBox, JComboBox ToComboBox, Integer Type, JDialog iFrame,  int x, int y, int w, int h){
		
		JComboBox comboBox = new JComboBox();
		if (Type < 2) {
			if (netId > -1 && FromComboBox.getSelectedIndex() > -1 && ToComboBox.getSelectedIndex() > -1) {
				setComboBoxItems(comboBox, sys.cc.sortByIdUp(sys.cc.getInOwners(Type, ((StructuredElement)FromComboBox.getSelectedItem()).getId(), ((StructuredElement)ToComboBox.getSelectedItem()).getId())));
			}
		}
		
		if (Type >= 2) {
			if (netId > -1 && FromComboBox.getSelectedIndex() > -1) {
				setComboBoxItems(comboBox, sys.cc.sortByIdUp(sys.cc.getDCableOut((StructuredElement)FromComboBox.getSelectedItem())));
			}
		}
		
		comboBox.setBounds(x, y, w, h);
		iFrame.getContentPane().add(comboBox);
		
		return comboBox;
	}
	public JComboBox boxComboBox(JComboBox OwnersComboBox, Integer Type, JDialog iFrame, int x, int y, int w, int h){
		
		JComboBox comboBox = new JComboBox();
		if (OwnersComboBox.getSelectedIndex() > -1) {
			setComboBoxItems(comboBox, sys.bc.getInOwnerByTypeUniversal(((Cabinet)OwnersComboBox.getSelectedItem()).getId(), Type));
		}
		comboBox.setBounds(x, y, w, h);
		iFrame.getContentPane().add(comboBox);
		
		return comboBox;
	}
	public JComboBox frameComboBox(JComboBox OwnersComboBox, JDialog iFrame, int x, int y, int w, int h){
		
		JComboBox comboBox = new JComboBox();
		
		if (OwnersComboBox.getSelectedIndex() > -1) {
			setComboBoxItems(comboBox, sys.fc.getInOwner(((DFramе)OwnersComboBox.getSelectedItem()).getId()));
		}
		comboBox.setBounds(x, y, w, h);
		iFrame.getContentPane().add(comboBox);
		
		return comboBox;
	}
	/**
	 * Связывает выпадающий список сетей с выпадающим списком колодцев
	 * @param NetsComboBox - выпадающий список сетей
	 * @param LinkedComboBox - связанный выпадающий список
	 */
	public void netsManholeComboLinked(final JComboBox NetsComboBox, final JComboBox LinkedComboBox) {
		
		ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LinkedComboBox.removeAllItems();
                setComboBoxItems(LinkedComboBox, sys.mc.sortByIdUp(sys.mc.getInNet(((Net)NetsComboBox.getSelectedItem()).getId())));               
            }
        };
        NetsComboBox.addActionListener(actionListener);      
	}
	/**
	 * Связывает выпадающий список сетей с выпадающим списком зданий
	 * @param NetsComboBox - выпадающий список сетей
	 * @param LinkedComboBox - связанный выпадающий список
	 */
	public void netsBuildingComboLinked(final JComboBox NetsComboBox, final JComboBox LinkedComboBox) {
		
		ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LinkedComboBox.removeAllItems();
                setComboBoxItems(LinkedComboBox, sys.buc.sortByIdUp(sys.buc.getInNet(((Net)NetsComboBox.getSelectedItem()).getId())));               
            }
        };
        NetsComboBox.addActionListener(actionListener);      
	}
	/**
	 * Связывает выпадающий список сетей с выпадающим списком колодцев и кроссов
	 * @param NetsComboBox - выпадающий список сетей
	 * @param LinkedComboBox - связанный выпадающий список
	 */
	public void netsManholeDFrameComboLinked(final JComboBox NetsComboBox, final JComboBox LinkedComboBox) {
		
		ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LinkedComboBox.removeAllItems();
                setComboBoxItems(LinkedComboBox, sys.mc.sortByIdUp(sys.mc.getInNet(((Net)NetsComboBox.getSelectedItem()).getId())));
                setComboBoxItems(LinkedComboBox, sys.dfc.sortByIdUp(sys.dfc.getInNet(((Net)NetsComboBox.getSelectedItem()).getId())));
                
            }
        };
        NetsComboBox.addActionListener(actionListener);      
	}
	/**
	 * Связывает выпадающий список сетей с выпадающим списком колодцев, шкафов и зданий
	 * @param NetsComboBox - выпадающий список сетей
	 * @param LinkedComboBox - связанный выпадающий список
	 */
	public void netsManholeCabinetBuildingComboLinked(final JComboBox NetsComboBox, final JComboBox LinkedComboBox) {
		
		ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LinkedComboBox.removeAllItems();
                setComboBoxItems(LinkedComboBox, sys.mc.sortByIdUp(sys.mc.getInNet(((Net)NetsComboBox.getSelectedItem()).getId())));
                setComboBoxItems(LinkedComboBox, sys.cbc.sortByIdUp(sys.cbc.getInNet(((Net)NetsComboBox.getSelectedItem()).getId())));
                setComboBoxItems(LinkedComboBox, sys.buc.sortByIdUp(sys.buc.getInNet(((Net)NetsComboBox.getSelectedItem()).getId())));
                
            }
        };
        NetsComboBox.addActionListener(actionListener);      
	}
	/**
	 * Связывает выпадающий список сетей и родителей с выпадающим списком кабелей. 
	 * Кабели сортируются по id. 
	 * @param NetsComboBox - выпадающий список сетей
	 * @param LinkedComboBox - связанный выпадающий список
	 * @param Type - тип кабелей
	 */
	public void netsCableComboLinked(final Integer netId/*final JComboBox NetsComboBox*/, final JComboBox FromComboBox, final JComboBox ToComboBox, final JComboBox LinkedComboBox, final Integer Type) {
		
		ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	LinkedComboBox.removeAllItems();
            	if (Type < 2)
            		if (netId > -1 && FromComboBox.getSelectedIndex() > -1 && ToComboBox.getSelectedIndex() > -1) {
            			
            			setComboBoxItems(LinkedComboBox, sys.cc.sortByIdUp(sys.cc.getInOwners(Type, ((StructuredElement)FromComboBox.getSelectedItem()).getId(), ((StructuredElement)ToComboBox.getSelectedItem()).getId())));               
            		}
            	
            	if (Type >= 2) {
        			if (netId > -1 && FromComboBox.getSelectedIndex() > -1) {
        				setComboBoxItems(LinkedComboBox, sys.cc.sortByIdUp(sys.cc.getDCableOut((StructuredElement)FromComboBox.getSelectedItem())));
        			}
        		}
            	
            }
        };
     //   NetsComboBox.addActionListener(actionListener);
        FromComboBox.addActionListener(actionListener);
        ToComboBox.addActionListener(actionListener);
        
	}
	/**
	 * Связывает выпадающий список сетей и таблицу кабелей
	 * @param netsComboBox - выпадающий список сетей
	 * @param cableTable - таблица кабелей
	 */
/*	public void linkNetsComboBoxCableTable (final JComboBox netsComboBox, final JTable cableTable) {
		
		ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
            	if (netsComboBox.getSelectedIndex() > -1) {
            		clearTable(cableTable);
            		Iterator<StructuredElement> i = sys.cc.getInNet(((Net)netsComboBox.getSelectedItem()).getId()).iterator();
            		while (i.hasNext()) {
            			addCableToTable(cableTable, (Cable)i.next());
            		}
            	}
            }
		};
        
		if (netsComboBox.getSelectedIndex() > -1) {
			clearTable(cableTable);
			Iterator<StructuredElement> i = sys.cc.getInNet(((Net)netsComboBox.getSelectedItem()).getId()).iterator();
    		while (i.hasNext()) {
    			addCableToTable(cableTable, (Cable)i.next());
    		}
		}
		
    	netsComboBox.addActionListener(actionListener);
        
	}*/
	/**
	 * Связывает выпадающий список сетей и таблицу абонентов
	 * @param netsComboBox - выпадающий список сетей
	 * @param subscriberTable - таблица абонентов
	 */
/*	public void linkNetsComboBoxSubscriberTable (final JComboBox netsComboBox, final JTable subscriberTable) {
		
		ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
            	if (netsComboBox.getSelectedIndex() > -1) {
            		clearTable(subscriberTable);
            		Iterator<StructuredElement> i = sys.sc.getInNet(((Net)netsComboBox.getSelectedItem()).getId()).iterator();
            		while (i.hasNext()) {
            			addSubscriberToTable(subscriberTable, (Subscriber)i.next());
            		}
            	}
            }
		};
        
		if (netsComboBox.getSelectedIndex() > -1) {
			clearTable(subscriberTable);
			Iterator<StructuredElement> i = sys.sc.getInNet(((Net)netsComboBox.getSelectedItem()).getId()).iterator();
    		while (i.hasNext()) {
    			addSubscriberToTable(subscriberTable, (Subscriber)i.next());
    		}
		}
		
    	netsComboBox.addActionListener(actionListener);
        
	}*/
	
	/**
	 * Связывает выпадающий список кроссов с выпадающим списком громполос
	 * @param dframeComboBox - список кроссов
	 * @param LinkedComboBox - список громполос
	 */
	public void dframeComboBoxLinked(final JComboBox dframeComboBox, final JComboBox LinkedComboBox) {
		
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
            	LinkedComboBox.removeAllItems();
            	if (dframeComboBox.getSelectedIndex() > -1)
            	setComboBoxItems(LinkedComboBox, sys.fc.sortByIdUp(sys.fc.getInOwner(((DFramе)dframeComboBox.getSelectedItem()).getId())));
            
            }
        };
       
        LinkedComboBox.removeAllItems();
    	if (dframeComboBox.getSelectedIndex() > -1)
    	setComboBoxItems(LinkedComboBox, sys.fc.sortByIdUp(sys.fc.getInOwner(((DFramе)dframeComboBox.getSelectedItem()).getId())));
        
    	dframeComboBox.addActionListener(actionListener);
        
	}
	/**
	 * Связывает список шкафов с выпадающим списком боксов
	 * @param cabinetComboBox - список шкафов
	 * @param LinkedComboBox - список боксов
	 * @param Type - тип отображаемых боксов
	 */
	public void cabinetComboBoxLinked(final JComboBox cabinetComboBox, final JComboBox LinkedComboBox, final Integer Type) {
		
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
            	LinkedComboBox.removeAllItems();
            	if (cabinetComboBox.getSelectedIndex() > -1)
            		setComboBoxItems(LinkedComboBox, sys.bc.sortByIdUp(sys.bc.getInOwnerByTypeUniversal(((Cabinet)cabinetComboBox.getSelectedItem()).getId(), Type)));
            }
        };
        
        LinkedComboBox.removeAllItems();
        if (cabinetComboBox.getSelectedIndex() > -1)
        	setComboBoxItems(LinkedComboBox, sys.bc.sortByIdUp(sys.bc.getInOwnerByTypeUniversal(((Cabinet)cabinetComboBox.getSelectedItem()).getId(), Type)));
    	
    	cabinetComboBox.addActionListener(actionListener);
        
	}
	/**
	 * Связывает выпадающий список типов кбеля с выпадающимим списками структурных элементов, между которыми кабель может быть создан
	 * @param netsComboBox - список сетей
	 * @param typeComboBox - список типов кабеля
	 * @param fromComboBox - список для элементов, из которых выходит кабель
	 * @param toComboBox - список для элементов, в которые приходит кабель
	 */
	public void cableTypeComboLinked(final Integer netId /*final JComboBox netsComboBox*/, final JComboBox typeComboBox, final JComboBox fromComboBox, final JComboBox toComboBox){
		
		ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
            	if (netId > -1 /*netsComboBox.getSelectedIndex() > -1*/ && typeComboBox.getSelectedIndex() > -1) {
            		Integer type = typeComboBox.getSelectedIndex();
            //		Integer netId = ((Net)netsComboBox.getSelectedItem()).getId();
        	
            		fromComboBox.removeAllItems(); fromComboBox.setEnabled(true);
            		toComboBox.removeAllItems(); toComboBox.setEnabled(true);
			
            		if ( type.equals(0)) {
            			setComboBoxItems(fromComboBox, sys.dfc.sortByIdUp(sys.dfc.getInNet(netId)));
            			setComboBoxItems(toComboBox, sys.cbc.sortByNumberUp(sys.cbc.getInNetByClass(netId, 1)));
            		}
    	
            		if ( type.equals(1)) {
            			setComboBoxItems(fromComboBox, sys.cbc.sortByNumberUp(sys.cbc.getInNet(netId)));
            			setComboBoxItems(toComboBox, sys.cbc.sortByNumberUp(sys.cbc.getInNet(netId)));
            		}
    	
            		if ( type.equals(2)) {
            			setComboBoxItems(fromComboBox, sys.cbc.sortByNumberUp(sys.cbc.getInNet(netId)));
            			toComboBox.setEnabled(false);
            			//setComboBoxItems(toComboBox, dbc.sortByIdUp(dbc.getInNet(netId)));
            		}
    	
            		if ( type.equals(3)) {
            			setComboBoxItems(fromComboBox, sys.dfc.sortByIdUp(sys.dfc.getInNet(netId)));
            			toComboBox.setEnabled(false);
            			//setComboBoxItems(toComboBox, dbc.sortByIdUp(dbc.getInNet(netId)));
            		}
            	}
            }
		};
		typeComboBox.addActionListener(actionListener);
    //    netsComboBox.addActionListener(actionListener);
        
        if (netId > -1 /*netsComboBox.getSelectedIndex() > -1*/ && typeComboBox.getSelectedIndex() > -1) {
    		Integer type = typeComboBox.getSelectedIndex();
    	//	Integer netId = ((Net)netsComboBox.getSelectedItem()).getId();
	
    		fromComboBox.removeAllItems(); fromComboBox.setEnabled(true);
    		toComboBox.removeAllItems(); toComboBox.setEnabled(true);
	
    		if ( type.equals(0)) {
    			setComboBoxItems(fromComboBox, sys.dfc.sortByIdUp(sys.dfc.getInNet(netId)));
    			setComboBoxItems(toComboBox, sys.cbc.sortByNumberUp(sys.cbc.getInNetByClass(netId, 1)));
    		}

    		if ( type.equals(1)) {
    			setComboBoxItems(fromComboBox, sys.cbc.sortByNumberUp(sys.cbc.getInNet(netId)));
    			setComboBoxItems(toComboBox, sys.cbc.sortByNumberUp(sys.cbc.getInNet(netId)));
    		}

    		if ( type.equals(2)) {
    			setComboBoxItems(fromComboBox, sys.cbc.sortByNumberUp(sys.cbc.getInNet(netId)));
    			toComboBox.setEnabled(false);
    			//setComboBoxItems(toComboBox, dbc.sortByIdUp(dbc.getInNet(netId)));
    		}

    		if ( type.equals(3)) {
    			setComboBoxItems(fromComboBox, sys.dfc.sortByIdUp(sys.dfc.getInNet(netId)));
    			toComboBox.setEnabled(false);
    			//setComboBoxItems(toComboBox, dbc.sortByIdUp(dbc.getInNet(netId)));
    		}
    	}
		
	}
	/**
	 * Устанавливает (добавляет) элементы выпадающего списка
	 * @param ComboBox - выпадающий список
	 * @param Collection - коллекция элементов
	 */
	public void setComboBoxItems(final JComboBox ComboBox, Collection<?> Collection) {
		
		Iterator<?> i = Collection.iterator();
		while (i.hasNext())  {ComboBox.addItem((AbstractElement)i.next());}
		
		//ComboBox.setSelectedIndex(Collection.size() - 1);
	}
	/**
	 * Устанавливает элементы списка
	 * @param List - список
	 * @param Collection - коллекция элементов
	 */
	public void setListItems (final JList List, Collection<?> Collection) {
		
		((DefaultListModel)List.getModel()).clear();
			
		Iterator<?> listItem = Collection.iterator();
		while (listItem.hasNext()) ((DefaultListModel)List.getModel()).addElement(listItem.next());

	}
	/**
	 * Создает и выводит на экран форму создания/редактирования элемента Сеть
	 * @param net - сеть для редактирования, если null - отображается форма создания новой сети
	 */
	public void formNet(final Net net){
		
		final JDialog iFrame = newDialog("Создать сеть", 410, 165);
		newLabel("Название сети (1-50 символов):", iFrame, 20, 15, 360, 14);
		
		final JTextField name = newTextField(iFrame, 20, 40, 360, 25);
		if (net != null){ 
			iFrame.setTitle("Редактировать сеть");
			name.setText(net.getName());
		}
		
		JButton saveButton = newButton("Сохранить", iFrame, 20, 75, 110, 25);
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!sys.v.validateNetName(name.getText())) { newError(iFrame, "Неверный формат названия сети!");return;}
				if (net != null) {
					Net oldNet =  net;
					net.setName(name.getText());
					sys.rw.addLogMessage("Сеть изменена:" + oldNet.toString() +" => " + net.toString());
					newInfo(iFrame, "Изменения сохранены");
				}
				else {
					Net newNet = new Net(); 
					newNet.setName(name.getText()); 
					sys.nc.addElement(newNet);
					String mes = "Создана сеть: "+ newNet.toString();
					sys.rw.addLogMessage(mes);
					newInfo(iFrame, mes);
				}
				iFrame.dispose();
			}
		});
		
		iFrame.setVisible(true);
	}
	/**
	 * Создает и выводит на экран форму создания/редактирования элемента Здание
	 * @param building - здание для редактирования, если null - отображается форма создания нового здания
	 */
	public void formBuilding(final Building building){
		
		final JDialog iFrame = newDialog("Создать здание", 410, 345);
		
	//	newLabel("Сеть:", iFrame, 20, 15, 360, 25);
	//	final JComboBox comboBox = newNetsComboBox(iFrame, 20, 40, 360, 25);
		
		newLabel("Улица (до 150 символов):", iFrame, 20, 75, 360, 14);
		final JTextField street = newTextField(iFrame, 20, 100, 360, 25);
		
		newLabel("Дом № (1-4 символа: А-Я,а-я,0-9):", iFrame, 20, 135, 360, 14);
		final JTextField number = newTextField(iFrame, 20, 160, 360, 25);
		
		newLabel("Описание (до 150 символов):", iFrame, 20, 195, 360, 14);
		final JTextField name = newTextField(iFrame, 20, 220, 360, 25);
		
		if (building != null){ 
			iFrame.setTitle("Редактировать здание");
			
	//		comboBox.setSelectedItem(sys.nc.getElement(building.getNet()));
	//		comboBox.setEnabled(false);
			
			name.setText(building.getName());
			street.setText(building.getStreet());
			number.setText(building.getSNumber());
		}
		
		JButton saveButton = newButton("Сохранить", iFrame, 20, 260, 110, 25);
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
	//			if (comboBox.getSelectedIndex() == -1) { newError(iFrame, "Не выбрана сеть!"); return; }
				if (!sys.v.validateOtherParametr(street.getText())) { newError(iFrame, "Неверный формат названия улицы!");return;}
				if (!sys.v.validateBuildingNumber(number.getText())) { newError(iFrame, "Неверный формат номера дома!");return;}
				if (!sys.v.validateOtherParametr(name.getText())) { newError(iFrame, "Неверный формат названия здания!");return;}
				
				if (building != null) {
					Building oldBuilding =  building;
					building.setName(name.getText());
					building.setSNumber(number.getText());
					building.setStreet(street.getText());
					sys.rw.addLogMessage("Здание изменено:" + oldBuilding.toString() +" => " + building.toString());
					newInfo(iFrame, "Изменения сохранены");
				}
				else {
					Building newBuilding = new Building(); 
					newBuilding.setName(name.getText());
					newBuilding.setSNumber(number.getText());
					newBuilding.setStreet(street.getText());
			//		newBuilding.attachToNet((Net)comboBox.getSelectedItem());
					newBuilding.attachToNet((Net)sys.nc.getOnlyElement());
					sys.buc.addElement(newBuilding);
					String mes = "Создано здание: "+ newBuilding.toString();
					sys.rw.addLogMessage(mes);
					newInfo(iFrame, mes);
				}
				iFrame.dispose();
			}
		});
		
		iFrame.setVisible(true);
	}
	/**
	 * Создает и выводит на экран форму создания/редактирования элемента Включение
	 * @param sub - абонент, для которого создается включение
	 * @param path - включение для редактирования, если null - отображается форма создания новой сети
	 */
	public Path formPath(final Subscriber sub, final Path path){
		
		final Vector<Path> v = new Vector<Path>(); v.add(null);
		final JDialog iFrame = newDialog("Создать включение", 410, 225);
		newLabel("Название включения (1-50 символов):", iFrame, 20, 15, 360, 14);
		final JTextField textField = newTextField(iFrame, 20, 40, 360, 25);
		
		newLabel("Пара перехода (Ц-ЦЦ):", iFrame, 20, 75, 360, 14);
		final JTextField transitText = newTextField(iFrame, 20, 100, 360, 25);
		
		if (path != null){ 
			iFrame.setTitle("Редактировать включение");
			textField.setText(path.getName());
			transitText.setText(path.getTransit());
		}
		
		JButton saveButton = newButton("Сохранить", iFrame, 20, 135, 110, 25);
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!sys.v.validatePathName(textField.getText())) { newError(iFrame, "Неверный формат названия включения!");return;}
				if (!sys.v.validatePathTransit(transitText.getText())) { newError(iFrame, "Неверный формат пары перехода!");return;}
				
				if (path != null) {
					Path oldPath =  path;
					path.setName(textField.getText());
					path.setTransit(transitText.getText());
					sys.rw.addLogMessage("Включение изменено:" + oldPath.toString() +" => " + path.toString());
					newInfo(iFrame, "Изменения сохранены");
					v.set(0, path);
				}
				else {
					Path newPath = new Path(sys.sc,sys.pc); 
					newPath.setName(textField.getText());
					newPath.setTransit(transitText.getText());
					newPath.setSubscriber(sub);
					sys.phc.addElement(newPath);
					String mes = "Создано включение: "+ newPath.toString() + ", для абонента " + sub.toString();
					sys.rw.addLogMessage(mes);
					newInfo(iFrame, mes);
					v.set(0, newPath);
				}
				iFrame.dispose();
			}
		});
		
		iFrame.setVisible(true);
		return v.get(0);
	}
	/**
	 * Создает и выводит на экран форму создания/редактирования элемента "Абонент"
	 * @param sub - абонент для редактирования, если null - отображается форма создания нового абонента
	 */
	public Subscriber formSubscriber (final Subscriber sub) {
		
		final Vector<Subscriber> v = new Vector<Subscriber>(); v.add(null);
		final int iFrameMinWidth = 410, iFrameMaxWidth = 830, iFrameMinHeight = 280, iFrameMaxHeight = 280;
		
		final JDialog iFrame = newDialog("Создать абонента", iFrameMinWidth, iFrameMinHeight);
		
//		newLabel("Сеть:", iFrame, 20, 15, 360, 25);
//		final JComboBox comboBox = newNetsComboBox(iFrame, 20, 40, 360, 25);
		
		if (sub != null) {
			iFrame.setTitle("Редактировать абонента");
	//		comboBox.setSelectedItem(sys.nc.getElement(sub.getNet()));
	//		comboBox.setEnabled(false);
		}
		newLabel("Имя абонента (1-50 символов):", iFrame, 20, 75, 360, 25);
		final JTextField name = newTextField(iFrame, 20, 100, 360, 25);
		
		newLabel("Телефонный номер (3-7 цифр):", iFrame, 20, 135, 360, 25);
		final JTextField phoneNumber = newTextField(iFrame, 20, 160, 360, 25);

		if (sub != null){
			name.setText(sub.getName());
			phoneNumber.setText(sub.getPhoneNumber());
		}
		
		/*
		 * Дополнительные параметры абонента
		 */
		newLabel("Адрес:", iFrame, 420, 15, 360, 25);
		final JTextField subscriberAdress = newTextField(iFrame, 420, 40, 360, 25);
		
		newLabel("Дата установки:", iFrame, 420, 75, 360, 25);
		final JTextField subscriberDate = newTextField(iFrame, 420, 100, 360, 25);
		
		newLabel("Тип оборудования:", iFrame, 420, 135, 360, 25);
		final JTextField subscriberEquipment = newTextField(iFrame, 420, 160, 360, 25);
		
		if (sub != null) {
			subscriberAdress.setText(sub.getAdress());
			subscriberDate.setText(sub.getDate());
			subscriberEquipment.setText(sub.getEquipment());
		}
		/*
		 * ------------------------------
		 */
		
        JButton saveButton = newButton("Сохранить", iFrame, 20, 200, 110, 25);
        saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Net selectedNet = (Net)comboBox.getSelectedItem();
				Net selectedNet = (Net)sys.nc.getOnlyElement();
										
		//		if (comboBox.getSelectedIndex() == -1) { newError(iFrame, "Не выбрана сеть!"); return; }
				if (!sys.v.validateSubscriberName(name.getText())) {newError(iFrame, "Неверный формат имени абонента!"); return;}
				if (!sys.v.validatePhoneNumber(phoneNumber.getText())) {newError(iFrame, "Неверный формат номера телефона!"); return;}
				
				if (!sys.v.validateOtherParametr(subscriberAdress.getText())) { newError(iFrame, "Неверный формат адреса абонента (до 150 символов)!"); return; }
				if (!sys.v.validateOtherParametr(subscriberEquipment.getText())) { newError(iFrame, "Неверный формат типа оборудования (до 150 символов)!"); return; }
				if (!sys.v.validateOtherParametr(subscriberDate.getText())) { newError(iFrame, "Неверный формат даты установки абонента (до 150 символов)!"); return; }
				
				Subscriber s = (Subscriber)sys.sc.findByPhoneNumber(phoneNumber.getText(), selectedNet.getId());
										
				if (sub != null) {
					v.set(0, sub);
					if (s != null && sub.getId().equals(s.getId()) == false) 
						if (newDialog(iFrame, "Абонент с таким телефонным номером уже сущесвует в этой сети! \r\n Создать еще одного абонента с данным номером?") == JOptionPane.NO_OPTION) { return; }
					
					String oldSub = sub.toString();
					sub.setName(name.getText());
					sub
						.setPhoneNumber(phoneNumber.getText())
						.setDate(subscriberDate.getText())
						.setAdress(subscriberAdress.getText())
						.setEquipment(subscriberEquipment.getText());
					sys.rw.addLogMessage("Абонент изменен: " + oldSub + " => " + sub.toString());
					newInfo(iFrame, "Изменения сохранены");
				}
				else {
					if (s != null)
						if (newDialog(iFrame, "Абонент с таким телефонным номером уже сущесвует в этой сети! \r\n Создать еще одного абонента с данным номером?") == JOptionPane.NO_OPTION) { return; }
					
					Subscriber newSubscriber = new Subscriber();
					Path newPath = new Path(sys.sc,sys.pc);
					
					newSubscriber
						.setDate(subscriberDate.getText())
						.setAdress(subscriberAdress.getText())
						.setEquipment(subscriberEquipment.getText())
						.attachToNet(selectedNet)
						.setName(name.getText());
					newSubscriber.setPhoneNumber(phoneNumber.getText());
								
					sys.sc.addElement(newSubscriber);
					v.set(0, newSubscriber);
					newPath.setSubscriber(newSubscriber);
					sys.phc.addElement(newPath);
					
					String mes = "Создан абонент: "+ newSubscriber.toString()+ ", добавлен в сеть: "+ selectedNet.toString();
					sys.rw.addLogMessage(mes);
					newInfo(iFrame, mes);
				}
				iFrame.dispose();
			}
		});
        
		newMoreButton(iFrame,iFrameMinWidth,iFrameMaxWidth,iFrameMinHeight, iFrameMaxHeight, 320, 200, 60, 25);
		iFrame.setVisible(true);
		return v.get(0);
	}
	
	/**
	 * Создает и выводит на экран форму создания/редактирования кабеля
	 * @param cable - элемент "Кабель", если null - выводится форма создания нового элемента
	 */
	public Cable formCable(final Cable cable) {
		final Vector<Cable> v = new Vector<Cable>(); v.add(null);
		final int iFrameMinWidth = 410, iFrameMaxWidth = 830, iFrameMinHeight = 520, iFrameMaxHeight = 520;
		final JDialog iFrame = newDialog("Создать кабель", iFrameMinWidth, iFrameMinHeight);
		
	//	newLabel("Добавить в сеть:", iFrame, 20, 15, 360, 25);
	//	final JComboBox netsComboBox = newNetsComboBox(iFrame, 20, 40, 360, 25);
		
		newLabel("Тип кабеля:", iFrame, 20, 75, 360, 25);
		final JComboBox typeComboBox = new JComboBox();
		typeComboBox.addItem("Магистральный");
		typeComboBox.addItem("Межшкафной");
		typeComboBox.addItem("Распределительный");
		typeComboBox.addItem("Прямого питания");
		iFrame.getContentPane().add(typeComboBox);
		typeComboBox.setBounds(20, 100, 360, 25);
		
		newLabel("Идущий от:", iFrame, 20, 135, 360, 25);
		final JComboBox fromComboBox = new JComboBox();
		iFrame.getContentPane().add(fromComboBox);
		fromComboBox.setBounds(20, 160, 360, 25);
		
		newLabel("Приходящий в:", iFrame, 20, 195, 360, 25);
		final JComboBox toComboBox = new JComboBox();
		iFrame.getContentPane().add(toComboBox);
		toComboBox.setBounds(20, 220, 360, 25);
		cableTypeComboLinked(sys.nc.getOnlyElement().getId()/*netsComboBox*/, typeComboBox, fromComboBox, toComboBox);
		
		newLabel("Номер (0-999):", iFrame, 20, 255, 360, 25);
		final JTextField cableNumber  = newTextField(iFrame, 20, 280, 360, 25);
		if (cable != null)cableNumber.setText(cable.getNumber().toString());
		
		newLabel("Емкость кабеля:", iFrame, 20, 315, 360, 25);
		final JComboBox comboBox2 = new JComboBox();
		comboBox2.addItem((Integer)10);
		comboBox2.addItem((Integer)20);
		comboBox2.addItem((Integer)30);
		comboBox2.addItem((Integer)50);
		comboBox2.addItem((Integer)100);
		comboBox2.addItem((Integer)150);
		comboBox2.setSelectedIndex(4);
		iFrame.getContentPane().add(comboBox2);
		comboBox2.setBounds(20, 340, 360, 25);
		
		newLabel("Марка кабеля:", iFrame, 20, 375, 360, 25);
		final JComboBox comboBox3 = new JComboBox();
		comboBox3.addItem("ТГ");
		comboBox3.addItem("ТПП");
		comboBox3.setSelectedIndex(1);
		iFrame.getContentPane().add(comboBox3);
		comboBox3.setBounds(20, 400, 360, 25);
		
		if (cable != null) {
			iFrame.setTitle("Редактировать кабель");
	//		netsComboBox.setSelectedItem(sys.nc.getElement(cable.getNet()));
	//		netsComboBox.setEnabled(false);
			typeComboBox.setSelectedIndex(cable.getType());
			typeComboBox.setEnabled(false);
			comboBox2.setSelectedItem(cable.getCapacity());
			comboBox2.setEnabled(false);
			comboBox3.setSelectedItem(cable.getLabel());
			
			if (cable.getType().equals(0)) {
				fromComboBox.setSelectedItem(sys.dfc.getElement(cable.getFrom()));
				toComboBox.setSelectedItem(sys.cbc.getElement(cable.getTo()));
			}
			if (cable.getType().equals(1)) {
				fromComboBox.setSelectedItem(sys.cbc.getElement(cable.getFrom()));
				toComboBox.setSelectedItem(sys.cbc.getElement(cable.getTo()));
			}
			if (cable.getType().equals(2)) {
				fromComboBox.setSelectedItem(sys.cbc.getElement(cable.getFrom()));
				//toComboBox.setSelectedItem(dbc.getElement(cable.getTo()));
			}
			if (cable.getType().equals(3)) {
				fromComboBox.setSelectedItem(sys.dfc.getElement(cable.getFrom()));
				//toComboBox.setSelectedItem(dbc.getElement(cable.getTo()));
			}
			
			fromComboBox.setEnabled(false);
			toComboBox.setEnabled(false);
		}
		/*
		 * Дополнительные параметры кабеля
		 */
		newLabel("Диаметр жилы, мм (0,1-5,0):", iFrame, 420, 15, 360, 25);
		final JTextField cableWireDiametr = newTextField(iFrame, 420, 40, 360, 25);
		cableWireDiametr.setText("0,5");
		
		newLabel("Длина, м (1-9999):", iFrame, 420, 75, 360, 25);
		final JTextField cableLenght = newTextField(iFrame, 420, 100, 360, 25);
		cableLenght.setText("1");
		
		newLabel("Год протяжки (4 цифры):", iFrame, 420, 135, 360, 25);
		final JTextField cableYear = newTextField(iFrame, 420, 160, 360, 25);
		cableYear.setText("2011");
		
		newLabel("Состояние:", iFrame,  420, 195, 360, 25);
		final JComboBox cableStatus = new JComboBox();
		cableStatus.addItem("Новый");
		cableStatus.addItem("Бывший в эксплуатации");
		cableStatus.setSelectedIndex(0);
		cableStatus.setBounds(420, 220, 360, 25);
		iFrame.getContentPane().add(cableStatus);		
		
		if (cable != null) {
			cableWireDiametr.setText(cable.getWireDiametr());
			cableLenght.setText(cable.getLenght().toString());
			cableYear.setText(cable.getYear());
			cableStatus.setSelectedIndex(cable.getStatus());
		}
		/*
		 * ------------------------------
		 */
		JButton saveButton = newButton("Сохранить", iFrame, 20, 440, 110, 25);
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
									
	//			if (netsComboBox.getSelectedIndex() == -1) { newError(iFrame, "Не выбрана сеть!"); return; }
				if (!sys.v.validateCableNumber(cableNumber.getText())) { newError(iFrame, "Неверный формат номера кабеля!"); return;}						
				if (!sys.v.validateCableLenght(cableLenght.getText())) { newError(iFrame, "Неверный формат длины кабеля!"); return;}						
				if (!sys.v.validateCableWireDiametr(cableWireDiametr.getText())) { newError(iFrame, "Неверный формат диаметра жилы кабеля!"); return;}						
				if (!sys.v.validateCableYear(cableYear.getText())) { newError(iFrame, "Неверный формат года прокладки кабеля!"); return;}						
				
	//			Net selectedNet = (Net)netsComboBox.getSelectedItem();
				Integer type = typeComboBox.getSelectedIndex();
				Integer number = sys.rw.valueOf(cableNumber.getText());
				StructuredElement from = (StructuredElement)fromComboBox.getSelectedItem();
				StructuredElement to = (StructuredElement)toComboBox.getSelectedItem();
				
				
				if (cable != null) {
					v.set(0, cable);
					Cable b = sys.cc.getInOwner(from.getId(), number, type);
					//Если кабель не магистральный, то проверять оба конца, магистральные не проверять на начало - кросс. 
					//В кроссе может быть кабели одинакового номера и типа
				//	if (type > 0)						
						if (b != null && !cable.getId().equals(b.getId())) {
							if (newDialog(iFrame, "Кабель такого типа и номера уже существует в "+from.toString()+"! Создать еще один?") == JOptionPane.NO_OPTION)
							//newError(iFrame, "Кабель такого типа и номера уже существует в "+from.toString()+"!"); 
							return;
						}
					//if (type >= 0 && type < 2) {
					if (type < 2) {
						b = sys.cc.getInOwner(to.getId(), number, type);
						if (b != null && !cable.getId().equals(b.getId())) {
						//	newError(iFrame, "Кабель такого типа и номера уже существует в "+to.toString()+"!"); 
							if (newDialog(iFrame, "Кабель такого типа и номера уже существует в "+to.toString()+"! Создать еще один?") == JOptionPane.NO_OPTION)
								
							return;
						}
					}
					cable.setNumber(number);
					cable
						.setLabel((String)comboBox3.getSelectedItem())	
						.setLenght(sys.rw.valueOf(cableLenght.getText()))
						.setWireDiametr(cableWireDiametr.getText())
						.setYear(cableYear.getText())
						.setStatus(cableStatus.getSelectedIndex());
					
					sys.rw.addLogMessage("Кабель изменен: " +cable.toString());
					newInfo(iFrame, "Изменения сохранены");
					
				}
				else {
					//аналогично
					//if (type > 0)
						if (sys.cc.getInOwner(from.getId(), number, type) != null) {
						//	newError(iFrame, "Кабель такого типа и номера уже существует в "+from.toString()+"!"); 
							if (newDialog(iFrame, "Кабель такого типа и номера уже существует в "+from.toString()+"! Создать еще один?") == JOptionPane.NO_OPTION)
							return;
						}
					
					if (type.equals(1))
						if (from.getId().equals(to.getId())) {newError(iFrame, "Выберите разные шкафы!"); return; }
					
					if (type < 2)
						if (sys.cc.getInOwner(to.getId(), number, type) != null) {
							//newError(iFrame, "Кабель такого типа и номера уже существует в "+to.toString()+"!"); 
							if (newDialog(iFrame, "Кабель такого типа и номера уже существует в "+to.toString()+"! Создать еще один?") == JOptionPane.NO_OPTION)
							return;
						}
					//}
					
					Cable newCable = new Cable(sys.dfc,sys.cbc,sys.dbc,sys.fc,sys.bc,sys.pc); 
				//	newCable.attachToNet(selectedNet);
					newCable.attachToNet((Net)sys.nc.getOnlyElement());
						
					newCable
						.setType(type)
						.setLabel((String)comboBox3.getSelectedItem())
						.setLenght(sys.rw.valueOf(cableLenght.getText()))
						.setYear(cableYear.getText())
						.setStatus(cableStatus.getSelectedIndex())
						.setWireDiametr(cableWireDiametr.getText())
						.setCapacity((Integer)comboBox2.getSelectedItem())
						.setFrom(from.getId())
						.setNumber(number);
					if (type < 2) newCable.setTo(to.getId());
					if (type >= 2) newCable.setTo(0);
					
					sys.cc.addElement(newCable); v.set(0, newCable);
					String mes = "Создан "+(String)typeComboBox.getSelectedItem()+" кабель: "+ newCable.toString()+ ", присоединён к сети: "+ sys.nc.getOnlyElement().toString();
					newInfo(iFrame, mes);
					sys.rw.addLogMessage(mes);
				}
				iFrame.dispose();
			}
		});
		
		newMoreButton(iFrame,iFrameMinWidth,iFrameMaxWidth,iFrameMinHeight, iFrameMaxHeight, 320, 440, 60, 25);
		iFrame.setVisible(true);
		
		return v.get(0);
	}
	/**
	 * Создает и выводит на экран форму создания/редактирования колодца
	 * @param man - колодец, если null - выводится форма создания нового элемента
	 */
	
	public void formManhole(final Manhole man) {

		final int iFrameMinWidth = 410, iFrameMaxWidth = 830, iFrameMinHeight = 370, iFrameMaxHeight = 370;
		
		final JDialog iFrame = newDialog("Создать колодец", iFrameMinWidth, iFrameMinHeight);
		 
	//	newLabel("Добавить в сеть:", iFrame, 20, 15, 360, 25);
	//	final JComboBox comboBox = newNetsComboBox(iFrame, 20, 40, 360, 25);
		
		newLabel("Номер колодца (1-4 символа: А-Я,а-я,0-9):", iFrame, 20, 75, 360, 25);
		final JTextField manholeNumberText = newTextField(iFrame, 20, 100, 360, 25);
		
		newLabel("Конструкция:", iFrame, 20, 135, 360, 25);
		final JComboBox manholeConstruction = new JComboBox();
		manholeConstruction.addItem("Железобетонный");
		manholeConstruction.addItem("Ктрпичный");
		manholeConstruction.setSelectedIndex(0);
		manholeConstruction.setBounds(20, 160, 360, 25);
		iFrame.getContentPane().add(manholeConstruction);		
		
		newLabel("Форма:", iFrame, 20, 195, 360, 25);
		final JComboBox manholeForm = new JComboBox();
		manholeForm.addItem("Овальный");
		manholeForm.addItem("Прямоугольный");
		manholeForm.setSelectedIndex(0);
		manholeForm.setBounds(20, 220, 360, 25);
		iFrame.getContentPane().add(manholeForm);		
		
		/*
		 * Дополнительные параметры колодца
		 */
		newLabel("Адрес:", iFrame, 420, 15, 360, 25);
		final JTextField manholeAdress = newTextField(iFrame, 420, 40, 360, 25);
		
		newLabel("Дата постройки (ДД.ММ.ГГГГ):", iFrame, 420, 75, 360, 25);
		final JTextField manholeDate = newTextField(iFrame, 420, 100, 360, 25);
		manholeDate.setText("01.01.2012");
		
		newLabel("Размеры (для нетиповых):", iFrame, 420, 135, 360, 25);
		final JTextField manholeSize = newTextField(iFrame, 420, 160, 360, 25);
			
		if (man != null) {
			manholeAdress.setText(man.getAdress());
			manholeDate.setText(man.getDate());
			manholeSize.setText(man.getSize());	
		}
		/*
		 * ------------------------------
		 */
		
		if (man != null) {
			
			iFrame.setTitle("Редактировать колодец");
			
		//	comboBox.setSelectedItem(sys.nc.getElement(man.getNet()));
		//	comboBox.setEnabled(false);
			manholeNumberText.setText(man.getSNumber());
			manholeConstruction.setSelectedIndex(man.getConstruction());
			manholeForm.setSelectedIndex(man.getForm());
			
		}
		
		JButton saveButton = newButton("Сохранить", iFrame, 20, 280, 110, 25);
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
		//		if (comboBox.getSelectedIndex() == -1) { newError(iFrame, "Не выбрана сеть!"); return; }
				if (!sys.v.validateCabinetNumber(manholeNumberText.getText())) { newError(iFrame, "Неверный номер колодца!"); return; }
				if (!sys.v.validateDate(manholeDate.getText())) { newError(iFrame, "Неверный формат даты постройки!"); return; }
				if (!sys.v.validateOtherParametr(manholeAdress.getText())) { newError(iFrame, "Неверный формат адреса колодца!"); return; }
				if (!sys.v.validateOtherParametr(manholeSize.getText())) { newError(iFrame, "Неверный формат размера колодца!"); return; }
				
		//		Net selectedNet = (Net)comboBox.getSelectedItem();
				String manholeNumber = manholeNumberText.getText();

				if (man != null) {
					
					Manhole b = sys.mc.elementInNet(manholeNumber, sys.nc.getOnlyElement().getId()/*selectedNet.getId()*/);
					if (b != null && !man.getId().equals(b.getId())) {newError(iFrame, "Колодец с номером " + manholeNumber + " уже сущесвует в этой сети"); return;}
					
					String old = man.toString();
					man
						.setAdress(manholeAdress.getText())
						.setDate(manholeDate.getText())
						.setSize(manholeSize.getText())
						.setConstruction(manholeConstruction.getSelectedIndex())
						.setForm(manholeForm.getSelectedIndex())
						.setSNumber(manholeNumber);
					sys.rw.addLogMessage("Колодец изменен: " + old + " => " + man.toString());
					newInfo(iFrame, "Изменения сохранены");
				}
				else {
					
					if (sys.mc.elementInNet(manholeNumber, sys.nc.getOnlyElement().getId()/*selectedNet.getId()*/) != null) {
						newError(iFrame, "Колодец с номером "+manholeNumber+" уже сущесвует в этой сети");
						return;
					}
					
					Manhole newManhole = new Manhole(); 
					newManhole
						.setAdress(manholeAdress.getText())
						.setDate(manholeDate.getText())
						.setSize(manholeSize.getText())
						.setConstruction(manholeConstruction.getSelectedIndex())
						.setForm(manholeForm.getSelectedIndex())
						//.attachToNet(selectedNet)
						.attachToNet((Net)sys.nc.getOnlyElement())
						.setSNumber(manholeNumber);
						
					sys.mc.addElement(newManhole);
					String mes = "Создан колодец: "+ newManhole.toString()+ ", добавлен в сеть: "+ sys.nc.getOnlyElement().toString()/*selectedNet.toString()*/;
					sys.rw.addLogMessage(mes);
					newInfo(iFrame, mes);
				}
				iFrame.dispose();
			}
		});
		newMoreButton(iFrame,iFrameMinWidth,iFrameMaxWidth,iFrameMinHeight, iFrameMaxHeight, 320, 280, 60, 25);

		iFrame.setVisible(true);
	}
	/**
	 * Создает и выводит на экран форму создания/редактирования элемента "Кабельная канализация"
	 * @param duct - элемент "Кабельная канализация", если null - выводится форма создания нового элемента
	 */
	public void formDuct(final Duct duct){
		
		final int iFrameMinWidth = 410, iFrameMaxWidth = 830, iFrameMinHeight =  430, iFrameMaxHeight =  430;

		final JDialog iFrame = newDialog("Создать канализацию", iFrameMinWidth, iFrameMinHeight);
		
	//	newLabel("Добавить в сеть:", iFrame, 20, 15, 360, 25);
	//	final JComboBox netsComboBox = newNetsComboBox(iFrame, 20, 40, 360, 25);
		
		newLabel("От:", iFrame, 20, 75, 360, 25);
		final JComboBox fromComboBox = manholeDFrameComboBox(sys.nc.getOnlyElement().getId()/*netsComboBox*/, iFrame, 20, 100, 360, 25);
		final JComboBox fromSideComboBox = new JComboBox();
		fromSideComboBox.addItem("Спереди");
		fromSideComboBox.addItem("Справа");
		fromSideComboBox.addItem("Сзади");
		fromSideComboBox.addItem("Слева");
		iFrame.getContentPane().add(fromSideComboBox);
		fromSideComboBox.setBounds(20, 135, 360, 25);
		
		newLabel("До:", iFrame, 20, 170, 360, 25);
		final JComboBox toComboBox = manholeCabinetBuildingComboBox(sys.nc.getOnlyElement().getId()/*netsComboBox*/, iFrame, 20, 195, 360, 25);
		final JComboBox toSideComboBox = new JComboBox();
		toSideComboBox.addItem("Спереди");
		toSideComboBox.addItem("Справа");
		toSideComboBox.addItem("Сзади");
		toSideComboBox.addItem("Слева");
		iFrame.getContentPane().add(toSideComboBox);
		toSideComboBox.setBounds(20, 230, 360, 25);
		
	//	netsManholeDFrameComboLinked(netsComboBox,fromComboBox);
	//	netsManholeCabinetBuildingComboLinked(netsComboBox,toComboBox);
		
		newLabel("Каналов в канализации (1-99):", iFrame, 20, 265, 360, 25);
		final JTextField capacityText = newTextField(iFrame, 20, 290, 360, 25);
		
		/*
		 * Дополнительные параметры канализации
		 */
		newLabel("Длина, м (1-9999):", iFrame, 420, 15, 360, 25);
		final JTextField ductLenght = newTextField(iFrame, 420, 40, 360, 25);
		ductLenght.setText("1");
		
		newLabel("Диаметр канала, мм (1-999):", iFrame, 420, 75, 360, 25);
		final JTextField tubeDiametr = newTextField(iFrame, 420, 100, 360, 25);
		tubeDiametr.setText("100");
		
		newLabel("Материал трубопровода (до 150 сим.):", iFrame, 420, 135, 360, 25);
		final JTextField tubeMaterual = newTextField(iFrame, 420, 160, 360, 25);
		
		newLabel("Дата прокладки (ДД.ММ.ГГГГ):", iFrame, 420, 195, 360, 25);
		final JTextField ductDate = newTextField(iFrame, 420, 220, 360, 25);
		ductDate.setText("01.01.2012");
		
		newLabel("Cпособ изготовления (до 150 сим.):", iFrame, 420, 255, 360, 25);
		final JTextField manufacturingМethod = newTextField(iFrame, 420, 280, 360, 25);
		
		
		
		JButton damageButton = newButton("Повреждения", iFrame, 420, 340, 120, 25);
		damageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tableDamageList(sys.dmc.getDamages(duct), duct);
			}
		});
		damageButton.setEnabled(false);
		
		if (duct != null) {
			ductLenght.setText(duct.getLenght().toString());
			tubeDiametr.setText(duct.getTubeDiametr().toString());
			tubeMaterual.setText(duct.getTubeMaterial());
			ductDate.setText(duct.getDate());
			manufacturingМethod.setText(duct.getМanufacturingМethod());
			damageButton.setEnabled(true);
		}
		/*
		 * ------------------------------
		 */
		if (duct != null) {
			iFrame.setTitle("Редактировать канализацию");
			
		//	netsComboBox.setSelectedItem(sys.nc.getElement(duct.getNet())); netsComboBox.setEnabled(false);
			
			AbstractElement from = sys.mc.getElement(duct.getFrom());
			if (from == null) from = sys.dfc.getElement(duct.getFrom());
			
			AbstractElement to = sys.mc.getElement(duct.getTo());
			if (to == null) to = sys.cbc.getElement(duct.getTo());
			if (to == null) to = sys.buc.getElement(duct.getTo());
			
			
			fromComboBox.setSelectedItem(from);
			toComboBox.setSelectedItem(to);
			
			fromSideComboBox.setSelectedIndex(duct.getFromSide());
			toSideComboBox.setSelectedIndex(duct.getToSide());
			
			capacityText.setText(((Integer)sys.tuc.getDuctsTubes(duct).size()).toString()); capacityText.setEnabled(false);
		}
        
		JButton saveButton = newButton("Сохранить", iFrame, 20, 340, 110, 25);
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
		//		if (netsComboBox.getSelectedIndex() == -1) { newError(iFrame, "Не выбрана сеть!"); return; }
				if (fromComboBox.getSelectedIndex() == -1) { newError(iFrame, "Не выбрано начало канализации!"); return; }
				if (toComboBox.getSelectedIndex() == -1) { newError(iFrame, "Не выбран конец канализации!"); return; }
				if (!sys.v.validateDuctCapacity(capacityText.getText())) { newError(iFrame, "Неверный формат емкости канализации!"); return; }
				if (!sys.v.validateDuctLenght(ductLenght.getText())) { newError(iFrame, "Неверный формат длины канализации!"); return; }
				if (!sys.v.validateTubeDiametr(tubeDiametr.getText())) { newError(iFrame, "Неверный формат диаметра канала!"); return; }
				if (!sys.v.validateDate(ductDate.getText())) { newError(iFrame, "Неверный формат даты!"); return; }
				if (!sys.v.validateOtherParametr(tubeMaterual.getText())) { newError(iFrame, "Неверный формат материала трубопровода!"); return; }
				if (!sys.v.validateOtherParametr(manufacturingМethod.getText())) { newError(iFrame, "Неверный формат способа изготовления!"); return; }
				
		//		Net selectedNet = (Net)netsComboBox.getSelectedItem();
				Integer capacity = sys.rw.valueOf(capacityText.getText());
				StructuredElement elementFrom = (StructuredElement)fromComboBox.getSelectedItem();
				StructuredElement elementTo = (StructuredElement)toComboBox.getSelectedItem();
				Integer fromSide = fromSideComboBox.getSelectedIndex();
				Integer toSide = toSideComboBox.getSelectedIndex();
				
			//	Duct d1 = duc.hasDuct(elementFrom, fromSide);
			//	Duct d2 = duc.hasDuct(elementTo, toSide);
				
				if (elementFrom.getId().equals(elementTo.getId())) { newError(iFrame, "Выберите разные элементы От и До!"); return; }

				if (duct != null) {
					
			//		if (d1 != null && !duct.getId().equals(d1.getId())) { newError(iFrame, "К элементу От " + elementFrom.toString() + " с выбранной стороны уже примыкает участок канализации " + d1.toString()); return; }
			//		if (d2 != null && !duct.getId().equals(d2.getId())) { newError(iFrame, "К элементу До " + elementTo.toString() + " с выбранной стороны уже примыкает участок канализации " + d2.toString()); return; }
					
				/*	
					Cabinet b = cbc.elementInNet(cabinetNumber, selectedNet.getId());
					if (b != null && !cabinet.getId().equals(b.getId())) {newError(iFrame, "Шкаф с номером "+cabinetNumber+" уже сущесвует в этой сети"); return;}
				*/	
					String old = duct.toString();
					duct
						.setFromSide(fromSide)
						.setToSide(toSide)
						.setLenght(sys.rw.valueOf(ductLenght.getText()))
						.setTubeDiametr(sys.rw.valueOf(tubeDiametr.getText()))
						.setDate(ductDate.getText())
						.setTubeMaterial(tubeMaterual.getText())
						.setМanufacturingМethod(manufacturingМethod.getText())
						.setFrom(elementFrom.getId())
						.setTo(elementTo.getId());
					sys.rw.addLogMessage("Участок канализации изменен: " + old + " => " + duct.toString());
					newInfo(iFrame, "Изменения сохранены");
				}
				else {
				/*	
					if (cbc.elementInNet(cabinetNumber, selectedNet.getId()) != null) {
						newError(iFrame, "Шкаф с номером "+cabinetNumber+" уже сущесвует в этой сети");
						return;
					}
				*/	
				//	if (d1 != null) { newError(iFrame, "К элементу От  " + elementFrom.toString() + " с выбранной стороны уже примыкает участок канализации " + d1.toString()); return; }
				//	if (d2 != null) { newError(iFrame, "К элементу До  " + elementTo.toString() + " с выбранной стороны уже примыкает участок канализации " + d2.toString()); return; }
					
					Duct newDuct = new Duct(sys.dfc, sys.cbc, sys.mc, sys.buc); 
					newDuct
						.setFromSide(fromSide)
						.setToSide(toSide)
						.setLenght(sys.rw.valueOf(ductLenght.getText()))
						.setTubeDiametr(sys.rw.valueOf(tubeDiametr.getText()))
						.setDate(ductDate.getText())
						.setTubeMaterial(tubeMaterual.getText())
						.setМanufacturingМethod(manufacturingМethod.getText())
						.setFrom(elementFrom.getId())
						.setTo(elementTo.getId())
						//.attachToNet(selectedNet);
						.attachToNet((Net)sys.nc.getOnlyElement());
					sys.duc.addElement(newDuct);
					String mes = "Создан участок канализации: "+ newDuct.toString()+ ", добавлен в сеть: "+ sys.nc.getOnlyElement().toString()/*selectedNet.toString()*/;
					sys.rw.addLogMessage(mes);
					
					for (int i = 0; i < capacity; i++) {
						Tube tube = new Tube();
						tube.setNumber(i);
						tube.setDuct(newDuct);
						sys.tuc.addElement(tube);
						sys.rw.addLogMessage("Создан канал №" + tube.getNumber().toString() + "в канализации " + newDuct.toString());
					}
					newInfo(iFrame, mes);
				}
				iFrame.dispose();			
			}
		});
		
		newMoreButton(iFrame, iFrameMinWidth, iFrameMaxWidth, iFrameMinHeight, iFrameMaxHeight, 320, 340, 60, 25);

		iFrame.setVisible(true);
				
	}

	/**
	 * Создает и выводит на экран форму поиска абонента
	 * @param netId - id сети
	 * @return выбранного среди найденых абонента или null, если ничего не выбрано
	 */
	public FormSubscriberSearch formSearchSubscriber () {
	
		return new FormSubscriberSearch(sys);
	}
	/**
	 * Создает и выводит на экран форму поиска кабеля
	 * @param netId - id сети
	 * @return выбранного среди найденых кабелей или null, если ничего не выбрано
	 */
	public Cable formSearchCable (final Integer netId) {
		final Vector<Cable> v = new Vector<Cable>(); v.add(null);
		
		final JDialog iFrame = newDialog("Выбрать кабель", 685, 600);
			
		final JTable cableTable = newTable(iFrame, 10, 10, 520, 540);
		final DefaultTableModel tableModel = (DefaultTableModel) cableTable.getModel();
		tableModel.setColumnIdentifiers(new String[]{"Кабель","От","До","Емкость","Исп.емкость","Длина"});
		
		clearTable(cableTable);
		Iterator<StructuredElement> i = sys.cc.getInNet(netId).iterator();
		while (i.hasNext()) { addCableToTable(cableTable, (Cable)i.next()); }
				
		JButton okButton = newButton("Выбрать", iFrame, 540, 10, 125, 26);
		
		/*
		 * Событие кнопки выбора кабеля
		 */	
		ActionListener selectCable = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (cableTable.getSelectionModel().isSelectionEmpty()){ newError(iFrame, "Кабель не выбран!"); return; }
				int selectedIndex = cableTable.getRowSorter().convertRowIndexToModel(cableTable.getSelectionModel().getMinSelectionIndex());
				v.set(0, (Cable)tableModel.getValueAt( selectedIndex, 0));
				iFrame.dispose();
			}
		};
		okButton.addActionListener(selectCable);
		/*
		 * ---------------------------------------------------------
		 */
		iFrame.setVisible(true);
		
		return v.get(0);
	}
	/**
	 * Создает и выводит на экран форму выбора кабеля в канале канализации
	 * @param tube - канал
	 * @return выбранное включение, либо null если ничего не выбрано
	 */
	public Cable formTubesCables(Tube tube) {
		
		final Vector<Cable> v = new Vector<Cable>(); v.add(null);
		final JDialog iFrame = newDialog("Канал: " + tube.toString(), 485, 270);
		
		newLabel("Кабели в канале:", iFrame, 10, 10, 320, 14);
		final JList cableList = newList(iFrame, 10, 30, 320, 200);
		setListItems(cableList, sys.cc.getTubesCables(tube));
		cableList.setSelectedIndex(0);
		
		JButton okButton = newButton("Выбрать", iFrame, 340, 30, 125, 26);
		/*
		 * Событие кнопки выбора кабеля
		 */	
		ActionListener selectPath = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (cableList.getSelectedIndex() == -1) {newError(iFrame,"Кабель не выбран!"); return;}
				v.set(0, (Cable)cableList.getSelectedValue());
				iFrame.dispose();	
			}
		};
		okButton.addActionListener(selectPath);
		/*
		 * ---------------------------------------------------------
		 */
		iFrame.setVisible(true);
		
		return v.get(0);
		
	}
	/**
	 * Создает и выводит на экран форму выбора включений данной пары
	 * @param pair - пара
	 * @return форма
	 */
	public FormPairPaths formPairPaths(Pair p) {
		
		final FormPairPaths form = new FormPairPaths(sys,p);
		return form;
		
	}
	/**
	 * Создает и выводит на экран форму создания/редактирования повреждения
	 * @param damage - повреждение для редактирования, если null - отображается форма создания нового повреждения
	 * @return повреждение
	 */
	public Damage formDamage(final Damage damage) {
		final Vector<Damage> v = new Vector<Damage>(); v.add(null);
		
		final JDialog iFrame = newDialog("Создать повреждение", 410, 445);
		
		newLabel("Дата обнаружения (ДД.ММ.ГГГГ):", iFrame,  20, 15, 360, 25);
		final JTextField openDate = newTextField(iFrame, 20, 40, 360, 25);
		
		newLabel("Дата устранения (ДД.ММ.ГГГГ):", iFrame, 20, 75, 360, 14);
		final JTextField closeDate = newTextField(iFrame, 20, 100, 360, 25);
		
		newLabel("Характер повреждения (до 300 символов):", iFrame, 20, 135, 360, 14);
		final JTextArea name = newTextArea(iFrame, 20, 160, 360, 75);
		name.setEditable(true);
		
		newLabel("Работы по устранению (до 300 символов):", iFrame, 20, 250, 360, 14);
		final JTextArea description = newTextArea(iFrame, 20, 275, 360, 75);
		description.setEditable(true);
		
		if (damage != null){ 
			iFrame.setTitle("Редактировать повреждение");
			openDate.setText(damage.getOpenDate());
			closeDate.setText(damage.getCloseDate());
			name.setText(damage.getName());
			description.setText(damage.getDescription());
		}
		
		JButton saveButton = newButton("Сохранить", iFrame, 20, 370, 110, 25);
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if (!sys.v.validateDate(openDate.getText())) { newError(iFrame, "Неверный формат даты обнаружения!");return;}
				if (!sys.v.validateDate(closeDate.getText())) { newError(iFrame, "Неверный формат даты устранения!");return;}	
				if (!sys.v.validateLongParametr(name.getText())) { newError(iFrame, "Неверный формат характера повреждения!");return;}
				if (!sys.v.validateLongParametr(description.getText())) { newError(iFrame, "Неверный формат описания повреждения!");return;}
				
				if (damage != null) {
					
					damage.setName(name.getText());
					damage.setOpenDate(openDate.getText());
					damage.setCloseDate(closeDate.getText());
					damage.setDescription(description.getText());
					v.set(0, damage);
					sys.rw.addLogMessage("Повреждение изменено:"  + damage.toString());
					newInfo(iFrame, "Изменения сохранены");
				}
				else {
					Damage newDamage = new Damage(); 
					newDamage.setName(name.getText());
					newDamage.setOpenDate(openDate.getText());
					newDamage.setCloseDate(closeDate.getText());
					newDamage.setDescription(description.getText());
					sys.dmc.addElement(newDamage);
					v.set(0, newDamage);
					String mes = "Создано повреждение: "+ newDamage.toString();
					sys.rw.addLogMessage(mes);
					newInfo(iFrame, mes);
				}
				iFrame.dispose();
			}
		});
		iFrame.setVisible(true);
		
		return v.get(0);
	}
	/**
	 * Создает и выводит на экран форму создания набора участков канализации
	 * @param net - сеть
	 * @return
	 */
	public Vector<Duct> formCreateDuctsSet(Net net){
		
		final Vector<Duct> d = new Vector<Duct>();
		final JDialog iFrame = newDialog("Создание списка элементов канализации для паспорта",800,600);
		newLabel("Участки канализации включенные в паспорт:", iFrame, 10, 10, 320, 14);
		final JList setList = newList(iFrame, 10, 30, 320, 520);
		newLabel("Участки канализации не включенные в паспорт:", iFrame, 460, 10, 320, 14);
		final JList fullList = newList(iFrame, 460, 30, 320, 520);
		
		final DefaultListModel setListModel = (DefaultListModel)setList.getModel();
		final DefaultListModel fullListModel = (DefaultListModel)fullList.getModel();
		
		JButton addButton = newButton("<", iFrame, 350, 60, 90, 26); addButton.setToolTipText("Включить участок канализации в паспорт");
		JButton removeButton = newButton(">", iFrame, 350, 110, 90, 26); removeButton.setToolTipText("Исключить участок канализации из паспорта");
		
		JButton okButton = newButton("OK", iFrame, 350, 520, 90, 26);
		
		setListItems(fullList, sys.duc.sortByIdUp(sys.duc.getInNet(net)));
		
		/*
		 * Событие кнопки добавления канализации в паспорт
		 */
		ActionListener addDuct = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (fullList.getSelectedIndex() == -1) { newError(iFrame, "Участок канализации не выбран!"); return; }
				setListModel.addElement(fullList.getSelectedValue());
				fullListModel.remove(fullList.getSelectedIndex());
			}
		};
		addButton.addActionListener(addDuct);
		/*
		 * ---------------------------------------------------------
		 */
		
		/*
		 * Событие кнопки исключения канализации из паспорта
		 */
		ActionListener removeDuct = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (setList.getSelectedIndex() == -1) { newError(iFrame, "Участок канализации не выбран!"); return; }
				fullListModel.addElement(setList.getSelectedValue());
				setListModel.remove(setList.getSelectedIndex());
			}
		};
		removeButton.addActionListener(removeDuct);
		/*
		 * ---------------------------------------------------------
		 */
		
		/*
		 * Событие кнопки OK
		 */
		ActionListener ok = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (setListModel.getSize() > 0) { 
				for (int i = 0; i < setListModel.getSize(); i++) d.add((Duct)setListModel.getElementAt(i));
				iFrame.dispose();
				}
			}
		};
		okButton.addActionListener(ok);
		/*
		 * ---------------------------------------------------------
		 */
		
		iFrame.setVisible(true);
		return d;
	}
	
	public JDialog tableDamageList(Collection<Damage> damageCollection, final AbstractElement owner) {
		
		final JDialog iFrame = newDialog("Список повреждений", 685, 600);
		iFrame.setResizable(true);
		
		final JTable damageTable = newTable(iFrame, 10, 10, 520, 560);
		final DefaultTableModel tableModel = (DefaultTableModel) damageTable.getModel();
		tableModel.setColumnIdentifiers(new String[]{"Характер повреждения","Дата устранения","Дата обнаружения"});
		
		
		JButton editButton = newButton("Редактировать", iFrame, 540, 10, 125, 26);
		
		JButton createButton = newButton("Добавить", iFrame, 540, 80, 125, 26);
		JButton deleteButton = newButton("Удалить", iFrame, 540, 120, 125, 26);
		
		Iterator<Damage> i = damageCollection.iterator();
		while (i.hasNext()) {
			addDamageToTable(damageTable, i.next());
		}
		/*
		 * Событие кнопки редактирования повреждения
		 */
		ActionListener editDamage = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (damageTable.getSelectionModel().isSelectionEmpty()){ newError(iFrame, "Повреждение не выбрано!"); return; }
				int selectedIndex = damageTable.getRowSorter().convertRowIndexToModel(damageTable.getSelectionModel().getMinSelectionIndex());
				Damage damage = (Damage)tableModel.getValueAt(selectedIndex, 0);
				formDamage(damage);
				updateDamageInTable(damageTable, damage, selectedIndex);
			}
		};
		editButton.addActionListener(editDamage);
		/*
		 * ---------------------------------------------------------
		 */
		/*
		 * Событие кнопки создания повреждения
		 */
		ActionListener createCable = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Damage damage = formDamage(null);
				if (damage != null) {
					damage.attachTo(owner);
					addDamageToTable(damageTable, damage);
				}
			}
		};
		createButton.addActionListener(createCable);
		/*
		 * ---------------------------------------------------------
		 */
		/*
		 * Событие кнопки удаления повреждения
		 */
		ActionListener deleteDamage = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if (damageTable.getSelectionModel().isSelectionEmpty()){ newError(iFrame, "Повреждение не выбрано!"); return; }
				int selectedIndex = damageTable.getRowSorter().convertRowIndexToModel(damageTable.getSelectionModel().getMinSelectionIndex());
				Damage damage = (Damage)tableModel.getValueAt( selectedIndex, 0);
				
				int n = newDialog(iFrame, "Удалить повреждение: " + damage.toString()+" ?");
				if (n == JOptionPane.YES_OPTION) {
					sys.dmc.removeElement(damage);
					String mes = "Повреждение " + damage.toString() + " удалено";
					sys.rw.addLogMessage(mes);
					((DefaultTableModel) damageTable.getModel()).removeRow(selectedIndex);
					newInfo(iFrame, mes);
				}	
			}
		};
		deleteButton.addActionListener(deleteDamage);
		/*
		 * ---------------------------------------------------------
		 */
		iFrame.setVisible(true);
		return iFrame;
	}
	/**
	 * Отображает паспорт элемента в браузере
	 * @param fileName - имя файла
	 */
	public void formViewPassport (String fileName) {
		
		try {
			File page = new File(fileName);
		    java.awt.Desktop.getDesktop().browse(page.toURI()); 
		} catch (IOException ex) {
			sys.rw.writeError(ex.toString());
		}
	}
	
	/**
	 * Добавляет пару во включение
	 * @param path - включение
	 * @param p - пара
	 * @return пару, если пара была добавлена во включение, иначе null
	 */
	public Pair addPairToPath(Path path, Pair p, JDialog frame) {
		
		Pair returnedPair = null;
		Cable c = (Cable)sys.cc.getElement(p.getCable());
		
		if (c.getType() == 0) {
			
			if (path.isdrPair()) {
				newError(frame,"Включение занимает пару прямого питания!\r\nНевозможно добавить магистральную пару");
				return null;
			}
			//	if (newDialog(frame, "Включение занимает пару прямого питания!\r\nВсе равно добавить магистральную пару?") == JOptionPane.NO_OPTION) { return false;}
			
			if (path.ismPair())
				if (newDialog(frame, "Включение уже занимает магистральную пару!\r\nЗаменить магистральную пару?") == JOptionPane.NO_OPTION) { return null;}
			
				Pair oldPair = (Pair)sys.pc.getElement(path.getmPair());
				
				path.addmPair(p);
				p.setStatus(1);
				returnedPair = p;
				
				if (oldPair != null) {
					if (sys.phc.isPairUsed(oldPair) == null)  {
						oldPair.setStatus(0);
						sys.rw.addLogMessage("Пара "+ oldPair.toString()+" освобождена ");
					}
					else {
						sys.rw.addLogMessage("Пара "+ oldPair.toString()+"удалена из включения " + path.toString());
					}
					
					returnedPair = oldPair;
				}
				String mes = "Пара "+ p.toString()+" занята включением: " + path.toString();
				sys.rw.addLogMessage(mes);
				newInfo(frame, mes);
				return returnedPair;
		
		}
		
		if (c.getType() == 1) {
			
			if (path.isdrPair()) {
				newError(frame,"Включение занимает пару прямого питания!\r\nНевозможно добавить межшкафную пару");
				return null;
			}
			
			if (path.isdbPair()) {
				newError(frame,"Включение занимает распределительную пару!\r\nНевозможно добавить межшкафную пару");
				return null;
			}
			
			//	if (newDialog(frame, "Включение занимает пару прямого питания!\r\nВсе равно добавить межшкафную пару?") == JOptionPane.NO_OPTION) { return false;}
			
				path.addicPair(p);
				p.setStatus(1);
				String mes = "Кабельная пара "+ p.toString()+" занята включением: " + path.toString();
				sys.rw.addLogMessage(mes);
				newInfo(frame, mes);
				return p;
		}
		
		if (c.getType() == 2) {
			
			if (path.isdrPair()) {
				newError(frame,"Включение занимает пару прямого питания!\r\nНевозможно добавить распределительную пару");
				return null;
			}
			//	if (newDialog(frame, "Включение занимает пару прямого питания!\r\nВсе равно добавить распределительную пару?") == JOptionPane.NO_OPTION) { return false;}
			
			if (path.isdbPair())
				if (newDialog(frame, "Включение уже занимает распределительную пару!\r\nЗаменить распределительную пару?") == JOptionPane.NO_OPTION) { return null;}
				
				Pair oldPair = (Pair)sys.pc.getElement(path.getdbPair());
				
				path.adddbPair(p);
				p.setStatus(1);
				returnedPair = p;
				
				if (oldPair != null) {
					if (sys.phc.isPairUsed(oldPair) == null)  {
						oldPair.setStatus(0);
						sys.rw.addLogMessage("Пара "+ oldPair.toString()+" освобождена ");
					}
					else {
						sys.rw.addLogMessage("Пара "+ oldPair.toString()+" удалена из включения " + path.toString());
					}
					returnedPair = oldPair;
				}
				
				String mes = "Кабельная пара "+ p.toString()+" занята включением: " + path.toString();
				sys.rw.addLogMessage(mes);
				newInfo(frame, mes);
				return returnedPair;
		}
		
		if (c.getType() == 3) {
													
			if (path.ismPair() || path.isdbPair() || path.isicPair()) {
				newError(frame, "Включение занимает маг./межшкаф./распред. пару!\r\nНевозможно добавить пару прямого питания?"); 
				return null;}
			
			if (path.isdrPair())
				if (newDialog(frame, "Включение занимает пару прямого питания!\r\n Заменить пару прямого питания?") == JOptionPane.NO_OPTION) { return null;}
			
			Pair oldPair = (Pair)sys.pc.getElement(path.getdrPair());
			
			path.adddrPair(p);
			p.setStatus(1);
			returnedPair = p;
			
			if (oldPair != null) {
				
				if (sys.phc.isPairUsed(oldPair) == null)  {
					oldPair.setStatus(0);
					sys.rw.addLogMessage("Пара "+ oldPair.toString()+" освобождена ");
				}
				else {
					sys.rw.addLogMessage("Пара "+ oldPair.toString()+" удалена из включения " + path.toString());
				}
				returnedPair = oldPair;
			}
				
				String mes = "Кабельная пара "+ p.toString()+" занята включением: " + path.toString();
				sys.rw.addLogMessage(mes);
				newInfo(frame, mes);
				
				return returnedPair;
		}
		return null;
	}
	/**
	 * Создает и добавляет в окно надпись
	 * @param Text - текст надписи
	 * @param iFrame - окно
	 * @param x, y, w, h - координаты и размеры надписи
	 * @return надпись
	 */
	public JLabel newLabel(String Text, JDialog iFrame, int x, int y, int w, int h) {
		
		JLabel newLabel = new JLabel(Text);
		newLabel.setBounds(x, y, w, h);
		iFrame.getContentPane().add(newLabel);
		
		return newLabel;
	}
	
	public JList newList(JDialog iFrame, int x, int y, int w, int h) {
				
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
	
	@SuppressWarnings("serial")
	public JTable newTable(JDialog iFrame, int x, int y, int w, int h) {
		
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
	
	
	public JTextArea newTextArea(JDialog iFrame, int x, int y, int w, int h) {
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(x, y, w, h);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		JTextArea textArea = new JTextArea();
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		iFrame.getContentPane().add(scrollPane);
		
		return textArea;
	}
	
	public JEditorPane newEditorPane (JDialog iFrame, int x, int y, int w, int h) {
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(x, y, w, h);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		JEditorPane editor = new JEditorPane();
		editor.setEditable(false);
		scrollPane.setViewportView(editor);
		iFrame.getContentPane().add(scrollPane);
		
		return editor;
	}
	
	public JButton newButton(String Text, JDialog iFrame, int x, int y, int w, int h) {
		
		JButton button = new JButton(Text);
		button.setBounds(x, y, w, h);
		iFrame.getContentPane().add(button);
		
		return button;
	}
	
	public JButton newMoreButton(final JDialog iFrame, final int iFrameMinWidth, final int iFrameMaxWidth, final int iFrameMinHeight, final int iFrameMaxHeight, int x, int y, int w, int h) {
		
		final JButton moreButton = newButton(" > ", iFrame, x, y, w, h);
		moreButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (iFrame.getSize().width == iFrameMinWidth) {
					iFrame.setSize(iFrameMaxWidth, iFrameMaxHeight);
					moreButton.setText(" < ");
				}
				else {
					iFrame.setSize(iFrameMinWidth, iFrameMinHeight);
					moreButton.setText(" > ");
				}
			}
		});
		
		return moreButton;
	}
	
	public JTextField newTextField(JDialog iFrame, int x, int y, int w, int h) {
		
		JTextField textField = new JTextField();
		textField.setBounds(x, y, w, h);
		iFrame.getContentPane().add(textField);
		
		return textField;
	}
	
	
	public int newDialog(Component parent, String mes) {
		
		Object[] options = {"Да", "Нет"};
		
		return JOptionPane.showOptionDialog(parent,
				mes,
				"Подтверждение операции",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,     //do not use a custom Icon
				options,  //the titles of buttons
				options[0]); //default button title
				
	}
	
	public void newInfo (Component parent, String mes) {JOptionPane.showMessageDialog(parent, mes, "Операция выполнена успешно", JOptionPane.INFORMATION_MESSAGE);}
	
	public void newError (Component parent, String mes) {JOptionPane.showMessageDialog(parent, mes, "Ошибка", JOptionPane.ERROR_MESSAGE);}
	
	public void viewDFrame(final DFramе dframe) {
		
		int W = 80, H = 100, marginX = 20, marginY = 20, inLine = 10;
		int lines = (int) Math.ceil ((double)dframe.getPlacesCount().intValue() / (double)inLine);
		int panelWidth = W * inLine + marginX * (inLine + 1);
		int panelHeight = H * lines + marginY * (lines + 1);
		
		final JDialog iFrame = newDialog("Просмотр кросса", panelWidth + 40 + 10, panelHeight + 100);
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setToolTipText(dframe.toString());
		panel.setBackground(new Color(0, 128, 128));
		panel.setBounds(20, 50, panelWidth, panelHeight);
		iFrame.getContentPane().add(panel);
		
		JLabel head = newLabel(dframe.toString(), iFrame, 20, 10, panelWidth, 30);
		head.setFont(new Font("Dialog", Font.BOLD, 16));
		head.setHorizontalAlignment(SwingConstants.CENTER);
		int x = 0, y = 0;
	
		JButton refreshButton = newButton("Обновить", iFrame, 20,10,90,26);
		refreshButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				iFrame.dispose();
				viewDFrame(dframe);			
			}
		});
		
		ActionListener frameClick = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new FormViewConnectedPointElement(sys, (Frame)((ElementView)e.getSource()).getElement(), null, null );			
			}
		};
		
		ActionListener placeClick = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
						
			}
		};
		
		JPopupMenu popupMenu = new JPopupMenu();

		JMenuItem menuItem = new JMenuItem("Добавить");
		popupMenu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			//	JPopupMenu pm = (JPopupMenu) ((JMenuItem)e.getSource()).getParent();
			//	ElementView ep = (ElementView)pm.getInvoker();
			//	ConnectedPointElement p = (ConnectedPointElement) ep.getElement();
				new FormFrame(sys, null, dframe);
				/*if (formFrame(null, dframe) != null ) {
					iFrame.dispose();
					viewDFrame(dframe);
				}*/
			}	
		});
		
	
		JMenuItem menuItem_1 = new JMenuItem("Редактировать");
		popupMenu.add(menuItem_1);
		menuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPopupMenu pm = (JPopupMenu) ((JMenuItem)e.getSource()).getParent();
				ElementView ep = (ElementView)pm.getInvoker();
				ConnectedPointElement p = (ConnectedPointElement) ep.getElement();
				
				new FormFrame(sys,(Frame)p, dframe);
				/*
				if (formFrame((Frame)p, dframe) != null ) {
					iFrame.dispose();
					viewDFrame(dframe);
				}*/
				
			}
		});
		
		JMenuItem menuItem_2 = new JMenuItem("Удалить");
		popupMenu.add(menuItem_2);
		menuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPopupMenu pm = (JPopupMenu) ((JMenuItem)e.getSource()).getParent();
				ElementView ep = (ElementView)pm.getInvoker();
				ConnectedPointElement p = (ConnectedPointElement) ep.getElement();	
				
				if (newDialog(iFrame, "Удалить громполосу " + p.toString()+" и все пары в ней?") == JOptionPane.YES_OPTION) {
					sys.removeFrame((Frame)p);
					iFrame.dispose();
					viewDFrame(dframe);
				}
			}
		});
		
		for (int place = 0; place < dframe.getPlacesCount(); place++) {
			
			if ( x > inLine - 1)  { x = 0; y++; }
				
				ElementView button = new ElementView();
				button.setBounds(marginX + x*(W + marginX), marginY + y*(H + marginY), W, H);
				panel.add(button);
				button.setElement(null);
				
				addPopupToConnectedPointElement(button, popupMenu);
	
				Frame frame =  (Frame)sys.fc.getInPlace((Integer)place, dframe.getId());
				if (frame != null) {
					button.setText(frame.toString());
					button.setToolTipText("Громполоса: "+ frame.toString());
					button.setElement(frame);
					button.setBackground(new Color(200, 0, 200));
					button.addActionListener(frameClick);
					button.setForeground(new Color(0, 0, 0));
				}
				else {
					button.setBackground(new Color(230, 230, 230));
					button.setForeground(new Color(160, 160, 160));
					
					button.setToolTipText("Незанятое место №" + ((Integer)place).toString());
					button.addActionListener(placeClick);
					button.setText(((Integer)place).toString());
				}
			x++;
		}
	
		iFrame.setVisible(true);
	}

	
	public void viewCable(final Cable element, final Integer netId, final JTextField textFieldForSelectResult) {
		
		//final Vector<Pair>  returnedPair = new Vector<Pair>(); returnedPair.add(null);
		
		int W = 18, H = 18, marginX = 8, marginY = 8, inLine = 10, labelPlaceLeft = 50, labelPlaceTop = 20, groupDevision = 14, infoListHeght = 200;
		int lines = (int) Math.ceil ((double)element.getCapacity().intValue() / (double)inLine);
		int panelWidth = groupDevision + labelPlaceLeft + W * inLine + marginX * (inLine + 1);
		int panelHeight = labelPlaceTop + H * lines + marginY * (lines + 1);
		
		final JDialog iFrame = newDialog("Просмотр кабеля", panelWidth + 40, panelHeight + infoListHeght + 100);
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setToolTipText(element.toString());
		panel.setBackground(new Color(200, 200, 200));
		panel.setBounds(20, 50, panelWidth, panelHeight);
		iFrame.getContentPane().add(panel);
		
		final JTextArea infoArea = newTextArea(iFrame, 20, 40 + panelHeight + 20, panelWidth, infoListHeght);
		JLabel head = newLabel(element.toShortString(), iFrame, 20, 10, panelWidth, 30);
		head.setFont(new Font("Dialog", Font.BOLD, 16));
		head.setHorizontalAlignment(SwingConstants.CENTER);
		
		/*
		 * Событие нажатие на кнопку пары
		 */
		ActionListener pairClick = new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				Pair p = (Pair)((ElementView)e.getSource()).getElement();
				viewPairInfo(p, infoArea);
		}};
		/*
		 * --------------------------------
		 */
		/*
		 * Событие нажатия на кнопку пустого места
		 */
		ActionListener placeClick = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textFieldForSelectResult != null) {
					Pair p = (Pair)((ElementView)e.getSource()).getElement();
					//returnedPair.set(0, p);
					textFieldForSelectResult.setText(p.getFromNumber().toString());
					iFrame.dispose();
				}
			}};
		/*
		 * ---------------------------------
		 */
		//хеш всех созданых кнопок для пар
		HashMap<Pair, ElementView> elementViewHash = new HashMap<Pair, ElementView>();
		JPopupMenu popupMenu = popupMenuForPair(iFrame, netId, elementViewHash);
		
		for (int ln = 0; ln < inLine; ln++) {
			JLabel l = new JLabel(((Integer)ln).toString());
			l.setFont(new Font("Dialog", Font.BOLD, 10));
			if (ln > 4) {
				l.setBounds(groupDevision + labelPlaceLeft + marginX + ln*(W + marginX), marginY, W, H);
			}
			else {
				l.setBounds(labelPlaceLeft + marginX + ln*(W + marginX), marginY, 20, H);
			}
			l.setHorizontalAlignment(SwingConstants.CENTER);
			panel.add(l);
		}
		
		int x = 0, y = 0;
		for (int place = 0; place < element.getCapacity(); place++) {
			
			if ( x > inLine - 1)  { x = 0; y++; }
			
				if (x == 0) {
					JLabel l = new JLabel(((Integer)place).toString());
					l.setFont(new Font("Dialog", Font.BOLD, 10));
					l.setBounds(marginX, labelPlaceTop + marginY + y*(H + marginY), 30, H);
					l.setHorizontalAlignment(SwingConstants.RIGHT);
					panel.add(l);
				}
				ElementView button = new ElementView();
				if (x > 4) {
					button.setBounds(groupDevision + labelPlaceLeft + marginX + x*(W + marginX), labelPlaceTop + marginY + y*(H + marginY), W, H);
				}
				else {
					button.setBounds(labelPlaceLeft + marginX + x*(W + marginX), labelPlaceTop + marginY + y*(H + marginY), W, H);
				}
				panel.add(button);
				Pair pairForEmptyPlace = new Pair(sys.fc,sys.bc,sys.dbc,sys.cc);
				pairForEmptyPlace.setFromNumber(place);
				button.setElement(pairForEmptyPlace);
			
				Pair pair = sys.pc.getInPlace((Cable)element, (Integer)place);
				
				if (pair != null) {
					button.setToolTipText("Пара: "+ pair.toString());
					button.setElement(pair);
					if (pair.getStatus() == 0) button.setBackground(new Color(0, 200, 0));
					if (pair.getStatus() == 1) button.setBackground(new Color(0, 0, 200));
					if (pair.getStatus() == 2) button.setBackground(new Color(250, 0, 0));
					button.addActionListener(pairClick);
					addPopupToPair(button, popupMenu);
					elementViewHash.put(pair, button);
				}
				else {
					button.setBackground(new Color(230, 230, 230));
					button.setToolTipText("Незанятое место №" + ((Integer)place).toString());
					button.addActionListener(placeClick);
				}

			x++;
			
		}
	
		iFrame.setVisible(true);
	//	return returnedPair.get(0);
	}
	
	public void viewDuct(final Duct duct, final Integer netId) {
		
		HashSet<Tube> h = sys.tuc.getDuctsTubes(duct);
		
		int W = 25, H = 25, marginX = 9, marginY = 9, inLine = 10, labelPlaceLeft = 50, labelPlaceTop = 30, infoListHeght = 200;
		int lines = (int) Math.ceil ((double)h.size() / (double)inLine);
		int panelWidth = labelPlaceLeft + W * inLine + marginX * (inLine + 1);
		int panelHeight = labelPlaceTop + H * lines + marginY * (lines + 1);
		
		final JDialog iFrame = newDialog("Просмотр канализации", panelWidth + 40, panelHeight + infoListHeght + 100);
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setToolTipText(duct.toString());
		panel.setBackground(new Color(200, 200, 200));
		panel.setBounds(20, 50, panelWidth, panelHeight);
		iFrame.getContentPane().add(panel);
		
		final JTextArea infoArea = newTextArea(iFrame, 20, 40 + panelHeight + 20, panelWidth, infoListHeght);
		JLabel head = newLabel(duct.toString(), iFrame, 20, 10, panelWidth, 30);
		head.setFont(new Font("Dialog", Font.BOLD, 16));
		head.setHorizontalAlignment(SwingConstants.CENTER);
		int x = 0, y = 0;
		
		/*
		 * Событие нажатие на кнопку канала
		 */
		ActionListener tubeClick = new ActionListener() { public void actionPerformed(ActionEvent e) {Tube t = (Tube)((ElementView)e.getSource()).getElement(); viewTubeInfo(t, infoArea);}};
		/*
		 * --------------------------------
		 */

		for (int ln = 0; ln < inLine; ln++) {
			JLabel l = new JLabel(((Integer)ln).toString());
			l.setFont(new Font("Dialog", Font.BOLD, 10));
			l.setBounds(labelPlaceLeft + marginX + ln*(W + marginX), marginY, 20, H);
			l.setHorizontalAlignment(SwingConstants.CENTER);
			panel.add(l);
		}
		
		for (int place = 0; place < h.size(); place++) {
			
			if ( x > inLine - 1)  { x = 0; y++; }
			
				
				if (x == 0) {
					JLabel l = new JLabel(((Integer)place).toString());
					l.setFont(new Font("Dialog", Font.BOLD, 10));
					l.setBounds(marginX, labelPlaceTop + marginY + y*(H + marginY), 30, H);
					l.setHorizontalAlignment(SwingConstants.RIGHT);
					panel.add(l);
				}
				ElementView button = new ElementView();
				button.setBounds(labelPlaceLeft + marginX + x*(W + marginX), labelPlaceTop + marginY + y*(H + marginY), W, H);
				
				panel.add(button);
				Tube tube = sys.tuc.getDuctByNumber(h, place);
				button.setElement(tube);
				button.setToolTipText("Канал № " + tube.getNumber().toString());
				button.setBackground(new Color(0, 200, 0));
				
				setTubeButtonColor(tube, button);
				button.addActionListener(tubeClick);
				addPopupToTube(button, popupMenuForTube(iFrame, netId));
				

			x++;
			
		}
		iFrame.setVisible(true);

	}
	
	public void viewManhole(final Manhole manhole, final Integer netId) {
		
		int paddingLeft = 10, paddingTop = 10, margin = 5, infoListH = 0;
		
		int manholeButtonW = 120, manholeButtonH = 120;
		int gorizontalDuctW = 180, gorizontalDuctH = 12;
		int verticalDuctW = 12, verticalDuctH = 180;
		int interDucts = 7;
		
		int labelW = 50, labelH = 14;
		
		int panelW = paddingLeft * 2 + margin * 2 + manholeButtonW + gorizontalDuctW * 2; 
		int panelH = paddingTop * 2 + margin * 2 + manholeButtonH + verticalDuctH * 2; 
		
		final JDialog iFrame = newDialog("Просмотр колодца", panelW + 40, panelH + infoListH + 100);
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(200, 200, 200));
		panel.setBounds(20, 50, panelW, panelH);
		iFrame.getContentPane().add(panel);
		
		JLabel head = newLabel(manhole.toString(), iFrame, 20, 10, panelW, 30);
		head.setFont(new Font("Dialog", Font.BOLD, 16));
		head.setHorizontalAlignment(SwingConstants.CENTER);
		
		/*
		 * Событие нажатие на кнопку колодца
		 */
	//	ActionListener manholeClick = new ActionListener() { public void actionPerformed(ActionEvent e) {Pair p = (Pair)((ElementView)e.getSource()).getElement(); viewPairInfo(p, infoArea);}};
		/*
		 * --------------------------------
		 */
		/*
		 * Событие нажатие на кнопку канализации
		 */
		ActionListener ductClick = new ActionListener() { public void actionPerformed(ActionEvent e) {Duct d = (Duct)((ElementView)e.getSource()).getElement(); viewDuct(d, netId);}};
		/*
		 * --------------------------------
		 */
		
		/*
		 * Событие нажатия на кнопку пустого места
		 */
	//	ActionListener placeClick = new ActionListener() {public void actionPerformed(ActionEvent e) {}};
		/*
		 * ---------------------------------
		 */
		//JPopupMenu popupMenu = popupMenuForPair(iFrame, netId);

		
		ElementView manholeButton = new ElementView();
		manholeButton.setElement(manhole);
		manholeButton.setBounds(paddingLeft + gorizontalDuctW + margin, paddingTop + verticalDuctH + margin , manholeButtonW, manholeButtonW);
	//	manholeButton.addActionListener(manholeClick);
		manholeButton.setText(manhole.toString());
		manholeButton.setBackground(new Color(0,200,200));
		panel.add(manholeButton);
		
		/*
		 * Отрисовываем канализацию сверху
		 */
		HashSet<Duct> ds = sys.duc.getDuctsBySide(manhole, 0);
		int ductsBlockW = ds.size() * verticalDuctW + (ds.size() - 1) * interDucts;
		Iterator<Duct> i = ds.iterator(); int ductIndex = 0;
		while (i.hasNext()) {
			Duct duct = i.next();
			ElementView ductButton = new ElementView();
			ductButton.setBackground(new Color(200,0,200));
			ductButton.setToolTipText(duct.toString());
			ductButton.setElement(duct);
			ductButton.addActionListener(ductClick);
			ductButton.setBounds(paddingLeft + gorizontalDuctW + margin + (manholeButtonW - ductsBlockW)/2 + ductIndex * (verticalDuctW + interDucts), paddingTop, verticalDuctW, verticalDuctH);
			panel.add(ductButton);

			ductIndex++;
		}
		JLabel front = new JLabel("Перед");
		front.setBounds(paddingLeft + gorizontalDuctW + margin + (manholeButtonW - ductsBlockW)/2 + ductsBlockW + 10, paddingTop, labelW, labelH);
		panel.add(front);
		/*
		 * --------------------------------------------------------
		 */
		/*
		 * Отрисовываем канализацию снизу
		 */
		ds = sys.duc.getDuctsBySide(manhole, 2);
		ductsBlockW = ds.size() * verticalDuctW + (ds.size() - 1) * interDucts;
		i = ds.iterator(); ductIndex = 0;
		while (i.hasNext()) {
			Duct duct = i.next();
			ElementView ductButton = new ElementView();
			ductButton.setBackground(new Color(200,0,200));
			ductButton.setToolTipText(duct.toString());
			ductButton.setElement(duct);
			ductButton.addActionListener(ductClick);
			ductButton.setBounds(paddingLeft + gorizontalDuctW + margin + (manholeButtonW - ductsBlockW)/2 + ductIndex * (verticalDuctW + interDucts), paddingTop + margin * 2 + verticalDuctH + manholeButtonH, verticalDuctW, verticalDuctH);
			panel.add(ductButton);

			ductIndex++;
		}
		JLabel back = new JLabel("Зад");
		back.setBounds(paddingLeft + gorizontalDuctW + margin + (manholeButtonW - ductsBlockW)/2 + ductsBlockW + 10, panelH - labelH - 10, labelW, labelH);
		panel.add(back);
		/*
		 * --------------------------------------------------------
		 */
		
		/*
		 * Отрисовываем канализацию справа
		 */
		ds = sys.duc.getDuctsBySide(manhole, 1);
		int ductsBlockH = ds.size() * gorizontalDuctH + (ds.size() - 1) * interDucts;
		i = ds.iterator(); ductIndex = 0;
		while (i.hasNext()) {
			Duct duct = i.next();
			ElementView ductButton = new ElementView();
			ductButton.setBackground(new Color(200,0,200));
			ductButton.setToolTipText(duct.toString());
			ductButton.setElement(duct);
			ductButton.addActionListener(ductClick);
			ductButton.setBounds(paddingLeft + margin * 2  + gorizontalDuctW + manholeButtonW, paddingTop + verticalDuctH + margin + (manholeButtonH - ductsBlockH)/2 + ductIndex * (gorizontalDuctH + interDucts), gorizontalDuctW, gorizontalDuctH);
			panel.add(ductButton);

			ductIndex++;
		}
		JLabel right = new JLabel("Право");
		right.setBounds(panelW - labelW - 10, paddingTop + verticalDuctH + margin + (manholeButtonH - ductsBlockH)/2 + ductsBlockH + 10, labelW, labelH);
		panel.add(right);
	
		/*
		 * --------------------------------------------------------
		 */
		
		/*
		 * Отрисовываем канализацию слева
		 */
		ds = sys.duc.getDuctsBySide(manhole, 3);
		ductsBlockH = ds.size() * gorizontalDuctH + (ds.size() - 1) * interDucts;
		i = ds.iterator(); ductIndex = 0;
		while (i.hasNext()) {
			Duct duct = i.next();
			ElementView ductButton = new ElementView();
			ductButton.setBackground(new Color(200,0,200));
			ductButton.setToolTipText(duct.toString());
			ductButton.setElement(duct);
			ductButton.addActionListener(ductClick);
			ductButton.setBounds(paddingLeft, paddingTop + verticalDuctH + margin + (manholeButtonH - ductsBlockH)/2 + ductIndex * (gorizontalDuctH + interDucts), gorizontalDuctW, gorizontalDuctH);
			panel.add(ductButton);

			ductIndex++;
		}
		JLabel left = new JLabel("Лево");
		left.setBounds(paddingLeft, paddingTop + verticalDuctH + margin + (manholeButtonH - gorizontalDuctH)/2 + ductsBlockH + 10, labelW, labelH);
		panel.add(left);
		/*
		 * --------------------------------------------------------
		 */
		
		iFrame.setVisible(true);
	}
	/**
	 * Выводит подробные данные о паре
	 * @param p - пара
	 * @param infoArea - текстовое поле для вывода данных
	 */
	public void viewPairInfo(Pair p,  JTextArea infoArea) {
		infoArea.setText("");
		Cable c = (Cable)sys.cc.getElement(p.getCable());
		infoArea.append("Сеть: "+ sys.nc.getElement(c.getNet()).toString()+"\r\n");
		if (c.getType().equals(0)) {
			Frame f = (Frame)sys.fc.getElement(p.getElementFrom());
			Box b = (Box)sys.bc.getElement(p.getElementTo());
			infoArea.append("Тип: магистральная\r\nУчасток: "+ sys.dfc.getElement(f.getOwnerId()).toString()+" - "+sys.cbc.getElement(b.getOwnerId()).toString()+"\r\n");
		}
		if (c.getType().equals(1)) {
			Box b1 = (Box)sys.bc.getElement(p.getElementFrom());
			Box b2 = (Box)sys.bc.getElement(p.getElementTo());
			infoArea.append("Тип: межшкафная\r\nУчасток: "+ sys.cbc.getElement(b1.getOwnerId()).toString()+" - "+sys.cbc.getElement(b2.getOwnerId()).toString()+"\r\n");
		}
		if (c.getType().equals(2)) {
			Box b = (Box)sys.bc.getElement(p.getElementFrom());
			infoArea.append("Тип: распределительная\r\nУчасток: "+ sys.cbc.getElement(b.getOwnerId()).toString()+" - "+sys.dbc.getElement(p.getElementTo()).toString()+"\r\n");
		}
		if (c.getType().equals(3)) {
			Frame f = (Frame)sys.fc.getElement(p.getElementFrom());
			infoArea.append("Тип: прямого питания\r\nУчасток: "+ sys.dfc.getElement(f.getOwnerId()).toString()+" - "+sys.dbc.getElement(p.getElementTo()).toString()+"\r\n");
		}
		infoArea.append("Кабель: "+c.toShortString()+"\r\n");
		infoArea.append("Пара: "+p.toString()+"\r\n");
		if (p.getStatus().equals(0)) infoArea.append("Состояние: свободна\r\n");
		if (p.getStatus().equals(1)){
			
			infoArea.append("Состояние: занята\r\n");
			Iterator<Path> i = sys.phc.getPairsPath(p).iterator();
			while (i.hasNext()) {
				Path path = i.next();
				infoArea.append("Абонент: " + sys.sc.getElement(path.getSubscriber())+"\r\n");
				infoArea.append("Включение: " + path.toString() + "\r\n");
			}
			
		}
		if (p.getStatus().equals(2)) infoArea.append("Состояние: повреждена\r\n");	
	}
	/**
	 * Выводит подробные данные о кабельном канале
	 * @param t - канал
	 * @param infoArea - текстовое поле для вывода данных
	 */
	public void viewTubeInfo(Tube t,  JTextArea infoArea) {
		infoArea.setText("");
		infoArea.append("Участок канализации: " + sys.duc.getElement(t.getDuct()).toString() + "\r\n");
		infoArea.append("Канал №: " + t.getNumber().toString() + "\r\n");
		
		if (t.cablesCount() == 0) {
			infoArea.append("Состояние: свободен");
			return;
		}
		infoArea.append("Состояние: используется:\r\n");
		
		Iterator<Integer> i = t.getCables().iterator();
		while (i.hasNext()) infoArea.append("Кабель: "+((Cable)sys.cc.getElement(i.next())).toLongString()+"\r\n");
	}
	/**
	 * Всплывающее меню для канала канализации
	 * @param iFrame - окно
	 * @param netId - id сети
	 * @return JPopupMenu объект всплывающего меню
	 */
	private  JPopupMenu popupMenuForTube (final JDialog iFrame, final Integer netId){
		JPopupMenu popupMenu = new JPopupMenu();

		JMenuItem menuItem = new JMenuItem("Добавить кабель");
		popupMenu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPopupMenu pm = (JPopupMenu) ((JMenuItem)e.getSource()).getParent();
				final ElementView ep = (ElementView)pm.getInvoker();
				final Tube t = (Tube) ep.getElement();
				final FormSearchCable form = new FormSearchCable(sys,netId);
				
				ActionListener selectCable = new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (form.cableTable.getSelectionModel().isSelectionEmpty()){ newError(iFrame, "Кабель не выбран!"); return; }
						int selectedIndex = form.cableTable.getRowSorter().convertRowIndexToModel(form.cableTable.getSelectionModel().getMinSelectionIndex());
						
						Cable cable = (Cable)((DefaultTableModel)form.cableTable.getModel()).getValueAt( selectedIndex, 0);
						
						if (t.containsCable(cable)) { newError(iFrame, "Кабель уже содержиться в канале"); return; }
						
						t.addCable(cable);
						sys.rw.addLogMessage("Кабель " + cable.toString()+ " добавлен в канал " + t.toString() + " участка канализации " + sys.duc.getElement(t.getDuct()));
						setTubeButtonColor(t, ep);	
						form.iFrame.dispose();
					}
				};
				form.okButton.addActionListener(selectCable);
			}
		});
		
		JMenuItem menuItem_1 = new JMenuItem("Удалить кабель");
		popupMenu.add(menuItem_1);
		menuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPopupMenu pm = (JPopupMenu) ((JMenuItem)e.getSource()).getParent();
				ElementView ep = (ElementView)pm.getInvoker();
				Tube t = (Tube) ep.getElement();
				Cable cable = formTubesCables(t);
				if (cable != null)
					if (t.removeCable(cable)) {
						sys.rw.addLogMessage("Кабель " + cable.toString()+ " удален из канала " + t.toString() + " участка канализации " + sys.duc.getElement(t.getDuct()));
						setTubeButtonColor(t, ep);
					}
			}
		});
		
		return popupMenu;
		
	}
	/**
	 * Всплывающее меню для пары
	 * @param iFrame - окно
	 * @param netId - id сети
	 * @param elementViewHash - хеш всех созданых кнопок для пар
	 * @return JPopupMenu объект всплывающего меню
	 */
	private JPopupMenu popupMenuForPair (final JDialog iFrame, final Integer netId, final HashMap<Pair, ElementView> elementViewHash){
		
		JPopupMenu popupMenu = new JPopupMenu();

		JMenuItem menuItem = new JMenuItem("Закрепить за абонентом");
		popupMenu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPopupMenu pm = (JPopupMenu) ((JMenuItem)e.getSource()).getParent();
				final ElementView ep = (ElementView)pm.getInvoker();
				final Pair p = (Pair) ep.getElement();
				final FormSubscriberSearch form = formSearchSubscriber();
				
				ActionListener selectSubscriber = new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (form.subscriberList.getSelectedIndex() == -1) {newError(form.iFrame,"Абонент не выбран!"); return;}
						Subscriber sub = (Subscriber)form.subscriberList.getSelectedValue();
						
						final FormSubscriberPaths formPath = new FormSubscriberPaths(sys,sub);
						
						ActionListener selectPath = new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								if (formPath.pathList.getSelectedIndex() == -1) {newError(formPath.iFrame,"Включение не выбрано!"); return;}
								Path path = (Path)formPath.pathList.getSelectedValue();
								
								Pair oldPair = addPairToPath(path,p,iFrame);
								setPairButtonColor(p, ep);
								if (oldPair != null) {
									ElementView oldPairButton = elementViewHash.get(oldPair);
									if (oldPairButton != null)
										setPairButtonColor(oldPair, oldPairButton);
								}
								formPath.iFrame.dispose();	
							}
						};
						formPath.okButton.addActionListener(selectPath);
	
						form.iFrame.dispose();	
					}
				};
				form.okButton.addActionListener(selectSubscriber);
			}
		});
		
		JMenuItem menuItem_1 = new JMenuItem("Отметить как поврежденная");
		popupMenu.add(menuItem_1);
		menuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPopupMenu pm = (JPopupMenu) ((JMenuItem)e.getSource()).getParent();
				ElementView ep = (ElementView)pm.getInvoker();
				Pair p = (Pair) ep.getElement();
				p.setStatus(2);
				sys.rw.addLogMessage("Пара "+ p.toString()+", изменен статус на поврежденная");
				setPairButtonColor(p, ep);
			}
		});
		
		JMenuItem menuItem_2 = new JMenuItem("Отметить как исправная");
		popupMenu.add(menuItem_2);
		menuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPopupMenu pm = (JPopupMenu) ((JMenuItem)e.getSource()).getParent();
				ElementView ep = (ElementView)pm.getInvoker();
				Pair p = (Pair) ep.getElement();
				p.setStatus(0);
				sys.rw.addLogMessage("Пара "+ p.toString()+", изменен статус на исправная");
				setPairButtonColor(p, ep);
				
			}
		});
				
		JMenuItem menuItem_3 = new JMenuItem("Освободить");
		popupMenu.add(menuItem_3);
		menuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPopupMenu pm = (JPopupMenu) ((JMenuItem)e.getSource()).getParent();
				final ElementView ep = (ElementView)pm.getInvoker();
				final Pair p = (Pair) ep.getElement();
				final FormPairPaths form =  formPairPaths(p);
				
				ActionListener selectPath = new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (form.pathList.getSelectedIndex() == -1) {newError(iFrame,"Включение не выбрано!"); return;}
					Path path = (Path)form.pathList.getSelectedValue();
					
					if (path.removePair(p)) {
						sys.rw.addLogMessage("Пара "+ p.toString()+" удалена из включения: "+ path.toString()+ " у абонента: " + sys.sc.getElement(path.getSubscriber()).toString());

						if (sys.phc.isPairUsed(p) == null)  {
							p.setStatus(0);
							sys.rw.addLogMessage("Пара "+ p.toString()+" освобождена ");
							setPairButtonColor(p, ep);
						}
					}				
					form.iFrame.dispose();	
					}
				};
				form.okButton.addActionListener(selectPath);			
			}
		});
		
		JMenuItem menuItem_4 = new JMenuItem("Карточка абонента");
		popupMenu.add(menuItem_4);
		menuItem_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPopupMenu pm = (JPopupMenu) ((JMenuItem)e.getSource()).getParent();
				ElementView ep = (ElementView)pm.getInvoker();
				Pair p = (Pair) ep.getElement();
				final FormPairSubscribers form = new FormPairSubscribers(sys, p);
				
				ActionListener selectSubscriber = new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (form.subscriberList.getSelectedIndex() == -1) {newError(iFrame,"Абонент не выбран!"); return;}
						Subscriber sub = (Subscriber)form.subscriberList.getSelectedValue();
						if (sub != null) formViewPassport(sys.rw.createSubscriberPassport(sub));
						form.iFrame.dispose();	
					}
				};
				form.okButton.addActionListener(selectSubscriber);
			}
		});
		
		return popupMenu;
		
	}
	/**
	 * Добавляет всплывающее меню для канала канализации
	 * @param popup - всплывающее меню
	 */
	private static void addPopupToTube(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
	private static void addPopupToPair(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
				//popup.setElement((ElementView)e.getSource());
				Pair p = (Pair)((ElementView)popup.getInvoker()).getElement();
				((JMenuItem)popup.getSubElements()[0]).setEnabled(false);
				((JMenuItem)popup.getSubElements()[1]).setEnabled(false);
				((JMenuItem)popup.getSubElements()[2]).setEnabled(false);
				((JMenuItem)popup.getSubElements()[3]).setEnabled(false);
				((JMenuItem)popup.getSubElements()[4]).setEnabled(false);
				
				if (p.getStatus() == 0) {
					((JMenuItem)popup.getSubElements()[0]).setEnabled(true);
					((JMenuItem)popup.getSubElements()[1]).setEnabled(true);
				}
				if (p.getStatus() == 1) {
					((JMenuItem)popup.getSubElements()[0]).setEnabled(true);
					((JMenuItem)popup.getSubElements()[3]).setEnabled(true);
					((JMenuItem)popup.getSubElements()[4]).setEnabled(true);
				}
				if (p.getStatus() == 2) {
					((JMenuItem)popup.getSubElements()[2]).setEnabled(true);
					
				}
				
			}
		});
	}
	
	private static void addPopupToConnectedPointElement(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
				//popup.setElement((ElementView)e.getSource());
				ConnectedPointElement p = (ConnectedPointElement)((ElementView)popup.getInvoker()).getElement();
				((JMenuItem)popup.getSubElements()[0]).setEnabled(false);
				((JMenuItem)popup.getSubElements()[1]).setEnabled(false);
				((JMenuItem)popup.getSubElements()[2]).setEnabled(false);
				
				if (p == null) { ((JMenuItem)popup.getSubElements()[0]).setEnabled(true); }
				else {
					((JMenuItem)popup.getSubElements()[1]).setEnabled(true);
					((JMenuItem)popup.getSubElements()[2]).setEnabled(true);
				}
				
				
				
			}
		});
	}
	/**
	 * Устанавливает цвет графического элемента канала канализации в зависимости от загруженности кабелями
	 * @param tube - канал
	 * @param button - графический элемент
	 */
	private void setTubeButtonColor(Tube tube, ElementView button) {
		
		if (tube.cablesCount() == 0)  { button.setBackground(new Color(0, 200, 0)); return; }
		if (tube.cablesCount() == 1) { button.setBackground(new Color(204, 204, 255)); return; }
		if (tube.cablesCount() == 2) { button.setBackground(new Color(153, 153, 255)); return; }
		if (tube.cablesCount() == 3) { button.setBackground(new Color(102, 102, 255)); return; }
		if (tube.cablesCount() > 3) { button.setBackground(new Color(51, 51, 255)); return; }
		if (tube.cablesCount() > 5) { button.setBackground(new Color(0, 0, 255)); return; }

	}
	/**
	 * Устанавливает цвет графического элемента пары
	 * @param pair - пара
	 * @param button - графический элемент
	 */
	private void setPairButtonColor (Pair pair, ElementView button) {
		
		if (pair.getStatus() == 0) { button.setBackground((new Color(0,200,0))); return; }
		if (pair.getStatus() == 1) { button.setBackground((new Color(0,0,200))); return; }
		if (pair.getStatus() == 2) { button.setBackground((new Color(250,0,0))); return; }
	}
	
}
