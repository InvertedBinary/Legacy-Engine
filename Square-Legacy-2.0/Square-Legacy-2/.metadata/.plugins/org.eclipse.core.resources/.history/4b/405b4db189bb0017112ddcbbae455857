package com.IB.SL.entity.abilities;

import java.util.List;

import com.IB.SL.Game;
import com.IB.SL.entity.Entity;
import com.IB.SL.entity.inventory.effects.onFire;
import com.IB.SL.entity.mob.Mob;
import com.IB.SL.entity.projectile.Projectile;
import com.IB.SL.entity.spawner.FireParticleSpawner;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;


public class SearingBolt extends Ability{
	
	int ExpV = (random.nextInt(1) + 3);

	public SearingBolt(double x, double y, double dir) {
		super(x, y, dir);
		range = random.nextInt(50) + 70;
		speed = 4;
		damage = 2;
		p = this;

		basicInitialization();
	}
	
	@Override
	public boolean use(Projectile p, Ability ability, Mob origin) {
		p = new SearingBolt(origin.getX(), origin.getY(), angle(), origin);
		super.use(p, ability, origin);
		return true;
	}
	
	protected void doEffect(Entity e) {
		e.effects.addEffect(new onFire(e, 180));
	}
	
	public SearingBolt(double x, double y, double dir, Mob mob) {
		super(x, y, dir);
		range = random.nextInt(50) + 70;
		speed = 4;
		damage = 2 + (mob.stat_MAT * 0.6);
		
		basicInitialization();

	}
	
	public void basicInitialization() {
		//System.out.println("SEARINGBOLT BASIC INIT CALLED");
		this.name = "Searing_Bolt";
		sprite = Sprite.rotate(Sprite.Searing_Bolt, angle);
		unlock = 3;
		manaCost = 3;
		FIRE_RATE = 45;
		//activator = KeyEvent.VK_3;
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
		displaySprite = Sprite.Searing_Bolt_Display;
		this.id = 11;

	}
	
	
	
	public void update() {
		List<Entity> entities = level.entities;
		Collision_Pierce(this, entities);

		if (!level.tileCollision((int) (x + nx), (int) (y + ny), 4, -2, 8)) {
			level.add(new FireParticleSpawner((int) (x + nx), (int) (y + ny), 2, 2, level, angle++));
	}	else {
		level.add(new FireParticleSpawner((int) (x + nx), (int) (y + ny), 2, 2, level, angle++));
	}
		move();
	}
		
	protected void move() {
		x += nx;
		y += ny;
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