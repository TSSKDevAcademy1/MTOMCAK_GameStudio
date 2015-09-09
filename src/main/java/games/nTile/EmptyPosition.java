package games.nTile;
import java.io.Serializable;

/**
 * Represent position of empty cell (zero value).
 */
public class EmptyPosition implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** x position. */
	private int x;
	/** y position. */
	private int y;

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x
	 *            the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y
	 *            the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}
}
