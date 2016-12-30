package me.winspeednl.libz.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * Image class.
 * Used by the Sprite class
 * 
 * @author      Sven Arends <sarends98@gmail.com>
 * @version     1.0
 * @since       0.2
 */
public class Image {
	
	public int width, height;
	public int x, y, w, h;
	public BufferedImage image;
	
	/**
	 * Constructor, Create a image
	 * @param path
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Image(String path, int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(Image.class.getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		width = image.getWidth();
		height = image.getHeight();
		this.image = image.getSubimage(x, y, w, h);
		image.flush();
	}
	
	/**
	 * Constructor, Create a image
	 * @param file
	 */
	public Image(File file) {
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		width = image.getWidth();
		height = image.getHeight();
		this.image = image;
		image.flush();
	}
}
