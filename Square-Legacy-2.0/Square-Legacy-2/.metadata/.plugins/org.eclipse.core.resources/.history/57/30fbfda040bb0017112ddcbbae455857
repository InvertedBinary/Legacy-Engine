package com.IB.SL.entity.mob.hostile;

import java.util.List;

import com.IB.SL.Game;
import com.IB.SL.Game.gameState;
import com.IB.SL.entity.inventory.ActiveEffects;
import com.IB.SL.entity.mob.Mob;
import com.IB.SL.entity.mob.Player;
import com.IB.SL.graphics.AnimatedSprite;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.graphics.SpriteSheet;
import com.IB.SL.graphics.UI.GUI;
import com.IB.SL.level.Node;
import com.IB.SL.level.RayCast;
import com.IB.SL.level.TileCoord;
import com.IB.SL.level.tile.Tile;
import com.IB.SL.util.Debug;
import com.IB.SL.util.Sound;
import com.IB.SL.util.Vector2i;

public class Gazer extends Mob {

	transient private AnimatedSprite down = new AnimatedSprite(SpriteSheet.Gazer_down, 16, 16, 3);
	transient private AnimatedSprite up = new AnimatedSprite(SpriteSheet.Gazer_up, 16, 16, 3);
	transient private AnimatedSprite left = new AnimatedSprite(SpriteSheet.Gazer_left, 16, 16, 3);
	transient private AnimatedSprite right = new AnimatedSprite(SpriteSheet.Gazer_right, 16, 16, 3);
	transient private GUI gui;
	transient private AnimatedSprite animSprite = down;
	transient double xa = 0;
	transient double ya = 0;
	transient double time = 0;
	transient public int fireRate = 0;
	transient public static boolean justspawned = false;
	transient private List<Node> path = null;
	transient double healTime = 0;
	transient private double abilityTime = 0;
	transient private double usageTime = 0;
	transient private double fleeTime = 0;
	transient private boolean wasEvading = false;
	transient private double evadeTime = 0;
	transient public Tile tile;
	transient private int visability;
	transient RayCast raycast;
	transient RayCast raycastFlee;
	transient private int dirInt = 0;
	transient List<Player> players;

	public Gazer(int x, int y) {
		this.maxhealth = 3;
		this.mobhealth = maxhealth;
		this.Exp = 10;
		gui = new GUI();
		this.entityState = entityState.Wander;
		this.speed = 1;
		this.x = x << 4;
		this.y = y << 4;
		this.id = 7;
		this.hostility = hostility.AGR;
		this.rarity = 6;
		this.name = "Gazer";
		sprite = Sprite.playerback;
		this.effects = new ActiveEffects(7, this);
	}

	private void move() {
		List<Player> players = level.getPlayers(this, 125 - visability);
		if (players.size() > 0 && entityState != entityState.Evade && entityState != entityState.Flee) {
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
		} else if (entityState == entityState.Flee) {
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
						try {
							
						if (!raycast.hasCollided()) {
							if (x < vec.getX() << 4)
								xa--;
							if (x > vec.getX() << 4)
								xa++;
							if (y < vec.getY() << 4)
								ya--;
							if (y > vec.getY() << 4)
								ya++;
						} else {
							entityState = entityState.Attack;
						}
						} catch (NullPointerException e) {
							
						}
					}
				}
			}
		} else if (entityState == entityState.Evade) {
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
							xa--;
						if (x > vec.getX() << 4)
							xa++;
						if (y < vec.getY() << 4)
							ya--;
						if (y > vec.getY() << 4)
							ya++;
					}
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
		raycastFlee = level.RayCast(new Vector2i(x, y - 8), dirInt, (int) 20);
		if (level.brightness <= 0) {
			visability = (int) (level.brightness * -1 / 2);
		} else {
			visability = 0;
		}

		List<Player> players = level.getPlayers(this, 90 - visability);
		List<Player> playersFlee = level.getPlayers(this, 100 - visability);

		if (this.evadeTime >= 125 && this.wasEvading && playersFlee.size() - 1 <= 0 && raycastFlee.hasCollided()) {
			this.entityState = entityState.Flee;
			this.fleeTime++;
		} else if (this.mobhealth <= 3) {
			this.entityState = entityState.Evade;
			this.wasEvading = true;
			this.evadeTime++;
		} else if (players.size() > 0) {
			this.entityState = entityState.Attack;
		} else {
			this.entityState = entityState.Wander;
		}

		if (this.entityState != entityState.Wander) {
			// System.out.println("STATE: " + this.entityState + " HEALTH: " +
			// this.mobhealth + " FleeTime: " + fleeTime);
		}
		updateShooting();
		if (fireRate > 0) {
			fireRate--;
		}
		time++;
		healTime++;
		abilityTime++;

		if ((this.fleeTime > 100)) {
			this.wasEvading = false;
			fleeTime = 0;
		}

		if (this.evadeTime > 125) {
			this.evadeTime = 0;
		}

		if (usageTime > 20) {
			this.speed -= 0.5;
			usageTime = 0;
		}

		if ((abilityTime % 200) == 0) {
			if ((abilityTime % 60) == 0 && this.speed == 1) {
				usageTime++;
				this.speed += 0.5;
			}
		}
		if ((this.mobhealth < 5 && (healTime % 125) == 0) && this.entityState == entityState.Flee) {
			this.mobhealth += 1;
			System.out.println(mobhealth);
			healTime = 0;
		}

		if ((this.mobhealth < 5 && (healTime % 210) == 0) && this.entityState == entityState.Wander) {
			this.mobhealth += 1;
			System.out.println(mobhealth);
			healTime = 0;
		}

		move();
		if (walking)
			animSprite.update();
		else
			animSprite.setFrame(0);
		if (ya < 0) {
			animSprite = up;
			dir = DIRECTION.UP;
			dirInt = 5;
		} else if (ya > 0) {
			animSprite = down;
			dir = DIRECTION.DOWN;
			dirInt = 8;
		}
		if (xa < 0) {
			animSprite = left;
			dir = DIRECTION.LEFT;
			dirInt = 3;
		} else if (xa > 0) {
			animSprite = right;
			dir = DIRECTION.RIGHT;
			dirInt = 0;
		}
	}

	public void render(Screen screen) {
		xOffset = -8;
		yOffset = -15;
		if (this.mobhealth < this.maxhealth)
			screen.renderSprite((int) x - 16, (int) y - 24, gui.renderMobHealthExperiment(this, 20), true);
		sprite = animSprite.getSprite();
		screen.renderMobSprite((int) (x + xOffset), (int) (y + yOffset), this);
		if (Game.getGame().gameState == gameState.INGAME_A) {
			screen.drawRect((int) x + xOffset, (int) y + yOffset, sprite.getWidth(), sprite.getHeight(), 0xFF0000,
					true);
			screen.drawRect((int) x - 8, (int) y - 15, sprite.getWidth(), sprite.getHeight(), 0xFF00FF, true);
			Debug.drawRect(screen, (int) x + xOffset, (int) y + yOffset, 16, 16, 0xff6600CC, true);

			try {
				if (players.size() > 0) {
					Game.getGame().getScreen().drawVectors(Game.getGame().getLevel().BresenhamLine((int) x, (int) y,
							raycastFlee.rayVector.x, raycastFlee.rayVector.y), 0xffFF3AFB, true);
				}
			} catch (NullPointerException e) {

			}
		}
	}

	private void updateShooting() {
		players = level.getPlayers(this, 110 - visability);
		/*
		 * if (entities.get(i).hostility == entities.get(i).hostility.NEU) { }
		 */
		if ((players.size() > 0) && fireRate <= 0 && entityState != entityState.Flee) {
			double px = level.getClientPlayer().getX();
			double py = level.getClientPlayer().getY();
			double sx = this.getX();
			double sy = this.getY();
			double dx = px - sx;
			double dy = py - sy;
			double dir = Math.atan2(dy, dx);
			raycast = level.RayCast(new Vector2i(x, y), dir, (int) 10);
			if (!raycast.hasCollided()) {
				eyeBolt(x, y + 4, dir);
				level.getClientPlayer().incombat = true;
				Sound.Play(Sound.Spell2, false);
				fireRate = 5;
			} else {
				visability += 50;
			}

		}
	}

}
