package me.winspeednl.libz.entities;

import me.winspeednl.libz.core.GameCore;
import me.winspeednl.libz.screen.Render;

public abstract class Entity {
	public int x, y, w, h, health, lookDirection, kills, deaths, damage;
	public boolean dead = false, canHit = false, isPlayer = false, isEnemy = false, isSolid = false, canExplode = false, explode = false;

	public abstract void update(GameCore gc, float dt);
	public abstract void render(GameCore gc, Render r);
}
