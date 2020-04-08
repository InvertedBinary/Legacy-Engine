package com.IB.LE2.world.level.tile.tiles;

import com.IB.LE2.media.graphics.Screen;
import com.IB.LE2.media.graphics.Sprite;
import com.IB.LE2.util.VARS;
import com.IB.LE2.world.level.tile.Tile;

public class XML_Tile extends Tile {

	public int id;

	public XML_Tile(int id, Sprite sprite) {
		this.id = id;
		this.sprite = sprite;
	}
	
	public void render(int x, int y, Screen screen) {
		screen.DrawTile(x << VARS.TILE_BIT_SHIFT, y << VARS.TILE_BIT_SHIFT, sprite);
	}
	
	public int getID() {
		return id;
	}
	
	public boolean liquid() {
		return false;
	}
	
	public boolean illuminator() {
		return false;
	}
}