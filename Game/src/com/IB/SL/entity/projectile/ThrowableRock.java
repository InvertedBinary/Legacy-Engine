package com.IB.SL.entity.projectile;

import java.util.List;

import com.IB.SL.Boot;
import com.IB.SL.entity.Entity;
import com.IB.SL.entity.mob.Mob;
import com.IB.SL.entity.mob.PlayerMP;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.util.Sound;


public class ThrowableRock extends Projectile{
	
	public static int FIRE_RATE = 80;
	public static int time = 0;
	public boolean isPlayers = false;
	
	private Mob mob;
	public ThrowableRock(double x, double y, double dir, Mob mob) {
		super(x, y, dir);
		range = random.nextInt(55) + 60;
		speed = 1.5;
		if (mob != Boot.get().getPlayer())		
			speed = 2;
		damage = 3;
		this.mob = mob;
		sprite = Sprite.RockTHROWING;
		this.collisionSound = Sound.Rock;
		this.breakParticle = 1;
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
		this.id = 3;
	}
	
	
	int rot_time = 0;
	public void update() {
		List<PlayerMP> players = level.players;
		List<Entity> entities = level.entities;
		rot_time++;
		
		if (rot_time > 4) {			
		angle++;
		sprite = Sprite.rotate(Sprite.RockTHROWING, angle);
		rot_time = 0;
		}

		if (mob == Boot.get().getPlayer()) {			
			Collision(this, entities); 
		} else {
			speed = 2;
			mod = 1500;

			PlayerCollision(players, this); 
			//PlayerCollision(players, level.Projectiles, 5, 5); 			
		}
		
		if (level.tileCollision((int) (x + nx), (int) (y + ny), 4, -2, 8)) {
			//level.add(new RockShatterSpawner((int) (x + nx), (int) (y + ny), 25, 4, level));
			Sound.Play(Sound.Rock, false);
			 remove();
	}
		if (time > 400) {
			remove();
		}
		
		move();
		
	}
	
	int mod = 1000;
	protected void move() {
		x += nx;
		y += ny -= mod;
		if (distance() > range) {
			//level.add(new RockSpawner((int) (x + nx), (int) (y + ny), 80, 1, level));
			remove();
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