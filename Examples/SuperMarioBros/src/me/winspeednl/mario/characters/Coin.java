package me.winspeednl.mario.characters;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import me.winspeednl.libz.audio.Sound;
import me.winspeednl.libz.core.GameCore;
import me.winspeednl.libz.entities.LibZ_Mob;
import me.winspeednl.libz.image.Sprite;
import me.winspeednl.libz.screen.Render;
import me.winspeednl.mario.Game;
import me.winspeednl.mario.Textures;

public class Coin extends LibZ_Mob {
	private int[] pixels;
	private int direction = 0, originalY = 0;
	public boolean canDie = false;
	
	private Game game;
	
	public Coin(Game game, int x, int y) {
		this.game = game;
		this.w = 27;
		this.h = 48;
		this.x = x + w/2;
		this.y = y;
		this.originalY = y;
		moveSpeed = 3;
		
		Sprite sprite = new Sprite(new Textures().COIN, 0, 0, w, h);
		pixels = sprite.pixels;
		
		game.audio.coin = new Sound("/audio/sfx/coin.wav");
		game.audio.coin.play();
	}
	
	public void update(GameCore gc) {		
		y -= moveSpeed;
		if (originalY - y > 64)
			game.entities.remove(this);
	}
	
	public void render(GameCore gc, Render r) {
		BufferedImage image = r.getImageFromArray(pixels, w, h);
		if (direction == 1)
			image = r.flip(r.getImageFromArray(pixels, w, h), true, false);
		
		int[] pixelsToRender = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		
		for (int x = 0; x < w; x++)
			for (int y = 0; y < h; y++)
					if (this.y + y < originalY)
						r.setPixel(this.x + x, this.y + y, pixelsToRender[x + y * w]);
	}
}