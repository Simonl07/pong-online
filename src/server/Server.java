package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.google.gson.JsonObject;

import server.matchmaking.MatchMaker;
import utils.JsonSocketReader;

public class Server {
	// private static final int POOLSIZE = 10;// thread pool size

	private MatchMaker match;
	private int port;
	private volatile boolean shutdown;

	public Server(int port) {
		match = new MatchMaker();
		this.port = port;
		this.shutdown = false;
	}

	public void startup() {
		class Processor implements Runnable {
			Socket socket;
			Processor(Socket socket) {
				this.socket = socket;
			}
			@Override
			public void run() {
				if (socket == null) {
					return;
				}
				// generate player, start listening
				PlayerInfo player = new PlayerInfo(socket);
				JsonSocketReader listener = new JsonSocketReader(socket);
				JsonObject json;
				while (!shutdown && (json = listener.next()) != null) {// TODO add end game condition
					handleRequest(json, player);
				}
			}
		}
		try (ServerSocket serverSocket = new ServerSocket(port)) {
			while (serverSocket != null && !shutdown) {
				Socket socket = serverSocket.accept();
				new Thread(new Processor(socket)).start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void handleRequest(JsonObject json, PlayerInfo player) {
		String request = json.get(Info.TYPE).getAsString();
		System.out.println(json.toString());
		switch(request) {
		case Info.MM_CLIENT_HELLO_TYPE:
			// wait for match making
			player.setPort(json.get("port").getAsInt());
			match.join(player);
			break;
		case Info.MM_CLIENT_CANCEL_TYPE:
			match.quit(player);
			break;
		// TODO more case: end of game, reconnect, ...
		}
	}
}
