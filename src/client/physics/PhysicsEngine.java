package client.physics;

import client.game.Game;
import client.network.NetworkEngine;

public class PhysicsEngine {

	private Game game;
	private int targetPPS;
	private PhysicsThread physicsThread;

	public PhysicsEngine(Game game, NetworkEngine networkEngine, int targetPPS) {
		this.game = game;
		this.targetPPS = targetPPS;
		this.physicsThread = new PhysicsThread(this.game, networkEngine, this.targetPPS);
		this.physicsThread.start();
	}

	/**
	 * @return the targetPPS
	 */
	public int getTargetPPS() {
		return targetPPS;
	}

	/**
	 * @param targetPPS the targetPPS to set
	 */
	public void setTargetPPS(int targetPPS) {
		this.targetPPS = targetPPS;
	}

}
