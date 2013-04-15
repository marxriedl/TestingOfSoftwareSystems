package minesweeper.model;

import minesweeper.model.board.Field;

/**
 * interface for the minesweeper game.
 * @author philipp
 *
 */
public interface Game {

	/**
	 * Starts the game. <br>
	 * Exits when the game is either one or lost.
	 */
	public abstract void start();

	/**
	 * adds a listener, which will be notified if anything in the game changes
	 * @param gameListener the listener to subscribe
	 */
	public abstract void addListener(GameListener gameListener);

	/**
	 * removes a listener from the subscribed listeners
	 * @param gameListener the listener to unsubscribe
	 */
	public abstract void removeListener(GameListener gameListener);

	/**
	 * Returns the game field of the game.
	 * @return the game field
	 */
	public abstract Field getField();

}