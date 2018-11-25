package server;

import java.io.IOException;
import java.io.InputStream;

public class Request {
	private InputStream input;

	public Request(InputStream input) {
		this.input = input;
	}

	public String read() {
		try {
			// read the length
			int length = input.read();
			if (length == -1) {
				return null;
			}

			// read the message
			byte[] bytes = new byte[length];
			input.read(bytes);
			String message = new String(bytes, Module.CHARSET);
			return message;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
