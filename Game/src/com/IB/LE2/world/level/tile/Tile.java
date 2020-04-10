package com.IB.LE2.world.level.tile;

import com.IB.LE2.media.graphics.Screen;
import com.IB.LE2.media.graphics.Sprite;
import com.IB.LE2.media.graphics.SpriteSheet;
import com.IB.LE2.world.level.TileCoord;
import com.IB.LE2.world.level.tile.tiles.Air;

public class Tile {
	
	public Sprite sprite;
	public static Tile Air = new Air();
	
	public Tile(Sprite sprite) { this.sprite = sprite; }
	public Tile() { }
	
	public static Sprite GenSpriteFromId(SpriteSheet sheet, int id) {
		int math = (int)(Math.sqrt(sheet.getWidth() / TileCoord.TILE_SIZE * sheet.getHeight() / TileCoord.TILE_SIZE));
		int x = id % math;
		int y = id / math;
		System.out.println("SHEET STAT: " + sheet.getWidth() + " :: " + math + " XYPAIR: " + x + ", " + y);
		
		Sprite sp = new Sprite(TileCoord.TILE_SIZE, x, y, sheet);
		Sprite.sprites.put("TILE_" + id, sp);
		return sp;
	}
	
	
	public void render(int x, int y, Screen screen) { }
	public void update() { }
	
	public boolean liquid() { return false; }
	public boolean illuminator() { return false; }
	
}
