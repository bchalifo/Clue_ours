package clueTests;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import clueGame.*;

public class PathTests{
	private static Board board;

	@Before
	public void setUp(){
		ClueGame game = new ClueGame("ClueFiles/ClueBoard.csv", "ClueFiles/ClueLegend.txt");
		game.loadConfigFiles();
		board = game.getBoard();
//		board.calcAdjacencies();
	}

	// Dark orange on map
	@Test
	public void testAdjInRooms(){
		// Test corner
		LinkedList<BoardCell> test = board.getAdjList(0, 0);
		Assert.assertEquals(0, test.size());
		//Test bottom walkway
		test = board.getAdjList(8, 16);
		Assert.assertEquals(0, test.size());
		//Test top walkway
		test = board.getAdjList(20, 20);
		Assert.assertEquals(0, test.size());
		//Test left walkway
		test = board.getAdjList(12, 17);
		Assert.assertEquals(0, test.size());
		//Test right walkway
		test = board.getAdjList(8, 18);
		Assert.assertEquals(0, test.size());
		//Test beside door
		test = board.getAdjList(19, 10);
		Assert.assertEquals(0, test.size());
	}

	// Purple on map
	@Test
	public void testAdjExit(){
		// Door up
		LinkedList<BoardCell> test = board.getAdjList(18, 11);
		Assert.assertEquals(1, test.size());
		// Door down
		test = board.getAdjList(4, 10);
		Assert.assertEquals(1, test.size());
		// Door right
		test = board.getAdjList(8, 4);
		Assert.assertEquals(1, test.size());
		// Door left
		test = board.getAdjList(7, 14);
		Assert.assertEquals(1, test.size());
	}

	// Green on map
	@Test
	public void testAdjDoor(){
		// Test movement in front of a door DOWN
		LinkedList<BoardCell> test = board.getAdjList(17, 11);
		Assert.assertTrue(test.contains(board.getCellAt(17,12)));
		Assert.assertTrue(test.contains(board.getCellAt(17,10)));
		Assert.assertTrue(test.contains(board.getCellAt(16,11)));
		Assert.assertTrue(test.contains(board.getCellAt(18, 11)));
		Assert.assertEquals(4, test.size());

		// Test movement in front of a door RIGHT
		test = board.getAdjList(7, 13);
		Assert.assertTrue(test.contains(board.getCellAt(8,13)));
		Assert.assertTrue(test.contains(board.getCellAt(6,13)));
		Assert.assertTrue(test.contains(board.getCellAt(7,14)));
		Assert.assertEquals(3, test.size());

		// Test movement in front of a door LEFT
		test = board.getAdjList(13, 5);
		Assert.assertTrue(test.contains(board.getCellAt(14,5)));
		Assert.assertTrue(test.contains(board.getCellAt(12,5)));
		Assert.assertTrue(test.contains(board.getCellAt(13,6)));
		Assert.assertTrue(test.contains(board.getCellAt(13,4)));
		Assert.assertEquals(4, test.size());

		// Test movement in front of a door UP
		test = board.getAdjList(5, 2);
		Assert.assertTrue(test.contains(board.getCellAt(6,2)));
		Assert.assertTrue(test.contains(board.getCellAt(4,2)));
		Assert.assertTrue(test.contains(board.getCellAt(5,1)));
		Assert.assertTrue(test.contains(board.getCellAt(5,3)));
		Assert.assertEquals(4, test.size());
	}

	// Light purple on map
	@Test
	public void testWalk(){
		// Test center of walkway
		LinkedList<BoardCell> test = board.getAdjList(14, 14);
		Assert.assertTrue(test.contains(board.getCellAt(15,14)));
		Assert.assertTrue(test.contains(board.getCellAt(13,14)));
		Assert.assertTrue(test.contains(board.getCellAt(14,15)));
		Assert.assertTrue(test.contains(board.getCellAt(14,13)));
		Assert.assertEquals(4, test.size());

		// Test Hallway with only one route
		test = board.getAdjList(22, 10);
		Assert.assertTrue(test.contains(board.getCellAt(22,9)));
		Assert.assertEquals(1, test.size());

		// Test Hallway top
		test = board.getAdjList(0, 5);
		Assert.assertTrue(test.contains(board.getCellAt(1,5)));
		Assert.assertTrue(test.contains(board.getCellAt(0,6)));
		Assert.assertEquals(2, test.size());

		// Test Hallway bottom
		test = board.getAdjList(22, 14);
		Assert.assertTrue(test.contains(board.getCellAt(21,14)));
		Assert.assertTrue(test.contains(board.getCellAt(22,13)));
		Assert.assertEquals(2, test.size());

		// Test Hallway left
		test = board.getAdjList(5, 0);
		Assert.assertTrue(test.contains(board.getCellAt(6,0)));
		Assert.assertTrue(test.contains(board.getCellAt(5,1)));
		Assert.assertEquals(2, test.size());		

		// Test Hallway right
		test = board.getAdjList(17, 22);
		Assert.assertTrue(test.contains(board.getCellAt(18,22)));
		Assert.assertTrue(test.contains(board.getCellAt(17,21)));
		Assert.assertEquals(2, test.size());	

		// Test Hallway next to closet
		test = board.getAdjList(6, 9);
		Assert.assertTrue(test.contains(board.getCellAt(5,9)));
		Assert.assertTrue(test.contains(board.getCellAt(6,8)));
		Assert.assertTrue(test.contains(board.getCellAt(6,10)));
		Assert.assertEquals(3, test.size());	

		// Test Hallway next to room
		test = board.getAdjList(9, 18);
		Assert.assertTrue(test.contains(board.getCellAt(9,17)));
		Assert.assertTrue(test.contains(board.getCellAt(9,19)));
		Assert.assertEquals(2, test.size());	
	}

	// Light blue on map
	@Test
	public void testWalking(){
		// Move one space
		board.calcTargets(13, 14, 1);
		Set<BoardCell> test = board.getTargets();
		Assert.assertTrue(test.contains(board.getCellAt(14,14)));
		Assert.assertTrue(test.contains(board.getCellAt(12,14)));
		Assert.assertTrue(test.contains(board.getCellAt(13,15)));
		Assert.assertTrue(test.contains(board.getCellAt(13,13)));
		Assert.assertEquals(4, test.size());

		// Move three spaces
		board.calcTargets(0, 6, 3);
		test = board.getTargets();
		Assert.assertTrue(test.contains(board.getCellAt(0,5)));
		Assert.assertTrue(test.contains(board.getCellAt(3,6)));
		Assert.assertTrue(test.contains(board.getCellAt(2,5)));
		Assert.assertEquals(3, test.size());

		// Move two spaces
		board.calcTargets(15, 4, 2);
		test = board.getTargets();
		Assert.assertTrue(test.contains(board.getCellAt(17,4)));
		Assert.assertTrue(test.contains(board.getCellAt(15,2)));
		Assert.assertTrue(test.contains(board.getCellAt(16,3)));
		Assert.assertTrue(test.contains(board.getCellAt(16,5)));
		Assert.assertTrue(test.contains(board.getCellAt(15,6)));
		Assert.assertTrue(test.contains(board.getCellAt(14,5)));
		Assert.assertEquals(6, test.size());
		
		// Move Three spaces
		board.calcTargets(9, 19, 3);
		test = board.getTargets();
		Assert.assertTrue(test.contains(board.getCellAt(8,21)));
		Assert.assertTrue(test.contains(board.getCellAt(7,20)));
		Assert.assertTrue(test.contains(board.getCellAt(6,19)));
		Assert.assertTrue(test.contains(board.getCellAt(9,16)));
		Assert.assertEquals(4, test.size());
	}
	
	// Light Blue
	@Test
	public void testWalkingInDoor(){
		// Gets into room with moves left over
		board.calcTargets(17, 4, 3);
		Set<BoardCell> test = board.getTargets();
		Assert.assertTrue(test.contains(board.getCellAt(16,2)));
		Assert.assertTrue(test.contains(board.getCellAt(15,3)));
		Assert.assertTrue(test.contains(board.getCellAt(15,4)));
		Assert.assertTrue(test.contains(board.getCellAt(16,6)));
		Assert.assertTrue(test.contains(board.getCellAt(17,7)));
		Assert.assertTrue(test.contains(board.getCellAt(18,6)));
		Assert.assertTrue(test.contains(board.getCellAt(19,4)));
		Assert.assertEquals(7, test.size());
		
		// Just enough to get in
		board.calcTargets(5, 11, 1);
		test = board.getTargets();
		Assert.assertTrue(test.contains(board.getCellAt(5,10)));
		Assert.assertTrue(test.contains(board.getCellAt(5,12)));
		Assert.assertTrue(test.contains(board.getCellAt(6,11)));
		Assert.assertTrue(test.contains(board.getCellAt(4,11)));
		Assert.assertEquals(4, test.size());
	}
	
	// Light blue on map
	@Test
	public void testWalkingOutDoor(){
		// Moves one out the door
		board.calcTargets(5, 20, 1);
		Set<BoardCell> test= board.getTargets();
		Assert.assertEquals(1, test.size());
		Assert.assertTrue(test.contains(board.getCellAt(5, 19)));
		
		// Moves two out the door
		board.calcTargets(8, 4, 2);
		test = board.getTargets();
		Assert.assertEquals(3, test.size());
		Assert.assertTrue(test.contains(board.getCellAt(8, 6)));
		Assert.assertTrue(test.contains(board.getCellAt(7, 5)));
		Assert.assertTrue(test.contains(board.getCellAt(9, 5)));
	}
}