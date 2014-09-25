package Game;

import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class IntBoard {
	private Map board;
	
	public IntBoard(){
		for(int i = 0; i < 4; i++){
			for(int j = 0; j < 4; j++){
				BoardCell cell = new BoardCell(i,j);
			}
		}
	}
	
	public BoardCell getCell(int row, int col){
		
		return null;
	}
	
	public void calcAdjacencies(){
		LinkedList adj = new LinkedList();
		
	}
	
	public void calcTargets(BoardCell cell, int roll){
		
	}
	
	public Set getTargets(){
		
		return null;
	}
	
	public LinkedList getAdjList(BoardCell cell){
		
		return null;
	}
}
