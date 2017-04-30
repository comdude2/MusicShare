package net.comdude2.apps.musicshare.ui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class LicenseWindow extends JFrame{
	
	private static final long serialVersionUID = -7330202166696307121L;
	private final Dimension winSize = new Dimension(800,600);
	private JTextArea license = null;
	private boolean accepted = false;
	private boolean confirmed = false;
	
	public LicenseWindow(){
		super("License");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE); //TODO CHANGE THIS ####################################
		this.setSize(winSize);
		JPanel pane = new JPanel();
		license = new JTextArea(30, 70);
		license.setSize(700, 500);
		license.setLocation(100, 100);
		license.setText("Pie");
		license.setEditable(false);
		JScrollPane scroller = new JScrollPane(license);
		pane.add(scroller);
		//Tick box and button stuff
		
		this.add(pane);
		this.setVisible(true);
	}
	
	public boolean acceptedLicense(){
		return accepted;
	}
	
	public boolean hasConfirmed(){
		return this.confirmed;
	}
	
}
