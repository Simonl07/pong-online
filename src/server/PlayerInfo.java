package server;

import java.net.Socket;

import utils.JsonSocketReader;
import utils.JsonSocketWriter;

public class PlayerInfo {
	private int score;
	private String host;
	private int port;
	private Socket socket;
	private JsonSocketReader reader;
	private JsonSocketWriter writer;
	
	public PlayerInfo(Socket socket) {
		this(0, socket);
	}
	public PlayerInfo(int score, Socket socket) {
		this.score = score;
		this.host = socket.getInetAddress().getHostAddress();
		this.socket = socket;
		this.reader = new JsonSocketReader(socket);
		this.writer = new JsonSocketWriter(socket);
	}
	
	public JsonSocketReader getReader() {
		return this.reader;
	}
	public JsonSocketWriter getWriter() {
		return this.writer;
	}
	public void setPort(int port) {
		this.port = port;
	}
	
	public void win() {
		score++;
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
