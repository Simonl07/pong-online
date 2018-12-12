package client.util;

import com.google.gson.JsonObject;

public final class JsonTemplates {

	public static JsonObject MM_CLIENT_HELLO(int port) {
		JsonObject json = new JsonObject();
		json.addProperty("type", "mm_client_hello");
		json.addProperty("port", port);
		return json;
	}

	public static JsonObject MM_CLIENT_CANCEL() {
		JsonObject json = new JsonObject();
		json.addProperty("type", "mm_client_cancel");
		return json;
	}

	public static JsonObject IG_CLIENT_REFLECT(int x, int y, double dx, double dy, long start) {
		JsonObject json = new JsonObject();
		json.addProperty("type", "ig_client_reflect");
		JsonObject v = new JsonObject();
		v.addProperty("x", x);
		v.addProperty("y", y);
		v.addProperty("dx", dx);
		v.addProperty("dy", dy);
		json.add("v", v);
		json.addProperty("start", start);
		return json;
	}

}
