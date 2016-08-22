package me.winspeednl.libz.particles;

import java.util.ArrayList;
import java.util.Random;

import me.winspeednl.libz.core.GameCore;
import me.winspeednl.libz.screen.Render;

public class Emitter {
	private GameCore gc;
	
	private ArrayList<Particle> particles = new ArrayList<Particle>();
	private int x, y;
	public int particlesAlive = 0;
	
	public Emitter(GameCore gc, int x, int y) {
		this.gc = gc;
		this.x = x;
		this.y = y;
	}
	
	public void createRandomParticles(int count, int width, int height, int speed, int color, int lifetime) {
		for (int i = 0; i < count; i++) {
			int direction = new Random().nextInt(359);
			lifetime += new Random().nextInt(10);
			int newSpeed = speed - 2 - new Random().nextInt(2);
			Particle particle = new Particle(x - width / 2, y - height / 2, width, height, newSpeed, direction, color, lifetime);
			particles.add(particle);
		}
		particlesAlive += count;
	}
	
	public void createDirectionalParticles(int count, int width, int height, int speed, int color, int lifetime, int direction) {
		for (int i = 0; i < count; i++) {
			direction += 5 - new Random().nextInt(10);
			lifetime += new Random().nextInt(10);
			Particle particle = new Particle(x - width / 2, y - height / 2, width, height, speed, direction, color, lifetime);
			particles.add(particle);
		}
		particlesAlive += count;
	}
	
	public void update() {
		for (int i = 0; i < particles.size(); i++) {
			Particle particle = particles.get(i);
			if (particle.getX() < 0 || particle.getY() < 0 || particle.getX() > gc.getWidth() || particle.getY() > gc.getHeight()) {
				particles.remove(i);
				particlesAlive--;
			}
			particle.update();
			if (particle.getAlive() >= particle.getLifetime()) {
				particles.remove(i);
				particlesAlive--;
			}
		}
	}
	
	public void render(Render r) {
		for (int i = 0; i < particles.size(); i++) {
			Particle particle = particles.get(i);
			particle.render(r);
		}
	}
}
