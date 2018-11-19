package physics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GameBoard extends JPanel implements Runnable {

	private Block p1;
	private Ball b;
	private final int DELAY = 1;
	private Thread animator;
	private int width;
	private int height;
	private int exitrounds;

	public GameBoard(int width, int height) {
		this.p1 = new Block();
		this.b = new Ball(150, 30, 5);
		this.width = width;
		this.height = height;
		initBoard();
	}

	private void initBoard() {

		this.addMouseMotionListener(new Mouse());
		setBackground(Color.black);
		this.b.getVector().setDx(0.25);
		this.b.getVector().setDy(0.25);
	}

	@Override
	public void addNotify() {
		super.addNotify();

		animator = new Thread(this);
		animator.start();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		paintP1Block(g);
		paintBall(g);
	}

	private void paintBall(Graphics g) {
		g.setColor(Color.white);
		g.fillOval(this.b.getX(), this.b.getY(), this.b.getR() * 2, this.b.getR() * 2);
	}

	private void paintP1Block(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(this.p1.getX(), this.p1.getY(), this.p1.getWidth(), this.p1.getHeight());
	}

	private class Mouse implements MouseMotionListener {

		@Override
		public void mouseDragged(MouseEvent e) {
			// Empty
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			p1.update(e);
		}
	}

	@Override
	public void run() {

		long beforeTime, timeDiff, sleep;

		beforeTime = System.currentTimeMillis();

		while (true) {
//			System.out.println(this.p1.getVector());
			physics();
			repaint();

			timeDiff = System.currentTimeMillis() - beforeTime;
			sleep = DELAY - timeDiff;

			if (sleep < 0) {
				sleep = 2;
			}

			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {

				String msg = String.format("Thread interrupted: %s", e.getMessage());

				JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
			}

			beforeTime = System.currentTimeMillis();
		}
	}

	private void physics() {

//		System.out.println(b.getX() + " " + b.getY());
		if (!checkWall()) {
			if (exitrounds == 0 && this.b.getBounds().intersects(p1.getBounds()) && this.b.getVector().getDx() < 0) {
				System.out.println("Ball: " + b.getVector());
				System.out.println("P1 Block: " + p1.getVector());
				this.b.getVector().setDx(Math.abs(this.b.getVector().getDx()));
				this.b.getVector().add(this.p1.getVector());
				
				System.out.println("Ball: " + b.getVector());
				System.out.println("P1 Block:" + p1.getVector());
				this.exitrounds = 2;
			}
			if (exitrounds != 0) {
				exitrounds--;
			}
		}
		b.update(System.currentTimeMillis());
	}

	private boolean checkWall() {
		if (this.b.getX() < 0 && this.b.getVector().getDx() < 0 || this.b.getX() > this.width - this.b.getR() * 2 && this.b.getVector().getDx() > 0) {
			this.b.getVector().setDx(-1 * this.b.getVector().getDx());
			this.b.getVector().setTimestamp(System.currentTimeMillis());
			return true;
		} else if (this.b.getY() < 0 && this.b.getVector().getDy() < 0 || this.b.getY() > this.height - this.b.getR() * 2 && this.b.getVector().getDy() > 0) {
			this.b.getVector().setDy(-1 * this.b.getVector().getDy());
			this.b.getVector().setTimestamp(System.currentTimeMillis());

			return true;
		}
		return false;
	}

}
