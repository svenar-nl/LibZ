package me.sven.libz.fx;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import me.sven.libz.graphics.Render;
import me.sven.libz.util.Vector2d;

/*
 * @author      Sven Arends <sarends98@gmail.com>
 * @version     1.0
 * @since       0.7
 * Thanks to: stumpygames.wordpress.com
 */
public class Particle {
	
	public int SQUARE = 0, CIRCLE = 1, IMAGE = 2;
	public String type = "";
	private Vector2d loc;
	private Vector2d vel;
	private Vector2d acc;
	private Vector2d size;
	private Vector2d maxSize;
	private Vector2d growth;
	private int life;
	private Color color;
	private int shape = SQUARE;
	private BufferedImage image = null;
	//private double rot, currRot; // Only image particles TODO
	
	private boolean ultSize = false;
	private boolean defaultSize = false;
	
	public Particle(double x, double y, double dx, double dy, double size, int life, Color c){
		this.loc = new Vector2d(x, y);
		this.vel = new Vector2d(dx, dy);
		this.acc = new Vector2d(0, 0);
		this.life = life;
		this.size = new Vector2d(size, size);
		this.growth = new Vector2d(0, 0);
		this.maxSize = new Vector2d(size, size);
		this.color = c;
	}

	public boolean update(){
		vel.add(acc);
		loc.add(vel);
		size.add(growth);
		life--;
		
		if (life <= 0) return true;
		
		if(defaultSize) {
			if(size.x >= maxSize.x) {
				if(size.y >= maxSize.y) return true;
				else size.x = maxSize.x;
			}
			if(size.y >= maxSize.y) size.y = maxSize.y;
			if(size.x <= 0)
				if(size.y <= 0) return true;
				else size.x = 1;
			if(size.y <= 0) size.y = 1;
			return false;
		}
		
		//currRot += rot;
	
		if(ultSize) {
			if(size.x > maxSize.x){
				size.x = maxSize.x;
				growth.x *= -1;
			}
			if(size.y > maxSize.y){
				size.y = maxSize.y;
				growth.y *= -1;
			}
			if(size.x <= 0){
				size.x = 1;
				growth.x *= -1;
			}
			if(size.y <= 0){
				size.y = 1;
				growth.y *= -1;
			}
		} else {
			if(size.x > maxSize.x) size.x = maxSize.x;
			if(size.y > maxSize.y) size.y = maxSize.y;
			if(size.x <= 0) size.x = 1;
			if(size.y <= 0) size.y = 1;
		}
		
		return false;
	}

	public void render(Render r){
		Graphics2D g = (Graphics2D) r.getGraphics();
		
		g.setColor(color);
		if (shape == SQUARE) g.fillRect( (int)(loc.x - (size.x / 2)), (int)(loc.y - (size.y / 2)), (int)size.x, (int)size.y);
		if (shape == CIRCLE) g.fillOval( (int)(loc.x - (size.x / 2)), (int)(loc.y - (size.y / 2)), (int)size.x, (int)size.y);
		if (shape == IMAGE && image != null && size.x > 0 && size.y > 0) {
			image = scaleImage(image, (int)size.x, (int)size.y);
			//AffineTransform old = g.getTransform();
			//g.setTransform(AffineTransform.getRotateInstance(Math.toRadians(currRot), loc.x, loc.y));
			g.drawImage(image, (int)(loc.x - (size.x / 2)), (int)(loc.y - (size.y / 2)), null);
			//g.setTransform(old);
		}
	}
	
	public BufferedImage scaleImage(BufferedImage img, int width, int height) {
	    int imgWidth = img.getWidth();
	    int imgHeight = img.getHeight();
	    if (imgWidth * height < imgHeight * width) {
	        width = imgWidth * height / imgHeight;
	    } else {
	        height = imgHeight * width / imgWidth;
	    }
	    if (width < 1 || height < 1) return img;
	    BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g = newImage.createGraphics();
	    try {
	        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
	        g.drawImage(img, 0, 0, width, height, null);
	    } finally {
	        g.dispose();
	    }
	    
	    return newImage;
	}
	
	public void setShape(int shape) {
		this.shape = shape;
	}
	
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	public void setLoc(double x,  double y){
		loc.x = x;
		loc.y = y;
	}

	public void setVel(double x,  double y){
		vel.x = x;
		vel.y = y;
	}

	public void setAcc(double x,  double y){
		acc.x = x;
		acc.y = y;
	}

	public void setSize(double x,  double y){
		size.x = x;
		size.y = y;
	}

	public void setMaxSize(double x,  double y){
		maxSize.x = x;
		maxSize.y = y;
	}

	public void setGrowth(double x,  double y){
		growth.x = x;
		growth.y = y;
	}

	public void setLife(double num){
		life = (int) num;
	}

	public void setSizeDeault(boolean c){
		defaultSize = c;
	}

	public void setUltSize(boolean c){
		defaultSize = false;
		ultSize = c;
	}

	public Vector2d getLoc(){
		return loc;
	}

	public Vector2d getVel(){
		return vel;
	}

	//public void setRotation(double rot) {
	//	this.rot = rot;
	//}
}
