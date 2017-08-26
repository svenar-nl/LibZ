package me.sven.zpong.obj;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import me.sven.libz.core.GameCore;
import me.sven.libz.core.Settings;
import me.sven.libz.input.Mouse;

public class Paddle {
	
	private boolean isLeft, AI;
	private int x, y, w, h, speed, points;
	private Mouse mouse;
	
	public Paddle(GameCore gc, boolean isLeft, boolean AI, int width, int height, int speed, int margin) {
		this.isLeft = isLeft;
		this.AI = AI;
		this.speed = speed;
		if (!AI) mouse = new Mouse(gc);
		this.w = width;
		this.h = height;
		if (isLeft) x = margin;
		else x = Settings.width - width - margin;
		y = Settings.height / 2 - height / 2;
	}
	
	public void update(Point ballPos) {
		if (AI) {
			if (y + h / 2 > ballPos.y) y -= speed;
			if (y + h / 2 < ballPos.y) y += speed;
		} else {
			if (y + h / 2 > mouse.getY()) y -= speed;
			if (y + h / 2 < mouse.getY()) y += speed;
		}
	}
	
	public void render(Graphics2D g) {
		if (!AI) g.fillOval(mouse.getX() - 5, mouse.getY() - 5, 10, 10);
		g.fillRect(x, y, w, h);
	}
	
	public Rectangle getRect() {
		return new Rectangle(x, y, w, h);
	}
	
	public boolean isLeft() {
		return isLeft;
	}
	
	public void addPoint() {
		points++;
	}
	
	public int getPoints() {
		return points;
	}
}
