/**
 * Создает и выводит на экран форму создания/редактирования элемента "Абонент"
 * @param sub - абонент для редактирования, если null - отображается форма создания нового абонента
 */
package org.teleline.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.teleline.model.Net;
import org.teleline.model.Path;
import org.teleline.model.Subscriber;
import org.teleline.system.Sys;


public class FormSubscriber extends Form {

	public FormSubscriber(final Sys iSys, final Subscriber sub) {
		super(iSys);
		final int iFrameMinWidth = 410, iFrameMaxWidth = 830, iFrameMinHeight = 220, iFrameMaxHeight = 280;
		
		createDialog("Создать абонента", iFrameMinWidth, iFrameMinHeight);
		
		addLabel("Имя абонента (1-50 символов):", 20, 15, 360, 25);
		final JTextField name = addTextField(20, 40, 360, 25);
		
		addLabel("Телефонный номер (3-7 цифр):", 20, 75, 360, 25);
		final JTextField phoneNumber = addTextField(20, 100, 360, 25);

		/*
		 * Дополнительные параметры абонента
		 */
		addLabel("Адрес:", 420, 15, 360, 25);
		final JTextField subscriberAdress = addTextField(420, 40, 360, 25);
		
		addLabel("Дата установки:", 420, 75, 360, 25);
		final JTextField subscriberDate = addTextField(420, 100, 360, 25);
		
		addLabel("Тип оборудования:", 420, 135, 360, 25);
		final JTextField subscriberEquipment = addTextField(420, 160, 360, 25);
		
		if (sub != null) {
			iFrame.setTitle("Редактировать абонента");
			name.setText(sub.getName());
			phoneNumber.setText(sub.getPhoneNumber());
			subscriberAdress.setText(sub.getAdress());
			subscriberDate.setText(sub.getDate());
			subscriberEquipment.setText(sub.getEquipment());
		}
		/*
		 * ------------------------------
		 */
        JButton saveButton = addButton("Сохранить", 20, 140, 110, 25);
        saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Net selectedNet = (Net)iSys.nc.getOnlyElement();
										
				if (!iSys.v.validateSubscriberName(name.getText())) {util_newError("Неверный формат имени абонента!"); return;}
				if (!iSys.v.validatePhoneNumber(phoneNumber.getText())) {util_newError("Неверный формат номера телефона!"); return;}
				if (!iSys.v.validateOtherParametr(subscriberAdress.getText())) { util_newError("Неверный формат адреса абонента (до 150 символов)!"); return; }
				if (!iSys.v.validateOtherParametr(subscriberEquipment.getText())) { util_newError("Неверный формат типа оборудования (до 150 символов)!"); return; }
				if (!iSys.v.validateOtherParametr(subscriberDate.getText())) { util_newError("Неверный формат даты установки абонента (до 150 символов)!"); return; }
				
				Subscriber s = (Subscriber)iSys.sc.findByPhoneNumber(phoneNumber.getText(), selectedNet.getId());
										
				if (sub != null) {
					if (s != null && sub.getId().equals(s.getId()) == false) 
						if (util_newDialog("Абонент с таким телефонным номером уже сущесвует в этой сети! \r\n Создать еще одного абонента с данным номером?") == JOptionPane.NO_OPTION) { return; }
					
					String oldSub = sub.toString();
					sub.setName(name.getText());
					sub
						.setPhoneNumber(phoneNumber.getText())
						.setDate(subscriberDate.getText())
						.setAdress(subscriberAdress.getText())
						.setEquipment(subscriberEquipment.getText());
					iSys.rw.addLogMessage("Абонент изменен: " + oldSub + " => " + sub.toString());
					util_newInfo("Изменения сохранены");
				}
				else {
					if (s != null)
						if (util_newDialog("Абонент с таким телефонным номером уже сущесвует в этой сети! \r\n Создать еще одного абонента с данным номером?") == JOptionPane.NO_OPTION) { return; }
					
					Subscriber newSubscriber = new Subscriber();
					Path newPath = new Path(iSys.sc,iSys.pc);
					
					newSubscriber
						.setDate(subscriberDate.getText())
						.setAdress(subscriberAdress.getText())
						.setEquipment(subscriberEquipment.getText())
						.attachToNet(selectedNet)
						.setName(name.getText());
					newSubscriber.setPhoneNumber(phoneNumber.getText());
								
					iSys.sc.addElement(newSubscriber);
					newPath.setSubscriber(newSubscriber);
					iSys.phc.addElement(newPath);
					
					String mes = "Создан абонент: "+ newSubscriber.toString()+ ", добавлен в сеть: "+ selectedNet.toString();
					iSys.rw.addLogMessage(mes);
					util_newInfo(mes);
				}
				iFrame.dispose();
			}
		});
		addMoreButton(iFrameMinWidth,iFrameMaxWidth,iFrameMinHeight, iFrameMaxHeight, 320, 140, 60, 25);
		iFrame.setVisible(true);
	}
}