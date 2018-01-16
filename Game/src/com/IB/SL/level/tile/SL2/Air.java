package com.IB.SL.level.tile.SL2;

import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.level.tile.Tile;

public class Air extends Tile {

	public Air(Sprite sprite) {
		super(sprite);

	}
	
	public void render(int x, int y, Screen screen) {
		//screen.renderTile(x << VARS.TILE_BIT_SHIFT, y << VARS.TILE_BIT_SHIFT, BrickCeiling);
	}
	
	public int getHex() {
		return -1;
	}
	
	public boolean solid() {
		return false;
}
	
	public boolean solidtwo() {
		return false;
}
}