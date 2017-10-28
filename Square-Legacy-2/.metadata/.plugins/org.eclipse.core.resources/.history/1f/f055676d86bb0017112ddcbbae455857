package com.IB.SL.level.worlds;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.util.Random;

import javax.imageio.ImageIO;

import com.IB.SL.Game;
import com.IB.SL.entity.mob.Player;
import com.IB.SL.entity.mob.bosses.Occulus;
import com.IB.SL.entity.mob.hostile.Siphon;
import com.IB.SL.entity.mob.hostile.UndeadCaster;
import com.IB.SL.entity.mob.hostile.VoidCharger;
import com.IB.SL.entity.mob.hostile.VoidSlinger;
import com.IB.SL.level.Level;
import com.IB.SL.level.TileCoord;
import com.IB.SL.level.interactables.Chest;

public class Dungeon02 extends Level implements Serializable{
	/**
	 * 
	 */
	protected static java.util.Random Random = new Random();
	public static java.util.Random random = Random;
	public Dungeon02(String path) {
		super(path);
	}
	
	
	
	
	protected void loadLevel(String path) {
		minimap_enabled = true;
		Level.Overworld = false;
		SpawnList.clear();
		SpawnTime_MOD = 100;
		
		SpawnList.add(new VoidSlinger(-1, -1));
		
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
			BufferedImage image = ImageIO.read(Dungeon02.class.getResource(path));
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
			BufferedImage overlayImage = ImageIO.read(Dungeon02.class.getResource("/overlays" + path));
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
		
		Game.currentLevelId = Maps.dungeon2Id;

		
		for (int i = 0; i < 1; i++) {
		//add(new AStarTracker(296, 381));
	 //  add(new Tracker(296, 381));
		//add(new Dumby(296, 381));
		}
		
		for(int i = 0; i < 80; i++){
			int sx = (random.nextInt((228 - 1) + 1) + 1);
			int sy = (random.nextInt((225 - 1) + 1) + 1);
			/*int sx = (random.nextInt(64) + 15) * 16;
			int sy = (random.nextInt(97) + 15) * 16;*/
			//while(!returnTileXY(tile, sx, sy).solid()) {
			if(!returnTileXY(sx, sy).solid()) {
				add(new VoidCharger(sx, sy));
			}
				if(!returnTileXY(sx - 2, sy + 2).solid()) {
				add(new VoidCharger(sx - 2, sy + 2));
				}
				if(!returnTileXY(sx + 2, sy - 2).solid()) {
					add(new VoidCharger(sx + 2, sy - 2));
				}					
				if(!returnTileXY(sx + 3, sy - 3).solid()) {
					add(new VoidCharger(sx + 3, sy - 3));
				}
				if(!returnTileXY(sx + 3, sy + 3).solid()) {
					add(new VoidCharger(sx + 3, sy + 3));
				}

			}
				//sx = 0;
			//	sy = 0;
			//}
			/*	if (!returnTileXY(tile, sx, sy).solid()) {

	         }*/
		
		for(int i = 0; i < 50; i++){
			int sx = (random.nextInt((164 - 1) + 1) + 1);
			int sy = (random.nextInt((175 - 1) + 1) + 1);
			/*int sx = (random.nextInt(64) + 15) * 16;
			int sy = (random.nextInt(97) + 15) * 16;*/
			//while(!returnTileXY(tile, sx, sy).solid()) {
			if(!returnTileXY(sx, sy).solid()) {
				add(new Siphon(sx, sy));
			}
			
	
			
			
				//sx = 0;
			//	sy = 0;
			//}
			/*	if (!returnTileXY(tile, sx, sy).solid()) {

	         }*/
		}
		
		for(int i = 0; i < 60; i++){
			int sx = (random.nextInt((144 - 1) + 1) + 1);
			int sy = (random.nextInt((172 - 1) + 1) + 1);
			/*int sx = (random.nextInt(64) + 15) * 16;
			int sy = (random.nextInt(97) + 15) * 16;*/
			//while(!returnTileXY(tile, sx, sy).solid()) {
			if(!returnTileXY(sx, sy).solid()) {
				add(new UndeadCaster(sx, sy));
			}
		}
		
		for(int i = 0; i < 70; i++){
			int sx = (random.nextInt((164 - 1) + 1) + 1);
			int sy = (random.nextInt((175 - 1) + 1) + 1);
			/*int sx = (random.nextInt(64) + 15) * 16;
			int sy = (random.nextInt(97) + 15) * 16;*/
			//while(!returnTileXY(tile, sx, sy).solid()) {
			if(!returnTileXY(sx, sy).solid()) {
				add(new Chest(sx, sy, Chest.Type.RANDOM));
			}
		}
		
		for(int i = 0; i < 30; i++){
			int sx = (random.nextInt((164 - 1) + 1) + 1);
			int sy = (random.nextInt((175 - 1) + 1) + 1);
			/*int sx = (random.nextInt(64) + 15) * 16;
			int sy = (random.nextInt(97) + 15) * 16;*/
			//while(!returnTileXY(tile, sx, sy).solid()) {
			if(!returnTileXY(sx, sy).solid()) {
				//add(new Coin(49, 53, 50000, 1, Coin.Type.RANDOM));
			}
		}
		
		//add(new Coin(49, 53, 50000, 1, Coin.Type.RANDOM));

		//add(new CopperGuardian(93, 131));
		for(int i = 0; i < 3; i++){
			int sx = (random.nextInt((164 - 1) + 1) + 1);
			int sy = (random.nextInt((175 - 1) + 1) + 1);
			if(!returnTileXY(sx, sy).solid()) {
				add(new Occulus(sx, sy));
			}
		}
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
		player.setPosition(401, 736, Maps.mainId, true); 
	if (x == 131 && y == 45 || x == 132 && y == 45) {
		player.setPosition(31, 46, Maps.voidBossRoomId, true); 	
			return;
		}
		player.setPosition(new TileCoord(401, 736));

		}
}
	
