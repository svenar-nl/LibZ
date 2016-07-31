package me.winspeednl.mario.level;

import me.winspeednl.libz.core.GameCore;
import me.winspeednl.libz.level.Level;
import me.winspeednl.libz.level.Tile;
import me.winspeednl.libz.screen.Render;
import me.winspeednl.mario.Game;
import me.winspeednl.mario.Textures;

public class Map {
	int width = 212, height = 14, tileSize = 48;
	private MapLoader mapLoader;
	public Level level;
	private Textures textures;
	//private GameCore gc;
	
	public Map(Game game, GameCore gc) {
		//this.gc = gc;
		
		mapLoader = new MapLoader(game, "/home/sven/eclipse/gameDev/SuperMarioBros/res/");
		// Linux "/home/sven/eclipse/gameDev/SuperMarioBros/res/"
		// Windows "E:\\Documents\\workspace\\SuperMarioBros\\res\\"
		
		level = new Level();
		textures = new Textures();
		
		String[] mapData = mapLoader.getMap();
		Tile[] tileList = new Tile[100];
		
		tileList[0] = level.createTile(textures.UNKNOWN, 0, 0, tileSize, tileSize, false);
		tileList[1] = level.createTile(textures.AIR, 0, 0, tileSize, tileSize, false);
		tileList[2] = level.createTile(textures.GROUND, 0, 0, tileSize, tileSize, true);
		tileList[3] = level.createTile(textures.MYSTERY, 0, 0, tileSize, tileSize, true);
			
		tileList[4] = level.createTile(textures.PIPE1, 0, 0, tileSize, tileSize, true);
		tileList[5] = level.createTile(textures.PIPE2, 0, 0, tileSize, tileSize, true);
		tileList[6] = level.createTile(textures.PIPE3, 0, 0, tileSize, tileSize, true);
		tileList[7] = level.createTile(textures.PIPE4, 0, 0, tileSize, tileSize, true);
			
		tileList[8] = level.createTile(textures.BLOCK1, 0, 0, tileSize, tileSize, true);
		tileList[9] = level.createTile(textures.BLOCK2, 0, 0, tileSize, tileSize, true);
		
		tileList[10] = level.createTile(textures.FLAGPOLE, 0, 0, tileSize, tileSize, false);
		tileList[11] = level.createTile(textures.FLAGPOLEKNOB, 0, 0, tileSize, tileSize, false);
		tileList[12] = level.createTile(textures.AIR, 0, 0, tileSize, tileSize, false);
		
		tileList[13] = level.createTile(textures.CASTLESTONE, 0, 0, tileSize, tileSize, true);
		tileList[14] = level.createTile(textures.CASTLETOP, 0, 0, tileSize, tileSize, true);
		tileList[15] = level.createTile(textures.CASTLETOPSTONE, 0, 0, tileSize, tileSize, true);
		tileList[16] = level.createTile(textures.CASTLEWINDOW, 0, 0, tileSize, tileSize, true);
		tileList[17] = level.createTile(textures.CASTLEBLACKTOP, 0, 0, tileSize, tileSize, true);
		tileList[18] = level.createTile(textures.CASTLEBLACK, 0, 0, tileSize, tileSize, true);

		tileList[19] = level.createTile(textures.MYSTERYINVISIBLE, 0, 0, tileSize, tileSize, true);

		level.loadMap(mapData, mapLoader.getWidth(), mapLoader.getHeight(), tileList, tileSize);
	}
	
	public void update(Game game, GameCore gc) {
	}

	public void render(Game game, GameCore gc, Render r) {
		level.render(gc, r);
	}
	
	public Level getLevel() {
		return level;
	}
}
