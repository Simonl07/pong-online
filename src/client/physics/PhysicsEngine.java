package client.physics;

import client.game.Game;

public class PhysicsEngine {
	
	private Game game;
	private PhysicsThread physicsThread;
	
	public PhysicsEngine(Game game){
		this.game = game;
		this.physicsThread = new PhysicsThread(this.game);
		this.physicsThread.start();
	}
	
}
