package me.winspeednl.libz.gui;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import me.winspeednl.libz.core.GameCore;
import me.winspeednl.libz.screen.Font;
import me.winspeednl.libz.screen.Render;

public abstract class Menu {
	private int[] pixels;
	private int backgroundColor = 0xFF000000;
	private ArrayList<String> items = new ArrayList<String>();
	private String guideText = "";
	private int selected = 0;
	private Font font = Font.STANDARDX4;
	private boolean keyPressed = false, useArrowKeys = false, useMouse = false;
	
	public Menu(GameCore gc, ArrayList<String> items) {
		this.items = items;
		pixels = new int[gc.getWidth() * gc.getHeight()];
	}
	
	public void update(GameCore gc) {
		if (useArrowKeys) {
			if (!keyPressed) {
				if (gc.getInput().isKeyPressed(KeyEvent.VK_DOWN)) {
					if (selected < items.size() - 1)
						selected++;
					else
						selected = 0;
				}
				if (gc.getInput().isKeyPressed(KeyEvent.VK_UP)) {
					if (selected > 0)
						selected--;
					else
						selected = items.size() - 1;
				}
				
				if (gc.getInput().isKeyPressed(KeyEvent.VK_ENTER)) {
					optionSelected(items.get(selected).toLowerCase(), selected);
				}
			}
			keyPressed = gc.getInput().isKeyPressed(KeyEvent.VK_UP) || gc.getInput().isKeyPressed(KeyEvent.VK_DOWN) || gc.getInput().isKeyPressed(KeyEvent.VK_ENTER);
		}
		if (useMouse) {
			
		}
	}
	
	public void arrowKeys(boolean bool) {
		useArrowKeys = bool;
	}
	
	public void mouse(boolean bool) {
		useMouse = bool;
	}
	
	public void render(GameCore gc, Render r) {
		for (int i = 0; i < pixels.length; i++)
			pixels[i] = backgroundColor;
		
		for (int x = 0; x < gc.getWidth(); x++)
			for (int y = 0; y < gc.getHeight(); y++)
				r.setPixel(x, y, pixels[x + y * gc.getWidth()]);
		
		for (int i = 0; i < items.size(); i++) {
			String item = items.get(i);
			if (i != selected)
				r.drawString(item, 0xFFFFFFFF, gc.getWidth() / 2 - r.getStringWidth(item, font) / 2, 500 + (48 * i), font);
			else
				r.drawString(">" + item + "<", 0xFFFFFFFF, gc.getWidth() / 2 - r.getStringWidth(">" + item + "<", font) / 2, 500 + (48 * i), font);
		}
		
		r.drawString(guideText, 0xFFFFFFFF, 10, gc.getHeight() - 30, font);
	}
	
	public abstract void optionSelected(String name, int id);
}
