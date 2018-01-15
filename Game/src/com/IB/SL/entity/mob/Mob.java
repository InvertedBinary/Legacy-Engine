package com.IB.SL.entity.mob;


import java.awt.Rectangle;
import java.io.Serializable;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import com.IB.SL.Boot;
import com.IB.SL.Game;
import com.IB.SL.VARS;
import com.IB.SL.entity.Entity;
import com.IB.SL.entity.PVector;
import com.IB.SL.entity.mob.hostile.Zombie;
import com.IB.SL.entity.particle.DefaultParticle;
import com.IB.SL.entity.projectile.DebugProjectile;
import com.IB.SL.entity.projectile.Projectile;
import com.IB.SL.entity.projectile.ThrowableRock;
import com.IB.SL.entity.projectile.WizardProjectile;
import com.IB.SL.entity.projectile.WizardProjectile2;
import com.IB.SL.graphics.Screen;
import com.IB.SL.level.Level;
import com.IB.SL.level.Node;
import com.IB.SL.level.TileCoord;
import com.IB.SL.util.Vector2i;

public abstract  class Mob extends Entity implements Serializable {
	
	
	transient protected boolean moving = false;
	transient private boolean solid;
	transient private List<Node> pathPull = null;
	transient double PathtimePull = 0;
	
    //EFFECTS LIST IN ENTITIES
	public long money;
	
	public int kills = 0;
	
	public enum DIRECTION {
		UP, DOWN, LEFT, RIGHT
	}
	
	public Mob() {
	}
	
	public Rectangle getBounds(Entity e) {
	    return new Rectangle((int) x() - (sprite.getWidth() >> 1), (int) y() - (sprite.getHeight() >> 1), sprite.getWidth(), sprite.getHeight());
	}
	
	public DIRECTION dir;
	public boolean canJump = false;
	
	public void move(double xa, double ya) {
		if (xa != 0 && ya != 0) {
			move(xa, 0);
			move(0, ya);
			return;
		}
		
		if (xa > 0) dir = DIRECTION.RIGHT;
		if (xa < 0) dir = DIRECTION.LEFT;
		if (ya > 0) dir = DIRECTION.DOWN;
		if (ya < 0) dir = DIRECTION.UP;

		while (xa != 0) {
			if (Math.abs(xa) > 1) {
				if (!collision(abs(xa), ya)) {
					this.setX(this.x() + abs(xa));
				}
				xa -= abs(xa);
			} else {
				if (!collision(abs(xa), ya)) {
					this.setX(this.x() + xa);
				}
				xa = 0;
			}
		}
		while (ya != 0) {
			if (Math.abs(ya) > 1) {
				if (!collision(xa, abs(ya))) {
					this.setY(this.y() + abs(ya));
					this.canJump = false;
				} else {
					if (this.vel().y > 0) {
					doFallDamage();
					this.vel().y = 0;
					this.canJump = true;
					}
				}
				ya -= abs(ya);
			} else {
				if (!collision(xa, abs(ya))) {
					this.setY(this.y() + ya);
					this.canJump = false;
				} else {
					if (this.vel().y > 0) {
					doFallDamage();
					this.vel().y = 0;
					this.canJump = true;
					}
				}
				ya = 0;
			}
		}
	}
	
	public void doFallDamage() {
		if (this.vel().y > 10) {
			this.mobhealth -= this.vel().y / 8;
			System.out.println(this.mobhealth);
		}
	}
		

	public void pull(Entity e, double rate) {
		double xpa = 0, ypa = 0;
		         double px = (int) e.x();
		         double py = (int) e.y();
		         Vector2i start = new Vector2i((int) x() >> VARS.TILE_BIT_SHIFT, (int)y() >> VARS.TILE_BIT_SHIFT);
		         Vector2i destination = new Vector2i(px / TileCoord.TILE_SIZE, py / TileCoord.TILE_SIZE);
		         pathPull = level.findPath(start, destination);
		         if (pathPull != null) {
		            if (pathPull.size() > 0) {
		               Vector2i vec = pathPull.get(pathPull.size() - 1).tile;
		               if (x() < vec.getX() << 4) xpa += rate;
		               if (x() > vec.getX() << 4) xpa -= rate;
		               if (y() < vec.getY() << 4) ypa += rate;
		               if (y() > vec.getY() << 4) ypa -= rate;
		            }
		         }
		      if (xpa != 0 || ypa != 0) {
		    	  move(xpa, ypa);
		    	  level.add(new DefaultParticle((int) (x()), (int) (y()), 4, 2, 0x000000, 4));
		    	  walking  = true;
		      } else {
		    	  walking = false;

		      }
			}
	
	public void pull(int x, int y, double rate) {
		double xpa = 0, ypa = 0;
		         double px = x;
		         double py = y;
		         Vector2i start = new Vector2i((int) x() >> VARS.TILE_BIT_SHIFT, (int)y() >> VARS.TILE_BIT_SHIFT);
		         Vector2i destination = new Vector2i(px / TileCoord.TILE_SIZE, py / TileCoord.TILE_SIZE);
		         pathPull = level.findPath(start, destination);
		         if (pathPull != null) {
		            if (pathPull.size() > 0) {
		               Vector2i vec = pathPull.get(pathPull.size() - 1).tile;
		               if (x < vec.getX() << 4) xpa += rate;
		               if (x > vec.getX() << 4) xpa -= rate;
		               if (y < vec.getY() << 4) ypa += rate;
		               if (y > vec.getY() << 4) ypa -= rate;
		            }
		         }
		      if (xpa != 0 || ypa != 0) {
		    	  move(xpa, ypa);
		    	  walking  = true;
		      } else {
		    	  walking = false;

		      }
			}
	
	
	
	private int abs(double value) {
		if (value < 0) return -1;
		return 1;
	}
	
		
	public abstract void update();
	
	public void shoot(double x, double y, double dir) {
		//dir *= 180 / Math.PI;
		Projectile WP = new WizardProjectile(x, y, dir);
		level.add(WP);
	}
	
	//TODO: Make faster with switch
	public void shoot(double x, double y, double dir, Player player, int id) {
		//dir *= 180 / Math.PI;
		
		if(id == 3) {
			Projectile RT = new ThrowableRock(x, y, dir, player);
			level.add(RT);
		} else if (id == 7777777) {
			level.add(new DebugProjectile(x, y, dir, player));
			if (Game.devModeInfoOn) {
				level.add(new Zombie((int)x / 16, (int)y / 16));
			} else if (Game.devModeOn) {
				level.addDoorTile((int)x / 16, (int)(y + 16) / 16);
			}
		}
	}
	
	/*public void fireAbility(double x, double y, double dir, Player player, Ability ab) {
		Ability sel = ab;
		sel.x = x;
		sel.y = y;
		sel.angle = dir;
		
		//level.add(sel);
	}*/
	
	protected void shootingtracker(double x, double y, double dir, boolean Particles) {
		Projectile WP2 = new WizardProjectile2(x, y, dir, Particles);
		level.add(WP2);

	}
	protected void shootingtracker(double x, double y, double dir) {
		Projectile WP2 = new WizardProjectile2(x, y, dir, false);
		level.add(WP2);

	}
	protected void RockTHROWING(double x, double y, double dir, Mob mob) {
		Projectile RT = new ThrowableRock(x, y, dir, mob);
		level.add(RT);

	}
	
	protected void Zombie(int x, int y) {
		Zombie zb = new Zombie(x, y);
		level.add(zb);

	}
	 
	protected boolean collision(double xa, double ya) {
		solid = false;
		for (int c = 0; c < 4; c++) {
			double xt = ((x() + xa) - c % 2 * 15 + 24) / TileCoord.TILE_SIZE;
			double yt = ((y() + ya) - c / 2 * 15 + 15) / TileCoord.TILE_SIZE;

			int ix = (int) Math.ceil(xt);
			int iy = (int) Math.ceil(yt);
			if (c % 2 == 0) ix = (int) Math.floor(xt);
			if (c / 2 == 0) iy = (int) Math.floor(yt);
			
			if (level.getTile(ix, iy).solid()) solid = true;
			if (level.getOverlayTile(ix, iy) != null) {
			if (level.getOverlayTile(ix, iy).solid()) solid = true;
			}
		}
		return solid;
	}
	
	
	
	public enum State {

	    Wander {
	        @Override
	        public Set<State> possibleFollowUps() {
	            return EnumSet.of(Attack, Evade);
	        }
	    },

	    Attack { 
	        @Override
	        public Set<State> possibleFollowUps() {
	            return EnumSet.of(Evade);
	        }
	    },

	    
	    Flee {
	        @Override
	        public Set<State> possibleFollowUps() {
	            return EnumSet.of(Wander, Attack);
	        }
	    },
	    
	    Evade // second to last  state 

	    ;
	    public Set<State> possibleFollowUps() {
	        //return EnumSet.noneOf(State.class);
	    	return EnumSet.of(Flee);
	    }
	}
	
	protected State entityState;	
	
	public abstract void render(Screen screen);



	public int oppositeDir(int odir) {
		int dir = -1;
		if (odir == 1) {
			dir = 0;
		}
		if (odir == 0) {
			dir = 1;
		}
		if (odir == 2) {
			dir = 3;
		}
		if (odir == 3) {
			dir = 2;
		}
		return dir;
	}





	public String getName() {
		return name;
	}

	public boolean isMoving() {
		return moving;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
	}

	public DIRECTION getDir() {
		return dir;
	}

	public void setDir(DIRECTION dir) {
		this.dir = dir;
	}

}




	

