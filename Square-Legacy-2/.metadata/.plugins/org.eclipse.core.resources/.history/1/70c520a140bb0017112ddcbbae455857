package com.IB.SL.entity.mob.peaceful;

import java.util.List;

import com.IB.SL.Game;
import com.IB.SL.entity.Entity;
import com.IB.SL.entity.inventory.ActiveEffects;
import com.IB.SL.entity.mob.Mob;
import com.IB.SL.entity.mob.Player;
import com.IB.SL.graphics.AnimatedSprite;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.graphics.SpriteSheet;
import com.IB.SL.graphics.font8x8;
import com.IB.SL.level.Node;
import com.IB.SL.level.TileCoord;
import com.IB.SL.util.Sound;
import com.IB.SL.util.Vector2i;

public class Guard extends Mob{
	
	transient private AnimatedSprite down = new AnimatedSprite(SpriteSheet.Guard_down, 16, 16, 3);
	transient private AnimatedSprite up = new AnimatedSprite(SpriteSheet.Guard_up, 16, 16, 3);
	transient  private AnimatedSprite left = new AnimatedSprite(SpriteSheet.Guard_left, 16, 16, 5);
	transient private AnimatedSprite right = new AnimatedSprite(SpriteSheet.Guard_right, 16, 16, 5);
	transient  private AnimatedSprite animSprite = down;
	
	transient  private int time = 0;
	transient  double xa = 0;
	transient  double ya = 0;
	transient  private List<Node> path = null;
	transient  List<Entity> entities = null;
	transient  List<Player> players = null;

	transient  public TileCoord p1; //= new TileCoord(43, 75);
	transient  public TileCoord p2; //= new TileCoord(60, 75);
	transient  public boolean p1Bx = true, p2Bx = false;
	transient  public boolean p1By = true, p2By = false;
	private boolean patroling = true;
	transient  private int timeUntilPatrol = 299;
	transient  private int time_swordSound = 0;
	transient  public boolean follower = false;
	transient  Vector2i start;
	transient  Vector2i destination;
	 
	public void onLoad(Entity e) {
		this.p1 = ((Guard)e).p1;
		this.p2 = ((Guard)e).p2;
		this.follower = ((Guard)e).follower;
		this.patroling = ((Guard)e).patroling;
		basicInitialization((int)x >> 4, (int)y >> 4, p1, p2);
}
	
	public Guard(int x, int y) {
		basicInitialization(x, y, null,null);
	}
	public Guard(int x, int y, TileCoord point1, TileCoord point2) {
		basicInitialization(x, y, point1, point2);
	}
	
	public Guard(int x, int y, boolean follower) {
		this.follower = follower;
		basicInitialization(x, y, null, null);
	}
	 
	private void basicInitialization(int x, int y, TileCoord point1, TileCoord point2) {
		this.x = x << 4;
		this.y = y << 4;
		this.p1 = point1;
		this.p2 = point2;
		this.id = 5439;
		this.name = "Guard";
		this.hostility = hostility.NEU;
		this.maxhealth = 20;
		this.mobhealth = maxhealth;
		this.speed = 0.5;
		new font8x8();
		sprite = Sprite.playerback;
		this.effects = new ActiveEffects(7, this);
	}
	
	
	public void stab() {
		try {
			List <Entity> ent = level.getEntities(this, 20, entities);
			List <Player> p = level.getPlayers(this, 100);

			if (ent.get(0).hostility == hostility.AGR) {
			if (time % 30 == 0) {
				if (p.size() > 0) {					
				  if (time_swordSound > 55) {
		    		  Sound.Play(Sound.Sword, false);
		    		  time_swordSound = 0;
		    	  }
				}
			for (int i = 0; i < ent.size(); i++) {
				Game.getGame().getLevel().damage((int)x, (int)y, (Mob)ent.get(0), 0, 1, name, 0);
					}
				}
			}
		} catch (Exception e) {
			
		}
		}

	private void follow() {
			 if (entities.size() > 0) {
		    	  xa = 0;
		    	  ya = 0;
		         double px = entities.get(0).getX();
		         double py = (int) entities.get(0).getY();
		          start = new Vector2i((int) getX() >> Game.TILE_BIT_SHIFT, (int)getY() >> Game.TILE_BIT_SHIFT);
		          destination = new Vector2i(px / TileCoord.TILE_SIZE, py / TileCoord.TILE_SIZE);
		         /*if (time % 1 == 0) */path = level.findPath(start, destination);
		         if (path != null) {
		            if (path.size() > 0) {
		               Vector2i vec = path.get(path.size() - 1).tile;
		               if (x < vec.getX() << 4) xa++;
		               if (x > vec.getX() << 4) xa--;
		               if (y < vec.getY() << 4) ya++;
		               if (y > vec.getY() << 4) ya--;
		            }
		         }
		      } else if (players.size() > 0 && follower) {
		    	  xa = 0;
			         ya = 0;
			         double px = level.getPlayerAt(0).getX();
			         double py = (int) level.getPlayerAt(0).getY();
			         Vector2i start = new Vector2i((int) getX() >> Game.TILE_BIT_SHIFT, (int)getY() >> Game.TILE_BIT_SHIFT);
			         Vector2i destination = new Vector2i(px / TileCoord.TILE_SIZE, py / TileCoord.TILE_SIZE);
			        /* if (time % 1 == 0) */path = level.findPath(start, destination);
			         if (path != null) {
			            if (path.size() > 0) {
			               Vector2i vec = path.get(path.size() - 1).tile;
			               if (x < vec.getX() << 4) xa++;
			               if (x > vec.getX() << 4) xa--;
			               if (y < vec.getY() << 4) ya++;
			               if (y > vec.getY() << 4) ya--;
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
	
	 private void move() {
		 if (players.size() > 0) {
		 if (entities.size() > 0) {
	    	  patroling = false;
	    	  xa = 0;
	    	  ya = 0;
	         double px = entities.get(0).getX();
	         double py = (int) entities.get(0).getY();
	          start = new Vector2i((int) getX() >> Game.TILE_BIT_SHIFT, (int)getY() >> Game.TILE_BIT_SHIFT);
	          destination = new Vector2i(px / TileCoord.TILE_SIZE, py / TileCoord.TILE_SIZE);
	      /*   if (time % 1 == 0) */path = level.findPath(start, destination);
	         if (path != null) {
	            if (path.size() > 0) {
	               Vector2i vec = path.get(path.size() - 1).tile;
	               if (x < vec.getX() << 4) xa++;
	               if (x > vec.getX() << 4) xa--;
	               if (y < vec.getY() << 4) ya++;
	               if (y > vec.getY() << 4) ya--;
	            }
	         }
	      }
	      } 
		 
		 if (entities.size() <= 0 && Game.getGame().frames > 40) {
	    	  	{
	    	  xa = 0;
	    	  ya = 0;
	    	  
	    	 /* if ((time % 1) == 0) { */
	    	  if (patroling == false) {
	    		  double px = (int) p1.x();
	 	         double py = (int) p1.y();
	 	          start = new Vector2i((int) getX() >> Game.TILE_BIT_SHIFT, (int)getY() >> Game.TILE_BIT_SHIFT);
	 	          destination = new Vector2i(px / TileCoord.TILE_SIZE, py / TileCoord.TILE_SIZE);
	 	      /*   if (time % 1 == 0) */path = level.findPath(start, destination);
	 	         if (path != null) {
	 	            if (path.size() > 0) {
	 	               Vector2i vec = path.get(path.size() - 1).tile;
	 	              if (x < vec.getX() << 4) xa++;
		               if (x > vec.getX() << 4) xa--;
		               if (y < vec.getY() << 4) ya++;
		               if (y > vec.getY() << 4) ya--;
		               
	 	            }
	 	         }
	    	  
	    		  
	    		  
	    	  } else if (patroling == true) {
	    	  if (p1Bx) {
	    		  xa++;
	    	  } else if (p2Bx) {
	    		  xa--;
	    	  }
	    	  
	    	  if (p1By) {
	    		  ya++;
	    	  } else if (p2By) {
	    		  ya--;
	    	  }
	    	  }
		 	// }
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
		
		players = level.getPlayers(this, 150);
		
		if (players.size() > 0) {			
		entities = level.getEntities(this, 135, HOSTILITY.AGR);
		} else {
			entities = level.getEntities(this, 135);
		}

		if (time_swordSound > 500) {
			time_swordSound = 0;
		}
		time_swordSound++;
		
		
		//timeUntilPatrol++;
		//System.out.println("PATROLLING? : " + patroling);
		
		
		
	//	if (p1.x() != p2.x() && p2.y() != p1.y()) {
		if (p1 == null || p2 == null) {
				follow();				
		} else {
		if (this.dir != dir.RIGHT) {			
		if (x >= p1.x() && this.x <= this.p2.x() && this.y >= this.p1.y() && this.y <= p1.y()) {
			patroling = true;
		}
	}
		if (this.dir != dir.LEFT) {
			if (x <= p2.x() && this.x >= this.p1.x() && this.y <= this.p2.y() && this.y >= p1.y()) {
				patroling = true;
			}
		}
		
		if (this.dir == dir.UP) {			
		if (y >= p1.y() && this.x <= this.p2.y() && this.x >= this.p1.x() && this.x <= p1.x()) {
			patroling = true;
		}
	}
		if (this.dir == dir.DOWN) {
			if (y <= p2.y() && this.y >= this.p1.y() && this.x <= this.p2.x() && this.x >= p1.x()) {
				patroling = true;
			}
		}
		
		
		/*if (time % 1500 == 0) {
		if (!moving) {
			patroling = true;
			timeUntilPatrol = 0;
		}
		}*/
		
		if (x <= p1.x()) {
			
			p1Bx = true;
			p2Bx = false;
		}
		
		if (x > p2.x()) { 
			p2Bx = true;
			p1Bx = false;
		}
		
		if (y <= p1.y()) {
			p1By = true;
			p2By = false;
		}
		
		if (y > p2.y()) { 
			p2By = true;
			p1By = false;
		}
		/*} else {
			p1Bx = false;
			p1By = false;
			p2Bx = false;
			p2By = false;


		}*/
		
		
			move();									
		}
		stab();
		time++;
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
		this.xOffset = -8;
		this.yOffset = -16;
		if (this.mobhealth < this.maxhealth) screen.renderSprite((int) x - 16, (int)y - 24, Game.getGame().gui.renderMobHealthExperiment(this, 20), true);

		sprite = animSprite.getSprite();
		screen.renderMobSprite((int) (x + xOffset), (int) (y + yOffset), this);
		

	}

}
