package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class JsonSocketReader {
	final static String EOT = JsonSocketWriter.EOT;
	private static final JsonParser parser = new JsonParser();
	private BufferedReader reader;

	public JsonSocketReader(Socket socket) {
		try {
			this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public JsonObject next() {
		try {
			JsonElement json = parser.parse(readNextMessage());
//			System.out.println(json.toString());
			if (json != null && !json.equals("")) {
//				System.out.println("return");
				return json.getAsJsonObject();
			}
			return null;
		} catch (JsonSyntaxException | IOException e) {
			return null;
		}
	}

	private String readNextMessage() throws IOException {
		StringBuilder builder = new StringBuilder();
		String line;
		while (!(line = reader.readLine().trim()).equals(EOT)) {
//			System.out.println("line:" + line +"|");
//			System.out.flush();
			builder.append(line);
//			System.out.println("to" + buffer.toString() + "|");
//			System.out.flush();
		}
//		System.out.println("|" + buffer.toString() + "|");
//		System.out.flush();
		return builder.toString();
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
