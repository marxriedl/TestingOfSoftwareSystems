package minesweeper.model;

import java.util.HashSet;
import java.util.Set;

import minesweeper.model.board.Field;
import minesweeper.model.board.Position;
import minesweeper.model.player.Player;

public class GameImpl implements Game {
	
	
	private final Field field;
	private final Player player;
	private Set<GameListener> listeners = new HashSet<GameListener>();

	public GameImpl(Field field, Player player) {
		this.player = player;
		this.field = field;
	}
	
	@Override
	public void start() {
		boolean won;
		while(!(won = field.everythingFound())) {
			Position position; 
			do {
				position = player.set();
			} while(!isValidPosition(position) || field.getCell(position).isVisible());
			if(field.makeVisible(position)) {
				fireBombExplodedEvent(position);
				break;
			}
			fireGameChangedEvent(position);
		}
		if(won) {
			fireGameWonEvent();
		}
	}

	private boolean isValidPosition(Position pos) {
		return pos.getX() >= 0 && pos.getX() < field.getWidth() && pos.getY() >= 0 && pos.getY() < field.getHeight();
	}

	private void fireGameWonEvent() {
		for(GameListener l : listeners) {
			l.gameWon(this);
		}
	}

	private void fireGameChangedEvent(Position position) {
		for(GameListener l : listeners) {
			l.gameChanged(this, position);
		}
	}

	private void fireBombExplodedEvent(Position position) {
		for(GameListener l : listeners ) {
			l.bombExploded(this, position);
		}
	}

	@Override
	public void addListener(GameListener gameListener) {
		listeners.add(gameListener);
	}
	
	@Override
	public void removeListener(GameListener gameListener) {
		listeners.remove(gameListener);
	}

	@Override
	public Field getField() {
		return field;
	}

}
