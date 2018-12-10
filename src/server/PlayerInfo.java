package server;

import java.net.Socket;

public class PlayerInfo {
	private int score;
	private String host;
	private int port;
	private Socket socket;
	
	public PlayerInfo(Socket socket) {
		this(0, socket);
	}
	public PlayerInfo(int score, Socket socket) {
		this.score = score;
		this.host = socket.getInetAddress().getHostAddress();
		this.port = socket.getPort();
		this.socket = socket;
	}
	
	public int getScore() {
		return score;
	}
	public String getHost() {
		return host;
	}
	public int getPort() {
		return port;
	}
	public Socket getSocket() {
		return socket;
	}
}
