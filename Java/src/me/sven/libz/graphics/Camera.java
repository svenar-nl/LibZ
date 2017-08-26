package me.sven.libz.graphics;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;

import me.sven.libz.core.Settings;

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
	private double zoom;
	
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
		this.zoom = 1;
	}
	
	private AffineTransform old;
	public void apply(Graphics2D g2d) {
		if (zoom < 0.2f) zoom = 0.2f;
		if (zoom > 12) zoom = 12;
		old = g2d.getTransform();
		AffineTransform tr2 = new AffineTransform(old);
		tr2.translate(offsetX * 2, offsetY * 2);
		tr2.translate(Settings.width / 2 - offsetX * 2, Settings.height / 2 - offsetY * 2);
		tr2.scale(zoom, zoom);
		tr2.translate(-Settings.width / 2 + offsetX / 2, -Settings.height / 2 + offsetY / 2);
		g2d.setTransform(tr2);
	}
	
	public void revert(Graphics2D g2d) {
		g2d.setTransform(old);
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
	public void revertTranslate(Graphics g) {
		g.translate(-offsetX, -offsetY);
	}
	
	public void zoom(Graphics2D g2d) {
		g2d.scale(zoom, zoom);
	}
	
	public void revertZoom(Graphics2D g2d) {
		g2d.scale(-zoom, -zoom);
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
	 * Add amount to the X offset
	 * @param offsetX
	 */
	public void addOffsetX(int offsetX) {
		this.offsetX += offsetX;
	}
	
	/**
	 * Add amount to the Y offset
	 * @param offsetY
	 */
	public void addOffsetY(int offsetY) {
		this.offsetY += offsetY;
	}
	
	/**
	 * set offset x
	 * @param offsetX
	 */
	public void setOffsetX(int offsetX) {
		this.offsetX = offsetX;
	}
	
	/**
	 * set offset y
	 * @param offsetY
	 */
	public void setOffsetY(int offsetY) {
		this.offsetY = offsetY;
	}
	
	/**
	 * Add amount to the zoom amount
	 * @param d
	 */
	public void addZoom(double zoom) {
		this.zoom += zoom;
	}
	
	public double getZoom() {
		return zoom;
	}

	public void setZoom(double zoom) {
		this.zoom = zoom;
	}

}