package Tests;

import static org.junit.Assert.*;

import java.util.HashSet;
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
		board = new IntBoard();
	}
	
	@Test
	public void testAdj(){
		BoardCell start = board.getCell(0,0);
		LinkedList<BoardCell> list = board.getAdjList(start);
		Assert.assertTrue(list.contains(board.getCell(0, 1)));
		Assert.assertTrue(list.contains(board.getCell(1, 0)));
		Assert.assertEquals(2, list.size());
	}
	
	@Test
	public void testAdj1(){
		BoardCell start = board.getCell(1, 2);
		LinkedList<BoardCell> list = board.getAdjList(start);
		Assert.assertTrue(list.contains(board.getCell(0, 2)));
		Assert.assertTrue(list.contains(board.getCell(1, 3)));
		Assert.assertTrue(list.contains(board.getCell(2, 2)));
		Assert.assertTrue(list.contains(board.getCell(1, 1)));
		Assert.assertEquals(4, list.size());
	}
	
	@Test
	public void testAdj2(){
		BoardCell start = board.getCell(2, 3);
		LinkedList<BoardCell> list = board.getAdjList(start);
		Assert.assertTrue(list.contains(board.getCell(1, 3)));
		Assert.assertTrue(list.contains(board.getCell(2, 2)));
		Assert.assertTrue(list.contains(board.getCell(3, 3)));
		Assert.assertEquals(3, list.size());
	}
	
	@Test
	public void testTargets00_2(){
		BoardCell start = board.getCell(0, 0);
		
		System.out.println("Start: 0, 0");
		
		board.calcTargets(start, 2);
		HashSet targets = new HashSet<BoardCell>();		
		targets = board.getTargets();		
		Assert.assertEquals(3, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(0, 2)));
		Assert.assertTrue(targets.contains(board.getCell(2, 0)));
		Assert.assertTrue(targets.contains(board.getCell(1, 1)));
	}
	
	@Test
	public void testTargets12_3(){
		BoardCell start = board.getCell(1, 2);
		
		System.out.println("Start: 1, 2");
		
		board.calcTargets(start, 3);
		HashSet targets = new HashSet<BoardCell>();		
		targets = board.getTargets();		
		Assert.assertEquals(8, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(1, 3)));
		Assert.assertTrue(targets.contains(board.getCell(1, 1)));
		Assert.assertTrue(targets.contains(board.getCell(0, 0)));
		Assert.assertTrue(targets.contains(board.getCell(2, 0)));
		Assert.assertTrue(targets.contains(board.getCell(3, 1)));
		Assert.assertTrue(targets.contains(board.getCell(3, 3)));
		Assert.assertTrue(targets.contains(board.getCell(2, 2)));
		Assert.assertTrue(targets.contains(board.getCell(0, 2)));
	}
	
	@Test
	public void testTargets23_4(){
		BoardCell start = board.getCell(2, 3);
		
		System.out.println("Start: 2, 3");
		
		board.calcTargets(start, 4);
		HashSet targets = new HashSet<BoardCell>();		
		targets = board.getTargets();		
		Assert.assertEquals(7, targets.size());
		Assert.assertTrue(targets.contains(board.getCell(0, 1)));
		Assert.assertTrue(targets.contains(board.getCell(1, 2)));
		Assert.assertTrue(targets.contains(board.getCell(1, 0)));
		Assert.assertTrue(targets.contains(board.getCell(3, 0)));
		Assert.assertTrue(targets.contains(board.getCell(3, 2)));
		Assert.assertTrue(targets.contains(board.getCell(2, 1)));
		Assert.assertTrue(targets.contains(board.getCell(0, 3)));
	}
}
