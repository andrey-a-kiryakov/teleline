package org.teleline.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import org.teleline.model.Duct;
import org.teleline.model.Tube;

import system.Sys;

public class FormViewDuct extends Form {

	public FormViewDuct(Sys iSys, final Duct duct) {
		super(iSys);
		
		HashSet<Tube> h = iSys.tuc.getDuctsTubes(duct);
		
		int W = 25, H = 25, marginX = 9, marginY = 9, inLine = 10, labelPlaceLeft = 50, labelPlaceTop = 30, infoListHeght = 200;
		int lines = (int) Math.ceil ((double)h.size() / (double)inLine);
		int panelWidth = labelPlaceLeft + W * inLine + marginX * (inLine + 1);
		int panelHeight = labelPlaceTop + H * lines + marginY * (lines + 1);
		
		createDialog("Просмотр канализации", panelWidth + 40, panelHeight + infoListHeght + 100);
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setToolTipText(duct.toString());
		panel.setBackground(new Color(200, 200, 200));
		panel.setBounds(20, 50, panelWidth, panelHeight);
		iFrame.getContentPane().add(panel);
		
		final JTextArea infoArea = addTextArea(20, 40 + panelHeight + 20, panelWidth, infoListHeght);
		JLabel head = addLabel(duct.toString(), 20, 10, panelWidth, 30);
		head.setFont(new Font("Dialog", Font.BOLD, 16));
		head.setHorizontalAlignment(SwingConstants.CENTER);
		int x = 0, y = 0;
		
		/*
		 * Событие нажатие на кнопку канала
		 */
		ActionListener tubeClick = new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				Tube t = (Tube)((ElementView)e.getSource()).getElement(); 
				viewTubeInfo(t, infoArea);
			}};
		/*
		 * --------------------------------
		 */

		for (int ln = 0; ln < inLine; ln++) {
			JLabel l = new JLabel(((Integer)ln).toString());
			l.setFont(new Font("Dialog", Font.BOLD, 10));
			l.setBounds(labelPlaceLeft + marginX + ln*(W + marginX), marginY, 20, H);
			l.setHorizontalAlignment(SwingConstants.CENTER);
			panel.add(l);
		}
		
		for (int place = 0; place < h.size(); place++) {
			
			if ( x > inLine - 1)  { x = 0; y++; }
			
				
				if (x == 0) {
					JLabel l = new JLabel(((Integer)place).toString());
					l.setFont(new Font("Dialog", Font.BOLD, 10));
					l.setBounds(marginX, labelPlaceTop + marginY + y*(H + marginY), 30, H);
					l.setHorizontalAlignment(SwingConstants.RIGHT);
					panel.add(l);
				}
				ElementView button = new ElementView();
				button.setBounds(labelPlaceLeft + marginX + x*(W + marginX), labelPlaceTop + marginY + y*(H + marginY), W, H);
				
				panel.add(button);
				Tube tube = iSys.tuc.getDuctByNumber(h, place);
				button.setElement(tube);
				button.setToolTipText("Канал № " + tube.getNumber().toString());
				button.setBackground(new Color(0, 200, 0));
				
				util_setTubeButtonColor(tube, button);
				button.addActionListener(tubeClick);
				addPopupToTube(button, popupMenuForTube());
				

			x++;
			
		}
		iFrame.setVisible(true);
		
	}
	
}