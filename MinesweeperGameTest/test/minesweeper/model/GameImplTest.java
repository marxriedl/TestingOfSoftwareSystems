package minesweeper.model;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import minesweeper.model.board.Cell;
import minesweeper.model.board.Field;
import minesweeper.model.board.Position;
import minesweeper.model.player.Player;

import org.junit.Before;
import org.junit.Test;

public class GameImplTest {

	private static final int TEST_POS_Y = 0;
	private static final int TEST_POS_X = 0;
	private static final Position TEST_POSITION = new Position(TEST_POS_X,
			TEST_POS_Y);
	private Cell testCell;
	private Field fieldMock;
	private Player playerMock;
	private Game game;
	private GameListener listenerMock;

	@Before
	public void setUp() {
		fieldMock = createMock(Field.class);
		playerMock = createMock(Player.class);
		game = new GameImpl(fieldMock, playerMock);
		listenerMock = createMock(GameListener.class);
		game.addListener(listenerMock);
		testCell = new Cell(TEST_POSITION);
	}

	@Test
	public void testGetField() {
		replay(fieldMock, playerMock, listenerMock);

		assertEquals(fieldMock, game.getField());

		verify(fieldMock, playerMock, listenerMock);
	}

	/**
	 * Test the win condition.
	 */
	@Test
	public void testWin() {
		// setup mocks: expected method calls
		expect(fieldMock.everythingFound()).andReturn(true);
		listenerMock.gameWon(game);

		// set mocks to replay mode
		replay(fieldMock, playerMock, listenerMock);

		// game.start should call everythingFound and then throws gameWon event
		game.start();

		// verify mocks
		verify(fieldMock, playerMock, listenerMock);
	}

	/**
	 * Check that listeners get removed after winning the game.
	 */
	@Test
	public void testRemoveListener() {
		game.removeListener(listenerMock);
		expect(fieldMock.everythingFound()).andReturn(true);

		replay(fieldMock, playerMock, listenerMock);

		game.start();

		verify(fieldMock, playerMock, listenerMock);
	}

	/**
	 * Test a normal game.
	 */
	@Test
	public void testNormalSet() {
		expect(fieldMock.everythingFound()).andReturn(false);
		recordNormalSet(); // mock setup for normal set

		replay(fieldMock, playerMock, listenerMock);

		game.start();

		verify(fieldMock, playerMock, listenerMock);
	}

	/**
	 * Test case where bomb explodes.
	 */
	@Test
	public void testSetOnBomb() {
		//setup mocks
		expect(fieldMock.everythingFound()).andReturn(false);
		expect(playerMock.set()).andReturn(TEST_POSITION);
		expect(fieldMock.getWidth()).andReturn(TEST_POS_X + 1);
		expect(fieldMock.getHeight()).andReturn(TEST_POS_Y + 1);
		expect(fieldMock.getCell(TEST_POSITION)).andReturn(testCell);
		expect(fieldMock.makeVisible(TEST_POSITION)).andReturn(true);
		listenerMock.bombExploded(game, TEST_POSITION);

		replay(fieldMock, playerMock, listenerMock);

		game.start();

		verify(fieldMock, playerMock, listenerMock);
	}

	/**
	 * Try to set an already uncovered cell.
	 */
	@Test
	public void testTrySetAlreadyVisibleCell() {
		Cell visibleCell = new Cell(TEST_POSITION);
		visibleCell.makeVisible();

		expect(fieldMock.everythingFound()).andReturn(false);
		expect(playerMock.set()).andReturn(TEST_POSITION);
		expect(fieldMock.getWidth()).andReturn(TEST_POS_X + 1);
		expect(fieldMock.getHeight()).andReturn(TEST_POS_Y + 1);
		//make field return the visible cell defined above
		expect(fieldMock.getCell(TEST_POSITION)).andReturn(visibleCell);

		recordNormalSet();

		replay(fieldMock, playerMock, listenerMock);

		game.start();

		verify(fieldMock, playerMock, listenerMock);
	}

	@Test
	public void testNegativeX() {
		expect(fieldMock.everythingFound()).andReturn(false);
		expect(playerMock.set()).andReturn(new Position(-1, TEST_POS_Y));

		recordNormalSet();

		replay(fieldMock, playerMock, listenerMock);

		game.start();

		verify(fieldMock, playerMock, listenerMock);
	}

	@Test
	public void testNegativeY() {
		expect(fieldMock.everythingFound()).andReturn(false);
		expect(playerMock.set()).andReturn(new Position(TEST_POS_X, -1));
		expect(fieldMock.getWidth()).andReturn(TEST_POS_X + 1);

		recordNormalSet();

		replay(fieldMock, playerMock, listenerMock);

		game.start();

		verify(fieldMock, playerMock, listenerMock);
	}

	@Test
	public void testTooHighX() {
		expect(fieldMock.everythingFound()).andReturn(false);
		expect(playerMock.set()).andReturn(TEST_POSITION);
		expect(fieldMock.getWidth()).andReturn(TEST_POS_X);

		recordNormalSet();

		replay(fieldMock, playerMock, listenerMock);

		game.start();

		verify(fieldMock, playerMock, listenerMock);
	}

	@Test
	public void testTooHighY() {
		expect(fieldMock.everythingFound()).andReturn(false);
		expect(playerMock.set()).andReturn(TEST_POSITION);
		expect(fieldMock.getWidth()).andReturn(TEST_POS_X + 1);
		expect(fieldMock.getHeight()).andReturn(TEST_POS_Y);

		recordNormalSet();

		replay(fieldMock, playerMock, listenerMock);

		game.start();

		verify(fieldMock, playerMock, listenerMock);
	}

	/**
	 * Setup mocks with expected calls.
	 */
	private void recordNormalSet() {
		expect(playerMock.set()).andReturn(TEST_POSITION);
		expect(fieldMock.getWidth()).andReturn(TEST_POS_X + 1);
		expect(fieldMock.getHeight()).andReturn(TEST_POS_Y + 1);
		expect(fieldMock.getCell(TEST_POSITION)).andReturn(testCell);

		expect(fieldMock.makeVisible(TEST_POSITION)).andReturn(false);
		listenerMock.gameChanged(game, TEST_POSITION);
		expect(fieldMock.everythingFound()).andReturn(true);
		listenerMock.gameWon(game);
	}

}
