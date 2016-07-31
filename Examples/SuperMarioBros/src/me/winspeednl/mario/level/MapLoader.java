package me.winspeednl.mario.level;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import me.winspeednl.mario.Game;

public class MapLoader {
	private int width, height;
	private String[] map;
	private String mapName = "1-1.map";
	public boolean isMapLoaded = false;
	
	@SuppressWarnings("resource")
	public MapLoader(Game game, String path) {
		mapName = game.currentMap + ".map";
		try {
			FileReader mapFile = new FileReader(path + File.separator + "maps" + File.separator + mapName);
			BufferedReader br = new BufferedReader(mapFile);
			
			String line1 = br.readLine();
			String line2 = br.readLine();
			
			this.width = Integer.parseInt(line1.split("x")[0]);
			this.height = Integer.parseInt(line1.split("x")[1]);
			
			map = new String[line2.split(",").length];
			for (int i = 0; i < line2.split(",").length; i++) {
				map[i] = line2.split(",")[i];
			}
			if (map.length == line2.split(",").length)
				isMapLoaded = false;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String[] getMap() {
		return map;
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public String getMapFile() {
		return mapName;
	}

	public void setMapFile(String mapName) {
		this.mapName = mapName;
	}
}