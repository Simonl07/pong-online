package server;

import java.io.IOException;
import java.net.Socket;

import com.google.gson.JsonObject;

import client.util.JsonSocketReader;
import client.util.JsonSocketWriter;

public class GameProcessor implements Runnable {

	private long session_id;
	private PlayerInfo left;
	private PlayerInfo right;

	public GameProcessor(PlayerInfo left, PlayerInfo right) {
		this.left = left;
		this.right = right;
		this.session_id = left.hashCode() + right.hashCode() + System.currentTimeMillis();
	}

	@Override
	public void run() {
		JsonObject jsonLeft = generateStartInfo(true);
		JsonObject jsonRight = generateStartInfo(false);
		
		new JsonSocketWriter(left.getSocket()).write(jsonLeft);
		new JsonSocketWriter(right.getSocket()).write(jsonRight);

		JsonSocketReader listenerLeft = new JsonSocketReader(left.getSocket());
		JsonSocketReader listenerRight = new JsonSocketReader(right.getSocket());
		JsonObject json;
		while ((json = listenerLeft.next()) != null) {
			String type = json.get("type").getAsString();
			if (type.equals("ig_client_end")) {
				break;
			}
			handle(json);
			json = listenerRight.next();
			if (json == null) {
				break;
			}
			type = json.get("type").getAsString();
			if (type.equals("ig_client_end")) {
				break;
			}
			handle(json);
		}
		// TODO end of game
	}

	private void handle(JsonObject json) {
		// TODO switch depends on type
	}
	
	private JsonObject generateStartInfo(boolean isLeft) {
		JsonObject json = new JsonObject();
		json.addProperty("type", "mm_server_start");
		json.addProperty("opp_host", isLeft ? right.getHost() : left.getHost());
		json.addProperty("opp_port", isLeft ? right.getPort() : left.getPort());
		json.addProperty("session_id", this.session_id);	// TODO get session id
		initGame(json);
		json.addProperty("you", isLeft ? "left" : "right");
		
		return json;
	}
	
	private static void initGame(JsonObject json) {
		// TODO generate game information
		JsonObject iv = new JsonObject ();
		iv.addProperty("x", 40);
		iv.addProperty("y", 120);
		iv.addProperty("dx", -4.6);
		iv.addProperty("dy", 2.6);
		
		json.add("iv", iv);
		
		// TODO generate start time
		json.addProperty("start", System.currentTimeMillis() + 5000);
		json.addProperty("score1", 0);
		json.addProperty("score2", 0);
	}

}
