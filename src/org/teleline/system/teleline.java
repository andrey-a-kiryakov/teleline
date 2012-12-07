package org.teleline.system;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowListener;

import org.teleline.gui.ExtFileFilter;
import org.teleline.gui.Form;
import org.teleline.gui.FormBox;
import org.teleline.gui.FormBuilding;
import org.teleline.gui.FormBuildings;
import org.teleline.gui.FormCabinet;
import org.teleline.gui.FormCabinets;
import org.teleline.gui.FormCable;
import org.teleline.gui.FormCables;
import org.teleline.gui.FormDBox;
import org.teleline.gui.FormDBoxes;
import org.teleline.gui.FormDFrame;
import org.teleline.gui.FormDFrames;
import org.teleline.gui.FormDuct;
import org.teleline.gui.FormDucts;
import org.teleline.gui.FormFrame;
import org.teleline.gui.FormManhole;
import org.teleline.gui.FormManholes;
import org.teleline.gui.FormNet;
import org.teleline.gui.FormPairDirect;
import org.teleline.gui.FormPairDistrib;
import org.teleline.gui.FormPairMagAndInt;
import org.teleline.gui.FormStatisticCommon;
import org.teleline.gui.FormSubscriber;
import org.teleline.gui.FormSubscribers;
import org.teleline.io.Reader;
import org.teleline.io.Writer;
import org.teleline.model.*;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.UIManager;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;


import java.io.File;

import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;

public class teleline {
	
	JFrame frmTeleline;
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
					window.frmTeleline.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
					
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
					if (Form.util_newDialog("Сохранить изменения в файле?") == JOptionPane.YES_OPTION) {
						Writer writer = new Writer(sys);
						writer.start();
					}					
				}
				 
				final JFileChooser chooser = new JFileChooser();
				
				chooser.setCurrentDirectory(new File("./saves"));
				chooser.setDialogTitle("Открыть файл...");
				chooser.setFileFilter(new ExtFileFilter("xml", "*.xml Файлы XML"));
				if (chooser.showDialog(frmTeleline, null) == JFileChooser.APPROVE_OPTION) {
					
					sys.clear();
					Reader reader = new Reader(sys, chooser.getSelectedFile());
					reader.start();
				}
			}
		});
		menuFile.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Сохранить");
		mntmNewMenuItem_1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Writer writer = new Writer(sys);
				writer.start();
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
		menuItem_6.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent arg0) { new FormPairMagAndInt(sys, 0); }});
		menu_2.add(menuItem_6);
		/**
		 * Создание межшкафных пар
		 */
		JMenuItem menuItem_7 = new JMenuItem("Межшкафные");
		menuItem_7.addActionListener(new ActionListener() { public void actionPerformed(ActionEvent arg0) { new FormPairMagAndInt(sys,1); }});
		menu_2.add(menuItem_7);
		/**
		 * Создание распределительных пар
		 */
		JMenuItem menuItem_8 = new JMenuItem("Распределительные");
		menuItem_8.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent arg0) {new FormPairDistrib(sys);}});
		menu_2.add(menuItem_8);
		/**
		 * Создание пар прямого питания
		 */
		JMenuItem menuItem_5 = new JMenuItem("Прямого питания");
		menuItem_5.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent arg0) { new FormPairDirect(sys); }});
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
		menuItem_14.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {new FormNet(sys, (Net)sys.nc.getOnlyElement());}});
		menuChange.add(menuItem_14);
		
		JSeparator separator_6 = new JSeparator();
		menuChange.add(separator_6);
		/**
		 * Редактирование элементов "Кросс"
		 */
		JMenuItem menuItem_12 = new JMenuItem("Кросс");
		menuChange.add(menuItem_12);
		menuItem_12.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent arg0) {new FormDFrames(sys, sys.dfc.getElements());}});
		/**
		 * Редактирование элементов "Шкаф"
		 */
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Шкаф");
		menuChange.add(mntmNewMenuItem_3);
		mntmNewMenuItem_3.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent arg0) {new FormCabinets(sys,sys.cbc.getElements());}});
		/**
		 * Редактирование элементов "КРТ"
		 */
		JMenuItem editDBoxMenuItem = new JMenuItem("Распределительную коробку (КРТ)");
		menuChange.add(editDBoxMenuItem);
		editDBoxMenuItem.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent arg0) {new FormDBoxes(sys, sys.dbc.getElements());	}});
		
		JSeparator separator_5 = new JSeparator();
		menuChange.add(separator_5);
		/**
		 * Редактирование элементов "Абонент"
		 */
		JMenuItem menuItem_11 = new JMenuItem("Абонента");
		menuItem_11.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent arg0) {new FormSubscribers(sys,sys.sc.getElements());}});
		/**
		 * Редактирование элементов Кабель
		 */
		JMenuItem menuItem_16 = new JMenuItem("Кабель");
		menuChange.add(menuItem_16);
		menuItem_16.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent arg0) {new FormCables(sys,sys.cc.getElements());}});
		
		JSeparator separator_7 = new JSeparator();
		menuChange.add(separator_7);
		/**
		 * Редактирование элементов "Колодец"
		 */
		JMenuItem menuItem_17 = new JMenuItem("Колодец");
		menuItem_17.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent arg0) {new FormManholes(sys, sys.mc.getElements());}});
		menuChange.add(menuItem_17);
		/**
		 * Редактирование элементов "Канализация"
		 */
		JMenuItem menuItem_18 = new JMenuItem("Канализацию");
		menuItem_18.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent arg0) {new FormDucts(sys,sys.duc.getElements());}});
		menuChange.add(menuItem_18);
		/**
		 * Редактирование элементов "Здание"
		 */
		JMenuItem menuItem_20 = new JMenuItem("Здание");
		menuItem_20.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent arg0) {new FormBuildings(sys, sys.buc.getElements());}});
		menuChange.add(menuItem_20);
		
		JSeparator separator_8 = new JSeparator();
		menuChange.add(separator_8);
		menuChange.add(menuItem_11);
		
		JMenu mnNewMenu_2 = new JMenu("Отчеты");
		menuBar.add(mnNewMenu_2);
		
		JMenuItem menuItem_fullDBoxesList = new JMenuItem("Общая статистика");
		menuItem_fullDBoxesList.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {new FormStatisticCommon(sys);}});
		mnNewMenu_2.add(menuItem_fullDBoxesList);			
	}
}
