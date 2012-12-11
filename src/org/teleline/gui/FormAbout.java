package org.teleline.gui;

import org.teleline.system.Sys;

public class FormAbout extends Form {

	public FormAbout(Sys iSys) {
		super(iSys);
		createDialog("О программе", 300, 130);
		addLabel("Teleline", 20, 20, 100, 25);
		addLabel("Версия 1.0.4", 20, 40, 100, 25);
		iFrame.setVisible(true);
	}
	
}