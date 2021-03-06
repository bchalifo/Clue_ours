package experiment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class IntBoard {
	// constants
	public final static int ROWS = 4;
	public final static int COLS = 4;
	// instance variables
	private BoardCell[][] grid;
	private Map<BoardCell, LinkedList<BoardCell>> adjMtx;
	private Set<BoardCell> visited;
	private HashSet<BoardCell> targets;
	
	// default constructor
	public IntBoard(){
		adjMtx = new HashMap<BoardCell, LinkedList<BoardCell>>();
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		grid = new BoardCell[ROWS][COLS];
		
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				grid[i][j] = new BoardCell(i, j);
			}
		}
		
		this.calcAdjacencies();
	}
	
	// returns board cell from the grid data structure
	public BoardCell getCell(int row, int col){
		if (row < 0 || row >= ROWS || col < 0 || col >= COLS) {
			System.out.println("Error: Out of Bounds");
		}
		return grid[row][col];
	}
	
	// calculates the adjacency lists for each grid cell, stores in map
	public void calcAdjacencies(){
		
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLS; j++) {
				// grab a cell from 'grid', create the adjacency list for that
				// cell, then store both in 'adjMtx'
				LinkedList<BoardCell> adjacencies = new LinkedList<BoardCell>();
				int row = i;
				int col = j;
				BoardCell cell = grid[row][col];
				
				// adjacency down
				if (row < ROWS - 1) {
					adjacencies.add(grid[row + 1][col]);
				}
				// adjacency up
				if (row > 0) {
					adjacencies.add(grid[row - 1][col]);
				}
				// adjacency right
				if (col < COLS - 1) {
					adjacencies.add(grid[row][col + 1]);	
				}
				// adjacency left
				if (col > 0) {
					adjacencies.add(grid[row][col - 1]);				
				}
				adjMtx.put(cell, adjacencies);
			}
		}
	}
	
	// set up for recursive function 'findAllTargets' which calculates all
	// possible target cells for a given start cell and roll value
	public void calcTargets(BoardCell startCell, int roll){
		visited.clear();
		targets.clear();
		
		visited.add(startCell);
		
		findAllTargets(startCell, roll);
	}	
	
	// recursive function for finding all targets for a roll using the
	// visited cells list, the current cell, and the remaining roll value
	public void findAllTargets(BoardCell cell, int roll){
		LinkedList<BoardCell> frontier = new LinkedList<BoardCell>();
		frontier = getAdjList(cell);

		for (BoardCell adjacent : frontier) {
			if (visited.contains(adjacent)) {
				continue;
			} else {
				visited.add(adjacent);
			}
			if (roll == 1) {
				targets.add(adjacent);
			} else {
				findAllTargets(adjacent, roll - 1);
			}
			visited.remove(adjacent);
		}
	}

	// returns the list of targets
	public HashSet<BoardCell> getTargets(){
		return targets;
	}

	// returns the adjacency list for a single grid cell
	public LinkedList<BoardCell> getAdjList(BoardCell cell){
		int row = cell.getRow();
		int col = cell.getCol();

		return adjMtx.get(grid[row][col]);
	}
}
