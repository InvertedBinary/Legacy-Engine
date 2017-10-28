package com.IB.SL.entity.projectile;

import java.util.List;
import java.util.Random;

import com.IB.SL.entity.mob.PlayerMP;
import com.IB.SL.entity.mob.hostile.minions.OpticalMob;
import com.IB.SL.entity.spawner.WallParticleSpawner;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;


public class Optical extends Projectile{
	
	public static int FIRE_RATE = 135;
	protected static java.util.Random Random = new Random();
	private int randomint = random.nextInt(30) + 12;
	private int spawnedOpticals = 0;
	
	public Optical(double x, double y, double dir) {
		super(x, y, dir);
		range = 110;
		speed = 1.8;
		damage = 10;
		sprite = Sprite.OpticalProjectile;
		
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
		this.id = 0;
	}
	
	public void update() {	
		time++;
		List<PlayerMP> players = level.players;
		PlayerCollision(players, this); 
		if (level.tileCollision((int) (x + nx), (int) (y + ny), 4, -2, 8)) {
			level.add(new WallParticleSpawner((int) (x + nx), (int) (y + ny), 2, 2, level));
		//	level.add(new ParticleSpawner((int) level.getPlayerAt(0).getX(), (int)level.getPlayerAt(0).getY(), 2, 2, level));
			 remove();
	}
		move(xa, ya);		
	}
	

		


	protected void move(double xa2, double ya2) {
		x += nx;
		y += ny;
		if (distance() > range) {
		for(int i1 = 0; i1 < 1; i1++){
			if (spawnedOpticals <= 14 && randomint > 29) {
				spawnedOpticals++;
				System.out.println(spawnedOpticals);
	         level.add(new OpticalMob((int)x / 16, (int)y / 16));
			}
		}
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