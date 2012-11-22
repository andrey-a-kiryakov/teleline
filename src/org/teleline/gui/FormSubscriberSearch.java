package org.teleline.gui;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JTextField;

import org.teleline.model.Sys;
/**
*Класс элементов Форма для формы поиска абонента по номеру и телефону
*
*/
public class FormSubscriberSearch extends Form{
	
	public FormSubscriberSearch(Sys iSys) {
		super(iSys);
		// TODO Auto-generated constructor stub
	}
	public JTextField textField;
	public JTextField textField_1;
	public JList subscriberList;

	public JButton findByPhoneButton;
	public JButton findByNameButton;
	public JButton okButton;
	
}