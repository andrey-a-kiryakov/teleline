package org.teleline.gui;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class FormListener implements WindowListener {
	
	private Form form;
	
	public FormListener (Form form){
		
		this.form = form;
		
	}
	
	public void windowClosing(WindowEvent e) {
		 
		form.close();
		
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