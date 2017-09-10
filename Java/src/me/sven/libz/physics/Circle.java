package me.sven.libz.physics;

public class Circle {
	
	private double x, y, r;
	
	/**
	 * Create new Circle
	 * @param x
	 * @param y
	 * @param r
	 */
	public Circle(double x, double y, double r) {
		this.x = x;
		this.y = y;
		this.r = r;
	}
	
	/**
	 * Set the x position
	 * 
	 * @param x
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * Set the y position
	 * 
	 * @param y
	 */
	public void setY(double y) {
		this.y = y;
	}
	
	/**
	 * Set the radius
	 * 
	 * @param radius
	 */
	public void setRadius(double radius) {
		this.r = radius;
	}
	

	/**
	 * Get the x position
	 * 
	 * @return x
	 */
	public double getX() {
		return x;
	}

	/**
	 * Get the y position
	 * 
	 * @return y
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * Get the radius
	 * 
	 * @return radius
	 */
	public double getRadius() {
		return r;
	}
}
