package me.winspeednl.zpong;

import java.awt.event.KeyEvent;

import me.winspeednl.libz.core.GameCore;
import me.winspeednl.libz.core.Input;
import me.winspeednl.libz.entities.LibZ_Mob;
import me.winspeednl.libz.image.Sprite;
import me.winspeednl.libz.screen.Render;

public class Paddle extends LibZ_Mob {
	
	private boolean isPlayer = false;
	private int[] pixels;
	private Input input;
	private Game game;
	
	public Paddle(Game game, GameCore gc, int x, int y, int w, int h, boolean isPlayer) {
		this.game = game;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.isPlayer = isPlayer;
		if (isPlayer)
			this.input = gc.getInput();
		
		pixels = new Sprite("/Paddle.png", 0, 0, w, h).pixels;
		moveSpeed = 2;
	}
	
	public void update(GameCore gc) {
		if (isPlayer) {
			if (input.isKeyPressed(KeyEvent.VK_W))
				if (y > 0)
					y -= moveSpeed;
			if (input.isKeyPressed(KeyEvent.VK_S))
				if (y < gc.getHeight() - h)
					y += moveSpeed;
		} else {
			int targetY = game.ball.y;
			if (targetY < y + h / 2)
				y -= moveSpeed;
			if (y + h < gc.getHeight())
				if (targetY > y + h / 2)
					y += moveSpeed;
		}
	}
	
	public void render(GameCore gc, Render r) {
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				r.setPixel(this.x + x, this.y + y, pixels[x + y * w]);
			}
		}
	}
}
