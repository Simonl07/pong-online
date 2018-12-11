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
	private boolean peerConnected;
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
		long delay = System.currentTimeMillis() - gamestart.get("start").getAsLong();
		this.game.getBall().setX(x + dx * delay);
		this.game.getBall().setY(y + dy * delay);
		this.game.getBall().addVector(new Vector(dx, dy));

		System.out.println(String.format("delay=%d, x=%f, y=%f", delay, x, y));
		return null;
	}

	public void connectPeer(String host, int port, int localPort, boolean isLeft) {
		this.connector.startServer(localPort);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.connector.startClient(host, port);
		this.game.setLeft(isLeft);
		this.setPeerConnected(true);
	}

	/**
	 * @return the peerConnected
	 */
	public boolean isPeerConnected() {
		return peerConnected;
	}

	/**
	 * @param peerConnected the peerConnected to set
	 */
	public void setPeerConnected(boolean peerConnected) {
		this.peerConnected = peerConnected;
	}

}
