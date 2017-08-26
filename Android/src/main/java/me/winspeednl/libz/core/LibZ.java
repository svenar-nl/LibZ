package me.winspeednl.libz.core;

import me.winspeednl.libz.graphics.Render;

/**
 * Created by sven on 27-1-17.
 */

public abstract class LibZ {

    public abstract void init(GameCore gc);
    public abstract void update(GameCore gc);
    public abstract void render(GameCore gc, Render r);
}