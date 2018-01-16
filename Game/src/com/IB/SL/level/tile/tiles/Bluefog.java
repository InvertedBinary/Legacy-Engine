package com.IB.SL.level.tile.tiles;

import com.IB.SL.VARS;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.level.Level;
import com.IB.SL.level.tile.Tile;

public class Bluefog extends Tile {

	public Bluefog(Sprite sprite) {
		super(sprite);

	}
	
	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << VARS.TILE_BIT_SHIFT, y << VARS.TILE_BIT_SHIFT, Bluefog);
	


}
	
	public boolean solid() {
		return false;
}
	
	public boolean solidtwo() {
		return true;
}	
	public int  getHex() {
		return Level.BluefogHex;
	}
}