package me.winspeednl.libz.entities;

import me.winspeednl.libz.core.GameCore;
import me.winspeednl.libz.graphics.Render;

/**
 * Entity class.
 * Create non-static objects
 * 
 * @author      Sven Arends <sarends98@gmail.com>
 * @version     1.0
 * @since       0.2
 */
public abstract class Entity {
	
	// Main variables
	private int x, y, w, h;
	private boolean alive = true;
	
	/**
	 * Constructor
	 * Is called using 'super(gc);'
	 * Adds 'this' to GameCore entities list
	 */
	public Entity(GameCore gc) {
		gc.getEntities().add(this);
	}
	
	/**
	 * Update this entity
	 * @param GameCore
	 */
	public abstract void update(GameCore gc);
	
	/**
	 * Render this entity
	 * @param GameCore
	 * @param Render
	 */
	public abstract void render(GameCore gc, Render r);
	
	/**
	 * Life is not forever
	 * Delete from CameCore entities list
	 */
	public void die() {
		alive = false;
	}
	
	/**
	 * @return alive
	 */
	public boolean isAlive() {
		return alive;
	}
	
	/**
	 * Set the X position
	 * @param x
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * Set the Y position
	 * @param y
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * Set the width
	 * @param width
	 */
	public void setWidth(int w) {
		this.w = w;
	}
	
	/**
	 * Set the height
	 * @param height
	 */
	public void setHeight(int h) {
		this.h = h;
	}
	
	/**
	 * Get the X position
	 * @return x
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Get the Y position
	 * @return y
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Get the width
	 * @return width
	 */
	public int getWidth() {
		return w;
	}
	
	/**
	 * Get the height
	 * @return height
	 */
	public int getHeight() {
		return h;
	}
}
