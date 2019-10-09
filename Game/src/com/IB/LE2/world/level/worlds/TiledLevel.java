package com.IB.LE2.world.level.worlds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.IB.LE2.Boot;
import com.IB.LE2.Game;
import com.IB.LE2.media.graphics.Screen;
import com.IB.LE2.media.graphics.Sprite;
import com.IB.LE2.media.graphics.SpriteSheet;
import com.IB.LE2.util.VARS;
import com.IB.LE2.util.FileIO.Assets;
import com.IB.LE2.util.FileIO.Tag;
import com.IB.LE2.util.FileIO.TagReadListener;
import com.IB.LE2.util.FileIO.TagReader;
import com.IB.LE2.util.shape.LineSegment;
import com.IB.LE2.util.shape.Vertex;
import com.IB.LE2.world.entity.Entity;
import com.IB.LE2.world.entity.mob.Player;
import com.IB.LE2.world.level.Level;
import com.IB.LE2.world.level.TileCoord;
import com.IB.LE2.world.level.scripting.LuaScript;
import com.IB.LE2.world.level.scripting.triggers.EventVolume;
import com.IB.LE2.world.level.tile.Tile;
import com.IB.LE2.world.level.tile.tiles.XML_Tile;

public class TiledLevel extends Level {
	private static final long serialVersionUID = 1L;

	private TagReader reader;
	private LuaScript script;
	
	public String path = "";
	public String tiled_xml = "";
	
	public TileCoord Spawnpoint;
	public ArrayList<EventVolume> event_volumes;
	public ArrayList<LineSegment> solid_geometry;
	
	public boolean LuaLoaded = false;
	

	public TiledLevel(String name) {
		path = Assets.get(name);
		String lvn = path.substring(path.lastIndexOf('\\') + 1, path.length());
		
		Game.lvl_name = lvn;

		this.tiled_xml = path + "/" + lvn + ".tmx";
		System.out.println("TILED: " + path);

		//add(new Emitter(128, 32 * 32, new PVector(0, 5), new Sprite(4, 0xFFFF00), 50, 50, 1, this));
		//add(new TagEntity("/XML/Entities/TestZombie.xml", false));
		loadLevel(path);
		initLua();
	}
	
	public void StopLua() {
		script = null;
		this.LuaLoaded = false;
	}
	
	public boolean runningLua() {
		return this.LuaLoaded;
	}
	
	public void initLua() {
		loadLua();
	}
	
	public void loadLua() {
		try {
			String luaString = path + "/script.lua";
			script = new LuaScript(luaString);
			//script.AddGeneralGlobals();
			script.addGlobal("level", this);
			//ls.addGlobal("pc", getClientPlayer());
			//ls.addGlobal("key", Boot.get().getInput());
			//ls.addGlobal("key", Boot.get()); <= Crashes lua when used
			script.run();
			LuaLoaded = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	protected void loadLevel(String path) {
		System.out.println("Loading A Tiled Level..");
		
		System.out.println(path);
		String lvn = path.substring(path.lastIndexOf('\\') + 1, path.length());
		System.out.println("PATH: " + path + " :: " + path + "/" + lvn + ".tmx");
		
		reader = new TagReader(tiled_xml, "level", new TagReadListener() {
			@Override
			public void TagsRead() {
				ProcessTags();
			}

			@Override
			public void TagsError() {
				
			}
		});
		
		reader.start();
	}
	
	private void ProcessTags() {
		//reader.PrintTags();
		
		for(Tag tag : reader.getTags()) {
			String name = tag.name;
			String uri = tag.uri;
			String value = tag.value;

			switch (uri) {
			case "map":
				if (!tag.testEquality("tiledversion", VARS.VAL_TILED_VER)) 
					System.err.println("[WARN] Map was saved in an untested version of tiled!");
				if (!tag.testEquality("orientation", "orthogonal")
					|| !tag.testEquality("renderorder", "right-down") || !tag.testEquality("infinite", "0"))
					System.err.println("[WARN] Using unsupported map set-up!");
				if (tag.get("tilewidth", 32) + tag.get("tileheight", 32) != 64)
					System.err.println("[WARN] Non-standard tile dimensions!");
				
				this.width = tag.get("width", width);
				this.height = tag.get("height", height);
				this.tiles = new int[width * height];
				this.solid_geometry = new ArrayList<>();
				this.event_volumes = new ArrayList<>();
				break;
			case "map.tileset":
				break;
			case "map.layer":
				addTileLayer(tag);
				break;
			case "map.objectgroup":
				addObjectGroup(tag);
				break;
			}
		}
	}
	
	public void addObjectGroup(Tag group) {
		String group_id = group.get("id", "-1");
		String group_name = group.get("name", "UnnamedObjectGroup");
		String group_color = group.get("color", "#ffffff");
		
		ArrayList<Tag> children = group.getChildren();
		for (Tag child : children) {
			String id = child.get("id", "-1");
			String name = child.get("name", "UnnamedObject");
			String type = child.get("type", "UnnamedObjectGroup");
			double x = child.get("x", 0.0);
			double y = child.get("y", 0.0);
			double w = child.get("width", -1.0);
			double h = child.get("height", -1.0);
			
			switch(type.toLowerCase()) {
			case "trigger":
				HashMap<String, String> properties = new HashMap<>();
				properties.put("name", name);
				properties.put("x", "" + x);
				properties.put("y", "" + y);
				properties.put("width", "" + w);
				properties.put("height", "" + h);

				for (Tag property : child.getChild(0).getChildren()) {
					String pname = property.get("name", "UnnamedProperty");
					String pvalue = property.get("value", "");
					properties.put(pname, pvalue);
				}
				addTrigger(new EventVolume(properties));
				break;
			case "c_mask":
				String polyline_points = child.getChild(0).get("points", "");
				
				Vertex previous = null;
                for (String s : polyline_points.split("\\s+")) {
                	String[] p = s.split(",");
                    Vertex v = new Vertex(
                    	Float.parseFloat(p[0]) + x, 
                    	Float.parseFloat(p[1]) + y
                    );
                    
                    if (previous != null) {
                    	LineSegment ls = new LineSegment(previous, v);
                    	ls.color = Long.decode("0x" + group_color.substring(1)).intValue();
                    	solid_geometry.add(ls);
                    }
                    
                    previous = v;
                }
                
				break;
			case "spawnpoint":
                Spawnpoint = new TileCoord((int)x / 32, (int)y / 32);
				break;
			}
		}
	}

	public void addTileLayer(Tag layer) {
		String id = layer.get("id", "-1");
		String name = layer.get("name", "UnnamedLayer");
		Tag data = layer.getChild(0);
		
        tile_map.put(0, Tile.Air);

		int[] tiles = explodeTileString(data.value);
		for (int i = 0; i < tiles.length; i++) {
			int tile_id = tiles[i];
			if (tile_id == 0) continue;
			
	        XML_Tile t = new XML_Tile(tile_id, Tile.GenSpriteFromId(SpriteSheet.get("Terrain"), tile_id));
	        tile_map.put(tile_id, t);
			
	        // Merge existing tile with tile on this layer
	        if (this.tiles[i] != 0 && this.tiles[i] != tile_id) {
	        	Sprite existing = tile_map.get(this.tiles[i]).sprite;
	        	Sprite composite = new Sprite(existing, tile_map.get(tile_id).sprite);
	        	
	        	tile_id = 1024 + i;
	        	XML_Tile new_tile = new XML_Tile(tile_id, composite);
	        	tile_map.put(tile_id, new_tile);
	        }
	        
			this.tiles[i] = tile_id;
		}
	}
	
	public void addTrigger(EventVolume t) {
		event_volumes = (event_volumes == null) ? new ArrayList<EventVolume>() : event_volumes;
		
		event_volumes.add(t);
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
			
	public void drawExtendedLevel(Screen screen) {
		if (Boot.drawDebug) {
			if (this.solid_geometry != null) {
				for (int i = 0; i < this.solid_geometry.size(); i++) {
					LineSegment ln = solid_geometry.get(i);
					Game.font8bit.render((int) ln.midpoint().x, (int) ln.midpoint().y, -3, 0xffFFFFFF, "LN: " + i, screen, true, false);
					ln.drawLine(screen, true);

					ln.Perpendicular().drawLine(screen, true);
				}
			}
			
			if (event_volumes != null)
				for (EventVolume ev : event_volumes) {
					if (ev != null)
					ev.render(screen);
				}
		}
	}

	public void TestEventVolumes(Entity e) {
		if (event_volumes != null)
		for (EventVolume ev : event_volumes) {
			if (ev != null)
				if (ev.player_only) {
					if (e instanceof Player)
					ev.Test(e, script);
				} else {
					ev.Test(e, script);
				}
		}
	}
			
	public void UpdateUnloaded() {
		if (LuaLoaded)
			StopLua();
	}
	
	public void MovePlayerTo(double x, double y, String path, boolean tile_mult) {
		Boot.get().getPlayer().setPositionTiled(x, y, path, true);
	}
}
