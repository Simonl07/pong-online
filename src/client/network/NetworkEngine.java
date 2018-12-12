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
	private PeerConnector connector;
	private boolean peerConnected;
	private Game game;
	private long clockOffset;
	private JsonSocketWriter writer;
	private JsonSocketReader reader;
	private ServerResponseMonitor serverMonitor;

	public NetworkEngine(Game game, String serverHost, int serverPort) {
		this.GAME_SERVER_HOST = serverHost;
		this.GAME_SERVER_PORT = serverPort;
		this.game = game;
		this.connector = new PeerConnector(game);
	}

	public void findMatch() throws UnknownHostException, IOException {
		Socket socket = new Socket(GAME_SERVER_HOST, GAME_SERVER_PORT);
		this.writer = new JsonSocketWriter(socket);
		this.reader = new JsonSocketReader(socket);

		writer.write(JsonTemplates.MM_CLIENT_HELLO(PeerConnector.DEFAULT_LOCAL_PORT));
		JsonObject gamestart = reader.next();
		this.game.setLeft(gamestart.get("you").getAsString().equals("left"));
		String oppHost = gamestart.get("opp_host").getAsString();
		int oppPort = gamestart.get("opp_port").getAsInt();
		this.connectPeer(oppHost, oppPort, PeerConnector.DEFAULT_LOCAL_PORT, this.game.isLeft());

		JsonObject iv = gamestart.get("iv").getAsJsonObject();
		double x = iv.get("x").getAsDouble();
		double y = iv.get("y").getAsDouble();
		double dx = iv.get("dx").getAsDouble();
		double dy = iv.get("dy").getAsDouble();
		long delay = System.currentTimeMillis() - gamestart.get("start").getAsLong();
		long delayAdjusted = this.game.isLeft() ? delay + this.getClockOffset() : delay;
		System.out.println(delayAdjusted + "|" + (x + dx * delayAdjusted));
		this.game.getBall().setX(x + dx * delayAdjusted);
		this.game.getBall().setY(y + dy * delayAdjusted);
		this.game.getBall().addVector(new Vector(dx, dy));

		// System.out.println(String.format("delay=%d, x=%f, y=%f", delay, x,
		// y));
		this.serverMonitor = new ServerResponseMonitor();
		this.serverMonitor.start();
	}

	public void connectPeer(String host, int port, int localPort, boolean isLeft) {
		if (isLeft) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.game.setLeft(isLeft);
			long offset = ClockSynchronizer.syncAsLeft(host, port);
			this.setClockOffset(offset);
			System.out.println(String.format("Offset obtained: %dms", offset));
		} else {
			ClockSynchronizer.syncAsRight(localPort);
			this.setClockOffset(0);
		}

		this.connector.startServer(localPort);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.connector.startClient(host, port);
		this.setPeerConnected(true);
	}

	public void reportReflect(int x, int y, double dx, double dy) {
		JsonObject json;
		if (this.game.isLeft()) {
			json = JsonTemplates.IG_CLIENT_REFLECT(x, y, dx, dy, System.currentTimeMillis() + this.getClockOffset());
		} else {
			json = JsonTemplates.IG_CLIENT_REFLECT(x, y, dx, dy, System.currentTimeMillis());
		}
		System.out.println("BOOM!\n" + json.toString());
		this.writer.write(json);
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

	/**
	 * @return the clockOffset
	 */
	public long getClockOffset() {
		return clockOffset;
	}

	/**
	 * @param clockOffset the clockOffset to set
	 */
	public void setClockOffset(long clockOffset) {
		this.clockOffset = clockOffset;
	}

	private class ServerResponseMonitor extends Thread {

		@Override
		public void run() {
			while (true) {
				JsonObject reflect = reader.next();
				if (reflect.get("type").getAsString().equals("ig_server_broadcast_reflect")) {
					JsonObject v = reflect.get("v").getAsJsonObject();
					double x = v.get("x").getAsDouble();
					double y = v.get("y").getAsDouble();
					double dx = v.get("dx").getAsDouble();
					double dy = v.get("dy").getAsDouble();

					synchronized (game.getBall()) {
						game.getBall().setX(x);
						game.getBall().setY(y);
						game.getBall().getVector().setDx(dx);
						game.getBall().getVector().setDy(dy);
						game.getBall().getVector().setTimestamp(reflect.get("start").getAsLong());
					}
				}
			}
		}

	}

}
