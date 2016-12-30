package me.winspeednl.libz.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

import me.winspeednl.libz.core.GameCore;

/**
 * Input class
 * The keyboard and mouse is handled here.
 * 
 * @author      Sven Arends <sarends98@gmail.com>
 * @version     1.0
 * @since       1.0
 */
public class InputListener implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
	
	private ArrayList<Key> keys = new ArrayList<Key>();
	private ArrayList<Mouse> mouses = new ArrayList<Mouse>();
	
	/**
	 * Constructor, used in GameCore.java, init()
	 * @param gc
	 */
	public InputListener(GameCore gc) {
		gc.getScreen().getCanvas().addKeyListener(this);
		gc.getScreen().getCanvas().addMouseListener(this);
		gc.getScreen().getCanvas().addMouseMotionListener(this);
		gc.getScreen().getCanvas().addMouseWheelListener(this);
	}
	
	/**
	 * Add a keyboad key
	 * @param key
	 */
	public void addKey(Key key) {
		keys.add(key);
	}
	
	/**
	 * Add a mouse
	 * @param mouse
	 */
	public void addMouse(Mouse mouse) {
		mouses.add(mouse);
	}
	
	/**
	 * KeyListener, MouseListener, MouseMotionListener and MouseWheelListener
	 */
	
	public void mouseDragged(MouseEvent e) {
		for (Mouse mouse : mouses) {
			mouse.setPosition(e.getPoint());
			if (!mouse.isDragging()) mouse.setDragPosition(e.getPoint());
			mouse.setDragging(true);
		}
	}

	public void mouseMoved(MouseEvent e) {
		for (Mouse mouse : mouses) {
			mouse.setPosition(e.getPoint());
			mouse.setDragging(false);
		}
	}

	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}

	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) for (Mouse mouse : mouses) mouse.setLeftPressed(true);
		if (e.getButton() == MouseEvent.BUTTON2) for (Mouse mouse : mouses) mouse.setMiddlePressed(true);
		if (e.getButton() == MouseEvent.BUTTON3) for (Mouse mouse : mouses) mouse.setRightPressed(true);
	}

	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) for (Mouse mouse : mouses) mouse.setLeftPressed(false);
		if (e.getButton() == MouseEvent.BUTTON2) for (Mouse mouse : mouses) mouse.setMiddlePressed(false);
		if (e.getButton() == MouseEvent.BUTTON3) for (Mouse mouse : mouses) mouse.setRightPressed(false);
	}

	public void keyPressed(KeyEvent e) {
		for (Key key : keys) if (key.getKey() == e.getKeyCode()) key.setPressed(true);
	}

	public void keyReleased(KeyEvent e) {
		for (Key key : keys) if (key.getKey() == e.getKeyCode()) key.setPressed(false);
	}

	public void keyTyped(KeyEvent e) {}

	public void mouseWheelMoved(MouseWheelEvent e) {
		e.consume();
		int notches = e.getWheelRotation();
		if (notches < 0) {
			for (Mouse mouse : mouses) mouse.setScrollUp(-notches);
		}
		else {
			for (Mouse mouse : mouses) mouse.setScrollDown(notches);
		}
	}

}
