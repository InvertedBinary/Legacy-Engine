package com.IB.SL.level.tile.tiles;

import com.IB.SL.VARS;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.level.Level;
import com.IB.SL.level.tile.Tile;

public class Hellstone extends Tile {

	public Hellstone(Sprite sprite) {
		super(sprite);
		// TODO Auto-generated constructor stub
	}
	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << VARS.TILE_BIT_SHIFT, y << VARS.TILE_BIT_SHIFT, Hellstone);
	
	
}	
	public int  getHex() {
		return Level.HellstoneHex;
	}
}
