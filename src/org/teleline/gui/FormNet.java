/**
 * Создает и выводит на экран форму создания/редактирования элемента Сеть
 * @param net - сеть для редактирования, если null - отображается форма создания новой сети
 */
package org.teleline.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;

import org.teleline.model.Net;

import system.Sys;

public class FormNet extends Form {

	public FormNet(final Sys iSys, final Net net) {
		super(iSys);
		createDialog("Создать сеть", 410, 165);
		addLabel("Название сети (1-50 символов):", 20, 15, 360, 14);
		
		final JTextField name = addTextField(20, 40, 360, 25);
		if (net != null){ 
			iFrame.setTitle("Редактировать сеть");
			name.setText(net.getName());
		}
		
		JButton saveButton = addButton("Сохранить", 20, 75, 110, 25);
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!iSys.v.validateNetName(name.getText())) { util_newError("Неверный формат названия сети!");return;}
				if (net != null) {
					Net oldNet =  net;
					net.setName(name.getText());
					iSys.rw.addLogMessage("Сеть изменена:" + oldNet.toString() +" => " + net.toString());
					util_newInfo("Изменения сохранены");
				}
				else {
					Net newNet = new Net(); 
					newNet.setName(name.getText()); 
					iSys.nc.addElement(newNet);
					String mes = "Создана сеть: "+ newNet.toString();
					iSys.rw.addLogMessage(mes);
					util_newInfo(mes);
				}
				iFrame.dispose();
			}
		});
		
		iFrame.setVisible(true);
	}
	
}