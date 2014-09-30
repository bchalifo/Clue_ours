package clueGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import clueGame.*;
import clueGame.RoomCell.DoorDirection;

public class Board {
	// constants
	public static final int MAX_ROWS = 30;
	public static final int MAX_COLS = 30;
	// instance variables
	private BoardCell[][] grid;
	private RoomCell[][] roomGrid;
	private Map<Character, String> rooms;
	private Map<BoardCell, LinkedList<BoardCell>> adjMtx;
	private Set<BoardCell> visited;
	private Set<BoardCell> targets;
	private int numRows;
	private int numColumns;
	private String layoutFile;
	private String legendFile;
	
	

	// constructor
	public Board() {
		// initialize containers
		this.grid = new BoardCell[MAX_ROWS][MAX_COLS];
		this.roomGrid = new RoomCell[MAX_ROWS][MAX_COLS];		
		this.rooms = new HashMap<Character, String>();
		this.adjMtx = new HashMap<BoardCell, LinkedList<BoardCell>>();
		this.visited = new HashSet<BoardCell>();
		this.targets = new HashSet<BoardCell>();
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

		// set the number of rows and columns
		Scanner layout1 = new Scanner(new File(layoutFile));
		this.numRows = 0;
		this.numColumns = 0;
		while(layout1.hasNextLine()){
			String line = layout1.nextLine();
			String[] parts = line.split(",");
			if (parts.length > this.numColumns) {
				this.numColumns = parts.length;
			}
			this.numRows++;
		}
		layout1.close();

		// load the board cells
		Scanner layout2 = new Scanner(new File(layoutFile));
		int row = 0;
		int col = 0;
		while(layout2.hasNextLine()){
			String line = layout2.nextLine();
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
								throw new BadConfigFormatException("Invalid cell '" + cell + "' at (" + row + "," + col +")");
							}
						}
					}
					// invalid cell name
					else{
						throw new BadConfigFormatException("Invalid cell '" + cell + "' at (" + row + "," + col +")");
					}
				}
			}
			row++;
		}
		layout2.close();
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

	// calculates the adjacency lists for each grid cell, stores in map
	public void calcAdjacencies(){

		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				// grab a cell from 'grid', create the adjacency list for that
				// cell, then store both in 'adjMtx'
				LinkedList<BoardCell> adjacencies = new LinkedList<BoardCell>();
				int row = i;
				int col = j;
				BoardCell cell = grid[row][col];
				
				// if the cell is a doorway, only add the adjacent cell in the door's direction
				if (grid[row][col].isDoorway()) {
					RoomCell doorway = roomGrid[row][col];
					
					// adjacency up
					if (doorway.getDoorDirection() == DoorDirection.UP) {
						adjacencies.add(grid[row - 1][col]);
					}
					
					// adjacency down
					if (doorway.getDoorDirection() == DoorDirection.UP) {
						adjacencies.add(grid[row + 1][col]);
					}
					
					// adjacency right
					if (doorway.getDoorDirection() == DoorDirection.UP) {
						adjacencies.add(grid[row][col + 1]);
					}
					
					// adjacency left
					if (doorway.getDoorDirection() == DoorDirection.UP) {
						adjacencies.add(grid[row][col - 1]);
					}
				}
				// if the cell is a walkway, check for other walkways and doorways
				else {
					BoardCell adj;

					// adjacency down
					if (row < numRows - 1) {
						adj = grid[row + 1][col];
						if (adj.isWalkway()) {
							adjacencies.add(adj);
						}
						else if (adj.isDoorway()) {
							RoomCell doorAdj = roomGrid[row + 1][col];
							if (doorAdj.getDoorDirection() == DoorDirection.UP) {
								adjacencies.add(adj);
							}
							
						}
					}
					// adjacency up
					if (row > 0) {
						adj = grid[row - 1][col];
						if (adj.isWalkway()) {
							adjacencies.add(adj);
						}
						else if (adj.isDoorway()) {
							RoomCell doorAdj = roomGrid[row - 1][col];
							if (doorAdj.getDoorDirection() == DoorDirection.DOWN) {
								adjacencies.add(adj);
							}
							
						}
					}
					// adjacency right
					if (col < numColumns - 1) {
						adj = grid[row][col + 1];
						if (adj.isWalkway()) {
							adjacencies.add(adj);
						}
						else if (adj.isDoorway()) {
							RoomCell doorAdj = roomGrid[row ][col + 1];
							if (doorAdj.getDoorDirection() == DoorDirection.LEFT) {
								adjacencies.add(adj);
							}
							
						}
					}
					// adjacency left
					if (col > 0) {
						adj = grid[row][col - 1];
						if (adj.isWalkway()) {
							adjacencies.add(adj);
						}
						else if (adj.isDoorway()) {
							RoomCell doorAdj = roomGrid[row ][col - 1];
							if (doorAdj.getDoorDirection() == DoorDirection.RIGHT) {
								adjacencies.add(adj);
							}
							
						}
					}
				}
				adjMtx.put(cell, adjacencies);
			}
		}
	}

	public LinkedList getAdjList(int row, int col){
		
		return adjMtx.get(grid[row][col]);
	}

	public void calcTargets(int row, int col, int roll){

	}

	public Set<BoardCell> getTargets(){
		return this.targets;
	}
}
