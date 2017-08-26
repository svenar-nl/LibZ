package me.sven.zpong.scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import me.sven.libz.core.GameCore;
import me.sven.libz.graphics.Render;
import me.sven.libz.input.Key;
import me.sven.libz.screen.Scene;

public class AboutScene extends Scene {
	
	private Key escKey;
	private ArrayList<String> lines;
	
	public AboutScene(GameCore gc, String sceneName) {
		super(gc, sceneName);
	}

	public void init(GameCore gc) {
		escKey = new Key(gc, KeyEvent.VK_ESCAPE);
		lines = new ArrayList<String>();
		
		lines.add("Press 'Escape' to go back.");
		lines.add("");
		lines.add("ZPong - LibZ Demo Game");
		lines.add("");
		lines.add("This is a demo game made using LibZ");
		lines.add("it's a remake of the game Pong from 1972 with some extra features");
		lines.add("");
		lines.add("LibZ is a game library written in Java from scratch");
	}

	public void update(GameCore gc) {
		if (escKey.isPressed()) gc.setPreviousScene();
	}

	public void render(GameCore gc, Render r) {
		Graphics2D g = (Graphics2D) r.getGraphics();
		g.setColor(Color.WHITE);
		Font font = new Font(Font.MONOSPACED, Font.BOLD, 25);
		g.setFont(font);
		for (int i = 0; i < lines.size(); i++) {
			String line = lines.get(i);
			g.drawString(line, 20, g.getFontMetrics().getHeight() + (i * g.getFontMetrics().getHeight()));
		}
	}
}
