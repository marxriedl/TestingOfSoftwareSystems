package minesweeper.model.player;

import minesweeper.model.board.Position;

public interface Player {
	
	/**
	 * calls the player to make a move
	 * @return the position which the player wants to set
	 */
	public Position set();

}
