package org.teleline.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowListener;

import org.teleline.model.*;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.UIManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SortOrder;
import javax.swing.RowSorter.SortKey;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import java.io.File;
import javax.swing.JList;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;

public class teleline {
	
	JFrame frmTeleline;
	public gui GUI;	
	public Sys sys;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			
			public void run() {
				try {
					teleline window = new teleline();
					WindowListener winListener = new telelineListener(window);
					window.frmTeleline.addWindowListener(winListener);
					
					window.frmTeleline.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * Create the application.
	 */
	public teleline() {
		
		sys = new Sys();
		GUI = new gui(sys,frmTeleline);

		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTeleline = new JFrame();
		
	//	frmTeleline.setResizable(false);
		frmTeleline.setTitle("teleLine - Система технического учета ЛКХ");
		frmTeleline.setBounds(0, 0, 800, 600);
		frmTeleline.setLocationRelativeTo(null);
		frmTeleline.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		UIManager.put("FileChooser.fileNameLabelText", "Имя файла:");
		UIManager.put("FileChooser.lookInLabelText", "Папка:");
		UIManager.put("FileChooser.filesOfTypeLabelText", "Тип:");
		UIManager.put("FileChooser.cancelButtonText", "Отмена");
		UIManager.put("FileChooser.upFolderToolTipText", "Вверх на один уровень");
		UIManager.put("FileChooser.newFolderToolTipText", "Новая папка");
		UIManager.put("FileChooser.homeFolderToolTipText", "Домашняя папка");
		UIManager.put("FileChooser.openButtonToolTipText","Открыть выбранный файл");
		UIManager.put("FileChooser.saveButtonText", "Сохранить");
		UIManager.put("FileChooser.saveButtonToolTipTex", "Сохранить файл");
		UIManager.put("FileChooser.directoryOpenButtonText","Открыть");
		UIManager.put("FileChooser.directoryOpenButtonToolTipText","Открыть выбранную папку");
		UIManager.put("FileChooser.homeFolderToolTipText","Домой");
		UIManager.put("FileChooser.openButtonText", "ОК");
        UIManager.put("FileChooser.fileNameHeaderText","Имя файла");
        UIManager.put("FileChooser.listViewButtonToolTipText","Список");
        UIManager.put("FileChooser.renameFileButtonText", "Переименовать");
        UIManager.put("FileChooser.deleteFileButtonText", "Удалить");
        UIManager.put("FileChooser.filterLabelText", "Типы файлов");
        UIManager.put("FileChooser.detailsViewButtonToolTipText", "Подробно");
        UIManager.put("FileChooser.fileSizeHeaderText","Размер");
        UIManager.put("FileChooser.fileDateHeaderText", "Дата ищменения:");
				
		final JMenuBar menuBar = new JMenuBar();
		frmTeleline.setJMenuBar(menuBar);
		
		JMenu menuFile = new JMenu("Файл");
		menuBar.add(menuFile);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Открыть...");
		mntmNewMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if (sys.rw.isSaved() == false) {
					if (GUI.newDialog(frmTeleline, "Сохранить изменения в файле?") == JOptionPane.YES_OPTION) {
						File file = sys.rw.save();
						if (file != null) {
							GUI.newInfo(frmTeleline, "Файл сохранен");
							frmTeleline.setTitle("teleLine - Система технического учета ЛКХ - " + file.getName());
							return;
						}
						else {
							GUI.newError(frmTeleline, "Ошибка при сохранении файла");
						}
					}					
				}
				 
				final JFileChooser chooser = new JFileChooser();
				
				chooser.setCurrentDirectory(new File("./saves"));
				chooser.setDialogTitle("Открыть файл...");
				chooser.setFileFilter(new ExtFileFilter("xml", "*.xml Файлы XML"));
				if (chooser.showDialog(frmTeleline, null) == JFileChooser.APPROVE_OPTION) {
					
					sys.clear();
					
					if (sys.rw.read(chooser.getSelectedFile())) {
						GUI.newInfo(frmTeleline, "Файл \"" + chooser.getSelectedFile().getName() + "\" прочитан");
						frmTeleline.setTitle("teleLine - Система технического учета ЛКХ - " + chooser.getSelectedFile().getName());
						
					}
					else {
						GUI.newError(frmTeleline, "Ошибка при чтении файла");
					}
				}
			}
		});
		menuFile.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Сохранить");
		mntmNewMenuItem_1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File file = sys.rw.save();
				if (file != null) {
					GUI.newInfo(frmTeleline, "Файл сохранен");
					frmTeleline.setTitle("teleLine - Система технического учета ЛКХ - " + file.getName());
				}
				else {
					GUI.newError(frmTeleline, "Ошибка при сохранении файла");
				}
			}
		});
		menuFile.add(mntmNewMenuItem_1);
		
		final JMenu menuCreate = new JMenu("Создать");
		menuBar.add(menuCreate);
	/*	
		JMenuItem menuItem = new JMenuItem("Сеть");
		menuItem.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent arg0) { GUI.formNet(null); }});
		menuCreate.add(menuItem);
	*/	
		JMenuItem menuItem_1 = new JMenuItem("Кросс");
		menuItem_1.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {new FormDFrame(sys, null);}});
		JSeparator separator = new JSeparator();
		menuCreate.add(separator);
		menuCreate.add(menuItem_1);
		
		JMenuItem menuItem_2 = new JMenuItem("Шкаф");
		menuItem_2.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {new FormCabinet(sys, null);}});
		menuCreate.add(menuItem_2);
		
		JMenuItem menuItem_3 = new JMenuItem("Распределительную коробку (КРТ)");
		menuItem_3.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {new FormDBox(sys, null);}});
		menuCreate.add(menuItem_3);
		
		JSeparator separator_1 = new JSeparator();
		menuCreate.add(separator_1);
		
		JMenuItem menuItem_4 = new JMenuItem("Громполосу");
		menuItem_4.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {new FormFrame(sys, null,null);}});
		menuCreate.add(menuItem_4);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Бокс");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent arg0) { new FormBox(sys,null,null);}});
		menuCreate.add(mntmNewMenuItem_2);
		
		JSeparator separator_2 = new JSeparator();
		menuCreate.add(separator_2);
		
		JMenu menu_2 = new JMenu("Кабельные пары");
		menuCreate.add(menu_2);
		/**
		 * Создание магистральных пар
		 */
		JMenuItem menuItem_6 = new JMenuItem("Магистральные");
		menuItem_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				final JDialog iFrame = GUI.newDialog("Создать магистральные пары", 410,  570);
				
			//	GUI.newLabel("Сеть:", iFrame, 20, 15, 360, 25);
			//	final JComboBox comboBox = GUI.newNetsComboBox(iFrame, 20, 40, 360, 25);
				
				final Integer netId = sys.nc.getOnlyElement().getId();
				
				GUI.newLabel("От кросса/громполосы:", iFrame, 20, 75, 360, 25);
				final JComboBox comboBox1 = GUI.dframeComboBox(sys.nc.getOnlyElement().getId()/*comboBox*/, iFrame, 20, 100, 360, 25);
				final JComboBox comboBox2 = GUI.frameComboBox(comboBox1, iFrame, 20, 135, 360, 25);
				GUI.dframeComboBoxLinked(comboBox1, comboBox2);
				
				GUI.newLabel("До шкафа/бокса:", iFrame, 20, 170, 360, 25);
				final JComboBox comboBox3 = GUI.cabinetComboBox(netId, 1, iFrame, 20, 195, 360, 25);
				final JComboBox comboBox4 = GUI.boxComboBox(comboBox3, 0, iFrame, 20, 230, 360, 25);
				GUI.cabinetComboBoxLinked(comboBox3, comboBox4, 0);
				
				final JComboBox comboBox6 = GUI.cableComboBox(netId/*comboBox*/, comboBox1, comboBox3, 0, iFrame, 20, 440, 360, 25);
				
		//		GUI.netsDFrameComboLinked(comboBox, comboBox1);
		//		GUI.netsCabinetComboLinked(comboBox, comboBox3, 1);
				GUI.netsCableComboLinked(netId, comboBox1, comboBox3, comboBox6, 0);
				
		        GUI.newLabel("Количество создаваемых пар:", iFrame, 20, 265, 360, 25);
				
				final JComboBox comboBox5 = new JComboBox();
				
				comboBox5.addItem((Integer)10);
				comboBox5.addItem((Integer)20);
				comboBox5.addItem((Integer)25);
				comboBox5.addItem((Integer)30);
				comboBox5.addItem((Integer)50);
				comboBox5.addItem((Integer)75);
				comboBox5.addItem((Integer)100);
				comboBox5.addItem((Integer)150);
				comboBox5.setSelectedIndex(6);
				comboBox5.setBounds(20, 290, 360, 25);
				iFrame.getContentPane().add(comboBox5);
				
				GUI.newLabel("ГП заполнять с:", iFrame, 20, 325, 360, 25);
				final JTextField dframeFrom = GUI.newTextField(iFrame, 140, 325, 140, 25);
				dframeFrom.setText("0");
				dframeFrom.setEditable(false);
				JButton selectFrom = GUI.newButton("Выбрать", iFrame, 290, 325, 90, 25);
				
				selectFrom.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
			//			if (comboBox.getSelectedIndex() == -1) { GUI.newError(iFrame, "Не выбрана сеть!"); return; }
						if (comboBox1.getSelectedIndex() == -1) { GUI.newError(iFrame, "Не выбран кросс!"); return; }
						if (comboBox2.getSelectedIndex() == -1) { GUI.newError(iFrame, "Не выбрана громполоса!"); return; }
						new FormViewConnectedPointElement(sys,(ConnectedPointElement)comboBox2.getSelectedItem(), dframeFrom, null);
					}
				});
				
			    GUI.newLabel("Бокс заполнять с:", iFrame, 20, 355, 260, 25);
			    final JTextField boxFrom = GUI.newTextField(iFrame, 140, 355, 140, 25);
				boxFrom.setText("0");
				boxFrom.setEditable(false);
				JButton selectBox = GUI.newButton("Выбрать", iFrame, 290, 355, 90, 25);
				
				selectBox.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
				//		if (comboBox.getSelectedIndex() == -1) { GUI.newError(iFrame, "Не выбрана сеть!"); return; }
						if (comboBox3.getSelectedIndex() == -1) { GUI.newError(iFrame, "Не выбран шкаф!"); return; }
						if (comboBox4.getSelectedIndex() == -1)	{ GUI.newError(iFrame, "Не выбран бокс!"); return; }
						new FormViewConnectedPointElement(sys,(ConnectedPointElement)comboBox4.getSelectedItem(), boxFrom, null);
					}
				});
				
				GUI.newLabel("Кабель заполнять с:", iFrame, 20, 385, 260, 25);
			    final JTextField cableFrom = GUI.newTextField(iFrame, 140, 385, 140, 25);
				cableFrom.setText("0");
				cableFrom.setEditable(false);
				JButton selectCable = GUI.newButton("Выбрать", iFrame, 290, 385, 90, 25);
				
				selectCable.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
					//	if (comboBox.getSelectedIndex() == -1) { GUI.newError(iFrame, "Не выбран кабель!"); return; }
						if (comboBox6.getSelectedIndex() == -1) { GUI.newError(iFrame, "Не выбран кабель!"); return; }
						new FormViewCable(sys,(Cable)comboBox6.getSelectedItem(), cableFrom);
					}
				});
			   
			    
			    GUI.newLabel("Добавить в магистральный кабель:", iFrame, 20, 415, 360, 25);
				
				JButton saveButton = GUI.newButton("Сoхранить", iFrame, 20, 490, 110, 25);
				saveButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						
					//	if (comboBox.getSelectedIndex() == -1) { GUI.newError(iFrame, "Не выбрана сеть!"); return; }
						if (comboBox1.getSelectedIndex() == -1) { GUI.newError(iFrame, "Не выбран кросс!"); return; }
						if (comboBox2.getSelectedIndex() == -1) { GUI.newError(iFrame, "Не выбрана громполоса!"); return; }
						if (comboBox3.getSelectedIndex() == -1)	{ GUI.newError(iFrame, "Не выбран шкаф!"); return; }
						if (comboBox4.getSelectedIndex() == -1)	{ GUI.newError(iFrame, "Не выбран бокс!"); return; }
						if (comboBox6.getSelectedIndex() == -1)	{ GUI.newError(iFrame, "Не выбран кабель!"); return; }
						
						DFramе selectedDFrame = (DFramе)comboBox1.getSelectedItem();
						Frame selectedFrame = (Frame)comboBox2.getSelectedItem();
						Cabinet selectedCabinet = (Cabinet)comboBox3.getSelectedItem();
						Box selectedBox = (Box)comboBox4.getSelectedItem();
						Cable selectedCable = (Cable)comboBox6.getSelectedItem();
						Integer pairCount = (Integer)comboBox5.getSelectedItem();
						Integer fromFrame = sys.rw.valueOf(dframeFrom.getText());
						Integer fromBox = sys.rw.valueOf(boxFrom.getText());
						Integer fromCable = sys.rw.valueOf(cableFrom.getText());
						
					//	if (pairCount + fromFrame > selectedFrame.getCapacity()) { GUI.newError(iFrame, "Данное количество пар не умещается в громполосе!"); return; }
					//	if (pairCount + fromBox > selectedBox.getCapacity()) { GUI.newError(iFrame, "Данное количество пар не умещается в боксе!"); return; }						
					//	if (pairCount + fromCable > selectedCable.getCapacity()) { GUI.newError(iFrame, "Данное количество пар не умещается в кабеле!"); return; }						
						
						//if (selectedCable.isConnect(pairCount) == false) { GUI.newError(iFrame, "В кабеле нет достаточного места для добавления указанного количества пар"); return; }
						
						for (Integer i = fromFrame; i < fromFrame + pairCount; i++)
							if (sys.pc.getInPlace(selectedFrame, i) != null)  { GUI.newError(iFrame, "В громполосе в заданном диапазоне уже существуют кабельные пары!"); return; }
						
						for (Integer i = fromBox; i < fromBox + pairCount; i++)
							if (sys.pc.getInPlace(selectedBox, i) != null)  { GUI.newError(iFrame, "В боксе в заданном диапазоне уже существуют кабельные пары!"); return; }	
						
						for (Integer i = fromCable; i < fromCable + pairCount; i++)
							if (sys.pc.getInPlace(selectedCable, i) != null)  { GUI.newError(iFrame, "В кабеле в заданном диапазоне уже существуют кабельные пары!"); return; }	
						
						//Integer inCableFirst = selectedCable.connect(pairCount);
												
						for (int i = 0; i < pairCount; i++) {
							
							Pair newPair = new Pair(sys.fc,sys.bc,sys.dbc,sys.cc);
							
							newPair
								.attachToElementFrom(selectedFrame)
								.attachToElementTo(selectedBox)
								.attachToCable(selectedCable)
								.setNumberInCable(fromCable + i)
								.setFromNumber(fromFrame + i)
								.setToNumber(fromBox + i);
								//.setType(0);
							
							sys.pc.addElement(newPair);
							String mes = "Создана магистральная кабельная пара: "+ newPair.toString()+ ", присоединена к кроссу: "+selectedDFrame.toString()+", громполосе: "+ selectedFrame.toString() + ", присоединена к шкафу: "+selectedCabinet.toString()+", боксу: " + selectedBox.toString();
							sys.rw.addLogMessage(mes);
							
						}
						String mes = "Создано " + pairCount.toString() + " кабельных пар, присоединены к кроссу: "+selectedDFrame.toString()+", громполосе: "+ selectedFrame.toString() + ", присоединены к шкафу: "+selectedCabinet.toString()+", боксу: " + selectedBox.toString();
						GUI.newInfo(iFrame, mes);
						iFrame.dispose();
					
					}
				});
				iFrame.setVisible(true);	
			}
		});
		
		menu_2.add(menuItem_6);
		/**
		 * Создание межшкафных пар
		 */
		JMenuItem menuItem_7 = new JMenuItem("Межшкафные");
		menuItem_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final JDialog iFrame = GUI.newDialog("Создать межшкафные пары", 410, 570);
				
		//		GUI.newLabel("Сеть:", iFrame, 20, 15, 360, 25);
		//		final JComboBox comboBox = GUI.newNetsComboBox(iFrame, 20, 40, 360, 25);
				
				final Integer netId = sys.nc.getOnlyElement().getId();
				
				GUI.newLabel("От шкафа/бокса (№1):", iFrame, 20, 75, 360, 25);
				final JComboBox comboBox1 = GUI.cabinetComboBox(netId, 0, iFrame, 20, 100, 360, 25);
				final JComboBox comboBox2 = GUI.boxComboBox(comboBox1, 1, iFrame, 20, 135, 360, 25);
				GUI.cabinetComboBoxLinked(comboBox1, comboBox2, 1);
				
				GUI.newLabel("До шкафа/бокса (№2):", iFrame, 20, 170, 360, 25);
				final JComboBox comboBox3 = GUI.cabinetComboBox(netId, 0, iFrame, 20, 195, 360, 25);
				final JComboBox comboBox4 = GUI.boxComboBox(comboBox3, 1, iFrame, 20, 230, 360, 25);
				GUI.cabinetComboBoxLinked(comboBox3, comboBox4, 1);
				
				final JComboBox comboBox6 = GUI.cableComboBox(netId/*comboBox*/, comboBox1, comboBox3, 1, iFrame, 20, 440, 360, 25);
				
		//		GUI.netsCabinetComboLinked(comboBox, comboBox1, 0);
		//		GUI.netsCabinetComboLinked(comboBox, comboBox3, 0);
				GUI.netsCableComboLinked(netId, comboBox1, comboBox3, comboBox6, 1);
				
				GUI.newLabel("Количество создаваемых пар:", iFrame, 20, 265, 360, 25);
					
				final JComboBox comboBox5 = new JComboBox();
					
				comboBox5.addItem((Integer)10);
				comboBox5.addItem((Integer)20);
				comboBox5.addItem((Integer)25);
				comboBox5.addItem((Integer)30);
				comboBox5.addItem((Integer)50);
				comboBox5.addItem((Integer)75);
				comboBox5.addItem((Integer)100);
				comboBox5.addItem((Integer)150);
				comboBox5.setSelectedIndex(6);
				comboBox5.setBounds(20, 290, 360, 25);
				iFrame.getContentPane().add(comboBox5);
					
				GUI.newLabel("Бокс1 заполнять с:", iFrame, 20, 325, 360, 25);
				final JTextField box1From = GUI.newTextField(iFrame, 140, 325, 140, 25);
				box1From.setText("0");
				box1From.setEditable(false);
				JButton selectFrom = GUI.newButton("Выбрать", iFrame, 290, 325, 90, 25);
				
				selectFrom.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
				//		if (comboBox.getSelectedIndex() == -1) { GUI.newError(iFrame, "Не выбрана сеть!"); return; }
						if (comboBox1.getSelectedIndex() == -1) { GUI.newError(iFrame, "Не выбран шкаф!"); return; }
						if (comboBox2.getSelectedIndex() == -1) { GUI.newError(iFrame, "Не выбрана бокс!"); return; }
						new FormViewConnectedPointElement(sys,(ConnectedPointElement)comboBox2.getSelectedItem(), box1From, null);
					}
				});
				
			    GUI.newLabel("Бокс2 заполнять с:", iFrame, 20, 355, 360, 25);
			    final JTextField box2From = GUI.newTextField(iFrame, 140, 355, 140, 25);
			    box2From.setText("0");
			    box2From.setEditable(false);
				JButton selectBox = GUI.newButton("Выбрать", iFrame, 290, 355, 90, 25);
				
				selectBox.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
			//			if (comboBox.getSelectedIndex() == -1) { GUI.newError(iFrame, "Не выбрана сеть!"); return; }
						if (comboBox3.getSelectedIndex() == -1) { GUI.newError(iFrame, "Не выбран шкаф!"); return; }
						if (comboBox4.getSelectedIndex() == -1)	{ GUI.newError(iFrame, "Не выбран бокс!"); return; }
						new FormViewConnectedPointElement(sys,(ConnectedPointElement)comboBox4.getSelectedItem(), box2From, null);
					}
				});
				
				GUI.newLabel("Кабель заполнять с:", iFrame, 20, 385, 260, 25);
			    final JTextField cableFrom = GUI.newTextField(iFrame, 140, 385, 140, 25);
				cableFrom.setText("0");
				cableFrom.setEditable(false);
				JButton selectCable = GUI.newButton("Выбрать", iFrame, 290, 385, 90, 25);
				
				selectCable.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
				//		if (comboBox.getSelectedIndex() == -1) { GUI.newError(iFrame, "Не выбран кабель!"); return; }
						if (comboBox6.getSelectedIndex() == -1) { GUI.newError(iFrame, "Не выбран кабель!"); return; }
						new FormViewCable(sys,(Cable)comboBox6.getSelectedItem(), cableFrom);
					}
				});
				    
				GUI.newLabel("Добавить в передаточный кабель:", iFrame, 20, 415, 360, 25);
				
				JButton saveButton = GUI.newButton("Сoхранить", iFrame, 20, 490, 110, 25);
				saveButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						
						if (comboBox1.getSelectedIndex() == -1 || comboBox3.getSelectedIndex() == -1)	{ GUI.newError(iFrame, "Не выбран шкаф!"); return; }
						if (comboBox2.getSelectedIndex() == -1 || comboBox4.getSelectedIndex() == -1)	{ GUI.newError(iFrame, "Не выбран бокс!"); return; }
						if (comboBox6.getSelectedIndex() == -1)	{ GUI.newError(iFrame, "Не выбран кабель!"); return; }
						
						Cabinet selectedCabinet1 = (Cabinet)comboBox1.getSelectedItem();
						Box selectedBox1 = (Box)comboBox2.getSelectedItem();
						Cabinet selectedCabinet2 = (Cabinet)comboBox3.getSelectedItem();
						Box selectedBox2 = (Box)comboBox4.getSelectedItem();
						Cable selectedCable = (Cable)comboBox6.getSelectedItem();
						if (selectedCabinet1.getId().equals(selectedCabinet2.getId())) {GUI.newError(iFrame,"Выберите разные шкафы!"); return;}
						Integer pairCount = (Integer)comboBox5.getSelectedItem();
						Integer fromBox1 = sys.rw.valueOf(box1From.getText());
						Integer fromBox2 = sys.rw.valueOf(box2From.getText());
						Integer fromCable = sys.rw.valueOf(cableFrom.getText());
						
						//if (pairCount + fromBox1 > selectedBox1.getCapacity()) { GUI.newError(iFrame, "Данное количество пар не умещается в боксе №1!"); return; }
						//if (pairCount + fromBox2 > selectedBox2.getCapacity()) { GUI.newError(iFrame, "Данное количество пар не умещается в боксе №2!"); return; }					
						//if (selectedCable.isConnect(pairCount) == false) { GUI.newError(iFrame, "В кабеле нет достаточного места для добавления указанного количества пар"); return; }
						
						//Integer inCableFirst = selectedCable.connect(pairCount);
						
						for (Integer i = fromBox1; i < fromBox1 + pairCount; i++)
							if (sys.pc.getInPlace(selectedBox1, i) != null)  { GUI.newError(iFrame, "В боксе №1 в заданном диапазоне уже существуют кабельные пары!"); return; }
						
						for (Integer i = fromBox2; i < fromBox2 + pairCount; i++)
							if (sys.pc.getInPlace(selectedBox2, i) != null)  { GUI.newError(iFrame, "В боксе №2 в заданном диапазоне уже существуют кабельные пары!"); return; }
						
						for (Integer i = fromCable; i < fromCable + pairCount; i++)
							if (sys.pc.getInPlace(selectedCable, i) != null)  { GUI.newError(iFrame, "В кабеле в заданном диапазоне уже существуют кабельные пары!"); return; }	
												
						for (int i = 0; i < pairCount; i++) {
							
							Pair newPair = new Pair(sys.fc,sys.bc,sys.dbc,sys.cc);
							
							newPair
								.attachToElementFrom(selectedBox1)
								.attachToElementTo(selectedBox2)
								.attachToCable(selectedCable)
								.setNumberInCable(fromCable + i)
								.setFromNumber(fromBox1 + i)
								.setToNumber(fromBox2 + i);
								//.setType(1);
							
							sys.pc.addElement(newPair);
							String mes = "Создана передаточная кабельная пара: "+ newPair.toString()+ ", присоединена к шкафу: "+selectedCabinet1.toString()+", боксу: "+ selectedBox1.toString() + ", присоединена к шкафу: "+selectedCabinet2.toString()+", боксу: " + selectedBox2.toString();
							sys.rw.addLogMessage(mes);
							
						}
						String mes = "Создано " + pairCount.toString() + " кабельных пар, присоединены к шкафу: "+selectedCabinet1.toString()+", боксу: "+ selectedBox1.toString() + ", присоединена к шкафу: "+selectedCabinet2.toString()+", боксу: " + selectedBox2.toString();
						GUI.newInfo(iFrame, mes);
						iFrame.dispose();
					
					}
				});
				
				iFrame.setVisible(true);
			}
		});
		menu_2.add(menuItem_7);
		/**
		 * Создание распределительных пар
		 */
		JMenuItem menuItem_8 = new JMenuItem("Распределительные");
		menuItem_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new FormPairDistrib(sys);
			}
		});
		menu_2.add(menuItem_8);
		/**
		 * Создание пар прямого питания
		 */
		JMenuItem menuItem_5 = new JMenuItem("Прямого питания");
		menuItem_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				new FormPairDirect(sys);
				
			}
		});
		menu_2.add(menuItem_5);
		
		JMenuItem menuItem_9 = new JMenuItem("Кабель");
		menuItem_9.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {new FormCable(sys, null);}});
		menuCreate.add(menuItem_9);
		
		JSeparator separator_3 = new JSeparator();
		menuCreate.add(separator_3);
		
		JMenuItem menuItem_10 = new JMenuItem("Колодец");
		menuItem_10.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent arg0) {new FormManhole(sys, null);}});
		menuCreate.add(menuItem_10);
		
		JMenuItem mntmNewMenuItem_4 = new JMenuItem("Канализацию");
		mntmNewMenuItem_4.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent arg0) { new FormDuct(sys, null);}});
		menuCreate.add(mntmNewMenuItem_4);
		
		JMenuItem menuItem_19 = new JMenuItem("Здание");
		menuItem_19.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent arg0) {new FormBuilding(sys, null);}});
		menuCreate.add(menuItem_19);
		
		JSeparator separator_4 = new JSeparator();
		menuCreate.add(separator_4);
		
		JMenuItem menuItem_15 = new JMenuItem("Абонента");
		menuItem_15.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent arg0) { new FormSubscriber(sys, null); }});
		menuCreate.add(menuItem_15);
		
		JMenu menuChange = new JMenu("Смотреть");
		menuBar.add(menuChange);
		/**
		 * Редактирование элементов "Сеть"
		 */
		JMenuItem menuItem_14 = new JMenuItem("Сеть");
		menuItem_14.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new FormNet(sys, (Net)sys.nc.getOnlyElement());
				}
			});
		menuChange.add(menuItem_14);
		
		JSeparator separator_6 = new JSeparator();
		menuChange.add(separator_6);
		/**
		 * Редактирование элементов "Кросс"
		 */
		JMenuItem menuItem_12 = new JMenuItem("Кросс");
		menuChange.add(menuItem_12);
		menuItem_12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				final JDialog iFrame = GUI.newDialog("Редактировать кросс", 585, 600);
				
			//	GUI.newLabel("Сеть:", iFrame, 10, 10, 420, 14);
			//	final JComboBox netsComboBox = GUI.newNetsComboBox(iFrame, 10, 30, 420, 25);
				
				GUI.newLabel("Список кроссов:", iFrame, 10, 10, 420, 14);
				final JList dframeList = GUI.newList(iFrame, 10, 30, 420, 530);
				
				GUI.setListItems(dframeList, sys.dfc.sortByNumberUp(sys.dfc.getInNet((Net)sys.nc.getOnlyElement())));
			
			//	GUI.netsComboBoxLinked(netsComboBox, dframeList, sys.dfc);
				
				JButton refreshButton = GUI.newButton("Обновить", iFrame, 440, 30, 125, 26);
				JButton editDFrameButton = GUI.newButton("Редактировать", iFrame, 440, 105, 125, 26);
				JButton viewDFrameButton = GUI.newButton("Смотреть", iFrame, 440, 145, 125, 26);
				JButton passportDFrameButton = GUI.newButton("Паспорт", iFrame, 440, 185, 125, 26);
				JButton createDFrameButton = GUI.newButton("Добавить", iFrame, 440, 255, 125, 26);
				JButton deleteDFrameButton = GUI.newButton("Удалить", iFrame, 440, 295, 125, 26);
				
				refreshButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent arg0) {
						GUI.setListItems(dframeList, sys.dfc.sortByNumberUp(sys.dfc.getInNet((Net)sys.nc.getOnlyElement())));
					}
				});
				/*
				 * Событие кнопки редактирования кросса
				 */
				ActionListener editDFrame = new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (dframeList.getSelectedIndex() == -1) { 
							GUI.newError(iFrame, "Кросс не выбран!"); 
							return; 
						}
						
						new FormDFrame(sys, (DFramе)dframeList.getSelectedValue());
					}
				};
				editDFrameButton.addActionListener(editDFrame);
				/*
				 * ---------------------------------------------------------
				 */
				/*
				 * Событие кнопки просмотра кросса
				 */
				ActionListener viewDFrame = new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (dframeList.getSelectedIndex() == -1) {
							GUI.newError(iFrame, "Кросс не выбран!");
							return;
						}
						new FormViewDFrame(sys,(DFramе)dframeList.getSelectedValue());
					}
				};
				viewDFrameButton.addActionListener(viewDFrame);
				/*
				 * ---------------------------------------------------------
				 */
				/*
				 * Событие кнопки создания кросса
				 */
				ActionListener createDFrame = new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						new FormDFrame(sys, null);
					}
				};
				createDFrameButton.addActionListener(createDFrame);
				/*
				 * ---------------------------------------------------------
				 */
				/*
				 * Событие кнопки удаления кросса
				 */
				ActionListener deleteCabinet = new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (dframeList.getSelectedIndex() == -1) { GUI.newError(iFrame, "Кросс не выбран!"); return; }
						int n = GUI.newDialog(iFrame, "Удалить " + dframeList.getSelectedValue().toString()+" и все его содержимое?");
						if (n == JOptionPane.YES_OPTION) {
							sys.removeDFrame((DFramе)dframeList.getSelectedValue());
							GUI.newInfo(iFrame, "Кросс и все его содержимое удалены");
							//GUI.setListItems(dframeList, sys.dfc.sortByNumberUp(sys.dfc.getInNet((Net)netsComboBox.getSelectedItem())));
							GUI.setListItems(dframeList, sys.dfc.sortByNumberUp(sys.dfc.getInNet((Net)sys.nc.getOnlyElement())));
						}		
					}
				};
				deleteDFrameButton.addActionListener(deleteCabinet);
				/*
				 * ---------------------------------------------------------
				 */
				/*
				 * Событие кнопки просмотра паспорта кросса
				 */
				ActionListener passportCabinet = new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (dframeList.getSelectedIndex() == -1) {
							GUI.newError(iFrame, "Кросс не выбран!");
							return;
						}
						GUI.formViewPassport(sys.rw.createDFramePassport((DFramе)dframeList.getSelectedValue()));
					}		
				};
				passportDFrameButton.addActionListener(passportCabinet);
				/*
				 * ---------------------------------------------------------
				 */
				iFrame.setVisible(true);	
			}
		});
		/**
		 * Редактирование элементов "Шкаф"
		 */
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Шкаф");
		menuChange.add(mntmNewMenuItem_3);
		mntmNewMenuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				new FormCabinets(sys,sys.cbc.getElements());	
			}
		});
		/**
		 * Редактирование элементов "КРТ"
		 */
		JMenuItem editDBoxMenuItem = new JMenuItem("Распределительную коробку (КРТ)");
		menuChange.add(editDBoxMenuItem);
		editDBoxMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				new FormDBoxes(sys, sys.dbc.getElements());	
			}
		});
		
		JSeparator separator_5 = new JSeparator();
		menuChange.add(separator_5);
		/**
		 * Редактирование элементов "Абонент"
		 */
		JMenuItem menuItem_11 = new JMenuItem("Абонента");
		menuItem_11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				new FormSubscribers(sys,sys.sc.getElements());
			}
		});
		/**
		 * Редактирование элементов Кабель
		 */
		JMenuItem menuItem_16 = new JMenuItem("Кабель");
		menuChange.add(menuItem_16);
		menuItem_16.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				final JDialog iFrame = GUI.newDialog("Редактировать кабель", 685, 600);
				
		//		GUI.newLabel("Сеть:", iFrame, 10, 10, 520, 14);
		//		final JComboBox netsComboBox = GUI.newNetsComboBox(iFrame, 10, 30, 520, 25);
				
				GUI.newLabel("Список кабелей:", iFrame, 10, 10, 520, 14);
				final JTable cableTable = GUI.newTable(iFrame, 10, 30, 520, 525);
				final DefaultTableModel tableModel = (DefaultTableModel) cableTable.getModel();
				tableModel.setColumnIdentifiers(new String[]{"Кабель","От","До","Емкость","Исп.емкость","Длина"});
				final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(cableTable.getModel());
				cableTable.setRowSorter(sorter);
			//	GUI.linkNetsComboBoxCableTable(netsComboBox, cableTable);
				
				Iterator<StructuredElement> i = sys.cc.getInNet(sys.nc.getOnlyElement().getId()).iterator();
	    		while (i.hasNext()) {
	    			GUI.addCableToTable(cableTable, (Cable)i.next());
	    		}
				
				JButton refreshButton = GUI.newButton("Обновить", iFrame, 540, 30, 125, 26);
				JButton editCableButton = GUI.newButton("Редактировать", iFrame, 540, 105, 125, 26);
				JButton viewCableButton = GUI.newButton("Смотреть", iFrame, 540, 145, 125, 26);
				JButton passportCableButton = GUI.newButton("Паспорт", iFrame, 540, 185, 125, 26);
				JButton createCableButton = GUI.newButton("Добавить", iFrame, 540, 255, 125, 26);
				JButton deleteCableButton = GUI.newButton("Удалить", iFrame, 540, 295, 125, 26);
				/*
				 * Событие кнопки обновления списка кабелей
				 */
				refreshButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						GUI.clearTable(cableTable);
						Iterator<StructuredElement> i = sys.cc.getInNet(sys.nc.getOnlyElement().getId()).iterator();
			    		while (i.hasNext()) {
			    			GUI.addCableToTable(cableTable, (Cable)i.next());
			    		}
					}
				});
				/*
				 * ---------------------------------------------------------
				 */
				/*
				 * Событие кнопки редактирования кабеля
				 */
				ActionListener editCable = new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (cableTable.getSelectionModel().isSelectionEmpty()){ GUI.newError(iFrame, "Кабель не выбран!"); return; }
						int selectedIndex = cableTable.getRowSorter().convertRowIndexToModel(cableTable.getSelectionModel().getMinSelectionIndex());
						Cable cable = (Cable)tableModel.getValueAt(selectedIndex, 0);
						new FormCable(sys, cable);
						GUI.updateCableInTable(cableTable, cable, selectedIndex);
					}
				};
				editCableButton.addActionListener(editCable);
				/*
				 * ---------------------------------------------------------
				 */
				/*
				 * Событие кнопки просмотра кабеля
				 */
				ActionListener viewCable = new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (cableTable.getSelectionModel().isSelectionEmpty()){ GUI.newError(iFrame, "Кабель не выбран!"); return; }
						int selectedIndex = cableTable.getRowSorter().convertRowIndexToModel(cableTable.getSelectionModel().getMinSelectionIndex());
						new FormViewCable(sys,(Cable)tableModel.getValueAt( selectedIndex, 0), null);
					}
				};
				viewCableButton.addActionListener(viewCable);
				/*
				 * ---------------------------------------------------------
				 */
				/*
				 * Событие кнопки создания кабеля
				 */
				ActionListener createCable = new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
					//	if (netsComboBox.getSelectedIndex() == -1) { GUI.newError(iFrame, "Сеть не выбрана"); return; }
						new FormCable(sys, null);
						//if (cable != null) GUI.addCableToTable(cableTable, cable);
					}
				};
				createCableButton.addActionListener(createCable);
				/*
				 * ---------------------------------------------------------
				 */
				/*
				 * Событие кнопки удаления кабеля
				 */
				ActionListener deleteCable = new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						
						if (cableTable.getSelectionModel().isSelectionEmpty()){ GUI.newError(iFrame, "Кабель не выбран!"); return; }
						int selectedIndex = cableTable.getRowSorter().convertRowIndexToModel(cableTable.getSelectionModel().getMinSelectionIndex());
						Cable cable = (Cable)tableModel.getValueAt( selectedIndex, 0);
						
						int n = GUI.newDialog(iFrame, "Удалить кабель: " + cable.toString()+" и все содержащиеся в нем пары?");
						if (n == JOptionPane.YES_OPTION) {
							sys.removeCable(cable);
							GUI.newInfo(iFrame, "Кабель "+cable.toString()+" и все содержащиеся в нем пары удалены");
							((DefaultTableModel) cableTable.getModel()).removeRow(selectedIndex);	
						}
						
						
					}
				};
				deleteCableButton.addActionListener(deleteCable);
				/*
				 * ---------------------------------------------------------
				 */
				/*
				 * Событие кнопки просмотра паспорта кабеля
				 */
				ActionListener passportCabinet = new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						//if (cableList.getSelectedIndex() == -1) { GUI.newError(iFrame, "Кабель не выбран!"); return; }
						if (cableTable.getSelectionModel().isSelectionEmpty()){ GUI.newError(iFrame, "Кабель не выбран!"); return; }
						int selectedIndex = cableTable.getRowSorter().convertRowIndexToModel(cableTable.getSelectionModel().getMinSelectionIndex());
						Cable cable = (Cable)tableModel.getValueAt( selectedIndex, 0);
						//Cable cable = (Cable)cableList.getSelectedValue();
						if (cable.getType() == 2) {GUI.newError(iFrame, "Паспорт для распределительного кабеля создается в составе паспорта шкафа."); return;}
						if (cable.getType() == 0) {GUI.formViewPassport(sys.rw.createМCablePassport(cable)); return;}
						if (cable.getType() == 1) {GUI.formViewPassport(sys.rw.createIcCablePassport(cable)); return;}
						if (cable.getType() == 3) {return;}
					}		
				};
				passportCableButton.addActionListener(passportCabinet);
				/*
				 * ---------------------------------------------------------
				 */
				ArrayList<SortKey> keys=new ArrayList<SortKey>();
		        keys.add(new SortKey(0, SortOrder.ASCENDING));                                             
		        sorter.setSortKeys(keys);
		        sorter.setSortsOnUpdates(true);
				iFrame.setVisible(true);	
			}
		});
		
		JSeparator separator_7 = new JSeparator();
		menuChange.add(separator_7);
		/**
		 * Редактирование элементов "Колодец"
		 */
		JMenuItem menuItem_17 = new JMenuItem("Колодец");
		menuItem_17.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new FormManholes(sys, sys.mc.getElements());
			}
		});
		menuChange.add(menuItem_17);
		/**
		 * Редактирование элементов "Канализация"
		 */
		JMenuItem menuItem_18 = new JMenuItem("Канализацию");
		menuItem_18.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final JDialog iFrame = GUI.newDialog("Редактировать канализацию", 585, 600);
				
		//		GUI.newLabel("Сеть:", iFrame, 10, 10, 420, 14);
		//		final JComboBox netsComboBox = GUI.newNetsComboBox(iFrame, 10, 30, 420, 25);
				
				GUI.newLabel("Список участков канализации:", iFrame, 10, 10, 420, 14);
				final JList ductList = GUI.newList(iFrame, 10, 30, 420, 525);
				
		//		GUI.netsComboBoxLinked(netsComboBox, ductList, sys.duc);
				
				GUI.setListItems(ductList, sys.duc.sortByIdUp(sys.duc.getInNet((Net)sys.nc.getOnlyElement())));
				
				JButton refreshButton = GUI.newButton("Обновить", iFrame, 440, 30, 125, 26);
				JButton editDuctButton = GUI.newButton("Редактировать", iFrame, 440, 105, 125, 26);
				JButton viewDuctButton = GUI.newButton("Смотреть", iFrame, 440, 145, 125, 26);
				JButton passportDuctButton = GUI.newButton("Паспорт", iFrame, 440, 185, 125, 26);
				JButton createDuctButton = GUI.newButton("Добавить", iFrame, 440, 255, 125, 26);
				JButton deleteDuctButton = GUI.newButton("Удалить", iFrame, 440, 295, 125, 26);
				/*
				 * Событие кнопки обновления списка участков канализации
				 */
				refreshButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						GUI.setListItems(ductList, sys.duc.sortByIdUp(sys.duc.getInNet((Net)sys.nc.getOnlyElement())));
					}
				});
				/*
				 * ---------------------------------------------------------
				 */
				/*
				 * Событие кнопки редактирования канализации
				 */
				editDuctButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (ductList.getSelectedIndex() == -1) { GUI.newError(iFrame, "Участок канализации не выбран!"); return; }
						new FormDuct(sys,(Duct)ductList.getSelectedValue());
				//		GUI.setListItems(ductList, sys.duc.sortByIdUp(sys.duc.getInNet((Net)netsComboBox.getSelectedItem())));	
					}
				});
				/*
				 * ---------------------------------------------------------
				 */
				/*
				 * Событие кнопки просмотра канализации
				 */
				ActionListener viewDuct = new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (ductList.getSelectedIndex() == -1) { GUI.newError(iFrame, "Участок канализации не выбран!"); return; }
						new FormViewDuct(sys,(Duct)ductList.getSelectedValue());
					}
				};
				viewDuctButton.addActionListener(viewDuct);
				/*
				 * ---------------------------------------------------------
				 */
				/*
				 * Событие кнопки создания канализации
				 */
				ActionListener createCable = new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
					//	if (netsComboBox.getSelectedIndex() == -1) { GUI.newError(iFrame, "Сеть не выбрана"); return; }
						new FormDuct(sys, null);
					//	GUI.setListItems(ductList, sys.duc.sortByIdUp(sys.duc.getInNet((Net)netsComboBox.getSelectedItem())));
					}
				};
				createDuctButton.addActionListener(createCable);
				/*
				 * ---------------------------------------------------------
				 */
				/*
				 * Событие кнопки удаления канализации
				 */
				ActionListener deleteDuct = new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (ductList.getSelectedIndex() == -1) { GUI.newError(iFrame, "Участок канализации не выбран!"); return; }
						int n = GUI.newDialog(iFrame, "Удалить участок канализации: " + ductList.getSelectedValue().toString());
						if (n == JOptionPane.YES_OPTION) {
							sys.removeDuct((Duct)ductList.getSelectedValue());
							GUI.newInfo(iFrame, "Участок канализации удален.");
						//	GUI.setListItems(ductList, sys.duc.sortByIdUp(sys.duc.getInNet((Net)netsComboBox.getSelectedItem())));	
						}		
					}
				};
				deleteDuctButton.addActionListener(deleteDuct);
				/*
				 * ---------------------------------------------------------
				 */
				/*
				 * Событие кнопки просмотра паспорта канализации
				 */
				ActionListener passportDuct = new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
					//	if (netsComboBox.getSelectedIndex() == -1) { GUI.newError(iFrame, "Сеть не выбрана"); return; }
					//	if (ductList.getSelectedIndex() == -1) { GUI.newError(iFrame, "Участок канализации не выбран!"); return; }
						Duct duct = (Duct)ductList.getSelectedValue();
						Building building = (Building)sys.buc.getElement(duct.getTo());
						//Manhole manhole = (Manhole)mc.getElement(duct.getTo());
						if (building != null)  { GUI.formViewPassport(sys.rw.createCableglandPassport(duct)); return;}
						Vector<Duct> v = GUI.formCreateDuctsSet((Net)sys.nc.getOnlyElement()/*(Net)netsComboBox.getSelectedItem()*/);
						if (v.size() >0) {
							GUI.formViewPassport(sys.rw.createDuctPassport(v));
						}
					}		
				};
				passportDuctButton.addActionListener(passportDuct);
				/*
				 * ---------------------------------------------------------
				 */
				
				iFrame.setVisible(true);
			}
		});
		menuChange.add(menuItem_18);
		/**
		 * Редактирование элементов "Здание"
		 */
		JMenuItem menuItem_20 = new JMenuItem("Здание");
		menuItem_20.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final JDialog iFrame = GUI.newDialog("Здания", 585, 600);
				
		//		GUI.newLabel("Сеть:", iFrame, 10, 10, 420, 14);
		//		final JComboBox netsComboBox = GUI.newNetsComboBox(iFrame, 10, 30, 420, 25);
				
				GUI.newLabel("Список зданий:", iFrame, 10, 10, 420, 14);
				final JList buildingList = GUI.newList(iFrame, 10, 30, 420, 525);
				
				//GUI.netsComboBoxLinked(netsComboBox, buildingList, sys.buc);
				
				GUI.setListItems(buildingList, sys.buc.sortByIdUp(sys.buc.getInNet((Net)sys.nc.getOnlyElement())));
				
				JButton refreshButton = GUI.newButton("Обновить", iFrame, 440, 30, 125, 26);
				JButton editBuildingButton = GUI.newButton("Редактировать", iFrame, 440, 105, 125, 26);
				JButton createBuildingButton = GUI.newButton("Добавить", iFrame, 440, 255, 125, 26);
				JButton deleteBuildingButton = GUI.newButton("Удалить", iFrame, 440, 295, 125, 26);
				
				refreshButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) { 
						GUI.setListItems(buildingList, sys.buc.sortByIdUp(sys.buc.getInNet((Net)sys.nc.getOnlyElement())));
					}
				});
				/*
				 * Событие кнопки редактирования здания
				 */
				editBuildingButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (buildingList.getSelectedIndex() == -1) { GUI.newError(iFrame, "Здание не выбрано!"); return; }
						new FormBuilding(sys, (Building)buildingList.getSelectedValue());
					//	GUI.setListItems(buildingList, sys.buc.sortByIdUp(sys.buc.getInNet((Net)netsComboBox.getSelectedItem())));	
					}
				});
				/*
				 * ---------------------------------------------------------
				 */
				/*
				 * Событие кнопки создания здания
				 */
				createBuildingButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
					//	if (netsComboBox.getSelectedIndex() == -1) { GUI.newError(iFrame, "Сеть не выбрана"); return; }
						new FormBuilding(sys, null);
					//	GUI.setListItems(buildingList, sys.buc.sortByIdUp(sys.buc.getInNet((Net)netsComboBox.getSelectedItem())));
					}
				});
				/*
				 * ---------------------------------------------------------
				 */
				/*
				 * Событие кнопки удаления здания
				 */
				ActionListener deleteBuilding = new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (buildingList.getSelectedIndex() == -1) { GUI.newError(iFrame, "Здание не выбрано!"); return; }
						int n = GUI.newDialog(iFrame, "Удалить здание?: " + buildingList.getSelectedValue().toString());
						if (n == JOptionPane.YES_OPTION) {
							sys.removeBuilding((Building)buildingList.getSelectedValue());
							GUI.newInfo(iFrame, "Здание удалено");
							GUI.setListItems(buildingList, sys.buc.sortByIdUp(sys.buc.getInNet((Net)sys.nc.getOnlyElement()/*(Net)netsComboBox.getSelectedItem()*/)));	
						}		
					}
				};
				deleteBuildingButton.addActionListener(deleteBuilding);
				/*
				 * ---------------------------------------------------------
				 */
				
				iFrame.setVisible(true);
			}
		});
		menuChange.add(menuItem_20);
		
		JSeparator separator_8 = new JSeparator();
		menuChange.add(separator_8);
		menuChange.add(menuItem_11);
		
		JMenu mnNewMenu_2 = new JMenu("Отчеты");
		menuBar.add(mnNewMenu_2);
		
		JMenuItem menuItem_fullDBoxesList = new JMenuItem("Общая статистика");
		menuItem_fullDBoxesList.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {
			new FormStatisticCommon(sys);
			}});
		mnNewMenu_2.add(menuItem_fullDBoxesList);			
	}
}
