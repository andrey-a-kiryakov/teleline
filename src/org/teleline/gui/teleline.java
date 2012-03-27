package org.teleline.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowListener;

import org.teleline.io.RW;
import org.teleline.io.Validator;
import org.teleline.model.*;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.UIManager;

import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import java.io.File;
import javax.swing.JList;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;

public class teleline {
	
	private JFrame frmTeleline;
	public IdGenerator ig;
	public gui GUI;
	public RW rw;
	public NetCollection nc;
	public DFrameCollection dfc;
	public CabinetCollection cbc; 
	public DBoxCollection dbc;
	public ManholeCollection mc;
	public DuctCollection duc;
	public BuildingCollection buc;
	public TubeCollection tuc;
	public FrameCollection fc;
	public BoxCollection bc;
	public CableCollection cc;
	public PairCollection pc;
	public PathCollection phc;
	public SubscriberCollection sc;
	public Validator V;
	
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
		
		ig = new IdGenerator();
		
		nc = new NetCollection(ig);
		dfc = new DFrameCollection(ig);
		cbc = new CabinetCollection(ig);
		dbc = new DBoxCollection(ig);
		mc = new ManholeCollection(ig);
		duc = new DuctCollection(ig);
		buc = new BuildingCollection(ig);
		tuc = new TubeCollection(ig);
		fc = new FrameCollection(ig);
		bc = new BoxCollection(ig);
		cc = new CableCollection(ig);
		pc = new PairCollection(ig);
		phc = new PathCollection(ig);
		sc = new SubscriberCollection(ig);
		V = new Validator();
		
		rw = new RW(ig,nc,dfc,cbc,dbc,mc,duc,buc,tuc,fc,bc,cc,pc,phc,sc);
		GUI = new gui(nc,dfc,cbc,dbc,mc,duc,buc,tuc,fc,bc,cc,pc,phc,sc,rw,V,frmTeleline);

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
				
				if (rw.isSaved() == false) {
					if (GUI.newDialog(frmTeleline, "Сохранить изменения в файле?") == JOptionPane.YES_OPTION) {
						if (rw.save()) {
							GUI.newInfo(frmTeleline, "Файл сохранен");
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
					
					rw.deleteNotSavedLog();
					nc.removeAllElements(); dfc.removeAllElements();
					cbc.removeAllElements(); dbc.removeAllElements();
					mc.removeAllElements(); fc.removeAllElements();
					bc.removeAllElements(); cc.removeAllElements();
					pc.removeAllElements(); sc.removeAllElements();
					phc.removeAllElements(); duc.removeAllElements();
					tuc.removeAllElements(); buc.removeAllElements();
					
					if (rw.read(chooser.getSelectedFile())) {
						GUI.newInfo(frmTeleline, "Файл прочитан!");
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
				
				if (rw.save()) {
					GUI.newInfo(frmTeleline, "Файл сохранен");
				}
				else {
					GUI.newError(frmTeleline, "Ошибка при сохранении файла");
				}
			}
		});
		menuFile.add(mntmNewMenuItem_1);
		
		final JMenu menuCreate = new JMenu("Создать");
		menuBar.add(menuCreate);
		
		JMenuItem menuItem = new JMenuItem("Сеть");
		menuItem.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent arg0) { GUI.formNet(null); }});
		menuCreate.add(menuItem);
		
		JMenuItem menuItem_1 = new JMenuItem("Кросс");
		menuItem_1.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {GUI.formDFrame(null);}});
		JSeparator separator = new JSeparator();
		menuCreate.add(separator);
		menuCreate.add(menuItem_1);
		
		JMenuItem menuItem_2 = new JMenuItem("Шкаф");
		menuItem_2.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {GUI.formCabinet(null);}});
		menuCreate.add(menuItem_2);
		
		JMenuItem menuItem_3 = new JMenuItem("Распределительную коробку");
		menuItem_3.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {GUI.formDBox(null);}});
		menuCreate.add(menuItem_3);
		
		JSeparator separator_1 = new JSeparator();
		menuCreate.add(separator_1);
		
		JMenuItem menuItem_4 = new JMenuItem("Громполосу");
		menuItem_4.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {GUI.formFrame(null,null);}});
		menuCreate.add(menuItem_4);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Бокс");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent arg0) {GUI.formBox(null,null);}});
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
				
				GUI.newLabel("Сеть:", iFrame, 20, 15, 360, 25);
				final JComboBox comboBox = GUI.newNetsComboBox(iFrame, 20, 40, 360, 25);
				
				GUI.newLabel("От кросса/громполосы:", iFrame, 20, 75, 360, 25);
				final JComboBox comboBox1 = GUI.dframeComboBox(comboBox, iFrame, 20, 100, 360, 25);
				final JComboBox comboBox2 = GUI.frameComboBox(comboBox1, iFrame, 20, 135, 360, 25);
				GUI.dframeComboBoxLinked(comboBox1, comboBox2);
				
				GUI.newLabel("До шкафа/бокса:", iFrame, 20, 170, 360, 25);
				final JComboBox comboBox3 = GUI.cabinetComboBox(comboBox, 1, iFrame, 20, 195, 360, 25);
				final JComboBox comboBox4 = GUI.boxComboBox(comboBox3, 0, iFrame, 20, 230, 360, 25);
				GUI.cabinetComboBoxLinked(comboBox3, comboBox4, 0);
				
				final JComboBox comboBox6 = GUI.cableComboBox(comboBox, comboBox1, comboBox3, 0, iFrame, 20, 440, 360, 25);
				
				GUI.netsDFrameComboLinked(comboBox, comboBox1);
				GUI.netsCabinetComboLinked(comboBox, comboBox3, 1);
				GUI.netsCableComboLinked(comboBox, comboBox1, comboBox3, comboBox6, 0);
				
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
						if (comboBox.getSelectedIndex() == -1) { GUI.newError(iFrame, "Не выбрана сеть!"); return; }
						if (comboBox1.getSelectedIndex() == -1) { GUI.newError(iFrame, "Не выбран кросс!"); return; }
						if (comboBox2.getSelectedIndex() == -1) { GUI.newError(iFrame, "Не выбрана громполоса!"); return; }
						Pair p = GUI.viewConnectedPointElement((ConnectedPointElement)comboBox2.getSelectedItem(), ((Net)comboBox.getSelectedItem()).getId(), true);
						if (p != null) dframeFrom.setText(p.getFromNumber().toString());
					}
				});
				
			    GUI.newLabel("Бокс заполнять с:", iFrame, 20, 355, 260, 25);
			    final JTextField boxFrom = GUI.newTextField(iFrame, 140, 355, 140, 25);
				boxFrom.setText("0");
				boxFrom.setEditable(false);
				JButton selectBox = GUI.newButton("Выбрать", iFrame, 290, 355, 90, 25);
				
				selectBox.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (comboBox.getSelectedIndex() == -1) { GUI.newError(iFrame, "Не выбрана сеть!"); return; }
						if (comboBox3.getSelectedIndex() == -1) { GUI.newError(iFrame, "Не выбран шкаф!"); return; }
						if (comboBox4.getSelectedIndex() == -1)	{ GUI.newError(iFrame, "Не выбран бокс!"); return; }
						Pair p = GUI.viewConnectedPointElement((ConnectedPointElement)comboBox4.getSelectedItem(), ((Net)comboBox.getSelectedItem()).getId(), true);
						if (p != null) boxFrom.setText(p.getFromNumber().toString());

					}
				});
				
				GUI.newLabel("Кабель заполнять с:", iFrame, 20, 385, 260, 25);
			    final JTextField cableFrom = GUI.newTextField(iFrame, 140, 385, 140, 25);
				cableFrom.setText("0");
				cableFrom.setEditable(false);
				JButton selectCable = GUI.newButton("Выбрать", iFrame, 290, 385, 90, 25);
				
				selectCable.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (comboBox.getSelectedIndex() == -1) { GUI.newError(iFrame, "Не выбран кабель!"); return; }
						if (comboBox6.getSelectedIndex() == -1) { GUI.newError(iFrame, "Не выбран кабель!"); return; }
						Pair p = GUI.viewCable((Cable)comboBox6.getSelectedItem(), ((Net)comboBox.getSelectedItem()).getId(), true);
						if (p != null) cableFrom.setText(p.getFromNumber().toString());

					}
				});
			   
			    
			    GUI.newLabel("Добавить в магистральный кабель:", iFrame, 20, 415, 360, 25);
				
				JButton saveButton = GUI.newButton("Сoхранить", iFrame, 20, 490, 110, 25);
				saveButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						
						if (comboBox.getSelectedIndex() == -1) { GUI.newError(iFrame, "Не выбрана сеть!"); return; }
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
						Integer fromFrame = rw.valueOf(dframeFrom.getText());
						Integer fromBox = rw.valueOf(boxFrom.getText());
						Integer fromCable = rw.valueOf(cableFrom.getText());
						
					//	if (pairCount + fromFrame > selectedFrame.getCapacity()) { GUI.newError(iFrame, "Данное количество пар не умещается в громполосе!"); return; }
					//	if (pairCount + fromBox > selectedBox.getCapacity()) { GUI.newError(iFrame, "Данное количество пар не умещается в боксе!"); return; }						
					//	if (pairCount + fromCable > selectedCable.getCapacity()) { GUI.newError(iFrame, "Данное количество пар не умещается в кабеле!"); return; }						
						
						//if (selectedCable.isConnect(pairCount) == false) { GUI.newError(iFrame, "В кабеле нет достаточного места для добавления указанного количества пар"); return; }
						
						for (Integer i = fromFrame; i < fromFrame + pairCount; i++)
							if (pc.getInPlace(selectedFrame, i) != null)  { GUI.newError(iFrame, "В громполосе в заданном диапазоне уже существуют кабельные пары!"); return; }
						
						for (Integer i = fromBox; i < fromBox + pairCount; i++)
							if (pc.getInPlace(selectedBox, i) != null)  { GUI.newError(iFrame, "В боксе в заданном диапазоне уже существуют кабельные пары!"); return; }	
						
						for (Integer i = fromCable; i < fromCable + pairCount; i++)
							if (pc.getInPlace(selectedCable, i) != null)  { GUI.newError(iFrame, "В кабеле в заданном диапазоне уже существуют кабельные пары!"); return; }	
						
						//Integer inCableFirst = selectedCable.connect(pairCount);
												
						for (int i = 0; i < pairCount; i++) {
							
							Pair newPair = new Pair(fc,bc,dbc,cc);
							
							newPair
								.attachToElementFrom(selectedFrame)
								.attachToElementTo(selectedBox)
								.attachToCable(selectedCable)
								.setNumberInCable(fromCable + i - 1)
								.setFromNumber(fromFrame + i)
								.setToNumber(fromBox + i);
								//.setType(0);
							
							pc.addElement(newPair);
							String mes = "Создана магистральная кабельная пара: "+ newPair.toString()+ ", присоединена к кроссу: "+selectedDFrame.toString()+", громполосе: "+ selectedFrame.toString() + ", присоединена к шкафу: "+selectedCabinet.toString()+", боксу: " + selectedBox.toString();
							rw.addLogMessage(mes);
							
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
				
				GUI.newLabel("Сеть:", iFrame, 20, 15, 360, 25);
				final JComboBox comboBox = GUI.newNetsComboBox(iFrame, 20, 40, 360, 25);
				
				GUI.newLabel("От шкафа/бокса (№1):", iFrame, 20, 75, 360, 25);
				final JComboBox comboBox1 = GUI.cabinetComboBox(comboBox, 0, iFrame, 20, 100, 360, 25);
				final JComboBox comboBox2 = GUI.boxComboBox(comboBox1, 1, iFrame, 20, 135, 360, 25);
				GUI.cabinetComboBoxLinked(comboBox1, comboBox2, 1);
				
				GUI.newLabel("До шкафа/бокса (№2):", iFrame, 20, 170, 360, 25);
				final JComboBox comboBox3 = GUI.cabinetComboBox(comboBox, 0, iFrame, 20, 195, 360, 25);
				final JComboBox comboBox4 = GUI.boxComboBox(comboBox3, 1, iFrame, 20, 230, 360, 25);
				GUI.cabinetComboBoxLinked(comboBox3, comboBox4, 1);
				
				final JComboBox comboBox6 = GUI.cableComboBox(comboBox, comboBox1, comboBox3, 1, iFrame, 20, 440, 360, 25);
				
				GUI.netsCabinetComboLinked(comboBox, comboBox1, 0);
				GUI.netsCabinetComboLinked(comboBox, comboBox3, 0);
				GUI.netsCableComboLinked(comboBox, comboBox1, comboBox3, comboBox6, 1);
				
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
						if (comboBox.getSelectedIndex() == -1) { GUI.newError(iFrame, "Не выбрана сеть!"); return; }
						if (comboBox1.getSelectedIndex() == -1) { GUI.newError(iFrame, "Не выбран шкаф!"); return; }
						if (comboBox2.getSelectedIndex() == -1) { GUI.newError(iFrame, "Не выбрана бокс!"); return; }
						Pair p = GUI.viewConnectedPointElement((ConnectedPointElement)comboBox2.getSelectedItem(), ((Net)comboBox.getSelectedItem()).getId(), true);
						if (p != null) box1From.setText(p.getFromNumber().toString());

					}
				});
				
			    GUI.newLabel("Бокс2 заполнять с:", iFrame, 20, 355, 360, 25);
			    final JTextField box2From = GUI.newTextField(iFrame, 140, 355, 140, 25);
			    box2From.setText("0");
			    box2From.setEditable(false);
				JButton selectBox = GUI.newButton("Выбрать", iFrame, 290, 355, 90, 25);
				
				selectBox.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (comboBox.getSelectedIndex() == -1) { GUI.newError(iFrame, "Не выбрана сеть!"); return; }
						if (comboBox3.getSelectedIndex() == -1) { GUI.newError(iFrame, "Не выбран шкаф!"); return; }
						if (comboBox4.getSelectedIndex() == -1)	{ GUI.newError(iFrame, "Не выбран бокс!"); return; }
						Pair p = GUI.viewConnectedPointElement((ConnectedPointElement)comboBox4.getSelectedItem(), ((Net)comboBox.getSelectedItem()).getId(), true);
						if (p != null) box2From.setText(p.getFromNumber().toString());

					}
				});
				
				GUI.newLabel("Кабель заполнять с:", iFrame, 20, 385, 260, 25);
			    final JTextField cableFrom = GUI.newTextField(iFrame, 140, 385, 140, 25);
				cableFrom.setText("0");
				cableFrom.setEditable(false);
				JButton selectCable = GUI.newButton("Выбрать", iFrame, 290, 385, 90, 25);
				
				selectCable.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (comboBox.getSelectedIndex() == -1) { GUI.newError(iFrame, "Не выбран кабель!"); return; }
						if (comboBox6.getSelectedIndex() == -1) { GUI.newError(iFrame, "Не выбран кабель!"); return; }
						Pair p = GUI.viewCable((Cable)comboBox6.getSelectedItem(), ((Net)comboBox.getSelectedItem()).getId(), true);
						if (p != null) cableFrom.setText(p.getFromNumber().toString());

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
						Integer fromBox1 = rw.valueOf(box1From.getText());
						Integer fromBox2 = rw.valueOf(box2From.getText());
						Integer fromCable = rw.valueOf(cableFrom.getText());
						
						//if (pairCount + fromBox1 > selectedBox1.getCapacity()) { GUI.newError(iFrame, "Данное количество пар не умещается в боксе №1!"); return; }
						//if (pairCount + fromBox2 > selectedBox2.getCapacity()) { GUI.newError(iFrame, "Данное количество пар не умещается в боксе №2!"); return; }					
						//if (selectedCable.isConnect(pairCount) == false) { GUI.newError(iFrame, "В кабеле нет достаточного места для добавления указанного количества пар"); return; }
						
						//Integer inCableFirst = selectedCable.connect(pairCount);
						
						for (Integer i = fromBox1; i < fromBox1 + pairCount; i++)
							if (pc.getInPlace(selectedBox1, i) != null)  { GUI.newError(iFrame, "В боксе №1 в заданном диапазоне уже существуют кабельные пары!"); return; }
						
						for (Integer i = fromBox2; i < fromBox2 + pairCount; i++)
							if (pc.getInPlace(selectedBox2, i) != null)  { GUI.newError(iFrame, "В боксе №2 в заданном диапазоне уже существуют кабельные пары!"); return; }
						
						for (Integer i = fromCable; i < fromCable + pairCount; i++)
							if (pc.getInPlace(selectedCable, i) != null)  { GUI.newError(iFrame, "В кабеле в заданном диапазоне уже существуют кабельные пары!"); return; }	
												
						for (int i = 0; i < pairCount; i++) {
							
							Pair newPair = new Pair(fc,bc,dbc,cc);
							
							newPair
								.attachToElementFrom(selectedBox1)
								.attachToElementTo(selectedBox2)
								.attachToCable(selectedCable)
								.setNumberInCable(fromCable + i - 1)
								.setFromNumber(fromBox1 + i)
								.setToNumber(fromBox2 + i);
								//.setType(1);
							
							pc.addElement(newPair);
							String mes = "Создана передаточная кабельная пара: "+ newPair.toString()+ ", присоединена к шкафу: "+selectedCabinet1.toString()+", боксу: "+ selectedBox1.toString() + ", присоединена к шкафу: "+selectedCabinet2.toString()+", боксу: " + selectedBox2.toString();
							rw.addLogMessage(mes);
							
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
				final JDialog iFrame = GUI.newDialog("Создать распределительные пары", 410, 500);
				
				GUI.newLabel("Сеть:", iFrame, 20, 15, 360, 25);
				final JComboBox comboBox = GUI.newNetsComboBox(iFrame, 20, 40, 360, 25);
				
				GUI.newLabel("От шкафа/бокса:", iFrame, 20, 75, 360, 25);
				final JComboBox comboBox1 = GUI.cabinetComboBox(comboBox, 0, iFrame, 20, 100, 360, 25);
				final JComboBox comboBox2 = GUI.boxComboBox(comboBox1, 2, iFrame, 20, 135, 360, 25);
				GUI.cabinetComboBoxLinked(comboBox1, comboBox2, 2);
				
				GUI.newLabel("До коробки:", iFrame, 20, 170, 360, 25);
				final JComboBox comboBox3 = GUI.dboxComboBox(comboBox, iFrame, 20, 195, 360, 25);
				
				final JComboBox comboBox6 = GUI.cableComboBox(comboBox, comboBox1, comboBox3, 2, iFrame, 20, 380, 360, 25);
				
				GUI.netsCabinetComboLinked(comboBox, comboBox1, 0);
				GUI.netsDBoxComboLinked(comboBox, comboBox3);
				GUI.netsCableComboLinked(comboBox, comboBox1, comboBox3, comboBox6, 2);
		   	
		        GUI.newLabel("Количество создаваемых пар:", iFrame, 20, 230, 360, 25);
				
				final JComboBox comboBox5 = new JComboBox();
				comboBox5.addItem((Integer)10);
				comboBox5.setSelectedIndex(0);
				comboBox5.setBounds(20, 255, 360, 25);
				iFrame.getContentPane().add(comboBox5);
				
				 GUI.newLabel("Бокс заполнять с:", iFrame, 20, 290, 260, 25);
				    final JTextField boxFrom = GUI.newTextField(iFrame, 140, 290, 140, 25);
					boxFrom.setText("0");
					boxFrom.setEditable(false);
					JButton selectBox = GUI.newButton("Выбрать", iFrame, 290, 290, 90, 25);
					
					selectBox.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							if (comboBox.getSelectedIndex() == -1) { GUI.newError(iFrame, "Не выбрана сеть!"); return; }
							if (comboBox1.getSelectedIndex() == -1) { GUI.newError(iFrame, "Не выбран шкаф!"); return; }
							if (comboBox2.getSelectedIndex() == -1)	{ GUI.newError(iFrame, "Не выбран бокс!"); return; }
							Pair p = GUI.viewConnectedPointElement((ConnectedPointElement)comboBox2.getSelectedItem(), ((Net)comboBox.getSelectedItem()).getId(), true);
							if (p != null) boxFrom.setText(p.getFromNumber().toString());
						}
					});
				
					GUI.newLabel("Кабель заполнять с:", iFrame, 20, 320, 260, 25);
				    final JTextField cableFrom = GUI.newTextField(iFrame, 140, 320, 140, 25);
					cableFrom.setText("0");
					cableFrom.setEditable(false);
					JButton selectCable = GUI.newButton("Выбрать", iFrame, 290, 320, 90, 25);
					
					selectCable.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							if (comboBox.getSelectedIndex() == -1) { GUI.newError(iFrame, "Не выбран кабель!"); return; }
							if (comboBox6.getSelectedIndex() == -1) { GUI.newError(iFrame, "Не выбран кабель!"); return; }
							Pair p = GUI.viewCable((Cable)comboBox6.getSelectedItem(), ((Net)comboBox.getSelectedItem()).getId(), true);
							if (p != null) cableFrom.setText(p.getFromNumber().toString());

						}
					});
				
				
			    GUI.newLabel("Добавить в распределительный кабель:", iFrame, 20, 355, 360, 25);
				
				JButton saveButton = GUI.newButton("Сoхранить", iFrame, 20, 420, 110, 25);
				saveButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						
						if (comboBox1.getSelectedIndex() == -1) { GUI.newError(iFrame, "Не выбран шкаф!"); return; }
						if (comboBox2.getSelectedIndex() == -1) { GUI.newError(iFrame, "Не выбран бокс!"); return; }
						if (comboBox3.getSelectedIndex() == -1)	{ GUI.newError(iFrame, "Не выбрана коробка!"); return; }
						if (comboBox6.getSelectedIndex() == -1)	{ GUI.newError(iFrame, "Не выбран кабель!"); return; }
						
						Cabinet selectedCabinet = (Cabinet)comboBox1.getSelectedItem();
						Box selectedBox = (Box)comboBox2.getSelectedItem();
						DBox selectedDBox = (DBox)comboBox3.getSelectedItem();
						Cable selectedCable = (Cable)comboBox6.getSelectedItem();
						Integer pairCount = (Integer)comboBox5.getSelectedItem();
						Integer fromBox = rw.valueOf(boxFrom.getText());
						Integer fromCable = rw.valueOf(cableFrom.getText());

					//	if (pairCount + fromBox > selectedBox.getCapacity()) { GUI.newError(iFrame, "Данное количество пар не умещается в боксе!"); return; }
					//	if (pairCount + 0 > selectedDBox.getCapacity()) { GUI.newError(iFrame, "Данное количество пар не умещается в КРТ"); return; }					
					//	if (selectedCable.isConnect(pairCount) == false) { GUI.newError(iFrame, "В кабеле нет достаточного места для добавления указанного количества пар"); return; }
						
					//	Integer inCableFirst = selectedCable.connect(pairCount);
						
						for (Integer i = fromBox; i < fromBox + pairCount; i++)
							if (pc.getInPlace(selectedBox, i) != null)  { GUI.newError(iFrame, "В боксе в заданном диапазоне уже существуют кабельные пары!"); return; }
						
						for (Integer i = 0; i < 0 + pairCount; i++)
							if (pc.getInPlace(selectedDBox, i) != null)  { GUI.newError(iFrame, "В КРТ в заданном диапазоне уже существуют кабельные пары!"); return; }				
						
						for (Integer i = fromCable; i < fromCable + pairCount; i++)
							if (pc.getInPlace(selectedCable, i) != null)  { GUI.newError(iFrame, "В кабеле в заданном диапазоне уже существуют кабельные пары!"); return; }	
						
						for (int i = 0; i < pairCount; i++) {
							
							Pair newPair = new Pair(fc,bc,dbc,cc);
							
							newPair
								.attachToElementFrom(selectedBox)
								.attachToElementTo(selectedDBox.getId())
								.attachToCable(selectedCable)
								.setNumberInCable(fromCable + i - 1)
								.setFromNumber(fromBox + i)
								.setToNumber(0 + i);
								//.setType(2);
							
							pc.addElement(newPair);
							String mes = "Создана распределительная пара: "+ newPair.toString()+ ", присоединена к шкафу: "+selectedCabinet.toString()+", боксу: "+ selectedBox.toString() + ", присоединена к коробке: " + selectedDBox.toString();
							rw.addLogMessage(mes);
						}
						String mes = "Создано " + pairCount.toString() + " кабельных пар, присоединены к шкафу: "+selectedCabinet.toString()+", боксу: "+ selectedBox.toString() + ", присоединены к коробке: " + selectedDBox.toString();
						GUI.newInfo(iFrame, mes);
						iFrame.dispose();
					}
				});
					
				iFrame.setVisible(true);
			}
		});
		menu_2.add(menuItem_8);
		/**
		 * Создание пар прямого питания
		 */
		JMenuItem menuItem_5 = new JMenuItem("Прямого питания");
		menuItem_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				final JDialog iFrame = GUI.newDialog("Создать пары прямого питания", 410, 500);
				
				GUI.newLabel("Сеть:", iFrame, 20, 15, 360, 25);
				final JComboBox comboBox = GUI.newNetsComboBox(iFrame, 20, 40, 360, 25);
				
				GUI.newLabel("От кросса/громполосы:", iFrame, 20, 75, 360, 25);
				final JComboBox comboBox1 = GUI.dframeComboBox(comboBox, iFrame, 20, 100, 360, 25);
				final JComboBox comboBox2 = GUI.frameComboBox(comboBox1, iFrame, 20, 135, 360, 25);
				GUI.dframeComboBoxLinked(comboBox1, comboBox2);
				
				GUI.newLabel("До коробки:", iFrame, 20, 170, 360, 25);
				final JComboBox comboBox3 = GUI.dboxComboBox(comboBox, iFrame, 20, 195, 360, 25);
				
				final JComboBox comboBox6 = GUI.cableComboBox(comboBox, comboBox1, comboBox3, 3, iFrame, 20, 380, 360, 25);
				
				GUI.netsDFrameComboLinked(comboBox, comboBox1);
				GUI.netsDBoxComboLinked(comboBox, comboBox3);
				GUI.netsCableComboLinked(comboBox, comboBox1, comboBox3, comboBox6, 3);
				
		        GUI.newLabel("Количество создаваемых пар:", iFrame, 20, 230, 360, 25);
				
				final JComboBox comboBox5 = new JComboBox();
				comboBox5.addItem((Integer)10);
				comboBox5.setSelectedIndex(0);
				comboBox5.setBounds(20, 255, 360, 25);
				iFrame.getContentPane().add(comboBox5);
				
				GUI.newLabel("ГП заполнять с:", iFrame, 20, 290, 360, 25);
				final JTextField dframeFrom = GUI.newTextField(iFrame, 140, 290, 140, 25);
				dframeFrom.setText("0");
				dframeFrom.setEditable(false);
				JButton selectFrom = GUI.newButton("Выбрать", iFrame, 290, 290, 90, 25);
				
				selectFrom.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (comboBox.getSelectedIndex() == -1) { GUI.newError(iFrame, "Не выбрана сеть!"); return; }
						if (comboBox1.getSelectedIndex() == -1) { GUI.newError(iFrame, "Не выбран кросс!"); return; }
						if (comboBox2.getSelectedIndex() == -1) { GUI.newError(iFrame, "Не выбрана громполоса!"); return; }
						Pair p = GUI.viewConnectedPointElement((ConnectedPointElement)comboBox2.getSelectedItem(), ((Net)comboBox.getSelectedItem()).getId(), true);
						if (p != null) dframeFrom.setText(p.getFromNumber().toString());

					}
				});
				
				GUI.newLabel("Кабель заполнять с:", iFrame, 20, 320, 260, 25);
			    final JTextField cableFrom = GUI.newTextField(iFrame, 140, 320, 140, 25);
				cableFrom.setText("0");
				cableFrom.setEditable(false);
				JButton selectCable = GUI.newButton("Выбрать", iFrame, 290, 320, 90, 25);
				
				selectCable.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (comboBox.getSelectedIndex() == -1) { GUI.newError(iFrame, "Не выбран кабель!"); return; }
						if (comboBox6.getSelectedIndex() == -1) { GUI.newError(iFrame, "Не выбран кабель!"); return; }
						Pair p = GUI.viewCable((Cable)comboBox6.getSelectedItem(), ((Net)comboBox.getSelectedItem()).getId(), true);
						if (p != null) cableFrom.setText(p.getFromNumber().toString());

					}
				});
			    
			    GUI.newLabel("Добавить в кабель:", iFrame, 20, 355, 360, 25);
			    JButton saveButton = GUI.newButton("Сoхранить", iFrame, 20, 420, 110, 25);
			    saveButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						
						if (comboBox1.getSelectedIndex() == -1) { GUI.newError(iFrame, "Не выбран кросс!"); return; }
						if (comboBox2.getSelectedIndex() == -1) { GUI.newError(iFrame, "Не выбрана громполоса!"); return; }
						if (comboBox3.getSelectedIndex() == -1)	{ GUI.newError(iFrame, "Не выбрана коробка!"); return; }
						if (comboBox6.getSelectedIndex() == -1)	{ GUI.newError(iFrame, "Не выбран кабель!"); return; }
						
						DFramе selectedDFrame = (DFramе)comboBox1.getSelectedItem();
						Frame selectedFrame = (Frame)comboBox2.getSelectedItem();
						DBox selectedDBox = (DBox)comboBox3.getSelectedItem();
						Cable selectedCable = (Cable)comboBox6.getSelectedItem();
						Integer pairCount = (Integer)comboBox5.getSelectedItem();
						Integer fromFrame = rw.valueOf(dframeFrom.getText());
						Integer fromCable = rw.valueOf(cableFrom.getText());
						
						
					//	if (pairCount + fromFrame > selectedFrame.getCapacity()) { GUI.newError(iFrame, "Данное количество пар не умещается в громполосе!"); return; }
					//	if (pairCount + 0 > selectedDBox.getCapacity()) { GUI.newError(iFrame, "Данное количество пар не умещается в КРТ"); return; }					
					//	if (selectedCable.isConnect(pairCount) == false) { GUI.newError(iFrame, "В кабеле нет достаточного места для добавления указанного количества пар"); return; }
						
						
						for (Integer i = fromFrame; i < fromFrame + pairCount; i++)
							if (pc.getInPlace(selectedFrame, i) != null)  { GUI.newError(iFrame, "В громполосе в заданном диапазоне уже существуют кабельные пары!"); return; }
						
						for (Integer i = 0; i < 0 + pairCount; i++)
							if (pc.getInPlace(selectedDBox, i) != null)  { GUI.newError(iFrame, "В КРТ в заданном диапазоне уже существуют кабельные пары!"); return; }				
						
						for (Integer i = fromCable; i < fromCable + pairCount; i++)
							if (pc.getInPlace(selectedCable, i) != null)  { GUI.newError(iFrame, "В кабеле в заданном диапазоне уже существуют кабельные пары!"); return; }	
						
						//Integer inCableFirst = selectedCable.connect(pairCount);
						
						for (int i = 0; i < pairCount; i++) {
							
							Pair newPair = new Pair(fc,bc,dbc,cc);
							
							newPair
								.attachToElementFrom(selectedFrame)
								.attachToElementTo(selectedDBox.getId())
								.attachToCable(selectedCable)
								.setNumberInCable(fromCable + i - 1)
								.setFromNumber(fromFrame + i)
								.setToNumber(0 + i);
							//	.setType(3);
							
							pc.addElement(newPair);
							String mes = "Создана кабельная пара прямого питания: "+ newPair.toString()+ ", присоединена к кроссу: "+selectedDFrame.toString()+", громполосе: "+ selectedFrame.toString() + ", присоединена к коробке: " + selectedDBox.toString();
							rw.addLogMessage(mes);
							
						}
						String mes = "Создано " + pairCount.toString() + " кабельных пар, присоединены к кроссу: "+selectedDFrame.toString()+", громполосе: "+ selectedFrame.toString() + ", присоединены к коробке: " + selectedDBox.toString();
						GUI.newInfo(iFrame, mes);
						iFrame.dispose();
					
					}
				});
						
				iFrame.setVisible(true);
				
			}
		});
		menu_2.add(menuItem_5);
		
		JMenuItem menuItem_9 = new JMenuItem("Кабель");
		menuItem_9.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {GUI.formCable(null);}});
		menuCreate.add(menuItem_9);
		
		JSeparator separator_3 = new JSeparator();
		menuCreate.add(separator_3);
		
		JMenuItem menuItem_10 = new JMenuItem("Колодец");
		menuItem_10.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent arg0) {GUI.formManhole(null);}});
		menuCreate.add(menuItem_10);
		
		JMenuItem mntmNewMenuItem_4 = new JMenuItem("Канализацию");
		mntmNewMenuItem_4.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent arg0) { GUI.formDuct(null);}});
		menuCreate.add(mntmNewMenuItem_4);
		
		JMenuItem menuItem_19 = new JMenuItem("Здание");
		menuItem_19.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent arg0) {GUI.formBuilding(null);}});
		menuCreate.add(menuItem_19);
		
		JSeparator separator_4 = new JSeparator();
		menuCreate.add(separator_4);
		
		JMenuItem menuItem_15 = new JMenuItem("Абонента");
		menuItem_15.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent arg0) { GUI.formSubscriber(null); }});
		menuCreate.add(menuItem_15);
		
		JMenu menuChange = new JMenu("Смотреть");
		menuBar.add(menuChange);
		/**
		 * Редактирование элементов "Сеть"
		 */
		JMenuItem menuItem_14 = new JMenuItem("Сеть");
		menuItem_14.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				final JDialog iFrame = GUI.newDialog("Редактировать сеть", 585, 600);
				
				
				GUI.newLabel("Список сетей:", iFrame, 10, 10, 420, 14);
				final JList netList = GUI.newList(iFrame, 10, 30, 420, 520);
				GUI.setListItems(netList, nc.sortByIdUp(nc.elements()));
				
				JButton editNetButton = GUI.newButton("Редактировать", iFrame, 440, 30, 125, 26);
			//	JButton mapNetButton = GUI.newButton("Карта", iFrame, 340, 75, 125, 26);
				
				JButton createNetButton = GUI.newButton("Добавить", iFrame, 440, 180, 125, 26);
				JButton deleteNetButton = GUI.newButton("Удалить", iFrame, 440, 225, 125, 26);
				
				/*
				 * Событие кнопки редактирования сети
				 */
				ActionListener editDFrame = new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (netList.getSelectedIndex() == -1) { GUI.newError(iFrame, "Сеть не выбрана!"); return; }
						
						GUI.formNet((Net)netList.getSelectedValue());
						GUI.setListItems(netList, nc.sortByIdUp(nc.elements()));
						
					}
				};
				editNetButton.addActionListener(editDFrame);
				/*
				 * ---------------------------------------------------------
				 */
				/*
				 * Событие кнопки создания сети
				 */
				ActionListener createNet = new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						GUI.formNet(null);
						GUI.setListItems(netList, nc.sortByIdUp(nc.elements()));
					}
				};
				createNetButton.addActionListener(createNet);
				/*
				 * ---------------------------------------------------------
				 */
				/*
				 * Событие кнопки удаления сети
				 */
				ActionListener deleteNet = new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (netList.getSelectedIndex() == -1) { GUI.newError(iFrame, "Сеть не выбрана!"); return; }	
						int n = GUI.newDialog(iFrame, "Удалить сеть: " + (Net)netList.getSelectedValue()+" и все её содержимое?");
						if (n == JOptionPane.YES_OPTION) {
							GUI.removeNet((Net)netList.getSelectedValue());
							GUI.setListItems(netList, nc.sortByIdUp(nc.elements()));
						}
					}
				};
				deleteNetButton.addActionListener(deleteNet);
				/*
				 * ---------------------------------------------------------
				 */
				
				/*
				 * Событие кнопки просмотра карты
				 */
			/*	ActionListener createMap = new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (netList.getSelectedIndex() == -1) { GUI.newError(iFrame, "Сеть не выбрана!"); return; }
						//GUI.formMap();
					}
				};
				mapNetButton.addActionListener(createMap);
			*/	/*
				 * ---------------------------------------------------------
				 */
				
				iFrame.setVisible(true);	
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
				
				GUI.newLabel("Сеть:", iFrame, 10, 10, 420, 14);
				final JComboBox netsComboBox = GUI.newNetsComboBox(iFrame, 10, 30, 420, 25);
				
				GUI.newLabel("Список кроссов:", iFrame, 10, 65, 420, 14);
				final JList dframeList = GUI.newList(iFrame, 10, 85, 420, 470);
				
				GUI.netsComboBoxLinked(netsComboBox, dframeList, dfc);
				
				JButton editDFrameButton = GUI.newButton("Редактировать", iFrame, 440, 85, 125, 26);
				JButton viewDFrameButton = GUI.newButton("Смотреть", iFrame, 440, 125, 125, 26);
				JButton passportDFrameButton = GUI.newButton("Паспорт", iFrame, 440, 165, 125, 26);
				JButton createDFrameButton = GUI.newButton("Добавить", iFrame, 440, 235, 125, 26);
				JButton deleteDFrameButton = GUI.newButton("Удалить", iFrame, 440, 275, 125, 26);
				
				/*
				 * Событие кнопки редактирования кросса
				 */
				ActionListener editDFrame = new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (dframeList.getSelectedIndex() == -1) { GUI.newError(iFrame, "Кросс не выбран!"); return; }
						
						GUI.formDFrame((DFramе)dframeList.getSelectedValue());
						GUI.setListItems(dframeList, dfc.sortByNumberUp(dfc.getInNet((Net)netsComboBox.getSelectedItem())));
						
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
						if (dframeList.getSelectedIndex() == -1) { GUI.newError(iFrame, "Кросс не выбран!"); return; }
						GUI.viewDFrame((DFramе)dframeList.getSelectedValue());
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
						if (netsComboBox.getSelectedIndex() == -1) { GUI.newError(iFrame, "Сеть не выбрана!"); return; }
						GUI.formDFrame(null);
						GUI.setListItems(dframeList, dfc.sortByNumberUp(dfc.getInNet((Net)netsComboBox.getSelectedItem())));
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
							GUI.removeDFrame((DFramе)dframeList.getSelectedValue());
							GUI.newInfo(iFrame, "Кросс и все его содержимое удалены");
							GUI.setListItems(dframeList, dfc.sortByNumberUp(dfc.getInNet((Net)netsComboBox.getSelectedItem())));	
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
						if (dframeList.getSelectedIndex() == -1) { GUI.newError(iFrame, "Кросс не выбран!"); return; }
						
						GUI.formViewPassport(rw.createDFramePassport((DFramе)dframeList.getSelectedValue()));
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
				
				final JDialog iFrame = GUI.newDialog("Редактировать шкаф", 585, 600);
				
				GUI.newLabel("Сеть:", iFrame, 10, 10, 420, 14);
				final JComboBox netsComboBox = GUI.newNetsComboBox(iFrame, 10, 30, 420, 25);
				
				GUI.newLabel("Список шкафов:", iFrame, 10, 65, 420, 14);
				final JList cabinetList = GUI.newList(iFrame, 10, 85, 420, 470);
				
				GUI.netsComboBoxLinked(netsComboBox, cabinetList, cbc);
				
				JButton editCabinetButton = GUI.newButton("Редактировать", iFrame, 440, 85, 125, 26);
				JButton viewCabinetButton = GUI.newButton("Смотреть", iFrame, 440, 125, 125, 26);
				JButton passportCabinetButton = GUI.newButton("Паспорт", iFrame, 440, 165, 125, 26);
				JButton createCabinetButton = GUI.newButton("Добавить", iFrame, 440, 235, 125, 26);
				JButton deleteCabinetButton = GUI.newButton("Удалить", iFrame, 440, 275, 125, 26);
				
				/*
				 * Событие кнопки редактирования шкафа
				 */
				ActionListener editCabinet = new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						Integer item =cabinetList.getSelectedIndex(); 
						if (item == -1) { GUI.newError(iFrame, "Шкаф не выбран!"); return; }
						
						GUI.formCabinet((Cabinet)cabinetList.getSelectedValue());
						GUI.setListItems(cabinetList, cbc.sortByIdUp(cbc.getInNet((Net)netsComboBox.getSelectedItem())));
						cabinetList.setSelectedIndex(item);
						
					}
				};
				editCabinetButton.addActionListener(editCabinet);
				/*
				 * ---------------------------------------------------------
				 */
				/*
				 * Событие кнопки просмотра шкафа
				 */
				ActionListener viewCabinet = new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (cabinetList.getSelectedIndex() == -1) { GUI.newError(iFrame, "Шкаф не выбран!"); return; }
						GUI.viewCabinet((Cabinet)cabinetList.getSelectedValue());
					}
				};
				viewCabinetButton.addActionListener(viewCabinet);
				/*
				 * ---------------------------------------------------------
				 */
				/*
				 * Событие кнопки создания шкафа
				 */
				ActionListener createCabinet = new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (netsComboBox.getSelectedIndex() == -1) { GUI.newError(iFrame, "Сеть не выбрана!"); return; }
						GUI.formCabinet(null);
						GUI.setListItems(cabinetList, cbc.sortByIdUp(cbc.getInNet((Net)netsComboBox.getSelectedItem())));
					}
				};
				createCabinetButton.addActionListener(createCabinet);
				/*
				 * ---------------------------------------------------------
				 */
				/*
				 * Событие кнопки удаления шкафа
				 */
				ActionListener deleteCabinet = new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (cabinetList.getSelectedIndex() == -1) { GUI.newError(iFrame, "Шкаф не выбран!"); return; }
						int n = GUI.newDialog(iFrame, "Удалить " + cabinetList.getSelectedValue().toString()+" и все его содержимое?");
						if (n == JOptionPane.YES_OPTION) {
							GUI.removeCabinet((Cabinet)cabinetList.getSelectedValue());
							GUI.newInfo(iFrame, "Шкаф и все его содержимое удалены");
							GUI.setListItems(cabinetList, cbc.sortByIdUp(cbc.getInNet((Net)netsComboBox.getSelectedItem())));	
						}		
					}
				};
				deleteCabinetButton.addActionListener(deleteCabinet);
				/*
				 * ---------------------------------------------------------
				 */
				/*
				 * Событие кнопки просмотра паспорта шкафа
				 */
				ActionListener passportCabinet = new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (cabinetList.getSelectedIndex() == -1) { GUI.newError(iFrame, "Шкаф не выбран!"); return; }
						
						GUI.formViewPassport(rw.createCabinetPassport((Cabinet)cabinetList.getSelectedValue()));
					}		
				};
				passportCabinetButton.addActionListener(passportCabinet);
				/*
				 * ---------------------------------------------------------
				 */
				iFrame.setVisible(true);	
			}
		});
		
		
		/**
		 * Редактирование элементов "Распределительная коробка"
		 */
		JMenuItem menuItem_13 = new JMenuItem("Распределительную коробку");
		menuChange.add(menuItem_13);
		menuItem_13.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				final JDialog iFrame = GUI.newDialog("Редактировать КРТ", 585, 600);
				
				GUI.newLabel("Сеть:", iFrame, 10, 10, 420, 14);
				final JComboBox netsComboBox = GUI.newNetsComboBox(iFrame, 10, 30, 420, 25);
				
				GUI.newLabel("Список КРТ:", iFrame, 10, 65, 420, 14);		
				final JList dboxList = GUI.newList(iFrame, 10, 85, 420, 470);
				
				GUI.netsComboBoxLinked(netsComboBox, dboxList, dbc);
				
				JButton editDBoxButton = GUI.newButton("Редактировать", iFrame, 440, 85, 125, 26);
				JButton viewDBoxButton = GUI.newButton("Смотреть", iFrame, 440, 125, 125, 26);
				JButton passportDBoxButton = GUI.newButton("Адр. лист", iFrame, 440, 165, 125, 26);
				JButton createDBoxButton = GUI.newButton("Добавить", iFrame, 440, 235, 125, 26);
				JButton deleteDBoxButton = GUI.newButton("Удалить", iFrame, 440, 275, 125, 26);
				
				/*
				 * Событие кнопки редактирования КРТ
				 */
				ActionListener editDBox = new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (dboxList.getSelectedIndex() == -1) { GUI.newError(iFrame, "Коробка не выбрана!"); return; }
						
						GUI.formDBox((DBox)dboxList.getSelectedValue());
						GUI.setListItems(dboxList, dbc.sortByIdUp(dbc.getInNet((Net)netsComboBox.getSelectedItem())));
						
					}
				};
				editDBoxButton.addActionListener(editDBox);
				/*
				 * ---------------------------------------------------------
				 */
				/*
				 * Событие кнопки просмотра КРТ
				 */
				ActionListener viewDBox = new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (dboxList.getSelectedIndex() == -1) { GUI.newError(iFrame, "Коробка не выбрана!"); return; }
						GUI.viewDBox((DBox)dboxList.getSelectedValue(), ((Net)netsComboBox.getSelectedItem()).getId());
					}
				};
				viewDBoxButton.addActionListener(viewDBox);
				/*
				 * ---------------------------------------------------------
				 */
				/*
				 * Событие кнопки создания КРТ
				 */
				ActionListener createDBox = new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (netsComboBox.getSelectedIndex() == -1) { GUI.newError(iFrame, "Сеть не выбрана!"); return; }
						GUI.formDBox(null);
						GUI.setListItems(dboxList, dbc.sortByIdUp(dbc.getInNet((Net)netsComboBox.getSelectedItem())));
					}
				};
				createDBoxButton.addActionListener(createDBox);
				/*
				 * ---------------------------------------------------------
				 */
				/*
				 * Событие кнопки удаления КРТ
				 */
				ActionListener deleteDBox = new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (dboxList.getSelectedIndex() == -1) { GUI.newError(iFrame, "Коробка не выбрана!"); return; }
						int n = GUI.newDialog(iFrame, "Удалить " + dboxList.getSelectedValue().toString()+" и все ее содержимое?");
						if (n == JOptionPane.YES_OPTION) {
							GUI.removeDBox((DBox)dboxList.getSelectedValue());
							GUI.newInfo(iFrame, "Коробка и все ее содержимое удалены");
							GUI.setListItems(dboxList, dbc.sortByIdUp(dbc.getInNet((Net)netsComboBox.getSelectedItem())));	
						}		
					}
				};
				deleteDBoxButton.addActionListener(deleteDBox);
				/*
				 * ---------------------------------------------------------
				 */
				/*
				 * Событие кнопки просмотра паспорта КРТ
				 */
				ActionListener passportDBox = new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
					//	if (dboxList.getSelectedIndex() == -1) { GUI.newError(iFrame, "Коробка не выбрана!"); return; }
						if (netsComboBox.getSelectedIndex() == -1) { GUI.newError(iFrame, "Сеть не выбрана!"); return; }
						
						GUI.formViewPassport(rw.createDBoxPassport((Net)netsComboBox.getSelectedItem()));
					}		
				};
				passportDBoxButton.addActionListener(passportDBox);
				/*
				 * ---------------------------------------------------------
				 */
				iFrame.setVisible(true);	
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
				
				final JDialog iFrame = GUI.newDialog("Редактировать абонента", 585, 700);
				
				GUI.newLabel("Сеть:", iFrame, 10, 10, 420, 14);
				final JComboBox netsComboBox = GUI.newNetsComboBox(iFrame, 10, 30, 420, 25);
				
				GUI.newLabel("Список абонентов:", iFrame, 10, 65, 420, 14);
				//final JList subscriberList = GUI.newList(iFrame, 10, 85, 420, 280);
				final JTable subscriberList = GUI.newTable(iFrame, 10, 85, 420, 280);
				final DefaultTableModel tableModel = (DefaultTableModel) subscriberList.getModel();
				tableModel.setColumnIdentifiers(new String[]{"Имя", "Телефон","Адрес"});
				
				JButton editSubscriberButton = GUI.newButton("Редактировать", iFrame, 440, 85, 125, 26);
				JButton passportSubscriberButton = GUI.newButton("Карточка", iFrame, 440, 125, 125, 26);
				JButton createSubscriberButton = GUI.newButton("Добавить", iFrame, 440, 195, 125, 26);
				JButton deletSubscribereButton = GUI.newButton("Удалить", iFrame, 440, 235, 125, 26);
				//GUI.netsComboBoxLinked(netsComboBox, subscriberList, sc);
				GUI.linkNetsComboBoxSubscriberTable(netsComboBox, subscriberList);
				
				GUI.newLabel("Список включений:", iFrame, 10, 380, 420, 14);
				final JList pathList = GUI.newList(iFrame, 10, 400, 420, 110);
				JButton addPathButton = GUI.newButton("Добавить", iFrame, 440, 400, 125, 26);
				JButton editPathButton = GUI.newButton("Редактировать", iFrame, 440, 440, 125, 26);		
				JButton deletePathButton = GUI.newButton("Удалить", iFrame, 440, 480, 125, 26);
				
				GUI.newLabel("Занимаемые пары:", iFrame, 10, 525, 420, 14);
				final JList pairList = GUI.newList(iFrame, 10, 545, 420, 110);
				JButton addPairButton = GUI.newButton("Добавить", iFrame, 440, 545, 125, 26);
				JButton deletePairButton = GUI.newButton("Удалить", iFrame, 440, 585, 125, 26);
				
				/*
				 * Событие выбора абонента в таблице
				 */
				ListSelectionListener subscriberSelect = new ListSelectionListener(){
					public void valueChanged(ListSelectionEvent e) {
					//	if (subscriberList.getSelectedIndex() != -1) 
					//		GUI.setListItems(pathList, phc.sortByIdUp(phc.getSubscriberPaths((Subscriber)subscriberList.getSelectedValue())));
						if (!subscriberList.getSelectionModel().isSelectionEmpty()){
							int selectedIndex = subscriberList.getRowSorter().convertRowIndexToModel(subscriberList.getSelectionModel().getMinSelectionIndex());
							Subscriber subscriber = (Subscriber)tableModel.getValueAt(selectedIndex, 0);
							GUI.setListItems(pathList, phc.sortByIdUp(phc.getSubscriberPaths(subscriber)));
						}
					}
				};
				subscriberList.getSelectionModel().addListSelectionListener(subscriberSelect);
				//subscriberList.addListSelectionListener(subscriberSelect);
				/*
				 * ---------------------------------------------------------
				 */
				/*
				 * Событие редактирования абонента
				 */
				ActionListener editSubscriber = new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
					//	if (subscriberList.getSelectedIndex() == -1) { GUI.newError(iFrame, "Абонент не выбран!"); return; }
					//	Subscriber sub = (Subscriber)subscriberList.getSelectedValue();
					//	GUI.formSubscriber(sub);
					//	GUI.setListItems(subscriberList, sc.sortByIdUp(sc.getInNet(((Net)netsComboBox.getSelectedItem()).getId())));
					//	subscriberList.setSelectedValue(sub, true);
						if (subscriberList.getSelectionModel().isSelectionEmpty()){ GUI.newError(iFrame, "Абонент не выбран!"); return; }
						int selectedIndex = subscriberList.getRowSorter().convertRowIndexToModel(subscriberList.getSelectionModel().getMinSelectionIndex());
						Subscriber subscriber = (Subscriber)tableModel.getValueAt(selectedIndex, 0);
						GUI.formSubscriber(subscriber);
						GUI.updateSubscriberInTable(subscriberList, subscriber, selectedIndex);
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
						//if (subscriberList.getSelectedIndex() == -1) { GUI.newError(iFrame, "Абонент не выбран!"); return; }
						//GUI.formViewPassport(rw.createSubscriberPassport((Subscriber)subscriberList.getSelectedValue()));
						if (subscriberList.getSelectionModel().isSelectionEmpty()){ GUI.newError(iFrame, "Абонент не выбран!"); return; }
						int selectedIndex = subscriberList.getRowSorter().convertRowIndexToModel(subscriberList.getSelectionModel().getMinSelectionIndex());
						Subscriber subscriber = (Subscriber)tableModel.getValueAt(selectedIndex, 0);
						GUI.formViewPassport(rw.createSubscriberPassport(subscriber));
						
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
						if (netsComboBox.getSelectedIndex() == -1) { GUI.newError(iFrame, "Сеть не выбрана"); return; }
						Subscriber subscriber = GUI.formSubscriber(null);
						if (subscriber != null) GUI.addSubscriberToTable(subscriberList, subscriber);	
						//GUI.formSubscriber(null);
							
						//	((DefaultListModel)subscriberList.getModel()).addElement(obj)
						//	GUI.setListItems(subscriberList, sc.sortByIdUp(sc.getInNet(((Net)netsComboBox.getSelectedItem()).getId())));
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
						if (subscriberList.getSelectionModel().isSelectionEmpty()){ GUI.newError(iFrame, "Абонент не выбран!"); return; }
						int selectedIndex = subscriberList.getRowSorter().convertRowIndexToModel(subscriberList.getSelectionModel().getMinSelectionIndex());
						Subscriber subscriber = (Subscriber)tableModel.getValueAt(selectedIndex, 0);
						
						//if (subscriberList.getSelectedIndex() == -1) { GUI.newError(iFrame, "Абонент не выбран!"); return; }
						//Subscriber sub = (Subscriber)subscriberList.getSelectedValue();
						
						if (GUI.newDialog(iFrame, "Удалить абонента: "+ subscriber.toString()+ " ? Все занимаемые пары будут освобождены.") == JOptionPane.YES_OPTION)
							GUI.removeSubscriber(subscriber);
							((DefaultTableModel) subscriberList.getModel()).removeRow(selectedIndex);
							//((DefaultListModel)pathList.getModel()).clear();
							//((DefaultListModel)pairList.getModel()).clear();	
							//((DefaultListModel)subscriberList.getModel()).removeElement(sub);	
							
							//GUI.setListItems(subscriberList, sc.sortByIdUp(sc.getInNet(((Net)netsComboBox.getSelectedItem()).getId())));	
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
						
						if (pathList.getSelectedIndex() != -1)
						GUI.setListItems(pairList, ((Path)pathList.getSelectedValue()).getUsedPairs());
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
						if (subscriberList.getSelectionModel().isSelectionEmpty()){ GUI.newError(iFrame, "Абонент не выбран!"); return; }
						int selectedIndex = subscriberList.getRowSorter().convertRowIndexToModel(subscriberList.getSelectionModel().getMinSelectionIndex());
						Subscriber subscriber = (Subscriber)tableModel.getValueAt(selectedIndex, 0);
						
						//if (subscriberList.getSelectedIndex() == -1) { GUI.newError(iFrame, "Абонент не выбран!"); return; }
						//	Path path = GUI.formPath((Subscriber)subscriberList.getSelectedValue(), null);
						Path path = GUI.formPath(subscriber, null);
						if ( path != null) 
								((DefaultListModel)pathList.getModel()).addElement(path);
								//GUI.setListItems(pathList, phc.sortByIdUp(phc.getSubscriberPaths((Subscriber)subscriberList.getSelectedValue())));

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
						if (subscriberList.getSelectionModel().isSelectionEmpty()){ GUI.newError(iFrame, "Абонент не выбран!"); return; }
						int selectedIndex = subscriberList.getRowSorter().convertRowIndexToModel(subscriberList.getSelectionModel().getMinSelectionIndex());
						Subscriber subscriber = (Subscriber)tableModel.getValueAt(selectedIndex, 0);
						
						//if (subscriberList.getSelectedIndex() == -1) { GUI.newError(iFrame, "Абонент не выбран!"); return; }
						if (pathList.getSelectedIndex() == -1) { GUI.newError(iFrame, "Включение не выбрано!"); return; }
						//if (GUI.formPath((Subscriber)subscriberList.getSelectedValue(), (Path)pathList.getSelectedValue()) != null)
						if (GUI.formPath(subscriber, (Path)pathList.getSelectedValue()) != null)
							//GUI.setListItems(pathList, phc.sortByIdUp(phc.getSubscriberPaths((Subscriber)subscriberList.getSelectedValue())));
							GUI.setListItems(pathList, phc.sortByIdUp(phc.getSubscriberPaths(subscriber)));
							
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
						//if (subscriberList.getSelectedIndex() == -1) { GUI.newError(iFrame, "Абонент не выбран!"); return; }
						if (subscriberList.getSelectionModel().isSelectionEmpty()){ GUI.newError(iFrame, "Абонент не выбран!"); return; }
						int selectedIndex = subscriberList.getRowSorter().convertRowIndexToModel(subscriberList.getSelectionModel().getMinSelectionIndex());
						//Subscriber subscriber = (Subscriber)tableModel.getValueAt(selectedIndex, 0);
						
						if (pathList.getSelectedIndex() == -1) { GUI.newError(iFrame, "Включение не выбрано!"); return; }
						if (GUI.newDialog(iFrame, "Удалить включение?") == JOptionPane.YES_OPTION) {
							Path path = (Path)pathList.getSelectedValue();
							GUI.removePath(path);
							((DefaultListModel)pairList.getModel()).clear();
							((DefaultListModel)pathList.getModel()).removeElement(path);
							
						//	GUI.setListItems(pathList, phc.sortByIdUp(phc.getSubscriberPaths((Subscriber)subscriberList.getSelectedValue())));		
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
						
						if (netsComboBox.getSelectedIndex() == -1) { GUI.newError(iFrame, "Сеть не выбрана!"); return; }
						final Integer netId = ((Net)netsComboBox.getSelectedItem()).getId();
						
						//if (subscriberList.getSelectedIndex() == -1) { GUI.newError(iFrame, "Абонент не выбран!"); return; }
						if (subscriberList.getSelectionModel().isSelectionEmpty()){ GUI.newError(iFrame, "Абонент не выбран!"); return; }
						
						if (pathList.getSelectedIndex() == -1) { GUI.newError(iFrame, "Включение не выбрано!"); return; }
						final Path path = (Path)pathList.getSelectedValue();
						
						final JDialog iSubFrame = GUI.newDialog("Добавить пару", 410,  330);
						
						GUI.newLabel("Тип пары:", iSubFrame, 20, 15, 360, 25);
						final JComboBox pairTypeComboBox = new JComboBox();
						pairTypeComboBox.addItem("Магистральную");
						pairTypeComboBox.addItem("Межшкафную");
						pairTypeComboBox.addItem("Распределительную");
						pairTypeComboBox.addItem("Прямого питания");
											
						
						iSubFrame.getContentPane().add(pairTypeComboBox);
						pairTypeComboBox.setBounds(20, 40, 360, 25);
						
						final JLabel label = GUI.newLabel("Идщую от кросса/громполосы:", iSubFrame, 20, 75, 360, 25);
						final JComboBox comboBox1 = GUI.dframeComboBox(netsComboBox, iSubFrame, 20, 100, 360, 25);
						final JComboBox comboBox2 = GUI.frameComboBox(comboBox1, iSubFrame, 20, 135, 360, 25);
						GUI.dframeComboBoxLinked(comboBox1, comboBox2);
						
						GUI.newLabel("Пара:", iSubFrame, 20, 170, 260, 25);
					    final JList selectedPairList = GUI.newList(iSubFrame, 20, 195, 360, 25);

						
						JButton selectFrom = GUI.newButton("Выбрать", iSubFrame, 290, 230, 90, 25);
						
						selectFrom.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
			
								if (comboBox1.getSelectedIndex() == -1) { GUI.newError(iSubFrame, "Не выбран кросс/шкаф!"); return; }
								if (comboBox2.getSelectedIndex() == -1) { GUI.newError(iSubFrame, "Не выбрана громполоса/бокс!"); return; }
								Pair p = GUI.viewConnectedPointElement((ConnectedPointElement)comboBox2.getSelectedItem(), netId, true);
								if (p != null) {
									Vector<Pair> v = new Vector<Pair>(); v.add(p);
									GUI.setListItems(selectedPairList, v);
									selectedPairList.setSelectedIndex(0);
								}
							}
						});
						    
				        JButton okButton = GUI.newButton("Сохранить", iSubFrame, 20, 250, 110, 25);
						
						
						 ActionListener actionListener = new ActionListener() {
					            public void actionPerformed(ActionEvent e) {
					            	comboBox1.removeAllItems();
									
					            	for (int i = 0; i < comboBox1.getActionListeners().length; i++ )
					            		comboBox1.removeActionListener(comboBox1.getActionListeners()[i]);
					            						            	
					            	if (pairTypeComboBox.getSelectedIndex() == 0) {
										
										label.setText("Идщую от кросса/громполосы:");
										GUI.setComboBoxItems(comboBox1, dfc.getInNet(netId));
										GUI.dframeComboBoxLinked(comboBox1, comboBox2);
									
									}
					            	
					            	if (pairTypeComboBox.getSelectedIndex() == 1) {
										
										label.setText("Идщую от шкафа/бокса:");
										GUI.setComboBoxItems(comboBox1, cbc.getInNet(netId));
										GUI.cabinetComboBoxLinked(comboBox1, comboBox2, 1);
									}
					            	
					            	if (pairTypeComboBox.getSelectedIndex() == 2) {
										
										label.setText("Идщую от шкафа/бокса:");
										GUI.setComboBoxItems(comboBox1, cbc.getInNet(netId));
										GUI.cabinetComboBoxLinked(comboBox1, comboBox2, 2);
														
									}
					            	
					            	if (pairTypeComboBox.getSelectedIndex() == 3) {
										
										label.setText("Идщую от кросса/громполосы:");
										GUI.setComboBoxItems(comboBox1, dfc.getInNet(netId));
										GUI.dframeComboBoxLinked(comboBox1, comboBox2);
										
									}
					            	
					            }
					        };
					   
					        pairTypeComboBox.addActionListener(actionListener);
					        pairTypeComboBox.setSelectedIndex(0);
					       
					        okButton.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent arg0) {
									
									if (selectedPairList.getSelectedIndex() == -1) { GUI.newError(iSubFrame, "Не выбрана пара!"); return; }	
									
									
									if ( GUI.addPairToPath(path, (Pair)selectedPairList.getSelectedValue(), iSubFrame) != null) {
										iSubFrame.dispose();
										GUI.setListItems(pairList, path.getUsedPairs());
									}
								}
					        });
						
						iSubFrame.setVisible(true);
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
						
						if (pathList.getSelectedIndex() == -1) { GUI.newError(iFrame, "Включение не выбрано!"); return; }
						if (pairList.getSelectedIndex() == -1) { GUI.newError(iFrame, "Пара не выбрана!"); return; }
						
						Pair delPair = (Pair)pairList.getSelectedValue();
						Path path = (Path)pathList.getSelectedValue();
						
						if (GUI.newDialog(iFrame, "Удалить пару: "+delPair.toString()+ " из включения: "+ path.toString()) == JOptionPane.YES_OPTION)
						if (path.removePair(delPair)) {
							
							String mes = "Пара:" + delPair.toString()+ ", удалена из включения:" + path.toString();
							rw.addLogMessage(mes);
							GUI.newInfo(iFrame, mes);
							((DefaultListModel)pairList.getModel()).removeElement(delPair);
							
							if (phc.isPairUsed(delPair) == null)  {
								delPair.setStatus(0);
								rw.addLogMessage("Пара "+ delPair.toString()+" освобождена ");
							}
						}
						else {
							GUI.newError(iFrame, "Пара не может быть удалена из включения!");
						}
					
					}
				};
				
				deletePairButton.addActionListener(deletePair);
				/*
				 * ------------------------------------------------ 
				 */
				
				iFrame.setVisible(true);
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
				
				GUI.newLabel("Сеть:", iFrame, 10, 10, 520, 14);
				final JComboBox netsComboBox = GUI.newNetsComboBox(iFrame, 10, 30, 520, 25);
				
				GUI.newLabel("Список кабелей:", iFrame, 10, 65, 520, 14);
				//final JList cableList = GUI.newList(iFrame, 10, 85, 520, 470);
				
				final JTable cableTable = GUI.newTable(iFrame, 10, 85, 520, 470);
				final DefaultTableModel tableModel = (DefaultTableModel) cableTable.getModel();
				tableModel.setColumnIdentifiers(new String[]{"Кабель","От","До","Емкость","Исп.емкость","Длина"});
				
				GUI.linkNetsComboBoxCableTable(netsComboBox, cableTable);
				
				
				
				//GUI.netsComboBoxLinked(netsComboBox, cableList, cc);
				
				JButton editCableButton = GUI.newButton("Редактировать", iFrame, 540, 85, 125, 26);
				JButton viewCableButton = GUI.newButton("Смотреть", iFrame, 540, 125, 125, 26);
				JButton passportCableButton = GUI.newButton("Паспорт", iFrame, 540, 165, 125, 26);
				JButton createCableButton = GUI.newButton("Добавить", iFrame, 540, 235, 125, 26);
				JButton deleteCableButton = GUI.newButton("Удалить", iFrame, 540, 275, 125, 26);
				
				/*
				 * Событие кнопки редактирования кабеля
				 */
				ActionListener editCable = new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						//if (cableList.getSelectedIndex() == -1) { GUI.newError(iFrame, "Кабель не выбран!"); return; }
						//GUI.formCable((Cable)cableList.getSelectedValue());
						//GUI.setListItems(cableList, cc.sortByIdUp(cc.getInNet((Net)netsComboBox.getSelectedItem())));

						if (cableTable.getSelectionModel().isSelectionEmpty()){ GUI.newError(iFrame, "Кабель не выбран!"); return; }
						int selectedIndex = cableTable.getRowSorter().convertRowIndexToModel(cableTable.getSelectionModel().getMinSelectionIndex());
						Cable cable = (Cable)tableModel.getValueAt(selectedIndex, 0);
						GUI.formCable(cable);
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
						//if (cableList.getSelectedIndex() == -1) { GUI.newError(iFrame, "Кабель не выбран!"); return; }
						//GUI.viewCable((Cable)cableList.getSelectedValue(), ((Net)netsComboBox.getSelectedItem()).getId());
						if (cableTable.getSelectionModel().isSelectionEmpty()){ GUI.newError(iFrame, "Кабель не выбран!"); return; }
						int selectedIndex = cableTable.getRowSorter().convertRowIndexToModel(cableTable.getSelectionModel().getMinSelectionIndex());
						GUI.viewCable((Cable)tableModel.getValueAt( selectedIndex, 0),((Net)netsComboBox.getSelectedItem()).getId(), false);
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
						if (netsComboBox.getSelectedIndex() == -1) { GUI.newError(iFrame, "Сеть не выбрана"); return; }
						Cable cable = GUI.formCable(null);
						//GUI.setListItems(cableList, cc.sortByIdUp(cc.getInNet((Net)netsComboBox.getSelectedItem())));
						if (cable != null) GUI.addCableToTable(cableTable, cable);
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
						
						//if (cableList.getSelectedIndex() == -1) { GUI.newError(iFrame, "Кабель не выбран!"); return; }
						int n = GUI.newDialog(iFrame, "Удалить кабель: " + cable.toString()+" и все содержащиеся в нем пары?");
						if (n == JOptionPane.YES_OPTION) {
							GUI.removeCable(cable);
							GUI.newInfo(iFrame, "Кабель "+cable.toString()+" и все содержащиеся в нем пары удалены");
							((DefaultTableModel) cableTable.getModel()).removeRow(selectedIndex);
							//GUI.setListItems(cableList, cc.sortByIdUp(cc.getInNet((Net)netsComboBox.getSelectedItem())));	
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
						if (cable.getType() == 0) {GUI.formViewPassport(rw.createМCablePassport(cable)); return;}
						if (cable.getType() == 1) {GUI.formViewPassport(rw.createIcCablePassport(cable)); return;}
						if (cable.getType() == 3) {return;}
					}		
				};
				passportCableButton.addActionListener(passportCabinet);
				/*
				 * ---------------------------------------------------------
				 */
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
				final JDialog iFrame = GUI.newDialog("Редактировать колодец", 585, 600);
				
				GUI.newLabel("Сеть:", iFrame, 10, 10, 420, 14);
				final JComboBox netsComboBox = GUI.newNetsComboBox(iFrame, 10, 30, 420, 25);
				
				GUI.newLabel("Список колодцев:", iFrame, 10, 65, 420, 14);
				final JList manholeList = GUI.newList(iFrame, 10, 85, 420, 470);
				
				GUI.netsComboBoxLinked(netsComboBox, manholeList, mc);
				
				JButton editManholeButton = GUI.newButton("Редактировать", iFrame, 440, 85, 125, 26);
				JButton viewManholeButton = GUI.newButton("Смотреть", iFrame, 440, 125, 125, 26);
				JButton passportManholeButton = GUI.newButton("Паспорт", iFrame, 440, 165, 125, 26);
				JButton createManholeButton = GUI.newButton("Добавить", iFrame, 440, 235, 125, 26);
				JButton deleteManholeButton = GUI.newButton("Удалить", iFrame, 440, 275, 125, 26);
				/*
				 * Событие кнопки редактирования колодца
				 */
				ActionListener editManhole = new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (manholeList.getSelectedIndex() == -1) { GUI.newError(iFrame, "Колодец не выбран!"); return; }
						GUI.formManhole((Manhole)manholeList.getSelectedValue());
						GUI.setListItems(manholeList, mc.sortByIdUp(mc.getInNet((Net)netsComboBox.getSelectedItem())));	
					}
				};
				editManholeButton.addActionListener(editManhole);
				/*
				 * ---------------------------------------------------------
				 */
				/*
				 * Событие кнопки просмотра колодца
				 */
				ActionListener viewManhole = new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (manholeList.getSelectedIndex() == -1) { GUI.newError(iFrame, "Колодец не выбран!"); return; }
						GUI.viewManhole((Manhole)manholeList.getSelectedValue(), ((Net)netsComboBox.getSelectedItem()).getId());
					}
				};
				viewManholeButton.addActionListener(viewManhole);
				/*
				 * ---------------------------------------------------------
				 */
				/*
				 * Событие кнопки создания колодца
				 */
				ActionListener createManhole = new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (netsComboBox.getSelectedIndex() == -1) { GUI.newError(iFrame, "Сеть не выбрана"); return; }
						GUI.formManhole(null);
						GUI.setListItems(manholeList, mc.sortByIdUp(mc.getInNet((Net)netsComboBox.getSelectedItem())));
					}
				};
				createManholeButton.addActionListener(createManhole);
				/*
				 * ---------------------------------------------------------
				 */
				/*
				 * Событие кнопки удаления колодца
				 */
				ActionListener deleteManhole = new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (manholeList.getSelectedIndex() == -1) { GUI.newError(iFrame, "Колодец не выбран!"); return; }
						int n = GUI.newDialog(iFrame, "Удалить колодец: " + manholeList.getSelectedValue().toString()+" и все участки канализации проходящие через него?");
						if (n == JOptionPane.YES_OPTION) {
							GUI.removeManhole((Manhole)manholeList.getSelectedValue());
							GUI.newInfo(iFrame, "Колодец и участки канализации, проходящие через него, удалены");
							GUI.setListItems(manholeList, mc.sortByIdUp(mc.getInNet((Net)netsComboBox.getSelectedItem())));	
						}		
					}
				};
				deleteManholeButton.addActionListener(deleteManhole);
				/*
				 * ---------------------------------------------------------
				 */
				/*
				 * Событие кнопки просмотра паспорта колодца
				 */
				ActionListener passportManhole = new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (manholeList.getSelectedIndex() == -1) { GUI.newError(iFrame, "Колодец не выбран!"); return; }
						GUI.formViewPassport(rw.createManholePassport((Manhole)manholeList.getSelectedValue()));
					}		
				};
				passportManholeButton.addActionListener(passportManhole);
				/*
				 * ---------------------------------------------------------
				 */
				iFrame.setVisible(true);
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
				
				GUI.newLabel("Сеть:", iFrame, 10, 10, 420, 14);
				final JComboBox netsComboBox = GUI.newNetsComboBox(iFrame, 10, 30, 420, 25);
				
				GUI.newLabel("Список участков канализации:", iFrame, 10, 65, 420, 14);
				final JList ductList = GUI.newList(iFrame, 10, 85, 420, 470);
				
				GUI.netsComboBoxLinked(netsComboBox, ductList, duc);
				
				JButton editDuctButton = GUI.newButton("Редактировать", iFrame, 440, 85, 125, 26);
				JButton viewDuctButton = GUI.newButton("Смотреть", iFrame, 440, 125, 125, 26);
				JButton passportDuctButton = GUI.newButton("Паспорт", iFrame, 440, 165, 125, 26);
				JButton createDuctButton = GUI.newButton("Добавить", iFrame, 440, 235, 125, 26);
				JButton deleteDuctButton = GUI.newButton("Удалить", iFrame, 440, 275, 125, 26);
				
				/*
				 * Событие кнопки редактирования канализации
				 */
				ActionListener editDuct = new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (ductList.getSelectedIndex() == -1) { GUI.newError(iFrame, "Участок канализации не выбран!"); return; }
						GUI.formDuct((Duct)ductList.getSelectedValue());
						GUI.setListItems(ductList, duc.sortByIdUp(duc.getInNet((Net)netsComboBox.getSelectedItem())));	
					}
				};
				editDuctButton.addActionListener(editDuct);
				/*
				 * ---------------------------------------------------------
				 */
				/*
				 * Событие кнопки просмотра канализации
				 */
				ActionListener viewDuct = new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (ductList.getSelectedIndex() == -1) { GUI.newError(iFrame, "Участок канализации не выбран!"); return; }
						GUI.viewDuct((Duct)ductList.getSelectedValue(), ((Net)netsComboBox.getSelectedItem()).getId());
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
						if (netsComboBox.getSelectedIndex() == -1) { GUI.newError(iFrame, "Сеть не выбрана"); return; }
						GUI.formDuct(null);
						GUI.setListItems(ductList, duc.sortByIdUp(duc.getInNet((Net)netsComboBox.getSelectedItem())));
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
							GUI.removeDuct((Duct)ductList.getSelectedValue());
							GUI.newInfo(iFrame, "Участок канализации удален.");
							GUI.setListItems(ductList, duc.sortByIdUp(duc.getInNet((Net)netsComboBox.getSelectedItem())));	
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
						if (netsComboBox.getSelectedIndex() == -1) { GUI.newError(iFrame, "Сеть не выбрана"); return; }
						if (ductList.getSelectedIndex() == -1) { GUI.newError(iFrame, "Участок канализации не выбран!"); return; }
						Duct duct = (Duct)ductList.getSelectedValue();
						Building building = (Building)buc.getElement(duct.getTo());
						Manhole manhole = (Manhole)mc.getElement(duct.getTo());
						if (building != null)  { GUI.formViewPassport(rw.createCableglandPassport(duct)); return;}
						Vector<Duct> v = GUI.formCreateDuctsSet((Net)netsComboBox.getSelectedItem());
						if (v.size() >0) {
							GUI.formViewPassport(rw.createDuctPassport(v));
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
				
				GUI.newLabel("Сеть:", iFrame, 10, 10, 420, 14);
				final JComboBox netsComboBox = GUI.newNetsComboBox(iFrame, 10, 30, 420, 25);
				
				GUI.newLabel("Список зданий:", iFrame, 10, 65, 420, 14);
				final JList buildingList = GUI.newList(iFrame, 10, 85, 420, 470);
				
				GUI.netsComboBoxLinked(netsComboBox, buildingList, buc);
				
				JButton editBuildingButton = GUI.newButton("Редактировать", iFrame, 440, 85, 125, 26);
				
				JButton createBuildingButton = GUI.newButton("Добавить", iFrame, 440, 235, 125, 26);
				JButton deleteBuildingButton = GUI.newButton("Удалить", iFrame, 440, 275, 125, 26);
				
				/*
				 * Событие кнопки редактирования здания
				 */
				ActionListener editBuilding = new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (buildingList.getSelectedIndex() == -1) { GUI.newError(iFrame, "Здание не выбрано!"); return; }
						GUI.formBuilding((Building)buildingList.getSelectedValue());
						GUI.setListItems(buildingList, buc.sortByIdUp(buc.getInNet((Net)netsComboBox.getSelectedItem())));	
					}
				};
				editBuildingButton.addActionListener(editBuilding);
				/*
				 * ---------------------------------------------------------
				 */
				/*
				 * Событие кнопки создания здания
				 */
				ActionListener createBuilding = new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (netsComboBox.getSelectedIndex() == -1) { GUI.newError(iFrame, "Сеть не выбрана"); return; }
						GUI.formBuilding(null);
						GUI.setListItems(buildingList, buc.sortByIdUp(buc.getInNet((Net)netsComboBox.getSelectedItem())));
					}
				};
				createBuildingButton.addActionListener(createBuilding);
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
							GUI.removeBuilding((Building)buildingList.getSelectedValue());
							GUI.newInfo(iFrame, "Здание удалено");
							GUI.setListItems(buildingList, buc.sortByIdUp(buc.getInNet((Net)netsComboBox.getSelectedItem())));	
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
		
		JMenu mnNewMenu_2 = new JMenu("О программе");
		menuBar.add(mnNewMenu_2);
		
	//	frmTeleline.getContentPane().setLayout(new BorderLayout(0, 0));
	//	frmTeleline.getContentPane().add(new GraficsPanel (), BorderLayout.CENTER);
			
		
	}
}
