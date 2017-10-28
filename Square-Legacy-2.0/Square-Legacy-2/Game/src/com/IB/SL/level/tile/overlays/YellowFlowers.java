package com.IB.SL.level.tile.overlays;

import com.IB.SL.Game;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.level.tile.Tile;

public class YellowFlowers extends Tile {

	public YellowFlowers(Sprite sprite) {
		super(sprite);

	}
	
	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << Game.TILE_BIT_SHIFT, y << Game.TILE_BIT_SHIFT, YellowFlowers);


}
	
	public boolean solid() {
		return false;
}
	
	public boolean solidtwo() {
		return false;
}
}