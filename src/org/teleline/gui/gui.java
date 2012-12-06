package org.teleline.gui;

import java.awt.Component;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import system.Sys;

public class gui {
	public Sys sys;

	
	public gui(Sys iSys, JFrame frame ) {
		this.sys = iSys;
	}
	
	
	
	public int newDialog(Component parent, String mes) {
		
		Object[] options = {"Да", "Нет"};
		
		return JOptionPane.showOptionDialog(parent,
				mes,
				"Подтверждение операции",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,     //do not use a custom Icon
				options,  //the titles of buttons
				options[0]); //default button title
				
	}
	
	
}
