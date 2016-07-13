package me.winspeednl.libz.entities;

import java.awt.Rectangle;

import me.winspeednl.libz.core.GameCore;
import me.winspeednl.libz.level.Tile;
import me.winspeednl.libz.screen.Render;

public abstract class Entity2 {
	public int x, y, w, h, health, lookDirection, kills, deaths, damage;
	public boolean dead = false, canHit = false, isPlayer = false, isEnemy = false, isSolid = false, canExplode = false, exploded = false;

	public abstract void update(GameCore gc, float dt);
	public abstract void render(GameCore gc, Render r);
	
	public boolean collides(int x1, int y1, int w1, int h1) {
		Rectangle object1 = new Rectangle(x, y, w, h);
		Rectangle object2 = new Rectangle(x1, y1, w1, h1);
		return object1.intersects(object2);
	}
	
	public boolean collides(Entity2 e) {
		Rectangle object1 = new Rectangle(x, y, w, h);
		Rectangle object2 = new Rectangle(e.x, e.y, e.w, e.h);
		return object1.intersects(object2);
	}
	
	public boolean collides(Entity2 e, int margin) {
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
