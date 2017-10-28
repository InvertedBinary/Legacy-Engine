package com.IB.SL.entity.mob.hostile;

import java.util.List;

import com.IB.SL.Game;
import com.IB.SL.Game.gameState;
import com.IB.SL.entity.Entity;
import com.IB.SL.entity.inventory.ActiveEffects;
import com.IB.SL.entity.mob.Mob;
import com.IB.SL.entity.mob.Player;
import com.IB.SL.entity.projectile.PoisonGoo;
import com.IB.SL.graphics.AnimatedSprite;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.graphics.SpriteSheet;
import com.IB.SL.graphics.UI.GUI;

public class PoisonZombie extends Mob{
	
	private transient AnimatedSprite down = new AnimatedSprite(SpriteSheet.pzombie_down, 16, 16, 3);
	private transient AnimatedSprite up = new AnimatedSprite(SpriteSheet.pzombie_up, 16, 16, 3);
	private transient AnimatedSprite left = new AnimatedSprite(SpriteSheet.pzombie_left, 16, 16, 2);
	private transient AnimatedSprite right = new AnimatedSprite(SpriteSheet.pzombie_right, 16, 16, 2);
	private transient GUI gui;
	private transient List<Entity> entities;

	private transient AnimatedSprite animSprite = down;
	transient double xa = 0;
	transient double ya = 0;
	transient double time = 0;
	transient int fireRate = 0;
	transient private int visability;
	transient public static boolean justspawnedtracker = false;
	transient List<Player> players;
	transient Player p;
	transient Entity tracking = null;
	
	public PoisonZombie(int x, int y) {
		this.maxhealth = 10;
		this.mobhealth = this.maxhealth;
		gui = new GUI();
		this.Exp = 5;
		this.x = x << 4;
		this.y = y << 4;
		this.id = 10;
		this.name = "Poison Zombie";
		this.speed = 0.5;
		this.rarity = 6;
		this.hostility = HOSTILITY.AGR;
		sprite = Sprite.playerback;
		this.effects = new ActiveEffects(7, this);
	}
	
	
	private void move() {
		xa = 0;
		ya = 0;
		players = level.getPlayers(this, 125 - visability);
		List<Player> players2 = level.getPlayers(this, 215 - visability);

		entities = level.getEntities(this, 150 - visability, hostility.NEU, hostility.PASS);
		
				//if (entities.get(0).hostility == hostility.NEU || entities.get(0).hostility == hostility.PASS) {
		 if (players.size() > 0) {
				p = players.get(0);
				tracking = p;
			//	Debug.drawLine(Game.getGame().getScreen(), (int)x / 16, (int)y / 16, (int)player.x / 16, (int)player.y / 16, 0xff000000, true);
				if ((int)x < (int)p.getX() + 20) xa+= this.speed;
				if ((int)x > (int)p.getX() - 20) xa-= this.speed;
				if ((int)y < (int)p.getY() + 20) ya+= this.speed;
				if ((int)y > (int)p.getY() - 20) ya-= this.speed;
				updateShooting();
		 } else if (entities.size() > 0) {
			if (players2.size() > 0) {
			Entity e = entities.get(0);
			tracking = e;
				if ((int)x < (int)e.getX() + 20) xa+= this.speed;
				if ((int)x > (int)e.getX() - 20) xa-= this.speed;
				if ((int)y < (int)e.getY() + 20) ya+= this.speed;
				if ((int)y > (int)e.getY() - 20) ya-= this.speed;
				updateMobShooting(e);
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
		if (this.mobhealth < this.maxhealth) screen.renderSprite((int) x - 16, (int)y - 24, gui.renderMobHealthExperiment(this, 20), true);
		this.xOffset = -8;
		this.yOffset = -15;
		if (Game.getGame().gameState == gameState.INGAME_A) {
			//gui.renderHealth(screen, this, (int) x - 16, (int)y - 24, true);
			}
		//gui.renderName(screen, "Zombie", (int)x - 14, (int)y- 25, -4, true, true);
		sprite = animSprite.getSprite();
		screen.renderMobSprite((int) (x + xOffset), (int) (y + yOffset), this);
		if (Game.getGame().gameState == gameState.INGAME_A) {
			screen.drawRect((int)x + xOffset, (int)y + yOffset, sprite.getWidth(), sprite.getHeight(), 0xFF0000, true);
			try {
			if (players.size() > 0 || entities.size() > 0) {
				if (tracking != null) {
					
				Game.getGame().getScreen().drawVectors(Game.getGame().getLevel().BresenhamLine((int) x, (int)y, (int)tracking.x, (int)tracking.y), 0xffFF3AFB, true);				
				}
			}
				} catch (Exception e) {
					
			}
			}
		

	}

    private void updateMobShooting(Entity e) {
        if (fireRate <= 0) {
            double px = e.getX();
            double py = e.getY();
            double sx = this.getX();
            double sy = this.getY();
            double dx = px - sx;
            double dy = py - sy;
            double dir = Math.atan2(dy, dx);
            PoisonGoo(x, y + 4, dir, 1);
            PoisonGoo(x, y + 4, dir, 1.5);
            PoisonGoo(x, y + 4, dir, 2);
            PoisonGoo(x, y + 4, dir, 2.5);
            fireRate = PoisonGoo.FIRE_RATE;
        }
    }	
    private void updateShooting() {
        List<Player> players = level.getPlayers(this, 150 - visability);
        if ((players.size() > 0) && fireRate <= 0) {
            double px = level.getClientPlayer().getX();
            double py = level.getClientPlayer().getY();
            double sx = this.getX();
            double sy = this.getY();
            double dx = px - sx;
            double dy = py - sy;
            double dir = Math.atan2(dy, dx);
            PoisonGoo(x, y + 4, dir, 1);
            PoisonGoo(x, y + 4, dir, 1.5);
            PoisonGoo(x, y + 4, dir, 2);
			level.getClientPlayer().incombat = true;
            fireRate = PoisonGoo.FIRE_RATE;
        }
    }
}
