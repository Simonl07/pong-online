package server;

import java.io.IOException;
import java.io.OutputStream;

public class Response {
	private OutputStream output;
	
	public Response(OutputStream output) {
		this.output = output;
	}
	
	public void write(String message) {
		try {
			byte[] bytes = message.getBytes(Module.CHARSET);
			// write the length
			output.write(bytes.length);
			
			// write the message
			output.write(bytes);
			output.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
