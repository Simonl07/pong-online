package client.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.google.gson.JsonObject;

import client.game.Game;
import utils.JsonSocketReader;
import utils.JsonSocketWriter;

public class PeerConnector {

	private Game game;
	private String remoteHost;
	private int remotePort;
	private ServerSocket server;
	private Thread serverThread;
	private Thread clientThread;
	private volatile boolean shutdown;
	public static final int DEFAULT_LOCAL_PORT = 8765;

	public PeerConnector(Game game) {
		this.game = game;
		this.shutdown = false;
	}

	public void startServer() {
		this.startServer(DEFAULT_LOCAL_PORT);
	}

	public void startServer(int localPort) {
		try {
			this.server = new ServerSocket(localPort);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.serverThread = new ServerThread();
		this.serverThread.start();
	}

	public void startClient(String remoteHost, int remotePort) {
		this.remoteHost = remoteHost;
		this.remotePort = remotePort;
		this.clientThread = new ClientThread();
		this.clientThread.start();
	}

	private class ServerThread extends Thread {

		@Override
		public void run() {
			while (!shutdown) {
				try {
					Socket socket = server.accept();
					JsonSocketReader listener = new JsonSocketReader(socket);
					while (!shutdown) {
						JsonObject json = listener.next();
						if (json.has("type") && json.get("type").getAsString().equals("ig_client_broadcast_blockpos")) {
							game.getOpponent().setY(json.get("y").getAsInt());
						}
					}

				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
	}

	private class ClientThread extends Thread {
		@Override
		public void run() {
			try {
				JsonSocketWriter writer = new JsonSocketWriter(new Socket(remoteHost, remotePort));
				while (!shutdown) {
					JsonObject json = new JsonObject();
					json.addProperty("type", "ig_client_broadcast_blockpos");
					json.addProperty("y", game.getMe().getY());
					writer.write(json);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

}
