package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {	
	private static final int POOLSIZE = 10;// number of threads in thread pool in server
	
	private int port;
	
	public Server(int port) {
		this.port = port;
	}
	
	public synchronized void startup() {
		try (ServerSocket serverSocket = new ServerSocket(port)) {
			ExecutorService pool = Executors.newFixedThreadPool(POOLSIZE);
			while (serverSocket != null) {
				Socket socket = serverSocket.accept();
				Processor process = new Processor(socket);
				pool.execute(process);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
