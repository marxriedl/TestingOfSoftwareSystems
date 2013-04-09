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
	private static final Position TEST_POSITION = new Position(TEST_POS_X, TEST_POS_Y);
	private Cell testCell;
	private Field fieldMock;
	private Player playerMock;
	private Game game;
	private GameListener listener;

	@Before
	public void setUp() {
		fieldMock = createMock(Field.class);
		playerMock = createMock(Player.class);
		game = new GameImpl(fieldMock, playerMock);
		listener = createMock(GameListener.class);
		game.addListener(listener);
		testCell = new Cell(TEST_POSITION);
	}

	@Test
	public void testGetField() {
		replay(fieldMock, playerMock, listener);
		
		assertEquals(fieldMock, game.getField());
		
		verify(fieldMock, playerMock, listener);
	}
	
	@Test
	public void testWin() {
		expect(fieldMock.everythingFound()).andReturn(true);
		listener.gameWon(game);
		
		replay(fieldMock, playerMock, listener);
		
		game.start();
		
		verify(fieldMock, playerMock, listener);
	}
	
	@Test
	public void testRemoveListener() {
		game.removeListener(listener);
		
		expect(fieldMock.everythingFound()).andReturn(true);
		
		replay(fieldMock, playerMock, listener);
		
		game.start();
		
		verify(fieldMock, playerMock, listener);
	}
	
	@Test
	public void testNormalSet() {
		expect(fieldMock.everythingFound()).andReturn(false);
		recordNormalSet();
		
		replay(fieldMock, playerMock, listener);
		
		game.start();
		
		verify(fieldMock, playerMock, listener);
	}
	
	@Test
	public void testSetOnBomb() {
		expect(fieldMock.everythingFound()).andReturn(false);
		expect(playerMock.set()).andReturn(TEST_POSITION);
		expect(fieldMock.getWidth()).andReturn(TEST_POS_X+1);
		expect(fieldMock.getHeight()).andReturn(TEST_POS_Y+1);
		expect(fieldMock.getCell(TEST_POSITION)).andReturn(testCell);
		
		expect(fieldMock.makeVisible(TEST_POSITION)).andReturn(true);
		listener.bombExploded(game, TEST_POSITION);
		
		replay(fieldMock, playerMock, listener);
		
		game.start();
		
		verify(fieldMock, playerMock, listener);
	}
	
	@Test
	public void testTrySetAlreadyVisibleCell() {
		Cell visibleCell = new Cell(TEST_POSITION);
		visibleCell.makeVisible();
		
		expect(fieldMock.everythingFound()).andReturn(false);
		expect(playerMock.set()).andReturn(TEST_POSITION);
		expect(fieldMock.getWidth()).andReturn(TEST_POS_X+1);
		expect(fieldMock.getHeight()).andReturn(TEST_POS_Y+1);
		expect(fieldMock.getCell(TEST_POSITION)).andReturn(visibleCell);
		
		recordNormalSet();
		
		replay(fieldMock, playerMock, listener);
		
		game.start();
		
		verify(fieldMock, playerMock, listener);
	}
	
	@Test
	public void testNegativeX() {
		expect(fieldMock.everythingFound()).andReturn(false);
		expect(playerMock.set()).andReturn(new Position(-1, TEST_POS_Y));
		
		recordNormalSet();
		
		replay(fieldMock, playerMock, listener);
		
		game.start();
		
		verify(fieldMock, playerMock, listener);
	}
	
	@Test
	public void testNegativeY() {
		expect(fieldMock.everythingFound()).andReturn(false);
		expect(playerMock.set()).andReturn(new Position(TEST_POS_X, -1));
		expect(fieldMock.getWidth()).andReturn(TEST_POS_X+1);
		
		recordNormalSet();
		
		replay(fieldMock, playerMock, listener);
		
		game.start();
		
		verify(fieldMock, playerMock, listener);
	}
	
	@Test
	public void testTooHighX() {
		expect(fieldMock.everythingFound()).andReturn(false);
		expect(playerMock.set()).andReturn(TEST_POSITION);
		expect(fieldMock.getWidth()).andReturn(TEST_POS_X);
		
		recordNormalSet();
		
		replay(fieldMock, playerMock, listener);
		
		game.start();
		
		verify(fieldMock, playerMock, listener);
	}
	
	@Test
	public void testTooHighY() {
		expect(fieldMock.everythingFound()).andReturn(false);
		expect(playerMock.set()).andReturn(TEST_POSITION);
		expect(fieldMock.getWidth()).andReturn(TEST_POS_X+1);
		expect(fieldMock.getHeight()).andReturn(TEST_POS_Y);
		
		recordNormalSet();
		
		replay(fieldMock, playerMock, listener);
		
		game.start();
		
		verify(fieldMock, playerMock, listener);
	}

	private void recordNormalSet() {
		expect(playerMock.set()).andReturn(TEST_POSITION);
		expect(fieldMock.getWidth()).andReturn(TEST_POS_X+1);
		expect(fieldMock.getHeight()).andReturn(TEST_POS_Y+1);
		expect(fieldMock.getCell(TEST_POSITION)).andReturn(testCell);
		
		expect(fieldMock.makeVisible(TEST_POSITION)).andReturn(false);
		listener.gameChanged(game, TEST_POSITION);
		expect(fieldMock.everythingFound()).andReturn(true);
		listener.gameWon(game);
	}
	
}
