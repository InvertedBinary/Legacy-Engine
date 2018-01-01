package com.IB.SL.level.tile.tiles;

import com.IB.SL.VARS;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.level.tile.Tile;

public class TorchTile extends Tile {

	public TorchTile(Sprite sprite) {
		super(sprite);
	}

	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << VARS.TILE_BIT_SHIFT, y << VARS.TILE_BIT_SHIFT, TorchTile);
		// screen.renderLight(x << Game.TILE_BIT_SHIFT, y <<
		// Game.TILE_BIT_SHIFT, 20, Level.brightness, Level.brightness,
		// Level.brightness);

	}

	public boolean illuminator() {
		return true;
	}

	public boolean solid() {
		return true;
	}
}
