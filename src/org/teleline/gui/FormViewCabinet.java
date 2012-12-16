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
import org.teleline.model.DFramе;
import org.teleline.model.Pair;
import org.teleline.model.StructuredElement;
import org.teleline.system.Sys;


public class FormViewCabinet extends Form {
	
	private StructuredElement element;
	private JPanel boxesPanel;
	
	public int inLine = 10, marginX = 20, marginY = 20, W = 100, H = 120;
	
	JPopupMenu popupMenu = new JPopupMenu();
	
	public FormViewCabinet(final Sys iSys, final StructuredElement element) {
		super(iSys);
		this.element = element;
		int lines = (int) Math.ceil ((double)element.getPlacesCount().intValue() / (double)inLine);
		int panelWidth = W * inLine + marginX * (inLine + 1);
		int panelHeight = H * lines + marginY * (lines + 1);
		
		createDialog("Просмотр " + element.toString(), panelWidth + 40 + 10, panelHeight + 140);
		boxesPanel = new JPanel();
		boxesPanel.setLayout(null);
		boxesPanel.setToolTipText(element.toString());
		boxesPanel.setBackground(new Color(0, 128, 128));
		boxesPanel.setBounds(20, 90, panelWidth, panelHeight);
		iFrame.getContentPane().add(boxesPanel);
		
		JLabel head = addLabel(element.toString(), 20, 45, panelWidth, 30);
		head.setFont(new Font("Dialog", Font.BOLD, 16));
		head.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		JButton refreshButton = addButton("Обновить", 20,10,90,26);
		refreshButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateElementsButtons();
			}
		});
		
		JButton dboxButton = addButton("Коробки", 120,10,90,26);
		dboxButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				
				
				HashSet<AbstractElement> dboxes = new HashSet<AbstractElement>();
				
				Iterator<Cable> i = iSys.cc.getDCableOut(element).iterator();
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
				fb.iFrame.setTitle("Коробки шкафа "+ element.toString());
			//	fb.refreshButton.setEnabled(false);
			}
		});
		
		JButton cableButton = addButton("Кабели", 220,10,90,26);
		cableButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				
				HashSet<AbstractElement> cables = new HashSet<AbstractElement>();
				Iterator<Cable> i = iSys.cc.getCables(element).iterator();
				while(i.hasNext()) cables.add(i.next());
				
				final FormCables fb = new FormCables(iSys, cables);
				fb.iFrame.setTitle("Кабели шкафа "+ element.toString());
			}
		});
	
		JMenuItem menuItem = new JMenuItem("Добавить");
		popupMenu.add(menuItem);
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPopupMenu pm = (JPopupMenu) ((JMenuItem)e.getSource()).getParent();
				ElementView ep = (ElementView)pm.getInvoker();
				FormBox form = new FormBox(iFrame, iSys, null, element);
				form.placeComboBox.setSelectedItem(ep.getPlaceNumber());
				form.iFrame.setVisible(true);
				updateElementsButtons();
			}	
		});
		
		JMenuItem menuItem_1 = new JMenuItem("Редактировать");
		popupMenu.add(menuItem_1);
		menuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPopupMenu pm = (JPopupMenu) ((JMenuItem)e.getSource()).getParent();
				ElementView ep = (ElementView)pm.getInvoker();
				ConnectedPointElement p = (ConnectedPointElement) ep.getElement();
				new FormBox(iFrame, iSys, (Box)p, element).iFrame.setVisible(true);
				//форма модальная, обновление только после закрытия
				updateElementsButtons();
			}
		});
		
		JMenuItem menuItem_2 = new JMenuItem("Удалить");
		popupMenu.add(menuItem_2);
		menuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPopupMenu pm = (JPopupMenu) ((JMenuItem)e.getSource()).getParent();
				ElementView ep = (ElementView)pm.getInvoker();
				ConnectedPointElement p = (ConnectedPointElement) ep.getElement();	
				
				if (util_newDialog("Удалить " + ((ConnectedPointElement)p).toString()+" и все пары в нем?") == JOptionPane.YES_OPTION) {
					iSys.removeBox((Box)p);
					updateElementsButtons();				
				}
			}
		});
		
		createElementsButtons();
	
		iFrame.setVisible(true);
	}
	/**
	 * Создает кнопки-представления элементов
	 */
	private void createElementsButtons() {
		
		ActionListener boxClick = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	
				new FormViewConnectedPointElement(iSys, (ConnectedPointElement)((ElementView)e.getSource()).getElement(), null, null );			
			}
		};
		
		ActionListener placeClick = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
						
			}
		};
		
		int x = 0, y = 0;
		
		for (int place = 0; place < element.getPlacesCount(); place++) {
			
			if ( x > inLine - 1)  { x = 0; y++; }
				
				ElementView button = new ElementView();
				button.setBounds(marginX + x*(W + marginX), marginY + y*(H + marginY), W, H);
				button.setPlaceNumber(place);
				boxesPanel.add(button);
			
				addPopupToConnectedPointElement(button, popupMenu);
	
				ConnectedPointElement connectedPointElement = null;
				
				if (element instanceof Cabinet) connectedPointElement =  (ConnectedPointElement)iSys.bc.getInPlace((Integer)place, element.getId());
				if (element instanceof DFramе) connectedPointElement =  (ConnectedPointElement)iSys.fc.getInPlace((Integer)place, element.getId());
				
				if (connectedPointElement != null) {
					button.setText(connectedPointElement.toString());
					button.setToolTipText("Бокс: "+ connectedPointElement.toString() + " ("+connectedPointElement.getCapacity()+"х2)");
					button.setElement(connectedPointElement);
					if (connectedPointElement.getType() == 0) button.setBackground(new Color(200, 0, 200));
					if (connectedPointElement.getType() == 1) button.setBackground(new Color(0, 200, 200));
					if (connectedPointElement.getType() == 2) button.setBackground(new Color(200, 200, 0));
					if (connectedPointElement.getType() == 3) button.setBackground(new Color(80, 80, 80));
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
	}
	/**
	 * Обновляет (удаляет и создает заново) кнопки-представления элементов
	 */
	public void updateElementsButtons() {
		
		boxesPanel.removeAll();
		createElementsButtons();
		boxesPanel.repaint();
	}
	
}