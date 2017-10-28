package com.IB.SL.level.tile.tiles;

import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.level.Level;
import com.IB.SL.level.tile.Tile;

public class MossCeiling extends Tile {

	public MossCeiling(Sprite sprite) {
		super(sprite);

	}
	
	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << 4, y << 4, MossCeiling);


}
	
	public boolean solid() {
		return true;
}
	
	public boolean solidtwo() {
		return true;
}	
	public int  getHex() {
		return Level.MossCeilingHex;
	}
}