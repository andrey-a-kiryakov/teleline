package org.teleline.gui;

import java.awt.Dialog.ModalityType;
import java.awt.Window;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import org.teleline.system.Sys;

public abstract class FormJDialog extends Form{
	
	Window owner;
	
	public FormJDialog(Window owner, Sys iSys) {
		super(iSys);
		this.owner = owner;
	}
	
	protected JDialog createDialog (String title, int width, int height) {
		
		iDialog = new JDialog(owner, ModalityType.TOOLKIT_MODAL);
		iDialog.setSize(width, height);
		iDialog.setLocationRelativeTo(iDialog);
		iDialog.setTitle(title);
		iDialog.setResizable(false);
		contentPane = iDialog.getContentPane();
		contentPane.setLayout(null);
		return iDialog;
	}
	/**
	 * Закрывает форму 
	 */
	public void close() {
		
		iDialog.dispose();
	}
	
	public void util_newInfo (String mes) {JOptionPane.showMessageDialog(iDialog, mes, "Информация", JOptionPane.INFORMATION_MESSAGE);}
	
	public void util_newError (String mes) {JOptionPane.showMessageDialog(iDialog, mes, "Ошибка", JOptionPane.ERROR_MESSAGE);}
}