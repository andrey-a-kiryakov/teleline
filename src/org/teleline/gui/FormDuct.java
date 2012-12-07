/**
 * Создает и выводит на экран форму создания/редактирования элемента "Кабельная канализация"
 * @param duct - элемент "Кабельная канализация", если null - выводится форма создания нового элемента
 */
package org.teleline.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import org.teleline.model.AbstractElement;
import org.teleline.model.Duct;
import org.teleline.model.Net;
import org.teleline.model.StructuredElement;
import org.teleline.model.Tube;
import org.teleline.system.Sys;


public class FormDuct extends Form {

	public FormDuct(final Sys iSys, final Duct duct) {
		super(iSys);
		final int iFrameMinWidth = 410, iFrameMaxWidth = 830, iFrameMinHeight =  390, iFrameMaxHeight =  430;

		createDialog("Создать канализацию", iFrameMinWidth, iFrameMinHeight);
		
		Integer netId = iSys.nc.getOnlyElement().getId();
		
		addLabel("От:", 20, 15, 360, 25);
		final JComboBox fromComboBox = addComboBox(20, 40, 360, 25);
		util_setComboBoxItems(fromComboBox, iSys.mc.sortByIdUp(iSys.mc.getInNet(netId)));
		util_setComboBoxItems(fromComboBox, iSys.dfc.sortByIdUp(iSys.dfc.getInNet(netId)));

		final JComboBox fromSideComboBox = addComboBox(20, 75, 360, 25);
		fromSideComboBox.addItem("Спереди");
		fromSideComboBox.addItem("Справа");
		fromSideComboBox.addItem("Сзади");
		fromSideComboBox.addItem("Слева");
		
		addLabel("До:",  20, 115, 360, 25);
		final JComboBox toComboBox = addComboBox(20, 140, 360, 25);
		util_setComboBoxItems(toComboBox, iSys.mc.sortByIdUp(iSys.mc.getInNet(netId)));
		util_setComboBoxItems(toComboBox, iSys.cbc.sortByIdUp(iSys.cbc.getInNet(netId)));
		util_setComboBoxItems(toComboBox, iSys.buc.sortByIdUp(iSys.buc.getInNet(netId)));
	
		final JComboBox toSideComboBox = addComboBox(20, 175, 360, 25);
		toSideComboBox.addItem("Спереди");
		toSideComboBox.addItem("Справа");
		toSideComboBox.addItem("Сзади");
		toSideComboBox.addItem("Слева");
		
	
		addLabel("Каналов в канализации (1-99):",  20, 215, 360, 25);
		final JTextField capacityText = addTextField( 20, 240, 360, 25);
		
		/*
		 * Дополнительные параметры канализации
		 */
		addLabel("Длина, м (1-9999):",  420, 15, 360, 25);
		final JTextField ductLenght = addTextField( 420, 40, 360, 25);
		ductLenght.setText("1");
		
		addLabel("Диаметр канала, мм (1-999):",  420, 75, 360, 25);
		final JTextField tubeDiametr = addTextField( 420, 100, 360, 25);
		tubeDiametr.setText("100");
		
		addLabel("Материал трубопровода (до 150 сим.):",  420, 135, 360, 25);
		final JTextField tubeMaterual = addTextField( 420, 160, 360, 25);
		
		addLabel("Дата прокладки (ДД.ММ.ГГГГ):",  420, 195, 360, 25);
		final JTextField ductDate = addTextField( 420, 220, 360, 25);
		ductDate.setText("01.01.2012");
		
		addLabel("Cпособ изготовления (до 150 сим.):",  420, 255, 360, 25);
		final JTextField manufacturingМethod = addTextField( 420, 280, 360, 25);
		
		
		
		JButton damageButton = addButton("Повреждения",  420, 340, 120, 25);
		damageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new FormDamages(iSys, iSys.dmc.getDamages(duct), duct);
			}
		});
		damageButton.setEnabled(false);
		
		if (duct != null) {
			ductLenght.setText(duct.getLenght().toString());
			tubeDiametr.setText(duct.getTubeDiametr().toString());
			tubeMaterual.setText(duct.getTubeMaterial());
			ductDate.setText(duct.getDate());
			manufacturingМethod.setText(duct.getМanufacturingМethod());
			damageButton.setEnabled(true);
		}
		/*
		 * ------------------------------
		 */
		if (duct != null) {
			iFrame.setTitle("Редактировать канализацию");
			
			AbstractElement from = iSys.mc.getElement(duct.getFrom());
			if (from == null) from = iSys.dfc.getElement(duct.getFrom());
			
			AbstractElement to = iSys.mc.getElement(duct.getTo());
			if (to == null) to = iSys.cbc.getElement(duct.getTo());
			if (to == null) to = iSys.buc.getElement(duct.getTo());
			
			
			fromComboBox.setSelectedItem(from);
			toComboBox.setSelectedItem(to);
			
			fromSideComboBox.setSelectedIndex(duct.getFromSide());
			toSideComboBox.setSelectedIndex(duct.getToSide());
			
			capacityText.setText(((Integer)iSys.tuc.getDuctsTubes(duct).size()).toString()); capacityText.setEnabled(false);
		}
        
		JButton saveButton = addButton("Сохранить",  20, 290, 110, 25);
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (fromComboBox.getSelectedIndex() == -1) { util_newError( "Не выбрано начало канализации!"); return; }
				if (toComboBox.getSelectedIndex() == -1) { util_newError( "Не выбран конец канализации!"); return; }
				if (!iSys.v.validateDuctCapacity(capacityText.getText())) { util_newError( "Неверный формат емкости канализации!"); return; }
				if (!iSys.v.validateDuctLenght(ductLenght.getText())) { util_newError( "Неверный формат длины канализации!"); return; }
				if (!iSys.v.validateTubeDiametr(tubeDiametr.getText())) { util_newError( "Неверный формат диаметра канала!"); return; }
				if (!iSys.v.validateDate(ductDate.getText())) { util_newError( "Неверный формат даты!"); return; }
				if (!iSys.v.validateOtherParametr(tubeMaterual.getText())) { util_newError( "Неверный формат материала трубопровода!"); return; }
				if (!iSys.v.validateOtherParametr(manufacturingМethod.getText())) { util_newError( "Неверный формат способа изготовления!"); return; }
				
				Integer capacity = iSys.rw.valueOf(capacityText.getText());
				StructuredElement elementFrom = (StructuredElement)fromComboBox.getSelectedItem();
				StructuredElement elementTo = (StructuredElement)toComboBox.getSelectedItem();
				Integer fromSide = fromSideComboBox.getSelectedIndex();
				Integer toSide = toSideComboBox.getSelectedIndex();

				if (elementFrom.getId().equals(elementTo.getId())) {util_newError( "Выберите разные элементы От и До!"); return; }

				if (duct != null) {
					
			//		if (d1 != null && !duct.getId().equals(d1.getId())) { util_newError( "К элементу От " + elementFrom.toString() + " с выбранной стороны уже примыкает участок канализации " + d1.toString()); return; }
			//		if (d2 != null && !duct.getId().equals(d2.getId())) { util_newError( "К элементу До " + elementTo.toString() + " с выбранной стороны уже примыкает участок канализации " + d2.toString()); return; }
					
				/*	
					Cabinet b = cbc.elementInNet(cabinetNumber, selectedNet.getId());
					if (b != null && !cabinet.getId().equals(b.getId())) {util_newError( "Шкаф с номером "+cabinetNumber+" уже сущесвует в этой сети"); return;}
				*/	
					String old = duct.toString();
					duct
						.setFromSide(fromSide)
						.setToSide(toSide)
						.setLenght(iSys.rw.valueOf(ductLenght.getText()))
						.setTubeDiametr(iSys.rw.valueOf(tubeDiametr.getText()))
						.setDate(ductDate.getText())
						.setTubeMaterial(tubeMaterual.getText())
						.setМanufacturingМethod(manufacturingМethod.getText())
						.setFrom(elementFrom.getId())
						.setTo(elementTo.getId());
					iSys.rw.addLogMessage("Участок канализации изменен: " + old + " => " + duct.toString());
					util_newInfo( "Изменения сохранены");
				}
				else {		
					Duct newDuct = new Duct(iSys.dfc, iSys.cbc, iSys.mc, iSys.buc); 
					newDuct
						.setFromSide(fromSide)
						.setToSide(toSide)
						.setLenght(iSys.rw.valueOf(ductLenght.getText()))
						.setTubeDiametr(iSys.rw.valueOf(tubeDiametr.getText()))
						.setDate(ductDate.getText())
						.setTubeMaterial(tubeMaterual.getText())
						.setМanufacturingМethod(manufacturingМethod.getText())
						.setFrom(elementFrom.getId())
						.setTo(elementTo.getId())
						.attachToNet((Net)iSys.nc.getOnlyElement());
					iSys.duc.addElement(newDuct);
					String mes = "Создан участок канализации: "+ newDuct.toString()+ ", добавлен в сеть: "+ iSys.nc.getOnlyElement().toString()/*selectedNet.toString()*/;
					iSys.rw.addLogMessage(mes);
					
					for (int i = 0; i < capacity; i++) {
						Tube tube = new Tube();
						tube.setNumber(i);
						tube.setDuct(newDuct);
						iSys.tuc.addElement(tube);
						iSys.rw.addLogMessage("Создан канал №" + tube.getNumber().toString() + "в канализации " + newDuct.toString());
					}
					util_newInfo(mes);
				}
				iFrame.dispose();			
			}
		});
		
		addMoreButton(iFrameMinWidth, iFrameMaxWidth, iFrameMinHeight, iFrameMaxHeight, 320, 290, 60, 25);

		iFrame.setVisible(true);
	}
	
}