package com.IB.SL.entity.mob.hostile;

import java.util.List;
import java.util.Random;

import com.IB.SL.Game;
import com.IB.SL.Game.gameState;
import com.IB.SL.entity.Entity;
import com.IB.SL.entity.inventory.ActiveEffects;
import com.IB.SL.entity.mob.Mob;
import com.IB.SL.entity.mob.Player;
import com.IB.SL.entity.mob.PlayerMP;
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

public class VoidCharger extends Mob {

	transient private AnimatedSprite down = new AnimatedSprite(SpriteSheet.VC_down,
			16, 16, 6);
	/*private AnimatedSprite up = new AnimatedSprite(SpriteSheet.VC_up, 16,
			16, 0);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.VC_left,
			16, 16, 0);
	private AnimatedSprite right = new AnimatedSprite(
			SpriteSheet.Slimey_right, 16, 16, 0);*/

	transient private AnimatedSprite animSprite = down;
	transient protected static java.util.Random Random = new Random();
	transient public static java.util.Random random = Random;
	transient double xa = 0;
	transient double ya = 0;
	transient double time = 0;
	transient public int fireRate = 0;
	transient public static boolean justspawned = false;
	transient private List<Node> path = null;
	transient private GUI gui;
	transient public int Explodetime = 0;
	
	public VoidCharger(int x, int y) {
		this.xBound = 8;
		this.yBound = 6;
		this.xOffset = 1;
		this.yOffset = 7;
		
		this.maxhealth = 12;
		this.mobhealth = this.maxhealth;
		gui = new GUI();
		this.Exp = 15;
		this.x = x << 4;
		this.y = y << 4;
		this.id = 1220;
		this.name = "Void Charger";
		this.speed = 1.2;
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
	         Vector2i start = new Vector2i((int) getX() >> Game.TILE_BIT_SHIFT, (int)getY() >> Game.TILE_BIT_SHIFT);
	         Vector2i destination = new Vector2i(px / TileCoord.TILE_SIZE, py / TileCoord.TILE_SIZE);
	         if (time % 1 == 0) path = level.findPath(start, destination);
	         if (path != null) {
	            if (path.size() > 0) {
	               Vector2i vec = path.get(path.size() - 1).tile;
	               if (x - 1 < vec.getX() << 4) xa++;
	               if (x > vec.getX() << 4) xa--;
	               if (y - 1 < vec.getY() << 4) ya++;
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
	int spawnTime = 1;
	public void update() {
		if (time % 8 == 0) {
			this.hurt = false;
		}
		if (spawnTime != 0) {
			spawnTime++;
		}
		
		if (spawnTime > 100) {
			spawnTime = 0;
		}
		
		if(spawnTime == 0) {
			updateShooting();
		}
        if (fireRate > 0) {
            fireRate--;
        }
		time++;
		move();
		//if (walking)
			animSprite.update();
			animSprite.update();
		//else
			//animSprite.setFrame(0);
		if (ya < 0) {

		/*	animSprite = up;
			dir = DIRECTION.UP;*/
		} else if (ya > 0) {
			dir = DIRECTION.DOWN;
		}
		animSprite = down;
	/*	if (xa < 0) {
			animSprite = left;
			dir = DIRECTION.LEFT;
		} else if (xa > 0) {
			animSprite = right;
			dir = DIRECTION.RIGHT;
		}*/
		
	}
	public void render(Screen screen) {
		if (this.mobhealth < this.maxhealth) screen.renderSprite((int) x - 16, (int)y - 20, gui.renderMobHealthExperiment(this, 20), true);
		this.xOffset =  -8;
		this.yOffset =  -15;
		sprite = animSprite.getSprite();
		screen.renderMobSprite((int) (x + xOffset), (int) (y + yOffset), this);
		if (Game.getGame().gameState == gameState.INGAME_A) {
			screen.drawRect((int)x + xOffset, (int)y + yOffset, sprite.getWidth(), sprite.getHeight(), 0xFF0000, true);
			}
	}
	private void updateShooting() {
	     List<Player> players2 = level.getPlayers(this, 25);
	      if(players2.size() > 0) {
	    	  Explodetime++;
	      } else {
	    	  Explodetime = 0;
	      }
		if (Explodetime > 10) {
			updateShooting2();
			remove();
			level.add(new VoidParticleSpawner((int) (x- 8), (int) (y - 15), 25, 20, level));
			if (players2.size() > 0) {
			for(int i = 0; i < players2.size(); i++) {
				level.damagePlayer((int)this.getX(), (int)this.getY(), (PlayerMP)players2.get(i), 0, 8, name, 0);

				Sound.Play(Sound.Explosion4, false);
			}
			}
		}
		
	}


/*private void updateShooting() {
    List<Player> players2 = level.getPlayers(this, 25);
     if(players2.size() > 0) {
   	  Explodetime++;
     } else {
   	  Explodetime = 0;
     }
	if (Explodetime > 10) {
		remove();
		updateShooting2();
		level.add(new VoidParticleSpawner((int) (x- 8), (int) (y - 15), 25, 20, level));
		for(int i = 0; i < players2.size(); i++) {
			level.damage(players2.get(i), 0, 10, this.name, 0);;
			Sound.Play(Sound.Explosion4, false);
		}
	}
	
}*/

private void updateShooting2() {
List<Entity> entities = level.getEntities(this, 25);
	//level.add(new VoidParticleSpawner((int) (x- 8), (int) (y - 15), 25, 20, level));
	for(int i = 0; i < entities.size(); i++) {
		if (!(entities.get(i) instanceof Player) && !entities.get(i).invulnerable) {
			try {
				level.damage((int)this.x, (int)this.y, (Mob) entities.get(i), 0, 8, this.name, 0);
			} catch (Exception e) {
				System.err.println("?!! Mob Cast Exception! " + entities.get(i));
		}
		//} else {
		//Sound.Play(Sound.Explosion4, false);
		//}
		}
	}
	
}}
