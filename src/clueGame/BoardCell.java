package clueGame;

public abstract class BoardCell {
	// instance variables
	private int row;
	private int col;

	// default constructor
	public BoardCell(){
		
	}
	
	// constructor with cell coordinates
	public BoardCell(int row, int col){
		this.row = row;
		this.col = col;
	}
	
	// check if cell is a walkway cell
	public boolean isWalkway() {
		return false;
	}
	
	// check if cell is a room cell
	public boolean isRoom() {
		return false;
	}
	
	// check if cell is a doorway cell
	public boolean isDoorway() {
		return false;
	}
	
	//getters for debugging
	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public abstract void draw();
}
