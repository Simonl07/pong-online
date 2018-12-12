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

	private void notifyStart(JsonSocketWriter notifyLeft, JsonSocketWriter notifyRight) {
		// inform each other
		JsonObject jsonLeft = generateStartInfo(true);
		JsonObject jsonRight = generateStartInfo(false);
		this.startTime = System.currentTimeMillis() + 5000;
		jsonLeft.addProperty("start", startTime);
		jsonRight.addProperty("start", startTime);
		
		notifyLeft.write(jsonLeft);
		notifyRight.write(jsonRight);
	}

	@Override
	public void run() {

		// listen to each other
		JsonSocketReader listenerLeft = left.getReader();
		JsonSocketReader listenerRight = right.getReader();
		JsonSocketWriter notifyLeft = left.getWriter();
		JsonSocketWriter notifyRight = right.getWriter();

		this.notifyStart(notifyLeft, notifyRight);

		boolean isLeft = true;
		JsonObject json = listenerLeft.next();
		String type;
		System.out.println("here!");
		System.out.flush();
		while (json != null && !(type = json.get(Info.TYPE).getAsString()).equals(Info.IG_CLIENT_END_GAME_TYPE)) {
			System.out.println("type -- " + type);
			System.out.flush();
			isLeft = !isLeft;
			switch (type) {
			case Info.IG_CLIENT_END_ROUND_TYPE:
				if (isLeft) {
					this.right.win();
				} else {
					this.left.win();
				}
				this.notifyStart(notifyLeft, notifyRight);
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
		String opp_host = isLeft ? this.right.getHost() : this.left.getHost();
		int opp_port = isLeft ? this.right.getPort() : this.left.getPort();
		return Info.MM_SERVER_START(opp_host, opp_port, this.session_id, this.left.getScore(), this.right.getScore(),
				isLeft);
	}

}
