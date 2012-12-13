/**
 * Создает и выводит на экран форму создания/редактирования элемента "Распределительная коробка"
 * @param dbox - элемент "КРТ", если null - выводится форма создания нового элемента
 */
package org.teleline.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import org.teleline.model.Building;
import org.teleline.model.DBox;
import org.teleline.model.Net;
import org.teleline.system.Sys;


public class FormDBox extends Form {

	public FormDBox(final Sys iSys, final DBox dbox) {
		super(iSys);
		// TODO Auto-generated constructor stub
	
		final int iFrameMinWidth = 410, iFrameMaxWidth = 830, iFrameMinHeight = 250, iFrameMaxHeight = 250;
		
		createDialog("Создать КРТ", iFrameMinWidth, iFrameMinHeight);
		
		addLabel("Здание:", 20, 10, 360, 25);
		final JComboBox buildingsComboBox = addComboBox(20, 35, 360, 25);
		this.util_setComboBoxItems(buildingsComboBox, iSys.buc.sortByIdUp(iSys.buc.getInNet(iSys.nc.getOnlyElement().getId())));
		
		addLabel("Емкость коробки:", 20, 75, 360, 25);
		final JComboBox comboBox1 = addComboBox(20, 100, 360, 25);
		comboBox1.addItem((Integer)10);
		comboBox1.setSelectedIndex(0);
		/*
		 * Дополнительные параметры коробки
		 */
		addLabel("Место расположения (до 150 символов):", 420, 15, 360, 25);
		final JTextField plase = addTextField(420, 40, 360, 25);
		/*
		 * ----------------
		 */
		if (dbox != null) {
			iFrame.setTitle ("Редактировать КРТ");
			buildingsComboBox.setSelectedItem(iSys.buc.getElement(dbox.getBuilding()));
			plase.setText(dbox.getPlase());
		}
		
		JButton saveButton = addButton("Сохранить", 20, 160, 110, 25);
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (buildingsComboBox.getSelectedIndex() == -1) { util_newError("Не выбрано здание!"); return; }
				if (!iSys.v.validateOtherParametr(plase.getText())) { util_newError("Неверный формат места расположения!"); return; }
				if (dbox!= null) {
					String old = dbox.toString();
					dbox
						.setBuilding((Building)buildingsComboBox.getSelectedItem())
						.setPlase(plase.getText());
					log.info("Коробка изменена: {} => {}" ,old, dbox);
					util_newInfo("Изменения сохранены");
				}
				else {
					DBox newDBox = new DBox(iSys.dfc,iSys.cbc,iSys.fc,iSys.bc,iSys.pc,iSys.cc,iSys.buc); 
					newDBox
						.setBuilding((Building)buildingsComboBox.getSelectedItem())
						.setPlase(plase.getText())
						.attachToNet((Net)iSys.nc.getOnlyElement());
					newDBox.setCapacity((Integer)comboBox1.getSelectedItem());				
					iSys.dbc.addElement(newDBox);
					String mes = "Создана коробка: "+ newDBox.toString()+ ", присоединена к сети: "+ iSys.nc.getOnlyElement().toString();
					log.info(mes);
					util_newInfo(mes);
				}
				iFrame.dispose();
			}
		});
		addMoreButton(iFrameMinWidth,iFrameMaxWidth,iFrameMinHeight, iFrameMaxHeight, 320, 160, 60, 25);
		
		iFrame.setVisible(true);
	}	
}