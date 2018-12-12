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
public class GUI extends JFrame {

	private String remoteHost;
	private int remotePort;
	private int localPort;

	public GUI(String remoteHost, int remotePort, int localPort) {
		this.remoteHost = remoteHost;
		this.remotePort = remotePort;
		this.localPort = localPort;
		initUI();
	}

	private void initUI() {
		Game g = new Game(1000, 600);
		NetworkEngine networkEngine = new NetworkEngine(g, "10.1.34.220", 8000);
		PhysicsEngine physicsEngine = new PhysicsEngine(g, networkEngine, 120);
		GraphicsEngine graphicsEngine = new GraphicsEngine(g, physicsEngine, networkEngine, 60);
//		try {
//			networkEngine.findMatch();
//		} catch (UnknownHostException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		this.add(graphicsEngine.getGraphicsComponent());
		networkEngine.connectPeer("localhost", 8889, 8888, true);

		
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
			GUI ex = new GUI(remotehost, remotePort, localPort);
			ex.setVisible(true);
		});
	}
}
