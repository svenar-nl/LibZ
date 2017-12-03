package me.sven.libz.core;

import java.awt.Graphics2D;
import java.util.HashMap;

import me.sven.libz.graphics.Camera;
import me.sven.libz.graphics.Render;
import me.sven.libz.graphics.Screen;
import me.sven.libz.graphics.Splash;
import me.sven.libz.graphics.Stats;
import me.sven.libz.input.InputListener;
import me.sven.libz.notification.LibZOverlay;
import me.sven.libz.screen.MainMenu;
import me.sven.libz.screen.Scene;

public class GameCore implements Runnable {
	
	private Logger log;
	private LibZ game;
	private Screen screen;
	private Render render;
	private Camera camera;
	private Thread thread;
	private InputListener inputListener;
	private int fps;
	private Stats stats;
	private Splash splashScreen;
	private HashMap<String, Scene> scenes = new HashMap<String, Scene>();
	private Scene activeScene, previousScene;
	private LibZOverlay libzOverlay;
	
	public GameCore(LibZ game) {
		this.game = game;
	}
	
	public void start() {
		if (Settings.isRunning) {
			log.warning("start() excecuted while game is running, skipping."); 
			return;
		}
		
		if (Settings.splash) {
			splashScreen = new Splash();
			init();
			try {
				initThread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			try {
			    Thread.sleep(Settings.splashDisplayTime);
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
			
			splashScreen.close();
		} else init();
	}
	
	public void stop() {
		thread.interrupt();
		screen.cleanup();
		Settings.isRunning = false;
		log.info("Application stopped");
		System.exit(0);
	}
	
	Thread initThread;
	
	public void init() {
		long start = System.nanoTime();
		
		initThread = new Thread(() -> {
			try {
				Settings.isRunning = true;
				log = new Logger(getClass().getSimpleName());
				
				camera = new Camera(0, 0, Settings.width, Settings.height);
				screen = new Screen();
				render = new Render(screen.getGraphics());
				inputListener = new InputListener(this);
				stats = new Stats();
				if (Settings.libzOverlay) libzOverlay = new LibZOverlay(this);
				
				if (Settings.menu) {
					Scene scene = new MainMenu(this);
					activeScene = scene;
					addScene("MainMenu", scene);
				}
				
				game.init(this);
				screen.show();
				thread = new Thread(this, "LibZ Game Thread");
				thread.start();
				
				if (Settings.resizable) log.fatal("'Settings.resizable' is depricated and is ignored");
				log.info(String.format("LibZ init took: %.3f sec", ((System.nanoTime() - start) / 1000000000.0)));
				if (Settings.debug) log.info("Logging game settings" + System.lineSeparator() + Settings.toLogString());
				else log.info("Screen size: " + Settings.width + "x" + Settings.height);
			} catch (Exception e) {
				log.fatal("Exception during startup:");
				log.fatal(e.toString());
				log.fatal("Application will now close");
				e.printStackTrace();
				
				System.exit(-1);
			}
		}, "LibZ Init Thread");
		initThread.start();
	}

	public void run() {
		long lastTime = System.nanoTime();
		double nsPerTick = 1000000000D / 60D;
		double delta = 0;
		
		while(!thread.isInterrupted()) {
			long startRender = System.nanoTime();
			
			boolean renderFrame = false;
			long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;
            while (delta >= 1) {
                delta -= 1;
                if (render.getGraphics() != screen.getGraphics()) render.setGraphics(screen.getGraphics());
                if (libzOverlay == null || (libzOverlay != null && !libzOverlay.isOverlayOpen)) {
                	game.update(this);
                	if (activeScene != null) activeScene.update(this);
                    screen.updateTitle(this);
                }
                if (Settings.debug) stats.update(this);
                if (Settings.libzOverlay && libzOverlay != null) libzOverlay.update();
            }
            renderFrame = true;
			
			try {
				Thread.sleep(1000 / Settings.fpsLimit);
			} catch (InterruptedException e) {}
			
			if (renderFrame) render();
			
			fps = (int) (1 / ((System.nanoTime() - startRender) / 1000000000.0));
		}
	}

	private void render() {
		screen.clear();
		if (activeScene != null) {
			if (activeScene.useCamera) {
				//camera.zoom((Graphics2D) render.getGraphics()); 
				//camera.translate(render.getGraphics());
				camera.apply((Graphics2D) render.getGraphics());
				activeScene.render(this, render);
			} else {
				activeScene.render(this, render);
				camera.apply((Graphics2D) render.getGraphics());
				//camera.zoom((Graphics2D) render.getGraphics()); 
				//camera.translate(render.getGraphics());
			}
			game.render(this, render);
			camera.revert((Graphics2D) render.getGraphics());
			//camera.revertZoom((Graphics2D) render.getGraphics()); 
			//camera.revertTranslate(render.getGraphics());
		} else {
			//camera.zoom((Graphics2D) render.getGraphics()); 
			//camera.translate(render.getGraphics());
			camera.apply((Graphics2D) render.getGraphics());
			game.render(this, render);
			camera.revert((Graphics2D) render.getGraphics());
			//camera.revertZoom((Graphics2D) render.getGraphics()); 
			//camera.revertTranslate(render.getGraphics());
		}
		
		if (Settings.debug) stats.render(render.getGraphics());
		if (Settings.libzOverlay && libzOverlay != null) libzOverlay.render(render);
		screen.update();
	}

	public int getFPS() {
		return fps;
	}

	public Stats getStats() {
		return stats;
	}

	public Screen getScreen() {
		return screen;
	}

	public InputListener getInput() {
		return inputListener;
	}

	public Camera getCamera() {
		return camera;
	}
	
	public boolean setActiveScene(String sceneName) {
		if (scenes.containsKey(sceneName.toLowerCase())) {
			previousScene = activeScene;
			activeScene = scenes.get(sceneName.toLowerCase());
		} else return false;
		return true;
	}
	
	public void setActiveScene(Scene scene) {
		previousScene = activeScene;
		activeScene = scene;
	}
	
	public Scene getScene(String sceneName) {
		return scenes.get(sceneName.toLowerCase());
	}

	public boolean addScene(String sceneName, Scene scene) {
		if (!scenes.containsKey(sceneName.toLowerCase())) scenes.put(sceneName.toLowerCase(), scene);
		else return false;
		return true;
	}

	public void setPreviousScene() {
		activeScene = previousScene;
	}
}
