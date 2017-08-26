package me.sven.libz.image;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class SpriteLoader {
	
	public static HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();
	
	public static BufferedImage load(String path) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(SpriteLoader.class.getResourceAsStream(path));
		} catch (IOException e) {
			System.out.println("Could not load image: " + path);
		}
		return image;
	}

	public static BufferedImage load(File file) {
		BufferedImage image = null;
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
	
	public static BufferedImage crop(BufferedImage image, int x, int y, int w, int h) {
		return image.getSubimage(x, y, w, h);
	}

	public static BufferedImage resize(BufferedImage image, int w, int h) {
		BufferedImage newImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

		Graphics g = newImage.createGraphics();
		g.drawImage(image, 0, 0, w, h, null);
		g.dispose();
		
		return newImage;
	}
}
