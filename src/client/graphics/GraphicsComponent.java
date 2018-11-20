package client.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import client.game.Game;

@SuppressWarnings("serial")
public class GraphicsComponent extends JPanel {

	private Game game;
	private GraphicsEngine graphicsEngine;

	public GraphicsComponent(Game game, GraphicsEngine graphicsEngine) {
		this.game = game;
		this.graphicsEngine = graphicsEngine;
		this.addMouseMotionListener(new Mouse());
		this.setDoubleBuffered(true);
		this.setBackground(Color.BLACK);
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
