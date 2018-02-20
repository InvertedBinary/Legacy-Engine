package com.IB.SL.level.worlds;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.IB.SL.Boot;
import com.IB.SL.VARS;
import com.IB.SL.entity.mob.Player;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.level.Level;
import com.IB.SL.level.TileCoord;
import com.IB.SL.level.tile.Tile;
import com.IB.SL.level.tile.Tile.stepSound;
import com.IB.SL.level.tile.SL2.XML_Tile;

public class Tiled_Level extends Level {
	private static final long serialVersionUID = 1L;

	public String path = "";
	
	String tiled_xml = "";
	String tiled_version = "";
	
	boolean readingLayer = false;
	boolean readingObjects = false;
	Integer current_layer = null;
	String current_object_layer = "";
	String current_object_type = "";

	ArrayList<String> tile_strings;

	public HashMap<String, String> props = new HashMap<String, String>();
	public ArrayList<LevelExit> exits;
	public TileCoord spawnpoint;
	public ArrayList<int[]> tilels;

	public Tiled_Level(String path) {
		super(path);
		String lvn = path.substring(path.lastIndexOf('/') + 1, path.length());

		this.tiled_xml = path + "/" + lvn + ".tmx";
		System.out.println("TILED: " + tiled_xml);
		this.path = path;
	}
	
	protected void loadLevel(String path) {
		SAXParserFactory parserFactory = SAXParserFactory.newInstance();
		SAXParser sp;
		
		System.out.println("Loading A Tiled Level..");
		try {
			String lvn = path.substring(path.lastIndexOf('/') + 1, path.length());
			sp = parserFactory.newSAXParser();
			//sp.parse("E:\\Dev\\Square Legacy 2\\Square-Legacy-2\\Game\\res\\XML\\Levels\\b10\\b10.tmx", this);
			System.out.println("PATH: " + path + " :: " + path + "/" + lvn + ".tmx");
			sp.parse(Tiled_Level.class.getResourceAsStream(path + "/" + lvn + ".tmx"), this);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//add(new Emitter(64, 1216, new PVector(1, -5), new Sprite(1 + Boot.randInt(0, 0), 0xffFFFF00), 20, 300, 1, this));
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch (qName) {
             case "map": {
              this.tiled_version = attributes.getValue("tiledversion");
              Boot.log(this.tiled_version.equals(VARS.VAL_TILED_VER) ? "Using a Valid Version of Tiled!" : "This Version of Tiled may not be Supported!", "Tiled_Level.java", false);
              	
           	  this.width = Integer.parseInt(attributes.getValue("width"));
           	  this.height = Integer.parseInt(attributes.getValue("height"));
           	  
           	  break;
             }
             
             case "layer": {
            	 String ln = attributes.getValue("name");
 				if (tile_strings == null) {
					tile_strings = new ArrayList<>();
				}
 				if (current_layer == null) {
 					current_layer = -1;
 				}
            	 
          	 		this.current_layer++;
          	 		System.out.println(ln + " : " + current_layer);
            	 break;
             }
             
             case "data": {
            	 if (attributes.getValue("encoding").equals("csv")) {
            		 readingLayer = true;
            	 } else {
            		 Boot.log("This level's tiles are encoded in an unsupported method. (Must use CSV!)..", "Tiled_Level.java", true);
            		 Boot.get().quit();
            	 }
            	 break;
             }
             
             case "objectgroup": {
            	 this.current_object_layer = attributes.getValue("name");
            	 break;
             }
             
             case "object": {
            	 this.current_object_type = attributes.getValue("type");
            	 if (this.current_object_type.equals("Exit_Zone")) {
            		 this.props.put("x", attributes.getValue("x"));
            		 this.props.put("y", attributes.getValue("y"));
            		 this.props.put("width", attributes.getValue("width"));
            		 this.props.put("height", attributes.getValue("height"));
            	 } else if (this.current_object_type.equals("SpawnPoint")) {
            		 this.props.put("x", attributes.getValue("x"));
            		 this.props.put("y", attributes.getValue("y"));
            	 }
            	 break;
             }
             
             case "property": {
            	 this.props.put(attributes.getValue("name"), attributes.getValue("value"));
            	 break;
             }
        }
	}
	
	@Override
    public void characters(char ch[], int start, int length) throws SAXException {
			if (readingLayer) {
				System.out.println("CURRENTLAYER: " + current_layer);
				if (tile_strings.size() == current_layer) {
				tile_strings.add(((new String(ch, start, length))));
				} else {
				tile_strings.set(current_layer, tile_strings.get(current_layer) + new String(ch, start, length));
				}
			}
    }
	
	/*public int[] t_layer1 = new int[width * height];
	public int[] t_layer2 = new int[width * height];*/

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		switch (qName) {
        case "data": {
			this.readingLayer = false;
			
			if (tilels == null) {
				tilels = new ArrayList<>();
			}
			
			tilels.add(explodeTileString(this.tile_strings.get(this.current_layer)));

			this.torchTiles = new int[width * height];
		}

        case "properties": {
        	if (this.current_object_type != null) {
        	if (this.current_object_type.equals("Exit_Zone")) {
        		LevelExit e = new LevelExit(this.toInt(props.get("x")), this.toInt(props.get("y")), this.toInt(props.get("width")), this.toInt(props.get("height")), props.get("To"), this.toInt(props.get("To_X")), this.toInt(props.get("To_Y")));
        		addExit(e);
        		} 
        	}
        	this.props = new HashMap<String, String>();
        }
        
			case "object": {
				if (this.current_object_type != null) {
					if (this.current_object_type.equals("SpawnPoint")) {
						spawnpoint = new TileCoord(toInt(props.get("x")) / 32, toInt(props.get("y")) / 32);
					}
				}
			}

        case "map": {
			Boot.log("Level fully loaded..", "Tiled_Level.java", false);
			
			Tile t = new Tile();
			t.readXML("/XML/Tiles/TileDefinitions.xml");
			for (int i = 0; i < (width * height); i++) {
				Tile[] merges = new Tile[tilels.size()];
				for (int j = 0; j < tilels.size(); j++) {
					if (tilels.get(j)[i] == 0) {
						merges[j] = null;
					} else {
						merges[j] = Tile.TileIndex.get(tilels.get(j)[i]);
					}
				}
				
				Sprite sp = null;
				boolean solid = false;
				boolean solid2 = false;
				boolean jumpThrough = false;
				boolean exit = false;
				for (int j = 0; j < merges.length; j++ ) {
					if (merges[j] != null) {
						if (sp == null && !merges[j].equals(Tile.TileIndex.get(0))) {
							sp = merges[j].sprite;
						}
					if (merges[j].solid()) solid = true;
					if (merges[j].solidtwo()) solid2 = true;
					if (merges[j].jumpThrough()) jumpThrough = true;
					if (merges[j].exit()) exit = true;
					
					if (j > 0 && sp != null) {
						sp = new Sprite(sp, merges[j].sprite);
						}
					}
				}
				
				if (sp == null) {
					this.tilels.get(0)[i] = 0;
				} else {
				int id = (1024 + i); //SET 1024 TO MAX NATURAL TILE ID
				XML_Tile ct = new XML_Tile("Compound_Tile", sp, stepSound.Hard, 
						id, solid, solid2, jumpThrough, exit);
				
				Tile.TileIndex.put(id, ct);
				this.tilels.get(0)[i] = (id);
				}
			}
			
			this.tiles = tilels.get(0);
			//this.overlayTiles = this.tilels.get(1);
			}
		}
	}
	
	public void addExit(LevelExit e) {
		if (this.exits == null) {
			this.exits = new ArrayList<LevelExit>();
		}
		exits.add(e);
	}
	
	public int toInt(String s) {
		return (int)Double.parseDouble(s);
	}
	
	public int[] explodeTileString(String tiles) {
     	int[] xml_tiles = new int[width * height];

		List<String> items = Arrays.asList(tiles.split("\\s*,\\s*"));
		for (int i = 0; i < items.size(); i++) {
			String str = items.get(i);
			int id = -1;
			
			try {
				id = Integer.parseInt(str);
			} catch (NumberFormatException e) {}
			
			if (id != -1) {
				xml_tiles[i] = id;
			}
		}
		return xml_tiles;
	}
	
	public void checkExits(Player player, Level level, int x, int y) {
		// refresh();
		System.out.println("NUM EXITS: " + exits.size());
		for (int i = 0; i < exits.size(); i++) {
			LevelExit exit = exits.get(i);
			if (x >= exit.x && x <= (exit.x + exit.w)) {
				if (y >= exit.y && y <= (exit.y + exit.h)) {
					player.setPositionTiled(exit.send_x, exit.send_y, exit.path, true);
				}
			}
		}
	}
	
}
