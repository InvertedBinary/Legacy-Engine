package com.IB.SL.level.tile.SL2;

import com.IB.SL.VARS;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.level.tile.Tile;
import com.IB.SL.util.shape.PhysicsBody;
import com.IB.SL.util.shape.Rectangle;

public class XML_Tile extends Tile {


	public stepSound st;
	public int hex;
	public boolean solid = false;
	public boolean solidTwo = false;
	public boolean isExit = false;
	public String name = "";
	
	public PhysicsBody pb;
	
	public XML_Tile(String name, Sprite sprite, stepSound st, int hex, boolean solid, boolean solidTwo, boolean isExit) {
		this.sprite = sprite;
		this.st = st;
		this.hex = hex;
		this.solid = solid;
		this.solidTwo = solidTwo;
		this.isExit = isExit;
		this.name = name;
	}
	
	public XML_Tile(Sprite sprite, stepSound st, int hex) {
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
		return solid;
}
	
	public boolean solidtwo() {
		return solidTwo;
}
	
	public boolean exit() {
		return isExit;
	}
	
}