package client.game;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import client.util.AverageRateOfChangeQueue;

public class Block implements Collidable {

	public static final int DEFAULT_START_X = 10;
	public static final int DEFAULT_START_Y = 10;
	public static final int DEFAULT_WIDTH = 20;
	public static final int DEFAULT_HEIGHT = 120;
	public static final double MAX_DY = 0.2;
	private int x;
	private int y;
	private int w;
	private int h;
	private AverageRateOfChangeQueue<Integer> arocq;

	public Block() {
		this(DEFAULT_START_X, DEFAULT_START_Y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}
	
	public Block(int x){
		this(x, DEFAULT_START_Y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	public Block(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.arocq = new AverageRateOfChangeQueue<>(3);
	}

	public int getX() {

		return x;
	}

	public int getY() {

		return y;
	}

	public int getWidth() {

		return w;
	}

	public int getHeight() {

		return h;
	}

	public void update(MouseEvent e) {
		this.y = e.getY() - this.h / 2;
		this.arocq.add(this.y);
	}
	
	

	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(this.x, this.y, this.w, this.h);
	}

	/**
	 * block_vector = 0.3 * log(|deltaY / 30| + 1)
	 * 
	 * The logarithm causes diminishing dy as user acheive higher instantaneous
	 * deltaY. The 0.3 is adjusted for optimal curve of acceration. The + 1
	 * ensures all values are greater than 0. If at the end dy is greater than
	 * maximum dy allowed for each block, max dy is returned instead
	 */
	@Override
	public Vector getVector() {
		double speed = this.arocq.getValueROC(100);
		int sign = (int) (speed / Math.abs(speed));
		double dy = Math.log(Math.abs(speed) + 1) * 0.2 * sign;

		if (Math.abs(dy) < Block.MAX_DY) {
			return new Vector(0.0, dy);
		}
		return new Vector(0.0, sign * Block.MAX_DY);
	}
}
