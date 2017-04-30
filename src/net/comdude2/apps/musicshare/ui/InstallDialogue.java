package net.comdude2.apps.musicshare.ui;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import net.comdude2.apps.musicshare.main.MusicShare;

public class InstallDialogue extends JFrame implements Runnable{
	
	private static final long serialVersionUID = -8593585408495127754L;
	@SuppressWarnings("unused")
	private MusicShare ms = null;
	private boolean agreed = false;
	private File installDir = null;
	
	public InstallDialogue(MusicShare ms){
		super("Music Share Installation");
		this.ms = ms;
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(false);
		//Ask to install
		int response = JOptionPane.showOptionDialog(null, "Do you want to install Music Share?", "Install", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null, null, null);
		if (response == 0){
			agreed = true;
		}else{
			System.exit(0);
		}
		this.setVisible(true);
	}
	
	public void run() {
		if (agreed){
			//Ask for directory
			JFileChooser jfc = new JFileChooser();
			jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int returnVal = jfc.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION){
				installDir = jfc.getSelectedFile();
			}else{
				//Cancel
				System.exit(0);
			}
			if (installDir != null){
				//License
				LicenseWindow lw = new LicenseWindow();
				if (lw.acceptedLicense()){
					/*
					 * Start installation and log to a window
					 */
				}else{
					JOptionPane.showConfirmDialog(null, "To install this program you must accept the license agreement.", "Message", JOptionPane.OK_OPTION);
					System.exit(0);
				}
			}
		}else{
			System.exit(0);
		}
	}

}
