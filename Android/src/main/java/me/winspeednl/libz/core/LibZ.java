package me.winspeednl.libz.core;

import me.winspeednl.libz.screen.Render;

public abstract class LibZ {

    public abstract void init(GameCore gc);
    public abstract void update(GameCore gc);
    public abstract void render(GameCore gc, Render r);
}
