package client.game;

import java.awt.event.MouseEvent;

public class Game {

	private Block left;
	private Block right;
	private boolean isLeft;
	private Ball ball;
	private int width;
	private int height;
	private int scoreP1;
	private int scoreP2;
	private long syncTimeDisplay;
	private boolean isActive;

	public Game(int width, int height) {
		this.width = width;
		this.height = height;
		this.left = new Block(50);
		this.right = new Block(this.width - Block.DEFAULT_WIDTH - 50);
		this.ball = new Ball();
		this.scoreP1 = 0;
		this.scoreP2 = 0;
		initBoard();
	}

	private void initBoard() {
		this.ball.setX(this.width / 2);
		this.ball.setY(30);
		this.ball.getVector().setDx(0);
		this.ball.getVector().setDy(0);
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

	/**
	 * @return the scoreP1
	 */
	public int getScoreP1() {
		return scoreP1;
	}

	/**
	 * @return the scoreP2
	 */
	public int getScoreP2() {
		return scoreP2;
	}

	/**
	 * @param scoreP1 the scoreP1 to set
	 */
	public void setScoreP1(int scoreP1) {
		this.scoreP1 = scoreP1;
	}

	/**
	 * @param scoreP2 the scoreP2 to set
	 */
	public void setScoreP2(int scoreP2) {
		this.scoreP2 = scoreP2;
	}

	/**
	 * @return the isLeft
	 */
	public boolean isLeft() {
		return isLeft;
	}

	/**
	 * @param isLeft the isLeft to set
	 */
	public void setLeft(boolean isLeft) {
		this.isLeft = isLeft;
	}

	public Block getMe() {
		return this.isLeft ? this.left : this.right;
	}

	public Block getOpponent() {
		return this.isLeft ? this.right : this.left;
	}

	/**
	 * @return the syncTimeDisplay
	 */
	public long getSyncTimeDisplay() {
		return syncTimeDisplay;
	}

	/**
	 * @param syncTimeDisplay the syncTimeDisplay to set
	 */
	public void setSyncTimeDisplay(long syncTimeDisplay) {
		this.syncTimeDisplay = syncTimeDisplay;
	}

	public void updateMouse(MouseEvent e) {
		this.getMe().update(e);
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

}
