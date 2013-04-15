package battleship.modeltest;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import battleship.model.BattleField;
import battleship.model.BattleFieldEvent;
import battleship.model.BattleFieldListener;
import battleship.model.BattleShip;
import battleship.model.Direction;

public class BattleFieldTest {

	private BattleField field;
	private int width = 20;
	private int height = 30;

	@Before
	public void setUp() throws Exception {
		field = new BattleField(width, height);
	}

	@After
	public void tearDown() throws Exception {
		field = null;
	}

	/**
	 * Test if field's width property was set correctly.
	 */
	@Test
	public void correctWidth() {
		assertEquals(width, field.getWidth());
	}

	/**
	 * Test if field's height property was set correctly.
	 */
	@Test
	public void correctHeight() {
		assertEquals(height, field.getHeight());
	}

	/**
	 * Check that the field contains no ships after initialization.
	 */
	@Test
	public void correctShipInitialization() {
		boolean containsShip = false;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				containsShip = containsShip | field.shipAtPosition(x, y);
			}
		}
		assertFalse(containsShip);
	}

	/**
	 * Check that there are no shots on the field after initialization.
	 */
	@Test
	public void correctShotInitialization() {
		boolean isShotAt = false;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				isShotAt = isShotAt | field.isShotAtPosition(x, y);
			}
		}
		assertFalse(isShotAt);
	}

	@Test
	public void atBeginIsNotDead() {
		assertFalse(field.isDead());
	}

	/**
	 * Test for setShip method. Sets multiple ships at different locations and
	 * then confirms whether the ships are at the correct place.
	 */
	@Test
	public void setShipsAtPositionRight() {
		assertTrue(field.setShip(0, 0, BattleShip.BOAT, Direction.RIGHT));
		assertTrue(field.shipAtPosition(0, 0) && field.shipAtPosition(1, 0));

		assertTrue(field.setShip(0, 1, BattleShip.FRIGATE, Direction.RIGHT));
		assertTrue(field.shipAtPosition(0, 1) && field.shipAtPosition(1, 1)
				&& field.shipAtPosition(2, 1));

		assertTrue(field.setShip(0, 2, BattleShip.CRUISER, Direction.RIGHT));
		assertTrue(field.shipAtPosition(0, 2) && field.shipAtPosition(1, 2)
				&& field.shipAtPosition(2, 2) && field.shipAtPosition(3, 2));

		assertTrue(field.setShip(0, 3, BattleShip.BATTLESHIP, Direction.RIGHT));
		assertTrue(field.shipAtPosition(0, 3) && field.shipAtPosition(1, 0)
				&& field.shipAtPosition(2, 3) && field.shipAtPosition(3, 3)
				&& field.shipAtPosition(4, 3));
	}

	/**
	 * Test for setShip method. Sets multiple ships at different locations and
	 * then confirms whether the ships are at the correct place.
	 */
	@Test
	public void setShipsAtPositionDown() {
		assertTrue(field.setShip(0, 0, BattleShip.BOAT, Direction.DOWN));
		assertTrue(field.shipAtPosition(0, 0) && field.shipAtPosition(0, 1));

		assertTrue(field.setShip(1, 0, BattleShip.FRIGATE, Direction.DOWN));
		assertTrue(field.shipAtPosition(1, 0) && field.shipAtPosition(1, 1)
				&& field.shipAtPosition(1, 2));

		assertTrue(field.setShip(2, 0, BattleShip.CRUISER, Direction.DOWN));
		assertTrue(field.shipAtPosition(2, 0) && field.shipAtPosition(2, 1)
				&& field.shipAtPosition(2, 2) && field.shipAtPosition(2, 3));

		assertTrue(field.setShip(3, 0, BattleShip.BATTLESHIP, Direction.DOWN));
		assertTrue(field.shipAtPosition(3, 0) && field.shipAtPosition(3, 1)
				&& field.shipAtPosition(3, 2) && field.shipAtPosition(3, 3)
				&& field.shipAtPosition(3, 4));
	}

	/**
	 * Try to set a boat at the same start index but other direction.
	 */
	@Test
	public void setShipAtOtherShipPosition() {
		assertTrue(field.setShip(0, 0, BattleShip.BOAT, Direction.RIGHT));
		assertFalse(field.setShip(0, 0, BattleShip.BATTLESHIP, Direction.DOWN));
	}

	/**
	 * Try to set ship at already occupied position
	 */
	@Test
	public void setShipAtOtherShipPositionDown() {
		assertTrue(field.setShip(1, 1, BattleShip.BOAT, Direction.DOWN));
		assertFalse(field.setShip(0, 1, BattleShip.BATTLESHIP, Direction.RIGHT));
	}

	/**
	 * Try to set a  ship outside of the field.
	 */
	@Test
	public void setShipOutOfFieldDown() {
		assertFalse(field.setShip(0, 27, BattleShip.BATTLESHIP, Direction.DOWN));
	}
	
	/**
	 * Try to set a  ship outside of the field.
	 */
	@Test
	public void setShipOutOfFieldRight() {
		assertFalse(field
				.setShip(27, 0, BattleShip.BATTLESHIP, Direction.RIGHT));
	}

	@Test
	public void shotAtPosition() {
		assertTrue(field.shotAtPosition(0, 0));
	}

	/**
	 * Test for shooting on one and the same field twice. Second shot must be
	 * illegal.
	 */
	@Test
	public void shotAtPositionTwice() {
		assertTrue(field.shotAtPosition(0, 0));
		assertFalse(field.shotAtPosition(0, 0));
	}

	/**
	 * Tests the end condition. Sets ship on the field, then shoots each field
	 * --> must be end of game.
	 */
	@Test
	public void isDeadAfterGame() {
		assertTrue(field.setShip(0, 0, BattleShip.BOAT, Direction.RIGHT));
		assertTrue(field.setShip(0, 1, BattleShip.FRIGATE, Direction.RIGHT));
		assertTrue(field.setShip(0, 2, BattleShip.CRUISER, Direction.RIGHT));
		assertTrue(field.setShip(0, 3, BattleShip.BATTLESHIP, Direction.RIGHT));
		for (int y = 0; y < 5; y++) {
			for (int x = 0; x < y + 2; x++) {
				assertTrue(field.shotAtPosition(x, y));
			}
		}

		assertTrue(field.isDead());
	}

	/**
	 * Test event mechanism
	 */
	@Test
	public void getFieldEvent() {
		field.addBattleFieldListener(new BattleFieldListener() {

			@Override
			public void update(BattleFieldEvent event) {
				assertEquals(((BattleField) event.getSource()), field);
			}
		});
		field.shotAtPosition(0, 0);
	}

	/**
	 * Test event mechanism
	 */
	@Test
	public void getNoFieldEvent() {
		BattleFieldListener listener = new BattleFieldListener() {

			@Override
			public void update(BattleFieldEvent event) {
				assertFalse(true);
			}
		};
		field.addBattleFieldListener(listener);
		field.removeBattleFieldListener(listener);

		field.shotAtPosition(0, 0);
	}
}