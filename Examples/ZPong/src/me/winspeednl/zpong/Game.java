package me.winspeednl.zpong;

import me.winspeednl.libz.core.GameCore;
import me.winspeednl.libz.core.LibZ;
import me.winspeednl.libz.screen.Render;
import me.winspeednl.libz.screen.Font;

public class Game extends LibZ {
	
	public Paddle player, enemy;
	public Ball ball;
	public int pScore = 0, eScore = 0;
	
	public static void main(String args[]) {
		GameCore gc = new GameCore(new Game());
		gc.setName("ZPong | winspeednl");
		gc.setScale(3f);
		gc.setWidth(320);
		gc.setHeight(180);
		gc.setSpriteBGColor(0xFF000000);
		gc.start();
	}
	
	public void init(GameCore gc) {
		player = new Paddle(this, gc, 0, 0, 8, 32, true);
		enemy = new Paddle(this, gc, gc.getWidth() - 8, 0, 8, 32, false);
		ball = new Ball(this, gc, gc.getWidth()/2-4, gc.getHeight()/2-4, 8, 8);
	}
	
	public void update(GameCore gc) {
		if (player != null)
			player.update(gc);
		if (enemy != null)
			enemy.update(gc);
		if (ball != null)
			ball.update(gc);
	}
	
	public void render(GameCore gc, Render r) {
		if (player != null)
			player.render(gc, r);
		if (enemy != null)
			enemy.render(gc, r);
		if (ball != null)
			ball.render(gc, r);
		
		r.drawString(pScore + " / " + eScore, 0xFFFFFFFF, gc.getWidth()/2-25, 10, Font.STANDARDX2);
	}
}