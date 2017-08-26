package me.sven.libz.tiled;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import me.sven.libz.core.GameCore;
import me.sven.libz.core.Settings;
import me.sven.libz.graphics.Render;
import me.sven.libz.image.Sprite;

public class TiledMap {
	
	private TMXParser tmxParser;
	private TSXParser tsxParser;
	private boolean debug = false;
	
	public TiledMap(String path) {
		load(path);
	}
	
	public TiledMap(String path, boolean debug) {
		this.debug = debug;
		load(path);
	}
	
	private void load(String path) {
		File file = new File(path);
		tmxParser = new TMXParser(file, debug);
		tsxParser = new TSXParser(tmxParser);
		
		for (Map.Entry<Integer, AnimationSprite> animSpriteKV : tsxParser.getAnimatedSprites().entrySet()) {
			AnimationSprite animSprite = animSpriteKV.getValue();
			animSprite.start();
		}
		
	}
	
	public void renderBackground(Render r, boolean screen) {
		if (tmxParser.getBackgroundColor() != null) {
			r.getGraphics().setColor(tmxParser.getBackgroundColor());
			if (!screen) r.getGraphics().fillRect(0, 0, tmxParser.getMapWidth() * tmxParser.getTileWidth(), tmxParser.getMapHeight() * tmxParser.getTileHeight());
			else r.getGraphics().fillRect(0, 0, Settings.width, Settings.height);
		}
	}
	
	public void update() {
		for (Map.Entry<Integer, AnimationSprite> animSpriteKV : tsxParser.getAnimatedSprites().entrySet()) {
			AnimationSprite animSprite = animSpriteKV.getValue();
			animSprite.update();
		}
	}
	
	public void render(String layerName, GameCore gc, Render r) {
		Layer layer = null;
		for (Layer l : tmxParser.getLayers()) {
			if (l.name.equalsIgnoreCase(layerName)) {
				layer = l;
				break;
			}
		}
		
		if (layer != null) {
			int xOffset = tmxParser.getXOffset();
			int yOffset = tmxParser.getYOffset();
			int i = 0;
			for (int y = 0; y < tmxParser.getMapHeight(); y++) {
				for (int x = 0; x < tmxParser.getMapWidth(); x++) {
					int tileX = xOffset + x * tmxParser.getTileWidth();
					int tileY = yOffset + y * tmxParser.getTileHeight();
					if (i != tmxParser.getMapWidth() * tmxParser.getMapHeight()) {
						int gid = layer.getGid(i);
						
						if (!tsxParser.getAnimatedSprites().containsKey(gid)) {
						//	if (tileX + tmxParser.getTileWidth() > 0) {
						//		if (tileY + tmxParser.getTileHeight() > 0) {
						//			if (tileX + gc.getCamera().getOffset().x < Settings.width) {
						//				if (tileY < Settings.height) {
											if (gid > 0) r.getGraphics().drawImage(tsxParser.getSprite(gid).image, tileX, tileY, null);
						//				}
						//			}
						//		}
						//	}
						} else {
						//	if (tileX + tmxParser.getTileWidth() > 0) {
						//		if (tileY + tmxParser.getTileHeight() > 0) {
						//			if (tileX + gc.getCamera().getOffset().x < Settings.width) {
						//				if (tileY < Settings.height) {
											if (gid > 0) {
												Sprite sprite = tsxParser.getAnimatedSprites().get(gid).getSprite();
												r.getGraphics().drawImage(sprite.image, tileX, tileY, null);
						//					}
						//				}
						//			}
						//		}
							}
						}
					}
					
					i++;
				}
			}
		}
	}

	public ArrayList<TiledObject> getObjectGroup(String objectGroupName) {
		Objectgroup objectGroup = tmxParser.getObjectgroups().get(objectGroupName);
		if (objectGroup == null) return new ArrayList<TiledObject>();
		return objectGroup.objects;
	}
}
