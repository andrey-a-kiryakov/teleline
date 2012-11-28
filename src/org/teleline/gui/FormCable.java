/**
 * Создает и выводит на экран форму создания/редактирования кабеля
 * @param cable - элемент "Кабель", если null - выводится форма создания нового элемента
 */
package org.teleline.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.teleline.model.Cable;
import org.teleline.model.Net;
import org.teleline.model.StructuredElement;
import org.teleline.model.Sys;

public class FormCable extends Form {

	public FormCable(final Sys iSys, final Cable cable) {
		super(iSys);
		
		final int iFrameMinWidth = 410, iFrameMaxWidth = 830, iFrameMinHeight = 480, iFrameMaxHeight = 480;
		createDialog("Создать кабель", iFrameMinWidth, iFrameMinHeight);
			
		addLabel("Тип кабеля:", 20, 15, 360, 25);
		final JComboBox typeComboBox = addComboBox(20, 40, 360, 25);
		typeComboBox.addItem("Магистральный");
		typeComboBox.addItem("Межшкафной");
		typeComboBox.addItem("Распределительный");
		typeComboBox.addItem("Прямого питания");
		
		addLabel("Идущий от:", 20, 75, 360, 25);
		final JComboBox fromComboBox = addComboBox(20, 100, 360, 25);
	
		
		addLabel("Приходящий в:", 20, 135, 360, 25);
		final JComboBox toComboBox = addComboBox(20, 160, 360, 25);
		
		final Integer netId = iSys.nc.getOnlyElement().getId();
		
		/**
		 * Связывает выпадающий список типов кбеля с выпадающимим списками структурных элементов, между которыми кабель может быть создан
		 * @param typeComboBox - список типов кабеля
		 * @param fromComboBox - список для элементов, из которых выходит кабель
		 * @param toComboBox - список для элементов, в которые приходит кабель
		 */

		ActionListener actionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
            	if (netId > -1 && typeComboBox.getSelectedIndex() > -1) {
            		Integer type = typeComboBox.getSelectedIndex();
            
        	
            		fromComboBox.removeAllItems(); fromComboBox.setEnabled(true);
            		toComboBox.removeAllItems(); toComboBox.setEnabled(true);
			
            		if ( type.equals(0)) {
            			util_setComboBoxItems(fromComboBox, iSys.dfc.sortByIdUp(iSys.dfc.getInNet(netId)));
            			util_setComboBoxItems(toComboBox, iSys.cbc.sortByNumberUp(iSys.cbc.getInNetByClass(netId, 1)));
            		}
    	
            		if ( type.equals(1)) {
            			util_setComboBoxItems(fromComboBox, iSys.cbc.sortByNumberUp(iSys.cbc.getInNet(netId)));
            			util_setComboBoxItems(toComboBox, iSys.cbc.sortByNumberUp(iSys.cbc.getInNet(netId)));
            		}
    	
            		if ( type.equals(2)) {
            			util_setComboBoxItems(fromComboBox, iSys.cbc.sortByNumberUp(iSys.cbc.getInNet(netId)));
            			toComboBox.setEnabled(false);
            		}
    	
            		if ( type.equals(3)) {
            			util_setComboBoxItems(fromComboBox, iSys.dfc.sortByIdUp(iSys.dfc.getInNet(netId)));
            			toComboBox.setEnabled(false);
            		}
            	}
            }
		};
		typeComboBox.addActionListener(actionListener);
        
        if (netId > -1 && typeComboBox.getSelectedIndex() > -1) {
    		Integer type = typeComboBox.getSelectedIndex();
	
    		fromComboBox.removeAllItems(); fromComboBox.setEnabled(true);
    		toComboBox.removeAllItems(); toComboBox.setEnabled(true);
	
    		if ( type.equals(0)) {
    			util_setComboBoxItems(fromComboBox, iSys.dfc.sortByIdUp(iSys.dfc.getInNet(netId)));
    			util_setComboBoxItems(toComboBox, iSys.cbc.sortByNumberUp(iSys.cbc.getInNetByClass(netId, 1)));
    		}

    		if ( type.equals(1)) {
    			util_setComboBoxItems(fromComboBox, iSys.cbc.sortByNumberUp(iSys.cbc.getInNet(netId)));
    			util_setComboBoxItems(toComboBox, iSys.cbc.sortByNumberUp(iSys.cbc.getInNet(netId)));
    		}

    		if ( type.equals(2)) {
    			util_setComboBoxItems(fromComboBox, iSys.cbc.sortByNumberUp(iSys.cbc.getInNet(netId)));
    			toComboBox.setEnabled(false);
    		}

    		if ( type.equals(3)) {
    			util_setComboBoxItems(fromComboBox, iSys.dfc.sortByIdUp(iSys.dfc.getInNet(netId)));
    			toComboBox.setEnabled(false);
    		}
    	}
		
		addLabel("Номер (0-999):", 20, 195, 360, 25);
		final JTextField cableNumber  = addTextField(20, 220, 360, 25);
		if (cable != null)cableNumber.setText(cable.getNumber().toString());
		
		addLabel("Емкость кабеля:", 20, 255, 360, 25);
		final JComboBox comboBox2 = addComboBox(20, 280, 360, 25);
		comboBox2.addItem((Integer)10);
		comboBox2.addItem((Integer)20);
		comboBox2.addItem((Integer)30);
		comboBox2.addItem((Integer)50);
		comboBox2.addItem((Integer)100);
		comboBox2.addItem((Integer)150);
		comboBox2.setSelectedIndex(4);
		
		addLabel("Марка кабеля:", 20, 315, 360, 25);
		final JComboBox comboBox3 = addComboBox(20, 340, 360, 25);
		comboBox3.addItem("ТГ");
		comboBox3.addItem("ТПП");
		comboBox3.setSelectedIndex(1);
		
		if (cable != null) {
			iFrame.setTitle("Редактировать кабель");
			typeComboBox.setSelectedIndex(cable.getType());
			typeComboBox.setEnabled(false);
			comboBox2.setSelectedItem(cable.getCapacity());
			comboBox2.setEnabled(false);
			comboBox3.setSelectedItem(cable.getLabel());
			
			if (cable.getType().equals(0)) {
				fromComboBox.setSelectedItem(iSys.dfc.getElement(cable.getFrom()));
				toComboBox.setSelectedItem(iSys.cbc.getElement(cable.getTo()));
			}
			if (cable.getType().equals(1)) {
				fromComboBox.setSelectedItem(iSys.cbc.getElement(cable.getFrom()));
				toComboBox.setSelectedItem(iSys.cbc.getElement(cable.getTo()));
			}
			if (cable.getType().equals(2)) {
				fromComboBox.setSelectedItem(iSys.cbc.getElement(cable.getFrom()));
			}
			if (cable.getType().equals(3)) {
				fromComboBox.setSelectedItem(iSys.dfc.getElement(cable.getFrom()));
			}
			
			fromComboBox.setEnabled(false);
			toComboBox.setEnabled(false);
		}
		/*
		 * Дополнительные параметры кабеля
		 */
		addLabel("Диаметр жилы, мм (0,1-5,0):", 420, 15, 360, 25);
		final JTextField cableWireDiametr = addTextField(420, 40, 360, 25);
		cableWireDiametr.setText("0,5");
		
		addLabel("Длина, м (1-9999):", 420, 75, 360, 25);
		final JTextField cableLenght = addTextField(420, 100, 360, 25);
		cableLenght.setText("1");
		
		addLabel("Год протяжки (4 цифры):",420, 135, 360, 25);
		final JTextField cableYear = addTextField(420, 160, 360, 25);
		cableYear.setText("2011");
		
		addLabel("Состояние:", 420, 195, 360, 25);
		final JComboBox cableStatus = addComboBox(420, 220, 360, 25);
		cableStatus.addItem("Новый");
		cableStatus.addItem("Бывший в эксплуатации");
		cableStatus.setSelectedIndex(0);
		cableStatus.setBounds(420, 220, 360, 25);
		iFrame.getContentPane().add(cableStatus);		
		
		if (cable != null) {
			cableWireDiametr.setText(cable.getWireDiametr());
			cableLenght.setText(cable.getLenght().toString());
			cableYear.setText(cable.getYear());
			cableStatus.setSelectedIndex(cable.getStatus());
		}
		/*
		 * ------------------------------
		 */
		JButton saveButton = addButton("Сохранить", 20, 390, 110, 25);
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
									
				if (!iSys.v.validateCableNumber(cableNumber.getText())) { util_newError("Неверный формат номера кабеля!"); return;}						
				if (!iSys.v.validateCableLenght(cableLenght.getText())) { util_newError("Неверный формат длины кабеля!"); return;}						
				if (!iSys.v.validateCableWireDiametr(cableWireDiametr.getText())) { util_newError("Неверный формат диаметра жилы кабеля!"); return;}						
				if (!iSys.v.validateCableYear(cableYear.getText())) { util_newError("Неверный формат года прокладки кабеля!"); return;}						
				
				Integer type = typeComboBox.getSelectedIndex();
				Integer number = iSys.rw.valueOf(cableNumber.getText());
				StructuredElement from = (StructuredElement)fromComboBox.getSelectedItem();
				StructuredElement to = (StructuredElement)toComboBox.getSelectedItem();
				
				
				if (cable != null) {
					Cable b = iSys.cc.getInOwner(from.getId(), number, type);
					//Если кабель не магистральный, то проверять оба конца, магистральные не проверять на начало - кросс. 
					//В кроссе может быть кабели одинакового номера и типа
				//	if (type > 0)						
						if (b != null && !cable.getId().equals(b.getId())) {
							if (util_newDialog("Кабель такого типа и номера уже существует в "+from.toString()+"! Создать еще один?") == JOptionPane.NO_OPTION)
					 
							return;
						}
					//if (type >= 0 && type < 2) {
					if (type < 2) {
						b = iSys.cc.getInOwner(to.getId(), number, type);
						if (b != null && !cable.getId().equals(b.getId())) { 
							if (util_newDialog("Кабель такого типа и номера уже существует в "+to.toString()+"! Создать еще один?") == JOptionPane.NO_OPTION)
								
							return;
						}
					}
					cable.setNumber(number);
					cable
						.setLabel((String)comboBox3.getSelectedItem())	
						.setLenght(iSys.rw.valueOf(cableLenght.getText()))
						.setWireDiametr(cableWireDiametr.getText())
						.setYear(cableYear.getText())
						.setStatus(cableStatus.getSelectedIndex());
					
					iSys.rw.addLogMessage("Кабель изменен: " +cable.toString());
					util_newInfo("Изменения сохранены");
					
				}
				else {
					//аналогично
					//if (type > 0)
						if (iSys.cc.getInOwner(from.getId(), number, type) != null) { 
							if (util_newDialog("Кабель такого типа и номера уже существует в "+from.toString()+"! Создать еще один?") == JOptionPane.NO_OPTION)
							return;
						}
					
					if (type.equals(1))
						if (from.getId().equals(to.getId())) {util_newError("Выберите разные шкафы!"); return; }
					
					if (type < 2)
						if (iSys.cc.getInOwner(to.getId(), number, type) != null) { 
							if (util_newDialog("Кабель такого типа и номера уже существует в "+to.toString()+"! Создать еще один?") == JOptionPane.NO_OPTION)
							return;
						}
					
					
					Cable newCable = new Cable(iSys.dfc,iSys.cbc,iSys.dbc,iSys.fc,iSys.bc,iSys.pc); 
					newCable.attachToNet((Net)iSys.nc.getOnlyElement());
						
					newCable
						.setType(type)
						.setLabel((String)comboBox3.getSelectedItem())
						.setLenght(iSys.rw.valueOf(cableLenght.getText()))
						.setYear(cableYear.getText())
						.setStatus(cableStatus.getSelectedIndex())
						.setWireDiametr(cableWireDiametr.getText())
						.setCapacity((Integer)comboBox2.getSelectedItem())
						.setFrom(from.getId())
						.setNumber(number);
					if (type < 2) newCable.setTo(to.getId());
					if (type >= 2) newCable.setTo(0);
					
					iSys.cc.addElement(newCable);
					String mes = "Создан "+(String)typeComboBox.getSelectedItem()+" кабель: "+ newCable.toString()+ ", присоединён к сети: "+ iSys.nc.getOnlyElement().toString();
					util_newInfo(mes);
					iSys.rw.addLogMessage(mes);
				}
				iFrame.dispose();
			}
		});
		
		addMoreButton(iFrameMinWidth,iFrameMaxWidth,iFrameMinHeight, iFrameMaxHeight, 320, 390, 60, 25);
		iFrame.setVisible(true);
		
	}
	
}