package com.IB.SL.level.worlds;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.util.Random;

import javax.imageio.ImageIO;

import com.IB.SL.Game;
import com.IB.SL.entity.inventory.item.consumables.CoinBag;
import com.IB.SL.entity.mob.Player;
import com.IB.SL.entity.mob.bosses.CopperGuardian;
import com.IB.SL.entity.mob.bosses.Occulus;
import com.IB.SL.entity.mob.hostile.RockGolem;
import com.IB.SL.entity.mob.hostile.UndeadCaster;
import com.IB.SL.entity.mob.hostile.Zombie;
import com.IB.SL.level.Level;
import com.IB.SL.level.TileCoord;
import com.IB.SL.level.interactables.Chest;

public class Dungeon01 extends Level implements Serializable{
	/**
	 * 
	 */
	protected static java.util.Random Random = new Random();
	public static java.util.Random random = Random;
	public Dungeon01(String path) {
		super(path);
	}
	
	
	
	
	protected void loadLevel(String path) {
		minimap_enabled = true;
		Level.Overworld = false;
		SpawnList.clear();
		SpawnTime_MOD = 100;
		
		SpawnList.add(new RockGolem(-1, -1));
		
		/*try {
			BufferedImage image = ImageIO.read(swampLevel.class.getResource(path));
			int w = width = image.getWidth();
			int h = height = image.getHeight();

			tiles = new int[w * h];
			image.getRGB(0, 0, w, h, tiles, 0, w);
		}catch (IOException e) {	
			e.printStackTrace();
			System.out.println("IOException! Failed To Load Level File!");
		}*/
		
		try {
			BufferedImage image = ImageIO.read(Dungeon01.class.getResource(path));
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
			BufferedImage overlayImage = ImageIO.read(Dungeon01.class.getResource("/overlays" + path));
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
		
		
		Game.currentLevelId = Maps.dungeon1Id;

		
		
		for (int i = 0; i < 1; i++) {
		//add(new AStarTracker(296, 381));
	 //  add(new Tracker(296, 381));
		//add(new Dumby(296, 381));
		}
		
		for(int i = 0; i < 140; i++){
			int sx = (random.nextInt((228 - 1) + 1) + 1);
			int sy = (random.nextInt((225 - 1) + 1) + 1);
			/*int sx = (random.nextInt(64) + 15) * 16;
			int sy = (random.nextInt(97) + 15) * 16;*/
			//while(!returnTileXY(tile, sx, sy).solid()) {
			if(!returnTileXY(sx, sy).solid()) {
				add(new UndeadCaster(sx, sy));
			}
				//sx = 0;
			//	sy = 0;
			//}
			/*	if (!returnTileXY(tile, sx, sy).solid()) {

	         }*/
		}
		
		for(int i = 0; i < 100; i++){
			int sx = (random.nextInt((164 - 29) + 29) + 29);
			int sy = (random.nextInt((175 - 62) + 62) + 62);
			/*int sx = (random.nextInt(64) + 15) * 16;
			int sy = (random.nextInt(97) + 15) * 16;*/
			//while(!returnTileXY(tile, sx, sy).solid()) {
			if(!returnTileXY(sx, sy).solid()) {
				add(new Zombie(sx, sy));
			}
			
			
			
			
				//sx = 0;
			//	sy = 0;
			//}
			/*	if (!returnTileXY(tile, sx, sy).solid()) {

	         }*/
		}
		
		for(int i = 0; i < 40; i++){
			int sx = (random.nextInt((228 - 29) + 29) + 29);
			int sy = (random.nextInt((170 - 62) + 62) + 62);
			/*int sx = (random.nextInt(64) + 15) * 16;
			int sy = (random.nextInt(97) + 15) * 16;*/
			//while(!returnTileXY(tile, sx, sy).solid()) {
			if(!returnTileXY(sx, sy).solid()) {
				//add(new PoisonZombie(sx, sy));
			}
		}
		
		for(int i = 0; i < 35; i++){
			int sx = (random.nextInt((164 - 59) + 59) + 59);
			int sy = (random.nextInt((175 - 72) + 72) + 72);
			/*int sx = (random.nextInt(64) + 15) * 16;
			int sy = (random.nextInt(97) + 15) * 16;*/
			//while(!returnTileXY(tile, sx, sy).solid()) {
			if(!returnTileXY(sx, sy).solid()) {
				add(new Chest(sx, sy, Chest.Type.RANDOM));
			}
		}
		
		for(int i = 0; i < 15; i++){
			int sx = (random.nextInt((164 - 1) + 1) + 1);
			int sy = (random.nextInt((175 - 1) + 1) + 1);
			if(!returnTileXY(sx, sy).solid()) {
				add(new CoinBag(49, 53, 50000, 1, CoinBag.Type.LARGE));
			}
		}
		
		//add(new Coin(49, 53, 50000, 1, Coin.Type.RANDOM));

		add(new CopperGuardian(93, 131));
		add(new Occulus(44, 40));
		
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
		player.setPosition(729, 363, Maps.mainId, true); 

		if (x == 296 && y == 464) {//296, 464 Spawn Haven
			player.setPosition(new TileCoord(26, 11));
			return;
		}
		player.setPosition(new TileCoord(729, 363));

		}
}
	
