package org.teleline.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.WindowConstants;

import org.teleline.model.Sys;

public class FormProgressBar extends Form {
	
	public JProgressBar progressBar;
	public JButton okButton;
	public JLabel label;
	
	public FormProgressBar(Sys iSys) {
		super(iSys);
		createDialog("Ход выполнения операции", 500, 120);
		okButton = addButton("OK", 375, 50, 110, 25);
		label = addLabel("", 15, 50, 300, 25);
		label.setFont(new Font("Dialog", Font.PLAIN, 10));
		progressBar = new JProgressBar();
		progressBar.setBounds(15, 10, 470, 25);
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