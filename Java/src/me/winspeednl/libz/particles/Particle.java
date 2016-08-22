package me.winspeednl.libz.particles;

import me.winspeednl.libz.screen.Render;

public class Particle {
	private int x, y, w, h, speed, direction, color, alive, lifetime;
	
	public Particle(int x, int y, int w, int h, int speed, int direction, int color, int lifetime) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.speed = speed;
		this.direction = direction;
		this.color = color;
		this.lifetime = lifetime;
	}
	
	public void update() {
		x += speed * Math.cos(Math.toRadians(direction));
		y += speed * Math.sin(Math.toRadians(direction));
		alive++;
	}
	
	public void render(Render r) {
		r.drawRect(x, y, w, h, color, 0, true);
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getAlive() {
		return alive;
	}

	public int getLifetime() {
		return lifetime;
	}
}
