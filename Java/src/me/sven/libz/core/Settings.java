package me.sven.libz.core;

public class Settings {
	
	public static String title = "Untitled LibZ project";
	public static String version = "0.0";
	public static int width = 800;
	public static int height = 600;
	public static int fpsLimit = 60;
	public static int splashDisplayTime = 2000;
	public static boolean fullscreen = false;
	public static boolean decorated = true;
	public static boolean resizable = true;
	public static boolean splash = true;
	public static boolean menu = true;
	public static boolean closeConfirmation = true;
	public static boolean isRunning = false;
	public static boolean debug = false;
	public static boolean libzOverlay = true;
	public static boolean onTop = false;
	
	public final static int fpsUnlimited = 1000;
	
	public static String toLogString() {
        return "Title: " + title + '\n' +
                "Version: " + version + '\n' +
                "Width: " + width + '\n' +
                "Height: " + height + '\n' +
                "Target FPS: " + fpsLimit + '\n' +
                "Fullscreen: " + fullscreen + '\n' +
                "Splash: " + splash + '\n' +
                "Decorated: " + decorated + '\n' +
                "Resizable: " + resizable + '\n' +
                "Splash Display Time: " + splashDisplayTime + "ms" + '\n' +
                "Debug: " + debug;
	}
}
