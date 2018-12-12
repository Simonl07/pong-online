package server;

import com.google.gson.JsonObject;

public class Info {
	public static final String TYPE = "type";
	public static final String LEFT = "left";
	public static final String RIGHT = "right";
	public static final String OPP_HOST = "opp_host";
	public static final String OPP_PORT = "opp_port";
	public static final String SESSION_ID = "session_id";
	public static final String YOU = "you";

	public static final String MM_CLIENT_HELLO_TYPE = "mm_client_hello";
	public static final String MM_CLIENT_CANCEL_TYPE = "mm_client_cancel";
	public static final String MM_SERVER_START_TYPE = "mm_server_start";

	public static final String IG_CLIENT_END_GAME_TYPE = "ig_client_end_game";
	public static final String IG_CLIENT_END_ROUND_TYPE = "ig_client_end_round";
	public static final String IG_CLIENT_REFLECT_TYPE = "ig_client_reflect";

	public static final String IG_SERVER_BROADCAST_REFLECT_TYPE = "ig_server_broadcast_reflect";
	public static final String IG_SERVER_END_GAME_TYPE = "ig_server_end_game";

	public static JsonObject IG_SERVER_END_GAME(int left, int right) {
		JsonObject json = new JsonObject();
		json.addProperty(Info.TYPE, IG_SERVER_END_GAME_TYPE);
		json.addProperty(Info.LEFT, left);
		json.addProperty(Info.RIGHT, right);
		return json;
	}
	
	public static JsonObject MM_SERVER_START(String opp_host, int opp_port, long session_id, int left, int right,
			boolean isLeft, JsonObject iv) {
		JsonObject json = new JsonObject();
		json.addProperty(TYPE, MM_SERVER_START_TYPE);
		json.addProperty(OPP_HOST, opp_host);
		json.addProperty(OPP_PORT, opp_port);
		json.addProperty(SESSION_ID, session_id);
		json.add("iv", iv);
		json.addProperty("left", left);
		json.addProperty("right", right);
		json.addProperty(YOU, (isLeft ? LEFT : RIGHT));
		return json;
	}

	public static JsonObject initGame() {
		JsonObject json = new JsonObject();
		json.addProperty("x", generatePosition(400, 600));
		json.addProperty("y", generatePosition(250, 350));
		json.addProperty("dx", (-1) * generateSpeed(0.08, 0.16));
		json.addProperty("dy", generateSpeed(-0.15, 0.15));
		return json;
	}
	
	private static int generatePosition(int low, int high) {
		return (int) (Math.random() * (high - low) + low);
	}
	
	private static double generateSpeed(double low, double high) {
		return Math.random() * (high - low) + low;
	}


}
