
public class Cell {
private CellState state;

public CellState getState() {
	return state;
}

public void setState(CellState state) {
	this.state = state;
}

public void kill() {
	this.state = state.getKilledState();	
}

public void advance() {
	this.state = state.getNextState();
}

public void spawn() {
	this.state = state.getSpawnState();
}
}
