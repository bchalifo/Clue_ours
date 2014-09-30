package clueGame;

public class RoomCell extends BoardCell {
	public RoomCell(){
		super();
	}
	
	public RoomCell(int row, int col, char initial) {
		super(row, col);
		this.roomInitial = initial;
		// TODO Auto-generated constructor stub
	}
	
	public RoomCell(int row, int col, char initial, DoorDirection direction) {
		super(row, col);
		this.roomInitial = initial;
		this.doorDirection = direction;
		// TODO Auto-generated constructor stub
	}

	public enum DoorDirection {
		UP, DOWN, RIGHT, LEFT
	}
	
	private DoorDirection doorDirection;
	private char roomInitial;
	
	@Override
	public boolean isRoom() {
		return true;
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub

	}

	public DoorDirection getDoorDirection() {
		return doorDirection;
	}

	public char getInitial() {
		return roomInitial;
	}

}
