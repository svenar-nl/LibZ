package me.winspeednl.libz.core;

import me.winspeednl.libz.screen.Render;
import me.winspeednl.libz.screen.Window;

public class GameCore implements Runnable {
	
	private Thread thread;
	private LibZ game;
	private Window window;
	public Render renderer;
	private Input input;
	
	private int width = 320, height = 240;
	private int scale = 1;
	private String title = "LibZ";
	
	private double frameCap = 1D / 60D;
	private boolean isRunning = false;
	private int spriteBGColor = 0xFF000000;
	private int fps = 0;
	private int offsetX, offsetY;
		
	public GameCore(LibZ game) {
		this.game = game;
	}
	
	public void start() {
		if (isRunning)
			return;
		
		window = new Window(this);
		renderer = new Render(this);
		input = new Input(this);
		
		isRunning = true;
		thread = new Thread(this);
		thread.start();
		game.init(this);
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
					if (renderer.getOverlayPixels()[i] != 0xFF000000)
						renderer.setPixel(i, renderer.getOverlayPixels()[i]);
				}
				
				window.update();
				
				frames++;
			} else {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		window.cleanup();
	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Window getWindow() {
		return window;
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
}