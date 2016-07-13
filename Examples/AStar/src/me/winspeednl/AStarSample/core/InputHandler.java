/*
* Written by: Sven Arends
* On: 12-07-2016 (dd-mm-yyyy)
* This is a LibZ Example
* 
* THIS CODE IS PUBLIC DOMAIN
*/
package me.winspeednl.AStarSample.core;

import java.awt.event.KeyEvent;

import me.winspeednl.libz.core.Input;

public class InputHandler {
	private Input input;
	private int speed = 1;
	
	public InputHandler(Input input) {
		this.input = input;
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public void update(Entity entity) {
		if (input.isKeyPressed(KeyEvent.VK_ESCAPE))
			System.exit(0);
		
		if (input.isKeyPressed(KeyEvent.VK_W))
			if (entity.y - speed > 0)
				entity.y -= speed;
		if (input.isKeyPressed(KeyEvent.VK_S))
				entity.y += speed;
		if (input.isKeyPressed(KeyEvent.VK_A))
			if (entity.x - speed > 0)
				entity.x -= speed;
		if (input.isKeyPressed(KeyEvent.VK_D))
				entity.x += speed;
	}
}
