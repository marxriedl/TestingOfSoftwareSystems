package minesweeper.model;

import java.util.HashSet;
import java.util.Set;

import minesweeper.model.board.Field;
import minesweeper.model.board.Position;
import minesweeper.model.player.Player;

/**
 * Implementation of the minesweeper game.
 * It contains most of the game logic and 
 * the prinicipal sequence of actions.
 * @author philipp
 *
 */
public class GameImpl implements Game {
	
	/**
	 * The field of the game
	 */
	private final Field field;
	
	/**
	 * the player which plays the game.
	 */
	private final Player player;
	
	/**
	 * Interested listeners
	 */
	private Set<GameListener> listeners = new HashSet<GameListener>();

	public GameImpl(Field field, Player player) {
		this.player = player;
		this.field = field;
	}
	
	@Override
	public void start() {
		boolean won;
		while(!(won = field.everythingFound())) {
			//ask the player to choose a position
			// until a bomb exploded or all non-bomb fields have been found
			Position position; 
			do {
				position = player.set();
				// ask the player again, if the position is invalid
			} while(!isValidPosition(position) || field.getCell(position).isVisible());
			if(field.makeVisible(position)) {
				// makeVisible == true -> bomb exploded
				fireBombExplodedEvent(position);
				break;
			}
			fireGameChangedEvent(position);
		}
		if(won) {
			fireGameWonEvent();
		}
	}

	/**
	 * checks, whether pos is a valid position within the field.
	 */
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
