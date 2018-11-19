package client;

import java.awt.EventQueue;

import javax.swing.JFrame;

import client.game.Game;
import client.graphics.RenderThread;

@SuppressWarnings("serial")
public class Client extends JFrame {

	private RenderThread rt;
	
	public Client() {
		initUI();
	}

	private void initUI() {

		Game g = new Game(1200, 600);
		add(g);

		setTitle("Client");
		setSize(1200, 620);

		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.rt = new RenderThread(g);
		this.rt.start();
	}

	public static void main(String[] args) {

		EventQueue.invokeLater(() -> {
			Client ex = new Client();
			ex.setVisible(true);
		});
	}
}
