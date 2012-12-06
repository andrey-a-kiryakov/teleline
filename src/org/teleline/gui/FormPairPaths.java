package org.teleline.gui;

import javax.swing.JButton;
import javax.swing.JList;

import org.teleline.model.Pair;

import system.Sys;

public class FormPairPaths extends Form {
	public JList pathList;
	public JButton okButton;
	
	public FormPairPaths(Sys iSys, Pair p) {
		super(iSys);
		// TODO Auto-generated constructor stub
		createDialog("Пара: " + p.toString(), 485, 270);
		addLabel("Включения:", 10, 10, 320, 14);
		pathList = addList(10, 30, 320, 200);
		util_setListItems(pathList, iSys.phc.sortByIdUp(iSys.phc.getPairsPath(p)));
		pathList.setSelectedIndex(0);
		okButton = addButton("Выбрать", 340, 30, 125, 26);
		
		iFrame.setVisible(true);
	}
	
}