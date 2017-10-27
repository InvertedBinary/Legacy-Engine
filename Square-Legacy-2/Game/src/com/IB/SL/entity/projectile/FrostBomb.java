package com.IB.SL.entity.projectile;

import java.util.List;
import java.util.Random;

import com.IB.SL.Game;
import com.IB.SL.Game.gameState;
import com.IB.SL.entity.inventory.effects.freeze;
import com.IB.SL.entity.inventory.item.Item;
import com.IB.SL.entity.mob.Mob;
import com.IB.SL.entity.mob.PlayerMP;
import com.IB.SL.entity.spawner.WallParticleSpawner;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;
import com.IB.SL.util.Sound;


public class FrostBomb extends Projectile {
	public static int FIRE_RATE = 25;
	public int xa, ya;
	int ExpV = (random.nextInt(1) + 3);
	Random dropChance = new Random();
	int drop;
	Item item;
	double round;

	protected static Random Random = new Random();
	
	public FrostBomb(double x, double y, double dir) {
		super(x, y, dir);
		damage = 23;
		basicInitialization();
	}
	
	public FrostBomb(double x, double y, double dir, Mob mob) {
		super(x, y, dir);
		damage = 1 + (mob.stat_ATC);
		basicInitialization();
	}
	
	public void basicInitialization() {
		range = random.nextInt(60) + 100;
		drop = (dropChance.nextInt((6 - 1) + 1) + 1);
		sprite = /*Sprite.rotate(*/Sprite.FrostBomb/*, angle)*/;	
		manaCost = 0;
		item = new Item();
		this.id = 4;
		Sound.Play(Sound.Spell2,  false);
		round = 0;
		life = 0;
	}
	
	public void addEffect(PlayerMP player) {
		player.effects.addEffect(new freeze(player, 100));
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
		List<PlayerMP> players = level.players;
		PlayerCollision(players, this);  	

		if (level.tileCollision((int) (x + nx), (int) (y + ny), 1, 0, 6)) {
			remove();
		} else {
			level.add(new WallParticleSpawner((int) (x + nx), (int) (y + ny - 4), 4, 4, level));
		}
		move();
	}
	
	protected void move() {
		
		if (round > (2 * Math.PI)) {
			round = 0;
			life++;
		}
		x += 0.25 * Math.sin(round);
		y += -0.25 * Math.cos(round);
		round += (Math.PI / 100);
		
		if (life > 20) remove();
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