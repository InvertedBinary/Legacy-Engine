package com.IB.LE2.world.level.tile.tiles;

import com.IB.LE2.media.graphics.Screen;
import com.IB.LE2.media.graphics.Sprite;
import com.IB.LE2.util.VARS;
import com.IB.LE2.world.level.tile.Tile;


@Deprecated
public class Terrain extends Tile {

	private stepSound st;
	private int hex;
	
	public Terrain(Sprite sprite) {
		super(sprite);
	}
	
	public Terrain(Sprite sprite, stepSound st, int hex) {
		super(sprite);
		this.st = st;
		this.hex = hex;
	}
	
	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << VARS.TILE_BIT_SHIFT, y << VARS.TILE_BIT_SHIFT, sprite);
}
	
	public int getHex() {
		return hex;
	}
	
	public stepSound StepSound() {
		return st;
	}
	
	public boolean solid() {
		return false;
}
	
	public boolean solidtwo() {
		return false;
}
	
	public boolean exit() {
		return false;
	}
	
}