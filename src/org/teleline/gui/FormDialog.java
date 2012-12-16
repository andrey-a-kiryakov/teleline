package org.teleline.gui;

import java.awt.Dialog.ModalExclusionType;
import java.awt.Dialog.ModalityType;
import java.awt.Window;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.teleline.model.AbstractElement;
import org.teleline.system.Sys;

public abstract class FormDialog {
	
	JDialog iFrame;
	Sys iSys;
	protected static final Logger log = LoggerFactory.getLogger("sys");
	Window owner;
	
	public FormDialog(Window owner, Sys iSys) {
		
			
			this.iSys = iSys;
			owner = owner;
			
		
	}
	
	protected JDialog createDialog (String title, int width, int height) {
		
		iFrame = new JDialog(owner, ModalityType.TOOLKIT_MODAL  );
		//iFrame.setModalExclusionType();
		iFrame.setSize(width, height);
		iFrame.setLocationRelativeTo(iFrame);
		//iFrame.setTitle(title);
		//iFrame.setResizable(false);
		iFrame.getContentPane().setLayout(null);
		return iFrame;
	}
	
	/**
	 * Добавляет кнопку к форме
	 * @return кнопка
	 */
	public JButton addButton(String Text, int x, int y, int w, int h) {
		
		JButton button = new JButton(Text);
		button.setBounds(x, y, w, h);
		iFrame.getContentPane().add(button);
		
		return button;
	}
	/**
	 * Добавляет надпись к форме
	 * @return надпись
	 */
	public JLabel addLabel(String Text, int x, int y, int w, int h) {
		
		JLabel newLabel = new JLabel(Text);
		newLabel.setBounds(x, y, w, h);
		iFrame.getContentPane().add(newLabel);
		
		return newLabel;
	}
	/**
	 * Добавляет выпадающий список к форме
	 * @return выпадающий список
	 */
	public JComboBox addComboBox(int x, int y, int w, int h) {
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(x, y, w, h);
		iFrame.getContentPane().add(comboBox);
		
		return comboBox;
	}
	/**
	 * Добавляет текстовое поле к форме
	 * @return текстовое поле
	 */
	public JTextField addTextField(int x, int y, int w, int h) {
		
		JTextField textField = new JTextField();
		textField.setBounds(x, y, w, h);
		iFrame.getContentPane().add(textField);
		
		return textField;
	}
	public void util_newInfo (String mes) {JOptionPane.showMessageDialog(iFrame, mes, "Информация", JOptionPane.INFORMATION_MESSAGE);}
	
	public void util_newError (String mes) {JOptionPane.showMessageDialog(iFrame, mes, "Ошибка", JOptionPane.ERROR_MESSAGE);}
	
	/**
	 * Устанавливает (добавляет) элементы выпадающего списка
	 * @param ComboBox - выпадающий список
	 * @param Collection - коллекция элементов
	 */
	public void util_setComboBoxItems(final JComboBox ComboBox, Collection<?> Collection) {
		
		Iterator<?> i = Collection.iterator();
		while (i.hasNext())  {ComboBox.addItem((AbstractElement)i.next());}
		
	}
	
	
}