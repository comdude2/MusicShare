package net.comdude2.apps.musicshare.background;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import javazoom.spi.mpeg.sampled.file.MpegAudioFileReader;
import net.comdude2.apps.musicshare.main.MusicShare;

public class MusicManager extends Thread{
	
	@SuppressWarnings("unused")
	private MusicShare ms = null;
	private PausablePlayer player = null;
	private Thread t = null;
	private boolean paused = false;
	
	public MusicManager(MusicShare ms){
		this.ms = ms;
	}
	
	public boolean loadSong(File f) throws Exception{
		stopSong();
		if (f != null){
			if (f.exists()){
				FileInputStream fis = new FileInputStream(f);
				player = new PausablePlayer(fis);
				return true;
			}
		}
		return false;
	}
	
	public boolean loadSong(InputStream is) throws Exception{
		stopSong();
		if (is != null){
			player = new PausablePlayer(is);
			return true;
		}
		return false;
	}
	
	public void playSong() throws JavaLayerException{
		if (paused){
			System.out.println("Resuming");
			player.resume();
		}else{
			this.t = new Thread(){
				@Override
				public void run(){
					try {
						player.play();
					} catch (JavaLayerException e) {
						e.printStackTrace();
					}
				}
			};
			t.start();
		}
		this.paused = false;
	}
	
	public void playSong(InputStream is) throws JavaLayerException{
		player = new PausablePlayer(is);
		this.t = new Thread(){
			@Override
			public void run(){
				try {
					player.play();
				} catch (JavaLayerException e) {
					e.printStackTrace();
				}
			}
		};
		t.start();
		this.paused = false;
	}
	
	public void pauseSong(){
		if (player != null){
			player.pause();
		}
		this.paused = true;
	}
	
	public void stopSong(){
		if (player != null){
			player.close();
			player = null;
		}
		try{this.t.interrupt();}catch(Exception e){}
		this.paused = false;
	}
	
	/*
	 * Get and Set
	 */
	
	public PausablePlayer getMusicPlayer(){
		return this.player;
	}
	
	public static long getMusicLength(File file){
		System.out.println("File: " + file);
		try{
			AudioFileFormat baseFileFormat = new MpegAudioFileReader().getAudioFileFormat(file);
			Map<String,Object> properties = baseFileFormat.properties();
			Long duration = (Long) properties.get("duration");
			return duration;
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Exception: " + e.getMessage());
			return -1L;
		}
	}
	
	public static String formatLength(long microseconds){
		int mili = (int) (microseconds / 1000);
        int sec = (mili / 1000) % 60;
        int min = (mili / 1000) / 60;
		return("Length: " + min + ":" + sec);
	}
	
	public boolean isPaused(){
		return this.paused;
	}
	
}
