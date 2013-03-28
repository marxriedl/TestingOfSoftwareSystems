package battleship.model;

import java.util.List;
import java.util.ArrayList;

public class BattleField {

	private boolean[][][] field;// width*height matrix, 3rd dimension for ships
								// & where has been shoot
	private List<BattleFieldListener> listeners = new ArrayList<BattleFieldListener>();
	private int countOfShips = 14;

	public BattleField(int width, int height) {
		field = new boolean[height][width][2];

	}

	public boolean setShip(int x, int y, BattleShip ship, Direction direction) {

		// Out of Bounds
		if ((direction == Direction.DOWN && y + ship.getLength() > field.length)
				|| (direction == Direction.RIGHT && x + ship.getLength() > field[0].length)) {
			return false;
		}

		int startValue = (direction == Direction.DOWN) ? y : x;
		int movVar = (direction == Direction.DOWN) ? y : x;
		int constVar = (direction == Direction.DOWN) ? x : y;

		// Ship in another ship
		for (; movVar < startValue + ship.getLength(); movVar++) {
			if (direction == Direction.DOWN) {
				if (field[movVar][constVar][0]) {
					return false;
				}
			} else {
				if (field[constVar][movVar][0]) {
					return false;
				}
			}
		}

		startValue = (direction == Direction.DOWN) ? y : x;
		movVar = (direction == Direction.DOWN) ? y : x;
		constVar = (direction == Direction.DOWN) ? x : y;

		// place ship
		for (; movVar < startValue + ship.getLength(); movVar++) {
			if (direction == Direction.DOWN) {
				field[movVar][constVar][0] = true;

			} else {
				field[constVar][movVar][0] = true;
			}
		}

		fireBattleEvent();
		return true;
	}

	public boolean shotAtPosition(int x, int y) {
		if (!isShotAtPosition(x, y)) {
			// System.out.println("shot at x: " + x + " y: " + y);
			field[y][x][1] = true;

			if (field[y][x][0]) {
				countOfShips--;
			}
			fireBattleEvent();
			System.out.println(listeners.size());
			return true;

		}

		return false;
	}

	public boolean isShotAtPosition(int x, int y) {
		return field[y][x][1];
	}

	public boolean shipAtPosition(int x, int y) {
		return field[y][x][0];
	}

	public boolean isDead() {
		return countOfShips == 0;
	}

	public void addBattleFieldListener(final BattleFieldListener battleListener) {
		listeners.add(battleListener);
	}

	public void removeBattleFieldListener(
			final BattleFieldListener battleListener) {
		listeners.remove(battleListener);
	}

	private void fireBattleEvent() {
		if(listeners.isEmpty()) return;
		final BattleFieldEvent event = new BattleFieldEvent(this);
		for (BattleFieldListener listener : listeners) {
			listener.update(event);
		}
	}
}