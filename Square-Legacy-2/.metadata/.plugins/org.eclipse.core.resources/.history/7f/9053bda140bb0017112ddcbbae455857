package com.IB.SL.entity.mob;


import java.awt.Rectangle;
import java.io.Serializable;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import com.IB.SL.Game;
import com.IB.SL.entity.Entity;
import com.IB.SL.entity.abilities.Ability;
import com.IB.SL.entity.abilities.Equilibrium;
import com.IB.SL.entity.abilities.GoldenOrb;
import com.IB.SL.entity.abilities.HealingSpell;
import com.IB.SL.entity.abilities.RadialBlast;
import com.IB.SL.entity.abilities.SearingBolt;
import com.IB.SL.entity.abilities.SummonFamiliar;
import com.IB.SL.entity.inventory.ActiveQuests;
import com.IB.SL.entity.inventory.Equipment;
import com.IB.SL.entity.inventory.Inventory;
import com.IB.SL.entity.mob.hostile.Zombie;
import com.IB.SL.entity.particle.DefaultParticle;
import com.IB.SL.entity.projectile.ArcherArrow;
import com.IB.SL.entity.projectile.BaskerBolt;
import com.IB.SL.entity.projectile.BeetleSting;
import com.IB.SL.entity.projectile.BlastingBolt;
import com.IB.SL.entity.projectile.BlinkBolt;
import com.IB.SL.entity.projectile.CopperShockwave;
import com.IB.SL.entity.projectile.CrystallineBolt;
import com.IB.SL.entity.projectile.DeathStalkBolt;
import com.IB.SL.entity.projectile.DebugProjectile;
import com.IB.SL.entity.projectile.DemonOrb;
import com.IB.SL.entity.projectile.FairyBolt;
import com.IB.SL.entity.projectile.FairyHeal;
import com.IB.SL.entity.projectile.FireBall;
import com.IB.SL.entity.projectile.FireBlast;
import com.IB.SL.entity.projectile.FlotSpray;
import com.IB.SL.entity.projectile.FlySting;
import com.IB.SL.entity.projectile.FrostBomb;
import com.IB.SL.entity.projectile.FrostBreath;
import com.IB.SL.entity.projectile.FrostShard;
import com.IB.SL.entity.projectile.GazerBolt;
import com.IB.SL.entity.projectile.MageBolt;
import com.IB.SL.entity.projectile.MendersBolt;
import com.IB.SL.entity.projectile.NullWave;
import com.IB.SL.entity.projectile.Optical;
import com.IB.SL.entity.projectile.OrbweaversBolt;
import com.IB.SL.entity.projectile.PoisonGoo;
import com.IB.SL.entity.projectile.PossibilityBolt;
import com.IB.SL.entity.projectile.Projectile;
import com.IB.SL.entity.projectile.RedShockwave;
import com.IB.SL.entity.projectile.RefractorsBolt;
import com.IB.SL.entity.projectile.Rock;
import com.IB.SL.entity.projectile.SageBolt;
import com.IB.SL.entity.projectile.SilversmithBolt;
import com.IB.SL.entity.projectile.SlimeBall;
import com.IB.SL.entity.projectile.SlowTrap;
import com.IB.SL.entity.projectile.StoneSpike;
import com.IB.SL.entity.projectile.StunBall;
import com.IB.SL.entity.projectile.StunTrap;
import com.IB.SL.entity.projectile.SwarmMine;
import com.IB.SL.entity.projectile.SwarmShoot;
import com.IB.SL.entity.projectile.SwarmStun;
import com.IB.SL.entity.projectile.ThrowableRock;
import com.IB.SL.entity.projectile.TrappersOrb;
import com.IB.SL.entity.projectile.VoidOoze;
import com.IB.SL.entity.projectile.WizardProjectile;
import com.IB.SL.entity.projectile.WizardProjectile2;
import com.IB.SL.entity.projectile.shootingtrackerSpam;
import com.IB.SL.graphics.Screen;
import com.IB.SL.level.Level;
import com.IB.SL.level.Node;
import com.IB.SL.level.TileCoord;
import com.IB.SL.util.Vector2i;

public abstract  class Mob extends Entity implements Serializable {
	
	
	transient protected boolean moving = false;
	transient public boolean walking = false;
	transient public boolean incombat;
	transient private boolean solid;
	public String name;
	transient private List<Node> pathPull = null;
	transient double PathtimePull = 0;
	
	public transient Inventory inventory;
	public transient Equipment equipment;
	public transient ActiveQuests quests;
    //EFFECTS LIST IN ENTITIES
	public long money;
	
	public double stat_ATC;
	public double stat_DEF;
	public double stat_VIT;
	public double stat_WIS;
	public double stat_EDR;
	public double stat_MAT;
	public double stat_MDF;
	public double stat_AGI;
	
	public int rarity = -1;
	
	public double stat_Health;
	public double stat_Mana;
	public double stat_Stam;
	
	
	
	public double stat_item_ATC;
	public double stat_item_DEF;
	public double stat_item_VIT;
	public double stat_item_WIS;
	public double stat_item_EDR;
	public double stat_item_MAT;
	public double stat_item_MDF;
	public double stat_item_AGI;
	
	public double stat_base_ATC;
	public double stat_base_DEF;
	public double stat_base_VIT;
	public double stat_base_WIS;
	public double stat_base_EDR;
	public double stat_base_MAT;
	public double stat_base_MDF;
	public double stat_base_AGI;
	
	public double stat_item_Health;
	public double stat_item_Mana;
	public double stat_item_Stam;

	public int kills = 0;
	
	public enum DIRECTION {
		UP, DOWN, LEFT, RIGHT
	}
	
	public Mob() {
	}
	
	public Rectangle getBounds(Entity e) {
	    return new Rectangle((int) x - (sprite.getWidth() >> 1), (int) y - (sprite.getHeight() >> 1), sprite.getWidth(), sprite.getHeight());
	}
	
	public DIRECTION dir;
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
		
		try {
			
			if (!Level.Overworld && level.getPlayersFixedBool((int)Game.getGame().getPlayer().getX() - 8, (int)Game.getGame().getPlayer().getY() - 15, 20)) {
	//	List<PlayerMP> players = level.getPlayersFixed((int)this.x + 8, (int) this.y + 8, 20);
			while (xa != 0) {
				if (Math.abs(xa) > 1) {
					if (!collision(abs(xa), ya)/* && !eCol(convert(xa), 0)*/) {
							if (!eCol(convert(xa), 0)) {
								this.x += abs(xa);
							}
					}
					xa -= abs(xa);
				} else {
					if (!collision(abs(xa), ya)/* && !eCol(convert(xa), 0)*/) {
						if (!eCol(convert(xa), 0)) {
						this.x += xa;
						}
				}
					xa = 0;
			}
		}
		while (ya != 0) {
			if (Math.abs(ya) > 1) {
				if (!collision(xa, abs(ya))/* && !eCol(0, convert(ya))*/) {
					if (!eCol(0, convert(ya))) {
						this.y += abs(ya);
					}
				}
				ya -= abs(ya);
			} else {
				if (!collision(xa, abs(ya))/* && !eCol(0, convert(ya))*/) {
					if (!eCol(0, convert(ya))) {
					this.y += ya;
					}
			}
				ya = 0;
			}
		}
		} else {
		
		while (xa != 0) {
				if (Math.abs(xa) > 1) {
					if (!collision(abs(xa), ya)/* && !eCol(convert(xa), 0)*/) {
								this.x += abs(xa);
					}
					xa -= abs(xa);
				} else {
					if (!collision(abs(xa), ya)/* && !eCol(convert(xa), 0)*/) {
						this.x += xa;
				}
					xa = 0;
			}
		}
		while (ya != 0) {
			if (Math.abs(ya) > 1) {
				if (!collision(xa, abs(ya))/* && !eCol(0, convert(ya))*/) {
						this.y += abs(ya);
				}
				ya -= abs(ya);
			} else {
				if (!collision(xa, abs(ya))/* && !eCol(0, convert(ya))*/) {
					this.y += ya;
			}
				ya = 0;
			}
		}
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
		
	}
		

	public void pull(Entity e, double rate) {
		double xpa = 0, ypa = 0;
		         double px = (int) e.getX();
		         double py = (int) e.getY();
		         Vector2i start = new Vector2i((int) getX() >> Game.TILE_BIT_SHIFT, (int)getY() >> Game.TILE_BIT_SHIFT);
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
		    	  level.add(new DefaultParticle((int) (x), (int) (y), 4, 2, 0x000000, 4));
		    	  walking  = true;
		      } else {
		    	  walking = false;

		      }
			}
	
	public void pull(int x, int y, double rate) {
		double xpa = 0, ypa = 0;
		         double px = x;
		         double py = y;
		         Vector2i start = new Vector2i((int) getX() >> Game.TILE_BIT_SHIFT, (int)getY() >> Game.TILE_BIT_SHIFT);
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
	
	protected void crystallineBolt(double x, double y, double dir, Mob m) {
		Projectile WP2 = new CrystallineBolt(x, y, dir, m);
		level.add(WP2);

	}
	
	protected void eyeBolt(double x, double y, double dir) {
		Projectile WP2 = new GazerBolt(x, y, dir);
		level.add(WP2);

	}
	
	protected void fairyHeal(double x, double y, double dir) {
		Projectile WP2 = new FairyHeal(x, y, dir);
		level.add(WP2);

	}
	
	protected void voidOoze(double x, double y, double dir) {
		Projectile WP2 = new VoidOoze(x, y, dir);
		level.add(WP2);

	}
	
	protected void flotBolt(double x, double y, double dir) {
		Projectile WP2 = new FlotSpray(x, y, dir);
		level.add(WP2);

	}
	
	protected void mageBolt(double x, double y, double dir) {
		Projectile WP2 = new MageBolt(x, y, dir);
		level.add(WP2);

	}
	
	protected void rockBolt(double x, double y, double dir) {
		Projectile WP2 = new Rock(x, y, dir);
		level.add(WP2);

	}
	
	protected void archerArrow(double x, double y, double dir) {
		Projectile WP2 = new ArcherArrow(x, y, dir);
		level.add(WP2);

	}
	
	protected void fireBlast(double x, double y, double dir) {
		Projectile WP2 = new FireBlast(x, y, dir);
		level.add(WP2);

	}
	
	protected void stunTrap(double x, double y, double dir) {
		Projectile WP2 = new StunTrap(x, y, dir);
		level.add(WP2);
	}
	
	protected void swarmMine(double x, double y, double dir) {
		Projectile WP2 = new SwarmMine(x, y, dir);
		level.add(WP2);
	}
	
	protected void swarmShoot(double x, double y, double dir) {
		Projectile WP2 = new SwarmShoot(x, y, dir);
		level.add(WP2);
	}
	
	protected void swarmStun(double x, double y, double dir) {
		Projectile WP2 = new SwarmStun(x, y, dir);
		level.add(WP2);
	}
	
	protected void slowTrap(double x, double y, double dir) {
		Projectile WP2 = new SlowTrap(x, y, dir);
		level.add(WP2);

	}
	
	protected void frostBomb(double x, double y, double dir) {
		Projectile WP2 = new FrostBomb(x, y, dir);
		level.add(WP2);
	}
	
	protected void frostBreath(double x, double y, double dir) {
		Projectile WP2 = new FrostBreath(x, y, dir);
		level.add(WP2);
	}
	
	protected void frostShard(double x, double y, double dir) {
		Projectile WP2 = new FrostShard(x, y, dir);
		level.add(WP2);
	}
	
	protected void fairyBolt(double x, double y, double dir) {
		Projectile WP2 = new FairyBolt(x, y, dir);
		level.add(WP2);

	}
	
	protected void baskerBolt(double x, double y, double dir) {
		Projectile WP2 = new BaskerBolt(x, y, dir);
		level.add(WP2);

	}
	
	protected void desertFlyBolt(double x, double y, double dir) {
		Projectile WP2 = new FlySting(x, y, dir);
		level.add(WP2);

	}
	
	protected void desertBeetleBolt(double x, double y, double dir) {
		Projectile WP2 = new BeetleSting(x, y, dir);
		level.add(WP2);

	}
	
	protected void deathStalkBolt(double x, double y, double dir) {
		Projectile WP2 = new DeathStalkBolt(x, y, dir);
		level.add(WP2);

	}
	
	protected void fairyFireBolt(double x, double y, double dir) {
		Projectile WP2 = new FireBall(x, y, dir);
		level.add(WP2);

	}
	
	protected void fairyStunBolt(double x, double y, double dir) {
		Projectile WP2 = new StunBall(x, y, dir);
		level.add(WP2);

	}
	
	protected void blastingBolt(double x, double y, double dir, Mob m) {
		Projectile WP2 = new BlastingBolt(x, y, dir, m);
		level.add(WP2);

	}
	
	protected void mendersBolt(double x, double y, double dir, Mob m) {
		Projectile WP2 = new MendersBolt(x, y, dir, m);
		level.add(WP2);

	}
	
	protected void orbweaversBolt(double x, double y, double dir, Mob m) {
		Projectile WP2 = new OrbweaversBolt(x, y, dir, m);
		level.add(WP2);

	}
	
	protected void possibilityBolt(double x, double y, double dir, Mob m) {
		Projectile WP2 = new PossibilityBolt(x, y, dir, m);
		level.add(WP2);

	}
	
	protected void refractorsBolt(double x, double y, double dir, Mob m) {
		Projectile WP2 = new RefractorsBolt(x, y, dir, m);
		level.add(WP2);

	}
	
	protected void sageBolt(double x, double y, double dir, Mob m) {
		Projectile WP2 = new SageBolt(x, y, dir, m);
		level.add(WP2);

	}
	
	protected void silversmithBolt(double x, double y, double dir, Mob m) {
		Projectile WP2 = new SilversmithBolt(x, y, dir, m);
		level.add(WP2);

	}
	
	protected void trappersOrb(double x, double y, double dir, Mob m) {
		Projectile WP2 = new TrappersOrb(x, y, dir, m);
		level.add(WP2);

	}
	
	public void blinkBolt(double x, double y, double dir, boolean particles, Mob m) {
		Projectile BB = new BlinkBolt(x, y, dir, particles, m);
		level.add(BB);
	}
	
	public void fireAbility(double x, double y, double dir, Player player, Ability ab) {
		Ability sel = ab;
		sel.x = x;
		sel.y = y;
		sel.angle = dir;
		
		level.add(sel);
	}
	
	protected void shootingtracker(double x, double y, double dir, boolean Particles) {
		Projectile WP2 = new WizardProjectile2(x, y, dir, Particles);
		level.add(WP2);

	}
	protected void shootingtracker(double x, double y, double dir) {
		Projectile WP2 = new WizardProjectile2(x, y, dir, false);
		level.add(WP2);

	}
	protected void DemonOrb(double x, double y, double dir) {
		Projectile DO = new DemonOrb(x, y, dir);
		level.add(DO);

	}
	protected void shootingSlime(double x, double y, double dir) {
		Projectile SB = new SlimeBall(x, y, dir);
		level.add(SB);

	}
	protected void shootingtrackerSpam(double x, double y, double dir) {
		Projectile sts = new shootingtrackerSpam(x, y, dir);
		level.add(sts);

	}
	protected void RockTHROWING(double x, double y, double dir, Mob mob) {
		Projectile RT = new ThrowableRock(x, y, dir, mob);
		level.add(RT);

	}
	protected void HealingSpell(double x, double y, double dir, Mob mob) {
		Ability HS = new HealingSpell(x, y, dir, mob);
		level.add(HS);
	}
	protected void SummonFamiliar(double x, double y, double dir) {
	//	level.add(new Guard((int)x / 16, (int)y / 16, true));
		Ability SF = new SummonFamiliar(x, y, dir);
		level.add(SF);
	}
	public void GoldenOrb(double x, double y, double dir) {
		Ability GO = new GoldenOrb(x, y, dir);
		level.add(GO);

	}
	public void GoldenOrb(double x, double y, double dir, Mob mob) {
		Ability GO = new GoldenOrb(x, y, dir, mob);
		level.add(GO);

	}
	
	public void PierceSpell(double x, double y, double dir) {
		Ability PS = new SearingBolt(x, y, dir);
		level.add(PS);

	}
	public void PierceSpell(double x, double y, double dir, Mob mob) {
		Ability PS = new SearingBolt(x, y, dir, mob);
		level.add(PS);

	}
	
	public void RadialBlast(double x, double y, double dir, Mob mob) {
		Ability RB = new RadialBlast(x, y, dir, mob);
		level.add(RB);

	}
	public void RadialBlast(double x, double y, double dir) {
		Ability RB = new RadialBlast(x, y, dir);
		level.add(RB);

	}
	
	protected void Equilibrium(double x, double y, double dir, Mob mob) {
		Ability EQ= new Equilibrium(x, y, dir, mob);
		level.add(EQ);
	}
	
	protected void Optical(double x, double y, double dir) {
		Projectile OP = new Optical(x, y, dir);
		level.add(OP);

	}
	
	protected void Zombie(int x, int y) {
		Zombie zb = new Zombie(x, y);
		level.add(zb);

	}
	protected void RedShockwave(double x, double y, double dir) {
		Projectile RS = new RedShockwave(x, y, dir);
		level.add(RS);

	}
	
	protected void copperShockwave(double x, double y, double dir) {
		Projectile RS = new CopperShockwave(x, y, dir);
		level.add(RS);

	}

	protected void stoneSpike(double x, double y, double dir) {
		Projectile RS = new StoneSpike(x, y, dir);
		level.add(RS);

	}
	
	
	protected void PoisonGoo(double x, double y, double dir, double speed) {
		Projectile PG = new PoisonGoo(x, y, dir, speed);
		level.add(PG);

	}
	
	protected void RedShockwave(double x, double y, double dir, Mob mob) {
		Projectile NW = new NullWave(x, y, dir, mob);
		level.add(NW);

	}
		//System.out.println("Angle: " + dir);
	
	 
	protected boolean collision(double xa, double ya) {
		solid = false;
		for (int c = 0; c < 4; c++) {
			double xt = ((x + xa) - c % 2 * 15 - 0) / TileCoord.TILE_SIZE;
			double yt = ((y + ya) - c / 2 * 15 - 0) / TileCoord.TILE_SIZE;
			if (this instanceof Player) {
				if (((Player)this).ridingOn != null) {
					yt = ((y + ya) - c / 2 * 15 + 14) / TileCoord.TILE_SIZE;
				}
			}
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

	public void death() {
		
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




	

