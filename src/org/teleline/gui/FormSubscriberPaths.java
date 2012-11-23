package org.teleline.gui;

import javax.swing.JButton;
import javax.swing.JList;

import org.teleline.model.Subscriber;
import org.teleline.model.Sys;

/**
 * Класс формы выбора включения абонента. Создаются все элементы формы и форма отображается.
 * @param sub - абонент
 * @param phc - коллекция включений
 */

public class FormSubscriberPaths extends Form {
	
	public JList pathList;
	public JButton okButton;
	
	public FormSubscriberPaths(Sys iSys, Subscriber sub) {
		super(iSys);
		// TODO Auto-generated constructor stub
		
		createDialog("Абонент: " + sub.toString(), 485, 270);
		addLabel("Включения:", 10, 10, 320, 14);
		pathList = addList(10, 30, 320, 200);
		util_setListItems(pathList, iSys.phc.sortByIdUp(iSys.phc.getSubscriberPaths(sub)));
		pathList.setSelectedIndex(0);
		okButton = addButton("Выбрать", 340, 30, 125, 26);
		iFrame.setVisible(true);
	}
	
	
}