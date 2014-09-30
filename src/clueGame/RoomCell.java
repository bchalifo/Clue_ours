package clueGame;

public class RoomCell extends BoardCell {
	private DoorDirection doorDirection;
	private char roomInitial;
	
	// default constructor
	public RoomCell(){
		super();
	}
	
	// constructor with coordinates, room initial, and door direction
//	public RoomCell(int row, int col, char initial) {
//		super(row, col);
//		this.roomInitial = initial;
//		this.doorway = false;
//	}
	
	public RoomCell(int row, int col, char initial, DoorDirection direction) {
		super(row, col);
		this.roomInitial = initial;
		this.doorDirection = direction;
	}

	// enum for door direction
	public enum DoorDirection {
		UP, DOWN, RIGHT, LEFT, NONE
	}
	
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	
	public char getInitial() {
		return roomInitial;
	}
	
	@Override
	public boolean isRoom() {
		return true;
	}
	
	@Override
	public boolean isDoorway() {
		if (doorDirection == DoorDirection.NONE) {
			return false;
		}
		return true;
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub

	}

}
