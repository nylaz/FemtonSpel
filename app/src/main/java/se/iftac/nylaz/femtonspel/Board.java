/*Lars Nyman
 *2016-11-15
 *Laboration 3 - Java SE - Javautvecklare -16
 */
package se.iftac.nylaz.femtonspel;

import java.util.ArrayList;

/**
 * The Class Board.
 */
public class Board {

	/** An ArrayList containing the tiles that make up the board. */
	private ArrayList<ArrayList<Tile>> tiles = new ArrayList<ArrayList<Tile>>();

	/**
	 * The pre-defined directions for the game, containing UP,LEFT,DOWN,RIGHT.
	 */
	public static enum Direction {

		/** The up choice. */
		UP,
		/** The left choice. */
		LEFT,
		/** The down choice. */
		DOWN,
		/** The right choice. */
		RIGHT
	}

	/**
	 * Instantiates a new 4x4 board.
	 */
	public Board() {
		int tileValue = 1;
		for (int row = 0; row < 4; row++) {
			tiles.add(new ArrayList<Tile>());
			for (int column = 0; column < 4; column++) {
				tiles.get(row).add(new Tile(tileValue));
				tileValue++;
			}
		}
		getTile(3,3).setValue(0);
	}

	/**
	 * Shuffle the board 100 times according to the moveEmptyTile() call.
	 */
	public void shuffleBoard() {
		for (int times = 0; times < 10; times++) {
			double direction = Math.random() * 100;
			if (direction < 25) {
				moveEmptyTile(Direction.UP);
			} else if (direction >= 25 && direction < 50) {
				moveEmptyTile(Direction.LEFT);
			} else if (direction >= 50 && direction < 75) {
				moveEmptyTile(Direction.DOWN);
			} else if (direction >= 75) {
				moveEmptyTile(Direction.RIGHT);
			}
		}
	}

	/**
	 * The logic to move the tile with the value 0 one step in the chosen direction.
	 *
	 * @param move
	 *            The chosen direction from the user.
	 * @return true, if a successful swap is made
	 */
	public boolean moveEmptyTile(Direction move) {
		Integer emptyRow = findEmptyTile().get(0);
		Integer emptyColumn = findEmptyTile().get(1);
		Tile emptyTile = getTile(emptyRow, emptyColumn);
		switch (move) {
		case UP:
			if (emptyRow.equals(0)) {
				return false;
			} else {
				setTile(emptyRow, emptyColumn, getTile(emptyRow - 1, emptyColumn));
				setTile(emptyRow - 1, emptyColumn, emptyTile);
			}
			break;
		case LEFT:
			if (emptyColumn.equals(0)) {
				return false;
			} else {
				setTile(emptyRow, emptyColumn, getTile(emptyRow, emptyColumn - 1));
				setTile(emptyRow, emptyColumn - 1, emptyTile);
			}
			break;
		case DOWN:
			if (emptyRow.equals(3)) {
				return false;
			} else {
				setTile(emptyRow, emptyColumn, getTile(emptyRow + 1, emptyColumn));
				setTile(emptyRow + 1, emptyColumn, emptyTile);
			}
			break;
		case RIGHT:
			if (emptyColumn.equals(3)) {
				return false;
			} else {
				setTile(emptyRow, emptyColumn, getTile(emptyRow, emptyColumn + 1));
				setTile(emptyRow, emptyColumn + 1, emptyTile);
			}
			break;
		}
		return true;
	}

	/**
	 * The logic to move a tile if the tile is on the same row or column,
	 * depending on the direction of the move.
	 *
	 * @param row
	 *            The index of the row where the tile is placed.
	 * @param column
	 *            The index of the column where the tile is placed.
	 * @return true, if a successful move is made. (Can be multiple moves)
	 */
	public boolean moveTile(int row, int column) {
		Integer emptyRow = findEmptyTile().get(0);
		Integer emptyColumn = findEmptyTile().get(1);
		if (column == emptyColumn && row < emptyRow) {
			while (row < findEmptyTile().get(0)) {
				moveEmptyTile(Direction.UP);
			}
			return true;
		} else if (row == emptyRow && column > emptyColumn) {
			while (column > findEmptyTile().get(1)) {
				moveEmptyTile(Direction.RIGHT);
			}
			return true;
		} else if (column == emptyColumn && row > emptyRow) {
			while (row > findEmptyTile().get(0)) {
				moveEmptyTile(Direction.DOWN);
			}
			return true;
		} else if (row == emptyRow && column < emptyColumn) {
			while (column < findEmptyTile().get(1)) {
				moveEmptyTile(Direction.LEFT);
			}
			return true;
		}
		return false;
	}

	/**
	 * Find the position in the board where the tile with the value 0 is.
	 *
	 * @return An arrayList containing the row and column of the 0-valued tile.
	 */
	public ArrayList<Integer> findEmptyTile() {
		ArrayList<Integer> emptyCoord = new ArrayList<Integer>();
		for (int row = 0; row < 4; row++) {
			for (int column = 0; column < 4; column++) {
				if (getTile(row, column).getValue() == 0) {
					emptyCoord.add(row);
					emptyCoord.add(column);
				}
			}
		}
		return emptyCoord;
	}

	/**
	 * Gets the tile on a given position in the board.
	 *
	 * @param row
	 *            The given row.
	 * @param column
	 *            The given column.
	 * @return The tile on position (row, column).
	 */
	public Tile getTile(int row, int column) {

		return tiles.get(row).get(column);
	}

	/**
	 * Sets the tile in the board on a given position (row, column).
	 *
	 * @param row
	 *            The given row
	 * @param column
	 *            The given column
	 * @param tile
	 *            The tile that will be set
	 */
	public void setTile(int row, int column, Tile tile) {
		tiles.get(row).set(column, tile);
	}

	/**
	 * Checks if the board is solved.
	 *
	 * @return true, if is solved
	 */
	public boolean isSolved() {
		int correctNumber = 1;
		boolean solved = true;
		for (int row = 0; row < 4; row++) {
			for (int column = 0; column < 4; column++) {
				if (tiles.get(row).get(column).getValue() != correctNumber) {
					solved = false;
				}
				correctNumber++;
				if (correctNumber == 16) {
					correctNumber = 0;
				}
			}
		}
		if (solved) {
			return true;
		} else
			return false;
	}
}
