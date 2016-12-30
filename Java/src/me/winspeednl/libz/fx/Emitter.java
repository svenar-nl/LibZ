package me.winspeednl.libz.fx;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import me.winspeednl.libz.core.GameCore;

/**
 * Emitter class.
 * This class creates / renders all particles
 * 
 * @author      Sven Arends <sarends98@gmail.com>
 * @version     1.0
 * @since       0.7
 */
public class Emitter {
	
	private GameCore gc;
	private ArrayList<Particle> particles = new ArrayList<Particle>();
	private int x, y;
	public int particlesAlive = 0;
	
	/**
	 * Create a particle emitter
	 * @param gamecore
	 * @param positionX
	 * @param positionY
	 */
	public Emitter(GameCore gc, int x, int y) {
		this.gc = gc;
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Create particle
	 * @param count
	 * @param size
	 * @param movementSpeed
	 * @param color
	 * @param lifetime
	 */
	public void createCircle(int count, int size, int speed, Color color, int lifetime) {
		for (int i = 0; i < count; i++) {
			int angle = (360 / count) * i;
			Particle particle = new Particle(x - size / 2, y - size / 2, size, speed, angle, color, lifetime);
			particles.add(particle);
		}
	}
	
	/**
	 * Create particles
	 * @param angle
	 * @param spread
	 * @param count
	 * @param size
	 * @param movementSpeed
	 * @param color
	 * @param lifetime
	 */
	public void createDirectional(int angleStart, int spread, int count, int size, int speed, Color color, int lifetime) {
		for (int i = 0; i < count; i++) {
			int angle = angleStart + ((spread / count) * i);
			Particle particle = new Particle(x - size / 2, y - size / 2, size, speed, angle, color, lifetime);
			particles.add(particle);
		}
	}
	
	/**
	 * Update all particles
	 * remove off-screen and dead particles
	 */
	public void update() {
		for (int i = 0; i < particles.size(); i++) {
			Particle particle = particles.get(i);
			particle.update();
			if (particle.getX() < gc.getCamera().getOffset().x || particle.getY() < gc.getCamera().getOffset().y || particle.getX() > gc.getCamera().getOffset().x + gc.getWidth() || particle.getY() > gc.getCamera().getOffset().y + gc.getHeight()) {
				particles.remove(i);
			} else if (particle.getAlive() >= particle.getLifetime()) {
				particles.remove(i);
			}
		}
		
		particlesAlive = particles.size();
	}
	
	/**
	 * Render all particles
	 * @param graphics
	 */
	public void render(Graphics g) {
		for (int i = 0; i < particles.size(); i++) {
			Particle particle = particles.get(i);
			particle.render(g);
		}
	}
	
	/**
	 * Get the X position
	 * @return positionX
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Get the Y position
	 * @return positionY
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Set the X position
	 * @param positionX
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * Set the Y position
	 * @param positionY
	 */
	public void setY(int y) {
		this.y = y;
	}
}
