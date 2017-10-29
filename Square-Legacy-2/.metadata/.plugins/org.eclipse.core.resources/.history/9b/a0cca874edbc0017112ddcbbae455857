package com.IB.SL.level.tile.tiles;

import com.IB.SL.Game;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.level.Level;
import com.IB.SL.level.tile.Tile;

public class DoorTile extends Tile {
	public DoorTile(Sprite sprite) {
		super(sprite);
	}
	
	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << Game.TILE_BIT_SHIFT, y << Game.TILE_BIT_SHIFT, new Sprite(16, 0xffFF3AFB));
		//screen.blendTiles(x << Game.TILE_BIT_SHIFT, y << Game.TILE_BIT_SHIFT, DoorTile, swirly);
	}
	
		public boolean solid() {
			return false;
	}
		public boolean solidtwo() {
			return false;
		}
		
		public boolean exit() {
			return true;
		}
		
		public int  getHex() {
			return Level.DoorHex;
		}
	}
