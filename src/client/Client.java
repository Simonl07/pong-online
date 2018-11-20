package client;

import java.awt.EventQueue;

import javax.swing.JFrame;

import client.game.Game;
import client.graphics.GraphicsEngine;
import client.physics.PhysicsEngine;

@SuppressWarnings("serial")
public class Client extends JFrame {

	public Client() {
		initUI();
	}

	private void initUI() {
		Game g = new Game(1200, 600);
		new PhysicsEngine(g);
		GraphicsEngine graphicsEngine = new GraphicsEngine(g);

		this.add(graphicsEngine.getGraphicsComponent());
		this.setTitle("pong-client");
		this.setSize(1200, 620);

		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {

		EventQueue.invokeLater(() -> {
			Client ex = new Client();
			ex.setVisible(true);
		});
	}
}
