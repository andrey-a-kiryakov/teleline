package org.teleline.gui;

import javax.swing.JDialog;
import javax.swing.JLabel;

public abstract class Form {
	
	public JDialog  iFrame;
	
	public JLabel addLabel(String Text, int x, int y, int w, int h) {
		JLabel newLabel = new JLabel(Text);
		newLabel.setBounds(x, y, w, h);
		iFrame.getContentPane().add(newLabel);
		
		return newLabel;
	}
}