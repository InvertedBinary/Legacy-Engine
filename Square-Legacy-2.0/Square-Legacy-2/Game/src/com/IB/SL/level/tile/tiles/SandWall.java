package com.IB.SL.level.tile.tiles;

import com.IB.SL.Game;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.level.Level;
import com.IB.SL.level.tile.Tile;

public class SandWall extends Tile {

	public SandWall(Sprite sprite) {
		super(sprite);

	}
	
	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << Game.TILE_BIT_SHIFT, y << Game.TILE_BIT_SHIFT, SandWall);


}
	
	public int getHex() {
		return Level.SandWallHex;
	}
	
	public boolean solid() {
		return true;
}
	
	public boolean solidtwo() {
		return false;
}
}