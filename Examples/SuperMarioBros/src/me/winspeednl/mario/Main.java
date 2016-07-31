package me.winspeednl.mario;

import me.winspeednl.libz.core.GameCore;

public class Main {
	static String title = "Super Mario Bros";
	static int
		screenWidth = 256*3,
		screenHeight = 224*3,
		screenScale = 1;
	
	public static void main(String[] args) {
		GameCore gc = new GameCore(new Game());
		gc.setTitle(title);
		gc.setWidth(screenWidth);
		gc.setHeight(screenHeight);
		gc.setScale(screenScale);
		gc.setSpriteBGColor(0xFF000000);
		gc.start();
	}

}
