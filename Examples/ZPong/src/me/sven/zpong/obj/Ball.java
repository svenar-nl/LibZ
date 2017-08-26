package me.sven.zpong.obj;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import me.sven.libz.core.Settings;

public class Ball {
	
	private int x, y, size, speed, xDir, yDir;
	
	public Ball(int size, int speed, boolean startLeft) {
		this.size = size;
		this.speed = speed;
		
		reset(startLeft);
	}
	
	public void update() {
		if (xDir < 0) x -= speed;
		if (xDir > 0) x += speed;
		if (yDir < 0) y -= speed;
		if (yDir > 0) y += speed;
	}
	
	public void render(Graphics2D g) {
		g.fillOval(x - size / 2, y - size / 2, size, size);
	}
	
	public Point getPos() {
		return new Point(x + size / 2, y + size / 2);
	}
	
	public Rectangle getRect() {
		return new Rectangle(x, y, size, size);
	}
	
	public void goRight() {
		xDir = 1;
	}
	
	public void goLeft() {
		xDir = -1;
	}
	
	public void goUp() {
		yDir = -1;
	}
	
	public void goDown() {
		yDir = 1;
	}
	
	public boolean isOffscreenLeft() {
		return x < 0;
	}
	
	public boolean isOffscreenRight() {
		return x + size > Settings.width;
	}
	
	public void reset(boolean startLeft) {
		x = Settings.width / 2 - size / 2;
		y = Settings.height / 2 - size / 2;
		
		if (startLeft) xDir = -1;
		else xDir = 1;
		if (Math.random() > 0.5) yDir = 1;
		else yDir = -1;
	}

	public int getSize() {
		return size;
	}
}
