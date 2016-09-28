package me.winspeednl.libz.core;

import me.winspeednl.libz.screen.Render;
import me.winspeednl.libz.screen.Screen;

public class GameCore implements Runnable {
	
	private Thread thread;
	private LibZ game;
	private Screen screen;
	public Render renderer;
	private Input input;
	
	private int width = 320, height = 240;
	private int scale = 1;
	private String title = "LibZ";
	
	private double frameCap = 1D / 60D;
	private boolean isRunning = false, fullscreen = false, undecorated = false, screenSizeLocked = false;
	private int spriteBGColor = 0x00000000;
	private int fps = 0;
	private int offsetX, offsetY;
		
	public GameCore(LibZ game) {
		this.game = game;
	}
	
	public void start() {
		if (isRunning)
			return;
		
		screen = new Screen(this);
		renderer = new Render(this);
		input = new Input(this);
		game.init(this);

		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public void stop() {
		if (!isRunning)
			return;
		isRunning = false;
	}
	
	public void run() {		
		double currTime;
		double lastTime = System.nanoTime() / 1000000000D;
		double passedTime;
		double unprocessedTime = 0;
		double frameTime = 0;
		int frames = 0;
		
		while (isRunning) {
			boolean render = false;
			
			currTime = System.nanoTime() / 1000000000D;
			passedTime = currTime - lastTime;
			lastTime = currTime;
			
			unprocessedTime += passedTime;
			frameTime += passedTime;
			
			while (unprocessedTime >= frameCap) {
				game.update(this);
				unprocessedTime -= frameCap;
				render = true;
				
				if(frameTime >= 1) {
					frameTime = 0;
					fps = frames;
					frames = 0;
				}
			}
			
			offsetX = renderer.getOffsetX();
			offsetY = renderer.getOffsetY();
			
			if (render) {
				renderer.clear();
				game.render(this, renderer);
				
				for (int i = 0; i < renderer.getOverlayPixels().length; i++) {
					if (renderer.getOverlayPixels()[i] != spriteBGColor)
						renderer.setPixel(i, renderer.getOverlayPixels()[i]);
				}
				
				screen.update();

				frames++;
			} else {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		screen.cleanup();
	}
	
	public void fullscreen() {
		fullscreen = true;
	}
	
	public void lockScreenSize() {
		screenSizeLocked = true;
	}
	
	public boolean isFullscreen() {
		return fullscreen;
	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		if (!screenSizeLocked)
			this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		if (!screenSizeLocked)
			this.height = height;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		if (!screenSizeLocked)
			this.scale = scale;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Screen getScreen() {
		return screen;
	}

	public Input getInput() {
		return input;
	}

	public int getSpriteBGColor() {
		return spriteBGColor;
	}

	public void setSpriteBGColor(int spriteBGColor) {
		this.spriteBGColor = spriteBGColor;
	}

	public int fps() {
		return fps;
	}

	public int getOffsetX() {
		return offsetX;
	}

	public int getOffsetY() {
		return offsetY;
	}
	
	public void setOffsetX(int offsetX) {
		renderer.setOffsetX(offsetX);
	}
	
	public void setOffsetY(int offsetY) {
		renderer.setOffsetY(offsetY);
	}

	public boolean isDecorated() {
		return !undecorated;
	}
	
	public void undecorated() {
		undecorated = true;
	}
}