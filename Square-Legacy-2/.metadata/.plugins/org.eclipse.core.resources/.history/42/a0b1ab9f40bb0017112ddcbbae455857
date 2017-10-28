package com.IB.SL.entity.projectile;

import java.util.List;
import java.util.Random;

import com.IB.SL.Game;
import com.IB.SL.Game.gameState;
import com.IB.SL.entity.Entity;
import com.IB.SL.entity.inventory.item.Item;
import com.IB.SL.entity.mob.Mob;
import com.IB.SL.entity.spawner.BleedSpawner;
import com.IB.SL.entity.spawner.ParticleSpawner;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.util.Sound;


public class Contradiction extends Projectile {
	public static int FIRE_RATE = 45;
	public int xa, ya;
	int ExpV = (random.nextInt(1) + 3);
	Random dropChance = new Random();
	int drop;
	Item item;

	protected static Random Random = new Random();
	
	public Contradiction(double x, double y, double dir) {
		super(x, y, dir);
		damage = 1;
		basicInit();
	}
	
	public Contradiction(double x, double y, double dir, Mob mob) {
		super(x, y, dir);
		damage = 1 + mob.stat_ATC / 5;

		basicInit();
	}
	
	void basicInit() {
		range = random.nextInt(80) + 260;
		drop = (dropChance.nextInt((6 - 1) + 1) + 1);
		speed = 3;
		sprite = Sprite.rotate(Sprite.Contradiction, angle);	
		manaCost = 0;
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
		item = new Item();
		this.id = 16;
		Sound.Play(Sound.Spell1,  false);

		
	}
	

	
	
	public void update() {
		
	/*	if (ny < -1 && nx > -1) {
			sprite = Sprite.CSCDL;		
		} else if (ny > 1 && nx > 1) {
			sprite = Sprite.bleed;
		} else if (nx < -1 && ny > -1) {
			sprite = Sprite.GoldenOrb;
		} else if (nx > 0 && ny < 0) {
			sprite = Sprite.BookShelf;
		}*/
		
/*	time++;
		if (time % 6 == 0) {
			sprite = Sprite.rotate(sprite, Math.PI / 20.0);
		}*/
		
		List<Entity> entities = level.entities;
		Collision(this, entities);
		
		if (level.tileCollision((int) (x + nx), (int) (y + ny), 1, 0, 6)) {
			level.add(new ParticleSpawner((int) level.getPlayerAt(0).getX(), (int)level.getPlayerAt(0).getY(), 2, 2, level));
			 remove();	
		} else {
			level.add(new BleedSpawner((int) (x + nx), (int) (y + ny - 4), 4, 4, level));		
		}

	
		move();
		
	}
	protected void move() {
		x += nx;
		y += ny;
		if (distance() > range) remove();
	}




	
	
	

	
	public double distance() {
		double dist = 0;
		dist = Math.sqrt(Math.abs((xOrigin - x) * (xOrigin - x) + (yOrigin -y) * (yOrigin - y)));
		return dist;
	}

	public void render(Screen screen) {
		screen.renderProjectile((int)x - 8,(int)y - 14, this);
		if (Game.getGame().gameState == gameState.INGAME_A) screen.drawRect((int)x - 3, (int)y - 9, 5, 5, 0x0093FF, true);

	}
}