package com.IB.LE2.world.entity.mob;


import java.awt.Rectangle;
import java.io.Serializable;
import java.util.EnumSet;
import java.util.Set;

import com.IB.LE2.Boot;
import com.IB.LE2.media.graphics.Screen;
import com.IB.LE2.util.VARS;
import com.IB.LE2.util.shape.LineSegment;
import com.IB.LE2.util.shape.Vertex;
import com.IB.LE2.world.entity.Entity;
import com.IB.LE2.world.level.worlds.TiledLevel;

public abstract  class Mob extends Entity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	transient protected boolean moving = false;
	transient private boolean solid;

	//TODO: Time since player was last near this entity
	// Stop updating them over such an interval to save on performance
	transient int waketime = 0;
	
    //TODO: MOVE EFFECTS LIST INTO ENTITIES
	
	//TODO: Create a list of 'stats' that can just be created and updated with
	// tags and lua instead of hard-coded variables
	
	public enum DIRECTION {
		UP, DOWN, LEFT, RIGHT
	}
	
	public Mob() { }
	
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
					this.yAxisCollisionResponse(ya);
				}
				ya -= abs(ya);
			} else {
				if (!collision(xa, abs(ya))) {
					this.setY(this.y() + ya);
					this.canJump = false;
				} else {
					this.yAxisCollisionResponse(ya);
				}
				ya = 0;
			}
		}
	}
	
	public void yAxisCollisionResponse(double ya) {
		if (this.vel().y() > 0) {
			DoFallDamage();
			this.vel().y(0);
			this.canJump = true;
		}
		if (ya < 0) {
			this.vel().y(VARS.Ag);
			this.canJump = false;
		}
	}
	
	public void DoFallDamage() {
		if (this.vel().y() > 10) {
			this.TakeDamage(this.vel().y() / 8);
		}
	}
	
	
	public void TakeDamage(double pts) {
		//this.mobhealth -= pts;
	}

	/*public void pull(Entity e, double rate) {
		double xpa = 0, ypa = 0;
		double px = (int) e.x();
		double py = (int) e.y();
		Vector2i start = new Vector2i((int) x() >> VARS.TILE_BIT_SHIFT, (int) y() >> VARS.TILE_BIT_SHIFT);
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
			walking = true;
		} else {
			walking = false;

		}
	}
	
	public void pull(int x, int y, double rate) {
		double xpa = 0, ypa = 0;
		double px = x;
		double py = y;
		Vector2i start = new Vector2i((int) x() >> VARS.TILE_BIT_SHIFT, (int) y() >> VARS.TILE_BIT_SHIFT);
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
			walking = true;
		} else {
			walking = false;

		}
	}*/
	
	
	
	private int abs(double value) {
		if (value < 0) return -1;
		return 1;
	}
	
		
	public abstract void update();
	
	public boolean sliding = false;
	
	protected boolean collision(double xa, double ya) {
		solid = false;

		double xt = ((x() + xa));
		double yt = ((y() + ya));
		
		BottomBound = new LineSegment(
			new Vertex((float) xt + 22f, (float) yt + 60f), 
			new Vertex((float)xt + 40f, (float)yt + 60f)
		);
		
		/*feetLine = new LineSegment(
				new Vertex((float) xt + 24f, (float) yt + 60f), 
				new Vertex((float)xt + 38f, (float)yt + 60f)
		);*/

		if (((TiledLevel) Boot.getLevel()).solid_geometry == null) {
			return false;
		}

		for (int i = 0; i < ((TiledLevel) Boot.getLevel()).solid_geometry.size(); i++) {
			LineSegment ls = ((TiledLevel) Boot.getLevel()).solid_geometry.get(i);
			
			boolean collides = false;
			if (i == 48)
				collides = BottomBound.CollidesWithLine(ls, true);
			else 
				collides = BottomBound.CollidesWithLine(ls, false);
			
			
			if (collides) {
				//is the player walking with the slope or down it?
				int slopePolarity = PolarityOf(ls.slope);
				int xaPolarity = PolarityOf(xa);
				
				/*if (this.pos().y() == ls.top_pt.y + 1) {
					move(0, -1);
				}*/

				if (xa != 0) {
					if (ls.slope == 0 || Math.abs(ls.slope) == Double.POSITIVE_INFINITY) {
						return true;
					}
					
					if (slopePolarity == xaPolarity) {
						//They are walking with the slope?
						if (slopePolarity == 1) {
							//double dy = ls.slope * (feetLine.right_pt.x - ls.left_pt.x);
							//System.out.println("Y: " + y() + " DY: " + dy + " ls.left_pt.x : plx): " + ls.left_pt.x + " : " + feetLine.left_pt.x);
							//move(0, -Math.abs(6));
						}
						move(0, -Math.abs(1 - (vx() * (ls.slope / 10))));
					} else {
						//They are walking down the slope?
						// Nothing special needs to happen really..
					}
				}
				
				return true;
			}
		}
		return false;
	}
	
	public int PolarityOf(double num) {
		if (num < 0)
			return -1;
		if (num > 0)
			return 1;
		
		return 0;
	}
	
	@Deprecated
	protected boolean old_polyline_collision(double xa, double ya) {
			boolean prev_solid = solid;
			solid = false;

			double xt = ((x() + xa));
			double yt = ((y() + ya));
			//AABB aabb = new AABB(this.getBounds());
			//aabb.moveTo(xt, yt);
			BottomBound = new LineSegment(new Vertex((float) xt + 24f, (float) yt + 60f), new Vertex((float)xt + 38f, (float)yt + 60f)); //=>38, 60
			//TODO: Add collision for parallel lines 
			if (((TiledLevel) Boot.getLevel()).solid_geometry == null) {
				return false;
			}

			for (int i = 0; i < ((TiledLevel) Boot.getLevel()).solid_geometry.size(); i++) {
				LineSegment ls = ((TiledLevel) Boot.getLevel()).solid_geometry.get(i);

				if (ls.CollidesWithLine(BottomBound)) {
					//System.out.println(i + ":: " + ls.origin.x);
					if (Math.abs(ls.slope) <= 3) {
						if (xa != 0 && ya == 0) {
							if (ls.slope != 0) {
							move(0, -Math.abs(1 - (vx() * (ls.slope / 10))));
							// this.y(this.y() );
							sliding = false;
							}
						}
					} else if (Math.abs(ls.slope) > 3 && Math.abs(ls.slope) < Double.POSITIVE_INFINITY && (BottomBound.midpoint().x > ls.left_pt.x && BottomBound.midpoint().x < ls.right_pt.x)) {
						if (canJump == true) {
						this.sliding = true;
							move(ls.slope / 4, 0);
						}
					} else if (Math.abs(ls.slope) == Double.POSITIVE_INFINITY) {
						this.sliding = false;
						if (xa != 0 && ya == 0) {
							this.solid = false;
						}
					} else {
						sliding = false;
					}
					solid = true;
				}
			}
			return solid;
		}

	/*protected boolean collision(double xa, double ya) {
		solid = false;
		for (int c = 0; c < 4; c++) {
			double xt = ((x() + xa) - c % 2 * 15 + 24) / TileCoord.TILE_SIZE;
			double yt = ((y() + ya) - c / 2 * 2 + 24) / TileCoord.TILE_SIZE;
			
			int ix = (int) Math.ceil(xt);
			int iy = (int) Math.ceil(yt);
			if (c % 2 == 0) ix = (int) Math.floor(xt);
			if (c / 2 == 0) iy = (int) Math.floor(yt);
			
			double dya = (ya + ((this.y() + 32)/32));
			double dif = (iy) - dya;
			//System.out.println("IY: " + iy + " , " + ya + " , " + dya + " DIF: " + dif);
			
			if (level.getTile(ix, iy).solid()) { 
				solid = true;
				continue;
			}
			
			/*if (level.getOverlayTile(ix, iy) != null) {
			if (level.getOverlayTile(ix, iy).solid()) { 
				solid = true;
				continue;
			}
			
			if (level.getOverlayTile(ix, iy).jumpThrough() && (ya > 0) && Math.abs(dif) < .31) {
				solid = true;
			}
			
			}*//*
			if (level.getTile(ix, iy).jumpThrough() && (ya > 0) && Math.abs(dif) < .31) {
				solid = true;
			}
			
		}
		return solid;
	}*/
	
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




	

