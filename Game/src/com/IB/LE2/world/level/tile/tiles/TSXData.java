package com.IB.LE2.world.level.tile.tiles;

import com.IB.LE2.media.graphics.Sprite;
import com.IB.LE2.media.graphics.SpriteSheet;
import com.IB.LE2.util.FileIO.Tag;
import com.IB.LE2.util.FileIO.TagReadListener;
import com.IB.LE2.util.FileIO.TagReader;
import com.IB.LE2.world.level.tile.Tile;
import com.IB.LE2.world.level.worlds.TiledLevel;

public class TSXData {
	
	private TagReader tempReader;
	private TiledLevel level;
	
	private int num_tiles = 0;
	
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
	
	private void addTile(SpriteSheet sheet, int localid, int vanityid) {
		System.out.println("LOCALID: " + localid + " :: " + vanityid);
		Sprite spr = Tile.GenSpriteFromId(sheet, localid);
		TagTile t = new TagTile(vanityid, spr);
		level.tile_map.put(vanityid, t);
	}
	
	private void addTile(Tag t, int id, int vanityid) {
		
	}
	
	private void mergeTiles(TagTile base, TagTile other) {
		
	}

}
