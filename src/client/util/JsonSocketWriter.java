package client.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import com.google.gson.JsonObject;

public class JsonSocketWriter {
	
	final static String EOT = "EOT";
	private PrintWriter writer;
	
	public JsonSocketWriter(Socket socket){
		try {
			this.writer = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public void write(JsonObject json) {
		this.writer.write(json.toString());
		this.writer.write(EOT);
		this.writer.flush();
	}
	

	/**
	 * @param socket the socket to set
	 */
	public void setSocket(Socket socket) {
		try {
			this.writer = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
