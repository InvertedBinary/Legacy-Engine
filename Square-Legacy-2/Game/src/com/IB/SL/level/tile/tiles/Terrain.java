package com.IB.SL.level.tile.tiles;

import com.IB.SL.Game;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.level.tile.Tile;

public class Terrain extends Tile {


	private stepSound st;
	private int hex;
	
	public Terrain(Sprite sprite) {
		super(sprite);
		this.sprite = sprite;
	}
	
	public Terrain(Sprite sprite, stepSound st, int hex) {
		super(sprite);
		this.st = st;
		this.hex = hex;
	}
	
	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << Game.TILE_BIT_SHIFT, y << Game.TILE_BIT_SHIFT, sprite);
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