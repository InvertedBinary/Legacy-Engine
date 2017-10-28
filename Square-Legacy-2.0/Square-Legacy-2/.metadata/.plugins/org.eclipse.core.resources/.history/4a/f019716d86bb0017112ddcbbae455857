package com.IB.SL.level.worlds;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

import com.IB.SL.Game;
import com.IB.SL.entity.mob.Player;
import com.IB.SL.level.Level;
import com.IB.SL.level.TileCoord;

public class swampLevel extends Level implements Serializable{
	public swampLevel(String path) {
		super(path);
	}
	
	
	
	
	protected void loadLevel(String path) {
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
			BufferedImage image = ImageIO.read(swampLevel.class.getResource(path));
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
			BufferedImage overlayImage = ImageIO.read(swampLevel.class.getResource("/overlays" + path));
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
		
		Game.currentLevelId = Maps.swampId;

		
		for (int i = 0; i < 1; i++) {
		//add(new AStarTracker(296, 381));
	 //  add(new Tracker(296, 381));
		//add(new Dumby(296, 381));
		}
		
		
		
	}
	

	
	
	
	
	protected void generateLevel() {
		for (int y = 0; y < 64; y++) {
			for (int x = 0; x < 64; x++) {
				getTile(x, y);
				}
			}
		}		
	
	public void resetLevel(Player player) {
		Game.get().setLevel(new SpawnHaven(Maps.SpawnHaven));
		Game.get().getLevel().add(player);
		player.setPosition(new TileCoord(277, 477));
	}
	
	
	public void checkExits(Player player, Level level, int x, int y) {
		refresh();
		Game.get().setLevel(new MainLevel(Maps.main));
		level = Game.get().getLevel();
		level.add(player);
		
		if (x == 296 && y == 464) {//296, 464 Spawn Haven
			player.setPosition(new TileCoord(26, 11));
			return;
		}
		player.setPosition(new TileCoord(277, 477));

		}
}
	
