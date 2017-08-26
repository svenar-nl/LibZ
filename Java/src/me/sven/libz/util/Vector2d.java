package me.sven.libz.util;

public class Vector2d {
	
	public double x, y;
	
	public Vector2d(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void add(double num) {
		x += num;
		y += num;
	}
	
	public void sub(double num) {
		x -= num;
		y -= num;
	}
	
	public void div(double num) {
		x /= num;
		y /= num;
	}
	
	public void mult(double num) {
		x *= num;
		y *= num;
	}
	
	public void add(Vector2d vec) {
		x += vec.x;
		y += vec.y;
	}
	
	public void sub(Vector2d vec) {
		x -= vec.x;
		y -= vec.y;
	}
	
	public void div(Vector2d vec) {
		x /= vec.x;
		y /= vec.y;
	}
	
	public void mult(Vector2d vec) {
		x *= vec.x;
		y *= vec.y;
	}
}
