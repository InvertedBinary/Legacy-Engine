package com.IB.SL.entity.projectile;

import java.util.List;
import java.util.Random;

import com.IB.SL.Boot;
import com.IB.SL.Game;
import com.IB.SL.entity.Entity;
import com.IB.SL.entity.mob.Mob;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.util.Sound;


public class Arrow extends Projectile {
	public static int FIRE_RATE = 25;
	public int xa, ya;
	int ExpV = (random.nextInt(1) + 3);
	Random dropChance = new Random();
	int drop;

	protected static Random Random = new Random();
	
	public Arrow(double x, double y, double dir) {
		super(x, y, dir);
		damage = 1;
		basicInitialization();
	}
	
	public Arrow(double x, double y, double dir, Mob mob) {
		super(x, y, dir);
		damage = 1;
		basicInitialization();
	}
	
	public void basicInitialization() {
		range = random.nextInt(80) + 260;
		drop = (dropChance.nextInt((6 - 1) + 1) + 1);
		speed = 3;

		sprite = Sprite.rotate(Sprite.Arrow, angle);	
		manaCost = 0;
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
		this.id = 4;
		Sound.Play(Sound.Spell2,  false);
	}
	

	
	public void update() {

		/*if (ny < -1 && nx > -1) {
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

		if (!level.tileCollision((int) (x + nx), (int) (y + ny), 1, 0, 6)) {
			move();
			Collision_Pierce(this, entities);
		} else {
			time++;
		}
		
		//move(xx + xa, (yy + ya) + (zz + za));
	}
	
	public void move() {
		moveArc();
	}

	public void render(Screen screen) {
		xOffset = -8;
		yOffset = -14;
		screen.renderProjectile((int)x + xOffset,(int)y + yOffset, this);
		if (Game.devModeOn) screen.drawRect((int)x - 3, (int)y - 9, 5, 5, 0x0093FF, true);
	}
}