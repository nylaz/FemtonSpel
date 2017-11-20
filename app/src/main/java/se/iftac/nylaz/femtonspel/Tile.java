/*Lars Nyman
 *2016-11-15
 *Laboration 3 - Java SE - Javautvecklare -16
 */
package se.iftac.nylaz.femtonspel;

/**
 * The Class Tile.
 */
public class Tile {

	/** The value of the tile. */
	private int value;

	/**
	 * Instantiates a new tile.
	 *
	 * @param value
	 *            The value of the tile
	 */
	public Tile(int value) {
		if (value < 16 && value >= 0) {
			this.value = value;
		}
	}

	/**
	 * Checks if the value of the tile is 0.
	 *
	 * @return true, if the value is 0
	 */
	public boolean isEmpty() {
		if (value == 0) {
			return true;
		} else
			return false;
	}

	/**
	 * Gets the current value of the tile.
	 *
	 * @return The value of the tile.
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Sets the new value to the tile.
	 *
	 * @param value
	 *            The chosen value of the tile.
	 */
	public void setValue(int value) {
		if (value < 16 && value >= 0) {
			this.value = value;
		}
	}
}
