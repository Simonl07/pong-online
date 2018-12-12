package client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import client.network.NetworkEngine;

@SuppressWarnings("serial")
public class MenuPanel extends JPanel implements ActionListener {

	private GUI gui;
	private GamePanel gamePanel;
	private String serverHost;
	private int serverPort;
	private JLabel loading;

	public MenuPanel(GUI gui, GamePanel g, String serverHost, int serverPort) {
		this.gui = gui;
		this.gamePanel = g;
		this.serverHost = serverHost;
		this.serverPort = serverPort;
		initUI();
	}

	private void initUI() {
		this.setSize(1000, 600);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBackground(Color.black);
		this.add(Box.createRigidArea(new Dimension(0, 200)));
		JLabel label = new JLabel();
		label.setText("PONG Online");
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		label.setFont(new Font("Serif", Font.PLAIN, 54));
		label.setForeground(Color.WHITE);
		this.add(label);
		this.add(Box.createRigidArea(new Dimension(0, 80)));
		JButton findMatch = new JButton();
		findMatch.setText("find match");
		findMatch.setAlignmentX(Component.CENTER_ALIGNMENT);
		findMatch.setBorderPainted(true);
		findMatch.setFocusPainted(true);
		findMatch.setContentAreaFilled(false);
		findMatch.addActionListener(this);
		this.add(findMatch);
		this.setVisible(true);
		this.loading = new JLabel("pairing with other players...");
		this.loading.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(Box.createRigidArea(new Dimension(0, 20)));
		this.loading.setFont(new Font("Serif", Font.PLAIN, 14));
		this.loading.setVisible(false);
		this.loading.setForeground(Color.WHITE);
		this.add(loading);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		loading.setVisible(true);
		Thread t = new Thread() {
			@Override
			public void run() {
				NetworkEngine ne = new NetworkEngine(gamePanel.getGame(), serverHost, serverPort);
				gamePanel.start(ne);
				try {
					ne.findMatch();
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				gui.showGame();
			}
		};
		t.start();
	}

}
