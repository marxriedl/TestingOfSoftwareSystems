package minesweeper.model.player;

import minesweeper.model.board.Position;

public interface Player {
	
	/**
	 * calls the player to make a move
	 */
	public Position set();

}
