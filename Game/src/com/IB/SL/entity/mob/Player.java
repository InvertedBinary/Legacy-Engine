package com.IB.SL.entity.mob;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import com.IB.SL.Boot;
import com.IB.SL.Game;
import com.IB.SL.VARS;
import com.IB.SL.entity.PVector;
import com.IB.SL.entity.projectile.Projectile;
import com.IB.SL.entity.projectile.XML_Projectile;
import com.IB.SL.graphics.AnimatedSprite;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.graphics.SpriteSheet;
import com.IB.SL.graphics.UI.GUI;
import com.IB.SL.input.Keyboard;
import com.IB.SL.input.Mouse;
import com.IB.SL.level.Node;
import com.IB.SL.level.RayCast;
import com.IB.SL.level.TileCoord;
import com.IB.SL.level.tile.Tile;
import com.IB.SL.level.worlds.MainLevel;
import com.IB.SL.level.worlds.Maps;
import com.IB.SL.level.worlds.SpawnHaven_Deprecated;
import com.IB.SL.level.worlds.Tiled_Level;
import com.IB.SL.level.worlds.XML_Level;
import com.IB.SL.util.Commands;
import com.IB.SL.util.LoadProperties;
import com.IB.SL.util.Sound;
import com.IB.SL.util.Vector2i;
import com.IB.SL.util.shape.Rectangle;

public class Player extends Mob implements Serializable{

	/**
	 * 
	 */
	private transient  static final long serialVersionUID = -8911018741301426797L;
	public transient  Keyboard input;
	public transient  Tile tile;
	transient double xOff = 0;
	transient double yOff = 0;
	public boolean buildMode = false;

	//private transient transient Inventory inventory;
	//public transient Level level;
	transient int walkingPacketTime = 0;
	public transient  AnimatedSprite down = new AnimatedSprite(SpriteSheet.player_down, 64, 64, 7);
	public transient  AnimatedSprite idle = new AnimatedSprite(SpriteSheet.player_up, 64, 64, 7);
	public transient  AnimatedSprite left = new AnimatedSprite(SpriteSheet.player_left, 64, 64, 7);
	public transient  AnimatedSprite right = new AnimatedSprite(SpriteSheet.player_right, 64, 64, 7);
	
//	private transient  AnimatedSprite player_upstill = new AnimatedSprite(SpriteSheet.player_upstill, 16, 16, 1);
//	private transient  AnimatedSprite player_downstill = new AnimatedSprite(SpriteSheet.player_downstill, 16, 16, 1);
//	private transient  AnimatedSprite player_leftstill = new AnimatedSprite(SpriteSheet.player_leftstill, 16, 16, 1);
//	private transient  AnimatedSprite player_rightstill = new AnimatedSprite(SpriteSheet.player_rightstill, 16, 16, 1);
	
	public transient  AnimatedSprite animSprite = down;
	
	public transient static java.util.Random random1 = new Random();
	public transient static int random = random1.nextInt(8 + 4);
	public transient  int fireRate = 1;

	public transient Commands cmd;
	private transient double time = 0;
	public transient boolean sprinting = false;
	public transient boolean commandModeOn = false;
	private transient boolean cmdReleased = true;
	public transient boolean swimming = false;
	public transient GUI gui;
	public transient LoadProperties loadProp;
	private  int tileX;
	private  int tileY;
	public boolean noclip = false;
	transient private List<Node> path = null;
	transient double Pathtime = 0;
	private transient boolean loadedProp = false;
	transient RayCast raycastDIR;
	private transient  int dirInt = 0;
	public int currentLevelId = 0;
	
	//TODO: Generate UUID and send instead of USErname
	
	
	public Player(Keyboard input) {
		this.name = Boot.get().PlayerName;
		this.input = input;
		sprite = Sprite.playerback;
	}
	
	public Player(double x, double y, Keyboard input, String username) { 
		this.setX(x);
		this.setY(y);
		this.name = username;
		this.input = input; 
		init();
	}
	
	public void init() {
		this.speed = 2;
		this.Lvl = 1;
		this.xBound = 8;
		this.yBound = 8;
		this.xOffset = -16;
		this.yOffset = -16;
		this.money = 30;
		this.hostility = hostility.PLAYER;

		this.maxhealth = 20;
		this.maxmana = 20;
		this.maxstamina = 20;
		
		cmd = new Commands();
		gui = new GUI();
		loadProp = new LoadProperties();
		
		this.mobhealth = maxhealth;
		this.mana = maxmana;
		this.stamina = maxstamina;
		
		body.bounds = new Rectangle((float)x(), (float)y(), 32, 64);
		body.set(VARS.PHYS_NOGRAV, true);
		body.bounds.set(VARS.REND_LOCALLY, true);
		
		System.out.println("ADDING NEW PLAYER: " + this.x() + "," + this.y());
	}
	
	
	
	
	@Deprecated
	public void invokeSave(Player p) {
		/*this.currentLevelId = Game.currentLevelId;
		for (int i = 0; i < level.entities.size(); i++) {
			System.out.println("[===] " + level.entities.get(i).getClass());
		}
		System.out.println("====>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>===     |      " + level.entities + " ||| + " + level.entities.size());
		Entity[] es = level.entities.toArray(new Entity[level.entities.size()]);
		level.saveMobs(es);
		for(int i = 0; i < es.length; i++) {
			System.out.println("  |==|  " + es[i]);
		}
		es = null;
		SaveGame.save(p);*/
	}
	
	@Deprecated
	public void invokeLoad(Player p) {
		/*try {
		loadProp.loadPrefs(Boot.get());;
		Player temp = SaveGame.load();

		System.out.println("-----------------------STEP1---------------------------");
		p.riding = false;
		p.speed = 1;
		
		p.currentLevelId = Game.currentLevelId;

		p.Lvl = temp.Lvl;
		p.kills = temp.kills;
		p.money = temp.money;
		setPosition(temp.x(), temp.y(), temp.currentLevelId, false);

		p.mobhealth = temp.mobhealth;
		p.mana = temp.mana;
		p.stamina = temp.stamina;
		
		System.out.println("-----------------------STEP2---------------------------");
		//this.inventory.loadItems(this);
		System.out.println("-----------------------STEP3---------------------------");
		//this.equipment.loadItems(this);
		System.out.println("-----------------------STEP4----------------------------");
		//this.quests.loadQuests(this);
		System.out.println("-----------------------STEP5----------------------------");
		temp = null;
		
		} catch (Exception e) {
			
		}*/
	}
	
	boolean addedAbility = false;
	
	public void updateBuild() {
		if (switchTimer > 0) {
		this.switchTimer--;
		}
	}
	
	
	 PVector pv = null;

	public void update() {		
		/*try {
			
		if (input.save){
			invokeSave(this);
			input.save = false;
		} else if (input.load){
			if (input.Sprint) {
				Boot.get().getLevel().loadMobs(Game.currentLevelId);
			} else {
				invokeLoad(this);
			}
			input.load = false;
		}
	
		} catch (Exception e) {
			
		}*/
		
        raycastDIR = level.RayCast(new Vector2i(x(), y()), dirInt, (int)3);
		
		Pathtime++;
		tileX = (int) x() >> VARS.TILE_BIT_SHIFT;
		tileY = (int) y() >> VARS.TILE_BIT_SHIFT;
		try {
			if (level.getTile(tileX, tileY).exit()) {
				level.checkExits(this, level, (int)x(), (int)y());
			}
		} catch (Exception e) {

		}

		if (Boot.get().devModeOn) {
		this.mana = maxmana;
		this.mobhealth = maxhealth;
		this.stamina = maxstamina;
		}
		int levelcounter = Lvl;
		int expCounter = 4;
			
		for (;levelcounter != 0; levelcounter--) {
			expCounter += (((levelcounter + 1) * (levelcounter + 1) * (levelcounter + 1))/2);
		}
		int expNeeded = expCounter;
		
		time++;
		
		animSprite.update();					
			
		if (!walking) {
				animSprite = idle;
				this.animSprite.setFrameRate(8);
			} else {
				this.animSprite.setFrameRate(6 -  (int)this.speed / 2);
			}
		
	//	if (abilityCooldown > 0) abilityCooldown--;
		if (fireRate > 0) fireRate--;
		
		if (!moving) {
			xOff = 0;
			yOff = 0;
		}
		
		if (swimming) {
			//speed = 0.5;
		}
		
		if (swimming && sprinting) {
			//speed = 1;
		}
		
		 double xa = 0;
		 double ya = 0;
		 double yv = 0;
				 
		 if (pv == null) {
			 pv = new PVector(vel());
		 }
		 
		if (input != null) {
		this.buildMode = input.buildMode;
			
		if (input.Sprint && walking)  { // 300
			speed = 4;
			sprinting = true;
		} else {
			speed = 2;
		}
		

		if (!input.Sprint || stamina <= 0) {
			sprinting = false;
		}
		
	//	if (inventoryOn == false) {
		if (this == level.getClientPlayer()) {
		if (input.up) {
			//animSprite = up;
			yv -= speed;
		} else if (input.down) {
			//animSprite = down;
			yv += speed;
		}
		if (input.left) {
			animSprite = left;				
			this.vel().x(-speed);
		} else if (input.right) {
			animSprite = right;				
			this.vel().x(speed);
			} 
		
		if (this.input.jump & this.canJump) {
			this.vel().y(-6.5);
		}
		
		}
		} else {
		}
			//System.out.println("POS: " + pos.toString() + " VEL: " + vel.toString());
			//body.set(VARS.PHYS_NOGRAV, false);
			//if (!body.bounds.intersects(Boot.get().pb.bounds)) {
			//body.move();
			//}
		PVector Gravity = new PVector();
		Gravity.y(VARS.Ag);
		
		this.vel().add(Gravity);
		
		if (this.equals(Boot.get().getPlayer())) {
		if (!((vel().x() == pv.x()) && (vel().y() == pv.y()))) {
			Boot.c.sendMessage("VEL|id=" + this.UUID + "@x=" + this.vel().x() + ",y=" + this.vel().y());
			Boot.c.sendMessage("POS|id=" + this.UUID + "@x=" + this.pos().x() + ",y=" + this.pos().y());
			pv = new PVector(vel());
		}
		
		if (xa != 0 || ya != 0) {
			Game.createNewPresence();
		}
		
		ya = vel().y();
		xa = vel().x();
		
		if (this.vel().y() > 53) {
			this.vel().y(53);
		}
		
		if (xa != 0) {
			walking = true;
		} else {
			walking = false;
		}
		
			if (!noclip) {
				move(xa, ya);
			} else {
				setX(x() + xa * speed);
				setY(y() + yv * speed);
			}
			
			this.vel().x(0);
		} else {
			ya = vel().y();
			xa = vel().x();
			move(xa, ya);
		}
		
			clear();
	
			if (this == level.getClientPlayer()) {
			updateBuild();
			}
						
		//command mode TOGGLE
			if (input != null) {
		if(input.commandMode && !commandModeOn && cmdReleased){
			commandModeOn = true;
			cmdReleased = false;
		}
		
			if (!input.commandMode)
				cmdReleased = true;

			if (input.commandMode && commandModeOn && cmdReleased) {
				commandModeOn = false;
				cmdReleased = false;
			}

		}

		updateShooting();
	}

	public void updateShooting() {
		if (Mouse.getButton() == 1) {
			XML_Projectile Test_Arrow = new XML_Projectile(x(), y(), Projectile.angle(), "/XML/Projectiles/Arrow.xml", this);
			XML_Projectile Test_Arrow2 = new XML_Projectile(x(), y(), Projectile.angle() + (Math.PI / 2), "/XML/Projectiles/Arrow.xml", this);
			Test_Arrow2.sprite = Sprite.WizardProjectile2;
			Test_Arrow.nx += vel().x();
			Test_Arrow.ny += vel().y();
			level.add(Test_Arrow);
			
			level.add(Test_Arrow2);
		}
	}
	
	private void clear() {
		for (int i = 0; i < level.getProjectiles().size(); i++) {
			Projectile p = level.getProjectiles().get(i);
			if (p.isRemoved()) level.getProjectiles().remove(i);
		}
	}
	
	public void setPosition(TileCoord tileCoord) {
		this.setX(tileCoord.x());
		this.setY(tileCoord.y());
	}
	
	public void setPositionTiled(double x, double y, String XML, boolean tileMult) {
		System.out.println("Got request to load a Tiled level.");
		if (tileMult) {
			x *= TileCoord.TILE_SIZE;
			y *= TileCoord.TILE_SIZE;
		}
		this.currentLevelId = -1;

		Tiled_Level newLevel = new Tiled_Level(XML);
		Boot.get().setLevel(newLevel);
		Boot.get().getLevel().add(this);

		this.removed = false;
		this.setX((x));
		this.setY((y));
		//newLevel.initLua();
		
		Game.createNewPresence();
	}
	
	@Deprecated
	@SuppressWarnings("deprecation")
	public void setPositionXML(double x, double y, String XML, boolean tileMult) {
		//Entity[] es = level.entities.toArray(new Entity[level.entities.size()]);
		//level.saveMobs(es);
		if (tileMult) {
			x *= TileCoord.TILE_SIZE;
			y *= TileCoord.TILE_SIZE;
		}

		if (Boot.get().getLevel() instanceof XML_Level) {
			if (((XML_Level) Boot.get().getLevel()).luaThread.isAlive()) {
				((XML_Level) Boot.get().getLevel()).killLua();
				((XML_Level) Boot.get().getLevel()).luaThread.interrupt();
				((XML_Level) Boot.get().getLevel()).luaThread.stop();
			}
		}
		
		this.currentLevelId = -1;
		XML_Level newLevel = new XML_Level(XML);
		
		
		Boot.get().setLevel(newLevel);
		//Sound.switchMusic(Sound.Windwalker, 1f);
		
		Boot.get().getLevel().add(this);

		this.removed = false;
		this.setX((x));
		this.setY((y));
		newLevel.initLua();
		
		
		//((XML_Level)Boot.get().levels.get(Boot.get().levels.size() - 1)).luaThread.stop();
		Game.createNewPresence();

	}
	
	@Deprecated
	public void setPosition(double x, double y, int LvlId, boolean tileMult) {
		//Entity[] es = level.entities.toArray(new Entity[level.entities.size()]);
		//level.saveMobs(es);
		if (tileMult) {
			x *= TileCoord.TILE_SIZE;
			y *= TileCoord.TILE_SIZE;
		}

		this.currentLevelId = Game.currentLevelId;
		
		System.out.println("Loaded ID: " + LvlId + ", ID: " + currentLevelId);
		
		switch (LvlId) {
		case 120:
			Boot.get().setLevel(new XML_Level(Maps.XML_Haven));
			//Sound.switchMusic(Sound.Windwalker, 1f);
			break;
		case 0:
			Boot.get().setLevel(new SpawnHaven_Deprecated(Maps.SpawnHaven));
			//Sound.switchMusic(Sound.Windwalker, 1f);
			break;
		case 1:
			Boot.get().setLevel(new MainLevel(Maps.main));
			Sound.switchMusic(Sound.Windwalker, 1f);
			SpriteSheet.minimapDYN = new SpriteSheet(Maps.main, 1024);
			break;
		}
		this.removed = false;
		Boot.get().getLevel().add(this);
		this.setX((x));
		this.setY((y));
		//Boot.get().getLevel().loadMobs(LvlId);
	}
	
	public String getUsername() {
		return this.name;
	}
	
	private transient Sprite arrow = Sprite.QuestArrow;

	public void render(Screen screen) {
		
		sprite = animSprite.getSprite();

		this.xOffset = -22;
		this.yOffset = -45;
		xOffset = 0;
		yOffset = 0;
		xOff = 0;
		yOff = 0;
		screen.renderMobSpriteUniversal((int) (x() + xOff + xOffset), (int) (y() + yOff + yOffset), sprite);
	
	}
	
	public int roundTo(int number, int multiple) {
		if (multiple == 0) {
			return number;
		}
		
		int remainder = number % multiple;
		if (remainder  == 0) {
			return number;
		}
		
		return number + multiple - remainder;
	}
	
	public static Image getImageFromArray(int[] pixels, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        WritableRaster raster = (WritableRaster) image.getData();
        raster.setPixels(0,0,width,height,pixels);
        return image;
    }
	
	public void saveLevel(int[] tiles, String path) {
		try {
			BufferedImage pixelImage = new BufferedImage(level.width, level.height, BufferedImage.TYPE_INT_RGB);    
		    pixelImage.setRGB(0, 0, level.width, level.height, tiles, 0, level.width);
		    
		    	File outputfile = new File(path);
				ImageIO.write(pixelImage, "png", outputfile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		System.out.println("Saved.");
	}
	
	transient public ArrayList<Tile> history = new ArrayList<Tile>();
	transient Tile toPlace = Tile.Wood;
	
	
	public void swapBlock(int index) {
		if (history.get(index) != null) {
		Tile old = history.get(0);
		history.set(0, history.get(index));
		history.set(index, old);
		switchTimer = 15;
		}
	}
	
	int switchTimer = 00;
	private void switchBlocks_key() {
	try {
	if (switchTimer == 0) {
	  if (Boot.get().getPlayer().input.a2) {
		swapBlock(1);
	} if (Boot.get().getPlayer().input.a3) {
		swapBlock(2);
	} if (Boot.get().getPlayer().input.a4) {		  
		swapBlock(3);
	} if (Boot.get().getPlayer().input.a5) {		  
		swapBlock(4);
	} if (Boot.get().getPlayer().input.a6) {		  
		swapBlock(5);
	} if (Boot.get().getPlayer().input.a7) {		 
		swapBlock(6);
	} if (Boot.get().getPlayer().input.a8) {		  
		swapBlock(7);
	} if (Boot.get().getPlayer().input.a9) {		  
		swapBlock(8);
	} if (Boot.get().getPlayer().input.a0) {
		swapBlock(9);
	}
	}
		} catch (NullPointerException e) {
			System.out.println("No Ability Was Found In Requested Slot");
		}
	}	
	
	@Deprecated
	public void renderBuildGUI(Screen screen) {
		switchBlocks_key();
		gui.font8x8.render(113, 5, -2, 0xff000000, "BUILD MODE", screen, false, false);
		gui.font8x8.render(112, 5, -2, 0xffFFFFFF, "BUILD MODE", screen, false, false);
		
		if (history.size() == 0) {
			history.add(Tile.Wood);
		}
		
		toPlace = history.get(0);
		
		int x = (screen.xo);
		int y = (screen.yo);
		
		screen.renderPlaceTile(x << VARS.TILE_BIT_SHIFT, y << VARS.TILE_BIT_SHIFT, toPlace);

		for (int i = 0; i < history.size(); i++) {
			int xo = 2; 
			int yo = Game.height - 25;
			screen.drawRect(xo + (i * 19) - 1, yo - 1, 17, 17, 0xff000000, false);
			screen.renderSprite(xo + (i * 19), yo, history.get(i).sprite, false);
		//	screen.renderAlphaSprite(0, 0, sprite.gray);
			if (i != 9) {
				gui.font.render(xo + (i * 19) - 17, yo, "" + (i + 1), screen, false, false);
			} else {
				gui.font.render(xo + (i * 19) - 17, yo, "" + 0, screen, false, false);

			}
		}
		
		if (history.size() > 9) {
			for (int i = 10; i < history.size(); i++) {
				history.remove(i);
			}
		}
		
		if (Mouse.getButton() == 3) {
			if (level.getTile(x, y).getHex() != Screen.ALPHA_COL) {
			Tile getTile = level.getTile(x, y);
			if (!history.contains(getTile)) {
			history.add(0, getTile );
			} else {
				history.remove(getTile);
				history.add(0, getTile);
			}
			System.out.println("Switched to tile: " + toPlace);
			}
		}
		
		if (Mouse.getButton() == 2) {
			saveLevel(level.tiles, "saved.png");
			saveLevel(level.overlayTiles, "ov_saved.png");
			Mouse.setMouseB(-1);
		}
		
		if (Mouse.getButton() == 1) {
			this.level.tiles[x + y *  level.width] = toPlace.getHex();
			SpriteSheet.minimapDYN.pixels[x + y * SpriteSheet.minimapDYN.getWidth()] = toPlace.getHex();
		}
		
	}
	
	public void renderGUI(Screen screen) {
		
		if(buildMode) {
			renderBuildGUI(screen);
		}
	
	if (level.minimap_collapsed) {
	screen.renderSheet(254, 0, SpriteSheet.minimap_hidden, false);
	}
	
		if (buildMode) {
		gui.renderBuild(screen, this);
		}
	
		String text = (int)Boot.get().getPlayer().x() / TileCoord.TILE_SIZE + "," + (int)Boot.get().getPlayer().y() / TileCoord.TILE_SIZE;
		//screen.renderSprite(1064/ Game.scale, 530 / Game.scale, gui.renderHealthExperiment(screen, this, 20), false);
		if (!level.minimap_enabled) {
			Boot.get().font.render((int)Game.width - text.length() * 16, 3, -3, text, screen, false, false);
			Boot.get().font.render((int)Game.width - text.length() * 16 + 1, 3, -3, 0xffFFFFFF, text, screen, false, false);
		} else if (!level.minimap_collapsed){
			//screen.renderSprite(275 - text.length() * 8, 1, new Sprite(50, 12, 0xff262626), false);
			Boot.get().font.render((int)Game.width - 35 - text.length() * 16, 3, -3, text, screen, false, false);
			Boot.get().font.render((int)Game.width - 35 - text.length() * 16 + 1, 3, -3, 0xffFFFFFF, text, screen, false, false);
		} else {
			Boot.get().font.render((int)Game.width - text.length() * 16, 3, -3, text, screen, false, false);	
			Boot.get().font.render((int)Game.width - text.length() * 16 + 1, 3, -3, 0xffFFFFFF, text, screen, false, false);
		}

		
		if (!gui.displayM && !gui.displayS) {
			gui.yOffH = 156;
		} else if (!gui.displayS && gui.displayM) {
			gui.yOffH = 143;
		} else if (gui.displayS && !gui.displayM) {
			gui.yOffH = 143;
		} else if (gui.displayS && gui.displayM){
			gui.yOffH = 130;
		}
		
		
		if (!gui.displayS) {
			gui.yOffM = 156;
		} else {
			gui.yOffM = 143;
		}
		
		
		
		  if (stamina < maxstamina) {
				gui.displayTimeS = 0;
				gui.displayS = true;
			} else if (gui.displayTimeS < 151){
				gui.displayS = false;
				gui.displayTimeS++;
			}
			if (gui.displayS) {
		screen.renderSprite(223, gui.yOffS, gui.renderBar(60, gui.staminabar, maxstamina, stamina), false); // 156
			}
			
		  if (incombat || mana < maxmana) {
				gui.displayTimeM = 0;
			} else if (gui.displayTimeM < 151){
				gui.displayTimeM++;
				gui.displayM = false;
			}
			if (gui.displayTimeM <= 150) {
				gui.displayM = true;
		screen.renderSprite(223, gui.yOffM, gui.renderBar(60, gui.manabar, maxmana, mana), false); // 143
			}
		
		
		if (incombat || mobhealth < maxhealth) {
			gui.displayTime = 0;
		} else if (gui.displayTime < 151){
			gui.displayTime++;
			gui.displayH = false;
		}
		if (gui.displayTime <= 150) {
			gui.displayH = true;
		screen.renderSprite(223, gui.yOffH, gui.renderBar(60, gui.healthbar, maxhealth, mobhealth), false); // 130
		}
	//	screen.renderSprite(0, 143, gui.expBar.getSprite(), false);
		
		if (Game.devModeOn) {
			//screen.drawRect((int) x() - 8, (int) y() - 15, 64, 64, 0x0093FF, true);
			body.draw(screen);
			//this.pos().draw(screen);
			//this.vel().draw(screen);
			try {
				//Boot.get().getScreen().drawVectors(Boot.get().getLevel().BresenhamLine((int) x(), (int) y(),
				//	raycastDIR.rayVector.x, raycastDIR.rayVector.y), 0xffFF3AFB, true);
				} catch (NullPointerException e) {
			}
		}
	}
}
















