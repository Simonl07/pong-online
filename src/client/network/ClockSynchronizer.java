package client.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import com.google.gson.JsonObject;

import utils.JsonSocketReader;
import utils.JsonSocketWriter;

public class ClockSynchronizer {
	public static long syncAsLeft(String host, int port) {
		final int SAMPLE_SIZE = 1;

		try {
			Socket socket = new Socket(host, port);
			JsonSocketReader reader = new JsonSocketReader(socket);
			JsonSocketWriter writer = new JsonSocketWriter(socket);
			JsonObject req = new JsonObject();
			req.addProperty("type", "pg_clock_sync_req");
			double start, end, singleTrip, offset, totalOffset = 0;
			for (int i = 0; i < SAMPLE_SIZE; i++) {
				start = System.currentTimeMillis();
				writer.write(req);
				JsonObject res = reader.next();
				end = System.currentTimeMillis();
				singleTrip = (end - start) / 2.0;
				offset = res.get("t").getAsLong() - (start);
				totalOffset += offset;
			}

			double averageOffset = totalOffset / SAMPLE_SIZE;
			req.addProperty("type", "pg_clock_sync_stop");
			writer.write(req);
			return (long) averageOffset;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static void syncAsRight(int port) {

		ServerSocket server = null;
		Socket socket = null;
		try {
			server = new ServerSocket(port);
			socket = server.accept();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}

		JsonSocketReader reader = new JsonSocketReader(socket);
		JsonSocketWriter writer = new JsonSocketWriter(socket);
		while (true) {
			JsonObject req = reader.next();
			if (req.has("type") && req.get("type").getAsString().equals("pg_clock_sync_req")) {
				JsonObject res = new JsonObject();
				res.addProperty("type", "pg_clock_sync_res");
				res.addProperty("t", System.currentTimeMillis());
				writer.write(res);
			} else if (req.has("type") && req.get("type").getAsString().equals("pg_clock_sync_stop")) {
				try {
					socket.close();
					server.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
		}

	}
}
