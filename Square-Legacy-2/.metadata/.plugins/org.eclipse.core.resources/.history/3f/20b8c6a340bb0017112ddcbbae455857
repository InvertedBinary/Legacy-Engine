package com.IB.SL.entity.mob.hostile;

import java.util.List;

import com.IB.SL.Game;
import com.IB.SL.Game.gameState;
import com.IB.SL.entity.Entity;
import com.IB.SL.entity.inventory.ActiveEffects;
import com.IB.SL.entity.mob.Mob;
import com.IB.SL.entity.mob.Player;
import com.IB.SL.entity.spawner.WallParticleSpawner;
import com.IB.SL.graphics.AnimatedSprite;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.graphics.SpriteSheet;
import com.IB.SL.graphics.UI.GUI;
import com.IB.SL.level.Node;
import com.IB.SL.level.TileCoord;
import com.IB.SL.util.Vector2i;

public class WaterFamiliar extends Mob {

	transient private AnimatedSprite down = new AnimatedSprite(SpriteSheet.WElemental_down, 16, 16, 0);
	transient private AnimatedSprite up = new AnimatedSprite(SpriteSheet.WElemental_up, 16, 16, 0);
	transient private AnimatedSprite left = new AnimatedSprite(SpriteSheet.WElemental_left, 16, 16, 0);
	transient private AnimatedSprite right = new AnimatedSprite(SpriteSheet.WElemental_right, 16, 16, 0);

	transient private AnimatedSprite animSprite = down;

	transient double xa = 0;
	transient double ya = 0;
	transient double time = 0;
	transient int maxlife = 100;
	transient public int fireRate = 0;
	transient public static boolean justspawned = false;
	transient private List<Node> path = null;
	transient private GUI gui;
	transient List<Entity> entities = null;
	transient List<Player> players = null;
	transient Vector2i start;
	transient Vector2i destination;

	public WaterFamiliar(int x, int y, int maxlife) {
		this.maxhealth = 10;
		this.mobhealth = this.maxhealth;
		gui = new GUI();
		this.Exp = 0;
		this.x = x << 4;
		this.y = y << 4;
		this.id = 53432;
		this.name = "Water Familiar";
		this.speed = 0.8;
		this.hostility = HOSTILITY.NEU;
		sprite = Sprite.playerback;
		this.effects = new ActiveEffects(7, this);

		this.maxlife = maxlife;

	}

	public void stab() {
		try {
			List<Entity> ent = level.getEntities(this, 20, entities);
			List<Player> p = level.getPlayers(this, 100);

			if (ent.get(0).hostility == hostility.AGR) {
				if (time % 30 == 0) {
					if (p.size() > 0) {
					}
					for (int i = 0; i < ent.size(); i++) {
						Game.getGame().getLevel().damage((int) x, (int) y, (Mob) ent.get(0), 0,
								1 + (Game.getGame().getPlayer().stat_base_MDF * 0.15), name, 0);
					}
				}
			}
		} catch (Exception e) {

		}
	}

	private void move() {
		if (entities.size() > 0) {
			xa = 0;
			ya = 0;
			double px = entities.get(0).getX();
			double py = (int) entities.get(0).getY();
			start = new Vector2i((int) getX() >> Game.TILE_BIT_SHIFT, (int) getY() >> Game.TILE_BIT_SHIFT);
			destination = new Vector2i(px / TileCoord.TILE_SIZE, py / TileCoord.TILE_SIZE);
			if (time % 1 == 0)
				path = level.findPath(start, destination);
			if (path != null) {
				if (path.size() > 0) {
					Vector2i vec = path.get(path.size() - 1).tile;
					if (x < vec.getX() << 4)
						xa++;
					if (x > vec.getX() << 4)
						xa--;
					if (y < vec.getY() << 4)
						ya++;
					if (y > vec.getY() << 4)
						ya--;
				}
			}
		} else if (players.size() > 0) {
			xa = 0;
			ya = 0;
			double px = level.getPlayerAt(0).getX();
			double py = (int) level.getPlayerAt(0).getY();
			Vector2i start = new Vector2i((int) getX() >> Game.TILE_BIT_SHIFT, (int) getY() >> Game.TILE_BIT_SHIFT);
			Vector2i destination = new Vector2i(px / TileCoord.TILE_SIZE, py / TileCoord.TILE_SIZE);
			if (time % 1 == 0)
				path = level.findPath(start, destination);
			if (path != null) {
				if (path.size() > 0) {
					Vector2i vec = path.get(path.size() - 1).tile;
					if (x < vec.getX() << 4)
						xa++;
					if (x > vec.getX() << 4)
						xa--;
					if (y < vec.getY() << 4)
						ya++;
					if (y > vec.getY() << 4)
						ya--;
				}
			}
		}
		if (xa != 0 || ya != 0) {
			move(xa * speed, ya * speed);
			walking = true;
		} else {
			walking = false;
		}
	}

	public void update() {
		maxlife--;
		if (time % 8 == 0) {
			this.hurt = false;
		}

		if (maxlife <= 0) {
			level.add(new WallParticleSpawner((int) (x), (int) (y), 50, 20, level));
			remove();
		}
		players = level.getPlayers(this, 150);
		entities = level.getEntities(this, 135, HOSTILITY.AGR);
		stab();
		if (fireRate > 0) {
			fireRate--;
		}
		time++;
		move();
		if (walking) {
			animSprite.update();
			level.add(new WallParticleSpawner((int) (x), (int) (y), 55, 1, level));
		} else
			animSprite.setFrame(0);
		if (ya < 0) {

			animSprite = up;
			dir = DIRECTION.UP;
		} else if (ya > 0) {
			animSprite = down;
			dir = DIRECTION.DOWN;
		}
		if (xa < 0) {
			// animSprite = left;
			dir = DIRECTION.LEFT;
		} else if (xa > 0) {
			// animSprite = right;
			dir = DIRECTION.RIGHT;
		}

	}

	public void render(Screen screen) {
		if (this.mobhealth < this.maxhealth)
			screen.renderSprite((int) x - 16, (int) y - 20, gui.renderMobHealthExperiment(this, 20), true);
		this.xOffset = -8;
		this.yOffset = -15;
		sprite = animSprite.getSprite();
		screen.renderMobSprite((int) (x + xOffset), (int) (y + yOffset), this);
		if (Game.getGame().gameState == gameState.INGAME_A) {
			screen.drawRect((int) x + xOffset, (int) y + yOffset, sprite.getWidth(), sprite.getHeight(), 0xFF0000,
					true);
		}
	}

}
