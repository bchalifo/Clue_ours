package clueGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

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
			String[] parts = line.split(", ");
			char key = parts[0].charAt(0);
			this.rooms.put(key, parts[1]);
		}
		
		Scanner boardIn = new Scanner(new File("ClueBoard.csv"));
		Scanner boardIn1 = new Scanner(new File("ClueBoard.csv"));
		int row = 0;
		if(boardIn.hasNextLine()){
			String line = boardIn1.nextLine();
			String[] parts = line.split(",");
			numColumns = parts.length;
		}
		while(boardIn.hasNextLine()){
			String line = boardIn.nextLine();
			String[] parts = line.split(",");
			if (parts.length != numColumns){
				throw new BadConfigFormatException("Invalid data size.");
			}
			// Iterated Switchception:
			for(int col = 0; col < numColumns; col++){
				switch(parts[col]){
				case "W":
					this.grid[row][col] = new WalkwayCell(row, col);
					break;
				default:
					if(rooms.containsKey(parts[col].charAt(0))){

						if(parts[col].length() == 1){
							this.grid[row][col] = new RoomCell(row, col, parts[col].charAt(0));
							this.roomGrid[row][col] = new RoomCell(row, col, parts[col].charAt(0));
						}
						else{
							switch(parts[col].charAt(1)){
							case 'U':
								this.grid[row][col] = new RoomCell(row, col, parts[col].charAt(0), DoorDirection.UP);
								this.roomGrid[row][col] = new RoomCell(row, col, parts[col].charAt(0), DoorDirection.UP);
								break;
							case'D':
								this.grid[row][col] = new RoomCell(row, col, parts[col].charAt(0), DoorDirection.DOWN);
								this.roomGrid[row][col] = new RoomCell(row, col, parts[col].charAt(0), DoorDirection.DOWN);
								break;
							case'L':
								this.grid[row][col] = new RoomCell(row, col, parts[col].charAt(0), DoorDirection.LEFT);
								this.roomGrid[row][col] = new RoomCell(row, col, parts[col].charAt(0), DoorDirection.LEFT);
								break;
							case'R':
								this.grid[row][col] = new RoomCell(row, col, parts[col].charAt(0), DoorDirection.RIGHT);
								this.roomGrid[row][col] = new RoomCell(row, col, parts[col].charAt(0), DoorDirection.RIGHT);
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
		this.numRows = row;
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

	public void calcAdjacencies(){
		// DO STUFF HERE
	}
	
	public LinkedList getAdjList(int row, int col){
		// DO STUFF HERE
		return null;
	}
	
	public void calcTargets(int row, int col, int roll){
		
	}
	
	public Set<BoardCell> getTargets(){
		return null;
	}
}
