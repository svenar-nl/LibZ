package me.sven.libz.graphics;

import java.awt.Color;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Splash {
	
	private JFrame frame;
	private Image img;
	
	public Splash() {
		frame = new JFrame("Powered by LibZ");
		try {
			img = ImageIO.read(getClass().getResourceAsStream("/LibZ_Splash.png"));
		} catch (IOException e) {
			return;
		}
		
		try {
			frame.setContentPane(new JLabel(new ImageIcon(img)));
		} catch (Exception e) {
			return;
		}
		frame.setSize(img.getWidth(null), img.getHeight(null));
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setUndecorated(true);
		frame.setAlwaysOnTop(true);
		frame.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.toFront();
		frame.requestFocus();
	}
	
	public void close() {
		frame.setVisible(false);
	}
}
