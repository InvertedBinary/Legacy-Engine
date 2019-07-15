package com.IB.LE2.world.entity.mob;

import java.io.Serializable;

import com.IB.LE2.Boot;
import com.IB.LE2.Game;
import com.IB.LE2.input.UI.GUI;
import com.IB.LE2.input.UI.UI_Manager;
import com.IB.LE2.input.UI.components.UI_Sprite;
import com.IB.LE2.input.UI.menu.TagMenu;
import com.IB.LE2.input.hardware.Keyboard;
import com.IB.LE2.input.hardware.Mouse;
import com.IB.LE2.media.graphics.AnimatedSprite;
import com.IB.LE2.media.graphics.Screen;
import com.IB.LE2.media.graphics.Sprite;
import com.IB.LE2.util.Debug;
import com.IB.LE2.util.VARS;
import com.IB.LE2.util.math.PVector;
import com.IB.LE2.util.shape.Rectangle;
import com.IB.LE2.world.entity.projectile.Selector;
import com.IB.LE2.world.entity.projectile.TagProjectile;
import com.IB.LE2.world.level.TileCoord;
import com.IB.LE2.world.level.tile.Tile;
import com.IB.LE2.world.level.worlds.TiledLevel;

public class Player extends Mob implements Serializable {
	private transient static final long serialVersionUID = -8911018741301426797L;

	public transient Keyboard input;
	public transient Tile tile;

	public transient AnimatedSprite
			idle  = (AnimatedSprite) Sprite.getNewAnim("PlayerIdle"),
			down  = (AnimatedSprite) Sprite.getNewAnim("PlayerDown"),
			left  = (AnimatedSprite) Sprite.getNewAnim("PlayerLeft"),
			right = (AnimatedSprite) Sprite.getNewAnim("PlayerRight");

	public transient AnimatedSprite animSprite = down;

	public boolean noclip = false;
	private TagMenu HUD;

	public int cam_xOff = 0;
	public int cam_yOff = 0;

	// TODO: Generate UUID and send instead of Username

	public Player(double x, double y, Keyboard input, String username) {
		this.setX(x);
		this.setY(y);
		this.name = username;
		this.input = input;
		init();
	}

	public void init() {
		
		this.speed = 2;
		this.xBound = 8;
		this.yBound = 8;
		this.xOffset = -16;
		this.yOffset = -16;
		this.health = 50;
		
		super.set("name", "" + name);
		super.set("health", "" + health);
		super.set("speed", "" + speed);
		super.set("mass", "" + mass);

		body.bounds = new Rectangle((float) x(), (float) y(), 32, 64);
		body.set(VARS.PHYS_NOGRAV, true);
		body.bounds.set(VARS.REND_LOCALLY, true);
		
		this.HUD = new TagMenu("HUD");

		System.out.println("ADDING NEW PLAYER: " + this.x() + "," + this.y());
	}
	
	public void ShowHUD() {
		UI_Manager.Load(HUD);
		
	}
	
	public void UpdateVars() {
		System.out.println("VARS: " + vars.keySet().size());
		for (String i : vars.keySet()) {
			System.out.println(" + " + i + " :: " + vars.get(i));
			//HUD.script.call("VarChanged", i, vars.get(i), vars.get(i));
		}
	}

	public boolean remove() {
		return super.remove();
	}
	
	public void set(String key, String val) {
		String from = this.svar(key);
		super.set(key, val);
		
		HUD.script.call("VarChanged", key, from, val);
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
		//HUD.script.call("SetHealthbar", GUI.progressBar(61, 100, this.health));
		//raycastDIR = level.RayCast(new Vector2i(x(), y()), dirInt, (int) 3);

		((TiledLevel) level).TestEventVolumes(this);
		
		animSprite.update();

		if (!walking) {
			animSprite = idle;
			this.animSprite.setFrameRate(8);
		} else {
			this.animSprite.setFrameRate(6 - (int) this.speed / 2);
		}

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
			if (input.Sprint && walking) { // 300
				speed = 4;
			} else {
				speed = 2;
			}

			// if (inventoryOn == false) {
			if (this == level.getClientPlayer()) {
				if (input.up) {
					// animSprite = up;
					yv -= speed;
				} else if (input.down) {
					// animSprite = down;
					yv += speed;
				}
				if (input.left) {
					animSprite = left;
					this.vel().x(-speed);
				} else if (input.right) {
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
			} else if (vel().x() < 0) {
				animSprite = left;
				walking = true;
			}
		}

		if (this.isClientPlayer()) {
			PVector Gravity = new PVector();
			Gravity.y(VARS.Ag);

			this.vel().add(Gravity);

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

		} else {
			ya = vel().y();
			xa = vel().x();
			move(xa, ya);
		}

		this.vel().x(0);
		this.pv.x(0);

		if (VARS.do_possession && Selector.selected != null) {
			Selector.selected.pos().set((Mouse.getX() / Boot.scale + Screen.xOffset) + 0,
					(Mouse.getY() / Boot.scale + Screen.yOffset) - Selector.selected.sprite.getHeight());
		}
	}

	public boolean isClientPlayer() {
		return this.equals(Boot.get().getPlayer());
	}

	private Selector selection_tool;

	public void updateShooting() {
		if (Mouse.getButton() == 2) {
			selection_tool = new Selector((Mouse.getX() / Boot.scale + Screen.xOffset) + 0, (Mouse.getY() / Boot.scale + Screen.yOffset) + 0);
			level.add(selection_tool);
			selection_tool.update();
		}

		if (Mouse.getButton() == 1) {
			//XML_Projectile Test_Arrow = new XML_Projectile((x()) + 32, y() + 32, "/Tags/Projectiles/Arrow.xml", this);
			TagProjectile Grenade = new TagProjectile(x() + 32, y() + 32, "/Tags/Projectiles/Grenade.xml", this);
			level.add(Grenade);
			Mouse.setMouseB(-1);
		}
	}

	public void setPosition(TileCoord tileCoord) {
		setPosition(tileCoord.x(), tileCoord.y());
	}
	
	public void setPosition(int x, int y) {
		setX(x);
		setY(y);
	}

	public void setPositionTiled(double x, double y, String XML, boolean tileMult) {
		TiledLevel newLevel = new TiledLevel(XML);
		Boot.get().setLevel(newLevel);
		Boot.get().getLevel().add(this);
		
		if (x == -1 && y == -1) {
			x = newLevel.Spawnpoint.x() / 32;
			y = newLevel.Spawnpoint.y() / 32;
		}

		if (tileMult) {
			x *= TileCoord.TILE_SIZE;
			y *= TileCoord.TILE_SIZE;
		}

		this.removed = false;
		this.setX((x));
		this.setY((y));
		
		//previous.killLua();

		Game.DiscordPlayerPosPresence();
	}

	public void render(Screen screen) {
		sprite = animSprite.getSprite();
		
		this.DrawXOffset = 0;
		this.DrawYOffset = 0;
		this.yOffset = 0;
		this.xOffset = 16;
		screen.DrawEntity(this, (int) (x() + DrawXOffset + cam_xOff), (int) (y() + DrawYOffset + cam_yOff));
	}

	public void renderGUI(Screen screen) {
		if (Boot.drawDebug) {
			if (BottomBound != null) {
				BottomBound.drawLine(screen, true);
			}

			Debug.drawRect(screen, (int) x() + DrawXOffset, (int) y() + DrawYOffset, sprite.getWidth(),
					sprite.getHeight(), 0xffFADE0F, true);
			Debug.drawRect(screen, (int) x() + xOffset, (int) y() + yOffset, EntWidth, EntHeight, 0xff00FFFF, true);
		}

		String text = (int) Boot.get().getPlayer().x() / TileCoord.TILE_SIZE + ","
				+ (int) Boot.get().getPlayer().y() / TileCoord.TILE_SIZE;
		Game.font16bit.render((int) Boot.width - text.length() * 16 - 3, 3, -3, text, screen, false, false);
		Game.font16bit.render((int) Boot.width - text.length() * 16 + 1 - 3, 3, -3, 0xffFFFFFF, text, screen, false,
				false);

		if (Game.devModeOn) {
			body.draw(screen);
		}
	}
}
