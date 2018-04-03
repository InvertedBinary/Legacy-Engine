package com.IB.SL.level;

import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.level.tile.Tile;

public class BlueBed extends Tile {

	public BlueBed(Sprite sprite) {
		super(sprite);

	}
	
	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << 4, y << 4, BlueBed);
	


}
}