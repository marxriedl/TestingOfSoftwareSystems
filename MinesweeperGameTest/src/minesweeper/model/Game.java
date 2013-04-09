package minesweeper.model;

import minesweeper.model.board.Field;

public interface Game {

	public abstract void start();

	public abstract void addListener(GameListener gameListener);

	public abstract void removeListener(GameListener gameListener);

	public abstract Field getField();

}