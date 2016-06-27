package me.winspeednl.libz.core;

import java.util.ArrayList;

import me.winspeednl.libz.entities.Entity;
import me.winspeednl.libz.screen.Render;

public abstract class LibZ {
	
	public ArrayList<Entity> objects = new ArrayList<Entity>();
	
	public abstract void init(GameCore gc);
	public abstract void update(GameCore gc);
	public abstract void render(GameCore gc, Render r);
}
