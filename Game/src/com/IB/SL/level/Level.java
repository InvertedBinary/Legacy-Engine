package com.IB.SL.level;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import com.IB.SL.Boot;
import com.IB.SL.Game;
import com.IB.SL.VARS;
import com.IB.SL.entity.Entity;
import com.IB.SL.entity.Entity.HOSTILITY;
import com.IB.SL.entity.mob.Mob;
import com.IB.SL.entity.mob.Mob.DIRECTION;
import com.IB.SL.entity.mob.Player;
import com.IB.SL.entity.mob.PlayerMP;
import com.IB.SL.entity.mob.XML_Mob;
import com.IB.SL.entity.particle.DamageIndicator;
import com.IB.SL.entity.particle.Particle;
import com.IB.SL.entity.projectile.Projectile;
import com.IB.SL.entity.spawner.Spawner;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.SpriteSheet;
import com.IB.SL.graphics.Weather.Rain;
import com.IB.SL.level.tile.Tile;
import com.IB.SL.level.tile.SL2.XML_Tile;
import com.IB.SL.level.tile.tiles.Water;
import com.IB.SL.util.SaveGame;
import com.IB.SL.util.Vector2i;


public class Level implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2980023760275759466L;
	public int width, height;
	protected int[] tilesInt;
	public int[] tiles;
	public int[] overlayTiles;
	public int[] torchTiles;
	transient public Tile tile;
	public static int nighttime = 0, daytime = 0;
	public List<Entity> entities = new ArrayList<Entity>();
	public List<Projectile> Projectiles = new ArrayList<Projectile>();
	public List<Particle> particles = new ArrayList<Particle>();
	public List<PlayerMP> players = new ArrayList<PlayerMP>();

	public List<Entity> entitiesList = new ArrayList<Entity>();
	public List<Spawner> Spawner = new ArrayList<Spawner>();

	//public List<AnimatedTile> tiles_anim = new ArrayList<AnimatedTile>();
	double addX, addY;
	ArrayList<Integer> xRad = new ArrayList<Integer>();
	double tx, ty;
	double otx, oty;
	
	
	public static int bitBrickHex = 0xffFF0000; 
	public static int bitMetalHex = 0xffCCCCCC;
	public static int BluefogHex = 0xff000059;
	public static int BookshelfBottomHex = 0xffD9F2C6;
	public static int BookshelfTopHex = 0xffCEEB5C;
	public static int BrickCeilingHex = 0xffF20000;
	public static int BrickWallHex = 0xffE50000;
	public static int CaveCeilingHex = 0xff666666;
	public static int CaveWallHex = 0xff595959;
	public static int CobbleStoneHex = 0xffF2F2F2;
	public static int CobblestoneCeilingHex = 0xff7F7F7F;
	public static int CobbleStoneWallHex = 0xff727272;
	public static int CrackedBrickHex = 0xffE5E5E5;
	public static int DarkStoneHex = 0xff0C0C0C;
	public static int DeepLavaHex = 0xffCC7E0A;
	public static int DeepWaterHex = 0xff0000BF;
	public static int DirtHex = 0xff725F00;
	public static int DirtCeilingHex = 0xff665300;
	public static int DirtWallHex = 0xff594800;
	public static int DresserBottomHex = 0xffB7CCA7;
	public static int DresserTopHex = 0xffC2D8B1;
	public static int GrassHex = 0xff00CC00;
	public static int HellBrickHex = 0xffBF0000;
	public static int HellBrickCeilingHex = 0xff7F0000;
	public static int HellbrickWallHex = 0xff720000;
	public static int HellCaveCeilingHex = 0xff660000;
	public static int HellCaveWallHex = 0xff590000;
	public static int HellsandHex = 0xffCC0000;
	public static int HellSandCeilingHex = 0xffB20000;
	public static int HellSandWallHex = 0xffA50000;
	public static int HellstoneHex = 0xffD80000;
	public static int IceHex = 0xffA3A3FF;
	public static int IceBrickHex = 0xffE6E6F2;
	public static int IceBrickCeilingHex = 0xffDADAE5;
	public static int IceBrickWallHex = 0xffCDCDD8;
	public static int IceCaveCeilingHex = 0xffC1C1CC;
	public static int IceCaveWallHex = 0xffB8B8C1;
	public static int IceSandHex = 0xffB2B2FF;
	public static int IceSandCeilingHex = 0xffA9A9F2;
	public static int IceSandWallHex = 0xffA0A0E5;
	public static int LavaHex = 0xffFF9A0C;
	public static int MetalHex = 0xffBFBFBF;
	public static int MetalCeilingHex = 0xffB2B2B2;
	public static int MetalWallHex = 0xffA5A5A5;
	public static int MossCeilingHex = 0xffA5D2A5;
	public static int MossWallHex = 0xff97BF97;
	public static int ObsidianCeilingHex = 0xff590059;
	public static int ObsidianWallHex = 0xff4C004C;
	public static int PathDirtHex = 0xff99A53D;
	public static int SandHex = 0xffFFFCB2;
	public static int SandBrickHex = 0xffD8D697;
	public static int SandBrickCeilingHex = 0xffCCC98E;
	public static int SandBrickWallHex = 0xffBFBC85;
	public static int SandCeilingHex = 0xffF2EFA9;
	public static int SandWallHex = 0xffE5E3A0;
	public static int SnowHex = 0xffFFFFFF;
	public static int StoneBrickHex = 0xff4C4C4C;
	public static int StoneBrickCeilingHex = 0xff3F3F3F;
	public static int StoneBrickWallHex = 0xff333333;
	public static int SwampHex = 0xff956533;
	public static int VoidTileHex = 0xffFF00FF;
	public static int WaterHex = 0xff0000CC;
	public static int WoodHex = 0xffFFBC75;
	public static int WoodCeilingHex = 0xffF2B36F;
	public static int WoodWallHex = 0xffE5A969;
	public static int SwirlyHex = 0xffFF70E9;
	public static int DoorHex = 0xff000000;
	public static int CounterHex = 0xff643100;
	public static int CounterBHex = 0xff542800;

	/**
	 * Overworld
	 */

public static int Cactus = 0xff00F700;
public static int FlowerCactus = 0xff00ED00;
public static int ColoredFlowers = 0xffFFDB00;
public static int YellowFlowers = 0xffFFFF00;
public static int AnvilHex = 0xff494949;
public static int StoveHex = 0xff303030;

	/**
	 * Overworld & Dungeon
	 */

public static int Torch = 0xFFFF6A00;
public static int Pebble = 0xffD3D3D3;
public static int BlueMushroom = 0xff1111FF;
public static int BlueMushroomDirt = 0xff1010ED;
public static int RedMushroom = 0xffFF1111;
public static int RedMushroomDirt = 0xffFF1111;
public static int GreenMushroom = 0xff11FF11;
public static int GreenMushroomDirt = 0xff11FF11;
public static int DirtPatch = 0xffB8DD00;
public static int RedmushroomDirt = 0xffED1010;

	/**
	 * Dungeon
	 */

public static int Bone = 0xffFCFCFC;
public static int CrossedBones = 0xffF9F9F9;
public static int Skull = 0xffF7F7F7;
public static int DarkCastle = 0xff444444;
public static int Cave = 0xff545454;
public static int Portal = 0xff7421A1;
public static int PathVertical = 0xff333A00;
public static int PathHorizontal = 0xff3B4400;
public static int PathCross = 0xff485400;
public static int PathCornerTL = 0xff515E00;
public static int PathCornerTR = 0xff5D6D00;
public static int PathCornerBL = 0xff657700;
public static int PathCornerBR = 0xff728700;
public static int PathEndLeft = 0xff7B9100;
public static int PathEndRight = 0xff88A000;
public static int PathEndTop = 0xff8EAA00;
public static int PathEndBottom = 0xff9BBA00;
public static int BrokenSword = 0xffA3C400;

	/**
	 * Village
	 */

public static int RedBed = 0xffFF0C0C;
public static int BlueBed = 0xff0C0CFF;
public static int GreenBed = 0xff0CFF0C;
public static int OrangeBed = 0xffFFDB0C;
public static int TopChair = 0xff8ADD0D;
public static int BottomChair = 0xff87D30C;
public static int LeftChair = 0xff80C40B;
public static int RightChair = 0xff79BA0B;
public static int Castle = 0xffAAAAAA;
public static int Village = 0xff69A009;
public static int Table = 0xff6FAA0A;
	
transient Tile setTiles;
transient private Comparator<Node> nodeSorter = new Comparator<Node>() {
		public int compare(Node n0, Node n1) {
			if (n1.fCost < n0.fCost)
				return +1;
			if (n1.fCost > n0.fCost)
				return -1;
			return 0;

		}
	};

//	public static String currentLevel = spawn.toString();
	
	public static int brightness = 50;
	public static boolean minimap_enabled = false;
	public static boolean minimap_collapsed = false;
	
	public int time = 0;
    public boolean day = false, night = false;
	        
	public Level(int width, int height) {
		this.width = width;
		this.height = height;
		tilesInt = new int[width * height];
		generateLevel();

	}

	public Level(String path) {
		loadLevel(path);
		generateLevel();
		// add(new ParticleSpawner(29 * TileCoord.TILE_SIZE, 31*TileCoord.TILE_SIZE, 13, 35, this));
	}
	
	public String Level_Dir;
	public Level(String path, boolean foo) { 
		this.Level_Dir = path;
		String XML = path + "/tags.xml";
		LoadXML(XML);
		loadLevel(path);
	}
	
	public void LoadXML(String XML) {
		
	}
	
	public void loadLua() {
		
	}
	
	/*private String getName() {
		return Level.currentLevel;
	}
	private HashMap<String, Level> levelMap = new HashMap<String, Level>();
	private Level currentLevel1;

	public void addLevel(Level level) {
	     levelMap.put(level.getName(), level);
	}


	public Level getLevel(String name) {
	     return levelMap.get(name);
	}

	public Level getCurrentLevel() { return currentLevel1; }

	public void gotoLevel(Player player, String levelName) {
	     Level level = getLevel(levelName);
	     currentLevel1 = level;
	     player.setLevel(level);

	     // do other initialization stuff to the level here
	}*/
	
	
	
	public void saveMobs(Entity[] es) {
		//System.out.println("SAVING TO: " + SaveGame.mobsFileName + Game.currentLevelId);
		SaveGame.save(es, SaveGame.mobsFileName + Game.currentLevelId);
	}
	
	public void loadMobs(int levelID) {
		Entity[] temp = SaveGame.loadEntities(levelID);
		if (temp != null) {
			
		System.out.println("ATTEMPTING TO LOAD: " + levelID);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>> | temp.length: " + temp.length);
		if (temp.length == 0) {
			return;
		}

		for(int i = 0; i < entities.size(); i++) {
			entities.get(i).remove();
		}
		
		for(int i = 0; i < temp.length; i++) {
			try {

				if (temp[i] != null) {
					Entity e = temp[i];
						System.out.println("LOADING Entity >>>>>>>>>>>>>>>>>>>>>>>>>> " + (e.getClass()));
					//Entity.getClass().getConstructor(new Class[] {Integer.TYPE}).newInstance(item.slot);
					if (e instanceof XML_Mob) {
						e = temp[i].getClass().getConstructor(new Class[] {Double.TYPE, Double.TYPE, String.class}).newInstance((int)(temp[i].x() / TileCoord.TILE_SIZE), (int)(temp[i].y() / TileCoord.TILE_SIZE), (((XML_Mob)temp[i]).XML_String));
					} else {						
					e = temp[i].getClass().getConstructor(new Class[] {Integer.TYPE, Integer.TYPE}).newInstance((int)(temp[i].x() / TileCoord.TILE_SIZE), (int)(temp[i].y() / TileCoord.TILE_SIZE));
					}
					e.onLoad(temp[i]);
					e.mobhealth = temp[i].mobhealth;
				Boot.get().getLevel().add(e);
				e.mobhealth = temp[i].mobhealth;

				} else {
					continue;
				}
				if (Arrays.asList(temp).isEmpty()) {
					return;
				}
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e1) {
				e1.printStackTrace();
			}
		}
	}
	}
	
	public void loadMobs() {
		Entity[] temp = SaveGame.loadEntities();
		if (temp != null) {
			
		System.out.println(">>>>>>>>>>>>>>>>>>>>>> | temp.length: " + temp.length);
		if (temp.length == 0) {
			return;
		}

		for(int i = 0; i < entities.size(); i++) {
			entities.get(i).remove();
		}
		
		for(int i = 0; i < temp.length; i++) {
			try {

				if (temp[i] != null) {
					Entity e = temp[i];
						System.out.println("LOADING Entity >>>>>>>>>>>>>>>>>>>>>>>>>> " + (e.getClass()));
					//Entity.getClass().getConstructor(new Class[] {Integer.TYPE}).newInstance(item.slot);
					e = temp[i].getClass().getConstructor(new Class[] {Integer.TYPE, Integer.TYPE}).newInstance((int)(temp[i].x() / TileCoord.TILE_SIZE), (int)(temp[i].y() / TileCoord.TILE_SIZE));
				//Entity e = temp[i].getClass().newInstance();
				/*	Field[] fields = temp[i].getClass().getDeclaredFields();
					Field[] fields2 = e.getClass().getDeclaredFields();

					for(int g = 0; g < fields.length; g++) {
					   Class t = fields[i].getType();
					   Object v = fields[i].get(i);
					   System.out.println("[]====[] || " + fields[i].getName());
					   for (int j = 0; j < fields2.length; j++) {
						   if (v != null) {
							  fields2[i].set(fields2[i], v);
						   }
					   }
					   
					}*/
					e.onLoad(temp[i]);
					e.mobhealth = temp[i].mobhealth;
				Boot.get().getLevel().add(e);
				e.mobhealth = temp[i].mobhealth;
				//e.setX(temp[i].getX());
				//e.setY(temp[i].getY());
				} else {
					continue;
				}
				if (Arrays.asList(temp).isEmpty()) {
					continue;
				}
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e1) {
				e1.printStackTrace();
			}
		}
		}
	/*	for (int i = 0; i < entities.size(); i++) {
			entities.get(i).onLoad(temp[i]);
		}*/
	}
	
	protected void generateLevel() {

	}
	
	protected void loadLevel(String path) {
		/*for (int i = 0; i < entities.size(); i++) {
		entities.get(i).remove();
		}*/
	}
	
	public void updateTest() {
			//loadLevel("/levels/Swamp.png");
	}
	
	 public void time1() {
		 
	      if (brightness <= -50) {
	         night = true;
	         day = false;
	      }
	      
	      if (brightness >= 50) {
	         day = true;
	         night = false;
	      }
	      
	      if (night) {
	    	  if (nighttime > 2500) {
	    		  daytime = 0;
	    		  brightness++;
	    	  }
	         return;
	      }
	      
	      if (day) {
	    	  if (daytime > 2600) {
	    	  if (brightness > -50) {
	    		  nighttime = 0;
	         brightness--;
	    		  }
	    	  }
	         return;
	      }
	      
	      brightness++;
	   }
	 
	 public void checkTile() {
		 if (players.size() > 0) {
			 for (int i = 0; i < players.size(); i++) {
				 if (returnTile().equalsIgnoreCase("Water")) {
					players.get(i).swimming = true;
				} else {
					players.get(i).swimming = false;
				}
			 }
		 }
	 }
	 
	 public String returnTile() {
		 Tile tile;
		 String tileString = "";
		 try {
		tile = getTile((int)getPlayerAt(0).x() >> VARS.TILE_BIT_SHIFT, (int)getPlayerAt(0).y() >> VARS.TILE_BIT_SHIFT);
		tileString = tile.toString();
		tileString = tileString.replace("com.IB.SL.level.tile.", "");
		tileString = tileString.substring(0,tileString.indexOf("@"));
		 } catch (Exception e) {
			 
		 }
		 return tileString;
	 }
	 
	 public String returnOverlayTile() {	 
		 Tile tile;
		 String tileString = "";
		tile = getOverlayTile((int)getPlayerAt(0).x() >> VARS.TILE_BIT_SHIFT, (int)getPlayerAt(0).y() >> VARS.TILE_BIT_SHIFT);
		if(tile != null) {
		tileString= tile.toString();
		tileString = tileString.replace("com.IB.SL.level.tile.", "");
		tileString = tileString.substring(0,tileString.indexOf("@")); 
		}  else {
			tileString = "[None]";
		}
	 return tileString;
	 
	 }
	 
	 public String returnTileFromValue(int xy, int yx) {
		 Tile tile;
			tile = getTile(xy, yx);
			String tileString = tile.toString();
			tileString = tileString.replace("com.IB.SL.level.tile.", "");
			tileString = tileString.substring(0,tileString.indexOf("@"));
			return tileString;
		 }
	 
	 public Tile returnTileXY(int xy, int yx) {	 
		 Tile tile;
			tile = getTile(xy, yx);
			return tile;
		 }
	 
	 public String returnTile(List<Entity> entities) {	 
		 Tile tile;
			tile = getTile((int)entities.get(0).x() >> VARS.TILE_BIT_SHIFT, (int)entities.get(0).y() >> VARS.TILE_BIT_SHIFT);
			String tileString = tile.toString();
			tileString = tileString.replace("com.IB.SL.level.tile.", "");
			tileString = tileString.substring(0,tileString.indexOf("@"));
			return tileString;
		 }
		 
	 
	 
	 transient private Vector2i start;
	 transient private Vector2i goal;
	
	 transient private Comparator<Entity> ySort = new Comparator<Entity>() {
		    public int compare(Entity e1, Entity e2) {
		      if (e1.y() > e2.y()) return 1; // Shift Up
		      if (e1.y() < e2.y()) return -1; // Shift Down
		      return 0;
		    }
		  };
		  
		  
			public boolean isRaining = false;
			transient public Rain rain = new Rain();

			
	public int myRandom(int min, int max) {
		Random rand = new Random();
		return rand.nextInt((max - min) + 1) + min;
	}
			
	public ArrayList<Mob> SpawnList = new ArrayList<Mob>();
	int  ji = 0;
	public int SpawnTime = 0;
	public int SpawnTime_MOD = 120;
	public void update() {
		
	if (!Boot.launch_args.containsKey("-nospawns")) {
		if (SpawnList.size() > 0) {
		if (SpawnTime_MOD != -1) {				
			SpawnTime++;
		if (SpawnTime % SpawnTime_MOD == 0) {
			int r1 = myRandom(0, SpawnList.size() - 1);
			int r2 = myRandom(0, 10);

			Mob m = SpawnList.get(r1);
			if (m.rarity != -1) {
				if (r2 >= m.rarity) {
					try {
						int sx, sy, lx, rx, ty, by;
						do {
							
						lx = (int)(Boot.get().getPlayer().x() / TileCoord.TILE_SIZE) - (int)(((Boot.get().getScreen().width / TileCoord.TILE_SIZE) / 2) + 8);
						rx = (int)(Boot.get().getPlayer().x() / TileCoord.TILE_SIZE) + (int)(((Boot.get().getScreen().width / TileCoord.TILE_SIZE) / 2) + 8);
						
						ty = (int)(Boot.get().getPlayer().y() / TileCoord.TILE_SIZE) - (int)(((Boot.get().getScreen().height / TileCoord.TILE_SIZE) / 2) + 5);
						by = (int)(Boot.get().getPlayer().y() / TileCoord.TILE_SIZE) + (int)(((Boot.get().getScreen().height / TileCoord.TILE_SIZE) / 2) + 5);
						sx = myRandom(lx - 4, rx + 4);
						sy = myRandom(ty - 4, by + 4);
						
						} while ((sx < lx && sx > rx) || (sy < by && sy > ty));
						Mob  mob = m.getClass().getConstructor(new Class[] {Integer.TYPE, Integer.TYPE}).newInstance(sx, sy);
						if(!returnTileXY(sx, sy).solid()) {
							System.out.println("SpawnList-Adding: " + mob);
							add(mob);							
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			SpawnTime = 0;
			return;
		}
		}
		}
	}
			
		
		
	//	System.out.println(minVal);

		
	/*	for (; ji < 10000; ji++) {

			int sx = myRandom(lx, rx);
			int sy = myRandom(ty, by);

			System.out.println("X: " + sx + " Y: " + sy);

			if (!returnTileXY(tile, sx, sy).solid()) {
				add(new WallParticleSpawner((int) (sx * TileCoord.TILE_SIZE), (int) (sy * TileCoord.TILE_SIZE), 20000, 1, this));
			}
		}*/
		
		
		if (Boot.get().getLevel().isRaining) {
			rain.update(false);
		}
		//TODO: Add mob spawning just outside of player view
	/*	for (int i = 0; i < players.size(); i++) {
			 addX = players.get(i).x / TileCoord.TILE_SIZE;
					 addY = 54;
					 
			 tx = (players.get(i).x / TileCoord.TILE_SIZE)- 12;
					 ty = (players.get(i).y / TileCoord.TILE_SIZE) - 8;
			 otx = players.get(i).x + 12;
					 oty = (players.get(i).y / TileCoord.TILE_SIZE) + 8;
		}
		
		
		for (int j = (int)tx; j < (int)otx; j++) {
		xRad.add(j);
	}
		
		for(int i = 0; i < xRad.size();i++) {
		if (i <= 24) {
			if (xRad.get(i) != (int)addX) {
				System.out.println(":VALID SPAWN LOCATION, X: " + addX + " , I: " + i);
			} else {
				System.out.println("INVALID SPAWN LOCATION, X: " + addX + " , I: " + i);
			//	break;
			}
		}
	}
		*/
		Collections.sort(entitiesList, ySort);

		updateTest();
		
		/*for (int i = 0; i < tiles_anim.size(); i++) {
			tiles_anim.get(i).update();
		}*/

		
		returnTile();
		//System.out.println(returnTile(tile));
		checkTile();
		
		
		
		/*if (this.Overworld) {
		if (night) {
			nighttime++;
		}
		if (day) {
			daytime++;
		}
		//System.out.println("[Level: 135] Brightness: " + brightness + " Day: " + day);
	      time++;
	      
	      if(time % 26 == 0) {  //ALTER 10 FOR HOW LONG IT TAKES DAY/NIGHT
	          time1();
	      }
	      
	      if(time > 1000000) {      //prevent it from going too large
	         time = 0;
	      }
		} else {
			day = true;
			night = false;
			time = 0;
			brightness = 50;
		}*/
		if (false) {	
		for (int i = 0; i < entities.size(); i++) {
			if (this.getPlayersBool(entities.get(i), 350)) {  //TODO: Make a list of entities for entities that need to update even when the player is not within 350px
					entities.get(i).update();
				}
			}
		} else {
			for (int i = 0; i < entities.size(); i++) {
						entities.get(i).update();
				}
		}
		
		for (int i = 0; i < Projectiles.size(); i++) {
			Projectiles.get(i).update();
		}
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).update();
		}
		for (int i = 0; i < players.size(); i++) {
				players.get(i).update();				
		}

		Water.update();
		
		}

	public List<Projectile> getProjectiles() {
		return Projectiles;
	}

	public boolean tileCollision(int x, int y, int size, int xOffset, int yOffset) {
		boolean solidtwo = false;
		for (int c = 0; c < 4; c++) {
			int xt = (x - c % 2 * size - xOffset) >> VARS.TILE_BIT_SHIFT;
			int yt = (y - c / 2 * size - yOffset) >> VARS.TILE_BIT_SHIFT;
			if (getTile(xt, yt).solidtwo())
				solidtwo = true;
		}
		return solidtwo;

	}
	
	private void renderMiniMap(Screen screen) {
		if (minimap_enabled == true) {
			int size = 75;
			int x = Game.width - size - 5;
			int y = 5;
			
			if (Boot.get().getPlayer().input.map) {
				size = 200;
				 x = 50;
				 y = 0;
			}
				
			if (!minimap_collapsed) {
			screen.renderMiniMap(x, y, size);
			}
		}
	}
	
	int bgcolor = 0xffBED0CA;
	SpriteSheet pl_bg = new SpriteSheet("/XML/Levels/a10/assets/paralax/bg.png", 640, 360);
	SpriteSheet pl_first = new SpriteSheet("/XML/Levels/a10/assets/paralax/first_layer.png", 149, 35);
	SpriteSheet pl_second = new SpriteSheet("/XML/Levels/a10/assets/paralax/second_layer.png", 234, 92);
	SpriteSheet pl_third = new SpriteSheet("/XML/Levels/a10/assets/paralax/third_layer.png", 352, 297);

	public void render(int xScroll, int yScroll, Screen screen) {
		screen.setOffset(xScroll, yScroll);
		int x0 = xScroll >> VARS.TILE_BIT_SHIFT;
		int x1 = (xScroll + screen.width + TileCoord.TILE_SIZE) >> VARS.TILE_BIT_SHIFT;
		int y0 = yScroll >> VARS.TILE_BIT_SHIFT;
		int y1 = (yScroll + screen.height + TileCoord.TILE_SIZE) >> VARS.TILE_BIT_SHIFT;
		
		try {
			
		//screen.renderSheet(0, 0, pl_bg, false);
		screen.renderParallax(bgcolor, pl_first, pl_second, pl_third, 37);
		
		//screen.renderSheet(0, 37, pl_first, false);
		//screen.renderSheet(0, 36, pl_second, false);
		//screen.renderSheet(0, 63, pl_third, false);
		} catch (Exception e) {
			
		}

		for (int y = y0; y < y1; y++) {
					  for (int x =x0; x < x1; x++) {
						// renderMiniMap(screen, 32, 32, 40, 40);
					    Tile tile = getTile(x,y);
					    tile.render(x,y,screen);
					    Tile overlayTile = getOverlayTile(x,y);
					    if (overlayTile != null) overlayTile.render(x,y,screen);
					    Tile torchTiles = getTorchTile(x,y);
					    if (torchTiles != null) torchTiles.render(x,y,screen);
					    if (setTiles != null) setTiles.render(x, y, screen);

					  }
					}

		
		
		/*for (int i = 0; i < HealthPot.size(); i++) {
			HealthPot.get(i).render(screen);
				}
		for (int i = 0; i < ManaPot.size(); i++) {
			ManaPot.get(i).render(screen);
				}*/	
			/*screen.blendTiles(283 * TileCoord.TILE_SIZE, 477 * TileCoord.TILE_SIZE, tile.CobbleStone, tile.Swamp);
			screen.blendTiles(282 * TileCoord.TILE_SIZE, 477 * TileCoord.TILE_SIZE, tile.CobbleStone, tile.Swamp);*/

				for(int i = 0; i < entities.size(); i++){
					if (!entities.get(i).ySort)
			         entities.get(i).render(screen);
			      }
				
				for(int i = 0; i < entitiesList.size(); i++){
					//		if (this.getPlayersBool(entitiesList.get(i), 350)) {  //TODO: Make a list of entities for entities that need to update even when the player is not within 350px
					if (entitiesList.get(i).ySort)
			         entitiesList.get(i).render(screen);
			   //   }
				}
				
				for(int i = 0; i < entitiesList.size(); i++){
					entitiesList.get(i).renderGUI(screen);
				}
				
				for(int i = 0; i < players.size(); i++) {
					players.get(i).renderGUI(screen);
				}
				
				 if (Boot.get().getLevel().isRaining) {
					 rain.render(screen);
				 }
		//screen.renderSprite(644 * TileCoord.TILE_SIZE, 206 * TileCoord.TILE_SIZE, Sprite.robobob, true);
		 renderMiniMap(screen);

		//screen.renderLight(277 * TileCoord.TILE_SIZE, 477 * TileCoord.TILE_SIZE, 40, 20, 20, 20, "test");

			/*	screen.renderSprite(4, 4, Sprite.abilitybox, false);
				screen.renderSprite(24, 4, Sprite.abilitybox, false);
				screen.renderSprite(44, 4, Sprite.abilitybox, false);
				screen.renderSprite(60, 4, Sprite.abilitybox, false);
				screen.renderSprite(76, 4, Sprite.abilitybox, false);*/

			//	public void renderMenu(Screen screen) {
				/*if (Player.inventoryOn) {
					screen.renderSheet(85, 40, SpriteSheet.inventory1, false);
					if (Game.healthPotionsRemaining > 0) {
					screen.renderSprite(131, 62, Sprite.HealthPotion, false);
					}
				}	*/

							
		
		//renderMiniMap(screen, 32, 32, 5, 5);

		//screen.renderLight(280 * TileCoord.TILE_SIZE - radius, 490 * TileCoord.TILE_SIZE - radius, radius);
		//screen.renderLight(277 * TileCoord.TILE_SIZE - radius, 477 * TileCoord.TILE_SIZE - radius, radius, 20, 20, 20);

	
		remove();
	}
	private void remove() {
		for(int i = 0; i < entitiesList.size(); i++){
	         if(entitiesList.get(i).isRemoved()) entitiesList.remove(i);
	      }
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i).isRemoved())
				entities.remove(i);
		}
		for (int i = 0; i < Projectiles.size(); i++) {
			if (Projectiles.get(i).isRemoved())
				Projectiles.remove(i);
		}
		for (int i = 0; i < particles.size(); i++) {
			if (particles.get(i).isRemoved())
				particles.remove(i);
		}
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).isRemoved())
				players.remove(i);
		}
		for (int i = 0; i < Spawner.size(); i++) {
			if (Spawner.get(i).isRemoved())
			Spawner.remove(i);
		}
	}

	public void add(Entity e) {
		e.init(this);
		entitiesList.add(e);
		if (e instanceof Particle) {
			particles.add((Particle) e);
		} else if (e instanceof Projectile) {
			Projectiles.add((Projectile) e);
		} else if (e instanceof Player) {
			players.add((PlayerMP) e);
		} else if (e instanceof Spawner) {
			Spawner.add((Spawner) e);
		} else {
			entities.add(e);
			if (e instanceof Mob) {
				try {
				e.maxhealth *= (10 / ( 1 + Math.pow(Math.E, -0.1 * (Boot.get().getPlayer().Lvl - 40)))) + 1;
				e.mobhealth = e.maxhealth;
				} catch (NullPointerException err) {
					
				}
			}
		}
	}
		//TODO: SEND PACKET TO ADD TO ALL CLIENTS
		

	public List<PlayerMP> getPlayers() {
		return players;

	}

	public Player getPlayerAt(int index) {
		return players.get(index);
	}
	
	public Entity getEntityAt(int index) {
		return entities.get(index);
	}

	public Player getClientPlayer() {
		return players.get(0);
	}
	
	
	public List<Entity> AOEFull(Screen screen, int x, int y, int rad, boolean render, int color) {
		List<Entity> result;
		if (render) {
			drawAOE(screen, x, y, rad, color);
		}

		x >>= 4;
		y >>= 4;

		result = getEntitiesListFixed(x, y, rad);
		return result;
	}
	
	
	public void drawAOE(Screen screen, int x, int y, int rad, int color) {
		x >>= 4;
		y >>= 4;
		
		screen.drawCir(x, y, rad, color, true);

	}
	
	public List<Vector2i> BresenhamLine(int x1, int y1, int x2, int y2){
		   List<Vector2i> result = new ArrayList<Vector2i>();
		   int dy = y2 - y1;
		   int dx = x2 - x1;
		   int xs, ys;
		   
		   if(dy < 0) {dy = -dy; ys = -1;} else { ys = 1;}
		   if(dx < 0) {dx = -dx; xs = -1;} else { xs = 1;}
		   dy <<= 1;
		   dx <<= 1;
		   
		   result.add(new Vector2i(x1, y1));
		   if(dx > dy){
		      int fraction = dy - (dx >> 1);
		      while(x1 != x2){
		         if(fraction >= 0){
		            y1 += ys;
		            fraction -= dx;
		         }
		         x1 += xs;
		         fraction += dy;
		         result.add(new Vector2i(x1, y1));
		      }
		   }else{
		      int fraction = dx - (dy >> 1);
		      while(y1 != y2){
		         if(fraction >= 0){
		            x1 += xs;
		            fraction -= dy;
		         }
		         y1 += ys;
		         fraction += dx;
		         result.add(new Vector2i(x1, y1));
		      }
		   }
		   return result;
		}
	
	public RayCast RayCast(Vector2i pos, double angle, float rayLength){
		   RayCast result = new RayCast();
		   result.setCollided(false);
		   if(rayLength <= 0){
		      result.setCollided(this.getTile(pos.getX()>>4, pos.getY()>>4).solid());
		      result.setPosition(pos);
		      return result;
		   }
		   double adjacent = pos.getX()+rayLength*Math.cos(angle);
		   double opposite = pos.getY()+rayLength*Math.sin(angle);
		   List<Vector2i> rayLine = BresenhamLine(pos.getX(), pos.getY(), (int)adjacent, (int)opposite);
		   if(!rayLine.isEmpty()){
		      for(int rayVectorIndex = 0;rayVectorIndex < rayLine.size();rayVectorIndex++){
		         Vector2i rayVector = rayLine.get(rayVectorIndex);
		         result.rayVector = rayVector;
		         if(this.getTile(rayVector.getX()>>4, rayVector.getY()>>4).solid()){
		            result.setPosition(rayVector);
		            result.setCollided(true);
		            break;
		         }
		      }
		   }
		   return result;
		}
	
	public List<Node> findPath(Vector2i start, Vector2i goal) {
		this.start = start;
		this.goal = goal;
		List<Node> openList = new ArrayList<Node>();
		List<Node> closedList = new ArrayList<Node>();
		Node current = new Node(start, null, 0, getDistance(start, goal));
		openList.add(current);
		while (openList.size() > 0) {
			current = openList.get(0);
			Collections.sort(openList, nodeSorter);
			if (current.tile.equals(goal)) {
				List<Node> path = new ArrayList<Node>();
				while (current.parent != null) {
					path.add(current);
					current = current.parent;
				}
				openList.clear();
				closedList.clear();
				return path;
			}
			openList.remove(current);
			closedList.add(current);
			for (int i = 0; i < 9; i++) {
				if (i == 4)
					continue;
				int x = current.tile.getX();
				int y = current.tile.getY();
				int xi = (i % 3) - 1;
				int yi = (i / 3) - 1;
				Tile at = getTile(x + xi, y + yi);
				if (at == null)
					continue;
				if (at.solid())
					continue;
				Vector2i a = new Vector2i(x + xi, y + yi);
				double gCost = current.gCost + (getDistance(current.tile, a)/* == 1 ? 1 : 1*/);
				double hCost = getDistance(a, goal);
				Node node = new Node(a, current, gCost, hCost);
				if (vecInList(closedList, a) && gCost >= node.gCost)
					continue;
				if (!vecInList(openList, a) || gCost < node.gCost)
					openList.add(node);

			}
		}
		closedList.clear();
		return null;
	}

	private boolean vecInList(List<Node> list, Vector2i vector) {
		for (Node n : list) {
			if (n.tile.equals(vector))
				return true;
		}
		return false;
	}

	private double getDistance(Vector2i tile, Vector2i goal) {
		double dx = tile.getX() - goal.getX();
		double dy = tile.getY() - goal.getY();
		
		return Math.sqrt(dx * dx + dy * dy);
	}

	/*public void addTile(AnimatedTile t) {
		tiles_anim.add(t);
	}*/
	
	
	public List<Entity> getEntities(Entity e, int radius, HOSTILITY host) {
		List<Entity> result = new ArrayList<Entity>();
		int ex = (int) e.x();
		int ey = (int) e.y();
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i).hostility == host) {
			Entity entity = entities.get(i);
			int x = (int) entity.x();
			int y = (int) entity.y();
			int dx = Math.abs(x - ex);
			int dy = Math.abs(y - ey);
			double distance = Math.sqrt((dx * dx) + (dy * dy));
			if (distance <= radius)
				result.add(entity);
				}
		}
		return result;
	}
	
	public List<Entity> getEntities(Entity e, int radius, HOSTILITY host, HOSTILITY host2) {
		List<Entity> result = new ArrayList<Entity>();
		int ex = (int) e.x();
		int ey = (int) e.y();
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i).hostility == host || entities.get(i).hostility == host2) {
			Entity entity = entities.get(i);
			int x = (int) entity.x();
			int y = (int) entity.y();
			int dx = Math.abs(x - ex);
			int dy = Math.abs(y - ey);
			double distance = Math.sqrt((dx * dx) + (dy * dy));
			if (distance <= radius)
				result.add(entity);
				}
		}
		return result;
	}
	
	public List<Entity> getEntities(Entity e, int radius, List<Entity> entities) {
		List<Entity> result = new ArrayList<Entity>();
		int ex = (int) e.x();
		int ey = (int) e.y();
		for (int i = 0; i < entities.size(); i++) {
			Entity entity = entities.get(i);
			int x = (int) entity.x();
			int y = (int) entity.y();
			int dx = Math.abs(x - ex);
			int dy = Math.abs(y - ey);
			double distance = Math.sqrt((dx * dx) + (dy * dy));
			if (distance <= radius)
				result.add(entity);
		}
		return result;
	}
	
	public List<Entity> getEntities(Entity e, int radius) {
		List<Entity> result = new ArrayList<Entity>();
		int ex = (int) e.x();
		int ey = (int) e.y();
		for (int i = 0; i < entities.size(); i++) {
			Entity entity = entities.get(i);
			int x = (int) entity.x();
			int y = (int) entity.y();
			int dx = Math.abs(x - ex);
			int dy = Math.abs(y - ey);
			double distance = Math.sqrt((dx * dx) + (dy * dy));
			if (distance <= radius)
				result.add(entity);
		}
		return result;
	}
	
	public List<Entity> getEntitiesFixed(int xx, int yy, int radius) {
		List<Entity> result = new ArrayList<Entity>();
		int ex = xx;
		int ey = yy;
		for (int i = 0; i < entities.size(); i++) {
			Entity entity = entities.get(i);
			int x = (int) entity.x();
			int y = (int) entity.y();
			int dx = Math.abs(x - ex);
			int dy = Math.abs(y - ey);
			double distance = Math.sqrt((dx * dx) + (dy * dy));
			if (distance <= radius)
				result.add(entity);
		}
		return result;
	}
	
	public List<Entity> getEntities(int x, int y, int radius)  {
		List<Entity> result = new ArrayList<Entity>();
		int ex = x;
		int ey = y;
		for (Entity e : entities) {
			int xx = (int) e.x();
			int yy = (int) e.y();
			int dx = Math.abs(x - ex);
			int dy = Math.abs(y - ey);
			double distance = Math.sqrt((dx * dx) + (dy * dy));
			if (distance <= radius)
				result.add(e);
		}
		return result;
	}
	
	public List<Entity> getEntitiesListFixed(int xx, int yy, int radius) {
		List<Entity> result = new ArrayList<Entity>();
		int ex = xx;
		int ey = yy;
		for (int i = 0; i < entitiesList.size(); i++) {
			Entity entity = entitiesList.get(i);
			int x = (int) entity.x();
			int y = (int) entity.y();
			int dx = Math.abs(x - ex);
			int dy = Math.abs(y - ey);
			double distance = Math.sqrt((dx * dx) + (dy * dy));
			if (distance <= radius)
				result.add(entity);
		}
		return result;
	}

	public List<Player> getPlayers(Entity e, int radius) {
		List<Player> result = new ArrayList<Player>();
		int ex = (int) e.x();
		int ey = (int) e.y();
		for (int i = 0; i < players.size(); i++) {
			Player player = players.get(i);
			int x = (int) player.x();
			int y = (int) player.y();
			int dx = Math.abs(x - ex);
			int dy = Math.abs(y - ey);
			double distance = Math.sqrt((dx * dx) + (dy * dy));
			if (distance <= radius)
				result.add(player);
		}
		return result;
	}
	
	public List<PlayerMP> getPlayers(Entity e, int radius, boolean no) {
		List<PlayerMP> result = new ArrayList<PlayerMP>();
		int ex = (int) e.x();
		int ey = (int) e.y();
		for (int i = 0; i < players.size(); i++) {
			PlayerMP player = players.get(i);
			int x = (int) player.x();
			int y = (int) player.y();
			int dx = Math.abs(x - ex);
			int dy = Math.abs(y - ey);
			double distance = Math.sqrt((dx * dx) + (dy * dy));
			if (distance <= radius)
				result.add(player);
		}
		return result;
	}
	
	
	public boolean getPlayersFixedBool(int xx, int yy, int radius) {
		boolean there = false;
		int ex = (int) xx;
		int ey = (int) yy;
		for (int i = 0; i < players.size(); i++) {
			PlayerMP player = players.get(i);
			int x = (int) player.x();
			int y = (int) player.y();
			int dx = Math.abs(x - ex);
			int dy = Math.abs(y - ey);
			double distance = Math.sqrt((dx * dx) + (dy * dy));
			if (distance <= radius) {
				there = true;
		} else {
			there = false;
		}
		}
			return there;
	}

	public boolean getPlayersBool(Entity e, int radius) {
		boolean there = false;
		int ex = (int) e.x();
		int ey = (int) e.y();
		for (int i = 0; i < players.size(); i++) {
			PlayerMP player = players.get(i);
			int x = (int) player.x();
			int y = (int) player.y();
			int dx = Math.abs(x - ex);
			int dy = Math.abs(y - ey);
			double distance = Math.sqrt((dx * dx) + (dy * dy));
			if (distance <= radius) {
				there = true;
		} else {
			there = false;
		}
		}
			return there;
	}
	
	public List<PlayerMP> getPlayersFixed(int xx, int yy, int radius) {
		List<PlayerMP> result = new ArrayList<PlayerMP>();
		int ex = (int) xx;
		int ey = (int) yy;
		for (int i = 0; i < players.size(); i++) {
			PlayerMP player = players.get(i);
			int x = (int) player.x();
			int y = (int) player.y();
			int dx = Math.abs(x - ex);
			int dy = Math.abs(y - ey);
			double distance = Math.sqrt((dx * dx) + (dy * dy));
			if (distance <= radius)
				result.add(player);
		}
		return result;
	}
	
	/*public void addTile(AnimatedTile e) {
		tiles_anim.add(e);
	}*/
	

	public Tile getTile(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height) {
			return Tile.VoidTile;
		}
		
		if (Boot.get().getScreen().hasAlpha(tiles[x + y * width])) {
			return Tile.Air;
		}
		
		
		Tile t = Tile.TileIndex.get((tiles[x + y * width]));
		if (t == null) {
			t = Tile.VoidTile;
		} else {
		}
		return t;
	}
	
	public Tile getTileLegacy(int x, int y) {				
		
		if (x < 0 || y < 0 || x >= width || y >= height)
			return Tile.VoidTile;
		
			if (tiles[x + y * width] == bitBrickHex)
			   return Tile.bitBrick;
			if (tiles[x + y * width] == bitMetalHex)
			   return Tile.bitMetal;
			if (tiles[x + y * width] == BluefogHex)
			   return Tile.Bluefog;
			if (tiles[x + y * width] == BookshelfBottomHex)
			   return Tile.BookshelfBottom;
			if (tiles[x + y * width] == BookshelfTopHex)
			   return Tile.BookshelfTop;
			if (tiles[x + y * width] == BrickCeilingHex)
			   return Tile.BrickCeiling;
			if (tiles[x + y * width] == BrickWallHex)
			   return Tile.BrickWall;
			if (tiles[x + y * width] == CaveCeilingHex)
			   return Tile.CaveCeiling;
			if (tiles[x + y * width] == CaveWallHex)
			   return Tile.CaveWall;
			if (tiles[x + y * width] == CobbleStoneHex)
			   return Tile.CobbleStone;
			if (tiles[x + y * width] == CobblestoneCeilingHex)
			   return Tile.CobblestoneCeiling;
			if (tiles[x + y * width] == CobbleStoneWallHex)
			   return Tile.CobbleStoneWall;
			if (tiles[x + y * width] == CrackedBrickHex)
			   return Tile.CrackedBrick;
			if (tiles[x + y * width] == DarkStoneHex)
			   return Tile.DarkStone;
			if (tiles[x + y * width] == DeepLavaHex)
			   return Tile.DeepLava;
			if (tiles[x + y * width] == DeepWaterHex)
			   return Tile.DeepWater;
			if (tiles[x + y * width] == DirtHex)
			   return Tile.Dirt;
			if (tiles[x + y * width] == DirtCeilingHex)
			   return Tile.DirtCeiling;
			if (tiles[x + y * width] == DirtWallHex)
			   return Tile.DirtWall;
			if (tiles[x + y * width] == DresserBottomHex)
			   return Tile.DresserBottom;
			if (tiles[x + y * width] == DresserTopHex)
			   return Tile.DresserTop;
			if (tiles[x + y * width] == GrassHex)
			   return Tile.Grass;
			if (tiles[x + y * width] == HellBrickHex)
			   return Tile.HellBrick;
			if (tiles[x + y * width] == HellBrickCeilingHex)
			   return Tile.HellBrickCeiling;
			if (tiles[x + y * width] == HellbrickWallHex)
			   return Tile.HellbrickWall;
			if (tiles[x + y * width] == HellCaveCeilingHex)
			   return Tile.HellCaveCeiling;
			if (tiles[x + y * width] == HellCaveWallHex)
			   return Tile.HellCaveWall;
			if (tiles[x + y * width] == HellsandHex)
			   return Tile.Hellsand;
			if (tiles[x + y * width] == HellSandCeilingHex)
			   return Tile.HellSandCeiling;
			if (tiles[x + y * width] == HellSandWallHex)
			   return Tile.HellSandWall;
			if (tiles[x + y * width] == HellstoneHex)
			   return Tile.Hellstone;
			if (tiles[x + y * width] == IceHex)
			   return Tile.Ice;
			if (tiles[x + y * width] == IceBrickHex)
			   return Tile.IceBrick;
			if (tiles[x + y * width] == IceBrickCeilingHex)
			   return Tile.IceBrickCeiling;
			if (tiles[x + y * width] == IceBrickWallHex)
			   return Tile.IceBrickWall;
			if (tiles[x + y * width] == IceCaveCeilingHex)
			   return Tile.IceCaveCeiling;
			if (tiles[x + y * width] == IceCaveWallHex)
			   return Tile.IceCaveWall;
			if (tiles[x + y * width] == IceSandHex)
			   return Tile.IceSand;
			if (tiles[x + y * width] == IceSandCeilingHex)
			   return Tile.IceSandCeiling;
			if (tiles[x + y * width] == IceSandWallHex)
			   return Tile.IceSandWall;
			if (tiles[x + y * width] == LavaHex)
			   return Tile.Lava;
			if (tiles[x + y * width] == MetalHex)
			   return Tile.Metal;
			if (tiles[x + y * width] == MetalCeilingHex)
			   return Tile.MetalCeiling;
			if (tiles[x + y * width] == MetalWallHex)
			   return Tile.MetalWall;
			if (tiles[x + y * width] == MossCeilingHex)
			   return Tile.MossCeiling;
			if (tiles[x + y * width] == MossWallHex)
			   return Tile.MossWall;
			if (tiles[x + y * width] == ObsidianCeilingHex)
			   return Tile.ObsidianCeiling;
			if (tiles[x + y * width] == ObsidianWallHex)
			   return Tile.ObsidianWall;
			if (tiles[x + y * width] == PathDirtHex)
			   return Tile.PathDirt;
			if (tiles[x + y * width] == SandHex)
			   return Tile.Sand;
			if (tiles[x + y * width] == SandBrickHex)
			   return Tile.SandBrick;
			if (tiles[x + y * width] == SandBrickCeilingHex)
			   return Tile.SandBrickCeiling;
			if (tiles[x + y * width] == SandBrickWallHex)
			   return Tile.SandBrickWall;
			if (tiles[x + y * width] == SandCeilingHex)
			   return Tile.SandCeiling;
			if (tiles[x + y * width] == SandWallHex)
			   return Tile.SandWall;
			if (tiles[x + y * width] == SnowHex)
			   return Tile.Snow;
			if (tiles[x + y * width] == StoneBrickHex)
			   return Tile.StoneBrick;
			if (tiles[x + y * width] == StoneBrickCeilingHex)
			   return Tile.StoneBrickCeiling;
			if (tiles[x + y * width] == StoneBrickWallHex)
			   return Tile.StoneBrickWall;
			if (tiles[x + y * width] == SwampHex)
			   return Tile.Swamp;
			if (tiles[x + y * width] == VoidTileHex)
			   return Tile.VoidTile;
			if (tiles[x + y * width] == WaterHex)
			   return Tile.Water;
			if (tiles[x + y * width] == WoodHex)
			   return Tile.Wood;
			if (tiles[x + y * width] == WoodCeilingHex)
			   return Tile.WoodCeiling;
			if (tiles[x + y * width] == WoodWallHex)
			   return Tile.WoodWall;
			if (tiles[x + y * width] == SwirlyHex)
			   return Tile.swirly;
		return Tile.VoidTile;
	}
	
	public Tile getOverlayTile(int x, int y) {
		  if (x < 0 || y < 0 || x >= width || y >= height) return null;
		//  if (overlayTiles != null) {
		    if (overlayTiles[x + y * width] == Cactus)
			   return Tile.Cactus;
			if (overlayTiles[x + y * width] == FlowerCactus)
			   return Tile.FlowerCactus;
			if (overlayTiles[x + y * width] == ColoredFlowers)
			   return Tile.ColoredFlowers;
			if (overlayTiles[x + y * width] == YellowFlowers) 
			   return Tile.YellowFlowers;
			if (overlayTiles[x + y * width] == Pebble)
			   return Tile.Pebble;
			if (overlayTiles[x + y * width] == BlueMushroom)
			   return Tile.BlueMushroom;
			if (overlayTiles[x + y * width] == BlueMushroomDirt)
			   return Tile.BlueMushroomDirt;
			if (overlayTiles[x + y * width] == RedMushroom)
			   return Tile.RedMushroom;
			if (overlayTiles[x + y * width] == RedMushroomDirt)
			   return Tile.RedMushroomDirt;
			if (overlayTiles[x + y * width] == GreenMushroom)
			   return Tile.GreenMushroom;
			if (overlayTiles[x + y * width] == GreenMushroomDirt)
			   return Tile.GreenMushroomDirt;
			if (overlayTiles[x + y * width] == DirtPatch)
			   return Tile.DirtPatch;
			if (overlayTiles[x + y * width] == DarkCastle)
			   return Tile.DarkCastle;
			if (overlayTiles[x + y * width] == Castle)
			   return Tile.Castle;
			if (overlayTiles[x + y * width] == Village)
			   return Tile.Village;
			if (overlayTiles[x + y * width] == Cave)
			   return Tile.Cave;
			if (overlayTiles[x + y * width] == Portal)
			   return Tile.Portal;
			if (overlayTiles[x + y * width] == PathVertical)
			   return Tile.PathVertical;
			if (overlayTiles[x + y * width] == PathHorizontal)
			   return Tile.PathHorizontal;
			if (overlayTiles[x + y * width] == PathCross)
			   return Tile.PathCross;
			if (overlayTiles[x + y * width] == PathCornerTL)
			   return Tile.PathCornerTL;
			if (overlayTiles[x + y * width] == PathCornerTR)
			   return Tile.PathCornerTR;
			if (overlayTiles[x + y * width] == PathCornerBL)
			   return Tile.PathCornerBL;
			if (overlayTiles[x + y * width] == PathCornerBR)
			   return Tile.PathCornerBR;
			if (overlayTiles[x + y * width] == PathEndLeft)
			   return Tile.PathEndLeft;
			if (overlayTiles[x + y * width] == PathEndRight)
			   return Tile.PathEndRight;
			if (overlayTiles[x + y * width] == PathEndTop)
			   return Tile.PathEndTop;
			if (overlayTiles[x + y * width] == PathEndBottom)
			   return Tile.PathEndBottom;
			if (overlayTiles[x + y * width] == BrokenSword)
			   return Tile.BrokenSword;
			if (overlayTiles[x + y * width] == RedBed)
			   return Tile.RedBed;
			if (overlayTiles[x + y * width] == BlueBed)
			   return Tile.BlueBed;
			if (overlayTiles[x + y * width] == GreenBed)
			   return Tile.GreenBed;
			if (overlayTiles[x + y * width] == OrangeBed)
			   return Tile.OrangeBed;
			if (overlayTiles[x + y * width] == TopChair)
			   return Tile.TopChair;
			if (overlayTiles[x + y * width] == BottomChair)
			   return Tile.BottomChair;
			if (overlayTiles[x + y * width] == LeftChair) return Tile.LeftChair;
			if (overlayTiles[x + y * width] == RightChair) return Tile.RightChair;
			if (overlayTiles[x + y * width] == Table) return Tile.Table;
			if (overlayTiles[x + y * width] == AnvilHex) return Tile.Anvil;
			if (overlayTiles[x + y * width] == StoveHex) return Tile.Stove;
			if (overlayTiles[x + y * width] == Skull) return Tile.skull;
			if (overlayTiles[x + y * width] == CrossedBones) return Tile.crossbone;
			if (overlayTiles[x + y * width] == Bone) return Tile.bone;
			if (overlayTiles[x + y * width] == DoorHex) return Tile.DoorTile;
			if (overlayTiles[x + y * width] == 0) return Tile.crossbone;
			if (overlayTiles[x + y * width] == CounterHex) return Tile.Counter;
			if (overlayTiles[x + y * width] == CounterBHex) return Tile.CounterB;

			

		  return null;
	//}
		//return null;
	}
	
	public void resetLevel(Player player) {
		
	}
	
public void resetLevelPostDeath(Player player) {
	//add(player);
	//player.setPosition(52, 78, Maps.spawnHavenId, true);
	}

	public Tile getTorchTile(int x, int y) {
		  if (x < 0 || y < 0 || x >= width || y >= height) return null;
		//  if (torchTiles != null) {
		  if (torchTiles[x + y * width] == Torch) 
			  return Tile.TorchTile;
		  else {
			  return null;
		  }
		//  }
		//return null;
	}
	public boolean getTile(double x, double y, Tile tile) {
		return false;
	}


	
	public void checkExits(Player player, Level level, int x, int y) {
	}

	protected static java.util.Random Random = new Random();
	public static java.util.Random random = Random;
	
	public void damage(int x, int y, Entity mob, long Exp, double damage, String name, int ExpV) {
		int color;
		String dmgInd = "" + damage;

		if (!mob.invulnerable) {
			mob.mobhealth -= (damage);
			mob.hurt = true;
			try {
				add(new DamageIndicator((int) (mob.x() - mob.getSprite().getWidth() / 2), (int) ((y - (mob.getSprite().getHeight() * 1.5))), 15, 1, dmgInd, 0xffDD0011));
			} catch (Exception e) {
				e.printStackTrace();
			}
			mob.incombat = true;
			if (mob.mobhealth <= 0) {

				mob.death();
				if (mob.remove()) {
						Boot.get().getLevel().getClientPlayer().kills += 1;
					if (mob.hostility != mob.hostility.PASS) {
						Boot.get().getPlayer().money += (mob.maxhealth / 2);
					}
				}
			}
		}
	}

	public void damagePlayer(int x, int y, PlayerMP player, long Exp, double damage, String name, int ExpV) {
			int color;
			int chance = (random.nextInt((101 - 1) + 1) + 1);
			String dmgInd = "0";

			if (damage < 1) {
				damage = 1;
			}

			System.out.println(player.maxhealth + " / " + player.mobhealth);

			dmgInd = "" + Math.round(damage);

			if (!player.invulnerable) {
					damage *= (7 / (1 + Math.pow(Math.E, -0.05 * (Boot.get().getPlayer().Lvl - 40)))) + 1;
					player.mobhealth -= (damage);
				System.out.println("Damage: " + damage);
				try {
					// add(new DamageIndicator((int)(player.getX()), (int)((y - 20)), 15, 1, dmgInd,
					// 0xffDD0011));
				} catch (Exception e) {
					e.printStackTrace();
				}
				// mob.getSprite().pixels[mob.getSprite().getWidth() *
				// mob.getSprite().getHeight()] = MyColor.changeBrightness(0, 60, false);
				player.incombat = true;
				if (player.mobhealth <= 0) {
					// player.remove();
				}
			}
		}

	protected void refresh() {
		if (Boot.get().autoSave) {
		Entity[] es = Boot.get().getLevel().entities.toArray(new Entity[Boot.get().getLevel().entities.size()]);
		Boot.get().getLevel().saveMobs(es);
		}
		for (int i = 0; i < entities.size(); i++) {
			entities.remove(i);
			entities.clear();
			//tiles_anim.clear();
		}
	}

	public List<Entity> getEntities() {
		return entities;
	}

	   public void removePlayerMP(String UUID) {
	     System.out.println("REMOVING PLAYER... " + UUID);
		   int index = 0;
	        for (Player e : players) {
	            if (e instanceof PlayerMP && ((PlayerMP) e).getUUID().equals(UUID)) {
	                break;
	            }
	            index++;
	        }
	        System.out.println("PLAYER REMOVED: " + players.get(index).getUsername());
	        players.get(index).remove();
	   }
	
	
	private int getPlayerMPIndex(String username) {
		int index = 0;
		for(Player e : players) {
			if (e instanceof PlayerMP && ((PlayerMP)e).getUsername().equals(username)) {
				break;
			}
			index++;
		}
		return index;
	}
	
	private int getPlayerMPIndexUUID(String UUID) {
		int index = 0;
		for(Player e : players) {
			if (e instanceof PlayerMP && ((PlayerMP)e).getUUID().equals(UUID)) {
				break;
			}
			index++;
		}
		return index;
	}


	
	public void MPTeleport(String tp) {
		try {
		int index = getPlayerMPIndex(tp);
		PlayerMP player = (PlayerMP) this.players.get(index);
		
		getClientPlayer().setX((int)player.x());
		getClientPlayer().setY((int)player.y());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public void addDoorTile(int x, int y) {
		try {			
		overlayTiles[x + y * width] = 0xff000000;
		tiles[x + y * width] = SwirlyHex;

		} catch (Exception e) {
			
		}
	}	
	
}