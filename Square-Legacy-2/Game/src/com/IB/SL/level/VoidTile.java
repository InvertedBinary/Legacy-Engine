package com.IB.SL.level;

import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.level.tile.Tile;

public class VoidTile extends Tile {

	public VoidTile(Sprite sprite) {
		super(sprite);
	}
	
	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << 4, y << 4, VoidTile);
		
		
	}
		public boolean solid() {
			return true;
	}
		public boolean solidtwo() {
			return false;
		}
	}
