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
	private String layoutFile;
	private String legendFile;

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
		// Setting up the 'rooms' map using the legend file		
		Scanner legend = new Scanner(new File(legendFile));
		while(legend.hasNextLine()) {
			String line =  legend.nextLine();
			String[] parts = line.split(", ");
			if (parts.length != 2) {
				throw new BadConfigFormatException("Invalid structure for legend file");
			}
			char key = parts[0].charAt(0);
			this.rooms.put(key, parts[1]);
		}
		legend.close();
		
		Scanner boardIn = new Scanner(new File(layoutFile));
		Scanner boardIn1 = new Scanner(new File(layoutFile));
		
		// set the number of rows and columns
		this.numRows = 0;
		this.numColumns = 0;
		while(boardIn1.hasNextLine()){
			String line = boardIn1.nextLine();
			String[] parts = line.split(",");
			if (parts.length > this.numColumns) {
				this.numColumns = parts.length;
			}
			this.numRows++;
		}
		
		// load the board cells
		int row = 0;
		int col = 0;
		while(boardIn.hasNextLine()){
			String line = boardIn.nextLine();
			String[] parts = line.split(",");
			if (parts.length != numColumns) {
				throw new BadConfigFormatException("Invalid row or column size");
			}
			// Iterated Switchception:
			for(col = 0; col < numColumns; col++){
				String cell = parts[col];
				switch(cell){
				// cell doesn't exist
				case "":
					throw new BadConfigFormatException("Invalid row or column size");
				
				// walkway cell
				case "W":
					this.grid[row][col] = new WalkwayCell(row, col);
					break;
				
				// other
				default:
					// room cell
					if(rooms.containsKey(cell.charAt(0)) && (cell.length() == 1 || cell.length() == 2)){
						// room
						if(cell.length() == 1){
							this.grid[row][col] = new RoomCell(row, col, cell.charAt(0), DoorDirection.NONE);
							this.roomGrid[row][col] = new RoomCell(row, col, cell.charAt(0), DoorDirection.NONE);
						}
						// doorway
						else{
							switch(cell.charAt(1)){
							case 'U':
								this.grid[row][col] = new RoomCell(row, col, cell.charAt(0), DoorDirection.UP);
								this.roomGrid[row][col] = new RoomCell(row, col, cell.charAt(0), DoorDirection.UP);
								break;
							case 'D':
								this.grid[row][col] = new RoomCell(row, col, cell.charAt(0), DoorDirection.DOWN);
								this.roomGrid[row][col] = new RoomCell(row, col, cell.charAt(0), DoorDirection.DOWN);
								break;
							case 'L':
								this.grid[row][col] = new RoomCell(row, col, cell.charAt(0), DoorDirection.LEFT);
								this.roomGrid[row][col] = new RoomCell(row, col, cell.charAt(0), DoorDirection.LEFT);
								break;
							case 'R':
								this.grid[row][col] = new RoomCell(row, col, cell.charAt(0), DoorDirection.RIGHT);
								this.roomGrid[row][col] = new RoomCell(row, col, cell.charAt(0), DoorDirection.RIGHT);
								break;
							case 'N':
								this.grid[row][col] = new RoomCell(row, col, cell.charAt(0), DoorDirection.NONE);
								this.roomGrid[row][col] = new RoomCell(row, col, cell.charAt(0), DoorDirection.NONE);
								break;
							default:
								System.out.println("Bad char: " + cell.charAt(1));
								throw new BadConfigFormatException("Invalid cell name '" + cell + "' at (" + row + "," + col +")");
							}
						}
					}
					// invalid cell name
					else{
						throw new BadConfigFormatException("Invalid cell name '" + cell + "' at (" + row + "," + col +")");
					}
				}
			}
			row++;
		}
		boardIn1.close();
		boardIn.close();
	}

	// set the source file for the board layout
	public void setLayoutFile(String layoutFile) {
		this.layoutFile = layoutFile;
	}

	// set the source file for the legend
	public void setLegendFile(String legendFile) {
		this.legendFile = legendFile;
	}

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
