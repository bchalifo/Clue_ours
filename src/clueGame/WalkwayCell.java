package clueGame;

public class WalkwayCell extends BoardCell {
	public WalkwayCell(){
		super();
	}
	
	public WalkwayCell(int row, int col) {
		super(row, col);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isWalkway() {
		return true;
	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub

	}

}
