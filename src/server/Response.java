package server;

import java.io.IOException;
import java.io.OutputStream;

import com.google.gson.JsonObject;

public class Response {
	private OutputStream output;
	
	public Response(OutputStream output) {
		this.output = output;
	}
	
	public void write(JsonObject json) {
		try {
			String message = Module.toString(json);
			byte[] bytes = message.getBytes(Module.CHARSET);
			output.write(bytes);
			output.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
