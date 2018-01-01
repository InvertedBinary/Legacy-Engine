package com.IB.SL.level.worlds;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import com.IB.SL.Boot;
import com.IB.SL.Game;
import com.IB.SL.entity.mob.Player;
import com.IB.SL.entity.mob.peaceful.Guard;
import com.IB.SL.entity.mob.peaceful.Horse;
import com.IB.SL.graphics.SpriteSheet;
import com.IB.SL.level.Level;
import com.IB.SL.level.TileCoord;
import com.IB.SL.level.interactables.Location_Shrine;
import com.IB.SL.level.interactables.Teleporter;
import com.IB.SL.util.Sound;

public class MainLevel extends Level{
		
	/**
	 * 
	 */

	public static boolean spawnASM = false;

	protected static java.util.Random Random = new Random();
	public static java.util.Random random = Random;
	//protected static Random randomYRandom = new Random();
	//public static int randomY = randomYRandom.nextInt(1 + 4);

	
	public MainLevel(String path) { 
		super(path);
	}
	
	
/*	public float noise(float x, float y) {
	    return simplexnoise(x / 5, y / 5);
	}
	
	private float simplexnoise(float f, float g) {
		return 0;
	}


	public float[][] generateOctavedSimplexNoise(int width, int height, int octaves, float roughness, float scale){
	      float[][] totalNoise = new float[width][height];
	       float layerFrequency = scale;
	       float layerWeight = 1;
	       float weightSum = 0;

	       for (int octave = 0; octave < octaves; octave++) {
	          //Calculate single layer/octave of simplex noise, then add it to total noise
	          for(int x = 0; x < width; x++){
	             for(int y = 0; y < height; y++){
	                totalNoise[x][y] += (float) noise(x * layerFrequency,y * layerFrequency) * layerWeight;
	             }
	          }
	          
	          //Increase variables with each incrementing octave
	           layerFrequency *= 2;
	           weightSum += layerWeight;
	           layerWeight *= roughness;
	           
	       }
	       return totalNoise;
	   }*/
	
	protected void loadLevel(String path) {
		minimap_enabled  = true;
		SpawnList.clear();
		
		SpawnTime_MOD = 150;
		
		try {
			BufferedImage image = ImageIO.read(MainLevel.class.getResource(path));
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
			BufferedImage overlayImage = ImageIO.read(MainLevel.class.getResource("/overlays" + path));  			int overlayW = width = overlayImage.getWidth();
			int overlayH = height = overlayImage.getHeight();
			overlayTiles = new int[overlayW * overlayH];
			torchTiles = new int[overlayW * overlayH];
			overlayImage.getRGB(0,0,overlayW,overlayH,overlayTiles,0,overlayW);
			overlayImage.getRGB(0,0, overlayW, overlayH, torchTiles, 0, overlayW);
			System.out.println("Loaded Overlays");
		} catch (IOException e) {
			System.out.println("Failed To Load Torches");
		}
		
		
		
		
		
      /*  add(new Shooting(277, 478));
		//System.out.println("X: " + entities.get(x).getX() / 16 + " Y: " + entities.get(x).getY() / 16);
		add(new AStarTracker(276,458));
		//add(new AStarHostile(285, 456));
		add(new Zombie(285, 456));

		add(new UndeadCaster(282, 461));
		add(new UndeadCaster(278, 465));
		add(new UndeadCaster(269, 459));
		//add(new Tracker(270, 456));
		add(new UndeadCaster(270, 456));

		add(new Shooting(276, 458));
		for (int i = 0; i < 1; i++) {
		add(new Dumby(274,461));
		add(new Occulus(402, 130));
		
		}
		for (int i = 0; i < 1000; i++) {
			add(new AStarTracker(randomX * randomX, randomY * randomY));
			System.out.println("X: " + entities.get(i).getX() + " Y: " + entities.get(i).getY());

		}*/
		
		Game.currentLevelId = Maps.mainId;

		add(new Guard(615, 230, new TileCoord(601, 230), new TileCoord(636, 230)));
		add(new Guard(652, 230, new TileCoord(649, 230), new TileCoord(685, 230)));
		add(new Guard(615, 228, new TileCoord(601, 228), new TileCoord(636, 228)));
		add(new Guard(652, 228, new TileCoord(649, 228), new TileCoord(685, 228)));
		
		add(new Guard(665, 222, false));
		add(new Guard(675, 221, false));

		//add(new Teleporter(270, 459));
		
		/*add(new Carraige(840, 170, "Ghelln"));
		add(new Carraige(241, 867, "Fenir"));
		add(new Carraige(640, 270, "Astellon"));
		add(new Shop(667, 218, 4));

		add(new Interactable_Bed(672, 213, 0));*/
		add(new Location_Shrine(672, 227, new TileCoord(673, 228)));
		
		for(int i = 0; i < 80; i++){
			int sx = (random.nextInt(30) + 12) * 16;
			int sy = (random.nextInt(50) + 15) * 16;
			if (returnTileXY(sx, sy).toString().equals(tile.Grass.toString()) || returnTileXY(sx, sy).toString().equals(tile.Swamp.toString()) || returnTileXY(sx, sy).toString().equals(tile.Sand.toString()) || returnTileXY(sx, sy).toString().equals(tile.Snow.toString())) {
			if (!returnTileXY(sx, sy).solid()) {
		         add(new Teleporter(sx, sy));
		         }
			}
      }
		
		add(new Location_Shrine(616, 442, new TileCoord(616, 443)));
        add(new Location_Shrine(779, 395, new TileCoord(779, 396)));
        add(new Location_Shrine(835, 132, new TileCoord(835, 133)));
        add(new Location_Shrine(613, 683, new TileCoord(613, 684)));
        add(new Location_Shrine(241, 833, new TileCoord(241, 834)));
		
	
		add(new Horse(230, 863));
		add(new Horse(226, 865));

		add(new Horse(633, 264));
		add(new Horse(637, 262));
		
		add(new Horse(619, 459));
		add(new Horse(831, 166));
		add(new Horse(827, 164));

		add(new Horse(791, 426));
		add(new Horse(787, 427));

		add(new Horse(616, 716));
		add(new Horse(620, 719));

	      } 
	
	protected void generateLevel() {
		for (int y = 0; y < 64; y++) {
			for (int x = 0; x < 64; x++) {
				getTile(x, y);
				}
			}
		}
	
	public void checkExits(Player player, Level level, int x, int y, boolean e) {
		refresh();
		player.setPosition(52, 78, Maps.spawnHavenId, true);
	}

}


