package com.IB.LE2.world.entity.mob.peaceful;

import java.util.List;
import java.util.Random;

import com.IB.LE2.media.graphics.AnimatedSprite;
import com.IB.LE2.media.graphics.Screen;
import com.IB.LE2.media.graphics.SpriteSheet;
import com.IB.LE2.world.entity.Entity;
import com.IB.LE2.world.entity.mob.Mob;
import com.IB.LE2.world.entity.mob.Player;
import com.IB.LE2.world.level.Node;

@Deprecated
public class Horse extends Mob {

	transient private AnimatedSprite left = new AnimatedSprite(SpriteSheet.zombie_left, 32, 32, 3);
	transient private AnimatedSprite right = new AnimatedSprite(SpriteSheet.zombie_right, 32, 32, 3);

	transient private AnimatedSprite animSprite = left;
	transient double xa = 0;
	transient double ya = 0;
	transient final double ox, oy;
	transient double time = 0;
	transient public int fireRate = 0;
	transient public static boolean justspawned = false;
	transient private List<Node> path = null;
	transient public boolean walking2 = false;
	transient Player p = null;
	transient Player lp = null;
	private int f_id;
	transient private int breatheTime = 519;

	public void onLoad(Entity e) {
		this.f_id = ((Horse)e).f_id;
		
	  //    if (this.f_id == Game.getGame().getLevel().getClientPlayer().horse_following.f_id) {
	    	 // this.lp = Game.getGame().getLevel().getClientPlayer();
	    //  }
    	}
	
	public Horse(int x, int y) {
		this.mobhealth = 150;
		this.setX(x << 4);
		this.setY(y << 4);
		this.ox = x;
		this.oy = y;
		this.f_id = (new Random().nextInt(5000));
		this.name = "Horse";
		this.invulnerable = true;
		this.ySort = false;
		//sprite = Sprite.Occulus;
	}

	private void move() {
	      /*List<Player> players = level.getPlayers(this, 20);
	      List<Player> players2 = level.getPlayers(this, 90);

	      if (players.size() > 0) {
	    	  Player p = players.get(0);
		      if (p != null && lp == null) {
		    	  lp = p;
		      }
		      Game.get().getPlayer().horse_following = this;
	    	  
	    	  if (p.ridingOn == null || p.ridingOn == this) {
	    			  
	    	  breatheTime++;
	    	  if (breatheTime % 520 == 0) {
	    		  Sound.Play(Sound.horse_Whinny, false);
	    	  }
				if (!Game.get().key.Sprint) {					
				p.animSprite.setFrame(0);
				
				p.speed = 3;
				p.riding = true;
				p.ridingOn = this;
		    	  walking2 = false;

				this.x = p.getX();
				this.y = p.getY();
				this.walking = p.walking;
				
				} else {
					p.speed = 1;
					p.riding = false;
					p.ridingOn = null;
					this.breatheTime = 519;
				}
				}
	      } */

	  /*   if (Game.getGame().getPlayer().horse_following != null) {
	     if (lp != null && p == null && (this.f_id == Game.getGame().getPlayer().horse_following.f_id)) {
	    	 xa = 0;
	    	 ya = 0;
				double px = (int) lp.getX();
				double py = (int) lp.getY();
				Vector2i start = new Vector2i((int) getX() >> Game.TILE_BIT_SHIFT, (int) getY() >> Game.TILE_BIT_SHIFT);
				Vector2i destination = new Vector2i((px / TileCoord.TILE_SIZE), (py / TileCoord.TILE_SIZE));
				if (time % 1 == 0)
					path = level.findPath(start, destination);
				if (path != null) {
					if (path.size() > 0 && (players2.size() <= 0)) {
						Vector2i vec = path.get(path.size() - 1).tile;
						if ((x) < (vec.getX()) << 4) {
							xa += 0.5;
							this.animSprite = this.right;
						}
						if ((x) > (vec.getX()) << 4) {
							xa -= 0.5;
							this.animSprite = this.left;
						}
						if ((y) < (vec.getY()) << 4) {
							ya += 0.5;
						}
						if ((y) > (vec.getY()) << 4) {
							ya -= 0.5;
						}
					}
				}
				if (xa != 0 || ya != 0) {
					move(xa, ya);
					this.walking2 = true;
				}else {
					this.walking2 = false;
				
				}
	      }
	      }*/
	      
	   }
	public void update() {
		if (this.dir == DIRECTION.LEFT) {
			animSprite = left;
		}
		if (this.dir == DIRECTION.RIGHT) {
			animSprite = right;					
		}
		
		time++;
		move();
		
		/*if (Game.get().getPlayer().ridingOn != this) {
			walking = false;
		}*/
		
		if (walking || walking2) 
			animSprite.update();
		else
			animSprite.setFrame(0);

	}
	public void render(Screen screen) {
		sprite = animSprite.getSprite();
		if (this.dir == DIRECTION.RIGHT) {
			screen.render32Mob((int) (x() - 14), (int) (y() - 15), this);
		} else {
			screen.render32Mob((int) (x() - 18), (int) (y() - 15), this);
		}
	}

}
