package physics;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;

public class Block implements Collidable {

	private static int DEFAULT_START_X = 10;
	private static int DEFAULT_START_Y = 100;
	private static int DEFAULT_WIDTH = 20;
	private static int DEFAULT_HEIGHT = 120;
	private static final double MAX_DY = 0.2;
	private int x;
	private int y;
	private int w;
	private int h;
	private int prevY;

	public Block() {
		this(DEFAULT_START_X, DEFAULT_START_Y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	public Block(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
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
		this.prevY = this.y;
		this.y = e.getY() - this.h / 2;
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(this.x, this.y, this.w, this.h);
	}

	@Override
	public Vector getVector() {
		double diff = (this.y - this.prevY) / 5;
		System.out.println("y" + this.y + " prevY" + this.prevY + "  " + diff);
		if(Math.abs(diff) < Block.MAX_DY){
			return new Vector(0.0, diff);
		}
		return new Vector(0.0, (diff / Math.abs(diff)) * Block.MAX_DY);
	}

	@Override
	public void collideWith(Collidable c) {
		// Nothing
	}
}
