package org.teleline.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.UUID;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.teleline.model.AbstractCollection;
import org.teleline.model.AbstractElement;
import org.teleline.model.Box;
import org.teleline.model.Building;
import org.teleline.model.Cabinet;
import org.teleline.model.Cable;
import org.teleline.model.DBox;
import org.teleline.model.Damage;
import org.teleline.model.Duct;
import org.teleline.model.Frame;
import org.teleline.model.Manhole;
import org.teleline.model.Pair;
import org.teleline.model.Path;
import org.teleline.model.StructuredElement;
import org.teleline.model.Subscriber;
import org.teleline.model.Tube;
import org.teleline.system.Sys;

import sun.security.provider.SHA2;

public class FormOptimizer extends FormJFrame {
	
	private JTable table;
	private DefaultTableModel tableModel;
	
	public JButton dframeButton;
	public JButton cabinetButton;
	public JButton frameButton;
	public JButton boxButton;
	public JButton cableButton;
	public JButton pairButton;
	
	public JTextField idField;
	public JButton idButton;
	public JLabel idLabel;
	
	
	public FormOptimizer(final Sys iSys) {
		super(iSys);
		
		createFrame("Общая статистика", 600, 600);
		
		dframeButton = addButton("Кросс",450,10,110,26);
		cabinetButton = addButton("Шкаф",450,40,110,26);
		frameButton = addButton("ГП",450,70,110,26);
		boxButton = addButton("Бокс",450,100,110,26);
		cableButton = addButton("Кабель",450,130,110,26);
		pairButton = addButton("Пара",450,160,110,26);
		
		NetworkInterface net;
		try {
			//net = NetworkInterface.getByName("eth0");
			byte[] b1 = null;// = net.getHardwareAddress();
			
			Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();

	        while (e.hasMoreElements()) {
	        	NetworkInterface neti = e.nextElement();
	        	//System.out.println(neti);
	            byte[] b = neti.getHardwareAddress();
	            
	            
	            if (b!= null) {
	            	//System.out.println(b.length);
	            	b1 = b;
	            	if (b.length == 6) {
	    	        	System.out.println(neti);

	            		for (int i = 0; i < b.length; i++)
	    	            
	    	            System.out.print(b[i]);
	    	            
	            	System.out.println("");
	            	}
	            }
	       /*     Enumeration<InetAddress> addresses = neti.getInetAddresses( );

	            while (addresses.hasMoreElements( )) {

	              System.out.println(addresses.nextElement( ));    

	            }*/
	            
	        }
			String name = "12345678";
	        UUID uuid = UUID.randomUUID();
	        UUID uuid1 = new UUID (22,33);
	        UUID uuid2 = UUID.nameUUIDFromBytes(b1);
	        UUID uuid3 = UUID.fromString("30-40-50-10-10");
	        
	        System.out.println(uuid);
	        System.out.println(uuid.version());
	        
	        System.out.println(uuid1);
	        System.out.println(uuid1.version());
	        
	        System.out.println(uuid2);
	        System.out.println(uuid2.version());
	        
	        System.out.println(uuid3);
	        System.out.println(uuid3.version());
	        
	        
	       // System.out.println(uuid.timestamp());
	        
	        
			FileOutputStream file = new FileOutputStream("mac");
			/*
			for (int i = 0; i < b1.length; i++)
	            for(int m = b1.length -1 ; m >=0; m--){
	            	//System.out.print(b1[i]b1[m]);
	            	file.write(b1[i]);
	            	file.write(b1[m]<<1);
	            	file.write(b1[m]);
	            	file.write(b1[i]>>1);
	            	
	        }*/
			System.out.println("+");
			
			//file.write(b1);
			file.close();
			File f = new File("mac");
			FileInputStream file1 = new FileInputStream(f);
			byte[] b2 = new byte[(int) f.length()];
			
			file1.read(b2);
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(b2);
			
			for (int i = 0; i < md.digest().length; i++) {
	            System.out.print(md.digest()[i]);
	        }
			
			
			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		
		idField = addTextField(10,430,110,26);
		idLabel = addLabel("0", 130, 430, 40,26);
		idButton = addButton("Посчитать", 10, 460, 110, 26);
			
		table = addTable(10,10,400,400);
		tableModel = (DefaultTableModel) table.getModel();
		tableModel.setColumnIdentifiers(new String[]{"Элемент","ID","Частота"});
		
		dframeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				util_clearTable(table);
				get(iSys.dfc);
			}		
		});
		
		cabinetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				util_clearTable(table);
				get(iSys.cbc);
			}		
		});
		
		frameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				util_clearTable(table);
				get(iSys.fc);
			}		
		});
		
		boxButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				util_clearTable(table);
				get(iSys.bc);
			}		
		});
		
		cableButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				util_clearTable(table);
				get(iSys.cc);
			}		
		});
		
		pairButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				util_clearTable(table);
				get(iSys.pc);
			}		
		});
		
		idButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Integer id = iSys.rw.valueOf(idField.getText());
				idLabel.setText(count(id).toString());
			}		
		});
		
		
		
		
		iFrame.setVisible(true);
	
	}
	
	private void get(AbstractCollection coll) {
		
		Iterator<AbstractElement> i = coll.getIterator();
		while (i.hasNext()) {
			AbstractElement element = i.next();
			
			Vector<Object> v = new Vector<Object>();
			v.add(element); 
			v.add(element.getId());
			v.add(count(element.getId()));
			tableModel.addRow(v);
		}
		
	}
	
	private Integer count (Integer id) {

		Integer counter = 0;
		
		Iterator<AbstractElement> i = iSys.nc.getIterator(); 
		while (i.hasNext())  {
			StructuredElement n = (StructuredElement)i.next();
			if (n.getId().equals(id)) counter++;
		}
		
		i = iSys.dfc.getIterator();
		while(i.hasNext()) {
			
			StructuredElement n = (StructuredElement)i.next();
			if (n.getId().equals(id)) counter++;
			
		}
		
		i = iSys.cbc.getIterator();
		while(i.hasNext()) {
			
			Cabinet f = (Cabinet)i.next();
			if (f.getId().equals(id)) counter++;
			
		}
		i = iSys.dbc.getIterator();
		
		while(i.hasNext()) {

			DBox f = (DBox)i.next();
			if (f.getId().equals(id)) counter++;
		}
		i = iSys.mc.getIterator();
		
		while(i.hasNext()) {
			
			Manhole f = (Manhole)i.next();
			if (f.getId().equals(id)) counter++;
			
		}
		
		i = iSys.duc.getIterator();
		
		while(i.hasNext()) {
			
			Duct f = (Duct)i.next();
			if (f.getId().equals(id)) counter++;
			if (f.getFrom().equals(id)) counter++;
			if (f.getTo().equals(id)) counter++;
		}
		
		i = iSys.tuc.getIterator();
		
		while(i.hasNext()) {
			
			Tube f = (Tube)i.next();
			if (f.getId().equals(id)) counter++;
			if (f.getDuct().equals(id)) counter++;
			
			
			if (f.cablesCount() > 0) {
				
				Iterator<Integer> c = f.getCables().iterator();
				while (c.hasNext()) { 
					if (c.next().equals(id)) 
						counter++;
				}
			}
		}
		
		
		i = iSys.buc.getIterator();
		
		while(i.hasNext()) {
			
			Building f = (Building)i.next();
			if (f.getId().equals(id)) counter++;	
		}
		
		
		i = iSys.fc.getIterator();
		
		while(i.hasNext()) {
			
			Frame f = (Frame)i.next();
			if (f.getId().equals(id)) counter++;
			if (f.getOwnerId().equals(id)) counter++;
		
		}
		
		i = iSys.bc.getIterator();
		
		while(i.hasNext()) {
			
			Box f = (Box)i.next();
			if (f.getId().equals(id)) counter++;
			if (f.getOwnerId().equals(id)) counter++;
		}
		
		i = iSys.cc.getIterator();
		
		while(i.hasNext()) {
			
			Cable f = (Cable)i.next();
			if (f.getId().equals(id)) counter++;
			if (f.getFrom().equals(id)) counter++;
			if (f.getTo().equals(id)) counter++;
			
		}
		
		i = iSys.pc.getIterator();
			
		while(i.hasNext()) {
			
			Pair f = (Pair)i.next();
			if (f.getId().equals(id)) counter++;
			if (f.getCable().equals(id)) counter++;
			if (f.getElementFrom().equals(id)) counter++;
			if (f.getElementTo().equals(id)) counter++;
			
		}
		
		i = iSys.phc.getIterator();
				
		while(i.hasNext()) {
			
			Path f = (Path)i.next();
			if (f.getId().equals(id)) counter++;
			if (f.getSubscriber().equals(id)) counter++;
			if (f.getmPair().equals(id)) counter++;
			if (f.getdrPair().equals(id)) counter++;
			if (f.getdbPair().equals(id)) counter++;
			if (f.getId().equals(id)) counter++;
			
			if (f.isicPair()) {
				for (int k = 0; k < f.geticPair().size(); k++)
					if (f.geticPair().get(k).equals(id)) counter++;
				
			}
			
			
		}
		
		i = iSys.sc.getIterator();
			
		while(i.hasNext()) {
				
			
			Subscriber f = (Subscriber)i.next();
			if (f.getId().equals(id)) counter++;
			
		}
		
		i = iSys.dmc.getIterator();
			
		while (i.hasNext()) {

			Damage f = (Damage)i.next();
			if (f.getId().equals(id)) counter++;
			if (f.getOwnerId().equals(id)) counter++;
		}
		return counter;
	}
	
	native public boolean getLicense(int i);
	
	
	
}