package com.IB.SL.entity.mob.hostile;

import java.util.List;

import com.IB.SL.Game;
import com.IB.SL.Game.gameState;
import com.IB.SL.entity.inventory.ActiveEffects;
import com.IB.SL.entity.inventory.effects.Poison;
import com.IB.SL.entity.mob.Mob;
import com.IB.SL.entity.mob.Player;
import com.IB.SL.entity.mob.PlayerMP;
import com.IB.SL.graphics.AnimatedSprite;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.graphics.SpriteSheet;
import com.IB.SL.graphics.UI.GUI;
import com.IB.SL.level.Node;
import com.IB.SL.level.TileCoord;
import com.IB.SL.util.Vector2i;

public class PlagueRat extends Mob {

	transient private AnimatedSprite down = new AnimatedSprite(SpriteSheet.PlagueRat_down, 16, 16, 3);
	transient private AnimatedSprite up = new AnimatedSprite(SpriteSheet.PlagueRat_up, 16, 16, 3);
	transient private AnimatedSprite left = new AnimatedSprite(SpriteSheet.PlagueRat_left, 16, 16, 3);
	transient private AnimatedSprite right = new AnimatedSprite(SpriteSheet.PlagueRat_right, 16, 16, 3);
	transient private AnimatedSprite animSprite = down;

	transient double xa = 0;
	transient double ya = 0;
	transient double time = 0;
	transient public int fireRate = 0;
	transient public static boolean justspawned = false;
	transient private List<Node> path = null;
	transient private GUI gui;

	public PlagueRat(int x, int y) {
		this.maxhealth = 1;
		this.mobhealth = this.maxhealth;
		gui = new GUI();
		this.Exp = 3;
		this.x = x << 4;
		this.y = y << 4;
		this.id = 4218;
		this.name = "Plague Rat";
		this.speed = 1.2;
		this.rarity = 2;
		this.hostility = HOSTILITY.AGR;
		sprite = Sprite.playerback;
		this.effects = new ActiveEffects(7, this);
	}

	public void stab() {
		try {
			List<Player> p = level.getPlayers(this, 20);
			if (time % 240 == 0) {
				for (int i = 0; i < p.size(); i++) {
					Game.getGame().getLevel().damagePlayer((int) x, (int) y, (PlayerMP) p.get(0), 0, 1, name, 0);
					p.get(i).effects.addEffect(new Poison((PlayerMP)p.get(0), 120, 30));
				}
				time = 0;
			}
		} catch (Exception e) {

		}
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
						this.animSprite = this.right;
					if (x > vec.getX() << 4)
						xa--;
					this.animSprite = this.left;
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
		stab();
		time++;
		if (fireRate > 0) {
			fireRate--;
		}
		move();

		if (walking)
			animSprite.update();
		else
			animSprite.setFrame(0);

		if (ya < 0) {
			dir = DIRECTION.UP;
			animSprite = up;
		} else if (ya > 0) {
			dir = DIRECTION.DOWN;
			animSprite = down;
		}
		if (xa < 0) {
			dir = DIRECTION.LEFT;
			animSprite = left;
		} else if (xa > 0) {
			dir = DIRECTION.RIGHT;
			animSprite = right;
		}

	}

	public void render(Screen screen) {
		if (this.mobhealth < this.maxhealth)
			screen.renderSprite((int) x - 16, (int) y - 24, gui.renderMobHealthExperiment(this, 20), true);
		this.xOffset = -8;
		this.yOffset = -15;
		if (Game.getGame().gameState == gameState.INGAME_A) {
			// gui.renderHealth(screen, this, (int) x - 16, (int)y - 24, true);
		}
		// gui.renderName(screen, "Zombie", (int)x - 14, (int)y- 25, -4, true,
		// true);
		sprite = animSprite.getSprite();
		screen.renderMob((int) (x + xOffset), (int) (y + yOffset), this);
		if (Game.getGame().gameState == gameState.INGAME_A) {
			screen.drawRect((int) x + xOffset, (int) y + yOffset, sprite.getWidth(), sprite.getHeight(), 0xFF0000,
					true);
		}

	}

}
