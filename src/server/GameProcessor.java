package server;

import com.google.gson.JsonObject;

import utils.JsonSocketReader;
import utils.JsonSocketWriter;

public class GameProcessor implements Runnable {

	private long session_id;
	private PlayerInfo left;
	private PlayerInfo right;
	private long startTime;
	private JsonSocketReader listenerLeft;
	private JsonSocketReader listenerRight;
	private JsonSocketWriter notifyLeft;
	private JsonSocketWriter notifyRight;

	public GameProcessor(PlayerInfo left, PlayerInfo right) {
		this.left = left;
		this.right = right;
		this.session_id = left.hashCode() + right.hashCode() + System.currentTimeMillis();
	}

	private void notifyStart() {
		// inform each other
		JsonObject iv = Info.initGame();
		JsonObject jsonLeft = generateStartInfo(true, iv);
		JsonObject jsonRight = generateStartInfo(false, iv);
		this.startTime = System.currentTimeMillis() + 3000;
		jsonLeft.addProperty("start", startTime);
		jsonRight.addProperty("start", startTime);

		this.notifyLeft.write(jsonLeft);
		this.notifyRight.write(jsonRight);
	}

	@Override
	public void run() {
		// listen to each other
		this.listenerLeft = left.getReader();
		this.listenerRight = right.getReader();
		this.notifyLeft = left.getWriter();
		this.notifyRight = right.getWriter();

		this.notifyStart();

		boolean isLeft = true;
		JsonObject json = listenerLeft.next();
		String type;
		while (json != null && !(type = json.get(Info.TYPE).getAsString()).equals(Info.IG_CLIENT_END_GAME_TYPE)) {
			isLeft = !isLeft;
			switch (type) {
			case Info.IG_CLIENT_END_ROUND_TYPE:
				if (isLeft) {
					this.right.win();
					this.notifyRight.write(Info.IG_SERVER_END_ROUND());
				} else {
					this.left.win();
					this.notifyLeft.write(Info.IG_SERVER_END_ROUND());
				}
				this.notifyStart();
				break;
			case Info.IG_CLIENT_REFLECT_TYPE:
				json.remove(Info.TYPE);
				json.addProperty(Info.TYPE, Info.IG_SERVER_BROADCAST_REFLECT_TYPE);
				if (isLeft) {
					this.notifyLeft.write(json);
				} else {
					this.notifyRight.write(json);
				}
			}
			json = isLeft ? listenerLeft.next() : listenerRight.next();
		}
		// end game
		JsonObject score = Info.IG_SERVER_END_GAME(left.getScore(), right.getScore());
		this.notifyLeft.write(score);
		this.notifyRight.write(score);
	}

	private JsonObject generateStartInfo(boolean isLeft, JsonObject iv) {
		String opp_host = isLeft ? this.right.getHost() : this.left.getHost();
		int opp_port = isLeft ? this.right.getPort() : this.left.getPort();
		return Info.MM_SERVER_START(opp_host, opp_port, this.session_id, this.left.getScore(), this.right.getScore(),
				isLeft, iv);
	}

}
