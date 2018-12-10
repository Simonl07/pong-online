package client.util;

import com.google.gson.JsonObject;

public final class JsonTemplates {

	public static JsonObject MM_CLIENT_HELLO(int port){
		JsonObject json = new JsonObject();
		json.addProperty("type", "mm_client_hello");
		json.addProperty("port", port);
		return json;
	}
	
	public static JsonObject MM_CLIENT_CANCEL(){
		JsonObject json = new JsonObject();
		json.addProperty("type", "mm_client_cancel");
		return json;
	}
	
	public static JsonObject IG_CLIENT_REFLECT(int x, int y, int dx, int dy){
		JsonObject json = new JsonObject();
		json.addProperty("type", "mm_client_cancel");
		return json; //TODO
	}
	
}
