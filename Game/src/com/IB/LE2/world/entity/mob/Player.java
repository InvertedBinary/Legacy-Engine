package com.IB.LE2.world.entity.mob;

import java.io.Serializable;
import java.util.List;

import com.IB.LE2.Boot;
import com.IB.LE2.Game;
import com.IB.LE2.input.UI.GUI;
import com.IB.LE2.input.hardware.Keyboard;
import com.IB.LE2.input.hardware.Mouse;
import com.IB.LE2.media.audio.Audio;
import com.IB.LE2.media.graphics.AnimatedSprite;
import com.IB.LE2.media.graphics.Screen;
import com.IB.LE2.media.graphics.Sprite;
import com.IB.LE2.util.Debug;
import com.IB.LE2.util.VARS;
import com.IB.LE2.util.Vector2i;
import com.IB.LE2.util.math.PVector;
import com.IB.LE2.util.shape.Rectangle;
import com.IB.LE2.world.entity.projectile.Selector;
import com.IB.LE2.world.entity.projectile.TagProjectile;
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

	public transient AnimatedSprite
			idle = (AnimatedSprite) Sprite.getNewAnim("PlayerIdle"),
			down = (AnimatedSprite) Sprite.getNewAnim("PlayerDown"),
			left = (AnimatedSprite) Sprite.getNewAnim("PlayerLeft"),
			right = (AnimatedSprite) Sprite.getNewAnim("PlayerRight");

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
		raycastDIR = level.RayCast(new Vector2i(x(), y()), dirInt, (int) 3);

		Pathtime++;
		tileX = (int) x() >> VARS.TILE_BIT_SHIFT;
		tileY = (int) y() >> VARS.TILE_BIT_SHIFT;
		
		((TiledLevel) level).TestEventVolumes(this);
		
		time++;
		animSprite.update();

		if (!walking) {
			animSprite = idle;
			this.animSprite.setFrameRate(8);
		} else {
			this.animSprite.setFrameRate(6 - (int) this.speed / 2);
		}

		// if (abilityCooldown > 0) abilityCooldown--;
		if (fireRate > 0)
			fireRate--;

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
			Audio.Play("Explosion4");
			//XML_Projectile Test_Arrow = new XML_Projectile((x()) + 32, y() + 32, "/Tags/Projectiles/Arrow.xml", this);
			TagProjectile Grenade = new TagProjectile(x() + 32, y() + 32, "/Tags/Projectiles/Grenade.xml", this);
			level.add(Grenade);
			Mouse.setMouseB(-1);
		}
	}

	public void setPosition(TileCoord tileCoord) {
		this.setX(tileCoord.x());
		this.setY(tileCoord.y());
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
