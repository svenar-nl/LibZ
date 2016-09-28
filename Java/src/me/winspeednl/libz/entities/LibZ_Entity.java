package me.winspeednl.libz.entities;

import java.awt.Rectangle;

import me.winspeednl.libz.core.GameCore;
import me.winspeednl.libz.level.Tile;
import me.winspeednl.libz.screen.Render;

public abstract class LibZ_Entity {
	public int x, y, w, h, health;

	public abstract void update(GameCore gc);
	public abstract void render(GameCore gc, Render r);
	
	public void taskTriggered(String taskName) {
		// This method needs to be overwritten to deal with tasks
	}
	
	public boolean collides(int x1, int y1, int w1, int h1) {
		Rectangle object1 = new Rectangle(x, y, w, h);
		Rectangle object2 = new Rectangle(x1, y1, w1, h1);
		return object1.intersects(object2);
	}
	
	public boolean collides(LibZ_Entity e) {
		Rectangle object1 = new Rectangle(x, y, w, h);
		Rectangle object2 = new Rectangle(e.x, e.y, e.w, e.h);
		return object1.intersects(object2);
	}
	
	public boolean collides(LibZ_Entity e, int margin) {
		Rectangle object1 = new Rectangle(x, y, w, h);
		Rectangle object2 = new Rectangle(e.x + margin, e.y + margin, e.w - margin, e.h - margin);
		return object1.intersects(object2);
	}
	
	public boolean collides(Tile tile) {
		Rectangle object1 = new Rectangle(x, y, w, h);
		Rectangle object2 = new Rectangle(tile.getX(), tile.getY(), tile.getWidth(), tile.getHeight());
		return object1.intersects(object2);
	}
	
	public boolean collides(Tile tile, int margin) {
		Rectangle object1 = new Rectangle(x, y, w, h);
		Rectangle object2 = new Rectangle(tile.getX() + margin, tile.getY() + margin, tile.getWidth() - margin, tile.getHeight() - margin);
		return object1.intersects(object2);
	}
}
