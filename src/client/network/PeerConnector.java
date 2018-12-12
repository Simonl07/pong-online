package client.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import client.game.Game;

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
					DataInputStream input = new DataInputStream(server.accept().getInputStream());
					// JsonSocketReader listener = new JsonSocketReader(socket);
					// while (!shutdown) {
					// JsonObject json = listener.next();
					// if (json.has("type") &&
					// json.get("type").getAsString().equals("ig_client_broadcast_blockpos"))
					// {
					// game.getOpponent().setY(json.get("y").getAsInt());
					// }
					// }
					while (!shutdown) {
						game.getOpponent().setY(input.readShort());
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
				// JsonSocketWriter writer = new JsonSocketWriter(new
				// Socket(remoteHost, remotePort));
				// while (!shutdown) {
				// JsonObject json = new JsonObject();
				// json.addProperty("type", "ig_client_broadcast_blockpos");
				// json.addProperty("y", game.getMe().getY());
				// try {
				// Thread.sleep(100);
				// } catch (InterruptedException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
				// writer.write(json);
				// }
				Socket socket = new Socket(remoteHost, remotePort);
				DataOutputStream output = new DataOutputStream(socket.getOutputStream());
				while (!shutdown) {
					output.writeShort(game.getMe().getY());
				}
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

}
