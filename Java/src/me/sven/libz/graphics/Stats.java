package me.sven.libz.graphics;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import me.sven.libz.core.GameCore;
import me.sven.libz.core.Settings;
import me.sven.libz.util.LineData;

public class Stats {
	
	private ArrayList<Integer> fpsHistory;
	private ArrayList<LineData> extraData;
	private int maxWidth = 600, maxHeight = 250, stroke = 2, opacity = 100;
	private Color fpsColor = Color.YELLOW;
	
	public Stats() {
		fpsHistory = new ArrayList<Integer>();
		extraData = new ArrayList<LineData>();
		
		maxWidth = Settings.width - Settings.width / 4;
		maxHeight = Settings.height / 4;
		
		for (int i = 0; i < maxWidth; i++) {
			fpsHistory.add(0);
			for (LineData ld : extraData) ld.data.add(0);
		}
	}
	
	public void update(GameCore gc) {
		maxWidth = Settings.width - Settings.width / 4;
		maxHeight = Settings.height / 4;
		fpsHistory.add(gc.getFPS() * 10);
		
		while (fpsHistory.size() >= maxWidth) fpsHistory.remove(0);
	}
	
	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		int height = Settings.height;
		
		int prevFpsX = 0;
		int prevFpsY = height;
		
		g2d.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
		g2d.setColor(new Color(255, 0, 0, opacity));
		g2d.fillRect(0, height - maxHeight, maxWidth, maxHeight);
		
		g2d.setColor(Color.GRAY);
		g2d.setStroke(new BasicStroke(stroke));
		g2d.drawLine(stroke / 2, height - maxHeight + stroke / 2, stroke / 2, height);
		g2d.drawLine(stroke / 2, height - maxHeight / 2, maxWidth - stroke / 2, height - maxHeight / 2);
		g2d.drawLine(stroke / 2, height - maxHeight / 2 - maxHeight / 4, maxWidth - stroke / 2, height - maxHeight / 2 - maxHeight / 4);
		g2d.drawLine(stroke / 2, height - maxHeight / 4, maxWidth - stroke / 2, height - maxHeight / 4);
		g2d.setStroke(new BasicStroke(1));
		
		try {
			for (int x = 0; x < fpsHistory.size(); x++) {
				int y = fpsHistory.get(x);
				y = height - (y / (height / maxHeight));
				if (y < height - maxHeight) y = height - maxHeight;
				
				
				g2d.setColor(fpsColor);
				g2d.drawLine(prevFpsX, prevFpsY, x, y);

				prevFpsX = x;
				prevFpsY = y;
			}
			g2d.setColor(fpsColor);
			g.drawString("FPS:" + (fpsHistory.get(fpsHistory.size() - 1) / 10), prevFpsX - 45, prevFpsY - 10);
			
			for (LineData ld : extraData) {
				g2d.setColor(ld.color);
				
				if (ld.data.size() - maxWidth > 0) {
					for (int i = 0; i < ld.data.size() - maxWidth; i++) {
						ld.data.remove(ld.data.size() - 1);
					}
				}
				
				for (int x = 0; x < ld.data.size(); x++) {
					int y = ld.data.get(x);
					y = height - (y / (height / maxHeight));
					
					if (x != 0 && y != 0) {
						if (y < height - maxHeight + 20) {
							y = height - maxHeight + 20;
							g2d.drawLine(maxWidth - ld.prevX, y, maxWidth - x, y);
						} else g2d.drawLine(maxWidth - ld.prevX, ld.prevY, maxWidth - x, y);
					}
					
					ld.prevX = x;
					ld.prevY = y;
				}
				int y = height - (ld.data.get(0) / (height / maxHeight));
				if (y < height - maxHeight + 20) {
					y = height - maxHeight + 20;
					g2d.setColor(new Color(100 + new Random().nextInt(155), 0, 0));
				}
				g.drawString(ld.prefix + ": " + ld.data.get(0), maxWidth - 50, y - 10);
			}
		} catch (Exception e) {}
	}
	
	
	
	public void createLine(String prefix, Color color) {
		LineData ld = new LineData();
		ld.prefix = prefix;
		ld.color = color;
		extraData.add(ld);
	}
	
	public void deleteLine(String prefix) {
		LineData removeMe = null;
		for (int i = 0; i < extraData.size(); i++) {
			LineData ld = extraData.get(i);
			if (ld.prefix == prefix) {
				removeMe = ld;
				break;
			}
		}
		if (removeMe != null) extraData.remove(removeMe);
	}
	
	public LineData getLine(String prefix) {
		LineData line = null;
		for (int i = 0; i < extraData.size(); i++) {
			LineData ld = extraData.get(i);
			if (ld.prefix == prefix) {
				line = ld;
				break;
			}
		}
		return line;
	}
	
	public void addLineData(String prefix, int num) {
		for (int i = 0; i < extraData.size(); i++) {
			LineData ld = extraData.get(i);
			if (ld.prefix == prefix) {
				ld.data.add(0, num);
				break;
			}
		}
	}
}