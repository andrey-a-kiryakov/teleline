package org.teleline.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;

import org.teleline.model.AbstractElement;
import org.teleline.model.Cable;
import org.teleline.model.ConnectedPointElement;
import org.teleline.model.DFramе;
import org.teleline.model.Frame;
import org.teleline.model.Pair;

import system.Sys;

public class FormViewDFrame extends Form {

	public FormViewDFrame(final Sys iSys, final DFramе dframe) {
		super(iSys);
		int W = 80, H = 100, marginX = 20, marginY = 20, inLine = 10;
		int lines = (int) Math.ceil ((double)dframe.getPlacesCount().intValue() / (double)inLine);
		int panelWidth = W * inLine + marginX * (inLine + 1);
		int panelHeight = H * lines + marginY * (lines + 1);
		
		createDialog("Просмотр кросса", panelWidth + 40 + 10, panelHeight + 100);
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setToolTipText(dframe.toString());
		panel.setBackground(new Color(0, 128, 128));
		panel.setBounds(20, 50, panelWidth, panelHeight);
		iFrame.getContentPane().add(panel);
		
		JLabel head = addLabel(dframe.toString(), 20, 10, panelWidth, 30);
		head.setFont(new Font("Dialog", Font.BOLD, 16));
		head.setHorizontalAlignment(SwingConstants.CENTER);
		int x = 0, y = 0;
	
		JButton refreshButton = addButton("Обновить", 20,10,90,26);
		refreshButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				iFrame.dispose();
				new FormViewDFrame(iSys, dframe);			
			}
		});
		
		JButton dboxButton = addButton("Коробки", 120,10,90,26);
		dboxButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				
				
				HashSet<AbstractElement> dboxes = new HashSet<AbstractElement>();
				
				Iterator<Cable> i = iSys.cc.getDCableOut(dframe).iterator();
				while (i.hasNext()) {
					
					Cable cable = i.next();
					
					Iterator<Pair> t = iSys.pc.getInCable(cable).iterator();
					
					
					while (t.hasNext()) {
						Pair pair = t.next();
						
						AbstractElement ae = iSys.dbc.getElement(pair.getElementTo());
							
						 if (!dboxes.contains(ae)) { 
							
							dboxes.add(ae);
			
						 }
					}
				}
				
				FormDBoxes fb = new FormDBoxes(iSys, dboxes);
				fb.iFrame.setTitle("Коробки кросса: "+ dframe.toString());
				fb.refreshButton.setEnabled(false);
			}
		});
		
		ActionListener frameClick = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				new FormViewConnectedPointElement(iSys, (Frame)((ElementView)e.getSource()).getElement(), null, null );			
			}
		};
		
		ActionListener placeClick = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
						
			}
		};
		
		JPopupMenu popupMenu = new JPopupMenu();

		JMenuItem menuItem = new JMenuItem("Добавить");
		popupMenu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			//	JPopupMenu pm = (JPopupMenu) ((JMenuItem)e.getSource()).getParent();
			//	ElementView ep = (ElementView)pm.getInvoker();
			//	ConnectedPointElement p = (ConnectedPointElement) ep.getElement();
				new FormFrame(iSys, null, dframe);
				/*if (formFrame(null, dframe) != null ) {
					iFrame.dispose();
					viewDFrame(dframe);
				}*/
			}	
		});
		
	
		JMenuItem menuItem_1 = new JMenuItem("Редактировать");
		popupMenu.add(menuItem_1);
		menuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPopupMenu pm = (JPopupMenu) ((JMenuItem)e.getSource()).getParent();
				ElementView ep = (ElementView)pm.getInvoker();
				ConnectedPointElement p = (ConnectedPointElement) ep.getElement();
				
				new FormFrame(iSys,(Frame)p, dframe);
				/*
				if (formFrame((Frame)p, dframe) != null ) {
					iFrame.dispose();
					viewDFrame(dframe);
				}*/
				
			}
		});
		
		JMenuItem menuItem_2 = new JMenuItem("Удалить");
		popupMenu.add(menuItem_2);
		menuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPopupMenu pm = (JPopupMenu) ((JMenuItem)e.getSource()).getParent();
				ElementView ep = (ElementView)pm.getInvoker();
				ConnectedPointElement p = (ConnectedPointElement) ep.getElement();	
				
				if (util_newDialog("Удалить громполосу " + p.toString()+" и все пары в ней?") == JOptionPane.YES_OPTION) {
					iSys.removeFrame((Frame)p);
					iFrame.dispose();
					new FormViewDFrame(iSys, dframe);
				}
			}
		});
		
		for (int place = 0; place < dframe.getPlacesCount(); place++) {
			
			if ( x > inLine - 1)  { x = 0; y++; }
				
				ElementView button = new ElementView();
				button.setBounds(marginX + x*(W + marginX), marginY + y*(H + marginY), W, H);
				panel.add(button);
				button.setElement(null);
				
				addPopupToConnectedPointElement(button, popupMenu);
	
				Frame frame =  (Frame)iSys.fc.getInPlace((Integer)place, dframe.getId());
				if (frame != null) {
					button.setText(frame.toString());
					button.setToolTipText("Громполоса: "+ frame.toString());
					button.setElement(frame);
					button.setBackground(new Color(200, 0, 200));
					button.addActionListener(frameClick);
					button.setForeground(new Color(0, 0, 0));
				}
				else {
					button.setBackground(new Color(230, 230, 230));
					button.setForeground(new Color(160, 160, 160));
					
					button.setToolTipText("Незанятое место №" + ((Integer)place).toString());
					button.addActionListener(placeClick);
					button.setText(((Integer)place).toString());
				}
			x++;
		}
	
		iFrame.setVisible(true);

	}
	
}