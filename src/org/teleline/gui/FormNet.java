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
import org.teleline.system.Sys;


public class FormNet extends FormJFrame {

	public FormNet(final Sys iSys, final Net net) {
		super(iSys);
		createFrame("Создать сеть", 410, 165);
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
					log.info("Сеть изменена: {} => {}",oldNet,net );
					iSys.changes = true;
					util_newInfo("Изменения сохранены");
				}
				else {
					Net newNet = new Net(); 
					newNet.setName(name.getText()); 
					iSys.nc.addElement(newNet);
					String mes = "Создана сеть: "+ newNet.toString();
					log.info(mes);
					iSys.changes = true;
					util_newInfo(mes);
				}
				iFrame.dispose();
			}
		});
		
		iFrame.setVisible(true);
	}
	
}