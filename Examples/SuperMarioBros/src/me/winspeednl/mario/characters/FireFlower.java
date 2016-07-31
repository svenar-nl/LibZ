package me.winspeednl.mario.characters;

import me.winspeednl.libz.audio.Sound;
import me.winspeednl.libz.core.GameCore;
import me.winspeednl.libz.entities.LibZ_Mob;
import me.winspeednl.libz.image.Sprite;
import me.winspeednl.libz.screen.Render;
import me.winspeednl.mario.Game;
import me.winspeednl.mario.Textures;

public class FireFlower extends LibZ_Mob {
	private int[] pixels;
	private int originalY = 0;
	private boolean isMovingUp = true;
	public boolean canDie = false;
	
	public FireFlower(Game game, int x, int y) {
		this.w = this.h = 48;
		this.x = x;
		this.y = y;
		this.originalY = y;
		moveSpeed = 3;
		
		Sprite sprite = new Sprite(new Textures().FIREFLOWER, 0, 0, w, h);
		pixels = sprite.pixels;
		
		game.audio.powerupApears = new Sound("/audio/sfx/powerup-appears.wav");
		game.audio.powerupApears.play();
	}
	
	public void update(GameCore gc) {
		if (isMovingUp) {
			y -= moveSpeed;

			if (originalY - y > h) {
				isMovingUp = false;
				canDie = true;
			}
		}
	}
	
	public void render(GameCore gc, Render r) {
	
		for (int x = 0; x < w; x++)
			for (int y = 0; y < h; y++)
				if (isMovingUp) {
					if (this.y + y < originalY)
						r.setPixel(this.x + x, this.y + y, pixels[x + y * w]);
				} else
						r.setPixel(this.x + x, this.y + y, pixels[x + y * w]);
	}
}