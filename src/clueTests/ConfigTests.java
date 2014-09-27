package clueTests;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import clueGame.*;

public class ConfigTests{
	private Board board;
	public static final int ROOMS = 9;
	public static final int DOORS = 18;
	public static final int ROWS = 23;
	public static final int COLS = 23;

	@Before
	public void setUp(){
		ClueGame game = new ClueGame("ClueLayout.csv", "ClueLegend.txt");
		game.loadConfigFiles();
		board = game.getBoard();
	}

	// test for having the correct number of rows
	@Test
	public void testRows(){		
		Assert.assertEquals(ROWS, board.getNumRows());
	}
	
	// test for having the correct number of columns
	@Test
	public void testCols(){
		Assert.assertEquals(COLS, board.getNumCols());
	}
	
	// test for having the correct number of rooms
	@Test
	public void RoomTest(){
		Map<Character, String> test = new HashMap<Character, String>();
		test = board.getRooms();
		Assert.assertEquals(ROOMS, test.size());
	}
	
	// test that room initials are properly mapped to room names
	@Test
	public void RoomValueTest(){
		Map<Character, String> test = new HashMap<Character, String>();
		test = board.getRooms();
		Assert.assertEquals("Hallway", test.get('W'));
		Assert.assertEquals("Kitchen", test.get('K'));
		Assert.assertEquals("Sun Room", test.get('S'));
		Assert.assertEquals("Office", test.get('Q'));
		Assert.assertEquals("Dining Room", test.get('D'));
		Assert.assertEquals("Bathroom", test.get('B'));
		Assert.assertEquals("Reading Room", test.get('R'));
		Assert.assertEquals("Theater", test.get('T'));
		Assert.assertEquals("Garage", test.get('G'));
		Assert.assertEquals("Living Room", test.get('L'));
		Assert.assertEquals("Crawl Space", test.get('C'));
		Assert.assertEquals("Closet", test.get('X'));
	}
	
	// test that doors are in correct location with correct direction
	@Test
	public void testDoors(){
		// Test Kitchen Door
		RoomCell door = new RoomCell();
		door = board.getRoomCell(8, 1);
		Assert.assertTrue(door.isDoorway());
		Assert.assertEquals(RoomCell.DoorDirection.DOWN, door.getDoorDirection());
		// Test Office Door
		door = board.getRoomCell(9, 7);
		Assert.assertTrue(door.isDoorway());
		Assert.assertEquals(RoomCell.DoorDirection.RIGHT, door.getDoorDirection());
		// Test Garage Door
		door = board.getRoomCell(19, 15);
		Assert.assertTrue(door.isDoorway());
		Assert.assertEquals(RoomCell.DoorDirection.UP, door.getDoorDirection());
		// Test Crawl Space Door
		door = board.getRoomCell(1, 21);
		Assert.assertTrue(door.isDoorway());
		Assert.assertEquals(RoomCell.DoorDirection.LEFT, door.getDoorDirection());
		// Test non-door
		BoardCell notDoor = new WalkwayCell();
		notDoor = board.getBoardCell(0, 3);
		Assert.assertFalse(notDoor.isDoorway());
	}

	// test for having the correct number of doors
	@Test
	public void testNumDoors(){
		int numDoors = 0;
		int numCells = board.getNumCols() * board.getNumRows();

		for(int i = 0; i < board.getNumRows(); i++){
			for(int j = 0; j < board.getNumCols(); j++){
				BoardCell cell = board.getBoardCell(i,j);
				if(cell.isDoorway()){
					numDoors++;
				}
			}
		}
		Assert.assertEquals(DOORS, numDoors);
	}
	
	// test that exception is thrown for differing row sizes
	@Test
	public void testBadRows() throws BadConfigFormatException, FileNotFoundException{
		ClueGame badGame = new ClueGame("badRow.csv", "ClueLegend.txt");
		badGame.loadConfigFiles();
		badGame.getBoard();
	}
	
	// test that exception is thrown for differing column sizes
	@Test
	public void testBadCols() throws BadConfigFormatException, FileNotFoundException{
		ClueGame badGame = new ClueGame("badCol.csv", "ClueLegend.txt");
		badGame.loadConfigFiles();
		badGame.getBoard();
	}
	
	// test that exception is thrown for mismatching legend and board
	@Test
	public void testBadLegend() throws BadConfigFormatException, FileNotFoundException{
		ClueGame badGame = new ClueGame("ClueBoard.csv", "badLegend.txt");
		badGame.loadConfigFiles();
		badGame.getBoard();
	}
}