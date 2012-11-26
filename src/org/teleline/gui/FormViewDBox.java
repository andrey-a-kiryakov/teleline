package org.teleline.gui;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import org.teleline.model.DBox;
import org.teleline.model.Pair;
import org.teleline.model.Sys;

public class FormViewDBox extends Form {
	
	public FormViewDBox(Sys iSys, final DBox element) {
		super(iSys);
		// TODO Auto-generated constructor stub
			
		int W = 18, H = 18, marginX = 8, marginY = 8, inLine = 10, labelPlaceLeft = 50, labelPlaceTop = 20, groupDevision = 14, infoListHeght = 200;
		int lines = (int) Math.ceil ((double)element.getCapacity().intValue() / (double)inLine);
		int panelWidth = groupDevision + labelPlaceLeft + W * inLine + marginX * (inLine + 1);
		int panelHeight = labelPlaceTop + H * lines + marginY * (lines + 1);
		
		Integer netId = iSys.nc.getOnlyElement().getId();
		
		createDialog("Просмотр КРТ", panelWidth + 40, panelHeight + infoListHeght + 100);
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setToolTipText(element.toString());
		panel.setBackground(new Color(200, 200, 200));
		panel.setBounds(20, 50, panelWidth, panelHeight);
		iFrame.getContentPane().add(panel);
		
		final JTextArea infoArea = addTextArea(20, 40 + panelHeight + 20, panelWidth, infoListHeght);
		JLabel head = addLabel(element.toString(), 20, 10, panelWidth, 30);
		head.setFont(new Font("Dialog", Font.BOLD, 16));
		head.setHorizontalAlignment(SwingConstants.CENTER);
		
		ActionListener pairClick = new ActionListener() { public void actionPerformed(ActionEvent e) {Pair p = (Pair)((ElementView)e.getSource()).getElement(); viewPairInfo(p, infoArea);}};
		
		ActionListener placeClick = new ActionListener() { public void actionPerformed(ActionEvent e) {}};
		
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
			
				Pair pair = iSys.pc.getInPlace((DBox)element, (Integer)place);
				
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