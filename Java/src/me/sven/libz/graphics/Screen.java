package me.sven.libz.graphics;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import me.sven.libz.core.GameCore;
import me.sven.libz.core.Settings;

public class Screen {
	
	private JFrame frame;
	private Canvas canvas;
	private Graphics graphics;
	private BufferStrategy bufferStrategy;

	private void setupFrame(Dimension windowSize) {
		canvas.setPreferredSize(windowSize);
		canvas.setMinimumSize(windowSize);
		canvas.setMaximumSize(windowSize);
		canvas.setSize(windowSize);
		
		frame.setTitle(Settings.title + " " + Settings.version);
		frame.getContentPane().setBackground(Color.BLACK);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setLocationRelativeTo(null);
		frame.setLayout(new BorderLayout());
		frame.add(canvas, BorderLayout.CENTER);
		frame.requestFocus();
		canvas.requestFocus();
		frame.setVisible(false);
		
		canvas.createBufferStrategy(3);
		bufferStrategy = canvas.getBufferStrategy();
		graphics = bufferStrategy.getDrawGraphics();
	}
	
	public Screen() {
		canvas = new Canvas();
		frame = new JFrame(Settings.title + " " + Settings.version);
		Dimension canvasSize = new Dimension(Settings.width, Settings.height);
		
		if (Settings.fullscreen) {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    GraphicsDevice gs = ge.getDefaultScreenDevice();
		    Rectangle bounds = gs.getDefaultConfiguration().getBounds();
		    if (gs.isFullScreenSupported()) {
		    
				frame.setUndecorated(true);
				frame.setAlwaysOnTop(true);
				frame.setResizable(true);
				frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
				
			    canvasSize = bounds.getSize();
			    Settings.width = (int) canvasSize.getWidth();
			    Settings.height = (int) canvasSize.getHeight();
			    
			    frame.setSize(Settings.width, Settings.height);
			    gs.setFullScreenWindow(frame);
		    }
		} else {
			frame.setLocationRelativeTo(null);
			frame.setSize(Settings.width, Settings.height);
			frame.setUndecorated(!Settings.decorated);
			if (Settings.resizable) {
				frame.addComponentListener(new ComponentListener() {
				    public void componentResized(ComponentEvent e) {
				    	Dimension canvasSize = new Dimension(e.getComponent().getWidth(), e.getComponent().getHeight()); 
				    	Settings.width = e.getComponent().getWidth();
				    	Settings.height = e.getComponent().getHeight();
				    	canvas.setPreferredSize(canvasSize);
						canvas.setMinimumSize(canvasSize);
						canvas.setMaximumSize(canvasSize);
						canvas.setSize(canvasSize);
						canvas.createBufferStrategy(3);
						bufferStrategy = canvas.getBufferStrategy();
						graphics = bufferStrategy.getDrawGraphics();
				    }
					public void componentHidden(ComponentEvent arg0) {}
					public void componentMoved(ComponentEvent arg0) {}
					public void componentShown(ComponentEvent arg0) {}
				});
			} else frame.setResizable(false);
		}
		setupFrame(canvasSize);
	}
	
	public void clear() {
		graphics.setColor(Color.BLACK);
		graphics.fillRect(0, 0, Settings.width, Settings.height);
	}
	
	public void update() {
		try {
			bufferStrategy.show();
		} catch (Exception e) {}
		Toolkit.getDefaultToolkit().sync();
	}
	
	public void cleanup() {
		bufferStrategy.dispose();
		graphics.dispose();
		frame.dispose();
	}
	
	public Graphics getGraphics() {
		return graphics;
	}
	
	public Canvas getCanvas() {
		return canvas;
	}
	
	public void updateTitle(GameCore gc) {
		if (Settings.debug) frame.setTitle(Settings.title + " " + Settings.version + " | " + Settings.width + "x" + Settings.height + " | " + gc.getFPS() + " FPS ");
	}
	
	public void showMouse() {
		frame.setCursor(Cursor.getDefaultCursor());
	}
	
	public void hideMouse() {
		frame.setCursor(frame.getToolkit().createCustomCursor(new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "null"));
	}

	public void show() {
		frame.setVisible(true);
	}
}
