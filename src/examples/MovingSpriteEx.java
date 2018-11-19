package examples;

import java.awt.EventQueue;
import javax.swing.JFrame;

/**
 * Examples of Java 2D Graphics taken from
 * http://zetcode.com/tutorials/javagamestutorial/movingsprites/
 * 
 * @author ZetCode
 */
@SuppressWarnings("serial")
public class MovingSpriteEx extends JFrame {

	public MovingSpriteEx() {

		initUI();
	}

	private void initUI() {

		add(new Board());

		setTitle("Moving sprite");
		setSize(400, 300);

		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {

		EventQueue.invokeLater(() -> {
			MovingSpriteEx ex = new MovingSpriteEx();
			ex.setVisible(true);
		});
	}
}