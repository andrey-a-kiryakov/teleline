package org.teleline.gui;

import java.util.HashSet;
import java.util.Iterator;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;

import org.teleline.model.AbstractElement;
import org.teleline.model.Pair;
import org.teleline.model.Path;
import org.teleline.model.PathCollection;
import org.teleline.model.Subscriber;
import org.teleline.model.SubscriberCollection;

public class FormPairSubscribers extends Form {
	
	public JList subscriberList;
	public JButton okButton;
	
	public FormPairSubscribers(SubscriberCollection sc, PathCollection phc, Pair pair) {
		super();
		// TODO Auto-generated constructor stub
		this.createDialog("Пара: " + pair.toString(), 485, 270);
		addLabel("Абоненты используюшие пару:", 10, 10, 320, 14);
		subscriberList = addList(10, 30, 320, 200);
		okButton = addButton("Выбрать", 340, 30, 125, 26);
		fillSubscriberList (sc, phc, pair);
		iFrame.setVisible(true);
		
	}
	
	public void fillSubscriberList (SubscriberCollection sc, PathCollection phc, Pair pair) {
		
		HashSet<Subscriber> s = new HashSet<Subscriber>();
		
		Iterator<Path> i = phc.getPairsPath(pair).iterator();
		while (i.hasNext()) s.add((Subscriber)sc.getElement(i.next().getSubscriber()));
	
		((DefaultListModel)subscriberList.getModel()).clear();
		
		Iterator<AbstractElement> listItem = sc.sortByIdUp(s).iterator();
		while (listItem.hasNext()) ((DefaultListModel)subscriberList.getModel()).addElement(listItem.next());
		
		subscriberList.setSelectedIndex(0);
		
	}
}