package me.sven.libz.screen;

import java.awt.Color;

import me.sven.libz.core.GameCore;

public abstract class MenuItem {
	boolean mouseOver;
	String name;
	GameCore gc;
	Color color = Color.WHITE;
	int x, y, w, h;
	
	public MenuItem(GameCore gc, String name) {
		this.name = name;
		this.gc = gc;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public abstract void onClick();
}