package me.sven.libz.tiled;

import java.util.ArrayList;

public class TiledLayer {
	
	public String name;
	public ArrayList<Integer> tiles;
	
	public TiledLayer(String name, ArrayList<Integer> tiles) {
		this.name = name;
		this.tiles = tiles;
	}
}
