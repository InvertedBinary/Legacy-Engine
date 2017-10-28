package com.IB.SL.entity.projectile;

import java.util.List;

import com.IB.SL.entity.inventory.effects.Slow;
import com.IB.SL.entity.mob.PlayerMP;
import com.IB.SL.entity.spawner.RockShatterSpawner;
import com.IB.SL.entity.spawner.RockSpawner;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.util.Sound;


public class VoidOoze extends Projectile{
	
	public static int FIRE_RATE = 80;
	public static int time = 0;
	public boolean isPlayers = false;
	
	public VoidOoze(double x, double y, double dir) {
		super(x, y, dir);
		range = random.nextInt(55) + 60;		
		speed = 2;
		damage = 11;
	
		sprite = Sprite.VoidSlingerBolt;
		this.collisionSound = Sound.erie;
		this.breakParticle = 1;
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
		this.id = 3;
	}
	
	
	int rot_time = 0;
	public void update() {
		List<PlayerMP> players = level.players;

		rot_time++;
		
		if (rot_time > 4) {			
		angle++;
		sprite = Sprite.rotate(Sprite.VoidSlingerBolt, angle);
		rot_time = 0;
		}

		
			speed = 2;
			mod = 1500;

			PlayerCollision(players, this); 
			//PlayerCollision(players, level.Projectiles, 5, 5); 			
		
		if (level.tileCollision((int) (x + nx), (int) (y + ny), 4, -2, 8)) {
			level.add(new RockShatterSpawner((int) (x + nx), (int) (y + ny), 25, 4, level));
			Sound.Play(Sound.erie, false);
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
			level.add(new RockSpawner((int) (x + nx), (int) (y + ny), 80, 1, level));
			remove();
		}
	}
	
	public void addEffect(PlayerMP player) {
		player.effects.addEffect(new Slow(player, 120));
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