package com.IB.LE2.world.level.tile;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.IB.LE2.media.graphics.Screen;
import com.IB.LE2.media.graphics.Sprite;
import com.IB.LE2.world.level.tile.tiles.Air;
import com.IB.LE2.world.level.tile.tiles.VoidTile;
import com.IB.LE2.world.level.tile.tiles.XML_Tile;

public class Tile {
	
	public enum stepSound {
		Organic, Hard, Squishy, Water;
	}

	public String XML_String = "";
	public Sprite sprite;
	public static HashMap<Integer, Tile> TileIndex = new HashMap<Integer, Tile>();
	
	public static Tile VoidTile = new VoidTile(Sprite.VoidTile);
	public static Tile Air = new Air(Sprite.VoidTile);
	
	public Tile(Sprite sprite) { this.sprite = sprite; }
	public Tile() { }
	
	public static Tile returnTile(int hex) {
		return TileIndex.get(hex);
	}
	
	public void readXML(String path) {
		this.XML_String = path;
		try {
		InputStream fXmlFile = Tile.class.getResourceAsStream(path);
		DocumentBuilderFactory dbFac = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFac.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		doc.getDocumentElement().normalize();
		
		//System.out.println("ROOT: " + doc.getDocumentElement().getNodeName());
		TileIndex.put(0, Air);
		buildTiles(doc);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void buildTiles(Document doc) {
		NodeList nList = doc.getElementsByTagName("Tiles");
		//System.out.println("--------------TILES--------------");
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				for (int i = 0; i < (((Element) nNode).getElementsByTagName("tile").getLength()); i++) {
					try {
						Element e = (Element) ((Element) nNode).getElementsByTagName("tile").item(i);
						//int hex = Long.decode(e.getAttribute("hex")).intValue();
						int id = Integer.parseInt(e.getAttribute("id"));
						boolean solid = (Boolean.parseBoolean(e.getAttribute("solid")));
						boolean solidTwo = (Boolean.parseBoolean(e.getAttribute("projSolid")));
						boolean jumpThrough = (Boolean.parseBoolean(e.getAttribute("jumpThrough")));
						boolean isExit = (Boolean.parseBoolean(e.getAttribute("isExitTile")));
						Field field = Sprite.class.getField(e.getAttribute("sprite"));
						Sprite sp = (Sprite) field.get(field.getType());
						String name = (e.getTextContent());

						/*System.out.println("HEX: " + (hex)
										+ ", AA: " + (hex >> 24)
										+ ", RR: " + (hex >> 0)
										+ ", GG: " + (hex >> 8)
										+ ", BB: " + (hex >> 16));*/

						XML_Tile t = new XML_Tile(name, sp, stepSound.Hard, id, solid, solidTwo, jumpThrough, isExit);
						TileIndex.put(id, t);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	
	public void render(int x, int y, Screen screen) {
	}
	
	public boolean solid() {
		return false;
	}
	
	public stepSound StepSound() {
		return null;
	}

	public boolean exit() {
		return false;
	}

	public boolean solidtwo() {
		return false;
	}
	
	public boolean jumpThrough() {
		return false;
	}

	public boolean liquid() {
		return false;
	}
	
	public boolean illuminator() {
		return false;
	}
	
	public boolean antiSpawn() {
		return false;
	}
	
	public int getHex() {
		return 0xffFF00FF;
	}

}
