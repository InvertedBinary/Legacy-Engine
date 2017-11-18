package com.IB.SL.level.tile.tiles;

import java.util.Random;

import com.IB.SL.VARS;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.level.Level;
import com.IB.SL.level.tile.Tile;

public class Water extends Tile  {

	//private static AnimatedSprite water = new AnimatedSprite(SpriteSheet.Water1, 16, 16, 2);
	
	//private int time = 0;
	//private static final AnimatedSprite water = new AnimatedSprite(SpriteSheet.water_anim, 16, 16, 1);
	
	
	static int anim = 0;
	static int s = 1;
	public static Sprite sprite;
	final Random random = new Random();

	public Water(Sprite sprite) {
		super(sprite);
		this.sprite = sprite;
	}

	public static void update() {
		anim++;
		if (anim % 48 == 0) {
			s *= -1;
		}
		if (s == -1) sprite = Sprite.water;
		if (s == 1) sprite = Sprite.water2;
	}

	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << VARS.TILE_BIT_SHIFT, y << VARS.TILE_BIT_SHIFT, sprite);
	}
	
	public stepSound StepSound() {
		return stepSound.Water;
	}
	
	public boolean solid() {
		return false;
}
	
	public boolean solidtwo() {
		return false;
	}
	
	public int getHex() {
		return Level.WaterHex;
	}
}