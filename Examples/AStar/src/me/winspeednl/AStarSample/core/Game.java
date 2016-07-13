/*
* Written by: Sven Arends
* On: 12-07-2016 (dd-mm-yyyy)
* This is a LibZ Example
* 
* THIS CODE IS PUBLIC DOMAIN
*/
package me.winspeednl.AStarSample.core;

import java.util.ArrayList;

import me.winspeednl.libz.core.GameCore;
import me.winspeednl.libz.core.LibZ;
import me.winspeednl.libz.image.Sprite;
import me.winspeednl.libz.level.Level;
import me.winspeednl.libz.level.Tile;
import me.winspeednl.libz.pathfinding.AStar;
import me.winspeednl.libz.pathfinding.Node;
import me.winspeednl.libz.screen.Render;

public class Game extends LibZ {
	
	private Level level;
	private AStar aStar;
	private Sprite floorSprite, wallSprite, playerSprite, enemySprite, nodeSprite;
	private InputHandler inputHandler;
	
	private String[] mapData;
	private Entity player, enemy;
	private Tile floor, wall;
	Tile[] mapTiles;
	private ArrayList<Tile> nodes = new ArrayList<Tile>();
	
	public void init(GameCore gc) {
			level = new Level();
			aStar = new AStar();
			inputHandler = new InputHandler(gc.getInput());
			
			floorSprite = new Sprite("/floor.png", 0, 0, 32, 32);
			wallSprite = new Sprite("/wall.png", 0, 0, 32, 32);
			
			playerSprite = new Sprite("/player.png", 0, 0, 32, 32);
			enemySprite = new Sprite("/enemy.png", 0, 0, 32, 32);
			nodeSprite = new Sprite("/node.png", 0, 0, 32, 32);
			
			player = new Entity(playerSprite, 0, 0, 32);
			enemy = new Entity(enemySprite, 256, 192, 32);
			
			player.setSpeed(4);
			enemy.setSpeed(4);
			
			floor = level.createTile(floorSprite, 0, 0, 32, 32, false);
			wall = level.createTile(wallSprite, 0, 0, 32, 32, true);
			
			inputHandler.setSpeed(player.getSpeed());
			
			mapData = new String[] {
					"0", "1", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", 
					"0", "1", "0", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "0", 
					"0", "1", "0", "1", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "1", "0", 
					"0", "1", "0", "1", "0", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "0", "1", "0", 
					"0", "1", "0", "1", "0", "1", "0", "0", "0", "0", "0", "1", "0", "0", "0", "1", "1", "0", "0", "0", "1", "0", "1", "0", 
					"0", "1", "0", "1", "0", "1", "0", "1", "1", "1", "0", "1", "0", "1", "0", "0", "1", "0", "1", "0", "1", "0", "1", "0", 
					"0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", 
					"0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", 
					"0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "0", "1", "0", "1", "0", "1", "0", "1", "0", 
					"0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "0", "0", "0", "1", "0", "1", "1", "0", "1", "0", "1", "0", "1", "0", 
					"0", "1", "0", "1", "0", "1", "0", "1", "0", "1", "1", "1", "1", "1", "0", "1", "1", "0", "1", "0", "1", "0", "1", "0", 
					"0", "1", "0", "1", "0", "1", "0", "1", "0", "0", "0", "0", "0", "0", "0", "1", "1", "0", "1", "0", "1", "0", "1", "0", 
					"0", "1", "0", "1", "0", "1", "0", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "0", "1", "0", "1", "0", "1", "0", 
					"0", "1", "0", "1", "0", "1", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "1", "0", "0", "0", "1", "0", 
					"0", "1", "0", "1", "0", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "1", "0", 
					"0", "0", "0", "1", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", 
			};
			
			mapTiles = new Tile[2];
			mapTiles[0] = floor;
			mapTiles[1] = wall;
			
			level.loadMap(mapData, 24, 16, mapTiles, 32);
			aStar.useDiagonal(false);
	}
	
	public void update(GameCore gc) {
		inputHandler.update(player);
		player.update(gc);
		enemy.update(gc);
		
		int[][] colls = aStar.getCollisionMap(level);
		
		ArrayList<Node> path = aStar.calculate(level.getWidth(), level.getHeight(), (player.x+player.w/2)/player.w, (player.y+player.h/2)/player.h, (enemy.x+enemy.w/2)/enemy.w, (enemy.y+enemy.h/2)/enemy.h, colls);
		if (path != null) {
			for (int i = 0; i < path.size(); i++) {
				Node node = path.get(i);
				int x = node.getX() * 32;
				int y = node.getY() * 32;
				int maxNodesCount = 50;
				if (nodes.size() < maxNodesCount)
					nodes.add(new Tile(nodeSprite, x, y, 32, 32, false));
			}
			enemy.setNodes(nodes);
		}
	}
	
	public void render(GameCore gc, Render r) {
		
		level.render(gc, r);
		
		for (int i = 0; i < nodes.size(); i++) {
			Tile tile = nodes.get(i);
			for (int x = 0; x < tile.getWidth(); x++) {
				for (int y = 0; y < tile.getHeight(); y++) {
					if (tile.getPixels()[x + y * tile.getWidth()] != gc.getSpriteBGColor())
						r.setPixel(tile.getX() + x, tile.getY() + y, tile.getPixels()[x + y * tile.getWidth()]);
				}
			}
		}
		
		nodes = new ArrayList<Tile>();
		
		player.render(gc, r);
		enemy.render(gc, r);
	}
	
	public static void main(String[] args) {
		GameCore gc = new GameCore(new Game());
		gc.setName("A* Example");
		gc.setWidth(786);
		gc.setHeight(512);
		gc.setScale(1f);
		gc.setSpriteBGColor(0xFF000000);
		gc.start();
	}
}
