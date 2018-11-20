package client.physics;

import client.game.Ball;
import client.game.Block;
import client.game.Game;

public class PhysicsThread extends Thread {

	private Game game;
	private int exitRounds;

	public PhysicsThread(Game game) {
		this.game = game;
	}

	@Override
	public void run() {
		while(true){
			physics();
		}
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
