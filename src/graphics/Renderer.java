package graphics;

import java.awt.EventQueue;

import javax.swing.JFrame;

import physics.GameBoard;

@SuppressWarnings("serial")
public class Renderer extends JFrame {

	public Renderer() {
		initUI();
	}

	private void initUI() {

		add(new GameBoard(800, 600));

		setTitle("Client");
		setSize(800, 620);

		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {

		EventQueue.invokeLater(() -> {
			Renderer ex = new Renderer();
			ex.setVisible(true);
		});
	}
}
