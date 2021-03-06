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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;
import org.teleline.model.Box;
import org.teleline.model.Cabinet;
import org.teleline.model.Cable;
import org.teleline.model.ConnectedPointElement;
import org.teleline.model.DFramе;
import org.teleline.model.Frame;
import org.teleline.model.Pair;
import org.teleline.model.Path;
import org.teleline.model.StructuredElement;
import org.teleline.model.Subscriber;
import org.teleline.model.Tube;
import org.teleline.model.Wrapper;
import org.teleline.system.Sys;
import org.teleline.system.SystemResurses;


public abstract class FormJFrame extends Form{
	
	private boolean resurseValid = false;
	private Integer resurseId = 0;
	
	public FormJFrame(Sys iSys) {
		super(iSys);
		
	}
	
	protected JFrame createFrame (String title, int width, int height) {
		
		if (!resurseValid && resurseId < 0) {
			
			SystemResurses res = new SystemResurses();
			resurseId = res.getResusrseId(iFrame);
			resurseValid = res.isResurseValid(this);
		}
		else {
			iFrame = new JFrame();
			iSys.mng.add(iFrame);
			iFrame.addWindowListener(new FormListener(this));
			iFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			iFrame.setSize(width, height);
			iFrame.setLocationRelativeTo(iFrame);
			iFrame.setTitle(title);
			iFrame.setResizable(false);
			contentPane = iFrame.getContentPane();
			contentPane.setLayout(null);
		}
		
		return iFrame;
	}
	/**
	 * Закрывает форму через менеджер форм
	 */
	public void close() {
		iSys.mng.close(iFrame);
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
	
	public JList addList( JPanel panel, String position) {
		
		JScrollPane scrollPane = new JScrollPane();
		//scrollPane.setBounds(x, y, w, h);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		JList list = new JList();
		list.setModel(new DefaultListModel());
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(list);
		panel.add(scrollPane,position);
		//iFrame.getContentPane().add(scrollPane);
				
		return list;
	}
	
	
	
	/*public JButton addMoreButton(final int iFrameMinWidth, final int iFrameMaxWidth, final int iFrameMinHeight, final int iFrameMaxHeight, int x, int y, int w, int h) {
		
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
	}*/
	
	public JComboBox frameComboBox(JComboBox OwnersComboBox, int x, int y, int w, int h){
		
		JComboBox comboBox = new JComboBox();
		
		if (OwnersComboBox.getSelectedIndex() > -1) {
			util_setComboBoxItems(comboBox, iSys.fc.getInOwner(((DFramе)OwnersComboBox.getSelectedItem()).getId()));
		}
		comboBox.setBounds(x, y, w, h);
		iFrame.getContentPane().add(comboBox);
		
		return comboBox;
	}
	
	public JComboBox boxComboBox(JComboBox OwnersComboBox, Integer Type,int x, int y, int w, int h){
		
		JComboBox comboBox = new JComboBox();
		if (OwnersComboBox.getSelectedIndex() > -1) {
			util_setComboBoxItems(comboBox, iSys.bc.getInOwnerByTypeUniversal(((Cabinet)OwnersComboBox.getSelectedItem()).getId(), Type));
		}
		comboBox.setBounds(x, y, w, h);
		iFrame.getContentPane().add(comboBox);
		
		return comboBox;
	}
	
	public JComboBox cableComboBox(JComboBox FromComboBox, StructuredElement toElement, Integer Type, int x, int y, int w, int h){
		
		JComboBox comboBox = new JComboBox();
		if (Type < 2) {
			if (FromComboBox.getSelectedIndex() > -1 && toElement != null) {
				util_setComboBoxItems(comboBox, iSys.cc.sortByIdUp(iSys.cc.getInOwners(Type, ((StructuredElement)FromComboBox.getSelectedItem()).getId(), toElement.getId())));
			}
		}
		
		if (Type >= 2) {
			if (FromComboBox.getSelectedIndex() > -1) {
				util_setComboBoxItems(comboBox, iSys.cc.sortByIdUp(iSys.cc.getDCableOut((StructuredElement)FromComboBox.getSelectedItem())));
			}
		}
		
		comboBox.setBounds(x, y, w, h);
		iFrame.getContentPane().add(comboBox);
		
		return comboBox;
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
            	util_setComboBoxItems(LinkedComboBox, iSys.fc.sortByIdUp(iSys.fc.getInOwner(((DFramе)dframeComboBox.getSelectedItem()).getId())));
            
            }
        };
       
        LinkedComboBox.removeAllItems();
    	if (dframeComboBox.getSelectedIndex() > -1)
    	util_setComboBoxItems(LinkedComboBox, iSys.fc.sortByIdUp(iSys.fc.getInOwner(((DFramе)dframeComboBox.getSelectedItem()).getId())));
        
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
            		util_setComboBoxItems(LinkedComboBox, iSys.bc.sortByIdUp(iSys.bc.getInOwnerByTypeUniversal(((Cabinet)cabinetComboBox.getSelectedItem()).getId(), Type)));
            }
        };
        
        LinkedComboBox.removeAllItems();
        if (cabinetComboBox.getSelectedIndex() > -1)
        	util_setComboBoxItems(LinkedComboBox, iSys.bc.sortByIdUp(iSys.bc.getInOwnerByTypeUniversal(((Cabinet)cabinetComboBox.getSelectedItem()).getId(), Type)));
    	
    	cabinetComboBox.addActionListener(actionListener);
        
	}
	/**
	 * Заполняет выпадающий список кабелей элементами. 
	 * Кабели сортируются по id. 
	 * @param cableComboBox - выпадающий список кабелей
	 * @param Type - тип кабелей
	 */
	public void setCableComboBoxItems(final StructuredElement fromElement, final StructuredElement toElement, final JComboBox cableComboBox, final Integer Type) {
		
		cableComboBox.removeAllItems();
            	if (Type < 2)
            		if (fromElement != null && toElement != null) {
            			
            			util_setComboBoxItems(cableComboBox, iSys.cc.sortByIdUp(iSys.cc.getInOwners(Type, fromElement.getId(), toElement.getId())));               
            		}
            	
            	if (Type >= 2) {
        			if (fromElement != null) {
        				util_setComboBoxItems(cableComboBox, iSys.cc.sortByIdUp(iSys.cc.getDCableOut(fromElement)));
        			}
        		}
            
	}

	
	
	/**
	 * Всплывающее меню для пары
	 * @param iDialog - окно
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
				Wrapper wrapper = new Wrapper();
				new FormSubscriberSearch(iFrame,iSys,wrapper);//модальное
				
				if (wrapper.getElement() != null ) {
					final FormSubscriberPaths formPath = new FormSubscriberPaths(iSys, (Subscriber)wrapper.getElement());
						
					formPath.okButton.addActionListener( new ActionListener() {
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
							formPath.close();
						}
					});
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
				log.info("Пара {}:, изменен статус на поврежденная", p);
				iSys.changes = true;
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
				log.info("Пара {}:, изменен статус на исправная", p);
				iSys.changes = true;
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
						log.info("Пара {} удалена из включения: {} у абонента: {}", p, path, iSys.sc.getElement(path.getSubscriber()));
						iSys.changes = true;
						if (iSys.phc.isPairUsed(p) == null)  {
							p.setStatus(0);
							log.info("Пара {} освобождена", p);
							iSys.changes = true;
							util_setPairButtonColor(p, ep);
						}
					}
					form.close();
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
	 * @param iDialog - окно
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
						log.info("Кабель {} добавлен в канал {} участка канализации {}", cable, t, iSys.duc.getElement(t.getDuct()));
						iSys.changes = true;
						util_setTubeButtonColor(t, ep);	
						form.close();
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
								log.info("Кабель {} удален из канала {} участка канализации {}" , cable, t, iSys.duc.getElement(t.getDuct()));
								iSys.changes = true;
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
						log.info("Пара {} освобождена", oldPair);
						iSys.changes = true;
					}
					else {
						log.info("Пара {} удалена из включения {}", oldPair, path);
						iSys.changes = true;
					}
					
					returnedPair = oldPair;
				}
				String mes = "Пара "+ p.toString()+" занята включением: " + path.toString();
				log.info(mes);
				iSys.changes = true;
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
				log.info(mes);
				iSys.changes = true;
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
						log.info("Пара {} освобождена ", oldPair);
						iSys.changes = true;
					}
					else {
						log.info("Пара {} удалена из включения {}",oldPair, path);
						iSys.changes = true;
					}
					returnedPair = oldPair;
				}
				
				String mes = "Кабельная пара "+ p.toString()+" занята включением: " + path.toString();
				log.info(mes);
				iSys.changes = true;
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
					log.info("Пара {} освобождена", oldPair);
					iSys.changes = true;
				}
				else {
					log.info("Пара {} удалена из включения {}",oldPair,path);
					iSys.changes = true;
				}
				returnedPair = oldPair;
			}
				String mes = "Кабельная пара "+ p.toString()+" занята включением: " + path.toString();
				log.info(mes);
				iSys.changes = true;
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
				Subscriber sub = (Subscriber) iSys.sc.getElement(path.getSubscriber());
				infoArea.append("Абонент: " + sub + "(№ "+sub.getPhoneNumber()+")"+"\r\n");
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
	
	public void util_newInfo (String mes) {JOptionPane.showMessageDialog(iFrame, mes, "Информация", JOptionPane.INFORMATION_MESSAGE);}
	
	public void util_newError (String mes) {JOptionPane.showMessageDialog(iFrame, mes, "Ошибка", JOptionPane.ERROR_MESSAGE);}
	
	public static int util_newDialog(String mes) {
		
		Object[] options = {"Да", "Нет"};
		
		return JOptionPane.showOptionDialog(/*iFrame*/null,
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
			log.error(ex.toString());
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