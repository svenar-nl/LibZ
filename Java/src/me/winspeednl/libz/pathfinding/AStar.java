package me.winspeednl.libz.pathfinding;

import java.util.ArrayList;
import java.util.PriorityQueue;

import me.winspeednl.libz.level.Level;

public class AStar {
	private final int DIAGONAL_COST = 14;
	private final int V_H_COST = 10;
	private boolean diagonal = true;

	private Node [][] grid = new Node[5][5];

	private PriorityQueue<Node> open;
 
	private boolean closed[][];
	private int startX, startY, endX, endY;

	private void setBlocked(int x, int y){
		grid[x][y] = null;
	}

	private void setStartCell(int x, int y){
		startX = x;
		startY = y;
	}

	private void setEndCell(int x, int y){
		endX = x;
		endY = y; 
	}

	private void checkAndUpdateCost(Node current, Node target, int cost){
		if(target == null || closed[target.getX()][target.getY()])
			return;
		int targetFCost = target.getHCost() + cost;

		boolean inOpen = open.contains(target);
		if(!inOpen || targetFCost < target.getFCost()){
			target.setFCost(targetFCost);
			target.setParent(current);
			if(!inOpen)
				open.add(target);
		}
	}

	private void AStarCalculator(){
		open.add(grid[startX][startY]);
		Node current;
		while(true){ 
			current = open.poll();
			if(current == null)
				break;
			closed[current.getX()][current.getY()] = true; 
					if(current.equals(grid[endX][endY])) {
				return; 
			} 
			Node t;  
			if(current.getX() - 1 >= 0){
				t = grid[current.getX() - 1][current.getY()];
				checkAndUpdateCost(current, t, current.getFCost() + V_H_COST); 
				if (diagonal) {
					if(current.getY() - 1 >= 0){  
						t = grid[current.getX() - 1][current.getY() - 1];
						checkAndUpdateCost(current, t, current.getFCost() + DIAGONAL_COST); 
					}
					if(current.getY() + 1 < grid[0].length){
						t = grid[current.getX() - 1][current.getY() + 1];
						checkAndUpdateCost(current, t, current.getFCost() + DIAGONAL_COST); 
					}
				}
			} 
			if(current.getY() - 1 >= 0){
				t = grid[current.getX()][current.getY() - 1];
				checkAndUpdateCost(current, t, current.getFCost() + V_H_COST); 
			}
			if(current.getY() + 1 < grid[0].length){
				t = grid[current.getX()][current.getY() + 1];
				checkAndUpdateCost(current, t, current.getFCost() + V_H_COST); 
			}
			if(current.getX() + 1 < grid.length){
				t = grid[current.getX() + 1][current.getY()];
				checkAndUpdateCost(current, t, current.getFCost() + V_H_COST); 
				if (diagonal) {
					if(current.getY() - 1 >= 0){
						t = grid[current.getX() + 1][current.getY() - 1];
						checkAndUpdateCost(current, t, current.getFCost() + DIAGONAL_COST); 
					}
					if(current.getY() + 1 < grid[0].length){
						t = grid[current.getX() + 1][current.getY() + 1];
						checkAndUpdateCost(current, t, current.getFCost() + DIAGONAL_COST); 
					}
				}
			}
		}
	}

	public ArrayList<Node> calculate(int width, int height, int sx, int sy, int ex, int ey, int[][] blocked){
		ArrayList<Node> path = new ArrayList<Node>();

		try {
			grid = new Node[width][height];
			closed = new boolean[width][height];
			open = new PriorityQueue<>((Object o1, Object o2) -> {
				Node c1 = (Node)o1;
				Node c2 = (Node)o2;
				return c1.getFCost() < c2.getFCost() ? - 1:
					c1.getFCost() > c2.getFCost() ? 1 : 0;
			});

			setStartCell(sx, sy);
			setEndCell(ex, ey); 
			for(int x = 0; x < width; x++){
				for(int y = 0; y < height; y++){
					grid[x][y] = new Node(x, y);
					grid[x][y].setHCost(Math.abs(x-ex)+Math.abs(x-ey));
				}
			}
			grid[sx][sy].setFCost(0);
			
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					if (blocked[x][y] == 1)
						setBlocked(x, y);
				}
			}
		   
			AStarCalculator(); 
		
		
			if(closed[endX][endY]){
				Node current = grid[endX][endY];
				int X = Integer.parseInt(current.toString().split(",")[0]);
				int Y = Integer.parseInt(current.toString().split(",")[1]);
				path.add(new Node(X, Y));
		
				while(current.getParent() != null){
					X = Integer.parseInt(current.getParent().toString().split(",")[0]);
					Y = Integer.parseInt(current.getParent().toString().split(",")[1]);
					path.add(new Node(X, Y));
					current = current.getParent();
				} 
			} else {
				return null;
			}
		} catch (Exception e) {}
		return path;
	}
	
	public void useDiagonal(boolean bool) {
		diagonal = bool;
		
	}
	
	public int[][] getCollisionMap(Level level) {
		int[][] colls = new int[level.getWidth()][level.getHeight()];
		
		for (int y = 0; y < level.getHeight(); y++) {
			for (int x = 0; x < level.getWidth(); x++) {
				int tileId = Integer.parseInt(level.getMapData()[x + y * level.getWidth()]); 
				if (level.getMapTiles()[tileId].isSolid())
					colls[x][y] = 1;
				else
					colls[x][y] = 0;
			}
		}
		
		return colls;
	}
	
	public ArrayList<Node> getNodes(ArrayList<Node> path, int tileWidth, int tileHeight) {
		ArrayList<Node> nodes = new ArrayList<Node>();
		for (int i = 0; i < path.size(); i++) {
			Node node = path.get(i);
			int x = node.getX() * tileWidth;
			int y = node.getY() * tileHeight;
			nodes.add(new Node(x, y));
		}
		return nodes;
	}
}