package com.IB.SL.entity.mob.bosses;

import java.util.List;

import com.IB.SL.entity.mob.Mob;
import com.IB.SL.entity.mob.Player;
import com.IB.SL.entity.projectile.Optical;
import com.IB.SL.graphics.AnimatedSprite;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.graphics.SpriteSheet;
import com.IB.SL.level.Node;
import com.IB.SL.util.Sound;
import com.IB.SL.util.Vector2i;

public class SlimeLord extends Mob {

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
	transient public static boolean justspawned = false;
	transient private List<Node> path = null;

	private double speed = 0.5;

	public SlimeLord(int x, int y) {
		this.mobhealth = 150;
		this.x = x << 4;
		this.y = y << 4;
		this.name = "Mob.bossMob.SlimeLord.name";
		sprite = Sprite.Occulus;
	}

	private void move() {
	      List<Player> players = level.getPlayers(this, 150);
	      if (players.size() > 0) {
	         xa = 0;
	         ya = 0;
	         double px = level.getPlayerAt(0).getX();
	         double py = (int) level.getPlayerAt(0).getY();
	         Vector2i start = new Vector2i((int) getX() >> 4, (int)getY() >> 4);
	         Vector2i destination = new Vector2i(px / 16, py / 16);
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
		if (time % 8 == 0) {
			this.hurt = false;
		}
		chargetime++;
		if (chargetime > 800) {
			this.speed = 1;
		}
		if (chargetime > 850) {
			this.speed = 0.5;
			chargetime = 0;
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
		screen.renderMobSpriteUniversal((int) (x - 8), (int) (y - 15), this);
	}
	private void updateShooting() {
		List<Player> players = level.getPlayers(this, 125);  
		if ((players.size() > 0) && fireRate <= 0/* && justspawned == false*/) {
			double px = level.getClientPlayer().getX();
			double py = level.getClientPlayer().getY();
			double sx = this.getX();
			double sy = this.getY();
			double dx = px - sx;
			double dy = py - sy;
			double dir = Math.atan2(dy, dx);
			Optical(x, y + 4, dir);
			level.getClientPlayer().incombat = true;
			Sound.Play(Sound.Spell2, false);
			fireRate = Optical.FIRE_RATE;
		}
	}

}
