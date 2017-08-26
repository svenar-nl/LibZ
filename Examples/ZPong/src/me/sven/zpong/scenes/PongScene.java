package me.sven.zpong.scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import me.sven.libz.audio.Sound;
import me.sven.libz.core.GameCore;
import me.sven.libz.core.Settings;
import me.sven.libz.fx.Emitter;
import me.sven.libz.graphics.Render;
import me.sven.libz.input.Key;
import me.sven.libz.screen.Scene;
import me.sven.zpong.obj.Ball;
import me.sven.zpong.obj.Paddle;

public class PongScene extends Scene {
	
	private Key escKey;
	private Paddle player, enemy;
	private Ball ball;
	private Emitter emitter;
	private int centerBarWidth = 8;
	private Color color = Color.WHITE;
	private Sound paddleHit, wallHit, die;
	
	public PongScene(GameCore gc, String sceneName) {
		super(gc, sceneName);
	}
	
	public void reset(GameCore gc) {
		player = new Paddle(gc, true, false, 20, 120, 8, 20);
		enemy = new Paddle(gc, false, true, 20, 120, 8, 20);
		ball = new Ball(30, 9, true);
		emitter = new Emitter(0, 0);
		
		paddleHit = new Sound("/paddleHit.wav");
		wallHit = new Sound("/wallHit.wav");
		die = new Sound("/die.wav");
	}

	public void init(GameCore gc) {
		escKey = new Key(gc, KeyEvent.VK_ESCAPE);
		reset(gc);
	}

	public void update(GameCore gc) {
		if (escKey.isPressed()) {
			reset(gc);
			gc.setPreviousScene();
		}
		
		player.update(ball.getPos());
		enemy.update(ball.getPos());
		ball.update();
		emitter.update();
		
		if (collides(player.getRect(), ball.getRect())) {
			if (player.isLeft()) ball.goRight();
			else ball.goLeft();
			emitter.setLoc(ball.getPos().x, ball.getPos().y);
			for (int i = 0; i < 20; i++) emitter.addMagicParticle(Color.WHITE);
			paddleHit.stop();
			paddleHit.play();
		}
		
		if (collides(enemy.getRect(), ball.getRect())) {
			if (enemy.isLeft()) ball.goRight();
			else ball.goLeft();
			emitter.setLoc(ball.getPos().x, ball.getPos().y);
			for (int i = 0; i < 20; i++) emitter.addMagicParticle(Color.WHITE);
			paddleHit.stop();
			paddleHit.play();
		}
		
		if (ball.getPos().y <= 0) {
			ball.goDown();
			wallHit.stop();
			wallHit.play();
		}
		if (ball.getPos().y + ball.getSize() >= Settings.height) {
			ball.goUp();
			wallHit.stop();
			wallHit.play();
		}
		
		if (ball.isOffscreenLeft()) {
			if (player.isLeft()) enemy.addPoint();
			else player.addPoint();
			emitter.addLineParticle(new Point(0, 0), new Point(0, Settings.height), Color.WHITE, 2, 1, 1, 40);
			ball.reset(false);
			die.stop();
			die.play();
		}
		
		if (ball.isOffscreenRight()) {
			if (!player.isLeft()) enemy.addPoint();
			else player.addPoint();
			emitter.addLineParticle(new Point(Settings.width, 0), new Point(Settings.width, Settings.height), Color.WHITE, 2, 1, 1, 40);
			ball.reset(true);
			die.stop();
			die.play();
		}
	}

	public void render(GameCore gc, Render r) {
		Graphics2D g = (Graphics2D) r.getGraphics();
		
		g.setColor(color);
		
		player.render(g);
		enemy.render(g);
		ball.render(g);
		emitter.render(r);
		
		Font font = new Font(Font.MONOSPACED, Font.BOLD, 100);
		g.setFont(font);
		g.drawString("" + player.getPoints(), Settings.width / 4, 120);
		g.drawString("" + enemy.getPoints(), Settings.width - Settings.width / 4, 120);
		
		g.fillRect(Settings.width / 2 - centerBarWidth / 2, 0, centerBarWidth, Settings.height);
	}
	
	public boolean collides(Rectangle object1, Rectangle object2) {
		return object1.intersects(object2);
	}
}
