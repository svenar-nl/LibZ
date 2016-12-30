package me.winspeednl.libz.core;

import me.winspeednl.libz.graphics.Render;

/**
 * LibZ abstract class
 * Use this to create your game
 * 
 * @author      Sven Arends <sarends98@gmail.com>
 * @version     1.0
 * @since       0.1
 */
public abstract class LibZ {
	public abstract void init(GameCore gc);
	public abstract void update(GameCore gc);
	public abstract void render(GameCore gc, Render r);
}
