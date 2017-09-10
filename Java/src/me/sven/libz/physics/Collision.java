package me.sven.libz.physics;

import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Area;

public class Collision {

	public static boolean shapeCollision(Shape shapeA, Shape shapeB) {
		Area areaA = new Area(shapeA);
		areaA.intersect(new Area(shapeB));
		return !areaA.isEmpty();
	}

	public static boolean recangleCollision(Rectangle rectA, Rectangle rectB) {
		return rectA.intersects(rectB);
	}

	public static boolean circleCollision(Circle circleA, Circle circleB) {
		double r = circleA.getRadius() + circleB.getRadius();
		double dx = circleA.getX() - circleB.getX();
		double dy = circleA.getY() - circleB.getY();
		boolean collides = (r * r) > (dx * dx + dy * dy);
		return collides;
	}
}
