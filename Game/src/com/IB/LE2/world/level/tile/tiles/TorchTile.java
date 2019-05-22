package com.IB.LE2.world.level.tile.tiles;

import com.IB.LE2.media.graphics.Screen;
import com.IB.LE2.media.graphics.Sprite;
import com.IB.LE2.util.VARS;
import com.IB.LE2.world.level.tile.Tile;

@Deprecated
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
