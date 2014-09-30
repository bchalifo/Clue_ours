package clueGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import clueGame.RoomCell.DoorDirection;

public class Board {
	// instance variables
	private BoardCell[][] grid;
	private RoomCell[][] roomGrid;
	private Map<Character, String> rooms;
	private int numRows;
	private int numColumns;

	public static final int MAX_ROWS = 30;
	public static final int MAX_COLS = 30;

	// constructor
	public Board() {
		this.grid = new BoardCell[MAX_ROWS][MAX_COLS];
		this.roomGrid = new RoomCell[MAX_ROWS][MAX_COLS];		
		this.rooms = new HashMap<Character, String>();
	}

	// loads the board layout
	public void loadBoardConfig() throws FileNotFoundException, BadConfigFormatException {
		// Read legend text file and input values into room map:		
		Scanner legendIn = new Scanner(new File("ClueLegend.txt"));
		while(legendIn.hasNextLine()) {
			String line =  legendIn.nextLine();
			String[] parts = line.split(",");
			char key = parts[0].charAt(0);
			this.rooms.put(key, parts[1]);
		}
		
		Scanner boardIn = new Scanner(new File("ClueBoard.csv"));
		int row = 0;
		while(boardIn.hasNextLine()){
			String line = boardIn.nextLine();
			String[] parts = line.split(",");
			// Iterated Switchception:
			for(int col = 0; col < parts.length; col++){
				switch(parts[col]){
				case "W":
					this.grid[row][col] = new WalkwayCell(row, col);
					break;
				default:
					if(rooms.containsKey(parts[col].charAt(0))){

						if(parts[col].length() == 1){
							this.grid[row][col] = new RoomCell(row, col, parts[col].charAt(0));
						}
						else{
							switch(parts[col].charAt(1)){
							case 'U':
								this.grid[row][col] = new RoomCell(row, col, parts[col].charAt(0), DoorDirection.UP);
								break;
							case'D':
								this.grid[row][col] = new RoomCell(row, col, parts[col].charAt(0), DoorDirection.DOWN);
								break;
							case'L':
								this.grid[row][col] = new RoomCell(row, col, parts[col].charAt(0), DoorDirection.LEFT);
								break;
							case'R':
								this.grid[row][col] = new RoomCell(row, col, parts[col].charAt(0), DoorDirection.RIGHT);
								break;
							}
						}
					}
					else{
						throw new BadConfigFormatException("Not a valid room at (" + row + "," + col +")");
					}
				}
			}
			row++;
		}
	}

	// getters
	// returns 2D array of BoardCells representing all cells on the board
	public BoardCell[][] getGrid() {
		return grid;
	}

	// returns map of room initials to room names
	public Map<Character, String> getRooms() {
		return this.rooms;
	}

	// returns number of rows
	public int getNumRows() {
		return numRows;
	}

	// returns number of columns
	public int getNumColumns() {
		return numColumns;
	}

	// returns BoardCell at the given coordinates
	public BoardCell getCellAt(int row, int col) {
		return grid[row][col];
	}

	// returns RoomCell at the given coordinates
	public RoomCell getRoomCellAt(int row, int col){
		if (grid[row][col].isRoom()) {
			return roomGrid[row][col];
		}
		System.out.println("Error: not a room cell.");
		return null;
	}

//	public static void main(String[] args){
//		Board b = new Board();
//		try {
//			b.loadBoardConfig();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			System.out.println(e.getMessage());
//			e.printStackTrace();
//		} catch (BadConfigFormatException e) {
//			// TODO Auto-generated catch block
//			System.out.println(e.getMessage());
//			e.printStackTrace();
//		}
//	}
}
