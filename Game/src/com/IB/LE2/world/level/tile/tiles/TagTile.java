package com.IB.LE2.world.level.tile.tiles;

import com.IB.LE2.media.graphics.AnimatedSprite;
import com.IB.LE2.media.graphics.Screen;
import com.IB.LE2.media.graphics.Sprite;
import com.IB.LE2.util.VARS;
import com.IB.LE2.world.level.tile.Tile;

public class TagTile extends Tile {

	public int id;
	
	private AnimatedSprite asprite;
	private boolean animated = false;

	public TagTile(int id, Sprite sprite) {
		this.id = id;
		this.sprite = sprite;
	}
	
	public TagTile(int id, AnimatedSprite asprite) {
		this.id = id;
		this.sprite = asprite.getSprite();
		this.animated = true;
	}
	
	public void render(int x, int y, Screen screen) {
		screen.DrawTile(x << VARS.TILE_BIT_SHIFT, y << VARS.TILE_BIT_SHIFT, sprite);
	}
	
	public void update() {
		if (!animated) return;
		this.asprite.update();
		this.sprite = asprite.getSprite();
	}
	
	public int getID() {
		return id;
	}
	
	public boolean liquid() {
		return false;
	}
	
	public boolean illuminator() {
		return false;
	}
}