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
	
	public static JsonObject MM_SERVER_START(String opp_host, String opp_port, String session_id, int left, int right, boolean isLeft){
		JsonObject json = new JsonObject();
		json.addProperty("type", "mm_client_hello");
		return json;
	}
	
	
}
