package me.winspeednl.mario;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import me.winspeednl.libz.core.GameCore;
import me.winspeednl.libz.core.LibZ;
import me.winspeednl.libz.image.Sprite;
import me.winspeednl.libz.screen.Render;
import me.winspeednl.mario.characters.Coin;
import me.winspeednl.mario.characters.FireFlower;
import me.winspeednl.mario.characters.Goomba;
import me.winspeednl.mario.characters.Mario;
import me.winspeednl.mario.characters.Mushroom;
import me.winspeednl.mario.characters.UP1Shroom;
import me.winspeednl.mario.level.Map;
import me.winspeednl.mario.overlay.Gui;

public class Game extends LibZ {
	
	private Gui gui;
	public Map map;
	public Mario mario;
	public Audio audio;
	
	public int timeLeft = 400, coins = 0, score = 0;
	private long oldTime = 0;
	public String currentMap = "1-1";
	
	public boolean gameStarted = false, gameOver = false;
	private int[] backgroundPixels;
	public ArrayList<Object> entities = new ArrayList<Object>();
	public ArrayList<String> particles = new ArrayList<String>();
	
	public void init(GameCore gc) {
		backgroundPixels = new int[gc.getWidth() * gc.getHeight()];
		for (int i = 0; i < backgroundPixels.length; i++) {
			backgroundPixels[i] = 0xFF5C94FC;
		}
		
		gui = new Gui();
		
	    
	    new Thread(() -> {
	    	map = new Map(this, gc);
	    }).start();
	    
		
		audio = new Audio();
	}

	public void update(GameCore gc) {
		if (mario == null && map != null)
			mario = new Mario(this, gc, map.getLevel());
		
		if (mario != null)
			if (mario.isDying) {
				audio.ground.stop();
				if (!audio.die.isPlaying())
					audio.die.play();
			}
		
		if (timeLeft < 100)
			if (audio.ground.isPlaying()) {
				audio.ground.stop();
				audio.groundHurry.loop(10);
				
			}
		
		if (gc.getInput().isKeyPressed(KeyEvent.VK_ESCAPE))
			System.exit(0);
		
		if (gc.getInput().isKeyPressed(KeyEvent.VK_ENTER)) {
			if (mario != null && !mario.inputEnabled) {
				mario.inputEnabled = true;
				gameStarted = true;
				audio.ground.loop(10);
			}
		}

		if (mario != null) {
			if (timeLeft == 0)
				mario.isDying = true;
			
			if (System.currentTimeMillis() - oldTime > 1000) {
				oldTime = System.currentTimeMillis();
				if (gameStarted && !gameOver && !mario.isDying && mario.inputEnabled)
					timeLeft--;
			}
			if (mario.isDead && !audio.die.isPlaying())
				if (!(mario.lives <= 0))
					mario.reset();
				else {
					mario.reset();
					System.out.println("Full game reset!");
				}
		}
		
		
		
		if (map != null)
			map.update(this, gc);
		
		if (mario != null)
			mario.update(gc);
		
		for (int i = 0; i < entities.size(); i++) {
			Object entity = entities.get(i);
			if (entity instanceof Coin) {
				Coin e = (Coin)entity;
				e.update(gc);
				if (e.y > gc.getHeight() || e.x + e.w < gc.getOffsetX())
					entities.remove(i);
			}
			
			if (entity instanceof Mushroom) {
				Mushroom e = (Mushroom)entity;
				e.update(gc);
				if (e.y > gc.getHeight() || e.x + e.w < gc.getOffsetX())
					entities.remove(i);
			}
			
			if (entity instanceof FireFlower) {
				FireFlower e = (FireFlower)entity;
				e.update(gc);
				if (e.y > gc.getHeight() || e.x + e.w < gc.getOffsetX())
					entities.remove(i);
			}
			
			if (entity instanceof UP1Shroom) {
				UP1Shroom e = (UP1Shroom)entity;
				e.update(gc);
				if (e.y > gc.getHeight() || e.x + e.w < gc.getOffsetX())
					entities.remove(i);
			}
			
			// Enemies
			if (entity instanceof Goomba) {
				Goomba e = (Goomba)entity;
				e.update(gc);
				if (e.y > gc.getHeight() || e.x + e.w < gc.getOffsetX()) {
					entities.remove(e);
				}
			}
		}
		
		for (int i = 0; i < particles.size(); i++) {
			String[] split = particles.get(i).split(":");
			String texture = split[0];
			int x = Integer.parseInt(split[1]);
			int y = Integer.parseInt(split[2]);
			int dir = Integer.parseInt(split[3]);
			int life = Integer.parseInt(split[4]);
			int lifeTime = Integer.parseInt(split[5]);
			int w = Integer.parseInt(split[6]);
			int h = Integer.parseInt(split[7]);
			
			if (life < lifeTime) {
				life ++;
				
				if (dir == 0) {
					x -= new Random().nextInt(3) + 1;
				} else {
					x += new Random().nextInt(3) + 1;
				}
				y += 9.81 / 2;
			} else {
				particles.remove(i);
			}
			
			String newParticle = texture + ":" + x + ":" + y + ":" + dir + ":" + life + ":" + lifeTime + ":" + w + ":" + h;
			if (i < particles.size())
				particles.set(i, newParticle);
		}
		
		if (gui != null)
			gui.update(this, gc);
	}
	
	public void render(GameCore gc, Render r) {
		for (int x = 0; x < gc.getWidth(); x++)
			for (int y = 0; y < gc.getHeight(); y++)
				r.setPixel(x+gc.getOffsetX(),  y+gc.getOffsetY(), backgroundPixels[x + y * gc.getWidth()]);
					
		if (map != null)
			map.render(this, gc, r);
		
		if (mario != null)
			mario.render(gc, r);
		
		for (int i = 0; i < entities.size(); i++) {
			Object entity = entities.get(i);
			if (entity instanceof Coin) {
				Coin e = (Coin)entity;
				e.render(gc, r);
			}
			
			if (entity instanceof Mushroom) {
				Mushroom e = (Mushroom)entity;
				e.render(gc, r);
			}
			
			if (entity instanceof FireFlower) {
				FireFlower e = (FireFlower)entity;
				e.render(gc, r);
			}
			
			if (entity instanceof UP1Shroom) {
				UP1Shroom e = (UP1Shroom)entity;
				e.render(gc, r);
			}
			
			// Enemies
			if (entity instanceof Goomba) {
				Goomba e = (Goomba)entity;
				e.render(gc, r);
			}
		}
		
		for (int i = 0; i < particles.size(); i++) {
			String[] split = particles.get(i).split(":");
			String texture = split[0];
			int x = Integer.parseInt(split[1]);
			int y = Integer.parseInt(split[2]);
			int w = Integer.parseInt(split[6]);
			int h = Integer.parseInt(split[7]);
			
			Sprite particle = new Sprite(texture, 0, 0, w, h);
			int[] pixels = particle.pixels;
			BufferedImage image = r.getImageFromArray(pixels, w, h);
			image = r.rotate(image, new Random().nextInt(45));
			pixels = image.getRGB(0, 0, w, h, pixels, 0, w);
			for (int X = 0; X < w; X++)
				for (int Y = 0; Y < h; Y++)
					r.setPixel(x + X, y + Y, pixels[X + Y * w]);
		}
		if (gui != null)
			gui.render(this, gc, r);
	}
}
