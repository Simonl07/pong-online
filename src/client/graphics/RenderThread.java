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
			try {
				Thread.sleep(16);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
