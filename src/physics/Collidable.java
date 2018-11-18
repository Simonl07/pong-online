package physics;

import java.awt.Rectangle;

public interface Collidable {

	public Rectangle getBounds();
	
	public Vector getVector();

	public void collideWith(Collidable c);
}
