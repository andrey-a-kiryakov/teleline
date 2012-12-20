/**
 * Создает и выводит на экран форму создания набора участков канализации
 * @param net - сеть
 * @return вектор элементов Duct
*/
package org.teleline.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.teleline.model.AbstractElement;
import org.teleline.model.Duct;
import org.teleline.system.Sys;


public class FormDuctsSet extends FormJFrame {
	
	public JButton okButton;
	public DefaultListModel setListModel;
	
	public FormDuctsSet(Sys iSys) {
		super(iSys);

		createFrame("Создание списка элементов канализации для паспорта",800,600);
		addLabel("Участки канализации включенные в паспорт:", 10, 10, 320, 14);
		final JList setList = addList(10, 30, 320, 520);
		
		addLabel("Участки канализации не включенные в паспорт:", 460, 10, 320, 14);
		//final JList fullList = addList(460, 30, 320, 520);
		final JTable fullList = addTable(460, 30, 320, 520);
		
		setListModel = (DefaultListModel)setList.getModel();
		//final DefaultListModel fullListModel = (DefaultListModel)fullList.getModel();
		((DefaultTableModel) fullList.getModel()).setColumnIdentifiers(new String[]{"Участок"});
		
		JButton addButton = addButton("<", 350, 60, 90, 26); 
		addButton.setToolTipText("Включить участок канализации в паспорт");
		JButton removeButton = addButton(">", 350, 110, 90, 26); 
		removeButton.setToolTipText("Исключить участок канализации из паспорта");
		
		okButton = addButton("OK", 350, 520, 90, 26);
		
		//util_setListItems(fullList, iSys.duc.sortByIdUp(iSys.duc.getElements()));
		
		Iterator<AbstractElement> i = iSys.duc.getIterator();
		while (i.hasNext()) {
			Vector<Object> v = new Vector<Object>();
			v.add(i.next());
			((DefaultTableModel) fullList.getModel()).addRow(v);
		}
		
		/*
		 * Событие кнопки добавления канализации в паспорт
		 */
		ActionListener addDuct = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//if (fullList.getSelectedIndex() == -1) { util_newError("Участок канализации не выбран!"); return; }
				if (fullList.getSelectionModel().isSelectionEmpty()){ util_newError("Участок канализации не выбран!"); return; }
				Integer selectedIndex = fullList.getRowSorter().convertRowIndexToModel(fullList.getSelectionModel().getMinSelectionIndex());
				
				setListModel.addElement((Duct)fullList.getValueAt(selectedIndex, 0));
				//fullListModel.remove(fullList.getSelectedIndex());
				((DefaultTableModel) fullList.getModel()).removeRow(selectedIndex);
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
				if (setList.getSelectedIndex() == -1) { util_newError("Участок канализации не выбран!"); return; }
				Vector<Object> v = new Vector<Object>();
				v.add(setList.getSelectedValue());
				((DefaultTableModel) fullList.getModel()).addRow(v);
				//fullListModel.addElement(setList.getSelectedValue());
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
	/*	ActionListener ok = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (setListModel.getSize() > 0) { 
				Vector<AbstractElement> d = new Vector<AbstractElement>();
				for (int i = 0; i < setListModel.getSize(); i++) 
					d.add((AbstractElement)setListModel.getElementAt(i));
				}
				iFrame.dispose();
			}
		};
		okButton.addActionListener(ok);*/
		/*
		 * ---------------------------------------------------------
		 */
		
		iFrame.setVisible(true);
	}
	
}