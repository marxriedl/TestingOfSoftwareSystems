package minesweeper.model.board;

import java.util.Set;

/**
 * This class models the field of the game.
 * It basically consists of a matrix of {@link Cell}s. 
 * @author philipp
 *
 */
public interface Field {

	public abstract Set<Cell> getNeighbours(Cell cell);

	public abstract Cell getCell(Position pos);

	/**
	 * Makes the cell with position pos visible.
	 * @return <code>true</code> if the cell contained a bomb.
	 */
	public abstract boolean makeVisible(Position pos);

	/**
	 * @return <code>true</code> when every non-bomb cell has been made visible
	 */
	public abstract boolean everythingFound();

	public abstract int getHeight();

	public abstract int getWidth();

	public abstract Cell getCell(int x, int y);

	/**
	 * @return the number of bombs within the field
	 */
	public abstract int getnBombs();

}