package me.winspeednl.libz.image;

import java.io.File;

/**
 * Sprite class.
 * Use this to load images and render using the Render class
 * 
 * @author      Sven Arends <sarends98@gmail.com>
 * @version     1.0
 * @since       0.2
 */
public class Sprite extends Image {
	
	public String path;
	
	/**
	 * Constructor, Create a sprite
	 * @param path
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Sprite(String path, int x, int y, int w, int h) { // Load from Jar
		super(path, x, y, w, h);
		this.path = path;
	}
	
	/**
	 * Constructor, Create a sprite
	 * @param file
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Sprite(File file, int x, int y, int w, int h) { // Load from FileSystem
		super(file);
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.path = file.getAbsolutePath();
	}
	
}
