package me.winspeednl.libz.core;

import java.util.ArrayList;

import me.winspeednl.libz.entities.LibZ_Entity;
import me.winspeednl.libz.screen.Render;

public abstract class LibZ {
	
	public ArrayList<LibZ_Entity> objects = new ArrayList<LibZ_Entity>();
	
	public abstract void init(GameCore gc);
	public abstract void update(GameCore gc);
	public abstract void render(GameCore gc, Render r);
}
