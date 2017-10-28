package com.IB.SL.entity.projectile;

import java.util.List;
import java.util.Random;

import com.IB.SL.Game;
import com.IB.SL.Game.gameState;
import com.IB.SL.entity.Entity;
import com.IB.SL.entity.inventory.effects.freeze;
import com.IB.SL.entity.inventory.item.Item;
import com.IB.SL.entity.mob.Mob;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.util.Sound;


public class IceArrow extends Projectile {
	public static int FIRE_RATE = 25;
	public int xa, ya;
	int ExpV = (random.nextInt(1) + 3);
	Random dropChance = new Random();
	int drop;
	Item item;

	protected static Random Random = new Random();
	
	public IceArrow(double x, double y, double dir) {
		super(x, y, dir);
		damage = 1;
		basicInitialization();
	}
	
	public IceArrow(double x, double y, double dir, Mob mob) {
		super(x, y, dir);
		damage = 1 + (mob.stat_ATC / 5);
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
		item = new Item();
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
		
		if (time > 300) {
			remove();
		}
		
		za -= 0.9;
		
		if (zz < 0) {
			zz = 0;
			za *= -0;
			xa *= 0;
			ya *= 0;
		}
		
		//move(xx + xa, (yy + ya) + (zz + za));
	}
	
	protected void move() {
		double deltaX = x - (x+nx);
		double deltaY = (y - (y+ny)) - (zz + za) / 900;
		this.angle = Math.atan2(deltaY, deltaX);
		x += nx;
		y += ny -= (zz + za) / 900;
		sprite = Sprite.rotate(Sprite.Arrow, angle);	
		if (distance() > range) remove();
	}
	
	protected void doEffect(Entity e) {
		e.effects.addEffect(new freeze(e, 180));
	}

	public double distance() {
		double dist = 0;
		dist = Math.sqrt(Math.abs((xOrigin - x) * (xOrigin - x) + (yOrigin -y) * (yOrigin - y)));
		return dist;
	}

	public void render(Screen screen) {
		xOffset = -8;
		yOffset = -14;
		screen.renderProjectile((int)x + xOffset,(int)y + yOffset, this);
		if (Game.getGame().gameState == gameState.INGAME_A) screen.drawRect((int)x - 3, (int)y - 9, 5, 5, 0x0093FF, true);

	}
}