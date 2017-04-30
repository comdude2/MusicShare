package net.comdude2.apps.musicshare.background;

import java.net.Socket;

import net.comdude2.apps.pcmanager.net.ServerCore;

public class MusicCore extends ServerCore{

	public MusicCore(String address, int port) throws Exception {
		super(address, port);
	}
	
	@Override
	public void handleConnection(Socket connection){
		MusicConnection mc = new MusicConnection(connection);
		mc.start();
		connections.add(mc);
	}

}
