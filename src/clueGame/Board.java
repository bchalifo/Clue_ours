package clueGame;

import java.util.Map;

public class Board {
	// instance variables
	private BoardCell[][] grid;
	private RoomCell[][] roomGrid;
	private Map<Character, String> rooms;
	private int numRows;
	private int numColumns;
	
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

	public int getNumColumns() {
		return numColumns;
	}
	public BoardCell getCellAt(int row, int col) {
		return grid[row][col];
	}
	
	public RoomCell getRoomCellAt(int row, int col){
		if (grid[row][col].isRoom()) {
			return roomGrid[row][col];
		}
		System.out.println("Error: not a room cell.");
		return null;
	}
}
