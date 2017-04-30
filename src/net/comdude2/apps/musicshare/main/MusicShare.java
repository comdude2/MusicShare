package net.comdude2.apps.musicshare.main;

import net.comdude2.apps.musicshare.background.MusicCore;
import net.comdude2.apps.musicshare.background.MusicManager;
import net.comdude2.apps.musicshare.ui.InstallDialogue;
import net.comdude2.apps.musicshare.ui.LicenseWindow;
import net.comdude2.apps.musicshare.ui.MusicShareUI;

public class MusicShare {
	
	public enum Mode {CLIENT,SERVER,INSTALL}
	
	private Mode mode = null;
	private MusicManager mm = null;
	
	//All
	
	//Client
	
	//Server
	
	public MusicShare(String[] args){
		boolean debug = true;
		//LicenseWindow lw = new LicenseWindow();
		//if (debug){return;}
		System.out.println("Music Share Application - Created by Matt Armer");
		if (args == null){
			mode = Mode.CLIENT;
		}else{
			if (args.length > 0){
				if (contains(args, "client")){
					mode = Mode.CLIENT;
				}else if (contains(args, "server")){
					mode = Mode.SERVER;
				}else if (contains(args, "install")){
					mode = Mode.INSTALL;
				}
			}else{
				mode = Mode.CLIENT;
			}
		}
	}
	
	public static void main(String[] args){
		MusicShare share = new MusicShare(args);
		share.start();
	}
	
	public void start(){
		mm = new MusicManager(this);
		if (mode == Mode.CLIENT){
			//Client
			MusicShareUI msui = new MusicShareUI(this);
			Thread t = new Thread(msui);
			t.start();
		}else if (mode == Mode.SERVER){
			//Server
			try {
				MusicCore mc = new MusicCore("127.0.0.1", 28900);
				mc.start();
			} catch (Exception e) {e.printStackTrace();}
		}else if (mode == Mode.INSTALL){
			//Install
			InstallDialogue id = new InstallDialogue(this);
			id.run();
		}
	}
	
	private boolean contains(String[] args, String s){
		for (String str : args){
			if (str.equalsIgnoreCase(s)){
				return true;
			}
		}
		return false;
	}
	
	/*
	 * Get and Set
	 */
	
	public MusicManager getMusicManager(){
		return this.mm;
	}
	
}
