package com.IB.SL.level.worlds;

import java.util.Random;

import com.IB.SL.level.Level;

public class RandomLevel extends Level {
	
	private static final Random random = new Random();

	public RandomLevel(int width, int height) {
		super(width, height);
		this.minimap_enabled = false;
		this.Overworld = false;
	}
	
	
	protected void generateLevel() {
		for (int y = 0; y < width; y++) {
			for (int x = 0; x < width; x++) {
				tilesInt [x + y * width] = random.nextInt(4);
				//tiles[294 + 380 * width] = random.nextInt(3);
				//getTile(x, y);
			}
		}
	}

}