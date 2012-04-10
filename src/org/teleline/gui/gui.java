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

import org.teleline.io.*;
import org.teleline.model.*;

public class gui {
	private RW rw;
	private NetCollection nc;
	private DFrameCollection dfc;
	private CabinetCollection cbc; 
	private DBoxCollection dbc;
	private ManholeCollection mc;
	private DuctCollection duc;
	private BuildingCollection buc;
	private TubeCollection tuc;
	private FrameCollection fc;
	private BoxCollection bc;
	private CableCollection cc;
	private PairCollection pc;
	private PathCollection phc;
	private SubscriberCollection sc;
	private Validator V;
	private JFrame frame;
	
	public gui(NetCollection nc, DFrameCollection dfc, CabinetCollection cbc, DBoxCollection dbc, ManholeCollection mc, DuctCollection duc, BuildingCollection buc, TubeCollection tuc, FrameCollection fc, BoxCollection bc, CableCollection cc, PairCollection pc, PathCollection phc, SubscriberCollection sc, RW rw, Validator V, JFrame frame ) {
		this.nc = nc;
		this.dfc = dfc;
		this.cbc = cbc;
		this.dbc = dbc;
		this.mc = mc;
		this.duc = duc;
		this.buc = buc;
		this.tuc = tuc;
		this.fc = fc;
		this.bc = bc;
		this.cc = cc;
		this.pc = pc;
		this.phc = phc;
		this.sc = sc;
		this.rw = rw;
		this.V = V;
		this.frame = frame;
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
	 * Обновляет строчку с кабелем в таблицк
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
		frame.setModal(true);
		
		frame.getContentPane().setLayout(null);
		
		return frame;
	}
	/**
	 * Создает выпадающий список сетей
	 * @param nc - коллекция элементов сеть
	 */
	public JComboBox newNetsComboBox(JDialog iFrame, int x, int y, int w, int h) {
		
		JComboBox comboBox = new JComboBox();
		setComboBoxItems(comboBox, nc.sortByIdUp(nc.elements()));
		
		comboBox.setBounds(x, y, w, h);
		iFrame.getContentPane().add(comboBox);
		
		return comboBox;
	}
	/**
	 * Создает выпадающий список кроссов
	 * @param NetsComboBox - выпадающий список сетей, из него берется сеть
	 * @return выпадающий список
	 */
	public JComboBox dframeComboBox( JComboBox NetsComboBox, JDialog iFrame, int x, int y, int w, int h){
		
		JComboBox comboBox = new JComboBox();
		if (NetsComboBox.getSelectedIndex() > -1) {
			setComboBoxItems(comboBox, dfc.sortByIdUp(dfc.getInNet(((Net)NetsComboBox.getSelectedItem()).getId())));
		}
		comboBox.setBounds(x, y, w, h);
		iFrame.getContentPane().add(comboBox);
		
		return comboBox;
	}
	
	public JComboBox cabinetComboBox(JComboBox NetsComboBox, Integer cabinetClass, JDialog iFrame, int x, int y, int w, int h){
		
		JComboBox comboBox = new JComboBox();
		if (NetsComboBox.getSelectedIndex() > -1) {
			setComboBoxItems(comboBox, cbc.sortByNumberUp(cbc.getInNetByClass(((Net)NetsComboBox.getSelectedItem()).getId(), cabinetClass)));
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
	public JComboBox buildingComboBox(JComboBox NetsComboBox, JDialog iFrame, int x, int y, int w, int h){
		
		JComboBox comboBox = new JComboBox();
		if (NetsComboBox.getSelectedIndex() > -1) {
			setComboBoxItems(comboBox, buc.sortByIdUp(buc.getInNet(((Net)NetsComboBox.getSelectedItem()).getId())));
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
			setComboBoxItems(comboBox, mc.sortByIdUp(mc.getInNet(((Net)NetsComboBox.getSelectedItem()).getId())));
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
	public JComboBox manholeDFrameComboBox(JComboBox NetsComboBox, JDialog iFrame, int x, int y, int w, int h){
		
		JComboBox comboBox = new JComboBox();
		if (NetsComboBox.getSelectedIndex() > -1) {
			setComboBoxItems(comboBox, mc.sortByIdUp(mc.getInNet(((Net)NetsComboBox.getSelectedItem()).getId())));
			setComboBoxItems(comboBox, dfc.sortByIdUp(dfc.getInNet(((Net)NetsComboBox.getSelectedItem()).getId())));
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
	public JComboBox manholeCabinetBuildingComboBox(JComboBox NetsComboBox, JDialog iFrame, int x, int y, int w, int h){
		
		JComboBox comboBox = new JComboBox();
		if (NetsComboBox.getSelectedIndex() > -1) {
			setComboBoxItems(comboBox, mc.sortByIdUp(mc.getInNet(((Net)NetsComboBox.getSelectedItem()).getId())));
			setComboBoxItems(comboBox, cbc.sortByIdUp(cbc.getInNet(((Net)NetsComboBox.getSelectedItem()).getId())));
			setComboBoxItems(comboBox, buc.sortByIdUp(buc.getInNet(((Net)NetsComboBox.getSelectedItem()).getId())));
			
		}
		comboBox.setBounds(x, y, w, h);
		iFrame.getContentPane().add(comboBox);
		
		return comboBox;
	}
	
	public JComboBox dboxComboBox(JComboBox NetsComboBox, JDialog iFrame, int x, int y, int w, int h){
		
		JComboBox comboBox = new JComboBox();
		if (NetsComboBox.getSelectedIndex() > -1) {
			setComboBoxItems(comboBox, dbc.sortByIdUp(dbc.getInNet(((Net)NetsComboBox.getSelectedItem()).getId())));
		}
		comboBox.setBounds(x, y, w, h);
		iFrame.getContentPane().add(comboBox);
		
		return comboBox;
	}
	
	public JComboBox cableComboBox(JComboBox NetsComboBox, JComboBox FromComboBox, JComboBox ToComboBox, Integer Type, JDialog iFrame,  int x, int y, int w, int h){
		
		JComboBox comboBox = new JComboBox();
		if (Type < 2) {
			if (NetsComboBox.getSelectedIndex() > -1 && FromComboBox.getSelectedIndex() > -1 && ToComboBox.getSelectedIndex() > -1) {
				setComboBoxItems(comboBox, cc.sortByIdUp(cc.getInOwners(Type, ((StructuredElement)FromComboBox.getSelectedItem()).getId(), ((StructuredElement)ToComboBox.getSelectedItem()).getId())));
			}
		}
		
		if (Type >= 2) {
			if (NetsComboBox.getSelectedIndex() > -1 && FromComboBox.getSelectedIndex() > -1) {
				setComboBoxItems(comboBox, cc.sortByIdUp(cc.getDCableOut((StructuredElement)FromComboBox.getSelectedItem())));
			}
		}
		
		comboBox.setBounds(x, y, w, h);
		iFrame.getContentPane().add(comboBox);
		
		return comboBox;
	}
	public JComboBox boxComboBox(JComboBox OwnersComboBox, Integer Type, JDialog iFrame, int x, int y, int w, int h){
		
		JComboBox comboBox = new JComboBox();
		if (OwnersComboBox.getSelectedIndex() > -1) {
			setComboBoxItems(comboBox, bc.getInOwnerByTypeUniversal(((Cabinet)OwnersComboBox.getSelectedItem()).getId(), Type));
		}
		comboBox.setBounds(x, y, w, h);
		iFrame.getContentPane().add(comboBox);
		
		return comboBox;
	}
	public JComboBox frameComboBox(JComboBox OwnersComboBox, JDialog iFrame, int x, int y, int w, int h){
		
		JComboBox comboBox = new JComboBox();
		
		if (OwnersComboBox.getSelectedIndex() > -1) {
			setComboBoxItems(comboBox, fc.getInOwner(((DFramе)OwnersComboBox.getSelectedItem()).getId()));
		}
		comboBox.setBounds(x, y, w, h);
		iFrame.getContentPane().add(comboBox);
		
		return comboBox;
	}
	/**
	 * Связывает выпадающий список сетей с выпадающим списком шкафов
	 * @param NetsComboBox - выпадающий список сетей
	 * @param LinkedComboBox - связанный выпадающий список
	 * @param cabinetClass - класс шкафа
	 */
	public void netsCabinetComboLinked(final JComboBox NetsComboBox, final JComboBox LinkedComboBox, final Integer cabinetClass) {
		
		ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LinkedComboBox.removeAllItems();
                setComboBoxItems(LinkedComboBox, cbc.sortByIdUp(cbc.getInNetByClass(((Net)NetsComboBox.getSelectedItem()).getId(), cabinetClass)));               
            }
        };
        NetsComboBox.addActionListener(actionListener);      
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
                setComboBoxItems(LinkedComboBox, mc.sortByIdUp(mc.getInNet(((Net)NetsComboBox.getSelectedItem()).getId())));               
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
                setComboBoxItems(LinkedComboBox, buc.sortByIdUp(buc.getInNet(((Net)NetsComboBox.getSelectedItem()).getId())));               
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
                setComboBoxItems(LinkedComboBox, mc.sortByIdUp(mc.getInNet(((Net)NetsComboBox.getSelectedItem()).getId())));
                setComboBoxItems(LinkedComboBox, dfc.sortByIdUp(dfc.getInNet(((Net)NetsComboBox.getSelectedItem()).getId())));
                
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
                setComboBoxItems(LinkedComboBox, mc.sortByIdUp(mc.getInNet(((Net)NetsComboBox.getSelectedItem()).getId())));
                setComboBoxItems(LinkedComboBox, cbc.sortByIdUp(cbc.getInNet(((Net)NetsComboBox.getSelectedItem()).getId())));
                setComboBoxItems(LinkedComboBox, buc.sortByIdUp(buc.getInNet(((Net)NetsComboBox.getSelectedItem()).getId())));
                
            }
        };
        NetsComboBox.addActionListener(actionListener);      
	}
	
	/**
	 * Связывает выпадающий список сетей с выпадающим списком КРТ
	 * @param NetsComboBox - выпадающий список сетей
	 * @param LinkedComboBox - связанный выпадающий список
	 */
	public void netsDBoxComboLinked(final JComboBox NetsComboBox, final JComboBox LinkedComboBox) {
		
		ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LinkedComboBox.removeAllItems();
                setComboBoxItems(LinkedComboBox, dbc.sortByIdUp(dbc.getInNet(((Net)NetsComboBox.getSelectedItem()).getId())));               
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
	public void netsCableComboLinked(final JComboBox NetsComboBox, final JComboBox FromComboBox, final JComboBox ToComboBox, final JComboBox LinkedComboBox, final Integer Type) {
		
		ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	LinkedComboBox.removeAllItems();
            	if (Type < 2)
            		if (NetsComboBox.getSelectedIndex() > -1 && FromComboBox.getSelectedIndex() > -1 && ToComboBox.getSelectedIndex() > -1) {
            			
            			setComboBoxItems(LinkedComboBox, cc.sortByIdUp(cc.getInOwners(Type, ((StructuredElement)FromComboBox.getSelectedItem()).getId(), ((StructuredElement)ToComboBox.getSelectedItem()).getId())));               
            		}
            	
            	if (Type >= 2) {
        			if (NetsComboBox.getSelectedIndex() > -1 && FromComboBox.getSelectedIndex() > -1) {
        				setComboBoxItems(LinkedComboBox, cc.sortByIdUp(cc.getDCableOut((StructuredElement)FromComboBox.getSelectedItem())));
        			}
        		}
            	
            }
        };
        NetsComboBox.addActionListener(actionListener);
        FromComboBox.addActionListener(actionListener);
        ToComboBox.addActionListener(actionListener);
        
	}
	/**
	 * Связывает выпадающий список сетей с выпадающим списком кроссов 
	 * Кроссы сортируются по id. 
	 * @param NetsComboBox - выпадающий список сетей
	 * @param LinkedComboBox - связанный выпадающий список
	 */
	public void netsDFrameComboLinked(final JComboBox NetsComboBox, final JComboBox LinkedComboBox) {
		
		ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LinkedComboBox.removeAllItems();
                setComboBoxItems(LinkedComboBox, dfc.sortByIdUp(dfc.getInNet(((Net)NetsComboBox.getSelectedItem()).getId())));
            }
        };
        NetsComboBox.addActionListener(actionListener);      
	}
	/**
	 * Связывает выпадающий список сетей с обычным списком структурных элементов.
	 * Структурные элементы сортируются по id.
	 * @param NetsComboBox - выпадающий список сетей
	 * @param List - список структурных элементов
	 * @param lc - коллекция структурных элементов
	 */
	public void netsComboBoxLinked(final JComboBox NetsComboBox, final JList List, final StructuredElementCollection lc) {
		
		ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
            	if (NetsComboBox.getSelectedIndex() > -1)
                setListItems(List, lc.sortByIdUp(lc.getInNet(((Net)NetsComboBox.getSelectedItem()).getId())));
                
            }
        };
               
        if (NetsComboBox.getSelectedIndex() > -1) setListItems(List, lc.sortByIdUp(lc.getInNet(((Net)NetsComboBox.getSelectedItem()).getId())));
        
        NetsComboBox.addActionListener(actionListener);
	}
	/**
	 * Связывает выпадающий список сетей и таблицу кабелей
	 * @param netsComboBox - выпадающий список сетей
	 * @param cableTable - таблица кабелей
	 */
	public void linkNetsComboBoxCableTable (final JComboBox netsComboBox, final JTable cableTable) {
		
		ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
            	if (netsComboBox.getSelectedIndex() > -1) {
            		clearTable(cableTable);
            		Iterator<StructuredElement> i = cc.getInNet(((Net)netsComboBox.getSelectedItem()).getId()).iterator();
            		while (i.hasNext()) {
            			addCableToTable(cableTable, (Cable)i.next());
            		}
            	}
            }
		};
        
		if (netsComboBox.getSelectedIndex() > -1) {
			clearTable(cableTable);
			Iterator<StructuredElement> i = cc.getInNet(((Net)netsComboBox.getSelectedItem()).getId()).iterator();
    		while (i.hasNext()) {
    			addCableToTable(cableTable, (Cable)i.next());
    		}
		}
		
    	netsComboBox.addActionListener(actionListener);
        
	}
	/**
	 * Связывает выпадающий список сетей и таблицу абонентов
	 * @param netsComboBox - выпадающий список сетей
	 * @param subscriberTable - таблица абонентов
	 */
	public void linkNetsComboBoxSubscriberTable (final JComboBox netsComboBox, final JTable subscriberTable) {
		
		ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
            	if (netsComboBox.getSelectedIndex() > -1) {
            		clearTable(subscriberTable);
            		Iterator<StructuredElement> i = sc.getInNet(((Net)netsComboBox.getSelectedItem()).getId()).iterator();
            		while (i.hasNext()) {
            			addSubscriberToTable(subscriberTable, (Subscriber)i.next());
            		}
            	}
            }
		};
        
		if (netsComboBox.getSelectedIndex() > -1) {
			clearTable(subscriberTable);
			Iterator<StructuredElement> i = sc.getInNet(((Net)netsComboBox.getSelectedItem()).getId()).iterator();
    		while (i.hasNext()) {
    			addSubscriberToTable(subscriberTable, (Subscriber)i.next());
    		}
		}
		
    	netsComboBox.addActionListener(actionListener);
        
	}
	
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
            	setComboBoxItems(LinkedComboBox, fc.sortByIdUp(fc.getInOwner(((DFramе)dframeComboBox.getSelectedItem()).getId())));
            
            }
        };
       
        LinkedComboBox.removeAllItems();
    	if (dframeComboBox.getSelectedIndex() > -1)
    	setComboBoxItems(LinkedComboBox, fc.sortByIdUp(fc.getInOwner(((DFramе)dframeComboBox.getSelectedItem()).getId())));
        
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
            		setComboBoxItems(LinkedComboBox, bc.sortByIdUp(bc.getInOwnerByTypeUniversal(((Cabinet)cabinetComboBox.getSelectedItem()).getId(), Type)));
            }
        };
        
        LinkedComboBox.removeAllItems();
        if (cabinetComboBox.getSelectedIndex() > -1)
        	setComboBoxItems(LinkedComboBox, bc.sortByIdUp(bc.getInOwnerByTypeUniversal(((Cabinet)cabinetComboBox.getSelectedItem()).getId(), Type)));
    	
    	cabinetComboBox.addActionListener(actionListener);
        
	}
	/**
	 * Связывает выпадающий список типов кбеля с выпадающимим списками структурных элементов, между которыми кабель может быть создан
	 * @param netsComboBox - список сетей
	 * @param typeComboBox - список типов кабеля
	 * @param fromComboBox - список для элементов, из которых выходит кабель
	 * @param toComboBox - список для элементов, в которые приходит кабель
	 */
	public void cableTypeComboLinked(final JComboBox netsComboBox, final JComboBox typeComboBox, final JComboBox fromComboBox, final JComboBox toComboBox){
		
		ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
            	if (netsComboBox.getSelectedIndex() > -1 && typeComboBox.getSelectedIndex() > -1) {
            		Integer type = typeComboBox.getSelectedIndex();
            		Integer netId = ((Net)netsComboBox.getSelectedItem()).getId();
        	
            		fromComboBox.removeAllItems(); fromComboBox.setEnabled(true);
            		toComboBox.removeAllItems(); toComboBox.setEnabled(true);
			
            		if ( type.equals(0)) {
            			setComboBoxItems(fromComboBox, dfc.sortByIdUp(dfc.getInNet(netId)));
            			setComboBoxItems(toComboBox, cbc.sortByNumberUp(cbc.getInNetByClass(netId, 1)));
            		}
    	
            		if ( type.equals(1)) {
            			setComboBoxItems(fromComboBox, cbc.sortByNumberUp(cbc.getInNet(netId)));
            			setComboBoxItems(toComboBox, cbc.sortByNumberUp(cbc.getInNet(netId)));
            		}
    	
            		if ( type.equals(2)) {
            			setComboBoxItems(fromComboBox, cbc.sortByNumberUp(cbc.getInNet(netId)));
            			toComboBox.setEnabled(false);
            			//setComboBoxItems(toComboBox, dbc.sortByIdUp(dbc.getInNet(netId)));
            		}
    	
            		if ( type.equals(3)) {
            			setComboBoxItems(fromComboBox, dfc.sortByIdUp(dfc.getInNet(netId)));
            			toComboBox.setEnabled(false);
            			//setComboBoxItems(toComboBox, dbc.sortByIdUp(dbc.getInNet(netId)));
            		}
            	}
            }
		};
		typeComboBox.addActionListener(actionListener);
        netsComboBox.addActionListener(actionListener);
        
        if (netsComboBox.getSelectedIndex() > -1 && typeComboBox.getSelectedIndex() > -1) {
    		Integer type = typeComboBox.getSelectedIndex();
    		Integer netId = ((Net)netsComboBox.getSelectedItem()).getId();
	
    		fromComboBox.removeAllItems(); fromComboBox.setEnabled(true);
    		toComboBox.removeAllItems(); toComboBox.setEnabled(true);
	
    		if ( type.equals(0)) {
    			setComboBoxItems(fromComboBox, dfc.sortByIdUp(dfc.getInNet(netId)));
    			setComboBoxItems(toComboBox, cbc.sortByNumberUp(cbc.getInNetByClass(netId, 1)));
    		}

    		if ( type.equals(1)) {
    			setComboBoxItems(fromComboBox, cbc.sortByNumberUp(cbc.getInNet(netId)));
    			setComboBoxItems(toComboBox, cbc.sortByNumberUp(cbc.getInNet(netId)));
    		}

    		if ( type.equals(2)) {
    			setComboBoxItems(fromComboBox, cbc.sortByNumberUp(cbc.getInNet(netId)));
    			toComboBox.setEnabled(false);
    			//setComboBoxItems(toComboBox, dbc.sortByIdUp(dbc.getInNet(netId)));
    		}

    		if ( type.equals(3)) {
    			setComboBoxItems(fromComboBox, dfc.sortByIdUp(dfc.getInNet(netId)));
    			toComboBox.setEnabled(false);
    			//setComboBoxItems(toComboBox, dbc.sortByIdUp(dbc.getInNet(netId)));
    		}
    	}
		
	}
	/**
	 * Связывает список шкафов или кроссов с выпадающим списком свободных мест
	 * @param structuredElementComboBox - список шкафов
	 * @param placesComboBox - список свободных мест
	 */
	public void structuredElementPlaceComboLinked(final JComboBox structuredElementComboBox, final JComboBox placesComboBox, final ConnectedPointElementCollection bc) {
		
        ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
            	placesComboBox.removeAllItems();
            	
            	if (structuredElementComboBox.getSelectedIndex() > -1) {
            		StructuredElement cab = (StructuredElement)structuredElementComboBox.getSelectedItem();
            			
            		for (int i = 0; i < cab.getPlacesCount(); i++) {
            			int k = 0;
            			Iterator<?> it = bc.getInOwner(cab.getId()).iterator();
            			while (it.hasNext())  if (((ConnectedPointElement)it.next()).getPlaceNumber().intValue() == i)  { k = 1; break;}
            			if (k == 0) placesComboBox.addItem((Integer)i);
            		}
            	}
            }
        };
        
         placesComboBox.removeAllItems();
    	
        if (structuredElementComboBox.getSelectedIndex() > -1) {
        	StructuredElement cab = (StructuredElement)structuredElementComboBox.getSelectedItem();
        		
    			for (int i = 0; i < cab.getPlacesCount(); i++) {
    				int k = 0;
    				Iterator<?> it = bc.getInOwner(cab.getId()).iterator();
    				while (it.hasNext())  if (((ConnectedPointElement)it.next()).getPlaceNumber().intValue() == i)  { k = 1; break;}
    				if (k == 0) placesComboBox.addItem((Integer)i);
    			}
    		
        }
    	structuredElementComboBox.addActionListener(actionListener);   
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
				if (!V.validateNetName(name.getText())) { newError(iFrame, "Неверный формат названия сети!");return;}
				if (net != null) {
					Net oldNet =  net;
					net.setName(name.getText());
					rw.addLogMessage("Сеть изменена:" + oldNet.toString() +" => " + net.toString());
					newInfo(iFrame, "Изменения сохранены");
				}
				else {
					Net newNet = new Net(); 
					newNet.setName(name.getText()); 
					nc.addElement(newNet);
					String mes = "Создана сеть: "+ newNet.toString();
					rw.addLogMessage(mes);
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
		
		newLabel("Сеть:", iFrame, 20, 15, 360, 25);
		final JComboBox comboBox = newNetsComboBox(iFrame, 20, 40, 360, 25);
		
		newLabel("Улица (до 150 символов):", iFrame, 20, 75, 360, 14);
		final JTextField street = newTextField(iFrame, 20, 100, 360, 25);
		
		newLabel("Дом № (1-4 символа: А-Я,а-я,0-9):", iFrame, 20, 135, 360, 14);
		final JTextField number = newTextField(iFrame, 20, 160, 360, 25);
		
		newLabel("Название (до 150 символов):", iFrame, 20, 195, 360, 14);
		final JTextField name = newTextField(iFrame, 20, 220, 360, 25);
		
		if (building != null){ 
			iFrame.setTitle("Редактировать здание");
			
			comboBox.setSelectedItem(nc.getElement(building.getNet()));
			comboBox.setEnabled(false);
			
			name.setText(building.getName());
			street.setText(building.getStreet());
			number.setText(building.getSNumber());
		}
		
		JButton saveButton = newButton("Сохранить", iFrame, 20, 260, 110, 25);
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (comboBox.getSelectedIndex() == -1) { newError(iFrame, "Не выбрана сеть!"); return; }
				if (!V.validateOtherParametr(street.getText())) { newError(iFrame, "Неверный формат названия улицы!");return;}
				if (!V.validateBuildingNumber(number.getText())) { newError(iFrame, "Неверный формат номера дома!");return;}
				if (!V.validateOtherParametr(name.getText())) { newError(iFrame, "Неверный формат названия здания!");return;}
				
				if (building != null) {
					Building oldBuilding =  building;
					building.setName(name.getText());
					building.setSNumber(number.getText());
					building.setStreet(street.getText());
					rw.addLogMessage("Здание изменено:" + oldBuilding.toString() +" => " + building.toString());
					newInfo(iFrame, "Изменения сохранены");
				}
				else {
					Building newBuilding = new Building(); 
					newBuilding.setName(name.getText());
					newBuilding.setSNumber(number.getText());
					newBuilding.setStreet(street.getText());
					newBuilding.attachToNet((Net)comboBox.getSelectedItem());
					buc.addElement(newBuilding);
					String mes = "Создано здание: "+ newBuilding.toString();
					rw.addLogMessage(mes);
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
				if (!V.validatePathName(textField.getText())) { newError(iFrame, "Неверный формат названия включения!");return;}
				if (!V.validatePathTransit(transitText.getText())) { newError(iFrame, "Неверный формат пары перехода!");return;}
				
				if (path != null) {
					Path oldPath =  path;
					path.setName(textField.getText());
					path.setTransit(transitText.getText());
					rw.addLogMessage("Включение изменено:" + oldPath.toString() +" => " + path.toString());
					newInfo(iFrame, "Изменения сохранены");
					v.set(0, path);
				}
				else {
					Path newPath = new Path(sc,pc); 
					newPath.setName(textField.getText());
					newPath.setTransit(transitText.getText());
					newPath.setSubscriber(sub);
					phc.addElement(newPath);
					String mes = "Создано включение: "+ newPath.toString() + ", для абонента " + sub.toString();
					rw.addLogMessage(mes);
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
		
		newLabel("Сеть:", iFrame, 20, 15, 360, 25);
		
		final JComboBox comboBox = newNetsComboBox(iFrame, 20, 40, 360, 25);
		if (sub != null) {
			iFrame.setTitle("Редактировать абонента");
			comboBox.setSelectedItem(nc.getElement(sub.getNet()));
			comboBox.setEnabled(false);
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
				Net selectedNet = (Net)comboBox.getSelectedItem();
										
				if (comboBox.getSelectedIndex() == -1) { newError(iFrame, "Не выбрана сеть!"); return; }
				if (!V.validateSubscriberName(name.getText())) {newError(iFrame, "Неверный формат имени абонента!"); return;}
				if (!V.validatePhoneNumber(phoneNumber.getText())) {newError(iFrame, "Неверный формат номера телефона!"); return;}
				
				if (!V.validateOtherParametr(subscriberAdress.getText())) { newError(iFrame, "Неверный формат адреса абонента (до 150 символов)!"); return; }
				if (!V.validateOtherParametr(subscriberEquipment.getText())) { newError(iFrame, "Неверный формат типа оборудования (до 150 символов)!"); return; }
				if (!V.validateOtherParametr(subscriberDate.getText())) { newError(iFrame, "Неверный формат даты установки абонента (до 150 символов)!"); return; }
				
				Subscriber s = (Subscriber)sc.findByPhoneNumber(phoneNumber.getText(), selectedNet.getId());
										
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
					rw.addLogMessage("Абонент изменен: " + oldSub + " => " + sub.toString());
					newInfo(iFrame, "Изменения сохранены");
				}
				else {
					if (s != null)
						if (newDialog(iFrame, "Абонент с таким телефонным номером уже сущесвует в этой сети! \r\n Создать еще одного абонента с данным номером?") == JOptionPane.NO_OPTION) { return; }
					
					Subscriber newSubscriber = new Subscriber();
					Path newPath = new Path(sc,pc);
					
					newSubscriber
						.setDate(subscriberDate.getText())
						.setAdress(subscriberAdress.getText())
						.setEquipment(subscriberEquipment.getText())
						.attachToNet(selectedNet)
						.setName(name.getText());
					newSubscriber.setPhoneNumber(phoneNumber.getText());
								
					sc.addElement(newSubscriber);
					v.set(0, newSubscriber);
					newPath.setSubscriber(newSubscriber);
					phc.addElement(newPath);
					
					String mes = "Создан абонент: "+ newSubscriber.toString()+ ", добавлен в сеть: "+ selectedNet.toString();
					rw.addLogMessage(mes);
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
	 * Создает и выводит на экран форму создания/режактирования элемента "Кросс"
	 * @param dframe - элемент "Кросс", если null - выводится форма создания нового элемента
	 */
	public void formDFrame(final DFramе dframe) {
		
		final JDialog iFrame = newDialog("Создать кросс", 410, 285);
		if (dframe != null) iFrame.setTitle("Редактировать кросс");
		
		newLabel("Добавить в сеть:", iFrame, 20, 15, 360, 25);
		final JComboBox comboBox = newNetsComboBox(iFrame, 20, 40, 360, 25);
		
		newLabel("Название кросса (1-50 символов):", iFrame, 20, 75, 360, 25);
		final JTextField textField = newTextField(iFrame,20, 100, 360, 25);
		
		newLabel("Мест в кроссе:", iFrame, 20, 135, 360, 25);
		final JComboBox placesBox = new JComboBox();
		placesBox.addItem((Integer)10);
		placesBox.addItem((Integer)20);
		placesBox.addItem((Integer)30);
		placesBox.setBounds(20, 160, 360, 25);
		iFrame.getContentPane().add(placesBox);

		if (dframe != null) {
			comboBox.removeAllItems();
			comboBox.addItem(nc.getElement(dframe.getNet()));
			comboBox.setSelectedIndex(0);
			comboBox.setEnabled(false);
			textField.setText(dframe.getName());
			placesBox.setSelectedItem(dframe.getPlacesCount());
			placesBox.setEnabled(false);
		}
		
		JButton saveButton = newButton("Сохранить", iFrame, 20, 200, 110, 25);
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if (comboBox.getSelectedIndex() == -1) { newError(iFrame, "Не выбрана сеть!"); return; }
				if (!V.validateDFrameName(textField.getText())) { newError(iFrame, "Неверный формат названия кросса!"); return;};
				
					if (dframe != null) {
					String oldDFrame = dframe.toString();
					dframe.setName(textField.getText());
					rw.addLogMessage("Кросс изменен: " + oldDFrame + " => " + dframe.toString());
					newInfo(iFrame, "Изменения сохранены");
				}
				else {
					DFramе newDFrame = new DFramе(); 
					newDFrame.setName(textField.getText()); 
					newDFrame.attachToNet((Net)comboBox.getSelectedItem());
					newDFrame.setPlacesCount((Integer)placesBox.getSelectedItem());
					dfc.addElement(newDFrame);
					String mes = "Создан кросс: "+ newDFrame.toString()+ ", присоединён к сети: "+((Net)comboBox.getSelectedItem()).toString();
					rw.addLogMessage(mes);
					newInfo(iFrame, mes);
				}
				iFrame.dispose();
			}
		});
		iFrame.setVisible(true);
	}
	/**
	 * Создает и выводит на экран форму создания/редактирования элемента "Шкаф"
	 * @param cabinet - элемент "Шкаф", если null - выводится форма создания нового элемента
	 */
	public void formCabinet(final Cabinet cabinet) {
		
		final int iFrameMinWidth = 410, iFrameMaxWidth = 830, iFrameMinHeight = 370, iFrameMaxHeight = 430;
		
		final JDialog iFrame = newDialog("Создать шкаф", iFrameMinWidth, iFrameMinHeight);
		 
		newLabel("Добавить в сеть:", iFrame, 20, 15, 360, 25);
		final JComboBox comboBox = newNetsComboBox(iFrame, 20, 40, 360, 25);
		
		newLabel("Номер шкафа (1-4 символа: А-Я,а-я,0-9):", iFrame, 20, 75, 360, 25);
		final JTextField formatedText = newTextField(iFrame, 20, 100, 360, 25);
		
		newLabel("Мест в шкафу:", iFrame, 20, 135, 360, 25);
		final JComboBox comboBox1 = new JComboBox();
		comboBox1.addItem((Integer)3);
		comboBox1.addItem((Integer)4);
		comboBox1.addItem((Integer)6);
		comboBox1.addItem((Integer)12);
		comboBox1.setSelectedIndex(3);
		comboBox1.setBounds(20, 160, 360, 25);
		iFrame.getContentPane().add(comboBox1);
		
		newLabel("Класс шкафа (1-2):", iFrame, 20, 195, 360, 25);
		final JTextField cabinetClass = newTextField(iFrame, 20, 220, 360, 25);
		
		if (cabinet != null) {
			
			iFrame.setTitle("Редактировать шкаф");
			
			comboBox.setSelectedItem(nc.getElement(cabinet.getNet()));
			comboBox.setEnabled(false);
			formatedText.setText(cabinet.getSNumber());
			
			comboBox1.setSelectedItem(cabinet.getPlacesCount());
			comboBox1.setEnabled(false);
			
			cabinetClass.setText(cabinet.getCabinetClass().toString());
			cabinetClass.setEnabled(false);
		}
		
		/*
		 * Дополнительные параметры шкафа
		 */
		newLabel("Адрес:", iFrame, 420, 15, 360, 25);
		final JTextField cabinetAdress = newTextField(iFrame, 420, 40, 360, 25);
		
		newLabel("Место расположения:", iFrame, 420, 75, 360, 25);
		final JTextField cabinetPlase = newTextField(iFrame, 420, 100, 360, 25);
		
		newLabel("Конструкция, материал:", iFrame, 420, 135, 360, 25);
		final JTextField cabinetMaterual = newTextField(iFrame, 420, 160, 360, 25);
		
		newLabel("Дата установки:", iFrame, 420, 195, 360, 25);
		final JTextField cabinetDate = newTextField(iFrame, 420, 220, 360, 25);
		
		newLabel("Cпособ установки:", iFrame, 420, 255, 360, 25);
		final JComboBox cabinetSetup = new JComboBox();
		cabinetSetup.addItem("Без шкафной коробки");
		cabinetSetup.addItem("Со шкафной коробки");
		cabinetSetup.setSelectedIndex(0);
		cabinetSetup.setBounds(420, 280, 360, 25);
		iFrame.getContentPane().add(cabinetSetup);		
		
		newLabel("Уличный или в помещении:", iFrame, 420, 315, 360, 25);
		final JComboBox cabinetArea = new JComboBox();
		cabinetArea.addItem("Уличный");
		cabinetArea.addItem("В помещении");
		cabinetArea.setSelectedIndex(0);
		cabinetArea.setBounds(420, 340, 360, 25);
		iFrame.getContentPane().add(cabinetArea);		
			
		if (cabinet != null) {
			cabinetAdress.setText(cabinet.getAdress());
			cabinetPlase.setText(cabinet.getPlace());
			cabinetMaterual.setText(cabinet.getMaterial());
			cabinetDate.setText(cabinet.getDate());
			cabinetSetup.setSelectedIndex(cabinet.getSetup());
			cabinetArea.setSelectedIndex(cabinet.getArea());	
		}
		/*
		 * ------------------------------
		 */
		
		JButton saveButton = newButton("Сохранить", iFrame, 20, 280, 110, 25);
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if (comboBox.getSelectedIndex() == -1) { newError(iFrame, "Не выбрана сеть!"); return; }
				if (!V.validateCabinetNumber(formatedText.getText())) { newError(iFrame, "Неверный номер шкафа!"); return; }
				if (!V.validateCabinetClass(cabinetClass.getText())) { newError(iFrame, "Неверный класс шкафа!"); return; }
				if (!V.validateOtherParametr(cabinetAdress.getText())) { newError(iFrame, "Неверный формат адреса шкафа (до 150 символов)!"); return; }
				if (!V.validateOtherParametr(cabinetPlase.getText())) { newError(iFrame, "Неверный формат места расположения шкафа (до 150 символов)!"); return; }
				if (!V.validateOtherParametr(cabinetMaterual.getText())) { newError(iFrame, "Неверный формат материала шкафа (до 150 символов)!"); return; }
				if (!V.validateOtherParametr(cabinetDate.getText())) { newError(iFrame, "Неверный формат даты установки шкафа (до 150 символов)!"); return; }
				
				Net selectedNet = (Net)comboBox.getSelectedItem();
				String cabinetNumber = formatedText.getText();

				if (cabinet != null) {
					
					Cabinet b = cbc.elementInNet(cabinetNumber, selectedNet.getId());
					if (b != null && !cabinet.getId().equals(b.getId())) {newError(iFrame, "Шкаф с номером "+cabinetNumber+" уже сущесвует в этой сети"); return;}
					
					String old = cabinet.toString();
					cabinet
						.setAdress(cabinetAdress.getText())
						.setPlace(cabinetPlase.getText())
						.setMaterial(cabinetMaterual.getText())
						.setDate(cabinetDate.getText())
						.setSetup(cabinetSetup.getSelectedIndex())
						.setArea(cabinetArea.getSelectedIndex())
						.setSNumber(cabinetNumber);
					rw.addLogMessage("Шкаф изменен: " + old + " => " + cabinet.toString());
					newInfo(iFrame, "Изменения сохранены");
				}
				else {
					
					if (cbc.elementInNet(cabinetNumber, selectedNet.getId()) != null) {
						newError(iFrame, "Шкаф с номером "+cabinetNumber+" уже сущесвует в этой сети");
						return;
					}
					
					Cabinet newCabinet = new Cabinet(); 
					newCabinet
						.setAdress(cabinetAdress.getText())
						.setPlace(cabinetPlase.getText())
						.setMaterial(cabinetMaterual.getText())
						.setDate(cabinetDate.getText())
						.setSetup(cabinetSetup.getSelectedIndex())
						.setArea(cabinetArea.getSelectedIndex())
						.attachToNet(selectedNet)
						.setPlacesCount((Integer)comboBox1.getSelectedItem())
						.setSNumber(cabinetNumber);
						newCabinet.setCabinetClass(rw.valueOf(cabinetClass.getText()));
					cbc.addElement(newCabinet);
					String mes = "Создан шкаф: "+ newCabinet.toString()+ ", присоединён к сети: "+ selectedNet.toString();
					rw.addLogMessage(mes);
					newInfo(iFrame, mes);
				}
				iFrame.dispose();
			}
		});
		
		newMoreButton(iFrame,iFrameMinWidth,iFrameMaxWidth,iFrameMinHeight, iFrameMaxHeight, 320, 280, 60, 25);
	
		iFrame.setVisible(true);
	}
	/**
	 * Создает и выводит на экран форму создания/редактирования кабеля
	 * @param cable - элемент "Кабель", если null - выводится форма создания нового элемента
	 */
	public Cable formCable(final Cable cable) {
		final Vector<Cable> v = new Vector<Cable>(); v.add(null);
		final int iFrameMinWidth = 410, iFrameMaxWidth = 830, iFrameMinHeight = 520, iFrameMaxHeight = 520;
		final JDialog iFrame = newDialog("Создать кабель", iFrameMinWidth, iFrameMinHeight);
		
		newLabel("Добавить в сеть:", iFrame, 20, 15, 360, 25);
		final JComboBox netsComboBox = newNetsComboBox(iFrame, 20, 40, 360, 25);
		
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
		cableTypeComboLinked(netsComboBox, typeComboBox, fromComboBox, toComboBox);
		
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
			netsComboBox.setSelectedItem(nc.getElement(cable.getNet()));
			netsComboBox.setEnabled(false);
			typeComboBox.setSelectedIndex(cable.getType());
			typeComboBox.setEnabled(false);
			comboBox2.setSelectedItem(cable.getCapacity());
			comboBox2.setEnabled(false);
			comboBox3.setSelectedItem(cable.getLabel());
			
			if (cable.getType().equals(0)) {
				fromComboBox.setSelectedItem(dfc.getElement(cable.getFrom()));
				toComboBox.setSelectedItem(cbc.getElement(cable.getTo()));
			}
			if (cable.getType().equals(1)) {
				fromComboBox.setSelectedItem(cbc.getElement(cable.getFrom()));
				toComboBox.setSelectedItem(cbc.getElement(cable.getTo()));
			}
			if (cable.getType().equals(2)) {
				fromComboBox.setSelectedItem(cbc.getElement(cable.getFrom()));
				//toComboBox.setSelectedItem(dbc.getElement(cable.getTo()));
			}
			if (cable.getType().equals(3)) {
				fromComboBox.setSelectedItem(dfc.getElement(cable.getFrom()));
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
									
				if (netsComboBox.getSelectedIndex() == -1) { newError(iFrame, "Не выбрана сеть!"); return; }
				if (!V.validateCableNumber(cableNumber.getText())) { newError(iFrame, "Неверный формат номера кабеля!"); return;}						
				if (!V.validateCableLenght(cableLenght.getText())) { newError(iFrame, "Неверный формат длины кабеля!"); return;}						
				if (!V.validateCableWireDiametr(cableWireDiametr.getText())) { newError(iFrame, "Неверный формат диаметра жилы кабеля!"); return;}						
				if (!V.validateCableYear(cableYear.getText())) { newError(iFrame, "Неверный формат года прокладки кабеля!"); return;}						
				
				Net selectedNet = (Net)netsComboBox.getSelectedItem();
				Integer type = typeComboBox.getSelectedIndex();
				Integer number = rw.valueOf(cableNumber.getText());
				StructuredElement from = (StructuredElement)fromComboBox.getSelectedItem();
				StructuredElement to = (StructuredElement)toComboBox.getSelectedItem();
				
				
				if (cable != null) {
					v.set(0, cable);
					Cable b = cc.getInOwner(from.getId(), number, type);
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
						b = cc.getInOwner(to.getId(), number, type);
						if (b != null && !cable.getId().equals(b.getId())) {
						//	newError(iFrame, "Кабель такого типа и номера уже существует в "+to.toString()+"!"); 
							if (newDialog(iFrame, "Кабель такого типа и номера уже существует в "+to.toString()+"! Создать еще один?") == JOptionPane.NO_OPTION)
								
							return;
						}
					}
					cable.setNumber(number);
					cable
						.setLabel((String)comboBox3.getSelectedItem())	
						.setLenght(rw.valueOf(cableLenght.getText()))
						.setWireDiametr(cableWireDiametr.getText())
						.setYear(cableYear.getText())
						.setStatus(cableStatus.getSelectedIndex());
					
					rw.addLogMessage("Кабель изменен: " +cable.toString());
					newInfo(iFrame, "Изменения сохранены");
					
				}
				else {
					//аналогично
					//if (type > 0)
						if (cc.getInOwner(from.getId(), number, type) != null) {
						//	newError(iFrame, "Кабель такого типа и номера уже существует в "+from.toString()+"!"); 
							if (newDialog(iFrame, "Кабель такого типа и номера уже существует в "+from.toString()+"! Создать еще один?") == JOptionPane.NO_OPTION)
							return;
						}
					
					if (type.equals(1))
						if (from.getId().equals(to.getId())) {newError(iFrame, "Выберите разные шкафы!"); return; }
					
					if (type < 2)
						if (cc.getInOwner(to.getId(), number, type) != null) {
							//newError(iFrame, "Кабель такого типа и номера уже существует в "+to.toString()+"!"); 
							if (newDialog(iFrame, "Кабель такого типа и номера уже существует в "+to.toString()+"! Создать еще один?") == JOptionPane.NO_OPTION)
							return;
						}
					//}
					
					Cable newCable = new Cable(dfc,cbc,dbc,fc,bc,pc); 
					newCable.attachToNet(selectedNet);
					newCable
						.setType(type)
						.setLabel((String)comboBox3.getSelectedItem())
						.setLenght(rw.valueOf(cableLenght.getText()))
						.setYear(cableYear.getText())
						.setStatus(cableStatus.getSelectedIndex())
						.setWireDiametr(cableWireDiametr.getText())
						.setCapacity((Integer)comboBox2.getSelectedItem())
						.setFrom(from.getId())
						.setNumber(number);
					if (type < 2) newCable.setTo(to.getId());
					if (type >= 2) newCable.setTo(0);
					
					cc.addElement(newCable); v.set(0, newCable);
					String mes = "Создан "+(String)typeComboBox.getSelectedItem()+" кабель: "+ newCable.toString()+ ", присоединён к сети: "+ selectedNet.toString();
					newInfo(iFrame, mes);
					rw.addLogMessage(mes);
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
		 
		newLabel("Добавить в сеть:", iFrame, 20, 15, 360, 25);
		final JComboBox comboBox = newNetsComboBox(iFrame, 20, 40, 360, 25);
		
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
			
			comboBox.setSelectedItem(nc.getElement(man.getNet()));
			comboBox.setEnabled(false);
			manholeNumberText.setText(man.getSNumber());
			manholeConstruction.setSelectedIndex(man.getConstruction());
			manholeForm.setSelectedIndex(man.getForm());
			
		}
		
		JButton saveButton = newButton("Сохранить", iFrame, 20, 280, 110, 25);
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if (comboBox.getSelectedIndex() == -1) { newError(iFrame, "Не выбрана сеть!"); return; }
				if (!V.validateCabinetNumber(manholeNumberText.getText())) { newError(iFrame, "Неверный номер колодца!"); return; }
				if (!V.validateDate(manholeDate.getText())) { newError(iFrame, "Неверный формат даты постройки!"); return; }
				if (!V.validateOtherParametr(manholeAdress.getText())) { newError(iFrame, "Неверный формат адреса колодца!"); return; }
				if (!V.validateOtherParametr(manholeSize.getText())) { newError(iFrame, "Неверный формат размера колодца!"); return; }
				
				Net selectedNet = (Net)comboBox.getSelectedItem();
				String manholeNumber = manholeNumberText.getText();

				if (man != null) {
					
					Manhole b = mc.elementInNet(manholeNumber, selectedNet.getId());
					if (b != null && !man.getId().equals(b.getId())) {newError(iFrame, "Колодец с номером " + manholeNumber + " уже сущесвует в этой сети"); return;}
					
					String old = man.toString();
					man
						.setAdress(manholeAdress.getText())
						.setDate(manholeDate.getText())
						.setSize(manholeSize.getText())
						.setConstruction(manholeConstruction.getSelectedIndex())
						.setForm(manholeForm.getSelectedIndex())
						.setSNumber(manholeNumber);
					rw.addLogMessage("Колодец изменен: " + old + " => " + man.toString());
					newInfo(iFrame, "Изменения сохранены");
				}
				else {
					
					if (mc.elementInNet(manholeNumber, selectedNet.getId()) != null) {
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
						.attachToNet(selectedNet)
						.setSNumber(manholeNumber);
						
					mc.addElement(newManhole);
					String mes = "Создан колодец: "+ newManhole.toString()+ ", добавлен в сеть: "+ selectedNet.toString();
					rw.addLogMessage(mes);
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
		
		newLabel("Добавить в сеть:", iFrame, 20, 15, 360, 25);
		final JComboBox netsComboBox = newNetsComboBox(iFrame, 20, 40, 360, 25);
		
		newLabel("От:", iFrame, 20, 75, 360, 25);
		final JComboBox fromComboBox = manholeDFrameComboBox(netsComboBox, iFrame, 20, 100, 360, 25);
		final JComboBox fromSideComboBox = new JComboBox();
		fromSideComboBox.addItem("Спереди");
		fromSideComboBox.addItem("Справа");
		fromSideComboBox.addItem("Сзади");
		fromSideComboBox.addItem("Слева");
		iFrame.getContentPane().add(fromSideComboBox);
		fromSideComboBox.setBounds(20, 135, 360, 25);
		
		newLabel("До:", iFrame, 20, 170, 360, 25);
		final JComboBox toComboBox = manholeCabinetBuildingComboBox(netsComboBox, iFrame, 20, 195, 360, 25);
		final JComboBox toSideComboBox = new JComboBox();
		toSideComboBox.addItem("Спереди");
		toSideComboBox.addItem("Справа");
		toSideComboBox.addItem("Сзади");
		toSideComboBox.addItem("Слева");
		iFrame.getContentPane().add(toSideComboBox);
		toSideComboBox.setBounds(20, 230, 360, 25);
		
		netsManholeDFrameComboLinked(netsComboBox,fromComboBox);
		netsManholeCabinetBuildingComboLinked(netsComboBox,toComboBox);
		
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
		
		if (duct != null) {
			ductLenght.setText(duct.getLenght().toString());
			tubeDiametr.setText(duct.getTubeDiametr().toString());
			tubeMaterual.setText(duct.getTubeMaterial());
			ductDate.setText(duct.getDate());
			manufacturingМethod.setText(duct.getМanufacturingМethod());
		}
		/*
		 * ------------------------------
		 */
		if (duct != null) {
			iFrame.setTitle("Редактировать канализацию");
			
			netsComboBox.setSelectedItem(nc.getElement(duct.getNet())); netsComboBox.setEnabled(false);
			
			AbstractElement from = mc.getElement(duct.getFrom());
			if (from == null) from = dfc.getElement(duct.getFrom());
			
			AbstractElement to = mc.getElement(duct.getTo());
			if (to == null) to = cbc.getElement(duct.getTo());
			if (to == null) to = buc.getElement(duct.getTo());
			
			
			fromComboBox.setSelectedItem(from);
			toComboBox.setSelectedItem(to);
			
			fromSideComboBox.setSelectedIndex(duct.getFromSide());
			toSideComboBox.setSelectedIndex(duct.getToSide());
			
			capacityText.setText(((Integer)tuc.getDuctsTubes(duct).size()).toString()); capacityText.setEnabled(false);
		}
        
		JButton saveButton = newButton("Сохранить", iFrame, 20, 340, 110, 25);
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (netsComboBox.getSelectedIndex() == -1) { newError(iFrame, "Не выбрана сеть!"); return; }
				if (fromComboBox.getSelectedIndex() == -1) { newError(iFrame, "Не выбрано начало канализации!"); return; }
				if (toComboBox.getSelectedIndex() == -1) { newError(iFrame, "Не выбран конец канализации!"); return; }
				if (!V.validateDuctCapacity(capacityText.getText())) { newError(iFrame, "Неверный формат емкости канализации!"); return; }
				if (!V.validateDuctLenght(ductLenght.getText())) { newError(iFrame, "Неверный формат длины канализации!"); return; }
				if (!V.validateTubeDiametr(tubeDiametr.getText())) { newError(iFrame, "Неверный формат диаметра канала!"); return; }
				if (!V.validateDate(ductDate.getText())) { newError(iFrame, "Неверный формат даты!"); return; }
				if (!V.validateOtherParametr(tubeMaterual.getText())) { newError(iFrame, "Неверный формат материала трубопровода!"); return; }
				if (!V.validateOtherParametr(manufacturingМethod.getText())) { newError(iFrame, "Неверный формат способа изготовления!"); return; }
				
				Net selectedNet = (Net)netsComboBox.getSelectedItem();
				Integer capacity = rw.valueOf(capacityText.getText());
				StructuredElement elementFrom = (StructuredElement)fromComboBox.getSelectedItem();
				StructuredElement elementTo = (StructuredElement)toComboBox.getSelectedItem();
				Integer fromSide = fromSideComboBox.getSelectedIndex();
				Integer toSide = toSideComboBox.getSelectedIndex();
				
				Duct d1 = duc.hasDuct(elementFrom, fromSide);
				Duct d2 = duc.hasDuct(elementTo, toSide);
				
				if (elementFrom.getId().equals(elementTo.getId())) { newError(iFrame, "Выберите разные элементы От и До!"); return; }

				if (duct != null) {
					
					if (d1 != null && !duct.getId().equals(d1.getId())) { newError(iFrame, "К элементу От " + elementFrom.toString() + " с выбранной стороны уже примыкает участок канализации " + d1.toString()); return; }
					if (d2 != null && !duct.getId().equals(d2.getId())) { newError(iFrame, "К элементу До " + elementTo.toString() + " с выбранной стороны уже примыкает участок канализации " + d2.toString()); return; }
					
				/*	
					Cabinet b = cbc.elementInNet(cabinetNumber, selectedNet.getId());
					if (b != null && !cabinet.getId().equals(b.getId())) {newError(iFrame, "Шкаф с номером "+cabinetNumber+" уже сущесвует в этой сети"); return;}
				*/	
					String old = duct.toString();
					duct
						.setFromSide(fromSide)
						.setToSide(toSide)
						.setLenght(rw.valueOf(ductLenght.getText()))
						.setTubeDiametr(rw.valueOf(tubeDiametr.getText()))
						.setDate(ductDate.getText())
						.setTubeMaterial(tubeMaterual.getText())
						.setМanufacturingМethod(manufacturingМethod.getText())
						.setFrom(elementFrom.getId())
						.setTo(elementTo.getId());
					rw.addLogMessage("Участок канализации изменен: " + old + " => " + duct.toString());
					newInfo(iFrame, "Изменения сохранены");
				}
				else {
				/*	
					if (cbc.elementInNet(cabinetNumber, selectedNet.getId()) != null) {
						newError(iFrame, "Шкаф с номером "+cabinetNumber+" уже сущесвует в этой сети");
						return;
					}
				*/	
					if (d1 != null) { newError(iFrame, "К элементу От  " + elementFrom.toString() + " с выбранной стороны уже примыкает участок канализации " + d1.toString()); return; }
					if (d2 != null) { newError(iFrame, "К элементу До  " + elementTo.toString() + " с выбранной стороны уже примыкает участок канализации " + d2.toString()); return; }
					
					Duct newDuct = new Duct(dfc, cbc, mc, buc); 
					newDuct
						.setFromSide(fromSide)
						.setToSide(toSide)
						.setLenght(rw.valueOf(ductLenght.getText()))
						.setTubeDiametr(rw.valueOf(tubeDiametr.getText()))
						.setDate(ductDate.getText())
						.setTubeMaterial(tubeMaterual.getText())
						.setМanufacturingМethod(manufacturingМethod.getText())
						.setFrom(elementFrom.getId())
						.setTo(elementTo.getId())
						
						.attachToNet(selectedNet);
					duc.addElement(newDuct);
					String mes = "Создан участок канализации: "+ newDuct.toString()+ ", добавлен в сеть: "+ selectedNet.toString();
					rw.addLogMessage(mes);
					
					for (int i = 0; i < capacity; i++) {
						Tube tube = new Tube();
						tube.setNumber(i);
						tube.setDuct(newDuct);
						tuc.addElement(tube);
						rw.addLogMessage("Создан канал №" + tube.getNumber().toString() + "в канализации " + newDuct.toString());
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
	 * Создает и выводит на экран форму создания/редактирования элемента "Распределительная коробка"
	 * @param dbox - элемент "КРТ", если null - выводится форма создания нового элемента
	 */
	public void formDBox(final DBox dbox) {
		
		final int iFrameMinWidth = 410, iFrameMaxWidth = 830, iFrameMinHeight = 310, iFrameMaxHeight = 310;
		
		final JDialog iFrame = newDialog("Создать КРТ", iFrameMinWidth, iFrameMinHeight);
		
		newLabel("Добавить в сеть:", iFrame, 20, 15, 360, 25);
		final JComboBox netsComboBox = newNetsComboBox(iFrame, 20, 40, 360, 25);
		
		newLabel("Здание:", iFrame, 20, 75, 360, 25);
		final JComboBox buildingsComboBox = buildingComboBox(netsComboBox, iFrame, 20, 100, 360, 25);
		
		netsBuildingComboLinked (netsComboBox,buildingsComboBox);
		
		newLabel("Емкость коробки:", iFrame, 20, 135, 360, 25);
		final JComboBox comboBox1 = new JComboBox();
		comboBox1.addItem((Integer)10);
		comboBox1.setSelectedIndex(0);
		iFrame.getContentPane().add(comboBox1);
		comboBox1.setBounds(20, 160, 360, 25);
		/*
		 * Дополнительные параметры коробки
		 */
		newLabel("Место расположения (до 150 символов):", iFrame, 420, 15, 360, 25);
		final JTextField plase = newTextField(iFrame, 420, 40, 360, 25);
		/*
		 * ----------------
		 */
		if (dbox != null) {
			iFrame.setTitle ("Редактировать КРТ");
			netsComboBox.setSelectedItem(nc.getElement(dbox.getNet())); 
			netsComboBox.setEnabled(false);
			buildingsComboBox.setSelectedItem(buc.getElement(dbox.getBuilding()));
			plase.setText(dbox.getPlase());
		}
		
		JButton saveButton = newButton("Сохранить", iFrame, 20, 220, 110, 25);
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Net selectedNet = (Net)netsComboBox.getSelectedItem();
				
				if (netsComboBox.getSelectedIndex() == -1) { newError(iFrame, "Не выбрана сеть!"); return; }
				if (buildingsComboBox.getSelectedIndex() == -1) { newError(iFrame, "Не выбрано здание!"); return; }
				if (!V.validateOtherParametr(plase.getText())) { newError(iFrame, "Неверный формат места расположения!"); return; }
				if (dbox!= null) {
					
					String old = dbox.toString();
					dbox
						.setBuilding((Building)buildingsComboBox.getSelectedItem())
						.setPlase(plase.getText());
					rw.addLogMessage("Коробка изменена: " + old + " => " + dbox.toString());
					newInfo(iFrame, "Изменения сохранены");
				}
				else {
					DBox newDBox = new DBox(dfc,cbc,fc,bc,pc,cc,buc); 
					newDBox
						.setBuilding((Building)buildingsComboBox.getSelectedItem())
						.setPlase(plase.getText())
						.attachToNet(selectedNet);
					newDBox.setCapacity((Integer)comboBox1.getSelectedItem());				
					dbc.addElement(newDBox);
					String mes = "Создана коробка: "+ newDBox.toString()+ ", присоединена к сети: "+ selectedNet.toString();
					rw.addLogMessage(mes);
					newInfo(iFrame, mes);
				}
				iFrame.dispose();
			}
		});
		newMoreButton(iFrame,iFrameMinWidth,iFrameMaxWidth,iFrameMinHeight, iFrameMaxHeight, 320, 220, 60, 25);
		
		iFrame.setVisible(true);
	}
	/**
	 * Создает и выводит на экран форму создания/режактирования элемента "Бокс"
	 * @param box - элемент "Бокс", если null - выводится форма создания нового элемента
	 */
	public Box formBox(final Box box, final Cabinet cabinet) {
		
	
		final Vector<Box> v = new Vector<Box>(); v.add(null);
		
		final JDialog iFrame = newDialog("Создать бокс", 410, 455);
		if (box != null) iFrame.setTitle("Редактировать бокс");
		
		newLabel("Сеть:", iFrame, 20, 15, 360, 25);
		
		final JComboBox comboBox = newNetsComboBox(iFrame, 20, 40, 360, 25);
		if (cabinet != null) {
			comboBox.setSelectedItem(nc.getElement(cabinet.getNet()));
			comboBox.setEnabled(false);
		}
		
		newLabel("Тип бокса:", iFrame, 20, 75, 360, 25);
		
		final JComboBox comboBox1 = new JComboBox();
		comboBox1.addItem("Магистральный");
		comboBox1.addItem("Передаточный");
		comboBox1.addItem("Распределительный");
		comboBox1.addItem("Универсальный");
		comboBox1.setSelectedIndex(0);
		iFrame.getContentPane().add(comboBox1);
		comboBox1.setBounds(20, 100, 360, 25);
		
		if (box != null) {
			comboBox1.setSelectedIndex(box.getType());
		}
		
		newLabel("Добавить в шкаф:", iFrame, 20, 135, 360, 25);
						
		final JComboBox comboBox2 = cabinetComboBox(comboBox, 0, iFrame, 20, 160, 360, 25);
		netsCabinetComboLinked(comboBox, comboBox2, 0);
		
		if (cabinet != null) {
			comboBox2.setSelectedItem(cabinet);
			comboBox2.setEnabled(false);
		}
		
			
		newLabel("Номер бокса (0-999):", iFrame, 20, 195, 360, 25);
		final JTextField formatedText = newTextField(iFrame, 20, 220, 360, 25);
		
		if (box != null) formatedText.setText(box.getNumber().toString());
		
		newLabel("Емкость бокса:", iFrame, 20, 255, 360, 25);
		
		final JComboBox comboBox3 = new JComboBox();
		
		comboBox3.addItem((Integer)50);
		comboBox3.addItem((Integer)100);
		comboBox3.setSelectedIndex(1);
		comboBox3.setBounds(20, 280, 360, 25);
		iFrame.getContentPane().add(comboBox3);
		if (box != null) {
			comboBox3.setSelectedItem(box.getCapacity());
			comboBox3.setEnabled(false);
		}
		
		newLabel("Место в шкафу:", iFrame, 20, 315, 360, 25);
		final JComboBox comboBox4 = new JComboBox();
		comboBox4.setBounds(20, 340, 360, 25);
		iFrame.getContentPane().add(comboBox4);
		
		structuredElementPlaceComboLinked(comboBox2, comboBox4, bc);
		if (box != null) {
			comboBox4.addItem(box.getPlaceNumber());
			comboBox4.setSelectedItem(box.getPlaceNumber());
		}

		JButton saveButton = newButton("Сохранить", iFrame, 20, 380, 110, 25);
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if (comboBox.getSelectedIndex() == -1) { newError(iFrame, "Не выбрана сеть!"); return; }
				if (comboBox2.getSelectedIndex() == -1) { newError(iFrame, "Не выбран шкаф!"); return; }
				if (comboBox4.getSelectedIndex() == -1) { newError(iFrame, "Не выбрано место!"); return; }
				if (!V.validateBoxNumber(formatedText.getText())) { newError(iFrame, "Не верный номер бокса!"); return; }
				
				Cabinet selectedCabinet = (Cabinet)comboBox2.getSelectedItem();
				Integer boxNumber = rw.valueOf(formatedText.getText());

				if (box != null) {
					Box b = bc.inOwner(boxNumber, selectedCabinet.getId(), comboBox1.getSelectedIndex());
					Box bx = (Box)bc.getInPlace((Integer)comboBox4.getSelectedItem(), selectedCabinet.getId());
					
					if (b != null && !box.getId().equals(b.getId())) {newError(iFrame, "Бокс с таким типом и номером уже сущесвует в этом шкафу!"); return;}
					if (bx != null && !box.getId().equals(bx.getId())) {newError(iFrame, "Данное место в шкафу занято!"); return;}
					
					String old = box.toString();
					box.setType((Integer)comboBox1.getSelectedIndex());
					box
						.attachTo(selectedCabinet)
						.setNumber(boxNumber)
						.setPlaceNumber((Integer)comboBox4.getSelectedItem())
						.setCapacity((Integer)comboBox3.getSelectedItem());
					
					box.setNumber(boxNumber);
					rw.addLogMessage("Бокс изменен: " + old + " => " + box.toString());
					newInfo(iFrame, "Изменения сохранены");
					v.clear(); v.add(box);
				}
				else {
					if (bc.getInPlace((Integer)comboBox4.getSelectedItem(), selectedCabinet.getId()) != null) { newError(iFrame, "Данное место в шкафу занято!"); return; }
					if (bc.inOwner(boxNumber, selectedCabinet.getId(), comboBox1.getSelectedIndex()) != null ) {
						newError(iFrame, "Бокс с таким типом и номером уже сущесвует в этом шкафу!"); return;
					}
					Box newBox = new Box();
					newBox.setType((Integer)comboBox1.getSelectedIndex());
					newBox
						.attachTo(selectedCabinet)
						.setNumber(boxNumber)
						.setPlaceNumber((Integer)comboBox4.getSelectedItem())
						.setCapacity((Integer)comboBox3.getSelectedItem());
					
					bc.addElement(newBox);
					String mes = "Создан "+(String)comboBox1.getSelectedItem()+" бокс: "+newBox.toString()+ ", добавлен в шкаф: "+ selectedCabinet.toString();
					rw.addLogMessage(mes);
					newInfo(iFrame, mes);
					v.clear(); v.add(newBox);
					
				}
				iFrame.dispose();
			}
		});
		
		iFrame.setVisible(true); 
		return v.get(0);
	}
	/**
	 * Создает и выводит на экран форму создания/редактирования элемента "Громполоса"
	 * @param frame - громполоса, если null - выводится форма создания нового элемента
	 */
	public Frame formFrame(final Frame frame, DFramе dframe) {
		final Vector<Frame> v = new Vector<Frame>(); v.add(null);
		
		final JDialog iFrame = newDialog("Создать громполосу", 410, 400);
		if (frame != null) iFrame.setTitle("Редактировать громполосу");
		
		newLabel("Сеть:", iFrame, 20, 15, 360, 25);
		final JComboBox comboBox = newNetsComboBox(iFrame, 20, 40, 360, 25);
		if (dframe != null) {
			comboBox.setSelectedItem(nc.getElement(dframe.getNet()));
			comboBox.setEnabled(false);
		}			
		
		newLabel("Добавить к кроссу:", iFrame, 20, 75, 360, 25);
		final JComboBox comboBox1 = dframeComboBox(comboBox, iFrame, 20, 100, 360, 25);
		netsDFrameComboLinked(comboBox, comboBox1);
		
		if (dframe != null) {
			comboBox1.setSelectedItem(dframe);
			comboBox1.setEnabled(false);
		}	
		
		newLabel("Номер громполосы (0-99):", iFrame, 20, 135, 360, 25);
		final JTextField frameNumberText = newTextField(iFrame, 20, 160, 360, 25);
		if (frame != null) frameNumberText.setText(frame.getNumber().toString());
		
		newLabel("Емкость громполосы:", iFrame, 20, 195, 360, 25);
		final JComboBox comboBox3 = new JComboBox();			
		comboBox3.addItem((Integer)25);
		comboBox3.addItem((Integer)50);
		comboBox3.addItem((Integer)100);
		comboBox3.addItem((Integer)150);
		comboBox3.setSelectedIndex(2);
		iFrame.getContentPane().add(comboBox3);
		comboBox3.setBounds(20, 220, 360, 25);
		if (frame != null) {
			comboBox3.setSelectedItem(frame.getCapacity());
			comboBox3.setEnabled(false);
		}
		
		newLabel("Место в кроссе:", iFrame, 20, 255, 360, 25);
		final JComboBox comboBox4 = new JComboBox();
		comboBox4.setBounds(20, 280, 360, 25);
		iFrame.getContentPane().add(comboBox4);
		structuredElementPlaceComboLinked(comboBox1, comboBox4, fc);
		if (frame != null) {
			comboBox4.addItem(frame.getPlaceNumber());
			comboBox4.setSelectedItem(frame.getPlaceNumber());
		}
		
		JButton saveButton = newButton("Сoхранить", iFrame, 20, 320, 110, 25);
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (comboBox.getSelectedIndex() == -1) { newError(iFrame, "Не выбрана сеть!"); return; }
				if (!V.validateFrameNumber(frameNumberText.getText())) { newError(iFrame, "Неверный формат номера громполосы!"); return; }
				
				DFramе selectedDFrame = (DFramе)comboBox1.getSelectedItem();
				Integer frameNumber = rw.valueOf(frameNumberText.getText());
				
				if (frame != null) {
					
					Frame f = (Frame)fc.isInOwner(frameNumber, selectedDFrame.getId());
					Frame fx = (Frame)fc.getInPlace((Integer)comboBox4.getSelectedItem(), selectedDFrame.getId());
					
					if (f != null && !frame.getId().equals(f.getId())) {newError(iFrame, "Громполоса с таким номером уже сущесвует в данном кроссе!"); return;}
					if (fx != null && !frame.getId().equals(fx.getId())) {newError(iFrame, "Данное место в кроссе занято!"); return;}
					
					String old = frame.toString();
					frame.attachTo(selectedDFrame);
					frame.setNumber(frameNumber);
					frame.setCapacity((Integer)comboBox3.getSelectedItem());
					frame.setPlaceNumber((Integer)comboBox4.getSelectedItem());
					
					rw.addLogMessage("Громполоса изменена: " + old + " => " + frame.toString());
					newInfo(iFrame, "Изменения сохранены");
					v.clear(); v.add(frame);
				}
				else {
					
					if (fc.getInPlace((Integer)comboBox4.getSelectedItem(), selectedDFrame.getId()) != null) { newError(iFrame, "Данное место в кроссе занято!"); return; }
					if (fc.isInOwner(frameNumber, selectedDFrame.getId()) != null ) { newError(iFrame, "Громполоса с таким номером уже сущесвует в этом шкафу!"); return; }
					
					Frame newFrame = new Frame();  
					newFrame.attachTo(selectedDFrame);
					newFrame.setNumber(frameNumber);
					newFrame.setCapacity((Integer)comboBox3.getSelectedItem());
					newFrame.setPlaceNumber((Integer)comboBox4.getSelectedItem());
					fc.addElement(newFrame);
					String mes = "Создана громполоса: "+ newFrame.toString()+ ", добавлена в кросс: "+ selectedDFrame.toString();
					newInfo(iFrame, mes);
					rw.addLogMessage(mes);
					v.clear(); v.add(newFrame);
					
				}
				iFrame.dispose();
			}
		});
		
		iFrame.setVisible(true);
		return v.get(0);
	}
	/**
	 * Создает и выводит на экран форму поиска абонента
	 * @param netId - id сети
	 * @return выбранного среди найденых абонента или null, если ничего не выбрано
	 */
	public Subscriber formSearchSubscriber (final Integer netId) {
		final Vector<Integer> v = new Vector<Integer>(); v.add(0);
		
		final JDialog iFrame = newDialog("Найти абонента", 485, 580);
		newLabel("Телефонный номер:", iFrame, 10, 10, 320, 14);
		final JTextField textField = newTextField(iFrame,10, 30, 320, 25);
		newLabel("Имя:", iFrame, 10, 65, 320, 14);
		final JTextField textField_1 = newTextField(iFrame, 10, 85, 320, 25);
		newLabel("Результаты поиска:", iFrame, 10, 120, 320, 14);
		final JList subscriberList = newList(iFrame, 10, 140, 320, 400);

		JButton findByPhoneButton = newButton("Найти", iFrame, 340, 30, 125, 26);
		JButton findByNameButton = newButton("Найти", iFrame, 340, 85, 125, 26);
		JButton okButton = newButton("Выбрать", iFrame, 340, 140, 125, 26);
		
		/*
		 * Событие кнопки поиска абонента о телефону
		 */
		ActionListener findSubscriberByPhone = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				setListItems(subscriberList, sc.searchByPhone(textField.getText(), netId));
			}
		};
		findByPhoneButton.addActionListener(findSubscriberByPhone);
		/*
		 * ---------------------------------------------------------
		 */
		/*
		 * Событие кнопки поиска абонента о телефону
		 */
		ActionListener findSubscriberByName = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setListItems(subscriberList, sc.searchByName(textField_1.getText(), netId));				
			}
		};
		findByNameButton.addActionListener(findSubscriberByName);
		/*
		 * ---------------------------------------------------------
		 */
		/*
		 * Событие кнопки выбора абонента
		 */	
		ActionListener selectSubscriber = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (subscriberList.getSelectedIndex() == -1) {newError(iFrame,"Абонент не выбран!"); return;}
				v.set(0, 1);
				iFrame.dispose();	
			}
		};
		okButton.addActionListener(selectSubscriber);
		/*
		 * ---------------------------------------------------------
		 */
		iFrame.setVisible(true);
		
		if (v.get(0) == 1) return (Subscriber)subscriberList.getSelectedValue();
		return null;
	}
	/**
	 * Создает и выводит на экран форму поиска кабеля
	 * @param netId - id сети
	 * @return выбранного среди найденых кабелей или null, если ничего не выбрано
	 */
	public Cable formSearchCable (final Integer netId) {
		final Vector<Cable> v = new Vector<Cable>(); v.add(null);
		
		final JDialog iFrame = newDialog("Найти кабель", 485, 530);
		newLabel("Номер кабеля:", iFrame, 10, 10, 320, 14);
		final JTextField cableNumberText = newTextField(iFrame,10, 30, 320, 25);
		
		newLabel("Результаты поиска:", iFrame, 10, 65, 320, 14);
		final JList cableList = newList(iFrame, 10, 85, 320, 400);
		
		//Список кабелей выводиться сразу
		setListItems(cableList, cc.sortByIdUp(cc.getInNet(netId)));
		
		JButton findByNumberButton = newButton("Найти", iFrame, 340, 30, 125, 26);
		
		JButton okButton = newButton("Выбрать", iFrame, 340, 85, 125, 26);
		
		/*
		 * Событие кнопки поиска кабеля по номеру
		 */
		ActionListener findCableByNumber = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				setListItems(cableList, cc.searchBySNumber(cableNumberText.getText(), netId));
			}
		};
		findByNumberButton.addActionListener(findCableByNumber);
		/*
		 * ---------------------------------------------------------
		 */
		
		/*
		 * Событие кнопки выбора кабеля
		 */	
		ActionListener selectCable = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (cableList.getSelectedIndex() == -1) {newError(iFrame,"Кабель не выбран!"); return;}
				v.set(0, (Cable)cableList.getSelectedValue());
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
	 * Создает и выводит на экран форму выбора включения абонента
	 * @param sub - абонент
	 * @return выбранное включение, либо null если ничего не выбрано
	 */
	public Path formSubscriberPaths(Subscriber sub) {
		
		final Vector<Path> v = new Vector<Path>(); v.add(null);
		final JDialog iFrame = newDialog("Абонент: " + sub.toString(), 485, 270);
		
		newLabel("Включения:", iFrame, 10, 10, 320, 14);
		final JList pathList = newList(iFrame, 10, 30, 320, 200);
		setListItems(pathList, phc.sortByIdUp(phc.getSubscriberPaths(sub)));
		pathList.setSelectedIndex(0);
		
		JButton okButton = newButton("Выбрать", iFrame, 340, 30, 125, 26);
		/*
		 * Событие кнопки выбора включения
		 */	
		ActionListener selectPath = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (pathList.getSelectedIndex() == -1) {newError(iFrame,"Включение не выбрано!"); return;}
				v.set(0, (Path)pathList.getSelectedValue());
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
	 * Создает и выводит на экран форму выбора кабеля в канале канализации
	 * @param tube - канал
	 * @return выбранное включение, либо null если ничего не выбрано
	 */
	public Cable formTubesCables(Tube tube) {
		
		final Vector<Cable> v = new Vector<Cable>(); v.add(null);
		final JDialog iFrame = newDialog("Канал: " + tube.toString(), 485, 270);
		
		newLabel("Кабели в канале:", iFrame, 10, 10, 320, 14);
		final JList cableList = newList(iFrame, 10, 30, 320, 200);
		setListItems(cableList, cc.getTubesCables(tube));
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
	 * @return выбранное включение, либо null если ничего не выбрано
	 */
	public Path formPairPaths(Pair pair) {
		
		final Vector<Integer> v = new Vector<Integer>(); v.add(0);
		final JDialog iFrame = newDialog("Пара: " + pair.toString(), 485, 270);
		
		newLabel("Включения:", iFrame, 10, 10, 320, 14);
		final JList pathList = newList(iFrame, 10, 30, 320, 200);
		setListItems(pathList, phc.sortByIdUp(phc.getPairsPath(pair)));
		pathList.setSelectedIndex(0);
		
		JButton okButton = newButton("Выбрать", iFrame, 340, 30, 125, 26);
		/*
		 * Событие кнопки выбора включения
		 */	
		ActionListener selectPath = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (pathList.getSelectedIndex() == -1) {newError(iFrame,"Включение не выбрано!"); return;}
				v.set(0, 1);
				iFrame.dispose();	
			}
		};
		okButton.addActionListener(selectPath);
		/*
		 * ---------------------------------------------------------
		 */
		iFrame.setVisible(true);
		
		if (v.get(0) == 1) return (Path)pathList.getSelectedValue();
		return null;
		
	}
	/**
	 * Создает и выводит на экран форму выбора абонента, использующего данную пару
	 * @param pair - пара
	 * @return выбранный абонент, либо null если ничего не выбрано
	 */
	public Subscriber formPairSubscribers(Pair pair) {
		
		final Vector<Integer> v = new Vector<Integer>(); v.add(0);
		final JDialog iFrame = newDialog("Пара: " + pair.toString(), 485, 270);
		
		newLabel("Абоненты используюшие пару:", iFrame, 10, 10, 320, 14);
		final JList subscriberList = newList(iFrame, 10, 30, 320, 200);
		
		HashSet<Subscriber> s = new HashSet<Subscriber>();
		
		Iterator<Path> i = phc.getPairsPath(pair).iterator();
		while (i.hasNext()) s.add((Subscriber)sc.getElement(i.next().getSubscriber()));
	
		setListItems(subscriberList, sc.sortByIdUp(s));
		subscriberList.setSelectedIndex(0);
		
		JButton okButton = newButton("Выбрать", iFrame, 340, 30, 125, 26);
		/*
		 * Событие кнопки выбора абонента
		 */	
		ActionListener selectSubscriber = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (subscriberList.getSelectedIndex() == -1) {newError(iFrame,"Абонент не выбран!"); return;}
				v.set(0, 1);
				iFrame.dispose();	
			}
		};
		okButton.addActionListener(selectSubscriber);
		/*
		 * ---------------------------------------------------------
		 */
		iFrame.setVisible(true);
		
		if (v.get(0) == 1) return (Subscriber)subscriberList.getSelectedValue();
		return null;
		
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
		
		setListItems(fullList, duc.sortByIdUp(duc.getInNet(net)));
		
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
	/**
	 * Отображает паспорт элемента в браузере
	 * @param fileName - имя файла
	 */
	public void formViewPassport (String fileName) {
		
		try {
			File page = new File(fileName);
		    java.awt.Desktop.getDesktop().browse(page.toURI()); 
		} catch (IOException ex) {
		    rw.writeError(ex.toString());
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
		Cable c = (Cable)cc.getElement(p.getCable());
		
		if (c.getType() == 0) {
			
			if (path.isdrPair()) {
				newError(frame,"Включение занимает пару прямого питания!\r\nНевозможно добавить магистральную пару");
				return null;
			}
			//	if (newDialog(frame, "Включение занимает пару прямого питания!\r\nВсе равно добавить магистральную пару?") == JOptionPane.NO_OPTION) { return false;}
			
			if (path.ismPair())
				if (newDialog(frame, "Включение уже занимает магистральную пару!\r\nЗаменить магистральную пару?") == JOptionPane.NO_OPTION) { return null;}
			
				Pair oldPair = (Pair)pc.getElement(path.getmPair());
				
				path.addmPair(p);
				p.setStatus(1);
				returnedPair = p;
				
				if (oldPair != null) {
					if (phc.isPairUsed(oldPair) == null)  {
						oldPair.setStatus(0);
						rw.addLogMessage("Пара "+ oldPair.toString()+" освобождена ");
					}
					else {
						rw.addLogMessage("Пара "+ oldPair.toString()+"удалена из включения " + path.toString());
					}
					
					returnedPair = oldPair;
				}
				String mes = "Пара "+ p.toString()+" занята включением: " + path.toString();
				rw.addLogMessage(mes);
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
				rw.addLogMessage(mes);
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
				
				Pair oldPair = (Pair)pc.getElement(path.getdbPair());
				
				path.adddbPair(p);
				p.setStatus(1);
				returnedPair = p;
				
				if (oldPair != null) {
					if (phc.isPairUsed(oldPair) == null)  {
						oldPair.setStatus(0);
						rw.addLogMessage("Пара "+ oldPair.toString()+" освобождена ");
					}
					else {
						rw.addLogMessage("Пара "+ oldPair.toString()+" удалена из включения " + path.toString());
					}
					returnedPair = oldPair;
				}
				
				String mes = "Кабельная пара "+ p.toString()+" занята включением: " + path.toString();
				rw.addLogMessage(mes);
				newInfo(frame, mes);
				return returnedPair;
		}
		
		if (c.getType() == 3) {
													
			if (path.ismPair() || path.isdbPair() || path.isicPair()) {
				newError(frame, "Включение занимает маг./межшкаф./распред. пару!\r\nНевозможно добавить пару прямого питания?"); 
				return null;}
			
			if (path.isdrPair())
				if (newDialog(frame, "Включение занимает пару прямого питания!\r\n Заменить пару прямого питания?") == JOptionPane.NO_OPTION) { return null;}
			
			Pair oldPair = (Pair)pc.getElement(path.getdrPair());
			
			path.adddrPair(p);
			p.setStatus(1);
			returnedPair = p;
			
			if (oldPair != null) {
				
				if (phc.isPairUsed(oldPair) == null)  {
					oldPair.setStatus(0);
					rw.addLogMessage("Пара "+ oldPair.toString()+" освобождена ");
				}
				else {
					rw.addLogMessage("Пара "+ oldPair.toString()+" удалена из включения " + path.toString());
				}
				returnedPair = oldPair;
			}
				
				String mes = "Кабельная пара "+ p.toString()+" занята включением: " + path.toString();
				rw.addLogMessage(mes);
				newInfo(frame, mes);
				
				return returnedPair;
		}
		return null;
	}
/*	public void formMap () {
		
		final int iFrameMinWidth = 300, iFrameMaxWidth = 650, iFrameMinHeight = 300, iFrameMaxHeight = 430;
		
		final JDialog iFrame = newDialog("Карта сети", iFrameMinWidth, iFrameMinHeight);
		
		iFrame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		iFrame.getContentPane().add(newGrafics(), BorderLayout.CENTER);
		iFrame.setResizable(true);
		iFrame.setVisible(true);
		
	}
	public JScrollPane newGrafics() {
		
		GraficsPanel graficsPanel = new GraficsPanel ();

		JScrollPane scrollPane = new JScrollPane(graficsPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		graficsPanel.setLayout(new BorderLayout(0, 0));
		JButton b = new JButton("2");
		b.setSize(300, 1000);
		graficsPanel.add(b, BorderLayout.SOUTH);
		
		
		graficsPanel.setSize(2000, 2000);
		
		
		return scrollPane;
	
	}*/
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
	
	public void viewCabinet(final Cabinet cabinet) {
		
		int W = 100, H = 120, marginX = 20, marginY = 20, inLine = 3;
		int lines = (int) Math.ceil ((double)cabinet.getPlacesCount().intValue() / (double)inLine);
		int panelWidth = W * inLine + marginX * (inLine + 1);
		int panelHeight = H * lines + marginY * (lines + 1);
		
		final JDialog iFrame = newDialog("Просмотр шкафа", panelWidth + 40 + 10, panelHeight + 100);
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setToolTipText(cabinet.toString());
		panel.setBackground(new Color(0, 128, 128));
		panel.setBounds(20, 50, panelWidth, panelHeight);
		iFrame.getContentPane().add(panel);
		
		JLabel head = newLabel(cabinet.toString(), iFrame, 20, 10, panelWidth, 30);
		head.setFont(new Font("Dialog", Font.BOLD, 16));
		head.setHorizontalAlignment(SwingConstants.CENTER);
		int x = 0, y = 0;
		
		ActionListener boxClick = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				viewConnectedPointElement((Box)((ElementView)e.getSource()).getElement(), cabinet.getNet(), false );			
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
				if (formBox(null, cabinet) != null ) {
					iFrame.dispose();
					viewCabinet(cabinet);
				}
			}	
		});
		
	
		JMenuItem menuItem_1 = new JMenuItem("Редактировать");
		popupMenu.add(menuItem_1);
		menuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPopupMenu pm = (JPopupMenu) ((JMenuItem)e.getSource()).getParent();
				ElementView ep = (ElementView)pm.getInvoker();
				ConnectedPointElement p = (ConnectedPointElement) ep.getElement();
				
				if (formBox((Box)p, cabinet) != null ) {
					iFrame.dispose();
					viewCabinet(cabinet);
				}
				
			}
		});
		
		JMenuItem menuItem_2 = new JMenuItem("Удалить");
		popupMenu.add(menuItem_2);
		menuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPopupMenu pm = (JPopupMenu) ((JMenuItem)e.getSource()).getParent();
				ElementView ep = (ElementView)pm.getInvoker();
				ConnectedPointElement p = (ConnectedPointElement) ep.getElement();	
				
				if (newDialog(iFrame, "Удалить бокс " + ((Box)p).toString()+" и все пары в нем?") == JOptionPane.YES_OPTION) {
					removeBox((Box)p);
					iFrame.dispose();
					viewCabinet(cabinet);
				}
			}
		});
		
		for (int place = 0; place < cabinet.getPlacesCount(); place++) {
			
			if ( x > inLine - 1)  { x = 0; y++; }
				
				ElementView button = new ElementView();
				button.setBounds(marginX + x*(W + marginX), marginY + y*(H + marginY), W, H);
				panel.add(button);
				button.setElement(null);
				
				addPopupToConnectedPointElement(button, popupMenu);
	
				Box box =  (Box)bc.getInPlace((Integer)place, cabinet.getId());
				if (box != null) {
					button.setText(box.toString());
					button.setToolTipText("Бокс: "+ box.toString());
					button.setElement(box);
					if (box.getType() == 0) button.setBackground(new Color(200, 0, 200));
					if (box.getType() == 1) button.setBackground(new Color(0, 200, 200));
					if (box.getType() == 2) button.setBackground(new Color(200, 200, 0));
					if (box.getType() == 3) button.setBackground(new Color(80, 80, 80));
					button.addActionListener(boxClick);
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
		
		ActionListener frameClick = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				viewConnectedPointElement((Frame)((ElementView)e.getSource()).getElement(), dframe.getNet(), false );			
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
				if (formFrame(null, dframe) != null ) {
					iFrame.dispose();
					viewDFrame(dframe);
				}
			}	
		});
		
	
		JMenuItem menuItem_1 = new JMenuItem("Редактировать");
		popupMenu.add(menuItem_1);
		menuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPopupMenu pm = (JPopupMenu) ((JMenuItem)e.getSource()).getParent();
				ElementView ep = (ElementView)pm.getInvoker();
				ConnectedPointElement p = (ConnectedPointElement) ep.getElement();
				
				if (formFrame((Frame)p, dframe) != null ) {
					iFrame.dispose();
					viewDFrame(dframe);
				}
				
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
					removeFrame((Frame)p);
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
	
				Frame frame =  (Frame)fc.getInPlace((Integer)place, dframe.getId());
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
	
	public Pair viewConnectedPointElement(final ConnectedPointElement element, final Integer netId, final boolean selectMode) {
		
		final Vector<Pair>  returnedPair = new Vector<Pair>(); returnedPair.add(null);
		
		int W = 15, H = 15, marginX = 7, marginY = 7, inLine = 10, labelPlaceLeft = 50, labelPlaceTop = 20, groupDevision = 14, infoListHeght = 200;
		int lines = (int) Math.ceil ((double)element.getCapacity().intValue() / (double)inLine);
		int panelWidth = groupDevision + labelPlaceLeft + W * inLine + marginX * (inLine + 1);
		int panelHeight = labelPlaceTop + H * lines + marginY * (lines + 1);
		
		final JDialog iFrame = newDialog("Просмотр бокса", panelWidth + 40, panelHeight + infoListHeght + 100);
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setToolTipText(element.toString());
		panel.setBackground(new Color(200, 200, 200));
		panel.setBounds(20, 50, panelWidth, panelHeight);
		iFrame.getContentPane().add(panel);
		
		final JTextArea infoArea = newTextArea(iFrame, 20, 40 + panelHeight + 20, panelWidth, infoListHeght);
		JLabel head = newLabel(element.toString(), iFrame, 20, 10, panelWidth, 30);
		head.setFont(new Font("Dialog", Font.BOLD, 16));
		head.setHorizontalAlignment(SwingConstants.CENTER);
		
		//хеш всех созданых кнопок для пар
		HashMap<Pair, ElementView> elementViewHash = new HashMap<Pair, ElementView>();
		
		/*
		 *Событие нажатия на существующую пару 
		 */
		ActionListener pairClick = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Pair p = (Pair)((ElementView)e.getSource()).getElement();
				viewPairInfo(p,infoArea);	
			}
		};
		/*
		 * ----------------------------------
		 */
		
		/*
		 * Событие нажатия на пустое место. 
		 * Используятся для выбора места для расположения создаваемых пар
		 */
		ActionListener placeClick = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selectMode) {
					Pair p = (Pair)((ElementView)e.getSource()).getElement();
					returnedPair.set(0, p);
					iFrame.dispose();
				}
			}
		};
		/*
		 * ----------------------------------
		 */
		
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
				/*
				 * Создание виртуальных пар для пустого места.
				 * В параметр fromNumber записывается номер места.
				 */
				Pair pairForEmptyPlace = new Pair(fc,bc,dbc,cc);
				pairForEmptyPlace.setFromNumber(place);
				button.setElement(pairForEmptyPlace);
				/*
				 * ----------------------------------
				 */
				
				Pair pair = pc.getInPlace((ConnectedPointElement)element, (Integer)place);
				
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
		return returnedPair.get(0);
	}

	public void viewDBox(final DBox element, final Integer netId) {
			
		int W = 15, H = 15, marginX = 7, marginY = 7, inLine = 10, labelPlaceLeft = 50, labelPlaceTop = 20, groupDevision = 14, infoListHeght = 200;
		int lines = (int) Math.ceil ((double)element.getCapacity().intValue() / (double)inLine);
		int panelWidth = groupDevision + labelPlaceLeft + W * inLine + marginX * (inLine + 1);
		int panelHeight = labelPlaceTop + H * lines + marginY * (lines + 1);
		
		final JDialog iFrame = newDialog("Просмотр КРТ", panelWidth + 40, panelHeight + infoListHeght + 100);
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setToolTipText(element.toString());
		panel.setBackground(new Color(200, 200, 200));
		panel.setBounds(20, 50, panelWidth, panelHeight);
		iFrame.getContentPane().add(panel);
		
		final JTextArea infoArea = newTextArea(iFrame, 20, 40 + panelHeight + 20, panelWidth, infoListHeght);
		JLabel head = newLabel(element.toString(), iFrame, 20, 10, panelWidth, 30);
		head.setFont(new Font("Dialog", Font.BOLD, 16));
		head.setHorizontalAlignment(SwingConstants.CENTER);
		
		ActionListener pairClick = new ActionListener() { public void actionPerformed(ActionEvent e) {Pair p = (Pair)((ElementView)e.getSource()).getElement(); viewPairInfo(p, infoArea);}};
		
		ActionListener placeClick = new ActionListener() { public void actionPerformed(ActionEvent e) {}};
		
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
				Pair pairForEmptyPlace = new Pair(fc,bc,dbc,cc);
				pairForEmptyPlace.setFromNumber(place);
				button.setElement(pairForEmptyPlace);
			
				Pair pair = pc.getInPlace((DBox)element, (Integer)place);
				
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
	}
	
	public Pair viewCable(final Cable element, final Integer netId, final boolean selectMode) {
		
		final Vector<Pair>  returnedPair = new Vector<Pair>(); returnedPair.add(null);
		
		int W = 15, H = 15, marginX = 7, marginY = 7, inLine = 10, labelPlaceLeft = 50, labelPlaceTop = 20, groupDevision = 14, infoListHeght = 200;
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
				if (selectMode) {
					Pair p = (Pair)((ElementView)e.getSource()).getElement();
					returnedPair.set(0, p);
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
				Pair pairForEmptyPlace = new Pair(fc,bc,dbc,cc);
				pairForEmptyPlace.setFromNumber(place);
				button.setElement(pairForEmptyPlace);
			
				Pair pair = pc.getInPlace((Cable)element, (Integer)place);
				
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
		return returnedPair.get(0);
	}
	
	public void viewDuct(final Duct duct, final Integer netId) {
		
		HashSet<Tube> h = tuc.getDuctsTubes(duct);
		
		int W = 25, H = 25, marginX = 8, marginY = 8, inLine = 10, labelPlaceLeft = 50, labelPlaceTop = 30, infoListHeght = 200;
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
				Tube tube = tuc.getDuctByNumber(h, place);
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
		
		int manholeButtonW = 80, manholeButtonH = 80;
		int gorizontalDuctW = 100, gorizontalDuctH = 20;
		int verticalDuctW = 20, verticalDuctH = 100;
		
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
		
		Integer manholeId = manhole.getId();
		Iterator<Duct> i = duc.getDucts(manhole).iterator();
		while (i.hasNext()) {
			Duct duct = i.next();
			ElementView ductButton = new ElementView();
			ductButton.setBackground(new Color(200,0,200));
			ductButton.setToolTipText(duct.toString());
			ductButton.setElement(duct);
			ductButton.addActionListener(ductClick);
			
			if (duct.getFrom().equals(manholeId)) {
				if (duct.getFromSide().equals(0)) {ductButton.setBounds(paddingLeft + gorizontalDuctW + margin + (manholeButtonW - verticalDuctW)/2, paddingTop, verticalDuctW, verticalDuctH);}
				if (duct.getFromSide().equals(1)) {ductButton.setBounds(paddingLeft + margin * 2  + gorizontalDuctW + manholeButtonW, paddingTop + verticalDuctH + margin + (manholeButtonH - gorizontalDuctH)/2, gorizontalDuctW, gorizontalDuctH);}
				if (duct.getFromSide().equals(2)) {ductButton.setBounds(paddingLeft + gorizontalDuctW + margin + (manholeButtonW - verticalDuctW)/2, paddingTop + margin * 2 + verticalDuctH + manholeButtonH, verticalDuctW, verticalDuctH);}
				if (duct.getFromSide().equals(3)) {ductButton.setBounds(paddingLeft, paddingTop + verticalDuctH + margin + (manholeButtonH - gorizontalDuctH)/2, gorizontalDuctW, gorizontalDuctH);}		
			}
			
			if (duct.getTo().equals(manholeId)) {
				if (duct.getToSide().equals(0)) {ductButton.setBounds(paddingLeft + gorizontalDuctW + margin + (manholeButtonW - verticalDuctW)/2, paddingTop, verticalDuctW, verticalDuctH);}
				if (duct.getToSide().equals(1)) {ductButton.setBounds(paddingLeft + margin * 2  + gorizontalDuctW + manholeButtonW, paddingTop + verticalDuctH + margin + (manholeButtonH - gorizontalDuctH)/2, gorizontalDuctW, gorizontalDuctH);}
				if (duct.getToSide().equals(2)) {ductButton.setBounds(paddingLeft + gorizontalDuctW + margin + (manholeButtonW - verticalDuctW)/2, paddingTop + margin * 2 + verticalDuctH + manholeButtonH, verticalDuctW, verticalDuctH);}
				if (duct.getToSide().equals(3)) {ductButton.setBounds(paddingLeft, paddingTop + verticalDuctH + margin + (manholeButtonH - gorizontalDuctH)/2, gorizontalDuctW, gorizontalDuctH);}
			}
			
			panel.add(ductButton);
	
		}
		
		JLabel front = new JLabel("Перед");
		front.setBounds(paddingLeft + gorizontalDuctW + margin + (manholeButtonW - verticalDuctW)/2 + verticalDuctW + 10, paddingTop, labelW, labelH);
		panel.add(front);
		
		JLabel right = new JLabel("Право");
		right.setBounds(panelW - labelW - 10, paddingTop + verticalDuctH + margin + (manholeButtonH - gorizontalDuctH)/2 + gorizontalDuctH + 10, labelW, labelH);
		panel.add(right);
	
		JLabel back = new JLabel("Зад");
		back.setBounds(paddingLeft + gorizontalDuctW + margin + (manholeButtonW - verticalDuctW)/2 + verticalDuctW + 10, panelH - labelH - 10, labelW, labelH);
		panel.add(back);
		
		JLabel left = new JLabel("Лево");
		left.setBounds(paddingLeft, paddingTop + verticalDuctH + margin + (manholeButtonH - gorizontalDuctH)/2 + gorizontalDuctH + 10, labelW, labelH);
		panel.add(left);
		
		
		iFrame.setVisible(true);

	}
	/**
	 * Выводит подробные данные о паре
	 * @param p - пара
	 * @param infoArea - текстовое поле для вывода данных
	 */
	public void viewPairInfo(Pair p,  JTextArea infoArea) {
		infoArea.setText("");
		Cable c = (Cable)cc.getElement(p.getCable());
		infoArea.append("Сеть: "+ nc.getElement(c.getNet()).toString()+"\r\n");
		if (c.getType().equals(0)) {
			Frame f = (Frame)fc.getElement(p.getElementFrom());
			Box b = (Box)bc.getElement(p.getElementTo());
			infoArea.append("Тип: магистральная\r\nУчасток: "+ dfc.getElement(f.getOwnerId()).toString()+" - "+cbc.getElement(b.getOwnerId()).toString()+"\r\n");
		}
		if (c.getType().equals(1)) {
			Box b1 = (Box)bc.getElement(p.getElementFrom());
			Box b2 = (Box)bc.getElement(p.getElementTo());
			infoArea.append("Тип: межшкафная\r\nУчасток: "+ cbc.getElement(b1.getOwnerId()).toString()+" - "+cbc.getElement(b2.getOwnerId()).toString()+"\r\n");
		}
		if (c.getType().equals(2)) {
			Box b = (Box)bc.getElement(p.getElementFrom());
			infoArea.append("Тип: распределительная\r\nУчасток: "+ cbc.getElement(b.getOwnerId()).toString()+" - "+dbc.getElement(p.getElementTo()).toString()+"\r\n");
		}
		if (c.getType().equals(3)) {
			Frame f = (Frame)fc.getElement(p.getElementFrom());
			infoArea.append("Тип: прямого питания\r\nУчасток: "+ dfc.getElement(f.getOwnerId()).toString()+" - "+dbc.getElement(p.getElementTo()).toString()+"\r\n");
		}
		infoArea.append("Кабель: "+c.toShortString()+"\r\n");
		infoArea.append("Пара: "+p.toString()+"\r\n");
		if (p.getStatus().equals(0)) infoArea.append("Состояние: свободна\r\n");
		if (p.getStatus().equals(1)){
			
			infoArea.append("Состояние: занята\r\n");
			Iterator<Path> i = phc.getPairsPath(p).iterator();
			while (i.hasNext()) {
				Path path = i.next();
				infoArea.append("Абонент: " + sc.getElement(path.getSubscriber())+"\r\n");
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
		infoArea.append("Участок канализации: " + duc.getElement(t.getDuct()).toString() + "\r\n");
		infoArea.append("Канал №: " + t.getNumber().toString() + "\r\n");
		
		if (t.cablesCount() == 0) {
			infoArea.append("Состояние: свободен");
			return;
		}
		infoArea.append("Состояние: используется:\r\n");
		
		Iterator<Integer> i = t.getCables().iterator();
		while (i.hasNext()) infoArea.append("Кабель: "+((Cable)cc.getElement(i.next())).toLongString()+"\r\n");
	}
	 
	/**
	 * Удаляет сеть и все элементы в ней
	 * @param net - сеть
	 */
	public void removeNet (Net net) {
		
		Iterator<StructuredElement> i = cbc.getInNet(net).iterator();
		while (i.hasNext()) removeCabinet((Cabinet)i.next());
		
		i = dfc.getInNet(net).iterator();
		while (i.hasNext()) removeDFrame((DFramе)i.next());
		
		i = dbc.getInNet(net).iterator();
		while (i.hasNext()) removeDBox((DBox)i.next());
		
		i = mc.getInNet(net).iterator();
		while (i.hasNext()) removeManhole((Manhole)i.next());
		
		i = buc.getInNet(net).iterator();
		while (i.hasNext()) removeBuilding((Building)i.next());
		
		i = cc.getInNet(net).iterator();
		while (i.hasNext()) removeCable((Cable)i.next());
		
		i = sc.getInNet(net).iterator();
		while (i.hasNext()) removeSubscriber((Subscriber)i.next());
		
		if (nc.removeElement(net))
		rw.addLogMessage("Удалена: Сеть " + net.toString());
	}
	/**
	 * Удаляет шкаф и все боксы в нем, подходяшие участки канализаци и кабели
	 * @param element - шкаф
	 */
	public void removeCabinet (Cabinet element) {
		
		Iterator<ConnectedPointElement> k = bc.getInOwner(element.getId()).iterator();
		while (k.hasNext()) removeBox((Box)k.next());
		
		Iterator<Duct> i = duc.getDucts(element).iterator();
		while (i.hasNext()) removeDuct(i.next());
		
		Iterator<Cable> c = cc.getCables(element).iterator();
		while (c.hasNext()) removeCable(c.next());
		
		if (cbc.removeElement(element))
		rw.addLogMessage("Удален: Шкаф " + element.toString());
	}
	
	/**
	 * Удаляет кросс и все громполосы в нем, подходяшие участки канализаци и кабели
	 * @param element - кросс
	 */
	public void removeDFrame (DFramе element) {
		
		Iterator<ConnectedPointElement> k = fc.getInOwner(element.getId()).iterator();
		while (k.hasNext()) removeFrame((Frame)k.next());
		
		Iterator<Duct> i = duc.getDucts(element).iterator();
		while (i.hasNext()) removeDuct(i.next());
		
		Iterator<Cable> c = cc.getCables(element).iterator();
		while (c.hasNext()) removeCable(c.next());
		
		if (dfc.removeElement(element))
		rw.addLogMessage("Удален: Кросс " + element.toString());
	}

	/**
	 * Удаляет бокс и все пары в нем. Также пары удаляются из занятых пар у абонентов
	 * @param box - бокс
	 */
	public void removeBox (Box box) {
		
		Pair pair = null;
		Path path = null;
		Iterator <Pair> p = pc.getInOwner(box).iterator();
		
		while (p.hasNext()){
			pair = p.next();
			Iterator<AbstractElement> ph = phc.elements().iterator();
			while (ph.hasNext()) {
				path = (Path) ph.next();
				if (path.isPairUsed(pair)) {
					path.removePair(pair);
					rw.addLogMessage("Пара "+ pair.toString()+" удалена из включения: "+ path.toString()+ " у абонента: " + sc.getElement(path.getSubscriber()).toString());
				}
			}
			if (pc.removeElement(pair))
			rw.addLogMessage("Удалена: Пара " + pair.toString());
		}
		if(bc.removeElement(box))
		rw.addLogMessage("Удален: Бокс " + box.toString());
	}
	/**
	 * Удаляет громполосу и все пары в ней. Также пары удаляются из занятых пар у абонентов
	 * @param frame - громполоса
	 */
	public void removeFrame (Frame frame) {
		
		Pair pair = null;
		Path path = null;
		Iterator <Pair> p = pc.getInOwner(frame).iterator();
		
		while (p.hasNext()){
			pair = p.next();
			Iterator<AbstractElement> ph = phc.elements().iterator();
			while (ph.hasNext()) {
				path = (Path) ph.next();
				if (path.isPairUsed(pair)) {
					path.removePair(pair);
					rw.addLogMessage("Пара "+ pair.toString()+" удалена из включения: "+ path.toString()+ " у абонента: " + sc.getElement(path.getSubscriber()).toString());
				}
			}
			
			if (pc.removeElement(pair))
			rw.addLogMessage("Удалена: Пара " + pair.toString());
		}
		if (fc.removeElement(frame))
		rw.addLogMessage("Удален: Громполоса " + frame.toString());
	}	
	/**
	 * Удаляет КРТ и все пары в ней. Также пары удаляются из всех включений
	 * @param dbox - КРТ
	 */
	public void removeDBox (DBox dbox) {
		
		Pair pair = null;
		Path path = null;
		Iterator <Pair> p = pc.getInOwner(dbox).iterator();
		
		while (p.hasNext()){
			pair = p.next();
			Iterator<AbstractElement> ph = phc.elements().iterator();
			while (ph.hasNext()) {
				path = (Path) ph.next();
				if (path.isPairUsed(pair)) {
					path.removePair(pair);
					rw.addLogMessage("Пара "+ pair.toString()+" удалена из включения: "+ path.toString()+ " у абонента: " + sc.getElement(path.getSubscriber()).toString());
				}
			}
			if (pc.removeElement(pair))
			rw.addLogMessage("Удалена: Пара " + pair.toString());
		}
		if (dbc.removeElement(dbox))
		rw.addLogMessage("Удалена: КРТ " + dbox.toString());
	}
	/**
	 * Удаляет кабель и все пары в нем. Кабель удаляется из всех каналов канализации. Также пары удаляются из всех включений.
	 * @param cable - Кабель
	 */
	public void removeCable(Cable cable) {
		
		Pair pair = null;
		Path path = null;
		
		Iterator <Pair> p = pc.getInCable(cable).iterator();
		while (p.hasNext()){
			pair = p.next();
			Iterator<AbstractElement> ph = phc.elements().iterator();
			while (ph.hasNext()) {
				path = (Path) ph.next();
				if (path.isPairUsed(pair)) {
					path.removePair(pair);
					rw.addLogMessage("Пара "+ pair.toString()+" удалена из включения: "+ path.toString()+ " у абонента: " + sc.getElement(path.getSubscriber()).toString());
				}
			}
			if (pc.removeElement(pair))
			rw.addLogMessage("Удалена: Пара " + pair.toString());
		}
		
		Iterator <Tube> t = tuc.getTubesByCable(cable).iterator();
		while (t.hasNext()) {
			t.next().removeCable(cable); 
		}
		
		if (cc.removeElement(cable))
		rw.addLogMessage("Удален: Кабель " + cable.toString());
	}
	/**
	 * Удаляет участок канализации. Удаляются все каналы в канализации. Кабели проходящии по данному участку не удаляются.
	 * @param duct - Канализация
	 */
	public void removeDuct(Duct duct) {
		
		Iterator<Tube> i = tuc.getDuctsTubes(duct).iterator();
		while (i.hasNext()) removeTube(i.next());
		if (duc.removeElement(duct))
			rw.addLogMessage("Удален: Участок кабельной канализации " + duct.toString());
	}
	/**
	 * Удаляет канал в канализации. Кабели, проходящие по каналу не удалаются
	 * @param tube - канал
	 */
	public void removeTube(Tube tube) {
		
		if (tuc.removeElement(tube))
			rw.addLogMessage("Удален: Канал " + tube.toString() + " в канализации "+ duc.getElement(tube.getDuct()));
	}
	/**
	 * Удаляет колодец. Все участки кабельной канализации проходящие через колодец - удаляются
	 * @param man - колодец
	 */
	public void removeManhole(Manhole man){
		
		Iterator<Duct> i = duc.getDucts(man).iterator();
		while (i.hasNext()) removeDuct(i.next());
		if (mc.removeElement(man)) rw.addLogMessage("Удален: Колодец " + man.toString());
	}
	/**
	 * Удаляет здание. Удаляется также участок канализации, подходящий к зданию.
	 * @param building - здание
	 */
	public void removeBuilding(Building building) {
		
		Iterator<Duct> i = duc.getDucts(building).iterator();
		while (i.hasNext()) removeDuct(i.next());
		
		if (buc.removeElement(building)) rw.addLogMessage("Удален: Здание " + building.toString());
	}
	/**
	 * Удаляет абонента. Удаляются также все включения данного абонента
	 * @param sub - Абонент
	 */
	public void removeSubscriber(Subscriber sub) {
		
		Iterator <Path> p = phc.getSubscriberPaths(sub).iterator();
		while (p.hasNext()) removePath(p.next());
		if (sc.removeElement(sub)) rw.addLogMessage("Удален: Абонент " + sub.toString());
	}
	/**
	 * Удаляет включение. Пара освобождается, если больше не задействована ни в одном включении
	 * @param path - Абонент
	 */
	public void removePath(Path path) {
		
		Iterator <Pair> p = path.getUsedPairs().iterator();
		path.removeAllPairs();
		
		while (p.hasNext()){
			Pair pair = p.next();
			rw.addLogMessage("Пара "+ pair.toString()+" удалена из включения: "+ path.toString()+ " у абонента: " + sc.getElement(path.getSubscriber()).toString());
			
			if (phc.isPairUsed(pair) == null)  {
				pair.setStatus(0);
				rw.addLogMessage("Пара "+ pair.toString()+" освобождена ");
			}	
		}
		if (phc.removeElement(path))
		rw.addLogMessage("Удален: Включение " + path.toString() + " у абонента: "+ sc.getElement(path.getSubscriber()).toString());
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
				ElementView ep = (ElementView)pm.getInvoker();
				Tube t = (Tube) ep.getElement();
				Cable cable = formSearchCable(netId);
				if (cable != null) {
					if (t.containsCable(cable)) { newError(iFrame, "Кабель уже содержиться в канале"); return; }
					t.addCable(cable);
					rw.addLogMessage("Кабель " + cable.toString()+ " добавлен в канал " + t.toString() + " участка канализации " + duc.getElement(t.getDuct()));
					setTubeButtonColor(t, ep);
				}	
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
						rw.addLogMessage("Кабель " + cable.toString()+ " удален из канала " + t.toString() + " участка канализации " + duc.getElement(t.getDuct()));
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
				ElementView ep = (ElementView)pm.getInvoker();
				Pair p = (Pair) ep.getElement();
				Subscriber sub = formSearchSubscriber(netId);
				
				if (sub != null) {
					Path path = formSubscriberPaths(sub);
					if (path != null) {
						Pair oldPair = addPairToPath(path,p,iFrame); 
						setPairButtonColor(p, ep);
						if (oldPair != null) {
							ElementView oldPairButton = elementViewHash.get(oldPair);
							if (oldPairButton != null)
								setPairButtonColor(oldPair, oldPairButton);
						}
					}
				}
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
				rw.addLogMessage("Пара "+ p.toString()+", изменен статус на поврежденная");
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
				rw.addLogMessage("Пара "+ p.toString()+", изменен статус на исправная");
				setPairButtonColor(p, ep);
				
			}
		});
				
		JMenuItem menuItem_3 = new JMenuItem("Освободить");
		popupMenu.add(menuItem_3);
		menuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPopupMenu pm = (JPopupMenu) ((JMenuItem)e.getSource()).getParent();
				ElementView ep = (ElementView)pm.getInvoker();
				Pair p = (Pair) ep.getElement();
				Path path = formPairPaths(p);
				if (path != null)
					if (path.removePair(p)) {
						rw.addLogMessage("Пара "+ p.toString()+" удалена из включения: "+ path.toString()+ " у абонента: " + sc.getElement(path.getSubscriber()).toString());

						if (phc.isPairUsed(p) == null)  {
							p.setStatus(0);
							rw.addLogMessage("Пара "+ p.toString()+" освобождена ");
							setPairButtonColor(p, ep);
						}
					}				
			}
		});
		
		JMenuItem menuItem_4 = new JMenuItem("Карточка абонента");
		popupMenu.add(menuItem_4);
		menuItem_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPopupMenu pm = (JPopupMenu) ((JMenuItem)e.getSource()).getParent();
				ElementView ep = (ElementView)pm.getInvoker();
				Pair p = (Pair) ep.getElement();
				Subscriber sub = formPairSubscribers(p);
				if (sub != null) formViewPassport(rw.createSubscriberPassport(sub));
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