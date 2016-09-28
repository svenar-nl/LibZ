package me.winspeednl.libz.screen;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import me.winspeednl.libz.core.GameCore;
import me.winspeednl.libz.image.Image;
import me.winspeednl.libz.image.Sprite;

public class Render {
	
	private int width, height;
	private int[] pixels, overlayPixels;
	private GameCore gc;
	private int offsetX, offsetY;
	private boolean translate = true;
	
	public Render(GameCore gc) {
		this.gc = gc;
		this.width = gc.getWidth();
		this.height = gc.getHeight();
		
		pixels = ((DataBufferInt) gc.getScreen().getImage().getRaster().getDataBuffer()).getData();
		overlayPixels = new int[pixels.length];
	}
	
	public void setPixel(int x, int y, int color) {
		if (translate) {
			x -= offsetX;
			y -= offsetY;
		}
		
		if ((x < 0 || x >= width || y < 0 || y >= height) || color == gc.getSpriteBGColor())
			return;

		pixels[x + y * width] = color;
	}
	
	public void setOverlayPixel(int x, int y, int color) {
		if ((x < 0 || x >= width || y < 0 || y >= height) || color == gc.getSpriteBGColor())
			return;

		getOverlayPixels()[x + y * width] = color;
	}

	public void setPixel(int pixel, int color) {
		pixels[pixel] = color;
	}
	
	public void drawString(String text, int color, int offX, int offY){
		drawString(text, color, offX, offY, Font.STANDARD);
	}
		
	public void drawString(String text, int color, int offX, int offY, String path){
		final int NumberUnicodes = 59;
		int[] offsets = new int[NumberUnicodes];
		int[] widths = new int[NumberUnicodes];
		Image image;
		
		image = new Image(path);
		int unicode = -1;
		for(int x = 0; x < image.width; x++) {
			int Color = image.imagePixels[x];
			if(Color == 0xff0000ff) {
				unicode++;
				offsets[unicode] = x;
			}
			if(Color == 0xffffff00)
				widths[unicode] = x - offsets[unicode];
		}
		
		text = text.toUpperCase();

		int offset = 0;
		for (int i = 0; i < text.length(); i++) {
			int Unicode = text.codePointAt(i) - 32;

			for (int x = 0; x < widths[Unicode]; x++) {
				for (int y = 1; y < image.height; y++) {
					if (image.imagePixels[(x + offsets[Unicode]) + y * image.width] == 0xFFFFFFFF)
						setOverlayPixel(x + offX + offset, y + offY - 1, color);
				}
			}

			offset += widths[Unicode];
		}
	}
	
	public void drawString(String text, int color, int offX, int offY, Sprite sprite){
		final int NumberUnicodes = 59;
		int[] offsets = new int[NumberUnicodes];
		int[] widths = new int[NumberUnicodes];
		
		int unicode = -1;
		for(int x = 0; x < sprite.w; x++) {
			int Color = sprite.pixels[x];
			if(Color == 0xff0000ff) {
				unicode++;
				offsets[unicode] = x;
			}
			if(Color == 0xffffff00)
				widths[unicode] = x - offsets[unicode];
		}
		
		text = text.toUpperCase();

		int offset = 0;
		for (int i = 0; i < text.length(); i++) {
			int Unicode = text.codePointAt(i) - 32;

			for (int x = 0; x < widths[Unicode]; x++) {
				for (int y = 1; y < sprite.h; y++) {
					if (sprite.pixels[(x + offsets[Unicode]) + y * sprite.w] == 0xFFFFFFFF)
						setOverlayPixel(x + offX + offset, y + offY - 1, color);
				}
			}

			offset += widths[Unicode];
		}
	}
	
	public void drawString(String text, int color, int offX, int offY, Font font){
		text = text.toUpperCase();

		int offset = 0;
		for (int i = 0; i < text.length(); i++) {
			int unicode = text.codePointAt(i) - 32;

			for (int x = 0; x < font.widths[unicode]; x++) {
				for (int y = 1; y < font.image.height; y++) {
					if (font.image.imagePixels[(x + font.offsets[unicode]) + y * font.image.width] == 0xFFFFFFFF)
						setOverlayPixel(x + offX + offset, y + offY - 1, color);
				}
			}

			offset += font.widths[unicode];
		}
	}
	
	public int getStringWidth(String text, Font font) {
		text = text.toUpperCase();
		int width = 0;
		for (int i = 0; i < text.length(); i++) {
			int unicode = text.codePointAt(i) - 32;
			width += font.widths[unicode];
		}
		return width;
	}
	
	public int getStringWidth(String text, Sprite sprite) {
		final int NumberUnicodes = 59;
		int[] offsets = new int[NumberUnicodes];
		int[] widths = new int[NumberUnicodes];
		
		int unicode = -1;
		for(int x = 0; x < sprite.w; x++) {
			int Color = sprite.pixels[x];
			if(Color == 0xff0000ff) {
				unicode++;
				offsets[unicode] = x;
			}
			if(Color == 0xffffff00)
				widths[unicode] = x - offsets[unicode];
		}
		
		text = text.toUpperCase();
		int width = 0;
		for (int i = 0; i < text.length(); i++) {
			int Unicode = text.codePointAt(i) - 32;
			width += widths[Unicode];
		}
		return width;
	}
	
	public void drawRect(int x, int y, int w, int h, int color, int thickness, boolean fill) {
		if (fill) {
			for (int xi = 0; xi < w; xi++) {
				for (int yi = 0; yi < h; yi++) {
					setPixel(x + xi, y + yi, color);
				}
			}
		} else {
			for (int i = 0; i < thickness; i++) {
				for (int xi = 0; xi < w; xi++) {
					setPixel(x + xi, y + i, color);
					setPixel(x + xi, y + h - i, color);
				}
				
				for (int yi = 0; yi < h; yi++) {
					setPixel(x + i, y + yi, color);
					setPixel(x + w - i, y + yi, color);
				}
			}
		}
	}
	
	public void drawOverlayRect(int x, int y, int w, int h, int color, int thickness, boolean fill) {
		if (fill) {
			for (int xi = 0; xi < w; xi++) {
				for (int yi = 0; yi < h; yi++) {
					setOverlayPixel(x + xi, y + yi, color);
				}
			}
		} else {
			for (int i = 0; i < thickness; i++) {
				for (int xi = 0; xi < w; xi++) {
					setOverlayPixel(x + xi, y + i, color);
					setOverlayPixel(x + xi, y + h - i, color);
				}
				
				for (int yi = 0; yi < h; yi++) {
					setOverlayPixel(x + i, y + yi, color);
					setOverlayPixel(x + w - i, y + yi, color);
				}
			}
		}
	}

	public void clear() {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				pixels[x + y * width] = 0xFF000000;
				overlayPixels[x + y * width] = gc.getSpriteBGColor();
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
	
	public BufferedImage flip(BufferedImage inputImage, boolean horizontal, boolean vertical) {
		BufferedImage image = inputImage;
		AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
		AffineTransformOp op;
		
		if (horizontal && !vertical) {
			tx = AffineTransform.getScaleInstance(-1, 1);
			tx.translate(-image.getWidth(null), 0);
			op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			image = op.filter(image, null);
		} else if (!horizontal && vertical) {
			tx.translate(0, -image.getHeight(null));
			op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			image = op.filter(image, null);
		} else {
			tx = AffineTransform.getScaleInstance(-1, -1);
			tx.translate(-image.getWidth(null), -image.getHeight(null));
			op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			image = op.filter(image, null);
		}
		
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
