package me.sven.libz.graphics;

import java.awt.Color;
import java.awt.Graphics;

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

	public void drawRect(Float x, Float y, Float w, Float h) {
		drawRect(Math.round(x), Math.round(y), Math.round(w), Math.round(h));
	}
}
