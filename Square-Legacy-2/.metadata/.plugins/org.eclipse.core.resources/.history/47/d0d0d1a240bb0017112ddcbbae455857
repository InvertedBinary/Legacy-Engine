package com.IB.SL.entity.mob.hostile;

import java.util.List;
import java.util.Random;

import com.IB.SL.Game;
import com.IB.SL.Game.gameState;
import com.IB.SL.entity.inventory.ActiveEffects;
import com.IB.SL.entity.mob.Mob;
import com.IB.SL.entity.mob.Player;
import com.IB.SL.entity.projectile.WizardProjectile2;
import com.IB.SL.entity.spawner.VoidParticleSpawner;
import com.IB.SL.graphics.AnimatedSprite;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.graphics.SpriteSheet;
import com.IB.SL.graphics.UI.GUI;
import com.IB.SL.level.Node;
import com.IB.SL.level.TileCoord;
import com.IB.SL.util.Sound;
import com.IB.SL.util.Vector2i;

public class Siphon extends Mob {

	transient private AnimatedSprite down = new AnimatedSprite(SpriteSheet.Siphon_down, 16, 16, 2);
	transient private AnimatedSprite up = new AnimatedSprite(SpriteSheet.Siphon_up, 16, 16, 2);
	transient private AnimatedSprite left = new AnimatedSprite(SpriteSheet.Siphon_left, 16, 16, 2);
	transient private AnimatedSprite right = new AnimatedSprite(SpriteSheet.Siphon_right, 16, 16, 2);

	transient private AnimatedSprite animSprite = down;
	transient protected static java.util.Random Random = new Random();
	transient public static java.util.Random random = Random;
	transient double xa = 0;
	transient double ya = 0;
	transient private List<Node> path = null;
	transient double time = 0;
	transient public int fireRate = 0;
	transient public static boolean justspawned = false;
	transient private GUI gui;
	transient public int Explodetime = 0;

	public Siphon(int x, int y) {
		this.xBound = 8;
		this.yBound = 6;
		this.xOffset = 1;
		this.yOffset = 7;

		this.maxhealth = 50;
		this.mobhealth = this.maxhealth;
		gui = new GUI();
		this.Exp = 25;
		this.x = x << 4;
		this.y = y << 4;
		this.id = 43432;
		this.name = "Siphon";
		this.speed = 0.5;
		this.hostility = HOSTILITY.AGR;
		sprite = Sprite.playerback;
		this.effects = new ActiveEffects(7, this);
	}

	private void move() {
		List<Player> players = level.getPlayers(this, 185);
		if (players.size() > 0) {
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
		} else {
			if (time % (random.nextInt(50) + 30) == 0) {
				xa = random.nextInt(3) - 1;
				ya = random.nextInt(3) - 1;
				if (random.nextInt(2) == 0) {
					xa = 0;
					ya = 0;
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
		if (time % 8 == 0) {
			this.hurt = false;
		}
		updatePull();
		if (fireRate > 0) {
			fireRate--;
		}
		time++;
		move();

		if (walking)
			animSprite.update();
		else
			animSprite.setFrame(0);
		if (ya < 0) {

			animSprite = up;
			dir = DIRECTION.UP;
		} else if (ya > 0) {
			animSprite = down;
			dir = DIRECTION.DOWN;
		}
		if (xa < 0) {
			animSprite = left;
			dir = DIRECTION.LEFT;
		} else if (xa > 0) {
			animSprite = right;
			dir = DIRECTION.RIGHT;
		}

	}

	public void render(Screen screen) {
		if (this.mobhealth < this.maxhealth)
			screen.renderSprite((int) x - 16, (int) y - 25, gui.renderMobHealthExperiment(this, 20), true);
		this.xOffset = -8;
		this.yOffset = -15;
		sprite = animSprite.getSprite();
		screen.renderMobSprite((int) (x + xOffset), (int) (y + yOffset), this);
		if (Game.getGame().gameState == gameState.INGAME_A) {
			screen.drawRect((int) x + xOffset, (int) y + yOffset, sprite.getWidth(), sprite.getHeight(), 0xFF0000,
					true);
		}
	}

	private void updateShooting() {
		List<Player> players = level.getPlayers(this, 120);
		if ((players.size() > 0) && fireRate <= 0 && entityState != entityState.Flee) {
			double px = level.getClientPlayer().getX();
			double py = level.getClientPlayer().getY();
			double sx = this.getX();
			double sy = this.getY();
			double dx = px - sx;
			double dy = py - sy;
			double dir = Math.atan2(dy, dx);
			shootingtracker(x, y + 4, dir, true);
			level.getClientPlayer().incombat = true;
			Sound.Play(Sound.Spell2, false);
			fireRate = WizardProjectile2.FIRE_RATE;

		}
	}

	int pullTime = 0;

	private void updatePull() {
		double var = 0.5;
		;
		updateShooting();
		if (pullTime > 850) {
			pullTime = 0;
		}
		pullTime++;
		if (pullTime < 550) {
			List<Player> players = level.getPlayers(this, 140);
			// List<Player> players2 = level.getPlayers(this, 20);
			if (players.size() > 0) {
				// var = 0.5;
				/*
				 * if (players2.size() > 0) { var = -20; pullTime = 551; }
				 */
				if (pullTime == 50) {
					Sound.Play(Sound.Siphon1, false);
					pullTime += 1;
				}
				Game.getGame().getPlayer().pull(this, var);
				level.add(new VoidParticleSpawner((int) (x - 4), (int) (y - 10), 10, 1, level));

			}

		}
	}
}
