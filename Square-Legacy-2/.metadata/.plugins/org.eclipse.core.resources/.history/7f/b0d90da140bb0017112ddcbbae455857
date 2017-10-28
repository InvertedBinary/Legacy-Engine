package com.IB.SL.entity.abilities;

import java.util.List;

import com.IB.SL.Game;
import com.IB.SL.entity.Entity;
import com.IB.SL.entity.mob.Mob;
import com.IB.SL.entity.mob.Player;
import com.IB.SL.entity.projectile.Projectile;
import com.IB.SL.entity.spawner.BleedSpawner;
import com.IB.SL.entity.spawner.WallParticleSpawner;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.level.TileCoord;


public class GoldenOrb extends Ability{
	
	int ExpV = (random.nextInt(1) + 3);
	private int time;
	
	
	
	public GoldenOrb(double x, double y, double dir, Mob mob) {
		super(x, y, dir);
		range = random.nextInt(80) + 260;
		damage = 6 + (mob.stat_MAT * 0.8);
		
		basicInitialization();
	}
	
	public GoldenOrb(double x, double y, double dir) {
		super(x, y, dir);
		range = random.nextInt(80) + 260;
		damage = 8;
		
		basicInitialization();
	}
	
	public void basicInitialization() {
		speed = 0.7;
		this.name = "Golden_Orb";
		sprite = Sprite.GoldenOrb;
		unlock = 5;
		manaCost = 8;
		FIRE_RATE = 60;
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
		this.displaySprite = sprite;
		this.id = 8;
	}
	
	public boolean use(Projectile p, Ability ability, Mob origin) {
		p = new GoldenOrb(origin.getX(), origin.getY(), angle(), origin);
		super.use(p, ability, origin);
		return true;
	}
	
	public void update() {

		List<Entity> entities = level.entities;
		Collision(this, entities);	

		if (level.tileCollision((int) (x + nx), (int) (y + ny), 4, -2, 8)) {
			level.add(new WallParticleSpawner((int) (x + nx), (int) (y + ny), 2, 2, level));
			 remove();
	}
		move();
		
	}

	protected void move() {
		x += nx;
		y += ny;
		if (distance() > range) remove();

		
		if (Game.devModeInfoOn) {
		System.out.println("Distance (In Tiles): " + distance() / TileCoord.TILE_SIZE);
	}
	}
	
	public double distance() {
		double dist = 0;
		dist = Math.sqrt(Math.abs((xOrigin - x) * (xOrigin - x) + (yOrigin -y) * (yOrigin - y)));
		return dist;
	}

	public void render(Screen screen) {
		screen.renderProjectile((int)x - 8,(int)y - 14, this);
		screen.renderLight((int) x - level.radius, (int) y - level.radius - 4, level.radius, 20, 20, 20);
		List<Entity> entities = (level.AOEFull(screen, (int)x << Game.TILE_BIT_SHIFT, (int)y << Game.TILE_BIT_SHIFT, level.radius, false, 0xffffffff));
		//TODO: Fix AOE damage only working in up-left quadrant
		//TODO: use AOEFull for .xo, .yo

		
		for (int i = 0; i < entities.size(); i++) {
		if (entities.size() > 0) {
				if (entities.get(i) instanceof Player) {
				} else {
				if (entities.get(i) instanceof Mob) {
					if (entities.get(i) != null) {
						if (!entities.get(i).invulnerable) {
							time++;
							if ((time % 55) == 0) {
				 	try {
						level.damage((int) (x + nx), (int)((y + ny)), (Mob) entities.get(i), entities.get(i).Exp, (int)this.damage / 3, Game.getGame().PersonNameGetter, ExpV);
						time = 0;

				 	} catch (Exception e) {
						System.out.println("?! Cast To Mob Exception!");
					}
						level.add(new BleedSpawner((int) entities.get(i).getX(), (int) entities.get(i).getY(), 15, 8, level));
						time = 0;

							}
						}
						}
					}
				}
			}
		}
	}
}
