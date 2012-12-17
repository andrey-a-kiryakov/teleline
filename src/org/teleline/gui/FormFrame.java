/**
 * Создает и выводит на экран форму создания/редактирования элемента "Громполоса"
 * @param frame - громполоса, если null - выводится форма создания нового элемента
 */
package org.teleline.gui;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import org.teleline.model.ConnectedPointElement;
import org.teleline.model.DFramе;
import org.teleline.model.Frame;
import org.teleline.model.StructuredElement;
import org.teleline.system.Sys;


public class FormFrame extends FormDialog {
	
	public JComboBox placeComboBox;
	private JComboBox dframeComboBox;
	
	public FormFrame(Window owner, final Sys iSys, final Frame frame, final StructuredElement dframe) {
		super(owner, iSys);
		
		createDialog("Создать громполосу", 410, 330);
		
		addLabel("Добавить к кроссу:", 20, 10, 360, 25);
		dframeComboBox = addComboBox(20, 35, 360, 25);
		util_setComboBoxItems(dframeComboBox, iSys.dfc.sortByIdUp(iSys.dfc.getInNet(iSys.nc.getOnlyElement().getId())));
		
		if (dframe != null) {
			dframeComboBox.setSelectedItem(dframe);
			dframeComboBox.setEnabled(false);
		}	
		
		addLabel("Номер громполосы (0-99):", 20, 65, 360, 25);
		final JTextField frameNumberText = addTextField(20, 90, 360, 25);
		
		addLabel("Емкость громполосы:", 20, 120, 360, 25);
		final JComboBox comboBox3 = addComboBox(20, 145, 360, 25);			
		comboBox3.addItem((Integer)25);
		comboBox3.addItem((Integer)50);
		comboBox3.addItem((Integer)100);
		comboBox3.addItem((Integer)150);
		comboBox3.setSelectedIndex(2);
		
		if (frame != null) {
			iFrame.setTitle("Редактировать громполосу");
			frameNumberText.setText(frame.getNumber().toString());
			comboBox3.setSelectedItem(frame.getCapacity());
			comboBox3.setEnabled(false);
		}
		
		addLabel("Место в кроссе:", 20, 175, 360, 25);
		placeComboBox = addComboBox(20, 200, 360, 25);
		
		dframeComboBox.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	
	            	placeComboBox.removeAllItems();
	            	
	            	if (dframeComboBox.getSelectedIndex() > -1) {
	            		StructuredElement cab = (StructuredElement)dframeComboBox.getSelectedItem();
	            			
	            		for (int i = 0; i < cab.getPlacesCount(); i++) {
	            			int k = 0;
	            			Iterator<?> it = iSys.fc.getInOwner(cab.getId()).iterator();
	            			while (it.hasNext())  if (((ConnectedPointElement)it.next()).getPlaceNumber().intValue() == i)  { k = 1; break;}
	            			if (k == 0) placeComboBox.addItem((Integer)i);
	            		}
	            	}
	            }
	        });
		
			placeComboBox.removeAllItems();
	    	
	        if (dframeComboBox.getSelectedIndex() > -1) {
	        	StructuredElement cab = (StructuredElement)dframeComboBox.getSelectedItem();
	        		
	    			for (int i = 0; i < cab.getPlacesCount(); i++) {
	    				int k = 0;
	    				Iterator<?> it = iSys.fc.getInOwner(cab.getId()).iterator();
	    				while (it.hasNext())  if (((ConnectedPointElement)it.next()).getPlaceNumber().intValue() == i)  { k = 1; break;}
	    				if (k == 0) placeComboBox.addItem((Integer)i);
	    			}
	        }
	        
		
		
		if (frame != null) {
			placeComboBox.addItem(frame.getPlaceNumber());
			placeComboBox.setSelectedItem(frame.getPlaceNumber());
		}
		
		JButton saveButton = addButton("Сoхранить", 20, 250, 110, 25);
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				if (!iSys.v.validateFrameNumber(frameNumberText.getText())) { util_newError("Неверный формат номера громполосы!"); return; }
				
				DFramе selectedDFrame = (DFramе)dframeComboBox.getSelectedItem();
				Integer frameNumber = iSys.rw.valueOf(frameNumberText.getText());
				
				if (frame != null) {
					
					Frame f = (Frame)iSys.fc.isInOwner(frameNumber, selectedDFrame.getId());
					Frame fx = (Frame)iSys.fc.getInPlace((Integer)placeComboBox.getSelectedItem(), selectedDFrame.getId());
					
					if (f != null && !frame.getId().equals(f.getId())) {util_newError("Громполоса с таким номером уже сущесвует в данном кроссе!"); return;}
					if (fx != null && !frame.getId().equals(fx.getId())) {util_newError("Данное место в кроссе занято!"); return;}
					
					String old = frame.toString();
					frame.attachTo(selectedDFrame);
					frame.setNumber(frameNumber);
					frame.setCapacity((Integer)comboBox3.getSelectedItem());
					frame.setPlaceNumber((Integer)placeComboBox.getSelectedItem());
					
					log.info("Громполоса изменена: {} => {}", old, frame);
					iSys.changes = true;
					util_newInfo("Изменения сохранены");
				}
				else {
					
					if (iSys.fc.getInPlace((Integer)placeComboBox.getSelectedItem(), selectedDFrame.getId()) != null) { util_newError("Данное место в кроссе занято!"); return; }
					if (iSys.fc.isInOwner(frameNumber, selectedDFrame.getId()) != null ) { util_newError("Громполоса с таким номером уже сущесвует в данном кроссе!"); return; }
					
					Frame newFrame = new Frame();  
					newFrame.attachTo(selectedDFrame);
					newFrame.setNumber(frameNumber);
					newFrame.setCapacity((Integer)comboBox3.getSelectedItem());
					newFrame.setPlaceNumber((Integer)placeComboBox.getSelectedItem());
					iSys.fc.addElement(newFrame);
					String mes = "Создана громполоса: "+ newFrame.toString()+ ", добавлена в кросс: "+ selectedDFrame.toString();
					util_newInfo(mes);
					log.info(mes);
					iSys.changes = true;
					
				}
				iFrame.dispose();
			}
		});		
		//iFrame.setVisible(true);
	}	
}