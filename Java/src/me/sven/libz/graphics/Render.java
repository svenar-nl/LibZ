package me.sven.libz.graphics;

import java.awt.Color;
import java.awt.Graphics;

import me.sven.libz.image.Sprite;

public class Render {
	
	private Graphics g;
	
	public Render(Graphics g) {
		this.g = g;
	}
	
	public Graphics getGraphics() {
		return g;
	}

	public void setGraphics(Graphics g) {
		this.g = g;
		
	}
	
	/**
	 * Draw a rectangle
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param color
	 */
	public void drawRect(int x, int y, int w, int h, Color color) {
		Graphics g = getGraphics();
		Color origColor = g.getColor();
		g.setColor(color);
		drawRect(x, y, w, h);
		g.setColor(origColor);
	}
	
	/**
	 * Draw a rectangle
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public void drawRect(int x, int y, int w, int h) {
		Graphics g = getGraphics();
		x = Math.min(x, x + w);
		w = Math.abs(w);
		y = Math.min(y, y + h);
		h = Math.abs(h);
		g.drawRect(x, y, w, h);
	}
	
	/**
	 * Draw a sprite with external x and y values
	 * @param sprite
	 * @param x
	 * @param y
	 */
	public void drawSprite(Sprite sprite, int x, int y) {
		Graphics g = getGraphics();
		g.drawImage(sprite.image, x, y, null);
	}
	
	/**
	 * Draw a sprite with internal x and y values
	 * @param sprite
	 */
	public void drawSprite(Sprite sprite) {
		Graphics g = getGraphics();
		g.drawImage(sprite.image, sprite.getX(), sprite.getY(), null);
	}
}
