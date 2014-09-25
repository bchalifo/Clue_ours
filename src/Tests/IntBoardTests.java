package Tests;

import static org.junit.Assert.*;
import java.util.LinkedList;
import java.util.Set;
import junit.framework.Assert;
import Game.*;
import org.junit.Before;
import org.junit.Test;

public class IntBoardTests {
	private IntBoard board;
	
	@Before
	public void setUp(){
		String fileName = "";
		board = new IntBoard(fileName);
	}
	
	@Test
	public void testAdj(){
		BoardCell start = board.getCell(0,3);
		LinkedList<BoardCell> list = board.getAdjList(start);
		Assert.assertTrue(list.contains(board.getCell(0, 4)));
		Assert.assertTrue(list.contains(board.getCell(1, 3)));
		Assert.assertEquals(2, list.size());
	}
	
	@Test
	public void testAdj1(){
		BoardCell start = board.getCell(9, 0);
		LinkedList<BoardCell> list = board.getAdjList(start);
		Assert.assertTrue(list.contains(board.getCell(10, 0)));
		Assert.assertTrue(list.contains(board.getCell(9, 1)));
		Assert.assertEquals(2, list.size());
	}
	
	@Test
	public void testAdj2(){
		BoardCell start = board.getCell(6, 8);
		LinkedList<BoardCell> list = board.getAdjList(start);
		Assert.assertTrue(list.contains(board.getCell(7, 8)));
		Assert.assertTrue(list.contains(board.getCell(5, 8)));
		Assert.assertTrue(list.contains(board.getCell(6, 9)));
		Assert.assertTrue(list.contains(board.getCell(6, 7)));
		Assert.assertEquals(4, list.size());
	}
	
	@Test
	public void testTargets03_2(){
		BoardCell start = board.getCell(0, 3);
		board.calcTargets(start, 2);
		Set targets = board.getTargets();
		Assert.assertEquals(2, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(1, 4)));
		Assert.assertTrue(targets.contains(board.getCell(2, 3)));
	}
	
	@Test
	public void testTargets90_3(){
		BoardCell start = board.getCell(9, 0);
		board.calcTargets(start, 3);
		Set targets = board.getTargets();
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(9, 1)));
		Assert.assertTrue(targets.contains(board.getCell(9, 3)));
		Assert.assertTrue(targets.contains(board.getCell(10, 0)));
		Assert.assertTrue(targets.contains(board.getCell(10, 2)));
		Assert.assertTrue(targets.contains(board.getCell(11, 1)));
		Assert.assertTrue(targets.contains(board.getCell(12, 0)));
	}
	
	@Test
	public void testTargets68_4(){
		BoardCell start = board.getCell(6, 8);
		board.calcTargets(start, 4);
		Set targets = board.getTargets();
		Assert.assertEquals(6, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(6, 4)));
		Assert.assertTrue(targets.contains(board.getCell(5, 5)));
		Assert.assertTrue(targets.contains(board.getCell(5, 7)));
		Assert.assertTrue(targets.contains(board.getCell(5, 9)));
		Assert.assertTrue(targets.contains(board.getCell(5, 11)));
		Assert.assertTrue(targets.contains(board.getCell(6, 8)));
		Assert.assertTrue(targets.contains(board.getCell(6, 12)));
		Assert.assertTrue(targets.contains(board.getCell(10, 8)));
	}
}
