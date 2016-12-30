package me.winspeednl.libz.graphics;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import me.winspeednl.libz.core.GameCore;

/**
 * Screen class
 * The game window is created here.
 * 
 * @author      Sven Arends <sarends98@gmail.com>
 * @version     1.0
 * @since       0.1
 */
public class Screen {
	
	private JFrame frame;
	private Canvas canvas;
	private Graphics graphics;
	private BufferStrategy bufferStrategy;
	
	/**
	 * Constructor, used in GameCore.java, init()
	 * @param GameCore Used in this class
	 */
	public Screen(GameCore gc) {
		canvas = new Canvas();
		frame = new JFrame(gc.getTitle());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Dimension size = new Dimension((int)gc.getWidth(), (int)gc.getHeight());
		canvas.setPreferredSize(size);
		canvas.setMaximumSize(size);
		canvas.setMaximumSize(size);
		canvas.setSize(size);
		
		if (gc.isFullscreen()) frame.setUndecorated(true);
		else frame.setUndecorated(!gc.isDecorated());
		
		frame.setLayout(new BorderLayout());
		frame.add(canvas, BorderLayout.CENTER);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.toFront();
		frame.requestFocus();
		
		canvas.createBufferStrategy(3);
		bufferStrategy = canvas.getBufferStrategy();
		graphics = bufferStrategy.getDrawGraphics();
		
		canvas.requestFocus();
	}
	
	/**
	 * Clear the screen
	 * @param GameCore
	 */
	public void clear(GameCore gc) {
		graphics.setColor(Color.BLACK);
		graphics.fillRect(0, 0, gc.getWidth(), gc.getHeight());
	}
	
	/**
	 * Update the screen
	 */
	public void update() {
		bufferStrategy.show();
		Toolkit.getDefaultToolkit().sync();
	}
	
	/**
	 * Destroy everything 
	 */
	public void cleanup() {
		bufferStrategy.dispose();
		graphics.dispose();
		frame.dispose();
	}
	
	/**
	 * Get the graphics to draw on
	 * @return Graphics
	 */
	protected Graphics getGraphics() {
		return graphics;
	}

	/**
	 * Get the canvas element
	 * @return Canvas
	 */
	public Canvas getCanvas() {
		return canvas;
	}
}