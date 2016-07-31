package me.winspeednl.zpong;

import java.awt.Rectangle;

public class Collision {
	
	public boolean collides (int x, int y, int w, int h, int x1, int y1, int w1, int h1) {
		Rectangle object1 = new Rectangle(x, y, w, h);
		Rectangle object2 = new Rectangle(x1, y1, w1, h1);
		return object1.intersects(object2);
	}
}
