package org.teleline.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.RowSorter.SortKey;
import javax.swing.SortOrder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import net.miginfocom.swing.MigLayout;

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
	final JList pathList;
	
	public JButton refreshButton;
	public JButton editSubscriberButton;
	public JButton passportSubscriberButton;
	public JButton createSubscriberButton;
	public JButton deletSubscribereButton;
	public JButton findButton;
	
	public JButton addPathButton; 
	public JButton editPathButton;		
	public JButton deletePathButton;
	
	public JButton addPairButton;
	public JButton deletePairButton;
	
	
	public FormSubscribers(final Sys iSys, Collection<AbstractElement> collection) {
		super(iSys);
		createDialog("Редактировать абонента", 685, 700);
		iFrame.setResizable(true);
		iFrame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel_2 = new JPanel();
		iFrame.getContentPane().add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_3 = new JPanel();
		panel_2.add(panel_3, BorderLayout.SOUTH);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_4 = new JPanel();
		panel_3.add(panel_4, BorderLayout.EAST);
		panel_4.setLayout(new MigLayout("", "[110px,fill]", "[][][]"));
		
		JButton addPathButton = new JButton("Добавить");
		panel_4.add(addPathButton, "cell 0 0");
		JButton editPathButton = new JButton("Редактировать");
		panel_4.add(editPathButton, "cell 0 1");
		JButton deletePathButton = new JButton("Удалить");
		panel_4.add(deletePathButton, "cell 0 2");
		
		JLabel lblNewLabel_1 = new JLabel("Включения абонента");
		panel_3.add(lblNewLabel_1, BorderLayout.NORTH);
		
		pathList = addList(panel_3,BorderLayout.CENTER);	
		JPanel panel_5 = new JPanel();
		panel_2.add(panel_5, BorderLayout.CENTER);
		panel_5.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_6 = new JPanel();
		panel_5.add(panel_6, BorderLayout.EAST);
		panel_6.setLayout(new MigLayout("", "[110px,fill]", "[70px,top][][70px,top][][90px,top][]"));
		
		JButton refreshButton = new JButton("Обновить");
		panel_6.add(refreshButton, "cell 0 0");
		JButton editSubscriberButton = new JButton("Редактировать");
		panel_6.add(editSubscriberButton, "cell 0 1");
		JButton passportSubscriberButton = new JButton("Карточка");
		panel_6.add(passportSubscriberButton, "cell 0 2");
		JButton createSubscriberButton = new JButton("Создать");
		panel_6.add(createSubscriberButton, "cell 0 3");
		JButton deletSubscribereButton = new JButton("Удалить");
		panel_6.add(deletSubscribereButton, "cell 0 4");
		JButton findButton = new JButton("Найти");
		panel_6.add(findButton, "cell 0 5");
		
		
		subscriberList = addTable(panel_5, BorderLayout.CENTER);
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
		JPanel panel = new JPanel();
		iFrame.getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		pairList = addTable(panel, BorderLayout.CENTER);
		pairList.setPreferredScrollableViewportSize( new Dimension(
				pairList.getPreferredScrollableViewportSize().width,
				5 * pairList.getRowHeight()
		));		
		final DefaultTableModel pairTableModel = (DefaultTableModel) pairList.getModel();
			pairTableModel.setColumnIdentifiers(new String[]{"Пара", "От","До"});
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.EAST);
		panel_1.setLayout(new MigLayout("", "[120px,fill]", "[][]"));
				
		JButton addPairButton = new JButton("Добавить");
		panel_1.add(addPairButton, "cell 0 0");
		JButton deletePairButton = new JButton("Удалить");
		panel_1.add(deletePairButton, "cell 0 1");				
		JLabel lblNewLabel = new JLabel("Пары включения");
		panel.add(lblNewLabel, BorderLayout.NORTH);
				
		Iterator<StructuredElement> i = iSys.sc.getInNet(iSys.nc.getOnlyElement().getId()).iterator();
	    	while (i.hasNext()) {
	    		addSubscriberToTable((Subscriber)i.next());	
	    }
					
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
						util_clearTable(pairList);
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
					new FormSubscriber(iSys, null);
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
						util_clearTable(pairList);
						
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
	
						Vector<Object> v = new Vector<Object>();
						v.add("Рамка перехода"); v.add(((Path)pathList.getSelectedValue()).getTransit());
						((DefaultTableModel) pairList.getModel()).addRow(v);	
					}
				}
			};
			pathList.addListSelectionListener(pathSelect);
			/*
			 * ---------------------------------------------------------
			 */
			/*
			 * Событие кнопки добавления включения
			 */
			addPathButton.addActionListener( new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (subscriberList.getSelectionModel().isSelectionEmpty()){ util_newError("Абонент не выбран!"); return; }
					int selectedIndex = subscriberList.getRowSorter().convertRowIndexToModel(subscriberList.getSelectionModel().getMinSelectionIndex());
					Subscriber subscriber = (Subscriber)tableModel.getValueAt(selectedIndex, 0);
					
					new FormPath(iSys, subscriber, null);
					}
			});
			/*
			 * ---------------------------------------------------------
			 */
			/*
			 * Событие кнопки редактирования включения
			 */
			editPathButton.addActionListener( new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (subscriberList.getSelectionModel().isSelectionEmpty()){util_newError("Абонент не выбран!"); return; }
					int selectedIndex = subscriberList.getRowSorter().convertRowIndexToModel(subscriberList.getSelectionModel().getMinSelectionIndex());
					Subscriber subscriber = (Subscriber)tableModel.getValueAt(selectedIndex, 0);
					
					if (pathList.getSelectedIndex() == -1) {util_newError("Включение не выбрано!"); return; }
					new FormPath(iSys, subscriber, (Path)pathList.getSelectedValue());
						
				}
			});
			/*
			 * ---------------------------------------------------------
			 */
			/*
			 * Событие кнопки удаления включения
			 */
			deletePathButton.addActionListener( new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (subscriberList.getSelectionModel().isSelectionEmpty()){util_newError("Абонент не выбран!"); return; }
					
					if (pathList.getSelectedIndex() == -1) {util_newError("Включение не выбрано!"); return; }
					if (util_newDialog("Удалить включение?") == JOptionPane.YES_OPTION) {
						Path path = (Path)pathList.getSelectedValue();
						iSys.removePath(path);
						util_clearTable(pairList);
						((DefaultListModel)pathList.getModel()).removeElement(path);		
					}
				}
			});
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
					if (pairList.getSelectionModel().isSelectionEmpty()) { util_newError("Пара не выбрана!"); return; }
					
					int selectedPairIndex = pairList.getRowSorter().convertRowIndexToModel(pairList.getSelectionModel().getMinSelectionIndex());
					Pair delPair = (Pair)pairList.getValueAt(selectedPairIndex, 0);
					Path path = (Path)pathList.getSelectedValue();
					
					if (util_newDialog("Удалить пару: " + delPair.toString() +  " из включения: "+ path.toString()) == JOptionPane.YES_OPTION)
					if (path.removePair(delPair)) {
						
						String mes = "Пара:" + delPair.toString()+ ", удалена из включения:" + path.toString();
						iSys.rw.addLogMessage(mes);
						util_newInfo(mes);
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