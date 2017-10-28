package com.IB.SL.level.tile.tiles;

import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.level.Level;
import com.IB.SL.level.tile.Tile;

public class HellBrick extends Tile {

	public HellBrick(Sprite sprite) {
		super(sprite);

	}
	
	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << 4, y << 4, HellBrick);


}
	
	public boolean solid() {
		return false;
}
	
	public boolean solidtwo() {
		return false;
}	
	public int  getHex() {
		return Level.HellBrickHex;
	}
}