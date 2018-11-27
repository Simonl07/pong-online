package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import com.google.gson.JsonObject;

public class Request {
	private BufferedReader reader;

	public Request(InputStream input) throws UnsupportedEncodingException {
		this.reader = new BufferedReader(new InputStreamReader(input, Module.CHARSET));
	}

	public JsonObject read() {
		try {
			StringBuilder builder = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null && !Module.isEOT(line)) {
				builder.append(line);
			}
			return Module.toJsonObject(builder.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
