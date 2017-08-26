package me.sven.libz.tiled;

import java.awt.Color;

public class TiledObject {
	public String name, type;
	public int id;
	public Float x, y, width, height;
	public Color color;

	public TiledObject(int id, String name, String type, Color color, Float x, Float y, Float width, Float height) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.color = color;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
}
