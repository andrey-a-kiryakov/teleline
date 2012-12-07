package org.teleline.system;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JOptionPane;

import org.teleline.gui.Form;
import org.teleline.io.Writer;



public class telelineListener implements WindowListener {
	
	private teleline win;
	
	public telelineListener (teleline window){
		
		this.win = window;
		
	}
	public void windowClosing(WindowEvent e) {
		 
		 if (win.sys.rw.isSaved() == false){ 
			
			 if (Form.util_newDialog("Сохранить изменения в файле?") ==  JOptionPane.YES_OPTION) {
				 
				 Writer writer = new Writer(win.sys);
				 writer.start();
			 }
		 }
		 
		 System.out.println(win.sys.mng.getSize());
		 win.sys.mng.closeAll();
		 
		 win.sys.rw.deleteNotSavedLog();
		 win.sys.rw.addLogMessage("== Программа закрыта ==");
	     win.sys.rw.writeLog();
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
       win.sys.rw.addLogMessage("== Программа запущена ==");
       win.sys.rw.writeLog();
    }

	public void windowActivated(WindowEvent e) {
		//System.out.println("windowActivated()");
	}
    
}