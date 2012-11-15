/**
 *  Создает и выводит на экран форму создания/режактирования элемента "Бокс"
 * @param box - элемент "Бокс", если null - выводится форма создания нового элемента
 */
package org.teleline.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JTextField;

import org.teleline.model.Box;
import org.teleline.model.Cabinet;
import org.teleline.model.ConnectedPointElement;
import org.teleline.model.Net;
import org.teleline.model.StructuredElement;

public class FormBox extends Form {
	
	private JComboBox comboBox1;
	private JComboBox comboBox2;
	private JComboBox comboBox3;
	private JComboBox placeComboBox;
	private JComboBox netsComboBox;
	private JComboBox cabinetsComboBox;
	private JTextField formatedText;
	public JButton saveButton;
	
	public FormBox(final Box box, final Cabinet cabinet) {
		super();
		// TODO Auto-generated constructor stub
		
		createDialog("Создать бокс", 410, 455);
	/*	if (box != null) iFrame.setTitle("Редактировать бокс");
		
		addLabel("Сеть:", 20, 15, 360, 25);
		netsComboBox = addComboBox(20, 40, 360, 25);
		util_setComboBoxItems(netsComboBox, iSys.nc.sortByIdUp(iSys.nc.elements()));
		if (cabinet != null) {
			netsComboBox.setSelectedItem(iSys.nc.getElement(cabinet.getNet()));
			netsComboBox.setEnabled(false);
		}
		
		addLabel("Тип бокса:", 20, 75, 360, 25);
		comboBox1 = addComboBox(20, 100, 360, 25);
		comboBox1.addItem("Магистральный");
		comboBox1.addItem("Передаточный");
		comboBox1.addItem("Распределительный");
		comboBox1.addItem("Универсальный");
		comboBox1.setSelectedIndex(0);
		if (box != null) {comboBox1.setSelectedIndex(box.getType());}
		
		addLabel("Добавить в шкаф:", 20, 135, 360, 25);
		cabinetsComboBox = addComboBox(20, 160, 360, 25);
		if (netsComboBox.getSelectedIndex() > -1) {
			util_setComboBoxItems(cabinetsComboBox, iSys.cbc.sortByNumberUp(iSys.cbc.getInNetByClass(((Net)netsComboBox.getSelectedItem()).getId(), 0)));
		}
		
		ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cabinetsComboBox.removeAllItems();
                util_setComboBoxItems(cabinetsComboBox, iSys.cbc.sortByIdUp(iSys.cbc.getInNetByClass(((Net)netsComboBox.getSelectedItem()).getId(), 0)));               
            }
        };
        netsComboBox.addActionListener(actionListener); 
        
		
		if (cabinet != null) {
			comboBox2.setSelectedItem(cabinet);
			comboBox2.setEnabled(false);
		}
		
		addLabel("Номер бокса (0-999):", 20, 195, 360, 25);
		formatedText = addTextField(20, 220, 360, 25);
		if (box != null) formatedText.setText(box.getNumber().toString());
		
		addLabel("Емкость бокса:", 20, 255, 360, 25);
		
		comboBox3 = addComboBox(20, 280, 360, 25);
		comboBox3.addItem((Integer)50);
		comboBox3.addItem((Integer)100);
		comboBox3.setSelectedIndex(1);
		
		if (box != null) {
			comboBox3.setSelectedItem(box.getCapacity());
			comboBox3.setEnabled(false);
		}
		
		addLabel("Место в шкафу:", 20, 315, 360, 25);
		placeComboBox = addComboBox(20, 340, 360, 25);
		
		ActionListener actionListener1 = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
            	placeComboBox.removeAllItems();
            	
            	if (cabinetsComboBox.getSelectedIndex() > -1) {
            		StructuredElement cab = (StructuredElement)cabinetsComboBox.getSelectedItem();
            			
            		for (int i = 0; i < cab.getPlacesCount(); i++) {
            			int k = 0;
            			Iterator<?> it = iSys.bc.getInOwner(cab.getId()).iterator();
            			while (it.hasNext())  if (((ConnectedPointElement)it.next()).getPlaceNumber().intValue() == i)  { k = 1; break;}
            			if (k == 0) placeComboBox.addItem((Integer)i);
            		}
            	}
            }
        };
        cabinetsComboBox.addActionListener(actionListener1); 
        
        placeComboBox.removeAllItems();
    	
        if (cabinetsComboBox.getSelectedIndex() > -1) {
        	StructuredElement cab = (StructuredElement)cabinetsComboBox.getSelectedItem();
        	
        	for (int i = 0; i < cab.getPlacesCount(); i++) {
    			int k = 0;
    			Iterator<?> it = iSys.bc.getInOwner(cab.getId()).iterator();
    			while (it.hasNext())  if (((ConnectedPointElement)it.next()).getPlaceNumber().intValue() == i)  { k = 1; break;}
    			if (k == 0) placeComboBox.addItem((Integer)i);
    		}
    	}
        
        if (box != null) {
    		placeComboBox.addItem(box.getPlaceNumber());
    		placeComboBox.setSelectedItem(box.getPlaceNumber());
    	}
     */   
        saveButton = addButton("Сохранить", 20, 380, 110, 25);
    	
       /* saveButton.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent arg0) {
    			
    			if (netsComboBox.getSelectedIndex() == -1) { util_newError("Не выбрана сеть!"); return; }
    			if (cabinetsComboBox.getSelectedIndex() == -1) { util_newError("Не выбран шкаф!"); return; }
    			if (placeComboBox.getSelectedIndex() == -1) { util_newError("Не выбрано место!"); return; }
    			if (!iSys.v.validateBoxNumber(formatedText.getText())) { util_newError("Не верный номер бокса!"); return; }
    			
    			Cabinet selectedCabinet = (Cabinet)cabinetsComboBox.getSelectedItem();
    			Integer boxNumber = rw.valueOf(formatedText.getText());

    			if (box != null) {
    				Box b = iSys.bc.inOwner(boxNumber, selectedCabinet.getId(), comboBox1.getSelectedIndex());
    				Box bx = (Box)iSys.bc.getInPlace((Integer)placeComboBox.getSelectedItem(), selectedCabinet.getId());
    				
    				if (b != null && !box.getId().equals(b.getId())) {util_newError("Бокс с таким типом и номером уже сущесвует в этом шкафу!"); return;}
    				if (bx != null && !box.getId().equals(bx.getId())) {util_newError("Данное место в шкафу занято!"); return;}
    				
    				String old = box.toString();
    				box.setType((Integer)comboBox1.getSelectedIndex());
    				box
    					.attachTo(selectedCabinet)
    					.setNumber(boxNumber)
    					.setPlaceNumber((Integer)placeComboBox.getSelectedItem())
    					.setCapacity((Integer)comboBox3.getSelectedItem());
    				
    				box.setNumber(boxNumber);
    			//	rw.addLogMessage("Бокс изменен: " + old + " => " + box.toString());
    				util_newInfo("Изменения сохранены");
    				
    			}
    			else {
    				if (iSys.bc.getInPlace((Integer)placeComboBox.getSelectedItem(), selectedCabinet.getId()) != null) { newError(iFrame, "Данное место в шкафу занято!"); return; }
    				if (iSys.bc.inOwner(boxNumber, selectedCabinet.getId(), comboBox1.getSelectedIndex()) != null ) {
    					util_newError("Бокс с таким типом и номером уже сущесвует в этом шкафу!"); return;
    				}
    				Box newBox = new Box();
    				newBox.setType((Integer)comboBox1.getSelectedIndex());
    				newBox
    					.attachTo(selectedCabinet)
    					.setNumber(boxNumber)
    					.setPlaceNumber((Integer)placeComboBox.getSelectedItem())
    					.setCapacity((Integer)comboBox3.getSelectedItem());
    				
    				iSys.bc.addElement(newBox);
    				String mes = "Создан "+(String)comboBox1.getSelectedItem()+" бокс: "+newBox.toString()+ ", добавлен в шкаф: "+ selectedCabinet.toString();
    			//	rw.addLogMessage(mes);
    				util_newInfo(mes);
    				
    			}
    			iFrame.dispose();
    		}
    	});*/
    	iFrame.setVisible(true);
    	
		
	}
}