package me.sven.libz.core;

import me.sven.libz.graphics.Render;

public abstract class LibZ {
	public abstract void init(GameCore gc);
	public abstract void update(GameCore gc);
	public abstract void render(GameCore gc, Render r);
	
	//TODO Message bus handler
}
