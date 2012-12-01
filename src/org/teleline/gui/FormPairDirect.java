package org.teleline.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import org.teleline.model.Cable;
import org.teleline.model.ConnectedPointElement;
import org.teleline.model.DBox;
import org.teleline.model.DFramе;
import org.teleline.model.Frame;
import org.teleline.model.Pair;
import org.teleline.model.Sys;

public class FormPairDirect extends Form {

	public FormPairDirect(final Sys iSys) {
		super(iSys);
		
		createDialog("Создать пары прямого питания", 410, 500);
		
		final Integer netId = iSys.nc.getOnlyElement().getId();
			
			addLabel("От кросса/громполосы:", 20, 75, 360, 25);
			final JComboBox comboBox1 = addComboBox(20, 100, 360, 25);
			util_setComboBoxItems(comboBox1, iSys.dfc.sortByIdUp(iSys.dfc.getInNet(netId)));
			final JComboBox comboBox2 = frameComboBox(comboBox1, 20, 135, 360, 25);
			dframeComboBoxLinked(comboBox1, comboBox2);
			
			addLabel("До коробки:", 20, 170, 360, 25);
			final JComboBox comboBox3 = addComboBox(20, 195, 360, 25);
			util_setComboBoxItems(comboBox3, iSys.dbc.sortByIdUp(iSys.dbc.getInNet(netId)));
			
			final JComboBox comboBox6 = cableComboBox(comboBox1, comboBox3, 3, 20, 380, 360, 25);
			
			netsCableComboLinked(netId, comboBox1, comboBox3, comboBox6, 3);
			
	        addLabel("Количество создаваемых пар:", 20, 230, 360, 25);
			
			final JComboBox comboBox5 = addComboBox(20, 255, 360, 25);
			comboBox5.addItem((Integer)10);
			comboBox5.setSelectedIndex(0);
			
			addLabel("ГП заполнять с:", 20, 290, 360, 25);
			final JTextField dframeFrom = addTextField(140, 290, 140, 25);
			dframeFrom.setText("0");
			dframeFrom.setEditable(false);
			JButton selectFrom = addButton("Выбрать", 290, 290, 90, 25);
			
			selectFrom.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (comboBox1.getSelectedIndex() == -1) { util_newError("Не выбран кросс!"); return; }
					if (comboBox2.getSelectedIndex() == -1) { util_newError("Не выбрана громполоса!"); return; }
					new FormViewConnectedPointElement(iSys, (ConnectedPointElement)comboBox2.getSelectedItem(), dframeFrom, null);
				}
			});
			
			addLabel("Кабель заполнять с:", 20, 320, 260, 25);
		    final JTextField cableFrom = addTextField(140, 320, 140, 25);
			cableFrom.setText("0");
			cableFrom.setEditable(false);
			JButton selectCable = addButton("Выбрать", 290, 320, 90, 25);
			
			selectCable.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (comboBox6.getSelectedIndex() == -1) { util_newError("Не выбран кабель!"); return; }
					new FormViewCable(iSys,(Cable)comboBox6.getSelectedItem(), netId, cableFrom);
				}
			});
		    
		    addLabel("Добавить в кабель:", 20, 355, 360, 25);
		    JButton saveButton = addButton("Сoхранить", 20, 420, 110, 25);
		    saveButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					if (comboBox1.getSelectedIndex() == -1) { util_newError("Не выбран кросс!"); return; }
					if (comboBox2.getSelectedIndex() == -1) { util_newError("Не выбрана громполоса!"); return; }
					if (comboBox3.getSelectedIndex() == -1)	{ util_newError("Не выбрана коробка!"); return; }
					if (comboBox6.getSelectedIndex() == -1)	{ util_newError("Не выбран кабель!"); return; }
					
					DFramе selectedDFrame = (DFramе)comboBox1.getSelectedItem();
					Frame selectedFrame = (Frame)comboBox2.getSelectedItem();
					DBox selectedDBox = (DBox)comboBox3.getSelectedItem();
					Cable selectedCable = (Cable)comboBox6.getSelectedItem();
					Integer pairCount = (Integer)comboBox5.getSelectedItem();
					Integer fromFrame = iSys.rw.valueOf(dframeFrom.getText());
					Integer fromCable = iSys.rw.valueOf(cableFrom.getText());
					
					
					for (Integer i = fromFrame; i < fromFrame + pairCount; i++)
						if (iSys.pc.getInPlace(selectedFrame, i) != null)  { util_newError("В громполосе в заданном диапазоне уже существуют кабельные пары!"); return; }
					
					for (Integer i = 0; i < 0 + pairCount; i++)
						if (iSys.pc.getInPlace(selectedDBox, i) != null)  { util_newError("В КРТ в заданном диапазоне уже существуют кабельные пары!"); return; }				
					
					for (Integer i = fromCable; i < fromCable + pairCount; i++)
						if (iSys.pc.getInPlace(selectedCable, i) != null)  { util_newError("В кабеле в заданном диапазоне уже существуют кабельные пары!"); return; }	
					
					//Integer inCableFirst = selectedCable.connect(pairCount);
					
					for (int i = 0; i < pairCount; i++) {
						
						Pair newPair = new Pair(iSys.fc,iSys.bc,iSys.dbc,iSys.cc);
						
						newPair
							.attachToElementFrom(selectedFrame)
							.attachToElementTo(selectedDBox.getId())
							.attachToCable(selectedCable)
							.setNumberInCable(fromCable + i)
							.setFromNumber(fromFrame + i)
							.setToNumber(0 + i);
						//	.setType(3);
						
						iSys.pc.addElement(newPair);
						String mes = "Создана кабельная пара прямого питания: "+ newPair.toString()+ ", присоединена к кроссу: "+selectedDFrame.toString()+", громполосе: "+ selectedFrame.toString() + ", присоединена к коробке: " + selectedDBox.toString();
						iSys.rw.addLogMessage(mes);
						
					}
					String mes = "Создано " + pairCount.toString() + " кабельных пар, присоединены к кроссу: "+selectedDFrame.toString()+", громполосе: "+ selectedFrame.toString() + ", присоединены к коробке: " + selectedDBox.toString();
					util_newInfo(mes);
					iFrame.dispose();
				
				}
			});
					
			iFrame.setVisible(true);
	}
	
}