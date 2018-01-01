package com.IB.SL.level.worlds;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.IB.SL.Boot;
import com.IB.SL.entity.mob.Player;
import com.IB.SL.entity.mob.XML_Mob;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.SpriteSheet;
import com.IB.SL.level.Level;
import com.IB.SL.level.tile.Tile;

public class XML_Level extends Level{
		
	public static boolean spawnASM = false;

	protected static java.util.Random Random = new Random();
	public static java.util.Random random = Random;

	public ArrayList<LevelExit> exits;
	
	public String XML_String = "";
	public String name = "";
	public int id = -1;
	
	protected void loadLevel(String path) {
		SpawnList.clear();
		
		SpawnTime_MOD = 150;
		
		this.tiles = readTiles(path + "/level.png");
		this.overlayTiles = readTiles(path + "/overlay.png");
		this.torchTiles = readTiles(path + "/overlay.png");
		
		add(new XML_Mob(55, 73, "TestZombie.xml"));
		//add(new Location_Shrine(50, 50, new TileCoord(673, 228)));
	} 
	
	public XML_Level(String path) {
		super(path, true);
	}
	
	public void LoadXML(String XML) {
		readXML(XML);
	}
	
	public void loadLua(String lua) {
		File luaFile = new File(lua);
		if (luaFile.exists()) {
			Globals globals = JsePlatform.standardGlobals();
			LuaValue level = CoerceJavaToLua.coerce(this);
			globals.set("level", level);
			LuaValue chunk = globals.loadfile(lua);
			chunk.call();
		}
	}
	
	public void readXML(String path) {
		this.XML_String = path;
		try {
		InputStream fXmlFile = getClass().getResourceAsStream(path);
		DocumentBuilderFactory dbFac = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFac.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		doc.getDocumentElement().normalize();
		
		System.out.println("ROOT: " + doc.getDocumentElement().getNodeName());
		initLevel(doc);
		initExits(doc);
		initMobs(doc);
		initCustomMobs(doc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void render(int xScroll, int yScroll, Screen screen) {
		super.render(xScroll, yScroll, screen);
		//System.out.println("XML_Level.render()");
		//recs.add(new Rectangle(0, 0, 20, 20));
		/*if (recs.size() == 0) {
			gentrangles();
		}*/
		for (int i = 0; i < recs.size(); i++) {
			Rectangle r = recs.get(i);
			System.out.println("Drawing.. " + recs.size());
			screen.drawRect(r.x, r.y, r.width, r.height, 0xffFF00FF, true);
		}
	}
	
	ArrayList<Rectangle> recs = new ArrayList<Rectangle>();
	/*public void gentrangles() {
		Rectangle rec = new Rectangle();
		int rx = -1, ry = -1;
		for (int y = 0; y < this.height; y++) {
			for (int x = 0; x < this.width; x++) {
				if (this.getTile(x, y).solid()) {
					if (rx == -1 && ry == -1) {
					rx = x;
					ry = y;
					}
				} else if (rx == -1 && ry == -1) {
					rec = new Rectangle(rx<<4, ry<<4, (rx - x)<<4, (ry - y)<<4);
					recs.add(rec);
					rx = -1; ry = -1;
				}
			}
		}
		if (height != 0 && width != 0) {
		//recs.add(new Rectangle());
		}
	}*/
	
	public void initLevel(Document doc) {
		NodeList nList = doc.getElementsByTagName("props");
		System.out.println("----------------------------");
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			System.out.println("\nCurrent Element :" + nNode.getNodeName());
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				try {
					this.id = Integer.parseInt(eElement.getAttribute("id"));
					this.name = (eElement.getElementsByTagName("name").item(0).getTextContent());
					this.minimap_enabled = Boolean.parseBoolean(((Element) eElement.getElementsByTagName("minimap").item(0)).getAttribute("enabled"));
					SpriteSheet.minimapDYN = new SpriteSheet(this.Level_Dir + "/level.png", (ImageIO.read(XML_Level.class.getResource((this.Level_Dir + "/level.png")))).getWidth());
					String hexString = ((Element) eElement.getElementsByTagName("null_tile_sprite").item(0)).getAttribute("tile");
					int hex = Long.decode(hexString).intValue();
					System.out.println("VOID TILE HEX: " + hex);
					Tile.VoidTile.sprite = Tile.TileIndex.get(hex).sprite;
					
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void initExits(Document doc) {
		exits = new ArrayList<LevelExit>();
		NodeList nList = doc.getElementsByTagName("exits");
		System.out.println("--------------EXITS--------------");
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			System.out.println("\nCurrent Element :" + nNode.getNodeName());
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				for (int i = 0; i < (((Element) nNode).getElementsByTagName("exit").getLength()); i++) {
					try {
						Element eElement = (Element) ((Element) nNode).getElementsByTagName("exit").item(i);
						this.exits.add(new LevelExit(eElement));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public void initCustomMobs(Document doc) {
		NodeList nList = doc.getElementsByTagName("custom_mobs");
		System.out.println("--------------Custom_Mobs--------------");
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			System.out.println("\nCurrent Element :" + nNode.getNodeName());
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				for (int i = 0; i < (((Element)nNode).getElementsByTagName("entity").getLength()); i++) {
				try {
				Element eElement = (Element) ((Element)nNode).getElementsByTagName("entity").item(i);
				XML_Mob m = BuildXMLMob(eElement);
				//m.setProperties(eElement);
				add(m);
				} catch (Exception e) {	
					e.printStackTrace();
					}
				}
			}
		}
	}
	
	public void initMobs(Document doc) {
		NodeList nList = doc.getElementsByTagName("mobs");
		System.out.println("--------------Mobs--------------");
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			System.out.println("\nCurrent Element :" + nNode.getNodeName());
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				for (int i = 0; i < (((Element)nNode).getElementsByTagName("entity").getLength()); i++) {
				try {
				Element eElement = (Element) ((Element)nNode).getElementsByTagName("entity").item(i);
				add(BuildXMLMob(eElement));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	private XML_Mob BuildXMLMob(Element eElement) {
		double x = Integer.parseInt(eElement.getAttribute("x"));
		double y = Integer.parseInt(eElement.getAttribute("y"));
		double uid = Integer.parseInt(eElement.getAttribute("uid"));
		String tags = "/XML/Entities/" + (eElement.getAttribute("tags"));
		XML_Mob m = new XML_Mob(x, y, tags);
		return m;
	}
	
	
	public int[] readTiles(String path) {
		int[] tiles = null;
		try {
			BufferedImage image = ImageIO.read(XML_Level.class.getResource(path));
			int w = width = image.getWidth();
			int h = height = image.getHeight();

			tiles = new int[w * h];
			image.getRGB(0, 0, w, h, tiles, 0, w);
		}catch (IOException e) {	
			e.printStackTrace();
			Boot.log("IOException! Failed To Load Level File!", "XML_Level.java", true);
		}
		return tiles;
	}
	
	public void checkExits(Player player, Level level, int x, int y) {
		// refresh();
		for (int i = 0; i < exits.size(); i++) {
			LevelExit exit = exits.get(i);
			if (x >= exit.xo && x <= (exit.xo + exit.w)) {
				if (y >= exit.yo && y <= (exit.yo + exit.h)) {
					player.setPositionXML(exit.tx, exit.ty, exit.xml, true);
				}
			}
		}
	}
}


