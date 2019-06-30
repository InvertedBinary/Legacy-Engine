package com.IB.LE2.world.entity.mob;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.imageio.ImageIO;

import com.IB.LE2.Boot;
import com.IB.LE2.Game;
import com.IB.LE2.input.UI.GUI;
import com.IB.LE2.input.hardware.Keyboard;
import com.IB.LE2.input.hardware.Mouse;
import com.IB.LE2.media.graphics.AnimatedSprite;
import com.IB.LE2.media.graphics.Screen;
import com.IB.LE2.media.graphics.Sprite;
import com.IB.LE2.media.graphics.SpriteSheet;
import com.IB.LE2.util.Debug;
import com.IB.LE2.util.VARS;
import com.IB.LE2.util.Vector2i;
import com.IB.LE2.util.math.PVector;
import com.IB.LE2.util.shape.Rectangle;
import com.IB.LE2.world.entity.projectile.Projectile;
import com.IB.LE2.world.entity.projectile.Selector;
import com.IB.LE2.world.entity.projectile.XML_Projectile;
import com.IB.LE2.world.level.Level;
import com.IB.LE2.world.level.Node;
import com.IB.LE2.world.level.RayCast;
import com.IB.LE2.world.level.TileCoord;
import com.IB.LE2.world.level.tile.Tile;
import com.IB.LE2.world.level.worlds.TiledLevel;

public class Player extends Mob implements Serializable {
	private transient static final long serialVersionUID = -8911018741301426797L;
	
	public transient Keyboard input;
	public transient Tile tile;
	public boolean buildMode = false;

	// private transient transient Inventory inventory;
	public transient AnimatedSprite down = new AnimatedSprite(SpriteSheet.player_down, 64, 64, 7);
	public transient AnimatedSprite idle = new AnimatedSprite(SpriteSheet.player_up, 64, 64, 7);
	public transient AnimatedSprite left = new AnimatedSprite(SpriteSheet.player_left, 64, 64, 7);
	public transient AnimatedSprite right = new AnimatedSprite(SpriteSheet.player_right, 64, 64, 7);

	// private transient AnimatedSprite player_upstill = new
	// AnimatedSprite(SpriteSheet.player_upstill, 16, 16, 1);
	// private transient AnimatedSprite player_downstill = new
	// AnimatedSprite(SpriteSheet.player_downstill, 16, 16, 1);
	// private transient AnimatedSprite player_leftstill = new
	// AnimatedSprite(SpriteSheet.player_leftstill, 16, 16, 1);
	// private transient AnimatedSprite player_rightstill = new
	// AnimatedSprite(SpriteSheet.player_rightstill, 16, 16, 1);

	public transient AnimatedSprite animSprite = down;

	public transient int fireRate = 1;

	private transient double time = 0;
	public transient boolean sprinting = false;
	public transient boolean swimming = false;
	public transient GUI gui;
	private int tileX;
	private int tileY;
	public boolean noclip = false;
	transient private List<Node> path = null;
	transient double Pathtime = 0;
	transient RayCast raycastDIR;
	private transient int dirInt = 0;
	public int currentLevelId = 0;

	public int cam_xOff = 0;
	public int cam_yOff = 0;

	// TODO: Generate UUID and send instead of USErname

	public Player(double x, double y, Keyboard input, String username)
	{
		this.setX(x);
		this.setY(y);
		this.name = username;
		this.input = input;
		init();
	}

	public void init()
		{
			this.speed = 2;
			this.xBound = 8;
			this.yBound = 8;
			this.xOffset = -16;
			this.yOffset = -16;

			gui = new GUI();

			body.bounds = new Rectangle((float) x(), (float) y(), 32, 64);
			body.set(VARS.PHYS_NOGRAV, true);
			body.bounds.set(VARS.REND_LOCALLY, true);

			System.out.println("ADDING NEW PLAYER: " + this.x() + "," + this.y());
		}

	public void added() {
		if (Boot.isConnected) {
			if (this.isClientPlayer()) {
				Boot.Client.sendMessage(Level.entityStringBuilder(Boot.get().getPlayer()));
			}
		}
	}

	public boolean remove() {
		if (Boot.isConnected) {
			if (this.isClientPlayer()) {
				Boot.Client.sendMessage("REM|id=" + UUID);
			}
		}
		return super.remove();
	}

	public void MouseClicked(int btn) {
		switch (btn) {
		case Mouse.LEFT_CLICK:
			updateShooting();			
			break;
		case Mouse.RIGHT_CLICK:
			break;
		case Mouse.MIDDLE_CLICK:
			break;
		}
	}
	
	PVector pv = null;
	public void update() {
			/*
			 * try {
			 * 
			 * if (input.save){ invokeSave(this); input.save = false; } else if
			 * (input.load){ if (input.Sprint) {
			 * Boot.get().getLevel().loadMobs(Game.currentLevelId); } else {
			 * invokeLoad(this); } input.load = false; }
			 * 
			 * } catch (Exception e) {
			 * 
			 * }
			 */

			raycastDIR = level.RayCast(new Vector2i(x(), y()), dirInt, (int) 3);

			Pathtime++;
			tileX = (int) x() >> VARS.TILE_BIT_SHIFT;
			tileY = (int) y() >> VARS.TILE_BIT_SHIFT;
			try {
				if (level.getTile(tileX, tileY).exit()) {
					level.checkExits(this, level, (int) x(), (int) y());
				}
			} catch (Exception e) {

			}

			time++;
			animSprite.update();

			if (!walking) {
				animSprite = idle;
				this.animSprite.setFrameRate(8);
			} else {
				this.animSprite.setFrameRate(6 - (int) this.speed / 2);
			}

			// if (abilityCooldown > 0) abilityCooldown--;
			if (fireRate > 0) fireRate--;

			if (!moving) {
				cam_xOff = 0;
				cam_yOff = 0;
			}

			double xa = 0;
			double ya = 0;
			double yv = 0;

			if (pv == null) {
				pv = new PVector(vel());
			}

			if (input != null) {
				this.buildMode = input.buildMode;

				if (input.Sprint && walking) { // 300
					speed = 4;
					sprinting = true;
				} else {
					speed = 2;
				}

				if (!input.Sprint) {
					sprinting = false;
				}

				// if (inventoryOn == false) {
				if (this == level.getClientPlayer()) {
					if (input.up) {
						// animSprite = up;
						yv -= speed;
					} else
						if (input.down) {
							// animSprite = down;
							yv += speed;
						}
					if (input.left) {
						animSprite = left;
						this.vel().x(-speed);
					} else
						if (input.right) {
						animSprite = right;
						this.vel().x(speed);
						}

					if ((this.input.jump || this.input.up) && this.canJump && !this.sliding) {
						this.vel().y(-6.5);
					}
					
					if (Mouse.getButton() == 1 && walking) {
						if (Screen.xo << VARS.TILE_BIT_SHIFT > this.x()) {
							this.animSprite = right;
						} else {
							this.animSprite = left;
						}
					}
				}
			} else { 
				walking = false;
				if (vel().x() > 0) {
					animSprite = right;
					walking = true;
				} else
					if (vel().x() < 0) {
						animSprite = left;
						walking = true;
					}
			}
			// System.out.println("POS: " + pos.toString() + " VEL: " + vel.toString());
			// body.set(VARS.PHYS_NOGRAV, false);
			// if (!body.bounds.intersects(Boot.get().pb.bounds)) {
			// body.move();
			// }

			// this.pv.add(Gravity);

			if (this.isClientPlayer()) {
				PVector Gravity = new PVector();
				Gravity.y(VARS.Ag);

				this.vel().add(Gravity);

				if (Boot.isConnected) {
					if ((vel().x() != pv.x()) || (vel().y() != pv.y())) {
						Boot.Client.sendMessage("VEL|id=" + this.UUID + "@x=" + this.vel().x() + ",y=" + this.vel().y());
						Boot.Client.sendMessage("POS|id=" + this.UUID + "@x=" + this.pos().x() + ",y=" + this.pos().y());
						pv.set(vel());
					}
				}

				// this.pv.add(Gravity);


				ya = vel().y();
				xa = vel().x();

				if (xa != 0 || (ya != 0 && ya != Gravity.y())) {
					Game.DiscordPlayerPosPresence();
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

				// this.pv.y(vel().y());

			} else {
				ya = vel().y();
				xa = vel().x();
				move(xa, ya);
			}

			this.vel().x(0);
			this.pv.x(0);

			clear();

			if (VARS.do_possession && Selector.selected != null)
			{
				Selector.selected.pos().set((Mouse.getX() / Boot.scale + Screen.xOffset) + 0,
						(Mouse.getY() / Boot.scale + Screen.yOffset) - Selector.selected.sprite.getHeight());
			}
		}

	public boolean isClientPlayer() {
		return this.equals(Boot.get().getPlayer());
	}

	private Selector selection_tool;
	public void updateShooting()
		{
			if (Mouse.getButton() == 2) {
				selection_tool = new Selector((Mouse.getX() / Boot.scale + Screen.xOffset) + 0,
						(Mouse.getY() / Boot.scale + Screen.yOffset) + 0);
				level.add(selection_tool);
				selection_tool.update();
			}

			if (Mouse.getButton() == 1) {
				XML_Projectile Test_Arrow = new XML_Projectile((x()) + 32, y() + 32, "/Tags/Projectiles/Arrow.xml",
						this);
				// XML_Projectile Test_Arrow2 = new XML_Projectile(x(), y(), Projectile.angle()
				// + (Math.PI / 2), "/XML/Projectiles/Arrow.xml", this);
				// Test_Arrow2.sprite = Sprite.WizardProjectile2;
				// Test_Arrow.nx += vel().x();
				// Test_Arrow.ny += vel().y();
				level.add(Test_Arrow);

				// level.add(Test_Arrow2);
			}

		}

	private void clear()
		{
			for (int i = 0; i < level.getProjectiles().size(); i++) {
				Projectile p = level.getProjectiles().get(i);
				if (p.isRemoved()) level.getProjectiles().remove(i);
			}
		}

	public void setPosition(TileCoord tileCoord)
		{
			this.setX(tileCoord.x());
			this.setY(tileCoord.y());
		}

	public void setPositionTiled(double x, double y, String XML, boolean tileMult)
		{
			System.out.println("Got request to load a Tiled level.");
			if (tileMult) {
				x *= TileCoord.TILE_SIZE;
				y *= TileCoord.TILE_SIZE;
			}
			this.currentLevelId = -1;

			((TiledLevel) Boot.get().getLevel()).killLua();
			
			TiledLevel newLevel = new TiledLevel(XML);
			Boot.get().setLevel(newLevel);
			Boot.get().getLevel().add(this);

			if (x == -1 && y == -1) {
				x = newLevel.spawnpoint.x() / 32;
				y = newLevel.spawnpoint.y() / 32;
			}

			this.removed = false;
			this.setX((x));
			this.setY((y));
			// newLevel.initLua();

			Game.DiscordPlayerPosPresence();
		}

	public String getUsername() {
		return this.getName();
	}

	private transient Sprite arrow = Sprite.get("Grass");

	public void render(Screen screen)
		{

			sprite = animSprite.getSprite();

			this.render_xOffset = 0;
			this.render_yOffset = 0;
			this.yOffset = 0;
			this.xOffset = 16;
			screen.renderMobSpriteUniversal(
					(int) (x() + render_xOffset + cam_xOff),
					(int) (y() + render_yOffset + cam_yOff), 
					sprite);

		}

	public int roundTo(int number, int multiple)
		{
			if (multiple == 0) {
				return number;
			}

			int remainder = number % multiple;
			if (remainder == 0) {
				return number;
			}

			return number + multiple - remainder;
		}

	public static Image getImageFromArray(int[] pixels, int width, int height)
		{
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			WritableRaster raster = (WritableRaster) image.getData();
			raster.setPixels(0, 0, width, height, pixels);
			return image;
		}

	public void saveLevel(int[] tiles, String path)
		{
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

	public void renderGUI(Screen screen)
		{
			if (Boot.drawDebug) {
				if (this.feetLine != null) {
					this.feetLine.drawLine(screen, true);
				}

				Debug.drawRect(screen, (int) x() + render_xOffset, (int) y() + render_yOffset, sprite.getWidth(),
						sprite.getHeight(), 0xffFADE0F, true);
				Debug.drawRect(screen, (int) x() + xOffset, (int) y() + yOffset, entWidth, entHeight, 0xff00FFFF, true);
			}

			if (Level.minimap_collapsed) {
				//screen.renderSheet(254, 0, SpriteSheet.minimap_hidden, false);
			}

			if (buildMode) {
				gui.renderBuild(screen, this);
			}

			String text = (int) Boot.get().getPlayer().x() / TileCoord.TILE_SIZE + ","
					+ (int) Boot.get().getPlayer().y() / TileCoord.TILE_SIZE;
			// screen.renderSprite(1064/ Game.scale, 530 / Game.scale,
			// gui.renderHealthExperiment(screen, this, 20), false);
			if (!level.minimap_enabled) {
				Game.font16bit.render((int) Boot.width - text.length() * 16, 3, -3, text, screen, false, false);
				Game.font16bit.render((int) Boot.width - text.length() * 16 + 1, 3, -3, 0xffFFFFFF, text, screen,
						false, false);
			} else
				if (!level.minimap_collapsed) {
					// screen.renderSprite(275 - text.length() * 8, 1, new Sprite(50, 12,
					// 0xff262626), false);
					Game.font16bit.render((int) Boot.width - 35 - text.length() * 16, 3, -3, text, screen, false,
							false);
					Game.font16bit.render((int) Boot.width - 35 - text.length() * 16 + 1, 3, -3, 0xffFFFFFF, text,
							screen, false, false);
				} else {
					Game.font16bit.render((int) Boot.width - text.length() * 16, 3, -3, text, screen, false, false);
					Game.font16bit.render((int) Boot.width - text.length() * 16 + 1, 3, -3, 0xffFFFFFF, text, screen,
							false, false);
				}

			if (Game.devModeOn) {
				// screen.drawRect((int) x() - 8, (int) y() - 15, 64, 64, 0x0093FF, true);
				body.draw(screen);
				// this.pos().draw(screen);
				// this.vel().draw(screen);
				try {
					// Boot.get().getScreen().drawVectors(Boot.get().getLevel().BresenhamLine((int)
					// x(), (int) y(),
					// raycastDIR.rayVector.x, raycastDIR.rayVector.y), 0xffFF3AFB, true);
				} catch (NullPointerException e) {
				}
			}
		}
}
