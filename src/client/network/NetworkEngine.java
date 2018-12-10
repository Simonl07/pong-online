package client.network;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import com.google.gson.JsonObject;

import client.game.Game;
import client.game.Vector;
import client.util.JsonTemplates;
import utils.JsonSocketReader;
import utils.JsonSocketWriter;

public class NetworkEngine {

	private final String GAME_SERVER_HOST;
	private final int GAME_SERVER_PORT;
	private Game game;
	private PeerConnector connector;

	public NetworkEngine(Game game, String serverHost, int serverPort) {
		this.GAME_SERVER_HOST = serverHost;
		this.GAME_SERVER_PORT = serverPort;
		this.game = game;
		this.connector = new PeerConnector(game);
	}

	public Socket findMatch() throws UnknownHostException, IOException {
		Socket socket = new Socket(GAME_SERVER_HOST, GAME_SERVER_PORT);
		JsonSocketWriter writer = new JsonSocketWriter(socket);
		JsonSocketReader reader = new JsonSocketReader(socket);
		this.connector.startServer();

		writer.write(JsonTemplates.MM_CLIENT_HELLO(PeerConnector.DEFAULT_LOCAL_PORT));
		System.out.println("check");
		JsonObject gamestart = reader.next();

		this.game.setLeft(gamestart.get("you").getAsString().equals("left"));

		String oppHost = gamestart.get("opp_host").getAsString();
		int oppPort = gamestart.get("opp_port").getAsInt();
		this.connector.startClient(oppHost, oppPort);
		
		JsonObject iv = gamestart.get("iv").getAsJsonObject();
		double x = iv.get("x").getAsDouble();
		double y = iv.get("y").getAsDouble();
		double dx = iv.get("dx").getAsDouble();
		double dy = iv.get("dy").getAsDouble();
		this.game.getBall().setX(x);
		this.game.getBall().setY(y);
		long sleep = gamestart.get("start").getAsLong() - System.currentTimeMillis();
		try {
			Thread.sleep(sleep);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.game.getBall().addVector(new Vector(dx, dy));

		return null;
	}
}
