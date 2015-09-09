package games.nTile;

import java.io.Serializable;
import java.util.Random;

/**
 * Represent playing fields consist of cells.
 */
public class PlayingField implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Playing field - cells.
	 */
	private final Cell[][] playingField;

	/**
	 * Rows count. Rows are indexed from 0 to (columnCount - 1).
	 */
	private final int rowCount;

	/**
	 * Column count. Columns are indexed from 0 to (columnCount - 1).
	 */
	private final int columnCount;

	/**
	 * Number Of cell. Number of all cell in playing filed.
	 */
	private final int numberOfCell;

	/** Empty position */
	public EmptyPosition emptyPos = new EmptyPosition();

	/**
	 * Constructor.
	 *
	 * @param rowCount
	 *            row count
	 * @param columnCount
	 *            column count
	 */
	public PlayingField(int rowCount, int columnCount) {
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		numberOfCell = rowCount * columnCount;
		playingField = new Cell[rowCount][columnCount];

		// field generate
		generate();
	}

	/**
	 * Generates playing field with random generator.
	 */
	private void generate() {
		Random cellValue = new Random();
		int ranValue;
		for (int r = 0; r < rowCount; r++) {
			for (int c = 0; c < columnCount; c++) {
				if (getCell(r, c) == null) {
					do {
						ranValue = cellValue.nextInt(numberOfCell);
						if (!ifExistValue(ranValue)) {
							playingField[r][c] = new Cell(ranValue);

							if (ranValue == 0) {
								emptyPos.setX(r);
								emptyPos.setY(c);
							}
						}
					} while (!(getCell(r, c) instanceof Cell));
				}
			}
		}
	}

	/**
	 * Returns true if value exist in playing field otherwise false.
	 *
	 * @return true if value exist in playing field
	 */
	private boolean ifExistValue(int value) {
		for (int r = 0; r < rowCount; r++) {
			for (int c = 0; c < columnCount; c++) {
				if (getCell(r, c) instanceof Cell && (getCell(r, c).getValue() == value)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Returns true if game is solved, false otherwise.
	 *
	 * @return true if game is solved, false otherwise.
	 */
	public boolean isSolved() {
		int check = 1;

		if (getCell(rowCount - 1, columnCount - 1).getValue() != 0)
			return false;
		else {
			for (int r = 0; r < rowCount; r++) {
				for (int c = 0; c < columnCount; c++) {
					// check sort cells ascending
					if (r != rowCount && c != columnCount) {
						if (getCell(r, c).getValue() == check) {
							check++;
						}
					}
				}
			}
		}
		System.out.println(check == numberOfCell);
		return check == numberOfCell;
	}

	/**
	 * Move with empty cell in specified direction.
	 */
	public void Move(int rowClick, int columnClick) {
		// get position of empty cell
		int rowEmpty = emptyPos.getX();
		int colEmpty = emptyPos.getY();
		
		int r = rowClick - rowEmpty;
		int c = columnClick - colEmpty; 
		
		System.out.println("Riadok:" + r + " Stlpec:" + c);
		
		if (colEmpty > 0 && colEmpty < columnCount || rowEmpty >= 0 && rowEmpty < rowCount) {
			playingField[rowEmpty][colEmpty] = playingField[rowEmpty + r][colEmpty + c];
			playingField[rowEmpty + r][colEmpty + c] = new Cell(0);
			emptyPos.setX(rowEmpty + r);
			emptyPos.setY(colEmpty + c);
		}
	}

	/**
	 * Returns cell with specified rows and columns, which are indexed from 0.
	 *
	 * @param row
	 *            row number.
	 * @param column
	 *            column number.
	 * @return tiles tile consist of row and column.
	 */
	public Cell getCell(int row, int column) {
		return playingField[row][column];
	}

	public int getRowCount() {
		return rowCount;
	}

	public int getColumnCount() {
		return columnCount;
	}
}
