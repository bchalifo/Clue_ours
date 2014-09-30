package clueGame;

import java.io.FileNotFoundException;
import java.util.Map;

public class ClueGame {
	// instance variables
	private Map<Character, String> rooms;
	private Board board;
	
	public ClueGame() {
		this.board = new Board();
	}

	public ClueGame(String GameFile, String GameLegend) {
		// TODO Auto-generated constructor stub
		this.board = new Board();
	}

	// ?
	public void loadConfigFiles() {
		try {
			this.board.loadBoardConfig();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (BadConfigFormatException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	// ?
	public void loadRoomConfig() {
		
	}

	public Board getBoard() {
		// TODO Auto-generated method stub
		return null;
	}

}
