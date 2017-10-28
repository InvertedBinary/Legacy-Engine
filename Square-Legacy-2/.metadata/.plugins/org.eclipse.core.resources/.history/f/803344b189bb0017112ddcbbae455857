package com.IB.SL.entity.abilities;

import java.util.List;

import com.IB.SL.Game;
import com.IB.SL.entity.Entity;
import com.IB.SL.entity.mob.Mob;
import com.IB.SL.entity.projectile.Projectile;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;


public class Kunai extends Ability{
	
	int ExpV = (random.nextInt(1) + 3);
	static boolean kunai_hit  = false;

	public Kunai(double x, double y, double dir) {
		super(x, y, dir);
		range = 90;
		speed = 5;
		damage = 1;
		p = this;

		basicInitialization();
	}
	
	@Override
	public boolean use(Projectile p, Ability ability, Mob origin) {
		p = new Kunai(origin.getX(), origin.getY(), angle(), origin);
		super.use(p, ability, origin);
		if (kunai_hit) {
			p = new Kunai(origin.getX(), origin.getY(), angle() + Math.PI / 16, origin);
			super.use(p, ability, origin);
			p = new Kunai(origin.getX(), origin.getY(), angle() + Math.PI / 32, origin);
			super.use(p, ability, origin);
			p = new Kunai(origin.getX(), origin.getY(), angle() - Math.PI / 16, origin);
			super.use(p, ability, origin);
			p = new Kunai(origin.getX(), origin.getY(), angle() - Math.PI / 32, origin);
			super.use(p, ability, origin);
			kunai_hit = false;
		}
		return true;
	}

	public Kunai(double x, double y, double dir, Mob mob) {
		super(x, y, dir);
		range = 90;
		speed = 5;
		damage = mob.stat_ATC;
		
		basicInitialization();

	}
	
	public void basicInitialization() {
		//System.out.println("SEARINGBOLT BASIC INIT CALLED");
		this.name = "Kunai";
		sprite = Sprite.rotate(Sprite.Kunai, angle);
		unlock = 11;
		manaCost = 0;
		staminaCost = 6;
		FIRE_RATE = 30;
		//activator = KeyEvent.VK_3;
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
		displaySprite = Sprite.Kunai_Display;
		this.id = 11;

	}
	
	
	
	public void update() {
		List<Entity> entities = level.entities;
		if (Collision(this, entities)) {
			kunai_hit = true;
		}

		if (!level.tileCollision((int) (x + nx), (int) (y + ny), 4, -2, 8)) {
	}	else {
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