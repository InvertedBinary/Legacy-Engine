package com.IB.SL.entity.mob.hostile;

import java.util.List;
import java.util.Random;

import com.IB.SL.Boot;
import com.IB.SL.entity.Entity;
import com.IB.SL.entity.mob.Mob;
import com.IB.SL.entity.mob.Player;
import com.IB.SL.entity.projectile.ThrowableRock;
import com.IB.SL.graphics.AnimatedSprite;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.graphics.SpriteSheet;
import com.IB.SL.graphics.UI.GUI;

public class Zombie extends Mob{
	
	transient private AnimatedSprite down = new AnimatedSprite(SpriteSheet.zombie_down, 16, 16, 3);
	transient private AnimatedSprite up = new AnimatedSprite(SpriteSheet.zombie_up, 16, 16, 3);
	transient private AnimatedSprite left = new AnimatedSprite(SpriteSheet.zombie_left, 16, 16, 2);
	transient private AnimatedSprite right = new AnimatedSprite(SpriteSheet.zombie_right, 16, 16, 2);
	
	transient private GUI gui;
	transient List<Entity> entities;
	
	transient private AnimatedSprite animSprite = down;
	transient double xa = 0;
	transient double ya = 0;
	transient double time = 0;
	transient int fireRate = 0;
	transient private int visability;
	transient public static boolean justspawnedtracker = false;
	transient List<Player> players;
	transient Player p;
	transient Entity tracking = null;
	
	public Zombie(int x, int y) {
		this.maxhealth = 10;
		this.mobhealth = this.maxhealth;
		gui = new GUI();
		this.Exp = 5;
		this.setX(x << 4);
		this.setY(y << 4);
		this.name = "Zombie";
		this.speed = 0.5;
		this.rarity = 4;
		this.hostility = "AGR";
		sprite = Sprite.playerback;
		
		Random rand = new Random();
		int r = 2;
			r = rand.nextInt(2);
			//r = 2;
		switch (r) {
		case 0:
			down = new AnimatedSprite(SpriteSheet.zombie02_down, 16, 16, 3);
			up = new AnimatedSprite(SpriteSheet.zombie02_up, 16, 16, 3);
			left = new AnimatedSprite(SpriteSheet.zombie02_left, 16, 16, 2);
			right = new AnimatedSprite(SpriteSheet.zombie02_right, 16, 16, 2);	
			break;
		case 1:
		down = new AnimatedSprite(SpriteSheet.zombie01_down, 16, 16, 3);
		up = new AnimatedSprite(SpriteSheet.zombie01_up, 16, 16, 3);
		left = new AnimatedSprite(SpriteSheet.zombie01_left, 16, 16, 2);
		right = new AnimatedSprite(SpriteSheet.zombie01_right, 16, 16, 2);	
		break;
		case 2:
			
		break;
		default:
			break;
		}
		
	}
	
	
	private void move() {
		xa = 0;
		ya = 0;
		players = level.getPlayers(this, 125 - visability);
		List<Player> players2 = level.getPlayers(this, 215 - visability);

		entities = level.getEntities(this, 150 - visability, "NEU", "PASS");
		
				//if (entities.get(0).hostility == hostility.NEU || entities.get(0).hostility == hostility.PASS) {
		if (entities.size() > 0) {
			if (players2.size() > 0) {
			Entity e = entities.get(0);
			tracking = e;
				if ((int)x() < (int)e.x() + 20) xa+= this.speed;
				if ((int)x() > (int)e.x() - 20) xa-= this.speed;
				if ((int)y() < (int)e.y() + 20) ya+= this.speed;
				if ((int)y() > (int)e.y() - 20) ya-= this.speed;
				updateMobShooting(e);
					}
				} else if (players.size() > 0) {
					p = players.get(0);
					tracking = p;
				//	Debug.drawLine(Game.getGame().getScreen(), (int)x / 16, (int)y / 16, (int)player.x / 16, (int)player.y / 16, 0xff000000, true);
					if ((int)x() < (int)p.x() + 20) xa+= this.speed;
					if ((int)x() > (int)p.x() - 20) xa-= this.speed;
					if ((int)y() < (int)p.y() + 20) ya+= this.speed;
					if ((int)y() > (int)p.y() - 20) ya-= this.speed;
					updateShooting();
					
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
		if (time % 8 == 0) {
			this.hurt = false;
		}
		if (level.brightness <= 0) {
			visability = level.brightness * -1 / 2;
		} else {
			visability = 0;
		}
		
		visability -= this.mobhealth / 2;
		
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
		
	}

	
	public void render(Screen screen) {
		xOffset = -8;
		yOffset = -15;
		if (this.mobhealth < this.maxhealth) screen.renderSprite((int) x() - 16, (int)y() - 24, gui.renderMobHealthExperiment(this, 20), true);

		sprite = animSprite.getSprite();
		screen.renderMobSprite((int) (x() + xOffset), (int) (y() + yOffset), this);
		if (Boot.get().devModeOn) {
			screen.drawRect((int)x() + xOffset, (int)y() + yOffset, sprite.getWidth(), sprite.getHeight(), 0xFF0000, true);
			try {
			if (players.size() > 0 || entities.size() > 0) {
				if (tracking != null) {
					
				Boot.get().getScreen().drawVectors(Boot.get().getLevel().BresenhamLine((int) x(), (int)y(), (int)tracking.x(), (int)tracking.y()), 0xffFF3AFB, true);				
				}
			}
				} catch (Exception e) {
					
			}
			}
		

	}

    private void updateMobShooting(Entity e) {
        if (fireRate <= 0) {
            double px = e.x();
            double py = e.y();
            double sx = this.x();
            double sy = this.y();
            double dx = px - sx;
            double dy = py - sy;
            double dir = Math.atan2(dy, dx);
            RockTHROWING(x(), y() + 4, dir, this);
            fireRate = ThrowableRock.FIRE_RATE;
        }
    }	
    private void updateShooting() {
        List<Player> players = level.getPlayers(this, 150 - visability);  
        if ((players.size() > 0) && fireRate <= 0) {
            double px = level.getClientPlayer().x();
            double py = level.getClientPlayer().y();
            double sx = this.x();
            double sy = this.y();
            double dx = px - sx;
            double dy = py - sy;
            double dir = Math.atan2(dy, dx);
            RockTHROWING(x(), y() + 4, dir, this);
			level.getClientPlayer().incombat = true;
            fireRate = ThrowableRock.FIRE_RATE;
        }
    }
}
