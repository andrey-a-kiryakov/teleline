/**
 * Создает и выводит на экран форму создания/редактирования элемента "Бокс"
 * @param box - элемент "Бокс", если null - выводится форма создания нового элемента
 */
package org.teleline.gui;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import org.teleline.model.Box;
import org.teleline.model.Cabinet;
import org.teleline.model.ConnectedPointElement;
import org.teleline.model.StructuredElement;
import org.teleline.system.Sys;


public class FormBox extends FormJDialog {
	
	private JComboBox typeComboBox;
	private JComboBox capacityComboBox;
	public JComboBox placeComboBox;
	private JComboBox cabinetsComboBox;
	private JTextField numberTextField;
	public JButton saveButton;
	
	public FormBox(Window owner, final Sys iSys, final Box box, final StructuredElement cabinet) {
		super(owner, iSys);
		
		createDialog("Создать бокс", 410, 385);
		if (box != null) iDialog.setTitle("Редактировать бокс");
		addLabel("Тип бокса:", 20, 10, 360, 25);
		typeComboBox = addComboBox(20, 35, 360, 25);
		typeComboBox.addItem("Магистральный");
		typeComboBox.addItem("Передаточный");
		typeComboBox.addItem("Распределительный");
		typeComboBox.addItem("Универсальный");
		typeComboBox.setSelectedIndex(0);
		
		if (box != null) {
			typeComboBox.setSelectedIndex(box.getType());
			typeComboBox.setEnabled(false);
		}
		
		addLabel("Добавить в шкаф:", 20, 65, 360, 25);
		cabinetsComboBox = addComboBox(20, 90, 360, 25);
		util_setComboBoxItems(cabinetsComboBox, iSys.cbc.sortByNumberUp(iSys.cbc.getElements()));
		
		if (cabinet != null) {
			cabinetsComboBox.setSelectedItem(cabinet);
			cabinetsComboBox.setEnabled(false);
		}
		
		addLabel("Номер бокса (0-999):", 20, 120, 360, 25);
		numberTextField = addTextField(20, 145, 360, 25);
		
		if (box != null) numberTextField.setText(box.getNumber().toString());
		
		addLabel("Емкость бокса:", 20, 175, 360, 25);
		capacityComboBox = addComboBox(20, 200, 360, 25);
		capacityComboBox.addItem((Integer)50);
		capacityComboBox.addItem((Integer)100);
		capacityComboBox.setSelectedIndex(1);
		
		if (box != null) {
			capacityComboBox.setSelectedItem(box.getCapacity());
			capacityComboBox.setEnabled(false);
		}
		
		addLabel("Место в шкафу:", 20, 230, 360, 25);
		placeComboBox = addComboBox(20, 255, 360, 25);
		
		cabinetsComboBox.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				placeComboBox.removeAllItems();
				
				if (cabinetsComboBox.getSelectedIndex() > -1) {
					StructuredElement cab = (StructuredElement)cabinetsComboBox.getSelectedItem();
					
					for (int i = 0; i < cab.getPlacesCount(); i++) {
						int k = 0;
						Iterator<?> it = iSys.bc.getInOwner(cab.getId()).iterator();
						while (it.hasNext())  if (((ConnectedPointElement)it.next()).getPlaceNumber().intValue() == i)  { k = 1; break;}
						if (k == 0) placeComboBox.addItem((Integer)i);
					}
				}
			}
		});
		
		placeComboBox.removeAllItems();
		
		if (cabinetsComboBox.getSelectedIndex() > -1) {
			StructuredElement cab = (StructuredElement)cabinetsComboBox.getSelectedItem();
			
			for (int i = 0; i < cab.getPlacesCount(); i++) {
				int k = 0;
				Iterator<?> it = iSys.bc.getInOwner(cab.getId()).iterator();
				while (it.hasNext())  if (((ConnectedPointElement)it.next()).getPlaceNumber().intValue() == i)  { k = 1; break;}
				if (k == 0) placeComboBox.addItem((Integer)i);
			}
		}
		
		if (box != null) {
			placeComboBox.addItem(box.getPlaceNumber());
			placeComboBox.setSelectedItem(box.getPlaceNumber());
		}
		
		saveButton = addButton("Сохранить", 20, 310, 110, 25);
		
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if (cabinetsComboBox.getSelectedIndex() == -1) { util_newError("Не выбран шкаф!"); return; }
				if (placeComboBox.getSelectedIndex() == -1) { util_newError("Не выбрано место!"); return; }
				if (!iSys.v.validateBoxNumber(numberTextField.getText())) { util_newError("Не верный номер бокса!"); return; }
				
				Cabinet selectedCabinet = (Cabinet)cabinetsComboBox.getSelectedItem();
				Integer boxNumber = iSys.rw.valueOf(numberTextField.getText());
				
				if (box != null) {
					Box b = iSys.bc.inOwner(boxNumber, selectedCabinet.getId(), typeComboBox.getSelectedIndex());
					Box bx = (Box)iSys.bc.getInPlace((Integer)placeComboBox.getSelectedItem(), selectedCabinet.getId());
					
					if (b != null && !box.getId().equals(b.getId())) {util_newError("Бокс с таким типом и номером уже сущесвует в этом шкафу!"); return;}
					if (bx != null && !box.getId().equals(bx.getId())) {util_newError("Данное место в шкафу занято!"); return;}
					
					String old = box.toString();
					box.setType((Integer)typeComboBox.getSelectedIndex());
					box
						.attachTo(selectedCabinet)
						.setNumber(boxNumber)
						.setPlaceNumber((Integer)placeComboBox.getSelectedItem())
						.setCapacity((Integer)capacityComboBox.getSelectedItem());
					
					box.setNumber(boxNumber);
					log.info("Бокс изменен: {} => {}",old,box);
					iSys.changes = true;
					util_newInfo("Изменения сохранены");
					
				}
				else {
					if (iSys.bc.getInPlace((Integer)placeComboBox.getSelectedItem(), selectedCabinet.getId()) != null) { util_newError("Данное место в шкафу занято!"); return;}
					if (iSys.bc.inOwner(boxNumber, selectedCabinet.getId(), typeComboBox.getSelectedIndex()) != null ) {
						util_newError("Бокс с таким типом и номером уже сущесвует в этом шкафу!"); return;
					}
					Box newBox = new Box();
					newBox.setType((Integer)typeComboBox.getSelectedIndex());
					newBox
					.attachTo(selectedCabinet)
					.setNumber(boxNumber)
					.setPlaceNumber((Integer)placeComboBox.getSelectedItem())
					.setCapacity((Integer)capacityComboBox.getSelectedItem());
					
					iSys.bc.addElement(newBox);
					String mes = "Создан "+(String)typeComboBox.getSelectedItem()+" бокс: "+newBox.toString()+ ", добавлен в шкаф: "+ selectedCabinet.toString();
					log.info(mes);
					iSys.changes = true;
					util_newInfo(mes);
				}
				iDialog.dispose();
			}
		});
//	iFrame.setVisible(true);
	}
}