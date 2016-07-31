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

public class Goomba extends LibZ_Mob {
	private int[] pixels;
	private int direction = 1, dieHeight = 0;
	private double gravity = 9.81;
	private boolean canMoveDown = false, canMoveLeft = false, canMoveRight = false, dieHeightReached = false, dieAudioPlayed = false;
	private long oldMillis = 0;
	private long oldMillisTouchReset = 0;

	public boolean isTouching = false;
	public int id = 0;
	
	private Level level;
	private Game game;
	
	public Goomba(Game game, Level level, int x, int y) {
		this.game = game;
		this.level = level;
		this.w = this.h = 48;
		this.x = x;
		this.y = y;
		moveSpeed = 2;
		isAlive = true;
		
		Sprite sprite = new Sprite(new Textures().GOOMBA, 0, 0, w, h);
		pixels = sprite.pixels;
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
		if (isAlive) {
			checkDirections(gc);
			
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
			
			if (!isTouching)
				oldMillisTouchReset = System.currentTimeMillis();
			else
				if (System.currentTimeMillis() - oldMillisTouchReset > 500)
					isTouching = false;
			
			dieHeight = y - 65;
		} else {
			if (!dieAudioPlayed) {
				dieAudioPlayed = true;
				game.audio.stomp = new Sound("/audio/sfx/stomp.wav");
				game.audio.stomp.play();
			}
			if (!dieHeightReached) {
				y -= gravity / 2;
				if (y <= dieHeight)
					dieHeightReached = true;
			} else {
				y += gravity / 2;
			}
		}
	}
	
	public void render(GameCore gc, Render r) {
		int[] deadPixels = null;
		BufferedImage image = r.getImageFromArray(pixels, w, h);
		if (isAlive) {
			if (System.currentTimeMillis() - oldMillis > 350) {
				oldMillis = System.currentTimeMillis();
				image = r.flip(r.getImageFromArray(pixels, w, h), true, false);
			}
			pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		} else {
			image = r.flip(r.getImageFromArray(pixels, w, h), false, true);
			deadPixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		}
		
		
		for (int x = 0; x < w; x++)
			for (int y = 0; y < h; y++)
				if (isAlive)
					r.setPixel(this.x + x, this.y + y, pixels[x + y * w]);
				else
					r.setPixel(this.x + x, this.y + y, deadPixels[x + y * w]);
		
		/*for (int x = 0; x < w; x++)
			for (int y = 0; y < h - 24; y++)
				r.setPixel(x + this.x, y + this.y + 24, 0xFFFF0000);
		
		for (int x = 0; x < w-12; x++)
			for (int y = 0; y < 24; y++)
				r.setPixel(x + this.x+6, y + this.y, 0xFF0000FF);*/
	}
}