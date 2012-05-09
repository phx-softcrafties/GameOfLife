
public abstract class CellState {
	public abstract boolean isAlive();

	public static class Alive extends CellState {
		public String toString() { return "o"; }
		public boolean isAlive() { return true; }
		@Override
		public CellState getKilledState() {
			return new Doomed();
		}
		@Override
		public CellState getNextState() {
			return this;
		}
		@Override
		public CellState getSpawnState() {
			return this;
		}
	}
	public static class Dead extends CellState {
		public String toString() { return "."; }
		public boolean isAlive() { return false; }
		@Override
		public CellState getKilledState() {
			return this;
		}
		@Override
		public CellState getNextState() {
			return this;
		}
		@Override
		public CellState getSpawnState() {
			return new Embrionic();
		}
	}
    public static class Doomed extends CellState {
    	public String toString() { return "-"; }
		public boolean isAlive() { return true; }
		@Override
		public CellState getKilledState() {
			return this;
		}
		@Override
		public CellState getNextState() {
			return new Dead();
		}
		@Override
		public CellState getSpawnState() {
			return new Alive();
		}	
    }
    public static class Embrionic extends CellState {
    	public String toString() { return "+"; }
		public boolean isAlive() { return false; }
		@Override
		public CellState getKilledState() {
			return new Dead();
		}
		@Override
		public CellState getNextState() {
			return new Alive();
		}
		@Override
		public CellState getSpawnState() {
			return this;
		}	
    }
	public abstract CellState getKilledState();
	public abstract CellState getNextState();
	public abstract CellState getSpawnState();
}
