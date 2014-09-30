package clueGame;

import java.io.FileNotFoundException;
import java.util.Map;

public class ClueGame {
	// instance variables
	private Map<Character, String> rooms;
	private Board board;
	
	// default constructor
	public ClueGame() {
		this.board = new Board();
	}

	// constructor with source files
	public ClueGame(String GameFile, String GameLegend) {
		this.board = new Board();
		this.board.setLayoutFile(GameFile);
		this.board.setLegendFile(GameLegend);
	}

	// attempt to construct a board using the provided source files
	public void loadConfigFiles() {
		try {
			// load board cells and calculate adjacencies
			this.board.loadBoardConfig();
			this.board.calcAdjacencies();
		} catch (FileNotFoundException e) {
			// inform user the source file could not be found
			System.out.println(e.getMessage());
		} catch (BadConfigFormatException e) {
			// inform user the source file had an invalid configuration
			System.out.println(e.getMessage());
		}
	}
	
	// ?
	public void loadRoomConfig() throws FileNotFoundException, BadConfigFormatException {
		
	}

	// returns game board
	public Board getBoard() {
		
		return this.board;
	}

}
