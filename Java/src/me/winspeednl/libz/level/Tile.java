package me.winspeednl.libz.level;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;

import me.winspeednl.libz.image.Sprite;

public class Tile {
	
	private int x, y, w, h;
	double rot;
	private boolean isSolid, flipX = false, flipY = false;
	private int[] pixels;
	private Sprite sprite;
	
	public Tile(String path, int x, int y, int w, int h, boolean isSolid, double rot, boolean flipX, boolean flipY) {
		this.sprite = new Sprite(path, 0, 0, w, h);
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.isSolid = isSolid;
		this.rot = rot;
		this.flipX = flipX;
		this.flipY = flipY;
		
		pixels = sprite.pixels;
	}
	public Tile(Sprite sprite, int x, int y, int w, int h, boolean isSolid, double rot, boolean flipX, boolean flipY) {
		this.sprite = sprite;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.isSolid = isSolid;
		this.rot = rot;
		this.flipX = flipX;
		this.flipY = flipY;
		
		pixels = sprite.pixels;
	}
	public Tile(File file, int x, int y, int w, int h, boolean isSolid, double rot, boolean flipX, boolean flipY) {
		this.sprite = new Sprite(file, 0, 0, w, h);
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.isSolid = isSolid;
		this.rot = rot;
		this.flipX = flipX;
		this.flipY = flipY;
		
		pixels = sprite.pixels;
	}
	
	public Tile(String path, int x, int y, int w, int h, boolean isSolid) {
		this.sprite = new Sprite(path, 0, 0, w, h);
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.isSolid = isSolid;
		this.rot = 0.0;
		this.flipX = false;
		this.flipY = false;
		
		pixels = sprite.pixels;
	}
	
	public Tile(Sprite sprite, int x, int y, int w, int h, boolean isSolid) {
		this.sprite = sprite;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.isSolid = isSolid;
		this.rot = 0.0;
		this.flipX = false;
		this.flipY = false;
		
		pixels = sprite.pixels;
	}
	public Tile(File file, int x, int y, int w, int h, boolean isSolid) {
		this.sprite = new Sprite(file, 0, 0, w, h);
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.isSolid = isSolid;
		this.rot = 0.0;
		this.flipX = false;
		this.flipY = false;
		
		pixels = sprite.pixels;
	}
	
	public int[] rotate(double rot) {
		BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		img.setRGB(0, 0, w, h, pixels, 0, w);
        
		AffineTransform tx = new AffineTransform();
        tx.rotate(rot,img.getWidth() / 2, img.getHeight() / 2);

        AffineTransformOp op = new AffineTransformOp(tx,AffineTransformOp.TYPE_BILINEAR);
        BufferedImage image = op.filter(img,null);
        return image.getRGB(0, 0, w, h, null, 0, w);
	}
	
	public void flip(boolean x, boolean y) {
		flipX = x;
		flipY = y;
	}
	
	public int[] flip() {
		BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		img.setRGB(0, 0, w, h, pixels, 0, w);
		
		AffineTransformOp op = null;
		if (flipX && !flipY) {
			AffineTransform tx;
			tx = AffineTransform.getScaleInstance(-1, 1);
			tx.translate(-img.getWidth(), 0);
			op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		}
		
		if (!flipX && flipY) {
			AffineTransform tx;
			tx = AffineTransform.getScaleInstance(1, -1);
			tx.translate(0, -img.getHeight());
			op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		}
		
		if (flipX && flipY) {
			AffineTransform tx;
			tx = AffineTransform.getScaleInstance(-1, -1);
			tx.translate(-img.getWidth(), -img.getHeight());
			op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		}
		
		int[] newPixels = null;
		if (flipX || flipY) {
			BufferedImage image = op.filter(img,null);
			newPixels = image.getRGB(0, 0, w, h, null, 0, w);
			for(int i = 0; i < newPixels.length; i++) {
				newPixels[i] = 0xFF000000;
			}
		}
		return newPixels;
	}
	
	public int[] flip(int[] pixels) {
		int[] newPixels = pixels;
		for (int x = 0; x < getWidth(); x++) {
			for (int y = 0; y < getHeight(); y++) {
				int xs = x, ys = y;
				if (flipX)
					xs = getWidth() - x - 1;
				if (flipY)
					ys = getHeight() - y - 1;
				
				newPixels[xs + ys * getWidth()] = pixels[x + y * getWidth()];
			}
		}
		
		return newPixels;
	}
	
	public double getRotation() {
		return Math.toDegrees(rot);
	}
	
	public double getRotationRadians() {
		return rot;
	}

	public int[] getPixels() {
		return pixels;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return w;
	}

	public void setWidth(int w) {
		this.w = w;
	}

	public int getHeight() {
		return h;
	}

	public void setHeight(int h) {
		this.h = h;
	}

	public boolean isSolid() {
		return isSolid;
	}

	public void setSolid(boolean isSolid) {
		this.isSolid = isSolid;
	}

	public Sprite getSprite() {
		return sprite;
	}
	
	public void setSprite(String path) {
		this.sprite = new Sprite(path, 0, 0, w, h);
		pixels = sprite.pixels;
	}
	
	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
		pixels = sprite.pixels;
	}
	
	public boolean isFlippedX() {
		return flipX;
	}

	public boolean isFlippedY() {
		return flipY;
	}
}
