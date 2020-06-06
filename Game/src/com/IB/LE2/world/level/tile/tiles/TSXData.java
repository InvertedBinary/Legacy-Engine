package com.IB.LE2.world.level.tile.tiles;

import java.util.ArrayList;

import com.IB.LE2.Boot;
import com.IB.LE2.asset.graphics.Sprite;
import com.IB.LE2.asset.graphics.SpriteSheet;
import com.IB.LE2.util.FileIO.Tag;
import com.IB.LE2.util.FileIO.TagReadListener;
import com.IB.LE2.util.FileIO.TagReader;
import com.IB.LE2.world.level.tile.Tile;
import com.IB.LE2.world.level.worlds.TiledLevel;

public class TSXData {
	
	private TagReader tempReader;
	private TiledLevel level;
	
	private int num_tiles = 0;
	private int merge_tiles = 0;
	
	public TSXData(TiledLevel level) {
		this.level = level;
	}
	
	public void read(String path, int startingID) {
		tempReader = new TagReader(path, "tsxdata", new TagReadListener() {
			@Override
			public void TagsRead() {
				ProcessTags(startingID);
			}

			@Override
			public void TagsError() {

			}
		});
		
		tempReader.start();
	}
	
	private void ProcessTags(int startingID) {
		int tile_count = 0;
		SpriteSheet tilesheet = SpriteSheet.get("Terrain");
		
		for (Tag tag : tempReader.getTags()) {
			String name = tag.name;
			String uri = tag.uri;
			String value = tag.value;
			
			switch (uri) {
			case "tileset":
				tile_count = tag.get("tilecount", 1024);
				this.num_tiles += tile_count;
				break;
			case "tileset.image":
				String imagepath = level.path + "/" + tag.get("source", "../../Textures/assets/World/blocks32.png");
				tilesheet = new SpriteSheet(imagepath, tag.get("width", 0), tag.get("height", 0));
				break;
			case "tileset.tile":
				int id = tag.getAttribute("id", -1);
				Tile t = addTile(tilesheet, id, id + startingID);
				Tag props_parent = tag.getChild(0);
				if (!props_parent.name.contentEquals("properties")) {
					break;
				}
				ArrayList<Tag> props = tag.getChild(0).getChildren();
				for (int i = 0; i < props.size(); i++) {
					Tag PropTag = props.get(i);
					String prop_name = PropTag.getAttribute("name", "null");
					switch(prop_name) {
						case "illuminator":
							t.setIllumination(PropTag.getAttribute("value", t.illumination()));
						break;
						case "light_radius":
							t.setIlluminationRadius(PropTag.getAttribute("value", t.illumination_radius()));
						break;
						case "light_attenuation":
							System.out.println("SETTING ATTENUATION: ");
							t.setIlluminationDropoff(PropTag.getAttribute("value", t.illumination_dropoff()));
							System.out.println(t.illumination_dropoff());
						break;
						default: 
							Boot.log("UNRECOGNIZED TILE PROPERTY -- " + prop_name, "TSXData", true);
						break;
					}
				}
				break;
			}
		}
		
		for (int i = 0; i < tile_count; i++) {
			addTile(tilesheet, i, i + startingID);
		}
	}
	
	public int getNumTiles() {
		return num_tiles;
	}
	
	public int getTotNumTiles() {
		return num_tiles + merge_tiles;
	}
	
	private Tile addTile(SpriteSheet sheet, int localid, int vanityid) {
		if (level.tile_map.containsKey(vanityid)) return level.tile_map.get(vanityid);
		
		Sprite spr = Tile.GenSpriteFromId(sheet, localid);
		TagTile t = new TagTile(vanityid, spr);
		level.tile_map.put(vanityid, t);
		
		return t;
	}
	
	public TagTile mergeTiles(Tile base, Tile other) {
		if (base.sprite == null || other.sprite == null)
			return (TagTile) base;
		
		Sprite composite = new Sprite(base.sprite, other.sprite);
		TagTile t = new TagTile(getTotNumTiles() + 1, composite);
		
		int newIllumination = (int) max(base.illumination(), other.illumination());
		float newLightRadius = max(base.illumination_radius(), other.illumination_radius());
		float newLightDrop = min(base.illumination_dropoff(), other.illumination_dropoff());

		t.setIllumination(newIllumination);
		t.setIlluminationRadius(newLightRadius);
		t.setIlluminationDropoff(newLightDrop);
		
		level.tile_map.put(t.id, t);
		merge_tiles++;
		return t;
	}
	
	public float max(float a, float b) {
		if (a == -1 || b == -1) return -1;
		return Math.max(a, b);
	}
	
	public float min(float a, float b) {
		if (a == -1 || b == -1) return -1;
		return Math.min(a, b);
	}
}
