package clueGame;

public class RoomCell extends BoardCell {
	private DoorDirection doorDirection;
	private char roomInitial;
	private boolean doorway;
	
	public RoomCell(){
		super();
		this.doorway = false;
	}
	
	public RoomCell(int row, int col, char initial) {
		super(row, col);
		this.roomInitial = initial;
		this.doorway = false;
	}
	
	public RoomCell(int row, int col, char initial, DoorDirection direction) {
		super(row, col);
		this.roomInitial = initial;
		this.doorDirection = direction;
		this.doorway = true;
	}

	public enum DoorDirection {
		UP, DOWN, RIGHT, LEFT
	}
	
	@Override
	public boolean isRoom() {
		return true;
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub

	}
	
	@Override
	public boolean isDoorway() {
		return doorway;
	}

	public DoorDirection getDoorDirection() {
		return doorDirection;
	}

	public char getInitial() {
		return roomInitial;
	}

}
