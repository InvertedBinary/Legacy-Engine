package com.IB.LE2.media.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
		try {
			InputStream fXmlFile = SpriteSheet.class.getResourceAsStream(path);
			DocumentBuilderFactory dbFac = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFac.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			BuildSheet(doc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void BuildSheet(Document doc) {
		NodeList nList = doc.getElementsByTagName("Sheets");
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				NodeList sheets = ((Element) nNode).getElementsByTagName("sheet");
				for (int i = 0; i < (sheets.getLength()); i++) {
					try {
						Element e = (Element) sheets.item(i);
						String path = e.getAttribute("src");
						int width = Integer.parseInt(e.getAttribute("w"));
						int height = Integer.parseInt(e.getAttribute("h"));
						SpriteSheet s = new SpriteSheet(path, width, height);
						
						if (e.hasChildNodes()) {
							for (int j = 0; j < e.getChildNodes().getLength(); j++) {
								Node child = e.getChildNodes().item(j);
								if (child.getNodeType() == Node.ELEMENT_NODE) {
									Element c = (Element) child;
									System.out.println(" - Building subsheet.." + child.getTextContent());
									
									int sx = Integer.parseInt(c.getAttribute("x"));
									int sy = Integer.parseInt(c.getAttribute("y"));
									int sw = Integer.parseInt(c.getAttribute("w"));
									int sh = Integer.parseInt(c.getAttribute("h"));
									
									int frameW, frameH;
									if (c.hasAttribute("size")) {
										int size = Integer.parseInt(c.getAttribute("size"));
										frameW = size; frameH = size;
									} else {
										frameW = Integer.parseInt(c.getAttribute("frame-width"));
										frameH = Integer.parseInt(c.getAttribute("frame-height"));
									}
									
									String name = c.getTextContent();
									SpriteSheet subsheet = new SpriteSheet(s, sx, sy, sw, sh, frameW, frameH);
									put(name, subsheet);
								} else {
									String name = e.getTextContent();
									put(name, s);
								}
							}
							
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
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
