package org.teleline.gui;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;

public abstract class Form {
	
	public JDialog  iFrame;
	
	public Form(String title, int width, int height) {
		
		iFrame = createDialog(title, width, height);
		
	}
	
	private JDialog createDialog (String title, int width, int height) {
		
		JDialog frame = new JDialog();
		frame.setSize(width, height);
		//frame.setLocationRelativeTo(this.frame);
		frame.setTitle(title);
		frame.setResizable(false);
	//	frame.setModal(true);
		frame.getContentPane().setLayout(null);
		return frame;
	}
	
	public JList addList( int x, int y, int w, int h) {
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(x, y, w, h);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		JList list = new JList();
		list.setModel(new DefaultListModel());
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPane.setViewportView(list);
		iFrame.getContentPane().add(scrollPane);
				
		return list;
	}
	
	public JButton addButton(String Text, int x, int y, int w, int h) {
		
		JButton button = new JButton(Text);
		button.setBounds(x, y, w, h);
		iFrame.getContentPane().add(button);
		
		return button;
	}
	
	public JLabel addLabel(String Text, int x, int y, int w, int h) {
		JLabel newLabel = new JLabel(Text);
		newLabel.setBounds(x, y, w, h);
		iFrame.getContentPane().add(newLabel);
		
		return newLabel;
	}
}