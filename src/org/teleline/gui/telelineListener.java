package org.teleline.gui;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JOptionPane;


public class telelineListener implements WindowListener {
	
	private teleline win;
	
	public telelineListener (teleline window){
		
		this.win = window;
		
	}
	public void windowClosing(WindowEvent e) {
		 
		 if (win.rw.isSaved() == false){ 
			
			 if (win.GUI.newDialog(e.getComponent(), "Сохранить изменения в файле?") ==  JOptionPane.YES_OPTION) {
				 
				 if (win.rw.save()) {
					 win.GUI.newInfo(e.getComponent(), "Файл успешно сохранен");
					}
					else {
						win.GUI.newError(e.getComponent(), "Ошибка при сохранении файла");
					}
			 }
		 
		 }
		 win.rw.deleteNotSavedLog();
		 win.rw.addLogMessage("== Программа закрыта ==");
	     win.rw.writeLog();
		 		 
    }

	public void windowClosed(WindowEvent e) {
         //System.out.println("windowClosing()");               
    }
	
	public void windowDeactivated(WindowEvent e) {
         //System.out.println("windowDeactivated()");               
    }

	public void windowDeiconified(WindowEvent e) {
         //System.out.println("windowDeiconified()");               
    }

	public void windowIconified(WindowEvent e) {
         //System.out.println("windowIconified()");               
    }

	public void windowOpened(WindowEvent e) {
       win.rw.checkFolders();
       win.rw.addLogMessage("== Программа запущена ==");
       win.rw.writeLog();
    }

	public void windowActivated(WindowEvent e) {
		//System.out.println("windowActivated()");
	}
    
}