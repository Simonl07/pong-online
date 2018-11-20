package client.game;

import java.awt.event.MouseEvent;

public class Game {

	private Block p1;
	private Ball ball;
	private int width;
	private int height;
	private int exitrounds;

	public Game(int width, int height) {
		this.p1 = new Block();
		this.ball = new Ball(150, 30, 5);
		this.width = width;
		this.height = height;
		initBoard();
	}

	private void initBoard() {
		this.ball.getVector().setDx(0.25);
		this.ball.getVector().setDy(0.25);
	}

	/**
	 * @return the p1
	 */
	public Block getP1() {
		return p1;
	}

	/**
	 * @return the ball
	 */
	public Ball getBall() {
		return ball;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @return the exitrounds
	 */
	public int getExitrounds() {
		return exitrounds;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	public void updateMouse(MouseEvent e) {
		this.p1.update(e);
	}
}
