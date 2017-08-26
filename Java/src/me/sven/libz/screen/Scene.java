package me.sven.libz.screen;

import me.sven.libz.core.GameCore;
import me.sven.libz.graphics.Render;

public abstract class Scene {
	
	public boolean useCamera = true;
	
	public Scene(GameCore gc, String sceneName) {
		gc.addScene(sceneName, this);
		init(gc);
	}
	
	public abstract void init(GameCore gc);
	public abstract void update(GameCore gc);
	public abstract void render(GameCore gc, Render r);
}
