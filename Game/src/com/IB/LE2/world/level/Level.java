package com.IB.LE2.world.level;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import com.IB.LE2.Boot;
import com.IB.LE2.Game;
import com.IB.LE2.media.graphics.Screen;
import com.IB.LE2.media.graphics.Weather.Precipitation;
import com.IB.LE2.util.VARS;
import com.IB.LE2.util.Vector2i;
import com.IB.LE2.util.IO.SaveGame;
import com.IB.LE2.world.entity.Entity;
import com.IB.LE2.world.entity.EntityContainer;
import com.IB.LE2.world.entity.emitter.Emitter;
import com.IB.LE2.world.entity.mob.Mob;
import com.IB.LE2.world.entity.mob.Player;
import com.IB.LE2.world.entity.mob.PlayerMP;
import com.IB.LE2.world.entity.particle.DamageIndicator;
import com.IB.LE2.world.entity.particle.Particle;
import com.IB.LE2.world.entity.projectile.Projectile;
import com.IB.LE2.world.level.tile.Tile;


public class Level extends EntityContainer implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2980023760275759466L;
	public int width, height;
	protected int[] tilesInt;
	public int[] tiles;
	public int[] overlayTiles;
	@Deprecated
	public int[] torchTiles;
	transient public Tile tile;
	public static int nighttime = 0, daytime = 0;

	//public List<AnimatedTile> tiles_anim = new ArrayList<AnimatedTile>();
	double addX, addY;
	ArrayList<Integer> xRad = new ArrayList<Integer>();
	double tx, ty;
	double otx, oty;
	
	transient private Comparator<Node> nodeSorter = new Comparator<Node>()
	{
		public int compare(Node n0, Node n1)
			{
				if (n1.fCost < n0.fCost) return +1;
				if (n1.fCost > n0.fCost) return -1;
				return 0;

			}
	};

//	public static String currentLevel = spawn.toString();
	
	public static int brightness = 50;
	public static boolean minimap_enabled = false;
	public static boolean minimap_collapsed = false;
	
	public int time = 0;
    public boolean day = false, night = false;
	        
	public Level(int width, int height)
		{
			this.width = width;
			this.height = height;
			tilesInt = new int[width * height];
			generateLevel();
		}

	public Level(String path)
		{
			loadLevel(path);
			generateLevel();
			// add(new ParticleSpawner(29 * TileCoord.TILE_SIZE, 31*TileCoord.TILE_SIZE, 13,
			// 35, this));
		}
	
	public String Level_Dir;
	public Level(String path, boolean foo)
		{
			this.Level_Dir = path;
			String XML = path + "/tags.xml";
			LoadXML(XML);
			loadLevel(path);
		}
	
	@Deprecated
	public void LoadXML(String XML) {}
	
	public void loadLua() {}
	
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
	
	
	@Deprecated
	public void saveMobs(Entity[] es) {
		//System.out.println("SAVING TO: " + SaveGame.mobsFileName + Game.currentLevelId);
		SaveGame.save(es, SaveGame.mobsFileName/* + Game.currentLevelId*/);
	}
	
	@Deprecated
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
		
		/*for(int i = 0; i < temp.length; i++) {
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
		}*/
	}
	}
	
	@Deprecated
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
	
	@Deprecated
	protected void generateLevel() {

	}
	
	protected void loadLevel(String path) {
		/*for (int i = 0; i < entities.size(); i++) {
		entities.get(i).remove();
		}*/
	}
	
	public static Entity createEntity(String msg)
		{
			System.out.println("CREATING ENTITY: " + msg);
			int eid = Integer.parseInt(msg.substring(msg.indexOf("id_e=") + 5, msg.indexOf("&")));
			String UUID = (msg.substring(msg.indexOf("id=") + 3, msg.indexOf("@")));
			double x = Double.parseDouble(msg.substring(msg.indexOf("x=") + 2, msg.indexOf(",")));
			double y = Double.parseDouble(msg.substring(msg.indexOf("y=") + 2, msg.indexOf("#")));
			//System.out.println("EID: " + eid /* + " ID: " + id */ + " X: " + x + " Y: " + y);

			Entity e = null;
			switch (eid) {
			case 0:
				String usrn = (msg.substring(msg.indexOf("un=") + 2));
				e = new PlayerMP(x, y, usrn, UUID);
				e.name = usrn;
				break;
			}
			return e;
		}
	
	public static String entityStringBuilder(Entity e) {
			String result = "ADD|id_e=0&id=25@x=96,y=1248#un=usrn";
			String eid, UUID, x, y, usrn;
			usrn = "";
			if (e instanceof Player) {
			 eid = "0";
			 if(e.name != null)
				 usrn = e.name;
			} else {
			 eid = "-1";
			}
			 UUID = e.UUID;
			 x = "" + e.x();
			 y = "" + e.y();
			
			result = "ADD|id_e=" + eid + "&id=" + UUID + "@x=" + x + ",y=" + y + "#un=" + usrn;
					
		return result;
	}
	
	@Deprecated
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
			transient public Precipitation rain = new Precipitation();

			
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
		if (!VARS.suspend_world) {
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
		
		for (int i = 0; i < emitters.size(); i++) {
			emitters.get(i).update();
		}
		}
		
		for (int i = 0; i < players.size(); i++) {
			players.get(i).update();				
		}

		//Water.update();
		
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
			int x = Boot.width - size - 5;
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
	/*SpriteSheet pl_bg = new SpriteSheet("/XML/Levels/a10/assets/parallax/bg.png", 640, 360);
	SpriteSheet pl_first = new SpriteSheet("/XML/Levels/a10/assets/parallax/first_layer.png", 149, 35);
	SpriteSheet pl_second = new SpriteSheet("/XML/Levels/a10/assets/parallax/second_layer.png", 234, 92);
	SpriteSheet pl_third = new SpriteSheet("/XML/Levels/a10/assets/parallax/third_layer.png", 352, 297);*/

	public void render(int xScroll, int yScroll, Screen screen) {
		screen.setOffset(xScroll, yScroll);
		int x0 = xScroll >> VARS.TILE_BIT_SHIFT;
		int x1 = (xScroll + screen.width + TileCoord.TILE_SIZE) >> VARS.TILE_BIT_SHIFT;
		int y0 = yScroll >> VARS.TILE_BIT_SHIFT;
		int y1 = (yScroll + screen.height + TileCoord.TILE_SIZE) >> VARS.TILE_BIT_SHIFT;
		
		try {
			
		//screen.renderSheet(0, 0, pl_bg, false);
		//screen.renderParallax(bgcolor, pl_first, pl_second, pl_third, 37);
		
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
					   // Tile overlayTile = getOverlayTile(x,y);
					   // if (overlayTile != null) overlayTile.render(x,y,screen);
					    //Tile torchTiles = getTorchTile(x,y);
					    //if (torchTiles != null) torchTiles.render(x,y,screen);
					   // if (setTiles != null) setTiles.render(x, y, screen);

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
		 
		 drawExtendedLevel(screen);

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
	
	public void drawExtendedLevel(Screen screen) {}
	
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
		for (int i = 0; i < emitters.size(); i++) {
			if (emitters.get(i).isRemoved())
			emitters.remove(i);
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
			e.added();
		} else if (e instanceof Emitter) {
			emitters.add((Emitter) e);
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
		return Boot.get().getPlayer();
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
		   if(rayLength <= 0) {
		      result.setCollided(this.getTile(pos.getX()>>4, pos.getY()>>4).solid());
		      result.setPosition(pos);
		      return result;
		   }
		   double adjacent = pos.getX()+rayLength*Math.cos(angle);
		   double opposite = pos.getY()+rayLength*Math.sin(angle);
		   List<Vector2i> rayLine = BresenhamLine(pos.getX(), pos.getY(), (int)adjacent, (int)opposite);
		   if(!rayLine.isEmpty()){
		      for(int rayVectorIndex = 0; rayVectorIndex < rayLine.size(); rayVectorIndex++){
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
	
	
	@Deprecated
	public List<Entity> getEntities(Entity e, int radius, String host) {
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
	@Deprecated
	public List<Entity> getEntities(Entity e, int radius, String host, String host2) {
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
	@Deprecated
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
	@Deprecated
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
	@Deprecated
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
	@Deprecated
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
	@Deprecated
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
	@Deprecated
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
	
	@Deprecated
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
	@Deprecated
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
	@Deprecated
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
		if (x < 0 || y < 0 || x >= width || y >= height) return Tile.VoidTile;
		
		Tile t = Tile.TileIndex.get((tiles[x + y * width]));
		t = (t == null) ? Tile.VoidTile : t;
		
		return t;
	}
	
	@Deprecated
	public void resetLevel(Player player) {
		
	}
	
	@Deprecated
	public void resetLevelPostDeath(Player player) {
	//add(player);
	//player.setPosition(52, 78, Maps.spawnHavenId, true);
	}

	public boolean getTile(double x, double y, Tile tile) {
		return false;
	}
	
	public void checkExits(Player player, Level level, int x, int y) {
	}

	protected static java.util.Random Random = new Random();
	public static java.util.Random random = Random;
	
	// Create one damage method, check if the entity shooting
	// is the entity hit detected and don't damage it
	@Deprecated
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
					if (mob.hostility != "PASS") {
						Boot.get().getPlayer().money += (mob.maxhealth / 2);
					}
				}
			}
		}
	}

	@Deprecated
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

	/*   public void removePlayerMP(String UUID) {
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
	}*/


	
}
