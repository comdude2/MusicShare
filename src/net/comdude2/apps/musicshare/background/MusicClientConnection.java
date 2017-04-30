package net.comdude2.apps.musicshare.background;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import net.comdude2.apps.pcmanager.net.ClientConnection;

public class MusicClientConnection extends ClientConnection{
	
	public MusicClientConnection(String address, int port, MusicManager mm) {
		super(address, port);
	}
	
	@Override
	public void run(){
		try {
			connection = new Socket(address, port);
			in = null;
			out = null;
			try {
				InputStreamReader isr = new InputStreamReader(this.connection.getInputStream());
				in = new BufferedReader(isr);
				out = new PrintWriter(this.connection.getOutputStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (in == null){return;}else if (out == null){return;}
			
			while(!halt){
				String line = in.readLine();
				handle(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void handle(String line){
		
	}
	
}
