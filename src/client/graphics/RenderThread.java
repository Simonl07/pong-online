package client.graphics;

import javax.swing.JPanel;

import client.game.Game;
import client.util.AverageRateOfChangeQueue;

public class RenderThread extends Thread {

	private JPanel graphicsComponent;
	private int targetDelay;
	private AverageRateOfChangeQueue<Integer> arocq;
	private static final int MAXIMUM_FPS_SUPPORTED = 200;
	private int fps;

	public RenderThread(JPanel graphicsComponent, int targetFPS) {
		this.graphicsComponent = graphicsComponent;
		this.targetDelay = FPS2Delay(targetFPS);
		this.arocq = new AverageRateOfChangeQueue<>(MAXIMUM_FPS_SUPPORTED);
		this.fps = 0;

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
			this.setFps(arocq.getEntryFrequency(1000));
		}
	}

	public int getFps() {
		return fps;
	}

	public void setFps(int fps) {
		this.fps = fps;
	}

}
