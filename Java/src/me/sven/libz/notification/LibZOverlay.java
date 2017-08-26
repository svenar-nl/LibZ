package me.sven.libz.notification;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import me.sven.libz.core.GameCore;
import me.sven.libz.core.Settings;
import me.sven.libz.graphics.Render;
import me.sven.libz.input.Key;

public class LibZOverlay {
	
	public boolean isOverlayOpen = false;
	
	private int firstStartBoxHeight = 100;
	private int firstStartBoxWidth = 180;
	private int firstStartBoxOpenSpeed = 6;
	private int firstStartBoxCurrHeight = 0;
	private boolean isFirstrun = true;
	private boolean firstStartBoxHasOpened = false;
	private int firstStartBoxBarHeight = 20;
	private int firstStartBoxDisplayTime = 2000;
	private long firstStartBoxStartMillis;
	private boolean firstStartBoxDelayComplete = false;
	
	private boolean isOpenKeyPressed = false;
	private Key openKey;
	
	public LibZOverlay(GameCore gc) {
		openKey = new Key(gc, KeyEvent.VK_HOME);
	}
	
	public void update() {
		if (isFirstrun) {
			if (firstStartBoxCurrHeight >= firstStartBoxHeight) {
				firstStartBoxHasOpened = true;
				if (firstStartBoxStartMillis == 0) firstStartBoxStartMillis = System.currentTimeMillis();
			}
			if (firstStartBoxCurrHeight > firstStartBoxHeight) firstStartBoxCurrHeight = firstStartBoxHeight;
			if (firstStartBoxCurrHeight < 0) {
				firstStartBoxCurrHeight = 0;
				if (firstStartBoxHasOpened) isFirstrun = false;
			}
			
			if (!firstStartBoxHasOpened) firstStartBoxCurrHeight += firstStartBoxOpenSpeed;
			else {
				if (System.currentTimeMillis() - firstStartBoxStartMillis > firstStartBoxDisplayTime) firstStartBoxDelayComplete = true;
				if (firstStartBoxDelayComplete) firstStartBoxCurrHeight -= firstStartBoxOpenSpeed;
			}
		}
		
		if (openKey.isPressed()) {
			if (!isOpenKeyPressed) {
				isOpenKeyPressed = true;
				isOverlayOpen = !isOverlayOpen;
			}
		} else isOpenKeyPressed = false;
	}
	
	public void render(Render r) {
		Graphics2D g = (Graphics2D) r.getGraphics();
		
		if (isFirstrun) {
			g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
			g.setColor(new Color(8, 75, 138));
			g.fillRect(Settings.width - firstStartBoxWidth, 0, firstStartBoxWidth, firstStartBoxCurrHeight);
			g.setColor(new Color(11, 56, 97));
			g.fillRect(Settings.width - firstStartBoxWidth, firstStartBoxCurrHeight - firstStartBoxBarHeight, firstStartBoxWidth, firstStartBoxBarHeight);
			
			g.setColor(new Color(243, 243, 243));
			g.drawString("Press home to", Settings.width - firstStartBoxWidth / 2 - g.getFontMetrics().stringWidth("Press home to") / 2, firstStartBoxCurrHeight / 2 - g.getFontMetrics().getHeight() + firstStartBoxBarHeight / 2);
			g.drawString("show the overlay", Settings.width - firstStartBoxWidth / 2 - g.getFontMetrics().stringWidth("show the overlay") / 2, firstStartBoxCurrHeight / 2 + firstStartBoxBarHeight / 2);
		}
		
		if (isOverlayOpen) {
			g.setColor(new Color(0, 0, 0, 128));
			g.fillRect(0, 0, Settings.width, Settings.height);
		}
	}
}
