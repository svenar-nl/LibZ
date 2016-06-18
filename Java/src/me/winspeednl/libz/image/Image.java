package me.winspeednl.libz.image;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Image {
	
	public int width, height;
	public int[] imagePixels;
	
	public Image(String path) {
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(Image.class.getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		width = image.getWidth();
		height = image.getHeight();
		imagePixels = image.getRGB(0, 0, width, height, null, 0, width);
		
		image.flush();
	}

	public Image(int w, int h, int[] p) {
		this.width = w;
		this.height = h;
		this.imagePixels = p;
	}
}
