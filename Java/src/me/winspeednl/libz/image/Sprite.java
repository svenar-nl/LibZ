package me.winspeednl.libz.image;

public class Sprite extends Image {
	
	public int x, y, w, h;
	public int[] pixels;
	public String path;
	
	public Sprite(String path, int x, int y, int w, int h) {
		super(path);
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.path = path;
				
		this.pixels = imagePixels;
	}
}
