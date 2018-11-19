package client.graphics;

import client.game.Game;

public class RenderThread extends Thread {

	private Game g;

	public RenderThread(Game g) {
		this.g = g;
	}

	@Override
	public void run() {
		while (true) {
			// System.out.println(this.p1.getVector());
			g.physics();
			g.repaint();
		}
	}
}
