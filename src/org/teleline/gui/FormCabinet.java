/**
 * Создает и выводит на экран форму создания/редактирования элемента "Шкаф"
 * @param cabinet - элемент "Шкаф", если null - выводится форма создания нового элемента
 */
package org.teleline.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import org.teleline.model.Cabinet;
import org.teleline.model.Net;

import system.Sys;

public class FormCabinet extends Form {

	public FormCabinet(final Sys iSys, final Cabinet cabinet) {
		super(iSys);
		// TODO Auto-generated constructor stub
		final int iFrameMinWidth = 410, iFrameMaxWidth = 830, iFrameMinHeight = 300, iFrameMaxHeight = 430;
		
		createDialog("Создать шкаф", iFrameMinWidth, iFrameMinHeight);
		 
		addLabel("Номер шкафа (1-4 символа: А-Я,а-я,0-9):", 20, 15, 360, 25);
		final JTextField formatedText = addTextField(20, 40, 360, 25);
		
		addLabel("Мест в шкафу:", 20, 75, 360, 25);
		final JComboBox comboBox1 = addComboBox(20, 100, 360, 25);
		comboBox1.addItem((Integer)1);
		comboBox1.addItem((Integer)3);
		comboBox1.addItem((Integer)4);
		comboBox1.addItem((Integer)6);
		comboBox1.addItem((Integer)12);
		comboBox1.setSelectedIndex(4);
		
		addLabel("Класс шкафа (1-2):", 20, 135, 360, 25);
		final JTextField cabinetClass = addTextField(20, 160, 360, 25);
		
		if (cabinet != null) {
			
			iFrame.setTitle("Редактировать шкаф");

			formatedText.setText(cabinet.getSNumber());
			
			comboBox1.setSelectedItem(cabinet.getPlacesCount());
			comboBox1.setEnabled(false);
			
			cabinetClass.setText(cabinet.getCabinetClass().toString());
			cabinetClass.setEnabled(false);
		}
		
		/*
		 * Дополнительные параметры шкафа
		 */
		addLabel("Адрес:", 420, 15, 360, 25);
		final JTextField cabinetAdress = addTextField(420, 40, 360, 25);
		
		addLabel("Место расположения:", 420, 75, 360, 25);
		final JTextField cabinetPlase = addTextField(420, 100, 360, 25);
		
		addLabel("Конструкция, материал:", 420, 135, 360, 25);
		final JTextField cabinetMaterual = addTextField(420, 160, 360, 25);
		
		addLabel("Дата установки:", 420, 195, 360, 25);
		final JTextField cabinetDate = addTextField(420, 220, 360, 25);
		
		addLabel("Cпособ установки:", 420, 255, 360, 25);
		final JComboBox cabinetSetup = new JComboBox();
		cabinetSetup.addItem("Без шкафной коробки");
		cabinetSetup.addItem("Со шкафной коробки");
		cabinetSetup.setSelectedIndex(0);
		cabinetSetup.setBounds(420, 280, 360, 25);
		iFrame.getContentPane().add(cabinetSetup);		
		
		addLabel("Уличный или в помещении:", 420, 315, 360, 25);
		final JComboBox cabinetArea = new JComboBox();
		cabinetArea.addItem("Уличный");
		cabinetArea.addItem("В помещении");
		cabinetArea.setSelectedIndex(0);
		cabinetArea.setBounds(420, 340, 360, 25);
		iFrame.getContentPane().add(cabinetArea);		
			
		if (cabinet != null) {
			cabinetAdress.setText(cabinet.getAdress());
			cabinetPlase.setText(cabinet.getPlace());
			cabinetMaterual.setText(cabinet.getMaterial());
			cabinetDate.setText(cabinet.getDate());
			cabinetSetup.setSelectedIndex(cabinet.getSetup());
			cabinetArea.setSelectedIndex(cabinet.getArea());	
		}
		/*
		 * ------------------------------
		 */
		
		JButton saveButton = addButton("Сохранить", 20, 220, 110, 25);
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			//	if (comboBox.getSelectedIndex() == -1) { newError(iFrame, "Не выбрана сеть!"); return; }
				if (!iSys.v.validateCabinetNumber(formatedText.getText())) { util_newError("Неверный номер шкафа!"); return; }
				if (!iSys.v.validateCabinetClass(cabinetClass.getText())) { util_newError("Неверный класс шкафа!"); return; }
				if (!iSys.v.validateOtherParametr(cabinetAdress.getText())) { util_newError("Неверный формат адреса шкафа (до 150 символов)!"); return; }
				if (!iSys.v.validateOtherParametr(cabinetPlase.getText())) { util_newError("Неверный формат места расположения шкафа (до 150 символов)!"); return; }
				if (!iSys.v.validateOtherParametr(cabinetMaterual.getText())) { util_newError("Неверный формат материала шкафа (до 150 символов)!"); return; }
				if (!iSys.v.validateOtherParametr(cabinetDate.getText())) { util_newError("Неверный формат даты установки шкафа (до 150 символов)!"); return; }
				
			//	Net selectedNet = (Net)comboBox.getSelectedItem();
				String cabinetNumber = formatedText.getText();

				if (cabinet != null) {
					
					Cabinet b = iSys.cbc.elementInNet(cabinetNumber, iSys.nc.getOnlyElement().getId());
					if (b != null && !cabinet.getId().equals(b.getId())) {util_newError("Шкаф с номером "+cabinetNumber+" уже сущесвует в этой сети"); return;}
					
					String old = cabinet.toString();
					cabinet
						.setAdress(cabinetAdress.getText())
						.setPlace(cabinetPlase.getText())
						.setMaterial(cabinetMaterual.getText())
						.setDate(cabinetDate.getText())
						.setSetup(cabinetSetup.getSelectedIndex())
						.setArea(cabinetArea.getSelectedIndex())
						.setSNumber(cabinetNumber);
					iSys.rw.addLogMessage("Шкаф изменен: " + old + " => " + cabinet.toString());
					util_newInfo("Изменения сохранены");
				}
				else {
					
					if (iSys.cbc.elementInNet(cabinetNumber, iSys.nc.getOnlyElement().getId()) != null) {
						util_newError("Шкаф с номером "+cabinetNumber+" уже сущесвует в этой сети");
						return;
					}
					
					Cabinet newCabinet = new Cabinet(); 
					newCabinet
						.setAdress(cabinetAdress.getText())
						.setPlace(cabinetPlase.getText())
						.setMaterial(cabinetMaterual.getText())
						.setDate(cabinetDate.getText())
						.setSetup(cabinetSetup.getSelectedIndex())
						.setArea(cabinetArea.getSelectedIndex())
					//	.attachToNet(selectedNet)
						.attachToNet((Net)iSys.nc.getOnlyElement())
						.setPlacesCount((Integer)comboBox1.getSelectedItem())
						.setSNumber(cabinetNumber);
						newCabinet.setCabinetClass(iSys.rw.valueOf(cabinetClass.getText()));
						iSys.cbc.addElement(newCabinet);
					String mes = "Создан шкаф: "+ newCabinet.toString()+ ", присоединён к сети: "+ ((Net)iSys.nc.getOnlyElement()).toString();
					iSys.rw.addLogMessage(mes);
					util_newInfo(mes);
				}
				iFrame.dispose();
			}
		});
		
		addMoreButton(iFrameMinWidth,iFrameMaxWidth,iFrameMinHeight, iFrameMaxHeight, 320, 220, 60, 25);
	
		iFrame.setVisible(true);
	}
	
}