package net.comdude2.apps.musicshare.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import net.comdude2.apps.musicshare.background.MusicClientConnection;
import net.comdude2.apps.musicshare.background.MusicManager;
import net.comdude2.apps.musicshare.main.MusicShare;

@SuppressWarnings("unused")
public class MusicShareUI extends JFrame implements Runnable{
	
	private static final long serialVersionUID = -7570361564576553887L;
	private MusicShare ms = null;
	private Dimension winSize = new Dimension(1024, 720);
	public MusicShareUI msui = this;
	private MusicClientConnection mcc = null;
	
	/*
	 * Swing Components
	 */
	
	/*  #Menu Objects  */
	private JPanel pane = new JPanel(new BorderLayout(3,3));
	private JMenuBar menuBar = null;
	/* Containers */
	private JSplitPane mainSplit = null;
	private JSplitPane subSplit = null;
	/*  Connection Menu  */
	private JMenu connectionMenu = null;
	private JMenuItem connect = null;
	private JMenuItem disconnect = null;
	/*  User Menu  */
	private JMenu userMenu = null;
	private JMenuItem preferencesMenu = null;
	/* List View */
	private DefaultListModel<String> musicList = null;
	/* Player Interface */
	private JPanel playerPane = new JPanel();
	private JButton playButton = null;
	private JButton pauseButton = null;
	private JButton nextButton = null;
	private JButton prevButton = null;
	
	/*
	 * End of Swing Components
	 */
	
	public MusicShareUI(MusicShare ms){
		super("Music Share");
		this.ms = ms;
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationByPlatform(true);
		try{UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}catch(Exception e){}
		this.setSize(winSize);
		this.setMinimumSize(winSize);
		this.setMaximumSize(winSize);
		this.setResizable(false);
		
		/*
		 * MusicClientConnection mcc = new MusicClientConnection("musicshare.comdude2.net", 28900, ms.getMusicManager());
		 * mcc.start();
		 */
		
		/* Containers */
		this.pane.setBorder(new EmptyBorder(5,5,5,5));
		this.mainSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		this.mainSplit.setDividerLocation(300);
		this.subSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		this.subSplit.setDividerLocation(500);
		
		
		this.addMenu();
		this.addPlayer();
		this.addLeftColumn();
		this.pane.add(mainSplit);
		this.pane.add(subSplit);
		this.setContentPane(this.pane);
		this.pack();
		this.setVisible(true);
	}
	
	public void run() {
		
	}
	
	/*
	 * Adding methods
	 */
	
	private void addMenu(){
		this.menuBar = new JMenuBar();
		this.connectionMenu = new JMenu("Connection");
		this.connect = new JMenuItem(new AbstractAction("Connect") {
			private static final long serialVersionUID = 4900109169902904952L;
			
			public void actionPerformed(ActionEvent e) {
		        String response = JOptionPane.showInputDialog(msui, "Enter address to connect to", "Connect");
		        if (response != null){
		        	if (response.length() > 0){
			        	System.out.println("Attempting to connect to: " + response + "...");
			        }else{
			        	System.out.println("No address provided.");
			        }
		        }else{
		        	System.out.println("No address provided.");
		        }
		        msui.connect.setEnabled(false);
		        msui.disconnect.setEnabled(true);
		    }
		});
		this.connectionMenu.add(this.connect);
		this.disconnect = new JMenuItem(new AbstractAction("Disconnect") {
			private static final long serialVersionUID = 938479568734987438L;
			
			public void actionPerformed(ActionEvent e){
				msui.ms.getMusicManager().stopSong();
				if (msui.mcc != null){
					msui.mcc.halt();
					msui.mcc.interrupt();
					msui.mcc = null;
				}
				System.out.println("Disconnected.");
				msui.connect.setEnabled(true);
				msui.disconnect.setEnabled(false);
			}
		});
		this.connectionMenu.add(this.disconnect);
		this.disconnect.setEnabled(false);
		this.menuBar.add(connectionMenu);
		
		this.setJMenuBar(this.menuBar);
	}
	
	private void addPlayer(){
		Dimension buttonSize = new Dimension(48,26);
		this.playerPane = new JPanel();
		
		this.prevButton = new JButton("<<");
		this.prevButton.setEnabled(false);
		this.prevButton.setSize(buttonSize);
		this.prevButton.setMinimumSize(buttonSize);
		this.prevButton.setMaximumSize(buttonSize);
		
		this.playerPane.add(this.prevButton);
		this.playButton = new JButton(">");
		this.playButton.setEnabled(true);
		this.playButton.setSize(buttonSize);
		this.playButton.setMinimumSize(buttonSize);
		this.playButton.setMaximumSize(buttonSize);
		/*Action Listener*/
		this.playButton.addActionListener(new ActionListener(){
			private boolean debug = false;
			public void actionPerformed(ActionEvent event) {
				if (debug){
					System.out.println("Type: " + event.getActionCommand());
					System.out.println("");
					System.out.println("Panel size: " + playerPane.getSize().toString());
					System.out.println("");
					System.out.println("Prev size: " + prevButton.getSize().toString());
					System.out.println("Play size: " + playButton.getSize().toString());
					System.out.println("Pause size: " + pauseButton.getSize().toString());
					System.out.println("Next size: " + nextButton.getSize().toString());
				}else{
					File f = new File("E:/My Songs/All Music/Bebe Rexha - I'm Gonna Show You Crazy (Official Music Video) (Cut).mp3");
					try {
						if (!ms.getMusicManager().isPaused()){
							ms.getMusicManager().loadSong(f);
						}
						ms.getMusicManager().playSong();
						long microseconds = MusicManager.getMusicLength(f);
						System.out.println(MusicManager.formatLength(microseconds));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				pauseButton.setEnabled(true);
				playButton.setEnabled(false);
			}
		});
		/**/
		this.playerPane.add(this.playButton);
		this.pauseButton = new JButton("||");
		this.pauseButton.setEnabled(false);
		this.pauseButton.setSize(buttonSize);
		this.pauseButton.setMinimumSize(buttonSize);
		this.pauseButton.setMaximumSize(buttonSize);
		/*Action Listener*/
		this.pauseButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event) {
				ms.getMusicManager().pauseSong();
				pauseButton.setEnabled(false);
				playButton.setEnabled(true);
			}
		});
		/**/
		this.playerPane.add(this.pauseButton);
		this.nextButton = new JButton(">>");
		this.nextButton.setEnabled(false);
		this.nextButton.setSize(buttonSize);
		this.nextButton.setMinimumSize(buttonSize);
		this.nextButton.setMaximumSize(buttonSize);
		
		this.playerPane.add(this.nextButton);
		this.playerPane.setSize(new Dimension(202,36));
		
		this.mainSplit.setRightComponent(this.subSplit);
		this.subSplit.setBottomComponent(this.playerPane);
		this.subSplit.setTopComponent(null);
	}
	
	private void addButtonMenu(JSplitPane split){
		JPanel panel = new JPanel();
		split.setBottomComponent(panel);
	}
	
	private void addListMenu(JSplitPane split){
		this.musicList = new DefaultListModel<String> ();
		this.musicList.addElement("Pie");
		JList<String> list = new JList<String>(this.musicList);
		JScrollPane scroller = new JScrollPane(list);
		Dimension size = new Dimension(300,300);
		scroller.setSize(size);
		scroller.setMinimumSize(size);
		scroller.setMaximumSize(size);
		split.setTopComponent(scroller);
	}
	
	private void addLeftColumn(){
		JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		split.setDividerLocation(300);
		this.addButtonMenu(split);
		this.addListMenu(split);
		this.mainSplit.setLeftComponent(split);
	}
	
	private void addLookupMenu(){
		
	}
	
}
