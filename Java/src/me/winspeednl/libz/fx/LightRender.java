package me.winspeednl.libz.fx;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import me.winspeednl.libz.core.GameCore;
import me.winspeednl.libz.graphics.Render;

/**
 * LightRender class.
 * This class renders all the lights
 * 
 * @author      Sven Arends <sarends98@gmail.com>
 * @version     1.0
 * @since       1.0
 */
public class LightRender {
	
	private BufferedImage lightMap;
	private ArrayList<Light> lights = new ArrayList<Light>();
	private Color ambient = new Color(0, 0, 0, 255);
	
	/**
	 * Constructor
	 * @param gc
	 */
	public LightRender(GameCore gc) {
		lightMap = new BufferedImage(gc.getWidth(), gc.getHeight(), BufferedImage.TYPE_INT_ARGB);
	}
	
	/**
	 * Update all lights
	 */
	public void update() {
		Graphics2D g2d = (Graphics2D) lightMap.getGraphics();
		
		g2d.setColor(ambient);
		g2d.fillRect(0, 0, lightMap.getWidth(), lightMap.getHeight());
		g2d.setComposite(AlphaComposite.DstOut);
		for (Light l : lights) {
			l.render(g2d);
		}
		g2d.dispose();
	}
	
	/**
	 * Render lightmap
	 * @param Render
	 */
	public void render(Render r) {
		r.getGraphics().drawImage(lightMap, 0, 0, null);
	}
	
	/**
	 * Set the ambient color
	 * @param color
	 */
	public void setAmbient(Color color) {
		this.ambient = color;
	}
	
	/**
	 * Get the ambient color
	 * @return color
	 */
	public Color getAmbient() {
		return ambient;
	}

	/**
	 * Add a new light
	 * @param light
	 * @return index
	 */
	public int addLight(Light light) {
		lights.add(light);
		return lights.indexOf(light);
	}
	
	/**
	 * Overwrite a existing light
	 * @param index
	 * @param light
	 */
	public void setLight(int index, Light light) {
		lights.set(index, light);
	}
	
	/**
	 * Get a light
	 * @param index
	 * @return Light
	 */
	public Light getLight(int index) {
		return lights.get(index);
	}
	
	/**
	 * Remove a light
	 * @param index
	 */
	public void removeLight(int index) {
		lights.remove(index);
	}
	
	/**
	 * Get all lights
	 * @return ArrayList Lights
	 */
	public ArrayList<Light> getLights() {
		return lights;
	}
}
