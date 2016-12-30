package me.winspeednl.libz.graphics;

import java.awt.Graphics;
import java.awt.Point;

/**
 * Render class.
 * Provides methods for rendering on the screen.
 * 
 * @author      Sven Arends <sarends98@gmail.com>
 * @version     1.0
 * @since       1.0
 */
public class Camera {
	
	private int x, y, w, h, offsetX, offsetY;
	
	/**
	 * Constructor, used in GameCore.java, init()
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Camera(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	/**
	 * Translate Camera
	 * @param graphics
	 */
	public void translate(Graphics g) {
		g.translate(offsetX, offsetY);
	}
	
	/**
	 * Reset Camera
	 * @param graphics
	 */
	public void revert(Graphics g) {
		g.translate(-offsetX, -offsetY);
	}
	
	/**
	 * Get the X Position
	 * @return x
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Set the X Position
	 * @param x
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * Get hte Y Position
	 * @return y
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Set the Y Position
	 * @param y
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Get the width
	 * @return width
	 */
	public int getWidth() {
		return w;
	}
	
	/**
	 * Set the width
	 * @param width
	 */
	public void setWidth(int w) {
		this.w = w;
	}
	
	/**
	 * Get the height
	 * @return height
	 */
	public int getHeight() {
		return h;
	}
	
	/**
	 * Set the height
	 * @param height
	 */
	public void setHeight(int h) {
		this.h = h;
	}
	
	/**
	 * Get the offset
	 * @return Point
	 */
	public Point getOffset() {
		return new Point(offsetX, offsetY);
	}
	
	/**
	 * Set the X offset
	 * @param offsetX
	 */
	public void addOffsetX(int offsetX) {
		this.offsetX += offsetX;
	}
	
	/**
	 * Set the Y offset
	 * @param offsetY
	 */
	public void addOffsetY(int offsetY) {
		this.offsetY += offsetY;
	}
}
