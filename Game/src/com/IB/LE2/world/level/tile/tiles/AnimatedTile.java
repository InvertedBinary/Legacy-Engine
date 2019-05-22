package com.IB.LE2.world.level.tile.tiles;

import com.IB.LE2.media.graphics.Screen;
import com.IB.LE2.media.graphics.Sprite;
import com.IB.LE2.util.VARS;
import com.IB.LE2.world.level.tile.Tile;

public class AnimatedTile extends Tile {

	public AnimatedTile(Sprite sprite) {
		super(sprite);
	}

	public static void update() {
	}

	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << VARS.TILE_BIT_SHIFT, y << VARS.TILE_BIT_SHIFT, sprite);
	}
}
