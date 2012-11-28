package org.teleline.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.teleline.model.AbstractElement;
import org.teleline.model.Box;
import org.teleline.model.Cable;
import org.teleline.model.ConnectedPointElement;
import org.teleline.model.Frame;
import org.teleline.model.Pair;
import org.teleline.model.Path;
import org.teleline.model.Subscriber;
import org.teleline.model.Sys;
import org.teleline.model.Tube;

public abstract class Form {
	
	public JDialog  iFrame;
	public Sys iSys;
	
	public Form(Sys iSys) {
		
		this.iSys = iSys;
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
	
	public JButton addMoreButton(final int iFrameMinWidth, final int iFrameMaxWidth, final int iFrameMinHeight, final int iFrameMaxHeight, int x, int y, int w, int h) {
		
		final JButton moreButton = addButton(" > ", x, y, w, h);
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
		iFrame.getContentPane().add(scrollPane);
		
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
		iFrame.getContentPane().add(scrollPane);
		return table;
	}
	/**
	 * Всплывающее меню для пары
	 * @param iFrame - окно
	 * @param netId - id сети
	 * @param elementViewHash - хеш всех созданых кнопок для пар
	 * @return JPopupMenu объект всплывающего меню
	 */
	public JPopupMenu popupMenuForPair (final HashMap<Pair, ElementView> elementViewHash){
		
		JPopupMenu popupMenu = new JPopupMenu();
		
		JMenuItem menuItem = new JMenuItem("Закрепить за абонентом");
		popupMenu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPopupMenu pm = (JPopupMenu) ((JMenuItem)e.getSource()).getParent();
				final ElementView ep = (ElementView)pm.getInvoker();
				final Pair p = (Pair) ep.getElement();
				final FormSubscriberSearch form = new FormSubscriberSearch(iSys);
				
				ActionListener selectSubscriber = new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (form.subscriberList.getSelectedIndex() == -1) {form.util_newError("Абонент не выбран!"); return;}
						Subscriber sub = (Subscriber)form.subscriberList.getSelectedValue();
						
						final FormSubscriberPaths formPath = new FormSubscriberPaths(iSys,sub);
						
						ActionListener selectPath = new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								if (formPath.pathList.getSelectedIndex() == -1) {formPath.util_newError("Включение не выбрано!"); return;}
								Path path = (Path)formPath.pathList.getSelectedValue();
								
								Pair oldPair = addPairToPath(path,p);
								util_setPairButtonColor(p, ep);
								if (oldPair != null) {
									ElementView oldPairButton = elementViewHash.get(oldPair);
									if (oldPairButton != null)
										util_setPairButtonColor(oldPair, oldPairButton);
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
				iSys.rw.addLogMessage("Пара "+ p.toString()+", изменен статус на поврежденная");
				util_setPairButtonColor(p, ep);
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
				iSys.rw.addLogMessage("Пара "+ p.toString()+", изменен статус на исправная");
				util_setPairButtonColor(p, ep);
				
			}
		});
				
		JMenuItem menuItem_3 = new JMenuItem("Освободить");
		popupMenu.add(menuItem_3);
		menuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPopupMenu pm = (JPopupMenu) ((JMenuItem)e.getSource()).getParent();
				final ElementView ep = (ElementView)pm.getInvoker();
				final Pair p = (Pair) ep.getElement();
				final FormPairPaths form =  new FormPairPaths(iSys,p);
				
				ActionListener selectPath = new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (form.pathList.getSelectedIndex() == -1) {form.util_newError("Включение не выбрано!"); return;}
					Path path = (Path)form.pathList.getSelectedValue();
					
					if (path.removePair(p)) {
						iSys.rw.addLogMessage("Пара "+ p.toString()+" удалена из включения: "+ path.toString()+ " у абонента: " + iSys.sc.getElement(path.getSubscriber()).toString());

						if (iSys.phc.isPairUsed(p) == null)  {
							p.setStatus(0);
							iSys.rw.addLogMessage("Пара "+ p.toString()+" освобождена ");
							util_setPairButtonColor(p, ep);
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
				new FormPairSubscribers(iSys, p);
			}
		});
		
		return popupMenu;
		
	}
	/**
	 * Всплывающее меню для канала канализации
	 * @param iFrame - окно
	 * @param netId - id сети
	 * @return JPopupMenu объект всплывающего меню
	 */
	public JPopupMenu popupMenuForTube (){
		JPopupMenu popupMenu = new JPopupMenu();

		JMenuItem menuItem = new JMenuItem("Добавить кабель");
		popupMenu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPopupMenu pm = (JPopupMenu) ((JMenuItem)e.getSource()).getParent();
				final ElementView ep = (ElementView)pm.getInvoker();
				final Tube t = (Tube) ep.getElement();
				final FormSearchCable form = new FormSearchCable(iSys,iSys.nc.getOnlyElement().getId());
				
				ActionListener selectCable = new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (form.cableTable.getSelectionModel().isSelectionEmpty()){ form.util_newError("Кабель не выбран!"); return; }
						int selectedIndex = form.cableTable.getRowSorter().convertRowIndexToModel(form.cableTable.getSelectionModel().getMinSelectionIndex());
						
						Cable cable = (Cable)((DefaultTableModel)form.cableTable.getModel()).getValueAt( selectedIndex, 0);
						
						if (t.containsCable(cable)) { form.util_newError("Кабель уже содержиться в канале"); return; }
						
						t.addCable(cable);
						iSys.rw.addLogMessage("Кабель " + cable.toString()+ " добавлен в канал " + t.toString() + " участка канализации " + iSys.duc.getElement(t.getDuct()));
						util_setTubeButtonColor(t, ep);	
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
				final ElementView ep = (ElementView)pm.getInvoker();
				final Tube t = (Tube) ep.getElement();
				final FormTubesCables form = new FormTubesCables(iSys,t);
				
				form.okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (form.cableList.getSelectedIndex() == -1) {form.util_newError("Кабель не выбран!"); return;}
						iFrame.dispose();
						Cable cable = (Cable)form.cableList.getSelectedValue();
						if (cable != null)
							if (t.removeCable(cable)) {
								iSys.rw.addLogMessage("Кабель " + cable.toString()+ " удален из канала " + t.toString() + " участка канализации " + iSys.duc.getElement(t.getDuct()));
								util_setTubeButtonColor(t, ep);
							}
					}
				});
			}
		});
		
		return popupMenu;
		
	}
	
	public Pair addPairToPath(Path path, Pair p) {
		
		Pair returnedPair = null;
		Cable c = (Cable)iSys.cc.getElement(p.getCable());
		
		if (c.getType() == 0) {
			
			if (path.isdrPair()) {
				util_newError("Включение занимает пару прямого питания!\r\nНевозможно добавить магистральную пару");
				return null;
			}
			//	if (newDialog(frame, "Включение занимает пару прямого питания!\r\nВсе равно добавить магистральную пару?") == JOptionPane.NO_OPTION) { return false;}
			
			if (path.ismPair())
				if (util_newDialog("Включение уже занимает магистральную пару!\r\nЗаменить магистральную пару?") == JOptionPane.NO_OPTION) { return null;}
			
				Pair oldPair = (Pair)iSys.pc.getElement(path.getmPair());
				
				path.addmPair(p);
				p.setStatus(1);
				returnedPair = p;
				
				if (oldPair != null) {
					if (iSys.phc.isPairUsed(oldPair) == null)  {
						oldPair.setStatus(0);
						iSys.rw.addLogMessage("Пара "+ oldPair.toString()+" освобождена ");
					}
					else {
						iSys.rw.addLogMessage("Пара "+ oldPair.toString()+"удалена из включения " + path.toString());
					}
					
					returnedPair = oldPair;
				}
				String mes = "Пара "+ p.toString()+" занята включением: " + path.toString();
				iSys.rw.addLogMessage(mes);
				util_newInfo(mes);
				return returnedPair;
		
		}
		
		if (c.getType() == 1) {
			
			if (path.isdrPair()) {
				util_newError("Включение занимает пару прямого питания!\r\nНевозможно добавить межшкафную пару");
				return null;
			}
			
			if (path.isdbPair()) {
				util_newError("Включение занимает распределительную пару!\r\nНевозможно добавить межшкафную пару");
				return null;
			}
			
			//	if (newDialog(frame, "Включение занимает пару прямого питания!\r\nВсе равно добавить межшкафную пару?") == JOptionPane.NO_OPTION) { return false;}
			
				path.addicPair(p);
				p.setStatus(1);
				String mes = "Кабельная пара "+ p.toString()+" занята включением: " + path.toString();
				iSys.rw.addLogMessage(mes);
				util_newInfo(mes);
				return p;
		}
		
		if (c.getType() == 2) {
			
			if (path.isdrPair()) {
				util_newError("Включение занимает пару прямого питания!\r\nНевозможно добавить распределительную пару");
				return null;
			}
			//	if (newDialog(frame, "Включение занимает пару прямого питания!\r\nВсе равно добавить распределительную пару?") == JOptionPane.NO_OPTION) { return false;}
			
			if (path.isdbPair())
				if (util_newDialog("Включение уже занимает распределительную пару!\r\nЗаменить распределительную пару?") == JOptionPane.NO_OPTION) { return null;}
				
				Pair oldPair = (Pair)iSys.pc.getElement(path.getdbPair());
				
				path.adddbPair(p);
				p.setStatus(1);
				returnedPair = p;
				
				if (oldPair != null) {
					if (iSys.phc.isPairUsed(oldPair) == null)  {
						oldPair.setStatus(0);
						iSys.rw.addLogMessage("Пара "+ oldPair.toString()+" освобождена ");
					}
					else {
						iSys.rw.addLogMessage("Пара "+ oldPair.toString()+" удалена из включения " + path.toString());
					}
					returnedPair = oldPair;
				}
				
				String mes = "Кабельная пара "+ p.toString()+" занята включением: " + path.toString();
				iSys.rw.addLogMessage(mes);
				util_newInfo(mes);
				return returnedPair;
		}
		
		if (c.getType() == 3) {
													
			if (path.ismPair() || path.isdbPair() || path.isicPair()) {
				util_newError("Включение занимает маг./межшкаф./распред. пару!\r\nНевозможно добавить пару прямого питания?"); 
				return null;}
			
			if (path.isdrPair())
				if (util_newDialog("Включение занимает пару прямого питания!\r\n Заменить пару прямого питания?") == JOptionPane.NO_OPTION) { return null;}
			
			Pair oldPair = (Pair)iSys.pc.getElement(path.getdrPair());
			
			path.adddrPair(p);
			p.setStatus(1);
			returnedPair = p;
			
			if (oldPair != null) {
				
				if (iSys.phc.isPairUsed(oldPair) == null)  {
					oldPair.setStatus(0);
					iSys.rw.addLogMessage("Пара "+ oldPair.toString()+" освобождена ");
				}
				else {
					iSys.rw.addLogMessage("Пара "+ oldPair.toString()+" удалена из включения " + path.toString());
				}
				returnedPair = oldPair;
			}
				
				String mes = "Кабельная пара "+ p.toString()+" занята включением: " + path.toString();
				iSys.rw.addLogMessage(mes);
				util_newInfo(mes);
				
				return returnedPair;
		}
		return null;
	}
	/**
	 * Выводит подробные данные о паре
	 * @param p - пара
	 * @param infoArea - текстовое поле для вывода данных
	 */
	public void viewPairInfo(Pair p,  JTextArea infoArea) {
		infoArea.setText("");
		Cable c = (Cable)iSys.cc.getElement(p.getCable());
		infoArea.append("Сеть: "+ iSys.nc.getElement(c.getNet()).toString()+"\r\n");
		if (c.getType().equals(0)) {
			Frame f = (Frame)iSys.fc.getElement(p.getElementFrom());
			Box b = (Box)iSys.bc.getElement(p.getElementTo());
			infoArea.append("Тип: магистральная\r\nУчасток: "+ iSys.dfc.getElement(f.getOwnerId()).toString()+" - "+iSys.cbc.getElement(b.getOwnerId()).toString()+"\r\n");
		}
		if (c.getType().equals(1)) {
			Box b1 = (Box)iSys.bc.getElement(p.getElementFrom());
			Box b2 = (Box)iSys.bc.getElement(p.getElementTo());
			infoArea.append("Тип: межшкафная\r\nУчасток: "+ iSys.cbc.getElement(b1.getOwnerId()).toString()+" - "+iSys.cbc.getElement(b2.getOwnerId()).toString()+"\r\n");
		}
		if (c.getType().equals(2)) {
			Box b = (Box)iSys.bc.getElement(p.getElementFrom());
			infoArea.append("Тип: распределительная\r\nУчасток: "+ iSys.cbc.getElement(b.getOwnerId()).toString()+" - "+iSys.dbc.getElement(p.getElementTo()).toString()+"\r\n");
		}
		if (c.getType().equals(3)) {
			Frame f = (Frame)iSys.fc.getElement(p.getElementFrom());
			infoArea.append("Тип: прямого питания\r\nУчасток: "+ iSys.dfc.getElement(f.getOwnerId()).toString()+" - "+iSys.dbc.getElement(p.getElementTo()).toString()+"\r\n");
		}
		infoArea.append("Кабель: "+c.toShortString()+"\r\n");
		infoArea.append("Пара: "+p.toString()+"\r\n");
		if (p.getStatus().equals(0)) infoArea.append("Состояние: свободна\r\n");
		if (p.getStatus().equals(1)){
			
			infoArea.append("Состояние: занята\r\n");
			Iterator<Path> i = iSys.phc.getPairsPath(p).iterator();
			while (i.hasNext()) {
				Path path = i.next();
				infoArea.append("Абонент: " + iSys.sc.getElement(path.getSubscriber())+"\r\n");
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
		infoArea.append("Участок канализации: " + iSys.duc.getElement(t.getDuct()).toString() + "\r\n");
		infoArea.append("Канал №: " + t.getNumber().toString() + "\r\n");
		
		if (t.cablesCount() == 0) {
			infoArea.append("Состояние: свободен");
			return;
		}
		infoArea.append("Состояние: используется:\r\n");
		
		Iterator<Integer> i = t.getCables().iterator();
		while (i.hasNext()) infoArea.append("Кабель: "+((Cable)iSys.cc.getElement(i.next())).toLongString()+"\r\n");
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
	
	public int util_newDialog(String mes) {
		
		Object[] options = {"Да", "Нет"};
		
		return JOptionPane.showOptionDialog(iFrame,
				mes,
				"Подтверждение операции",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,     //do not use a custom Icon
				options,  //the titles of buttons
				options[0]); //default button title
				
	}
	
	public void util_setPairButtonColor (Pair pair, ElementView button) {
		
		if (pair.getStatus() == 0) { button.setBackground((new Color(0,200,0))); return; }
		if (pair.getStatus() == 1) { button.setBackground((new Color(0,0,200))); return; }
		if (pair.getStatus() == 2) { button.setBackground((new Color(250,0,0))); return; }
	}
	/**
	 * Устанавливает цвет графического элемента канала канализации в зависимости от загруженности кабелями
	 * @param tube - канал
	 * @param button - графический элемент
	 */
	public void util_setTubeButtonColor(Tube tube, ElementView button) {
		
		if (tube.cablesCount() == 0)  { button.setBackground(new Color(0, 200, 0)); return; }
		if (tube.cablesCount() == 1) { button.setBackground(new Color(204, 204, 255)); return; }
		if (tube.cablesCount() == 2) { button.setBackground(new Color(153, 153, 255)); return; }
		if (tube.cablesCount() == 3) { button.setBackground(new Color(102, 102, 255)); return; }
		if (tube.cablesCount() > 3) { button.setBackground(new Color(51, 51, 255)); return; }
		if (tube.cablesCount() > 5) { button.setBackground(new Color(0, 0, 255)); return; }

	}
	/**
	 * Отображает паспорт элемента в браузере
	 * @param fileName - имя файла
	 */
	public void util_viewPassport (String fileName) {
		
		try {
			File page = new File(fileName);
		    java.awt.Desktop.getDesktop().browse(page.toURI()); 
		} catch (IOException ex) {
			iSys.rw.writeError(ex.toString());
		}
	}
	
	public static void addPopupToPair(Component component, final JPopupMenu popup) {
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
	
	public static void addPopupToConnectedPointElement(Component component, final JPopupMenu popup) {
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
	 * Добавляет всплывающее меню для канала канализации
	 * @param popup - всплывающее меню
	 */
	public static void addPopupToTube(Component component, final JPopupMenu popup) {
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

}