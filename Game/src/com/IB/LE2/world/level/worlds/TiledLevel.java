package com.IB.LE2.world.level.worlds;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

import com.IB.LE2.Boot;
import com.IB.LE2.Game;
import com.IB.LE2.asset.graphics.Screen;
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
import com.IB.LE2.world.level.tile.tiles.TSXData;
import com.IB.LE2.world.level.tile.tiles.TagTile;

public class TiledLevel extends Level {
	private static final long serialVersionUID = 1L;

	private TagReader reader;
	private LuaScript script;
	private TSXData tsx;

	public String path = "";
	public String tiled_xml = "";

	public TileCoord Spawnpoint;
	public ArrayList<EventVolume> event_volumes;
	public ArrayList<LineSegment> solid_geometry;

	public boolean LuaLoaded = false;

	public TiledLevel(String name) {
		initPathing(name);
		// add(new Emitter(128, 32 * 32, new PVector(0, 5), new Sprite(4, 0xFFFF00), 50,
		// 50, 1, this));
		// add(new TagEntity("/XML/Entities/TestZombie.xml", false));
		loadLevel(path);
		initLua();
	}

	public TiledLevel(String name, boolean preload) {
		initPathing(name);
		loadLevel(path);
		if (!preload) initLua();
	}

	public void initPathing(String name) {
		this.name = name;
		path = Assets.get(name);
		String lvn = path.substring(path.lastIndexOf('\\') + 1, path.length());

		Game.lvl_name = lvn;

		this.tiled_xml = path + "/" + lvn + ".tmx";
		System.out.println("TILED: " + path);
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
			// script.AddGeneralGlobals();
			script.addGlobal("level", this);
			if (Boot.get() != null) if (Boot.get().getMenu() != null) script.addGlobal("menu", Boot.get().getMenu());
			// ls.addGlobal("pc", getClientPlayer());
			// ls.addGlobal("key", Boot.get().getInput());
			// ls.addGlobal("key", Boot.get()); <= Crashes lua when used
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

		tsx = new TSXData(this);
		
		reader = new TagReader(tiled_xml, "level", new TagReadListener() {
			@Override
			public void TagsRead() {
				ProcessTags(false);
			}

			@Override
			public void TagsError() {

			}
		});

		reader.start();
		
		buildLightmap();
	}

	public void reload() {
		reader = new TagReader(tiled_xml, "level", new TagReadListener() {
			@Override
			public void TagsRead() {
				ProcessTags(true);
			}

			@Override
			public void TagsError() {

			}
		});

		reader.start();

		initLua();
	}

	private void ProcessTags(boolean reloading) {
		for (Tag tag : reader.getTags()) {
			String name = tag.name;
			String uri = tag.uri;
			String value = tag.value;

			switch (uri) {
			case "map":
				if (!tag.testEquality("tiledversion", VARS.VAL_TILED_VER))
					System.err.println("[WARN] Map was saved in an untested version of tiled!");
				if (!tag.testEquality("orientation", "orthogonal") || !tag.testEquality("renderorder", "right-down")
						|| !tag.testEquality("infinite", "0"))
					System.err.println("[WARN] Using unsupported map set-up!");
				if (tag.get("tilewidth", 32) + tag.get("tileheight", 32) != 64)
					System.err.println("[WARN] Non-standard tile dimensions!");

				this.width = tag.get("width", width);
				this.height = tag.get("height", height);
				if (!reloading) this.tiles = new int[width * height];
				this.solid_geometry = new ArrayList<>();
				this.event_volumes = new ArrayList<>();
				break;
			case "map.tileset":
				//
				tsx.read(path + "/" + tag.get("source", ""), tag.get("firstgid", 1));
				break;
			case "map.layer":
				if (!reloading) addTileLayer(tag);
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

			switch (type.toLowerCase()) {
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
			case "skybox":
				for (Tag property : child.getChild(0).getChildren()) {
					switch (property.get("name", "NULL")) {
					case "BaseBrightness":
						this.BaseBrightness = property.get("value", 0);
						break;
					case "DoDayCycle":
						this.DoDayCycle = property.get("value", false);
						break;
					}
				}
				break;
			case "c_mask":
			case "collidable":
			case "wall":
			case "solid":
				String polyline_points = child.getChild(0).get("points", "");
				String uri = child.getChild(0).uri;
				String[] points = polyline_points.split("\\s+");

				if (uri.endsWith("polygon")) {
					String[] pn = new String[points.length + 2];
					System.arraycopy(points, 0, pn, 0, points.length);
					pn[points.length + 1] = points[0];
					pn[points.length] = points[points.length - 1];
					points = pn;
				}

				Vertex previous = null;
				for (String s : points) {
					String[] p = s.split(",");
					Vertex v = new Vertex(Float.parseFloat(p[0]) + x, Float.parseFloat(p[1]) + y);

					if (previous != null) {
						LineSegment ls = new LineSegment(previous, v);
						ls.color = Long.decode("0x" + group_color.substring(1)).intValue();
						solid_geometry.add(ls);
					}

					previous = v;
				}

				break;
			case "spawnpoint":
				Spawnpoint = new TileCoord((int) x / 32, (int) y / 32);
				break;
			}
		}
	}

	public static byte[] DecompressZlib(byte[] data) {
		try {
			Inflater inflater = new Inflater();
			inflater.setInput(data);

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
			byte[] buffer = new byte[1024];

			while (!inflater.finished()) {
				int count = inflater.inflate(buffer);
				outputStream.write(buffer, 0, count);
			}
			outputStream.close();
			byte[] output = outputStream.toByteArray();
			inflater.end();

			return output;

		} catch (IOException | DataFormatException e) {
			
		}
		return data;
	}
	
	int numLayersProcessed = 0;

	public void addTileLayer(Tag layer) {
		String id = layer.get("id", "-1");
		String name = layer.get("name", "UnnamedLayer");

		Tag data = layer.getChild(0);
		String tileEncoding = data.get("encoding", "CSV");
		String tileCompression = data.get("compression", "NONE");

		tile_map.put(0, Tile.Air);

		int[] tiles;
		switch (tileEncoding) {
		case "base64":
			tiles = new int[width * height];
			lightmap = new int[width * height];
			
			byte[] b64bytes = Base64.getDecoder().decode(data.value);
		    byte[] bytes;
		    
		    switch (tileCompression) {
		    case "NONE":
		    	bytes = b64bytes;
		    	break;
		    default:
		    case "zlib":
				bytes = DecompressZlib(b64bytes);
		    	break;
		    }

			for (int i = 0; i < tiles.length; i++) {
				int head = i * 4;
				int val = ((bytes[head + 3] & 0xFF) << 24) |
						  ((bytes[head + 2] & 0xFF) << 16) |
						  ((bytes[head + 1] & 0xFF) <<  8) |
						  ((bytes[head + 0] & 0xFF) <<  0);
				tiles[i] = val;
			}
			break;
		default:
			Boot.log("Level Encoded in Unsupported Format. Assuming CSV-- Expect to crash!", "TiledLevel", true);
		case "CSV":
			tiles = explodeTileString(data.value);
			break;
		}
		
		/*for (int i = 0; i < tiles.length; i++) {
			if (tiles[i] != 0 && this.tiles[i] != tiles[i]) {
				this.tiles[i] = tiles[i];
			}
		}*/
		
		for (int i = 0; i < tiles.length; i++) {
			int existingId = this.tiles[i];
			int tile_id = tiles[i];
			Tile existingT = tile_map.get(existingId);
			Tile tile = tile_map.get(tile_id);

			if (tile_id != 0 && this.tiles[i] != tiles[i]) {
				if (numLayersProcessed > 0) {
					if (existingId != 0) {

						TagTile merged = tsx.mergeTiles(existingT, tile);
						tile_id = merged.id;
					}
				}
				this.tiles[i] = tile_id;
			}
		}
		numLayersProcessed++;
	}
	
	public void buildLightmap() {
		System.out.println("Generating Lightmap..");
		lightmap = new int[width * height];

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				Tile t = tile_map.get(tiles[x + y * width]);
				if (t.illumination() <= 0) 
					continue;
				
				
					float radius = t.illumination_radius();
					System.out.println("RADIUS: " + radius);
					if (!this.DoDayCycle) {
						radius = -1;
					}
					
					float attenuation = t.illumination_dropoff();

					TileCoord[] tiles = getTileNeighbors(x, y, (int)radius);
					for (int i = 0; i < tiles.length; i++) {
						double distance = calcDist(tiles[i].tx(), tiles[i].ty(), x, y);
						if (distance > radius && radius != -1) continue;
						
						int location = tiles[i].tx() + tiles[i].ty() * width;
						double drop = distance * attenuation;
						if (attenuation == -1)
							drop = 1;
						illuminate(location, t.illumination() / (drop));
					}
			}
		}
	}
	
	public double calcDist(double xo, double yo, double xx, double yy) {    
		return Math.sqrt((yy - yo) * (yy - yo) + (xx - xo) * (xx - xo));
	}
	
	public TileCoord[] getTileNeighbors(int xp, int yp, int radius) {
		int sidelen = 2 * radius + 1;
		int wid = sidelen;
		int hei = sidelen;
		
		int index = 0;
		int yz = yp - radius;
		int xz = xp - radius;
		if (radius == -1) {
			yz = 0;
			xz = 0;
			wid = this.width;
			hei = this.height;
		}
		TileCoord[] results = new TileCoord[wid * hei];
		
		for (int y = yz; y < yz + wid; y++) {
			for (int x = xz; x < xz + hei; x++) {
				results[index] = new TileCoord(x, y);
				index++;
			}
		}
		return results;
	}

	public void illuminate(int tileLocation, double value) {
		if (tileLocation < 0 || tileLocation > lightmap.length - 1) return;
		
		if (lightmap[tileLocation] < value) 
			lightmap[tileLocation] = (int) Math.ceil(value);
	}
	
	public void addTrigger(EventVolume t) {
		event_volumes = (event_volumes == null) ? new ArrayList<EventVolume>() : event_volumes;

		event_volumes.add(t);
	}

	public int[] explodeTileString(String tiles) {
		return Arrays.stream(tiles.split("\\s*,\\s*")).mapToInt(Integer::parseInt).toArray();
	}

	public void drawExtendedLevel(Screen screen) {
		if (Boot.drawDebug) {
			if (this.solid_geometry != null) {
				for (int i = 0; i < this.solid_geometry.size(); i++) {
					LineSegment ln = solid_geometry.get(i);
					Game.font8bit.render((int) ln.midpoint().x, (int) ln.midpoint().y, -3, 0xffFFFFFF, "LN: " + i,
							screen, true, false);
					ln.drawLine(screen, true);

					ln.Perpendicular().drawLine(screen, true);
				}
			}

			if (event_volumes != null) for (EventVolume ev : event_volumes) {
				if (ev != null) ev.render(screen);
			}
		}
	}

	public void TestEventVolumes(Entity e) {
		if (event_volumes != null) for (EventVolume ev : event_volumes) {
			if (ev != null) {
				if (script == null) {
					initLua(); // TODO: Figure out why scripts are nullified on level switching
				}
				if (ev.player_only) {
					if (e instanceof Player) ev.Test(e, script);
				} else {
					ev.Test(e, script);
				}
			}
		}
	}

	public boolean equals(Object other) {
		if (!(other instanceof TiledLevel)) return false;
		return (this.name.equals(((TiledLevel) other).name));
	}

	public void preLoadLevel(String lvln) {
		if (!Game.levelExists(lvln)) {
			System.out.println("Preloading " + lvln);
			Level lvl = new TiledLevel(lvln, true);
			if (!Game.levels.contains(lvl)) Game.levels.add(lvl);
		}
	}

	public void UpdateUnloaded() {
		if (LuaLoaded) StopLua();
	}

	public void MovePlayerTo(double x, double y, String path, boolean tile_mult) {
		Boot.get().getPlayer().setPositionTiled(x, y, path, tile_mult);
	}
}
