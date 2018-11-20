package client;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

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
		PhysicsEngine physicsEngine = new PhysicsEngine(g, 120);
		GraphicsEngine graphicsEngine = new GraphicsEngine(g, physicsEngine);

		this.add(graphicsEngine.getGraphicsComponent());
		this.setTitle("pong-client");
		this.setSize(1200, 620);

		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {

		try {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("src/client/dash_digital-7.ttf")));
		} catch (IOException | FontFormatException e) {
			e.printStackTrace();
		}
		
		

		EventQueue.invokeLater(() -> {
			Client ex = new Client();
			ex.setVisible(true);
		});
	}
}
