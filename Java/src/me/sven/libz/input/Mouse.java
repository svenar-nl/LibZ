package me.sven.libz.input;

import java.awt.Point;

import me.sven.libz.core.GameCore;

/**
 * Mouse class
 * Create a new Mouse
 * 
 * @author      Sven Arends <sarends98@gmail.com>
 * @version     1.0
 * @since       1.0
 */
public class Mouse {
	
	private InputListener inputListener;
	private boolean leftPressed = false, rightPressed = false, middlePressed = false, dragging = false;
	private int x, y, dragX, dragY, notchesUp, notchesDown;
	
	/**
	 * Constructor, Create a mouse
	 * @param gc
	 */
	public Mouse(GameCore gc) {
		inputListener = gc.getInput();
		inputListener.addMouse(this);
	}
	
	/**
	 * Is left presser?
	 * @return boolean
	 */
	public boolean isLeftPressed() {
		return leftPressed;
	}
	
	/**
	 * Set left pressed
	 * @param leftPressed
	 */
	protected void setLeftPressed(boolean leftPressed) {
		this.leftPressed = leftPressed;
		if (!leftPressed) setDragging(false);
	}
	
	/**
	 * Is right pressed?
	 * @return boolean
	 */
	public boolean isRightPressed() {
		return rightPressed;
	}
	
	/**
	 * Set right pressed
	 * @param rightPressed
	 */
	protected void setRightPressed(boolean rightPressed) {
		this.rightPressed = rightPressed;
		if (!rightPressed) setDragging(false);
	}
	
	/**
	 * Is middle pressed?
	 * @return boolean
	 */
	public boolean isMiddlePressed() {
		return middlePressed;
	}
	
	/**
	 * Set middle pressed
	 * @param middlePressed
	 */
	protected void setMiddlePressed(boolean middlePressed) {
		this.middlePressed = middlePressed;
		if (!middlePressed) setDragging(false);
	}
	
	/**
	 * Set scroll amount up
	 * @param notchesUp
	 */
	protected void setScrollUp(int notchesUp) {
		this.notchesUp = notchesUp;
	}
	
	/**
	 * Has scrolled up?
	 * @return boolean
	 */
	public boolean scrolledUp() {
		boolean trigger = false;
		if (notchesUp > 0) {
			notchesUp--;
			trigger = true;
		}
		return trigger;
	}
	
	/**
	 * Set scroll amount down
	 * @param notchesDown
	 */
	protected void setScrollDown(int notchesDown) {
		this.notchesDown = notchesDown;
	}
	
	/**
	 * Has scrolled down?
	 * @return boolean
	 */
	public boolean scrolledDown() {
		boolean trigger = false;
		if (notchesDown > 0) {
			notchesDown--;
			trigger = true;
		}
		return trigger;
	}
	
	/**
	 * Get X position
	 * @return x
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Get Y position
	 * @return y
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Get drag start X
	 * @return x
	 */
	public int getDragX() {
		return dragX;
	}
	
	/**
	 * Get drag start Y
	 * @return y
	 */
	public int getDragY() {
		return dragY;
	}
	
	/**
	 * Set position
	 * @param pos
	 */
	protected void setPosition(Point pos) {
		this.x = pos.x;
		this.y = pos.y;
	}
	
	/**
	 * Set drag position
	 * @param pos
	 */
	protected void setDragPosition(Point pos) {
		this.dragX = pos.x;
		this.dragY = pos.y;
	}
	
	/**
	 * Is dragging?
	 * @return boolean
	 */
	public boolean isDragging() {
		return dragging;
	}
	
	/**
	 * Set dragging
	 * @param dragging
	 */
	protected void setDragging(boolean dragging) {
		this.dragging = dragging;
	}
}
