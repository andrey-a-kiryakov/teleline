package org.teleline.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;

import org.teleline.model.Cabinet;
import org.teleline.model.ConnectedPointElement;
import org.teleline.model.DFramе;

import system.Sys;

public class FormAddPair extends Form {
	
	public JButton okButton;
	public JList selectedPairList;
	
	public FormAddPair(final Sys iSys/*, final Path path*/) {
		super(iSys);
		final Integer netId = iSys.nc.getOnlyElement().getId();
		
		createDialog("Добавить пару", 410,  330);
		
		addLabel("Тип пары:", 20, 15, 360, 25);
		final JComboBox pairTypeComboBox = addComboBox(20, 40, 360, 25);
		pairTypeComboBox.addItem("Магистральную");
		pairTypeComboBox.addItem("Межшкафную");
		pairTypeComboBox.addItem("Распределительную");
		pairTypeComboBox.addItem("Прямого питания");
		
		final JLabel label = addLabel("Идщую от кросса/громполосы:",20, 75, 360, 25);
		final JComboBox comboBox1 = addComboBox(20, 100, 360, 25);
		util_setComboBoxItems(comboBox1, iSys.dfc.sortByIdUp(iSys.dfc.getInNet(netId)));
				
		final JComboBox comboBox2 = addComboBox(20, 135, 360, 25);
		util_setComboBoxItems(comboBox2, iSys.fc.getInOwner(((DFramе)comboBox1.getSelectedItem()).getId()));
		
		dframeComboBoxLinked(comboBox1, comboBox2);
		
		addLabel("Пара:", 20, 170, 260, 25);
	    selectedPairList = addList(20, 195, 360, 25);

		JButton selectFrom = addButton("Выбрать",290, 230, 90, 25);
		selectFrom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if (comboBox1.getSelectedIndex() == -1) { util_newError("Не выбран кросс/шкаф!"); return; }
				if (comboBox2.getSelectedIndex() == -1) { util_newError("Не выбрана громполоса/бокс!"); return; }
				new FormViewConnectedPointElement(iSys,(ConnectedPointElement)comboBox2.getSelectedItem(), null, selectedPairList);
			}
		});
		    
        okButton = addButton("Сохранить", 20, 250, 110, 25);
		
		 ActionListener actionListener = new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	            	comboBox1.removeAllItems();
					
	            	for (int i = 0; i < comboBox1.getActionListeners().length; i++ )
	            		comboBox1.removeActionListener(comboBox1.getActionListeners()[i]);
	            						            	
	            	if (pairTypeComboBox.getSelectedIndex() == 0) {
						
						label.setText("Идщую от кросса/громполосы:");
						util_setComboBoxItems(comboBox1, iSys.dfc.getInNet(netId));
						dframeComboBoxLinked(comboBox1, comboBox2);
					
					}
	            	
	            	if (pairTypeComboBox.getSelectedIndex() == 1) {
						
						label.setText("Идщую от шкафа/бокса:");
						util_setComboBoxItems(comboBox1, iSys.cbc.getInNet(netId));
						cabinetComboBoxLinked(comboBox1, comboBox2, 1);
					}
	            	
	            	if (pairTypeComboBox.getSelectedIndex() == 2) {
						
						label.setText("Идщую от шкафа/бокса:");
						util_setComboBoxItems(comboBox1, iSys.cbc.getInNet(netId));
						cabinetComboBoxLinked(comboBox1, comboBox2, 2);
										
					}
	            	
	            	if (pairTypeComboBox.getSelectedIndex() == 3) {
						
						label.setText("Идщую от кросса/громполосы:");
						util_setComboBoxItems(comboBox1, iSys.dfc.getInNet(netId));
						dframeComboBoxLinked(comboBox1, comboBox2);
						
					}
	            	
	            }
	        };
	   
	        pairTypeComboBox.addActionListener(actionListener);
	        pairTypeComboBox.setSelectedIndex(0);
	       
	        
		
		iFrame.setVisible(true);

	}
	/**
	 * Связывает выпадающий список кроссов с выпадающим списком громполос
	 * @param dframeComboBox - список кроссов
	 * @param LinkedComboBox - список громполос
	 */
	public void dframeComboBoxLinked(final JComboBox dframeComboBox, final JComboBox LinkedComboBox) {
		
		dframeComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
            	LinkedComboBox.removeAllItems();
            	if (dframeComboBox.getSelectedIndex() > -1)
            	util_setComboBoxItems(LinkedComboBox, iSys.fc.sortByIdUp(iSys.fc.getInOwner(((DFramе)dframeComboBox.getSelectedItem()).getId())));
            
            }
        });
       
        LinkedComboBox.removeAllItems();
    	if (dframeComboBox.getSelectedIndex() > -1)
    		util_setComboBoxItems(LinkedComboBox, iSys.fc.sortByIdUp(iSys.fc.getInOwner(((DFramе)dframeComboBox.getSelectedItem()).getId())));
        
	}
	/**
	 * Связывает список шкафов с выпадающим списком боксов
	 * @param cabinetComboBox - список шкафов
	 * @param LinkedComboBox - список боксов
	 * @param Type - тип отображаемых боксов
	 */
	public void cabinetComboBoxLinked(final JComboBox cabinetComboBox, final JComboBox LinkedComboBox, final Integer Type) {
		
		cabinetComboBox.addActionListener( new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
            	LinkedComboBox.removeAllItems();
            	if (cabinetComboBox.getSelectedIndex() > -1)
            		util_setComboBoxItems(LinkedComboBox, iSys.bc.sortByIdUp(iSys.bc.getInOwnerByTypeUniversal(((Cabinet)cabinetComboBox.getSelectedItem()).getId(), Type)));
            }
        });
        
        LinkedComboBox.removeAllItems();
        if (cabinetComboBox.getSelectedIndex() > -1)
        	util_setComboBoxItems(LinkedComboBox, iSys.bc.sortByIdUp(iSys.bc.getInOwnerByTypeUniversal(((Cabinet)cabinetComboBox.getSelectedItem()).getId(), Type)));
 
	}
	
}