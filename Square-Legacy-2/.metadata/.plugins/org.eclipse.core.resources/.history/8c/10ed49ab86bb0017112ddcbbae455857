package com.IB.SL.entity.mob.bosses;

import java.util.List;

import com.IB.SL.Game;
import com.IB.SL.entity.inventory.ActiveEffects;
import com.IB.SL.entity.inventory.EquipableItem;
import com.IB.SL.entity.inventory.item.equipables.rings.OpticBond;
import com.IB.SL.entity.mob.Mob;
import com.IB.SL.entity.mob.Player;
import com.IB.SL.entity.projectile.Optical;
import com.IB.SL.graphics.AnimatedSprite;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.graphics.SpriteSheet;
import com.IB.SL.graphics.UI.GUI;
import com.IB.SL.level.Node;
import com.IB.SL.level.TileCoord;
import com.IB.SL.util.Vector2i;

public class Occulus extends Mob {

	transient private AnimatedSprite down = new AnimatedSprite(SpriteSheet.Occulus_down, 32, 32, 0);
	transient private AnimatedSprite up = new AnimatedSprite(SpriteSheet.Occulus_up, 32, 32, 0);
	transient private AnimatedSprite left = new AnimatedSprite(SpriteSheet.Occulus_left, 32, 32, 0);
	transient private AnimatedSprite right = new AnimatedSprite(SpriteSheet.Occulus_right, 32, 32, 0);

	transient private AnimatedSprite animSprite = down;
	transient double xa = 0;
	transient double ya = 0;
	transient double time = 0;
	transient public int fireRate = 0;
	transient private int chargetime = 0;
	transient private int healTime = 0;
	transient int fireRateTime = 0;
	transient int index = 0;
	transient private GUI gui;
	transient public static boolean justspawned = false;
	transient private List<Node> path = null;

	transient private double speed = 0.5;

	public Occulus(int x, int y) {
		this.maxhealth = 350;
		this.mobhealth = maxhealth;
		this.name = "Occulos";
		this.hostility = hostility.BOSS;
		this.x = x << 4;
		this.y = y << 4;
		this.id = 2;
		sprite = Sprite.Occulus;
		gui = new GUI();
		this.effects = new ActiveEffects(7, this);
	}
	
	private void move() {
	      List<Player> players = level.getPlayers(this, 200);
	      if (players.size() > 0) {
	         xa = 0;
	         ya = 0;
	         double px = level.getPlayerAt(0).getX();
	         double py = (int) level.getPlayerAt(0).getY();
	         Vector2i start = new Vector2i((int) getX() >> Game.TILE_BIT_SHIFT, (int)getY() >> Game.TILE_BIT_SHIFT);
	         Vector2i destination = new Vector2i(px / TileCoord.TILE_SIZE, py / TileCoord.TILE_SIZE);
	         if (time % 1 == 0) path = level.findPath(start, destination);
	         if (path != null) {
	            if (path.size() > 0) {
	               Vector2i vec = path.get(path.size() - 1).tile;
	               if (x < vec.getX() << 4) xa++;
	               if (x > vec.getX() << 4) xa--;
	               if (y < vec.getY() << 4) ya++;
	               if (y > vec.getY() << 4) ya--;
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
		if (time % 2 == 0) {
			this.hurt = false;
		}
		chargetime++;
		healTime++;
		fireRateTime++;
		if (chargetime > 800) {
			this.speed = 1;
		}
		
		if (chargetime == 800) {
			
		}
		
		if (chargetime > 850) {
			this.speed = 0.5;
			chargetime = 0;
		}
		
		
		if (healTime > 590) {
			if (this.mobhealth < 150) {
				healTime++;
			this.mobhealth++;
			System.out.println(this.mobhealth);
			}
			if (this.mobhealth < 20) {
				healTime++;
				this.mobhealth++;
				this.mobhealth++;
			}
		}
		if (healTime > 600) {
			healTime = 0;
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
		xOffset = -16;
		yOffset = -24;
		sprite = animSprite.getSprite();
		screen.renderMobSpriteUniversal((int) (x + xOffset), (int) (y + yOffset), this);
	}
	
	public void renderGUI(Screen screen) {
		gui.font.render((int) (x - 50), (int) (y - 40), -5, 0, this.name, screen, true, false);
		if (level.getPlayers(this, 200).size() > 0)
		screen.renderSprite((Game.width  / 2) - 54, 2, Sprite.resize(gui.renderBar(60, gui.healthbar, maxhealth, mobhealth), 1.5), false); // 130
	}
	
	private void updateShooting() {
		List<Player> players = level.getPlayers(this, 150);
		if ((players.size() > 0) && fireRate <= 0/* && justspawned == false*/) {
			double px = level.getClientPlayer().getX();
			double py = level.getClientPlayer().getY();
			double sx = this.getX();
			double sy = this.getY();
			double dx = px - sx;
			double dy = py - sy;
			double dir = Math.atan2(dy, dx);
			Optical(x, y, dir);
			level.getClientPlayer().incombat = true;
			//Sound.Play(Sound.Spell2, -8, false);
			fireRate = Optical.FIRE_RATE;
			if (fireRateTime >= 800) {
				fireRate = 1;
				Optical(x, y, dir + 10);
				Optical(x, y, dir + 20);
			//	variance = 10;
			//	Optical(x + variance, y + 4 + variance, dir);

			}
			if (fireRateTime >= 810) {
				fireRateTime = 0;
				Optical(x, y, dir + 10);
				Optical(x, y, dir + 20);
				//variance = 20;
				//Optical(x + variance, y + 4 + variance, dir);

			}
		}
	}

	
	public void death() {
			if (!removed) {
			level.add(new OpticBond((int)x, (int) y, 2000, 1, EquipableItem.slot_UTILITY1));
			}
	}

}
