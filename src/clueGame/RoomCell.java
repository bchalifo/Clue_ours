package clueGame;

public class RoomCell extends BoardCell {
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

}
