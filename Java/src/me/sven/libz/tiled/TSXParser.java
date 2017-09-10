package me.sven.libz.tiled;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import me.sven.libz.image.Animation;
import me.sven.libz.image.Sprite;

public class TSXParser {
	
	private DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	private DocumentBuilder db;
	private Document doc;
	
	//private TMXParser tmxParser;
	private HashMap<String, TSXTileset> tsxTilesets = new HashMap<String, TSXTileset>();
	private HashMap<Integer, Sprite> sprites = new HashMap<Integer, Sprite>();
	private HashMap<Integer, AnimationSprite> animatedSprites = new HashMap<Integer, AnimationSprite>();
	
	public TSXParser(TMXParser tmxParser) {
		//this.tmxParser = tmxParser;
		
		try {
			db = dbf.newDocumentBuilder();
		} catch (Exception e) {}
		
		for (Tileset tileset : tmxParser.getTilesets()) {
			if (!tileset.internal) { // EXTERNAL
				File file = new File(tileset.source);
				try {
					doc = db.parse(file);
					doc.getDocumentElement().normalize();
				} catch (Exception e) {}

				TSXTileset tsxTileset = new TSXTileset(doc.getDocumentElement().getAttribute("name"), tileset.firstgid, Integer.parseInt(doc.getDocumentElement().getAttribute("tilewidth")), Integer.parseInt(doc.getDocumentElement().getAttribute("tileheight")), Integer.parseInt(doc.getDocumentElement().getAttribute("tilecount")), Integer.parseInt(doc.getDocumentElement().getAttribute("columns")));
				for (int i = 0; i < doc.getDocumentElement().getChildNodes().getLength(); i++) {
					Node child = doc.getDocumentElement().getChildNodes().item(i);
					if (child.getNodeType() == Node.ELEMENT_NODE) {
						Element eChild = (Element) child;
						String source = new File(file.getAbsolutePath().replace(file.getName(), "") + eChild.getAttribute("source")).getAbsolutePath();
						TSXImage img = new TSXImage(source, Integer.parseInt(eChild.getAttribute("width")), Integer.parseInt(eChild.getAttribute("height")));
						tsxTileset.image = img;
					}
				}
				tsxTilesets.put(tsxTileset.name, tsxTileset);
				
			} else { // INTERNAL
				TSXTileset tsxTileset = new TSXTileset(tileset.name, tileset.firstgid, tileset.tilewidth, tileset.tileheight, tileset.tilecount, tileset.columns);
				
				File file = new File(tileset.source);
				try {
					doc = db.parse(file);
					doc.getDocumentElement().normalize();
				} catch (Exception e) {}
				
				NodeList images = doc.getElementsByTagName("image");
				for (int i = 0; i < images.getLength(); i++) {
					Node image = images.item(i);
					if (image.getNodeType() == Node.ELEMENT_NODE) {
						Element eImage = (Element) image;
						if (eImage.getParentNode().getNodeType() == Node.ELEMENT_NODE) {
							Element eParent = (Element) eImage.getParentNode();
							if (eParent.getAttribute("name").equalsIgnoreCase(tileset.name)) {
								String source = new File(file.getAbsolutePath().replace(file.getName(), "") + eImage.getAttribute("source")).getAbsolutePath();
								TSXImage img = new TSXImage(source, Integer.parseInt(eImage.getAttribute("width")), Integer.parseInt(eImage.getAttribute("height")));
								tsxTileset.image = img;
							}
						}
					}
				}
				
				NodeList frames = doc.getElementsByTagName("frame");
				for (int i = 0; i < frames.getLength(); i++) {
					Node frame = frames.item(i);
					if (frame.getNodeType() == Node.ELEMENT_NODE) {
						Element eFrame = (Element) frame;
						if (eFrame.getParentNode().getNodeType() == Node.ELEMENT_NODE) {
							Element eAnimation = (Element) eFrame.getParentNode();
							if (eAnimation.getParentNode().getNodeType() == Node.ELEMENT_NODE) {
								Element eTile = (Element) eAnimation.getParentNode();
								int id = Integer.parseInt(eTile.getAttribute("id"));
								if (eTile.getParentNode().getNodeType() == Node.ELEMENT_NODE) {
									Element eTileset = (Element) eTile.getParentNode();
									int firstgid = Integer.parseInt(eTileset.getAttribute("firstgid"));
									int gid = firstgid + id;
									int tileGid = firstgid + Integer.parseInt(eFrame.getAttribute("tileid"));
									
									if (animatedSprites.containsKey(gid)) {
										AnimationSprite anim = animatedSprites.get(gid);
										anim.addTmpSprite(tileGid);
										animatedSprites.put(gid, anim);
									} else {
										int delay = Integer.parseInt(eFrame.getAttribute("duration"));
										AnimationSprite anim = new AnimationSprite(new ArrayList<BufferedImage>(), delay / 15);
										anim.addTmpSprite(tileGid);
										animatedSprites.put(gid, anim);
									}
								}
							}
						}
					}
				}
				
				tsxTilesets.put(tsxTileset.name, tsxTileset);
			}
		}
		
		for (Map.Entry<String, TSXTileset> tsxtsKV : tsxTilesets.entrySet()) {
			TSXTileset tsxts = tsxtsKV.getValue();
			int x = 0;
			int y = 0;
			for (int i = 0; i < tsxts.tilecount; i++) {
				int gid = i;
				x++;
				if (x == tsxts.columns + 1) {
					x = 1;
					y++;
				}
				Sprite sprite = new Sprite("tiled_" + (tsxts.firstgid + gid), new File(tsxts.image.source));
				sprite.crop((x-1) * tsxts.tilewidth, y * tsxts.tileheight, tsxts.tilewidth, tsxts.tileheight);
				
				tsxts.sprites.put((tsxts.firstgid + gid), sprite);
				this.sprites.put((tsxts.firstgid + gid), sprite);
			}
		}
		
		for (Map.Entry<Integer, AnimationSprite> animSpriteKV : animatedSprites.entrySet()) {
			if (!animSpriteKV.getValue().spritesLoaded) {
				AnimationSprite animSprite = animSpriteKV.getValue();
				for (int gid : animSprite.tmpGid) {
					Sprite sprite = sprites.get(gid);
					animSprite.addSprite(sprite);
				}
			}
		}
	}
	
	public Sprite getSprite(int gid) {
		try {
			return animatedSprites.get(gid).getSprite();
		} catch (Exception e) {
			return sprites.get(gid);
		}
	}
	
	public HashMap<Integer, AnimationSprite> getAnimatedSprites() {
		return animatedSprites;
	}
	
	public int firstGid() {
		return 1;
	}
	
	public int lastGid() {
		return sprites.size();
	}

	public ArrayList<Sprite> getSprites() {
		return new ArrayList<Sprite>(sprites.values());
	}
}

class TSXTileset {
	public String name;
	public int firstgid, tilewidth, tileheight, tilecount, columns;
	public TSXImage image;
	public HashMap<Integer, Sprite> sprites = new HashMap<Integer, Sprite>();
	
	public TSXTileset(String name, int firstgid, int tilewidth, int tileheight, int tilecount, int columns) {
		this.name = name;
		this.firstgid = firstgid;
		this.tilewidth = tilewidth;
		this.tileheight = tileheight;
		this.tilecount = tilecount;
		this.columns = columns;
	}
}

class TSXImage {
	public String source;
	public int width, height;
	
	public TSXImage(String source, int width, int height) {
		this.source = source;
		this.width = width;
		this.height = height;
	}
}

class AnimationSprite extends Animation {
	
	public ArrayList<Integer> tmpGid = new ArrayList<Integer>();
	public boolean spritesLoaded = false;
	public int index = 0;
	
	public AnimationSprite(ArrayList<BufferedImage> sprites, int spriteDelay) {
		super(sprites, spriteDelay);
	}

	public void addTmpSprite(int gid) {
		tmpGid.add(gid);
	}
}