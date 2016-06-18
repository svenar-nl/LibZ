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
	private float scale = 2f;
	private String name = "LibZ";
	
	private double frameCap = 1D / 60D;
	private boolean isRunning = false;
	private int spriteBGColor = 0xFF0000FF;
	private int fps = 0;
	private int offsetX, offsetY;
	
	private boolean debug = false;
	
	private int playerX, playerY;
	
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
				game.update(this, (float)frameCap);
				unprocessedTime -= frameCap;
				render = true;
				
				if(frameTime >= 1) {
					frameTime = 0;
					fps = frames;
					frames = 0;
					if(debug) System.out.println("FPS: " + fps);
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
		
		cleanup();
	}
	
	public void cleanup() {
		window.cleanUp();
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

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Window getWindow() {
		return window;
	}

	public Input getInput() {
		return input;
	}

	public int getPlayerX() {
		return playerX;
	}

	public void setPlayerX(int playerX) {
		this.playerX = playerX;
	}

	public int getPlayerY() {
		return playerY;
	}

	public void setPlayerY(int playerY) {
		this.playerY = playerY;
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

	public void setDebug(boolean debug) {
		this.debug = debug;
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