package me.winspeednl.libz.logger;

import java.time.LocalDateTime;

public class Logger {
	
	private String prefix = "LibZ";
	private boolean color = false, minimalOutput = false;
	
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	
	public Logger(String prefix) {
		this.prefix = prefix;
	}
	
	public void enableColor(boolean bool) {
		color = bool;
	}
	
	public void minimalOutput(boolean bool) {
		minimalOutput = bool;
	}
	
	public void log(String Type, String ansiColor, String message) {
		LocalDateTime currentTime = LocalDateTime.now();
		String timeStamp = "[" + currentTime.getHour() + ":" + currentTime.getMinute() + ":" + currentTime.getSecond() + "] ";
		
		String output = "[" + prefix + "] " + message;
		
		if (color && minimalOutput) output = "[" + ansiColor + prefix + ANSI_RESET + "] " + message;
		if (color && !minimalOutput) output = timeStamp + "[" + ansiColor + Type.toUpperCase() + ANSI_RESET + "] " + "[" + prefix + "] " + message;
		if (!color && !minimalOutput) output = timeStamp + "[" + Type.toUpperCase() + "] " + "[" + prefix + "] " + message;

		System.out.println(output);
	}
	
	public void info(String message) {
		log("INFO", ANSI_BLUE, message);
	}
	public void warning(String message) {
		log("WARNING", ANSI_YELLOW, message);
	}
	public void danger(String message) {
		log("DANGER", ANSI_RED, message);
	}
	public void success(String message) {
		log("SUCCESS", ANSI_GREEN, message);
	}
}
