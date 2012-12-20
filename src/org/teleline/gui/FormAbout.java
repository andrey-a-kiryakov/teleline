package org.teleline.gui;

import org.teleline.system.Sys;

public class FormAbout extends FormJFrame {

	public FormAbout(Sys iSys) {
		super(iSys);
		createFrame("О программе", 300, 130);
		addLabel("Teleline", 20, 20, 100, 25);
		addLabel("Версия 1.1.3", 20, 40, 100, 25);
		iFrame.setVisible(true);
	}
}