package clueGame;

import java.util.Map;

public class Board {
	// instance variables
	private BoardCell[][] grid;
	private RoomCell[][] gridDoor;
	private Map<Character, String> rooms;
	private int numRows;
	private int numCols;
	
	// loads the board layout
	public void loadBoardConfig() {
		
	}
	
	// getters
	public BoardCell[][] getGrid() {
		return grid;
	}

	public Map<Character, String> getRooms() {
		return rooms;
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumCols() {
		return numCols;
	}
	public BoardCell getBoardCell(int row, int col) {
		return grid[row][col];
	}
	
	public RoomCell getRoomCell(int row, int col){
		return gridDoor[row][col];
	}
}
