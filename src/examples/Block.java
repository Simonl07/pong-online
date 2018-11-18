package examples;

import java.awt.Image;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

public class Block {

	private int dx;
	private int dy;
	private int x = 40;
	private int y = 60;
	private int w;
	private int h;

	public Block() {
		this.w = 10;
		this.h = 10;
	}

	public void move() {
		x += dx;
		y += dy;
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

	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_A) {
			dx = -2;
		}

		if (key == KeyEvent.VK_D) {
			dx = 2;
		}

		if (key == KeyEvent.VK_W) {
			dy = -2;
		}

		if (key == KeyEvent.VK_S) {
			dy = 2;
			
		}
	}

	public void keyReleased(KeyEvent e) {

		int key = e.getKeyCode();

		if (key == KeyEvent.VK_A) {
			dx = 0;
		}

		if (key == KeyEvent.VK_D) {
			dx = 0;
		}

		if (key == KeyEvent.VK_W) {
			dy = 0;
		}

		if (key == KeyEvent.VK_S) {
			dy = 0;
		}
	}
}