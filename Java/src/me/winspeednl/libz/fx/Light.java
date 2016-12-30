package me.winspeednl.libz.fx;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Light class
 * A single light
 * 
 * @author      Sven Arends <sarends98@gmail.com>
 * @version     1.0
 * @since       1.0
 */
public class Light {
	private BufferedImage image;

	public int x, y, radius, luminosity, quality = 4;
	
	/**
	 * Cunstructor
	 * @param x
	 * @param y
	 * @param radius
	 * @param luminosity
	 */
	public Light(int x, int y, int radius, int luminosity) {
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.luminosity = luminosity;

		regenerate();
	}
	
	/**
	 * Create the light image
	 */
	private void regenerate() {
		image = new BufferedImage(radius * 2, radius * 2, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) image.getGraphics();

		int numSteps = radius / quality;
		g2d.setColor(new Color(0, 0, 0, luminosity));
		for (int i = 0; i < numSteps; i++) {
			g2d.fillOval(radius - i * quality, radius - i * quality, i * quality * 2, i * quality * 2);
		}
	}

	/**
	 * Render the light
	 * @param g2d
	 */
	public void render(Graphics2D g2d) {
		g2d.drawImage(image, x, y, null);
	}
}
