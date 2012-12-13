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
import org.teleline.model.StructuredElement;
import org.teleline.model.Wrapper;
import org.teleline.system.Sys;


public class FormPairDirect extends Form {

	public FormPairDirect(final Sys iSys) {
		super(iSys);
		
		createDialog("Создать пары прямого питания", 410, 450);
			
			addLabel("От кросса/громполосы:", 20, 15, 360, 25);
			final JComboBox dframeComboBox = addComboBox(20, 40, 360, 25);
			util_setComboBoxItems(dframeComboBox, iSys.dfc.sortByIdUp(iSys.dfc.getElements()));
			final JComboBox frameComboBox = frameComboBox(dframeComboBox, 20, 75, 360, 25);
			dframeComboBoxLinked(dframeComboBox, frameComboBox);
			
			addLabel("До коробки:", 20, 110, 360, 25);
			final JTextField dboxTo = addTextField(20, 135, 260, 25);
			dboxTo.setText("-");
			dboxTo.setEditable(false);
			final Wrapper dboxWrapper = new Wrapper();
			JButton selectToBox = addButton("Выбрать", 290, 135, 90, 25);
			
			selectToBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					final FormDBoxes form = new FormDBoxes(iSys, iSys.dbc.getElements());
					JButton selectButton = new JButton("Выбрать");
					form.buttonPanel.add(selectButton, "cell 0 5");
					selectButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							if (form.table.getSelectionModel().isSelectionEmpty()){ form.util_newError("Коробка не выбрана!"); return; }
							int selectedIndex = form.table.getRowSorter().convertRowIndexToModel(form.table.getSelectionModel().getMinSelectionIndex());
							dboxWrapper.setElement((DBox)form.table.getModel().getValueAt(selectedIndex, 0));
							dboxTo.setText(dboxWrapper.getElement().toString());
							form.close();
						}
						
					});
				}
			});
			
			addLabel("Добавить в кабель:", 20, 170, 360, 25);
			final JComboBox cableComboBox = addComboBox(20, 195, 360, 25);
			setCableComboBoxItems((StructuredElement)dframeComboBox.getSelectedItem(),(StructuredElement)dboxWrapper.getElement(), cableComboBox, 3);
			
			dframeComboBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					setCableComboBoxItems((StructuredElement)dframeComboBox.getSelectedItem(),(StructuredElement)dboxWrapper.getElement(), cableComboBox, 3);
				}
			});
			
	        addLabel("Количество создаваемых пар:", 20, 230, 360, 25);
			
			final JComboBox comboBox5 = addComboBox(20, 255, 360, 25);
			comboBox5.addItem((Integer)10);
			comboBox5.setSelectedIndex(0);
			
			addLabel("ГП заполнять с:", 20, 290, 360, 25);
			final JTextField dframeFrom = addTextField(150, 290, 130, 25);
			dframeFrom.setText("0");
			dframeFrom.setEditable(false);
			JButton selectFrom = addButton("Выбрать", 290, 290, 90, 25);
			
			selectFrom.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (dframeComboBox.getSelectedIndex() == -1) { util_newError("Не выбран кросс!"); return; }
					if (frameComboBox.getSelectedIndex() == -1) { util_newError("Не выбрана громполоса!"); return; }
					new FormViewConnectedPointElement(iSys, (ConnectedPointElement)frameComboBox.getSelectedItem(), dframeFrom, null);
				}
			});
			
			addLabel("Кабель заполнять с:", 20, 320, 260, 25);
		    final JTextField cableFrom = addTextField(150, 320, 130, 25);
			cableFrom.setText("0");
			cableFrom.setEditable(false);
			JButton selectCable = addButton("Выбрать", 290, 320, 90, 25);
			
			selectCable.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (cableComboBox.getSelectedIndex() == -1) { util_newError("Не выбран кабель!"); return; }
					new FormViewCable(iSys,(Cable)cableComboBox.getSelectedItem(), cableFrom);
				}
			});
		    
		    JButton saveButton = addButton("Сoхранить", 20, 370, 110, 25);
		    saveButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					
					if (dframeComboBox.getSelectedIndex() == -1) { util_newError("Не выбран кросс!"); return; }
					if (frameComboBox.getSelectedIndex() == -1) { util_newError("Не выбрана громполоса!"); return; }
					if (dboxWrapper.getElement() == null)	{ util_newError("Не выбрана коробка!"); return; }
					if (cableComboBox.getSelectedIndex() == -1)	{ util_newError("Не выбран кабель!"); return; }
					
					DFramе selectedDFrame = (DFramе)dframeComboBox.getSelectedItem();
					Frame selectedFrame = (Frame)frameComboBox.getSelectedItem();
					DBox selectedDBox = (DBox)dboxWrapper.getElement();
					Cable selectedCable = (Cable)cableComboBox.getSelectedItem();
					Integer pairCount = (Integer)comboBox5.getSelectedItem();
					Integer fromFrame = iSys.rw.valueOf(dframeFrom.getText());
					Integer fromCable = iSys.rw.valueOf(cableFrom.getText());
					
					
					for (Integer i = fromFrame; i < fromFrame + pairCount; i++) {
						if (iSys.pc.getInPlace(selectedFrame, i) != null)  { util_newError("В громполосе в заданном диапазоне уже существуют кабельные пары!"); return; }
						if (i > selectedFrame.getCapacity()) { util_newError("Пары не умещаются в громполосе в заданном диапазоне!"); return; }
					}
					for (Integer i = 0; i < 0 + pairCount; i++) {
						if (iSys.pc.getInPlace(selectedDBox, i) != null)  { util_newError("В КРТ в заданном диапазоне уже существуют кабельные пары!"); return; }
						if (i > selectedDBox.getCapacity()) { util_newError("Пары не умещаются в КРТ в заданном диапазоне!"); return; }
					}
					for (Integer i = fromCable; i < fromCable + pairCount; i++) {
						if (iSys.pc.getInPlace(selectedCable, i) != null)  { util_newError("В кабеле в заданном диапазоне уже существуют кабельные пары!"); return; }	
						if (i > selectedCable.getCapacity()) { util_newError("Пары не умещаются в кабеле в заданном диапазоне!"); return; }
					}
					for (int i = 0; i < pairCount; i++) {
						
						Pair newPair = new Pair(iSys.fc,iSys.bc,iSys.dbc,iSys.cc);
						
						newPair
							.attachToElementFrom(selectedFrame)
							.attachToElementTo(selectedDBox.getId())
							.attachToCable(selectedCable)
							.setNumberInCable(fromCable + i)
							.setFromNumber(fromFrame + i)
							.setToNumber(0 + i);
						
						iSys.pc.addElement(newPair);
						String mes = "Создана кабельная пара прямого питания: "+ newPair.toString()+ ", присоединена к кроссу: "+selectedDFrame.toString()+", громполосе: "+ selectedFrame.toString() + ", присоединена к коробке: " + selectedDBox.toString();
						log.info(mes);
						
					}
					String mes = "Создано " + pairCount.toString() + " кабельных пар, присоединены к кроссу: "+selectedDFrame.toString()+", громполосе: "+ selectedFrame.toString() + ", присоединены к коробке: " + selectedDBox.toString();
					util_newInfo(mes);
					iFrame.dispose();
				
				}
			});
					
			iFrame.setVisible(true);
	}
	
}