package me.winspeednl.libz.pathfinding;

public class Node {
	private int x, y, g, h, f;
	private Node parent;
	
	public Node(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setParent(Node parent) {
		this.parent = parent;
	}
	
	public void setGCost(int g) {
		this.g = g;
		this.f = g + h;
	}
	
	public void setHCost(int h) {
		this.h = h;
		this.f = g + h;
	}
	
	public void setFCost(int f) {
		this.f = f;
	}
	
	public Node getParent() {
		return parent;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public int getGCost() {
		return g;
	}
	
	public int getHCost() {
		return h;
	}
	
	public int getFCost() {
		return f;
	}
	
	@Override
	public String toString(){
		return "Node(" + this.getX() + "," + this.getY() + ")";
	}
}
