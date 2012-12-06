package org.teleline.gui;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JDialog;

import org.teleline.model.Sys;


public class FormListener implements WindowListener {
	
	private JDialog dialog;
	private Sys sys;
	
	public FormListener (Sys sys, JDialog dialog){
		
		this.dialog = dialog;
		
	}
	
	public void windowClosing(WindowEvent e) {
		 
	//	sys.mng.remove(dialog);
		
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
      
    }

	public void windowActivated(WindowEvent e) {
		//System.out.println("windowActivated()");
	}
    
}