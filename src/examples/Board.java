package examples;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * Examples of Java 2D Graphics taken from
 * http://zetcode.com/tutorials/javagamestutorial/movingsprites/
 * 
 * @author ZetCode
 */
@SuppressWarnings("serial")
public class Board extends JPanel implements ActionListener {

	private Timer timer;
	private Block spaceShip;
	private final int DELAY = 10;

	public Board() {

		initBoard();
	}

	private void initBoard() {

		addKeyListener(new TAdapter());
		setBackground(Color.white);

		spaceShip = new Block();

		timer = new Timer(DELAY, this);
		timer.start();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		doDrawing(g);

		Toolkit.getDefaultToolkit().sync();
	}

	private void doDrawing(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;

		g2d.drawRect(spaceShip.getX(), spaceShip.getY(), spaceShip.getWidth(), spaceShip.getHeight());
	}

	public void actionPerformed(ActionEvent e) {
		step();
	}

	private void step() {
		spaceShip.move();

		repaint();
	}

	private class TAdapter extends KeyAdapter {

		@Override
		public void keyReleased(KeyEvent e) {
			spaceShip.keyReleased(e);
		}

		@Override
		public void keyPressed(KeyEvent e) {
			System.out.println("step");
			spaceShip.keyPressed(e);
		}
	}
}