package server;

import com.google.gson.JsonObject;

import utils.JsonSocketReader;
import utils.JsonSocketWriter;

public class GameProcessor implements Runnable {

	private long session_id;
	private PlayerInfo left;
	private PlayerInfo right;
	private long startTime;

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
		this.startTime = System.currentTimeMillis() + 5000;
		jsonLeft.addProperty("start", startTime);
		jsonRight.addProperty("start", startTime);

		// listen to each other
		JsonSocketReader listenerLeft = left.getReader();
		JsonSocketReader listenerRight = right.getReader();
		JsonSocketWriter notifyLeft = left.getWriter();
		JsonSocketWriter notifyRight = left.getWriter();

		notifyLeft.write(jsonLeft);
		notifyRight.write(jsonRight);

		boolean isLeft = true;
		JsonObject json;
		String type;
		System.out.println("here!");
		System.out.flush();
		while ((json = listenerLeft.next()) != null
				&& !(type = json.get(Info.TYPE).getAsString()).equals(Info.IG_CLIENT_END_GAME_TYPE)) {
			isLeft = !isLeft;
			System.out.println("type -- " + type);
			System.out.flush();
			switch (type) {
			case Info.IG_CLIENT_END_ROUND_TYPE:
				if (isLeft) {
					this.right.win();
				} else {
					this.left.win();
				}
				break;
			case Info.IG_CLIENT_REFLECT_TYPE:
				json.remove(Info.TYPE);
				json.addProperty(Info.TYPE, Info.IG_SERVER_BROADCAST_REFLECT_TYPE);
				if (isLeft) {
					notifyLeft.write(json);
					System.out.println("left: " + json.toString());
					System.out.flush();
				} else {
					notifyRight.write(json);
					System.out.println("right: " + json.toString());
					System.out.flush();
				}
			}
			json = isLeft ? listenerLeft.next() : listenerRight.next();
		}
		// end game
		JsonObject score = new JsonObject();
		score.addProperty(Info.TYPE, Info.IG_SERVER_END_GAME_TYPE);
		score.addProperty(Info.LEFT, left.getScore());
		score.addProperty(Info.RIGHT, right.getScore());
		notifyLeft.write(json);
		notifyRight.write(json);
	}

	private JsonObject generateStartInfo(boolean isLeft) {
		JsonObject json = new JsonObject();
		json.addProperty(Info.TYPE, Info.MM_SERVER_START_TYPE);
		json.addProperty(Info.OPP_HOST, isLeft ? right.getHost() : left.getHost());
		json.addProperty(Info.OPP_PORT, isLeft ? right.getPort() : left.getPort());
		json.addProperty(Info.SESSION_ID, this.session_id);
		initGame(json);
		json.addProperty(Info.YOU, isLeft ? Info.LEFT : Info.RIGHT);
		System.out.println(json.toString());
		return json;
	}

	private static void initGame(JsonObject json) {
		// TODO generate game information
		JsonObject iv = new JsonObject();
		iv.addProperty("x", 500);
		iv.addProperty("y", 120);
		iv.addProperty("dx", -0.15);
		iv.addProperty("dy", 0);

		json.add("iv", iv);

		json.addProperty("score1", 0);
		json.addProperty("score2", 0);
	}

}
