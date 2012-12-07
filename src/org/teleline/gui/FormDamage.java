/**
 * Создает и выводит на экран форму создания/редактирования повреждения
 * @param damage - повреждение для редактирования, если null - отображается форма создания нового повреждения
 * @return повреждение
 */
package org.teleline.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.teleline.model.AbstractElement;
import org.teleline.model.Damage;
import org.teleline.system.Sys;


public class FormDamage extends Form {

	public FormDamage(final Sys iSys, final Damage damage, final AbstractElement owner) {
		super(iSys);
		
		createDialog("Создать повреждение", 410, 445);
		
		addLabel("Дата обнаружения (ДД.ММ.ГГГГ):",  20, 15, 360, 25);
		final JTextField openDate = addTextField(20, 40, 360, 25);
		
		addLabel("Дата устранения (ДД.ММ.ГГГГ):",20, 75, 360, 14);
		final JTextField closeDate = addTextField(20, 100, 360, 25);
		
		addLabel("Характер повреждения (до 300 символов):", 20, 135, 360, 14);
		final JTextArea name = addTextArea(20, 160, 360, 75);
		name.setEditable(true);
		
		addLabel("Работы по устранению (до 300 символов):", 20, 250, 360, 14);
		final JTextArea description = addTextArea(20, 275, 360, 75);
		description.setEditable(true);
		
		if (damage != null){ 
			iFrame.setTitle("Редактировать повреждение");
			openDate.setText(damage.getOpenDate());
			closeDate.setText(damage.getCloseDate());
			name.setText(damage.getName());
			description.setText(damage.getDescription());
		}
		
		JButton saveButton = addButton("Сохранить", 20, 370, 110, 25);
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if (!iSys.v.validateDateRequired(openDate.getText())) { util_newError("Неверный формат даты обнаружения!");return;}
				if (!iSys.v.validateDate(closeDate.getText())) { util_newError("Неверный формат даты устранения!");return;}	
				if (!iSys.v.validateLongParametr(name.getText())) { util_newError("Неверный формат характера повреждения!");return;}
				if (!iSys.v.validateLongParametr(description.getText())) { util_newError("Неверный формат описания повреждения!");return;}
				
				if (damage != null) {
					
					damage.setName(name.getText());
					damage.setOpenDate(openDate.getText());
					damage.setCloseDate(closeDate.getText());
					damage.setDescription(description.getText());
					iSys.rw.addLogMessage("Повреждение изменено:"  + damage.toString());
					util_newInfo("Изменения сохранены");
				}
				else {
					Damage newDamage = new Damage(); 
					newDamage.setName(name.getText());
					newDamage.setOpenDate(openDate.getText());
					newDamage.setCloseDate(closeDate.getText());
					newDamage.setDescription(description.getText());
					newDamage.attachTo(owner);
					iSys.dmc.addElement(newDamage);
					String mes = "Создано повреждение: "+ newDamage.toString();
					iSys.rw.addLogMessage(mes);
					util_newInfo(mes);
				}
				iFrame.dispose();
			}
		});
		iFrame.setVisible(true);
	}
	
}