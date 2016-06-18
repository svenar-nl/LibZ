package me.winspeednl.libz.screen;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import me.winspeednl.libz.core.GameCore;

public class Render {
	
	private int width, height;
	private int[] pixels, overlayPixels;
	private GameCore gc;
	private Font font = Font.STANDARD;
	private int offsetX, offsetY;
	private boolean translate = true;
	
	public Render(GameCore gc) {
		this.gc = gc;
		this.width = gc.getWidth();
		this.height = gc.getHeight();
		
		pixels = ((DataBufferInt) gc.getWindow().getImage().getRaster().getDataBuffer()).getData();
		overlayPixels = new int[pixels.length];
	}
	
	public void setPixel(int x, int y, int color) {
		if (translate) {
			x -= offsetX;
			y -= offsetY;
		}
		
		if ((x < 0 || x >= width || y < 0 || y >= height) || color == gc.getSpriteBGColor())// || colorDistance(0xFFFF00FF, color) < 300)
			return;

		pixels[x + y * width] = color;
	}
	

	public void setPixel(int pixel, int color) {
		pixels[pixel] = color;
	}
	
	public void setOverlayPixel(int x, int y, int color) {
		//if (translate) {
		//	x -= offsetX;
		//	y -= offsetY;
		//}
		
		if ((x < 0 || x >= width || y < 0 || y >= height) || color == gc.getSpriteBGColor())// || colorDistance(0xFFFF00FF, color) < 300)
			return;

		getOverlayPixels()[x + y * width] = color;
	}
	
	public void drawString(String text, int color, int offX, int offY){
		text = text.toUpperCase();

		int offset = 0;
		for (int i = 0; i < text.length(); i++) {
			int unicode = text.codePointAt(i) - 32;

			for (int x = 0; x < font.widths[unicode]; x++) {
				for (int y = 1; y < font.image.height; y++) {
					if (font.image.imagePixels[(x + font.offsets[unicode]) + y * font.image.width] == 0xffffffff)
						setOverlayPixel(x + offX + offset, y + offY - 1, color);
				}
			}

			offset += font.widths[unicode];
		}
	}

	public void clear() {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				pixels[x + y * width] = 0xFF000000;
				getOverlayPixels()[x + y * width] = 0xFF000000;
			}
		}
	}
	
	public BufferedImage rotate(BufferedImage img, double degree) {
		AffineTransform tx = new AffineTransform();
        tx.rotate(degree,img.getWidth() / 2, img.getHeight() / 2);

        AffineTransformOp op = new AffineTransformOp(tx,AffineTransformOp.TYPE_BILINEAR);
        BufferedImage image = op.filter(img,null);
        return image;
	}
	
	public BufferedImage getImageFromArray(int[] pixels, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        image.setRGB(0, 0, width, height, pixels, 0, width);
        return image;
    }

	public int getOffsetX() {
		return offsetX;
	}

	public void setOffsetX(int offsetX) {
		this.offsetX = offsetX;
	}

	public int getOffsetY() {
		return offsetY;
	}

	public void setOffsetY(int offsetY) {
		this.offsetY = offsetY;
	}

	public int[] getPixels() {
		return pixels;
	}
	
	public int[] getOverlayPixels() {
		return overlayPixels;
	}
}
