/**
 * Создает и выводит на экран форму создания/редактирования элемента "Кросс"
 * @param dframe - элемент "Кросс", если null - выводится форма создания нового элемента
 */
package org.teleline.gui;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import org.teleline.model.DFramе;
import org.teleline.model.Net;
import org.teleline.model.Wrapper;
import org.teleline.system.Sys;


public class FormDFrame extends FormJDialog {

	public FormDFrame(Window owner, final Sys iSys, final DFramе dframe, final Wrapper wrapper) {
		super(owner, iSys);
		
		createDialog("Создать кросс", 410, 235);
		
		addLabel("Название кросса (1-50 символов):", 20, 10, 360, 25);
		final JTextField textField = addTextField(20, 35, 360, 25);
		
		addLabel("Мест в кроссе:", 20, 65, 360, 25);
		final JComboBox placesBox = addComboBox(20, 90, 360, 25);
		placesBox.addItem((Integer)10);
		placesBox.addItem((Integer)20);
		placesBox.addItem((Integer)30);
		
		if (dframe != null) {
			iDialog.setTitle("Редактировать кросс");
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
					log.info("Кросс изменен: {} => {}", oldDFrame, dframe);
					iSys.changes = true;
					util_newInfo("Изменения сохранены");
				}
				else {
					DFramе newDFrame = new DFramе(); 
					newDFrame.setName(textField.getText()); 
					newDFrame.attachToNet((Net)iSys.nc.getOnlyElement());
					newDFrame.setPlacesCount((Integer)placesBox.getSelectedItem());
					iSys.dfc.addElement(newDFrame);
					if (wrapper != null) wrapper.setElement(newDFrame);
					String mes = "Создан кросс: "+ newDFrame.toString()+ ", присоединён к сети: "+((Net)iSys.nc.getOnlyElement()).toString();
					log.info(mes);
					iSys.changes = true;
					util_newInfo(mes);
				}
				iDialog.dispose();
			}
		});
		//iFrame.setVisible(true);
	}
}