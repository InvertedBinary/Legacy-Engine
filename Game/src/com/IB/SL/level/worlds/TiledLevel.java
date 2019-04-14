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
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.level.Level;
import com.IB.SL.level.TileCoord;
import com.IB.SL.level.tile.Tile;
import com.IB.SL.level.tile.Tile.stepSound;
import com.IB.SL.level.tile.SL2.XML_Tile;
import com.IB.SL.util.LuaScript;
import com.IB.SL.util.shape.LineSegment;
import com.IB.SL.util.shape.Vertex;

public class TiledLevel extends Level {
	private static final long serialVersionUID = 1L;

	public String path = "";
	
	String tiled_xml = "";
	String tiled_version = "";
	
	boolean readingLayer = false;
	boolean readingObjects = false;
	Integer current_layer = -1;
	String current_object_layer = "";
	String current_object_type = "";

	ArrayList<String> tile_strings;

	public HashMap<String, String> props = new HashMap<String, String>();
	public ArrayList<LevelExit> exits;
	public TileCoord spawnpoint;
	public ArrayList<int[]> tilels;
	public ArrayList<LineSegment> solid_geometry;

	public TiledLevel(String path) {
		super(path);
		String lvn = path.substring(path.lastIndexOf('/') + 1, path.length());
		
		Boot.get().lvl_name = lvn;

		this.tiled_xml = path + "/" + lvn + ".tmx";
		System.out.println("TILED: " + tiled_xml);
		this.path = path;

		//add(new Emitter(128, 32 * 32, new PVector(0, 5), new Sprite(4, 0xFFFF00), 50, 50, 1, this));
		//add(new TagEntity("/XML/Entities/TestZombie.xml", false));

		initLua();
	}
	
	
	
	public void killLua() {
		this.loadedLua = false;
		try {
			luaThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public boolean runningLua() {
		return this.loadedLua;
	}
	
	public void initLua() {
		loadLua();
	}
	
	public Thread luaThread;
	LuaScript ls;
	boolean loadedLua = false;
	public void loadLua() {
		try {
		String luaString = path + "/script.lua";
		ls = new LuaScript(luaString);
		ls.addGlobal("level", this);
		//ls.addGlobal("pc", getClientPlayer());
		//ls.addGlobal("key", Boot.get().getInput());
		//ls.addGlobal("key", Boot.get()); <= Crashes lua when used
		
		luaThread = new Thread(ls, "LUA For " + luaString);
		luaThread.start();
		loadedLua = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
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
			sp.parse(TiledLevel.class.getResourceAsStream(path + "/" + lvn + ".tmx"), this);
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
 				
 				
            	 
            	/* if (ln.equals("Tiles")) {
            		 this.current_layer = 0;
            	 } else if (ln.equals("Overlays")) {
            		 this.current_layer = 1;
            	 }*/
          	 		this.current_layer++;
          	 		//System.out.println(ln + " : " + current_layer);
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
            	 if (current_object_layer.equals("Collision_mask")) {
            		 String color = attributes.getValue("color");
            		 if (color == null) {
            			 color = "#ff00ff";
            		 }
            		 this.props.put("color", color);
            	 }
            	 break;
             }
             
             case "object": {
            	 this.current_object_type = attributes.getValue("type");
            	 if (current_object_type == null)
            		 break;
            	 
            	 if (this.current_object_type.equalsIgnoreCase("Exit_Zone")) {
            		 this.props.put("x", attributes.getValue("x"));
            		 this.props.put("y", attributes.getValue("y"));
            		 this.props.put("width", attributes.getValue("width"));
            		 this.props.put("height", attributes.getValue("height"));
            	 } else if (this.current_object_type.equalsIgnoreCase("SpawnPoint")) {
            		 this.props.put("x", attributes.getValue("x"));
            		 this.props.put("y", attributes.getValue("y"));
            	 } else if (this.current_object_type.equalsIgnoreCase("c_mask")) {
            		 this.props.put("x", attributes.getValue("x"));
            		 this.props.put("y", attributes.getValue("y"));
            	 }
            	 break;
             }
             
             case "polyline": {
            	 if (this.current_object_type == null)
            		 break;
            	 
            	 if (this.current_object_type.equalsIgnoreCase("c_mask")) {
            		 if (this.solid_geometry == null) {
            			 this.solid_geometry = new ArrayList<>();
            		 }
            		 
					ArrayList<Vertex> pts = new ArrayList<>();

					String coords = attributes.getValue("points");
					String c_array[] = coords.split("\\s+");
					for (String s : c_array) {
						String c_xy[] = s.split(",");
						float x = Float.parseFloat(c_xy[0]) + Float.parseFloat(this.props.get("x"));
						float y = Float.parseFloat(c_xy[1]) + Float.parseFloat(this.props.get("y"));
						pts.add(new Vertex(x, y));
					}
					
					Vertex v1 = null;
					Vertex v2 = null;
					for (int i = 0; i < pts.size(); i++) {
						Vertex v = pts.get(i);

						if (i % 2 == 0) {
							v2 = v;
						} else {
							v1 = v;
						}
						
						
						if (v1 != null && v2 != null) {
							LineSegment ls = new LineSegment(v1, v2);
							if (this.props.containsKey("color")) {
							String color = this.props.get("color").substring(1);
								ls.color = Long.decode("0x" + color).intValue();
							} else {
								ls.color = 0xffFF00FF;
							}
							this.solid_geometry.add(ls);
						}
					}
					
            	 //System.out.println("Adding some new geometry.. " + solid_geometry);
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
				//System.out.println("CURRENTLAYER: " + current_layer);
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
        		LevelExit e = new LevelExit(this.asInt(props.get("x")), this.asInt(props.get("y")), this.asInt(props.get("width")), this.asInt(props.get("height")), props.get("To"), this.asInt(props.get("To_X")), this.asInt(props.get("To_Y")));
        		addExit(e);
        		} 
        	}
        	this.props = new HashMap<String, String>();
        }
        
			case "object": {
				if (this.current_object_type != null) {
					if (this.current_object_type.equals("SpawnPoint")) {
						spawnpoint = new TileCoord(asInt(props.get("x")) / 32, asInt(props.get("y")) / 32);
					}
				}
			}

        case "map": {
			//Boot.log("Tile layer " + this.current_layer + " fully loaded..", "Tiled_Level.java", false);
			
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
	
	public static int asInt(String s) {
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
			
	public void drawExtendedLevel(Screen screen)
		{	
			if (Boot.drawDebug) {
				if (this.solid_geometry != null) {
					for (int i = 0; i < this.solid_geometry.size(); i++) {
						LineSegment ln = solid_geometry.get(i);
						Boot.get().font8bit.render((int)ln.midpoint().x, (int)ln.midpoint().y, 0xffFFFFFF, "LN: " + i, screen, 0, true, false);
						ln.drawLine(screen, true);
						
						ln.Perpendicular().drawLine(screen, true);
					}
				}
			}
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
