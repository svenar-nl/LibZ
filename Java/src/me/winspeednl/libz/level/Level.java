package me.winspeednl.libz.level;

import java.util.ArrayList;

public class Level {
	private ArrayList<Tile> tiles;
	private ArrayList<Tile> mapTiles;
	private ArrayList<String> mapData;
	private int width, height, tileSize;
	
	public Level() {
		tiles = new ArrayList<Tile>();
		mapTiles = new ArrayList<Tile>();
		mapData = new ArrayList<String>();
	}
	
	public void loadMap(String[] mapData, int w, int h, Tile[] tileList, int s) {
		width = w;
		height = h;
		tileSize = s;
		for (Tile tile : tileList) {
			mapTiles.add(tile);
		}
		for (String data : mapData) {
			this.mapData.add(data);
		}
		
		for (int i = 0; i < mapData.length; i++) {
			Tile tile = mapTiles.get(Integer.parseInt(this.mapData.get(i)));
			int X = i * tileSize;
			int Y = 0;
			if (i != 0) {
				Y = Integer.parseInt(String.valueOf((i / width)).split(",")[0]) * tileSize;
				X -= Y * width;
			} else
				Y = 0;
			Tile newTile = createTile(tile.getSprite(), X, Y, tileSize, tileSize, tile.getRotationRadians(), tile.isSolid(), tile.isFlippedX(), tile.isFlippedY());
			tiles.add(newTile);
		}
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getTileSize() {
		return tileSize;
	}

	public void addTile(Tile tile) {
		tiles.add(tile);
	}
	
	public Tile createTile(String sprite, int x, int y, int w, int h, boolean isSolid) {
		Tile tile = new Tile(sprite, x, y, w, h, isSolid, 0, false, false);
		return tile;
	}
	
	public Tile createTile(String sprite, int x, int y, int w, int h, double rot, boolean isSolid) {
		Tile tile = new Tile(sprite, x, y, w, h, isSolid, Math.toRadians(rot), false, false);
		return tile;
	}
	
	public Tile createTile(String sprite, int x, int y, int w, int h, double rot, boolean isSolid, boolean flipX, boolean flipY) {
		Tile tile = new Tile(sprite, x, y, w, h, isSolid, Math.toRadians(rot), flipX, flipY);
		return tile;
	}
	
	public void removeTile(Tile tile) {
		tiles.remove(tile);
	}
	
	public void removeTile(int x, int y) {
		Tile tile = null;
		synchronized (tiles) {
			for (Tile t : tiles) {
				if (t.getX() == x && t.getY() == y) {
					tile = t;
					break;
				}
			}
		}
		tiles.remove(tile);
	}

	public ArrayList<Tile> getTiles() {
		return tiles;
	}
	
	public ArrayList<Tile> getMapTiles() {
		return mapTiles;
	}
}
