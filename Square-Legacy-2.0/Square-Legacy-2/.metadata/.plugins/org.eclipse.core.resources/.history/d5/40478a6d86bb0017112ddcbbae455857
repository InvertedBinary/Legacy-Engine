package com.IB.SL.level.worlds;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.util.Random;

import javax.imageio.ImageIO;

import com.IB.SL.Game;
import com.IB.SL.entity.mob.Player;
import com.IB.SL.entity.mob.bosses.SwarmBoss;
import com.IB.SL.entity.mob.hostile.DesertBeetle;
import com.IB.SL.entity.mob.hostile.DesertFly;
import com.IB.SL.entity.mob.hostile.FireElemental;
import com.IB.SL.level.Level;
import com.IB.SL.level.TileCoord;
import com.IB.SL.level.interactables.Chest;

public class Dungeon04 extends Level implements Serializable{
	/**
	 * 
	 */
	protected static java.util.Random Random = new Random();
	public static java.util.Random random = Random;
	public Dungeon04(String path) {
		super(path);
	}
	
	protected void loadLevel(String path) {
		minimap_enabled = true;
		Level.Overworld = false;
		SpawnList.clear();
		SpawnTime_MOD = 100;
		
		SpawnList.add(new FireElemental(-1, -1));
		SpawnList.add(new DesertBeetle(-1, -1));
		SpawnList.add(new DesertFly(-1, -1));
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
			BufferedImage image = ImageIO.read(Dungeon04.class.getResource(path));
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
			BufferedImage overlayImage = ImageIO.read(Dungeon04.class.getResource("/overlays" + path));
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
		
		Game.currentLevelId = Maps.dungeon4Id;
		
		for(int i = 0; i < 130; i++){
			int sx = (random.nextInt((356 - 1) + 1) + 1);
			int sy = (random.nextInt((356 - 1) + 1) + 1);
			/*int sx = (random.nextInt(64) + 15) * 16;
			int sy = (random.nextInt(97) + 15) * 16;*/
			//while(!returnTileXY(tile, sx, sy).solid()) {
			if(!returnTileXY(sx, sy).solid()) {
				add(new Chest(sx, sy, Chest.Type.RANDOM));
			}
		}
		
		//add(new Coin(49, 53, 50000, 1, Coin.Type.RANDOM));

		//add(new CopperGuardian(93, 131));
		add(new SwarmBoss(123, 194));

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
		player.setPosition(654, 82, Maps.mainId, true); 

		player.setPosition(new TileCoord(517, 400));

		}
}
	
