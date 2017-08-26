package me.sven.libz.fx;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import me.sven.libz.core.Settings;
import me.sven.libz.graphics.Render;
import me.sven.libz.util.Vector2d;

public class Emitter {
	
	private Vector2d loc;
	private ArrayList<Particle> particles = new ArrayList<Particle>();
	public boolean collide = true;
	
	public Emitter(double x, double y) {
		loc = new Vector2d(x, y);
	}
	
	public void update() {
		ArrayList<Particle> deadParticles = new ArrayList<Particle>();
		
		for (Particle particle : particles) {
			if (particle.update()) if (!deadParticles.contains(particle)) deadParticles.add(particle);
					
			if (collide) {
				if(particle.getLoc().x <= 0){
					particle.getLoc().x = 0;
					particle.getVel().x *= -.8 - random(.1);
				}
				if(particle.getLoc().x >= Settings.width){
					particle.getLoc().x = Settings.width;
					particle.getVel().x *= -.8 - random(.1);
				}
				if(particle.getLoc().y <= 0){
					particle.getLoc().y = 0;
					particle.getVel().y *= -.8 - random(.1);
				}
				if(particle.getLoc().y >= Settings.height){
					particle.getLoc().y = Settings.height;
					particle.getVel().y *= -.8 - random(.1);
				}
			}
		}
		
		for (Particle particle : deadParticles) particles.remove(particle);
	}
	
	public void render(Render r) {
		for (Particle particle : particles) particle.render(r);
	}
	
	// Add particle
	
	public void addFireParticle(int spread) {
		/*Color c = (int)(Math.random() * 10) > 5 ? new Color(226, 88, 34) : new Color(226, 120, 34);
		if ((int)(Math.random() * 10) > 5) c = new Color(226, 152, 34);
		if ((int)(Math.random() * 10) > 5) c = new Color(226, 184, 34);
		if ((int)(Math.random() * 10) > 5) c = new Color(226, 56, 34);*/
		
		Color c = new Color(254, 184, 28);
		if ((int)(Math.random() * 10) > 5) c = new Color(148, 41, 0);
		if ((int)(Math.random() * 10) > 5) c = new Color(244, 130, 4);
		if ((int)(Math.random() * 10) > 5) c = new Color(224, 101, 0);
		if ((int)(Math.random() * 10) > 5) c = new Color(217, 92, 2);
		if ((int)(Math.random() * 10) > 5) c = new Color(226, 152, 34);
		if ((int)(Math.random() * 10) > 5) c = new Color(226, 184, 34);
		if ((int)(Math.random() * 10) > 5) c = new Color(226, 78, 34);
		
		
		Particle p = new Particle(loc.x - spread / 2 + new Random().nextInt(spread), loc.y - spread / 2 + new Random().nextInt(spread), 0, 0, 0, 0, c);
		p.setVel(random(.5), random(.5));
		p.setAcc(0, -randomPlus(.04) - .02);
		p.setLife(randomPlus(150) + 550);
		p.setSize(16, 16);
		p.setShape(p.CIRCLE);
		p.setMaxSize(25, 25);
		p.setGrowth(-.1, -.1);
		p.setSizeDeault(false);
		particles.add(p);
	}
	
	public void addSparkParticle(double spread) {
		Color c = random(32) > 16 ? new Color(255, 212, 0) : new Color(255, 128, 0);
		Particle p = new Particle(loc.x, loc.y, 0, 0, 0, 0, c);
		p.setVel(random(spread), random(spread));
		p.setAcc(0,  randomPlus(.2) + .1);
		p.setLife(randomPlus(150) + 150);
		p.setSize(5, 25);
		p.setMaxSize(25, 25);
		p.setGrowth(-randomPlus(.2) - .5, -randomPlus(.2) - .5);
		p.setSizeDeault(true);
		particles.add(p);
	}
	
	public void addWaterParticle(double size, double moveX, double moveY, int life) {
		Color c = random(32) > 16 ? new Color(77, 116, 255) : new Color(77, 146, 255);
		Particle p = new Particle(loc.x, loc.y, 0, 0, 0, 0, c);
		p.setVel(moveX - random(1), moveY - random(1));
		p.setAcc(0, .3);
		p.setLife(randomPlus(life) + life);
		p.setSize(size, size);
		p.setShape(p.CIRCLE);
		p.setMaxSize(size, size);
		p.setGrowth(-.1, -.1);
		p.setSizeDeault(true);
		particles.add(p);
	}
	
	public void addCircleParticle(double vel, Color color) {
		Particle p = new Particle(loc.x, loc.y, 0, 0, 0, 0, color);
		p.setVel(random(vel), random(vel));
		p.setAcc(random(.1), random(.1));
		p.setLife(randomPlus(50) + 50);
		p.setSize(25, 25);
		p.setShape(p.CIRCLE);
		p.setMaxSize(25, 25);
		p.setGrowth(-.4, -.4);
		p.setSizeDeault(true);
		particles.add(p);
	}
	
	public void addMagicParticle(Color color) {
		Particle p = new Particle(loc.x, loc.y, 0, 0, 0, 0, color);
		p.setVel(random(1), random(1));
		p.setAcc(random(.1), random(.1));
		p.setLife(randomPlus(15) + 15);
		p.setSize(15, 15);
		p.setShape(p.CIRCLE);
		p.setMaxSize(15, 15);
		p.setGrowth(-.8, -.8);
		p.setSizeDeault(true);
		particles.add(p);
	}
	
	public void addLineParticle(Point start, Point end, Color color, int stepPrecision, double spread, double size, int life) {
		if (start.x - end.x == 0 && !(start.y - end.y == 0)) { // Vertical
			Particle p = new Particle(start.x, start.y, random(spread), random(spread), size, life, color);
			particles.add(p);
			p = new Particle(end.x, end.y, random(spread), random(spread), size, life, color);
			particles.add(p);
			int y = end.y - start.y;
			if (y > 0) {
				for (int i = 0; i < y; i += stepPrecision) {
					p = new Particle(start.x, start.y + i, random(spread), random(spread), size, life, color);
					particles.add(p);
				}
			} else {
				y = start.y - end.y;
				for (int i = 0; i < y; i += stepPrecision) {
					p = new Particle(start.x, end.y + i, random(spread), random(spread), size, life, color);
					particles.add(p);
				}
			}
			return;
		}
		if (start.y - end.y == 0 && !(start.x - end.x == 0)) { // Horizontal
			Particle p = new Particle(start.x, start.y, random(spread), random(spread), size, life, color);
			particles.add(p);
			p = new Particle(end.x, end.y, random(spread), random(spread), size, life, color);
			particles.add(p);
			int x = end.x - start.x;
			if (x > 0) {
				for (int i = 0; i < x; i += stepPrecision) {
					p = new Particle(start.x + i, start.y, random(spread), random(spread), size, life, color);
					particles.add(p);
				}
			} else {
				x = start.x - end.x;
				for (int i = 0; i < x; i += stepPrecision) {
					p = new Particle(end.x + i, start.y, random(spread), random(spread), size, life, color);
					particles.add(p);
				}
			}
			return;
		}
		double m = (double)(start.y - end.y) / (double)(start.x - end.x);
		double b = start.x * m - start.y;
		double x = start.x;
		double y = start.y;
		double dis = Math.sqrt(Math.pow((x - end.x), 2) + Math.pow((y - end.y), 2));
		//double staticDis = Math.sqrt(Math.pow((x - end.x), 2) + Math.pow((y - end.y), 2));
		while (!(dis < 4)) {
			dis = Math.sqrt(Math.pow((x - end.x), 2) + Math.pow((y - end.y), 2));
			if (x % stepPrecision == 0) {
				Particle p = new Particle(x, y, random(spread), random(spread), size, life, color);
				particles.add(p);
			}
			y = ((start.y - end.y) * x) / (start.x - end.x) - b;
			if (end.x > start.x) x += 1;
			else x -= 1;
			if (x < 0 || y < 0 || x > Settings.width || y > Settings.height) break;
		}
		Particle p = new Particle(end.x, end.y, random(spread), random(spread), size, life, color);
		particles.add(p);
	}
	
	public void addParticle(double x, double y, double dx, double dy, double size, int life, Color color) {
		Particle p = new Particle(x, y, dx, dy, size, life, color);
		particles.add(p);
	}
	
	
	public void addParticle(Particle particle) {
		particle.setLoc(particle.getLoc().x + loc.x, particle.getLoc().y + loc.y);
		particles.add(particle);
	}
	
	public double random( double num ){
		return (num * 2) * new Random().nextDouble() - num;
	}

	public double randomPlus( double num ){
		double temp = ((num * 2)  * new Random().nextDouble()) - num;
		if( temp < 0 )
			return temp * -1;
		else
			return temp;
	}
	
	// Getter and Setters
	
	public void setLoc(double x,  double y){
		loc.x = x;
		loc.y = y;
	}
	
	public Vector2d getLoc(){
		return loc;
	}

	public int particles() {
		return particles.size();
	}
}
