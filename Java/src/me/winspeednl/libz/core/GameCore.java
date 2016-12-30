package me.winspeednl.libz.core;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;

import me.winspeednl.libz.entities.Entity;
import me.winspeednl.libz.graphics.Camera;
import me.winspeednl.libz.graphics.Render;
import me.winspeednl.libz.graphics.Screen;
import me.winspeednl.libz.input.InputListener;

/**
 * GameCore class.
 * This is where the magic happens.
 * 
 * @author      Sven Arends <sarends98@gmail.com>
 * @version     1.0
 * @since       0.1
 */
public class GameCore implements Runnable {

	private String title = "LibZ";
	private int width = 200, height = 200, tps, fps;
	private boolean fullscreen = false, screenSizeLocked = false, isDecorated = true;
	private LibZ game;
	private Screen screen;
	private Render render;
	private InputListener input;
	private Thread thread;
	private Camera camera;
	private ArrayList<Entity> entities = new ArrayList<Entity>(), deadEntities = new ArrayList<Entity>();
	
	/**
	 * Init
	 * @param game
	 */
	public GameCore(LibZ game) {
		this.game = game;
	}
	
	/**
	 * Init
	 * @param game
	 * @param width
	 * @param height
	 */
	public GameCore(LibZ game, int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Start the game
	 */
	public void start() {
		init();
		game.init(this);
		thread = new Thread(this);
		thread.start();
	}
	
	/**
	 * Stop the game
	 */
	public void stop() {
		thread.interrupt();
	}
	
	/**
	 * Initialize everything required for the game.
	 */
	private void init() {
		if (fullscreen) {
			GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
			setSize((int) env.getMaximumWindowBounds().getWidth(), (int) env.getMaximumWindowBounds().getHeight());
		}
		lockScreenSize();
		camera = new Camera(0, 0, getWidth(), getHeight());
		screen = new Screen(this);
		render = new Render(this);
		input = new InputListener(this);
	}

	/**
	 * After calling this the screen size cannot be changed.
	 */
	private void lockScreenSize() {
		screenSizeLocked = true;
	}
	
	/**
	 * The game loop
	 */
	public void run() {
		long lastTime = System.nanoTime();
        double nsPerTick = 1000000000D / 60D;

        int ticks = 0;
        int frames = 0;

        long fpsTimer = System.currentTimeMillis();
        double delta = 0;
        
		while(!thread.isInterrupted()) {
			long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;
            boolean renderFrame = true;

            while (delta >= 1) {
                ticks++;
                delta -= 1;
                update();
            }
            renderFrame = true;
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
            	return;
            }

            if (renderFrame) {
                frames++;
                render();
            }
            
            if (System.currentTimeMillis() - fpsTimer >= 1000) {
            	fpsTimer += 1000;
                this.tps = ticks;
                this.fps = frames;
                frames = 0;
                ticks = 0;
            }
		}
	}
	
	/**
	 * Update the game
	 */
	private void update() {
		game.update(this);
		for (Entity entity : entities) {
			entity.update(this);
			if (!entity.isAlive()) deadEntities.add(entity);
		}
		for (Entity deadEntity : deadEntities) {
			entities.remove(deadEntity);
		}
		deadEntities = new ArrayList<Entity>();
	}

	/**
	 * Render a single frame
	 */
	private void render() {
		screen.clear(this);
		camera.translate(render.getGraphics());
		game.render(this, render);
		camera.revert(render.getGraphics());
		screen.update();
	}

	// Getters and Setters
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setFullscreen(boolean fullscreen) {
		this.fullscreen = fullscreen;
	}

	public boolean isFullscreen() {
		return fullscreen;
	}
	
	public void setWidth(int width) {
		if (!screenSizeLocked) this.width = width;
	}
	
	public void setHeight(int height) {
		if (!screenSizeLocked) this.height = height;
	}
	
	public void setSize(int width, int height) {
		if (!screenSizeLocked) {
			this.width = width;
			this.height = height;
		}
	}
	
	public void setSize(Dimension dimension) {
		if (!screenSizeLocked) {
			this.width = dimension.width;
			this.height = dimension.height;
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Screen getScreen() {
		return screen;
	}
	
	public Render getRender() {
		return render;
	}
	
	public InputListener getInput() {
		return input;
	}
	
	public int getTPS() {
		return tps;
	}
	
	public int getFPS() {
		return fps;
	}

	public boolean isDecorated() {
		return isDecorated;
	}
	
	public void setDecorated(boolean decorated) {
		isDecorated = decorated;
	}
	
	public Camera getCamera() {
		return camera;
	}
	
	public ArrayList<Entity> getEntities() {
		return entities;
	}
}