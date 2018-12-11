package client.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;

import client.game.Ball;
import client.game.Block;
import client.game.Game;
import client.network.NetworkEngine;
import client.physics.PhysicsEngine;

public class GraphicsEngine {

	private Game game;
	private GraphicsComponent graphicsComponent;
	private PhysicsEngine physicsEngine;
	private NetworkEngine networkEngine;
	private RenderThread renderer;
	private int gameWidth;
	private int gameHeight;
	private int targetFPS;
	private static Font SCORE_FONT;
	static {
		try {
			SCORE_FONT = Font.createFont(Font.TRUETYPE_FONT, new File("src/client/dash_digital-7.ttf"));
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public GraphicsEngine(Game game, PhysicsEngine physicsEngine, NetworkEngine networkEngine, int targetFPS) {
		this.game = game;
		this.gameWidth = game.getWidth();
		this.gameHeight = game.getHeight();
		this.graphicsComponent = new GraphicsComponent(this.game, this);
		this.physicsEngine = physicsEngine;
		this.networkEngine = networkEngine;
		this.targetFPS = targetFPS;
		this.renderer = new RenderThread(this.game, this.graphicsComponent, this.targetFPS);
		this.renderer.start();
	}

	public GraphicsComponent getGraphicsComponent() {
		return this.graphicsComponent;
	}

	public void paintGraphics(Graphics g) {
		paintMe(g);
		paintOpp(g);
		paintBall(g);
		paintMidLine(g);
		paintScore(g);
		paintPPS(g);
		paintFPS(g);
		paintSyncClock(g);
	}

	private void paintBall(Graphics g) {
		Ball ball = this.game.getBall();
		g.setColor(Color.WHITE);
		g.fillOval(ball.getX(), ball.getY(), ball.getR() * 2, ball.getR() * 2);
	}

	private void paintMe(Graphics g) {
		Block p1 = this.game.getMe();
		g.setColor(Color.WHITE);
		g.fillRect(p1.getX(), p1.getY(), p1.getWidth(), p1.getHeight());
	}

	private void paintOpp(Graphics g) {
		Block p2 = this.game.getOpponent();
		g.setColor(Color.WHITE);
		g.fillRect(p2.getX(), p2.getY(), p2.getWidth(), p2.getHeight());
	}

	private void paintMidLine(Graphics g) {
		int dashWidth = 6;
		int dashHeight = 6;
		int dashSpace = 10;
		g.setColor(Color.WHITE);
		for (int y = 0; y < this.gameHeight; y += dashHeight + dashSpace) {
			g.fillRect(this.gameWidth / 2 - dashWidth / 2, y, dashWidth, dashHeight);
		}
	}

	private void paintScore(Graphics g) {
		Font prev = g.getFont();
		g.setFont(GraphicsEngine.SCORE_FONT.deriveFont((float) 20.0));
		g.drawString(Integer.toString(this.game.getScoreP1()), (int) (this.gameWidth / 2 - this.gameWidth * 0.1 - 20), 40);
		g.drawString(Integer.toString(this.game.getScoreP2()), (int) (this.gameWidth / 2 + this.gameWidth * 0.1), 40);
		g.setFont(prev);
	}

	private void paintPPS(Graphics g) {
		Font prev = g.getFont();
		g.setFont(GraphicsEngine.SCORE_FONT.deriveFont((float) 8.0));
		g.drawString("target PHY/s: " + this.physicsEngine.getTargetPPS() + "  PHY/s: " + this.game.getPps(), 2, 17);
		g.setFont(prev);
	}

	private void paintFPS(Graphics g) {
		Font prev = g.getFont();
		g.setFont(GraphicsEngine.SCORE_FONT.deriveFont((float) 8.0));
		g.drawString("target FPS: " + this.targetFPS + "     FPS: " + this.game.getFps(), 2, 30);
		g.setFont(prev);
	}

	private void paintSyncClock(Graphics g) {
		Font prev = g.getFont();
		g.setFont(GraphicsEngine.SCORE_FONT.deriveFont((float) 8.0));
		g.drawString("sync clock: " + System.currentTimeMillis() + this.networkEngine.getClockOffset(), 2, 43);
		g.setFont(prev);
	}
}
