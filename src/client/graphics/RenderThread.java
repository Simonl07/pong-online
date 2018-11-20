package client.graphics;

public class RenderThread extends Thread {

	private GraphicsComponent g;

	public RenderThread(GraphicsComponent g) {
		this.g = g;
	}

	@Override
	public void run() {
		while (true) {
			g.repaint();
		}
	}
}
