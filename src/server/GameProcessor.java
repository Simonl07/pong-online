package server;

import java.io.IOException;
import java.net.Socket;

import com.google.gson.JsonObject;

import client.util.JsonSocketWriter;

public class GameProcessor implements Runnable {

	private PlayerInfo left;
	private PlayerInfo right;

	public GameProcessor(PlayerInfo left, PlayerInfo right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public void run() {
		JsonObject jsonLeft = generateStartInfo(true);
		JsonObject jsonRight = generateStartInfo(false);
		new JsonSocketWriter(left.getSocket()).write(jsonLeft);
		new JsonSocketWriter(left.getSocket()).write(jsonRight);
		// TODO what's next? -- listen and update game info
		// TODO end of game
	}

	private JsonObject generateStartInfo(boolean isLeft) {
		JsonObject json = new JsonObject();
		json.addProperty("type", "mm_server_start");
		json.addProperty("opp_host", isLeft ? right.getHost() : left.getHost());
		json.addProperty("opp_port", isLeft ? right.getPort() : left.getPort());
		json.addProperty("session_id", "");	// TODO get socket id
		Module.initGame(json);
		json.addProperty("you", isLeft ? "left" : "right");
		
		return json;
	}

}
