package me.sven.zpong;

import me.sven.libz.core.GameCore;
import me.sven.libz.core.LibZ;
import me.sven.libz.graphics.Render;
import me.sven.zpong.scenes.AboutScene;
import me.sven.zpong.scenes.PongScene;

public class Game extends LibZ {
	
	public void init(GameCore gc) {
		new PongScene(gc, "Game");
		new AboutScene(gc, "About");
	}

	public void update(GameCore gc) {}

	public void render(GameCore gc, Render r) {}
}
