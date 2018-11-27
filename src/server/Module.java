package server;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Module {
	public static final String CHARSET = "UTF-8";
	public static final int PORT = 6666;
	
	private static final JsonParser parser = new JsonParser();
	
	public static final String END_OF_TRANSMIT = "EOT";
	
	public static final String TYPE = "type";
	public static final String TYPE_HELLO = "mm_client_hello";
	public static final String TYPE_SERVER_START = "mm_server_start";
	
	public static final String TYPE_IG_CLIENT_REFLECT = "ig_client_reflect";
	public static final String TYPE_IG_BLOCK = "ig_client_broadcast_blockpos";
	public static final String TYPE_IG_END = "ig_client_end";
	
	public static String initGame() {
		// TODO game information
		return "";
	}
	
	public static JsonObject toJsonObject(String s) {
		if (s == null || s.trim().equals("") || isEOT(s)) {
			return null;
		}
		return parser.parse(s).getAsJsonObject();
	}
	
	public static String toString(JsonObject json) {
		return json.toString();
	}
	
	public static boolean isEOT(String line) {
		return END_OF_TRANSMIT.equals(line);
	}
	
	public static String getType(JsonObject json) {
		return json.get(TYPE).getAsString();
	}
	
	
}
