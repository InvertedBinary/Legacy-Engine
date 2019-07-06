package com.IB.LE2.world.entity.projectile;

import java.util.List;
import java.util.Random;

import com.IB.LE2.Boot;
import com.IB.LE2.media.graphics.Screen;
import com.IB.LE2.media.graphics.Sprite;
import com.IB.LE2.world.entity.Entity;
import com.IB.LE2.world.entity.emitter.WallParticleSpawner;
import com.IB.LE2.world.entity.mob.Mob;

public class DebugProjectile extends Projectile {
	public static int FIRE_RATE = 25;
	public int xa, ya;
	int ExpV = (random.nextInt(1) + 3);
	Random dropChance = new Random();
	int drop;

	protected static Random Random = new Random();
	
	public DebugProjectile(double x, double y, double dir) {
		super(x, y, dir);
		damage = 1;
		basicInit();
	}
	
	public DebugProjectile(double x, double y, double dir, Mob mob) {
		super(x, y, dir);
		damage = 1;
		basicInit();
	}
	
	void basicInit() {
		range = random.nextInt(80) + 260;
		drop = (dropChance.nextInt((6 - 1) + 1) + 1);
		speed = 3;
		sprite = /*Sprite.rotate(*/Sprite.get("Grass")/*, angle)*/;	
		manaCost = 0;
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
		this.id = 7777777;
		//Sound.Play(Sound.Spell2,  false);
	}
	

	
	public void update() {
		List<Entity> entities = level.entities;
			level.add(new WallParticleSpawner((int) (x() + nx), (int) (y() + ny - 4), 4, 4, level));
			
			DebugCollision(this, entities);
			
		move();
	}
	
	public boolean DebugCollision(Projectile p, List<Entity> entities) {
		Entity ee = Collide(p, entities);
		if (ee != null) {
			Boot.log("===============================================", false);
			Boot.log("ID: " + ee.UUID, true);
			//Boot.log("HOSTILITY: " + ee.hostility, true);
			Boot.log("X: " + ee.x() / 16, true);
			Boot.log("Y: " + ee.y() / 16, true);
			Boot.log("===============================================", false);
			remove();
			return true;
		}
		return false;
	}
	
	protected void move() {
		x(x() + nx);
		y(y() + ny);
		if (distance() > range) remove();
	}

	public double distance() {
		double dist = 0;
		dist = Math.sqrt(Math.abs((xOrigin - x()) * (xOrigin - x()) + (yOrigin -y()) * (yOrigin - y())));
		return dist;
	}

	public void render(Screen screen) {
		screen.drawEntity((int)x() - 8,(int)y() - 14, this);
		if (Boot.get().devModeOn) screen.drawRect((int)x() - 3, (int)y() - 9, 5, 5, 0x0093FF, true);

	}
}