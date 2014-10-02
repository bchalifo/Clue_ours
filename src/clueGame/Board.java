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
				legend.close();
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

		// load the board cells using the layout file
		Scanner layout2 = new Scanner(new File(layoutFile));
		int row = 0;
		int col = 0;
		while(layout2.hasNextLine()){
			String line = layout2.nextLine();
			String[] parts = line.split(",");
			if (parts.length != numColumns) {
				layout2.close();
				throw new BadConfigFormatException("Invalid row or column size");
			}
			// Iterated Switchception:
			// determine cell type from its name in the file
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
							// doorway up
							case 'U':
								this.grid[row][col] = new RoomCell(row, col, cell.charAt(0), DoorDirection.UP);
								this.roomGrid[row][col] = new RoomCell(row, col, cell.charAt(0), DoorDirection.UP);
								break;

							// doorway down
							case 'D':
								this.grid[row][col] = new RoomCell(row, col, cell.charAt(0), DoorDirection.DOWN);
								this.roomGrid[row][col] = new RoomCell(row, col, cell.charAt(0), DoorDirection.DOWN);
								break;

							// doorway left
							case 'L':
								this.grid[row][col] = new RoomCell(row, col, cell.charAt(0), DoorDirection.LEFT);
								this.roomGrid[row][col] = new RoomCell(row, col, cell.charAt(0), DoorDirection.LEFT);
								break;

							// doorway right
							case 'R':
								this.grid[row][col] = new RoomCell(row, col, cell.charAt(0), DoorDirection.RIGHT);
								this.roomGrid[row][col] = new RoomCell(row, col, cell.charAt(0), DoorDirection.RIGHT);
								break;

							// don't know what this is but it was breaking our tests
							case 'N':
								this.grid[row][col] = new RoomCell(row, col, cell.charAt(0), DoorDirection.NONE);
								this.roomGrid[row][col] = new RoomCell(row, col, cell.charAt(0), DoorDirection.NONE);
								break;

							// invalid cell name
							default:
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
					if (doorway.getDoorDirection() == DoorDirection.DOWN) {
						adjacencies.add(grid[row + 1][col]);
					}
					// adjacency right
					if (doorway.getDoorDirection() == DoorDirection.RIGHT) {
						adjacencies.add(grid[row][col + 1]);
					}
					// adjacency left
					if (doorway.getDoorDirection() == DoorDirection.LEFT) {
						adjacencies.add(grid[row][col - 1]);
					}
				}
				// if the cell is a walkway, check for other walkways and doorways
				else if (cell.isWalkway()) {
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

	// set up for recursive function 'findAllTargets' which calculates all
	// possible target cells for a given start cell and roll value
	public void calcTargets(int row, int col, int roll){
		// clear data from previous rolls
		visited.clear();
		targets.clear();
		
		// set the starting cell
		BoardCell startCell = grid[row][col];
		visited.add(startCell);
		findAllTargets(startCell, roll);
	}	

	// recursive function for finding all targets for a roll using the
	// visited cells list, the current cell, and the remaining roll value
	public void findAllTargets(BoardCell cell, int roll){
		LinkedList<BoardCell> frontier = new LinkedList<BoardCell>();
		int row = cell.getRow();
		int col = cell.getCol();
		frontier = getAdjList(row, col);

		for (BoardCell adjacent : frontier) {
			if (visited.contains(adjacent)) {
				continue;
			} else {
				visited.add(adjacent);
			}
			if (roll == 1 || adjacent.isDoorway()) {
				targets.add(adjacent);
			} else {
				findAllTargets(adjacent, roll - 1);
			}
			visited.remove(adjacent);
		}
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
		return roomGrid[row][col];
	}

	// returns adjacency list for the cell at the given coordinates
	public LinkedList<BoardCell> getAdjList(int row, int col){
		return adjMtx.get(grid[row][col]);
	}

	// returns list of targets for the given BoardCell
	public Set<BoardCell> getTargets(){
		return this.targets;
	}

	// set the source file for the board layout
	public void setLayoutFile(String layoutFile) {
		this.layoutFile = layoutFile;
	}

	// set the source file for the legend
	public void setLegendFile(String legendFile) {
		this.legendFile = legendFile;
	}
}
