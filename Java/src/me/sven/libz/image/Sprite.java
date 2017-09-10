package me.sven.libz.image;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;

public class Sprite {

	public int x, y, cropX, cropY, w, h;
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
		this.cropX = 0;
		this.cropY = 0;
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
		this.cropX = 0;
		this.cropY = 0;
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
		this.cropX = 0;
		this.cropY = 0;
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
		this.cropX = x;
		this.cropY = y;
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
	 * 
	 * @return duration
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * Set the frame duration in ms
	 * 
	 * @param duration
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}

	/**
	 * Set the x position
	 * 
	 * @param x
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Set the y position
	 * 
	 * @param y
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * Get the x position
	 * 
	 * @return x
	 */
	public int getX() {
		return x;
	}

	/**
	 * Get the y position
	 * 
	 * @return y
	 */
	public int getY() {
		return y;
	}

	/**
	 * Set the x and y position
	 * 
	 * @param pos
	 */
	public void setPos(Point pos) {
		this.x = pos.x;
		this.y = pos.y;
	}

	/**
	 * Get the x and y position as a point
	 * 
	 * @return pos
	 */
	public Point getPos() {
		return new Point(x, y);
	}

	/**
	 * Effect: invert the colors of the image
	 */
	public void effect_invert() {
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				int rgba = image.getRGB(x, y);
				Color color = new Color(rgba, true);
				color = new Color(0xFF - color.getRed(), 0xFF - color.getGreen(), 0xFF - color.getBlue());
				image.setRGB(x, y, color.getRGB());
			}
		}
	}

	/**
	 * Effect: create monochrome image
	 * 
	 * @param threshold
	 */
	public void effect_monochrome(int threshold) {
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				int rgba = image.getRGB(x, y);
				Color color = new Color(rgba, true);
				if (color.getRed() + color.getGreen() + color.getBlue() > threshold)
					color = Color.WHITE;
				else
					color = Color.BLACK;
				image.setRGB(x, y, color.getRGB());
			}
		}
	}
	
	/**
	 * Blur the image
	 * @param amount
	 */
	public void effect_blur(int amount) { // TODO
		int radius = amount;
		int size = radius * 2 + 1;
		float weight = 1.0f / (size * size);
		float[] data = new float[size * size];

		for (int i = 0; i < data.length; i++) {
			data[i] = weight;
		}

		Kernel kernel = new Kernel(size, size, data);
		ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
		image = op.filter(image, null);
		
	}
	
	/**
	 * Create a trapezoid of a image
	 * @param amountX
	 * @param amountY
	 */
	public void effect_trapezoid(float amountX, float amountY) {
		if (amountX < 0 || amountX > 1 || amountY < 0 || amountY > 1)
			return;
		int w = image.getWidth();
		int h = image.getHeight();
		float A = w * amountX;
		float B = w * (1 - amountY);

		int[] pixels = new int[h * w];
		int[] new_pixels = new int[h * w];

		pixels = (int[]) image.getRaster().getDataElements(0, 0, w, h, null);
		for (int y = 0; y < h; y++) {
			int yw = y * w;

			final float y_to_h = (float) y / (float) h;
			final float C_A_offset = A * (1 - y_to_h);
			final float trapeziumLine = -C_A_offset + B + (w - B) * y_to_h;
			final float k = trapeziumLine / w;

			for (int x = 0; x < w; x++) {
				int destX = Math.min(w - 1, Math.round(C_A_offset + x * k));
				new_pixels[yw + destX] = pixels[yw + x];

			}
		}
		image.getRaster().setDataElements(0, 0, w, h, new_pixels);
	}
}
