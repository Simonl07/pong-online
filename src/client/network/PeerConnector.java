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
	private int localPort;
	private ServerSocket server;
	private Thread serverThread;
	private Thread clientThread;
	private volatile boolean shutdown;

	public PeerConnector(Game game, String remoteHost, int remotePort, int localPort){
		this.game = game;
		this.remoteHost = remoteHost;
		this.remotePort = remotePort;
		this.localPort = localPort;
		try {
			this.server = new ServerSocket(this.localPort);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.shutdown = false;
		this.serverThread = new ServerThread();
		this.serverThread.start();
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
							game.getP2().setY(json.get("y").getAsInt());
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
					json.addProperty("y", game.getP1().getY());
					writer.write(json);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

}
