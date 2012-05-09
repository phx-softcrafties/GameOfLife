

public class GameOfLife {
	public static void main(String...args) throws InterruptedException {
		new GameOfLife(5,5);
	}
	
	public GameOfLife(int width, int height) throws InterruptedException {
		Board board = new Board(width, height);
		board.seed();
		playUntilDead(board);
		System.out.println("They're dead, Jim.");
	}

	private void playUntilDead(Board board) throws InterruptedException {
		while(board.atLeastOneLiveCell()) {
			board.printBoard(System.out);
			System.out.println();
			board.tick();
			Thread.sleep(1000);
		}
	}
}
