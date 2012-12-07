package org.teleline.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SortOrder;
import javax.swing.RowSorter.SortKey;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.teleline.model.AbstractElement;
import org.teleline.model.Box;
import org.teleline.model.DBox;
import org.teleline.model.Frame;
import org.teleline.model.Pair;
import org.teleline.model.Path;
import org.teleline.model.StructuredElement;
import org.teleline.model.Subscriber;
import org.teleline.system.Sys;


public class FormSubscribers extends Form {
	
	public JTable subscriberList;
	public JTable pairList;
	
	public FormSubscribers(final Sys iSys, Collection<AbstractElement> collection) {
		super(iSys);
		createDialog("Редактировать абонента", 685, 700);
			
			addLabel("Список абонентов:", 10, 10, 520, 14);
			subscriberList = addTable(10, 30, 520, 375);
			final DefaultTableModel tableModel = (DefaultTableModel) subscriberList.getModel();
			tableModel.setColumnIdentifiers(new String[]{"Имя", "Телефон","Адрес"});
			
			subscriberList.getColumnModel().getColumn(1).setMaxWidth(70);
			subscriberList.getColumnModel().getColumn(1).setPreferredWidth(70);
			final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(subscriberList.getModel());
			subscriberList.setRowSorter(sorter);
			
			ArrayList<SortKey> keys=new ArrayList<SortKey>();
	        keys.add(new SortKey(0, SortOrder.ASCENDING));                                             
	        sorter.setSortKeys(keys);
	        sorter.setSortsOnUpdates(true);
		
			JButton refreshButton = addButton("Обновить", 540, 30, 125, 26);
			JButton editSubscriberButton = addButton("Редактировать", 540, 105, 125, 26);
			JButton passportSubscriberButton = addButton("Карточка", 540, 135, 125, 26);
			JButton createSubscriberButton = addButton("Добавить", 540, 215, 125, 26);
			JButton deletSubscribereButton = addButton("Удалить", 540, 245, 125, 26);
			
			JButton findButton = addButton("Найти", 540, 375, 125, 26);
					
				Iterator<StructuredElement> i = iSys.sc.getInNet(iSys.nc.getOnlyElement().getId()).iterator();
	    		while (i.hasNext()) {
	    			addSubscriberToTable((Subscriber)i.next());	
	    		}
			
			addLabel("Список включений:", 10, 420, 520, 14);
			final JList pathList = addList(10, 440, 520, 90);
			JButton addPathButton = addButton("Добавить", 540, 440, 125, 26);
			JButton editPathButton = addButton("Редактировать", 540, 470, 125, 26);		
			JButton deletePathButton = addButton("Удалить", 540, 500, 125, 26);
			
			addLabel("Занимаемые пары:", 10, 545, 520, 14);
			//final JList pairList = addList(10, 565, 520, 90);
			
			pairList = addTable(10, 565, 520, 90);
			final DefaultTableModel pairTableModel = (DefaultTableModel) pairList.getModel();
			pairTableModel.setColumnIdentifiers(new String[]{"Пара", "От","До"});
			
			JButton addPairButton = addButton("Добавить", 540, 565, 125, 26);
			JButton deletePairButton = addButton("Удалить", 540, 595, 125, 26);
			
			findButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					final FormSubscriberSearch form = new FormSubscriberSearch(iSys);
					form.okButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							if (form.subscriberList.getSelectedIndex() == -1) {form.util_newError("Абонент не выбран!"); return;}
							Subscriber sub = (Subscriber)form.subscriberList.getSelectedValue();
							
							for (int i = 0; i < tableModel.getRowCount(); i++) {
								
								if (((Subscriber)tableModel.getValueAt(i, 0)).equals(sub)){
									//Integer rowIndex = subscriberList.convertRowIndexToModel(i);
									Integer rowIndex = sorter.convertRowIndexToView(i);
									subscriberList.addRowSelectionInterval(rowIndex, rowIndex);
									util_scrollTable(subscriberList,rowIndex);

									System.out.println(i);
									break;
								}
							}
							form.close();
						}
					});
				
				}
			});
			
			refreshButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					util_clearTable(subscriberList);
					Iterator<StructuredElement> i = iSys.sc.getInNet(iSys.nc.getOnlyElement().getId()).iterator();
		    		while (i.hasNext()) {
		    			addSubscriberToTable((Subscriber)i.next());
		    		}
				}
			});
			/*
			 * Событие выбора абонента в таблице
			 */
			ListSelectionListener subscriberSelect = new ListSelectionListener(){
				public void valueChanged(ListSelectionEvent e) {
					if (!subscriberList.getSelectionModel().isSelectionEmpty()){
						int selectedIndex = subscriberList.getRowSorter().convertRowIndexToModel(subscriberList.getSelectionModel().getMinSelectionIndex());
						Subscriber subscriber = (Subscriber)tableModel.getValueAt(selectedIndex, 0);
						util_setListItems(pathList, iSys.phc.sortByIdUp(iSys.phc.getSubscriberPaths(subscriber)));
					}
				}
			};
			subscriberList.getSelectionModel().addListSelectionListener(subscriberSelect);
			/*
			 * ---------------------------------------------------------
			 */
			/*
			 * Событие редактирования абонента
			 */
			ActionListener editSubscriber = new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (subscriberList.getSelectionModel().isSelectionEmpty()){ 
						util_newError("Абонент не выбран!"); 
						return; 
					}
					int selectedIndex = subscriberList.getRowSorter().convertRowIndexToModel(subscriberList.getSelectionModel().getMinSelectionIndex());
					Subscriber subscriber = (Subscriber)tableModel.getValueAt(selectedIndex, 0);
					new FormSubscriber(iSys, subscriber);
					//updateSubscriberInTable(subscriberList, subscriber, selectedIndex);
				}
				
				
			};
			editSubscriberButton.addActionListener(editSubscriber);
			/*
			 * ---------------------------------------------------------
			 */
			/*
			 * Событие просмотра карточки абонента
			 */
			ActionListener passportSubscriber = new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (subscriberList.getSelectionModel().isSelectionEmpty()){ 
						util_newError("Абонент не выбран!"); 
						return; 
					}
					int selectedIndex = subscriberList.getRowSorter().convertRowIndexToModel(subscriberList.getSelectionModel().getMinSelectionIndex());
					Subscriber subscriber = (Subscriber)tableModel.getValueAt(selectedIndex, 0);
					util_viewPassport(iSys.rw.createSubscriberPassport(subscriber));
				}
			};
			passportSubscriberButton.addActionListener(passportSubscriber);
			/*
			 * ---------------------------------------------------------
			 */
			/*
			 * Событие добавления абонента
			 */
			ActionListener createSubscriber = new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
				//	if (netsComboBox.getSelectedIndex() == -1) { newError(iFrame, "Сеть не выбрана"); return; }
					new FormSubscriber(iSys, null);
					//if (subscriber != null) addSubscriberToTable(subscriberList, subscriber);	
				}
			};
			createSubscriberButton.addActionListener(createSubscriber);
			/*
			 * ---------------------------------------------------------
			 */
			/*
			 * Событие удаления абонента
			 */
			ActionListener deleteSubscriber = new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (subscriberList.getSelectionModel().isSelectionEmpty()){ util_newError("Абонент не выбран!"); return; }
					int selectedIndex = subscriberList.getRowSorter().convertRowIndexToModel(subscriberList.getSelectionModel().getMinSelectionIndex());
					Subscriber subscriber = (Subscriber)tableModel.getValueAt(selectedIndex, 0);
					
					if (util_newDialog("Удалить абонента: "+ subscriber.toString()+ " ? Все занимаемые пары будут освобождены.") == JOptionPane.YES_OPTION) {
						iSys.removeSubscriber(subscriber);
						((DefaultTableModel) subscriberList.getModel()).removeRow(selectedIndex);
					}
				}
			};
			deletSubscribereButton.addActionListener(deleteSubscriber);
			/*
			 * ---------------------------------------------------------
			 */
			/*
			 * Событие выбора включения в списке
			 */
			ListSelectionListener pathSelect = new ListSelectionListener(){
				public void valueChanged(ListSelectionEvent e) {
					if (pathList.getSelectedIndex() != -1) {
						util_clearTable(pairList);
						Iterator<Pair> i = ((Path)pathList.getSelectedValue()).getUsedPairs().iterator();
						while(i.hasNext()){
							addPairToTable(i.next());
						}
					}
					
						//util_setListItems(pairList, ((Path)pathList.getSelectedValue()).getUsedPairs());
				}
			};
			pathList.addListSelectionListener(pathSelect);
			/*
			 * ---------------------------------------------------------
			 */
			/*
			 * Событие кнопки добавления включения
			 */
			ActionListener createPath = new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (subscriberList.getSelectionModel().isSelectionEmpty()){ util_newError("Абонент не выбран!"); return; }
					int selectedIndex = subscriberList.getRowSorter().convertRowIndexToModel(subscriberList.getSelectionModel().getMinSelectionIndex());
					Subscriber subscriber = (Subscriber)tableModel.getValueAt(selectedIndex, 0);
					
					new FormPath(iSys, subscriber, null);
				/*	if ( path != null) { 
							((DefaultListModel)pathList.getModel()).addElement(path);
					}*/
				}
			};
			addPathButton.addActionListener(createPath);
			/*
			 * ---------------------------------------------------------
			 */
			/*
			 * Событие кнопки редактирования включения
			 */
			ActionListener editPath = new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (subscriberList.getSelectionModel().isSelectionEmpty()){util_newError("Абонент не выбран!"); return; }
					int selectedIndex = subscriberList.getRowSorter().convertRowIndexToModel(subscriberList.getSelectionModel().getMinSelectionIndex());
					Subscriber subscriber = (Subscriber)tableModel.getValueAt(selectedIndex, 0);
					
					if (pathList.getSelectedIndex() == -1) {util_newError("Включение не выбрано!"); return; }
					new FormPath(iSys, subscriber, (Path)pathList.getSelectedValue());
						
					//util_setListItems(pathList, iSys.phc.sortByIdUp(iSys.phc.getSubscriberPaths(subscriber)));
						
				}
			};
			editPathButton.addActionListener(editPath);
			/*
			 * ---------------------------------------------------------
			 */
			/*
			 * Событие кнопки удаления включения
			 */
			ActionListener deletePath = new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					//if (subscriberList.getSelectedIndex() == -1) { newError(iFrame, "Абонент не выбран!"); return; }
					if (subscriberList.getSelectionModel().isSelectionEmpty()){util_newError("Абонент не выбран!"); return; }
					//int selectedIndex = subscriberList.getRowSorter().convertRowIndexToModel(subscriberList.getSelectionModel().getMinSelectionIndex());
					//Subscriber subscriber = (Subscriber)tableModel.getValueAt(selectedIndex, 0);
					
					if (pathList.getSelectedIndex() == -1) {util_newError("Включение не выбрано!"); return; }
					if (util_newDialog("Удалить включение?") == JOptionPane.YES_OPTION) {
						Path path = (Path)pathList.getSelectedValue();
						iSys.removePath(path);
						((DefaultListModel)pairList.getModel()).clear();
						((DefaultListModel)pathList.getModel()).removeElement(path);		
					}
				}
			};
			deletePathButton.addActionListener(deletePath);
			/*
			 * ---------------------------------------------------------
			 */
			/*
			 * Событие кнопки вызова формы для добавления пары
			 */
			ActionListener addPair = new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					if (subscriberList.getSelectionModel().isSelectionEmpty()){util_newError("Абонент не выбран!"); return; }
					if (pathList.getSelectedIndex() == -1) { util_newError("Включение не выбрано!"); return; }
					final Path path = (Path)pathList.getSelectedValue();
					final FormAddPair form = new FormAddPair(iSys);
					
					form.okButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							
							if (form.selectedPairList.getSelectedIndex() == -1) {form.util_newError("Не выбрана пара!"); return; }	
							
							
							if ( addPairToPath(path, (Pair)form.selectedPairList.getSelectedValue()) != null) {
								form.close();
								util_clearTable(pairList);
								Iterator<Pair> i = path.getUsedPairs().iterator();
								while(i.hasNext()){
									addPairToTable(i.next());
								}
								//util_setListItems(pairList, path.getUsedPairs());
							}
						}
			        });
				}
			};
			
			addPairButton.addActionListener(addPair);
			/*
			 * ------------------------------------------------ 
			 */
			/*
			 * Событие кнопки удаления пары из включения
			 */
			ActionListener deletePair = new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					if (pathList.getSelectedIndex() == -1) { util_newError("Включение не выбрано!"); return; }
					if (pairList.getSelectionModel().isSelectionEmpty()/*pairList.getSelectedIndex() == -1*/) { util_newError("Пара не выбрана!"); return; }
					
					int selectedPairIndex = pairList.getRowSorter().convertRowIndexToModel(pairList.getSelectionModel().getMinSelectionIndex());
					//Pair delPair = (Pair)pairList.getSelectedValue();
					Pair delPair = (Pair)pairList.getValueAt(selectedPairIndex, 0);
					Path path = (Path)pathList.getSelectedValue();
					
					if (util_newDialog("Удалить пару: " + delPair.toString() +  " из включения: "+ path.toString()) == JOptionPane.YES_OPTION)
					if (path.removePair(delPair)) {
						
						String mes = "Пара:" + delPair.toString()+ ", удалена из включения:" + path.toString();
						iSys.rw.addLogMessage(mes);
						util_newInfo(mes);
						//((DefaultListModel)pairList.getModel()).removeElement(delPair);
						util_clearTable(pairList);
						Iterator<Pair> i = path.getUsedPairs().iterator();
						while(i.hasNext()){ addPairToTable(i.next());}
						
						if (iSys.phc.isPairUsed(delPair) == null)  {
							delPair.setStatus(0);
							iSys.rw.addLogMessage("Пара "+ delPair.toString()+" освобождена ");
						}
					}
					else {
						util_newError("Пара не может быть удалена из включения!");
					}
				
				}
			};
			
			deletePairButton.addActionListener(deletePair);
			/*
			 * ------------------------------------------------ 
			 */
			
	        
			iFrame.setVisible(true);

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
		v.add(subscriber.getAdress());
		((DefaultTableModel) subscriberList.getModel()).addRow(v);
	}
	/**
	 * Добавляет пару в таблицу отображения пар
	 * @param pair - пара
	 */
	private void addPairToTable(Pair pair) {
		
		Vector<AbstractElement> b = pair.getOwners();
		Vector<Object> v = new Vector<Object>();
		v.add(pair);
		
		switch (b.get(0).whoIsIt()) {
		
			case 0:
				v.add(iSys.dfc.getElement(((Frame)b.get(0)).getOwnerId()));
			break;
			case 1:
				v.add(iSys.cbc.getElement(((Box)b.get(0)).getOwnerId()));
			break;
			case 2:
				v.add(iSys.buc.getElement(((DBox)b.get(0)).getBuilding()));
			break;
		}
	
		switch (b.get(1).whoIsIt()) {
			
			case 0:
				v.add(iSys.dfc.getElement(((Frame)b.get(1)).getOwnerId()));
			break;
			case 1:
				v.add(iSys.cbc.getElement(((Box)b.get(1)).getOwnerId()));
			break;
			case 2:
				v.add(iSys.buc.getElement(((DBox)b.get(1)).getBuilding()));
			break;
		}
		
		((DefaultTableModel) pairList.getModel()).addRow(v);
	}

	/**
	 * Обновляет строчку с абонентом в таблице
	 * @param table - таблица
	 * @param subscriber - абонент
	 * @param index - позиция обновляемой строки в таблице
	 */
/*	private void updateSubscriberInTable(Subscriber subscriber, Integer index) {
		
		DefaultTableModel tableModel = (DefaultTableModel) subscriberList.getModel();
		tableModel.setValueAt(subscriber, index, 0);
		tableModel.setValueAt(subscriber.getPhoneNumber(), index, 1);
		tableModel.setValueAt(subscriber.getAdress(), index, 2);
	}*/
	
}