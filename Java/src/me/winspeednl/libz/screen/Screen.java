package me.winspeednl.libz.screen;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import me.winspeednl.libz.core.GameCore;

public class Screen {
	
	private JFrame frame;
	private Canvas canvas;
	private BufferedImage image;
	private Graphics graphics;
	private BufferStrategy bufferStrategy;
	
	public Screen(GameCore gc) {
		
		canvas = new Canvas();

		frame = new JFrame(gc.getTitle());
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		if (gc.isFullscreen()) {
			GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
			Dimension size = new Dimension((int)env.getMaximumWindowBounds().getWidth(), (int)env.getMaximumWindowBounds().getHeight());
			gc.setWidth(size.width);
			gc.setHeight(size.height);
			gc.lockScreenSize();

			image = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
			
			canvas.setPreferredSize(size);
			canvas.setMaximumSize(size);
			canvas.setMaximumSize(size);
			canvas.setSize(size);
			
			frame.setUndecorated(true);
		} else {
			image = new BufferedImage(gc.getWidth(), gc.getHeight(), BufferedImage.TYPE_INT_ARGB);

			Dimension size = new Dimension((int)(gc.getWidth() * gc.getScale()), (int)(gc.getHeight() * gc.getScale()));
			canvas.setPreferredSize(size);
			canvas.setMaximumSize(size);
			canvas.setMaximumSize(size);
			canvas.setSize(size);
			
			if (!gc.isDecorated()) frame.setUndecorated(true);
		}
		
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
	
	public void update() {
		graphics.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
		bufferStrategy.show();
	}
	
	public void cleanup() {
		bufferStrategy.dispose();
		graphics.dispose();
		image.flush();
		frame.dispose();
	}

	public Canvas getCanvas() {
		return canvas;
	}
	
	public JFrame getFrame() {
		return frame;
	}

	public BufferedImage getImage() {
		return image;
	}
}
