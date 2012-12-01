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
	
	public gui(Sys iSys, JFrame frame ) {
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
/*	public void addDamageToTable(JTable table, Damage damage){
		
		Vector<Object> v = new Vector<Object>();
		v.add(damage);
		v.add(damage.getOpenDate());
		v.add(damage.getCloseDate());
		((DefaultTableModel) table.getModel()).addRow(v);
	}*/
	/**
	 * Обновляет строчку с повреждением в таблице
	 * @param table - таблица
	 * @param damage - повреждение
	 * @param index - позиция обновляемой строки в таблице
	 */
/*	public void updateDamageInTable(JTable table, Damage damage, Integer index) {
		
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
		tableModel.setValueAt(damage, index, 0);
		tableModel.setValueAt(damage.getOpenDate(), index, 1);
		tableModel.setValueAt(damage.getCloseDate(), index, 2);
	}*/
	
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
        FromComboBox.addActionListener(actionListener);
        ToComboBox.addActionListener(actionListener);
        
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
	 * Создает и выводит на экран форму выбора включений данной пары
	 * @param pair - пара
	 * @return форма
	 */
	public FormPairPaths formPairPaths(Pair p) {
		
		final FormPairPaths form = new FormPairPaths(sys,p);
		return form;
		
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
	
}
