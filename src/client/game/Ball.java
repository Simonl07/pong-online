package client.game;

import java.awt.Rectangle;

public class Ball implements Collidable {

	private double x;
	private double y;
	private int r;
	private Vector vector;

	public Ball(int x, int y, int r) {
		this.x = x;
		this.y = y;
		this.r = r;
		this.vector = new Vector();
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return (int) x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return (int) y;
	}

	/**
	 * @return the r
	 */
	public int getR() {
		return r;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * @param r the r to set
	 */
	public void setR(int r) {
		this.r = r;
	}

	public void addVector(Vector v) {
		this.vector.add(v);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(this.getX(), this.getY(), this.r, this.r);
	}

	@Override
	public void collideWith(Collidable c) {
		this.vector.add(c.getVector());
	}

	@Override
	public Vector getVector() {
		return this.vector;
	}

	public void update(long newTimestamp) {
		long diff = newTimestamp - this.getVector().getTimestamp();
		this.x += this.getVector().getDx() * diff;
		this.y += this.getVector().getDy() * diff;
		this.getVector().setTimestamp(newTimestamp);
	}
}
