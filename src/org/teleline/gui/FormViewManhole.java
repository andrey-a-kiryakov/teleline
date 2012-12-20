package org.teleline.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.teleline.model.Duct;
import org.teleline.model.Manhole;
import org.teleline.system.Sys;


public class FormViewManhole extends FormJFrame {

	public FormViewManhole(final Sys iSys, final Manhole manhole) {
		super(iSys);
		
		int paddingLeft = 10, paddingTop = 10, margin = 5, infoListH = 0;
		
		int manholeButtonW = 120, manholeButtonH = 120;
		int gorizontalDuctW = 180, gorizontalDuctH = 12;
		int verticalDuctW = 12, verticalDuctH = 180;
		int interDucts = 7;
		
		int labelW = 50, labelH = 14;
		
		int panelW = paddingLeft * 2 + margin * 2 + manholeButtonW + gorizontalDuctW * 2; 
		int panelH = paddingTop * 2 + margin * 2 + manholeButtonH + verticalDuctH * 2; 
		
		createFrame("Просмотр колодца", panelW + 40, panelH + infoListH + 100);
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(200, 200, 200));
		panel.setBounds(20, 50, panelW, panelH);
		iFrame.getContentPane().add(panel);
		
		JLabel head = addLabel(manhole.toString(), 20, 10, panelW, 30);
		head.setFont(new Font("Dialog", Font.BOLD, 16));
		head.setHorizontalAlignment(SwingConstants.CENTER);
		
		/*
		 * Событие нажатие на кнопку колодца
		 */
	//	ActionListener manholeClick = new ActionListener() { public void actionPerformed(ActionEvent e) {Pair p = (Pair)((ElementView)e.getSource()).getElement(); viewPairInfo(p, infoArea);}};
		/*
		 * --------------------------------
		 */
		/*
		 * Событие нажатие на кнопку канализации
		 */
		ActionListener ductClick = new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				Duct d = (Duct)((ElementView)e.getSource()).getElement(); 
				new FormViewDuct(iSys, d);
			}};
		/*
		 * --------------------------------
		 */
		
		/*
		 * Событие нажатия на кнопку пустого места
		 */
	//	ActionListener placeClick = new ActionListener() {public void actionPerformed(ActionEvent e) {}};
		/*
		 * ---------------------------------
		 */
		//JPopupMenu popupMenu = popupMenuForPair(iFrame, netId);

		
		ElementView manholeButton = new ElementView();
		manholeButton.setElement(manhole);
		manholeButton.setBounds(paddingLeft + gorizontalDuctW + margin, paddingTop + verticalDuctH + margin , manholeButtonW, manholeButtonW);
	//	manholeButton.addActionListener(manholeClick);
		manholeButton.setText(manhole.toString());
		manholeButton.setBackground(new Color(0,200,200));
		panel.add(manholeButton);
		
		/*
		 * Отрисовываем канализацию сверху
		 */
		HashSet<Duct> ds = iSys.duc.getDuctsBySide(manhole, 0);
		int ductsBlockW = ds.size() * verticalDuctW + (ds.size() - 1) * interDucts;
		Iterator<Duct> i = ds.iterator(); int ductIndex = 0;
		while (i.hasNext()) {
			Duct duct = i.next();
			ElementView ductButton = new ElementView();
			ductButton.setBackground(new Color(200,0,200));
			ductButton.setToolTipText(duct.toString());
			ductButton.setElement(duct);
			ductButton.addActionListener(ductClick);
			ductButton.setBounds(paddingLeft + gorizontalDuctW + margin + (manholeButtonW - ductsBlockW)/2 + ductIndex * (verticalDuctW + interDucts), paddingTop, verticalDuctW, verticalDuctH);
			panel.add(ductButton);

			ductIndex++;
		}
		JLabel front = new JLabel("Перед");
		front.setBounds(paddingLeft + gorizontalDuctW + margin + (manholeButtonW - ductsBlockW)/2 + ductsBlockW + 10, paddingTop, labelW, labelH);
		panel.add(front);
		/*
		 * --------------------------------------------------------
		 */
		/*
		 * Отрисовываем канализацию снизу
		 */
		ds = iSys.duc.getDuctsBySide(manhole, 2);
		ductsBlockW = ds.size() * verticalDuctW + (ds.size() - 1) * interDucts;
		i = ds.iterator(); ductIndex = 0;
		while (i.hasNext()) {
			Duct duct = i.next();
			ElementView ductButton = new ElementView();
			ductButton.setBackground(new Color(200,0,200));
			ductButton.setToolTipText(duct.toString());
			ductButton.setElement(duct);
			ductButton.addActionListener(ductClick);
			ductButton.setBounds(paddingLeft + gorizontalDuctW + margin + (manholeButtonW - ductsBlockW)/2 + ductIndex * (verticalDuctW + interDucts), paddingTop + margin * 2 + verticalDuctH + manholeButtonH, verticalDuctW, verticalDuctH);
			panel.add(ductButton);

			ductIndex++;
		}
		JLabel back = new JLabel("Зад");
		back.setBounds(paddingLeft + gorizontalDuctW + margin + (manholeButtonW - ductsBlockW)/2 + ductsBlockW + 10, panelH - labelH - 10, labelW, labelH);
		panel.add(back);
		/*
		 * --------------------------------------------------------
		 */
		
		/*
		 * Отрисовываем канализацию справа
		 */
		ds = iSys.duc.getDuctsBySide(manhole, 1);
		int ductsBlockH = ds.size() * gorizontalDuctH + (ds.size() - 1) * interDucts;
		i = ds.iterator(); ductIndex = 0;
		while (i.hasNext()) {
			Duct duct = i.next();
			ElementView ductButton = new ElementView();
			ductButton.setBackground(new Color(200,0,200));
			ductButton.setToolTipText(duct.toString());
			ductButton.setElement(duct);
			ductButton.addActionListener(ductClick);
			ductButton.setBounds(paddingLeft + margin * 2  + gorizontalDuctW + manholeButtonW, paddingTop + verticalDuctH + margin + (manholeButtonH - ductsBlockH)/2 + ductIndex * (gorizontalDuctH + interDucts), gorizontalDuctW, gorizontalDuctH);
			panel.add(ductButton);

			ductIndex++;
		}
		JLabel right = new JLabel("Право");
		right.setBounds(panelW - labelW - 10, paddingTop + verticalDuctH + margin + (manholeButtonH - ductsBlockH)/2 + ductsBlockH + 10, labelW, labelH);
		panel.add(right);
	
		/*
		 * --------------------------------------------------------
		 */
		
		/*
		 * Отрисовываем канализацию слева
		 */
		ds = iSys.duc.getDuctsBySide(manhole, 3);
		ductsBlockH = ds.size() * gorizontalDuctH + (ds.size() - 1) * interDucts;
		i = ds.iterator(); ductIndex = 0;
		while (i.hasNext()) {
			Duct duct = i.next();
			ElementView ductButton = new ElementView();
			ductButton.setBackground(new Color(200,0,200));
			ductButton.setToolTipText(duct.toString());
			ductButton.setElement(duct);
			ductButton.addActionListener(ductClick);
			ductButton.setBounds(paddingLeft, paddingTop + verticalDuctH + margin + (manholeButtonH - ductsBlockH)/2 + ductIndex * (gorizontalDuctH + interDucts), gorizontalDuctW, gorizontalDuctH);
			panel.add(ductButton);

			ductIndex++;
		}
		JLabel left = new JLabel("Лево");
		left.setBounds(paddingLeft, paddingTop + verticalDuctH + margin + (manholeButtonH - gorizontalDuctH)/2 + ductsBlockH + 10, labelW, labelH);
		panel.add(left);
		/*
		 * --------------------------------------------------------
		 */
		
		iFrame.setVisible(true);
		
	}
	
}