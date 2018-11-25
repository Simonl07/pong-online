package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Processor implements Runnable {

	private Socket socket;
	private Module module;

	public Processor(Socket socket) {
		this.socket = socket;
		module = new Module();
	}

	@Override
	public void run() {
		try {
			InputStream input = socket.getInputStream();
			OutputStream output = socket.getOutputStream();
			byte[] bytes = new byte[1024];
			int length = 0;
			while ((length = input.read(bytes)) != -1) {
				String request = new String(bytes, 0, length, Module.CHARSET);
				String response = module.handle(request);
				output.write(response.getBytes(Module.CHARSET));
				output.flush();
			}
			if (socket.isClosed()) {
				socket.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
