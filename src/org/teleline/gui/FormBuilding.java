/**
 * Создает и выводит на экран форму создания/редактирования элемента Здание
 * @param building - здание для редактирования, если null - отображается форма создания нового здания
 */
package org.teleline.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import javax.swing.JTextField;

import org.teleline.model.Building;
import org.teleline.model.Net;
import org.teleline.system.Sys;


public class FormBuilding extends Form {

	public FormBuilding(final Sys iSys, final Building building) {
		super(iSys);
		// TODO Auto-generated constructor stub
		createDialog("Создать здание", 410, 270);
			
		addLabel("Улица (до 150 символов):", 20, 10, 360, 14);
		final JTextField street = addTextField(20, 35, 360, 25);
			
		addLabel("Дом № (1-4 символа: А-Я,а-я,0-9):", 20, 65, 360, 14);
		final JTextField number = addTextField(20, 90, 360, 25);
			
		addLabel("Описание (до 150 символов):", 20, 125, 360, 14);
		final JTextField name = addTextField(20, 150, 360, 25);
			
			if (building != null){ 
				iFrame.setTitle("Редактировать здание");
				
				name.setText(building.getName());
				street.setText(building.getStreet());
				number.setText(building.getSNumber());
			}
			
			JButton saveButton = addButton("Сохранить", 20, 190, 110, 25);
			saveButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					if (!iSys.v.validateOtherParametr(street.getText())) { util_newError("Неверный формат названия улицы!");return;}
					if (!iSys.v.validateBuildingNumber(number.getText())) { util_newError("Неверный формат номера дома!");return;}
					if (!iSys.v.validateOtherParametr(name.getText())) { util_newError("Неверный формат названия здания!");return;}
					
					if (building != null) {
						Building oldBuilding =  building;
						building.setName(name.getText());
						building.setSNumber(number.getText());
						building.setStreet(street.getText());
						iSys.rw.addLogMessage("Здание изменено:" + oldBuilding.toString() +" => " + building.toString());
						util_newInfo("Изменения сохранены");
					}
					else {
						Building newBuilding = new Building(); 
						newBuilding.setName(name.getText());
						newBuilding.setSNumber(number.getText());
						newBuilding.setStreet(street.getText());
						newBuilding.attachToNet((Net)iSys.nc.getOnlyElement());
						iSys.buc.addElement(newBuilding);
						String mes = "Создано здание: "+ newBuilding.toString();
						iSys.rw.addLogMessage(mes);
						util_newInfo(mes);
					}
					iFrame.dispose();
				}
			});		
			iFrame.setVisible(true);
	}
}