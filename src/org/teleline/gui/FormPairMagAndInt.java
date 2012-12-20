package org.teleline.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.teleline.model.Box;
import org.teleline.model.Cabinet;
import org.teleline.model.Cable;
import org.teleline.model.ConnectedPointElement;
import org.teleline.model.Pair;
import org.teleline.model.StructuredElement;
import org.teleline.system.Sys;


public class FormPairMagAndInt extends FormJFrame {

	public FormPairMagAndInt(final Sys iSys, final int type) {
		super(iSys);
		createFrame("Создать магистральные пары", 410,  510);
		if (type == 1) iFrame.setTitle("Создать межшкафные пары"); 
			
		final Integer netId = iSys.nc.getOnlyElement().getId();
		
		JLabel l1 = addLabel("От кросса/громполосы:",  20, 15, 360, 25);
		if (type == 1) l1.setText("От шкафа/бокса (№1):");
		final JComboBox fromStructuredElementComboBox = addComboBox(20, 40, 360, 25);
		final JComboBox fromConnectedPointElementComboBox = addComboBox(20, 75, 360, 25);
		
		JLabel l2 = addLabel("До шкафа/бокса:", 20, 110, 360, 25);
		if (type == 1) l2.setText("До шкафа/бокса (№2):");
		final JComboBox toStructuredElementComboBox = addComboBox(20, 135, 360, 25);
		final JComboBox toConnectedPointElementComboBox = addComboBox(20, 170, 360, 25);
		
		final JComboBox cableComboBox = addComboBox(20, 380, 360, 25); 
		
		if (type == 0) {	
			
			util_setComboBoxItems(fromStructuredElementComboBox, iSys.dfc.sortByIdUp(iSys.dfc.getElements()));
			dframeComboBoxLinked(fromStructuredElementComboBox, fromConnectedPointElementComboBox);
			
			util_setComboBoxItems(toStructuredElementComboBox, iSys.cbc.sortByNumberUp(iSys.cbc.getInNetByClass(netId, 1)));
			cabinetComboBoxLinked(toStructuredElementComboBox, toConnectedPointElementComboBox, 0);
			
			setCableComboBoxItems((StructuredElement)fromStructuredElementComboBox.getSelectedItem(),(StructuredElement)toStructuredElementComboBox.getSelectedItem(), cableComboBox, 0);
			
			fromStructuredElementComboBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					setCableComboBoxItems((StructuredElement)fromStructuredElementComboBox.getSelectedItem(),(StructuredElement)toStructuredElementComboBox.getSelectedItem(), cableComboBox, 0);
					
				}
			});
			
			toStructuredElementComboBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					setCableComboBoxItems((StructuredElement)fromStructuredElementComboBox.getSelectedItem(),(StructuredElement)toStructuredElementComboBox.getSelectedItem(), cableComboBox, 0);
					
				}
			});
		}
		
		if (type == 1) {
			
			util_setComboBoxItems(fromStructuredElementComboBox, iSys.cbc.sortByNumberUp(iSys.cbc.getInNetByClass(netId, 1)));
			cabinetComboBoxLinked(fromStructuredElementComboBox, fromConnectedPointElementComboBox, 1);
			
			util_setComboBoxItems(toStructuredElementComboBox, iSys.cbc.sortByNumberUp(iSys.cbc.getInNetByClass(netId, 1)));
			cabinetComboBoxLinked(toStructuredElementComboBox, toConnectedPointElementComboBox, 1);
			
			setCableComboBoxItems((StructuredElement)fromStructuredElementComboBox.getSelectedItem(),(StructuredElement)toStructuredElementComboBox.getSelectedItem(), cableComboBox, 1);
			
			fromStructuredElementComboBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					setCableComboBoxItems((StructuredElement)fromStructuredElementComboBox.getSelectedItem(),(StructuredElement)toStructuredElementComboBox.getSelectedItem(), cableComboBox, 1);
					
				}
			});
			
			toStructuredElementComboBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					setCableComboBoxItems((StructuredElement)fromStructuredElementComboBox.getSelectedItem(),(StructuredElement)toStructuredElementComboBox.getSelectedItem(), cableComboBox, 1);
					
				}
			});
			
		}
			
		
	        addLabel("Количество создаваемых пар:", 20, 205, 360, 25);
			final JComboBox comboBox5 = addComboBox(20, 230, 360, 25);
			comboBox5.addItem((Integer)10);
			comboBox5.addItem((Integer)20);
			comboBox5.addItem((Integer)25);
			comboBox5.addItem((Integer)30);
			comboBox5.addItem((Integer)50);
			comboBox5.addItem((Integer)75);
			comboBox5.addItem((Integer)100);
			comboBox5.addItem((Integer)150);
			comboBox5.setSelectedIndex(6);
			
			
			JLabel l3 = addLabel("ГП заполнять с:", 20, 265, 360, 25);
			if (type == 1) l3.setText("Бокс1 заполнять с:");
			final JTextField fromConnectedPointElementPair = addTextField(140, 265, 140, 25);
			fromConnectedPointElementPair.setText("0");
			fromConnectedPointElementPair.setEditable(false);
			JButton selectFrom = addButton("Выбрать", 290, 265, 90, 25);
			
			selectFrom.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					String s1 = "Не выбран кросс!";
					String s2 = "Не выбрана громполоса!";
					if (type == 1) {
						s1 = "Не выбран шкаф!";
						s2 = "Не выбран бокс";
					}
					if (fromStructuredElementComboBox.getSelectedIndex() == -1) { util_newError(s1); return; }
					if (fromConnectedPointElementComboBox.getSelectedIndex() == -1) { util_newError(s2); return; }
					new FormViewConnectedPointElement(iSys,(ConnectedPointElement)fromConnectedPointElementComboBox.getSelectedItem(), fromConnectedPointElementPair, null);
				}
			});
			
		    addLabel("Бокс заполнять с:", 20, 295, 260, 25);
		    final JTextField boxFrom = addTextField(140, 295, 140, 25);
			boxFrom.setText("0");
			boxFrom.setEditable(false);
			JButton selectBox = addButton("Выбрать", 290, 295, 90, 25);
			
			selectBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (toStructuredElementComboBox.getSelectedIndex() == -1) { util_newError("Не выбран шкаф!"); return; }
					if (toConnectedPointElementComboBox.getSelectedIndex() == -1)	{ util_newError("Не выбран бокс!"); return; }
					new FormViewConnectedPointElement(iSys,(ConnectedPointElement)toConnectedPointElementComboBox.getSelectedItem(), boxFrom, null);
				}
			});
			
			addLabel("Кабель заполнять с:", 20, 325, 260, 25);
		    final JTextField cableFrom = addTextField(140, 325, 140, 25);
			cableFrom.setText("0");
			cableFrom.setEditable(false);
			JButton selectCable = addButton("Выбрать", 290, 325, 90, 25);
			
			selectCable.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (cableComboBox.getSelectedIndex() == -1) {util_newError("Не выбран кабель!"); return; }
					new FormViewCable(iSys,(Cable)cableComboBox.getSelectedItem(), cableFrom);
				}
			});
		   
		    
		    addLabel("Добавить кабель:", 20, 355, 360, 25);
			
			JButton saveButton = addButton("Сoхранить", 20, 430, 110, 25);
			saveButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					String s1 = "Не выбран кросс!";
					String s2 = "Не выбрана громполоса!";
					if (type == 1) {
						s1 = "Не выбран шкаф!";
						s2 = "Не выбран бокс";
					}
					if (fromStructuredElementComboBox.getSelectedIndex() == -1) { util_newError(s1); return; }
					if (fromConnectedPointElementComboBox.getSelectedIndex() == -1) { util_newError(s2); return; }
					if (toStructuredElementComboBox.getSelectedIndex() == -1)	{ util_newError("Не выбран шкаф!"); return; }
					if (toConnectedPointElementComboBox.getSelectedIndex() == -1)	{ util_newError("Не выбран бокс!"); return; }
					if (cableComboBox.getSelectedIndex() == -1)	{ util_newError("Не выбран кабель!"); return; }
					
					StructuredElement selectedFromStructuredElement = (StructuredElement)fromStructuredElementComboBox.getSelectedItem();
					ConnectedPointElement selectedFromConnectedPointElement = (ConnectedPointElement)fromConnectedPointElementComboBox.getSelectedItem();
					Cabinet selectedCabinet = (Cabinet)toStructuredElementComboBox.getSelectedItem();
					Box selectedBox = (Box)toConnectedPointElementComboBox.getSelectedItem();
					Cable selectedCable = (Cable)cableComboBox.getSelectedItem();
					if (type == 1) {
						if (selectedFromStructuredElement.getId().equals(selectedCabinet.getId())) {util_newError("Выберите разные шкафы!"); return;}
					}
					Integer pairCount = (Integer)comboBox5.getSelectedItem();
					Integer fromElement = iSys.rw.valueOf(fromConnectedPointElementPair.getText());
					Integer fromBox = iSys.rw.valueOf(boxFrom.getText());
					Integer fromCable = iSys.rw.valueOf(cableFrom.getText());
					
					String s3 = "В громполосе в заданном диапазоне уже существуют кабельные пары!";
					String s4 = "В боксе в заданном диапазоне уже существуют кабельные пары!";
					String s31 = "Пары не умещаются в громполосе в заданном диапазоне!";
					String s41 = "Пары не умещаются в боксе в заданном диапазоне!";
					
					
									
					if (type == 1) {
						s3 = "В боксе №1 в заданном диапазоне уже существуют кабельные пары!";
						s4 = "В боксе №2 в заданном диапазоне уже существуют кабельные пары!";
						s31 = "Пары не умещаются в боксе №1 в заданном диапазоне!";
						s41 = "Пары не умещаются в боксе №2 в заданном диапазоне!";
					}
					for (Integer i = fromElement; i < fromElement + pairCount; i++) {
						if (iSys.pc.getInPlace(selectedFromConnectedPointElement, i) != null)  { util_newError(s3); return; }
						if (i > selectedFromConnectedPointElement.getCapacity()) { util_newError(s31); return; }
					}
					for (Integer i = fromBox; i < fromBox + pairCount; i++) {
						if (iSys.pc.getInPlace(selectedBox, i) != null)  { util_newError(s4); return; }	
						if (i > selectedBox.getCapacity()) { util_newError(s41); return; }
					}
					for (Integer i = fromCable; i < fromCable + pairCount; i++) {
						if (iSys.pc.getInPlace(selectedCable, i) != null)  { util_newError("В кабеле в заданном диапазоне уже существуют кабельные пары!"); return; }	
						if (i > selectedCable.getCapacity()) { util_newError("Пары не умещаются в кабеле в заданном диапазоне!"); return; }
					}
					//Integer inCableFirst = selectedCable.connect(pairCount);
											
					for (int i = 0; i < pairCount; i++) {
						
						Pair newPair = new Pair(iSys.fc,iSys.bc,iSys.dbc,iSys.cc);
						
						newPair
							.attachToElementFrom(selectedFromConnectedPointElement)
							.attachToElementTo(selectedBox)
							.attachToCable(selectedCable)
							.setNumberInCable(fromCable + i)
							.setFromNumber(fromElement + i)
							.setToNumber(fromBox + i);
							//.setType(0);
						
						iSys.pc.addElement(newPair);
						String mes = "Создана магистральная кабельная пара: "+ newPair.toString()+ ", присоединена к кроссу: "+selectedFromStructuredElement.toString()+", громполосе: "+ selectedFromConnectedPointElement.toString() + ", присоединена к шкафу: "+selectedCabinet.toString()+", боксу: " + selectedBox.toString();
						if (type == 1) {
							mes = "Создана передаточная кабельная пара: "+ newPair.toString()+ ", присоединена к шкафу: "+selectedFromStructuredElement.toString()+", боксу: "+ selectedFromConnectedPointElement.toString() + ", присоединена к шкафу: "+selectedCabinet.toString()+", боксу: " + selectedBox.toString();
						}
						log.info(mes);
						iSys.changes = true;
						
					}
					String mes = "Создано " + pairCount.toString() + " кабельных пар, присоединены к кроссу: "+selectedFromStructuredElement.toString()+", громполосе: "+ selectedFromConnectedPointElement.toString() + ", присоединены к шкафу: "+selectedCabinet.toString()+", боксу: " + selectedBox.toString();
					if (type == 1) {
						mes = "Создано " + pairCount.toString() + " кабельных пар, присоединены к шкафу: "+selectedFromStructuredElement.toString()+", боксу: "+ selectedFromConnectedPointElement.toString() + ", присоединена к шкафу: "+selectedCabinet.toString()+", боксу: " + selectedBox.toString();
					}
					util_newInfo(mes);
					iFrame.dispose();
				
				}
			});
			iFrame.setVisible(true);
	}
	
}