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
import org.teleline.model.Box;
import org.teleline.model.Cabinet;
import org.teleline.model.Cable;
import org.teleline.model.ConnectedPointElement;
import org.teleline.model.Pair;
import org.teleline.system.Sys;


public class FormViewCabinet extends Form {

	public FormViewCabinet(final Sys iSys, final Cabinet cabinet) {
		super(iSys);
		// TODO Auto-generated constructor stub
		int W = 100, H = 120, marginX = 20, marginY = 20, inLine = 3;
		int lines = (int) Math.ceil ((double)cabinet.getPlacesCount().intValue() / (double)inLine);
		int panelWidth = W * inLine + marginX * (inLine + 1);
		int panelHeight = H * lines + marginY * (lines + 1);
		
		createDialog("Просмотр шкафа", panelWidth + 40 + 10, panelHeight + 140);
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setToolTipText(cabinet.toString());
		panel.setBackground(new Color(0, 128, 128));
		panel.setBounds(20, 90, panelWidth, panelHeight);
		iFrame.getContentPane().add(panel);
		
		JLabel head = addLabel(cabinet.toString(), 20, 45, panelWidth, 30);
		head.setFont(new Font("Dialog", Font.BOLD, 16));
		head.setHorizontalAlignment(SwingConstants.CENTER);
		int x = 0, y = 0;
		
		JButton refreshButton = addButton("Обновить", 20,10,90,26);
		refreshButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				iFrame.dispose();
				new FormViewCabinet(iSys, cabinet);			
			}
		});
		
		JButton dboxButton = addButton("Коробки", 120,10,90,26);
		dboxButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				
				
				HashSet<AbstractElement> dboxes = new HashSet<AbstractElement>();
				
				Iterator<Cable> i = iSys.cc.getDCableOut(cabinet).iterator();
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
				fb.iFrame.setTitle("Коробки шкафа "+ cabinet.toString());
				fb.refreshButton.setEnabled(false);
			}
		});
		
		JButton cableButton = addButton("Кабели", 220,10,90,26);
		cableButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				
				HashSet<AbstractElement> cables = new HashSet<AbstractElement>();
				
				Iterator<Cable> i = iSys.cc.getCables(cabinet).iterator();
				while(i.hasNext()) cables.add(i.next());
				
				FormCables fb = new FormCables(iSys, cables);
				fb.iFrame.setTitle("Кабели шкафа "+ cabinet.toString());
				fb.refreshButton.setEnabled(false);
			}
		});
			
		
		ActionListener boxClick = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				new FormViewConnectedPointElement(iSys, (Box)((ElementView)e.getSource()).getElement(), null, null );			
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
				new FormBox(iSys, null, cabinet); 
			}	
		});
		
	
		JMenuItem menuItem_1 = new JMenuItem("Редактировать");
		popupMenu.add(menuItem_1);
		menuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPopupMenu pm = (JPopupMenu) ((JMenuItem)e.getSource()).getParent();
				ElementView ep = (ElementView)pm.getInvoker();
				ConnectedPointElement p = (ConnectedPointElement) ep.getElement();
				
				new FormBox(iSys, (Box)p, cabinet); 
			}
		});
		
		JMenuItem menuItem_2 = new JMenuItem("Удалить");
		popupMenu.add(menuItem_2);
		menuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPopupMenu pm = (JPopupMenu) ((JMenuItem)e.getSource()).getParent();
				ElementView ep = (ElementView)pm.getInvoker();
				ConnectedPointElement p = (ConnectedPointElement) ep.getElement();	
				
				if (util_newDialog("Удалить бокс " + ((Box)p).toString()+" и все пары в нем?") == JOptionPane.YES_OPTION) {
					iSys.removeBox((Box)p);
					iFrame.dispose();
					new FormViewCabinet(iSys, cabinet);
				}
			}
		});
		
		for (int place = 0; place < cabinet.getPlacesCount(); place++) {
			
			if ( x > inLine - 1)  { x = 0; y++; }
				
				ElementView button = new ElementView();
				button.setBounds(marginX + x*(W + marginX), marginY + y*(H + marginY), W, H);
				panel.add(button);
				button.setElement(null);
				
				addPopupToConnectedPointElement(button, popupMenu);
	
				Box box =  (Box)iSys.bc.getInPlace((Integer)place, cabinet.getId());
				if (box != null) {
					button.setText(box.toString());
					button.setToolTipText("Бокс: "+ box.toString() + " ("+box.getCapacity()+"х2)");
					button.setElement(box);
					if (box.getType() == 0) button.setBackground(new Color(200, 0, 200));
					if (box.getType() == 1) button.setBackground(new Color(0, 200, 200));
					if (box.getType() == 2) button.setBackground(new Color(200, 200, 0));
					if (box.getType() == 3) button.setBackground(new Color(80, 80, 80));
					button.addActionListener(boxClick);
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