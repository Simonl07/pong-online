package client.physics;

import client.game.Ball;
import client.game.Block;
import client.game.Game;
import client.util.AverageRateOfChangeQueue;

public class PhysicsThread extends Thread {

	private Game game;
	private int exitRounds;
	private long targetDelay;
	private AverageRateOfChangeQueue<Integer> arocq;
	private static final int MAXIMUM_PPS_SUPPORTED = 200;

	public PhysicsThread(Game game, int targetPPS) {
		this.game = game;
		this.setPPS(targetPPS);
		this.arocq = new AverageRateOfChangeQueue<>(MAXIMUM_PPS_SUPPORTED);
	}

	public void setPPS(int targetPPS) {
		this.targetDelay = PPS2Delay(targetPPS);
	}

	@Override
	public void run() {
		long start, delay;
		while (true) {
			start = System.currentTimeMillis();
			physics();
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
			this.game.setPps(arocq.getEntryFrequency(1000));
		}
	}

	private int PPS2Delay(int pps) {

		return pps == 0 ? 0 : (int) (1000.0 / pps);
	}

	public void physics() {
		Ball ball = this.game.getBall();
		Block p1 = this.game.getP1();

		if (!checkWall(ball)) {
			if (exitRounds == 0 && intersect(ball, p1) && ball.getVector().getDx() < 0) {
				System.out.println("Ball: " + ball.getVector());
				System.out.println("P1 Block: " + p1.getVector());
				ball.getVector().setDx(Math.abs(ball.getVector().getDx()));
				ball.getVector().add(p1.getVector());

				System.out.println("Ball: " + ball.getVector());
				System.out.println("P1 Block:" + p1.getVector());
				this.exitRounds = 2;
			}
			if (exitRounds != 0) {
				exitRounds--;
			}
		}
		ball.update(System.currentTimeMillis());
	}

	private boolean intersect(Ball ball, Block block) {
		return ball.getBounds().intersects(block.getBounds());
	}

	private boolean checkWall(Ball ball) {
		if (ball.getX() < 0 && ball.getVector().getDx() < 0 || ball.getX() > this.game.getWidth() - ball.getR() * 2 && ball.getVector().getDx() > 0) {
			ball.getVector().setDx(-1 * ball.getVector().getDx());
			ball.getVector().setTimestamp(System.currentTimeMillis());
			return true;
		} else if (ball.getY() < 0 && ball.getVector().getDy() < 0 || ball.getY() > this.game.getHeight() - ball.getR() * 2 && ball.getVector().getDy() > 0) {
			ball.getVector().setDy(-1 * ball.getVector().getDy());
			ball.getVector().setTimestamp(System.currentTimeMillis());

			return true;
		}
		return false;
	}
}
