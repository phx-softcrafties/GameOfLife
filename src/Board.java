import java.io.PrintStream;
import java.util.Random;

public class Board {
	private Cell[][] board;
	private final int width;
	private final int height;
	
	public Board(int width, int height) {
		this.width = width;
		this.height = height;
		board = new Cell[width][height];
	}

	void seed() {
		final Random r = new Random();
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				CellState cell = new CellState.Dead();
				if(r.nextBoolean()) {
					cell = new CellState.Alive();
				}
				setCellStateAt(x, y, cell);
			}
		}
	}

	void printBoard(PrintStream out) {
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				CellState cell = getCellStateAt(x, y);
				out.print(cell);
			}
			out.println();
		}
	}

	public void tick() {
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				prognosticateCell(x,y);
			}
		}
		printBoard(System.out);
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				updateCell(x,y);
			}
		}
		printBoard(System.out);
	}
	private void updateCell(int x, int y) {
		// TODO Auto-generated method stub
		board[x][y].advance();
	}

	private void prognosticateCell(int x, int y) {
		CellState cell = getCellStateAt(x, y);
		int n = neighborsAlive(x, y);
		if(cell.isAlive()) {
			if(n < 2 || n > 3) {
				killCellAt(x, y);
			}
		} else {
			if(n == 3) {
				spawnCellAt(x, y);
			}
		}
	}
	
	private void spawnCellAt(int x, int y) {
		board[x][y].spawn();
	}

	private void killCellAt(int x, int y) {
		board[x][y].kill();
	}

	public boolean atLeastOneLiveCell() {
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				CellState cell = getCellStateAt(x, y);
				if(cell.isAlive()) {
					return true;
				}
			}
		}
		return false;
	}

	void setCellStateAt(int x, int y, CellState state) {
		board[x][y].setState(state);
	}

	CellState getCellStateAt(int x, int y) {
		return board[x][y].getState();
	}

	int neighborsAlive(int x, int y) {
		CellState[] neighbors = findNeighbors(x, y);
		int neighborsAlive = 0;
		for (CellState cell : neighbors) {
			if(cell.isAlive()) {
				neighborsAlive++;
			}
		}
		return neighborsAlive;
	}

	private CellState[] findNeighbors(int x, int y) {
		int safeRight = (x+1)%width;
		int safeUp = (y+1)%height;
		int safeLeft = (x+width-1)%width;
		int safeDown = (y+height-1)%height;

		CellState right = getCellStateAt(safeRight, y);
		CellState up = getCellStateAt(x, safeUp);
		CellState left = getCellStateAt(safeLeft, y);
		CellState down = getCellStateAt(x, safeDown);
		CellState right_up = getCellStateAt(safeRight, safeUp);
		CellState right_down = getCellStateAt(safeRight, safeDown);
		CellState left_down = getCellStateAt(safeLeft, safeDown);
		CellState left_up = getCellStateAt(safeLeft, safeUp);
		
		CellState[] neighbors = {right, left, up, down, right_up, right_down, left_down, left_up};
		return neighbors;
	}

	void initialize() {
		CellState cell = new CellState.Dead();
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				board[x][y] = new Cell();
				setCellStateAt(x, y, cell);
			}
		}
	}
}