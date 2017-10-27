package com.IB.SL.entity.abilities;

import java.util.List;

import com.IB.SL.Game;
import com.IB.SL.entity.Entity;
import com.IB.SL.entity.mob.Mob;
import com.IB.SL.entity.projectile.Projectile;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;


public class HeatSeekingBolt extends Ability{
	
	int ExpV = (random.nextInt(1) + 3);
	transient double onx = 0, ony = 0;
	public HeatSeekingBolt(double x, double y, double dir) {
		super(x, y, dir);
		range = 200;
		speed = 4;
		damage = 1;
		p = this;

		basicInitialization();
	}
	
	@Override
	public boolean use(Projectile p, Ability ability, Mob origin) {
		p = new HeatSeekingBolt(origin.getX(), origin.getY(), angle(), origin);
		super.use(p, ability, origin);
		return true;
	}
	
	public HeatSeekingBolt(double x, double y, double dir, Mob mob) {
		super(x, y, dir);
		range = 200;
		speed = 4;
		damage = 1 + (mob.stat_VIT / 10);
		
		basicInitialization();

	}
	
	public void basicInitialization() {
		//System.out.println("SEARINGBOLT BASIC INIT CALLED");
		this.name = "Heat_Seeking_Bolt";
		sprite = Sprite.rotate(Sprite.Heatseeking_bolt, angle);
		unlock = 6;
		manaCost = 1;
		FIRE_RATE = 5;
		//activator = KeyEvent.VK_3;
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
		
		onx = nx;
		ony = ny;
		displaySprite = Sprite.Heatseeking_bolt_Display;
		this.id = 11;

	}
	
	
	
	public void update() {
		move();
		List<Entity> entities = level.entities;
		Collision(this, entities);
		
		if (!level.tileCollision((int) (x + nx), (int) (y + ny), 4, -2, 8)) {
	}	else {
	}
	}
		
	protected void move() {
		List<Entity> e = level.getEntities(level.getClientPlayer(), 100, HOSTILITY.AGR, HOSTILITY.BOSS);
//		List<Entity> es = level.getEntities((int)level.getClientPlayer().getX(), (int)level.getClientPlayer().getY() - 4, 16);
		System.out.println("SIZE: " + e.size());
		if (e.size() > 0) {
			Entity ee = e.get(0);
			System.out.println(ee + " X: " + (ee.getX() / 16) + " Y: " + (ee.getY() / 16)+ " NX: " + (x / 16) + " NY: " + (y / 16));
			if ((x / 16) < ee.getX() / 16) {
				nx = speed / 2;
			} else if ((x / 16) > ee.getX() / 16) {
				nx = -(speed) / 2;
			}
			
			if ((y / 16) < ee.getY() / 16) {
				ny = speed / 2;
			} else if ((y / 16) > ee.getY() / 16) {
				ny = -(speed) / 2;
			}
			x += nx;
			y += ny;
		} 
		
		if (e.size() <= 0) {
			x += onx;
			y += ony;
		}
		if (distance() > range) remove();
		
		if (Game.devModeInfoOn) {
		System.out.println("Distance (In Tiles): " + distance() / 16);
	}
	}

	
	public double distance() {
		double dist = 0;
		dist = Math.sqrt(Math.abs((xOrigin - x) * (xOrigin - x) + (yOrigin -y) * (yOrigin - y)));
		return dist;
	}

	public void render(Screen screen) {
		screen.renderProjectile((int)x - 8,(int)y - 14, this);
	}
}