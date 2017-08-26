package me.sven.libz.image;

import java.awt.image.BufferedImage;
import java.io.File;

public class Sprite {

	public int x, y, w, h;
	public float size;
	public BufferedImage image;
	public String path;
	private int duration;

	/**
	 * Constructor, Create a sprite
	 * 
	 * @param path
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Sprite(String name, String path) { // Load from Jar
		image = SpriteLoader.load(path);
		size = -1;
		this.x = 0;
		this.y = 0;
		this.w = image.getWidth();
		this.h = image.getHeight();
		this.path = path;
		SpriteLoader.sprites.put(name.toLowerCase(), this);
	}

	/**
	 * Constructor, Create a sprite
	 * 
	 * @param file
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Sprite(String name, File file) { // Load from FileSystem
		image = SpriteLoader.load(file);
		size = file.length();
		this.x = 0;
		this.y = 0;
		this.w = image.getWidth();
		this.h = image.getHeight();
		this.path = file.getAbsolutePath();
		SpriteLoader.sprites.put(name.toLowerCase(), this);
	}

	/**
	 * Constructor, Create a sprite
	 * 
	 * @param image
	 * @param duration
	 */
	public Sprite(BufferedImage image, int duration) {
		this.image = image;
		this.duration = duration;
		this.x = 0;
		this.y = 0;
		this.w = image.getWidth();
		this.h = image.getHeight();
	}
	
	/**
	 * Constructor, Create a sprite
	 * 
	 * @param name
	 * @param image
	 * @param duration
	 */
	public Sprite(String name, BufferedImage image, int duration) {
		this.image = image;
		this.duration = duration;
		this.x = 0;
		this.y = 0;
		this.w = image.getWidth();
		this.h = image.getHeight();
		SpriteLoader.sprites.put(name, this);
	}

	/**
	 * Crop this sprite
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public void crop(int x, int y, int w, int h) {
		image = SpriteLoader.crop(image, x, y, w, h);
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	/**
	 * Crop the sprite and returning it as a new Sprite
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Sprite getCrop(int x, int y, int w, int h) {
		return new Sprite(SpriteLoader.crop(image, x, y, w, h), 0);
	}

	/**
	 * Resize this sprite
	 * 
	 * @param width
	 * @param height
	 */
	public void resize(int w, int h) {
		image = SpriteLoader.resize(image, w, h);
		this.w = w;
		this.h = h;
	}

	/**
	 * Get the frame duration in ms
	 * @return duration
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * Set the frame duration in ms
	 * @param duration
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}
}
