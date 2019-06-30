package com.IB.LE2.media.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class SpriteSheet {

	private String path;
	public final int SIZE;
	public final int SPRITE_WIDTH, SPRITE_HEIGHT;
	private int width, height;
	public int[] pixels;
	
	public static HashMap<String, SpriteSheet> sheets = new HashMap<>();
	
	public static SpriteSheet get(String s) {
		SpriteSheet result = null;
		if (sheets.containsKey(s)) {
			result = sheets.get(s);
		}
		return result;
	}
	
	public static void put(String name, SpriteSheet sheet) {
		sheets.put(name, sheet);
	}
	
	public static void LoadTags(String path) {
		System.out.println("Loading Sprite Sheets.. " + path);
		
		put("TitleMenu", title);
		put("BGFade", bgFade);
		put("Terrain", blocks);
		put("AbilityBox", abilitybox);
		put("player", player);
		
		
	}
	
	public static SpriteSheet title = new SpriteSheet("/Textures/sheets/01_GUI/Menu/menu_Main/Menu.png", 300);
	public static SpriteSheet bgFade = new SpriteSheet("/Textures/sheets/01_GUI/Menu/Universal/fade.png", 300, 168);

	public static SpriteSheet blocks = new SpriteSheet("/Textures/sheets/03_WorldGen/blocks32.png", 1024);
	public static SpriteSheet abilitybox = new SpriteSheet("/Textures/sheets/01_GUI/abilitybox.png", 20, 20);

	public static SpriteSheet player = new SpriteSheet("/Textures/sheets/00_MobSheets/Players/PlayerSprite.png", 512, 512);
	public static SpriteSheet player_down = new SpriteSheet(player, 0, 0, 1, 7, 64);
	public static SpriteSheet player_up = new SpriteSheet(player, 1, 0, 1, 7, 64);
	public static SpriteSheet player_left = new SpriteSheet(player, 3, 0, 1, 7, 64);
	public static SpriteSheet player_right = new SpriteSheet(player, 2, 0, 1, 7, 64);
	
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
	
	public SpriteSheet(String path) {
		this.path = path;
		this.SIZE = -1;
		
		this.SPRITE_WIDTH = -1;
		this.SPRITE_HEIGHT = -1;
		
		this.pixels = new int[SPRITE_WIDTH * SPRITE_HEIGHT];
		load();
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
			image.getRGB(0, 0, width, height, pixels, 0, width);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println(" Failed!");
		}
	}
}
