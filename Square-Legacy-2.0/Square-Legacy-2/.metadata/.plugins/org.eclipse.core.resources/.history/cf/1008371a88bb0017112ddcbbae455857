package com.IB.SL.entity.projectile;

import java.util.List;

import com.IB.SL.entity.inventory.effects.freeze;
import com.IB.SL.entity.mob.PlayerMP;
import com.IB.SL.graphics.Screen;
import com.IB.SL.graphics.Sprite;


public class FrostBreath extends Projectile{
	
	public static int FIRE_RATE = 120;
	public static int time = 0;
	public FrostBreath(double x, double y, double dir) {
		super(x, y, dir);
		range = random.nextInt(100) + 150;
		speed = (random.nextInt(1) + 1) / 1.5;
		damage = 1;
		sprite = Sprite.rotate(Sprite.FrostBreath, angle);
		this.breakParticle = 0;
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
		this.id = 7011;
	}
	
	public void addEffect(PlayerMP player) {
		player.effects.addEffect(new freeze(player, 10));
	}
	
	public void update() {
		List<PlayerMP> players = level.players;
		PlayerCollision(players, this); 
	     
		
		
		if (level.tileCollision((int) (x + nx), (int) (y + ny), 4, -2, 8)) {
			 remove();
	}
	
		move();
		
	}

	protected void move() {
		x += nx;
		y += ny;
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