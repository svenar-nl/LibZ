package me.winspeednl.libz.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import me.winspeednl.libz.core.GameCore;
import me.winspeednl.libz.image.Sprite;

/**
 * Render class.
 * Provides methods for rendering on the screen.
 * 
 * @author      Sven Arends <sarends98@gmail.com>
 * @version     1.0
 * @since       0.2
 */
public class Render {

	private GameCore gc;
	
	/**
	 * Constructor, used in GameCore.java, init()
	 * @param GameCore
	 */
	public Render(GameCore gc) {
		this.gc = gc;
	}

	/**
	 * Draws a sprite onto the screen
	 * @param sprite
	 * @param x
	 * @param y
	 */
	public void drawSprite(Sprite sprite, int x, int y) {
		Graphics g = getGraphics();
		g.drawImage(sprite.image, x, y, null);
	}
	
	/**
	 * Draws a sprite onto the screen with the given rotation
	 * @param sprite
	 * @param x
	 * @param y
	 * @param rotation
	 */
	public void drawSprite(Sprite sprite, int x, int y, double rotation) {
		Graphics g = getGraphics();
		AffineTransform transform = new AffineTransform();
	    transform.rotate(rotation, sprite.image.getWidth() / 2, sprite.image.getHeight() / 2);
	    AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
	    BufferedImage image = op.filter(sprite.image, null);
		g.drawImage(image, x, y, null);
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
	 * Draw a circle
	 * @param x
	 * @param y
	 * @param circleRadius
	 */
	public void drawCircle(int x, int y, int circleRadius) {
		Graphics g = getGraphics();
		g.drawOval(x, y, circleRadius, circleRadius);
	}
	
	/**
	 * Flip a sprite
	 * @param sprite
	 * @param horizontal
	 * @param vertical
	 * @return Sprite
	 */
	public Sprite flip(Sprite sprite, boolean horizontal, boolean vertical) {
		BufferedImage image = sprite.image;
		AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
		AffineTransformOp op;
		
		if (horizontal && !vertical) {
			tx = AffineTransform.getScaleInstance(-1, 1);
			tx.translate(-image.getWidth(null), 0);
			op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			image = op.filter(image, null);
		} else if (!horizontal && vertical) {
			tx.translate(0, -image.getHeight(null));
			op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			image = op.filter(image, null);
		} else {
			tx = AffineTransform.getScaleInstance(-1, -1);
			tx.translate(-image.getWidth(null), -image.getHeight(null));
			op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			image = op.filter(image, null);
		}
		Sprite s = new Sprite(sprite.path, 0, 0, sprite.width, sprite.height);
		s.image = image;
		return s;
	}
	
	/**
	 * Get the Graphics to draw on
	 * @return Graphics
	 */
	public Graphics getGraphics() {
		return gc.getScreen().getGraphics();
	}
}
