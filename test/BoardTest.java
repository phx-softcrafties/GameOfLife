import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class BoardTest {
	final static int WIDTH = 3;
	final static int HEIGHT = 3;
	private Board b;

	@Before
	public void setUp() {
		b = new Board(WIDTH, HEIGHT);
		b.initialize();
	}
	
	@Test
	public void testInitialize() {
		for(int x = 0; x < WIDTH; x++) {
			for(int y = 0; y < HEIGHT; y++) {
				CellState cell = b.getCellStateAt(x, y);
				assertFalse(cell.isAlive());
			}
		}
	}

	@Test
	public void testPlaceALiveCell() {
		CellState cell = new CellState.Alive();
		b.setCellStateAt(0, 0, cell);
		cell = b.getCellStateAt(0, 0);
		assertTrue(cell.isAlive());
	}

	@Test
	public void testNeighborsAliveDiag() {
		CellState cell = new CellState.Alive();
		b.setCellStateAt(0, 0, cell);
		b.setCellStateAt(1, 1, cell);
		b.setCellStateAt(2, 2, cell);
		int n = b.neighborsAlive(1, 1);
		assertEquals(2, n);
	}

	@Test
	public void testTwoWithOneNeighborEach() {
		CellState cell = new CellState.Alive();
		b.setCellStateAt(1, 0, cell);
		b.setCellStateAt(2, 1, cell);
		b.tick();
		cell = b.getCellStateAt(1, 0);
		assertFalse(cell.isAlive());
		cell = b.getCellStateAt(2, 1);
		assertFalse(cell.isAlive());
	}
	
	@Test
	public void testCellLivesWithThreeNeighbors() {
		CellState cell = new CellState.Alive();
		b.setCellStateAt(0, 2, cell);
		b.setCellStateAt(1, 0, cell);
		b.setCellStateAt(1, 1, cell);
		b.setCellStateAt(2, 1, cell);
		b.tick();
		cell = b.getCellStateAt(1, 1);
		assertTrue(cell.isAlive());
	}
	
	@Test
	public void testCellDiesWithMoreThanThreeNeighbors() {
		CellState cell = new CellState.Alive();
		b.setCellStateAt(0, 2, cell);
		b.setCellStateAt(1, 0, cell);
		b.setCellStateAt(0, 1, cell);
		b.setCellStateAt(1, 1, cell);
		b.setCellStateAt(2, 1, cell);
		b.tick();
		cell = b.getCellStateAt(1, 1);
		b.printBoard(System.out);
		assertFalse(cell.isAlive());
	}
	
	@Test
	public void testCellIsBornWithThreeNeighbors() {
		CellState alive = new CellState.Alive();
		b.setCellStateAt(0, 2, alive);
		b.setCellStateAt(0, 1, alive);
		b.setCellStateAt(2, 1, alive);
		b.tick();
		CellState state = b.getCellStateAt(1, 1);
		b.printBoard(System.out);
		assertTrue(state.isAlive());
	}
}
