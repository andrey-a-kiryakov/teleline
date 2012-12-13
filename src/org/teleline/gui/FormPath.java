/**
 * Создает и выводит на экран форму создания/редактирования элемента Включение
 * @param sub - абонент, для которого создается включение
 * @param path - включение для редактирования, если null - отображается форма создания нового включения
 */
package org.teleline.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;

import org.teleline.model.Path;
import org.teleline.model.Subscriber;
import org.teleline.system.Sys;


public class FormPath extends Form {

	public FormPath(final Sys iSys, final Subscriber sub, final Path path) {
		super(iSys);
		
		createDialog("Создать включение", 410, 225);
		addLabel("Название включения (1-50 символов):", 20, 15, 360, 14);
		final JTextField textField = addTextField(20, 40, 360, 25);
		
		addLabel("Пара перехода (Ц-ЦЦ):", 20, 75, 360, 14);
		final JTextField transitText = addTextField(20, 100, 360, 25);
		
		if (path != null){ 
			iFrame.setTitle("Редактировать включение");
			textField.setText(path.getName());
			transitText.setText(path.getTransit());
		}
		
		JButton saveButton = addButton("Сохранить", 20, 135, 110, 25);
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!iSys.v.validatePathName(textField.getText())) { util_newError("Неверный формат названия включения!");return;}
				if (!iSys.v.validatePathTransit(transitText.getText())) { util_newError("Неверный формат пары перехода!");return;}
				
				if (path != null) {
					Path oldPath =  path;
					path.setName(textField.getText());
					path.setTransit(transitText.getText());
					log.info("Включение изменено: {} => {}", oldPath, path);
					util_newInfo("Изменения сохранены");
				}
				else {
					Path newPath = new Path(iSys.sc,iSys.pc); 
					newPath.setName(textField.getText());
					newPath.setTransit(transitText.getText());
					newPath.setSubscriber(sub);
					iSys.phc.addElement(newPath);
					String mes = "Создано включение: "+ newPath.toString() + ", для абонента " + sub.toString();
					log.info(mes);
					util_newInfo(mes);
				}
				iFrame.dispose();
			}
		});
		
		iFrame.setVisible(true);
	}
}