package clueGame;

public class RoomCell extends BoardCell {
	// instance variables
	private DoorDirection doorDirection;
	private char roomInitial;
	
	// default constructor
	public RoomCell(){
		super();
	}
	
	// constructor with coordinates, initial, and door direction
	public RoomCell(int row, int col, char initial, DoorDirection direction) {
		super(row, col);
		this.roomInitial = initial;
		this.doorDirection = direction;
	}

	// enum for door direction
	public enum DoorDirection {
		UP, DOWN, RIGHT, LEFT, NONE
	}
	
	// gets door direction
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	
	// gets character initial for room
	public char getInitial() {
		return roomInitial;
	}
	
	// room cell is always a room
	@Override
	public boolean isRoom() {
		return true;
	}
	
	// check whether room cell is a doorway
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
