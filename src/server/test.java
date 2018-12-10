package server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import com.google.gson.JsonObject;

import utils.JsonSocketReader;
import utils.JsonSocketWriter;

public class test {

//	static class Con implements Runnable {
//		int i;
//
//		Con(int x) {
//			i = x;
//		}
//
//		@Override
//		public void run() {
//			try {
//				Socket a = new Socket(InetAddress.getLocalHost(), 8000);
//				JsonSocketWriter wa = new JsonSocketWriter(a);
//				JsonSocketReader ra = new JsonSocketReader(a);
//				JsonObject jsona = new JsonObject();
//				jsona.addProperty("type", "mm_client_hello");
//				wa.write(jsona);
//				System.out.println(i + ra.next().toString());
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//
//	}

	public static void main(String[] args) {
		Server s = new Server(8000);
		s.startup();
//		new Thread(new Con(1)).start();
//		new Thread(new Con(2)).start();
//		new Thread(new Con(3)).start();
//		new Thread(new Con(4)).start();

	}

}
