package minesweeper.model;

import minesweeper.model.board.Position;

public interface GameListener {

	void gameWon(Game game);

	void gameChanged(Game game, Position position);

	void bombExploded(Game game, Position position);

}
