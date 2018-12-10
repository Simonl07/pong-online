package server;

import com.google.gson.JsonObject;

import utils.JsonSocketReader;
import utils.JsonSocketWriter;

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
		// inform each other
		JsonObject jsonLeft = generateStartInfo(true);
		JsonObject jsonRight = generateStartInfo(false);
		new JsonSocketWriter(left.getSocket()).write(jsonLeft);
		new JsonSocketWriter(right.getSocket()).write(jsonRight);

		// listen to each other
		JsonSocketReader listenerLeft = new JsonSocketReader(left.getSocket());
		JsonSocketReader listenerRight = new JsonSocketReader(right.getSocket());
		JsonSocketWriter notifyLeft = new JsonSocketWriter(left.getSocket());
		JsonSocketWriter notifyRight = new JsonSocketWriter(right.getSocket());
		boolean isLeft = true;
		JsonObject json = listenerLeft.next();
		String type;
		while ((json = listenerLeft.next()) != null
				&& !(type = json.get("type").getAsString()).equals("ig_client_end_game")) {
			isLeft = !isLeft;
			switch (type) {
			case "ig_client_end_round":
				if (isLeft) {
					this.right.win();
				} else {
					this.left.win();
				}
				break;
			case "ig_client_reflect":
				json.remove("type");
				json.addProperty("type", "ig_server_broadcast_reflect");
				if (isLeft) {
					notifyLeft.write(json);
				} else {
					notifyRight.write(json);
				}
			}
			json = isLeft ? listenerLeft.next() : listenerRight.next();
		}
		// end game
		JsonObject score = new JsonObject();
		score.addProperty("type", "ig_server_end_game");
		score.addProperty("left", left.getScore());
		score.addProperty("right", right.getScore());
		notifyLeft.write(json);
		notifyRight.write(json);
	}

	private JsonObject generateStartInfo(boolean isLeft) {
		JsonObject json = new JsonObject();
		json.addProperty("type", "mm_server_start");
		json.addProperty("opp_host", isLeft ? right.getHost() : left.getHost());
		json.addProperty("opp_port", isLeft ? right.getPort() : left.getPort());
		json.addProperty("session_id", this.session_id);
		initGame(json);
		json.addProperty("you", isLeft ? "left" : "right");
		return json;
	}

	private static void initGame(JsonObject json) {
		// TODO generate game information
		JsonObject iv = new JsonObject();
		iv.addProperty("x", 40);
		iv.addProperty("y", 120);
		iv.addProperty("dx", -4.6);
		iv.addProperty("dy", 2.6);

		json.add("iv", iv);

		json.addProperty("start", System.currentTimeMillis() + 5000);
		json.addProperty("score1", 0);
		json.addProperty("score2", 0);
	}

}
