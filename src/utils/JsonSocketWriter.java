package utils;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import com.google.gson.JsonObject;

public class JsonSocketWriter {

	final static String EOT = "EOT";
	private PrintWriter writer;

	public JsonSocketWriter(Socket socket) {
		try {
			this.writer = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void write(JsonObject json) {

		String output = json.toString() + "\n" + EOT + "\n";
		System.out.println(output);
		this.writer.write(output);
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
