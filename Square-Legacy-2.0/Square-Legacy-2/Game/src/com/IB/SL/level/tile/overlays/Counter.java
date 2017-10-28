package com.IB.SL.level.tile.overlays;

import com.IB.SL.Game;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.level.tile.Tile;

public class Counter extends Tile {
	public Counter(Sprite sprite) {
		super(sprite);
		this.sprite = sprite;
	}
	
	
	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << Game.TILE_BIT_SHIFT, y << Game.TILE_BIT_SHIFT, sprite);
	}
	
	public boolean solid() {
		return true;
}
	
	public boolean solidtwo() {
		return false;
}
	
	public boolean exit() {
		return false;
	}
}
