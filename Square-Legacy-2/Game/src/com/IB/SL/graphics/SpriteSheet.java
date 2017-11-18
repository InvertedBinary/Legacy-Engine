package com.IB.SL.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.IB.SL.level.worlds.Maps;

public class SpriteSheet {
	
	/**
	 * 
	 */
	private String path;
	public final int SIZE;
	public final int SPRITE_WIDTH, SPRITE_HEIGHT;
	private int width, height;
	public int[] pixels;
	
	
	public static SpriteSheet Teleporter = new SpriteSheet("/Textures/sheets/03_WorldGen/Structure/Teleporter.png", 32, 512);
	public static SpriteSheet maps_Spawn = new SpriteSheet(Maps.main, 1024, 1024);

	public static SpriteSheet inventoryItems = new SpriteSheet("/Textures/sheets/01_GUI/Menu/Inventory/inventoryItems.png", 136, 104);
	public static SpriteSheet inventoryEquip = new SpriteSheet("/Textures/sheets/01_GUI/Menu/Inventory/inventoryEquip.png", 136, 104);
	public static SpriteSheet inventoryQuest = new SpriteSheet("/Textures/sheets/01_GUI/Menu/Inventory/inventoryQuest.png", 136, 104);
	public static SpriteSheet inventoryAbilities = new SpriteSheet("/Textures/sheets/01_GUI/Menu/Inventory/inventoryAbilities.png", 136, 104);
	public static SpriteSheet inventorySkills = new SpriteSheet("/Textures/sheets/01_GUI/Menu/Inventory/inventorySkills.png", 136, 104);
	public static SpriteSheet invAbilityGreen = new SpriteSheet("/Textures/sheets/01_GUI/Menu/Inventory/AbilityGreen.png", 160, 160);

	public static SpriteSheet statPoints = new SpriteSheet("/Textures/sheets/01_GUI/Menu/Inventory/statPointsNew.png", 300, 168);
	public static SpriteSheet statPtFade = new SpriteSheet("/Textures/sheets/01_GUI/Menu/Inventory/BtnFade.png", 32, 32);
	public static SpriteSheet statPtFade2 = new SpriteSheet("/Textures/sheets/01_GUI/Menu/Inventory/BtnFade2.png", 32, 32);
	public static SpriteSheet statPtFade3 = new SpriteSheet("/Textures/sheets/01_GUI/Menu/Inventory/BtnFade3.png", 32, 32);

	public static SpriteSheet ChestInventory = new SpriteSheet("/Textures/sheets/01_GUI/Menu/Inventory/Chests/ChestInventory.png", 89, 89);

	public static SpriteSheet anim_Teleporter = new SpriteSheet(Teleporter, 0, 0, 1, 16, 32);
	
	public static SpriteSheet loc_shrine = new SpriteSheet("/Textures/sheets/03_WorldGen/Structure/loc_shrine.png", 32, 32);
	public static SpriteSheet shrine = new SpriteSheet("/Textures/sheets/03_WorldGen/Structure/loc_shrine.png", 32, 256);

	public static SpriteSheet blocks = new SpriteSheet("/Textures/sheets/03_WorldGen/blocks32.png", 1024);

	public static SpriteSheet blocks16x = new SpriteSheet("/Textures/sheets/03_WorldGen/blocks.png", 512);
	public static SpriteSheet Items = new SpriteSheet("/Textures/sheets/02_Items/Items.png", 256);

	public static SpriteSheet spells = new SpriteSheet("/Textures/sheets/04_Attacks/Projectiles.png", 256);
	public static SpriteSheet particles = new SpriteSheet("/Textures/sheets/04_Attacks/Particles.png", 256);
	public static SpriteSheet ability_NullBoss = new SpriteSheet("/Textures/sheets/04_Attacks/ability_NullBoss.png", 128, 192);
	public static SpriteSheet spells_Display = new SpriteSheet("/Textures/sheets/04_Attacks/ProjectileIcons.png", 256);

	//public static SpriteSheet inventory1 = new SpriteSheet("/Textures/sheets/00_MobSheets/inventorytab1.png", 128, 96);
	//public static SpriteSheet HealthPotion = new SpriteSheet("/Textures/sheets/00_MobSheets/HealthPotion.png", 8);

//	public static SpriteSheet water_anim = new SpriteSheet(blocks, 0, 6, 3, 1, 16);
	
	
	public static SpriteSheet title = new SpriteSheet("/Textures/sheets/01_GUI/Menu/menu_Main/Menu.png", 300);
	public static SpriteSheet robobob = new SpriteSheet("/Textures/sheets/00_MobSheets/robobob.png", 20);
	public static SpriteSheet robotbob = new SpriteSheet(robobob, 0, 0, 1, 1, 20);
	public static SpriteSheet bgFade = new SpriteSheet("/Textures/sheets/01_GUI/Menu/Universal/fade.png", 300, 168);

	/**
	 * Pause MENUS
	 */
	public static SpriteSheet buttonSet = new SpriteSheet("/Textures/sheets/01_GUI/Menu/Universal/buttonSet.png", 216, 216);

	public static SpriteSheet pauseMenu = new SpriteSheet("/Textures/sheets/01_GUI/Menu/menu_Pause/Pause.png", 300);
	public static SpriteSheet pauseMenuResume = new SpriteSheet("/Textures/sheets/01_GUI/Menu/menu_Pause/PauseResume.png", 300);
	public static SpriteSheet pauseMenuMenu = new SpriteSheet("/Textures/sheets/01_GUI/Menu/menu_Pause/PauseMenu.png", 300);
	public static SpriteSheet pauseMenuQuit = new SpriteSheet("/Textures/sheets/01_GUI/Menu/menu_Pause/PauseQuit.png", 300);
	
	public static SpriteSheet options = new SpriteSheet("/Textures/sheets/01_GUI/Menu/menu_Pause/Options.png", 300);

	public static SpriteSheet anim_Water = new SpriteSheet("/Textures/sheets/03_WorldGen/animWater.png", 16, 32);
	public static SpriteSheet Water1 = new SpriteSheet(anim_Water, 0, 0, 1, 2, 16);
	//public static SpriteSheet Water2 = new SpriteSheet(anim_Water, 1, 0, 1, 3, 16);

	public static SpriteSheet minimapDYN = new SpriteSheet("/overlays/levels/WorldMap.png", 1024);
	public static SpriteSheet minimap_hidden = new SpriteSheet("/Textures/sheets/01_GUI/Menu/Universal/MapClose.png", 45, 10);

	public static SpriteSheet player = new SpriteSheet("/Textures/sheets/00_MobSheets/Players/PlayerSprite.png", 512, 512);

	public static SpriteSheet mobHealth = new SpriteSheet("/Textures/sheets/01_GUI/HUD/MobHealth.png", 128, 704);
	
	public static SpriteSheet healthbar = new SpriteSheet("/Textures/sheets/01_GUI/HUD/HealthEx2.png", 72, 1952);
	public static SpriteSheet anim_hb = new SpriteSheet(healthbar, 0, 0, 1, 61, 72, 32);
	
	public static SpriteSheet manabar = new SpriteSheet("/Textures/sheets/01_GUI/HUD/ManaEx2.png", 72, 1952);
	public static SpriteSheet anim_mb = new SpriteSheet(manabar, 0, 0, 1, 61, 72, 32);

	public static SpriteSheet StaminaBar = new SpriteSheet("/Textures/sheets/01_GUI/HUD/StaminaEx2.png", 72, 1952);
	public static SpriteSheet anim_sb = new SpriteSheet(StaminaBar, 0, 0, 1, 61, 72, 32);

	public static SpriteSheet expbar = new SpriteSheet("/Textures/sheets/01_GUI/HUD/Exp.png", 156, 4832);
	public static SpriteSheet anim_eb = new SpriteSheet(expbar, 0, 0, 1, 151, 156, 32);

	public static SpriteSheet Zombie = new SpriteSheet("/Textures/sheets/00_MobSheets/Aggressive/Generic/zombie.png", 128, 96);
	public static SpriteSheet abilitybox = new SpriteSheet("/Textures/sheets/01_GUI/abilitybox.png", 20, 20);
	public static SpriteSheet abilitybox2 = new SpriteSheet("/Textures/sheets/01_GUI/Menu/Universal/Frame.png", 18, 18);
	
	public static SpriteSheet anim_abilityBoxBase = new SpriteSheet("/Textures/sheets/01_GUI/AbilityBoxAnim.png", 20, 340);
	public static SpriteSheet anim_abilityBox = new SpriteSheet(anim_abilityBoxBase, 0, 0, 1, 17, 20);
	
	public static SpriteSheet AbilityBarOpen = new SpriteSheet("/Textures/sheets/01_GUI/Menu/Abilities/AbilityBarOpen.png", 300, 300);
	public static SpriteSheet AbilityBarClosed = new SpriteSheet("/Textures/sheets/01_GUI/Menu/Abilities/AbilityBarClose.png", 32, 32);


	public static SpriteSheet player_down = new SpriteSheet(player, 0, 0, 1, 7, 64);
	public static SpriteSheet player_up = new SpriteSheet(player, 1, 0, 1, 7, 64);
	public static SpriteSheet player_left = new SpriteSheet(player, 3, 0, 1, 7, 64);
	public static SpriteSheet player_right = new SpriteSheet(player, 2, 0, 1, 7, 64);

	
	
	public static SpriteSheet Mace_SwooshBase = new SpriteSheet("/Textures/sheets/04_Attacks/MaceSwish.png", 32, 512);
	public static SpriteSheet Mace_Swoosh = new SpriteSheet(Mace_SwooshBase, 0, 0, 1, 16, 32);

	/*public static SpriteSheet CopperGuardian_Sword1 = new SpriteSheet(CopperGuardian, 0, 0, 2, 2, 90);
	public static SpriteSheet CopperGuardian_Sword2 = new SpriteSheet(CopperGuardian, 0, 0, 2, 2, 90);
	public static SpriteSheet CopperGuardian_Sword3 = new SpriteSheet(CopperGuardian, 0, 0, 2, 2, 90);*/
	public static SpriteSheet Water_Main = new SpriteSheet("/Textures/sheets/03_WorldGen/animWater.png", 32, 64);
	public static SpriteSheet water_anim = new SpriteSheet(Water_Main, 0, 0, 1, 1, 32);
	

	public static SpriteSheet zombie_down = new SpriteSheet(Zombie, 0, 0, 1, 3, 16);
	public static SpriteSheet zombie_up = new SpriteSheet(Zombie, 1, 0, 1, 3, 16);
	public static SpriteSheet zombie_left = new SpriteSheet(Zombie, 2, 0, 1, 3, 16);
	public static SpriteSheet zombie_right = new SpriteSheet(Zombie, 3, 0, 1, 3, 16);
	
	public static SpriteSheet zombie01 = new SpriteSheet("/Textures/sheets/00_MobSheets/Aggressive/Generic/zombie01.png", 128, 96);
	public static SpriteSheet zombie01_down = new SpriteSheet(zombie01, 0, 0, 1, 3, 16);
	public static SpriteSheet zombie01_up = new SpriteSheet(zombie01, 1, 0, 1, 3, 16);
	public static SpriteSheet zombie01_left = new SpriteSheet(zombie01, 2, 0, 1, 3, 16);
	public static SpriteSheet zombie01_right = new SpriteSheet(zombie01, 3, 0, 1, 3, 16);
	
	public static SpriteSheet zombie02 = new SpriteSheet("/Textures/sheets/00_MobSheets/Aggressive/Generic/zombie02.png", 128, 96);
	public static SpriteSheet zombie02_down = new SpriteSheet(zombie02, 0, 0, 1, 3, 16);
	public static SpriteSheet zombie02_up = new SpriteSheet(zombie02, 1, 0, 1, 3, 16);
	public static SpriteSheet zombie02_left = new SpriteSheet(zombie02, 2, 0, 1, 3, 16);
	public static SpriteSheet zombie02_right = new SpriteSheet(zombie02, 3, 0, 1, 3, 16);
	
	public static SpriteSheet player_rightstill = new SpriteSheet(player, 3, 4, 1, 1, 16);
	public static SpriteSheet player_leftstill = new SpriteSheet(player, 2, 4, 1, 1, 16);
	public static SpriteSheet player_upstill = new SpriteSheet(player, 1, 3, 1, 1, 16);
	public static SpriteSheet player_downstill = new SpriteSheet(player, 0, 3, 1, 1, 16);
	
	
	public static SpriteSheet VC = new SpriteSheet("/Textures/sheets/00_MobSheets/Minions/VoidCharger.png", 128, 96);
	public static SpriteSheet VC_down = new SpriteSheet(VC, 0, 0, 1, 6, 16);

	
	public static SpriteSheet Shop = new SpriteSheet("/Textures/sheets/03_WorldGen/Structure/Shop01.png", 32, 960);
	public static SpriteSheet Shop_1 = new SpriteSheet(Shop, 0, 0, 1, 30, 32);
	
	public static SpriteSheet Shop2 = new SpriteSheet("/Textures/sheets/03_WorldGen/Structure/Shop02.png", 32, 960);
	public static SpriteSheet Shop_2 = new SpriteSheet(Shop2, 0, 0, 1, 30, 32);
	
	public static SpriteSheet Shop3 = new SpriteSheet("/Textures/sheets/03_WorldGen/Structure/Shop03.png", 32, 960);
	public static SpriteSheet Shop_3 = new SpriteSheet(Shop3, 0, 0, 1, 30, 32);
	public static SpriteSheet Shop_Inn = new SpriteSheet("/Textures/sheets/03_WorldGen/Structure/inn.png", 32, 960);
	public static SpriteSheet Shop_inn = new SpriteSheet(Shop_Inn, 0, 0, 1, 30, 32);
	
	public static SpriteSheet Blacksmith = new SpriteSheet("/Textures/sheets/03_WorldGen/Structure/Blacksmith.png", 32, 960);
	public static SpriteSheet Blacksmith_Shop = new SpriteSheet(Blacksmith, 0, 0, 1, 30, 32);
	public static SpriteSheet Blacksmith_still = new SpriteSheet("/Textures/sheets/03_WorldGen/Structure/Blacksmith_still.png", 32, 960);
	public static SpriteSheet Blacksmith_Shop_still = new SpriteSheet(Blacksmith_still, 0, 0, 1, 30, 32);
	
	public static SpriteSheet Counter = new SpriteSheet("/Textures/sheets/03_WorldGen/Structure/Counter.png", 32, 16);
	
	public static SpriteSheet ArcaneBench = new SpriteSheet("/Textures/sheets/03_WorldGen/Structure/ArcaneBench.png", 32, 320);
	public static SpriteSheet ArcaneBench_1 = new SpriteSheet(ArcaneBench, 0, 0, 1, 10, 32);
	
	public static SpriteSheet Artificer = new SpriteSheet("/Textures/sheets/03_WorldGen/Structure/Artificer.png", 32, 960);
	public static SpriteSheet Artificer_Shop = new SpriteSheet(Artificer, 0, 0, 1, 30, 32);
	
	public static SpriteSheet Villager01 = new SpriteSheet("/Textures/sheets/00_MobSheets/Neutral/Villager01.png", 128, 96);
	public static SpriteSheet Villager01_down = new SpriteSheet(Villager01, 0, 0, 1, 3, 16);
	public static SpriteSheet Villager01_up = new SpriteSheet(Villager01, 1, 0, 1, 3, 16);
	public static SpriteSheet Villager01_left = new SpriteSheet(Villager01, 2, 0, 1, 3, 16);
	public static SpriteSheet Villager01_right = new SpriteSheet(Villager01, 3, 0, 1, 3, 16);
	
	public static SpriteSheet Villager02 = new SpriteSheet("/Textures/sheets/00_MobSheets/Neutral/Villager02.png", 128, 96);
	public static SpriteSheet Villager02_down = new SpriteSheet(Villager02, 0, 0, 1, 3, 16);
	public static SpriteSheet Villager02_up = new SpriteSheet(Villager02, 1, 0, 1, 3, 16);
	public static SpriteSheet Villager02_left = new SpriteSheet(Villager02, 2, 0, 1, 3, 16);
	public static SpriteSheet Villager02_right = new SpriteSheet(Villager02, 3, 0, 1, 3, 16);
	
	public static SpriteSheet Villager03 = new SpriteSheet("/Textures/sheets/00_MobSheets/Neutral/Villager03.png", 128, 96);
	public static SpriteSheet Villager03_down = new SpriteSheet(Villager03, 0, 0, 1, 3, 16);
	public static SpriteSheet Villager03_up = new SpriteSheet(Villager03, 1, 0, 1, 3, 16);
	public static SpriteSheet Villager03_left = new SpriteSheet(Villager03, 2, 0, 1, 3, 16);
	public static SpriteSheet Villager03_right = new SpriteSheet(Villager03, 3, 0, 1, 3, 16);
	
	public static SpriteSheet Barmaid = new SpriteSheet("/Textures/sheets/00_MobSheets/Neutral/Barmaid.png", 128, 96);
	public static SpriteSheet Barmaid_down = new SpriteSheet(Barmaid, 0, 0, 1, 3, 16);
	public static SpriteSheet Barmaid_up = new SpriteSheet(Barmaid, 1, 0, 1, 3, 16);
	public static SpriteSheet Barmaid_left = new SpriteSheet(Barmaid, 2, 0, 1, 3, 16);
	public static SpriteSheet Barmaid_right = new SpriteSheet(Barmaid, 3, 0, 1, 3, 16);
	
	public static SpriteSheet Alice = new SpriteSheet("/Textures/sheets/00_MobSheets/Neutral/Alice.png", 128, 96);
	public static SpriteSheet Alice_down = new SpriteSheet(Alice, 0, 0, 1, 3, 16);
	public static SpriteSheet Alice_up = new SpriteSheet(Alice, 1, 0, 1, 3, 16);
	public static SpriteSheet Alice_left = new SpriteSheet(Alice, 2, 0, 1, 3, 16);
	public static SpriteSheet Alice_right = new SpriteSheet(Alice, 3, 0, 1, 3, 16);
	
	public static SpriteSheet Guard = new SpriteSheet("/Textures/sheets/00_MobSheets/Neutral/Guard.png", 128, 96);
	public static SpriteSheet Guard_down = new SpriteSheet(Guard, 0, 0, 1, 3, 16);
	public static SpriteSheet Guard_up = new SpriteSheet(Guard, 1, 0, 1, 3, 16);
	public static SpriteSheet Guard_left = new SpriteSheet(Guard, 2, 0, 1, 5, 16);
	public static SpriteSheet Guard_right = new SpriteSheet(Guard, 3, 0, 1, 5, 16);
	
	public static SpriteSheet FrostSpirit = new SpriteSheet("/Textures/sheets/00_MobSheets/Aggressive/Frost/FrostSpirit.png", 128, 96);
	public static SpriteSheet FrostSpirit_down = new SpriteSheet(FrostSpirit, 0, 0, 1, 3, 16);
	
	
	public static SpriteSheet chests = new SpriteSheet(blocks, 3, 0, 1, 3, 16);
	
	public static SpriteSheet Equipment_Util = new SpriteSheet("/Textures/sheets/02_Items/Equipment/Equipment_Util.png", 256, 256);
	public static SpriteSheet Equipment_Main = new SpriteSheet("/Textures/sheets/02_Items/Equipment/Equipment_Main.png", 256, 256);
	public static SpriteSheet Equipment_Vanity = new SpriteSheet("/Textures/sheets/02_Items/Equipment/Equipment_Vainity.png", 256, 256);
	
	public static SpriteSheet Buttons = new SpriteSheet("/Textures/sheets/01_GUI/Menu/Universal/Buttons.png", 256, 256);
	
	
	private Sprite[] sprites;
	
	
	public SpriteSheet(SpriteSheet sheet, int x, int y, int width, int height, int spriteSize) {
		int xx = x * spriteSize;
		int yy = y * spriteSize;
		int w = width * spriteSize;
		int h = height * spriteSize;
		if (width == height) {
			SIZE = width;
		} else {
			SIZE = -1;
		}
		SPRITE_WIDTH = w;
		SPRITE_HEIGHT = h;
		pixels = new int[w * h];
		for (int y0 = 0; y0 < h; y0++) {
			int yp = yy + y0;
			for (int x0 = 0; x0 < w; x0++) {
			int xp = xx + x0;
				pixels[x0 + y0 * w] = sheet.pixels[xp + yp * sheet.SPRITE_WIDTH];
			}
		}
		int frame = 0;
		sprites = new Sprite[width * height];
		for (int ya = 0; ya < height; ya++) {
			for (int xa = 0; xa < width; xa++) {
				int[] spritePixels = new int[spriteSize * spriteSize];
				for (int y0 = 0; y0 < spriteSize; y0++) {
					for (int x0 = 0; x0 < spriteSize; x0++) {
						spritePixels[x0 + y0 * spriteSize] = pixels[(x0 + xa * spriteSize) + (y0 + ya * spriteSize) * SPRITE_WIDTH];
					}
				}
				Sprite sprite = new Sprite(spritePixels, spriteSize, spriteSize);
					sprites[frame++] = sprite;
			}
		}
	}
	
	
	public SpriteSheet(SpriteSheet sheet, int x, int y, int width, int height, int wid, int hei) {
		int xx = x * wid;
		int yy = y * hei;
		int w = width * wid;
		int h = height * hei;
		if (width == height) {
			SIZE = width;
		} else {
			SIZE = -1;
		}
		SPRITE_WIDTH = w;
		SPRITE_HEIGHT = h;
		pixels = new int[w * h];
		for (int y0 = 0; y0 < h; y0++) {
			int yp = yy + y0;
			for (int x0 = 0; x0 < w; x0++) {
			int xp = xx + x0;
				pixels[x0 + y0 * w] = sheet.pixels[xp + yp * sheet.SPRITE_WIDTH];
			}
		}
		int frame = 0;
		sprites = new Sprite[width * height];
		for (int ya = 0; ya < height; ya++) {
			for (int xa = 0; xa < width; xa++) {
				int[] spritePixels = new int[wid * hei];
				for (int y0 = 0; y0 < hei; y0++) {
					for (int x0 = 0; x0 < wid; x0++) {
						spritePixels[x0 + y0 * wid] = pixels[(x0 + xa * wid) + (y0 + ya * hei) * SPRITE_WIDTH];
					}
				}
				Sprite sprite = new Sprite(spritePixels, wid, hei);
					sprites[frame++] = sprite;
			}
		}
	}
	
	public SpriteSheet(String path, int size) {
	this.path = path;
			SIZE = size;
			SPRITE_WIDTH = size;
			SPRITE_HEIGHT = size;
			pixels = new int[SIZE * SIZE];
			load();
}

public SpriteSheet(String path, int width, int height) {
	this.path = path;
	SIZE = -1;
	SPRITE_WIDTH = width;
	SPRITE_HEIGHT = height;
	pixels = new int[SPRITE_WIDTH * SPRITE_HEIGHT];
	load();
}

public Sprite[] getSprites() {
	return sprites; 
	
}

public int getWidth() {
	return width;
}

public int getHeight() {
	return height;
}
public int[] getPixels() {
	return pixels;
}

private void load() {
	try {
		System.out.print("Attempting To Fetch SpriteSheet At: " + path + "...");
		BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
		System.out.println(" Succeeded!");
		 width = image.getWidth();
		 height = image.getHeight();
		 pixels = new int[width * height];
		image.getRGB (0, 0, width, height, pixels, 0, width);
	} catch (IOException e) {
		e.printStackTrace();
	} catch (Exception e) {
		System.err.println(" Failed!");
	}
	}
}
