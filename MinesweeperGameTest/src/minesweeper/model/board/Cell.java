package minesweeper.model.board;

/**
 * This cell class models one of the cells of the game field.
 * It can either contain a bomb or not.
 * Visible cells have been already set.
 * @author philipp
 *
 */
public class Cell {

	private static final int BOMB = -1;
	private final Position position;
	private int value;
	private boolean visible; 

	public Cell(int i, int j) {
		this(new Position(i,j));
	}

	public Cell(Position position) {
		this.position = position;		
	}

	public Position getPosition() {
		return position;
	}

	protected void setValue(int value) {
		this.value = value;
	}
	
	protected void setBomb() {
		this.value = BOMB;
	}
	
	protected boolean isBomb() {
		return value == BOMB;
	}
	
	public boolean isVisibleBomb() {
		return visible && value == BOMB;
	}
	

	protected int getValue() {
		return value;
	}
	
	public int getVisibleValue() {
		return visible ? value : 0;
	}
	
	public void makeVisible() {
		visible = true;
	}

	public boolean isVisible() {
		return visible;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((position == null) ? 0 : position.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cell other = (Cell) obj;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "(" + position.getX() + "," + position.getY() + "):" + getValue();
	}
	
}
