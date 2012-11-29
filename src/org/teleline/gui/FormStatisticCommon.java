package org.teleline.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextArea;
import org.teleline.model.Sys;


public class FormStatisticCommon extends Form {
	
	private JTextArea info;
	
	public FormStatisticCommon(Sys iSys) {
		super(iSys);
		
		createDialog("Общая статистика", 500, 600);
		
		info = addTextArea(10,10,480,560);
		getStatistic();
		iFrame.setVisible(true);
	}
	
	private void getStatistic() {
		
		info.append("Сеть: " + iSys.nc.getOnlyElement().toString()+"\r\n" );
		info.append("Кроссов: " + iSys.dfc.getSize() + "\r\n");
		info.append("Шкафов: " + iSys.cbc.getSize() + "\r\n");
		info.append("КРТ: " + iSys.dbc.getSize() + "\r\n");
		info.append("Зданий: " + iSys.buc.getSize() + "\r\n");
		info.append("Колодцев: " + iSys.mc.getSize() + "\r\n");
		info.append("Участков канализации: " + iSys.duc.getSize() + "\r\n");
		info.append("Каналов канализации: " + iSys.tuc.getSize() + "\r\n");
		info.append("Кабелей : " + iSys.cc.getSize() + "\r\n");
		info.append("Громполоc: " + iSys.fc.getSize() + "\r\n");
		info.append("Боксов: " + iSys.bc.getSize() + "\r\n");
		info.append("Включений: " + iSys.phc.getSize() + "\r\n");
		info.append("Пар: " + iSys.pc.getSize() + "\r\n");
		info.append("Абонентов: " + iSys.sc.getSize() + "\r\n");
		info.append("Повреждений: " + iSys.dmc.getSize() + "\r\n");
	}
	
}