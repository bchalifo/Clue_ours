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
	public static final int ROOMS = 11;
	public static final int DOORS = 14;
	public static final int ROWS = 23;
	public static final int COLS = 23;

	@Before
	public void setUp(){
		ClueGame game = new ClueGame("ClueBoard.csv", "ClueLegend.txt");
		game.loadConfigFiles();
		board = game.getBoard();
		System.out.println("***********************************NEW TEST***************************************");
	}

	// test for having the correct number of rows
	@Test
	public void testRows(){		
		Assert.assertEquals(ROWS, board.getNumRows());
	}
	
	// test for having the correct number of columns
	@Test
	public void testCols(){
		Assert.assertEquals(COLS, board.getNumColumns());
	}
	
	// test for having the correct number of rooms
	@Test
	public void RoomTest(){
		Assert.assertEquals(ROOMS, board.getRooms().size());
	}
	
	// test that room initials are properly mapped to room names
	@Test
	public void RoomValueTest(){
		Map<Character, String> test = new HashMap<Character, String>();
		test = board.getRooms();
		Assert.assertEquals("Hallway", test.get('W'));
		Assert.assertEquals("Kitchen", test.get('K'));
		Assert.assertEquals("Study", test.get('S'));
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
		door = board.getRoomCellAt(4, 10);
		Assert.assertTrue(door.isDoorway());
		Assert.assertEquals(RoomCell.DoorDirection.DOWN, door.getDoorDirection());
		// Test Office Door
		door = board.getRoomCellAt(2, 4);
		Assert.assertTrue(door.isDoorway());
		Assert.assertEquals(RoomCell.DoorDirection.RIGHT, door.getDoorDirection());
		// Test Garage Door
		door = board.getRoomCellAt(18, 11);
		Assert.assertTrue(door.isDoorway());
		Assert.assertEquals(RoomCell.DoorDirection.UP, door.getDoorDirection());
		// Test Crawl Space Door
		door = board.getRoomCellAt(5, 20);
		Assert.assertTrue(door.isDoorway());
		Assert.assertEquals(RoomCell.DoorDirection.LEFT, door.getDoorDirection());
		// Test non-door
		BoardCell notDoor = new WalkwayCell();
		notDoor = board.getCellAt(0, 3);
		Assert.assertFalse(notDoor.isDoorway());
	}

	// test for having the correct number of doors
	@Test
	public void testNumDoors(){
		int numDoors = 0;
		int numCells = board.getNumColumns() * board.getNumRows();

		for(int i = 0; i < board.getNumRows(); i++){
			for(int j = 0; j < board.getNumColumns(); j++){
				BoardCell cell = board.getCellAt(i,j);
				if(cell.isDoorway()){
					numDoors++;
				}
			}
		}
		Assert.assertEquals(DOORS, numDoors);
	}
	
	// test that exception is thrown for differing row sizes
	@Test (expected = BadConfigFormatException.class)
	public void testBadRows() throws BadConfigFormatException, FileNotFoundException{
		ClueGame badGame = new ClueGame("badRows.csv", "ClueLegend.txt");
		badGame.loadConfigFiles();
		board = badGame.getBoard();
		board.loadBoardConfig();
	}
	
	// test that exception is thrown for differing column sizes
	@Test (expected = BadConfigFormatException.class)
	public void testBadCols() throws BadConfigFormatException, FileNotFoundException{
		ClueGame badGame = new ClueGame("badColumns.csv", "ClueLegend.txt");
		badGame.loadConfigFiles();
		board = badGame.getBoard();
		board.loadBoardConfig();
	}
	
	// test that exception is thrown for mismatching legend and board
	@Test (expected = BadConfigFormatException.class)
	public void testBadLegend() throws BadConfigFormatException, FileNotFoundException{
		ClueGame badGame = new ClueGame("ClueBoard.csv", "badLegend.txt");
		badGame.loadConfigFiles();
		board = badGame.getBoard();
		board.loadBoardConfig();
	}
}