package me.winspeednl.zpong;

import me.winspeednl.libz.core.GameCore;
import me.winspeednl.libz.entities.LibZ_Entity;
import me.winspeednl.libz.image.Sprite;
import me.winspeednl.libz.screen.Render;

public class Ball extends LibZ_Entity{
	
	private Game game;
	private boolean xDir = false, yDir = false;
	private int[] pixels;
	private double speed = 2.5;
	
	public Ball(Game game, GameCore gc, int x, int y, int w, int h) {
		this.game = game;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		
		pixels = new Sprite("/Ball.png", 0, 0, w, h).pixels;
	}

	public void update(GameCore gc) {
		if (xDir)
			x += speed;
		else
			x -= speed;
		if (yDir)
			y += speed;
		else
			y -= speed;
		if (y <= 0 || y >= gc.getHeight() - h)
			yDir = !yDir;
		
		if (new Collision().collides(x, y, w, h, game.player.x, game.player.y, game.player.w, game.player.h))
			xDir = true;
		if (new Collision().collides(x, y, w, h, game.enemy.x, game.enemy.y, game.enemy.w, game.enemy.h))
			xDir = false;
		
		if (x + w < 0) {
			game.eScore++;
			x = gc.getWidth() / 2 - 8;
		}
		if (x - w > gc.getWidth()) {
			game.pScore++;
			x = gc.getWidth() / 2 - 8;
		}
	}
	
	public void render(GameCore gc, Render r) {
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				//if (pixels[x + y * w] != )
				r.setPixel(this.x + x, this.y + y, pixels[x + y * w]);
			}
		}
	}
}
