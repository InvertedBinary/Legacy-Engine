package com.IB.LE2.world.level.tile;

import java.util.HashMap;

import com.IB.LE2.media.graphics.Screen;
import com.IB.LE2.media.graphics.Sprite;
import com.IB.LE2.media.graphics.SpriteSheet;
import com.IB.LE2.world.level.TileCoord;
import com.IB.LE2.world.level.tile.tiles.Air;

public class Tile {
	
	public Sprite sprite;
	public static HashMap<Integer, Tile> TileIndex = new HashMap<Integer, Tile>();
	public static Tile Air = new Air();
	
	public Tile(Sprite sprite) { this.sprite = sprite; }
	public Tile() { }
	
	public static Tile returnTile(int id) {
		return TileIndex.get(id);
	}
	
	public static Sprite GenSpriteFromId(SpriteSheet sheet, int id) {
		int x = id % TileCoord.TILE_SIZE - 1;
		int y = id / TileCoord.TILE_SIZE;
		
		return new Sprite(TileCoord.TILE_SIZE, x, y, sheet);
	}
	
	
	public void render(int x, int y, Screen screen) { }
	
	public boolean liquid() { return false; }
	public boolean illuminator() { return false; }
	
}
