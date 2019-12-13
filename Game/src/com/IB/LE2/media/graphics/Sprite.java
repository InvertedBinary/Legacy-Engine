package com.IB.LE2.media.graphics;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.IB.LE2.Boot;
import com.IB.LE2.world.level.TileCoord;


public class Sprite {
	
	public final int SIZE;
	private int x, y;
	private int width, height;
	public int[] pixels;
	public SpriteSheet sheet;
	
	public static HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();
	public static ArrayList<AnimatedSprite> animated_sprites = new ArrayList<>();
	public static final Sprite NULL_SPRITE = new Sprite(TileCoord.TILE_SIZE, 0xffFFFFFF);

	public static Sprite get(String s) {
		Sprite result = Sprite.NULL_SPRITE;
		if (sprites.containsKey(s)) {
			result = sprites.get(s);
		} else {
			System.out.println("Failed to get sprite " + s + " is it loaded..?");
		}
		return result;
	}
	
	public static AnimatedSprite getNewAnim(String s) {
		AnimatedSprite template = (AnimatedSprite) get(s);
		AnimatedSprite newSpr;
		
		newSpr = new AnimatedSprite(template.sheet, template.getWidth(), template.getHeight(), template.getLength());
		
		return newSpr;
	}
	
	public static void put(String name, Sprite spr) {
		sprites.put(name, spr);
	}
	
	public static void LoadTags(String path) {
		try {
			DocumentBuilderFactory dbFac = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFac.newDocumentBuilder();
			Document doc = dBuilder.parse(path);
			doc.getDocumentElement().normalize();
			BuildSprite(doc);
		} catch (FileNotFoundException e) {
			Boot.log("Sprite definitions not found for this asset pack.", "Sprite", true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void BuildSprite(Document doc) {
		NodeList nList = doc.getElementsByTagName("Sprites");
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node nNode = nList.item(temp);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				for (int i = 0; i < (nNode.getChildNodes().getLength()); i++) {
					try {
						Node node = nNode.getChildNodes().item(i);
						if (node.getNodeType() == Node.ELEMENT_NODE) {
							Element e = (Element) node;
							String name = e.getTextContent();
							Sprite s = NULL_SPRITE;
							
							String noden = e.getNodeName();
							if (noden.equalsIgnoreCase("anim")) {
								SpriteSheet sheet = SpriteSheet.get(e.getAttribute("sheet"));
								int width = Integer.parseInt(e.getAttribute("w"));
								int height = Integer.parseInt(e.getAttribute("h"));
								int length = Integer.parseInt(e.getAttribute("len"));
								int rate = Integer.parseInt(e.getAttribute("rate"));
								s = new AnimatedSprite(sheet, width, height, length);
								((AnimatedSprite)s).setFrameRate(rate);
							} else if (noden.equalsIgnoreCase("color")) {
								int width = Integer.parseInt(e.getAttribute("w"));
								int height = Integer.parseInt(e.getAttribute("h"));
								int color = Long.decode(e.getAttribute("color")).intValue();
								s = new Sprite(width, height, color);
							} else if (noden.equalsIgnoreCase("square")){
								SpriteSheet sheet = SpriteSheet.get(e.getAttribute("sheet"));
								int x = Integer.parseInt(e.getAttribute("x"));
								int y = Integer.parseInt(e.getAttribute("y"));
								int size = Integer.parseInt(e.getAttribute("s"));
								s = new Sprite(size, x, y, sheet);
							} else {
								SpriteSheet sheet = SpriteSheet.get(e.getAttribute("sheet"));
								int x = Integer.parseInt(e.getAttribute("x"));
								int y = Integer.parseInt(e.getAttribute("y"));
								int width = Integer.parseInt(e.getAttribute("w"));
								int height = Integer.parseInt(e.getAttribute("h"));
								s = new Sprite(width, height, x, y, sheet);
							}
							
							put(name, s);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	protected Sprite(SpriteSheet sheet, int width, int height) {
		SIZE = (width == height) ? width : -1;
		this.width = width;
		this.height = height;
		this.sheet = sheet;
		
	}

	public Sprite(int size, int x, int y, SpriteSheet sheet) {
		SIZE = size;
		this.width = size;
		this.height = size;
		pixels = new int[SIZE * SIZE];
		this.x = x * size;
		this.y = y * size;
		this.sheet = sheet;
		load();
	}
	
	public Sprite(int width, int height, int x, int y, SpriteSheet sheet) {
		SIZE = (width == height) ? width : -1;
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		this.x = x;
		this.y = y;
		this.sheet = sheet;
		load();
	}
	
	
	
	 /*  public static Sprite rotate(Sprite sprite) {
		   
		      int width = (int) (sprite.width);
		      int height = (int) (sprite.height);
		      int[] pixels = new int[width * height];
		      for (int y = 0; y < height; y++){
		         for (int x = 0; x < width; x++){
		        		 pixels[x + y * width] = sprite.pixels[(int) y + x * sprite.width];
		        	 }
		      }
		      Sprite rSprite = new Sprite(pixels, width, height);
		      
		      return rSprite;
		   }
	   
	   public static Sprite rotate(Sprite sprite, int degrees) {
		   
		return null;
	   }*/
	   
	public Sprite(int width, int height, int color) {
		SIZE = -1;
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		setColor(color);
	}
	
	public Sprite(int size, int color) {
		SIZE = size;
		this.width = size;
		this.height = size;
		pixels = new int [SIZE*SIZE];
		setColor(color);
	}
	
	
	public Sprite(int[] pixels, int width, int height) {
		SIZE = (width == height) ? width : -1;
		this.width = width;
		this.height = height;
		this.pixels = new int[pixels.length];
		//System.arraycopy(pixels, 0, this.pixels, 0, pixels.length);
		for (int i = 0; i < pixels.length; i++) {
			this.pixels[i] = pixels[i];
		}
	}

	public static Sprite resize(Sprite sprite, double mag) {
		int[] pixels = new int[(int) (sprite.pixels.length * mag * mag)];
		int width = (int) (sprite.width * mag);
		int height = (int) (sprite.height * mag);
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				pixels[x + y * width] = sprite.pixels[(int) (x / mag + Math.floor(y / mag) * sprite.width)];
			}
		}

		Sprite nSprite = new Sprite(pixels, width, height);

		return nSprite;
	}	   
	
	public static Sprite rotate(Sprite sprite, double angle) {
		return new Sprite(rotate(sprite.pixels, sprite.width, sprite.height, angle), sprite.width, sprite.height);
	}
	
	
	private static int[] rotate(int[] pixels, int width, int height, double angle) {
		int[] result = new int[width * height];
		
		double nx_x = rot_x(-angle, 1.0, 0.0);
		double nx_y = rot_y(-angle, 1.0, 0.0);
		double ny_x = rot_x(-angle, 0.0, 1.0);
		double ny_y = rot_y(-angle, 0.0, 1.0);
		
		double x0 = rot_x(-angle, -width / 2.0, -height / 2.0) + width / 2.0;
		double y0 = rot_y(-angle, -width / 2.0, -height / 2.0) + height / 2.0;
		
		for (int y = 0; y < height; y++) {
			double x1 = x0;
			double y1 = y0;
			for (int x = 0; x < width; x++) {
				int xx = (int) x1;
				int yy = (int) y1;
				
				int col = 0;
				if (xx < 0 || xx >= width || yy < 0 || yy >= height) col = Screen.ALPHA_COL /*0xff00CFCB*/;
				else col = pixels[xx + yy * width];
				result[x + y * width] = col;
				
				x1 += nx_x;
				y1 += nx_y;
				
			}
			
			x0 += ny_x;
			y0 += ny_y;
			
		}

		
		return result;
	}
	
	
	private static double rot_x(double angle, double x, double y) {
		double cos = Math.cos(angle - Math.PI / 2);
		double sin = Math.sin(angle - Math.PI / 2);
		return x * cos + y * -sin;
	}
	
	private static double rot_y(double angle, double x, double y) {
		double cos = Math.cos(angle - Math.PI / 2);
		double sin = Math.sin(angle - Math.PI / 2);
		return x * sin + y * cos;
	}
	   
	public static Sprite[] split(SpriteSheet sheet) {
		int amount = (sheet.getWidth() * sheet.getHeight()) / sheet.SPRITE_WIDTH * sheet.SPRITE_HEIGHT;
		Sprite[] sprites = new Sprite[amount];
		int current = 0;
		int[] pixels = new int[sheet.SPRITE_WIDTH * sheet.SPRITE_HEIGHT];
		for (int yp = 0; yp < sheet.getHeight() / sheet.SPRITE_HEIGHT; yp++) {
			for (int xp = 0; xp < sheet.getWidth() / sheet.SPRITE_WIDTH; xp++) {
				
				for (int y = 0; y < sheet.SPRITE_HEIGHT; y++) {
					for (int x = 0; x < sheet.SPRITE_WIDTH; x++) {
						int xo = x + xp * sheet.SPRITE_WIDTH;
						int yo = y + yp * sheet.SPRITE_HEIGHT;
						pixels[x + y * sheet.SPRITE_WIDTH] = sheet.getPixels()[xo + yo * sheet.getWidth()];
					} 
				}
				
				sprites[current++] = new Sprite(pixels, sheet.SPRITE_WIDTH, sheet.SPRITE_HEIGHT);
			}
		}
		return sprites;
	}
	
	public static Sprite Slim(Sprite sprite) {
		int[] p = sprite.pixels;
		int lx = -1;
		int rx = -1;
		for (int y = 0; y < sprite.getHeight(); y++) {
			for (int x = 0; x < sprite.getWidth(); x++) {
				int pixel = p[x + y * sprite.getWidth()];
				if (pixel == Screen.ALPHA_COL) continue;
					else if (x > lx) lx = x;
				
			}
		}
		
		return new Sprite(p, sprite.width, sprite.height);
	}
	
	public Sprite(Sprite s, Sprite s2) {
		SIZE = s.SIZE;
		if (s.SIZE != s2.SIZE) {
			for (int i = 0; i < height * width; i++) {
				pixels[i] = 0;
			}
		} else {
			this.width = s.width;
			this.height = s.height;
			pixels = new int[SIZE * SIZE];
			this.x = x * SIZE;
			this.y = y * SIZE;
			this.sheet = s.sheet;
			
			for (int i = 0; i < height * width; i++) {
				if (s2.pixels[i] != Screen.ALPHA_COL) {
					pixels[i] = s2.pixels[i];
				} else {
					pixels[i] = s.pixels[i];
				}
			} 
		}
	}
	
	
	private void setColor(int color) {
		for (int i = 0; i < height * width; i++) {
			pixels[i] = color;
		}
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
			
	private void load() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++)
				pixels[x + y * width] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.SPRITE_WIDTH];
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setPosition(int x, int y) {
		this.x = x;
		this.y = y;
	}
}