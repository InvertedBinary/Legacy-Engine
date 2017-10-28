package com.IB.SL.entity.projectile;

import java.util.List;

import com.IB.SL.Game;
import com.IB.SL.entity.Entity;
import com.IB.SL.entity.mob.Mob;
import com.IB.SL.entity.mob.PlayerMP;
import com.IB.SL.entity.spawner.RockShatterSpawner;
import com.IB.SL.entity.spawner.RockSpawner;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.util.Sound;


public class ThrowingKnife extends Projectile{
	
	public static int FIRE_RATE = 80;
	public static int time = 0;
	public boolean isPlayers = false;
	
	private Mob mob;
	public ThrowingKnife(double x, double y, double dir, Mob mob) {
		super(x, y, dir);
		range = random.nextInt(55) + 60;
		speed = 1.5;
		if (mob != Game.getGame().getPlayer())		
			speed = 2;
		damage = 3 + Game.getGame().getPlayer().Lvl;
		this.mob = mob;
		sprite = Sprite.knifeThrow;
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
		sprite = Sprite.rotate(Sprite.knifeThrow, angle);
		rot_time = 0;
		}

		if (mob == Game.getGame().getPlayer()) {			
			Collision(this, entities); 
		} else {
			speed = 2;
			mod = 1500;

			PlayerCollision(players, this); 
			//PlayerCollision(players, level.Projectiles, 5, 5); 			
		}
		
		if (level.tileCollision((int) (x + nx), (int) (y + ny), 4, -2, 8)) {
			level.add(new RockShatterSpawner((int) (x + nx), (int) (y + ny), 25, 4, level));
			Sound.Play(Sound.Rock, false);
			 remove();
	}
		if (time > 400) {
			remove();
		}
		
		za -= 0.9;
		
		if (zz < 0) {
			zz = 0;
			za *= -0;
			xa *= 0;
			ya *= 0;
		}
		
		move();
		
	}
	
	int mod = 1000;
	protected void move() {
		x += nx;
		y += ny -= (zz + za) / mod;
		if (distance() > range) {
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