package com.IB.SL.level.worlds;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.util.Random;

import javax.imageio.ImageIO;

import com.IB.SL.Game;
import com.IB.SL.entity.mob.Player;
import com.IB.SL.entity.mob.npc.TestQG;
import com.IB.SL.entity.mob.peaceful.Alice;
import com.IB.SL.entity.mob.peaceful.Guard;
import com.IB.SL.entity.mob.peaceful.Villager01;
import com.IB.SL.level.Level;
import com.IB.SL.level.TileCoord;
import com.IB.SL.level.interactables.ArcaneBench;
import com.IB.SL.level.interactables.Chest;
import com.IB.SL.level.interactables.EnchArray;
import com.IB.SL.level.interactables.Shop;

public class IceHaven extends Level implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3216105815855688772L;
	/**
	 * 
	 */
	/*Random randomY = new Random();
	Random randomX = new Random();
	public java.util.Random randomXX = randomX;
	int spawnRateX = (randomX.nextInt((41 - 10) + 10) + 10);
	int spawnRateY = (randomY.nextInt((38 - 20) + 20) + 20);*/
	protected static java.util.Random Random = new Random();
	public static java.util.Random random = Random;
	
	public IceHaven(String path) {
		super(path);
	}
	
	protected void loadLevel(String path) {
		minimap_enabled = false;
		Level.Overworld = false;
		
		SpawnList.clear();
		SpawnTime_MOD = -1;
		
		try {
			BufferedImage image = ImageIO.read(IceHaven.class.getResource(path));
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
			BufferedImage overlayImage = ImageIO.read(IceHaven.class.getResource("/overlays" + path));
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
		
		Game.currentLevelId = Maps.IceHavenId;

		//add(new Coin(20, 15, 50000, 1, Coin.Type.RANDOM));
		
		//add(new CopperGuardian(25, 15));
		
		
		
		for(int i = 0; i < 15; i++){
			int sx = (random.nextInt((67 - 36) + 36) + 36);
			int sy = (random.nextInt((76 - 39) + 39) + 39);
			while(!returnTileXY(sx, sy).solid()) {
				add(new Villager01(sx, sy));
				sx = 0;
				sy = 0;
			}
		}
		

	}
	
	protected void generateRandomLevel() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				tiles[x + y * width] = random.nextInt(4); // 0-3
			}
		}
	}
	
	protected void generategetLevel() {

		for (int y = 0; y < 64; y++) {
			for (int x = 0; x < 64; x++) {
				getTile(x, y);
				}
			}
		}		
	
	public void resetLevel(Player player) {
		player.setPosition(643, 220, Maps.mainId, true);
	}
	
	
	public void checkExits(Player player, Level level, int x, int y) { 
		refresh();
		//if (Game.getGame().developerPlaying) {
		
		player.setPosition(838, 131, Maps.mainId, true); 
		}

//}




}
	
