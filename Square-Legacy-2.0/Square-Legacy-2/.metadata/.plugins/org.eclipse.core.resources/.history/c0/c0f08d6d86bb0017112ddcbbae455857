package com.IB.SL.level.worlds;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.util.Random;

import javax.imageio.ImageIO;

import com.IB.SL.Game;
import com.IB.SL.entity.mob.Player;
import com.IB.SL.entity.spawner.HealthPotSpawner;
import com.IB.SL.level.Level;
import com.IB.SL.level.interactables.Shop;

public class TutorialWorld extends Level implements Serializable{
	/**
	 * 
	 */
	protected static java.util.Random Random = new Random();
	public static java.util.Random random = Random;
	public TutorialWorld(String path) {
		super(path);
	}
	
	
	protected void loadLevel(String path) {
		minimap_enabled = true;
		Level.Overworld = false;
		
		SpawnList.clear();
		SpawnTime_MOD = -1;

		try {
			BufferedImage image = ImageIO.read(TutorialWorld.class.getResource(path));
			System.out.println("Overlay Path: " + "/overlays" + path);
			int w = width = image.getWidth();
			int h = height = image.getHeight();

			tiles = new int[w * h];
			image.getRGB(0, 0, w, h, tiles, 0, w);
		}catch (IOException e) {	
			e.printStackTrace();
			System.out.println("IOException! Failed To Load Level File!");
		}
		
		try {
			BufferedImage overlayImage = ImageIO.read(TutorialWorld.class.getResource("/overlays" + path));
			int overlayW = width = overlayImage.getWidth();
			int overlayH = height = overlayImage.getHeight();
			overlayTiles = new int[overlayW * overlayH];
			torchTiles = new int[overlayW * overlayH];
			overlayImage.getRGB(0,0,overlayW,overlayH,overlayTiles,0,overlayW);
			overlayImage.getRGB(0,0, overlayW, overlayH, torchTiles, 0, overlayW);
			System.out.println("Loaded Overlays");
		} catch (IOException e) {
			System.out.println("Failed To Load Torches");
		}
		
		Game.currentLevelId = Maps.tutWorldId;

		
		add(new HealthPotSpawner(71 * 16, 14 * 16, 3800, 1, this));
		
		add(new Shop(53, 21));

		

	}

	protected void generateLevel() {
		for (int y = 0; y < 64; y++) {
			for (int x = 0; x < 64; x++) {
				getTile(x, y);
				}
			}
		}		
	
	public void checkExits(Player player, Level level, int x, int y) {
		refresh();
		player.setPosition(401, 736, Maps.tutWorldId, true); 
	}
}
	
