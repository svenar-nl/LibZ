package me.winspeednl.libz.fx;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Particle class.
 * A single particle to be created / rendered using the Emitter class
 * 
 * @author      Sven Arends <sarends98@gmail.com>
 * @version     1.0
 * @since       0.7
 */
public class Particle {

	private int x, y, s, speed, angle, lifetime;
	private long start, alive;
	private Color color;
	
	/**
	 * Create a particle
	 * @param positionX
	 * @param positionY
	 * @param size
	 * @param movementSpeed
	 * @param rotation
	 * @param color
	 * @param lifetime
	 */
	public Particle(int x, int y, int size, int speed, int angle, Color color, int lifetime) {
		this.x = x;
		this.y = y;
		this.s = size;
		this.speed = speed;
		this.angle = angle;
		this.color = color;
		this.lifetime = lifetime;
		this.start = System.currentTimeMillis();
	}
	
	/**
	 * Update position and lifetime
	 */
	public void update() {
		x += speed * Math.cos(Math.toRadians(angle));
		y += speed * Math.sin(Math.toRadians(angle));
		alive = System.currentTimeMillis() - start;
	}
	
	/**
	 * Render this particle
	 * @param graphics
	 */
	public void render(Graphics g) {
		Color origC = g.getColor();
		g.setColor(color);
		g.fillOval(x, y, s, s);
		g.setColor(origC);
	}
	
	/**
	 * Get positionX
	 * @return positionX
	 */
	public int getX() {
		return x;
	}

	/**
	 * Get positionY
	 * @return positionY
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Time since creation
	 * @return alive
	 */
	public long getAlive() {
		return alive;
	}
	
	/**
	 * Time to live
	 * @return lifetime
	 */
	public int getLifetime() {
		return lifetime;
	}
}
