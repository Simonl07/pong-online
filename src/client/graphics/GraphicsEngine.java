package client.graphics;

import java.awt.Color;
import java.awt.Graphics;

import client.game.Ball;
import client.game.Block;
import client.game.Game;

public class GraphicsEngine {

	private Game game;
	private GraphicsComponent graphicsComponent;
	private RenderThread renderer;

	public GraphicsEngine(Game game) {
		this.game = game;
		this.graphicsComponent = new GraphicsComponent(this.game, this);
		this.renderer = new RenderThread(this.graphicsComponent);
		this.renderer.start();
	}

	public GraphicsComponent getGraphicsComponent() {
		return this.graphicsComponent;
	}

	public void paintGraphics(Graphics g) {
		paintP1Block(g);
		paintBall(g);
	}

	private void paintBall(Graphics g) {
		Ball ball = this.game.getBall();
		g.setColor(Color.white);
		g.fillOval(ball.getX(), ball.getY(), ball.getR() * 2, ball.getR() * 2);
	}

	private void paintP1Block(Graphics g) {
		Block p1 = this.game.getP1();
		g.setColor(Color.white);
		g.fillRect(p1.getX(), p1.getY(), p1.getWidth(), p1.getHeight());
	}
}
