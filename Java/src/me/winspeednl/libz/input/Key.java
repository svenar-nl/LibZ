package me.winspeednl.libz.input;

import me.winspeednl.libz.core.GameCore;

/**
 * Key class
 * Create a new Key
 * 
 * @author      Sven Arends <sarends98@gmail.com>
 * @version     1.0
 * @since       1.0
 */
public class Key {
	
	private InputListener inputListener;
	private int key;
	private boolean pressed = false;
	
	/**
	 * Constructor, Create a key
	 * @param gc
	 * @param key
	 */
	public Key(GameCore gc, int key) {
		inputListener = gc.getInput();
		this.key = key;
		
		inputListener.addKey(this);
	}
	
	/**
	 * is key pressed?
	 * @return boolean
	 */
	public boolean isPressed() {
		return pressed;
	}
	
	/**
	 * Get the keyCode
	 * @return keyCode
	 */
	public int getKey() {
		return key;
	}
	
	/**
	 * Set pressed
	 * @param pressed
	 */
	protected void setPressed(boolean pressed) {
		this.pressed = pressed;
	}
}
