package org.teleline.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.teleline.model.Cable;
import org.teleline.model.Pair;
import org.teleline.system.Sys;


public class FormViewCable extends Form {

	public FormViewCable(Sys iSys, final Cable element, final JTextField textFieldForSelectResult) {
		super(iSys);
		
		int W = 18, H = 18, marginX = 8, marginY = 8, inLine = 10, labelPlaceLeft = 50, labelPlaceTop = 20, groupDevision = 14, infoListHeght = 200;
		int lines = (int) Math.ceil ((double)element.getCapacity().intValue() / (double)inLine);
		int panelWidth = groupDevision + labelPlaceLeft + W * inLine + marginX * (inLine + 1);
		int panelHeight = labelPlaceTop + H * lines + marginY * (lines + 1);
		
		createDialog("Просмотр кабеля", panelWidth + 40, panelHeight + infoListHeght + 100);
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setToolTipText(element.toString());
		panel.setBackground(new Color(200, 200, 200));
		panel.setBounds(20, 50, panelWidth, panelHeight);
		iFrame.getContentPane().add(panel);
		
		final JTextArea infoArea = addTextArea(20, 40 + panelHeight + 20, panelWidth, infoListHeght);
		JLabel head = addLabel(element.toShortString(), 20, 10, panelWidth, 30);
		head.setFont(new Font("Dialog", Font.BOLD, 16));
		head.setHorizontalAlignment(SwingConstants.CENTER);
		
		/*
		 * Событие нажатие на кнопку пары
		 */
		ActionListener pairClick = new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				Pair p = (Pair)((ElementView)e.getSource()).getElement();
				viewPairInfo(p, infoArea);
		}};
		/*
		 * --------------------------------
		 */
		/*
		 * Событие нажатия на кнопку пустого места
		 */
		ActionListener placeClick = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textFieldForSelectResult != null) {
					Pair p = (Pair)((ElementView)e.getSource()).getElement();
					//returnedPair.set(0, p);
					textFieldForSelectResult.setText(p.getFromNumber().toString());
					iFrame.dispose();
				}
			}};
		/*
		 * ---------------------------------
		 */
		//хеш всех созданых кнопок для пар
		HashMap<Pair, ElementView> elementViewHash = new HashMap<Pair, ElementView>();
		JPopupMenu popupMenu = popupMenuForPair(elementViewHash);
		
		for (int ln = 0; ln < inLine; ln++) {
			JLabel l = new JLabel(((Integer)ln).toString());
			l.setFont(new Font("Dialog", Font.BOLD, 10));
			if (ln > 4) {
				l.setBounds(groupDevision + labelPlaceLeft + marginX + ln*(W + marginX), marginY, W, H);
			}
			else {
				l.setBounds(labelPlaceLeft + marginX + ln*(W + marginX), marginY, 20, H);
			}
			l.setHorizontalAlignment(SwingConstants.CENTER);
			panel.add(l);
		}
		
		int x = 0, y = 0;
		for (int place = 0; place < element.getCapacity(); place++) {
			
			if ( x > inLine - 1)  { x = 0; y++; }
			
				if (x == 0) {
					JLabel l = new JLabel(((Integer)place).toString());
					l.setFont(new Font("Dialog", Font.BOLD, 10));
					l.setBounds(marginX, labelPlaceTop + marginY + y*(H + marginY), 30, H);
					l.setHorizontalAlignment(SwingConstants.RIGHT);
					panel.add(l);
				}
				ElementView button = new ElementView();
				if (x > 4) {
					button.setBounds(groupDevision + labelPlaceLeft + marginX + x*(W + marginX), labelPlaceTop + marginY + y*(H + marginY), W, H);
				}
				else {
					button.setBounds(labelPlaceLeft + marginX + x*(W + marginX), labelPlaceTop + marginY + y*(H + marginY), W, H);
				}
				panel.add(button);
				Pair pairForEmptyPlace = new Pair(iSys.fc,iSys.bc,iSys.dbc,iSys.cc);
				pairForEmptyPlace.setFromNumber(place);
				button.setElement(pairForEmptyPlace);
			
				Pair pair = iSys.pc.getInPlace((Cable)element, (Integer)place);
				
				if (pair != null) {
					button.setToolTipText("Пара: "+ pair.toString());
					button.setElement(pair);
					if (pair.getStatus() == 0) button.setBackground(new Color(0, 200, 0));
					if (pair.getStatus() == 1) button.setBackground(new Color(0, 0, 200));
					if (pair.getStatus() == 2) button.setBackground(new Color(250, 0, 0));
					button.addActionListener(pairClick);
					addPopupToPair(button, popupMenu);
					elementViewHash.put(pair, button);
				}
				else {
					button.setBackground(new Color(230, 230, 230));
					button.setToolTipText("Незанятое место №" + ((Integer)place).toString());
					button.addActionListener(placeClick);
				}

			x++;
			
		}
	
		iFrame.setVisible(true);

	}
	
}