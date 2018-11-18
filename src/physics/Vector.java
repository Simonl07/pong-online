package physics;

public class Vector {
	private double dx;
	private double dy;
	private long timestamp;

	public Vector() {
		this(0.0, 0.0, System.currentTimeMillis());
	}

	public Vector(double dx, double dy) {
		this(dx, dy, System.currentTimeMillis());
	}

	public Vector(double dx, double dy, long timestamp) {
		this.dx = dx;
		this.dy = dy;
		this.timestamp = timestamp;
	}

	/**
	 * @return the dx
	 */
	public double getDx() {
		return dx;
	}

	/**
	 * @return the dy
	 */
	public double getDy() {
		return dy;
	}

	/**
	 * @param dx the dx to set
	 */
	public void setDx(double dx) {
		this.timestamp = System.currentTimeMillis();
		this.dx = dx;
	}

	/**
	 * @param dy the dy to set
	 */
	public void setDy(double dy) {
		this.timestamp = System.currentTimeMillis();
		this.dy = dy;
	}

	/**
	 * @return the timestamp
	 */
	public long getTimestamp() {
		return timestamp;
	}

	/**
	 * @return the timestamp
	 */
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public void add(Vector other) {
		System.out.println("NEW");
		this.timestamp = System.currentTimeMillis();
		this.dx += other.dx;
		this.dy += other.dy;
	}

	@Override
	public String toString() {
		return "Vector [dx=" + dx + ", dy=" + dy + "]";
	}

}
