package client;

import java.awt.EventQueue;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JFrame;

import client.game.Game;
import client.graphics.GraphicsEngine;
import client.network.NetworkEngine;
import client.physics.PhysicsEngine;

@SuppressWarnings("serial")
public class Client extends JFrame {

	private String remoteHost;
	private int remotePort;
	private int localPort;

	public Client(String remoteHost, int remotePort, int localPort) {
		this.remoteHost = remoteHost;
		this.remotePort = remotePort;
		this.localPort = localPort;
		initUI();
	}

	private void initUI() {
		Game g = new Game(1000, 600);
		PhysicsEngine physicsEngine = new PhysicsEngine(g, 120);
		GraphicsEngine graphicsEngine = new GraphicsEngine(g, physicsEngine, 60);
		NetworkEngine networkEngine = new NetworkEngine(g, "serverhost", 0);
		try {
			networkEngine.findMatch();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.add(graphicsEngine.getGraphicsComponent());
		this.setTitle("pong-client");
		this.setSize(1000, 620);

		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.validate();
	}

	public static void main(String[] args) {

		String remotehost = args[0];
		int remotePort = Integer.parseInt(args[1]);
		int localPort = Integer.parseInt(args[2]);

		EventQueue.invokeLater(() -> {
			Client ex = new Client(remotehost, remotePort, localPort);
			ex.setVisible(true);
		});
	}
}
