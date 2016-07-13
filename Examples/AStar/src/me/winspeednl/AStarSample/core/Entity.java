/*
* Written by: Sven Arends
* On: 12-07-2016 (dd-mm-yyyy)
* This is a LibZ Example
* 
* THIS CODE IS PUBLIC DOMAIN
*/
package me.winspeednl.AStarSample.core;

import java.util.ArrayList;

import me.winspeednl.libz.core.GameCore;
import me.winspeednl.libz.entities.LibZ_Entity;
import me.winspeednl.libz.image.Sprite;
import me.winspeednl.libz.level.Tile;
import me.winspeednl.libz.pathfinding.Node;
import me.winspeednl.libz.screen.Render;

public class Entity extends LibZ_Entity {
	private int[] pixels;
	private ArrayList<Tile> nodes = new ArrayList<Tile>();
	private int walkToNode = 0;
	private int speed = 1;
	
	public Entity(Sprite sprite, int x, int y, int size) {
		this.x = x;
		this.y = y;
		this.w = this.h = size;
		this.pixels = sprite.pixels;
	}
	
	public void update(GameCore gc) {
		if (nodes.size() > 0) {
			if (walkToNode < nodes.size()) {
				int nX = nodes.get(walkToNode).getX(),
					nY = nodes.get(walkToNode).getY();
				Node node = new Node(nX, nY);
				
				if (x == node.getX() && y == node.getY())
					walkToNode++;
				
				if (x < node.getX())
					x += speed;
				if (x > node.getX())
					x -= speed;
				
				if (y < node.getY())
					y += speed;
				if (y > node.getY())
					y -= speed;
			}
		}
	}
	
	public void render(GameCore gc, Render r) {
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				r.setPixel(this.x + x, this.y + y, pixels[x + y * w]);
			}
		}
	}
	
	public void setNodes(ArrayList<Tile> nodes) {
		this.nodes = nodes;
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public int getSpeed() {
		return speed;
	}
}
