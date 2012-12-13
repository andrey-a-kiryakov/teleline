/**
 * Создает и выводит на экран форму создания/редактирования колодца
 * @param man - колодец, если null - выводится форма создания нового элемента
 */
package org.teleline.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import org.teleline.model.Manhole;
import org.teleline.model.Net;
import org.teleline.system.Sys;


public class FormManhole extends Form {

	public FormManhole(final Sys iSys, final Manhole man) {
		super(iSys);
		final int iFrameMinWidth = 410, iFrameMaxWidth = 830, iFrameMinHeight = 310, iFrameMaxHeight = 310;
		
		createDialog("Создать колодец", iFrameMinWidth, iFrameMinHeight);
		
		addLabel("Номер колодца (1-4 символа: А-Я,а-я,0-9):", 20, 15, 360, 25);
		final JTextField manholeNumberText = addTextField(20, 40, 360, 25);
		
		addLabel("Конструкция:", 20, 75, 360, 25);
		final JComboBox manholeConstruction = addComboBox(20, 100, 360, 25);
		manholeConstruction.addItem("Железобетонный");
		manholeConstruction.addItem("Кирпичный");
		manholeConstruction.setSelectedIndex(0);		
		
		addLabel("Форма:", 20, 135, 360, 25);
		final JComboBox manholeForm = addComboBox(20, 160, 360, 25);
		manholeForm.addItem("Овальный");
		manholeForm.addItem("Прямоугольный");
		manholeForm.setSelectedIndex(0);		
		
		/*
		 * Дополнительные параметры колодца
		 */
		addLabel("Адрес:", 420, 15, 360, 25);
		final JTextField manholeAdress = addTextField(420, 40, 360, 25);
		
		addLabel("Дата постройки (ДД.ММ.ГГГГ):", 420, 75, 360, 25);
		final JTextField manholeDate = addTextField(420, 100, 360, 25);
		manholeDate.setText("01.01.2012");
		
		addLabel("Размеры (для нетиповых):", 420, 135, 360, 25);
		final JTextField manholeSize = addTextField(420, 160, 360, 25);
			
		if (man != null) {
			manholeAdress.setText(man.getAdress());
			manholeDate.setText(man.getDate());
			manholeSize.setText(man.getSize());	
		}
		/*
		 * ------------------------------
		 */
		
		if (man != null) {
			
			iFrame.setTitle("Редактировать колодец");
			manholeNumberText.setText(man.getSNumber());
			manholeConstruction.setSelectedIndex(man.getConstruction());
			manholeForm.setSelectedIndex(man.getForm());
			
		}
		
		JButton saveButton = addButton("Сохранить", 20, 220, 110, 25);
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if (!iSys.v.validateCabinetNumber(manholeNumberText.getText())) { util_newError("Неверный номер колодца!"); return; }
				if (!iSys.v.validateDate(manholeDate.getText())) { util_newError("Неверный формат даты постройки!"); return; }
				if (!iSys.v.validateOtherParametr(manholeAdress.getText())) { util_newError("Неверный формат адреса колодца!"); return; }
				if (!iSys.v.validateOtherParametr(manholeSize.getText())) { util_newError("Неверный формат размера колодца!"); return; }
				
				String manholeNumber = manholeNumberText.getText();

				if (man != null) {
					
					Manhole b = iSys.mc.elementInNet(manholeNumber, iSys.nc.getOnlyElement().getId());
					if (b != null && !man.getId().equals(b.getId())) {util_newError("Колодец с номером " + manholeNumber + " уже сущесвует в этой сети"); return;}
					
					String old = man.toString();
					man
						.setAdress(manholeAdress.getText())
						.setDate(manholeDate.getText())
						.setSize(manholeSize.getText())
						.setConstruction(manholeConstruction.getSelectedIndex())
						.setForm(manholeForm.getSelectedIndex())
						.setSNumber(manholeNumber);
					log.info("Колодец изменен: {} => {}", old, man);
					iSys.changes = true;
					util_newInfo("Изменения сохранены");
				}
				else {
					
					if (iSys.mc.elementInNet(manholeNumber, iSys.nc.getOnlyElement().getId()) != null) {
						util_newError("Колодец с номером " + manholeNumber + " уже сущесвует в этой сети");
						return;
					}
					
					Manhole newManhole = new Manhole(); 
					newManhole
						.setAdress(manholeAdress.getText())
						.setDate(manholeDate.getText())
						.setSize(manholeSize.getText())
						.setConstruction(manholeConstruction.getSelectedIndex())
						.setForm(manholeForm.getSelectedIndex())
						.attachToNet((Net)iSys.nc.getOnlyElement())
						.setSNumber(manholeNumber);	
					iSys.mc.addElement(newManhole);
					String mes = "Создан колодец: "+ newManhole.toString()+ ", добавлен в сеть: "+ iSys.nc.getOnlyElement().toString()/*selectedNet.toString()*/;
					log.info(mes);
					iSys.changes = true;
					util_newInfo(mes);
				}
				iFrame.dispose();
			}
		});
		addMoreButton(iFrameMinWidth,iFrameMaxWidth,iFrameMinHeight, iFrameMaxHeight, 320, 220, 60, 25);

		iFrame.setVisible(true);
	}
	
}