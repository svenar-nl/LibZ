package me.winspeednl.libz.core;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Input implements KeyListener, MouseListener, MouseMotionListener {
	
	private GameCore gc;
	
	private boolean[] keys = new boolean[256];
	private boolean[] keysLast = new boolean[256];
	
	private boolean[] buttons = new boolean[5];
	private boolean[] buttonsLast = new boolean[5];
	
	private int mouseX, mouseY;
	
	public Input(GameCore gc){
		this.gc = gc;
		gc.getScreen().getCanvas().addKeyListener(this);
		gc.getScreen().getCanvas().addMouseListener(this);
		gc.getScreen().getCanvas().addMouseMotionListener(this);
	}
	
	public void update() {
		keysLast = keys.clone();
		buttonsLast = buttons.clone();
	}
	
	public boolean isKey(int keyCode) {
		return keys[keyCode];
	}
	
	public boolean isKeyPressed(int keyCode) {
		return keys[keyCode] && !keysLast[keyCode];
	}
	
	public boolean isKeyReleased(int keyCode) {
		return !keys[keyCode] && keysLast[keyCode];
	}
	
	public boolean isButton(int button) {
		return buttons[button];
	}
	
	public boolean isButtonPressed(int button) {
		return buttons[button] && !buttonsLast[button];
	}
	
	public boolean isButtonReleased(int button) {
		return !buttons[button] && buttonsLast[button];
	}
	
	public void mouseDragged(MouseEvent e) {
		mouseX = (int)(e.getX() / gc.getScale());
		mouseY = (int)(e.getY() / gc.getScale());
	}

	public void mouseMoved(MouseEvent e) {
		mouseX = (int)(e.getX() / gc.getScale());
		mouseY = (int)(e.getY() / gc.getScale());
	}

	public void mouseClicked(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}

	public void mousePressed(MouseEvent e) {
		try {buttons[e.getButton()] = true;} catch (Exception ex) {}
	}

	public void mouseReleased(MouseEvent e) {
		try {buttons[e.getButton()] = false;} catch (Exception ex) {}
	}

	public void keyPressed(KeyEvent e) {
		try {keys[e.getKeyCode()] = true;} catch (Exception ex) {}
	}

	public void keyReleased(KeyEvent e) {
		try {keys[e.getKeyCode()] = false;} catch (Exception ex) {}
	}

	public void keyTyped(KeyEvent e) {}
	
	public int getMouseX(){
		return mouseX;
	}
	
	public int getMouseY(){
		return mouseY;
	}

}
