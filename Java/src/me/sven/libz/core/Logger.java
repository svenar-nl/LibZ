package me.sven.libz.core;

public class Logger {
	
	private String name = "";
	
	public Logger(String name) {
		this.name = name;
	}
	
	public void info(String msg) {
		log("[INFO] " + msg);
	}
	
	public void warning(String msg) {
		log("[WARNING] " + msg);
	}
	
	public void fatal(String msg) {
		log("[FATAL] " + msg);
	}
	
	public void log(String msg) {
		System.out.println("[" + name + "] " + msg);
	}
}
