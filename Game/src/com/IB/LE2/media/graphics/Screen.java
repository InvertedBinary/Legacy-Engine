package com.IB.LE2.media.graphics;

import java.util.List;

import com.IB.LE2.Boot;
import com.IB.LE2.input.hardware.Mouse;
import com.IB.LE2.util.Vector2i;
import com.IB.LE2.world.entity.Entity;
import com.IB.LE2.world.entity.mob.Player;
import com.IB.LE2.world.level.TileCoord;

public class Screen {

	public int width, height;
	public int[] pixels;
	
	public static int xOffset;
	public static int yOffset;
	public static int xo, yo;
	
	public final static int ALPHA_COL = 0xffFF00FF;
	public       static int BACKGROUND_COL = 0xff000000;

	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height]; // 50,400
	}

	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = BACKGROUND_COL;
		}
	}

	public void renderSheet(int xp, int yp, SpriteSheet sheet, boolean fixed) {
		if (fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}
		for (int y = 0; y < sheet.SPRITE_HEIGHT; y++) {
			int ya = y + yp;
			for (int x = 0; x < sheet.SPRITE_WIDTH; x++) {
				int xa = x + xp;
				if (xa < 0 || xa >= width || ya < 0 || ya >= height)
					continue;
				int color = sheet.pixels[x + y * sheet.SPRITE_WIDTH];
				if (color != ALPHA_COL) {
					pixels[xa + ya * width] = color;
				}
			}
		}
	}

	public void renderSprite(int xp, int yp, Sprite sprite, boolean fixed) {
		if (fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}
		for (int y = 0; y < sprite.getHeight(); y++) {
			double ya = y + yp;
			for (int x = 0; x < sprite.getWidth(); x++) {
				int xa = x + xp;
				if (xa < 0 || xa >= width || ya < 0 || ya >= height)
					continue;
				int color = sprite.pixels[x + y * sprite.getWidth()];
				if (color != ALPHA_COL) {
					pixels[(int) (xa + ya * width)] = (int) (color);// + (0x110000 * ((x - y + Math.round(Math.random()
																	// * 10)) / 2)));
				}
			}
		}
	}

	private int blendColors(int[] rgb1, int[] rgb2) {
		int newR = (rgb1[1] * rgb1[0] + rgb2[1] * (255 - rgb1[0])) / 255;
		int newG = (rgb1[2] * rgb1[0] + rgb2[2] * (255 - rgb1[0])) / 255;
		int newB = (rgb1[3] * rgb1[0] + rgb2[3] * (255 - rgb1[0])) / 255;

		return ((newR & 0xFF) << 16) | ((newG & 0xFF) << 8) | ((newB & 0xFF) << 0);
	}

	public boolean hasAlpha(int hex) {
		if (((hex >> 24) & 0xFF) < 255)
			return true;
		return false;
	}

	private int[] hexToRGB(int hex) {
		int[] rgb = new int[4];
		rgb[0] = (hex >> 24) & 0xFF; // alpha
		rgb[1] = (hex >> 16) & 0xFF; // red
		rgb[2] = (hex >> 8) & 0xFF; // green
		rgb[3] = (hex >> 0) & 0xFF; // blue

		return rgb;
	}

	public void DrawAlphaSprite(Sprite sprite, int xp, int yp) {
		DrawAlphaSprite(sprite, xp, yp, false);
	}

	public void DrawAlphaSprite(Sprite sprite, int xp, int yp, boolean fixed) {
		if (fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}
		for (int y = 0; y < sprite.getHeight(); y++) {
			int ya = y + yp;
			for (int x = 0; x < sprite.getWidth(); x++) {
				int xa = x + xp;
				if (xa < 0 || xa >= width || ya < 0 || ya >= height)
					continue;
				int color = sprite.pixels[x + y * sprite.getWidth()];
				if (color != ALPHA_COL) {
					if (hasAlpha(color)) {
						int[] rgb1 = hexToRGB(color); // Sprite color as int array
						int[] rgb2 = hexToRGB(pixels[xa + ya * width]); // pixel color as int array
						pixels[xa + ya * width] = blendColors(rgb1, rgb2);
					} else {
						pixels[xa + ya * width] = color;
					}
				}
			}
		}
	}

	public void DrawText(int xp, int yp, Sprite sprite, int color, boolean fixed) {
		if (fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}
		for (int y = 0; y < sprite.getHeight(); y++) {
			double ya = y + yp;
			for (int x = 0; x < sprite.getWidth(); x++) {
				int xa = x + xp;
				if (xa < 0 || xa >= width || ya < 0 || ya >= height)
					continue;
				int col = sprite.pixels[x + y * sprite.getWidth()];
				if (col != ALPHA_COL)
					pixels[(int) (xa + ya * width)] = color;

			}
		}
	}

	public void DrawTile(int xp, int yp, Sprite sprite) {
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < sprite.SIZE; y++) {
			int ya = y + yp;
			for (int x = 0; x < sprite.SIZE; x++) {
				int xa = x + xp;
				if (xa < -sprite.SIZE || xa >= width || ya < 0 || ya >= height)
					break;
				if (xa < 0)
					xa = 0;
				int color = sprite.pixels[x + y * sprite.SIZE];
				if (color != ALPHA_COL)
					pixels[xa + ya * width] = color;
			}
		}
	}

	public void DrawEntity(Entity e, int xp, int yp) {
		Sprite sprite = e.getSprite();
		
		int tilesx = xp;
		int tilesy = yp;
		xp -= xOffset;
		yp -= yOffset;
		for (int y = 0; y < sprite.getHeight(); y++) {
			int ya = y + yp;
			for (int x = 0; x < sprite.getWidth(); x++) {
				int xa = x + xp;
				if (xa < -sprite.getWidth() || xa >= width || ya < 0 || ya >= height)
					break;
				if (xa < 0)
					xa = 0;
				int col = sprite.pixels[x + y * sprite.getWidth()];
				if (col != ALPHA_COL) {
					if (e.hurt > 0) {
						col = 0xffFF0000;
					}
					col = colSwitch(col, tilesx, tilesy);

					pixels[(int) (xa + ya * width)] = col;
				}
			}
		}
	}

	public void DrawCircle(int xp, int yp, int radius, int color, boolean fixed) {
		if (fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}

		xp -= radius;
		yp -= radius;

		int col = 0;
		for (int y = 0; y < radius * 2; y++) {
			int ya = y + yp;
			for (int x = 0; x < radius * 2; x++) {
				int xa = x + xp;
				if (xa < 0 || xa >= this.width || ya < 0 || ya >= this.height)
					continue;
				int d = (int) Math.sqrt((y - radius) * (y - radius) + (x - radius) * (x - radius));
				if (d == radius - 1) {
					col = pixels[xa + ya * this.width];
					col = color;
					pixels[xa + ya * this.width] = col;
				}
			}
		}
	}

	public void DrawRect(int xp, int yp, int width, int height, int color, boolean fixed) {
		if (fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}
		for (int x = xp; x < xp + width; x++) {
			if (x < 0 | x >= this.width || yp >= this.height)
				continue;
			if (yp > 0)
				pixels[x + yp * this.width] = color;
			if (yp + height >= this.height)
				continue;
			if (yp + height > 0)
				pixels[x + (yp + height) * this.width] = color;

		}
		for (int y = yp; y <= yp + height; y++) {
			if (xp >= this.width || y < 0 || y >= this.height)
				continue;
			if (xp > 0)
				pixels[xp + y * this.width] = color;
			if (xp + width >= this.width)
				continue;
			if (xp + width > 0)
				pixels[(xp + width) + y * this.width] = color;

		}
	}

	public void DrawFillRect(int xp, int yp, int width, int height, int color, boolean fixed) {
		if (fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}
		for (int y = 0; y < height; y++) {
			int ya = y + yp;
			for (int x = 0; x < width; x++) {
				int xa = x + xp;
				if (xa < 0 || xa >= this.width || ya < 0 || ya >= this.height)
					continue;
				int col = color;
				pixels[xa + ya * this.width] = col;
			}
		}
	}

	public void DrawFillRect(int xp, int yp, int width, int height, int color, int border_color, boolean fixed) {
		if (fixed) {
			xp -= xOffset;
			yp -= yOffset;
		}
		for (int y = 0; y < height; y++) {
			int ya = y + yp;
			for (int x = 0; x < width; x++) {
				int xa = x + xp;
				if (xa < 0 || xa >= this.width || ya < 0 || ya >= this.height)
					continue;
				int col = color;

				if (x == 0 || y == 0 || x == width - 1 || y == height - 1)
					col = border_color;

				pixels[xa + ya * this.width] = col;
			}
		}
	}

	public void DrawVectors(List<Vector2i> list, int color, boolean fixed) {
		for (Vector2i vec : list) {
			int xPixel = vec.getX();
			int yPixel = vec.getY();
			if (fixed) {
				xPixel -= xOffset;
				yPixel -= yOffset;
			}
			if (xPixel < 0 || xPixel >= this.width || yPixel < 0 || yPixel >= this.height)
				continue;
			pixels[xPixel + yPixel * this.width] = color;
		}
	}

	public void setOffset(int xOffset, int yOffset) {
		Screen.xOffset = xOffset;
		Screen.yOffset = yOffset;
		xo = (Mouse.getX() / Boot.scale + xOffset) / TileCoord.TILE_SIZE;
		yo = (Mouse.getY() / Boot.scale + yOffset) / TileCoord.TILE_SIZE;
	}

	public int colSwitch(int col, int tilesx, int tilesy) {
		return col;
	}

	@Deprecated // TODO: rewrite getEntities in level
	public void drawLine(Player player, List<Entity> entities) {
		int xp = (int) player.x();
		int yp = (int) player.y();
		xp -= xOffset;
		yp -= yOffset;
		int col = 0;
		// entities = Boot.get().getLevel().getEntities(player, 20);
		for (int i = 0; i < entities.size(); i++) {
			for (int y = (int) player.y(); y < entities.get(i).y(); y++) {
				int ya = y + yp;
				for (int x = (int) player.x(); x < entities.get(i).x(); x++) {
					int xa = x + xp;
					if (xa < 0 || xa >= this.width || ya < 0 || ya >= this.height)
						continue;
					col = pixels[xa + ya * this.width];
					pixels[xa + ya * this.width] = col;
				}
			}

		}
	}
}
