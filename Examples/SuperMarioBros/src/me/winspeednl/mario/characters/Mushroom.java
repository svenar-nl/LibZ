package me.winspeednl.mario.characters;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import me.winspeednl.libz.audio.Sound;
import me.winspeednl.libz.core.GameCore;
import me.winspeednl.libz.entities.LibZ_Mob;
import me.winspeednl.libz.image.Sprite;
import me.winspeednl.libz.level.Level;
import me.winspeednl.libz.screen.Render;
import me.winspeednl.mario.Game;
import me.winspeednl.mario.Textures;

public class Mushroom extends LibZ_Mob {
	private int[] pixels;
	private int direction = 0, originalY = 0;
	private double gravity = 9.81;
	private boolean canMoveDown = false, canMoveLeft = false, canMoveRight = false, isMovingUp = true;;
	public boolean canDie = false;
	
	private Level level;
	
	public Mushroom(Game game, Level level, int x, int y) {
		this.level = level;
		this.w = this.h = 48;
		this.x = x;
		this.y = y;
		this.originalY = y;
		moveSpeed = 3;
		
		Sprite sprite = new Sprite(new Textures().MUSHROOM, 0, 0, w, h);
		pixels = sprite.pixels;
		
		game.audio.powerupApears = new Sound("/audio/sfx/powerup-appears.wav");
		game.audio.powerupApears.play();
	}
	
	private void checkDirections(GameCore gc) {
		try{
			if (level != null) {
				if (!level.getTile(x/48, (y+h+1)/48).isSolid() && !level.getTile((x+w)/48, (y+h+1)/48).isSolid())
					canMoveDown = true;
				else
					canMoveDown = false;
				
				if (!level.getTile((x-1)/48, y/48).isSolid() && !level.getTile((x-1)/48, (y+h-8)/48).isSolid())
					canMoveLeft = true;
				else
					canMoveLeft = false;
				
				if (!level.getTile((x+w+1)/48, y/48).isSolid() && !level.getTile((x+w+1)/48, (y+h-8)/48).isSolid())
					canMoveRight = true;
				else
					canMoveRight = false;
			}
		} catch (Exception e) {}
	}
	
	public void update(GameCore gc) {
		checkDirections(gc);
		
		if (isMovingUp) {
			y -= moveSpeed;

			if (originalY - y > h) {
				isMovingUp = false;
				canDie = true;
			}
		} else {
			if (direction == 0) {
				if (!canMoveRight)
					direction = 1;
				x += moveSpeed;
			}
			if (direction == 1) {
				if (!canMoveLeft)
					direction = 0;
				x -= moveSpeed;
			}
			
			if (canMoveDown)
				y += gravity;
		}
	}
	
	public void render(GameCore gc, Render r) {
		BufferedImage image = r.getImageFromArray(pixels, w, h);
		if (direction == 1)
			image = r.flip(r.getImageFromArray(pixels, w, h), true, false);
		
		int[] pixelsToRender = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		
		for (int x = 0; x < w; x++)
			for (int y = 0; y < h; y++)
				if (isMovingUp) {
					if (this.y + y < originalY)
						r.setPixel(this.x + x, this.y + y, pixelsToRender[x + y * w]);
				} else
						r.setPixel(this.x + x, this.y + y, pixelsToRender[x + y * w]);
	}
}