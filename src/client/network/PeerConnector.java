package client.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import client.game.Game;

public class PeerConnector {

	private Game game;
	private String remoteHost;
	private int remotePort;
	private int localPort;
	private ServerSocket server;
	private Thread serverThread;
	private Thread clientThread;
	private volatile boolean shutdown;
	final static String EOT = "EOT";

	public PeerConnector(String remoteHost, int remotePort, int localPort) throws IOException {
		this.remoteHost = remoteHost;
		this.remotePort = remotePort;
		this.localPort = localPort;
		this.server = new ServerSocket(this.localPort);
		this.shutdown = false;
		this.serverThread = new ServerThread();
		this.serverThread.start();
	}

	private class ServerThread extends Thread {
		@Override
		public void run() {
			while (!shutdown) {
				try {
					Socket socket = server.accept();
					listenSocket(socket);
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}

		private void listenSocket(Socket socket) {
			BufferedReader in = null;
			try {
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				while (!shutdown) {
					String message = readNextMessage(in);
					JsonObject json = new JsonParser().parse(message).getAsJsonObject();
					if (json.has("type") && json.get("type").getAsString().equals("ig_client_broadcast_blockpos")) {
						int y = json.get("y").getAsInt();
						System.out.println("update y to " + y);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		private String readNextMessage(BufferedReader in) throws IOException {
			String message = "";
					
			String line = in.readLine();
			while (line != null && !line.trim().equals(EOT)) {
				message += line + "\n";
				line = in.readLine();
			}
			return message;
		}
	}

	private class ClientThread extends Thread {

	}

}
