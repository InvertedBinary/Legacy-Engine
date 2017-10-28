package com.IB.SL.entity.mob.hostile;

import java.util.List;

import com.IB.SL.Game;
import com.IB.SL.Game.gameState;
import com.IB.SL.entity.Entity;
import com.IB.SL.entity.inventory.ActiveEffects;
import com.IB.SL.entity.mob.Mob;
import com.IB.SL.entity.mob.Player;
import com.IB.SL.graphics.AnimatedSprite;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.graphics.SpriteSheet;
import com.IB.SL.graphics.UI.GUI;

public class VoidSlinger extends Mob{
	
	transient private AnimatedSprite down = new AnimatedSprite(SpriteSheet.VoidSlinger_down, 16, 16, 6);
	transient private AnimatedSprite up = new AnimatedSprite(SpriteSheet.VoidSlinger_up, 16, 16, 6);
	transient private AnimatedSprite left = new AnimatedSprite(SpriteSheet.VoidSlinger_left, 16, 16, 6);
	transient private AnimatedSprite right = new AnimatedSprite(SpriteSheet.VoidSlinger_right, 16, 16, 6);
	
	transient private GUI gui;
	transient List<Entity> entities;
	
	transient private AnimatedSprite animSprite = down;
	transient double xa = 0;
	transient double ya = 0;
	transient double time = 0;
	transient int fireRate = 0;
	transient int blastRate = 0;
	transient private int visability;
	transient public static boolean justspawnedtracker = false;
	transient List<Player> players;
	transient Player p;
	transient Entity tracking = null;
	
	public VoidSlinger(int x, int y) {
		this.maxhealth = 25;
		this.mobhealth = this.maxhealth;
		gui = new GUI();
		this.Exp = 18;
		this.x = x << 4;
		this.y = y << 4;
		this.id = 10;
		this.name = "Void Elemental";
		this.speed = 0;
		this.rarity = 4;
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
		if (entities.size() > 0) {
			if (players2.size() > 0) {
			Entity e = entities.get(0);
			tracking = e;
				if ((int)x < (int)e.getX() + 20) xa+= this.speed;
				if ((int)x > (int)e.getX() - 20) xa-= this.speed;
				if ((int)y < (int)e.getY() + 20) ya+= this.speed;
				if ((int)y > (int)e.getY() - 20) ya-= this.speed;

					}
				} else if (players.size() > 0) {
					p = players.get(0);
					tracking = p;
				//	Debug.drawLine(Game.getGame().getScreen(), (int)x / 16, (int)y / 16, (int)player.x / 16, (int)player.y / 16, 0xff000000, true);
					if ((int)x < (int)p.getX() + 20) xa+= this.speed;
					if ((int)x > (int)p.getX() - 20) xa-= this.speed;
					if ((int)y < (int)p.getY() + 20) ya+= this.speed;
					if ((int)y > (int)p.getY() - 20) ya-= this.speed;
					updateShooting();
					updateBlast();
					
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
         
        	 if (fireRate > 0)    fireRate--;
        	 if (blastRate > 0)    blastRate--;
        
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
		if (this.mobhealth < this.maxhealth) screen.renderSprite((int) x - 16, (int)y - 24, gui.renderMobHealthExperiment(this, 20), true);
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
            voidOoze(x, y + 4, dir + Math.PI / 9);
            voidOoze(x, y + 4, dir);
            voidOoze(x, y + 4, dir - Math.PI / 9);
			level.getClientPlayer().incombat = true;
            fireRate = 40;
        }
    }
    
    private void updateBlast() {
        List<Player> players = level.getPlayers(this, 150 - visability);  
        if ((players.size() > 0) && blastRate <= 0) {

        	voidOoze(x + Math.random() * 160 - 80, y + Math.random() * 160 - 80, Math.random() * Math.PI);
        	voidOoze(x + Math.random() * 160 - 80, y + Math.random() * 160 - 80, Math.random() * Math.PI);
        	voidOoze(x + Math.random() * 160 - 80, y + Math.random() * 160 - 80, Math.random() * Math.PI);
        	voidOoze(x + Math.random() * 160 - 80, y + Math.random() * 160 - 80, Math.random() * Math.PI);
        	voidOoze(x + Math.random() * 160 - 80, y + Math.random() * 160 - 80, Math.random() * Math.PI);
        	voidOoze(x + Math.random() * 160 - 80, y + Math.random() * 160 - 80, Math.random() * Math.PI);
        	voidOoze(x + Math.random() * 160 - 80, y + Math.random() * 160 - 80, Math.random() * Math.PI);
        	voidOoze(x + Math.random() * 160 - 80, y + Math.random() * 160 - 80, Math.random() * Math.PI);
        	voidOoze(x + Math.random() * 160 - 80, y + Math.random() * 160 - 80, Math.random() * Math.PI);
        	voidOoze(x + Math.random() * 160 - 80, y + Math.random() * 160 - 80, Math.random() * Math.PI);
            
			level.getClientPlayer().incombat = true;
            blastRate = 160;
        }
    }
}
