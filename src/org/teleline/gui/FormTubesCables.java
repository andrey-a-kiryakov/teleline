/**
 * Создает и выводит на экран форму выбора кабеля в канале канализации
 * @param tube - канал
 * @return выбранное включение, либо null если ничего не выбрано
 */
package org.teleline.gui;

import javax.swing.JButton;
import javax.swing.JList;
import org.teleline.model.Tube;

import system.Sys;


public class FormTubesCables extends Form {
		
	public JButton okButton;
	public JList cableList;
	
	public FormTubesCables(Sys iSys, Tube tube) {
		super(iSys);
		
		createDialog("Канал: " + tube.toString(), 485, 270);
		
		addLabel("Кабели в канале:", 10, 10, 320, 14);
		cableList = addList(10, 30, 320, 200);
		util_setListItems(cableList, iSys.cc.getTubesCables(tube));
		cableList.setSelectedIndex(0);
		
		okButton = addButton("Выбрать", 340, 30, 125, 26);
		/*
		 * Событие кнопки выбора кабеля
		 */	
		/*ActionListener selectPath = new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (cableList.getSelectedIndex() == -1) {util_newError("Кабель не выбран!"); return;}
				iFrame.dispose();	
			}
		};*/
		//okButton.addActionListener(selectPath);
		/*
		 * ---------------------------------------------------------
		 */
		iFrame.setVisible(true);
	}
}