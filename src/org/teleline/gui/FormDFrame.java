/**
 * Создает и выводит на экран форму создания/режактирования элемента "Кросс"
 * @param dframe - элемент "Кросс", если null - выводится форма создания нового элемента
 */
package org.teleline.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import org.teleline.model.DFramе;
import org.teleline.model.Net;
import org.teleline.model.Sys;

public class FormDFrame extends Form {

	public FormDFrame(final Sys iSys, final DFramе dframe) {
		super(iSys);
		// TODO Auto-generated constructor stub
		createDialog("Создать кросс", 410, 235);
		if (dframe != null) iFrame.setTitle("Редактировать кросс");
		
		addLabel("Название кросса (1-50 символов):", 20, 10, 360, 25);
		final JTextField textField = addTextField(20, 35, 360, 25);
		
		addLabel("Мест в кроссе:", 20, 65, 360, 25);
		final JComboBox placesBox = addComboBox(20, 90, 360, 25);
		placesBox.addItem((Integer)10);
		placesBox.addItem((Integer)20);
		placesBox.addItem((Integer)30);

		if (dframe != null) {
			textField.setText(dframe.getName());
			placesBox.setSelectedItem(dframe.getPlacesCount());
			placesBox.setEnabled(false);
		}
		
		JButton saveButton = addButton("Сохранить", 20, 150, 110, 25);
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if (!iSys.v.validateDFrameName(textField.getText())) { util_newError("Неверный формат названия кросса!"); return;};
				
					if (dframe != null) {
					String oldDFrame = dframe.toString();
					dframe.setName(textField.getText());
					iSys.rw.addLogMessage("Кросс изменен: " + oldDFrame + " => " + dframe.toString());
					util_newInfo("Изменения сохранены");
				}
				else {
					DFramе newDFrame = new DFramе(); 
					newDFrame.setName(textField.getText()); 
					newDFrame.attachToNet((Net)iSys.nc.getOnlyElement());
					newDFrame.setPlacesCount((Integer)placesBox.getSelectedItem());
					iSys.dfc.addElement(newDFrame);
					String mes = "Создан кросс: "+ newDFrame.toString()+ ", присоединён к сети: "+((Net)iSys.nc.getOnlyElement()).toString();
					iSys.rw.addLogMessage(mes);
					util_newInfo(mes);
				}
				iFrame.dispose();
			}
		});
		iFrame.setVisible(true);
	}
	
}