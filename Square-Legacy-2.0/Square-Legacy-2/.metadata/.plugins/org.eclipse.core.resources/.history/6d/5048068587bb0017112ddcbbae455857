package com.IB.SL.entity.mob.hostile.minions;

import java.util.List;

import com.IB.SL.Game;
import com.IB.SL.entity.inventory.ActiveEffects;
import com.IB.SL.entity.mob.Mob;
import com.IB.SL.entity.mob.Player;
import com.IB.SL.entity.projectile.WizardProjectile2;
import com.IB.SL.graphics.AnimatedSprite;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.graphics.SpriteSheet;
import com.IB.SL.level.Node;
import com.IB.SL.level.TileCoord;
import com.IB.SL.util.Vector2i;

public class OpticalMob extends Mob {

	transient private AnimatedSprite down = new AnimatedSprite(SpriteSheet.Optical_down, 16, 16, 3);
	transient private AnimatedSprite up = new AnimatedSprite(SpriteSheet.Optical_up, 16, 16, 3);
	transient private AnimatedSprite right = new AnimatedSprite(SpriteSheet.Optical_left, 16, 16, 2);
	transient private AnimatedSprite left = new AnimatedSprite(SpriteSheet.Optical_right, 16, 16, 2);

	transient private AnimatedSprite animSprite = down;
	transient double xa = 0;
	transient double ya = 0;
	transient double time = 0;
	transient public int fireRate = 0;
	transient public static boolean justspawned = false;
	transient private List<Node> path = null;

	transient private double speed = 0.5;

	public OpticalMob(int x, int y) {
		this.Exp = 2;
		this.maxhealth = 2;
		this.mobhealth = this.maxhealth;
		this.Exp = 10;
		this.x = x << 4;
		this.y = y << 4;
		this.id = 3;
		this.name = "Optical";
		this.speed = 0.5;
		this.hostility = HOSTILITY.AGR;
		sprite = Sprite.playerback;
		this.effects = new ActiveEffects(7, this);
	}

	private void move() {
		List<Player> players = level.getPlayers(this, 150);
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
		updateShooting();
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
		sprite = animSprite.getSprite();
		this.xOffset = -8;
		this.yOffset = -15;
		screen.renderMobSprite((int) (x + xOffset), (int) (y + yOffset), this);
	}

	private void updateShooting() {
		List<Player> players = level.getPlayers(this, 125);
		if ((players.size() > 0) && fireRate <= 0/* && justspawned == false */) {
			double px = level.getClientPlayer().getX();
			double py = level.getClientPlayer().getY();
			double sx = this.getX();
			double sy = this.getY();
			double dx = px - sx;
			double dy = py - sy;
			double dir = Math.atan2(dy, dx);
			shootingtracker(x, y + 4, dir);
			level.getClientPlayer().incombat = true;
			// Sound.Play(Sound.Spell2, -8, false);
			fireRate = WizardProjectile2.FIRE_RATE;
		}
	}

}
