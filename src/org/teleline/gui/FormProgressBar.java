package org.teleline.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.WindowConstants;

import org.teleline.system.Sys;


public class FormProgressBar extends FormJFrame {
	
	public JProgressBar progressBar;
	public JButton okButton;
	public JLabel label;
	
	public FormProgressBar(Sys iSys) {
		super(iSys);
		createFrame("Ход выполнения операции", 600, 120);
		okButton = addButton("OK", 475, 50, 110, 25);
		label = addLabel("", 15, 50, 400, 25);
		label.setFont(new Font("Dialog", Font.PLAIN, 10));
		progressBar = new JProgressBar();
		progressBar.setBounds(15, 10, 570, 25);
		progressBar.setStringPainted(true);
		progressBar.setMinimum(0);
		progressBar.setMaximum(100);
		//progressBar.setValue(30);
		iFrame.getContentPane().add(progressBar);
		iFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		okButton.setEnabled(false);
		//iFrame.setModal(true);
		
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				iFrame.dispose();
			}
		});
		iFrame.setVisible(true);
		
		
	}
	
}