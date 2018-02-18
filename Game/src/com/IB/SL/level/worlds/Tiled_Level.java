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
import com.IB.SL.entity.PVector;
import com.IB.SL.entity.emitter.Emitter;
import com.IB.SL.entity.mob.Player;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.level.Level;
import com.IB.SL.level.tile.Tile;
import com.IB.SL.level.tile.Tile.stepSound;
import com.IB.SL.level.tile.SL2.XML_Tile;

public class Tiled_Level extends Level {
	private static final long serialVersionUID = 1L;

	String path = "";
	
	String tiled_xml = "";
	String tiled_version = "";
	
	boolean readingLayer = false;
	boolean readingObjects = false;
	int current_layer = -1;
	String current_object_layer = "";
	String current_object_type = "";

	String tile_string = "";
	String overlay_string = "";

	public HashMap<String, String> props = new HashMap<String, String>();
	
	public ArrayList<LevelExit> exits;
;

	
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
            	 if (ln.equals("Tiles")) {
            		 this.current_layer = 0;
            	 } else if (ln.equals("Overlays")) {
            		 this.current_layer = 1;
            	 }
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
		if (readingLayer && this.current_layer == 0) {
           this.tile_string += ((new String(ch, start, length)));
		}
		
		if (readingLayer && this.current_layer == 1) {
	       this.overlay_string += ((new String(ch, start, length)));
		}
    }
	
	public int[] t_layer1 = new int[width * height];
	public int[] t_layer2 = new int[width * height];

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		switch (qName) {
        case "data": {
			this.readingLayer = false;

			if (this.current_layer == 0) {
				this.t_layer1 = explodeTileString(this.tile_string);
			}

			if (this.current_layer == 1) {
				//System.out.println("OVERLAY TILES: " + this.overlay_string);
				this.t_layer2 = explodeTileString(this.overlay_string);
			}
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

        case "map": {
			Boot.log("Level fully loaded..", "Tiled_Level.java", false);
			
			Tile t = new Tile();
			t.readXML("/XML/Tiles/TileDefinitions.xml");
			for (int i = 0; i < (width * height); i++) {
				if (t_layer2 != null && t_layer1 != null) {
					if (t_layer2[i] == 0) {
						continue;
					}
					Tile t1 = Tile.TileIndex.get(t_layer1[i]);
					Tile t2 = Tile.TileIndex.get(t_layer2[i]);
					if (t1 != null && t2 != null) {
							
						Sprite sp = t1.sprite;
						Sprite sp2 = t2.sprite;
						Sprite sp3;
						if (t_layer1[i] == 0) {
							sp3 = sp2;
						} else {
							sp3 = new Sprite(sp, sp2);
						}
						int id = (1024 + i);
						XML_Tile ct = new XML_Tile("Compound_Tile", sp3, stepSound.Hard, 
								id, t1.solid() || t2.solid(), t1.solidtwo() || t2.solidtwo(), 
								t1.jumpThrough() || t2.jumpThrough(), t1.exit() || t2.exit());

						Tile.TileIndex.put(id, ct);
						t_layer1[i] = (id);
					}
				}
			}
			
			this.tiles = t_layer1;
			this.overlayTiles = t_layer2;
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
