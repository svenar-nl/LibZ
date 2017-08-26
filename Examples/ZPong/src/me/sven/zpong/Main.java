package me.sven.zpong;

import me.sven.libz.core.GameCore;
import me.sven.libz.core.Settings;

public class Main {

	public static void main(String[] args) {
		GameCore gc = new GameCore(new Game());
		Settings.title = "ZPong";
		Settings.version = "v2";
		Settings.fullscreen = true;
		Settings.menu = true;
		gc.start();
	}
}
