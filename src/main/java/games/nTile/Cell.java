package games.nTile;

import java.io.Serializable;

/**
 * Cell represent integer value.
 * 
 */

public class Cell implements Serializable {

	private static final long serialVersionUID = 1L;
	/** Value of the cell. */
	private final int value;

	/**
	 * Constructor.
	 * 
	 * @param value
	 *            value of the cell
	 */
	public Cell(int value) {
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}

	@Override
	public String toString() {
		return getValue() + "";
	}
}
