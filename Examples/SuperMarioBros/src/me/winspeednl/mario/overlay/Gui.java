package me.winspeednl.mario.overlay;

import me.winspeednl.libz.core.GameCore;
import me.winspeednl.libz.image.Sprite;
import me.winspeednl.libz.screen.Render;
import me.winspeednl.mario.Game;
import me.winspeednl.mario.Textures;

public class Gui {
	private int timeLeft = 300, textColor = 0xFFFFFFFF;
	private String gameScoreString = "000000", coinString = "00", worldString = "1-1";
	private Sprite title = new Sprite(new Textures().GUITITLE, 121, 73, 528, 264);
	private Sprite coin = new Sprite(new Textures().GUICOIN, 266, 49, 15, 24);
	private int[] backgroundPixels;
	
	private Textures textures = new Textures();
		
	public void update(Game game, GameCore gc) {
		if (game.coins < 10)
			coinString = "0" + game.coins;
		else
			coinString = String.valueOf(game.coins);
			
		if (backgroundPixels == null) {
			backgroundPixels = new int[gc.getWidth() * gc.getHeight()];
			for (int i = 0; i < backgroundPixels.length; i++) {
				backgroundPixels[i] = 0xFF010101;
			}
		}
		
		this.timeLeft = game.timeLeft;
	}

	public void render(Game game, GameCore gc, Render r) {		
		if (game.mario != null) {
			if (game.mario.isDead)
				for (int x = 0; x < gc.getWidth(); x++)
					for (int y = 0; y < gc.getHeight(); y++)
						r.setOverlayPixel(x,  y, backgroundPixels[x + y * gc.getWidth()]);
		}
		if (!game.gameStarted) {
			// Title
			for (int x = 0; x < title.w; x++)
				for (int y = 0; y < title.h; y++)
					r.setOverlayPixel(title.x + x, title.y + y, title.pixels[x + y * title.w]);
		}
		
		// Mario score text
		gameScoreString = String.valueOf(game.score);
		for (int i = 0; i < 6; i++)
			if (gameScoreString.length() != 6)
				gameScoreString = "0" + gameScoreString;
			else
				break;

			r.drawString("Mario", textColor, 70, 22, textures.SMB);
		r.drawString(gameScoreString, textColor, 70, 46, textures.SMB);
		
		// Coin + text
		for (int x = 0; x < coin.w; x++)
			for (int y = 0; y < coin.h; y++)
				r.setOverlayPixel(coin.x + x, coin.y + y, coin.pixels[x + y * coin.w]);
		r.drawString("X" + coinString, textColor, 285, 46, textures.SMB);
		
		// World text
		r.drawString("World", textColor, gc.getWidth() - 340, 22, textures.SMB);
		r.drawString(worldString, textColor, gc.getWidth() - 310, 46, textures.SMB);
				
		// Time text
		r.drawString("Time", textColor, gc.getWidth() - 165, 22, textures.SMB);
		if (game.gameStarted)
			r.drawString(String.valueOf(timeLeft), textColor, gc.getWidth() - 144, 46, textures.SMB);
		
		
		// Die text
		if (game.mario != null)
			if (game.mario.isDead) {
				if (game.mario.lives > 0) {
					r.drawString("X" + game.mario.lives, textColor, gc.getWidth() / 2 - 100, gc.getHeight() / 2 - 12, textures.SMB);
				} else
					r.drawString("Game Over", textColor, gc.getWidth() / 2 - 100, gc.getHeight() / 2 - 12, textures.SMB);
			}

	}
}
