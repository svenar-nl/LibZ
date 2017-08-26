package me.sven.libz.screen;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;

import me.sven.libz.core.GameCore;
import me.sven.libz.core.Settings;
import me.sven.libz.graphics.Render;
import me.sven.libz.input.Mouse;

public class MainMenu extends Scene {
	
	private Mouse mouse;
	private Font titleFont, mainfont, notificationFont;
	private ArrayList<MenuItem> items;
	
	public MainMenu(GameCore gc) {
		super(gc, "MainMenu");
		useCamera = false;
	}

	public void init(GameCore gc) {
		mouse = new Mouse(gc);
		titleFont = new Font(Font.SANS_SERIF, Font.BOLD, 80);
		mainfont = new Font(Font.SANS_SERIF, Font.ITALIC, 30);
		notificationFont = new Font(Font.SANS_SERIF, Font.PLAIN, 20);
		gc.getScreen().hideMouse();
		
		emptyMenu();
		items.add(new MenuItem(gc, "Play") {
			public void onClick() {
				gc.setActiveScene(gc.getScene("Game"));
			}
		});
		items.add(new MenuItem(gc, "About") {
			public void onClick() {
				gc.setActiveScene(gc.getScene("About"));
			}
		});
		items.add(new MenuItem(gc, "Quit") {
			public void onClick() {
				System.exit(0);
			}
		});
	}

	public void update(GameCore gc) {
		for (MenuItem item : items) {
			Rectangle itemRect = new Rectangle(item.x, item.y, item.w, item.h);
			if (itemRect.contains(mouse.getX(), mouse.getY())) {
				item.color = Color.GRAY;
				if (mouse.isLeftPressed()) item.onClick();
			} else item.color = Color.WHITE;
		}
	}

	public void render(GameCore gc, Render r) {
		Graphics2D g = (Graphics2D) r.getGraphics();
		g.setColor(Color.WHITE);
		g.setStroke(new BasicStroke(1));
		g.setFont(titleFont);
		g.drawString(Settings.title, Settings.width / 2 - g.getFontMetrics().stringWidth(Settings.title) / 2, Settings.height / 5);
		
		g.setFont(mainfont);
		for (int i = 0; i < items.size(); i++) {
			String str = items.get(i).name;
			g.setColor(items.get(i).color);
			int x = Settings.width / 2 - g.getFontMetrics().stringWidth(str) / 2;
			int y = Settings.height / 3 + (i * (g.getFontMetrics().getHeight() + 10));
			g.drawString(str, x, y);
			g.drawLine(Settings.width / 2 - Settings.width / 8, y, Settings.width / 2 + Settings.width / 8, y);
			if (items.get(i).x == 0) {
				items.get(i).x = x;
				items.get(i).y = y - g.getFontMetrics().getHeight();
				items.get(i).w = g.getFontMetrics().stringWidth(str);
				items.get(i).h = g.getFontMetrics().getHeight();
			}
		}
		
		g.setFont(notificationFont);
		
		g.setColor(Color.WHITE);
		g.drawOval(mouse.getX() - 10, mouse.getY() - 10, 20, 20);
		g.fillOval(mouse.getX() - 3, mouse.getY() - 3, 6, 6);
	}

	public void emptyMenu() {
		items = new ArrayList<MenuItem>();
	}
	
	public void addItem(MenuItem item) {
		items.add(item);
	}

	public MenuItem getItem(int i) {
		return items.get(0);
	}

	public void setItem(int i, MenuItem menuItem) {
		items.set(i, menuItem);
	}
}
