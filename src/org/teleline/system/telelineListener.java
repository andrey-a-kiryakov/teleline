package org.teleline.system;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JOptionPane;

import org.teleline.gui.FormJFrame;
import org.teleline.io.Writer;



public class telelineListener implements WindowListener {
	
	private teleline win;
	
	public telelineListener (teleline window){
		
		this.win = window;
		
	}
	public void windowClosing(WindowEvent e) {
		 
		 if (win.sys.changes == true){ 
			
			 if (FormJFrame.util_newDialog("Сохранить изменения в файле?") ==  JOptionPane.YES_OPTION) {
				 
				 Writer writer = new Writer(win.sys);
				 writer.start();
			 }
		 }
		 
		 win.sys.mng.closeAll();
		 Sys.log_app.info("== Программа закрыта ==");
		 win.sys.rw.deleteNotSavedLog();
		 win.frmTeleline.dispose();
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
       win.sys.rw.checkFolders();
       Sys.log_app.info("== Программа запущена ==");
     //  win.sys.rw.writeLog();
    }

	public void windowActivated(WindowEvent e) {
		//System.out.println("windowActivated()");
	}
    
}