package com.IB.SL.level.tile.SL2;

import com.IB.SL.VARS;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.level.tile.Tile;
import com.IB.SL.util.shape.PhysicsBody;

public class XML_Tile extends Tile {


	public stepSound st;
	public int id;
	public boolean solid = false;
	public boolean solidTwo = false;
	public boolean isExit = false;
	public boolean jumpThrough;
	public String name = "";
	
	public PhysicsBody pb;
	
	public XML_Tile(String name, Sprite sprite, stepSound st, int hex, boolean solid, boolean solidTwo, boolean jumpThrough, boolean isExit) {
		this.sprite = sprite;
		this.st = st;
		this.id = hex;
		this.solid = solid;
		this.solidTwo = solidTwo;
		this.jumpThrough = jumpThrough;
		this.isExit = isExit;
		this.name = name;
	}
	
	public XML_Tile(Sprite sprite, stepSound st, int hex) {
		super(sprite);
		this.st = st;
		this.id = hex;
	}
	
	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << VARS.TILE_BIT_SHIFT, y << VARS.TILE_BIT_SHIFT, sprite);
	}
	
	public int getID() {
		return id;
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
	
	public boolean jumpThrough() {
		return jumpThrough;
	}
	
}