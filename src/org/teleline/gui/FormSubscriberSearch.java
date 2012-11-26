package org.teleline.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JTextField;

import org.teleline.model.Sys;
/**
*Класс элементов Форма для формы поиска абонента по номеру и телефону
*
*/
public class FormSubscriberSearch extends Form{
	
	public JTextField textField;
	public JTextField textField_1;
	public JList subscriberList;

	public JButton findByPhoneButton;
	public JButton findByNameButton;
	public JButton okButton;
	
	public FormSubscriberSearch(final Sys iSys) {
		super(iSys);
		// TODO Auto-generated constructor stub
		
		createDialog("Найти абонента", 485, 580);
		addLabel("Телефонный номер:", 10, 10, 320, 14);
		final JTextField textField = addTextField(10, 30, 320, 25);
		addLabel("Имя:", 10, 65, 320, 14);
		final JTextField textField_1 = addTextField(10, 85, 320, 25);
		addLabel("Результаты поиска:", 10, 120, 320, 14);
		subscriberList = addList(10, 140, 320, 400);

		findByPhoneButton = addButton("Найти", 340, 30, 125, 26);
		findByNameButton = addButton("Найти", 340, 85, 125, 26);
		okButton = addButton("Выбрать", 340, 140, 125, 26);
		
		final Integer netId = iSys.nc.getOnlyElement().getId();
		/*
		 * Событие кнопки поиска абонента по телефону
		 */
		findByPhoneButton.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				util_setListItems(subscriberList, iSys.sc.searchByPhone(textField.getText(), netId));
			}
		});
		/*
		 * ---------------------------------------------------------
		 */
		/*
		 * Событие кнопки поиска абонента о телефону
		 */
		findByNameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				util_setListItems(subscriberList, iSys.sc.searchByName(textField_1.getText(), netId));				
			}
		});
		
		/*
		 * ---------------------------------------------------------
		 */
		iFrame.setVisible(true);
	}
}