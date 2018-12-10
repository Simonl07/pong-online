package client.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class JsonSocketReader {
	
	final static String EOT = "EOT";
	private BufferedReader reader;
	
	public JsonSocketReader(Socket socket){
		try {
			this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public JsonObject next(){
		try {
			return new JsonParser().parse(readNextMessage()).getAsJsonObject();
		} catch (JsonSyntaxException | IOException e) {
			return null;
		}
	}
	
	
	private String readNextMessage() throws IOException {
		String message = "";
				
		String line = reader.readLine();
		while (line != null && !line.trim().equals(EOT)) {
			message += line + "\n";
			line = reader.readLine();
		}
		System.out.println(message);
		return message;
	}
	

	/**
	 * @param socket the socket to set
	 */
	public void setSocket(Socket socket) {
		try {
			this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
