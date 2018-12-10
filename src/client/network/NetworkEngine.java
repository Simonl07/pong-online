package client.network;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import client.util.JsonSocketReader;
import client.util.JsonSocketWriter;

public class NetworkEngine {

	public final String GAME_SERVER_HOST;
	public final int GAME_SERVER_PORT;
	
	public NetworkEngine(String serverHost, int serverPort){
		this.GAME_SERVER_HOST = serverHost;
		this.GAME_SERVER_PORT = serverPort;
	}
		
	public Socket findMatch() throws UnknownHostException, IOException{
		Socket s = new Socket(GAME_SERVER_HOST, GAME_SERVER_PORT);
		JsonSocketWriter writer = new JsonSocketWriter(s);
		JsonSocketReader reader = new JsonSocketReader(s);
		
		return null; //TODO
	}
}
