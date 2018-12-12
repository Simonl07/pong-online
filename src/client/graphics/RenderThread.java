package client.graphics;

import javax.swing.JPanel;

import client.game.Game;
import client.util.AverageRateOfChangeQueue;

public class RenderThread extends Thread {

	private Game game;
	private JPanel graphicsComponent;
	private int targetDelay;
	private AverageRateOfChangeQueue<Integer> arocq;
	private static final int MAXIMUM_FPS_SUPPORTED = 200;

	public RenderThread(Game game, JPanel graphicsComponent, int targetFPS) {
		this.game = game;
		this.graphicsComponent = graphicsComponent;
		this.targetDelay = FPS2Delay(targetFPS);
		this.arocq = new AverageRateOfChangeQueue<>(MAXIMUM_FPS_SUPPORTED);

	}

	private int FPS2Delay(int fps) {
		return fps == 0 ? 0 : (int) (1000.0 / fps);
	}

	@Override
	public void run() {
		long start, delay;
		while (true) {
			start = System.currentTimeMillis();
			this.graphicsComponent.repaint();
			arocq.add(0);
			if (this.targetDelay > 0) {
				delay = this.targetDelay - (System.currentTimeMillis() - start);
				if (delay > 0) {
					try {
						Thread.sleep(delay);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			this.game.setFps(arocq.getEntryFrequency(1000));
		}
	}
}
