package minesweeper.model;

import minesweeper.model.board.Position;

/**
 * Interface for interested listeners
 * @author philipp
 *
 */
public interface GameListener {

	/**
	 * Notifies the game listener, that the game has been won.
	 * @param game the game which has been won
	 */
	void gameWon(Game game);
	
	/**
	 * Notifies the listener, that the state of the game changed, e.g. a cell has been made visible.
	 * @param game the game which has changed.
	 * @param position the position of the cell which has been changed.
	 */
	void gameChanged(Game game, Position position);

	/**
	 * Notifies the listener, that a bomb exploded.
	 * @param game the game on which the bomb exploded
	 * @param position the position where the bomb exploded
	 */
	void bombExploded(Game game, Position position);

}
