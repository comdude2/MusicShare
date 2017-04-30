package net.comdude2.apps.musicshare.background;

import java.net.Socket;

import net.comdude2.apps.pcmanager.net.ServerConnection;

public class MusicConnection extends ServerConnection{
	
	public MusicConnection(Socket connection) {
		super(connection);
	}
	
	@Override
	protected void interpret(String line){
		
	}
	
}
