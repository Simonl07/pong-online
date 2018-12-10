package client.game;

import java.awt.event.MouseEvent;

public class Game {

	private Block left;
	private Block right;
	private Ball ball;
	private int width;
	private int height;
	private int scoreP1;
	private int scoreP2;
	private int pps;
	private int fps;
	private int gameStage;

	public Game(int width, int height) {
		this.width = width;
		this.height = height;
		this.left = new Block(30);
		this.right = new Block(this.width - Block.DEFAULT_WIDTH - 30);
		this.ball = new Ball();
		this.scoreP1 = 0;
		this.scoreP2 = 0;
		this.pps = 0;
		this.fps = 0;
		initBoard();
	}

	private void initBoard() {
		this.ball.getVector().setDx(0.3);
		this.ball.getVector().setDy(0.3);
	}

	/**
	 * @return the p1
	 */
	public Block getP1() {
		return left;
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
	 * @return the pps
	 */
	public int getPps() {
		return pps;
	}

	/**
	 * @return the fps
	 */
	public int getFps() {
		return fps;
	}

	/**
	 * @param pps the pps to set
	 */
	public void setPps(int pps) {
		this.pps = pps;
	}

	/**
	 * @param fps the fps to set
	 */
	public void setFps(int fps) {
		this.fps = fps;
	}
	
	

	/**
	 * @return the p2
	 */
	public Block getP2() {
		return right;
	}

	/**
	 * @param p2 the p2 to set
	 */
	public void setP2(Block p2) {
		this.right = p2;
	}

	/**
	 * @return the gameStage
	 */
	public int getGameStage() {
		return gameStage;
	}

	/**
	 * @param gameStage the gameStage to set
	 */
	public void setGameStage(int gameStage) {
		this.gameStage = gameStage;
	}

	public void updateMouse(MouseEvent e) {
		this.left.update(e);
	}
	
	
}
