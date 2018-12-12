package client;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import client.game.Game;
import client.graphics.GraphicsEngine;
import client.network.NetworkEngine;
import client.physics.PhysicsEngine;

@SuppressWarnings("serial")
public class GamePanel extends JPanel {

	private GraphicsEngine graphicsEngine;
	private PhysicsEngine physicsEngine;
	private NetworkEngine networkEngine;
	private Game game;
	private GUI gui;

	public GamePanel(GUI gui) {
		this.game = new Game(1000, 600);
		this.gui = gui;
	}

	public void start(NetworkEngine networkEngine) {
		this.networkEngine = networkEngine;
		this.physicsEngine = new PhysicsEngine(this.game, networkEngine, 120);
		this.graphicsEngine = new GraphicsEngine(this.game, this, this.physicsEngine, this.networkEngine, 60);

		this.addMouseMotionListener(new Mouse());
	}

	public Game getGame() {
		return game;
	}

	@Override
	public void paintComponent(Graphics g) {
		graphicsEngine.paintGraphics(g);
	}

	private class Mouse implements MouseMotionListener {
		@Override
		public void mouseDragged(MouseEvent e) {
			// Empty
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			game.updateMouse(e);
		}
	}

}
