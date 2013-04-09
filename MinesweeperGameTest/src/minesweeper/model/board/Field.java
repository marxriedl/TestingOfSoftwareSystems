package minesweeper.model.board;

import java.util.Set;

public interface Field {

	public abstract Set<Cell> getNeighbours(Cell cell);

	public abstract Cell getCell(Position pos);

	public abstract boolean makeVisible(Position pos);

	public abstract boolean everythingFound();

	public abstract int getHeight();

	public abstract int getWidth();

	public abstract Cell getCell(int x, int y);

	public abstract int getnBombs();

}