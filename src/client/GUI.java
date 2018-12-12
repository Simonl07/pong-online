package client;

import java.awt.CardLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GUI extends JFrame {

	private CardLayout cardLayout = new CardLayout();
	private JPanel cardPanel = new JPanel(cardLayout);
	private String serverHost;
	private int serverPort;

	public GUI(String serverHost, int serverPort) {
		this.serverHost = serverHost;
		this.serverPort = serverPort;
		initUI();
	}

	private void initUI() {
		this.getContentPane().setLayout(cardLayout);
		GamePanel g = new GamePanel(this);
		MenuPanel m = new MenuPanel(this, g, this.serverHost, this.serverPort);
		this.cardPanel.add(m, "menu");
		this.cardPanel.add(g, "game");
		this.add(cardPanel, "cards");

		this.setTitle("pong-client");
		this.setSize(1000, 620);

		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void showMenu() {
		this.cardLayout.show(cardPanel, "menu");
	}

	public void showGame() {
		this.cardLayout.show(cardPanel, "game");
	}

	public static void main(String[] args) {

		String serverHost = args[0];
		int serverPort = Integer.parseInt(args[1]);

		EventQueue.invokeLater(() -> {
			GUI ex = new GUI(serverHost, serverPort);
			ex.setVisible(true);
		});
	}

}
