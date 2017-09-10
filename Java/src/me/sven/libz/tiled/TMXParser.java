package me.sven.libz.tiled;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import me.sven.libz.core.Logger;
import me.sven.libz.image.Sprite;

public class TMXParser {

	private DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	private DocumentBuilder db;
	private Document doc;

	private HashMap<String, Objectgroup> objectgroups = new HashMap<String, Objectgroup>();
	private ArrayList<Tileset> tilesets = new ArrayList<Tileset>();
	private ArrayList<Layer> layers = new ArrayList<Layer>();
	private int xOffset, yOffset, mapWidth, mapHeight, tileWidth, tileHeight;
	private Color backgroundColor;
	private Logger log;

	public TMXParser(File file, boolean debug) {
		log = new Logger(getClass().getSimpleName());
		
		try {
			db = dbf.newDocumentBuilder();
			doc = db.parse(file);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		doc.getDocumentElement().normalize();
		
		xOffset = 0;
		yOffset = 0;
		mapWidth = Integer.parseInt(doc.getDocumentElement().getAttribute("width"));
		mapHeight = Integer.parseInt(doc.getDocumentElement().getAttribute("height"));
		tileWidth = Integer.parseInt(doc.getDocumentElement().getAttribute("tilewidth"));
		tileHeight = Integer.parseInt(doc.getDocumentElement().getAttribute("tileheight"));
		try {
			backgroundColor = Color.decode(doc.getDocumentElement().getAttribute("backgroundcolor"));
		} catch (Exception e) {}
		
		if (debug) {
			log.info("map size: " + doc.getDocumentElement().getAttribute("width") + "x" + doc.getDocumentElement().getAttribute("height"));
			log.info("tile size: " + doc.getDocumentElement().getAttribute("tilewidth") + "x" + doc.getDocumentElement().getAttribute("tileheight"));
			log.info("map size in pixels: " + (Integer.parseInt(doc.getDocumentElement().getAttribute("width")) * Integer.parseInt(doc.getDocumentElement().getAttribute("tilewidth"))) + "x" + (Integer.parseInt(doc.getDocumentElement().getAttribute("height")) * Integer.parseInt(doc.getDocumentElement().getAttribute("tileheight"))));
			log.info("Total # of tilesets: " + doc.getElementsByTagName("tileset").getLength());
			log.info("Total # of layers: " + doc.getElementsByTagName("layer").getLength());
			log.info("Total # of objectgroups: " + doc.getElementsByTagName("objectgroup").getLength());
		}

		
		NodeList tilesets = doc.getElementsByTagName("tileset");
		for (int i = 0; i < tilesets.getLength(); i++) {
			Node tileset = tilesets.item(i);
			if (tileset.getNodeType() == Node.ELEMENT_NODE) {
				Element eTileset = (Element) tileset;
				int firstgid = Integer.parseInt(eTileset.getAttribute("firstgid"));
				if (eTileset.getAttribute("source") != "") {
					String source = new File(file.getAbsolutePath().replace(file.getName(), "") + eTileset.getAttribute("source")).getAbsolutePath();
					Tileset ts = new Tileset(firstgid, source);
					this.tilesets.add(ts);
				} else {
					String name = eTileset.getAttribute("name");
					int tileWidth = Integer.parseInt(eTileset.getAttribute("tilewidth"));
					int tileHeight = Integer.parseInt(eTileset.getAttribute("tileheight"));
					int tileCount = Integer.parseInt(eTileset.getAttribute("tilecount"));
					int columns = Integer.parseInt(eTileset.getAttribute("columns"));
					Tileset ts = new Tileset(name, firstgid, tileWidth, tileHeight, tileCount, columns);
					ts.source = file.getAbsolutePath();
					this.tilesets.add(ts);
				}
			}
		}

		NodeList layers = doc.getElementsByTagName("layer");
		for (int i = 0; i < layers.getLength(); i++) {
			Node layer = layers.item(i);
			if (layer.getNodeType() == Node.ELEMENT_NODE) {
				Element eLayer = (Element) layer;
				String name = eLayer.getAttribute("name");
				int id = this.layers.size();
				int width = Integer.parseInt(eLayer.getAttribute("width"));
				int height = Integer.parseInt(eLayer.getAttribute("height"));
				Layer l = new Layer(name, id, width, height);
				
				this.layers.add(l);

			}
		}
		
		NodeList tiles = doc.getElementsByTagName("tile");
		for (int i = 0; i < tiles.getLength(); i++) {
			Node tile = tiles.item(i);
			if (tile.getNodeType() == Node.ELEMENT_NODE) {
				Element eTile = (Element) tile;
				if (eTile.getParentNode().getParentNode().getNodeType() == Node.ELEMENT_NODE) {
					Element eLayer = (Element) eTile.getParentNode().getParentNode();
					
					Layer layer = null;
					for (Layer l : this.layers) {
						if (l.name.equalsIgnoreCase(eLayer.getAttribute("name"))) {
							layer = l;
							break;
						}
					}
					if (layer != null) {
						Tile t = new Tile(Integer.parseInt(eTile.getAttribute("gid")));
						layer.tiles.add(t);
					}
				}
			}
		}
		
		NodeList objectgroups = doc.getElementsByTagName("objectgroup");
		for (int i = 0; i < objectgroups.getLength(); i++) {
			Node objectgroup = objectgroups.item(i);
			if (objectgroup.getNodeType() == Node.ELEMENT_NODE) {
				Element eObjectgroup = (Element) objectgroup;
				String name = eObjectgroup.getAttribute("name");
				Objectgroup og = new Objectgroup(name);
				this.objectgroups.put(name, og);
			}
		}

		NodeList objects = doc.getElementsByTagName("object");
		for (int i = 0; i < objects.getLength(); i++) {
			Node object = objects.item(i);
			if (object.getNodeType() == Node.ELEMENT_NODE) {
				Element eObject = (Element) object;
				if (eObject.getParentNode().getNodeType() == Node.ELEMENT_NODE) {
					Element eParent = (Element) eObject.getParentNode();

					int id = Integer.parseInt(eObject.getAttribute("id"));
					String name = "";
					String type = "";
					Float x = Float.parseFloat(eObject.getAttribute("x"));
					Float y = Float.parseFloat(eObject.getAttribute("y"));
					Float width = 0f;
					Float height = 0f;
					Color color = Color.WHITE;
					
					try {
						width = Float.parseFloat(eObject.getAttribute("width"));
						height = Float.parseFloat(eObject.getAttribute("height"));
					} catch (Exception e) {
					}
					
					try {
						name = eObject.getAttribute("name");
					} catch (Exception e) {}
					
					try {
						type = eObject.getAttribute("type");
					} catch (Exception e) {}
					
					try {
						color = Color.decode(eParent.getAttribute("color"));
					} catch (Exception e) {}
					
					TiledObject o = new TiledObject(id, name, type, color, x, y, width, height);
					this.objectgroups.get(eParent.getAttribute("name")).objects.add(o);
				}
			}
		}
	}

	public ArrayList<Tileset> getTilesets() {
		return tilesets;
	}

	public ArrayList<Layer> getLayers() {
		return layers;
	}
	
	public Layer getLayer(String name) {
		Layer layer = null;
		for (Layer l : layers)
			if (l.name.equalsIgnoreCase(name)) {
				layer = l;
				break;
			}
		return layer;
	}

	public Objectgroup getObjectgroup(String key) {
		return objectgroups.get(key);
	}

	public HashMap<String, Objectgroup> getObjectgroups() {
		return objectgroups;
	}
	
	public int getXOffset() {
		return xOffset;
	}
	
	public int getYOffset() {
		return yOffset;
	}
	
	public int getMapWidth() {
		return mapWidth;
	}
	
	public int getMapHeight() {
		return mapHeight;
	}
	
	public int getTileWidth() {
		return tileWidth;
	}
	
	public int getTileHeight() {
		return tileHeight;
	}
	
	public Color getBackgroundColor(){
		return backgroundColor;
	}
}

class Tileset {
	public int firstgid;
	public String source;
	public boolean internal;
	public String name;
	public int tilewidth, tileheight, tilecount, columns;
	public TSXImage image;
	public HashMap<Integer, Sprite> sprites = new HashMap<Integer, Sprite>();

	public Tileset(int firstgid, String source) {
		this.firstgid = firstgid;
		this.source = source;
		this.internal = false;
	}
	
	public Tileset(String name, int firstgid, int tilewidth, int tileheight, int tilecount, int columns) {
		this.name = name;
		this.firstgid = firstgid;
		this.tilewidth = tilewidth;
		this.tileheight = tileheight;
		this.tilecount = tilecount;
		this.columns = columns;
		this.internal = true;
	}
}

class Layer {
	public int id, width, height;
	public String name;
	public ArrayList<Tile> tiles = new ArrayList<Tile>();

	public Layer(String name, int id, int width, int height) {
		this.name = name;
		this.id = id;
		this.width = width;
		this.height = height;
	}

	public int getGid(int i) {
		return tiles.get(i).gid;
	}
}

class Tile {
	public int gid;

	public Tile(int gid) {
		this.gid = gid;
	}
}

class Objectgroup {
	public String name;
	public ArrayList<TiledObject> objects = new ArrayList<TiledObject>();

	public Objectgroup(String name) {
		this.name = name;
	}
}