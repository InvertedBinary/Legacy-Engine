package com.IB.SL.entity.mob.debug;

import java.util.List;

import com.IB.SL.entity.mob.Mob;
import com.IB.SL.entity.mob.Player;
import com.IB.SL.entity.projectile.WizardProjectile2;
import com.IB.SL.graphics.AnimatedSprite;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.graphics.SpriteSheet;
import com.IB.SL.util.Sound;

public class Tracker extends Mob{
	
	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.UndeadCaster_down, 16, 16, 3);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.UndeadCaster_up, 16, 16, 3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.UndeadCaster_left, 16, 16, 2);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.UndeadCaster_right, 16, 16, 2);
	
	private AnimatedSprite animSprite = down;
	double xa = 0;
	double ya = 0;
	double time = 0;
	int fireRate = 0;
	
	private double speed = 0.5;
	public static boolean justspawnedtracker = false;
	
	public Tracker(int x, int y) {
		this.mobhealth = 5;
		this.x = x << 4;
		this.y = y << 4;
		this.name = "Mob.TrackerMob.testMob.name";
		sprite = Sprite.playerback;
	}
	
	
	private void move() {
		xa = 0;
		ya = 0;
		List<Player> players = level.getPlayers(this, 75);
		if (players.size() > 0) {
		Player player = players.get(0);
		if ((int)x < (int)player.getX() + 20) xa+= this.speed;
		if ((int)x > (int)player.getX() - 20) xa-= this.speed;
		if ((int)y < (int)player.getY() + 20) ya+= this.speed;
		if ((int)y > (int)player.getY() - 20) ya-= this.speed;
	
		} else {

		}

	      /*List<Player> players = level.getPlayers(this, 75);
	      if (players.size() > 0) {
	         xa = 0;
	         ya = 0;
	         Player player = players.get(0);
	         if (x < player.getX()) xa ++;
	         if (x > player.getX()) xa --;
	         if (y < player.getY()) ya ++;
	         if (y > player.getY()) ya --;
	      } else {
	         if (time % (random.nextInt(50) + 30) == 0) {
	            xa = random.nextInt(3) - 1;
	            ya = random.nextInt(3) - 1;
	            if (random.nextInt(2) == 0) {
	               xa = 0;
	               ya = 0;
	            }
	         }
	      }*/
		
		if (xa != 0 || ya != 0) {
		move(xa, ya);
		walking = true;
	}else {
		walking = false;
	}
}
	
	
	
	public void update() {
        time++;
        if (fireRate > 0) {
            fireRate--;
        }
		move();
		if (walking) animSprite.update();
		else animSprite.setFrame(0);
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
		updateShooting();
		
	}

	
	public void render(Screen screen) {
		sprite = animSprite.getSprite();
		screen.renderMob((int) (x - 8), (int) (y - 15), this);
	}

    private void updateShooting() {
        List<Player> players = level.getPlayers(this, 75);  
        if ((players.size() > 0) && fireRate <= 0 && justspawnedtracker == false) {
            double px = level.getClientPlayer().getX();
            double py = level.getClientPlayer().getY();
            double sx = this.getX();
            double sy = this.getY();
            double dx = px - sx;
            double dy = py - sy;
            double dir = Math.atan2(dy, dx);
            shootingtracker(x, y + 4, dir);
			Sound.Play(Sound.Spell2, false);
            fireRate = WizardProjectile2.FIRE_RATE;
        }
    }
}
