package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.gson.JsonObject;

import client.util.JsonSocketReader;
import server.matchmaking.MatchMaker;

public class Server {
	// private static final int POOLSIZE = 10;// number of threads in thread
	// pool in server

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
		if (!json.has("type")) {
			// TODO mistake happen, throw exception or return null/false or ?
			return;
		}
		String request = json.get("type").getAsString();
		switch(request) {
		case "mm_client_hello":
			// wait for match making
			match.join(player);
			break;
		// TODO more case: cancel waiting, end of game, reconnect, ...
		}
	}
}
